package us.zingalicio.granchisel.entity.entities;

import net.minecraft.server.v1_7_R1.EntityPig;
import net.minecraft.server.v1_7_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_7_R1.World;

public class CustomEntitySupportPig extends EntityPig
{
	PathfinderGoalSelector goalSelector;
	
   	public CustomEntitySupportPig(World world) 
	{
		super(world);
		this.a(0.9F, 0.9F);
		this.getNavigation().a(true);
		this.goalSelector = new PathfinderGoalSelector(world != null && world.methodProfiler != null ? world.methodProfiler : null);
	}
}
