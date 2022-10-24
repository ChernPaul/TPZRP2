package org.example.model;
import java.math.BigInteger;

public class FingerInterval {
    private BigInteger start;
    private BigInteger end;

    public FingerInterval(BigInteger start, BigInteger end) {
        this.start = start;
        this.end = end;
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }

    public void setStart(BigInteger start) {
        this.start = start;
    }

    public void setEnd(BigInteger end) {
        this.end = end;
    }


    @Override
    public String toString() {
        return "FingerInterval{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    public boolean isNumberValueBetweenStartAndEnd(BigInteger number) {
        return number.compareTo(start) >= 0 && number.compareTo(end) <= 0;
    }

    public boolean isNumberInIntervalAllCases(BigInteger number) {
        //check if interval with start < end
        if (isNumberValueBetweenStartAndEnd(number) && !isInverted())
            return true;
//        if (start.compareTo(number) == 0 || end.compareTo(number) == 0) {
//            return true;
//        }
        return start.compareTo(end) >= 1 && ((number.compareTo(end) <=1) || (number.compareTo(start) >=1 ));
    }

    public boolean isInverted(){
        return start.compareTo(end) > 0;
    }
}