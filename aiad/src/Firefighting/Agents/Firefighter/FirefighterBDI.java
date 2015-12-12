package Firefighting.Agents.Firefighter;

import Firefighting.Agents.Firefighter.Goals.ExtinguishFireGoal;
import Firefighting.Agents.Firefighter.Goals.MoveToFireGoal;
import Firefighting.Agents.Firefighter.Plans.*;
import Scene.Elements.ControlCenter;
import Scene.Elements.Firefighter;
import Firefighting.Agents.ControlCenter.ControlCenterBDI;
import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.*;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Grid2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.micro.annotation.*;

import java.util.Random;

@Agent
@Plans(
        {
                @Plan(trigger=@Trigger(factchangeds = "time")           , body=@Body(SearchFirePlan.class)),
                @Plan(trigger=@Trigger(goals=ExtinguishFireGoal.class)  , body=@Body(ExtinguishFirePlan.class)),
                @Plan(trigger=@Trigger(goals=MoveToFireGoal.class)      , body=@Body(MoveToFirePlan.class))
        })
public class FirefighterBDI {

    @Agent
    public BDIAgent agent;
    @Belief
    protected Grid2D space = (Grid2D)agent.getParentAccess().getExtension("2dspace").get();
    @Belief
    protected ISpaceObject myself = space.getAvatar(agent.getComponentDescription(), agent.getModel().getFullName());
    @Belief
    protected ISpaceObject[] controlcenterInfo = space.getSpaceObjectsByType(ControlCenter.TYPE);

    @Belief(updaterate=500)
    protected long time = System.currentTimeMillis();
    @Belief
    public boolean figthing = false;

    public int id ;


    @AgentBody
    public void body(){
        id = ControlCenterBDI.FIRE_FIGHT_COUNT++;

        Random r = new Random();
        int vision  = r.nextInt(3);
        int speed   = r.nextInt(3);

        int x,y;

        x=0;
        y=0;

        if(space.createSpaceObject(Firefighter.TYPE, Firefighter.createFFighter(x,y,vision,speed), null) != null)
            myself = space.createSpaceObject(Firefighter.TYPE, Firefighter.createFFighter(x,y,vision,speed), null);

     }

    public Grid2D getSpace() {
        return space;
    }

    public ISpaceObject getMyself() {
        return myself;
    }

    public IVector2 getPosition() {
        return (IVector2)getMyself().getProperty("position");
    }

    public int getAgentNumber() { return id; }



}
