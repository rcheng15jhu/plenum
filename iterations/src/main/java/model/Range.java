package model;

public class Range {
    private int startTime;
    private int endTime;

    public Range(int startTime, int endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Range parseRange(String req) {
        String[] str = req.split(" ");
        int sTime;
        int eTime;
        try {
            sTime = Integer.parseInt(str[0]);
            eTime = Integer.parseInt(str[1]);
        } catch(Exception e) {
            System.out.println("Incorrect Range query");
            throw e;
        }
        return new Range(sTime, eTime);
    }

    @Override
    public String toString() {
        return "Range{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}