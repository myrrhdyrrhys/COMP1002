/*
NAME: FileIO.java
PURPOSE: A static class with a module designed to open files and read in its contents to a graph and return that to caller (used in UnitTestGraph.java).
AUTHOR: Lachlan Murray
LAST MOD: 5:40PM 21/09/20
*/

import java.util.*;
import java.io.*;
import java.awt.*;

public class FileIO
{
    /*
    NAME: readFile 
    IMPORTS: fileName (String)
    EXPORTS: readArray (DSAGraph)
    ASSERTION: The module will attempt to open the file of the given filename, read its contents into a graph and then returns the graph to the caller.
    */
    public static DSAGraph readFile(String fileName)
    {
        DSAGraph readArray = new DSAGraph();   //set read array
        FileInputStream fileStream = null;  //Create a stream object for the file to be read
        InputStreamReader rdr;  //Create an object that can read the stream
        BufferedReader bufRdr;  //Create a buffered reader object for reading a line at a time
        String line;    //String to store the line read
        try
        {
            fileStream = new FileInputStream(fileName);
            rdr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(rdr);
            line = bufRdr.readLine();   //reads first line
            if(line != null)    //Check if file is empty by checking first line isn't null
            {
                while(line != null)
                {
                    processLine(line, readArray);  //method to do while reading lines (e.g. storing)
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
    EXPORT: none
    ASSERTION: The module will add the rows line by line and to the readArray
    */
    private static void processLine(String edgeRow, DSAGraph readArray)
    {
        String[] splitLine; //Array of String to store the split line's components
        splitLine = edgeRow.split(" ");  //Splits read line according to the space between the vertex labels

        try
        {
            readArray.addVertex(splitLine[0], 0);
            readArray.addVertex(splitLine[1], 0);  //add the two vertices first, so that their references may be added
            readArray.addEdge(splitLine[0], splitLine[1], splitLine[0]+splitLine[1], 0);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }            
}
