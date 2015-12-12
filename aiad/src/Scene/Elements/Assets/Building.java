package Scene.Elements.Assets;

import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.*;

public class Building {
    static public String TYPE = "building";
    static String POSITION = "position";

    static public Map createBuilding( int x , int y){
        Map building = new HashMap();
        Random r = new Random();
        building.put("position", new Vector2Int(x,y));
        return building;
    }
    static public Map createBuilding( IVector2 pos){
        Map building = new HashMap();
        Random r = new Random();
        building.put("position", pos);
        return building;
    }


    ISpaceObject building ;
    public Building(ISpaceObject building){
        this.building = building;
    }
    public IVector2 getPosition(){
        return (IVector2 )building.getProperty(Building.POSITION) ;
    }
}
