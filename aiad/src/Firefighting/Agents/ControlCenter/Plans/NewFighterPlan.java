package Firefighting.Agents.ControlCenter.Plans;

import Firefighting.Agents.ControlCenter.ControlCenterBDI;
import Scene.Elements.ControlCenter;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.PlanBody;
import jadex.bdiv3.annotation.PlanCapability;
import jadex.bdiv3.runtime.impl.PlanFailureException;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.math.IVector2;

@Plan
public class NewFighterPlan {

    @PlanCapability
    private ControlCenterBDI controlcenter;


    @PlanBody
    public void body() throws PlanFailureException {
        ControlCenter cc =  new ControlCenter( (ISpaceObject) controlcenter.getSpace().getSpaceObjectsByType(ControlCenter.TYPE)[0] );
        cc.setPositionsToExtinguish(new IVector2[ControlCenterBDI.FIRE_FIGHT_COUNT]);
        cc.setPositionsToProtect(new IVector2[ControlCenterBDI.FIRE_FIGHT_COUNT]);
    }
}
