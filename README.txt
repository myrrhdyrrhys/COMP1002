****************************
Executable: cryptoGraph.java
****************************

Purpose: cryptoGraph.java provides the user with options to read in, manipulate, display and export information stored in .json and .csv files relating to semi-current trading prices and statistics for a number of crypto-currencies.

Requirements: The directory that contains cryptoGraph.java should also contain:
    - DSAGraph.java: A class for the data structure of graphs, used for storing information with connections (edges) between its items (vertices).
    - DSAGraphVertex.java: A class for storing the information related to a vertex for graphs constructed using the DSAGraph class.
    - DSAGraphEdge.java: A class for storing the information related to a connection between vertices in the DSAGraph class.
    - DSALinkedList.java: A class for the data structure of linked lists, used in multiple other classes to hold related data in a list. Examples being the lists of vertices and edges in the DSAGraph class, the list of adjacent nodes in DSAGraphEdge, the data to be stored in DSAShufflingQueue.
    - DSAStack.java: A class that makes use of DSALinkedList.java to provide a stack data structure for use in the DSAGraph class (for DFSDest)
    - DSAQueue.java: Parent class to DSAShufflingQueue.java
    - DSAShufflingQueue.java: A class that makes use of DSALinkedList.java to provide a queue data structure for use in the DSAGraph class (for breadthFirstSearch)
    - DSACryptoCurrency.java: A class for holding the information specific to a single type of crypto-currency. Used to hold the related data at the vertices of the graph made in cryptoGraph.java.
    - DSACryptoTrade.java: A class for holding the information specific to a single type of trade between crypto-currencies. Used to hold the related data at the edges of the graph made in cryptoGraph.java.
    - ParseJSON.java: A class for reading the information from 24hr.json and exchangeInfo.json, and storing the information in a graph.
    - UserInterface.java: A class containing methods used for data input validation for different types of data. It is used for the menu system present in cryptoGraph, when used in interactive mode.
    Further descriptions of how these classes are used in the operation of cryptoGraph are in the associated project report.

NOTE: This program makes use of a JSON parser, obtained from www.github.com/stleary/JSON-java, and as such requires the .jar to be specified in the compilation and execution lines.

Compilation: javac -cp json-20200518.jar *.java

Execution (for usage guide): java -Xss4m -cp .:json-20200518.jar cryptoGraph

