package PriorityScheduling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

class Scheduler {
	int clock=0;
	PriorityQueue<Process> Pqueue=new PriorityQueue<>(3,new PriorityComparer());
	ArrayList<Process> processArr=new ArrayList<>();
	ArrayList<Process> Processes=new ArrayList<>();
	
		public Scheduler(ArrayList<Process> processArr) {
			this.processArr=processArr;
		}
	
		public void start() throws IOException {
			while(processArr.size()!=0||Pqueue.size()!=0) {
				for (Process P : processArr) {
					if(P.getArivalTime()<=clock) {
						Pqueue.add(P);
					}
				}
				processArr.removeAll(Pqueue);
				if(Pqueue.size()!=0) {
						Process P=Pqueue.peek();
						P.execute(this);						
						Processes.add(P);
						Pqueue.poll();
						for (Process process : Pqueue) {
							process.Age();
							System.out.println(process.PriorityNumber);
							
						}
					}
				else {
					 clock++;
					 }
			}
			for (Process P : Processes) {
				System.out.println(P.getname()+ " "+P.getArivalTime() +" "+P.getwaitingtime()+" "+P.getturnaroundtime());
				
			}
			System.out.print("\n");
			getAvrageWaitingTime(Processes);
			getAvrageTurnaroundTime(Processes);
			System.out.println("----------------------------------------\n\n");
	}
		public void getAvrageWaitingTime(ArrayList<Process> P) {
			double totalWaitingTime=0;
			for (Process process : P) {
				totalWaitingTime+=process.getwaitingtime();
			}
			System.out.println("average waiting time : "+(double)totalWaitingTime/P.size());
		}
		public void getAvrageTurnaroundTime(ArrayList<Process> P) {
			double totalTurnaroundTime=0;
			for (Process process : P) {
				totalTurnaroundTime+=process.getturnaroundtime();
			}
			System.out.println("average waiting time : "+(double)totalTurnaroundTime/P.size());
		}
}

