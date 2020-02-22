package ai;

public class Node {
    int x,y;
    Node parent;
    int f,g,h;
    boolean solution;
    Node(int i, int j){
        x = i;
        y = j;
    }
}
