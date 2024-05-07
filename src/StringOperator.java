import java.util.List;
import java.util.ArrayList;

public class StringOperator {
    public static int CharDiff(String s1, String s2){
        int retval = 0;
        if (s1.length() != s2.length()){
            retval = -1;
        }
        else{
            for (int i = 0; i < s1.length(); i++){
                if (s1.charAt(i) == s2.charAt(i)){
                    retval++;
                }
            }
        }
        return s1.length() - retval;
    }
    
    public static List<Integer> LocateDiff(String s1, String s2){
    	List<Integer> retval = new ArrayList<Integer>(); 
    	if (s1.length() == s2.length()) {
    		for (int i = 0; i < s1.length(); i++){
                if (s1.charAt(i) == s2.charAt(i)){
                    retval.add(i);
                }
            }
    	}
    	return retval;
    }
}
