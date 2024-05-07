import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;

public class GameTest {
    public static void main(String[] args) throws IOException {

        //new reader object for userinput
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        boolean entering_word = true;
        boolean word_exists;
        Runtime rt = Runtime.getRuntime();

        DictionarySeeker DS = null;
        String startword, endword;
        startword = "";
        endword = "";
        
        int last_entered_wordlength = -1;
        while (entering_word) {
            //Reset display
        	word_exists = false;
        	startword = "";
        	endword = "";
        	//Begin taking first word
            while(!word_exists) {
            	System.out.println("Enter start word");
            	startword = reader.readLine().toLowerCase();
            	
            	//Verify word exists :
            	//prepare dictionary seeker IF entered word has differeent length
            	if (startword.length() != last_entered_wordlength) {
            		DS = new DictionarySeeker("words_official.txt");
            		int wordcount = 0;
                	try{
                		wordcount = DS.Fill(startword.length());
                	}
                	catch (IOException e){
                		System.err.println("Incorrect size!");
                		DS = null;
                		continue; //restart loop
                	}
                	last_entered_wordlength = startword.length();
            	}
            	
            	if (!DS.isWord(startword)) {
            		System.out.println("Word does not exist");
            		continue;
            	}
            	else {
            		word_exists = true;
            	}
            	
            	word_exists = true;
            }
            
            word_exists = false;
            
            while (!word_exists) {
            	//check end word
                System.out.println("Enter end word");
                endword = reader.readLine().toLowerCase();

                //check if word exists
                if (!DS.isWord(endword)){
                	System.out.println("Endword does not exist!");
                	continue;
                }
                //check end word size
                else if (startword.length() != endword.length()) {
                	System.out.println("Must be same length as startword");
                	continue;
                }
                
                word_exists = true;
            };
            
            //IF NOT CANCELLED
            entering_word = false;
        }
        
        while(true){
        System.out.println("Choose algorithm : ");
        System.out.println("1. UCS");
        System.out.println("2. GBFS");
        System.out.println("3. A*");
        System.out.println("4. Exit");


        String action = reader.readLine();
        
        Node answer = null;

        rt.gc(); //Garbage collect
		long start_time = System.currentTimeMillis();	
		long mem1 = rt.totalMemory() - rt.freeMemory();
        
        Algorithm.DS = DS;
        if (action.equals("1")){
            answer = Algorithm.UCS(startword, endword);
            System.out.println("UCS :");
        }
        else if (action.equals("2")){
            answer = Algorithm.GBFS(startword, endword);
            System.out.println("GBFS :");
        }
        else if (action.equals("3")){
            answer = Algorithm.AStar(startword, endword);
            System.out.println("A* :");
        }
        else if (action.equals("4")){
            return;
        }

        long mem2 = rt.totalMemory() - rt.freeMemory();
        //seek answer
        if (answer != null) {
        	// System.out.println(answer.getTrail());
        }
        else {
        	System.out.println("Not found!");
        }

        int nodevisit = Algorithm.last_node_visits;
        long proctime = System.currentTimeMillis() - start_time;
        long memusage = mem2-mem1;

        System.out.printf("Nodes visitted : %d\n",nodevisit);
        System.out.printf("TIme taken : %d Miliseconds\n",proctime);
        System.out.printf("Memory used : %d Bytes\n",memusage);
        }
    }
}
