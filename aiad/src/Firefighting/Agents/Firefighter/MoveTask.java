package Firefighting.Agents.Firefighter;


import Scene.Elements.Nature.Fire;
import Scene.Elements.Firefighter;
import jadex.bridge.service.types.clock.IClockService;
import jadex.extension.envsupport.environment.AbstractTask;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.SpaceObject;
import jadex.extension.envsupport.environment.space2d.Grid2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.Set;

/**
 *  Move an object towards a destination.
 */
public class MoveTask extends AbstractTask
{
    //-------- constants --------

    /** The task name. */
    public static final String	PROPERTY_TYPENAME = "move";

    /** The destination property. */
    public static final String	PROPERTY_DESTINATION = "destination";

    /** The speed property of the moving object (units per second). */
    public static final String	PROPERTY_SPEED	= "speed";

    /** The Firefighter */
    public static final String	PROPERTY_FIREFIGHTER	= "firefighter";

    //-------- IObjectTask methods --------

    /**
     *  Executes the task.
     *  @param space	The environment in which the task is executing.
     *  @param obj	The object that is executing the task.
     *  @param progress	The time that has passed according to the environment executor.
     */
    public void execute(IEnvironmentSpace space, ISpaceObject obj, long progress, IClockService clock)
    {


        IVector2	destination	= (IVector2)getProperty("destination");
        IVector2	loc	= (IVector2)obj.getProperty(Firefighter.PROP_POSITION);
        Grid2D grid = (Grid2D) space;
       // int	speed	= ((Integer)obj.getProperty(Firefighter.PROP_SPEED)).intValue();


        // Deteta fogos a sua volta
        String[] arr = {Fire.TYPE};
        Set<SpaceObject> neighbours = grid.getNearGridObjects(loc, 1, arr);
        if ( loc == null || destination == null || neighbours.size() > 0){
            setFinished(space, obj, true);
            return;
        }

        // Gera vetor de movimento do Bombeiro
        int dirX = 0;
        int dirY = 0;



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

    public boolean isNearFire(Grid2D space, IVector2 pos) {
        if (space.getSpaceObjectsByGridPosition(new Vector2Double(pos.getXAsDouble()+1, pos.getYAsDouble()), Fire.TYPE) != null ||
                space.getSpaceObjectsByGridPosition(new Vector2Double(pos.getXAsDouble()-1, pos.getYAsDouble()), Fire.TYPE) != null ||
                space.getSpaceObjectsByGridPosition(new Vector2Double(pos.getXAsDouble(), pos.getYAsDouble()+1), Fire.TYPE) != null ||
                space.getSpaceObjectsByGridPosition(new Vector2Double(pos.getXAsDouble(), pos.getYAsDouble()-1), Fire.TYPE) != null ||
                space.getSpaceObjectsByGridPosition(new Vector2Double(pos.getXAsDouble()+1, pos.getYAsDouble()-1), Fire.TYPE) != null ||
                space.getSpaceObjectsByGridPosition(new Vector2Double(pos.getXAsDouble()-1, pos.getYAsDouble()+1), Fire.TYPE) != null ||
                space.getSpaceObjectsByGridPosition(new Vector2Double(pos.getXAsDouble()+1, pos.getYAsDouble()+1), Fire.TYPE) != null ||
                space.getSpaceObjectsByGridPosition(new Vector2Double(pos.getXAsDouble()-1, pos.getYAsDouble()-1), Fire.TYPE) != null)
        {
            return true;
        }

        return false;
    }
}