import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.PriorityQueue;

class Node{	
	int id;
	//marks for the algorithm
	//------------------------------------
	boolean marked = false;
	Edge augmEdge = null; //the edge over which we brought the flow increase
	int incFlow = -1; //-1 means a potentially infinite flow
	//------------------------------------
	ArrayList<Edge> inEdges;
	ArrayList<Edge> outEdges;
	
	public Node(int i) {
		id = i;
		inEdges = new ArrayList<Edge>();
		outEdges = new ArrayList<Edge>();
	}
}

class Edge{
	int startID; 
	int endID;
	int capacity; 
	int currFlow;
	
	public Edge(int fromNode, int toNode, int capacity2) {
		startID = fromNode;
		endID = toNode;
		capacity = capacity2;
		currFlow = 0;
	}
}

class Network{
	Node[] nodes;
	
	/**
	 * Create a new network with n nodes (0..n-1).
	 * @param n the size of the network.
	 */
	public Network(int n){
		nodes = new Node[n];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i]= new Node(i);
		}
	}
	/**
	 * Add a connection to the network, with all the corresponding in and out edges.
	 * @param fromNode
	 * @param toNode
	 * @param capacity
	 */
	public void addConnection(int fromNode, int toNode, int capacity){
		Edge e = new Edge(fromNode, toNode, capacity);
		nodes[fromNode].outEdges.add(e);
		nodes[toNode].inEdges.add(e);
	}

	/**
	 * Reset all the marks of the algorithm, before the start of a new iteration.
	 */
	public void resetMarks(){
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].marked = false;
			nodes[i].augmEdge = null;
			nodes[i].incFlow = -1;
		}
	}
}

public class Izziv09 {

	public static void izpisiPovecujoco(Network network, Node v) {
		System.out.print(v.id);
		
		if (v.augmEdge == null) {
			System.out.println();
		}
		else if (v.augmEdge.endID == v.id) {
			System.out.print("+  ");
			izpisiPovecujoco(network, network.nodes[v.augmEdge.startID]);
		}
		else {
			System.out.print("-  ");
			izpisiPovecujoco(network, network.nodes[v.augmEdge.endID]);
		}
	}

	public static Node poisciPovecujoco(Network network) {
		PriorityQueue<Integer> oznacene = new PriorityQueue<>();
        
        Node izvor = network.nodes[0];
        izvor.marked = true;
        izvor.incFlow = Integer.MAX_VALUE;

        oznacene.add(0);

        while (oznacene.size() > 0) {
            Node u = network.nodes[oznacene.remove()];
            if (u == network.nodes[network.nodes.length - 1]) {
				return u;
            }

            for (Edge e : u.outEdges) {
                Node v = network.nodes[e.endID];
                if (!v.marked && e.currFlow < e.capacity) {
                    v.incFlow = Math.min(u.incFlow, e.capacity - e.currFlow);
                    v.marked = true;
					v.augmEdge = e;
                    oznacene.add(v.id);
                }
            }
            for (Edge e : u.inEdges) {
                Node v = network.nodes[e.startID];
                if (!v.marked && e.currFlow > 0) {
                    v.incFlow = Math.min(u.incFlow, e.currFlow);
                    v.marked = true;
					v.augmEdge = e;
					oznacene.add(v.id);
                }
            }
        }
		return null;
	}

	public static void povecajPretok(Network network, Node u, int delta) {
		if (u.augmEdge == null) {
			return;
		}

		if (u.augmEdge.endID == u.id) {
			u.augmEdge.currFlow += delta;
			povecajPretok(network, network.nodes[u.augmEdge.startID], delta);
		}
		else {
			u.augmEdge.currFlow -= delta;
			povecajPretok(network, network.nodes[u.augmEdge.endID], delta);
		}
	}

    public static void FordFulkerson(Network network) {
        Node povecujoca = null;
		while ((povecujoca = poisciPovecujoco(network)) != null) {
			System.out.print(povecujoca.incFlow + ": ");
			izpisiPovecujoco(network, povecujoca);
			
			povecajPretok(network, povecujoca, povecujoca.incFlow);
			network.resetMarks();
		}
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        Network network = new Network(n);

        while (sc.hasNextInt()) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int c = sc.nextInt();
            network.addConnection(u, v, c);
        }

        FordFulkerson(network);
		sc.close();
    }
}