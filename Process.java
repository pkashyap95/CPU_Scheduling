public class Process {
    
    private String pid;
    private int arrivalTime;
    private int burst;
    private int contextSwitch;
    private int completeTime;
    private int turnaroundTime;
    private int serviceTime;
    
    public Process(String processId, int arriveTime, int burstCycle){
        pid=processId;
        arrivalTime=arriveTime;
        burst=burstCycle;
        contextSwitch=0;
        serviceTime=burstCycle;
    }
    /////////////////////////////////////////////////////////////////////
    public int getServiceTime(){
        return serviceTime;
    }
    
    public String getPID(){
        return pid;
    }
    
    public int getArrivalTime(){
        return arrivalTime;
    }
    
    public int getBurst(){
        return burst;
    }
    
    public int getTurnAroundTime(){
        return turnaroundTime;
    }
    
    public int getCompletionTime(){
        return completeTime;
    }
    ////////////////////////////////////////////////////////
    public void context(){
        contextSwitch++;
    }
    
    public void reduceServiceTime(){
        serviceTime--;
    }
    
    public void addCompletionTime(int time){
        completeTime=time;
    }
    
    public void addTurnAroundTime(int time){
        turnaroundTime=time;
    }
}
