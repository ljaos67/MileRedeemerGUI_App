/**********************************************************
 *                                                        *
 *  CSCI 470/502        Assignment 6         Summer 2021  *
 *                                                        *
 *  Class Name:  MileRedeemer                             *
 *                                                        *
 *  Developer(s):  Leonart Jaos                           *
 *                                                        *
 *  Purpose: MileRedeemer parses user input file and initi*
 *  alizes destination arrayList members in ReadDestinatio*
 *  ns. getCityNames parses the destination arraylist city*
 *  names into a string array. RedeemMiles takes user inpu*
 *  t miles and month of departure to calculate best utili*
 *  zation of Frequent Flyer Miles. The location and class*
 *  are stored into an array, and if remaining miles are  *
 *  leftover, flight classes are evaluated for upgrade.   *
 *  Remaining miles after utilization are stored as a priv*
 *  ate member.                                           *
 *                                                        *
 **********************************************************/

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class MileRedeemer
{
    private Destination destination; // destination object for storage into arrayList
    private ArrayList<Destination> Arr = new ArrayList<Destination>(); //ArrayList declaration for records
    private int rMls; // remaining miles

    // Parses user input and stores members into Destination ArrayList
    // Sorts results by regular miles required for flight
    public void readDestinations(Scanner fileName) throws IOException
    {
        String destinationRecord;
        String[] subStrings; // used to split input from record

        // parses through record
        while((fileName.hasNext()))
        {
            destination = new Destination();
            destinationRecord = fileName.nextLine();

            // splits record by semicolon
            subStrings = destinationRecord.split( ";",5);

            // set destination members based off parsed array
            destination.setCty(subStrings[0]);
            destination.setMls(Integer.parseInt(subStrings[1]));
            destination.setOsMls(Integer.parseInt(subStrings[2]));
            destination.setAdMls(Integer.parseInt(subStrings[3]));

            // parse by hyphem for start and end month members
            subStrings = subStrings[4].split("-",2);
            destination.setStrMth(Integer.parseInt(subStrings[0]));
            destination.setEndMth(Integer.parseInt(subStrings[1]));

            // add destination object into Desination ArrayList
            Arr.add(destination);
        }

        // sorts arraylist
        MileageComparator comp = new MileageComparator();
        Collections.sort(Arr, comp);
    }

    // retrieved location names from destination ArrayList
    public String[] getCityNames()
    {
        ArrayList<String> cNames = new ArrayList<String>();
        for(int i = 0; i < Arr.size(); i++)
        {
            cNames.add(Arr.get(i).getCty());
        }

        // sorts by ascending order
        Collections.sort(cNames);

        // arraylist to array
        String[] nArray = (String[]) cNames.toArray(new String[cNames.size()]);
        return nArray;
    }

    // takes user input for flyer miles and departure month
    // calculates best miles utililization
    public String[] redeemMiles(int miles, int month)
    {
        // empty array and array with enough space for city name and its class type
        String[] empty = {};
        String[] cNames = new String[(2 * Arr.size())];

        // used for parsing through city name and class type array and for
        // when miles are insufficient for redemption
        int a = 0;
        int emptyCount = 0;

        // Parses through Destination ArrayList, starts with furthest locations for best value
        for(int i = 0; i < Arr.size(); i++)
        {
            // if miles are enough and elgible for offseason miles
            // subtract miles and assign class
            if(Arr.get(i).getOsMls() <= miles && (month >= Arr.get(i).getStrMth() && month <= Arr.get(i).getEndMth()))
            {
                miles = miles - Arr.get(i).getOsMls();
                cNames[a] = Arr.get(i).getCty();
                cNames[a+1] = "Economy";
                a = a + 2;
            }
            // if miles are enough, subtract miles and assign class
            else if(Arr.get(i).getMls() <= miles)
            {
                miles = miles - Arr.get(i).getMls();
                cNames[a] = Arr.get(i).getCty();
                cNames[a+1] = "Economy";
                a = a + 2;
            }
            // insufficient miles
            else
            {
                emptyCount++;
                cNames[a] = Arr.get(i).getCty();
                cNames[a+1] = "Not eligible";
                a = a + 2;
            }
        }

        // set instance variable for remaining miles
        rMls = miles;

        // upgrades miles of available flights if enough miles
        // changes class designation
        a = 0;
        for(int i = 0; i < Arr.size(); i++)
        {
            if(((rMls >= Arr.get(i).getAdMls()) && (cNames[a+1].equals("Economy"))))
            {
                rMls = rMls - Arr.get(i).getAdMls();
                cNames[a+1] = "First";
                a = a + 2;
            }
            else
            {
                a = a + 2;
            }
        }
        if(emptyCount == Arr.size())
            return empty;
        else
            return cNames;
    }
    // returns remaining miles
    public int getRmls()
    {
        return rMls;
    }
    public ArrayList<Destination> getArrList(){return Arr;}

}
