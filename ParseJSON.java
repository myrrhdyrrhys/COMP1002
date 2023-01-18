/*
NOTE: The methods parseAssetInfo and processLine have been adapted from the methods I have previously submitted for every DSA weekly assessment requiring file input from a csv file.
NAME: ParseJSON.java
PURPOSE: The methods for reading in information relating to certain crypto-currencies and their trades. Asset information is expected to come from asset_info.csv and trade info from 24hr.json
AUTHOR: Lachlan Murray (19769390)
*/

import java.util.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;
import java.io.*;

public class ParseJSON
{
    /*
    NAME: parseAssetInfo 
    IMPORTS: fileName (String)
    EXPORTS: readArray (DSAGraph)
    ASSERTION: The module will attempt to open the file of the given filename, read its contents into a graph and then returns the graph to the caller.
    */
    public static DSAGraph parseAssetInfo(DSAGraph readArray, String fileName) 
    {
        FileInputStream fileStream = null;  //Create a stream object for the file to be read
        InputStreamReader rdr;  //Create an object that can read the stream
        BufferedReader bufRdr;  //Create a buffered reader object for reading a line at a time
        String line;    //String to store the line read
        try
        {
            fileStream = new FileInputStream(fileName);
            rdr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(rdr);
            line = bufRdr.readLine();   //reads first line (line that says source)
            line = bufRdr.readLine();   //reads second line (attribute headings)
            line = bufRdr.readLine();   //reads third line (when the entries actually begin in the csv
            if(line != null)    //Check if file is empty by checking first line isn't null
            {
                while(line != null)
                {
                    readArray = processLine(line, readArray);  //method to do while reading lines (e.g. storing)
                    line = bufRdr.readLine();
                }
            }
            else
            {
                throw new IllegalArgumentException("The chosen file is empty.");
            }        
            fileStream.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Couldn't find your file " + e.getMessage() + " Try again!");    //Typo/file doesnt exist
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage()); //Error when scanning empty file
        }
        catch(IOException e)    //Change to multiple specififc exceptions in order of most precise to most broad (e.g. include FileNotFoundException)
        {
            if(fileStream != null)
            {
                try
                {
                    fileStream.close();
                }
                catch(IOException ex2)
                {
                    //
                }
            }
            System.out.println("Error in file processing: " + e.getMessage());
        }
        
        return readArray;
    }
            
    /*
    SUBMODULE: processLine
    IMPORT: edgeRow (String), readArray (DSAGraph)
    EXPORT: readArray (DSAGraph)
    ASSERTION: The module will add the rows line by line and to the readArray
    */
    private static DSAGraph processLine(String edgeRow, DSAGraph readArray)
    {
        String[] splitLine; //Array of String to store the split line's components
        String[] splitLong; //Array of String to store split longs components later in method
        /*NOTE: the below line was obtained from https://stackoverflow.com/questions/15738918/splitting-a-csv-file-with-quotes-as-text-delimiter-using-string-split on 26/10/2020*/
        splitLine = edgeRow.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");  //Splits read line according to commas (ignoring quotation marks around monetary values
        double base, total = 1.0;
        long tempLong;
        int exp;

        try
        {
            //construct "value" field of DSAGraphVertex to be added
            splitLine[5] = splitLine[5].replace("$", "");  //clean up the dollar sign
            splitLine[5] = splitLine[5].replace("\"", "");  //clean up quotation marks
            splitLine[5] = splitLine[5].replace(",", "");  //clean up commas
            double price = Double.parseDouble(splitLine[5]);           
            if (splitLine[7].equals("?"))
            {
                splitLine[7] = "0";   //clean up case of unknown volume in circulation
            }
            else if (splitLine[7].contains("E"))    //case of large exponent
            {
                splitLong = splitLine[7].split("E+");
                base = Double.parseDouble(splitLong[0]);
                exp = Integer.parseInt(splitLong[1]);
                for (int i = 0; i < exp; i++)
                {
                    total = total * 10;
                }
                base = base * total;
                tempLong = (long)base;
                splitLine[7] = String.valueOf(tempLong);
            } 
            long circulating = Long.parseLong(splitLine[7]);
            DSACryptoCurrency curr = new DSACryptoCurrency(price, circulating);

            readArray.addVertex(splitLine[2], curr);   //add vertex to the graph
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return readArray;
    }   

    /*
    SUBMODULE: parse24hr
    IMPORT: graph (DSAGraph), fileName (String)
    EXPORT: graph (DSAGraph)
    ASSERTION: The method will parse in an array of Crypto-currency related trade information, and store each entry of the array in a graph edge.
    */
    public static DSAGraph parse24hr(DSAGraph graph, String fileName)
    {
        DSALinkedList vertices = graph.getVertices();
        Iterator iter = vertices.iterator();

        try
        {
            String label1 = null, label2 = null, temp1 = null, temp2 = null;
            DSAGraphVertex temp;
            DSAGraphEdge edge;
            DSACryptoTrade trade;

            JSONTokener jsonToken = new JSONTokener(new FileReader(fileName));   //24hr.json expected as fileName, or a file formatted the same way
            JSONArray symbols = new JSONArray(jsonToken);   //in order to reach each edge
            
            for (int i = 0; i < symbols.length(); i++)
            {
                JSONObject symbol = (JSONObject)symbols.get(i);     //retrieve each block
                trade = new DSACryptoTrade(symbol.getDouble("weightedAvgPrice"), symbol.getDouble("priceChangePercent"));                
                
                if (graph.hasEdge(symbol.getString("symbol")) == true)    //if edge already exists (e.g. parseAssetInfo had already been run
                {
                    edge = graph.getEdge(symbol.getString("symbol"));   //for each edge, retrieve it from the edge list in the graph (made with parseAssetInfo), and set its value/information from 24hr.json
                    edge.setValue(trade);
                }
                else    //make a new edge, the trade file does not contain fields for base and quote asset names, therefore having to have the program import asset info (names in asset_info.csv) first and then iterating through that list of vertex keys
                {
                    //cases for different combinations of keys (by length) in the trade labels
                    if (symbol.getString("symbol").length() == 6)
                    {
                        //3-3
                        label1 = symbol.getString("symbol").substring(0, 3);
                        label2 = symbol.getString("symbol").substring(3);
                    }
                    else if (symbol.getString("symbol").length() == 7)
                    {
                        //4-3
                        temp1 = symbol.getString("symbol").substring(0, 4);
                        temp2 = symbol.getString("symbol").substring(4);

                        while (iter.hasNext())  //iterates through asset labels
                        {      
                            temp = (DSAGraphVertex)iter.next();
                        
                            if (((temp1.equals(temp.getLabel()))||(temp2.equals(temp.getLabel())))&&((label1 == null)||(label2 == null)))   //label is found and both labels have not already been filled
                            {
                                if (temp1.equals(temp.getLabel()))
                                {
                                    label1 = temp.getLabel();   //base asset found
                                }
                                else
                                {
                                    label2 = temp.getLabel();   //quote asset found
                                }
                            }
                        }
                            
                        //3-4
                        if ((label1 == null)||(label2 == null)) //if the keys were not found in 4-3
                        {
                            iter = vertices.iterator();
                            label1 = null;
                            label2 = null;  //reset these from above iteration
 
                            temp1 = symbol.getString("symbol").substring(0, 3);
                            temp2 = symbol.getString("symbol").substring(3);

                            while (iter.hasNext())
                            {      
                                temp = (DSAGraphVertex)iter.next();
                                 
                                if (((temp1.equals(temp.getLabel()))||(temp2.equals(temp.getLabel())))&&((label1 == null)||(label2 == null)))   //label is found and both labels have not already been filled
                                {
                                    if (temp1.equals(temp.getLabel()))
                                    {
                                        label1 = temp.getLabel();   //base asset found
                                    }
                                    else
                                    {
                                        label2 = temp.getLabel();   //quote asset found
                                    }
                                }
                            }
                        }
                    }
                    else if (symbol.getString("symbol").length() == 8)
                    {
                        //4-4
                        temp1 = symbol.getString("symbol").substring(0, 4);
                        temp2 = symbol.getString("symbol").substring(4);

                        while (iter.hasNext())
                        {      
                            temp = (DSAGraphVertex)iter.next();
                             
                            if (((temp1.equals(temp.getLabel()))||(temp2.equals(temp.getLabel())))&&((label1 == null)||(label2 == null)))   //label is found and both labels have not already been filled
                            {
                                if (temp1.equals(temp.getLabel()))
                                {
                                    label1 = temp.getLabel();   //base asset found
                                }
                                else
                                {
                                    label2 = temp.getLabel();   //quote asset found
                                }
                            } 
                        }
                            
                        //3-5
                        if ((label1 == null)||(label2 == null))
                        {
                            iter = vertices.iterator();
                            label1 = null;
                            label2 = null;  //reset these from above iteration
 
                            temp1 = symbol.getString("symbol").substring(0, 3);
                            temp2 = symbol.getString("symbol").substring(3);

                            while (iter.hasNext())
                            {      
                                temp = (DSAGraphVertex)iter.next();
  
                                if (((temp1.equals(temp.getLabel()))||(temp2.equals(temp.getLabel())))&&((label1 == null)||(label2 == null)))   //label is found and both labels have not already been filled
                                {
                                    if (temp1.equals(temp.getLabel()))
                                    {
                                        label1 = temp.getLabel();   //base asset found
                                    }
                                    else
                                    {
                                        label2 = temp.getLabel();   //quote asset found
                                    }
                                }                      
                            }
                        }                        

                        //5-3
                        if ((label1 == null)||(label2 == null)) //if the keys were not found in 4-3
                        {
                            iter = vertices.iterator();
                            label1 = null;
                            label2 = null;  //reset these from above iteration
 
                            temp1 = symbol.getString("symbol").substring(0, 5);
                            temp2 = symbol.getString("symbol").substring(5);

                            while (iter.hasNext())
                            {      
                                temp = (DSAGraphVertex)iter.next();
                                                     
                                if (((temp1.equals(temp.getLabel()))||(temp2.equals(temp.getLabel())))&&((label1 == null)||(label2 == null)))   //label is found and both labels have not already been filled
                                {
                                    if (temp1.equals(temp.getLabel()))
                                    {
                                        label1 = temp.getLabel();   //base asset found
                                    }
                                    else
                                    {
                                        label2 = temp.getLabel();   //quote asset found
                                    }
                                } 
                            }
                        }
                    }
                    else if (symbol.getString("symbol").length() == 9)
                    {
                        //5-4
                        label1 = symbol.getString("symbol").substring(0, 5);
                        label2 = symbol.getString("symbol").substring(5);
                        
                        while (iter.hasNext())
                        {      
                            temp = (DSAGraphVertex)iter.next();
  
                            if (((temp1.equals(temp.getLabel()))||(temp2.equals(temp.getLabel())))&&((label1 == null)||(label2 == null)))   //label is found and both labels have not already been filled
                            {
                                if (temp1.equals(temp.getLabel()))
                                {
                                    label1 = temp.getLabel();   //base asset found
                                }
                                else
                                {
                                    label2 = temp.getLabel();   //quote asset found
                                }
                            } 
                        }
                            
                        //4-5
                        if ((label1 == null)||(label2 == null)) //if the keys were not found in 4-3
                        {
                            iter = vertices.iterator();
                            label1 = null;
                            label2 = null;  //reset these from above iteration
 
                            temp1 = symbol.getString("symbol").substring(0, 4);
                            temp2 = symbol.getString("symbol").substring(4);

                            while (iter.hasNext())
                            {      
                                temp = (DSAGraphVertex)iter.next();
                        
                                if (((temp1.equals(temp.getLabel()))||(temp2.equals(temp.getLabel())))&&((label1 == null)||(label2 == null)))   //label is found and both labels have not already been filled
                                {
                                    if (temp1.equals(temp.getLabel()))
                                    {
                                        label1 = temp.getLabel();   //base asset found
                                    }
                                    else
                                    {
                                        label2 = temp.getLabel();   //quote asset found
                                    }
                                } 
                            }
                        }
                    }
           
                    graph.addEdge(label1, label2, symbol.getString("symbol"), trade); 
                
                    label1 = null;
                    label2 = null;  //reset labels for next iteration
                
                    iter = vertices.iterator(); //reset iterator for next iteration
                }
            }           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return graph;
    }   
}
