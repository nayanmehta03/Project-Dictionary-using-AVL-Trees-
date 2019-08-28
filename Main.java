import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Scanner scan = new Scanner(System.in);
        /* Creating object of AVLTree */
        AVLTree avlt = new AVLTree();
        FileWriter fw = new FileWriter("D:\\avltree.txt");
        System.out.println("AVLTree Tree Test\n");
        char ch;
        /*  Perform tree operations  */
        do
        {
            System.out.println("\nAVLTree Operations\n");
            System.out.println("1. Insert ");
            System.out.println("2. Search");
            System.out.println("3. Count nodes");
            System.out.println("4. Check empty");
            System.out.println("5. Clear tree");
            System.out.println("6. Serialize.");

            int choice = scan.nextInt();
            switch (choice)
            {
                case 1 :
                    System.out.println("Enter integer element to insert");
                    String data = scan.next();
                    avlt.insert( AVLTree.capitalizeWord(data) );
                    break;
                case 2 :
                    System.out.println("Enter integer element to search");
                    String search = scan.next();
                    System.out.println("Search result : "+ avlt.search( AVLTree.capitalizeWord(search) ));
                    break;
                case 3 :
                    System.out.println("Nodes = "+ avlt.countNodes());
                    break;
                case 4 :
                    System.out.println("Empty status = "+ avlt.isEmpty());
                    break;
                case 5 :
                    System.out.println("\nTree Cleared");
                    avlt.makeEmpty();
                    break;
                case 6:
                    System.out.println("Serializing....");
                    avlt.serialize(fw);
                    System.out.println("Serialized.");
                    fw.close();
                    break;
                default :
                    System.out.println("Wrong Entry \n ");
                    break;
            }
            /*  Display tree  */
            System.out.print("\nPost order : ");
            avlt.postorder();
            System.out.print("\nPre order : ");
            avlt.preorder();
            System.out.print("\nIn order : ");
            avlt.inorder();

            System.out.println("\nDo you want to continue (Type Y or N) \n");
            ch = scan.next().charAt(0);
        } while (ch == 'Y'|| ch == 'y');
    }
}