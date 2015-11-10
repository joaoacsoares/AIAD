package Scenery.Elements;

import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.HashMap;
import java.util.Map;


public class Wind {
    static public String TYPE = "wind";

    static public Map createWind(int x , int y){
        Map wind = new HashMap();
        wind.put("x",x);
        wind.put("y",y);
        return wind;
    }

    ISpaceObject wind;

    public Wind(ISpaceObject wind){
        this.wind = wind;
    }

    public int getX(){
        return  Integer.parseInt( wind.getProperty("x").toString() );
    }

    public int getY(){
        return Integer.parseInt( wind.getProperty("y").toString() );
    }

    public void  setX(int x){
        wind.setProperty("x",x);
    }

    public void setY(int y){
        wind.setProperty("y",y);
    }
}
