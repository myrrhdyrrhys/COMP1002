/*
NOTE: This class file was submitted by myself previously as part of DSA's weekly assessments (Week 6 in particular), although edited slightly since then to work for directed graphs rather than undirected, as well as the addition of an iterative implementation of depth-first search (named DFSDest), and addition of edges as a separate type and list.
NAME: DSAGraph.java
PURPOSE: to provide a class for implementing graph data structures.
AUTHOR: Lachlan Murray (19769390)
LAST MOD:
*/

import java.util.*;
import java.io.*;

public class DSAGraph implements Serializable
{
    //Class fields
    private DSALinkedList vertices;
    private DSALinkedList edges;

    /*
    SUBMODULE: Default Constructor
    IMPORTS: none
    EXPORTS: address of new DSAGraph object
    ASSERTION: vertices is set to null.
    */
    public DSAGraph()
    {
        vertices = new DSALinkedList();
        edges = new DSALinkedList();
    }
    
    //MUTATORS
    public void addVertex(String label, Object value)
    {
        DSAGraphVertex vertex = new DSAGraphVertex(label, value);

        if (hasVertex(label) == false)   //if the label is not already in the vertices list
        {
            vertices.insertLast(vertex);
        }
    }

    //must do addVertex before adding edges
    public void addEdge(String label1, String label2, String edgeLabel, Object value)
    {
        //could check if (vertices CONTAINS label1 and label2)
        //currently adds for directed graph
        DSAGraphVertex vertex = null, vertex1 = null, vertex2 = null;     
        DSAGraphEdge edge = null;
        Iterator iter = vertices.iterator();
        String key;        

        if (hasEdge(edgeLabel) == false)    //checks if edge already exists
        {
            //finds each vertex from the edge in the vertices list
            while (iter.hasNext())
            {
                vertex = (DSAGraphVertex)iter.next();
                key = vertex.getLabel();
                
                if(key.equals(label1))
                {
                    vertex1 = vertex;
                }
                else if(key.equals(label2))
                {
                    vertex2 = vertex;
                }
            }
            
            //adds as an edge to the edge list
            edge = new DSAGraphEdge(edgeLabel, value, vertex1, vertex2);
    
            //adds directed link in vertices list
            if ((vertex1 != null)&&(vertex2 != null))
            {
                vertex1.addEdge(vertex2);
                //vertex2.addEdge(vertex1);   //undirectional
                edges.insertLast(edge);
            }   
        }
    }
    
    //ACCESSORS
    public boolean hasVertex(String label)
    {
        String key;
        boolean checked = false;
        Iterator iter;

        if (vertices != null)
        {
            iter = vertices.iterator();

            while (iter.hasNext())  //loop through to see if key already there 
            {
                key = ((DSAGraphVertex)iter.next()).getLabel();

                if (key.equals(label))
                {
                    checked = true;
                }
            }
        }
    
        return checked;    
    }

    public boolean hasEdge(String label)
    {
        String key;
        boolean checked = false;
        Iterator iter;
        
        if (edges != null)
        {
            iter = edges.iterator();
            
            while (iter.hasNext())  //loop through to see if key is already there
            {
                key = ((DSAGraphEdge)iter.next()).getLabel();

                if (key.equals(label))
                {
                    checked = true;
                }
            }
        }
        
        return checked;
    }                    
    
    public int getVertexCount()
    {
        int counter = 0;
        Iterator iter = vertices.iterator();

        while (iter.hasNext())
        {
            iter.next();
            counter++;
        }
        
        return counter;
    }
    
    public int getEdgeCount()
    {
        int counter = 0;
        Iterator iter = edges.iterator();     

        while (iter.hasNext())
        {
            iter.next();
            counter++;
        }
        
        return counter;
    }

    public DSAGraphVertex getVertex(String label)
    {
        Iterator iter = vertices.iterator();
        DSAGraphVertex vertex = null, vertexOut = null;        
        String key;
        boolean done = false;

        while ((iter.hasNext())&&(done == false))
        {
            vertex = (DSAGraphVertex)iter.next();
            key = vertex.getLabel();

            if (key.equals(label))
            {
                vertexOut = vertex;
                done = true;
            }
        }
    
        return vertexOut;
    }

    public DSAGraphEdge getEdge(String label)
    {
        Iterator iter = edges.iterator();
        DSAGraphEdge edge = null, edgeOut = null;
        String key;
        boolean done = false;
        
        while ((iter.hasNext())&&(done == false))
        {
            edge = (DSAGraphEdge)iter.next();
            key = edge.getLabel();
            
            if (key.equals(label))
            {
                edgeOut = edge;
                done = true;
            }
        }
        
        return edgeOut;
    }    

    public DSALinkedList getAdjacent(String label)
    {
        DSAGraphVertex vertex = getVertex(label);
        
        DSALinkedList adjacent = vertex.getAdjacent();
        
        return adjacent;
    }
    
    public DSALinkedList getVertices()
    {
        return vertices;
    }

    public DSALinkedList getEdges()
    {
        return edges;
    }

    public boolean isAdjacent(String label1, String label2)
    {
        DSALinkedList adjacent = getAdjacent(label1);
        String key;
        boolean found = false;
        Iterator iter = adjacent.iterator();
        
        while ((iter.hasNext())&&(found == false))
        {
            key = ((DSAGraphVertex)iter.next()).getLabel();

            if (key.equals(label2))
            {
                found = true;
            }
        }

        return found;
    }
    
    //DISPLAY METHODS
    public void displayAsList()
    {
        Iterator iter = vertices.iterator();    //graph iterator
        Iterator nodeIter = null;               //adjacency list iterator
        DSAGraphVertex vertex = null, nodeVertex = null;
        DSALinkedList adjacent = null;

        while(iter.hasNext())
        {
            vertex = (DSAGraphVertex)iter.next();

            System.out.print(vertex.getLabel() + " | ");

            adjacent = vertex.getAdjacent();
            nodeIter = adjacent.iterator();

            while(nodeIter.hasNext())
            {
                nodeVertex = (DSAGraphVertex)nodeIter.next();
                System.out.print(nodeVertex.getLabel() + " ");
            }
            
            System.out.println();
        }
    }

    public void displayAsMatrix()
    {
        Iterator iter = vertices.iterator();
        Iterator iter2 = vertices.iterator();
        DSAGraphVertex vertex, vertex2;
        boolean isAdj;

        System.out.print("   ");
        
        while(iter.hasNext())   //top row
        {
            vertex = (DSAGraphVertex)iter.next();
            System.out.print(vertex.getLabel() + " |");
        }
        iter = vertices.iterator(); //reset iterator

        System.out.println();

        while(iter.hasNext())   //each subsequent row
        {
            vertex = (DSAGraphVertex)iter.next();
            System.out.print(vertex.getLabel() + " |"); //first column

            while(iter2.hasNext())  //truth table
            { 
                vertex2 = (DSAGraphVertex)iter2.next();
                isAdj = isAdjacent(vertex.getLabel(), vertex2.getLabel());

                if(isAdj == true)
                {
                    System.out.print("1  ");
                }
                else
                {
                    System.out.print("0  ");
                }
            }            
            
            iter2 = vertices.iterator();    //reset inner iterator
    
            System.out.println();
        }
    }

    //for finding the smallest valued vertex (by label) for ordering purposes in BFS
    private DSAGraphVertex findSmallest(DSALinkedList list)
    {
        Iterator iter = list.iterator();
        DSAGraphVertex curr = null, smallest = null;

        while(iter.hasNext())
        {
            curr = (DSAGraphVertex)iter.next();

            if (curr.getVisited() == false) //only if it has not yet been visited
            {
                if ((smallest == null)||((curr.getLabel().compareTo(smallest.getLabel())) < 0))  //case of current nodes label being smaller, or there being only one element in the list
                {
                    smallest = curr;
                }
            }
        }
        
        return smallest;
    }

    //for clearing the visited status on all nodes on the graph before a new search is performed
    public void setAllNotVisited()
    {
        Iterator iter = vertices.iterator();
        DSAGraphVertex vertex;

        while (iter.hasNext())
        {
            vertex = (DSAGraphVertex)iter.next();
            vertex.clearVisited();
        }
    }

    public void depthFirstSearch(DSAGraphVertex node)
    {
        DSALinkedList adjacent;
        DSAGraphVertex adjNode;

        node.setVisited();
        System.out.print(node.getLabel() + "\t");
        
        adjacent = node.getAdjacent();
        if (adjacent == null)
        {
            return;
        }
        
        for (Object temp : adjacent)
        {
            adjNode = (DSAGraphVertex)temp;
            if (adjNode.getVisited() == false)
            {
                depthFirstSearch(adjNode);
            }
        }
    }


    //A variant of the above Depth-First Search, this time with a terminating node in mind, rather than traversing the full graph. Utilises iteration rather than recursion.
    /*public DSALinkedList DFSDest(DSAGraphVertex node, DSAGraphVertex dest)
    {
        DSALinkedList list, path = new DSALinkedList();
        Iterator iter;
        DSAStack stack = new DSAStack();
        boolean found = false, found2;
        DSAGraphVertex v, v2 = null; 

        stack.push(node);

        while ((!stack.isEmpty())&&(found != true))
        {
            found2 = false;            

            v = (DSAGraphVertex)stack.pop();
            System.out.print(v.getLabel());
            path.insertLast(v.getLabel());  //add to list of vertex labels for path, used in another method to fins overall exchange rate
            v.setVisited();
            
            if (v.getLabel().equals(dest.getLabel()))
            {
                found = true;
            }
            else
            {
                System.out.print(" -> ");
                list = v.getAdjacent();
                iter = list.iterator();

                while ((iter.hasNext())&&(found2 != true))
                {
                    v2 = (DSAGraphVertex)iter.next();

                    if (v2.getVisited() == false)
                    {
                        found2 = true;
                    }
                }
                
                stack.push(v2);   
            }
        }

        return path;
    }*/        

    public DSALinkedList DFSDest(DSAGraphVertex node, DSAGraphVertex dest)
    {
        DSALinkedList list, path = new DSALinkedList();
        Iterator iter;
        DSAStack stack = new DSAStack();
        boolean found = false;
        DSAGraphVertex v, v2 = null; 

        stack.push(node);

        while ((!stack.isEmpty())&&(found != true))
        {
            v = (DSAGraphVertex)stack.pop();

            if (v.getVisited() == false)
            {
                System.out.print(v.getLabel());
                path.insertLast(v.getLabel());  //add to list of vertex labels for path, used in another method to fins overall exchange rate
                v.setVisited();
                
                if (v.getLabel().equals(dest.getLabel()))
                {
                    found = true;
                }
                else
                {
                    System.out.print(" -> ");
                    list = v.getAdjacent();
                    iter = list.iterator();
    
                    while (iter.hasNext())
                    {
                        v2 = (DSAGraphVertex)iter.next();
    
                        if (v2.getVisited() == false)
                        {
                            stack.push(v2);
                        }
                    }
                }
            }
        }

        return path;
    } 

    public void breadthFirstSearch(DSAGraphVertex node)
    {
        DSALinkedList adj; //list to hold the links of each node
        DSAGraphVertex adjacencyNode = null;    //placeholder vertex
        DSAQueue queue = new DSAShufflingQueue();    //enqueues and dequeues

        queue.enqueue(node);   //push and set visited the first vertex
        node.setVisited();        

        while (queue.isEmpty() == false)
        {
            adjacencyNode = (DSAGraphVertex)queue.dequeue();   //get first vertex in queue
            System.out.println(adjacencyNode.getLabel() + "\t");  //display it
            
            adj = adjacencyNode.getAdjacent();  //get adjacency list for vertex
            adjacencyNode = findSmallest(adj);  //this will be the smallest node that has not yet been visited
            while (adjacencyNode != null)
            {
                queue.enqueue(adjacencyNode);
                adjacencyNode.setVisited();
                adjacencyNode = findSmallest(adj);        
            }
        }   
    }
}
