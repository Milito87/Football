package org.izv.ad.jmunoz.footballdb.model.entity;

public class Position {

    public long id;
    public String position;

    public Position() {
    }

    public Position(long id, String position) {
        this.id = id;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", position='" + position + '\'' +
                '}';
    }
}
