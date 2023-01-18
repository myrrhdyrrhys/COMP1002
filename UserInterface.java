/*
NOTE: This program was submitted by myself previously as a part of the unit Programming Design and Implementation's worksheet 7 submission requirements.
NAME: UserInterface.java
PURPOSE: To provide an interface for all future programs that require input from the user. Allows for checking user input values of a certain type against a certain range.
CREATOR: Lachlan Murray (19769390)
LAST MOD: 2:50PM 28/05/2020
*/

import java.util.*;

public class UserInterface
{
    /*
    NAME: userInput
    IMPORT: prompt (String), lower (int), upper (int)
    EXPORT: out (int)
    ASSERTION: Validates user input of type string against a range, then returns valid input
    */
    public static int userInput(String prompt, int lower, int upper)
    {
        Scanner sc = new Scanner(System.in);
        int out;
        String error = "Error, the input integer must be between " + lower + " and " + upper + " (inclusive).";  //Error message for invalid input int
        
        do
        {
            try
            {
                System.out.print(prompt);
                out = sc.nextInt();
            }
            catch(InputMismatchException e)
            {
                sc.next();  //clear buffer of incorrect input value
                out = lower - 1;    //set value to out of range of loop as not to exit
                displayError(error);    //Display error message
            }
        }while ((out < lower)||(out > upper));  //loops while input int is out of range
        
        return out; //returns valid int value
    }

    /*
    NAME: userInput
    IMPORT: prompt (String), lower (double), upper (double)
    EXPORT: out (double)
    ASSERTION: 
    */
    public static double userInput(String prompt, double lower, double upper)
    {
        Scanner sc = new Scanner(System.in);
        double out;
        String error = "Error, the input double must be between " + lower + " and " + upper + " (inclusive).";  //Error message for invalid input double
        
        do
        {
            try
            {
                System.out.print(prompt);
                out = sc.nextDouble();
            }
            catch(InputMismatchException e)
            {
                sc.next();  //clear buffer of incorrect input value
                out = lower - 1.0;    //set value to out of range of loop as not to exit
                displayError(error);    //Display error message
            }
        }while ((out < lower)||(out > upper));  //loops while input double is out of range
        
        return out; //returns valid double value
    }

    /*
    NAME: userInput
    IMPORT: prompt (String), lower (long), upper (long)
    EXPORT: out (long)
    ASSERTION: 
    */
    public static long userInput(String prompt, long lower, long upper)
    {
        Scanner sc = new Scanner(System.in);
        long out;
        String error = "Error, the input long must be between " + lower + " and " + upper + " (inclusive).";  //Error message for invalid input long
        
        do
        {
            try
            {
                System.out.print(prompt);
                out = sc.nextLong();
            }
            catch(InputMismatchException e)
            {
                sc.next();  //clear buffer of incorrect input value
                out = lower - 1;    //set value to out of range of loop as not to exit
                displayError(error);    //Display error message
            }
        }while ((out < lower)||(out > upper));  //loops while input long is out of range
        
        return out; //returns valid long value
    }

    /*
    NAME: userInput
    IMPORT: prompt (String), lower (float), upper (float)
    EXPORT: out (float)
    ASSERTION: 
    */
    public static float userInput(String prompt, float lower, float upper)
    {
        Scanner sc = new Scanner(System.in);
        float out;
        String error = "Error, the input float must be between " + lower + " and " + upper + " (inclusive).";  //Error message for invalid input float
        
        do
        {
            try
            {
                System.out.print(prompt);
                out = sc.nextFloat();
            }
            catch(InputMismatchException e)
            {
                sc.next();  //clear buffer of incorrect input value
                out = lower - 1;    //set value to out of range of loop as not to exit
                displayError(error);    //Display error message
            }
        }while ((out < lower)||(out > upper));  //loops while input float is out of range
        
        return out; //returns valid float value
    }

    /*
    NAME: userInput
    IMPORT: prompt (String), lower (char), upper (char)
    EXPORT: out (char)
    ASSERTION: 
    */
    public static char userInput(String prompt, char lower, char upper)
    {
        Scanner sc = new Scanner(System.in);
        char out;
        String error = "Error, the input char must be between " + lower + " and " + upper + " (inclusive).";  //Error message for invalid input char
        
        do
        {
            try
            {
                System.out.print(prompt);
                out = sc.next().charAt(0);
            }
            catch(InputMismatchException e)
            {
                sc.next();  //clear buffer of incorrect input value
                out = (char)((int)lower - 1);    //set value to out of range of loop as not to exit
                displayError(error);    //Display error message
            }
        }while (((int)out < (int)lower)||((int)out > (int)upper));  //loops while input char is out of range, checked using ascii values
        
        return out; //returns valid char value
    }

    /*
    NAME: userInputChar
    IMPORT: prompt (String), charOptOne (char), charOptTwo (char), charOptThree (char), charOptFour (char)
    EXPORT: out (char)
    ASSERTION: valid char is returned as out
    */
    public static char userInputChar(String prompt, char charOptOne, char charOptTwo, char charOptThree, char charOptFour)
    {
        Scanner sc = new Scanner(System.in);
        char out;
        String error = "Error. The input char must be " + charOptOne + " or " + charOptTwo + " or " + charOptThree + " or " + charOptFour;    //Error message

        do
        {
            try
            {
                System.out.print(prompt);  //Asks user for input char
                out = sc.next().charAt(0);    //Sets variable intEqualsOut to input value
            }
            catch(InputMismatchException e)
            {
                sc.next();    //clear buffer of incorrect input value
                out = 'z';  //set value to (hopefully) out of range of loop as not to exit
                displayError(error);    //display error message
            }
        }while ((out != charOptOne)&&(out != charOptTwo)&&(out != charOptThree)&&(out != charOptFour));   //loops while input is what it cannot be

        return out;    //returns char value to calling module after checking against range
    }

    /*
    NAME: userInput
    IMPORT: prompt (String)
    EXPORT: out (String)
    ASSERTION: 
    */
    public static String userInput(String prompt)
    {
        Scanner sc = new Scanner(System.in);
        String out;
        String error = "Error, the input must be a string. ";  //Error message for invalid input String
        
        do
        {
            try
            {
                System.out.print(prompt);
                out = sc.nextLine();
            }
            catch(IllegalArgumentException e)
            {
                sc.next();  //clear buffer of incorrect input value
                out = "";    //set value to out of range of loop as not to exit
                displayError(error);    //Display error message
            }
        }while (out.equals(""));  //loops while input string is out of range
        
        return out; //returns valid string
    }

    /*
    NAME: displayError
    IMPORT: error (String)
    EXPORT: none
    ASSERTION: displays the error string to the user
    */
    public static void displayError(String error)
    {
        System.out.println(error);        
    }
}
