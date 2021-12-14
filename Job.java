public class Job {
    private String jobID;
    private int arrivalTime;
    private int newDuration;
    private int initialDuration;

    public Job (String jobID, int arrivalTime, int duration) {
        this.jobID = jobID;
        this.arrivalTime = arrivalTime;
        initialDuration = duration;
        newDuration = duration;
    }

    public String getJobId() {
        return jobID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getDuration() {
        return newDuration;
    }

    public void setDuration(int dur) {
        if (newDuration > 0) {
            newDuration = dur;
        } else newDuration = 0;
    }

    public void resetDuration() {
        newDuration = initialDuration;
    }
}
