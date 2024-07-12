package juan.modules.render;

import org.lwjgl.input.Keyboard;

import juan.Client;
import juan.events.Event;
import juan.events.listeners.EventRender3D;
import juan.modules.Module;
import juan.settings.BooleanSetting;
import juan.settings.NumberSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class ChinaHat extends Module {

    public NumberSetting radius = new NumberSetting("radius", 0.7, 0.1, 2.0, 0.1);
    public NumberSetting height = new NumberSetting("height", 0.3, 0.1, 1.0, 0.1);
    public NumberSetting opacity = new NumberSetting("opacity", 0.7, 0.1, 1.0, 0.1);
    public NumberSetting segments = new NumberSetting("segments", 20, 3, 80, 4);

    public ChinaHat() {
        super("ChinaHat", Keyboard.KEY_NONE, Category.RENDER, true);
        this.addSettings(radius, height, segments, opacity);
    }

    public void onEvent(Event e) {
        if (e instanceof EventRender3D) {
            renderChinaHat(((EventRender3D) e).getPartialTicks());
        }
    }

    private void renderChinaHat(float partialTicks) {
        double radius = this.radius.getValue();
        double height = this.height.getValue();
        int segments = (int) this.segments.getValue();

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        double x = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * partialTicks;
        double y = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * partialTicks;
        double z = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * partialTicks;

        GlStateManager.translate(
            x - mc.getRenderManager().viewerPosX,
            y - mc.getRenderManager().viewerPosY + mc.thePlayer.height + 0.1,
            z - mc.getRenderManager().viewerPosZ
        );

        WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
        worldrenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);

        // Center point
        worldrenderer.pos(0, height, 0).color(255, 255, 255, 100).endVertex();
        for (int i = 0; i <= segments; i++) {
            int colour = Client.theme.getColour(i);

            int r = (colour >> 16) & 0xFF;
            int g = (colour >> 8) & 0xFF;
            int b = colour & 0xFF;

            double angle = (Math.PI * 2 * i / segments);
            worldrenderer.pos(
                Math.cos(angle) * radius,
                0,
                Math.sin(angle) * radius
            ).color(r / 255f, g / 255f, b / 255f, (float) opacity.getValue()).endVertex();
        }

        Tessellator.getInstance().draw();

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private float getRainbowColor(int i, int segments, int offset) {
        double factor = Math.sin(((System.currentTimeMillis() + i * 200) % 2000) / 2000.0 * Math.PI + offset);
        return (float) ((factor * 0.5 + 0.5) * 255);
    }
}