package juan.modules.render;

import java.awt.Color;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.lwjgl.input.Keyboard;
import juan.Client;
import juan.events.Event;
import juan.events.listeners.EventKey;
import juan.events.listeners.EventRenderGUI;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import juan.settings.*;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class Theme extends Module {
    
    public ModeSetting theme = new ModeSetting("theme", "pink-purple-aqua", "rainbow", "unsaturated rainbow", "white", "green-blue gradient", "pink-purple-aqua");
    public BooleanSetting includeBadge = new BooleanSetting("include badge", false);
    public NumberSetting speed = new NumberSetting("speed", 1, 0, 4, 0.2);
    public NumberSetting smoothness = new NumberSetting("smoothness", 1, 0, 2, 0.1);
    public NumberSetting saturation = new NumberSetting("saturation", 0.6, 0, 1, 0.1);
    
    public Theme() {
        super("Theme", Keyboard.KEY_NONE, Category.RENDER, true);
        this.addSettings(theme, includeBadge, speed, smoothness, saturation);
    }
    
    public void onEnable() {
        toggle();
    }
    
    public int getColour(int index) {
    	float timeAdjust = 0;
        float hue;
        float saturation;
        float brightness;

        switch(theme.getMode()) {
            case "white":
                return -1;
                
            case "rainbow":
                timeAdjust += (index) * 0.05f / smoothness.getValue();
                timeAdjust %= 1.0f;
                return Color.HSBtoRGB(timeAdjust, (float) this.saturation.getValue(), 1);
            
            case "green-blue gradient":
                float[] greenBlueHues = {180f / 360f, 240f / 360f};
                return getSmoothedColor(greenBlueHues, timeAdjust, index);
                
            case "pink-purple-aqua":
                float[] pinkPurpleAquaHues = {320f / 360f, 270f / 360f, 190f / 360f};
                return getSmoothedColor(pinkPurpleAquaHues, timeAdjust, index);
        }
        return -1;
    }
    
    public List<String> getThemes() {
        return theme.getModes();
    }
    
    private int getSmoothedColor(float[] hues, float timeAdjust, int index) {
        int numColors = hues.length;
        int smoothnessNormalisation = 1 * hues.length;
        timeAdjust = (float) ((System.currentTimeMillis() % (4000 / (speed.getValue() / hues.length))) / (4000f / (speed.getValue() / hues.length)));
        float cycle = (float) ((timeAdjust + index * 0.05f / (smoothness.getValue() * smoothnessNormalisation)) * numColors);
        cycle = cycle - (float)Math.floor(cycle);
        
        int i = (int)(cycle * numColors);
        float f = (cycle * numColors) - i;
        
        float hue1 = hues[i % numColors];
        float hue2 = hues[(i + 1) % numColors];
        
        if (Math.abs(hue2 - hue1) > 0.5f) {
            if (hue2 > hue1) hue1 += 1f;
            else hue2 += 1f;
        }
        
        float t = f * f * (3 - 2 * f);
        float hue = hue1 + (hue2 - hue1) * t;
        hue = hue - (float)Math.floor(hue);
        
        return Color.HSBtoRGB(hue, (float) saturation.getValue(), 1f);
    }
}