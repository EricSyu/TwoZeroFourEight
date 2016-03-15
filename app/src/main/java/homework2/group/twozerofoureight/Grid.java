package homework2.group.twozerofoureight;

import java.util.ArrayList;

public class Grid {

    public Component[][] field;
    public Component[][] undoField;
    private Component[][] bufferField;

    public Grid(int sizeX, int sizeY) {
        field = new Component[sizeX][sizeY];
        undoField = new Component[sizeX][sizeY];
        bufferField = new Component[sizeX][sizeY];
        clearGrid();
        clearUndoGrid();
    }

    public Rect randomAvailableCell() {
       ArrayList<Rect> availableRects = getAvailableCells();
       if (availableRects.size() >= 1) {
           return availableRects.get((int) Math.floor(Math.random() * availableRects.size()));
       }
       return null;
    }

    public ArrayList<Rect> getAvailableCells() {
        ArrayList<Rect> availableRects = new ArrayList<Rect>();
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                if (field[xx][yy] == null) {
                    availableRects.add(new Rect(xx, yy));
                }
            }
        }
        return availableRects;
    }

    public boolean isCellsAvailable() {
        return (getAvailableCells().size() >= 1);
    }

    public boolean isCellAvailable(Rect rect) {
        return !isCellOccupied(rect);
    }

    public boolean isCellOccupied(Rect rect) {
        return (getCellContent(rect) != null);
    }

    public Component getCellContent(Rect rect) {
        if (rect != null && isCellWithinBounds(rect)) {
            return field[rect.getX()][rect.getY()];
        } else {
            return null;
        }
    }

    public Component getCellContent(int x, int y) {
        if (isCellWithinBounds(x, y)) {
            return field[x][y];
        } else {
            return null;
        }
    }

    public boolean isCellWithinBounds(Rect rect) {
        return 0 <= rect.getX() && rect.getX() < field.length
            && 0 <= rect.getY() && rect.getY() < field[0].length;
    }

    public boolean isCellWithinBounds(int x, int y) {
        return 0 <= x && x < field.length
                && 0 <= y && y < field[0].length;
    }

    public void insertTile(Component component) {
        field[component.getX()][component.getY()] = component;
    }

    public void removeTile(Component component) {
        field[component.getX()][component.getY()] = null;
    }

    public void saveTiles() {
        for (int xx = 0; xx < bufferField.length; xx++) {
            for (int yy = 0; yy < bufferField[0].length; yy++) {
                if (bufferField[xx][yy] == null) {
                    undoField[xx][yy] = null;
                } else {
                    undoField[xx][yy] = new Component(xx, yy, bufferField[xx][yy].getValue());
                }
            }
        }
    }

    public void prepareSaveTiles() {
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                if (field[xx][yy] == null) {
                    bufferField[xx][yy] = null;
                } else {
                    bufferField[xx][yy] = new Component(xx, yy, field[xx][yy].getValue());
                }
            }
        }
    }

    public void revertTiles() {
        for (int xx = 0; xx < undoField.length; xx++) {
            for (int yy = 0; yy < undoField[0].length; yy++) {
                if (undoField[xx][yy] == null) {
                    field[xx][yy] = null;
                } else {
                    field[xx][yy] = new Component(xx, yy, undoField[xx][yy].getValue());
                }
            }
        }
    }

    public void clearGrid() {
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                field[xx][yy] = null;
            }
        }
    }

    public void clearUndoGrid() {
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                undoField[xx][yy] = null;
            }
        }
    }
}
