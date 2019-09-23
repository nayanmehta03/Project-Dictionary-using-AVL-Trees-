import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;

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

       // if (!wordExist(t,word))
      // {
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

    //}
        else {
            t.definition.add(definition);//= definition;
            t.wordType.add(wordType);
            //t.wordType[t.count] = wordType;
           // t.count++;
            return t;
        }
        t.height = max( height( t.left ), height( t.right ) ) + 1;
        // t.wordType = wordType;
        // t.definition = definition;
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
    //DictionaryEntry de = new DictionaryEntry("hello","hello","Hello");

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
              // System.out.println(rs.getString(1)+" "+ rs.getString(2)+" "+ rs.getString(3));
                //System.out.println(dic.insert(rs.getString(1),rs.getString(2),rs.getString(3)));
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


}
