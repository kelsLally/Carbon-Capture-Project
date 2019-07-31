import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.cplex.IloCplex;

public class Cplex_Solver {
    private ILPGraph graph;
    private double demand;

    public Cplex_Solver (ILPGraph graph, double demand) {
        this.graph = graph;
        this.demand = demand;
        cplex_solve();
    }

    private void cplex_solve () {
        try {
            IloCplex cplex = new IloCplex();

            // Variables: is edge opened and flow
            for (ILPEdge edge : graph.getILPEdge_array()){
                edge.setIsOpen(cplex.intVar (0,1, edge.toString() + " isOpen"));
                edge.setFlow(cplex.numVar (0,Double.MAX_VALUE, edge.toString() + " flow"));
            }

            // Constraint: conservation of flow at each interior node
            for (int i = 0; i < graph.getNodeCount(); i++){
                if (i != graph.getSource() && i !=graph.getSink()){
                    IloLinearNumExpr expr = cplex.linearNumExpr();
                    for(ILPEdge e : graph.getAdjacency_list_out()[i]){
                        expr.addTerm(1, e.getFlow());
                    }
                    for (ILPEdge e : graph.getAdjacency_list_in()[i]){
                        expr.addTerm(-1, e.getFlow());
                    }
                    cplex.addEq(expr, 0);
                }
            }

            // Constraint: Meet Demand
            IloLinearNumExpr total_flow = cplex.linearNumExpr();
            for (ILPEdge e : graph.getAdjacency_list_out()[graph.getSource()]){
                total_flow.addTerm(1, e.getFlow());
            }
            cplex.addEq(demand, total_flow);

            // Constraint: Flow Doesn't Exceed Capacity (considers whether edge is open)
            for (ILPEdge e : graph.getILPEdge_array()){
                IloLinearNumExpr cap_minus_flow = cplex.linearNumExpr();
                cap_minus_flow.addTerm(e.getCapacity(), e.getIsOpen());
                cap_minus_flow.addTerm(-1, e.getFlow());
                cplex.addRange(0, cap_minus_flow, Double.MAX_VALUE);
            }

            // Constraint: non-negative flows
            for (ILPEdge e : graph.getILPEdge_array()) {
                IloLinearNumExpr flow = cplex.linearNumExpr();
                flow.addTerm(1, e.getFlow());
                cplex.addRange(0, flow, Double.MAX_VALUE);
            }

            // Objective

            // Define NumVar cost, constraint it be equal to total cost
            IloNumVar cost = cplex.numVar(0, Double.MAX_VALUE);
            IloLinearNumExpr cost_constraint = cplex.linearNumExpr();
            for (ILPEdge e : graph.getILPEdge_array()){
                cost_constraint.addTerm(e.getFixed_cost(), e.getIsOpen());
                cost_constraint.addTerm(e.getVariable_cost(), e.getFlow());
            }
            cost_constraint.addTerm(-1, cost);
            cplex.addEq(0, cost_constraint);

            IloObjective obj = cplex.minimize(cost);
            cplex.add(obj);

            // prints extra information for Cplex if not null
            cplex.setOut(null);

            // Solve
            if (cplex.solve()) {

                for (ILPEdge e: graph.getILPEdge_array()) {
                    if (cplex.getValue(e.getFlow()) != 0){
                        e.setNum_flow(cplex.getValue(e.getFlow()));
                        System.out.println(e);
                    }
                }
                //CSVEdgeWriter csvEdgeWriter = new CSVEdgeWriter("C:\\Users\\book_\\Documents\\Summer2019\\graphVisualizer\\edge_file_tester_solution.csv", edges);
                System.out.println("Peak: " + cplex.getValue(cost));

            } else {
                System.out.println("Not Feasible");
            }
            cplex.clearModel();


        } catch (IloException e) {
            e.printStackTrace();
        }
    }

    public ILPEdge[] solvedILPEdges () {
        return graph.getILPEdge_array();
    }
}
