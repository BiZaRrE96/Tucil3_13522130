import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DictionarySeeker{
    private ArrayList<String> words;
    private String txtDictDir;
    private int maxDebugWordCount = 0;
    private boolean testmode = false;
    
    public DictionarySeeker(String txtDictDir){
        this.txtDictDir = txtDictDir;
    }

    public int Fill(int word_len) throws IOException{
        if (word_len < 0){
            throw new IOException("Incorrect Word Size");
        }
        return fill_words(word_len);
    }

    private int fill_words(int word_len){
        this.words = new ArrayList<String>();
        int wordcount = 0;
        try{
            FileInputStream streem = new FileInputStream(txtDictDir);
            char c;
            String s = "";
            int temp;
            while ((temp = streem.read()) != -1) {
                c = (char) temp;
                if (c == '\n'){
                    if (s.length() == word_len + 1 || word_len == 0){
                        if (testmode){
                            System.out.println(s);
                        }
                        assert(s != "\n");
                        wordcount += 1;
                        words.add(s.trim());
                    }
                    s = "";
                }
                else{
                    s += c;
                }

                //DEBUG
                if (maxDebugWordCount > 0 && wordcount >= maxDebugWordCount){
                    break;
                }
            }
            if (testmode){
                System.out.println("SUCCESS!");
            }
            //Finally
            streem.close();
        }   
        catch (FileNotFoundException e){
            System.err.println(e.getMessage());
            wordcount = 0;
        }   
        catch (IOException e){
            System.err.println(e.getMessage());
            wordcount = 0;
        }

        return wordcount;
    }

    public String Test(int i){
        return words.get(i);
    }
    
    //Find word in dictionary
    public boolean isWord(String s) {
    	boolean retval = false;
    	for (String item : words) {
    		if (item.equalsIgnoreCase(s)) {
    			retval = true;
    			break;
    		}
    		else {
    			//System.out.printf("%s != %s\n",item,s);
    		}
    	}
    	return retval;
    }
    
    public ArrayList<String> exposeWords(){
    	return words;
    }
}
