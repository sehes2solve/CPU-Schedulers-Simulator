package SR;

import java.util.Comparator;

public class PComparator implements Comparator<Pair> {
	 public int compare(Pair p1,Pair p2) {
		 return p1.Brus-p2.Brus;
	 }
}
