package Firefighting.Agents.Firefighter.Plans;

import Firefighting.Agents.Firefighter.FirefighterBDI;
import Firefighting.Agents.Firefighter.Goals.MoveToFireGoal;
import Scene.Elements.ControlCenter;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.PlanBody;
import jadex.bdiv3.annotation.PlanCapability;
import jadex.extension.envsupport.math.IVector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@Plan
public class SearchFirePlan {

    @PlanCapability
    private FirefighterBDI agent;

    @PlanBody
    public void body(){
        if ( agent.figthing){
            return;
        }

        //System.out.println("[FireFighter-"+ agent.id +"]" + "Estou à procura de um fogo para apagar | SearchFirePlan");

        Random rnd = new Random();
        ControlCenter cc =  new ControlCenter( agent.getSpace().getSpaceObjectsByType(ControlCenter.TYPE)[0] );
        ArrayList<IVector2> fireDist = cc.getFireList();


        if (fireDist != null && fireDist.size() > 0){
            agent.figthing = true;
            //System.out.println("[FireFighter-"+ agent.id +"]" + "Encontrei um fogo para apagar  | SearchFirePlan");
            agent.agent.dispatchTopLevelGoal(new MoveToFireGoal( findFireToFight( cc ,agent.getPosition() ) ));
        }



    }

    public IVector2 findFireToFight(  ControlCenter cc , IVector2 currentPos ){
        /*TODO : If no fire exists this function will crash the agent - Atirar Excepçao de plano falhado*/

        ArrayList<IVector2> fires = (ArrayList<IVector2>) cc.getFireList().clone();
        ArrayList<IVector2> assets= (ArrayList<IVector2>) cc.getBuildingsList().clone();

        boolean assets_f = false;

        cc.setPositionsToExtinguish(agent.id, null);
        //Removing fires already allocated
        IVector2[] allocations = cc.getPositionsToExtinguish();
        fires.removeAll( new ArrayList<IVector2>(Arrays.asList( allocations )) );
        //Removing assets already allocated
        IVector2[] assetsAllocs = cc.getPositionsToProtect();

        //If i'm already protecting an asset , i will continue protecting it
        if ( assets.size() > 0 &&   assetsAllocs.length > agent.id && assetsAllocs[agent.id] != null){
            if( assets.contains(assetsAllocs[agent.id])) {
                return assetsAllocs[agent.id];
            }
        }

        //Checking if there is an unprotected asset
        assets.removeAll( new ArrayList<IVector2>(Arrays.asList(assetsAllocs )) );


        //If there are still unprotected assets the firefighter will analise only assets
        if ( !assets.isEmpty() ){
            fires = assets;
            assets_f = true;
        }

        //The firefighter will choose the nearest fire to extinguish
        double minDist = Double.MAX_VALUE;
        IVector2 chosenOne = null;
        for (IVector2 fire : fires){
            double dist = fire.getDistance(currentPos).getAsDouble();
            if (dist < minDist ){
                chosenOne = fire;
                minDist = dist;
            }
        }
        if ( fires.isEmpty() ){
            chosenOne = agent.getPosition();
        }
        if ( assets_f ){
            cc.setPositionsToProtect(agent.id, chosenOne);
        }
        else {
            cc.setPositionsToExtinguish(agent.id, chosenOne);
        }

        return chosenOne;
    }

}
