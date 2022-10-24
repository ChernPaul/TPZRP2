package org.example.model;
import java.math.BigInteger;
import java.util.List;

public class ChordNode {

    private BigInteger position;
    private List<FingerRecord> fingersTable;

    public ChordNode(BigInteger position, List<FingerRecord> fingersTable) {
        this.position = position;
        this.fingersTable = fingersTable;
    }

    public ChordNode(BigInteger position) {
        this.position = position;
        this.fingersTable = null;
    }
    public BigInteger getPosition() {
        return position;
    }

    public List<FingerRecord> getFingersTable() {
        return fingersTable;
    }

    public void setPosition(BigInteger position) {
        this.position = position;
    }

    public void setFingersTable(List<FingerRecord> fingersTable) {
        this.fingersTable = fingersTable;
    }

    public void addFinger(FingerRecord finger) {
        fingersTable.add(finger);
    }


    @Override
    public String toString() {
        return "\nChordNode{" +
                "position=" + position +
                ", fingersTable=" + fingersTableString() +
                '}';
    }

    private String fingersTableString() {
        StringBuilder result = new StringBuilder();

        for (FingerRecord finger : fingersTable) {
            result.append("\n=============================================================================");
            result.append("\nStart = ").append(finger.getStart());
            result.append("\nInterval = ").append(finger.getInterval());
            result.append("\nSuccessor position = ").append(finger.getSuccessor().getPosition());
            result.append("\n=============================================================================");
        }
        return result.toString();
    }
}