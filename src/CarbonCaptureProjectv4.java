public class CarbonCaptureProjectv4 {
    public static void main (String[] args) {
        int node_count = 30;
        double proportion_of_sources = 0.4;
        double proportion_of_sinks = 0.4;
        double demand = 150;

        GraphGenerator graphGenerator = new GraphGenerator(node_count, proportion_of_sources, proportion_of_sinks);
        Cplex_Solver cplex_solver = new Cplex_Solver(new ILPGraph(0, node_count - 1, graphGenerator.getILPEdgeList(), node_count), demand);
        CSVEdgeWriter csvEdgeWriter = new CSVEdgeWriter("C:\\Users\\book_\\Documents\\Summer2019\\graphVisualizer\\edge_file_solution_tester.csv", cplex_solver.solvedILPEdges());

        Flow_Network flow_network = graphGenerator.getFlowNetwork();

        System.out.println();
        System.out.println();
        flow_network.solveCheapestPathHeuristic(demand);
        System.out.println("solve cheapest path heuristic:");
        System.out.println(flow_network.getFlow() + " cost: " + flow_network.getCost());
        flow_network.print_flow_edges();

        flow_network = graphGenerator.getFlowNetwork();
        System.out.println(flow_network.getFlow());

        System.out.println();
        System.out.println();
        flow_network.solveNathanielHeuristic(demand);
        System.out.println("solve Nathaniel heuristic:");
        System.out.println(flow_network.getFlow() + " cost: " + flow_network.getCost());
        flow_network.print_flow_edges();


        System.out.println();
        System.out.println();
        flow_network.solveSeanHeuristic(demand);
        System.out.println("solve Sean heuristic:");
        System.out.println(flow_network.getFlow() + " cost: " + flow_network.getCost());
        flow_network.print_flow_edges();

    }
}
