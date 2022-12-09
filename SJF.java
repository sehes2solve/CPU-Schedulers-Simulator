import inter.Proccessable;

import java.util.*;

public class SJF implements Proccessable {
    public static String SJF(ArrayList<Integer> AT,ArrayList<Integer>  BT, ArrayList<Integer> t
            ,Integer[] TAT,Integer[] WT,ArrayList<Double> ATAT_AWT)
    {
        Comparator<Integer> cmpr = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) { return BT.get(o1).compareTo(BT.get(o2)); } };
        String order = "";
        ArrayList<Integer> arrived = new ArrayList<>();
        PriorityQueue<Integer> waiting = new PriorityQueue<>(cmpr);
        boolean done = false;int nxt = -1,tat,wt;
        for(int i = 0,finish = 0;!done && AT.size() != 0;i++)
        {
            arrived.clear();
            for(int j = 0;j < AT.size();j++)
                if(AT.get(j) == i)
                    arrived.add(j);
            for (int arr : arrived) waiting.add(arr);

            if((i == finish || finish == 0) && !waiting.isEmpty())
                t.add(i);
            while (i == finish || finish == 0)
            {
                if(!waiting.isEmpty())
                {
                    if(nxt != -1)
                    {
                        tat = i - AT.get(nxt);
                        wt = tat - BT.get(nxt);
                        TAT[nxt] = tat;
                        WT[nxt] = wt;
                    }
                    nxt = waiting.poll();
                    finish = i + BT.get(nxt);
                    order += "P" + (nxt + 1) + " ";

                }
                else
                {
                    if (i >= Collections.max(AT))
                    {
                        done = true;
                        t.add(i);

                        tat = i - AT.get(nxt);
                        wt = tat - BT.get(nxt);
                        TAT[nxt] = tat;
                        WT[nxt] = wt;
                        tat = 0;wt = 0;
                        for(int j = 0;j < AT.size();j++)
                        {
                            tat += TAT[j];
                            wt += WT[j];
                        }
                        ATAT_AWT.add((double)(tat)/AT.size());
                        ATAT_AWT.add((double)(wt)/AT.size());
                    } else if(i == finish) finish = 0;
                    break;
                }
            }

        }
        return order;
    }
    public void execute()
    {
        Scanner cin=new Scanner(System.in);
        System.out.println("Enter the number of processes");
        int n=cin.nextInt();
        Integer[] arr1 = new Integer[n];
        Integer[] arr2 = new Integer[n];
        System.out.println("Enter the arrival time and burst time for each process");
        for(int i=0 ; i<n ; i++){
            arr1[i]=cin.nextInt();
            arr2[i]=cin.nextInt();
        }
        ArrayList<Integer> timestamp = new ArrayList<>(), AT = new ArrayList<>(List.of(arr1))
                , BT = new ArrayList<>(List.of(arr2));
        Integer[] TAT =  new Integer[AT.size()],WT =  new Integer[AT.size()];
        ArrayList<Double> ATAT_AWT = new ArrayList<>();
        String order = SJF(AT,BT,timestamp,TAT,WT,ATAT_AWT);
        System.out.println(timestamp);
        System.out.println(order);
        for(int i = 0;i < AT.size();i++)
            System.out.print(TAT[i] + " ");
        System.out.println("");
        for(int i = 0;i < AT.size();i++)
            System.out.print(WT[i] + " ");
        System.out.println("");
        System.out.println(ATAT_AWT.get(0));
        System.out.println(ATAT_AWT.get(1));
    }
}
