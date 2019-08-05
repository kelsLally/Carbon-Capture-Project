/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author sauerberg
 */
public class MultiEdge implements Edge {

    int start;
    int end;
    double[] capacities;
    double[] fixed_costs;
    double[] variable_costs;
    int level; // -1 means unopened
    double flow;

    @Override
    public String toString() {
        return "MultiEdge{" +
                "start=" + start +
                ", end=" + end +
                ", flow=" + flow +
                '}';
    }

    public MultiEdge(int start, int end, double[] capacities, double[] fixed_costs, double[] variable_costs) {
        if (start < 0 || end < 0) {
            throw new IllegalArgumentException("Edges must have non-negative start and end indices.");
        }
        if (capacities.length != fixed_costs.length || capacities.length != variable_costs.length) {
            throw new IllegalArgumentException("Capacity and cost arrays must have same length.");
        }
        for (int i = 0; i < capacities.length; i++) {
            if (capacities[i] < 0) {
                throw new IllegalArgumentException("Edges must have non-negative capacity.");
            }
            if (fixed_costs[i] < 0) {
                throw new IllegalArgumentException("Edges must have non-negative fixed_cost.");
            }
            if (variable_costs[i] < 0) {
                throw new IllegalArgumentException("Edges must have non-negative variable_cost.");
            }
        }
        this.start = start;
        this.end = end;
        this.capacities = capacities;
        this.fixed_costs = fixed_costs;
        this.variable_costs = variable_costs;
        this.level = -1;
        this.flow = 0;

    }

    public MultiEdge(int start, int end, double[] capacities, double[] fixed_costs, double[] variable_costs, int level, double flow) {
        if (start < 0 || end < 0) {
            throw new IllegalArgumentException("Edges must have non-negative start and end indices.");
        }
        if (capacities.length != fixed_costs.length || capacities.length != variable_costs.length) {
            throw new IllegalArgumentException("Capcity and cost arrays must have same length.");
        }
        for (int i = 0; i < capacities.length; i++) {
            if (capacities[i] < 0) {
                throw new IllegalArgumentException("Edges must have non-negative capacity.");
            }
            if (fixed_costs[i] < 0) {
                throw new IllegalArgumentException("Edges must have non-negative fixed_cost.");
            }
            if (variable_costs[i] < 0) {
                throw new IllegalArgumentException("Edges must have non-negative variable_cost.");
            }
        }
        this.start = start;
        this.end = end;
        this.capacities = capacities;
        this.fixed_costs = fixed_costs;
        this.variable_costs = variable_costs;
        this.level = level;
        this.flow = flow;

        if (!this.isValid()) {
            throw new IllegalArgumentException("New edge has larger flow than admitted by level and capcity.");
        }
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getEnd() {
        return end;
    }

    @Override
    public boolean isOpen() {
        return level == -1;
    }

    @Override
    public double getFlow() {
        return flow;
    }

    @Override
    public double getCurrentCost() {
        if (level == -1) {
            return 0;
        }
        return fixed_costs[level] + variable_costs[level] * Math.abs(flow);
    }

    @Override
    public double getCost(double additional_flow) {
        int newLevel = findMinLevel(additional_flow + flow);
        if (newLevel == -1) {
            return 0;
        } else if (newLevel == -2) {
            return Double.MAX_VALUE;
        }
        return fixed_costs[newLevel] + variable_costs[newLevel]
                * Math.abs(additional_flow + flow) - getCurrentCost();
    }

    @Override
    public boolean setFlow(double amount) {
        if (findMinLevel(amount) == -2) {
            return false;
        }
        level = findMinLevel(amount);
        this.flow = amount;
        return true;
    }

    @Override
    public boolean augmentFlow(double amount) {
        if (findMinLevel(flow + amount) == -2) {
            return false;
        }
        level = findMinLevel(flow + amount);
        flow += amount;
        return true;
    }

    @Override
    public boolean isValid() {
        if (level == -1){
            return flow == 0;
        }
        return capacities[level] >= Math.abs(flow);
    }

    private int findMinLevel(double flow) {
        if (flow == 0) {
            return -1;
        }
        for (int i = 0; i < capacities.length; i++) {
            if (capacities[i] >= Math.abs(flow)) {
                return i;
            }
        }
        return -2;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public double getResidualCapacity(int level) {
        if (level == -1 || level == -2) {
            return 0;
        }
        return capacities[level] - flow;
    }

    @Override
    public double getResidualCapacity() {
        return getResidualCapacity(level);
    }

    @Override
    public double getFixedCostToIncreaseFlow() {
        if (getResidualCapacity() > 0) {
            return 0;
        } else if (level == fixed_costs.length - 1) {
            return Double.MAX_VALUE;
        } else if (level == -1) {
            return fixed_costs[0];
        }
        return fixed_costs[level + 1] - fixed_costs[level];
    }

    public double[] getCapacities() {
        return capacities;
    }

    public double[] getFixed_costs() {
        return fixed_costs;
    }

    public double[] getVariable_costs() {
        return variable_costs;
    }

    public double getCostNonNegative(double additional_flow){
        return Math.max(0, getCost(additional_flow));
    }
}
