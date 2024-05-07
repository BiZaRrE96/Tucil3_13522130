import java.io.IOException;

public class logic_block {
    DictionarySeeker DS = null;
    public String startword, endword;
    public String dict_address = "words_official.txt";
    int last_word_length;
    
    public logic_block(){
    	startword = "";
    	endword = "";
    	last_word_length = 0;
    }
    
    public int test_start(String s) {
    	System.out.println("START");
    	if (s.length() != last_word_length) {
    		System.out.println("DEFINE DS");
    		DS = new DictionarySeeker(dict_address);
    		int wordcount = 0;
        	try{
        		wordcount = DS.Fill(s.length());
        	}
        	catch (IOException e){
        		System.err.println("Incorrect size!");
        		DS = null;
        		return 1;
        	}
        	last_word_length = s.length();
        	System.out.printf("DONE (%d)\n",wordcount);
    	}
    	
    	if (!DS.isWord(s)) {
    		return -1;
    	}
    	
    	startword = s;
    	return 0;
    }
    
    public int test_end(String s) {
    	if (s.length() != startword.length()) {
    		return 1;
    	}
    	else {
    		if (DS == null) {
    			return 2;
    		}
    	}
    	if (!DS.isWord(s)) {
    		return -1;
    	}
    	
    	endword = s;
    	
    	return 0;
    }
    
    public Node UCS() {
    	Algorithm.DS = DS;
    	return Algorithm.UCS(startword, endword);
    }
    public Node GBFS() {
    	Algorithm.DS = DS;
    	return Algorithm.GBFS(startword, endword);
    }
    public Node ASTAR() {
    	Algorithm.DS = DS;
    	return Algorithm.AStar(startword, endword);
    }
}
