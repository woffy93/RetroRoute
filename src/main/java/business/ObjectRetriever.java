package business;

import business.exceptions.WrongRoomIdException;
import model.Room;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

public class ObjectRetriever {

    private static final String NEWLINE = "\n";
    private static int WHITESPACES_N;
    private final Set<Room> rooms;
    Set<String> retrievedObjects;
    private Map<Integer, Room> roomsHashMap; // maps room.id to room object
    private Set<String> wantedObjects;

    public ObjectRetriever(List<Room> rooms) {
        this.rooms = new HashSet<>();
        this.rooms.addAll(rooms);
        WHITESPACES_N = calcWhiteSpaces(this.rooms);
        initializeRoomsHashMap();
    }

    public String retrieve(Integer startRoomId, List<String> wantedObjects) throws WrongRoomIdException {
        this.wantedObjects = new HashSet<>();
        this.retrievedObjects = new HashSet<>();
        this.wantedObjects.addAll(wantedObjects);

        StringBuilder sb = new StringBuilder();
        insertHeader(sb);

        if (roomsHashMap.containsKey(startRoomId)) {
            dfs(roomsHashMap.get(startRoomId), null, sb);
        } else {
            throw new WrongRoomIdException("Start room id is not present in provided rooms");
        }
        if (retrievedObjects.containsAll(wantedObjects)) {
            System.out.println(sb.toString());
            return sb.toString();
        } else {
            String message = "It is not possible to retrieve all the specified objects as at least one of them is not present in the rooms";
            System.out.println(message);
            return message;
        }
    }

    private void dfs(Room room, Room parent, StringBuilder sb) throws WrongRoomIdException {
        List<String> retirevedInRoom = retrieveObjectsInRoom(room);
        retrievedObjects.addAll(retirevedInRoom);
        printRoomInfo(room, retirevedInRoom, sb);
        List<Room> neighbours = getNeighbours(room);
        room.setVisited(true); //ok we can discuss, design-wise, if visited is property of the room object but it saves me from adding another data structure and seems cleaner
        for (Room neighbor : neighbours) {
            if (neighbor != null && !neighbor.isVisited() && !retrievedObjects.containsAll(wantedObjects)) {
                dfs(neighbor, room, sb);
            }
        }
        if (parent != null && !retrievedObjects.containsAll(wantedObjects)) { // arrived to a dead end, going back (arrived to a leaf)
            printRoomInfo(parent, null, sb);
        }
    }


    private void initializeRoomsHashMap() {
        roomsHashMap = new HashMap<>();
        for (Room room : rooms) {
            roomsHashMap.put(room.getId(), room);
        }
    }


    private List<Room> getNeighbours(Room room) throws WrongRoomIdException {
        List<Room> neighbours = new ArrayList<>();
        for (Integer neighbourId : room.getNeighborsId()) {
            if (roomsHashMap.containsKey(neighbourId)) {
                neighbours.add(roomsHashMap.get(neighbourId));
            } else {
                throw new WrongRoomIdException("Tried to access a room with an id which is not present/valid");
            }
        }
        return neighbours;
    }


    /*
     * Returns a list of Strings representing the stuff we have to collect in the room
     */
    private List<String> retrieveObjectsInRoom(Room room) {
        List<String> retirevedInRoom = new ArrayList<>();
        for (String object : room.getObjects()) {
            if (wantedObjects.contains(object)) {
                retirevedInRoom.add(object);
            }
        }
        return retirevedInRoom;
    }


    private void insertSpacing(StringBuilder sb, String word) {
        for (int i = 0; i < (WHITESPACES_N - word.length()); i++) {
            sb.append(" ");
        }
    }

    /*
     * Calculates the number of spaces needed for correct spacing, it could become useless if we decide to have a maximum length for attributes' values
     */
    private int calcWhiteSpaces(Set<Room> rooms) {
        int n = 0;
        for (Room room : rooms) {
            if (room.getId() > n) {
                n = room.getId();
            }
        }
        for (Room room : rooms) {
            if (room.getObjects().toString().length() > n) {
                n = room.getObjects().toString().length();
            }
        }

        for (Room room : rooms) {
            if (room.getName().length() > n) {
                n = room.getName().length();
            }
        }

        return n + 3; // longest word + extra spaces
    }


    /*
     * Adds the header as per wanted output format to StringBuilder
     */
    private void insertHeader(StringBuilder sb) {
        String id = "ID";
        String name = "Name";
        String objects = "Objects collected";
        sb.append(id);
        insertSpacing(sb, id);
        sb.append(name);
        insertSpacing(sb, name);
        sb.append(objects);
        insertSpacing(sb, objects);
        sb.append(NEWLINE);
        sb.append("---------------------------------------------------------------");
        sb.append(NEWLINE);
    }

    /*
     * Adds a line to StringBuilder with room's id, name and retrieved objects
     */
    private void printRoomInfo(Room room, List<String> retirevedInRoom, StringBuilder sb) {
        sb.append(room.getId());
        insertSpacing(sb, room.getId().toString());
        sb.append(room.getName());
        insertSpacing(sb, room.getName());
        if (CollectionUtils.isNotEmpty(retirevedInRoom)) {
            for (String obj : retirevedInRoom) {
                sb.append(obj);
                sb.append(" ");
            }
        } else {
            sb.append("None");
        }
        sb.append(NEWLINE);
    }
}
