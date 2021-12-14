import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // ArrayList of Job objects
    public static ArrayList<Job> jobs = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        // .txt file containing the job names, arrival times, and service times
        String fileName = args[0];

        // one of RR, SRT, FB, or ALL
        String algorithmName = args[1];

        // read in the jobs file and add the jobs to the jobs ArrayList
        Scanner scan = new Scanner(new File(fileName));
        while (scan.hasNext()) {
            String temp = scan.nextLine();
            if (!temp.equals("")) {
                String[] line = temp.split("\t");
                Job currJob = new Job(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                jobs.add(currJob);
            }
        }

        // run the algorithms
        if ("RR".equals(algorithmName)) {
            RR roundRobin = new RR(jobs);
            roundRobin.run();
        } else if ("SRT".equals(algorithmName)) {
            SRT shortestRemainingTime = new SRT(jobs);
            shortestRemainingTime.run();
        } else if ("FB".equals(algorithmName)) {
            FB feedback = new FB(jobs);
            feedback.run();
        } else if ("ALL".equals(algorithmName)) {
            RR roundRobin = new RR(jobs);
            roundRobin.run();
            SRT shortestRemainingTime = new SRT(jobs);
            shortestRemainingTime.run();
            FB feedback = new FB(jobs);
            feedback.run();
        }
    }
}
