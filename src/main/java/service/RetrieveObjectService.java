package service;

import business.JsonParser;
import business.ObjectRetriever;
import business.exceptions.InvalidJsonInputException;
import business.exceptions.WrongRoomIdException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.Room;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class RetrieveObjectService {
    public static String retrieve(String jsonString) throws InvalidJsonInputException, WrongRoomIdException {
        JsonElement jelement = new com.google.gson.JsonParser().parse(jsonString);
        JsonObject jsonObject = jelement.getAsJsonObject();
        if (jsonObject.has("startRoomId") && jsonObject.has("wantedObjects") && jsonObject.has("data")) {
            List<Room> rooms = JsonParser.parseDataJson(jsonObject.get("data"));
            ObjectRetriever objectRetriever = new ObjectRetriever(rooms);
            return objectRetriever.retrieve(jsonObject.get("startRoomId").getAsInt(), JsonParser.parseWantedObjects(jsonObject));
        } else {
            throw new InvalidJsonInputException("received input not as required");
        }
    }
}
