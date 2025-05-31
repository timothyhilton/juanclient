package juan.modules.movement;

import org.lwjgl.input.Keyboard;
import juan.events.Event;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.potion.Potion;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", Keyboard.KEY_N, Category.MOVEMENT, true);
    }

    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
    }

    @Override
    public void onEvent(Event e) {
        if (!(e instanceof EventUpdate) || !e.isPre())
            return;

        mc.thePlayer.movementInput.updatePlayerMoveState();

        float threshold = 0.8F;
        boolean forwardEnough = mc.thePlayer.movementInput.moveForward >= threshold;
        boolean canSprint = mc.thePlayer.getFoodStats().getFoodLevel() > 6.0F
                || mc.thePlayer.capabilities.allowFlying;
        boolean usingItem = mc.thePlayer.isUsingItem();
        boolean blinded = mc.thePlayer.isPotionActive(Potion.blindness);

        int sprintKey = mc.gameSettings.keyBindSprint.getKeyCode();

        if (forwardEnough && canSprint && !usingItem && !blinded) {
            KeyBinding.setKeyBindState(sprintKey, true);
        } else {
            KeyBinding.setKeyBindState(sprintKey, false);
        }
    }
}
