import java.io.Serializable;
import java.util.ArrayList;

public class DictionaryEntry implements Serializable {

    DictionaryEntry left, right;
    String word;
    int height ;
    ArrayList<String> definition = new ArrayList<>();
    ArrayList<String> wordType = new ArrayList<>();

    DictionaryEntry()
    {
        word = "";
       //definition ="";
       //wordType = "";
        left = null;
        right = null;
        height = 0;

    }

    DictionaryEntry(String word, String wordType, String definition)
    {
        this.word = word;

        this.wordType.add(wordType);// =wordType;
        left = null;
        right = null;
        height = 0;
        this.definition.add(definition);//= definition;
    }

    public String toString()
    {
      String string = "" ;
        for(int i = 0; i<definition.size() ;i++){
            string += word+" : ("+wordType.get(i)+") :"+" "+definition.get(i)+"\n";
        }
        return string;
        //return word+" : ("+wordType+") :"+" "+definition;
    }

}
