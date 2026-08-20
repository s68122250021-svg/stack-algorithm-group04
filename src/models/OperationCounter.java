package models;
 
public class OperationCounter {
    private long pushCount;
    private long popCount;
    private long comparisonCount;
    private long loopCount;

    public void incrementPush() {
        pushCount++;
    }
 
    public void incrementPop() {
        popCount++;
    }
 
    public void incrementComparison() {
        comparisonCount++;
    }

    public void incrementLoop() {
        loopCount++;
    }
 
    public long getPushCount() {
        return pushCount;
    }
 
    public long getPopCount() {
        return popCount;
    }
 
    public long getComparisonCount() {
        return comparisonCount;
    }

    public long getLoopCount() {
        return loopCount;
    }
 
    @Override
    public String toString() {
        return "Push=" + pushCount + ", Pop=" + popCount + ", Comparisons=" + comparisonCount + ", Loops: " + loopCount;
    }
}
