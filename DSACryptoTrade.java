/*
NAME: DSACryptoTrade.java
PURPOSE: A model class for holding the cryptocurrency trade information to be stored in the "value" field in a DSAGraphEdge.
AUTHOR: Lachlan Murray (19769390)
*/

import java.util.*;
import java.io.*;

public class DSACryptoTrade implements Serializable
{
    //Classfields
    private double exchangeRate;
    private double percentChange;

    //Contructor
    public DSACryptoTrade(double inExchange, double inPercent)
    {
        exchangeRate = inExchange;
        percentChange = inPercent;
    }

    //Accessors
    public double getExchange()
    {
        return exchangeRate;
    }

    public double getPercent()
    {
        return percentChange;
    }

    //Mutators
    public void setExchange(double inExchange)
    {
        exchangeRate = inExchange;
    }
    
    public void setPercent(double inPercent)
    {
        percentChange = inPercent;
    }
    
    //Others
    public String toString()
    {
        String outStr = "Exchange Rate: " + String.valueOf(exchangeRate) + "\n" + "Percent Change (last 24hrs): " + String.valueOf(percentChange);
    
        return outStr;
    }
}   
