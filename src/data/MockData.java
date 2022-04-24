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
        /*collections = new ArrayList<>();

        collections.add(new Collection(1, "Naruto"));
        collections.add(new Collection(2, "Naruto Shippuden"));
        collections.add(new Collection(3, "One Piece"));
        collections.add(new Collection(4, "Bleach"));
        collections.add(new Collection(5, "The Rising of Shield Hero"));
        collections.add(new Collection(6, "One Punch Man"));
        collections.add(new Collection(7, "Daemon Slayer"));
        collections.add(new Collection(8, "Comic de prueba con un titulo que probablemente sea demasiado largo para comprobar el correcto funcionamiento del programa"));*/
    }
}
