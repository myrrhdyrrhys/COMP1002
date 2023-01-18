/*
NAME: CryptoGraph.java
PURPOSE: a program to analyse cryto-currency trading data using ADTs
AUTHOR: Lachlan Murray (19769390)
*/

import java.util.*;
import java.io.*;

public class cryptoGraph
{
    public static void main(String args[])
    {
        //variable declarations
        String assetFile, tradeFile;

        //take command line arguments to determine mode chosen
        if (args.length == 0)
        {
            System.out.println("\nUsage information/guide");
            outputGuide();
        }
        else if (args[0].equals("-i"))
        {
            System.out.println("\nInteractive mode chosen.");
            interactiveMode();
        }
        else if (args[0].equals("-r"))
        {
            System.out.println("\nReport mode chosen.");
            assetFile = args[1];
            tradeFile = args[2];    //take filenames of files that contain report data from command line
            reportMode(assetFile, tradeFile);
        }
        else
        {
            System.out.println("Incorrect arguments passed. Please run program with no arguments following 'cryptoGraph' to see usage guide.");
        }
    }

    //The module responsible for outputting the usage guide of the program to the terminal
    private static void outputGuide()
    {
        System.out.println("\n\t- This program is designed to read in crypto-currency trading data and display certain information to the user.");
        System.out.println("\t- The program has two modes, which may be chosen using command line arguments.");
        System.out.println("\n\t- The first mode is Report Mode, in which the user may specify an asset file and a trade file located in the program's directory to be read in, with key statistics regarding the data displayed to the user.");
        System.out.println("\t\tExecution (for report mode): java -cp .:json-20200518.jar cryptoGraph -r <asset_file> <trade_file>\n\t\t(e.g. java -cp .:json-20200518.jar cryptoGraph -r asset_info.csv 24hr.json)");
        System.out.println("\n\t- The second mode is Interactive Mode, in which the user will be presented with a menu with a number options to read in crypto-currency data, query for information regarding certain assets, trades and trade paths, as well as be given the option to save the stored information to a serialized file.");
        System.out.println("\t\tExecution (for interactive mode): java -Xss4m -cp .:json-20200518.jar cryptoGraph -i\n");
    }

    //module for providing an interactive menu to import data, inspect it, and export it serialiised
    private static void interactiveMode()
    {
        DSAGraph graph = new DSAGraph();
        int menuChoice;
        
        menuChoice = UserInterface.userInput("Please choose and option from the following. (Note that option 1 must be executed before any of the other options)\n\t1. Load data\n\t2. Find and display an asset\n\t3. Find and display trade details between two assets\n\t4. Find and display potential trade paths between two assets\n\t5. Set a filter for an asset\n\t6. View an overview of an asset\n\t7. View an overview of a trade\n\t8. Save data (Serialised)\n\t0. Exit\n", 0, 1);
        switch (menuChoice) //The first instance of the menu, where option 1 or 0 must be chosen
        {
            case 1:
                graph = importData(graph);   //import asset, trade or serialized data
            break;
            case 0:
                System.out.println("Program exiting... Have a nice day!");
            break;
        }
        
        if (menuChoice != 0)    //If the option to exit was not chose in the first menu instance
        {
            do
            {
                menuChoice = UserInterface.userInput("Please choose and option from the following.\n\t1. Load data\n\t2. Find and display an asset\n\t3. Find and display trade details between two assets\n\t4. Find and display potential trade paths between two assets\n\t5. Set a filter for an asset\n\t6. View an overview of important assets\n\t7. View an overview of important trades\n\t8. Save data (Serialised)\n\t0. Exit\n", 0, 8);
                switch (menuChoice)
                {
                    case 1:
                        graph = importData(graph);   //import asset, trade or serialised data
                    break;
                    
                    case 2:
                        displayAsset(graph); //find and display a particular asset
                    break;
                    
                    case 3:
                        displayTrade(graph); //find and display a particular trade between assets
                    break;
                    
                    case 4:
                        displayPaths(graph); //find and display potential trade paths between two assets
                    break;
                    
                    case 5:
                        graph = setFilter(graph);    //set filter for assets
                    break;
                
                    case 6:
                        assetOverview(graph);    //Give an overview of an assets information
                    break;
                    
                    case 7: 
                        tradeOverview(graph);    //give an overview of a trades information
                    break;
    
                    case 8:
                        saveData(graph);     //save currently stored data serialised in a file
                    break;
                    
                    case 0:
                        System.out.println("Program exiting... Have a nice day!");
                    break;
                }
            }while(menuChoice != 0);    //loop menu while exit option not chosen
        }
    }

    //import asset, trade or serialised data
    private static DSAGraph importData(DSAGraph graph)
    {
        String fileName;
        int menuChoice;
        DSALinkedList v = graph.getVertices();

        //takes input for choice, checks to see if vertices have been imported first
        if (v.isEmpty() == true)
        {
            do
            {
                menuChoice = UserInterface.userInput("Please choose and option from the following. (Note that asset data must be loaded before trade data)\n\t1. Load data from a serialized file\n\t2. Load asset data (asset_info.csv)\n\t3. Load trade data (24hr.json)\n", 1, 2);
                fileName = UserInterface.userInput("Please enter the filename of the file to read from: ");
                switch (menuChoice) //The first instance of the menu, where option 1 or 0 must be chosen
                {
                    case 1:
                        graph = loadData(fileName); //import serialized data
                    break;
                    case 2:
                        graph = ParseJSON.parseAssetInfo(graph, fileName);   //import asset data
                    break;
                }
            }while(graph.getVertices().isEmpty() == true);
        }
        else
        {
            menuChoice = UserInterface.userInput("Please choose and option from the following. (Note that asset data must be loaded before trade data)\n\t1. Load data from a serialized file\n\t2. Load asset data (asset_info.csv)\n\t3. Load trade data(24hr.json)\n", 1, 3);
            fileName = UserInterface.userInput("Please enter the filename of the file to read from: ");
            switch (menuChoice) //The first instance of the menu, where option 1 or 0 must be chosen
            {
                case 1:
                    graph = loadData(fileName); //import serialized data
                break;
                case 2:
                    graph = ParseJSON.parseAssetInfo(graph, fileName);   //import asset data
                break;
                case 3:
                    graph = ParseJSON.parse24hr(graph, fileName);   //import trade data
                break;
            }
        }

        return graph;
    }

    //Display a particular asset
    private static void displayAsset(DSAGraph graph)
    {
        //get name of asset
        String label = UserInterface.userInput("Please enter the asset label (e.g. BTC): ");

        //get asset from graph using key
        DSAGraphVertex asset = graph.getVertex(label);
        
        //display label
        System.out.println("Asset Label: " + asset.getLabel());
        
        //retrieve detailed asset info
        DSACryptoCurrency curr = (DSACryptoCurrency)asset.getValue();

        //display detailed information
        System.out.println(curr.toString());
    }
    
    //Display a particular trade
    private static void displayTrade(DSAGraph graph)
    { 
        //get name of trade
        String label = UserInterface.userInput("Please enter the trade label (e.g. ETHBTC): ");

        //get trade from graph using key
        DSAGraphEdge trade = graph.getEdge(label);
        
        //display information
        System.out.println(trade.toString());
    }
    
    //display the trade paths between two assets
    private static void displayPaths(DSAGraph graph)
    {
        //get vertex labels
        String label1 = UserInterface.userInput("Please enter the label of the base asset: ");
        String label2 = UserInterface.userInput("Please enter the label of the quote asset: ");

        //get vertices
        DSAGraphVertex v1 = graph.getVertex(label1);
        DSAGraphVertex v2 = graph.getVertex(label2);
        
        //ouput
        System.out.println("An indirect trade path from base asset '" + label1 + "' to quote asset '" + label2 +"'.");

        //run traversal
        DSALinkedList path = graph.DFSDest(v1, v2);
        System.out.println();
        
        //reset visited flag
        graph.setAllNotVisited();
    
        /*NOT FULLY IMPLEMENTED
        //calculate effective exchange rate from path
        double exchangeRate = calcExchangeRate(graph, path);
 
        System.out.println();
        System.out.println("Effective exchange rate is 1:" + String.valueOf(exchangeRate));*/
    }

    //calculates the effective exhange rate between two assets, by following the path found by DFSDest()
    private static double calcExchangeRate(DSAGraph graph, DSALinkedList path)
    {
        Iterator iter = path.iterator();
        double total = 1.0;        
        DSAGraphEdge edge;
        DSACryptoTrade trade = null;
        String edgeLabel, v1, v2;

        v1 = (String)iter.next();

        while (iter.hasNext())
        {
            v2 = (String)iter.next();
            edgeLabel = v1 + v2;    //NOT HOW IT WORKS FOR DFS, could be the edge between the current vertex and the vertex 3 places before it in the list, current;y throws null pointer exception because it reaches edges that do not exist
            edge = graph.getEdge(edgeLabel);
            if (edge.getValue() == null)
            {
                System.out.println(edge.getLabel());
            }
            else
            {
                trade = (DSACryptoTrade)edge.getValue();
            }
            total *= trade.getExchange();
            v1 = v2;
        }

        return total;
    }
        
    //Set a filter to include or exclude particular assets from the trade path searches
    private static DSAGraph setFilter(DSAGraph graph)
    {
        //retrieve vertex from graph
        String label = UserInterface.userInput("Please enter the asset label (e.g. ETH): ");
        DSAGraphVertex v = graph.getVertex(label);

        //ask user for operation type
        int choice = UserInterface.userInput("Would you like to:\n\t1. Filter out the chosen vertex from path searches, or\n\t2. Remove the filter already placd on the chosen vertex\n(Note that the filter is reset after a search performed (one-time use))\n", 1, 2);
        if (choice == 1)
        {
            v.setVisited();
        }
        else
        {
            v.clearVisited();
        }

        return graph;
    }
        
    //Provide an overview of the information relating to 'important' assets in the list
    private static void assetOverview(DSAGraph graph)
    {
        //find the top 10 valued assets (to USDT)
        DSALinkedList topTen = constructTopTenUSD(graph);
        
        //output
        System.out.println("The following is a list of the top ten highest valued assets when compared to USD.");
        Iterator iter = topTen.iterator();
        DSAGraphEdge temp;
        
        while (iter.hasNext())
        {
            temp = (DSAGraphEdge)iter.next();
            System.out.println(temp.toString());
        }
        System.out.println();
    }
        
    //Provide an overview of the information relating to 'important' trades in the list
    private static void tradeOverview(DSAGraph graph)
    {
        //find the top 10 trades with the highest average price ratio in the past 24hrs
        DSALinkedList topTen = constructTopTenPrice(graph);
        
        //output
        System.out.println("The following is a list of the top ten highest average trading price ratios.");
        Iterator iter = topTen.iterator();
        DSAGraphEdge temp;
        
        while (iter.hasNext())
        {
            temp = (DSAGraphEdge)iter.next();
            System.out.println(temp.toString());
        }
        System.out.println();
    }

    //Load a graph from a serialized file
    private static DSAGraph loadData(String fileName)
    {
        FileInputStream fileStrm;
        ObjectInputStream objStrm;
        DSAGraph inObj = null;

        try
        {
            fileStrm = new FileInputStream(fileName);
            objStrm = new ObjectInputStream(fileStrm);
            inObj = (DSAGraph)objStrm.readObject();
            objStrm.close();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Class DSAGraph not found. " + e.getMessage());
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Unable to load object from file!");
        }
        
        return inObj;
    } 

    //Save the currently stored data to a serialised file
    private static void saveData(DSAGraph objToSave)
    {
        FileOutputStream fileStrm;
        ObjectOutputStream objStrm;
        
        try 
        {
            fileStrm = new FileOutputStream("serialisedGraph.txt"); 
            objStrm = new ObjectOutputStream(fileStrm);
            objStrm.writeObject(objToSave); //serialize and save to specified filename
            objStrm.close();
            System.out.println("Graph saved to serialisedGraph.txt");
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Unable to save object to file!");
        }
    }

    //Output statistics for the data set provided
    private static void reportMode(String assetFile, String tradeFile)
    {
        //will involve reading a formatted .json file of asset and trade data, calculating top statistics and outputting the information to the terminal
        //initialise graph
        DSAGraph graph = new DSAGraph();

        //retrieve vertex/asset information
        graph = ParseJSON.parseAssetInfo(graph, assetFile);
        
        //retieve edge/trade information
        graph = ParseJSON.parse24hr(graph, tradeFile);
        
        //TEMPORARY (to print out edge list for debugging purposes)
        /*DSALinkedList edges = graph.getEdges();
        Iterator iter = edges.iterator();
        DSAGraphEdge temp2;
        while (iter.hasNext())
        {
            temp2 = (DSAGraphEdge)iter.next();
            System.out.println(temp2.toString());
        }*/

        //find the top 10 trades with the highest percentage change in the past 24hrs
        DSALinkedList topTen = constructTopTenPercent(graph);
        
        //output
        System.out.println("The following is a list of the top ten percentage changes in trading prices.");
        Iterator iter = topTen.iterator();
        DSAGraphEdge temp;
        
        while (iter.hasNext())
        {
            temp = (DSAGraphEdge)iter.next();
            System.out.println(temp.toString());
        }

        System.out.println();
    }
    
    //exports a linked list of ten edges with the highest percentage price change
    private static DSALinkedList constructTopTenPercent(DSAGraph graph)
    {
        DSALinkedList edges = graph.getEdges();     //list of edges in the graph
        DSALinkedList ten = new DSALinkedList();    //list to hold the top 10
        Iterator iter = edges.iterator(), iter2;    //iterators for the edge list and top ten list
        DSAGraphEdge edge, currHighest = null, tenEdge;    //temporary edges
        DSACryptoTrade tradeTemp;   //temporary detailed trade info
        double percent, currPercent, TOL = 0.0001;    //temporary percentage values and Tolerance value for comparisons
        boolean done = false, present = false;        //loop flags
        int counter = 0;    //to keep track of how many items are in the top ten list (stops at ten)

        //loops through the graph's edge list to find highest valued edge for percent, then iterates the edge list again 9 more times, but after the first iteration it checks the contents of the top ten list to ensure no doubles
        while (done == false)   //will be true when ten items in top ten list
        {
            while ((iter.hasNext()))    //loop through edge list
            {
                edge = (DSAGraphEdge)iter.next();   //retrieve current graph edge from edge list
                tradeTemp = (DSACryptoTrade)edge.getValue();    //retrieve detailed information about the trade                
                percent = tradeTemp.getPercent();   //retrieve percentage change from current trade/edge
                
                if (currHighest == null) //if it's the first percent found in list
                {
                    currHighest = edge;   //set current highest to it
                }
                else
                {
                    //check if already in top ten list
                    iter2 = ten.iterator(); //top ten list iterator
        
                    while ((iter2.hasNext())&&(present == false))   //iterates through until found or until end of list
                    {
                        tenEdge = (DSAGraphEdge)iter2.next();   //retrieve current edge in top ten list
                        tradeTemp = (DSACryptoTrade)tenEdge.getValue(); //retrieve current edge's detailed information

                        if (Math.abs(tradeTemp.getPercent() - percent) < TOL)   //if the same (percent is already in top ten list and should be ignored for rest of while loop)
                        {
                            present = true;
                        }   
                    }
                    
                    //if not already in top ten list, check against currHighest and swap if need be
                    if (present == false)
                    {
                        tradeTemp = (DSACryptoTrade)currHighest.getValue(); //retrieve detailed information of edge

                        if (percent > tradeTemp.getPercent())   //if current edge's percent change is greater than the current highest's
                        {
                            currHighest = edge; //set new current highest
                        }
                    }
                    
                    present = false; //reset present checker       
                }
            }
            
            //insert 'highest' edge in the latest iteration of the edge list
            ten.insertLast(currHighest);
            currHighest = null; //reset current highest 

            iter = edges.iterator();    //reset iterator

            //check if list has ten items yet
            iter2 = ten.iterator();
            
            while (iter2.hasNext())
            {
                counter++;          //should be 0 initially
                iter2.next();
            }
            
            if (counter == 10)
            {
                done = true;    //list is complete
            }
            else
            {
                counter = 0;    //keep going
            }
        }
    
        return ten;
    }

    //searches trades list for the ten with the highest averagePrice values, and constructs a list of these edges to return
    private static DSALinkedList constructTopTenPrice(DSAGraph graph)
    {
        DSALinkedList edges = graph.getEdges();
        DSALinkedList ten = new DSALinkedList();    //list to hold the top 10
        Iterator iter = edges.iterator(), iter2;
        DSAGraphEdge edge, currHighest = null, currEdge;
        DSACryptoTrade tradeTemp;
        double avg, currAvg, TOL = 0.0001;
        boolean done = false, present = false;        
        int counter = 0;

        while (done == false)
        {
            while ((iter.hasNext()))
            {
                edge = (DSAGraphEdge)iter.next();   //retrieve current graph edge from edge list
                tradeTemp = (DSACryptoTrade)edge.getValue();    //retrieve detailed information about trades
                avg = tradeTemp.getExchange();   //retrieve exchange rate from current trade/edge
                
                if (currHighest == null) //if it's the first rate found in list
                {
                    currHighest = edge;   //set current highest to it
                }
                else
                {
                    //check if already in top ten list
                    iter2 = ten.iterator(); //top ten lsit iterator
        
                    while ((iter2.hasNext())&&(present == false))   //iterates through until found or until end of list
                    {
                        currEdge = (DSAGraphEdge)iter2.next();
                        tradeTemp = (DSACryptoTrade)currEdge.getValue();

                        if (Math.abs(tradeTemp.getExchange() - avg) < TOL)   //if the same (percent is already in top ten list and should be ignored)
                        {
                            present = true;
                        }   
                    }
                    
                    //if not, check against currLowest and swap if need be
                    if (present == false)
                    {
                        tradeTemp = (DSACryptoTrade)currHighest.getValue();

                        if (avg > tradeTemp.getExchange())
                        {
                            currHighest = edge;
                        }
                    }
                    
                    present = false; //reset present checker       
                }
            }
            
            //insert lowest edge in the latest iteration of the edge list
            ten.insertLast(currHighest);     
            currHighest = null; //reset current highest value

            iter = edges.iterator();    //reset iterator

            //check if list has ten items yet
            iter2 = ten.iterator();
            
            while (iter2.hasNext())
            {
                counter++;          //should be 0 initially
                iter2.next();
            }
            
            if (counter == 10)
            {
                done = true;
            }
            else
            {
                counter = 0;
            }
        }
    
        return ten;
    }

    //searches trades list for the ten trades containing "USDT" with the highest averagePrice value, and then constructs a list of these edges to return
    private static DSALinkedList constructTopTenUSD(DSAGraph graph)
    {
        DSALinkedList edges = graph.getEdges();
        DSALinkedList ten = new DSALinkedList();    //list to hold the top 10
        Iterator iter = edges.iterator(), iter2;
        DSAGraphEdge edge, currHighest = null, currEdge;
        DSAGraphVertex to;
        DSACryptoTrade tradeTemp;
        double exchange, currExchange, TOL = 0.0001;
        boolean done = false, present = false;        
        int counter = 0;
        String quote;

        while (done == false)
        {
            while ((iter.hasNext()))
            {
                edge = (DSAGraphEdge)iter.next();   //retrieve current graph edge from edge list
                to = edge.getTo();
                quote = to.getLabel();
                if ((quote).equals("USDT"))   //check if the trade is between usd
                {
                    tradeTemp = (DSACryptoTrade)edge.getValue();    //retrieve detailed information about trades
                    exchange = tradeTemp.getExchange();   //retrieve exchange rate from current trade/edge
                    
                    //check if already in top ten list
                    iter2 = ten.iterator(); //top ten list iterator
            
                    while ((iter2.hasNext())&&(present == false))   //iterates through until found or until end of list
                    {
                        currEdge = (DSAGraphEdge)iter2.next();  //current edge in the ten list
                        tradeTemp = (DSACryptoTrade)currEdge.getValue();

                        if (Math.abs(tradeTemp.getExchange() - exchange) < TOL)   //if the same (percent is already in top ten list and should be ignored)
                        {
                            present = true;
                        }   
                    }

                    //if not already in top ten list
                    if (present == false)
                    {
                        if (currHighest == null) //if it's the first rate found in the edges list
                        {
                            currHighest = edge;   //set current highest to it
                        }
                        else
                        {               
                            //if not, check against currHighest and swap if need be
                            tradeTemp = (DSACryptoTrade)currHighest.getValue();
    
                            if (exchange > tradeTemp.getExchange())
                            {
                                currHighest = edge;
                            }
                        }    
                    }

                    present = false; //reset present checker       
                }
            }
                
            //insert lowest edge in the latest iteration of the edge list
            ten.insertLast(currHighest);     
            currHighest = null; //reset current highest

            iter = edges.iterator();    //reset iterator
    
            //check if list has ten items yet
            iter2 = ten.iterator();
                
            while (iter2.hasNext())
            {
                counter++;          //should be 0 initially
                iter2.next();
            }
                
            if (counter == 10)
            {
                done = true;
            }
            else
            {
                counter = 0;
            }
        }
    
        return ten;
    }
}
