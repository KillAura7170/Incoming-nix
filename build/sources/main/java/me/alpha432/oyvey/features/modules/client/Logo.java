package me.alpha432.oyvey.features.modules.client;

import me.alpha432.oyvey.event.events.Render2DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.ColorUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Logo extends Module {
    public static final ResourceLocation mark = new ResourceLocation("textures/incoming zero.png");
    public Setting<Integer> imageX;
    public Setting<Integer> imageY;
    public Setting<Integer> imageWidth;
    public Setting<Integer> imageHeight;
    private int color;

    public Logo() {
        super("Logo", "Puts a logo there (there)", Category.CLIENT, false, false, false);
        this.imageX = this.register(new Setting("WatermarkX", 6, 0, 300));
        this.imageY = this.register(new Setting("WatermarkY", 40, 0, 300));
        this.imageWidth = this.register(new Setting("WatermarkWidth", 50, 0, 1000));
        this.imageHeight = this.register(new Setting("WatermarkHeight", 50, 0, 1000));

    }

    public void renderLogo() {
        int width = this.imageWidth.getValue(true);
        int height = this.imageHeight.getValue(true);
        int x = this.imageX.getValue(true);
        int y = this.imageY.getValue(true);
        mc.renderEngine.bindTexture(mark);
        GlStateManager.color(255.0F, 255.0F, 255.0F);
        Gui.drawScaledCustomSizeModalRect(x - 2, y - 36, 7.0F, 7.0F, width - 7, height - 7, width, height, (float) width, (float) height);
    }

    public void onRender2D(Render2DEvent event) {
        if (!fullNullCheck()) {
            int width = this.renderer.scaledWidth;
            int height = this.renderer.scaledHeight;
            this.color = ColorUtil.toRGBA(ClickGui.getInstance().red.getValue(true), ClickGui.getInstance().green.getValue(true), ClickGui.getInstance().blue.getValue(true));
            if (this.enabled.getValue(true)) {
                this.renderLogo();
            }

        }

    }
}