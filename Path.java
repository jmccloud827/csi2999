package ai;

import java.util.PriorityQueue;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.shape.Line;

public class Path {
    Builder build;
    Node[][] grid;
    int[] solution;
    PriorityQueue<Node> openNodes;
    boolean[][] closeNodes;
    int sX,sY;
    int eX,eY;
    Path(Builder b){
        build = b;
        grid = new Node[400][800];
        closeNodes = new boolean[400][800];
        openNodes = new PriorityQueue<Node>((Node n1, Node n2)-> {
            return n1.f < n2.f ? -1 : n1.f > n2.f ? 1 : 0;
        });
        sX = 200;sY = 700;
        eX = 200;eY = 75;
        process();
    }
    public void process(){
        grid[sX][sY] = new Node(sX, sY, eX, eY);
        openNodes.add(grid[sX][sY]);
        Node current;
        while(true){
            current = openNodes.poll();
            if(current == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No Solution!");
            alert.setContentText("The Current Path has No Solution");
            alert.showAndWait();
            return;
            }
            if(build.hitPath(current.x, current.y)){
                grid[current.x][current.y].hit = true;
                break;
            }
            closeNodes[current.x][current.y] = true;
            if(current.equals(grid[eX][eY])){
                return;
            }
            updateCost(current, current.x, current.y - 1, current.f + 10);//up
            updateCost(current, current.x, current.y + 1, current.f + 10);//down
            updateCost(current, current.x - 1, current.y, current.f + 10);//left
            updateCost(current, current.x + 1, current.y, current.f + 10);//right
            updateCost(current, current.x - 1, current.y - 1, current.f + 15); //up-left
            updateCost(current, current.x + 1, current.y - 1, current.f + 15);//up-right;
            updateCost(current, current.x - 1, current.y + 1, current.f + 15); //down-left;
            updateCost(current, current.x + 1, current.y + 1, current.f + 15);//down-right
        }
    }
    public void updateCost(Node current, int x, int y, int cost){
        if(grid[x][y] == null){
            grid[x][y] = new Node(x, y, eX, eY);
        }
        if(build.hitPath(x, y) || closeNodes[x][y]){
            grid[x][y].hit = true;
            return;
        }
        boolean isOpen = openNodes.contains(grid[x][y]);
        if(!isOpen || (grid[x][y].h+cost) < grid[x][y].f){
            grid[x][y].f = grid[x][y].h+cost;
            grid[x][y].parent = current;
            if(!isOpen){
                openNodes.add(grid[x][y]);
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
            while(current.parent != null){
                if(solution[current.y] != 0){
                    solution[current.y] = current.x;
                }
                current = current.parent;
            }
        } else {
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
}
