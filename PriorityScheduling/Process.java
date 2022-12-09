package PriorityScheduling;

public class Process {
	public String ProcessName;
	public int ArivalTime;
	public int BurstTime;
	public int PriorityNumber;
	public int WaitingTime=0;
	public int TurnaroundTime=0;
	public int completionTime;

	public Process(String name,int ArivalTime,int BurstTime,int PriorityNumber){
		this.ArivalTime=ArivalTime;
		this.BurstTime=BurstTime;
		this.PriorityNumber=PriorityNumber;
		this.ProcessName=name;
	}
	public String getname() {
		return ProcessName;
	}
	public int getArivalTime() {
		return ArivalTime;
	}
	public void Age() {//this should solve the starvation problem but it doesn't exist
									//since i don't keep on adding procceses its a list that starts and ends 
									// so I am at a loss on how to solve a problem that doesn't exist
									// ask people and see about it
									// Iam thinking about doing this after any process executes improving all that is still in the queue.
		if(this.PriorityNumber>0) this.PriorityNumber--;
		
	}
	public void execute(Scheduler a) {
		a.clock=a.clock+BurstTime;
		this.completionTime=a.clock;
		this.TurnaroundTime=completionTime-ArivalTime;
		this.WaitingTime=TurnaroundTime-BurstTime;
	}
	public int getwaitingtime() {
		return this.WaitingTime;
	}
	public int getturnaroundtime() {
		return this.TurnaroundTime;
	}
}
