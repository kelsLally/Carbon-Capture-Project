/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ilog.concert.IloIntVar;
import ilog.concert.IloNumVar;

/**
 *
 * @author sauerberg
 */
public class ILPEdge {

    private double capacity;
    private double fixed_cost;
    private double variable_cost;
    private int start;
    private int end;
    private IloIntVar isOpen;
    private IloNumVar flow;
    private double num_flow;

    public ILPEdge(int start, int end, double capacity, double fixed_cost, double variable_cost) {
        if (capacity < 0 || fixed_cost < 0 || variable_cost < 0 || start < 0 || end < 0) {
            throw new IllegalArgumentException("Edges can't accept negative attributes.");
        }
        this.capacity = capacity;
        this.fixed_cost = fixed_cost;
        this.variable_cost = variable_cost;
        this.start = start;
        this.end = end;
        this.isOpen = null;
        this.flow = null;
        this.num_flow = 0;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getFixed_cost() {
        return fixed_cost;
    }

    public double getVariable_cost() {
        return variable_cost;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void setIsOpen(IloIntVar isOpen) {
        this.isOpen = isOpen;
    }

    public void setFlow(IloNumVar flow) {
        this.flow = flow;
    }

    public IloIntVar getIsOpen() {
        return isOpen;
    }

    public IloNumVar getFlow() {
        return flow;
    }

    public void setNum_flow(double new_num_flow) {
        num_flow = new_num_flow;
    }

    public double getNum_flow() {
        return num_flow;
    }

    @Override
    public String toString() {
        return "ILPEdge{" +
                "start=" + start +
                ", end=" + end +
                ", num_flow=" + num_flow +
                '}';
    }
}