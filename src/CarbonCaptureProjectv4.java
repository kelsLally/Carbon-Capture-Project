public class CarbonCaptureProjectv4 {
    public static void main (String[] args) {

        Comparison_Test comparison_test = new Comparison_Test("C:\\Users\\book_\\Documents\\Summer2019\\MSUResearch\\average.csv", "C:\\Users\\book_\\Documents\\Summer2019\\MSUResearch\\allData.csv");
        comparison_test.runTest();

        /*int node_count = 20;
        double proportion_of_sources = 0.4;
        double proportion_of_sinks = 0.4;
        double demand = 2000;

        GraphGenerator graphGenerator = new GraphGenerator(node_count, proportion_of_sources, proportion_of_sinks);
        Cplex_Solver cplex_solver = new Cplex_Solver(new ILPGraph(0, node_count - 1, graphGenerator.getILPEdgeList(), node_count), demand);
        CSVEdgeWriter csvEdgeWriter = new CSVEdgeWriter("C:\\Users\\book_\\Documents\\Summer2019\\graphVisualizer\\edge_file_solution_tester.csv", cplex_solver.solvedILPEdges());

        Flow_Network flow_network = graphGenerator.getFlowNetwork();

        //for (int i = 0; i < 1000000; i++) {
            System.out.println();
            System.out.println();
            flow_network.solveCheapestPathHeuristic(demand);
            System.out.println("\n Solved:");
            System.out.println(flow_network.getFlow() + " cost: " + flow_network.getCost());
            flow_network.print_flow_edges();

            //graphGenerator = new GraphGenerator(node_count, proportion_of_sources, proportion_of_sinks);
            //flow_network = graphGenerator.getFlowNetwork();

        //}



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

*/

    }
}
