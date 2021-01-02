package model;

public class Range {
    private int startTime;
    private int endTime;

    public Range(int startTime, int endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Range parseRange(String req) {
        int sTime;
        int eTime;
        try {
            String[] str = req.split(" ");
            sTime = Integer.parseInt(str[0]);
            eTime = Integer.parseInt(str[1]);
        } catch(Exception e) {
            System.out.println("Empty or incorrect Range query");
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