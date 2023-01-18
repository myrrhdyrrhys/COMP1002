/*
NOTE: This test harness was submitted by myself previously as part of the unit Programming Design and Implementation's Worksheet 7 submission requirements.
NAME: UnitTestUserInterface.java
PURPOSE: To test the functionality of the UserInterface class and its components
AUTHOR: Lachlan Murray (19769390)
LAST MOD: 4:15PM 7/05/2020
*/

import java.util.*;

public class UnitTestUserInterface
{
    public static void main(String[] args)
    {
        try
        {
            //Input int
            System.out.println("Input int test:");
            int intOut = UserInterface.userInput("Please input an integer between 0 and 10: ", 0, 10);
            System.out.println(intOut + " is between 0 and 10 (inclusive).");

            //Input double
            System.out.println("Input double test:");
            double doubleOut = UserInterface.userInput("Please input a real value between 2.2 and 13.4555: ", 2.2, 13.455);
            System.out.println(doubleOut + " is between 2.2 and 13.4555 (inclusive).");

            //Input long
            System.out.println("Input long test:");
            long longOut = UserInterface.userInput("Please input a long number between 0 and 500000000: ", 0, 500000000);
            System.out.println(longOut + " is between 0 and 5000000000 (inclusive).");

            //Input float
            System.out.println("Input float test:");
            float floatOut = UserInterface.userInput("Please input a real float between 0 and 6.5: ", 0.0f, 6.5f);
            System.out.println(floatOut + " is between 0 and 6.5 (inclusive).");

            //Input char
            System.out.println("Input char test:");
            char charOut = UserInterface.userInput("Please input a character between a and z: ", 'a', 'z');
            System.out.println(charOut + " is between a and z (inclusive).");

            //Input String
            System.out.println("Input String test:");
            String stringOut = UserInterface.userInput("Please input String that is not empty: ");
            System.out.println(stringOut + " is not nothing.");
        
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
