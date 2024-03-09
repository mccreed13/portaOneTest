import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Exercise {
    private final String filename;
    private Long max = null;
    private Long min = null;
    private Double average = 0d;
    private Long median;

    private final ArrayList<Long> bufferMax = new ArrayList<>();
    private final ArrayList<Long> bufferMin = new ArrayList<>();
    private ArrayList<Long> maxOrder = new ArrayList<>();
    private ArrayList<Long> minOrder = new ArrayList<>();
    private final ArrayList<Long> allElements = new ArrayList<>();


    public Exercise(String filename) throws IOException {
        if(filename == null || filename.isEmpty()){
            throw new IllegalArgumentException("Path to file is empty");
        }
        this.filename = filename;
        readFileAndProcessNums(filename);
    }

    /**
     * Reads a file, parsing each line to number and do all operations with them.
     *
     * @param fname Path to file with numbers.
     */
    private void readFileAndProcessNums(String fname) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fname));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            try {
                Long l = Long.parseLong(line);
                doOperations(l);
            } catch (NumberFormatException e) {
                System.out.println("This is not a number: " + line);
            }
        }
        br.close();
        findAverageAndMedian();
    }

    /**
     * Does operations with one number.
     * @param l A number.
     */
    private void doOperations(Long l) {
        maxAndMin(l);
        allElements.add(l);
        addToOrder(l);
    }

    /**
     * Checks is current number a max or min from whole file.
     * @param l A number.
     */
    private void maxAndMin(Long l){
        if (max == null || l > max) {
            max = l;
        }
        if (min == null || l < min) {
            min = l;
        }
    }

    /**
     * Finds average and median number from whole file.
     */
    private void findAverageAndMedian() {
        double size = allElements.size();
        for (Long element : allElements) {
            average += element / size;
        }
        median = getMedian();
    }

    /**
     * Adds number to max or min order.
     * @param l A number.
     */
    private void addToOrder(Long l) {
        addToMaxOrder(l);
        addToMinOrder(l);
    }

    /**
     * Adds number to max order.
     * @param l A number.
     */
    private void addToMaxOrder(Long l) {
        if (bufferMax.isEmpty() || bufferMax.get(bufferMax.size() - 1) < l) {
            bufferMax.add(l);
        } else {
            if (bufferMax.size() >= maxOrder.size()) {
                maxOrder = new ArrayList<>(bufferMax);
                bufferMax.clear();
                bufferMax.add(l);
            }
        }
    }

    /**
     * Adds number to min order.
     * @param l A number.
     */
    private void addToMinOrder(Long l) {
        if (bufferMin.isEmpty() || bufferMin.get(bufferMin.size() - 1) > l) {
            bufferMin.add(l);
        } else {
            if (bufferMin.size() >= minOrder.size()) {
                minOrder = new ArrayList<>(bufferMin);
                bufferMin.clear();
                bufferMin.add(l);
            }
        }
    }

    /**
     * Gets median number from whole file.
     * @return Median.
     */
    private Long getMedian() {
        int size = allElements.size();
        Collections.sort(allElements);
        if (size % 2 == 0) {
            return (allElements.get(size / 2) + allElements.get(size / 2 - 1)) / 2;
        } else {
            return allElements.get(size / 2);
        }
    }

    @Override
    public String toString() {
        if(max == null || min == null){
            return "Operations did not do";
        }
        return "From file " + filename +
                "\nmax=" + max +
                "\nmin=" + min +
                "\naverage=" + average +
                "\nmedian=" + median +
                "\nmaxOrder=" + maxOrder +
                "\nminOrder=" + minOrder;
    }
}
