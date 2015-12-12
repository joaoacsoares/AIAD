package Scene.Elements.Nature;

import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tree {

    private static final int FUEL       = 60;
    private static final int RESISTANCE = 5;

    static public String TYPE   = "tree";
    static public String BURNING = "Burning";
    static public String BURNT = "Burnt";
    static public String NOT_BURNING = "NotBurning";

    static String CURRENT_STATE = "state";
    static String BURN_TIME     = "burningTime";
    static String FUEL_TIME     = "fuelTime";
    static String FIRE_RESIST   = "fireResistance";
    static String DENSITY       = "density";
    static String POSITION      = "position";

    ISpaceObject tree;

    static public Random rnd = new Random();
    static public  Map createTree( String s, int  d, int x , int y){
        Map tree = new HashMap();
        tree.put("position" , new Vector2Int(x,y));
        tree.put("density"          ,  d );
        tree.put("fireResistance"   , (d+1)*RESISTANCE );
        tree.put("fuelTime"         , (d+1)*FUEL );
        tree.put("burningTime"      , 0 );
        tree.put("state"            , Tree.NOT_BURNING);

        return tree;
    }

    public Tree(ISpaceObject tree){
        this.tree = tree;
    }

    public void initFireResistance(){
        tree.setProperty(FIRE_RESIST, (getDensity()+1) * 5);
    }

    // Getters and Setters
    public String   getState(){
        return tree.getProperty(Tree.CURRENT_STATE).toString();
    }
    public void     setState(String s){
        tree.setProperty(Tree.CURRENT_STATE,s);
    }
    public IVector2 getPosition(){
        return (IVector2 )tree.getProperty(Tree.POSITION) ;
    }
    public void     setPosition(IVector2 p){
        tree.setProperty(Tree.POSITION, p);
    }
    public int      getBurnTime(){
        return Integer.parseInt(tree.getProperty(Tree.BURN_TIME).toString());
    }
    public void     setBurnTime(int b){
        tree.setProperty(Tree.BURN_TIME, b);
    }
    public int      getFuelTime(){
        return Integer.parseInt(tree.getProperty(Tree.FUEL_TIME).toString()) ;
    }
    public void     setFuelTime(int f){
        tree.setProperty(Tree.FUEL_TIME, f );
    }
    public int      getFireResist(){
        return Integer.parseInt(tree.getProperty(Tree.FIRE_RESIST).toString()) ;
    }
    public void     setFireResist(int r){
        tree.setProperty(Tree.FIRE_RESIST, r );
    }
    public int      getDensity(){
        return Integer.parseInt(tree.getProperty(Tree.DENSITY).toString());
    }
    public void     setDensity(int d){
        tree.setProperty(Tree.DENSITY, d);
    }

    // Utils
    static public int randomDensity(){
        return  rnd.nextInt(3)  ;
    }
}
