/*
NAME: DSAGraphEdge.java
PURPOSE: To provide a model class for edges in a graph, required if storing weights or other information
AUTHOR: Lachlan Murray (19769390)
*/

import java.util.*;
import java.io.*;

public class DSAGraphEdge implements Serializable
{
    //CLASSFIELDS
    private String label;
    private Object value;   
    private DSAGraphVertex from;
    private DSAGraphVertex to;

    //Constructor
    public DSAGraphEdge(String inLabel, Object inValue, DSAGraphVertex fromVertex, DSAGraphVertex toVertex)
    {
        if ((inLabel != null)&&(fromVertex != null)&&(toVertex != null))
        {
            label = inLabel;
            value = inValue;
            from = fromVertex;
            to = toVertex;
        }
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
        
    public DSAGraphVertex getFrom()
    {
        return from;
    }
    
    public DSAGraphVertex getTo()
    {
        return to;
    }

    //MUTATORS
    public void setLabel(String inLabel)
    {
        label = inLabel;
    }
    
    public void setValue(Object inValue)
    {
        value = inValue;
    }   
    
    public String toString()
    {
        DSACryptoTrade val = (DSACryptoTrade)value;

        String outStr = "Edge Label: " + label + "\n" + val.toString() + "\nBaseAsset: " + from.getLabel() + "\nQuote Asset: " + to.getLabel();
        
        return outStr;
    }
}
