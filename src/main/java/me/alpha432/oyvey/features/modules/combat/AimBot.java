package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.RotationUtil;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.entity.player.EntityPlayer;

public class AimBot
extends Module {
    private final Setting<Mode> mode = this.register( new Setting <> ( "Rotate" , Mode.None ));
    private final Setting<Float> range = this.register( new Setting <> ( "Range" , 8.0f , 0.0f , 20.0f ));
    private final Setting<Boolean> onlyBow = this.register( new Setting <> ( "Bow Only" , true ));
    EntityPlayer aimTarget = null;
    RotationUtil aimbotRotation = null;

    public AimBot() {
        super("AimBot", "ab", Module.Category.COMBAT, true, false, false);
    }

    public
    enum Mode {
        Legit,
        Packet,
        None

    }
}

