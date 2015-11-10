package Scenery.Elements;

import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tree {

    static public String TYPE = "tree";

    static String STATE = "state";
    static String BURNTIME = "burnTime";
    static String FUELTIME = "fuelTime";
    static String HP = "hp";
    static String DENSITY = "density";
    static String RISK = "risk";
    static String POSITION = "position";


    static public String TREE_BURNING = "Burning";
    static public String TREE_BURNED  = "Burned";
    static public String TREE_NORMAL = "Normal";


    ISpaceObject tree;


    static public Random rnd = new Random();

    public Tree(ISpaceObject tree_) {
        this.tree = tree_;
    }

    static public  Map createTree(int  dens ,int x , int y){
        Map tree = new HashMap();
        tree.put("position", new Vector2Int(x,y));
        tree.put("risk", 0);
        tree.put("density", dens );
        tree.put("hp", (dens+1)*5 );
        tree.put("fuelTime", (dens+3)*20 );
        tree.put("burnTime", 0 );
        tree.put("state", Tree.TREE_NORMAL);
        return tree;
    }

    static public int getRandomDensity(){
        return  rnd.nextInt(3)  ;
    }



    public String getState(){
        return tree.getProperty(Tree.STATE).toString();
    }
    public int getBurnTime(){
        return Integer.parseInt(tree.getProperty(Tree.BURNTIME).toString());
    }
    public int getFuelTime(){
        return Integer.parseInt(tree.getProperty(Tree.FUELTIME).toString()) ;
    }
    public int getHp(){
        return Integer.parseInt(tree.getProperty(Tree.HP).toString()) ;
    }
    public int getDensity(){
        return Integer.parseInt(tree.getProperty(Tree.DENSITY).toString());
    }
    public Double getRisk(){
        return Double.parseDouble(tree.getProperty(Tree.RISK).toString());
    }
    public IVector2 getPosition(){
        return (IVector2 )tree.getProperty(Tree.POSITION) ;
    }


    public void setState(String state){
        tree.setProperty(Tree.STATE,state);
    }
    public void setBurnTime(int newBurn){
        tree.setProperty(Tree.BURNTIME , newBurn);
    }
    public void setFuelTime(int fuelTime){
        tree.setProperty(Tree.FUELTIME , fuelTime );
    }
    public void setHp(int hp){
        tree.setProperty(Tree.HP , hp );
    }
    public void setDensity(int density){
        tree.setProperty(Tree.DENSITY , density);
    }
    public void setRisk(double risk){
        tree.setProperty(Tree.RISK, risk);
    }
    public void setPosition(IVector2 pos){
        tree.setProperty(Tree.POSITION , pos);
    }


}
