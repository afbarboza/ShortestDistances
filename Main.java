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

    public static void breadthFirstSearch(MutableGraph<NodeType> graph, NodeType startVertex) {
        /* Creates a FIFO queue data structure, initialized with @startVertex */
        LinkedList<NodeType> queue = new LinkedList<NodeType>();

        /* Mark @startVertex as explored, all others as inexplored */
        for (NodeType node : graph.nodes()) {
            if (node.equals(startVertex)) {
                node.setExplored(true);
                node.setDistanceFromStartVertex(0);
                queue.add(node);
            }
        }

        while (!queue.isEmpty()) {
            NodeType node = queue.removeFirst();

            Set<NodeType> neighbors = graph.adjacentNodes(node);
            Iterator<NodeType> neighborhood = neighbors.iterator();
            NodeType currentNeighbor = neighborhood.next();

            while (true) {
                if (!nodeWasExplored(graph, currentNeighbor)) {
                    currentNeighbor.setExplored(true);
                    queue.add(currentNeighbor);
                    System.out.println(currentNeighbor.getLabel());
                }

                if (neighborhood.hasNext())
                    currentNeighbor = neighborhood.next();
                else
                    break;
            }

//            for (NodeType neighbor : graph.adjacentNodes(node)) {
//                if (!neighbor.isExplored()) {
//                    neighbor.setExplored(true);
//                    if (!queue.contains(neighbor)) {
//                        queue.add(neighbor);
//                        System.out.println(neighbor.getLabel());
//                    }
//                }
//            }
        }

    }

    public static void main(String[] args) {
        initInputReader();
        MutableGraph<NodeType> graph = buildGraph();
        breadthFirstSearch(graph, new NodeType("1"));
        // printGraph(graph);
    }
}
