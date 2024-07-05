package juan.modules.combat;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;

import juan.events.Event;
import juan.events.listeners.EventHitDelay;
import juan.events.listeners.EventMotion;
import juan.modules.Module;
import juan.settings.BooleanSetting;
import juan.settings.NumberSetting;
import juan.util.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class HitDelayFix extends Module {
    

    public HitDelayFix() {
        super("HitDelayFix", Keyboard.KEY_NONE, Category.COMBAT, true);
    }
    
    public void onEvent(Event e) {
    if(e instanceof EventHitDelay) {
    	EventHitDelay event = (EventHitDelay) e;
    	
		if (event.minecraft.playerController.isNotCreative() && !this.isEnabled()) {
			event.minecraft.leftClickCounter = 10;
		} else if (this.isEnabled()) {
			event.minecraft.leftClickCounter = 0;
		}
    }
}
}
