import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RepeatedDnaSequences {

    public static void main(String[] args) {
        List<String> inp= Arrays.asList("ACGT", "AGACCTAGAC", "AAAAACCCCCAAAAACCCCCC",
            "GGGGGGGGGGGGGGGGGGGGGGGGG", "TTTTTCCCCCCCTTTTTTCCCCCCCTTTTTTT", "TTTTTGGGTTTTCCA",
            "AAAAAACCCCCCCAAAAAAAACCCCCCCTG", "ATATATATATATATAT");
        //these are the inputs k for each
        List<Integer> inputsK = Arrays.asList(3, 3, 8, 12, 10, 14, 10, 6);
        for(int i=0;i<inputsK.size();i++){
            System.out.println(findRepeatedSequences(inp.get(i), inputsK.get(i)));
        }
    }

    private static Set<String> findRepeatedSequences(String s, int k) {
        int n=s.length();
        if(n < k) return new HashSet<>();

        //Mapping of characters
        Map<Character,Integer> mapping=new HashMap<>();
        mapping.put('A', 1);
        mapping.put('C', 2);
        mapping.put('G', 3);
        mapping.put('T', 4);

        int a=4;

        //convert numbers to integer representation
        List<Integer> numbers = new ArrayList<>(Arrays.asList(new Integer[n]));
        Arrays.fill(numbers.toArray(),0);

        for(int i=0;i<n;i++)
            numbers.set(i,mapping.get(s.charAt(i)));

        int hashValue=0;
        Set<Integer> currSet=new HashSet<>();
        Set<String> out=new HashSet<>();
        for(int i=0;i<n-k+1;i++){
            //calculate first hash
            if(i==0){
                for(int j=0;j<k;j++){
                    hashValue += numbers.get(j) * Math.pow(a,k-j-1);
                }
            }else{
                int presentHash=hashValue;
                int firstNum=numbers.get(i-1) * (int) Math.pow(a,k-1);
                int diff=presentHash - firstNum;
                hashValue= (diff * a) + numbers.get(i+k-1)   ;

            }

            if(currSet.contains(hashValue))
                out.add(s.substring(i,i+k));
            else
                currSet.add(hashValue);

        }

        return out;


    }

    //solution 2 here k is 10 by default
    /*
    Complexity
Time complexity:O(N)
Space complexity:O(N)
    **/
    public List<String> findRepeatedDnaSequences(String s) {
        Set seen = new HashSet(), repeated = new HashSet();
        for (int i = 0; i + 9 < s.length(); i++) {
            String ten = s.substring(i, i + 10);
            if (!seen.add(ten))
                repeated.add(ten);
        }
        return new ArrayList(repeated);
    }
}






