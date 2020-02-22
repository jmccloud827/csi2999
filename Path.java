package ai;

import java.util.PriorityQueue;
import javafx.scene.Group;
import javafx.scene.shape.Line;

public class Path {
    Builder build;
    Node[][] grid;
    int[] solution;
    Group line;
    PriorityQueue<Node> openNodes;
    boolean[][] closeNodes;
    int sX,sY;
    int eX,eY;
    Path(Builder b){
        build = b;
        line = new Group();
        grid = new Node[400][800];
        closeNodes = new boolean[400][800];
        openNodes = new PriorityQueue<Node>((Node n1, Node n2)-> {
            return n1.f < n2.f ? -1 : n1.f > n2.f ? 1 : 0;
        });
        sX = 200;sY = 700;
        eX = 200;eY = 75;
        addBuild();
        grid[sX][sY].f = 0;
        process();
    }
    public void addBuild(){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(build.hitPath(i, j)){
                    grid[i][j] = null;
                } else {
                    grid[i][j] = new Node(i, j);
                grid[i][j].h = Math.abs(i - eX) + Math.abs(j - eY);
                grid[i][j].solution = false;
                }
            }
        }
    }
    public void process(){
        openNodes.add(grid[sX][sY]);
        Node current;
        while(true){
            current = openNodes.poll();
            if(current == null){
                break;
            }
            closeNodes[current.x][current.y] = true;
            if(current.equals(grid[eX][eY])){
                return;
            }
            Node t;
            if(current.x - 1 >= 0){
                t = grid[current.x - 1][current.y];
                updateCost(current, t, current.f + 10);
                if(current.y - 1 >= 0){
                    t = grid[current.x - 1][current.y - 1];
                    updateCost(current, t, current.f + 14); 
                }
                if(current.y + 1 < grid[0].length){
                    t = grid[current.x - 1][current.y + 1];
                    updateCost(current, t, current.f + 14); 
                }
            }
            if(current.y - 1 >= 0){
                t = grid[current.x][current.y - 1];
                updateCost(current, t, current.f + 10);
            }
            if(current.y + 1 < grid[0].length){
                t = grid[current.x][current.y + 1];
                updateCost(current, t, current.f + 10);
            }
            if(current.x + 1 < grid.length){
                t = grid[current.x + 1][current.y];
                updateCost(current, t, current.f + 10);
                if(current.y - 1 >= 0){
                    t = grid[current.x + 1][current.y - 1];
                    updateCost(current, t, current.f + 14);
                }
                if(current.y + 1 < grid[0].length){
                    t = grid[current.x + 1][current.y + 1];
                    updateCost(current, t, current.f + 14);
                }
            }
        }
    }
    public void updateCost(Node current, Node t, int cost){
        if(t == null || closeNodes[t.x][t.y]){
            return;
        }
        boolean isOpen = openNodes.contains(t);
        if(!isOpen || (t.h+cost) < t.f){
            t.f = t.h+cost;
            t.parent = current;
            if(!isOpen){
                openNodes.add(t);
            }
        }
    }
    public int[] solution(){
        solution = new int[800];
        if(closeNodes[eX][eY]){
            for(int i = 0; i < solution.length; i++){
                solution[i] = 200;
            }
            Node current = grid[eX][eY];
            grid[current.x][current.y].solution = true;
            while(current.parent != null){
                grid[current.x][current.y].solution = true;
                if(solution[current.y] != 0){
                    solution[current.y] = current.x;
                }
                current = current.parent;
            }
        } else {
            System.out.println("No Solution");
            return solution;
        }
        for(int i = 75; i < solution.length - 100; i++){
            int temp = solution[i+1] - solution[i];
            if(temp > 1 || temp < 1){
                int end = 0;
                for(int j = i; j < solution.length - 100; j++){
                    int temp2 = solution[j+1] - solution[j];
                    if(temp2 == 0 || j == 699){
                        end = j;
                        break;
                    }
                }
                for(int j = end; j > i; j--){
                    solution[j] -= temp;
                }
                i = end;
            }
        }
        return solution;
    }
    public Group show(){
        for(int i = 75; i < solution.length - 100; i++){
            line.getChildren().add(new Line(solution[i], i, solution[i + 1], i + 1));
        }
        return line;
    }
}
