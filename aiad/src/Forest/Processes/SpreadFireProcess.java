package Forest.Processes;

import java.util.*;

import Scene.Elements.Nature.Fire;
import Scene.Elements.Nature.Tree;
import Scene.Elements.Nature.Wind;
import jadex.bridge.service.types.clock.IClockService;
import jadex.commons.SimplePropertyObject;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.ISpaceProcess;
import jadex.extension.envsupport.environment.space2d.Grid2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;

public class SpreadFireProcess extends SimplePropertyObject implements ISpaceProcess {

    private ArrayList<ISpaceObject> getNeighbors(IVector2 pos, Grid2D grid) {

        int x = pos.getXAsInteger(); int y = pos.getYAsInteger();
        int w = grid.getAreaSize().getXAsInteger()-1; int h = grid.getAreaSize().getYAsInteger()-1;

        ArrayList<ISpaceObject> temp = new ArrayList<ISpaceObject>();

        Wind wind = new Wind(grid.getSpaceObjectsByType(Wind.TYPE)[0]);

        Random rnd = new Random();
        rnd.nextDouble();

        double wind_prob = 0.3;

        /*
        |O|O|O|    |O|O|O|
        |O|X|O| or |O|X|O| or (...)
        |O|W|O|    |O|O|W|
        */

        if ( y > 0              && ( wind.getY() < 0 || rnd.nextDouble() < wind_prob ))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x,y-1)   , Tree.TYPE).toArray()[0] );

        if ( x < w && y > 0   && ( wind.getY() < 0 && wind.getX() > 0|| rnd.nextDouble() < wind_prob ))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x+1,y-1) , Tree.TYPE).toArray()[0] );

        if ( x < w            && (wind.getX() > 0 || rnd.nextDouble() < wind_prob ))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x+1,y)   , Tree.TYPE).toArray()[0] );

        if ( x < w && y < h && (wind.getX() > 0 && wind.getY() > 0 || rnd.nextDouble() < wind_prob))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x+1,y+1) , Tree.TYPE).toArray()[0] );

        if ( y < h            && (wind.getY() > 0 || rnd.nextDouble() < wind_prob))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x,y+1)   , Tree.TYPE).toArray()[0] );

        if ( x > 0 && y < h   && (wind.getX() < 0 && wind.getY() > 0 || rnd.nextDouble() < wind_prob))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x-1,y+1) , Tree.TYPE).toArray()[0] );

        if ( x > 0              && (wind.getX() < 0 || rnd.nextDouble() < wind_prob))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x-1,y)   , Tree.TYPE).toArray()[0] );

        if ( x > 0 && y > 0     && (wind.getX() < 0 && wind.getY() < 0 || rnd.nextDouble() < wind_prob))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x-1,y-1) , Tree.TYPE).toArray()[0]);

        return temp;
    }

    @Override
    public void execute(IClockService arg0, IEnvironmentSpace arg1) {
        Grid2D space = (Grid2D) arg1;
        ISpaceObject[] fire = space.getSpaceObjectsByType("fire");

        for (ISpaceObject f : fire) {
            Fire flame = new Fire(f);
            IVector2 pos    = flame.getPosition();

            ISpaceObject tree_   = (ISpaceObject) ((Grid2D)arg1).getSpaceObjectsByGridPosition(pos,Tree.TYPE).toArray()[0];

            Tree tree = new Tree(tree_);

            if ( tree.getBurnTime() == tree.getFuelTime() ){
                arg1.destroySpaceObject(f.getId());
                tree.setState(Tree.BURNT);
                continue;
            }

            tree.setBurnTime(tree.getBurnTime() + 1);

            ArrayList<ISpaceObject> neighbors = getNeighbors( flame.getPosition(), space);

            for (ISpaceObject neigh : neighbors ) {
                Tree neighTree = new Tree(neigh);
                if ( neighTree.getFireResist() > 0)
                neighTree.setFireResist(neighTree.getFireResist() -1 );
            }
        }

        ISpaceObject[] forest = space.getSpaceObjectsByType("tree");
        for (ISpaceObject tree_ : forest) {
            Tree tree = new Tree(tree_);

            if ( tree.getState().equals(Tree.BURNING) || tree.getState().equals(Tree.BURNT)){
                continue;
            }

            if (  tree.getFireResist() <= 0 ) {
                tree.setState(Tree.BURNING);
                space.createSpaceObject(Fire.TYPE, Fire.createFlame(tree.getPosition()), null);
            }

        }

    }

    @Override
    public void shutdown(IEnvironmentSpace arg0) {

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void start(IClockService arg0, IEnvironmentSpace arg1) {

        Grid2D space = (Grid2D) arg1;


    }

}