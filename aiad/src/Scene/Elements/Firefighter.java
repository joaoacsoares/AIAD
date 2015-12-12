package Scene.Elements;

import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Firefighter {
    static public String    TYPE = "fighter";

    static public String PROP_POSITION     = "position";
    static public String PROP_VISION       = "vision";
    static public String PROP_SPEED        = "speed";
    static public String PROP_STATE        = "state";
    static public String PROP_EXTINGUISHED = "extinguished"; // numero extinguido

    static public String STATE_FIGHTER_INACTIVE = "Inactive";
    static public String STATE_FIGHTER_MOVING   = "Moving";
    static public String STATE_FIGHTER_EXTING   = "Extinguishing";

    ISpaceObject fighter;

    static public  Map createFFighter( int x , int y, int vision, int speed){
        Map fighter = new HashMap();
        fighter.put("position"          , new Vector2Int(x,y));
        fighter.put("vision"            , vision);
        fighter.put("speed"             , speed);
        fighter.put("extinguished"      , 0);
        fighter.put("state"             , Firefighter.STATE_FIGHTER_INACTIVE );

        return fighter;
    }

    static public Map createFFighter(int spaceX, int spaceY){
        Random r = new Random();
        int vision = r.nextInt(3);
        int speed = r.nextInt(3);

        // A partir de que canto vao sair Bombeiros
        int corner = r.nextInt(4);
        int x = 0;
        int y = 0;

        if      (corner == 1){ x = 0; y = 0;}
        else if (corner == 2){ x = 0; y = spaceY-1;}
        else if (corner == 3){ x = spaceX-1; y = 0;}
        else if (corner == 0){ x = spaceX-1; y = spaceY-1;}

        Map fighter = new HashMap();
        fighter.put("position"          , new Vector2Int(x,y));
        fighter.put("vision"            , vision);
        fighter.put("speed"             , speed);
        fighter.put("extinguished"      , 0);
        fighter.put("state"             , Firefighter.STATE_FIGHTER_INACTIVE );

        return fighter;
    }

    public Firefighter(ISpaceObject fighter){
        this.fighter = fighter;
    }

    public IVector2 getPosition(){
        return (IVector2 )fighter.getProperty(Firefighter.PROP_POSITION) ;
    }
    public int getVision(){
        return Integer.parseInt(fighter.getProperty(Firefighter.PROP_VISION).toString());
    }
    public int getSpeed(){
        return Integer.parseInt(fighter.getProperty(Firefighter.PROP_SPEED).toString());
    }
    public String getState(){
        return fighter.getProperty(Firefighter.PROP_STATE).toString();
    }


    public void setPosition(IVector2 pos){
        fighter.setProperty(Firefighter.PROP_POSITION , pos);
    }

    public void incrementExtinguish(){
        fighter.setProperty(Firefighter.PROP_EXTINGUISHED , this.getExtinguished()+1);
    }

    public int getExtinguished(){return Integer.parseInt(fighter.getProperty(Firefighter.PROP_EXTINGUISHED).toString()); }
    public void setState(String state)      {fighter.setProperty(Firefighter.PROP_STATE, state);}
    public void setExtinguished(int ext)    {fighter.setProperty(Firefighter.PROP_EXTINGUISHED, ext);}
    public void addOneExtinguished()        { fighter.setProperty(Firefighter.PROP_EXTINGUISHED, getExtinguished() + 1);}


}
