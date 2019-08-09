public class CarbonCaptureProjectv4 {

    public static void main(String[] args) {
        int node_count = 30;
        double proportion_of_sources = 0.4;
        double proportion_of_sinks = 0.4;
        double demand = 300;

        int brendan = 0;
        int sean = 0;
        int nathaniel = 0;
        int sean2 = 0;
        int nathaniel2 = 0;

        for (int i = 0; i < 10000; i++) {

            GraphGenerator graphGenerator = new GraphGenerator(node_count, proportion_of_sources, proportion_of_sinks);
            //Cplex_Solver cplex_solver = new Cplex_Solver(new ILPGraph(0, node_count - 1, graphGenerator.getILPEdgeList(), node_count), demand);
            //CSVEdgeWriter csvEdgeWriter = new CSVEdgeWriter("C:\\Users\\book_\\Documents\\Summer2019\\graphVisualizer\\edge_file_solution_tester.csv", cplex_solver.solvedILPEdges());

            Flow_Network flow_network = graphGenerator.getFlowNetwork();

            flow_network.solveCheapestPathHeuristic(demand);
            //System.out.println("\nBrendan Heuristic:");
            System.out.println("\nBrendan Heuristic: " + flow_network.getFlow() + " cost: " + flow_network.getCost());
            //flow_network.print_flow_edges();
            //System.out.println(flow_network.isValid());
            double b = flow_network.getCost();

            flow_network.clearFlow();

            flow_network.solveSeanHeuristicNonNegative(demand);
            //System.out.println("\nSean heuristic:");
            System.out.println("Sean heuristic: " + flow_network.getFlow() + " cost: " + flow_network.getCost());
            //flow_network.print_flow_edges();
            //System.out.println(flow_network.isValid());

            double s = flow_network.getCost();
            flow_network.clearFlow();

            flow_network.solveNathanielHeuristicNonNegative(demand);
            //System.out.println("\nNathaniel heuristic:");
            System.out.println("Nathaniel heuristic: " + flow_network.getFlow() + " cost: " + flow_network.getCost());
            //flow_network.print_flow_edges();
            //System.out.println(flow_network.getFlow());

            double n = flow_network.getCost();

            flow_network.clearFlow();

            flow_network.solveSeanHeuristicDP(demand);
            //System.out.println("\nSean heuristic:");
            System.out.println("Sean heuristic2: " + flow_network.getFlow() + " cost: " + flow_network.getCost());
            //flow_network.print_flow_edges();
            //System.out.println(flow_network.isValid());

            double s2 = flow_network.getCost();
            flow_network.clearFlow();

            flow_network.solveNathanielHeuristicDP(demand);
            //System.out.println("\nNathaniel heuristic:");
            System.out.println("Nathaniel heuristic2: " + flow_network.getFlow() + " cost: " + flow_network.getCost());
            //flow_network.print_flow_edges();
            //System.out.println(flow_network.getFlow());

            double n2 = flow_network.getCost();

            var min = Math.min(n2, Math.min(s2, Math.min(b, Math.min(s, n))));
            if (min == b){
                brendan++;
            }
            if (min == s){
                sean++;
            }
            if (min == n){
                nathaniel++;
            }
            if (min == s2){
                sean2++;
            }
            if (min == n2){
                nathaniel2++;
            }

        }

        System.out.println("Brendan: " + brendan);
        System.out.println("Sean: " + sean);
        System.out.println("Nathaniel: " + nathaniel);
        System.out.println("Sean2: " + sean2);
        System.out.println("Nathaniel2: " + nathaniel2);


    }
}
