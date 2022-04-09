package data;

import model.entities.Collection;

import java.util.ArrayList;
import java.util.List;

public class MockData {
    private static List<Collection> collections;

    public static List<Collection> getCollections(){
        if(collections == null){
            populateData();
        }

        return collections;
    }

    private static void populateData(){
        collections = new ArrayList<>();

        collections.add(new Collection("Naruto"));
        collections.add(new Collection("Naruto Shippuden"));
        collections.add(new Collection("One Piece"));
        collections.add(new Collection("Bleach"));
        collections.add(new Collection("The Rising of Shield Hero"));
        collections.add(new Collection("One Punch Man"));
        collections.add(new Collection("Daemon Slayer"));
        collections.add(new Collection("Comic de prueba con un titulo que probablemente sea demasiado largo para comprobar el correcto funcionamiento del programa"));
    }
}
