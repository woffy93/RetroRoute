package business;

import business.exceptions.InvalidJsonInputException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import model.Room;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonParser {
    public static List<Room> parseDataJson(JsonElement jelement) throws InvalidJsonInputException{
        try {
            JsonObject jsonObject = jelement.getAsJsonObject();
            List<Room> rooms = new ArrayList<>();
            if (jsonObject.has("rooms")) {
                JsonArray roomsArray = jsonObject.getAsJsonArray("rooms");
                for (JsonElement room : roomsArray) {
                    JsonObject roomObject = room.getAsJsonObject();
                    Room roomPojo = new Room(roomObject.get("id").getAsInt());
                    roomPojo.setNeighborsId(parseRoomNeighbors(roomObject));
                    roomPojo.setObjects(parseRoomObjects(roomObject));
                    roomPojo.setName(roomObject.get("name").getAsString());
                    rooms.add(roomPojo);
                }
                return rooms;

            } else {
                throw new InvalidJsonInputException("Trying to parse a json not corresponding to a valid input: rooms array not present");
            }
        } catch (JsonParseException e){
            throw new InvalidJsonInputException("Error in parsing json");
        }

    }

    public static List<String> parseWantedObjects(JsonObject jsonObject) {
        List<String> wantedObjects = new ArrayList<>();
        JsonArray wantedObjectsJson = jsonObject.getAsJsonArray("wantedObjects");
        for (JsonElement obj : wantedObjectsJson) {
            wantedObjects.add(obj.getAsString());
        }
        return wantedObjects;
    }

    private static List<Integer> parseRoomNeighbors(JsonObject roomJson) {
        List<Integer> neighbors = new ArrayList<Integer>();
        if (roomJson.has("north")) {
            neighbors.add(roomJson.get("north").getAsInt());
        }
        if (roomJson.has("south")) {
            neighbors.add(roomJson.get("south").getAsInt());
        }
        if (roomJson.has("east")) {
            neighbors.add(roomJson.get("east").getAsInt());
        }
        if (roomJson.has("west")) {
            neighbors.add(roomJson.get("west").getAsInt());
        }

        return neighbors;
    }

    private static List<String> parseRoomObjects(JsonObject roomJson) {
        JsonArray jsonArray = roomJson.getAsJsonArray("objects");
        ArrayList<String> objects = new ArrayList<String>();
        for(JsonElement jsonElement : jsonArray){
            JsonObject object = jsonElement.getAsJsonObject();
            objects.add(object.get("name").getAsString());
        }
        return objects;
    }

}
