/**********************************************************
 *                                                        *
 *  CSCI 470/502        Assignment 6         Summer 2021  *
 *                                                        *
 *  Class Name:  Destination                              *
 *                                                        *
 *  Developer(s):  Leonart Jaos                           *
 *                                                        *
 *  Purpose:  The below class represents our object defini*
 *  tion of the input file. Members include the location, *
 *  miles, off season miles, upgrade miles, and the start *
 *  and end months of the offseason. Getters and setters  *
 *  for all members.                                      *
 *                                                        *
 **********************************************************/

public class Destination
{
    private String cty; // city of travel location
    private int mls; // normal miles required to travel
    private int osMls; // offseason miles required to travel
    private int adMls; // additional miles required to upgrade to first class
    private int strMth; // start month for offseason number
    private int endMth; // end month for offseason number

    // Setter for city of travel location
    public void setCty(String cty)
    {
        this.cty = cty;
    }
    // Getter for city of travel location
    public String getCty()
    {
        return cty;
    }
    // Setter for normal miles required to travel
    public void setMls(int mls)
    {
        this.mls = mls;
    }
    // Getter for normal miles required to travel
    public int getMls()
    {
        return mls;
    }
    // Setter for offseason miles required to travel
    public void setOsMls(int osMls)
    {
        this.osMls = osMls;
    }
    // Getter for offseason miles required to travel
    public int getOsMls()
    {
        return osMls;
    }
    // Setter for additional miles required to upgrade to first class
    public void setAdMls(int adMls)
    {
        this.adMls = adMls;
    }
    // Getter for additional miles required to upgrade to first class
    public int getAdMls()
    {
        return adMls;
    }
    // Setter for start month for offseason number
    public void setStrMth(int strMth)
    {
        this.strMth = strMth;
    }
    // Getter for start month for offseason number
    public int getStrMth()
    {
        return strMth;
    }
    // Setter for end month for offseason number
    public void setEndMth(int endMth)
    {
        this.endMth = endMth;
    }
    // Getter for end month for offseason number
    public int getEndMth()
    {
        return endMth;
    }
}
