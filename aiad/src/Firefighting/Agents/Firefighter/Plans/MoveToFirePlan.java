package Firefighting.Agents.Firefighter.Plans;

import Firefighting.Agents.Firefighter.FirefighterBDI;
import Firefighting.Agents.Firefighter.Goals.ExtinguishFireGoal;
import Firefighting.Agents.Firefighter.Goals.MoveToFireGoal;
import Firefighting.Agents.Firefighter.MoveTask;
import Scene.Elements.Firefighter;

import java.util.HashMap;
import java.util.Map;


import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.PlanAPI;
import jadex.bdiv3.annotation.PlanBody;
import jadex.bdiv3.annotation.PlanCapability;
import jadex.bdiv3.annotation.PlanReason;


import jadex.bdiv3.runtime.IPlan;
import jadex.bdiv3.runtime.PlanFinishedTaskCondition;
import jadex.commons.future.DelegationResultListener;
import jadex.commons.future.Future;
import jadex.extension.envsupport.environment.AbstractTask;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.SpaceObject;
import jadex.extension.envsupport.math.IVector2;


@Plan
public class MoveToFirePlan {

    @PlanCapability
    private FirefighterBDI agent;

    @PlanAPI
    private IPlan plan;

    @PlanReason
    private MoveToFireGoal goal;

    @PlanBody
    public void body(){

        ISpaceObject myself     = agent.getMyself();
        IVector2 destination    = goal.getDestination();
        IEnvironmentSpace space = agent.getSpace();

        // Começar a mover - Alterar Estado
        myself.setProperty(Firefighter.PROP_STATE, Firefighter.STATE_FIGHTER_MOVING);

        // Move Task especificação
        Map movementProperties = new HashMap();

        movementProperties.put(MoveTask.PROPERTY_DESTINATION    , destination);
        movementProperties.put(MoveTask.PROPERTY_SPEED          , myself.getProperty(Firefighter.PROP_SPEED));
        movementProperties.put(MoveTask.PROPERTY_FIREFIGHTER    , myself);
        movementProperties.put(AbstractTask.PROPERTY_CONDITION  , new PlanFinishedTaskCondition(plan));


        Future<Void> future = new Future<Void>();
        DelegationResultListener<Void> listener = new DelegationResultListener<Void>(future, true);
        Object mtaskid = space.createObjectTask(MoveTask.PROPERTY_TYPENAME, movementProperties, myself.getId());
        space.addTaskListener(mtaskid, agent.getMyself().getId(), listener);
        future.get();
        space.removeTaskListener(mtaskid,agent.getMyself().getId(),listener);
        space.removeObjectTask(mtaskid,agent.getMyself().getId());
        ((SpaceObject) agent.getMyself()).removeTask(mtaskid,null);

        //agent.agent.dispatchTopLevelGoal(new ExtinguishFireGoal(goal.destination));


        destination = (IVector2) myself.getProperty("position");
        agent.figthing=true;
        myself.setProperty(Firefighter.PROP_STATE, Firefighter.STATE_FIGHTER_EXTING);
        agent.agent.dispatchTopLevelGoal(new ExtinguishFireGoal(destination));

    }
}
