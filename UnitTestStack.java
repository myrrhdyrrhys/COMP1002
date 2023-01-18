/*
NAME: UnitTestStack.java
PURPOSE: to test the performance of the DSAStack program
AUTHOR: Lachlan Murray (19769390)
LAST MOD: 2:20PM 22/10/2020
*/

public class UnitTestStack
{
    public static void main(String[] args)
    {
        try
        {
            //Create a new stack
            DSAStack s = new DSAStack();
            Object placeHolder;           
 
            //Insert elements onto the stack
            s.push(5.6);
            s.push(19);
            s.push("test");
            s.push(7.2);
            s.push(22);
    
            //Print elements of stack
            System.out.println("Displaying the contents of the stack after pushing on: 5.6, 19, 'test', 7.2, 22");
            s.displayStack();

            //((Dequeue)) two of the elements
            placeHolder = s.pop();
            placeHolder = s.pop();
            
            //Print elements of stack
            System.out.println("Displaying the contents of the stack once the two top-most values are removed:");
            s.displayStack();
        }   
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
