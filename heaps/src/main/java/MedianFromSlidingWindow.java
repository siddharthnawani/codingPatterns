import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class MedianFromSlidingWindow {

    public static void main(String[] args) {
        int [][]arr = {{3, 1, 2, -1, 0, 5, 8}, {1, 2}, {4, 7, 2, 21}, {22, 23, 24, 56, 76, 43, 121 ,1 ,2 ,0 ,0 ,2 ,3 ,5}, {1, 1, 1, 1, 1}};
        int[] k = {4, 1, 2, 5, 2};
        for(int i=0; i<k.length; i++){
            System.out.print(i+1);
            System.out.println(".\tInput array = " + Arrays.toString(arr[i]) + ", k = " + k[i]);
            double[] output = medianSlidingWindow(arr[i], k[i]);
            System.out.println("\tMedians = " + Arrays.toString(output));

        }
    }

    private static PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    private static PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

    public static double[] medianSlidingWindow_2(int[] nums, int k) {

        double[] median = new double[nums.length - k + 1];
        for(int i=0; i < nums.length; i++){
            add(nums[i]);
            // When the index reaches size of k, we can find the median and remove the first element in the window
            if (i + 1 >= k) {
                median[i-k+1] = findMedian();
                remove(nums[i-k+1]);
            }
        }
        return median;
    }

    // For odd number of elements, top most element in maxHeap is the median of the current window,
    // else mean of maxHeap top & minHeap top represents the median
    private static double findMedian(){
        return maxHeap.size() > minHeap.size() ? maxHeap.peek() : minHeap.peek() / 2.0 + maxHeap.peek() / 2.0;
    }

    // This method adds the next element in the sliding window in the appropriate heap and rebalances the heaps
    private static void add(int num){
        if (maxHeap.size() == 0 || maxHeap.peek() >= num)
            maxHeap.add(num);
        else minHeap.add(num);
        rebalanceHeaps();
    }

    // This method removes the first element in the sliding window from the appropriate heap and rebalances the heaps
    private static void remove(int num){
        if (num > maxHeap.peek())
            minHeap.remove(num);
        else maxHeap.remove(num);
        rebalanceHeaps();
    }

    // This method keeps the height of the 2 heaps same
    private static void rebalanceHeaps(){
        if (maxHeap.size() == minHeap.size())
            return;
        if (maxHeap.size() > minHeap.size() + 1)
            minHeap.add(maxHeap.poll());
        else if (maxHeap.size() < minHeap.size())
            maxHeap.add(minHeap.poll());
    }


    public static double[] medianSlidingWindow(int[] nums, int k) {
        // To store the medians
        List<Double> medians = new ArrayList<Double>();

        // To keep track of the numbers that need to be removed from the heaps
        HashMap<Integer, Integer> outgoingNum = new HashMap<>();

        // Max heap
        PriorityQueue<Integer> smallList = new PriorityQueue<>(Collections.reverseOrder());

        // Min heap
        PriorityQueue<Integer> largeList = new PriorityQueue<>();

        // Initialize the max heap
        for (int i = 0; i < k; i++)
            smallList.offer(nums[i]);

        // Transfer the top 50% of the numbers from max heap to min heap
        for (int i = 0; i < k / 2; i++)
            largeList.offer(smallList.poll());

        // Variable to keep the heaps balanced
        int balance = 0;

        int i = k;
        while (true) {
            // If the window size is odd
            if ((k & 1) == 1)
                medians.add((double) (smallList.peek()));
            else
                medians.add((double) ((long)smallList.peek() + (long)largeList.peek()) * 0.5);

            // Break the loop if all elements have been processed
            if (i >= nums.length)
                break;

            // Outgoing number
            int outNum = nums[i - k];

            // Incoming number
            int inNum = nums[i];
            i++;

            // If the outgoing number is from max heap
            if (outNum <= smallList.peek())
                balance -= 1;
            else
                balance += 1;

            // Add/Update the outgoing number in the hash map
            if (outgoingNum.containsKey(outNum))
                outgoingNum.put(outNum, outgoingNum.get(outNum) + 1);
            else
                outgoingNum.put(outNum, 1);

            // If the incoming number is less than the top of the max heap, add it in that heap
            // Otherwise, add it in the min heap
            if (smallList.size() > 0 && inNum <= smallList.peek()) {
                balance += 1;
                smallList.offer(inNum);
            } else {
                balance -= 1;
                largeList.offer(inNum);
            }

            // Re-balance the heaps
            if (balance < 0)
                smallList.offer(largeList.poll());
            else if (balance > 0)
                largeList.offer(smallList.poll());

            // Since the heaps have been balanced, we reset the balance variable to 0.
            // This ensures that the two heaps contain the correct elements for the calculations to be performed on the elements in the next window.
            balance = 0;

            // Remove invalid numbers present in the hash map from top of max heap
            while (smallList.size() > 0 && outgoingNum.containsKey(smallList.peek()) && outgoingNum.get(smallList.peek()) > 0)
                outgoingNum.put(smallList.peek(), outgoingNum.get(smallList.poll()) - 1);

            // Remove invalid numbers present in the hash map from top of min heap
            while (largeList.size() > 0 && outgoingNum.containsKey(largeList.peek()) && outgoingNum.get(largeList.peek()) > 0)
                outgoingNum.put(largeList.peek(), outgoingNum.get(largeList.poll()) - 1);
        }

        double[] arr = medians.stream().mapToDouble(Double::doubleValue).toArray();
        return arr;
    }
}





