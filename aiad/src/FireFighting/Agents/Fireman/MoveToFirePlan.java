package FireFighting.Agents.Fireman;

import Scenery.Elements.Fireman;

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
import jadex.extension.envsupport.math.Vector2Int;


@Plan
public class MoveToFirePlan {

    @PlanCapability
    private FiremanBDI agent;

    @PlanAPI
    private IPlan plan;

    @PlanReason
    private MoveToFireGoal goal;

    @PlanBody
    public void body(){

        ISpaceObject myself     = agent.getMyself();
        IVector2 destination    = new Vector2Int(5,5); // DESTINATION TEM DE SER FOGO
        IEnvironmentSpace space = agent.getSpace();

        myself.setProperty(Fireman.STATE, "moving");

        Map movementProperties = new HashMap();

        movementProperties.put(MoveTask.PROPERTY_DESTINATION    , destination);
        movementProperties.put(MoveTask.PROPERTY_SPEED          , myself.getProperty(Fireman.SPEED));
        movementProperties.put(MoveTask.PROPERTY_FIREMAN, myself);
        movementProperties.put(AbstractTask.PROPERTY_CONDITION  , new PlanFinishedTaskCondition(plan));

        Future<Void> future = new Future<Void>();
        DelegationResultListener<Void> listener = new DelegationResultListener<Void>(future, true);
        Object mtaskid = space.createObjectTask(MoveTask.PROPERTY_TYPENAME, movementProperties, myself.getId());
        space.addTaskListener(mtaskid, agent.getMyself().getId(), listener);
        future.get();
        space.removeTaskListener(mtaskid, agent.getMyself().getId(), listener);
        space.removeObjectTask(mtaskid, agent.getMyself().getId());
        ((SpaceObject) agent.getMyself()).removeTask(mtaskid,null);
    }
}
