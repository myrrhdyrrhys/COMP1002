/*
NOTE: This class file was submitted by myself previously as part of DSA's weekly assessments (Week 6 in particular)
NAME: DSAGraphVertex.java
PURPOSE: a class for the vertices of the graphs implemented using DSAGraph.java
AUTHOR: Lachlan Murray (19769390)
LAST MOD:
*/  

import java.util.*;
import java.io.*;

public class DSAGraphVertex implements Serializable
{
    //Class fields
    private String label;
    private Object value;
    private DSALinkedList links;
    private boolean visited;
    
    //ALTERNATE CONSTRUCTOR
    public DSAGraphVertex(String inLabel, Object inValue)
    {
        if (inLabel != null)
        {
            label = inLabel;
        }
        
        value = inValue;
        links = new DSALinkedList();
        visited = false;
    }
    
    //MUTATORS
    public void addEdge(DSAGraphVertex vertex)
    {
        links.insertLast(vertex);
    }

    public void setVisited()
    {
        visited = true;   
    }

    public void clearVisited()
    {
        visited = false;
    }
    
    //ACCESSORS
    public String getLabel()
    {
        return label;
    }
    
    public Object getValue()
    {
        return value;
    }
    
    public boolean getVisited()
    {
        return visited;
    }

    public DSALinkedList getAdjacent()
    {    
        return links;
    }

    public String toString()
    {
        return label;
    }
}    
