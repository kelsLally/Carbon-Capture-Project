# Carbon-Capture-Project

Carbon-Capture-Project is a repository created by Nathaniel Sauerberg and Kelsey Lally
as part of the Montana State University Research Experience for Undergraduates, completed
the summer of 2019.

The goal of this system was to find a way to create and solve the Carbon Capture System
problem. This problem deals with capturing the carbon from sources such as power plants, and transporting it along
pipelines to be stored in the ground where it will not effect global climate change.

We translated this problem into a flow network model and worked to solve a min-cost flow that would take into account
fixed costs, variable costs, and capacities of facilities and pipelines. With this NP-hard problem we created an
integer linear program that runs on IBM cplex to find the optimal solution on small cases and several heuristics that
will find the non-optimal solution for larger problems. 