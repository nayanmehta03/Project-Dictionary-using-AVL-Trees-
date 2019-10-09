package Data;

import GUI.AlertBox;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Scanner;


public class Dictionary implements Serializable {

    private DictionaryEntry root;

    Dictionary()
    {
        root = null;
    }

    public boolean isEmpty()
    {
        return root == null;
    }

    public void makeEmpty()
    {
        root = null;
    }

    public void insert(String word, String wordType, String definition)
    {
        root = insert(word, wordType, definition , root);
    }

    private int height(DictionaryEntry t )
    {
        return t == null ? -1 : t.height;
    }

    private int max(int lhs, int rhs)
    {
        return lhs > rhs ? lhs : rhs;
    }
    private DictionaryEntry insert(String word, String wordType, String definition, DictionaryEntry t)
    {
        if (t == null)
            t = new DictionaryEntry(word, wordType, definition );
        else if (word.compareTo( t.word) < 0)
        {

            t.left = insert( word, wordType, definition, t.left );
            if( height( t.left ) - height( t.right ) == 2 )
                if( word.compareTo(t.left.word) < 0 )
                    t = rotateWithLeftChild( t );
                else
                    t = doubleWithLeftChild( t );
        }
        else if( word.compareTo(t.word) > 0 )
        {
            t.right = insert( word, wordType, definition, t.right );
            if( height( t.right ) - height( t.left ) == 2 )
                if( word.compareTo(t.right.word)>0)
                    t = rotateWithRightChild( t );
                else
                    t = doubleWithRightChild( t );
        }
        else {
            t.definition.add(definition);
            t.wordType.add(wordType);
            return t;
        }
        t.height = max( height( t.left ), height( t.right ) ) + 1;

        return t;
    }

    private DictionaryEntry rotateWithLeftChild(DictionaryEntry k2)
    {
        DictionaryEntry k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = max( height( k1.left ), k2.height ) + 1;
        return k1;
    }


    private DictionaryEntry rotateWithRightChild(DictionaryEntry k1)
    {
        DictionaryEntry k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = max( height( k2.right ), k1.height ) + 1;
        return k2;
    }

    private DictionaryEntry doubleWithLeftChild(DictionaryEntry k3)
    {
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }


    private DictionaryEntry doubleWithRightChild(DictionaryEntry k1)
    {
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }

    public DictionaryEntry search(String val)
    {
        return search(root,val);
    }

    private boolean wordExist(DictionaryEntry r, String val)
    {
        boolean found = false;
        while ((r != null) && !found)
        {
            String rval = r.word;
            if (val.compareTo(rval) < 0)
                r = r.left;
            else if (val.compareTo(rval) > 0)
                r = r.right;
            else
            {
                found = true;
                break;
            }
            found = wordExist(r, val);
        }
        return found;
    }

    private DictionaryEntry search(DictionaryEntry r, String val)
    {
        val = capitalizeWord(val);
        DictionaryEntry found = null;
        while ((r != null))
        {
            String rval = r.word;
            if (val.compareTo(rval) < 0)
                r = r.left;
            else if (val.compareTo(rval) > 0)
                r = r.right;
            else
            {
                found = r;
                break;
            }
            found = search(r, val);
        }
        return found;
    }

    public static String capitalizeWord(String str)
    {
        String capitalizeWord="";

        String first=str.substring(0,1);
        String afterfirst=str.substring(1);
        capitalizeWord+=first.toUpperCase()+afterfirst.toLowerCase()+" ";

        return capitalizeWord.trim();
    }

    public  Dictionary createDictionary()
    {
        try{

            Dictionary dic = new Dictionary();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/entries","root","nayan123");
            String query = "select * FROM entries ";
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            while(rs.next())
            {
                dic.insert(rs.getString(1),rs.getString(2),rs.getString(3));
            }
            con.close();
            return dic;
        }catch(Exception e){ System.out.println(e);}
        return null;
    }


    public void inorder()
    {
        inorder(root);
    }

    private void inorder(DictionaryEntry r)
    {
        if (r != null)
        {
            inorder(r.left);
            System.out.print(r.word +" ");
            inorder(r.right);
        }
    }

    public static void downloadDictionary(){
        String filename = "dictionary.txt";
        File f = new File(filename);
        try {
            URL url = new URL("https://drive.google.com/uc?authuser=0&id=1Jm8qNpadprEX0WhG0jCJxwtfTeYybIij&export=download");
            if(f.exists()){
                f.delete();
                ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                FileOutputStream fos =new FileOutputStream("dictionary.txt");
                fos.getChannel().transferFrom(rbc,0,Long.MAX_VALUE);

            }
            else {
                ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                FileOutputStream fos =new FileOutputStream("dictionary.txt");
                fos.getChannel().transferFrom(rbc,0,Long.MAX_VALUE);
            }
        }catch (Exception e){
            AlertBox.display("Error","Something went wrong while downloading dictionary. Please check your connection.");
        }

    }

    public static void downloadSuggestions(){
        String filename = "suggestions.txt";
        File f = new File(filename);
        try {
            URL url = new URL("https://drive.google.com/uc?authuser=0&id=1nZdsXDecvfGFVA5uzWXQgnUop_yWaX3L&export=download");
            if(f.exists()){
                f.delete();
                ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                FileOutputStream fos =new FileOutputStream("suggestions.txt");
                fos.getChannel().transferFrom(rbc,0,Long.MAX_VALUE);

            }
            else {
                ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                FileOutputStream fos =new FileOutputStream("suggestions.txt");
                fos.getChannel().transferFrom(rbc,0,Long.MAX_VALUE);
            }
        }catch (Exception e){
            AlertBox.display("Error","Something went wrong while downloading suggestions. Please check your connection.");
        }

    }

    public static Dictionary deSerialise(){
        Dictionary dictionary = new Dictionary();
        String filename = "dictionary.txt";

        try{
            FileInputStream fileInputStream=new FileInputStream(filename);
            ObjectInputStream in=new ObjectInputStream(fileInputStream);

            dictionary=(Dictionary)in.readObject();
            in.close();
            fileInputStream.close();
        }catch (Exception e){
           AlertBox.display("Error","Something went wrong while creating dictionary.");
        }

        return dictionary;


    }

    public static HashSet deSerialiseSug(){
        HashSet<String> suggestions = new HashSet<>();
        String filename = "suggestions.txt";

        try{
            FileInputStream fileInputStream=new FileInputStream(filename);
            ObjectInputStream in=new ObjectInputStream(fileInputStream);

            suggestions=(HashSet<String>) in.readObject();
            in.close();
            fileInputStream.close();
        }catch (Exception e){
            AlertBox.display("Error","Something went wrong while creating suggestions.");
        }

        return suggestions;


    }

    public static void main(String[] args) throws Exception {
        Dictionary dic = null ;
        Scanner sc = new Scanner(System.in);
       // dic = dictionary.createDictionary();
        String filename="dictionary.txt";
        //Dictionary.downloadDictionary();

       /*try{
            FileOutputStream fileOutputStream=new FileOutputStream(filename);
            ObjectOutputStream out=new ObjectOutputStream(fileOutputStream);
            out.writeObject(dic);

            out.close();
            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }*/



    }


}
