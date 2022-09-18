package me.alpha432.oyvey.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.alpha432.oyvey.OyVey;
import me.alpha432.oyvey.event.events.ClientEvent;
import me.alpha432.oyvey.event.events.PacketEvent;
import me.alpha432.oyvey.event.events.Render2DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.util.Timer;
import me.alpha432.oyvey.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

import static me.alpha432.oyvey.OyVey.commandManager;

public class HUD extends Module {
    private static final ResourceLocation box = new ResourceLocation("textures/gui/container/shulker_box.png");
    private static final ItemStack totem = new ItemStack(Items.TOTEM_OF_UNDYING);
    private static HUD INSTANCE = new HUD();
    public Setting<String> gameTitle = register(new Setting("AppTitle", "Incoming v1.2.7"));
    public Setting<Boolean> timestamp = register(new Setting("TimeStamps", Boolean.TRUE));
    private final Setting<Boolean> grayNess = register(new Setting("Gray", Boolean.TRUE));
    private final Setting<Boolean> renderingUp = register(new Setting("RenderingUp", Boolean.FALSE, "Orientation of the HUD-Elements."));
    private final Setting<Boolean> waterMark = register(new Setting("Watermark", Boolean.FALSE, "displays watermark"));
    private final Setting<String> waterMarkName = register(new Setting("WaterMarkName", "Incoming v1.2.7", v -> this.waterMark.getValue(true)));
    private final Setting<Boolean> arrayList = register(new Setting("ActiveModules", Boolean.FALSE, "Lists the active modules."));
    private final Setting<Boolean> coords = register(new Setting("Coords", Boolean.FALSE, "Your current coordinates"));
    private final Setting<Boolean> direction = register(new Setting("Direction", Boolean.FALSE, "The Direction you are facing."));
    private final Setting<Boolean> armor = register(new Setting("Armor", Boolean.FALSE, "ArmorHUD"));
    private final Setting<Boolean> totems = register(new Setting("Totems", Boolean.FALSE, "TotemHUD"));
    private final Setting<Boolean> greeter = register(new Setting("Welcomer", Boolean.FALSE, "The time"));
    public Setting<Boolean> speed = register(new Setting("Speed", Boolean.FALSE, "Your Speed"));
    public Setting<Boolean> potions = this.register(new Setting<>("Potions", Boolean.FALSE, "Active potion effects"));
    public Setting<Boolean> potionSync = this.register(new Setting<>("PotionSync", Boolean.FALSE, v -> this.potions.getValue(true)));
    private final Setting<Boolean> ping = register(new Setting("Ping", Boolean.FALSE, "Your response time to the server."));
    private final Setting<Boolean> ms = register(new Setting("ms", false, v -> this.ping.getValue(true)));
    private final Setting<Boolean> tps = register(new Setting("TPS", Boolean.FALSE, "Ticks per second of the server."));
    private final Setting<Boolean> fps = register(new Setting("FPS", Boolean.FALSE, "Your frames per second."));
    private final Setting<Boolean> server = register(new Setting("Server", false, "Shows the server"));
    private final Setting<Boolean> lag = register(new Setting("LagNotifier", Boolean.FALSE, "The time"));
    public Setting<Integer> rainbowSpeed = this.register(new Setting<Object>("PrefixSpeed", 20, 0, 100));
    public Setting<Integer> rainbowSaturation = this.register(new Setting<Object>("Saturation", 255, 0, 255));
    public Setting<Integer> rainbowBrightness = this.register(new Setting<Object>("Brightness", 255, 0, 255));
    private final Timer timer = new Timer();
    private Map<String, Integer> players = new HashMap<>();
    public Setting<Boolean> future = register(new Setting("Alpha", true));
    public Setting<Boolean> commandPrefix = register(new Setting("CommandPrefix", true));
    public Setting<Boolean> rainbowPrefix = register(new Setting("RainbowPrefix", true));
    public Setting<String> command = register(new Setting("Command", "Incoming"));
    public Setting<TextUtil.Color> commandColor = register(new Setting("NameColor", TextUtil.Color.BLUE));
    public Setting<Boolean> notifyToggles = register(new Setting("ChatNotify", Boolean.FALSE, "notifys in chat"));
    public Setting<Integer> animationHorizontalTime = register(new Setting("AnimationHTime", 500, 1, 1000, v -> this.arrayList.getValue(true)));
    public Setting<Integer> animationVerticalTime = register(new Setting("AnimationVTime", 50, 1, 500, v -> this.arrayList.getValue(true)));
    public Setting<RenderingMode> renderingMode = register(new Setting("Ordering", RenderingMode.ABC));
    public Setting<Integer> waterMarkY = register(new Setting("WatermarkPosY", 2, 0, 20, v -> this.waterMark.getValue(true)));
    public Setting<Boolean> textRadar = this.register(new Setting<>("TextRadar", Boolean.FALSE, "A TextRadar"));
    public Setting<Integer> textRadarUpdates = this.register(new Setting<>("TRUpdates", 500, 0, 1000));
    public Setting<Boolean> time = register(new Setting("Time", Boolean.FALSE, "The time"));
    public Setting<Integer> lagTime = register(new Setting("LagTime", 1000, 0, 2000));
    public Setting<Boolean> colorSync = this.register(new Setting("Sync", Boolean.FALSE, "Universal colors for hud."));
    public Map<Integer, Integer> colorHeightMap = new HashMap<>();
    public Map<Integer, Integer> colorMap = new HashMap<>();
    private int color;
    private boolean shouldIncrement;
    private int hitMarkerTimer;
    public float hue;


    public HUD() {
        super("HUD", "HUD Elements rendered on your screen", Module.Category.CLIENT, true, false, false);
        setInstance();
    }

    public static HUD getInstance() {
        if (INSTANCE == null)
            INSTANCE = new HUD();
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    public void onUpdate() {
        if (this.shouldIncrement)
            this.hitMarkerTimer++;
        if (this.hitMarkerTimer == 10) {
            this.hitMarkerTimer = 0;
            this.shouldIncrement = false;
        }
        if (this.timer.passedMs(HUD.getInstance().textRadarUpdates.getValue(true))) {
            this.players = this.getTextRadarPlayers();
            this.timer.reset();
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        Display.setTitle(gameTitle.getValue(true));


        int colorSpeed = 101 - this.rainbowSpeed.getValue(true);
        float tempHue = this.hue = (float) (System.currentTimeMillis() % (long) (360 * colorSpeed)) / (360.0f * (float) colorSpeed);
        for (int i = 0; i <= 510; ++i) {
            this.colorHeightMap.put(i, Color.HSBtoRGB(tempHue, (float) this.rainbowSaturation.getValue(true) / 255.0f, (float) this.rainbowBrightness.getValue(true) / 255.0f));
            tempHue += 0.0013071896f;
        }


    }


    public void onRender2D(Render2DEvent event) {
        if (fullNullCheck())
            return;
        int width = this.renderer.scaledWidth;
        int height = this.renderer.scaledHeight;
        if (this.textRadar.getValue(true)) {
            this.drawTextRadar(0);
        }
        this.color = ColorUtil.toRGBA((ClickGui.getInstance()).red.getValue(true), (ClickGui.getInstance()).green.getValue(true), (ClickGui.getInstance()).blue.getValue(true));
        if (this.waterMark.getValue(true)) {
            String string = this.waterMarkName.getPlannedValue();

            if ((ClickGui.getInstance()).rainbow.getValue(true)) {
                if ((ClickGui.getInstance()).rainbowModeHud.getValue(true) == ClickGui.rainbowMode.Static) {
                    this.renderer.drawString(string, 2.0F, this.waterMarkY.getValue(true), ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB(), true);
                } else {
                    int[] arrayOfInt = {1};
                    char[] stringToCharArray = string.toCharArray();
                    float f = 0.0F;
                    for (char c : stringToCharArray) {
                        this.renderer.drawString(String.valueOf(c), 2.0F + f, this.waterMarkY.getValue(true), ColorUtil.rainbow(arrayOfInt[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB(), true);
                        f += this.renderer.getStringWidth(String.valueOf(c));
                        arrayOfInt[0] = arrayOfInt[0] + 1;
                    }
                }
            } else {
                this.renderer.drawString(string, 2.0F, this.waterMarkY.getValue(true), this.color, true);
            }
        }
        int[] counter1 = {1};
        int j = (mc.currentScreen instanceof net.minecraft.client.gui.GuiChat && !this.renderingUp.getValue(true)) ? 14 : 0;
        if (this.arrayList.getValue(true))
            if (this.renderingUp.getValue(true)) {
                if (this.renderingMode.getValue(true) == RenderingMode.ABC) {
                    for (int k = 0; k < OyVey.moduleManager.sortedModulesABC.size(); k++) {
                        String str = OyVey.moduleManager.sortedModulesABC.get(k);
                        RenderUtil.drawRectangleCorrectly(width - 2 - this.renderer.getStringWidth(str) - 7, 2 + j * 10, mc.fontRenderer.getStringWidth(str) + 8, mc.fontRenderer.FONT_HEIGHT + 1, ColorUtil.toRGBA(0, 0, 0, 89));
                        RenderUtil.drawRectangleCorrectly(width - 2, 2 + j * 10, 3, mc.fontRenderer.FONT_HEIGHT + 1, ColorUtil.toRGBA(255, 255, 255, 255));
                        this.renderer.drawString(str, (width - 2 - this.renderer.getStringWidth(str) - 5), (2 + j * 10), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                        j++;
                        counter1[0] = counter1[0] + 1;
                    }
                } else {
                    for (int k = 0; k < OyVey.moduleManager.sortedModules.size(); k++) {
                        Module module = OyVey.moduleManager.sortedModules.get(k);
                        String str = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
                        RenderUtil.drawRectangleCorrectly(width - 2 - this.renderer.getStringWidth(str) - 7, 2 + j * 10, mc.fontRenderer.getStringWidth(str) + 8, mc.fontRenderer.FONT_HEIGHT + 1, ColorUtil.toRGBA(0, 0, 0, 89));
                        RenderUtil.drawRectangleCorrectly(width - 2, 2 + j * 10, 3, mc.fontRenderer.FONT_HEIGHT + 1, ColorUtil.toRGBA(255, 255, 255, 255));
                        this.renderer.drawString(str, (width - 2 - this.renderer.getStringWidth(str) - 5), (2 + j * 10), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                        j++;
                        counter1[0] = counter1[0] + 1;
                    }
                }
            } else if (this.renderingMode.getValue(true) == RenderingMode.ABC) {
                for (int k = 0; k < OyVey.moduleManager.sortedModulesABC.size(); k++) {
                    String str = OyVey.moduleManager.sortedModulesABC.get(k);
                    j += 10;
                    RenderUtil.drawRectangleCorrectly(width - 2 - this.renderer.getStringWidth(str) - 7, height - j, mc.fontRenderer.getStringWidth(str) + 8, mc.fontRenderer.FONT_HEIGHT + 1, ColorUtil.toRGBA(0, 0, 0, 89));
                    RenderUtil.drawRectangleCorrectly(width - 2, height - j, 3, mc.fontRenderer.FONT_HEIGHT + 1, ColorUtil.toRGBA(255, 255, 255, 255));
                    this.renderer.drawString(str, (width - 2 - this.renderer.getStringWidth(str) - 5), (height - j), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                    counter1[0] = counter1[0] + 1;
                }
            } else {
                for (int k = 0; k < OyVey.moduleManager.sortedModules.size(); k++) {
                    Module module = OyVey.moduleManager.sortedModules.get(k);
                    String str = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
                    j += 10;
                    RenderUtil.drawRectangleCorrectly(width - 2 - this.renderer.getStringWidth(str) - 7, height - j, mc.fontRenderer.getStringWidth(str) + 8, mc.fontRenderer.FONT_HEIGHT + 1, ColorUtil.toRGBA(0, 0, 0, 89));
                    RenderUtil.drawRectangleCorrectly(width - 2, height - j, 3, mc.fontRenderer.FONT_HEIGHT + 1, ColorUtil.toRGBA(255, 255, 255, 255));
                    this.renderer.drawString(str, (width - 2 - this.renderer.getStringWidth(str) - 5), (height - j), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                    counter1[0] = counter1[0] + 1;
                }
            }
        String grayString = this.grayNess.getValue(true) ? String.valueOf(ChatFormatting.GRAY) : "";
        int i = (mc.currentScreen instanceof net.minecraft.client.gui.GuiChat && this.renderingUp.getValue(true)) ? 13 : (this.renderingUp.getValue(true) ? -2 : 0);
        if (this.renderingUp.getValue(true)) {


            if (potions.getValue(true)) {
                List<PotionEffect> effects = new ArrayList<>((Minecraft.getMinecraft()).player.getActivePotionEffects());
                for (PotionEffect potionEffect : effects) {
                    String str = OyVey.potionManager.getColoredPotionString(potionEffect);
                    i += 10;

                    renderer.drawString(str, (width - renderer.getStringWidth(str) - 2), (height - 2 - i), this.potionSync.getValue(true) ? ((ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : (this.potionSync.getValue(true) ? this.color : potionEffect.getPotion().getLiquidColor())) : potionEffect.getPotion().getLiquidColor(), true);
                }
            }

            if (this.server.getValue(true)) {
                String sText = grayString + "Server " + ChatFormatting.WHITE + (mc.isSingleplayer() ? "SinglePlayer" : Objects.requireNonNull(mc.getCurrentServerData()).serverIP);
                i += 10;
                this.renderer.drawString(sText, (width - this.renderer.getStringWidth(sText) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                counter1[0] = counter1[0] + 1;
            }

            if (this.speed.getValue(true)) {
                String str = grayString + "Speed " + ChatFormatting.WHITE + OyVey.speedManager.getSpeedKpH() + " km/h";
                i += 10;
                this.renderer.drawString(str, (width - this.renderer.getStringWidth(str) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                counter1[0] = counter1[0] + 1;
            }
            if (this.time.getValue(true)) {
                String str = grayString + "Time " + ChatFormatting.WHITE + (new SimpleDateFormat("h:mm a")).format(new Date());
                i += 10;
                this.renderer.drawString(str, (width - this.renderer.getStringWidth(str) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                counter1[0] = counter1[0] + 1;
            }
            if (this.tps.getValue(true)) {
                String str = grayString + "TPS " + ChatFormatting.WHITE + OyVey.serverManager.getTPS();
                i += 10;
                this.renderer.drawString(str, (width - this.renderer.getStringWidth(str) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                counter1[0] = counter1[0] + 1;
            }
            String fpsText = grayString + "FPS " + ChatFormatting.WHITE + Minecraft.debugFPS;
            String sText = grayString + "Server " + ChatFormatting.WHITE + (mc.isSingleplayer() ? "SinglePlayer" : Objects.requireNonNull(mc.getCurrentServerData()).serverIP);
            String str1 = grayString + "Ping " + ChatFormatting.WHITE + OyVey.serverManager.getPing() + (this.ms.getValue(true) ? "ms" : "");
            if (this.renderer.getStringWidth(str1) > this.renderer.getStringWidth(fpsText)) {
                if (this.ping.getValue(true)) {
                    i += 10;
                    this.renderer.drawString(str1, (width - this.renderer.getStringWidth(str1) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                    counter1[0] = counter1[0] + 1;
                }
                if (this.fps.getValue(true)) {
                    i += 10;
                    this.renderer.drawString(fpsText, (width - this.renderer.getStringWidth(fpsText) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                    counter1[0] = counter1[0] + 1;
                }
            } else {
                if (this.fps.getValue(true)) {
                    i += 10;
                    this.renderer.drawString(fpsText, (width - this.renderer.getStringWidth(fpsText) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                    counter1[0] = counter1[0] + 1;
                }
                if (this.ping.getValue(true)) {
                    i += 10;
                    this.renderer.drawString(str1, (width - this.renderer.getStringWidth(str1) - 2), (height - 2 - i), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                    counter1[0] = counter1[0] + 1;
                }
            }
        } else {

            if (potions.getValue(true)) {
                List<PotionEffect> effects = new ArrayList<>((Minecraft.getMinecraft()).player.getActivePotionEffects());
                for (PotionEffect potionEffect : effects) {
                    String str = OyVey.potionManager.getColoredPotionString(potionEffect);
                    renderer.drawString(str, (width - renderer.getStringWidth(str) - 2), (2 + i++ * 10), this.potionSync.getValue(true) ? ((ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : (this.potionSync.getValue(true) ? this.color : potionEffect.getPotion().getLiquidColor())) : potionEffect.getPotion().getLiquidColor(), true);
                }
            }
            if (this.server.getValue(true)) {
                String sText = grayString + "Server " + ChatFormatting.WHITE + (mc.isSingleplayer() ? "SinglePlayer" : Objects.requireNonNull(mc.getCurrentServerData()).serverIP);
                this.renderer.drawString(sText, (width - this.renderer.getStringWidth(sText) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                counter1[0] = counter1[0] + 1;
            }
            if (this.speed.getValue(true)) {
                String str = grayString + "Speed " + ChatFormatting.WHITE + OyVey.speedManager.getSpeedKpH() + " km/h";
                this.renderer.drawString(str, (width - this.renderer.getStringWidth(str) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                counter1[0] = counter1[0] + 1;
            }
            if (this.time.getValue(true)) {
                String str = grayString + " Time " + ChatFormatting.WHITE + (new SimpleDateFormat("h:mm a")).format(new Date());
                this.renderer.drawString(str, (width - this.renderer.getStringWidth(str) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                counter1[0] = counter1[0] + 1;
            }
            if (this.tps.getValue(true)) {
                String str = grayString + "TPS " + ChatFormatting.WHITE + OyVey.serverManager.getTPS();
                this.renderer.drawString(str, (width - this.renderer.getStringWidth(str) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                counter1[0] = counter1[0] + 1;
            }
            String fpsText = grayString + "FPS " + ChatFormatting.WHITE + Minecraft.debugFPS;
            String str1 = grayString + "Ping " + ChatFormatting.WHITE + OyVey.serverManager.getPing() + (this.ms.getValue(true) ? "ms" : "");
            if (this.renderer.getStringWidth(str1) > this.renderer.getStringWidth(fpsText)) {
                if (this.ping.getValue(true)) {
                    this.renderer.drawString(str1, (width - this.renderer.getStringWidth(str1) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                    counter1[0] = counter1[0] + 1;
                }
                if (this.fps.getValue(true)) {
                    this.renderer.drawString(fpsText, (width - this.renderer.getStringWidth(fpsText) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                    counter1[0] = counter1[0] + 1;
                }
            } else {
                if (this.fps.getValue(true)) {
                    this.renderer.drawString(fpsText, (width - this.renderer.getStringWidth(fpsText) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                    counter1[0] = counter1[0] + 1;
                }
                if (this.ping.getValue(true)) {
                    this.renderer.drawString(str1, (width - this.renderer.getStringWidth(str1) - 2), (2 + i++ * 10), (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                    counter1[0] = counter1[0] + 1;
                }
            }
        }
        boolean inHell = mc.world.getBiome(mc.player.getPosition()).getBiomeName().equals("Hell");
        int posX = (int) mc.player.posX;
        int posY = (int) mc.player.posY;
        int posZ = (int) mc.player.posZ;
        float nether = !inHell ? 0.125F : 8.0F;
        int hposX = (int) (mc.player.posX * nether);
        int hposZ = (int) (mc.player.posZ * nether);
        i = (mc.currentScreen instanceof net.minecraft.client.gui.GuiChat) ? 14 : 0;
        String coordinates = ChatFormatting.RESET + (inHell ? String.valueOf(ChatFormatting.WHITE) + posX + ChatFormatting.GRAY + " [" + hposX + "], " + ChatFormatting.WHITE + posY + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + posZ + ChatFormatting.GRAY + " [" + hposZ + "]" : (String.valueOf(ChatFormatting.WHITE) + posX + ChatFormatting.GRAY + " [" + hposX + "], " + ChatFormatting.WHITE + posY + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + posZ + ChatFormatting.GRAY + " [" + hposZ + "]"));
        String direction = this.direction.getValue(true) ? OyVey.rotationManager.getDirection4D(false) : "";
        String coords = this.coords.getValue(true) ? coordinates : "";
        i += 10;
        if ((ClickGui.getInstance()).rainbow.getValue(true)) {
            String rainbowCoords = this.coords.getValue(true) ? ((inHell ? (posX + " [" + hposX + "], " + posY + ", " + posZ + " [" + hposZ + "]") : (posX + " [" + hposX + "], " + posY + ", " + posZ + " [" + hposZ + "]"))) : "";
            if ((ClickGui.getInstance()).rainbowModeHud.getValue(true) == ClickGui.rainbowMode.Static) {
                this.renderer.drawString(direction, 2.0F, (height - i - 11), ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB(), true);
                this.renderer.drawString(rainbowCoords, 2.0F, (height - i), ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB(), true);
            } else {
                int[] counter2 = {1};
                char[] stringToCharArray = direction.toCharArray();
                float s = 0.0F;
                for (char c : stringToCharArray) {
                    this.renderer.drawString(String.valueOf(c), 2.0F + s, (height - i - 11), ColorUtil.rainbow(counter2[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB(), true);
                    s += this.renderer.getStringWidth(String.valueOf(c));
                    counter2[0] = counter2[0] + 1;
                }
                int[] counter3 = {1};
                char[] stringToCharArray2 = rainbowCoords.toCharArray();
                float u = 0.0F;
                for (char c : stringToCharArray2) {
                    this.renderer.drawString(String.valueOf(c), 2.0F + u, (height - i), ColorUtil.rainbow(counter3[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB(), true);
                    u += this.renderer.getStringWidth(String.valueOf(c));
                    counter3[0] = counter3[0] + 1;
                }
            }
        } else {
            this.renderer.drawString(direction, 2.0F, (height - i - 11), this.color, true);
            this.renderer.drawString(coords, 2.0F, (height - i), this.color, true);
        }
        if (this.armor.getValue(true))
            renderArmorHUD(true);
        if (this.totems.getValue(true))
            renderTotemHUD();
        if (this.greeter.getValue(true))
            renderGreeter();
        if (this.lag.getValue(true))
            renderLag();
    }

    public Map<String, Integer> getTextRadarPlayers() {
        return EntityUtill.getTextRadarPlayers();
    }

    public void renderGreeter() {
        int width = this.renderer.scaledWidth;
        String text = "";
        if (this.greeter.getValue(true))
            text = text + MathUtil.getTimeOfDay() + mc.player.getDisplayNameString();
        if ((ClickGui.getInstance()).rainbow.getValue(true)) {
            if ((ClickGui.getInstance()).rainbowModeHud.getValue(true) == ClickGui.rainbowMode.Static) {
                this.renderer.drawString(text, width / 2.0F - this.renderer.getStringWidth(text) / 2.0F + 2.0F, 2.0F, ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB(), true);
            } else {
                int[] counter1 = {1};
                char[] stringToCharArray = text.toCharArray();
                float i = 0.0F;
                for (char c : stringToCharArray) {
                    this.renderer.drawString(String.valueOf(c), width / 2.0F - this.renderer.getStringWidth(text) / 2.0F + 2.0F + i, 2.0F, ColorUtil.rainbow(counter1[0] * (ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB(), true);
                    i += this.renderer.getStringWidth(String.valueOf(c));
                    counter1[0] = counter1[0] + 1;
                }
            }
        } else {
            this.renderer.drawString(text, width / 2.0F - this.renderer.getStringWidth(text) / 2.0F + 2.0F, 2.0F, this.color, true);
        }
    }

    public void renderLag() {
        int width = this.renderer.scaledWidth;
        if (OyVey.serverManager.isServerNotResponding()) {
            String text = ChatFormatting.RED + "Server not responding " + MathUtil.round((float) OyVey.serverManager.serverRespondingTime() / 1000.0F, 1) + "s.";
            this.renderer.drawString(text, width / 2.0F - this.renderer.getStringWidth(text) / 2.0F + 2.0F, 20.0F, this.color, true);
        }
    }

    public void renderTotemHUD() {
        int width = this.renderer.scaledWidth;
        int height = this.renderer.scaledHeight;
        int totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.TOTEM_OF_UNDYING)).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)
            totems += mc.player.getHeldItemOffhand().getCount();
        if (totems > 0) {
            GlStateManager.enableTexture2D();
            int i = width / 2;
            int iteration = 0;
            int y = height - 55 - ((mc.player.isInWater() && mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
            int x = i - 189 + 180 + 2;
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0F;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(totem, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, totem, x, y, "");
            RenderUtil.itemRender.zLevel = 0.0F;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            this.renderer.drawStringWithShadow(totems + "", (x + 19 - 2 - this.renderer.getStringWidth(totems + "")), (y + 9), 16777215);
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }

    public void renderArmorHUD(boolean percent) {
        int width = this.renderer.scaledWidth;
        int height = this.renderer.scaledHeight;
        GlStateManager.enableTexture2D();
        int i = width / 2;
        int iteration = 0;
        int y = height - 55 - (HUD.mc.player.isInWater() && HUD.mc.playerController.gameIsSurvivalOrAdventure() ? 10 : 0);
        for (ItemStack is : HUD.mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) continue;
            int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0f;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(HUD.mc.fontRenderer, is, x, y, "");
            RenderUtil.itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            String s = is.getCount() > 1 ? is.getCount() + "" : "";
            this.renderer.drawStringWithShadow(s, x + 19 - 2 - this.renderer.getStringWidth(s), y + 9, 0xFFFFFF);
            if (!percent) continue;
            int dmg = 0;
            int itemDurability = is.getMaxDamage() - is.getItemDamage();
            float green = ((float) is.getMaxDamage() - (float) is.getItemDamage()) / (float) is.getMaxDamage();
            float red = 1.0f - green;
            dmg = 100 - (int) (red * 100.0f);
            this.renderer.drawStringWithShadow(dmg + "", x + 8 - this.renderer.getStringWidth(dmg + "") / 2, y - 11, ColorUtil.toRGBA((int) (red * 255.0f), (int) (green * 255.0f), 0));
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }

    @SubscribeEvent
    public void onUpdateWalkingPlayer(AttackEntityEvent event) {
        this.shouldIncrement = true;
    }

    public void onLoad() {
        commandManager.setClientMessage(getCommandMessage());
    }

    @SubscribeEvent
    public void onSettingChange(ClientEvent event) {
        if (event.getSetting() != null && this.equals(event.getSetting().getFeature()))
            commandManager.setClientMessage(getCommandMessage());
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getStage() == 0 && event.getPacket() instanceof SPacketChat) {

            if (timestamp.getValue(true)) {
                String originalMessage = ((SPacketChat) event.getPacket()).chatComponent.getFormattedText();
                String message = this.getTimeString(originalMessage) + originalMessage;

                ((SPacketChat) event.getPacket()).chatComponent = new TextComponentString(message);
            }
        }

    }

    public String getTimeString(String message) {
        String date = new SimpleDateFormat("h:mm").format(new Date());
        String timeString = "<" + date + ">" + " ";
        StringBuilder builder = new StringBuilder(timeString);
        builder.insert(0, this.rainbowPrefix.getValue(true) ? "\u00a7+" : ChatFormatting.LIGHT_PURPLE);
        if (!message.contains(HUD.getInstance().getRainbowCommandMessage())) {
            builder.append("\u00a7r");
        }
        return builder.toString();
    }

    public String getTimeString2() {
        String date = new SimpleDateFormat("h:mm").format(new Date());
        String timeString = "<" + date + ">";
        StringBuilder builder = new StringBuilder(timeString);
        return builder.toString();
    }

    public String getCommandMessage() {
        if (commandPrefix.getValue(true) || timestamp.getValue(true)) {
            StringBuilder stringBuilder = new StringBuilder((this.timestamp.getValue(true) ? getTimeString2() : "") + (this.commandPrefix.getValue(true) ? ("<" + this.getRawCommandMessage() + ">") : ""));
            stringBuilder.insert(0, (this.timestamp.getValue(true) || this.commandPrefix.getValue(true)) && this.rainbowPrefix.getValue(true) ? "\u00a7+" : ChatFormatting.LIGHT_PURPLE);
            stringBuilder.append("\u00a7r ");
            return stringBuilder.toString();
        }
        return "";
    }

    public String getRainbowCommandMessage() {
        StringBuilder stringBuilder = new StringBuilder(this.getRawCommandMessage());
        stringBuilder.insert(0, this.rainbowPrefix.getValue(true) ? "\u00a7+" : ChatFormatting.LIGHT_PURPLE);
        stringBuilder.append("\u00a7r");
        return stringBuilder.toString();
    }

    public String getRawCommandMessage() {
        return this.command.getValue(true);
    }

    public void drawTextRadar(final int yOffset) {
        if (!this.players.isEmpty()) {
            int y = this.renderer.getFontHeight() + 7 + yOffset;
            for (final Map.Entry<String, Integer> player : this.players.entrySet()) {
                final String text = player.getKey() + " ";
                final int textheight = this.renderer.getFontHeight() + 1;
                this.renderer.drawString(text, 2.0f, (float) y, (ClickGui.getInstance()).rainbow.getValue(true) ? (((ClickGui.getInstance()).rainbowModeA.getValue(true) == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB() : ColorUtil.rainbow((ClickGui.getInstance()).rainbowHue.getValue(true)).getRGB()) : this.color, true);
                y += textheight;
            }
        }
    }


    public enum RenderingMode {
        Length, ABC
    }
}
