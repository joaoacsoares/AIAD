package Scene.Elements;

import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.math.IVector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControlCenter {
    // Type
    static public String TYPE                = "controlcenter";

    // Lists
    static public String FIRES               = "firelist";
    static public String BUILDINGS           = "buildingslist";

    //
    static public String EXTINGUISH_FIRE     = "extinguishpos";
    static public String EXTINGUISH_BUILDING = "extinguishbuildings";

    ISpaceObject controlcenter;

    static public Map createControlCenter(ArrayList<IVector2> positions){
        Map ccenter = new HashMap();
        ccenter.put("firelist" , positions);
        ccenter.put(BUILDINGS, new ArrayList<IVector2>());
        return ccenter;
    }

    public ControlCenter(ISpaceObject cc){
        this.controlcenter = cc;
    }

    public void setPositionsToExtinguish(int i, IVector2 value){
        if ( i < getPositionsToExtinguish().length) {
            getPositionsToExtinguish()[i] = value;
        }
    }

    public void setFireList(ArrayList<IVector2> fireList){
        controlcenter.setProperty(ControlCenter.FIRES, fireList);
    }

    public void setPositionsToExtinguish(IVector2[] p){
        controlcenter.setProperty(ControlCenter.EXTINGUISH_FIRE,p);
    }
    public void setBuildingsList(ArrayList<IVector2> b){
        controlcenter.setProperty(ControlCenter.BUILDINGS, b);

    }
    public void setPositionsToProtect(IVector2[] p){
        controlcenter.setProperty(ControlCenter.EXTINGUISH_BUILDING, p);
    }
    public void setPositionsToProtect(int i, IVector2 value){
        if ( i <  getPositionsToProtect().length)
            getPositionsToProtect()[i] = value;
    }

    public IVector2[] getPositionsToExtinguish(){
        return (IVector2[]) controlcenter.getProperty(ControlCenter.EXTINGUISH_FIRE);
    }

    public IVector2[] getPositionsToProtect(){
        return (IVector2[]) controlcenter.getProperty(ControlCenter.EXTINGUISH_BUILDING);
    }
    public ArrayList<IVector2> getFireList(){
        return ( ArrayList<IVector2> )controlcenter.getProperty(ControlCenter.FIRES);
    }
    public ArrayList<IVector2> getBuildingsList(){
        return ( ArrayList<IVector2> )controlcenter.getProperty(ControlCenter.BUILDINGS);
    }
}
