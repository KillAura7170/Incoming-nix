/*    */ package me.alpha432.oyvey.features.modules.player;
/*    */ 
/*    */ import me.alpha432.oyvey.OyVey;
/*    */ import me.alpha432.oyvey.features.modules.Module;
/*    */ import me.alpha432.oyvey.features.setting.Setting;
/*    */ import me.alpha432.oyvey.util.Timer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimerSpeed
/*    */   extends Module
/*    */ {
/* 13 */   public Setting<Boolean> autoOff = register(new Setting("AutoOff", Boolean.valueOf(false)));
/* 14 */   public Setting<Integer> timeLimit = register(new Setting("Limit", Integer.valueOf(250), Integer.valueOf(1), Integer.valueOf(2500), v -> ((Boolean)this.autoOff.getValue(true)).booleanValue()));
/* 15 */   public Setting<TimerMode> mode = register(new Setting("Mode", TimerMode.NORMAL));
/* 16 */   public Setting<Float> timerSpeed = register(new Setting("Speed", Float.valueOf(4.0F), Float.valueOf(0.1F), Float.valueOf(20.0F)));
/* 17 */   public Setting<Float> fastSpeed = register(new Setting("Fast", Float.valueOf(10.0F), Float.valueOf(0.1F), Float.valueOf(100.0F), v -> (this.mode.getValue(true) == TimerMode.SWITCH), "Fast Speed for switch."));
/* 18 */   public Setting<Integer> fastTime = register(new Setting("FastTime", Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(500), v -> (this.mode.getValue(true) == TimerMode.SWITCH), "How long you want to go fast.(ms * 10)"));
/* 19 */   public Setting<Integer> slowTime = register(new Setting("SlowTime", Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(500), v -> (this.mode.getValue(true) == TimerMode.SWITCH), "Recover from too fast.(ms * 10)"));
/* 20 */   public Setting<Boolean> startFast = register(new Setting("StartFast", Boolean.valueOf(false), v -> (this.mode.getValue(true) == TimerMode.SWITCH)));
/* 21 */   public float speed = 1.0F;
/* 22 */   private final Timer timer = new Timer();
/* 23 */   private final Timer turnOffTimer = new Timer();
/*    */   private boolean fast = false;
/*    */   
/*    */   public TimerSpeed() {
/* 27 */     super("Timer", "Will speed up the game.", Module.Category.PLAYER, false, false, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 32 */     this.turnOffTimer.reset();
/* 33 */     this.speed = ((Float)this.timerSpeed.getValue(true)).floatValue();
/* 34 */     if (!((Boolean)this.startFast.getValue(true)).booleanValue()) {
/* 35 */       this.timer.reset();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 41 */     if (((Boolean)this.autoOff.getValue(true)).booleanValue() && this.turnOffTimer.passedMs(((Integer)this.timeLimit.getValue(true)).intValue())) {
/* 42 */       disable();
/*    */       return;
/*    */     } 
/* 45 */     if (this.mode.getValue(true) == TimerMode.NORMAL) {
/* 46 */       this.speed = ((Float)this.timerSpeed.getValue(true)).floatValue();
/*    */       return;
/*    */     } 
/* 49 */     if (!this.fast && this.timer.passedDms(((Integer)this.slowTime.getValue(true)).intValue())) {
/* 50 */       this.fast = true;
/* 51 */       this.speed = ((Float)this.fastSpeed.getValue(true)).floatValue();
/* 52 */       this.timer.reset();
/*    */     } 
/* 54 */     if (this.fast && this.timer.passedDms(((Integer)this.fastTime.getValue(true)).intValue())) {
/* 55 */       this.fast = false;
/* 56 */       this.speed = ((Float)this.timerSpeed.getValue(true)).floatValue();
/* 57 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 63 */     this.speed = 1.0F;
/* 64 */     OyVey.timerManager.reset();
/* 65 */     this.fast = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayInfo() {
/* 70 */     return this.timerSpeed.getValueAsString();
/*    */   }
/*    */   
/*    */   public enum TimerMode {
/* 74 */     NORMAL,
/* 75 */     SWITCH;
/*    */   }
/*    */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\player\TimerSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */