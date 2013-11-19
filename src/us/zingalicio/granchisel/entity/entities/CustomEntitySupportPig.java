package us.zingalicio.granchisel.entity.entities;

import net.minecraft.server.v1_6_R2.EntityPig;
import net.minecraft.server.v1_6_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_6_R2.World;

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
