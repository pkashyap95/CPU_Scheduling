
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class reader {

    private String fileName;
    private int processCounter=0;
    private ArrayList <Process> processKeeper=null;
    private BufferedReader br;
    private String[] value;

    public reader(String name) {
        fileName = name;
    }

    public void open() throws IOException {
        File input = new File(fileName);
        br = new BufferedReader(new FileReader(input));
        read();
    }

    public void read() throws IOException {
        br.readLine();
        String line = "";
        while ((line = br.readLine()) != null) {
            value = line.split(",");
            Process prop=processMaker(value);
            setPro(prop);
        }
        sortArrival();
        br.close();
    }
    
    public Process processMaker(String[] trial){
        String pid= "P"+trial[0];
        int arrival= Integer.parseInt(trial[1]) ;
        int burst= Integer.parseInt(trial[2]) ;
        Process pro=new Process(pid,arrival,burst);
        return pro;
    }
    
    
    public void setPro(Process p1){
        if(processKeeper==null){
            processKeeper=new <Process> ArrayList();
        }
        processKeeper.add(processCounter, p1);
        processCounter++;
    }
    
    public ArrayList <Process> processList(){
        return processKeeper;
    }
    public void sortArrival(){
        for(int i=1; i<processKeeper.size();i++){
            Process temp1=processKeeper.get(i);
            int tempArr=temp1.getArrivalTime();
            int j=i;
            while(j>0 && processKeeper.get(j-1).getArrivalTime()>tempArr){
                Process temp2=processKeeper.get(j-1);
                processKeeper.set(j, temp2);
                j--;
            }
            processKeeper.set(j, temp1);
        }
    }
}

