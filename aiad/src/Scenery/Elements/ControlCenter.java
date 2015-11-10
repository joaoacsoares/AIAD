package Scenery.Elements;

import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.math.IVector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControlCenter {
    static public String TYPE = "controlcenter";
    static public String FIRES = "fires";
    static public String BUILDINGS = "buildings";
    ISpaceObject controlcenter;

    static public Map createControlCenter(ArrayList<IVector2> positions) {
        Map cc = new HashMap();
        cc.put("fires", positions);
        cc.put("buildings", new ArrayList<IVector2>());
        return cc;
    }

    public ControlCenter(ISpaceObject cc) {
        this.controlcenter = cc;
    }
}