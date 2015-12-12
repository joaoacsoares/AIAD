package Firefighting.Agents.ControlCenter.Plans;

import Firefighting.Agents.ControlCenter.ControlCenterBDI;
import Scene.Elements.Assets.Building;
import Scene.Elements.ControlCenter;
import Scene.Elements.Nature.Fire;
import jadex.bdiv3.annotation.*;
import jadex.bdiv3.runtime.IPlan;
import jadex.bdiv3.runtime.impl.PlanFailureException;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.SpaceObject;
import jadex.extension.envsupport.environment.space2d.Grid2D;
import jadex.extension.envsupport.math.IVector2;

import java.util.ArrayList;
import java.util.Set;

@Plan
public class ProtectPlan {

    @PlanAPI
    private IPlan plan;

    @PlanCapability
    private ControlCenterBDI controlcenter;

    @PlanBody
    public void body() throws PlanFailureException {

        ISpaceObject[] assets = controlcenter.getSpace().getSpaceObjectsByType(Building.TYPE);
        ArrayList<IVector2> assetsInDanger = new ArrayList<IVector2>();
        ArrayList<IVector2> firesNearAssets = new ArrayList<IVector2>();
        Grid2D space = controlcenter.getSpace();

        boolean assetInDanger = false;

        for (ISpaceObject asset : assets) {
            Building currentBuilding = new Building(asset);

            // Procura fogos a volta de Assets - 3 casas
            String[] firetype = {Fire.TYPE};
            Set<SpaceObject> fires = space.getNearGridObjects(currentBuilding.getPosition(), 3 , firetype );

            if(fires.size() > 0){
                assetInDanger = true;
                assetsInDanger.add(currentBuilding.getPosition());

                Object[] dangerousFires = fires.toArray();
                for(int i = 0; i < dangerousFires.length; i++) {
                    Fire currentFire = new Fire( (ISpaceObject) dangerousFires[i]);
                    firesNearAssets.add(currentFire.getPosition());
                }
            }
        }

        ControlCenter cc = new ControlCenter( space.getSpaceObjectsByType(ControlCenter.TYPE)[0] );

        if(!assetInDanger){
            cc.setBuildingsList(new ArrayList<IVector2>());
            cc.setPositionsToProtect(new IVector2[ControlCenterBDI.FIRE_FIGHT_COUNT]);
            plan.abort();
            return;
        }

        cc.setFireList(firesNearAssets);
        cc.setBuildingsList(assetsInDanger);
    }

    @PlanAborted
    public void aborted()
    {
        controlcenter.agent.adoptPlan(new SearchFirePlan());
    }

}
