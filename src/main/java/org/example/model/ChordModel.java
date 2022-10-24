package org.example.model;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class ChordModel {

    private Map<BigInteger, ChordNode> model;
    private BigInteger modelSize;
    private int bitsCount;

    public ChordModel(int bitsCount, List<Integer> nodesPositions) {

        this.bitsCount = bitsCount;
        this.modelSize = BigInteger.TWO.pow(bitsCount);
        this.model = new TreeMap<>();

        for (Integer pos : nodesPositions) {
            addNode(pos.toString());
        }
    }

    public void addNode(String position) {

        BigInteger pos = new BigInteger(position);
        ChordNode newNode = new ChordNode(pos);
        newNode.setFingersTable(calculateNewFingersTable(newNode));
        model.put(pos, newNode);
        updateReferences();
    }

    public void removeNode(String position) {
        BigInteger pos = new BigInteger(position);
        model.remove(pos);
        updateReferences();
    }

    public ChordNode findNode(String startPos, String targetPos) {
        Set<BigInteger> nodesPositions = model.keySet();

        BigInteger pos = new BigInteger(targetPos);
        if (!nodesPositions.contains(pos)) {
            throw new RuntimeException("[ERROR] Target node doesn't exist");
        }

        if (!isPositionValid(new BigInteger(startPos), BigInteger.valueOf(model.size())) ||
                !isPositionValid(new BigInteger(targetPos),BigInteger.valueOf((long) Math.pow(2, model.size())))) {
            throw new RuntimeException("[ERROR] Positions not valid");
        }
        int nodesVisited = 0;

        BigInteger currentPos = new BigInteger(startPos);

        // check yourself
        if (startPos.equals(targetPos)) {
            System.out.printf("Transitions count: " + nodesVisited);
            return model.get(currentPos);
        }

        BigInteger targetPosition = new BigInteger(targetPos);

        while (currentPos.compareTo(targetPosition)!=0) {
            ChordNode currentNode = model.get(currentPos);
            System.out.printf(currentPos + " --->> ");
            ++nodesVisited;

            // BigInteger fixedPosition = currentPos;
            for (FingerRecord currentFinger : currentNode.getFingersTable()) {
                FingerInterval currentInterval = currentFinger.getInterval();

                if (currentInterval.isNumberInIntervalAllCases(targetPosition)) {
                    currentPos = currentFinger.getSuccessor().getPosition();
                    break;
                }
            }

//            if (fixedPosition.equals(currentPos)) {
//                int lastFingerIndex = currentNode.getFingersTable().size() - 2;
//                currentPos = currentNode.getFingersTable().get(lastFingerIndex).getSuccessor().getPosition();
         //  }
        }
        System.out.printf(currentPos + "\n");
        System.out.printf("Transitions count: " + nodesVisited);
        return model.get(currentPos);
    }

    private List<FingerRecord> calculateNewFingersTable(ChordNode node) {

        List<FingerRecord> fingers = new ArrayList<>();
        int fingersTableSize = bitsCount;

        for (int i = 1; i <= fingersTableSize; ++i) {
            // use function and create interval
            BigInteger fingerStart = calculateFingerStart(node, i);
            BigInteger nextFingerStart = calculateFingerStart(node, i + 1);
            FingerInterval interval = new FingerInterval(fingerStart, nextFingerStart);
            // consider 0 elements case
            ChordNode successor = model.isEmpty() ? node : calculateSuccessor(interval);
            // create an add new Finger Table
            FingerRecord currentFinger = new FingerRecord(fingerStart, interval, successor);
            fingers.add(currentFinger);
        }

        return fingers;
    }

    private ChordNode calculateSuccessor(FingerInterval interval) {

        // find nodes which active
        Set<BigInteger> positions = model.keySet();
        // going through key and find suc-r
        if(!interval.isInverted()){
        for (BigInteger currentPosition : positions) {
            if (interval.isNumberInIntervalAllCases(currentPosition)) {
                return model.get(currentPosition);
            }
        }
            BigInteger i=interval.getEnd();
            for (BigInteger pos : positions){
                if (pos.compareTo(i) > 0) {
                return model.get(pos);
                }
            }
        }
        if(interval.isInverted()) {
            BigInteger i=interval.getStart();
            for (BigInteger pos : positions){
                if (pos.compareTo(i) > 0) {
                    return model.get(pos);
                }
            }
        }

        BigInteger firstKey = positions.stream().toList().get(0);
        return model.get(firstKey);
    }
   // at any change of system
    private void updateReferences() {

        for (ChordNode currentNode : model.values()) {
            for (FingerRecord currentFinger : currentNode.getFingersTable()) {
                // without changes
                FingerInterval currentInterval = currentFinger.getInterval();
                // changes
                ChordNode newSuccessor = calculateSuccessor(currentInterval);
                currentFinger.setSuccessor(newSuccessor);

            }
        }
    }

    private BigInteger calculateFingerStart(ChordNode node, int fingerNumber) {
        BigInteger fingerStart = node.getPosition();
        fingerStart = fingerStart.add(BigInteger.TWO.pow(fingerNumber - 1));
        return fingerStart.mod(BigInteger.TWO.pow(bitsCount));
    }

    private static boolean isPositionValid(BigInteger position, BigInteger modelSize) {
        return position.compareTo(BigInteger.ZERO) >= 0 && position.compareTo(modelSize) < 0;
    }

    public void printSystem(String header) {
        System.out.println("======== " + header + " ========");
        System.out.println("=======================================\n");
        for (BigInteger key:model.keySet()) {
            System.out.println("Chord number: " + key);
            System.out.println("{");
            for(FingerRecord finger: model.get(key).getFingersTable()){
                System.out.println("=======================================");
                System.out.println("Finger Start: " + finger.getStart());
                System.out.println("Finger Interval: " + finger.getInterval());
                System.out.println("Finger Successor index: " + getKey(model,finger.getSuccessor()));
                System.out.println("=======================================");
            }
            System.out.println("}");
        }


    }
    public static <K, V> K getKey(Map<K, V> map, V value)
    {
        for (Map.Entry<K, V> entry: map.entrySet())
        {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }


    public static void main(String[] args) {
        FingerInterval inter = new FingerInterval(BigInteger.valueOf(4), BigInteger.ZERO);
        // 1. Create system
        inter.isNumberInIntervalAllCases(BigInteger.ZERO);
        ChordModel chordModel = new ChordModel(ClassWithTestData.INITIAL_SYSTEM_BIT_DIMENSION, ClassWithTestData.INITIAL_NODES_POSITION);
        chordModel.printSystem("#1. Created system");

        // 2. Add node
        chordModel.addNode(ClassWithTestData.NODE_POSITION_TO_ADD);
        chordModel.printSystem("#2. System with new node");

        // 3. Remove node
        chordModel.removeNode(ClassWithTestData.NODE_POSITION_TO_REMOVE);
        chordModel.printSystem("#3. System without one node");

        // 4. Search node
        chordModel.findNode(ClassWithTestData.NODE_POSITION_START_SEARCH, ClassWithTestData.NODE_POSITION_TO_SEARCH);
    }



}
