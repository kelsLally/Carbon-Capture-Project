import java.io.File;
import java.io.FileWriter;

public class Comparison_Test {
    private String average_file_name;
    private String all_data_file_name;
    private double demand;
    private int min_node_count;
    private int max_node_count;
    private int skip_node_count;
    private int rep_count;

    // default value constructor
    public Comparison_Test (String average_file_name, String all_data_file_name) {
        this.average_file_name = average_file_name;
        this.all_data_file_name = all_data_file_name;
        this.min_node_count = 6;
        this.max_node_count = 20;
        this.skip_node_count = 2;
        this.rep_count = 20;
    }

    // explicit value constructor
    public Comparison_Test (String average_file_name, String all_data_file_name, int min_node_count, int max_node_count, int skip_node_count, int rep_count){
        this.average_file_name = average_file_name;
        this.all_data_file_name = all_data_file_name;
        this.min_node_count = min_node_count;
        this.max_node_count = max_node_count;
        this.skip_node_count = skip_node_count;
        this.rep_count = rep_count;
    }

    public void runTest () {
        try {
//            File average_file = new File(average_file_name);
//            boolean fvar = average_file.createNewFile();
//            if (!fvar) {
//                average_file.delete();
//                fvar = average_file.createNewFile();
//            }
//            FileWriter fileWriter = new FileWriter(average_file);

            File all_data_file = new File(all_data_file_name);
            boolean fvar2 = all_data_file.createNewFile();
            if (!fvar2) {
                all_data_file.delete();
                fvar2 = all_data_file.createNewFile();
            }
            FileWriter allFileWriter = new FileWriter(all_data_file);

            allFileWriter.append("node_count");
            allFileWriter.append(",");
            allFileWriter.append("cplex_cost");
            allFileWriter.append(",");
            allFileWriter.append("cplex_time");
            allFileWriter.append(",");
            allFileWriter.append("heuristic_1_cost");
            allFileWriter.append(",");
            allFileWriter.append("heuristic_1_time");
            allFileWriter.append(",");
            allFileWriter.append("heuristic_2_cost");
            allFileWriter.append(",");
            allFileWriter.append("heuristic_2_time");
            allFileWriter.append(",");
            allFileWriter.append("heuristic_3_cost");
            allFileWriter.append(",");
            allFileWriter.append("heuristic_3_time");
            allFileWriter.append("\n");


            //double [][][] test_values = new double [max_node_count - min_node_count + 1][2][rep_count];


            for (int node_count = min_node_count; node_count <= max_node_count; node_count += skip_node_count ) {
                System.out.print("\n Node Count: " + node_count);
                int counter = 0;
                while (counter < rep_count) {
                    GraphGenerator graphGenerator = new GraphGenerator(node_count);
                    ILPGraph ilpGraph = new ILPGraph (graphGenerator.getS(), graphGenerator.getT(), graphGenerator.getILPEdgeList(), graphGenerator.getNode_count());
                    demand = .5 * getDemand(ilpGraph);

                    long startTime_cplex = System.nanoTime();
                    Cplex_Solver cplex_solver = new Cplex_Solver(ilpGraph, demand);
                    long endTime_cplex = System.nanoTime();
                    double cplex_cost = cplex_solver.getFinalCost();
                    long cplex_time = endTime_cplex - startTime_cplex;
                    //System.out.println("cplex");


                    Flow_Network flow_network = graphGenerator.getFlowNetwork();

                    long startTime_heuristic_1 = System.nanoTime();
                    flow_network.solveCheapestPathHeuristic(demand);
                    long endTime_heuristic = System.nanoTime();
                    double heuristic_1_cost = flow_network.getCost();
                    long heuristic_1_time = endTime_heuristic - startTime_heuristic_1;
                    //System.out.println("heur1");

                    flow_network = graphGenerator.getFlowNetwork();
                    long startTime_heuristic_2 = System.nanoTime();
                    flow_network.solveSeanHeuristicNonNegative(demand);
                    long endTime_heuristic_2 = System.nanoTime();
                    double heuristic_2_cost = flow_network.getCost();
                    long heuristic_2_time = endTime_heuristic_2 - startTime_heuristic_2;
                    //System.out.println("heur2");

                    flow_network = graphGenerator.getFlowNetwork();
                    long startTime_heuristic_3 = System.nanoTime();
                    flow_network.solveNathanielHeuristicNonNegative(demand);
                    long endTime_heuristic_3 = System.nanoTime();
                    double heuristic_3_cost = flow_network.getCost();
                    long heuristic_3_time = endTime_heuristic_3 - startTime_heuristic_3;
                    //System.out.println("heur3");

                    if (cplex_cost > 0 && flow_network.solveCheapestPathHeuristic(demand)){
                        allFileWriter.append(String.valueOf(node_count));
                        allFileWriter.append(",");

                        allFileWriter.append(String.valueOf(cplex_cost));
                        allFileWriter.append(",");
                        allFileWriter.append(String.valueOf(cplex_time));
                        allFileWriter.append(",");

                        allFileWriter.append(String.valueOf(heuristic_1_cost));
                        allFileWriter.append(",");
                        allFileWriter.append(String.valueOf(heuristic_1_time));
                        allFileWriter.append(",");
                        allFileWriter.append(String.valueOf(heuristic_2_cost));
                        allFileWriter.append(",");
                        allFileWriter.append(String.valueOf(heuristic_2_time));
                        allFileWriter.append(",");
                        allFileWriter.append(String.valueOf(heuristic_3_cost));
                        allFileWriter.append(",");
                        allFileWriter.append(String.valueOf(heuristic_3_time));
                        allFileWriter.append("\n");


                        counter++;
                        System.out.print(".");
                    }
//
//                    test_values[node_count - min_node_count][0][rep] = cplex_cost;
//                    test_values[node_count - min_node_count][1][rep] = cheapest_path_heuristic_cost;
                }

                //System.out.println();
                //System.out.println();
            }

//            double [][] average_test_value = new double[max_node_count - min_node_count + 1][2];
//            for (int i = 0; i < max_node_count - min_node_count + 1; i ++) {
//                for (int j = 0; j < 2; j++) {
//                    int sum = 0;
//                    for (int k = 0; k < test_values[i][j].length; k ++) {
//                        sum += test_values[i][j][k];
//                    }
//                    average_test_value[i][j] = sum / rep_count;
//                }
//            }
//
//            double [] percentage_optimal = new double[max_node_count - min_node_count + 1];
//            for (int i = 0; i < max_node_count - min_node_count + 1; i ++) {
//                percentage_optimal[i] = average_test_value[i][1] / average_test_value[i][0];
//                fileWriter.append(String.valueOf(percentage_optimal[i]));
//                fileWriter.append("\n");
//            }

            allFileWriter.flush();
            allFileWriter.close();
            //fileWriter.flush();
            //fileWriter.close();

        } catch (java.io.IOException e) {
            System.out.print("File error: ");
            e.printStackTrace();
        }
    }


    // saturates the capacity of either the sources or the sinks
    public double getDemand (ILPGraph ilpGraph) {

        ILPEdge [] ilpEdges = ilpGraph.getILPEdge_array();
        int source = ilpGraph.getSource();
        int sink = ilpGraph.getSink();

        double source_capacity = 0;
        double sink_capacity = 0;
        for (int i = 0; i < ilpEdges.length; i++) {
            if (ilpEdges[i].getStart() == source) {
                source_capacity += ilpEdges[i].getCapacity();
            } else if (ilpEdges[i].getEnd() == sink) {
                sink_capacity += ilpEdges[i].getCapacity();
            }
        }
//        System.out.println("demand diff: " + (sink_capacity - source_capacity));
//        System.out.println("sink: " + sink_capacity);
//        System.out.println("source: " + source_capacity);
//        System.out.println();
        if (source_capacity <= sink_capacity) {
            return source_capacity;
        }
        return sink_capacity;
    }
}
