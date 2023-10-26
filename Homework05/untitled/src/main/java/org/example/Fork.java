package org.example;

public class Fork {
    private boolean using;
    private int id;

    public Fork(int id) {
        this.id = id;
    }

    public boolean isUsing() {
        return using;
    }

    public void setUsing(boolean using) {
        this.using = using;
    }

    @Override
    public String toString() {
        return "" + id;
    }
}
