/*
 * Decompiled with CFR 0.151.
 */
package me.alpha432.oyvey.features.modules.player;

import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class HasteHack
extends Module {
    public HasteHack() {
        super("HasteTest", "hh", Module.Category.PLAYER, true, false, true);
    }

    @Override
    public void onUpdate() {
        HasteHack.mc.player.addPotionEffect(new PotionEffect(MobEffects.HASTE));
    }
}

