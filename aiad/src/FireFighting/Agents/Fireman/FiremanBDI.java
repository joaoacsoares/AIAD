package FireFighting.Agents.Fireman;

import Scenery.Elements.Fireman;
import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.*;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Grid2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Int;
import jadex.micro.annotation.*;

@Agent
@Plans(
        {
                @Plan(trigger=@Trigger(goals=MoveToFireGoal.class), body=@Body(MoveToFirePlan.class))
        }
)

public class FiremanBDI {

    @Agent
    protected BDIAgent agent;
    @Belief
    protected Grid2D space = (Grid2D) agent.getParentAccess().getExtension("2dspace").get();
    @Belief
    protected ISpaceObject myself = space.getAvatar(agent.getComponentDescription(), agent.getModel().getFullName());
    @Belief
    public boolean fighting = false;

    int id;

    @AgentBody
    public void body() {
        int vision = 3;
        int speed = 3;

        int x, y;
        if(space!=null||myself!=null){
            x = 0;
            y = 0;
            //System.out.print(agent.getComponentDescription()+","+ agent.getModel().getFullName());
            myself = space.createSpaceObject(Fireman.TYPE, Fireman.createFireman(x, y, vision, speed), null);
            agent.adoptPlan(new MoveToFirePlan());
        }
    }


    public Grid2D getSpace() {
        return space;
    }

    public ISpaceObject getMyself() {
        return myself;
    }

    public IVector2 getPosition() {
        return (IVector2) getMyself().getProperty("position");
    }

    public int getAgentNumber() {
        return id;
    }

}