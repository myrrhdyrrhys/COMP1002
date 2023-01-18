/*
NOTE: This class file was submitted by myself previously as part of DSA's weekly assessments (Week 4 in particular)
NAME: DSALinkedList.java
PURPOSE: to provide the class for creating linked lists with ListNodes.
AUTHOR: Lachlan Murray (19769390)
LAST MOD:
*/

import java.util.*;
import java.io.*;

public class DSALinkedList implements Iterable, Serializable
{
    //PRIVATE CLASSES
    /*
    CLASS: DSAListNode
    ASSERTIONS: to provide the class for creating instances of nodes in a linked list.
    */
    private class DSAListNode implements Serializable
    {
        //Class fields
        private Object value;
        private DSAListNode prev;   //when set to null indicates the head node
        private DSAListNode next;   //when set to null indicates the tail node
    
        /*
        SUBMODULE: Alternate Constructor
        IMPORTS: inValue (Object)
        EXPORTS: address of new DSAListNode object
        ASSERTION: value is set to the inValue Object value, and prev and next is set to null.
        */
        public DSAListNode(Object inValue)
        {
            value = inValue;
            prev = null;
            next = null;
        }
    
        //ACCESSORS
        public Object getValue()
        {
            return value;
        }

        public DSAListNode getPrev()
        {
            return prev;
        }
    
        public DSAListNode getNext()
        {
            return next;
        }
    
        //MUTATORS
        public void setValue(Object inValue)
        {
            value = inValue;
        }

        public void setPrev(DSAListNode newPrev)
        {
            prev = newPrev;
        }
    
        public void setNext(DSAListNode newNext)
        {
            next = newNext;
        }
    }
    
    //iterator Method
    public Iterator iterator()
    {
        return new DSALinkedListIterator(this); //hook iterator to 'this' DSALinkedListObject
    }

    /*
    CLASS: DSALinkedListIterator
    ASSERTIONS: to allow iteration through linked list elements.
    */
    private class DSALinkedListIterator implements Iterator, Serializable
    {
        //Class fields
        private DSAListNode iterNext;    //the cursor
        
        /*
        SUBMODULE: Alternate Constructor
        IMPORTS: inList (DSALinkedList)
        EXPORTS: address of new DSALinkedListIterator object
        ASSERTION: iterNext (cursor) is set tot eh head of inList.
        */
        public DSALinkedListIterator(DSALinkedList inList)
        {
            iterNext = inList.head;
        }

        //Iterator interface
        public boolean hasNext()
        {
            return (iterNext != null);
        }
        
        public Object next()
        {
            Object value;
            
            if (iterNext == null)
            {
                value = null;
            }
            else
            {
                value = iterNext.getValue();    //retrieves value in the node pointed to
                iterNext = iterNext.getNext();  
            }
            
            return value;
        }

        public void remove()
        {
            throw new UnsupportedOperationException("Unsupported operation!");
        }
    }
 
    //DSALinkedList
    //Class fields
    private DSAListNode head;
    private DSAListNode tail;

    /*
    SUBMODULE: Default Constructor
    IMPORTS: none
    EXPORTS: address of new DSALinkedList object
    ASSERTION: head and tail are set to null.
    */
    public DSALinkedList()
    {
        head = null;
        tail = null;
    }

    //MUTATORS
    public void insertFirst(Object newValue)
    {
        DSAListNode newNd = new DSAListNode(newValue);
        
        if (isEmpty() == true)
        {
            head = newNd;
            tail = newNd;   //sets the head and tail node as the single existing node
        }
        else
        {
            newNd.setNext(head);    //set the new node to point to the current head as next
            head.setPrev(newNd);    //set old head to point to new node as previous
            //newNd.setPrev(null);    prev is null by default
            head = newNd;       //Sets new head to the most recent node
        }
    }

    public void insertLast(Object newValue)
    {
        DSAListNode newNd = new DSAListNode(newValue);
        //DSAListNode currNd;
    
        if (isEmpty() == true)
        {
            head = newNd;
            tail = newNd;   //sets the head and tail node as the single existing node
        }
        else
        {
            newNd.setPrev(tail);    //set the new node to point to the current tail as previous
            tail.setNext(newNd);    //set old tail to point to new node as next
            tail = newNd;   //set new tail to the new node
            
            /* //needed if single ended
            while (currNd.getNext() != null)    //traverses to last node
            {
                currNd = currNd.getNext();
            }
            */
        }
    }

    public Object removeFirst()
    {
        Object nodeValue;

        if (isEmpty() == true)
        {
            throw new IllegalArgumentException("The list is empty!");
        }
        else if (head.getNext() == null) //case of single-node list
        {
            nodeValue = head.getValue();
            head = null;
            tail = null;
        }
        else
        {
            nodeValue = head.getValue();
            head = head.getNext();
            head.setPrev(null);
        }

        return nodeValue;
    }

    public Object removeLast()
    {
        Object nodeValue;
    
        if (isEmpty() == true)
        {
            throw new IllegalArgumentException("The list is empty!"); 
        }
        else if (tail.getPrev() == null)
        {
            nodeValue = tail.getValue();
            head = null;
            tail = null;
        }
        else
        {
            nodeValue = tail.getValue();
            tail = tail.getPrev();
            tail.setNext(null);
        }

        return nodeValue;
    }
       

    //ACCESSORS
    public boolean isEmpty()
    {
        boolean empty = false;
    
        if (head == null)
        {
            empty = true;
        }
        
        return empty;
    }

    public Object peekFirst()
    {
        Object nodeValue;

        if (isEmpty() == true)
        {
            throw new IllegalArgumentException("The list is empty.");
        }
        else
        {
            nodeValue = head.getValue();
        }

        return nodeValue;
    }

    public Object peekLast()
    {
        Object nodeValue;
        DSAListNode currNd;
    
        if (isEmpty() == true)
        {
            throw new IllegalArgumentException("The list is empty.");
        }
        else
        {   
            nodeValue = tail.getValue();
        }
    
        return nodeValue;
    }

}
