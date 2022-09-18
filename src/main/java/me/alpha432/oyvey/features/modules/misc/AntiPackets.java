/*     */ package me.alpha432.oyvey.features.modules.misc;
/*     */ 
/*     */ import me.alpha432.oyvey.event.events.PacketEvent;
/*     */ import me.alpha432.oyvey.features.command.Command;
/*     */ import me.alpha432.oyvey.features.modules.Module;
/*     */ import me.alpha432.oyvey.features.setting.Setting;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AntiPackets
/*     */   extends Module
/*     */ {
/* 121 */   private final Setting<Mode> mode = register(new Setting("Packets", Mode.CLIENT));
/* 122 */   private final Setting<Integer> page = register(new Setting("SPackets", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(10), v -> (this.mode.getValue(true) == Mode.SERVER)));
/* 123 */   private final Setting<Integer> pages = register(new Setting("CPackets", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(4), v -> (this.mode.getValue(true) == Mode.CLIENT)));
/* 124 */   private final Setting<Boolean> AdvancementInfo = register(new Setting("AdvancementInfo", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 1)));
/* 125 */   private final Setting<Boolean> Animation = register(new Setting("Animation", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 1)));
/* 126 */   private final Setting<Boolean> BlockAction = register(new Setting("BlockAction", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 1)));
/* 127 */   private final Setting<Boolean> BlockBreakAnim = register(new Setting("BlockBreakAnim", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 1)));
/* 128 */   private final Setting<Boolean> BlockChange = register(new Setting("BlockChange", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 1)));
/* 129 */   private final Setting<Boolean> Camera = register(new Setting("Camera", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 1)));
/* 130 */   private final Setting<Boolean> ChangeGameState = register(new Setting("ChangeGameState", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 1)));
/* 131 */   private final Setting<Boolean> Chat = register(new Setting("Chat", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 1)));
/* 132 */   private final Setting<Boolean> ChunkData = register(new Setting("ChunkData", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 2)));
/* 133 */   private final Setting<Boolean> CloseWindow = register(new Setting("CloseWindow", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 2)));
/* 134 */   private final Setting<Boolean> CollectItem = register(new Setting("CollectItem", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 2)));
/* 135 */   private final Setting<Boolean> CombatEvent = register(new Setting("Combatevent", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 2)));
/* 136 */   private final Setting<Boolean> ConfirmTransaction = register(new Setting("ConfirmTransaction", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 2)));
/* 137 */   private final Setting<Boolean> Cooldown = register(new Setting("Cooldown", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 2)));
/* 138 */   private final Setting<Boolean> CustomPayload = register(new Setting("CustomPayload", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 2)));
/* 139 */   private final Setting<Boolean> CustomSound = register(new Setting("CustomSound", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 2)));
/* 140 */   private final Setting<Boolean> DestroyEntities = register(new Setting("DestroyEntities", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 3)));
/* 141 */   private final Setting<Boolean> Disconnect = register(new Setting("Disconnect", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 3)));
/* 142 */   private final Setting<Boolean> DisplayObjective = register(new Setting("DisplayObjective", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 3)));
/* 143 */   private final Setting<Boolean> Effect = register(new Setting("Effect", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 3)));
/* 144 */   private final Setting<Boolean> Entity = register(new Setting("Entity", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 3)));
/* 145 */   private final Setting<Boolean> EntityAttach = register(new Setting("EntityAttach", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 3)));
/* 146 */   private final Setting<Boolean> EntityEffect = register(new Setting("EntityEffect", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 3)));
/* 147 */   private final Setting<Boolean> EntityEquipment = register(new Setting("EntityEquipment", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 3)));
/* 148 */   private final Setting<Boolean> EntityHeadLook = register(new Setting("EntityHeadLook", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 4)));
/* 149 */   private final Setting<Boolean> EntityMetadata = register(new Setting("EntityMetadata", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 4)));
/* 150 */   private final Setting<Boolean> EntityProperties = register(new Setting("EntityProperties", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 4)));
/* 151 */   private final Setting<Boolean> EntityStatus = register(new Setting("EntityStatus", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 4)));
/* 152 */   private final Setting<Boolean> EntityTeleport = register(new Setting("EntityTeleport", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 4)));
/* 153 */   private final Setting<Boolean> EntityVelocity = register(new Setting("EntityVelocity", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 4)));
/* 154 */   private final Setting<Boolean> Explosion = register(new Setting("Explosion", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 4)));
/* 155 */   private final Setting<Boolean> HeldItemChange = register(new Setting("HeldItemChange", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 4)));
/* 156 */   private final Setting<Boolean> JoinGame = register(new Setting("JoinGame", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 5)));
/* 157 */   private final Setting<Boolean> KeepAlive = register(new Setting("KeepAlive", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 5)));
/* 158 */   private final Setting<Boolean> Maps = register(new Setting("Maps", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 5)));
/* 159 */   private final Setting<Boolean> MoveVehicle = register(new Setting("MoveVehicle", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 5)));
/* 160 */   private final Setting<Boolean> MultiBlockChange = register(new Setting("MultiBlockChange", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 5)));
/* 161 */   private final Setting<Boolean> OpenWindow = register(new Setting("OpenWindow", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 5)));
/* 162 */   private final Setting<Boolean> Particles = register(new Setting("Particles", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 5)));
/* 163 */   private final Setting<Boolean> PlaceGhostRecipe = register(new Setting("PlaceGhostRecipe", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 5)));
/* 164 */   private final Setting<Boolean> PlayerAbilities = register(new Setting("PlayerAbilities", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 6)));
/* 165 */   private final Setting<Boolean> PlayerListHeaderFooter = register(new Setting("PlayerListHeaderFooter", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 6)));
/* 166 */   private final Setting<Boolean> PlayerListItem = register(new Setting("PlayerListItem", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 6)));
/* 167 */   private final Setting<Boolean> PlayerPosLook = register(new Setting("PlayerPosLook", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 6)));
/* 168 */   private final Setting<Boolean> RecipeBook = register(new Setting("RecipeBook", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 6)));
/* 169 */   private final Setting<Boolean> RemoveEntityEffect = register(new Setting("RemoveEntityEffect", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 6)));
/* 170 */   private final Setting<Boolean> ResourcePackSend = register(new Setting("ResourcePackSend", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 6)));
/* 171 */   private final Setting<Boolean> Respawn = register(new Setting("Respawn", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 6)));
/* 172 */   private final Setting<Boolean> ScoreboardObjective = register(new Setting("ScoreboardObjective", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 7)));
/* 173 */   private final Setting<Boolean> SelectAdvancementsTab = register(new Setting("SelectAdvancementsTab", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 7)));
/* 174 */   private final Setting<Boolean> ServerDifficulty = register(new Setting("ServerDifficulty", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 7)));
/* 175 */   private final Setting<Boolean> SetExperience = register(new Setting("SetExperience", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 7)));
/* 176 */   private final Setting<Boolean> SetPassengers = register(new Setting("SetPassengers", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 7)));
/* 177 */   private final Setting<Boolean> SetSlot = register(new Setting("SetSlot", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 7)));
/* 178 */   private final Setting<Boolean> SignEditorOpen = register(new Setting("SignEditorOpen", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 7)));
/* 179 */   private final Setting<Boolean> SoundEffect = register(new Setting("SoundEffect", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 7)));
/* 180 */   private final Setting<Boolean> SpawnExperienceOrb = register(new Setting("SpawnExperienceOrb", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 8)));
/* 181 */   private final Setting<Boolean> SpawnGlobalEntity = register(new Setting("SpawnGlobalEntity", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 8)));
/* 182 */   private final Setting<Boolean> SpawnMob = register(new Setting("SpawnMob", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 8)));
/* 183 */   private final Setting<Boolean> SpawnObject = register(new Setting("SpawnObject", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 8)));
/* 184 */   private final Setting<Boolean> SpawnPainting = register(new Setting("SpawnPainting", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 8)));
/* 185 */   private final Setting<Boolean> SpawnPlayer = register(new Setting("SpawnPlayer", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 8)));
/* 186 */   private final Setting<Boolean> SpawnPosition = register(new Setting("SpawnPosition", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 8)));
/* 187 */   private final Setting<Boolean> Statistics = register(new Setting("Statistics", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 8)));
/* 188 */   private final Setting<Boolean> TabComplete = register(new Setting("TabComplete", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 9)));
/* 189 */   private final Setting<Boolean> Teams = register(new Setting("Teams", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 9)));
/* 190 */   private final Setting<Boolean> TimeUpdate = register(new Setting("TimeUpdate", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 9)));
/* 191 */   private final Setting<Boolean> Title = register(new Setting("Title", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 9)));
/* 192 */   private final Setting<Boolean> UnloadChunk = register(new Setting("UnloadChunk", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 9)));
/* 193 */   private final Setting<Boolean> UpdateBossInfo = register(new Setting("UpdateBossInfo", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 9)));
/* 194 */   private final Setting<Boolean> UpdateHealth = register(new Setting("UpdateHealth", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 9)));
/* 195 */   private final Setting<Boolean> UpdateScore = register(new Setting("UpdateScore", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 9)));
/* 196 */   private final Setting<Boolean> UpdateTileEntity = register(new Setting("UpdateTileEntity", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 10)));
/* 197 */   private final Setting<Boolean> UseBed = register(new Setting("UseBed", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 10)));
/* 198 */   private final Setting<Boolean> WindowItems = register(new Setting("WindowItems", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 10)));
/* 199 */   private final Setting<Boolean> WindowProperty = register(new Setting("WindowProperty", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 10)));
/* 200 */   private final Setting<Boolean> WorldBorder = register(new Setting("WorldBorder", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.SERVER && ((Integer)this.page.getValue(true)).intValue() == 10)));
/* 201 */   private final Setting<Boolean> Animations = register(new Setting("Animations", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 1)));
/* 202 */   private final Setting<Boolean> ChatMessage = register(new Setting("ChatMessage", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 1)));
/* 203 */   private final Setting<Boolean> ClickWindow = register(new Setting("ClickWindow", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 1)));
/* 204 */   private final Setting<Boolean> ClientSettings = register(new Setting("ClientSettings", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 1)));
/* 205 */   private final Setting<Boolean> ClientStatus = register(new Setting("ClientStatus", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 1)));
/* 206 */   private final Setting<Boolean> CloseWindows = register(new Setting("CloseWindows", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 1)));
/* 207 */   private final Setting<Boolean> ConfirmTeleport = register(new Setting("ConfirmTeleport", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 1)));
/* 208 */   private final Setting<Boolean> ConfirmTransactions = register(new Setting("ConfirmTransactions", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 1)));
/* 209 */   private final Setting<Boolean> CreativeInventoryAction = register(new Setting("CreativeInventoryAction", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 2)));
/* 210 */   private final Setting<Boolean> CustomPayloads = register(new Setting("CustomPayloads", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 2)));
/* 211 */   private final Setting<Boolean> EnchantItem = register(new Setting("EnchantItem", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 2)));
/* 212 */   private final Setting<Boolean> EntityAction = register(new Setting("EntityAction", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 2)));
/* 213 */   private final Setting<Boolean> HeldItemChanges = register(new Setting("HeldItemChanges", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 2)));
/* 214 */   private final Setting<Boolean> Input = register(new Setting("Input", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 2)));
/* 215 */   private final Setting<Boolean> KeepAlives = register(new Setting("KeepAlives", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 2)));
/* 216 */   private final Setting<Boolean> PlaceRecipe = register(new Setting("PlaceRecipe", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 2)));
/* 217 */   private final Setting<Boolean> Player = register(new Setting("Player", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 3)));
/* 218 */   private final Setting<Boolean> PlayerAbility = register(new Setting("PlayerAbility", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 3)));
/* 219 */   private final Setting<Boolean> PlayerDigging = register(new Setting("PlayerDigging", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.page.getValue(true)).intValue() == 3)));
/* 220 */   private final Setting<Boolean> PlayerTryUseItem = register(new Setting("PlayerTryUseItem", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 3)));
/* 221 */   private final Setting<Boolean> PlayerTryUseItemOnBlock = register(new Setting("TryUseItemOnBlock", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 3)));
/* 222 */   private final Setting<Boolean> RecipeInfo = register(new Setting("RecipeInfo", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 3)));
/* 223 */   private final Setting<Boolean> ResourcePackStatus = register(new Setting("ResourcePackStatus", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 3)));
/* 224 */   private final Setting<Boolean> SeenAdvancements = register(new Setting("SeenAdvancements", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 3)));
/* 225 */   private final Setting<Boolean> PlayerPackets = register(new Setting("PlayerPackets", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 4)));
/* 226 */   private final Setting<Boolean> Spectate = register(new Setting("Spectate", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 4)));
/* 227 */   private final Setting<Boolean> SteerBoat = register(new Setting("SteerBoat", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 4)));
/* 228 */   private final Setting<Boolean> TabCompletion = register(new Setting("TabCompletion", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 4)));
/* 229 */   private final Setting<Boolean> UpdateSign = register(new Setting("UpdateSign", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 4)));
/* 230 */   private final Setting<Boolean> UseEntity = register(new Setting("UseEntity", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 4)));
/* 231 */   private final Setting<Boolean> VehicleMove = register(new Setting("VehicleMove", Boolean.valueOf(false), v -> (this.mode.getValue(true) == Mode.CLIENT && ((Integer)this.pages.getValue(true)).intValue() == 4)));
/* 232 */   private int hudAmount = 0;
/*     */   
/*     */   public AntiPackets() {
/* 235 */     super("PacketCancel", "pc", Module.Category.MISC, true, false, false);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Send event) {
/* 240 */     if (!isEnabled()) {
/*     */       return;
/*     */     }
/* 243 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketAnimation && ((Boolean)this.Animations.getValue(true)).booleanValue()) {
/* 244 */       event.setCanceled(true);
/*     */     }
/* 246 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketChatMessage && ((Boolean)this.ChatMessage.getValue(true)).booleanValue()) {
/* 247 */       event.setCanceled(true);
/*     */     }
/* 249 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketClickWindow && ((Boolean)this.ClickWindow.getValue(true)).booleanValue()) {
/* 250 */       event.setCanceled(true);
/*     */     }
/* 252 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketClientSettings && ((Boolean)this.ClientSettings.getValue(true)).booleanValue()) {
/* 253 */       event.setCanceled(true);
/*     */     }
/* 255 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketClientStatus && ((Boolean)this.ClientStatus.getValue(true)).booleanValue()) {
/* 256 */       event.setCanceled(true);
/*     */     }
/* 258 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketCloseWindow && ((Boolean)this.CloseWindows.getValue(true)).booleanValue()) {
/* 259 */       event.setCanceled(true);
/*     */     }
/* 261 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketConfirmTeleport && ((Boolean)this.ConfirmTeleport.getValue(true)).booleanValue()) {
/* 262 */       event.setCanceled(true);
/*     */     }
/* 264 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketConfirmTransaction && ((Boolean)this.ConfirmTransactions.getValue(true)).booleanValue()) {
/* 265 */       event.setCanceled(true);
/*     */     }
/* 267 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketCreativeInventoryAction && ((Boolean)this.CreativeInventoryAction.getValue(true)).booleanValue()) {
/* 268 */       event.setCanceled(true);
/*     */     }
/* 270 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketCustomPayload && ((Boolean)this.CustomPayloads.getValue(true)).booleanValue()) {
/* 271 */       event.setCanceled(true);
/*     */     }
/* 273 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketEnchantItem && ((Boolean)this.EnchantItem.getValue(true)).booleanValue()) {
/* 274 */       event.setCanceled(true);
/*     */     }
/* 276 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketEntityAction && ((Boolean)this.EntityAction.getValue(true)).booleanValue()) {
/* 277 */       event.setCanceled(true);
/*     */     }
/* 279 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketHeldItemChange && ((Boolean)this.HeldItemChanges.getValue(true)).booleanValue()) {
/* 280 */       event.setCanceled(true);
/*     */     }
/* 282 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketInput && ((Boolean)this.Input.getValue(true)).booleanValue()) {
/* 283 */       event.setCanceled(true);
/*     */     }
/* 285 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketKeepAlive && ((Boolean)this.KeepAlives.getValue(true)).booleanValue()) {
/* 286 */       event.setCanceled(true);
/*     */     }
/* 288 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlaceRecipe && ((Boolean)this.PlaceRecipe.getValue(true)).booleanValue()) {
/* 289 */       event.setCanceled(true);
/*     */     }
/* 291 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer && ((Boolean)this.Player.getValue(true)).booleanValue()) {
/* 292 */       event.setCanceled(true);
/*     */     }
/* 294 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayerAbilities && ((Boolean)this.PlayerAbility.getValue(true)).booleanValue()) {
/* 295 */       event.setCanceled(true);
/*     */     }
/* 297 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayerDigging && ((Boolean)this.PlayerDigging.getValue(true)).booleanValue()) {
/* 298 */       event.setCanceled(true);
/*     */     }
/* 300 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayerTryUseItem && ((Boolean)this.PlayerTryUseItem.getValue(true)).booleanValue()) {
/* 301 */       event.setCanceled(true);
/*     */     }
/* 303 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock && ((Boolean)this.PlayerTryUseItemOnBlock.getValue(true)).booleanValue()) {
/* 304 */       event.setCanceled(true);
/*     */     }
/* 306 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketRecipeInfo && ((Boolean)this.RecipeInfo.getValue(true)).booleanValue()) {
/* 307 */       event.setCanceled(true);
/*     */     }
/* 309 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketResourcePackStatus && ((Boolean)this.ResourcePackStatus.getValue(true)).booleanValue()) {
/* 310 */       event.setCanceled(true);
/*     */     }
/* 312 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketSeenAdvancements && ((Boolean)this.SeenAdvancements.getValue(true)).booleanValue()) {
/* 313 */       event.setCanceled(true);
/*     */     }
/* 315 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketSpectate && ((Boolean)this.Spectate.getValue(true)).booleanValue()) {
/* 316 */       event.setCanceled(true);
/*     */     }
/* 318 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketSteerBoat && ((Boolean)this.SteerBoat.getValue(true)).booleanValue()) {
/* 319 */       event.setCanceled(true);
/*     */     }
/* 321 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketTabComplete && ((Boolean)this.TabCompletion.getValue(true)).booleanValue()) {
/* 322 */       event.setCanceled(true);
/*     */     }
/* 324 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketUpdateSign && ((Boolean)this.UpdateSign.getValue(true)).booleanValue()) {
/* 325 */       event.setCanceled(true);
/*     */     }
/* 327 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketUseEntity && ((Boolean)this.UseEntity.getValue(true)).booleanValue()) {
/* 328 */       event.setCanceled(true);
/*     */     }
/* 330 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketVehicleMove && ((Boolean)this.VehicleMove.getValue(true)).booleanValue()) {
/* 331 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 337 */     if (!isEnabled()) {
/*     */       return;
/*     */     }
/* 340 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketAdvancementInfo && ((Boolean)this.AdvancementInfo.getValue(true)).booleanValue()) {
/* 341 */       event.setCanceled(true);
/*     */     }
/* 343 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketAnimation && ((Boolean)this.Animation.getValue(true)).booleanValue()) {
/* 344 */       event.setCanceled(true);
/*     */     }
/* 346 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketBlockAction && ((Boolean)this.BlockAction.getValue(true)).booleanValue()) {
/* 347 */       event.setCanceled(true);
/*     */     }
/* 349 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketBlockBreakAnim && ((Boolean)this.BlockBreakAnim.getValue(true)).booleanValue()) {
/* 350 */       event.setCanceled(true);
/*     */     }
/* 352 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketBlockChange && ((Boolean)this.BlockChange.getValue(true)).booleanValue()) {
/* 353 */       event.setCanceled(true);
/*     */     }
/* 355 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketCamera && ((Boolean)this.Camera.getValue(true)).booleanValue()) {
/* 356 */       event.setCanceled(true);
/*     */     }
/* 358 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketChangeGameState && ((Boolean)this.ChangeGameState.getValue(true)).booleanValue()) {
/* 359 */       event.setCanceled(true);
/*     */     }
/* 361 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketChat && ((Boolean)this.Chat.getValue(true)).booleanValue()) {
/* 362 */       event.setCanceled(true);
/*     */     }
/* 364 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketChunkData && ((Boolean)this.ChunkData.getValue(true)).booleanValue()) {
/* 365 */       event.setCanceled(true);
/*     */     }
/* 367 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketCloseWindow && ((Boolean)this.CloseWindow.getValue(true)).booleanValue()) {
/* 368 */       event.setCanceled(true);
/*     */     }
/* 370 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketCollectItem && ((Boolean)this.CollectItem.getValue(true)).booleanValue()) {
/* 371 */       event.setCanceled(true);
/*     */     }
/* 373 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketCombatEvent && ((Boolean)this.CombatEvent.getValue(true)).booleanValue()) {
/* 374 */       event.setCanceled(true);
/*     */     }
/* 376 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketConfirmTransaction && ((Boolean)this.ConfirmTransaction.getValue(true)).booleanValue()) {
/* 377 */       event.setCanceled(true);
/*     */     }
/* 379 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketCooldown && ((Boolean)this.Cooldown.getValue(true)).booleanValue()) {
/* 380 */       event.setCanceled(true);
/*     */     }
/* 382 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketCustomPayload && ((Boolean)this.CustomPayload.getValue(true)).booleanValue()) {
/* 383 */       event.setCanceled(true);
/*     */     }
/* 385 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketCustomSound && ((Boolean)this.CustomSound.getValue(true)).booleanValue()) {
/* 386 */       event.setCanceled(true);
/*     */     }
/* 388 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketDestroyEntities && ((Boolean)this.DestroyEntities.getValue(true)).booleanValue()) {
/* 389 */       event.setCanceled(true);
/*     */     }
/* 391 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketDisconnect && ((Boolean)this.Disconnect.getValue(true)).booleanValue()) {
/* 392 */       event.setCanceled(true);
/*     */     }
/* 394 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketChunkData && ((Boolean)this.ChunkData.getValue(true)).booleanValue()) {
/* 395 */       event.setCanceled(true);
/*     */     }
/* 397 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketCloseWindow && ((Boolean)this.CloseWindow.getValue(true)).booleanValue()) {
/* 398 */       event.setCanceled(true);
/*     */     }
/* 400 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketCollectItem && ((Boolean)this.CollectItem.getValue(true)).booleanValue()) {
/* 401 */       event.setCanceled(true);
/*     */     }
/* 403 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketDisplayObjective && ((Boolean)this.DisplayObjective.getValue(true)).booleanValue()) {
/* 404 */       event.setCanceled(true);
/*     */     }
/* 406 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketEffect && ((Boolean)this.Effect.getValue(true)).booleanValue()) {
/* 407 */       event.setCanceled(true);
/*     */     }
/* 409 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketEntity && ((Boolean)this.Entity.getValue(true)).booleanValue()) {
/* 410 */       event.setCanceled(true);
/*     */     }
/* 412 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketEntityAttach && ((Boolean)this.EntityAttach.getValue(true)).booleanValue()) {
/* 413 */       event.setCanceled(true);
/*     */     }
/* 415 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketEntityEffect && ((Boolean)this.EntityEffect.getValue(true)).booleanValue()) {
/* 416 */       event.setCanceled(true);
/*     */     }
/* 418 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketEntityEquipment && ((Boolean)this.EntityEquipment.getValue(true)).booleanValue()) {
/* 419 */       event.setCanceled(true);
/*     */     }
/* 421 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketEntityHeadLook && ((Boolean)this.EntityHeadLook.getValue(true)).booleanValue()) {
/* 422 */       event.setCanceled(true);
/*     */     }
/* 424 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketEntityMetadata && ((Boolean)this.EntityMetadata.getValue(true)).booleanValue()) {
/* 425 */       event.setCanceled(true);
/*     */     }
/* 427 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketEntityProperties && ((Boolean)this.EntityProperties.getValue(true)).booleanValue()) {
/* 428 */       event.setCanceled(true);
/*     */     }
/* 430 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketEntityStatus && ((Boolean)this.EntityStatus.getValue(true)).booleanValue()) {
/* 431 */       event.setCanceled(true);
/*     */     }
/* 433 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketEntityTeleport && ((Boolean)this.EntityTeleport.getValue(true)).booleanValue()) {
/* 434 */       event.setCanceled(true);
/*     */     }
/* 436 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketEntityVelocity && ((Boolean)this.EntityVelocity.getValue(true)).booleanValue()) {
/* 437 */       event.setCanceled(true);
/*     */     }
/* 439 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketExplosion && ((Boolean)this.Explosion.getValue(true)).booleanValue()) {
/* 440 */       event.setCanceled(true);
/*     */     }
/* 442 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketHeldItemChange && ((Boolean)this.HeldItemChange.getValue(true)).booleanValue()) {
/* 443 */       event.setCanceled(true);
/*     */     }
/* 445 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketJoinGame && ((Boolean)this.JoinGame.getValue(true)).booleanValue()) {
/* 446 */       event.setCanceled(true);
/*     */     }
/* 448 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketKeepAlive && ((Boolean)this.KeepAlive.getValue(true)).booleanValue()) {
/* 449 */       event.setCanceled(true);
/*     */     }
/* 451 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketMaps && ((Boolean)this.Maps.getValue(true)).booleanValue()) {
/* 452 */       event.setCanceled(true);
/*     */     }
/* 454 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketMoveVehicle && ((Boolean)this.MoveVehicle.getValue(true)).booleanValue()) {
/* 455 */       event.setCanceled(true);
/*     */     }
/* 457 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketMultiBlockChange && ((Boolean)this.MultiBlockChange.getValue(true)).booleanValue()) {
/* 458 */       event.setCanceled(true);
/*     */     }
/* 460 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketOpenWindow && ((Boolean)this.OpenWindow.getValue(true)).booleanValue()) {
/* 461 */       event.setCanceled(true);
/*     */     }
/* 463 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketParticles && ((Boolean)this.Particles.getValue(true)).booleanValue()) {
/* 464 */       event.setCanceled(true);
/*     */     }
/* 466 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketPlaceGhostRecipe && ((Boolean)this.PlaceGhostRecipe.getValue(true)).booleanValue()) {
/* 467 */       event.setCanceled(true);
/*     */     }
/* 469 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerAbilities && ((Boolean)this.PlayerAbilities.getValue(true)).booleanValue()) {
/* 470 */       event.setCanceled(true);
/*     */     }
/* 472 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerListHeaderFooter && ((Boolean)this.PlayerListHeaderFooter.getValue(true)).booleanValue()) {
/* 473 */       event.setCanceled(true);
/*     */     }
/* 475 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerListItem && ((Boolean)this.PlayerListItem.getValue(true)).booleanValue()) {
/* 476 */       event.setCanceled(true);
/*     */     }
/* 478 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook && ((Boolean)this.PlayerPosLook.getValue(true)).booleanValue()) {
/* 479 */       event.setCanceled(true);
/*     */     }
/* 481 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketRecipeBook && ((Boolean)this.RecipeBook.getValue(true)).booleanValue()) {
/* 482 */       event.setCanceled(true);
/*     */     }
/* 484 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketRemoveEntityEffect && ((Boolean)this.RemoveEntityEffect.getValue(true)).booleanValue()) {
/* 485 */       event.setCanceled(true);
/*     */     }
/* 487 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketResourcePackSend && ((Boolean)this.ResourcePackSend.getValue(true)).booleanValue()) {
/* 488 */       event.setCanceled(true);
/*     */     }
/* 490 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketRespawn && ((Boolean)this.Respawn.getValue(true)).booleanValue()) {
/* 491 */       event.setCanceled(true);
/*     */     }
/* 493 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketScoreboardObjective && ((Boolean)this.ScoreboardObjective.getValue(true)).booleanValue()) {
/* 494 */       event.setCanceled(true);
/*     */     }
/* 496 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketSelectAdvancementsTab && ((Boolean)this.SelectAdvancementsTab.getValue(true)).booleanValue()) {
/* 497 */       event.setCanceled(true);
/*     */     }
/* 499 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketServerDifficulty && ((Boolean)this.ServerDifficulty.getValue(true)).booleanValue()) {
/* 500 */       event.setCanceled(true);
/*     */     }
/* 502 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketSetExperience && ((Boolean)this.SetExperience.getValue(true)).booleanValue()) {
/* 503 */       event.setCanceled(true);
/*     */     }
/* 505 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketSetPassengers && ((Boolean)this.SetPassengers.getValue(true)).booleanValue()) {
/* 506 */       event.setCanceled(true);
/*     */     }
/* 508 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketSetSlot && ((Boolean)this.SetSlot.getValue(true)).booleanValue()) {
/* 509 */       event.setCanceled(true);
/*     */     }
/* 511 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketSignEditorOpen && ((Boolean)this.SignEditorOpen.getValue(true)).booleanValue()) {
/* 512 */       event.setCanceled(true);
/*     */     }
/* 514 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketSoundEffect && ((Boolean)this.SoundEffect.getValue(true)).booleanValue()) {
/* 515 */       event.setCanceled(true);
/*     */     }
/* 517 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketSpawnExperienceOrb && ((Boolean)this.SpawnExperienceOrb.getValue(true)).booleanValue()) {
/* 518 */       event.setCanceled(true);
/*     */     }
/* 520 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketSpawnGlobalEntity && ((Boolean)this.SpawnGlobalEntity.getValue(true)).booleanValue()) {
/* 521 */       event.setCanceled(true);
/*     */     }
/* 523 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketSpawnMob && ((Boolean)this.SpawnMob.getValue(true)).booleanValue()) {
/* 524 */       event.setCanceled(true);
/*     */     }
/* 526 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketSpawnObject && ((Boolean)this.SpawnObject.getValue(true)).booleanValue()) {
/* 527 */       event.setCanceled(true);
/*     */     }
/* 529 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketSpawnPainting && ((Boolean)this.SpawnPainting.getValue(true)).booleanValue()) {
/* 530 */       event.setCanceled(true);
/*     */     }
/* 532 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketSpawnPlayer && ((Boolean)this.SpawnPlayer.getValue(true)).booleanValue()) {
/* 533 */       event.setCanceled(true);
/*     */     }
/* 535 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketSpawnPosition && ((Boolean)this.SpawnPosition.getValue(true)).booleanValue()) {
/* 536 */       event.setCanceled(true);
/*     */     }
/* 538 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketStatistics && ((Boolean)this.Statistics.getValue(true)).booleanValue()) {
/* 539 */       event.setCanceled(true);
/*     */     }
/* 541 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketTabComplete && ((Boolean)this.TabComplete.getValue(true)).booleanValue()) {
/* 542 */       event.setCanceled(true);
/*     */     }
/* 544 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketTeams && ((Boolean)this.Teams.getValue(true)).booleanValue()) {
/* 545 */       event.setCanceled(true);
/*     */     }
/* 547 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketTimeUpdate && ((Boolean)this.TimeUpdate.getValue(true)).booleanValue()) {
/* 548 */       event.setCanceled(true);
/*     */     }
/* 550 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketTitle && ((Boolean)this.Title.getValue(true)).booleanValue()) {
/* 551 */       event.setCanceled(true);
/*     */     }
/* 553 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketUnloadChunk && ((Boolean)this.UnloadChunk.getValue(true)).booleanValue()) {
/* 554 */       event.setCanceled(true);
/*     */     }
/* 556 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketUpdateBossInfo && ((Boolean)this.UpdateBossInfo.getValue(true)).booleanValue()) {
/* 557 */       event.setCanceled(true);
/*     */     }
/* 559 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketUpdateHealth && ((Boolean)this.UpdateHealth.getValue(true)).booleanValue()) {
/* 560 */       event.setCanceled(true);
/*     */     }
/* 562 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketUpdateScore && ((Boolean)this.UpdateScore.getValue(true)).booleanValue()) {
/* 563 */       event.setCanceled(true);
/*     */     }
/* 565 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketUpdateTileEntity && ((Boolean)this.UpdateTileEntity.getValue(true)).booleanValue()) {
/* 566 */       event.setCanceled(true);
/*     */     }
/* 568 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketUseBed && ((Boolean)this.UseBed.getValue(true)).booleanValue()) {
/* 569 */       event.setCanceled(true);
/*     */     }
/* 571 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketWindowItems && ((Boolean)this.WindowItems.getValue(true)).booleanValue()) {
/* 572 */       event.setCanceled(true);
/*     */     }
/* 574 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketWindowProperty && ((Boolean)this.WindowProperty.getValue(true)).booleanValue()) {
/* 575 */       event.setCanceled(true);
/*     */     }
/* 577 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketWorldBorder && ((Boolean)this.WorldBorder.getValue(true)).booleanValue()) {
/* 578 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 584 */     String standart = "§aAntiPackets On!§f Cancelled Packets: ";
/* 585 */     StringBuilder text = new StringBuilder(standart);
/* 586 */     if (!this.settings.isEmpty()) {
/* 587 */       for (Setting setting : this.settings) {
/* 588 */         if (!(setting.getValue(true) instanceof Boolean) || !((Boolean)setting.getValue(true)).booleanValue() || setting.getName().equalsIgnoreCase("Enabled") || setting.getName().equalsIgnoreCase("drawn"))
/* 589 */           continue;  String name = setting.getName();
/* 590 */         text.append(name).append(", ");
/*     */       } 
/*     */     }
/* 593 */     if (text.toString().equals(standart)) {
/* 594 */       Command.sendMessage("§aAntiPackets On!§f Currently not cancelling any Packets.");
/*     */     } else {
/* 596 */       String output = removeLastChar(removeLastChar(text.toString()));
/* 597 */       Command.sendMessage(output);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 603 */     int amount = 0;
/* 604 */     if (!this.settings.isEmpty()) {
/* 605 */       for (Setting setting : this.settings) {
/* 606 */         if (!(setting.getValue(true) instanceof Boolean) || !((Boolean)setting.getValue(true)).booleanValue() || setting.getName().equalsIgnoreCase("Enabled") || setting.getName().equalsIgnoreCase("drawn"))
/* 607 */           continue;  amount++;
/*     */       } 
/*     */     }
/* 610 */     this.hudAmount = amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/* 615 */     if (this.hudAmount == 0) {
/* 616 */       return "";
/*     */     }
/* 618 */     return this.hudAmount + "";
/*     */   }
/*     */   
/*     */   public String removeLastChar(String str) {
/* 622 */     if (str != null && str.length() > 0) {
/* 623 */       str = str.substring(0, str.length() - 1);
/*     */     }
/* 625 */     return str;
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 629 */     CLIENT,
/* 630 */     SERVER;
/*     */   }
/*     */ }


/* Location:              C:\Users\KillAura\Desktop\Incoming 1.0.2 libs.jar!\me\alpha432\oyvey\features\modules\misc\AntiPackets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */