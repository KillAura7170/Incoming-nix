/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class Shaders
extends Module {
    public Setting<Mode> shader = this.register(new Setting<Mode>("Mode", Mode.green));
    private static Shaders INSTANCE = new Shaders();

    public Shaders() {
        super("Shaders", "i dont even know anymore", Module.Category.RENDER, false, false, false);
    }

    @Override
    public void onUpdate() {
        if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (Shaders.mc.entityRenderer.getShaderGroup() != null) {
                Shaders.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
            try {
                Shaders.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/" + (Object)((Object)this.shader.getValue(true)) + ".json"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else if (Shaders.mc.entityRenderer.getShaderGroup() != null && Shaders.mc.currentScreen == null) {
            Shaders.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }

    @Override
    public String getDisplayInfo() {
        return this.shader.currentEnumName();
    }

    @Override
    public void onDisable() {
        if (Shaders.mc.entityRenderer.getShaderGroup() != null) {
            Shaders.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }

    public static enum Mode {
        notch,
        antialias,
        art,
        bits,
        blobs,
        blobs2,
        blur,
        bumpy,
        color_convolve,
        creeper,
        deconverge,
        desaturate,
        entity_outline,
        flip,
        fxaa,
        green,
        invert,
        ntsc,
        outline,
        pencil,
        phosphor,
        scan_pincusion,
        sobel,
        spider,
        wobble;

    }
}

