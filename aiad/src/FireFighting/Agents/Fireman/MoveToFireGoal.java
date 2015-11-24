package FireFighting.Agents.Fireman;

import jadex.bdiv3.annotation.Goal;
import jadex.extension.envsupport.math.IVector2;

@Goal
public class MoveToFireGoal {

    protected IVector2 destination;

    public MoveToFireGoal(IVector2 destination){
        this.destination = destination;
    }

    public IVector2 getDestination(){
        return destination;
    }

}
