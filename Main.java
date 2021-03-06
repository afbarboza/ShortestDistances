package com.learning;

import com.google.common.graph.*;

import java.util.*;

import static com.learning.InputUtils.*;


public class Main {

    public static void buildNeighborhood(MutableGraph<NodeType> graph, ArrayList<NodeType> adjacencyList) {
        NodeType node = adjacencyList.get(0);
        graph.addNode(node);

        for (int i = 1; i < adjacencyList.size(); i++)  {
            NodeType currentNeighbor = adjacencyList.get(i);
            graph.addNode(currentNeighbor);
            graph.putEdge(node, currentNeighbor);
        }
    }

    public static MutableGraph<NodeType> buildGraph() {
        MutableGraph<NodeType> graph = GraphBuilder
                    .undirected()
                    .build();

        String currentLine = readNextLine();
        while (!currentLine.isBlank()) {
            ArrayList<NodeType> adjacencyList = parseLineIntoStringList(currentLine);
            buildNeighborhood(graph, adjacencyList);
            currentLine = readNextLine();
        }

        return graph;
    }

    public static void printGraph(MutableGraph<NodeType> graph) {
        for (NodeType node : graph.nodes()) {
            System.out.print(node.getLabel() + ": ");

            for (NodeType neighbor : graph.adjacentNodes(node)) {
                System.out.print(neighbor.getLabel() + " | ");
            }

            System.out.println();
        }

        System.out.println("****************************************");
    }

    public static boolean nodeWasExplored(MutableGraph<NodeType> graph, NodeType node) {
        for (NodeType currentNode : graph.nodes()) {
            if (currentNode.equals(node) && currentNode.isExplored()) {
                return true;
            }
        }

        return false;
    }

    public static void findShortestDistancesWithBreadthFirstSearch(MutableGraph<NodeType> graph, NodeType startVertex) {
        /* Creates a FIFO queue data structure, initialized with @startVertex */
        LinkedList<NodeType> queue = new LinkedList<NodeType>();

        /* Mark @startVertex as explored, all others as inexplored */
        for (NodeType node : graph.nodes()) {
            if (node.equals(startVertex)) {
                node.setExplored(true);
                node.setDistanceFromStartVertex(0);
                System.out.println(node.getLabel() + ": start vertex");
                queue.add(node);
            }
        }

        while (!queue.isEmpty()) {
            /* remove the vertex from the from the FIFO queue */
            NodeType node = queue.removeFirst();

            /* for each edge (@node, w) in adjacency list, do */
            Set<NodeType> neighborhood = graph.adjacentNodes(node);
            for (NodeType neighbor : neighborhood) {
                /** yeah, I know. Please notice that the method adjacentNodes from Guava was marked
                 * as unstable at the time this code was written.
                 * Besides, it returns Collections.unmodifiableSet */

                if (!nodeWasExplored(graph, neighbor)) { /* if neighbor was inexplored, do */
                    neighbor.setExplored(true);

                    /* the shortest path from @startVertex to @neighbor is @node + 1 */
                    neighbor.setDistanceFromStartVertex(node.getDistanceFromStartVertex() + 1);
                    
                    /* add @neighbor to the end of FIFO queue */
                    queue.add(neighbor);
                    System.out.println(neighbor.getLabel() + ": " + neighbor.getDistanceFromStartVertex());
                }
            }
        }
    }

    public static void main(String[] args) {
        initInputReader();
        MutableGraph<NodeType> graph = buildGraph();
        findShortestDistancesWithBreadthFirstSearch(graph, new NodeType("1"));
    }
}
