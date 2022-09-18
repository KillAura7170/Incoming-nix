/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;

public class ViewModChanger
extends Module {
    public final Setting<Float> size = this.register(new Setting<Float>("Size", Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(15.0f)));
    public final Setting<Float> offsetX = this.register(new Setting<Float>("OffsetX", Float.valueOf(0.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f)));
    public final Setting<Float> offsetY = this.register(new Setting<Float>("OffsetY", Float.valueOf(0.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f)));
    public final Setting<Float> offsetZ = this.register(new Setting<Float>("OffsetZ", Float.valueOf(0.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f)));
    public final Setting<Float> offhandX = this.register(new Setting<Float>("OffhandX", Float.valueOf(0.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f)));
    public final Setting<Float> offhandY = this.register(new Setting<Float>("OffhandY", Float.valueOf(0.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f)));
    public final Setting<Float> offhandZ = this.register(new Setting<Float>("OffhandZ", Float.valueOf(0.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f)));
    public final Setting<Float> armPitch = this.register(new Setting<Float>("Arm Pitch", Float.valueOf(0.0f), Float.valueOf(-1.0f), Float.valueOf(1.0f)));
    private static ViewModChanger INSTANCE = new ViewModChanger();

    public ViewModChanger() {
        super("ViewMod", "Changes ViewModelChanger of items", Module.Category.RENDER, false, false, false);
        this.setInstance();
    }

    public static ViewModChanger getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewModChanger();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }
}

