package Firefighting.Agents.ControlCenter;

import Firefighting.Agents.ControlCenter.Plans.ProtectPlan;
import Firefighting.Agents.ControlCenter.Plans.SearchFirePlan;
import Firefighting.Agents.ControlCenter.Plans.NewFighterPlan;
import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.*;
import jadex.extension.envsupport.environment.space2d.Grid2D;
import jadex.micro.annotation.*;

@Agent
@Plans(
    {
        @Plan(trigger = @Trigger(factchangeds = "time"           ), body = @Body(ProtectPlan.class)),
        @Plan(body = @Body(SearchFirePlan.class)),
        @Plan(trigger = @Trigger(factchangeds = "numberFF"       ), body = @Body(NewFighterPlan.class))
    }
)
public class ControlCenterBDI {

    @Agent
    public BDIAgent agent;
    static public int FIRE_FIGHT_COUNT;

    @Belief(dynamic = true,updaterate=300)
    int numberFF = FIRE_FIGHT_COUNT;

    @Belief
    protected Grid2D space = (Grid2D) agent.getParentAccess().getExtension("2dspace").get();

    @Belief(updaterate=500)
    protected long time = System.currentTimeMillis();


    @AgentBody
    public void executeBody() throws InterruptedException {
       // agent.adoptPlan(new ProtectPlan());
        //agent.adoptPlan(new SearchFirePlan());
        agent.adoptPlan(new NewFighterPlan());
    }

    public Grid2D getSpace() {
        return space;
    }

}
