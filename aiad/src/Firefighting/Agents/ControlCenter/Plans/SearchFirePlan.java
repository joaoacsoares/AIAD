package Firefighting.Agents.ControlCenter.Plans;

import Firefighting.Agents.ControlCenter.ControlCenterBDI;
import Scene.Elements.ControlCenter;
import Scene.Elements.Nature.Fire;
import Scene.Elements.Firefighter;
import Scene.Elements.Nature.Tree;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.PlanAPI;
import jadex.bdiv3.annotation.PlanBody;
import jadex.bdiv3.annotation.PlanCapability;
import jadex.bdiv3.runtime.IPlan;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;

import java.util.ArrayList;



@Plan
public class SearchFirePlan {

    @PlanAPI
    private IPlan plan;

    @PlanCapability
    private ControlCenterBDI controlcenter;

    @PlanBody
    public void body() throws InterruptedException {

        ISpaceObject[] fire = controlcenter.getSpace().getSpaceObjectsByType(Fire.TYPE);

        ArrayList<IVector2> fireToExtinguish = new ArrayList<IVector2>();

        for (ISpaceObject f : fire) {
            fireToExtinguish.add((IVector2)f.getProperty("position"));
            if ( fireToExtinguish.size() >= ControlCenterBDI.FIRE_FIGHT_COUNT){
                //break;
            }
        }

        if(fire.length == 0){
            ISpaceObject[] trees        = controlcenter.getSpace().getSpaceObjectsByType(Tree.TYPE);
            ISpaceObject[] firefighters = controlcenter.getSpace().getSpaceObjectsByType(Firefighter.TYPE);
            int burntTrees =0;
            int notBurntTrees =0;
            int extinguishedByFfFires=0;

            ArrayList<Integer> extinguishedFires = new ArrayList<Integer>();

            int firesExtinguished = 0;
            for (ISpaceObject tree_ : trees) {
                Tree tree = new Tree(tree_);
                if ( tree.getState() == Tree.NOT_BURNING){
                    notBurntTrees++;
                }else{
                    burntTrees++;
                }
            }
            for (ISpaceObject firefighter_ : firefighters) {
                Firefighter firefighter = new Firefighter(firefighter_);
                extinguishedFires.add(firefighter.getExtinguished());
                extinguishedByFfFires += firefighter.getExtinguished();
                firesExtinguished += firefighter.getExtinguished();
            }


            System.out.println(
                    "[ControlCenter] O fogo foi extinto!\n"  +
                            "Foram Extintos : "+firesExtinguished+" fogos\n"+
                            "   Firefighters : " + extinguishedByFfFires +"\n"+
                            "Arderam        : "+burntTrees+" arvores\n"+
                            "Nao arderam    : "+notBurntTrees+ " arvores\n"
            );
            for (int i = 0; i < extinguishedFires.size(); i++){
                int fExtinguished = extinguishedFires.get(i);
                System.out.println("    Firefighter("+i+")" + "-Extingiu: " + fExtinguished);
            }


            controlcenter.agent.killAgent();

        }

        Space2D space = controlcenter.getSpace();
        ControlCenter cc = new ControlCenter( space.getSpaceObjectsByType(ControlCenter.TYPE)[0] );
        //cc.setFireList(fireToExtinguish);
        cc.setFireList(fireToExtinguish);
    }

}
