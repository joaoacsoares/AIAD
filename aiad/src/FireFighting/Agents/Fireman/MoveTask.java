package FireFighting.Agents.Fireman;


import Scenery.Elements.Fireman;
import Scenery.Elements.Flame;
import Scenery.Elements.Tree;
import jadex.bridge.service.types.clock.IClockService;
import jadex.extension.envsupport.environment.AbstractTask;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.SpaceObject;
import jadex.extension.envsupport.environment.space2d.Grid2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 *  Move an object towards a destination.
 */
public class MoveTask extends AbstractTask
{
    public static final String	PROPERTY_TYPENAME = "move";
    public static final String	PROPERTY_DESTINATION = "destination";
    public static final String	PROPERTY_SPEED	= "speed";
    public static final String  PROPERTY_FIREMAN = "fireman";

    public void execute(IEnvironmentSpace space, ISpaceObject obj, long progress, IClockService clock)
    {

        IVector2	destination	= (IVector2)getProperty("destination");
        IVector2	loc	= (IVector2)obj.getProperty(Fireman.POSITION);
        Grid2D grid = (Grid2D) space;

        String[] arr = {Flame.TYPE};
        Set<SpaceObject> neighbours = grid.getNearGridObjects(loc, 1, arr);

        // Gera vetor de movimento do Bombeiro
        int dirX = 0;
        int dirY = 0;

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if      (loc.getXAsInteger() < destination.getXAsInteger()){ dirX =  1 /*speed*/; }
        else if (loc.getXAsInteger() > destination.getXAsInteger()){ dirX = -1 /*speed*/; }
        else  {dirX = 0;}

        if      (loc.getYAsInteger() < destination.getYAsInteger()){ dirY =  1 /*speed*/; }
        else if (loc.getYAsInteger() > destination.getYAsInteger()){ dirY = -1 /*speed*/; }
        else  {dirY = 0;}


        IVector2 newloc = loc.copy().add(new Vector2Int(dirX,dirY));

        // Executa movimento

        ((Grid2D)space).setPosition(obj.getId(), newloc);
        if( newloc.equals(destination) ) {
            setFinished(space, obj, true);
        }
    }

}