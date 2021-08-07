/**********************************************************
 *                                                        *
 *  CSCI 470/502        Assignment 6         Summer 2021  *
 *                                                        *
 *  Class Name:  MileageComparator
 *                                                        *
 *  Developer(s):  Leonart Jaos                           *
 *                                                        *
 *  Purpose: Comparator for the purpose of comparing desti*
 *  nation objects. Used for sort.                        *
 *                                                        *
 **********************************************************/

import java.util.Comparator;

public class MileageComparator implements Comparator<Destination>
{
    @Override
    public int compare(Destination d1, Destination d2)
    {
        return (d2.getMls() - d1.getMls());
    }
}
