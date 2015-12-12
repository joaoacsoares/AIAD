package Scene.Elements.Nature;

import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.HashMap;
import java.util.Map;

public class Fire {
    static public String TYPE = "fire";
    static String PROP_POSITION     = "position";


    static public Map createFlame( int x , int y){
        Map flame = new HashMap();
        flame.put("position", new Vector2Int(x,y));
        flame.put("intensity", 1);
        return flame;
    }
    static public Map createFlame( IVector2 pos){
        Map flame = new HashMap();
        flame.put("position", pos);
        flame.put("intensity", 1);
        return flame;
    }



    ISpaceObject flame ;
    public Fire(ISpaceObject flame){
        this.flame = flame;
    }

    public IVector2 getPosition(){
        return (IVector2 )flame.getProperty(Fire.PROP_POSITION) ;
    }
}
