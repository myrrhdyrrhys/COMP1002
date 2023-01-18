/*
NOTE: This class file was submitted by myself previously as part of DSA's weekly assessments (Week 6 in particular)
NAME: UnitTestGraph.java
PURPOSE: to test the functionality of the methods in DSAGraph.java and DSAGraphVertex.java and also FileIO.java
AUTHOR: Lachlan Murray (19769390)
LAST MOD:
*/

import java.util.*;

public class UnitTestGraph
{
    public static void main(String[] args)
    {
        //Variable declarations
        DSAGraph graph = null;
        DSAGraphVertex vertex, vertex2;
        DSAGraphEdge edge;
        DSALinkedList adjacent, path;
        boolean isAdj = false;
        int integer = 0;

        //CONSTRUCTOR test (Test 1)
        try
        {
            System.out.println("Testing construction of DSAGraph.");
            graph = new DSAGraph();
            System.out.println("Construction Successful.");
        }
        catch(Exception e)
        {
            System.out.println("Failed construction.");
        }
        
        //INSERT test (Test 2)
        try
        {
            System.out.println("Testing insertion of vertices and edges to graph with: prac6_1.al ");
            graph = FileIO.readFile("prac6_1.al");

            System.out.println("Inserting Successful.");
        }
        catch(Exception e)
        {
            System.out.println("Failed insertion to graph." + e.getMessage());
        }

        //DISPLAY tests (list and matrix) (Test 3)
        try
        {
            System.out.println("Displaying graph in adjacency list form.");
            graph.displayAsList();
            
            System.out.println("Displaying graph in adjacency matrix form.");
            graph.displayAsMatrix();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        //ACCESSORS tests (hasVertex, getCounts) (Test 4)
        try
        {
            //hasVertex
            System.out.println("Checking if graph contains the vertex ('C', 0).");
            if (graph.hasVertex("C") == true)
            {
                System.out.println("Vertex found.");
            }
            else
            {
                throw new IllegalArgumentException("C not found!");
            }
             
            //getVertexCount
            System.out.println("Checking if the number of vertices is 7.");
            integer = graph.getVertexCount();
            if (integer == 7)
            {
                System.out.println("Correct number of vertices found.");
            }
            else
            {
                throw new IllegalArgumentException("The number of vertices found was " + integer + ", not 7");
            }
            
            //getEdgeCount
            System.out.println("Checking if the number of edges is 10.");
            integer = graph.getEdgeCount();
            if (integer == 10)
            {
                System.out.println("Correct number of edges found.");
            }
            else
            {
                throw new IllegalArgumentException("The number of edges found was " + integer + ", not 10");
            }
            
            System.out.println("Non-getter Accessor tests Successful.");
        }         
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        //ACCESSOR tests (getVertex, getEdge, getAdjacent, isAdjacent) (Test 5)
        try
        {
            System.out.println("Testing getter for a vertex.");
            vertex = graph.getVertex("B");
            if (vertex == null)
            {
                throw new IllegalArgumentException("Failed getVertex().");
            }
            else if ((int)vertex.getValue() == 0)
            {
                System.out.println("getVertex() successful.");
            }
            
            System.out.println("Testing getter for an edge.");
            edge = graph.getEdge("AB");
            if (edge == null)
            {
                throw new IllegalArgumentException("Failed getEdge().");
            }
            else if ((int)edge.getValue() == 0)
            {
                System.out.println("getEdge() successful.");
            }

            System.out.println("Testing getter for an adjacency list.");
            adjacent = graph.getAdjacent("A");
            if (adjacent.isEmpty() == true)
            {
                throw new IllegalArgumentException("Failed getAdjacent().");
            }
            else
            {
                System.out.println("getAdjacent() successful.");
            }

            System.out.println("Testing if two vertices are adjacent (A and B).");
            isAdj = graph.isAdjacent("A", "B");
            if (isAdj == false)
            {
                throw new IllegalArgumentException("Failed isAdjacent() check.");
            }
            else
            {
                System.out.println("isAdjacent() check successful.");
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
                
        //SEARCH TESTS (depth-first and breadth-first) (Test 6)
        try
        {
            System.out.println("Displaying depth-first search of graph.");
            vertex = graph.getVertex("A");  //get starting node
            graph.depthFirstSearch(vertex);
            System.out.println();
            
            graph.setAllNotVisited();   //make sure vertices aren't marked as traversed before search
            System.out.println("Displaying breadth-first search of graph.");
            vertex = graph.getVertex("A");
            graph.breadthFirstSearch(vertex);

            graph.setAllNotVisited();
            System.out.println("Displaying path from one node to another. (A to F)");
            vertex = graph.getVertex("A");
            vertex2 = graph.getVertex("F");
            path = graph.DFSDest(vertex, vertex2);
            System.out.println();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
