package ai;

public class Node {
    int x,y;
    Node parent;
    int f,g,h;
    boolean hit;
    Node(int i, int j, int eX, int eY){
        x = i;
        y = j;
        hit = false;
        h = Math.max(Math.abs(i - eX), Math.abs(j - eY));
    }
}
