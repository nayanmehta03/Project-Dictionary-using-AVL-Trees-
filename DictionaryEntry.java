import java.io.Serializable;
import java.util.ArrayList;

public class DictionaryEntry implements Serializable {

    DictionaryEntry left, right;
    String word;
    int height ;
    ArrayList<String> definition ;
    String wordType;

    DictionaryEntry()
    {
        word = "";
       //definition ="";
        wordType = "";
        left = null;
        right = null;
        height = 0;

    }

    DictionaryEntry(String word, String wordType, String definition)
    {
        this.word = word;
        this.definition.add(definition);//= definition;
        this.wordType =wordType;
        left = null;
        right = null;
        height = 0;
    }

    public String toString()
    {
      String string = word ;
        for(int i = 0; i<definition.size() ;i++){
            string += word+" : ("+wordType+") :"+" "+definition.get(i);
        }
        return string;
        //return word+" : ("+wordType+") :"+" "+definition;
    }

}
