package org.example.model;

import java.math.BigInteger;

public class FingerRecord {
    private BigInteger start;
    private FingerInterval interval;
    private ChordNode successor;
    // private ChordNode predecessor;

    public FingerRecord(BigInteger start, FingerInterval interval, ChordNode successor) {
        this.start = start;
        this.interval = interval;
        this.successor = successor;
        // this.predecessor = predecessor;
    }

    public void setStart(BigInteger start) {
        this.start = start;
    }

    public void setInterval(FingerInterval interval) {
        this.interval = interval;
    }


    public void setSuccessor(ChordNode successor) {
        this.successor = successor;
    }
    public BigInteger getStart() {
        return start;
    }

    public FingerInterval getInterval() {
        return interval;
    }
    public ChordNode getSuccessor() {
        return successor;
    }

    @Override
    public String toString() {
        return "FingerRecord {" +
                "start=" + start +
                ", interval=" + interval +
                ", successor=" + successor +
                '}';
    }

    /* public void setPredecessor(ChordNode predecessor) {
            this.predecessor = predecessor;
        }
        */

//    public ChordNode getPredecessor() {
//        return predecessor;
//    }
}
