import java.util.concurrent.ArrayBlockingQueue;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
public class Scheduler {
    public static void main(String [] args) throws FileNotFoundException{
        ArrayList <Process> please=null;
        String address=args[0];//Reading in the file or file directory 
        ///////////////////////////////////////////////////////////////////
        
        try{ //Reading the CSV file and storing it in the temporary ArrayList

            reader trial=new reader(address);

            trial.open();

            please=trial.processList();
        }
        catch(IOException e){
            System.out.println("No file");
        }
        ////////////////////////////////////////////////////////////////////////
        System.out.println("Process        BurstTime        ArrivalTime");//Displaying All the processes in the queue
        for(int j=0;j<please.size();j++){
            System.out.println(please.get(j).getPID()+"                "+ please.get(j).getBurst()+"               "+please.get(j).getArrivalTime());
        }
        ArrayBlockingQueue readyQ=new ArrayBlockingQueue(please.size());
        for(int i=0;i<please.size();i++){
            readyQ.add(please.get(i));
        }
        ////////////////////////////////////////////////////////////////////////
        Scanner in=new Scanner(System.in);
        int clock=0;
        Process current_cpu;
        System.out.print("Enter the Time Quantum: ");
        int n=in.nextInt();//Reading in the desired time quantum 
        int timeQuantum= n;
        int contextSwitch=-1;
        int cpu_idle_counter=0;
        int numProcess=readyQ.size();
        ArrayBlockingQueue finishedQ=new ArrayBlockingQueue(numProcess);
        ////////////////////////////////////////////////////////////////////////
        while(!readyQ.isEmpty()){
            current_cpu=(Process) readyQ.poll();
            System.out.println(" ");
            for (int cpuStartTime=clock;(clock-cpuStartTime)<timeQuantum;clock++){ //keeps running for the time quantum times
                if(current_cpu==null){ // Checking for number of CPU Idle times
                    cpu_idle_counter++;
                }
                System.out.println("At clock time = "+clock+" "+ current_cpu.getPID() +" is running");
                if(current_cpu.getBurst()==current_cpu.getServiceTime()){ //Process starts running for the first time
                }
                
                current_cpu.reduceServiceTime();//Decrease the service Time

                if(current_cpu.getServiceTime()==0){   //Checking if the process finished execution
                    current_cpu.addCompletionTime(clock);
                    finishedQ.add(current_cpu);   //Process is sent to completedQ
                    System.out.println(current_cpu.getPID()+" completed at clock time= "+current_cpu.getCompletionTime());
                    contextSwitch++; //Increments the context switch value
                    System.out.println("Context switch occurs");
                    clock++;
                    break;
                }
            }
            if(current_cpu.getServiceTime()>0){//Incomplete Processes gets added to the back to the end of the readyQ again
                readyQ.add(current_cpu);
                contextSwitch++; //Increments context switch value
                System.out.println("Context switch occurs");
            }
        }
        
        System.out.println("");
        System.out.println("The Time Quantum used was: " + n);
        
        //Calculate CPU Utilization 
        double cpuUtilization= ((clock-cpu_idle_counter)/(clock))*100;
        System.out.println("The CPU Utilization is " + cpuUtilization+ " % ");
        
        //Calculating Throughput
        double clockDou=clock;
        double size= finishedQ.size();
        double throughPut=size/clockDou;
        System.out.printf("The through put is "+ "%.4f ",throughPut);
        System.out.println();
        
        //Calculating Average Turnaround Time
        int totalTurnAroundTime=0;
        for(int i=0; i<numProcess;i++){
            Process temp1=(Process) finishedQ.poll();
            int turnAroundTime= temp1.getCompletionTime()-temp1.getArrivalTime();
            temp1.addTurnAroundTime(turnAroundTime);
            totalTurnAroundTime=turnAroundTime+totalTurnAroundTime;
            finishedQ.add(temp1);
        }
        double avgTAT= totalTurnAroundTime/size;
        System.out.println("The average turnaround time is: "+ avgTAT);
        
        //Average Waiting Time
        int totalWaitTime=0;
        for(int j=0; j<numProcess;j++){
            Process temp2= (Process)finishedQ.poll();
            int waitTime=temp2.getTurnAroundTime()-temp2.getBurst();
            totalWaitTime=totalWaitTime+waitTime;
        }
        double avgWaiting= totalWaitTime/size;
        System.out.println("The average waiting time is: "+ avgWaiting);
        
        System.out.println("The number of context switches: "+contextSwitch);
    }
}