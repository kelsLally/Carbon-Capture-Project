import java.util.ArrayList;
/*

public class ILPGraph {
    private int source;
    private int sink;
    private ILPEdge[] edge_array;
    private int nodeCount;
    ArrayList <ILPEdge> [] adjacency_list_out;
    ArrayList <ILPEdge> [] adjacency_list_in;

    public ILPGraph(int s, int t, ILPEdge[] edge_array, int nodeCount) {
        this.source = s;
        this.sink = t;
        this.edge_array = edge_array;
        this.nodeCount = nodeCount;
        this.adjacency_list_out = makeAdjacencyListOut(edge_array, nodeCount);
        this.adjacency_list_in = makeAdjacencyListIn(edge_array, nodeCount);
    }

    private ArrayList <ILPEdge> [] makeAdjacencyListOut (ILPEdge[] edges, int number_of_nodes) {
        ArrayList <ILPEdge> [] solution = new ArrayList [nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            solution[i] = new ArrayList <ILPEdge> ();
        }
        for (ILPEdge e: edges) {
            int v1 = e.getStart();
            solution[v1].add(e);
        }
        return solution;
    }

    private ArrayList <ILPEdge> [] makeAdjacencyListIn (ILPEdge[] edges, int number_of_nodes) {
        ArrayList <ILPEdge> [] solution = new ArrayList [nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            solution[i] = new ArrayList <ILPEdge> ();
        }
        for (ILPEdge e: edges) {
            int v1 = e.getEnd();
            solution[v1].add(e);
        }
        return solution;
    }

    public int getSource() {
        return source;
    }

    public int getSink() {
        return sink;
    }

    public ILPEdge[] getILPEdge_array() {
        return edge_array;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public ArrayList<ILPEdge>[] getAdjacency_list_out() {
        return adjacency_list_out;
    }

    public ArrayList<ILPEdge>[] getAdjacency_list_in() {
        return adjacency_list_in;
    }

    @Override
    public String toString() {
        String graph = "";
        for (int i = 0; i < adjacency_list_out.length; i ++){
            System.out.print( adjacency_list_out[i] + "; ");
            System.out.println();
        }

        return "";
    }
}

 */

