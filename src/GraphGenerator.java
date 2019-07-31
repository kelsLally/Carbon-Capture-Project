import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class GraphGenerator {
    private int node_count; // total nodes including s and t
    private double prop_sources;
    private double prop_sinks;
    private Node[] nodes;
    private HashSet <Line> lines;
    private MultiEdge[] multiEdgeArray;
    //private MultiEdge[][] matrix;

    private static double[] CAP = new double[]{50, 200, 500, 1000};
    private static double[] FC = new double[]{75, 150, 300, 550};
    private static double[] VC = new double[]{1, .8, .6, .4};

    public GraphGenerator (int node_count, double prop_source, double prop_sink) {
        this.node_count = node_count;
        this.prop_sources = prop_source;
        this.prop_sinks = prop_sink;
        final int X_MAX = 100;
        final int Y_MAX = 100;

        nodes = generateNodeArray(node_count, X_MAX, Y_MAX);

        // generate the virtual edges between super source/ sink and sources/ sinks
        //matrix = new MultiEdge[node_count][node_count];
        ArrayList <MultiEdge> virtualEdges = new ArrayList<>();
        for (int i = 0; i < nodes.length; i ++) {
            switch (nodes[i].getNode_type()) {
                case SOURCE:
                    virtualEdges.add(generateVirtualEdge(0, i));
                    break;
                case SINK:
                    virtualEdges.add(generateVirtualEdge(i, nodes.length - 1));
                    break;
            }
        }

        // find the distances between all nodes
        double [][] distance_matrix = new double [node_count - 2][node_count - 2];
        for (int i = 1; i < node_count - 1; i ++) {
            for (int j = 1; j < node_count - 1; j ++) {
                distance_matrix[i - 1][j - 1] = getLength(nodes[i].getX(), nodes[i].getY(), nodes[j].getX(), nodes[j].getY());
            }
        }

        lines = new HashSet<>();
        for (int i = 1; i < node_count - 1; i ++) {
            double ultmin = 0;
            for (int j = 0; j < 3; j ++) {
                // go through the array 3 times, find the smallest distance that is more than 0 and not already in the set
                double mindist = Integer.MAX_VALUE;
                int index = -1;
                for (int k = 0; k < distance_matrix[i - 1].length; k++){
                    if(distance_matrix[i - 1][k] > ultmin && !lines.contains(new Line(i, k + 1, distance_matrix[i - 1][k])) && distance_matrix[i - 1][k] < mindist){
                        mindist = distance_matrix[i - 1][k];
                        index = k;
                    }
                }
                ultmin = distance_matrix[i - 1][index];
                lines.add(new Line(i, index + 1, distance_matrix[i - 1][index]));
            }
        }



        Line[] lineArray = new Line[lines.size()];
        lines.toArray(lineArray);

        multiEdgeArray = new MultiEdge[lines.size()+ virtualEdges.size()];
        for (int i = 0; i < lineArray.length; i ++) {
            multiEdgeArray[i] = generateMultiEdge(lineArray[i].getStart(), lineArray[i].getEnd(), lineArray[i].getLength());
        }
        for (int i = lineArray.length; i < multiEdgeArray.length; i ++) {
            multiEdgeArray[i] = virtualEdges.get(i - lineArray.length);
        }



    }

    public Flow_Network getFlowNetwork(){
        MultiEdge[] copy = new MultiEdge[multiEdgeArray.length];
        for (int i = 0; i < multiEdgeArray.length; i++) {
            copy[i] = new MultiEdge(multiEdgeArray[i].getStart(), multiEdgeArray[i].getEnd(), multiEdgeArray[i].getCapacities(), multiEdgeArray[i].getFixed_costs(), multiEdgeArray[i].getVariable_costs());
        }
        return new Flow_Network(node_count, copy);
    }

    public ILPEdge[] getILPEdgeList() {

        ArrayList<ILPEdge> arrayListEdges = new ArrayList<>();
        for (int i = 0; i < multiEdgeArray.length; i++) {
            MultiEdge e = multiEdgeArray [i];
            for (int k = 0; k < e.getCapacities().length; k++) {
                ILPEdge ilpEdge = new ILPEdge(e.getStart(), e.getEnd(), e.getCapacities()[k], e.getFixed_costs()[k], e.getVariable_costs()[k]);
                arrayListEdges.add(ilpEdge);
            }
        }
        ILPEdge[] edges = new ILPEdge[arrayListEdges.size()];

        for (int i = 0; i < edges.length; i ++) {
            edges[i] = arrayListEdges.get(i);
        }
        CSVEdgeWriter csvEdgeWriter = new CSVEdgeWriter("C:\\Users\\book_\\Documents\\Summer2019\\MSUResearch\\graphVisualizer2_0\\edge_file_tester.csv", edges);
        return edges;
    }

    // creates an array of nodes including a super source and super sink
    private Node[] generateNodeArray (int node_count, int x_max, int y_max) {
        Node[] nodes = new Node[node_count];
        // make s
        nodes [0] = new Node(0, 0, NodeType.SUPER_SOURCE);

        for (int i = 1; i < node_count - 1; i++) {
            nodes[i] = generateNode(x_max, y_max);

        }
        // make t
        nodes [node_count - 1]  = new Node(0, 0, NodeType.SUPER_SINK);

        CSVNodeWriter csvNodeWriter = new CSVNodeWriter("C:\\Users\\book_\\Documents\\Summer2019\\graphVisualizer\\node_file_tester.csv", nodes);
        return  nodes;
    }

    // creates a node with a random position and a semi random type (40% source, 40% sink, 20% pipe junction)
    private Node generateNode (int max_x, int max_y) {
        Random rand = new Random ();
        int x = rand.nextInt(max_x);
        int y = rand.nextInt(max_y);
        NodeType nodeType = generateRandomNodeType();

        return new Node(x, y, nodeType);
    }

    // generates a random node type (source, sink, junction)
    private NodeType generateRandomNodeType () {
        Random rand = new Random();
        double key = rand.nextDouble();
        if (key >= 0 && key < prop_sources) {
            return NodeType.SOURCE;
        } else if (key < prop_sources + prop_sinks) {
            return NodeType.SINK;
        }
        return NodeType.JUNCTION;
    }

    // generates a random multiedge used for real edges
    private MultiEdge generateMultiEdge (int start, int end, double length) {
        Random rand = new Random();
        double [] capacities = new double[CAP.length];
        double [] fixed = new double[CAP.length];
        double [] variable = new double[CAP.length];
        for (int i = 0; i < CAP.length; i++){
            capacities[i] = CAP[i];
            fixed[i] = FC[i] * length * (rand.nextDouble()+.5);
            variable[i] = VC[i] * (rand.nextDouble()+.5);
        }
        return new MultiEdge(start, end, capacities, fixed ,variable);
    }

    // generates a virtual multi-edge representing the costs associated with a facility
    private MultiEdge generateVirtualEdge (int start, int end) {
        Random rand = new Random();
        double [] capacity = new double [] {rand.nextInt( 2000 - 500)+ 500};
        double [] fixed_cost = new double [] {rand.nextInt(1500 - 500) + 500};
        double [] variable_cost = new double [] {rand.nextInt(50 - 10) + 10};

        return new MultiEdge(start, end, capacity, fixed_cost, variable_cost);
    }


    // runs the distance formula to get the length of an edge
    private double getLength (double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2));
    }
}
