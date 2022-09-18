/*    */ package me.alpha432.oyvey.features.modules.module2b2t;
/*    */ 
/*    */ import me.alpha432.oyvey.features.modules.Module;
/*    */ import me.alpha432.oyvey.features.setting.Setting;
/*    */ 
/*    */ public class QueueSkip
/*    */   extends Module {
/*  8 */   private final Setting<Integer> packets = register(new Setting("Packets", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(4)));
/*    */   
/* 10 */   public Setting<Server> server = register(new Setting("Server", Server.NORMAL));
/*    */   
/* 12 */   public Setting<Mode> mode = register(new Setting("Mode", Mode.NORMAL, v -> (this.server.getValue(true) == Server.NORMAL)));
/* 13 */   public Setting<Float> prioCount = register(new Setting("PrioPlayerCount", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(1000.0F), v -> (this.mode.getValue(true) == Mode.PRIO)));
/* 14 */   public Setting<Float> playerCount = register(new Setting("PlayerCount", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(1000.0F), v -> (this.mode.getValue(true) == Mode.NORMAL)));
/*    */ 
/*    */ 
/*    */   
/*    */   public QueueSkip() {
/* 19 */     super("QueueSkip", "Skips the QUEUE!", Module.Category.MODULE2B2T, true, false, true);
/*    */   }
/*    */   
/*    */   public void onUpdate() {}
/*    */   
/*    */   public enum Mode {
/* 25 */     PRIO,
/* 26 */     NORMAL; }
/*    */   
/*    */   public enum Server {
/* 29 */     NORMAL,
/* 30 */     OLDFAG,
/* 31 */     NINEBEE;
/*    */   }
/*    */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\misc\QueueSkip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */