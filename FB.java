import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FB {
    public ArrayList<Job> jobs;
    public ArrayList<boolean[]> result;

    public FB (ArrayList<Job> j) {
        jobs = new ArrayList<>();
        jobs.addAll(j);
        result = new ArrayList<>();
    }

    public void run() {
        // timer
        int time = 0;
        // boolean array indicating if a job is active (i.e. has arrived and not done running)
        boolean[] activeJobs = new boolean[jobs.size()];
        // index of the job that the CPU is currently running
        int currJobIndex = -1;
        // index of the job that the CPU was previously running
        int prevJobIndex = -1;
        // queues ordered by priority
        Queue<Integer> queue1 = new LinkedList<>();
        Queue<Integer> queue2 = new LinkedList<>();
        Queue<Integer> queue3 = new LinkedList<>();
        // the current queue
        int currQueue = -1;
        // boolean indicating if a job arrived
        boolean jobArrived = false;
        // jobs that still haven't finished running
        ArrayList<Job> remainingJobs = new ArrayList<>(jobs);
        // job that the CPU is currently running
        Job currRunningJob;

        while (!remainingJobs.isEmpty()) {
            // array of the job running at the current time step
            // e.g. if we have 5 jobs and job D is running
            // currRunningJobs = [false, false, false, true, false]
            // this will be used to display the results of the FB algorithm
            boolean[] currRunningJobs = new boolean[jobs.size()];

            // add all freshly arrived jobs to queue 1
            for (int i = 0; i < activeJobs.length; i++) {
                Job currJob = jobs.get(i);
                if (currJob.getArrivalTime() == time) {
                    activeJobs[i] = true;
                    queue1.add(i);
                    jobArrived = true;
                }
            }

            // move the previous job to a lower priority queue
            if (jobArrived) {
                if (currQueue == 1) {
                    queue2.add(prevJobIndex);
                } else if (currQueue == 2 || currQueue == 3) {
                    queue3.add(prevJobIndex);
                }
            } else if (queue1.isEmpty() && queue2.isEmpty() && queue3.isEmpty()) {
                currJobIndex = prevJobIndex;
            }

            // get the job to run
            if (!queue1.isEmpty()) {
                currJobIndex = queue1.remove();
                currQueue = 1;
            } else if (!queue2.isEmpty()) {
                currJobIndex = queue2.remove();
                currQueue = 2;
            } else if (!queue3.isEmpty()) {
                currJobIndex = queue3.remove();
                currQueue = 3;
            }
            currRunningJob = jobs.get(currJobIndex);
            
            // run the selected job and add the currently running jobs to the result array
            if (activeJobs[currJobIndex]) {
                for (int i = 0; i < jobs.size(); i++) {
                    currRunningJobs[i] = jobs.get(i).getJobId().equals(currRunningJob.getJobId());
                }
                result.add(currRunningJobs);
            } else if (!activeJobs[currJobIndex] && queue1.isEmpty() && queue2.isEmpty() && queue3.isEmpty()) {
                result.add(currRunningJobs);
            }

            // reduce the duration of the currently running job by 1
            currRunningJob.setDuration(currRunningJob.getDuration() - 1);

            // check if the currently running job is done running
            if (currRunningJob.getDuration() == 0) {
                remainingJobs.remove(currRunningJob);
                activeJobs[currJobIndex] = false;
            }

            // after we finish running the current job move it to a lower priority queue
            if (activeJobs[currJobIndex]) {
                if (queue1.isEmpty() && queue2.isEmpty() && queue3.isEmpty()) {
                    prevJobIndex = currJobIndex;
                } else {
                    if (currQueue == 1) {
                        queue2.add(currJobIndex);
                    } else {
                        queue3.add(currJobIndex);
                    }
                }
            }

            // increment the current time step and reset jobArrived
            time++;
            jobArrived = false;
        }

        // display result of the FB algorithm
        System.out.println("Feedback");

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
