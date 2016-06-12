package gpath;

import java.util.ArrayList;
import java.util.List;


public class Gpath {

    class Box {

        int line;
        int column;

        Box() {
            this.line = 1;
            this.column = 1;
        }

        Box(int line, int column) {
            this.line = line;
            this.column = column;
        }

        Box(Box box) {
            this.line = box.getLine();
            this.column = box.getColumn();
        }

        int getLine() {
            return line;
        }

        int getColumn() {
            return column;
        }

        void setLine(int line) {
            this.line = line;
        }

        void setColumn(int column) {
            this.column = column;
        }

        public String toString() {
            return "(" + line + "," + column + ")";
        }

        public boolean equals(Box box) {
            return ((this.line == box.line) && (this.column == box.column));
        }
    }

    boolean contains(List<Box> path, Box box){
        for (Box b : path){
            if (box.equals(b)){
                return true;
            }
        }
        return false;
    }
    
    void think(List<Box> path) {
        Box current_position = new Box(path.get(path.size() - 1));
        if ((current_position.getLine() == dimension) && (current_position.getColumn() == dimension)) {
            for (Box p : path) {
                System.out.print(p + " | ");
            }
            System.out.println();
            counter++;
        } else {
            List<Box> next_hops = new ArrayList<>();
            if ((current_position.getLine() == 1) && (current_position.getColumn() == 1)) {
                next_hops.add(new Box(1, 2));
                next_hops.add(new Box(2, 1));
            } else {
                next_hops = resolve(path);
            }
            if (!(next_hops.isEmpty())) {
                for (Box hop : next_hops) {
                    List<Box> tmp_path = new ArrayList<>();
                    for (Box p : path) {
                        tmp_path.add(new Box(p.getLine(), p.getColumn()));
                    }
                    tmp_path.add(hop);
                    think(tmp_path);
                }
            }
        }
    }

    List<Box> resolve(List<Box> path) {
        Box current_position = new Box(path.get(path.size() - 1));
        Box last_position = new Box(path.get(path.size() - 2));
        Box aux = last_position;
        List<Box> next_hops = new ArrayList<>();
        if (current_position.getColumn() == last_position.getColumn()) {
            // resolve next horizental movements
            if (current_position.getColumn() > 1) {
                aux = new Box(current_position);
                aux.setColumn(current_position.getColumn() - 1);
                if (!(contains(path, aux))) {
                    next_hops.add(aux);
                }
            }
            if (current_position.getColumn() < dimension) {
                aux = new Box(current_position);
                aux.setColumn(current_position.getColumn() + 1);
                if (!(contains(path, aux))) {
                    next_hops.add(aux);
                }
            }
        } else {
            // resolve next vertical movements
            if (current_position.getLine() > 1) {
                aux = new Box(current_position);
                aux.setLine(current_position.getLine() - 1);
                if (!(contains(path, aux))) {
                    next_hops.add(aux);
                }
            }
            if (current_position.getLine() < dimension) {
                aux = new Box(current_position);
                aux.setLine(current_position.getLine() + 1);
                if (!(contains(path, aux))) {
                    next_hops.add(aux);
                }
            }
        }
        return next_hops;
    }

    int counter;
    int dimension = 4;
    Box initial = new Box();
    List<Box> launcher_path = new ArrayList<>();

    public static void main(String[] args) {
        Gpath gpath = new Gpath();
        gpath.launcher_path.add(gpath.initial);
        gpath.think(gpath.launcher_path);
        System.out.println("RESULT = " + gpath.counter);
    }
}
