package PriorityScheduling;

import inter.Proccessable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PriorityScheduling implements Proccessable {

	public void execute() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int ProcessNum;
		String temp;
		System.out.println("enter the number of processes:");
		temp=br.readLine();
		ProcessNum=Integer.parseInt(temp);
		ArrayList<Process> processArr=new ArrayList<Process>();
		for (int i = 0; i < ProcessNum; i++) {
			String name;
			int ArivalTime;
			int BurstTime;
			int PriorityNumber;
			System.out.println("enter process name:");
			name=br.readLine();
			System.out.println("enter arival time:");
			temp=br.readLine();
			ArivalTime=Integer.parseInt(temp);
			System.out.println("enter burst time:");
			temp=br.readLine();
			BurstTime=Integer.parseInt(temp);
			System.out.println("enter priority number:");
			temp=br.readLine();
			PriorityNumber=Integer.parseInt(temp);
			Process p=new Process(name,ArivalTime,BurstTime,PriorityNumber);
			processArr.add(p);
		}	
		Scheduler S=new Scheduler(processArr);
		S.start();
	}
}
