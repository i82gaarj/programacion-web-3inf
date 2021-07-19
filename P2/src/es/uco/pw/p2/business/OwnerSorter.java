package es.uco.pw.p2.business;

import java.util.Comparator;

public class OwnerSorter implements Comparator<Ad> 
{
    @Override
    public int compare(Ad a1, Ad a2) {
    	UserDTO owner2 = a2.getOwner();
    	String name2 = owner2.getFirstname() + " " + owner2.getLastname();
    	UserDTO owner1 = a1.getOwner();
    	String name1 = owner1.getFirstname() + " " + owner1.getLastname();
        return name1.compareToIgnoreCase(name2);
    }
}