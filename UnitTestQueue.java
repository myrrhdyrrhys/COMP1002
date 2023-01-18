/*
NAME: UnitTestQueue.java
PURPOSE: to test the performance of the DSAShufflingQueue program
AUTHOR: Lachlan Murray (19769390)
LAST MOD: 3:00PM 22/08/2020
*/

public class UnitTestQueue
{
    public static void main(String[] args)
    {
        try
        {
            Object placeHolder;

            //SHUFFLING QUEUE
            //Create a new queue
            DSAQueue qOne = new DSAShufflingQueue();
            
            //Insert elements into the queue
            qOne.enqueue(5.6);
            qOne.enqueue(19);
            qOne.enqueue("test");
            qOne.enqueue(7.2);
            qOne.enqueue(22);
    
            //Print elements of the queue
            System.out.println("Displaying contents of queue after these values are added: 5.6, 19, 'test', 7.2, 22");
            qOne.displayQueue();
            
            //Dequeue three of the elements
            placeHolder = qOne.dequeue();
            placeHolder = qOne.dequeue();
            placeHolder = qOne.dequeue();
            
            //Print elements of queue
            System.out.println("Displaying contents of queue once the three front-most values are dequeued:");
            qOne.displayQueue();


            /*//CIRCULAR QUEUE
            //Create a new queue (cicular)
            DSAQueue qTwo = new DSACircularQueue();
            
            //Insert elements into the queue
            qTwo.enqueue("eleven");
            qTwo.enqueue(2);
            qTwo.enqueue(5);
            qTwo.enqueue(6.9);
            
            //Print elements of the queue
            qTwo.displayQueue();        

            //Dequeue two elements of the queue
            placeHolder = qTwo.dequeue();
            placeHolder = qTwo.dequeue();

            //Print elements of queue
            System.out.println("Once the two front-most values are dequeued:");
            qTwo.displayQueue();
            */
        }   
        catch(IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }
}
