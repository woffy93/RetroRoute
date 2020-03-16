package model;

import java.util.List;
import java.util.Objects;

public class Room {
    private final Integer id;
    private String name;
    private List<Integer> neighborsId;
    private List<String> objects;
    private boolean visited = false; // design-wise maybe I should have avoided this property but even if I could have avoided it it helps to keep the logic cleaner

    public Room(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getNeighborsId() {
        return neighborsId;
    }

    public void setNeighborsId(List<Integer> neighborsId) {
        this.neighborsId = neighborsId;
    }

    public List<String> getObjects() {
        return objects;
    }

    public void setObjects(List<String> objects) {
        this.objects = objects;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        Room room = (Room) o;
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Room{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", neighborsId=").append(neighborsId);
        sb.append(", objects=").append(objects);
        sb.append(", visited=").append(visited);
        sb.append('}');
        return sb.toString();
    }
}
