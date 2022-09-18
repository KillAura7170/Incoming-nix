package me.alpha432.oyvey.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.events.ClientEvent;
import me.alpha432.oyvey.features.command.Command;
import me.alpha432.oyvey.features.gui.OyVeyGui;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.ColorUtil;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ClickGui
        extends Module {
    private static ClickGui INSTANCE = new ClickGui();
    public Setting<String> prefix = this.register(new Setting<>("Prefix", "."));
    public Setting<Boolean> customFov = this.register(new Setting<>("CustomFov", false));
    public Setting<Boolean> snowing = this.register(new Setting<>("Snowing", true));
    public Setting<Boolean> rainbowRolling = this.register(new Setting<>("Rolling rainbow", true));
    public Setting<Boolean> outline = this.register(new Setting<>("Outline", true));


    public Setting<Float> fov = this.register(new Setting<>("Fov", 150.0f, -180.0f, 180.0f));
    public Setting<Integer> red = this.register(new Setting<>("Red", 190, 0, 255));
    public Setting<Integer> green = this.register(new Setting<>("Green", 40, 0, 255));
    public Setting<Integer> blue = this.register(new Setting<>("Blue", 40, 0, 255));
    public Setting<Integer> hoverAlpha = this.register(new Setting<>("Alpha", 180, 0, 255));
    public Setting<Integer> alpha = this.register(new Setting<>("HoverAlpha", 240, 0, 255));

    public Setting<Boolean> rainbow = this.register(new Setting<>("Rainbow", false));
    public Setting<rainbowMode> rainbowModeHud = this.register(new Setting<Object>("HRainbowMode", rainbowMode.Static, v -> this.rainbow.getValue(true)));
    public Setting<rainbowModeArray> rainbowModeA = this.register(new Setting<Object>("ARainbowMode", rainbowModeArray.Static, v -> this.rainbow.getValue(true)));
    public Setting<Integer> rainbowHue = this.register(new Setting<Object>("Delay", 7, 0, 600, v -> this.rainbow.getValue(true)));
    public Setting<Float> rainbowBrightness = this.register(new Setting<Object>("Brightness ", 59.5f, 1.0f, 255.0f, v -> this.rainbow.getValue(true)));
    public Setting<Float> rainbowSaturation = this.register(new Setting<Object>("Saturation", 176.7f, 1.0f, 255.0f, v -> this.rainbow.getValue(true)));

    public float hue;


    private OyVeyGui click;

    public ClickGui() {
        super("ClickGui", "Opens the ClickGui", Module.Category.CLIENT, true, false, false);
        this.setInstance();
        setBind(Keyboard.KEY_RSHIFT);
    }

    public static ClickGui getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClickGui();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (this.customFov.getValue(true)) {
            ClickGui.mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, this.fov.getValue(true));
        }
    }

    public int getCurrentColorHex() {
        if (this.rainbow.getValue(true)) {
            return Color.HSBtoRGB(this.hue, (float) this.rainbowSaturation.getValue(true).intValue() / 255.0f, (float) this.rainbowBrightness.getValue(true).intValue() / 255.0f);
        }
        return ColorUtil.toARGB(this.red.getValue(true), this.green.getValue(true), this.blue.getValue(true), this.alpha.getValue(true));
    }

    public Color getCurrentColor() {
        if (this.rainbow.getValue(true)) {
            return Color.getHSBColor(this.hue, (float) this.rainbowSaturation.getValue(true).intValue() / 255.0f, (float) this.rainbowBrightness.getValue(true).intValue() / 255.0f);
        }
        return new Color(this.red.getValue(true), this.green.getValue(true), this.blue.getValue(true), this.alpha.getValue(true));
    }


    @SubscribeEvent
    public void onSettingChange(ClientEvent event) {
        if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(this.prefix)) {
                OyVey.commandManager.setPrefix(this.prefix.getPlannedValue());
                Command.sendMessage("Prefix set to " + ChatFormatting.DARK_GRAY + OyVey.commandManager.getPrefix());
            }
            OyVey.colorManager.setColor(this.red.getPlannedValue(), this.green.getPlannedValue(), this.blue.getPlannedValue(), this.hoverAlpha.getPlannedValue());
        }
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(OyVeyGui.getClickGui());
    }

    @Override
    public void onLoad() {
        OyVey.colorManager.setColor(this.red.getValue(true), this.green.getValue(true), this.blue.getValue(true), this.hoverAlpha.getValue(true));
        OyVey.commandManager.setPrefix(this.prefix.getValue(true));
    }

    @Override
    public void onTick() {
        if (!(ClickGui.mc.currentScreen instanceof OyVeyGui)) {
            this.disable();
        }


    }

    public enum rainbowModeArray {
        Static,
        Up

    }

    public enum rainbowMode {
        Static,
        Sideway

    }

}

