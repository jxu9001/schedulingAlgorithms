Included Files:
Main.java (reads in the jobs from a .txt file and runs the specified algorithm(s))
Job.java (class representing Job objects)
RR.java (runs and prints the results of the Round Robin scheduling algorithm (quantum = 1))
SRT.java (runs and prints the results of the Shortest Remaining Time scheduling algorithm)
FB.java (runs and prints the results of the Feedback scheduling algorithm (3 queues, all with quantum = 1))

How to compile and run the files:
1. navigate to the directory containing the .java files and the .txt file(s)
2. type the following into the terminal (no quotes)
3. "javac *.java"
4. "java Main <fileName> <algorithmName>" where algorithmName = RR, SRT, FB, or ALL (run all 3 scheduling algorithms)
