package juan.modules.render;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import juan.events.Event;
import juan.events.listeners.EventRenderCamera;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import net.minecraft.util.MathHelper;

public class FreeLook extends Module {
    private float originalYaw;
    private float originalPitch;
    private boolean wasInFirstPerson;

    private float cameraYaw;
    private float cameraPitch;

    private long lastUpdateTime;

    public FreeLook() {
        super("FreeLook", Keyboard.KEY_V, Category.MOVEMENT, true);
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
        if (e instanceof EventUpdate && e.isPre()) {
            mc.thePlayer.rotationYaw = originalYaw;
            mc.thePlayer.rotationPitch = originalPitch;

            long currentTime = System.currentTimeMillis();
            float deltaTime = (currentTime - lastUpdateTime) / 1000f;
            lastUpdateTime = currentTime;

            float sensitivity = mc.gameSettings.mouseSensitivity * 4F + 0.2F;
            float deltaYaw = Mouse.getDX() * sensitivity * 0.15F;
            float deltaPitch = -Mouse.getDY() * sensitivity * 0.15F;

            // Smooth the movement using delta time
            cameraYaw += deltaYaw * (deltaTime * 60);  // Normalize to 60 FPS
            cameraPitch += deltaPitch * (deltaTime * 60);

            cameraPitch = MathHelper.clamp_float(cameraPitch, -90.0F, 90.0F);

            // Wrap yaw between 0 and 360 degrees
            cameraYaw = cameraYaw % 360;
            if (cameraYaw < 0) cameraYaw += 360;
        }

        if (e instanceof EventRenderCamera) {
            EventRenderCamera event = (EventRenderCamera) e;
            event.yaw = cameraYaw;
            event.pitch = cameraPitch;
        }
    }
}