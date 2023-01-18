/*
NAME: DSACryptoCurrency.java
PURPOSE: The model class that would be used for holding the information relating to a type of crypto-currency to be stored in the "value" field of DSAGraphVertex in the graph, information taken from the relevant csv file.
AUTHOR: Lachlan Murray (19769390)
*/

import java.util.*;
import java.io.*;

public class DSACryptoCurrency implements Serializable
{
    //Classfields
    private double price;
    private long circulating;

    //Constructor
    public DSACryptoCurrency(double inPrice, long inCirculating)
    {
        price = inPrice;
        circulating = inCirculating;
    }

    //Accessors
    public double getPrice()
    {
        return price;
    }

    public long getCirculating()
    {
        return circulating;
    }
    
    //Mutators
    public void setPrice(double inPrice)
    {
        price = inPrice;
    }
    
    public void setCirculating(long inCirculating)
    {
        circulating = inCirculating;
    }
    
    //Others
    public String toString()
    {
        String outStr = "Price: " + String.valueOf(price) + "\nCurrently Circulating: " + String.valueOf(circulating);
        
        return outStr;
    }
}
