package juan.modules.combat;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;

import juan.events.Event;
import juan.events.listeners.EventMotion;
import juan.modules.Module;
import juan.settings.NumberSetting;
import juan.util.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class KillAura extends Module {
    
    public Timer timer = new Timer();
    private Random random = new Random();
    
    public NumberSetting 
    	minCPS = new NumberSetting("minCPS", 9, 5, 15, 1),
    	maxCPS = new NumberSetting("maxCPS", 13, 5, 15, 1);
    
    private int 
    	yawFuzzinessRange = 3,
    	pitchFuzzinessRange = 5;
    
    private float movementSlowness = 0.5f;
    
    public KillAura() {
        super("KillAura", Keyboard.KEY_R, Category.COMBAT, false);
        this.addSettings(minCPS, maxCPS);
    }
    
    public void onEnable() {
        
    }
    
    public void onDisable() {
        
    }
    
    public void onEvent(Event e) {
        if(e instanceof EventMotion) {
            if(e.isPre()) {
            	movementSlowness = 0.3f;
                
                EventMotion event = (EventMotion)e;
                
                List<Entity> targets = mc.theWorld.loadedEntityList.stream()
                        .filter(EntityLivingBase.class::isInstance)
                        .filter(entity -> 
                        	entity.getDistanceToEntity(mc.thePlayer) < 4 
                        	&& entity != mc.thePlayer 
                        	&& !entity.isDead 
                        	&& !entity.isInvisible()
                        )
                        .collect(Collectors.toList());
                
                targets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase)entity).getDistanceToEntity(mc.thePlayer)));
                
                if(!targets.isEmpty()) {
                    EntityLivingBase target = (EntityLivingBase) targets.get(0);
                    
                    if(target.getDistanceToEntity(mc.thePlayer) > 1) {
                    	float[] rotations = getRotations(target, true, true);
                    	
                        mc.thePlayer.rotationYaw = rotations[0];
                        mc.thePlayer.rotationPitch = rotations[1];
                    	
//                    	event.setYaw(rotations[0]);
//                    	event.setPitch(rotations[1]);
                    }
                    
                    
                    if(timer.hasTimeElapsed(getRandomCPSDelay(), true))
                        return;
                    
                    mc.thePlayer.playerSwingItem();
                    
//                    if(isRaycastHit(target)) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, Action.ATTACK));
//                    }
                }
                
            }
        }
    }
    
    private boolean isRaycastHit(Entity target) {
        Vec3 playerPos = mc.thePlayer.getPositionEyes(1.0F);
        Vec3 lookVec = mc.thePlayer.getLook(1.0F);
        
        double reachDistance = mc.playerController.getBlockReachDistance();
        Vec3 endVec = playerPos.addVector(lookVec.xCoord * reachDistance, lookVec.yCoord * reachDistance, lookVec.zCoord * reachDistance);
        
        MovingObjectPosition rayTraceResult = mc.theWorld.rayTraceBlocks(playerPos, endVec, false, false, false);
        
        if (rayTraceResult != null && rayTraceResult.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            return rayTraceResult.entityHit == target;
        }
        
        List<Entity> entitiesInPath = mc.theWorld.getEntitiesInAABBexcluding(mc.thePlayer, 
            mc.thePlayer.getEntityBoundingBox().expand(0.3, 0.3, 0.3).addCoord(lookVec.xCoord * reachDistance, lookVec.yCoord * reachDistance, lookVec.zCoord * reachDistance).expand(1.0, 1.0, 1.0), 
            entity -> entity != null && entity.canBeCollidedWith());
        
        for (Entity entity : entitiesInPath) {
            if (entity.getEntityBoundingBox().calculateIntercept(playerPos, endVec) != null && entity == target) {
                return true;
            }
        }
        
        return false;
    }

    public float[] getRotations(Entity e, boolean smooth, boolean jitter) {
        double deltaX = e.posX - mc.thePlayer.posX;
        double deltaY = (e.posY + e.getEyeHeight()) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double deltaZ = e.posZ - mc.thePlayer.posZ;
        double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        
        float yaw = (float) Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90.0F;
        float pitch = (float) -Math.toDegrees(Math.atan2(deltaY, distance));
        
        if(smooth) {
        	yaw = mc.thePlayer.rotationYaw + (yaw - mc.thePlayer.rotationYaw) / (random.nextFloat() * 2f + movementSlowness);
            pitch = mc.thePlayer.rotationPitch + (pitch - mc.thePlayer.rotationPitch) / (random.nextFloat() * 2f + movementSlowness);
        }
        
        if(jitter) {
        	yaw += getRandomFuzziness(-yawFuzzinessRange, yawFuzzinessRange);
        	pitch += getRandomFuzziness(-pitchFuzzinessRange, pitchFuzzinessRange);
        }
        
        return new float[] { yaw, pitch };
    }
    
    private float getRandomFuzziness(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }
    
    private int getRandomCPSDelay() {
        int cps = (int) (random.nextInt((int) (maxCPS.getValue() - minCPS.getValue() + 1)) + minCPS.getValue());
        return 1000 / cps;
    }
    
}
