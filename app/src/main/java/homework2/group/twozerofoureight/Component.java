package homework2.group.twozerofoureight;

public class Component extends Rect {
    private int value;
    private Component[] mergedFrom = null;

    public Component(int x, int y, int value) {
        super(x, y);
        this.value = value;
    }

    public Component(Rect rect, int value) {
        super(rect.getX(), rect.getY());
        this.value = value;
    }

    public void updatePosition(Rect rect) {
        this.setX(rect.getX());
        this.setY(rect.getY());
    }

    public int getValue() {
        return this.value;
    }

    public Component[] getMergedFrom() {
       return mergedFrom;
    }

    public void setMergedFrom(Component[] component) {
        mergedFrom = component;
    }
}
