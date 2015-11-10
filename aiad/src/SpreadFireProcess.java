
import java.util.*;

import Scenery.Elements.Flame;
import Scenery.Elements.Tree;
import Scenery.Elements.Wind;
import jadex.bridge.service.types.clock.IClockService;
import jadex.commons.SimplePropertyObject;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.ISpaceProcess;
import jadex.extension.envsupport.environment.space2d.Grid2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;

public class SpreadFireProcess extends SimplePropertyObject implements ISpaceProcess {

    private ArrayList<ISpaceObject> getNeighbours(IVector2 pos, Grid2D grid) {
        int x = pos.getXAsInteger();
        int y = pos.getYAsInteger();

        int w = grid.getAreaSize().getXAsInteger();
        int h = grid.getAreaSize().getYAsInteger();
        ArrayList<ISpaceObject> temp = new ArrayList<ISpaceObject>();

        Wind wind = new Wind(grid.getSpaceObjectsByType(Wind.TYPE)[0]);
        Random rnd = new Random();
        double prob = 0.2;
        //Adding all the Neighbours
        //------------------------------
        rnd.nextDouble();
        if ( y > 0              && ( wind.getY() < 0 || rnd.nextDouble() < prob ))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x,y-1)   , Tree.TYPE).toArray()[0] );

        if ( x < w-1 && y > 0   && ( wind.getY() < 0 && wind.getX() > 0|| rnd.nextDouble() < prob ))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x+1,y-1) , Tree.TYPE).toArray()[0] );

        if ( x < w-1            && (wind.getX() > 0 || rnd.nextDouble() < prob ))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x+1,y)   , Tree.TYPE).toArray()[0] );

        if ( x < w-1 && y < h-1 && (wind.getX() > 0 && wind.getY() > 0 || rnd.nextDouble() < prob))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x+1,y+1) , Tree.TYPE).toArray()[0] );

        if ( y < h-1            && (wind.getY() > 0 || rnd.nextDouble() < prob))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x,y+1)   , Tree.TYPE).toArray()[0] );

        if ( x > 0 && y < h-1   && (wind.getX() < 0 && wind.getY() > 0 || rnd.nextDouble() < prob))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x-1,y+1) , Tree.TYPE).toArray()[0] );

        if ( x > 0              && (wind.getX() < 0 || rnd.nextDouble() < prob))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x-1,y)   , Tree.TYPE).toArray()[0] );

        if ( x > 0 && y > 0     && (wind.getX() < 0 && wind.getY() < 0 || rnd.nextDouble() < prob))
            temp.add(   (ISpaceObject)grid.getSpaceObjectsByGridPosition(new Vector2Double(x-1,y-1) , Tree.TYPE).toArray()[0]);

        return temp;
    }

    @Override
    public void execute(IClockService arg0, IEnvironmentSpace arg1) {
        Grid2D space = (Grid2D) arg1;
        ISpaceObject[] fire = space.getSpaceObjectsByType("fire");

        /*int newDelta = (int) ( arg0.getDelta() - Math.log(arg0.getTick())*50);
        if ( newDelta <= 1000 )
        {newDelta = 1000;}
        arg0.setDelta(500);
*/
       for (ISpaceObject flame_ : fire) {
            Flame flame = new Flame(flame_);
            IVector2 pos    = flame.getPosition();

            ISpaceObject tree_   = (ISpaceObject) ((Grid2D)arg1).getSpaceObjectsByGridPosition(pos,Tree.TYPE).toArray()[0];

            Tree tree = new Tree(tree_);

            if (tree.getBurnTime() == tree.getFuelTime()){
                arg1.destroySpaceObject( flame_.getId()  );
                tree.setState(Tree.TREE_BURNED);
                continue;
            }

            tree.setBurnTime(tree.getBurnTime() + 1);

            ArrayList<ISpaceObject> neighbours = getNeighbours(flame.getPosition(), space);

            for (ISpaceObject neigh : neighbours ) {
                Tree neighTree = new Tree(neigh);
                if ( neighTree.getHp() > 0)
                neighTree.setHp(neighTree.getHp() -1 );
            }

        }

        ISpaceObject[] forest = space.getSpaceObjectsByType("tree");
        for (ISpaceObject tree_ : forest) {
            Tree tree = new Tree(tree_);

            if ( tree.getState().equals(Tree.TREE_BURNING) || tree.getState().equals(Tree.TREE_BURNED)){
                continue;
            }

            if (  tree.getHp() <= 0 ) {
                tree.setState(Tree.TREE_BURNING);
                space.createSpaceObject(Flame.TYPE, Flame.createFlame(tree.getPosition()), null);
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