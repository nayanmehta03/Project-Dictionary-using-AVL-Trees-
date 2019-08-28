/*
 *  Java Program to Implement AVL Tree
 */

class AVLNode
{
    AVLNode left, right;
    String data;
    int height;

    public AVLNode()
    {
        left = null;
        right = null;
        data = "";
        height = 0;
    }

    public AVLNode(String s)
    {
        left = null;
        right = null;
        data = s ;
        height = 0;
    }
}

