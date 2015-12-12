package Firefighting.Agents.Firefighter.Plans;

import Firefighting.Agents.Firefighter.FirefighterBDI;
import Firefighting.Agents.Firefighter.Goals.ExtinguishFireGoal;
import Scene.Elements.Nature.Fire;
import Scene.Elements.Firefighter;
import Scene.Elements.Nature.Tree;
import jadex.bdiv3.annotation.*;
import jadex.bdiv3.runtime.IPlan;
import jadex.extension.envsupport.environment.SpaceObject;
import jadex.extension.envsupport.environment.space2d.Grid2D;
import jadex.extension.envsupport.math.IVector2;

import java.util.Set;

@Plan
public class ExtinguishFirePlan {

    @PlanCapability
    private FirefighterBDI agent;

    @PlanAPI
    private IPlan plan;

    @PlanReason
    private ExtinguishFireGoal goal;

    @PlanBody
    public void body(){

        IVector2 combatPosition = goal.getCombatPosition();

        Grid2D space = (Grid2D) agent.getSpace();
        String[] helper = {Fire.TYPE};
        Set<SpaceObject> fires = space.getNearGridObjects(combatPosition, 1, helper );
        helper[0] = Tree.TYPE;
        Set<SpaceObject> trees = space.getNearGridObjects(combatPosition, 1, helper );
        for (SpaceObject fire : fires){
            // TODO: nem isto da. mutex deve ser a unica solução
            try {
                Firefighter ff = new Firefighter(agent.getMyself());
                ff.incrementExtinguish();
                space.destroySpaceObject(fire.getId());
            }catch (Exception e){
                System.out.println("Fire was already extinguished");
            }
        }
        for (SpaceObject tree_ : trees){
            Tree tree = new Tree(tree_);
            tree.initFireResistance();
            tree.setState(Tree.NOT_BURNING);
        }
        agent.figthing = false;


    }
}
