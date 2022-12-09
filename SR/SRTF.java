package SR;

import inter.Proccessable;

import java.util.ArrayList;
import java.util.Scanner;

public class SRTF implements Proccessable {

	public void execute() {
		Scanner scan=new Scanner(System.in);
		int n= scan.nextInt();
		int c=scan.nextInt();
		ArrayList<Pair> x= new ArrayList<>();
		ArrayList<Integer> brus= new ArrayList<>();
		for (int i = 0;i<n; i++) {
			Pair p= new Pair(scan.next(),scan.nextInt(),scan.nextInt());
			x.add(p);
		}
		x.sort(new ArriveComparator());
		for (int i = 0;i<n; i++) {
			brus.add(x.get(i).Brus);
		}
		ArrayList<Pair> z=new ArrayList<Pair>();
		Pair pp=null;
		n=0;
		Boolean b=false;
		for (int i = 0;; i++) {
			for (int j = 0; j < x.size(); j++) {
				if(i==x.get(j).arrive)
					z.add(x.get(j));
			}
			z.sort(new PComparator());
			if(n==x.size())
				break;
			Pair p=z.get(0);
			if((p!=pp&&pp!=null)&&!b&&c!=0||i==0) {
				b=true;
				for (int j = 0; j < z.size(); j++) {
					x.get(x.indexOf(z.get(j))).w8+=c;
				}
				for (int j = 0; j < c; j++) {
					if(j!=0) {
						i++;
						for (int k = 0; k < x.size(); k++) {
							if(i==x.get(k).arrive)
								z.add(x.get(k));
						}
						z.sort(new PComparator());
						if(n==x.size())
							break;
						p=z.get(0);
					}
					System.out.print(i+":"+'c'+" ");
					pp=p;
				}
				continue;
			}
			b=false;
			p.Brus--;
			if(z.get(0).Brus==0) {
				z.remove(0);
				n++;
			}
			for (int j = 0; j < z.size(); j++) {
				if(z.get(j)!=p)
					x.get(x.indexOf(z.get(j))).w8++;
			}
			System.out.print(i+":"+p.name+" ");
			pp=p;
		}
//		for (int i = 0; i < x.size(); i++) {
//			x.get(i).w8+=c;
//		}
		for (int i = 0; i < x.size(); i++) {
			x.get(i).turnAround=brus.get(i)+x.get(i).w8;
		}
		System.out.println();
		int avgW8=0,avgTA=0;
		for (int i = 0; i < x.size(); i++) {
			System.out.println(x.get(i).name+" waiting time is: "+x.get(i).w8); avgW8 += x.get(i).w8;
			System.out.println(x.get(i).name+" turn around time is: "+x.get(i).turnAround); avgTA += x.get(i).turnAround;
		}
		System.out.println("Avarage waiting time is: "+(float)avgW8/n);
		System.out.println("Avarage turn around time is: "+(float)avgTA/n);
	}

}
/*
4
2
P1 0 7
P2 2 4
P3 4 1
P4 5 4
4
0
P1 0 17
P2 3 6
P3 4 10
P4 29 4
5
1
P1 0 4
P2 1 8
P3 3 2
P4 10 6
P5 12 5
 */