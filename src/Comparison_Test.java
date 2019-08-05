import java.io.File;
import java.io.FileWriter;

public class Comparison_Test {
    private String average_file_name;
    private String all_data_file_name;
    // make a graph run on cplex, run heur 1, produce comparison number, write that number to csv
    // new row in csv for each number of nodes
    public Comparison_Test (String average_file_name, String all_data_file_name) {
        this.average_file_name = average_file_name;
        this.all_data_file_name = all_data_file_name;
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

            int demand = 1000;
            int min_node_count = 25;
            int max_node_count = 500;
            int skip_node_count = 25;
            int rep_count = 20;

            //double [][][] test_values = new double [max_node_count - min_node_count + 1][2][rep_count];

            for (int node_count = min_node_count; node_count <= max_node_count; node_count += skip_node_count ) {
                System.out.print("\n Node Count: " + node_count);
                for (int rep = 0; rep < rep_count; rep++) {
                    GraphGenerator graphGenerator = new GraphGenerator(node_count, 0.4, 0.4);

                    ILPGraph ilpGraph = new ILPGraph(0, node_count - 1, graphGenerator.getILPEdgeList(), node_count);
                    long startTime_cplex = System.nanoTime();
                    Cplex_Solver cplex_solver = new Cplex_Solver(ilpGraph, demand);
                    long endTime_cplex = System.nanoTime();
                    double cplex_cost = cplex_solver.getFinalCost();
                    long cplex_time = endTime_cplex - startTime_cplex;


                    Flow_Network flow_network = graphGenerator.getFlowNetwork();
                    long startTime_heuristic = System.nanoTime();
                    flow_network.solveCheapestPathHeuristic(demand);
                    long endTime_heuristic = System.nanoTime();
                    double cheapest_path_heuristic_cost = flow_network.getCost();
                    long heuristic_time = endTime_heuristic - startTime_heuristic;


                    if (cplex_cost > 0 && flow_network.solveCheapestPathHeuristic(demand)){
                        allFileWriter.append(String.valueOf(node_count));
                        allFileWriter.append(",");

                        //System.out.println("cplex cost: " + cplex_cost);
                        allFileWriter.append(String.valueOf(cplex_cost));
                        allFileWriter.append(",");

                        //System.out.println("Flow network is valid: " + flow_network.solveCheapestPathHeuristic(demand));

                        //System.out.println("cheapest path cost: " + cheapest_path_heuristic_cost);
                        //System.out.println();
                        allFileWriter.append(String.valueOf(cheapest_path_heuristic_cost));
                        allFileWriter.append(",");

                        allFileWriter.append(String.valueOf(cplex_time));
                        allFileWriter.append(",");
                        allFileWriter.append(String.valueOf(heuristic_time));

                        allFileWriter.append("\n");


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
}