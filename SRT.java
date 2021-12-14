import java.util.ArrayList;

public class SRT {
    public ArrayList<Job> jobs;
    public ArrayList<boolean[]> result;

    public SRT (ArrayList<Job> j) {
        jobs = new ArrayList<>();
        jobs.addAll(j);
        result = new ArrayList<>();
    }

    public void run() {
        // timer
        int time = 0;
        // boolean array indicating if a job is active (i.e. has arrived and not done running)
        boolean[] activeJobs = new boolean[jobs.size()];
        // index of the job w/ the shortest remaining time
        int shortestJobIndex = -1;
        // jobs that still haven't finished running
        ArrayList<Job> remainingJobs = new ArrayList<>(jobs);
        // job that the CPU is currently running
        Job currRunningJob = null;

        while (!remainingJobs.isEmpty()) {
            // array of the job running at the current time step
            // e.g. if we have 5 jobs and job D is running
            // currRunningJobs = [false, false, false, true, false]
            // this will be used to display the results of the RR algorithm
            boolean[] currRunningJobs = new boolean[jobs.size()];

            // shortest remaining time across all active jobs
            int shortestRemainingTime = Integer.MAX_VALUE;

            // get all the jobs that are active at the current time
            for (int i = 0; i < activeJobs.length; i++) {
                Job currJob = jobs.get(i);
                if (currJob.getArrivalTime() == time) {
                    activeJobs[i] = true;
                }
                // also get the shortest remaining time across all active jobs
                // and the index of the job w/ the shortest remaining time
                if (activeJobs[i] && currJob.getDuration() < shortestRemainingTime) {
                    shortestJobIndex = i;
                    shortestRemainingTime = currJob.getDuration();
                }
            }

            // run the job w/ the shortest remaining time
            if (activeJobs[shortestJobIndex]) {
                currRunningJob = jobs.get(shortestJobIndex);
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
                activeJobs[shortestJobIndex] = false;
            }

            // increment the current time step
            time++;
        }

        // display result of the SRT algorithm
        System.out.println("Shortest Remaining Time");

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
