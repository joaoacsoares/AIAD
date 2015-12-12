package Forest.Processes;

import java.util.ArrayList;
import java.util.Random;

import Scene.Elements.ControlCenter;
import Scene.Elements.Nature.Tree;
import Scene.Elements.Nature.Wind;
import jadex.bridge.service.types.clock.IClockService;
import jadex.commons.SimplePropertyObject;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceProcess;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;

public class CreateForestProcess extends SimplePropertyObject implements ISpaceProcess{

     private void insertTrees(Space2D space){
        double changeProb = 0.9;
        Random rand     = new Random();
        int[][] map = new  int[space.getAreaSize().getXAsInteger()][space.getAreaSize().getYAsInteger()];

        int lastVal = Tree.randomDensity();
        space.createSpaceObject(Tree.TYPE, Tree.createTree(Tree.NOT_BURNING,lastVal,0,0), null);
        map[0][0] = lastVal;
        for (int x = 1; x < space.getAreaSize().getXAsInteger(); x++){
            lastVal = map[x-1][0];
            if( rand.nextDouble() > changeProb ){
                lastVal = Tree.randomDensity();
            }
            space.createSpaceObject(Tree.TYPE ,Tree.createTree(Tree.NOT_BURNING, lastVal,x,0 ), null);
            map[x][0] = lastVal;
        }
        for(int x = 0 ; x < space.getAreaSize().getXAsInteger() ; x++) {
            for (int y = 1; y < space.getAreaSize().getYAsInteger(); y++) {
                lastVal = map[x][y - 1];
                if (rand.nextDouble() > changeProb ) {
                    lastVal = Tree.randomDensity();
                }
                space.createSpaceObject(Tree.TYPE , Tree.createTree(Tree.NOT_BURNING, lastVal , x, y), null);
                map[x][y] = lastVal;
            }
        }

    }
    @Override
    public void execute(IClockService arg0, IEnvironmentSpace arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void shutdown(IEnvironmentSpace arg0) {
        // TODO Auto-generated method stub

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void start(IClockService arg0, IEnvironmentSpace arg1) {

        Space2D space = (Space2D)arg1;
        insertTrees(space);
        space.createSpaceObject(Wind.TYPE , Wind.createWind(1,0) , null);
        space.createSpaceObject(ControlCenter.TYPE, ControlCenter.createControlCenter(new ArrayList<IVector2>()), null);
    }

}
