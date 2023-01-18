/*
NAME: DSAStack.java
PURPOSE: to simulate a stack using Linked Lists.
AUTHOR: Lachlan Murray (19769390)
LAST MOD:
*/

import java.util.*;

public class DSAStack implements Iterable
{
    //Class fields
    private DSALinkedList stack;
    private int count;
    
    //Class constant (no longer needed after implementing dynamic list
    //private static final int DEFAULT_CAPACITY = 100; 

    /*
    SUBMODULE: Default Constructor
    IMPORTS: none
    EXPORTS: address of new DSAStack object
    ASSERTION: stack is a new DSALinkedList, and count is 0
    */
    public DSAStack()
    {
        stack = new DSALinkedList();
        count = 0;
    }

    //ACCESSORS
    public int getCount()
    {
        return count;
    }

    public DSALinkedList getStack()
    {
        return stack;
    }

    public boolean isEmpty()
    {
        boolean empty = false;
        
        if (stack.isEmpty() == true)
        {
            empty = true;
        }
        
        return empty;
    }

    //MUTATORS
    /*
    SUBMODULE: push
    IMPORTS: value (boolean)
    EXPORTS: none
    ASSERTIONS: adds a new item to the top of the stack.
    */
    public void push(Object value)
    {
        stack.insertFirst(value);
        //count += 1;
    }

    /*
    SUBMODULE: pop
    IMPORTS: none
    EXPORTS: topVal (Object)
    ASSERTIONS: removes the top-most item from the stack.
    */
    public Object pop()
    {
        Object topVal = stack.peekFirst();
        stack.removeFirst();
        //count -= 1;

        return topVal;
    }

    /*
    SUBMODULE: top
    IMPORTS: none 
    EXPORTS: topVal (Object)
    ASSERTIONS: looks at the top-most item, but leaves it on the stack.
    */
    public Object top()
    {
        Object topVal = stack.peekFirst();

        return topVal;
    }

    /*
    SUBMODULE: displayStack
    IMPORTS: none
    EXPORTS: none
    ASSERTION: will print contents of queue to user
    */
    public void displayStack()
    {
        if (isEmpty())
        {
            System.out.println("Stack is Empty!");
        }
        else
        {
            for (Object temp : stack)
            {
                System.out.println(temp);
            }
            System.out.println("-----------------------------------------------");
        }
    }

    public Iterator iterator()
    {
        return stack.iterator();
    }
}
