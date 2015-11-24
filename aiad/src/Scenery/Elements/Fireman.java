package Scenery.Elements;

import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.HashMap;
import java.util.Map;

public class Fireman {
    static public String TYPE = "fireman";
    static public String POSITION = "position";
    static public String SPEED = "speed";
    static public String VISION = "vision";
    static public String STATE  = "state";

    ISpaceObject fireman;

    public static Map createFireman(int x, int y, int vision, int speed) {
        Map fireman = new HashMap();
        fireman.put("position" , new Vector2Int(x,y));
        fireman.put("vision", vision);
        fireman.put("speed", speed);
        fireman.put("state", Fireman.STATE);

        return fireman;
    }

    public Fireman(ISpaceObject fireman){
        if ( ! fireman.getType().equals(Fireman.TYPE) ){
            System.out.println("Error.");
        }
        this.fireman = fireman;
    }

    public IVector2 getPosition(){
        return (IVector2 )fireman.getProperty(Fireman.POSITION) ;
    }
    public int getVision(){
        return Integer.parseInt(fireman.getProperty(Fireman.VISION).toString());
    }
    public int getSpeed(){
        return Integer.parseInt(fireman.getProperty(Fireman.SPEED).toString());
    }
    public String getState(){
        return fireman.getProperty(Fireman.STATE).toString();
    }


    public void setPosition(IVector2 pos){
        fireman.setProperty(Fireman.POSITION, pos);
    }
}
