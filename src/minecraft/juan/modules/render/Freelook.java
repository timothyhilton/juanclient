package juan.modules.render;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import juan.events.Event;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class FreeLook extends Module {
    
    private float cameraYaw;
    private float cameraPitch;
    private boolean wasThirdPerson;

    public FreeLook() {
        super("FreeLook", Keyboard.KEY_V, Category.RENDER, true);
    }
    
    public void onEnable() {
        Minecraft mc = Minecraft.getMinecraft();
        wasThirdPerson = mc.gameSettings.thirdPersonView != 0;
        if (!wasThirdPerson) {
            mc.gameSettings.thirdPersonView = 1;
        }
        cameraYaw = mc.thePlayer.rotationYaw;
        cameraPitch = mc.thePlayer.rotationPitch;
        toggled = true;
    }

    public void onDisable() {
        Minecraft mc = Minecraft.getMinecraft();
        if (!wasThirdPerson) {
            mc.gameSettings.thirdPersonView = 0;
        }
        toggled = false;
    }
    
    public void onEvent(Event e) {
        if (e instanceof EventUpdate && e.isPre() && toggled) {
            Minecraft mc = Minecraft.getMinecraft();
            float sensitivityX = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float sensitivityY = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            cameraYaw += Mouse.getDX() * sensitivityX * 0.15F;
            cameraPitch -= Mouse.getDY() * sensitivityY * 0.15F;

            cameraPitch = MathHelper.clamp_float(cameraPitch, -90.0F, 90.0F);
        }
    }

    public float getCameraYaw() {
        return toggled ? cameraYaw : Minecraft.getMinecraft().thePlayer.rotationYaw;
    }

    public float getCameraPitch() {
        return toggled ? cameraPitch : Minecraft.getMinecraft().thePlayer.rotationPitch;
    }

}