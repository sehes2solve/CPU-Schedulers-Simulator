package PriorityScheduling;

import java.util.Comparator;

public class PriorityComparer implements Comparator<Process> {
	public int compare(Process First,Process second) {
		if(First.PriorityNumber>second.PriorityNumber) return 1;
		if(First.PriorityNumber<second.PriorityNumber) return -1;
		else return 0;
	}
}
