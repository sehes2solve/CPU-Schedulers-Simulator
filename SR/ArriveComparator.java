package SR;

import java.util.Comparator;

public class ArriveComparator implements Comparator<Pair> {
	 public int compare(Pair p1,Pair p2) {
		 return p1.arrive-p2.arrive;
	 }
}
