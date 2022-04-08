package daos;

import data.MockData;

public class CollectionDAO implements ICollectionDAO{
    /**
     * This method returns the collection list;
     * @return Object[]. Object[0] is a String which is a server message that indicates if the request has been
     * processed successfully and Object[1] is a list of Collection.
     */
    @Override
    public Object[] getCollectionList() {
        Object[] returnObject = new Object[2];

        String message = "ok";

        returnObject[0] = message;

        returnObject[1] = MockData.getCollections();

        return returnObject;
    }
}
