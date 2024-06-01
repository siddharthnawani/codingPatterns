import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindKClosestPoints {

    // Driver code
    public static void main(String[] args) {

        int[][]inputs={
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5},
                {1, 2, 4, 5, 6},
                {1, 2, 3, 4, 5, 10}
        };
        int[] k = {4, 4, 2, 3};
        int[] x = {4, 3, 10, -5};
        for(int i=0; i<k.length; i++){
            List<Integer> kList = findClosestElements(inputs[i], k[i], x[i]);
            System.out.print(i+1);
            System.out.println(".\tThe "+k[i]+" closest elements for the number "+x[i]+ " in the array "+ Arrays.toString(inputs[i])+ " are: ");
            System.out.print("\t[");
            for(int j = 0; j < k[i]-1; j++) {
                System.out.print(kList.get(j) + ", ");
            }
            System.out.println(kList.get(k[i]-1) + "]");
            
        }
    }

    public static List<Integer> findClosestElements(int[] nums, int k, int target) {

        List<Integer> closestElements = new ArrayList<>();

        // If the length of 'nums' is the same as k, return 'nums'
        if (nums.length == k) {
            for (int num : nums) {
                closestElements.add(num);
            }
            return closestElements;
        }

        // if target is less than or equal to first element in 'nums',
        // return the first k elements from 'nums'
        if (target <= nums[0]) {
            for (int i = 0; i < k; i++) {
                closestElements.add(nums[i]);
            }
            return closestElements;
        }

        // if target is greater than or equal to last element in 'nums',
        // return the last k elements from 'nums'
        if (target >= nums[nums.length - 1]) {
            for (int i = nums.length - k; i < nums.length; i++) {
                closestElements.add(nums[i]);
            }
            return closestElements;
        }

        // find the first closest element to target using binary search
        int firstClosest = BinarySearch.binarySearch(nums, target);

        // initialize the sliding window pointers
        int windowLeft = firstClosest - 1;
        int windowRight = windowLeft + 1;

        // expand the sliding window until its size becomes equal to k
        while ((windowRight - windowLeft - 1) < k) {

            // if window's left pointer is going out of bounds, move the window rightward
            if (windowLeft == -1) {
                windowRight++;
                continue;
            }

            // if window's right pointer is going out of bounds
            // OR if the element pointed to by window's left pointer is closer to target than
            // the element pointed to by window's right pointer, move the window leftward
            if (windowRight == nums.length || Math.abs(nums[windowLeft] - target) <= Math.abs(nums[windowRight] - target)) {
                windowLeft--;
            }

            // if the element pointed to by window's right pointer is closer to target than
            // the element pointed to by window's left pointer, move the window rightward
            else {
                windowRight++;
            }
        }

        // return k closest elements present inside the window
        for (int i = windowLeft + 1; i < windowRight; i++) {
            closestElements.add(nums[i]);
        }
        return closestElements;
    }
}

class BinarySearch{
    public static int binarySearch(int[] array, int target) {
        int l=0,r=array.length-1;
        int mid=-1;

        while(l<=r){
            mid=l+ (r-l)/2;
            if(array[mid]==target) return mid;
            if(target <array[mid]) r=mid-1;
            else l=mid+1;
        }

        return l;
    }
}




