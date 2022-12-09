import PriorityScheduling.PriorityScheduling;
import SR.SRTF;
import inter.Proccessable;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scan=new Scanner(System.in);
        System.out.println("1- Non-Preemptive Shortest- Job First");
        System.out.println("2- Shortest- Remaining Time First");
        System.out.println("3- Non-preemptive Priority Scheduling");
        System.out.println("4- AG");
        int ch=scan.nextInt();
        Proccessable proccess;
        if(ch==1){
            proccess=new SJF();
        }else if(ch==2){
            proccess=new SRTF();
        }else if(ch==3){
            proccess=new PriorityScheduling();
        }else{
            proccess=new AG();
        }
        proccess.execute();
    }
}
