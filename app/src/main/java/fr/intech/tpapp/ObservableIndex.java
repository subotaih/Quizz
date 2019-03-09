package fr.intech.tpapp;
import java.io.Serializable;
import java.util.Observable;

public class ObservableIndex extends Observable implements Serializable {

    private int value;

    public ObservableIndex(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        this.setChanged();
        this.notifyObservers(value);
    }

}
