import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RR {
    public ArrayList<Job> jobs;
    public ArrayList<boolean[]> result;

    public RR (ArrayList<Job> j) {
        jobs = new ArrayList<>();
        jobs.addAll(j);
        result = new ArrayList<>();
    }

    // runs the RR algorithm and displays the results
    public void run() {
        // timer
        int time = 0;
        // ready queue, process at the front of this queue will be run
        Queue<Job> readyQueue = new LinkedList<>();
        // jobs that still haven't finished running
        ArrayList<Job> remainingJobs = new ArrayList<>(jobs);
        // job that the CPU is currently running
        Job currRunningJob = null;

        // continue running until there are no more remaining jobs
        while (!remainingJobs.isEmpty()) {
            // array of the job running at the current time step
            // e.g. if we have 5 jobs and job D is running
            // currRunningJobs = [false, false, false, true, false]
            // this will be used to display the results of the RR algorithm
            boolean[] currRunningJobs = new boolean[jobs.size()];

            // get all jobs that arrive @ the current time
            for (Job job : jobs) {
                if (job.getArrivalTime() == time) {
                    readyQueue.add(job);
                }
            }

            // preempt the currently running job if needed
            if (remainingJobs.contains(currRunningJob)) {
                readyQueue.add(currRunningJob);
            }

            // get the job at the start of the queue and run it
            if (!readyQueue.isEmpty()) {
                currRunningJob = readyQueue.remove();
                for (int i = 0; i < jobs.size(); i++) {
                    currRunningJobs[i] = jobs.get(i).getJobId().equals(currRunningJob.getJobId());
                }
            }

            // add the currently running jobs to the result array
            result.add(currRunningJobs);

            // reduce the duration of the currently running job by 1
            currRunningJob.setDuration(currRunningJob.getDuration() - 1);

            // check if the currently running job is done running
            if (currRunningJob.getDuration() == 0) {
                remainingJobs.remove(currRunningJob);
            }

            // increment the current time step
            time++;
        }

        // display result of the RR algorithm
        System.out.println("Round Robin");

        for (Job j : jobs) {
            System.out.print(j.getJobId() + " ");
        }

        System.out.println();

        for (boolean[] r : result) {
            for (boolean b : r) {
                System.out.print(b ? "X " : "  ");
            }
            System.out.println();
        }

        System.out.println("DONE");

        // reset the jobs to their original state
        for (Job job : jobs) {
            job.resetDuration();
        }
    }
}
