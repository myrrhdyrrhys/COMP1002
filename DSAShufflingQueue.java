/*
NOTE: This class file was submitted by myself previously as part of DSA's weekly assessments (Week 3 in particular)
NAME: DSAShufflingQueue.java
PURPOSE: to simulate a shuffling queue using arrays.
AUTHOR: Lachlan Murray (19769390)
LAST MOD:
*/

public class DSAShufflingQueue extends DSAQueue
{
    //Class fields
        //private Object[] queue;
    private int back;

    //Class constant
        //private static final int DEFAULT_CAPACITY = 100;

    /*
    SUBMODULE: Default Constructor
    IMPORTS: none
    EXPORTS: address of new DSAShufflingQueue object
    ASSERTION: queue is Object array of size DEFAULT_CAPACITY, and count is 0
    */
    public DSAShufflingQueue()
    {
        super();
            //queue = new Object[DEFAULT_CAPACITY];
        back = 0;
    }
    
    /*
    SUBMODULE: Alternate Constructor
    IMPORTS: maxCapacity (integer)
    EXPORTS: address of new DSAShufflingQueue object
    ASSERTION: queue is Object array of size maxCapacity, and count is 0
    */
    public DSAShufflingQueue(int maxCapacity) throws IllegalArgumentException
    {
        super(maxCapacity);

        if (maxCapacity >= 1)
        {
                //queue = new Object[maxCapacity];
            back = 0;
        }
        else
        {
            throw new IllegalArgumentException("Max Capacity of queue should be greater than 0!");
        }
    }

    //ACCESSORS
    public int getBack()
    {
        return back;
    }
    
    @Override
    public boolean isEmpty()
    {
        boolean empty = false;
        
        if (back == 0)
        {
            empty = true;
        }
        
        return empty;
    }
    
    @Override
    public boolean isFull()
    {   
        boolean full = false;
        
        if (back == queue.length)
        {
            full = true;
        }
        
        return full;
    }

    //MUTATORS
    /*
    SUBMODULE: enqueue
    IMPORTS: value (Object)
    EXPORTS: none
    ASSERTION: adds item to the queue
    */
    @Override
    public void enqueue(Object value)
    {
        if (isFull())
        {
            throw new IllegalArgumentException("Queue is full!");
        }
        else
        {
            queue[back] = value;    //Inserts element at rear of queue
            back += 1;
        }
    }

    /*
    SUBMODULE: dequeue
    IMPORTS: none 
    EXPORTS: frontVal (Object)
    ASSERTIONS: delete item from front of queue
    */
    @Override
    public Object dequeue()
    {
        Object frontVal = queue[0];

        if (isEmpty())
        {
            throw new IllegalArgumentException("Queue is empty!");
        }
        else
        {
            for (int i = 0; i < (back - 1); i++)
            {
                queue[i] = queue[i + 1];    //Shuffles all of the elements across by one
            }
            
            if (back < queue.length)
            {
                queue[back - 1] = 0;    //Stores the value of 0 at the back of queue
            }
        
            back -= 1;
        }

        return frontVal;
    }

    /*
    SUBMODULE: peek
    IMPORTS: none
    EXPORTS: frontVal (Object)
    ASSERTION: will give the front value of queue but not remove it
    */
    @Override
    public Object peek()
    {
        Object frontVal = queue[0];

        return frontVal;
    }

    /*
    SUBMODULE: displayQueue
    IMPORTS: none
    EXPORTS: none
    ASSERTION: will print contents of queue to user
    */
    @Override
    public void displayQueue()
    {
        if (isEmpty())
        {
            System.out.println("Queue is Empty!");
        }
        else
        {
            for (int i = 0; i < back; i++)
            {
                System.out.println(queue[i]);
            }
            System.out.println("------------------------");
        }
    }
}
