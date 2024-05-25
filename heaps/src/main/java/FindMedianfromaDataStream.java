import java.util.PriorityQueue;

public class FindMedianfromaDataStream {

    PriorityQueue<Integer> maxHeapFoSmallNum;
    PriorityQueue<Integer> minHeapFOrLargeNum;


    public FindMedianfromaDataStream() {
        maxHeapFoSmallNum = new PriorityQueue<>((a, b) -> (b - a));
        minHeapFOrLargeNum = new PriorityQueue<>((a, b) -> (a - b));
    }

    public void insertNum(int num) {
        if (maxHeapFoSmallNum.isEmpty() || maxHeapFoSmallNum.peek() > num)
            maxHeapFoSmallNum.add(num);
        else minHeapFOrLargeNum.add(num);

        if (maxHeapFoSmallNum.size() > minHeapFOrLargeNum.size() + 1)
            minHeapFOrLargeNum.add(maxHeapFoSmallNum.poll());
        else if (maxHeapFoSmallNum.size() < minHeapFOrLargeNum.size())
            maxHeapFoSmallNum.add(minHeapFOrLargeNum.poll());


    }

    public double findMedian() {
        if (maxHeapFoSmallNum.size() == minHeapFOrLargeNum.size())
            return (maxHeapFoSmallNum.peek() + minHeapFOrLargeNum.peek()) / 2.0;

        return maxHeapFoSmallNum.peek();
    }


    public static void main(String[] args) {
        // Driver code
        int[] nums = {35, 22, 30, 25, 1};
        FindMedianfromaDataStream medianOfAges = null;
        for (int i = 0; i < nums.length; i++) {
            System.out.print(i + 1);
            System.out.print(".\tData stream: [");
            medianOfAges = new FindMedianfromaDataStream();
            for (int j = 0; j <= i; j++) {
                System.out.print(nums[j]);
                if (j != i)
                    System.out.print(", ");
                medianOfAges.insertNum(nums[j]);
            }
            System.out.println("]");
            System.out.println("\tThe median for the given numbers is: " + medianOfAges.findMedian());

        }


    }
}
