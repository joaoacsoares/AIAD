package Scenery.Elements;

import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.HashMap;
import java.util.Map;

public class Flame {
    static public String TYPE = "fire";
    static String POSITION = "position";

    ISpaceObject flame ;

    public Flame(ISpaceObject flame){
        this.flame = flame;
    }

    static public Map createFlame( IVector2 pos){
        Map flame = new HashMap();
        flame.put("position", pos);
        flame.put("intensity", 1);
        return flame;
    }

    static public Map createFlame( int x , int y){
        Map flame = new HashMap();
        flame.put("position", new Vector2Int(x,y));
        flame.put("intensity", 1);
        return flame;
    }
    public IVector2 getPosition(){
        return (IVector2 )flame.getProperty(Flame.POSITION);
    }
}
