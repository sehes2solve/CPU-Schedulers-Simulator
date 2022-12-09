import com.sun.jdi.ObjectCollectedException;
import inter.Proccessable;
import javafx.util.Pair;
import org.w3c.dom.ls.LSOutput;

import javax.print.attribute.IntegerSyntax;
import java.lang.reflect.Array;
import java.lang.Math;
import java.util.*;

import static java.lang.Math.max;

public class AG implements Proccessable {

    static class pair<T, C>
    {
        T first;
        C second;
        pair(){this.first = null; this.second = null;}

        pair(T first, C second)
        {
            this.first = first;
            this.second = second;
        }
    }
    static class Process
    {
        int ID;
        String name;
        String color;
        int arrivalTime;
        int burstTime;
        int fixedBurstTime;
        int priority;
        int processQuantum;
        int index;
        int _AG;

        //calculation
        int turnaroundTime;
        int watingTime;
        int AverageWaitingTime;
        int AverageTurnaroundTime;

        Process() {}
        Process(String name, String color, int arrivalTime, int burstTime, int fixedBurstTime, int priority, int processQuantum)
        {
            this.name = name;
            this.color = color;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.fixedBurstTime = fixedBurstTime;
            this.priority = priority;
            this.processQuantum = processQuantum;
            _AG = 0;
            turnaroundTime = 0;
            watingTime = 0;
            AverageWaitingTime = 0;
            AverageTurnaroundTime = 0;
        }
    }

    static int numOfProcesses;
    static int quantum;
    static int nextArrival;
    static int totalSimTime;
    static int totalTurnaroundTime;
    static int totalWatingTime;
    static int averageTurnaroundTime;
    static int averageWatingTime;

    static ArrayList<Process> processes = new ArrayList<>();
    static Queue<pair<Process, Integer>> queue = new LinkedList<>();
    static ArrayList<pair<Process, Integer>> output = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> quantumHistory = new ArrayList<>();
    static ArrayList<Process> dieList = new ArrayList<>();
    AG()
    {
        numOfProcesses = 0;
        quantum = 0;
        nextArrival = 0;
        totalSimTime = 0;
    }

    AG(int numOfProcesses, int quantum, int nextArrival, int totalSimTime)
    {
        this.numOfProcesses = numOfProcesses;
        this.quantum = quantum;
        this.nextArrival = nextArrival;
        this.totalSimTime = totalSimTime;
    }

    public static int quantumCalc(ArrayList<Process> arr)
    {
        double sum = 0;
        for(Process p : arr)
        {
            sum += p.processQuantum;
        }
        sum /= arr.size();              //Mean of Quantum
        sum = Math.ceil(sum/10);        //ceil (10% of the Mean)
        return (int)sum;
    }

    public static Process nextProcess(Queue<pair<Process, Integer>> queue, int timeNow)     //Extract the next process from the queue
    {
        int ii = 0;
        for(pair<Process, Integer> p : queue)
        {
            if (p.second == 1)                   /////////////////////////////////////////////////////////////////// else if
            {
                if(ii == 0) queue.poll();        //if the process is in the top of the queue, remove it from the queue
                else p.second = 0;              //if the process is inside the queue and for that I can't remove it, so just remark it with 0(as if it's removed from the queue)
                return p.first;
            }
            ii++;
        }
        return null;
    }

    public static Process nextProcess2(Queue<pair<Process, Integer>> queue, int timeNow, Process currentProcess)        //Extract the min Ag process from the queue
    {
        Process _min = new Process();
        Process minProcess = currentProcess;
        _min._AG = Integer.MAX_VALUE;
        int ii = 0, minIdx = -1;

        for(pair<Process, Integer> p : queue)
        {
            if(p.second == 1)                   /////////////////////////////////////////////////////////////////// else if
            {
                if(p.first._AG < _min._AG)
                {
                    _min = p.first;
                    minIdx = ii;
                    minProcess = p.first;
                }
            }
            ii++;
        }
        if(currentProcess._AG <=  minProcess._AG) return currentProcess;        //if the current process is the minAG
        if(minIdx == 0 && queue.size() > 0) queue.poll();   //if the process is in the top of the queue, remove it from the queue
        else                            //if the process is inside the queue and for that I can't remove it, so just remark it with 0(as if it's removed from the queue)
        {
            for(pair<Process, Integer> p : queue)       //loop on the queue again to search for the _minProcess to make it's integer 0
            {
                if(minProcess.name == p.first.name && p.second == 1)
                {
                    p.second = 0;
                    break;
                }
            }
        }
        return minProcess;
    }
    public static ArrayList<Integer> addQuantumHistory(ArrayList<Process> processes)
    {
        ArrayList<Integer> temp = new ArrayList<>();
        for(Process p : processes)
        {
            temp.add(p.processQuantum);
        }
        return temp;
    }
    public static void print()  //For debug
    {
        System.out.println("queue");
        for(pair<Process, Integer> p : queue)
        {
            System.out.println(p.first.name + " " + p.second);
        }
    }

    public void execute()
    {
        boolean ok = false;
        Scanner cin = new Scanner(System.in);

        System.out.println("Enter the number of processes");
        numOfProcesses =  cin.nextInt();
        System.out.println("Enter the Quantum");
        quantum = cin.nextInt();

        int totalSim = 0;
        System.out.println("Enter the information of the processes");
        for(int i = 0 ; i<numOfProcesses ; i++)
        {
            Scanner in = new Scanner(System.in);
            Process p = new Process();

            String name = in.next();           p.name = name;
            int burst = in.nextInt();               p.burstTime = burst;   p.fixedBurstTime = burst;  totalSim += burst;
            int arrival = in.nextInt();                p.arrivalTime = arrival;
            int priority = in.nextInt();               p.priority = priority;
            p.processQuantum = quantum;
            p.index = i;
            p._AG = p.priority + p.arrivalTime + p.burstTime;
            processes.add(p);
        }
        totalSim = max(totalSim, (processes.get(processes.size() - 1).arrivalTime + processes.get(processes.size() - 1).burstTime));
        int time = 0, index = 1, queueIDX = 1;
        Process runningProcess = processes.get(0);      //active the first process

        for (int i = 0 ; i<=totalSim ; i++)      //Core implementation
        {
            if(queueIDX < numOfProcesses && i >= processes.get(queueIDX).arrivalTime)    //if process arrive, add it to queue
            {
                System.out.println("Enter at " + i);
                pair<Process, Integer> temp2 = new pair(processes.get(queueIDX++), 1);
                queue.add(temp2);
                if(ok)
                {
                    runningProcess = nextProcess(queue, i);
                    ok = false;
                }
            }
            if(ok) continue;
            if(!ok && runningProcess.burstTime == 0)                                            //case 3 : The process finished it's job
            {
                time = 0;
                dieList.add(runningProcess);
                runningProcess.turnaroundTime = i - runningProcess.arrivalTime;
                runningProcess.watingTime = (runningProcess.turnaroundTime - runningProcess.fixedBurstTime);
                runningProcess = nextProcess(queue, i);
                if(runningProcess == null)
                {
                    System.out.println("System has no processes now");
                    System.out.println(" ");
                    ok = true;
                    continue;
                }
                if(i == totalSim) break;
            }

            ////////////////The 3 cases
            else if(time >= runningProcess.processQuantum  && time >= processes.get(index).arrivalTime)          //case 1 : The process used all it's quantum
            {
                if(runningProcess.burstTime > 0) //The process still have job to do, this condition I can ignore as the process will not enter here if it's finished
                {
                    pair temp3 = new pair(runningProcess, 1);
                    queue.add(temp3);                                            //step 1 : add the process to the end of the queue
                    runningProcess.processQuantum += quantumCalc(processes);    //step 2 : then increases it's Quantum time by (ceil(10% of the (mean of Quantum)))
                    runningProcess = nextProcess(queue, i);              //step 3 : next process in the queue will be active  //make it's function don't forget //if there is no process arrive yet, then this process will continue
                    if(runningProcess == null) System.out.println("erorr : unexpected null");
                }
                time = 0;                                                       //restart the timer for the new process
                index = runningProcess.index;                                   //update the index by the index of the new process
            }
            else if(queue.size() > 0 && time >= ((runningProcess.processQuantum + 1) /2))     //case2 : The process didn’t use all its quantum time
            {
                Process tempProcess = nextProcess2(queue, i, runningProcess);
                if(tempProcess.name != runningProcess.name)     //To handle the case if the running process is already the minAG process.
                {
                    pair<Process, Integer> temp4 = new pair(runningProcess, 1);
                    queue.add(temp4);
                    runningProcess.processQuantum += runningProcess.processQuantum - time;
                    runningProcess = tempProcess;
                    time = 0;
                    index = runningProcess.index;
                }
            }

            ////////////////////////////////////////////////Precess running
            System.out.println("running process : " + runningProcess.name);
            System.out.println("Process Quantum : " + runningProcess.processQuantum);
            System.out.println("i = " + i);
            System.out.println("time = " + time);
            System.out.println("ceil of quantum = " + (runningProcess.processQuantum + 1) /2);
            System.out.println(" ");

            ArrayList<Integer> temp = addQuantumHistory(processes);
            quantumHistory.add(temp);
            time++;
            runningProcess.burstTime--;
            if(output.size() == 0 || runningProcess.name != output.get(output.size() - 1).first.name)       //Add the process to the output whenever it's Enter or if this is the first process in the simultion
            {
                pair<Process, Integer> temp0 = new pair(runningProcess, i);
                output.add(temp0);
            }
        }
        for(Process p : processes)
        {
            totalTurnaroundTime += p.turnaroundTime;
            totalWatingTime += p.watingTime;
        }
        averageTurnaroundTime = totalTurnaroundTime / processes.size();
        averageWatingTime = totalWatingTime / processes.size();
        for(Process p : processes)
        {
            System.out.println("Turnaround time for process " + p.name + " = " + p.turnaroundTime);
            System.out.println("Wating time for process " + p.name + " = " + p.watingTime);
            System.out.println(" ");
        }
        System.out.println("Average turnaround time = " + averageTurnaroundTime);
        System.out.println("Average wating time = " + averageWatingTime);

        System.out.println("Quantum History : ");
        for(ArrayList<Integer> array : quantumHistory)
        {
            for(Integer integer : array)
            {
                System.out.print(integer + ", ");
            }
            System.out.println(" ");
        }
    }
}

/*
p1
17
0
4
p2
6
3
9
p3
10
4
3
p4
4
29
8
p4
4
35
8
*/