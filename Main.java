

import java.io.*;
import java.sql.SQLDataException;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLDataException,StreamCorruptedException {

        Dictionary dictionary = new Dictionary();
        Dictionary dic = null ;
        Scanner sc = new Scanner(System.in);
        //dic = dictionary.createDictionary();
        String filename="dictionary.txt";

       /* try{
            FileOutputStream fileOutputStream=new FileOutputStream(filename);
            ObjectOutputStream out=new ObjectOutputStream(fileOutputStream);
            out.writeObject(dic);

            out.close();
            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }*/

        Dictionary de =null;
        try{
            FileInputStream fileInputStream=new FileInputStream(filename);
            ObjectInputStream in=new ObjectInputStream(fileInputStream);

            de=(Dictionary)in.readObject();
            in.close();
            fileInputStream.close();
            while (true)
            {
                System.out.println("Enter word to search: ");
                System.out.println( de.search(Dictionary.capitalizeWord(sc.next())));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
