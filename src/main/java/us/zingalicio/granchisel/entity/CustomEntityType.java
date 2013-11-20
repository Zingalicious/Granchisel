package us.zingalicio.granchisel.entity;

import net.minecraft.server.v1_6_R2.EntityInsentient;
import net.minecraft.server.v1_6_R2.EntityPig;

import org.bukkit.entity.EntityType;

import us.zingalicio.granchisel.entity.entities.CustomEntityPig;

public enum CustomEntityType 
{
	PIG("Pig", 90, EntityType.PIG, EntityPig.class, CustomEntityPig.class);

	private String name;
	private int id;
	private EntityType entityType;
	private Class<? extends EntityInsentient> nmsClass;
	private Class<? extends EntityInsentient> customClass;
	
	private CustomEntityType(String name, int id, EntityType entityType, Class<?extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass)
	{
		this.name = name;
		this.id = id;
		this.entityType = entityType;
		this.nmsClass = nmsClass;
		this.customClass = customClass;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public EntityType getEntityType()
	{
		return this.entityType;
	}
	
	public Class<? extends EntityInsentient> getNMSClass()
	{
		return this.nmsClass;
	}
	
	public Class<? extends EntityInsentient> getCustomClass()
	{
		return this.customClass;
	}
}
