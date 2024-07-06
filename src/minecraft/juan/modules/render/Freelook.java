package juan.modules.render;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import juan.events.Event;
import juan.events.listeners.EventKey;
import juan.events.listeners.EventMotion;
import juan.events.listeners.EventRenderCamera;
import juan.events.listeners.EventRenderGUI;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import juan.settings.BooleanSetting;
import net.minecraft.util.MathHelper;

public class FreeLook extends Module {
    private float originalYaw;
    private float originalPitch;
    private boolean wasInFirstPerson;

    private float cameraYaw;
    private float cameraPitch;

    private long lastUpdateTime;
    
    public BooleanSetting autoDisable = new BooleanSetting("AutoDisable", true);

    public FreeLook() {
        super("FreeLook", Keyboard.KEY_F, Category.MOVEMENT, true);
        this.addSettings(autoDisable);
    }

    public void onEnable() {
        originalYaw = mc.thePlayer.rotationYaw;
        originalPitch = mc.thePlayer.rotationPitch;

        cameraYaw = mc.thePlayer.rotationYaw;
        cameraPitch = mc.thePlayer.rotationPitch;

        wasInFirstPerson = mc.gameSettings.thirdPersonView == 0;
        if (wasInFirstPerson) {
            mc.gameSettings.thirdPersonView = 1;
        }

        lastUpdateTime = System.currentTimeMillis();
    }

    public void onDisable() {
        mc.thePlayer.rotationYaw = originalYaw;
        mc.thePlayer.rotationPitch = originalPitch;

        if (wasInFirstPerson) {
            mc.gameSettings.thirdPersonView = 0;
        }
    }

    public void onEvent(Event e) {
        if (e instanceof EventRenderGUI) {
        	if(autoDisable.enabled && !Keyboard.isKeyDown(Keyboard.KEY_F)) {
            	this.toggle();
            }
        	
            mc.thePlayer.rotationYaw = originalYaw;
            mc.thePlayer.rotationPitch = originalPitch;

            long currentTime = System.currentTimeMillis();
            float deltaTime = (currentTime - lastUpdateTime) / 1000f;
            lastUpdateTime = currentTime;

            float sensitivity = mc.gameSettings.mouseSensitivity * 2f;
            float deltaYaw = mc.mouseHelper.deltaX * sensitivity * 0.15F;
            float deltaPitch = mc.mouseHelper.deltaY * sensitivity * 0.15F;

            cameraYaw += deltaYaw * (deltaTime * mc.getDebugFPS()); 
            cameraPitch += deltaPitch * (deltaTime * mc.getDebugFPS());

            cameraPitch = MathHelper.clamp_float(cameraPitch, -90.0F, 90.0F);

            cameraYaw = cameraYaw % 360;
            if (cameraYaw < 0) cameraYaw += 360;
            
        }

        if (e instanceof EventRenderCamera) {
        	mc.thePlayer.rotationYaw = originalYaw;
            mc.thePlayer.rotationPitch = originalPitch;
            
            EventRenderCamera event = (EventRenderCamera) e;
            event.yaw = cameraYaw;
            event.pitch = cameraPitch;
        }
    }
}