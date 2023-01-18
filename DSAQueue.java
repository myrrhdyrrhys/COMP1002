/*
NOTE: This class file was submitted by myself previously as part of DSA's weekly assessments (Week 3 in particular)
NAME: DSAQueue.java
PURPOSE: parent class to DSAShufflingQueue.java and DSACircularQueue.java
AUTHOR: Lachlan Murray (19769390)
LAST MOD:
*/

public abstract class DSAQueue
{
    //Class fields
    protected Object[] queue;   //Protected as the subclasses will need to access regularly

    //Class constant
    private static final int DEFAULT_CAPACITY = 100;

    /*
    SUBMODULE: Default Constructor
    IMPORTS: none
    EXPORTS: address of new DSAQueue object
    ASSERTION: queue is Object array of size DEFAULT_CAPACITY
    */
    public DSAQueue()
    {
        queue = new Object[DEFAULT_CAPACITY];
    }
    
    /*
    SUBMODULE: Alternate Constructor
    IMPORTS: maxCapacity (integer)
    EXPORTS: address of new DSAQueue object
    ASSERTION: queue is Object array of size maxCapacity
    */
    public DSAQueue(int maxCapacity) throws IllegalArgumentException
    {
        if (maxCapacity >= 1)
        {
            queue = new Object[maxCapacity];
        }
        else
        {
            throw new IllegalArgumentException("Max Capacity of queue should be greater than 0!");
        }
    }    

    //ACCESSORS
    public abstract boolean isEmpty();
    
    public abstract boolean isFull();

    //MUTATORS
    public abstract void enqueue(Object value);

    public abstract Object dequeue();

    public abstract Object peek();
    
    public abstract void displayQueue();
}    
