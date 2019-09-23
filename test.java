import java.io.*;

public class test{
    public static void main(String[] args){
        Demo object=new Demo(1,"gg");
        String filename="demo.txt";

        try{
            FileOutputStream fileOutputStream=new FileOutputStream(filename);
            ObjectOutputStream out=new ObjectOutputStream(fileOutputStream);

            out.writeObject(object);
            out.close();
            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        Demo object1=null;
        try{
            FileInputStream fileInputStream=new FileInputStream(filename);
            ObjectInputStream in=new ObjectInputStream(fileInputStream);

            object1=(Demo)in.readObject();
            in.close();
            fileInputStream.close();

            System.out.println("Object has been deserialized ");
            System.out.println("a = " + object1.a);
            System.out.println("b = " + object1.b);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}