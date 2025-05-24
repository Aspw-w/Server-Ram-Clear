package aspw.serverramcleaner.serverRamCleaner.mixins;

import aspw.serverramcleaner.serverRamCleaner.client.ServerRamCleanerMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {

    @Inject(method = "sendMessage", at = @At("HEAD"), cancellable = true)
    private void chatCommandHook(String message, boolean addToHistory, CallbackInfo ci) {
        if (message.startsWith(ServerRamCleanerMod.commandPrefix)) {
            ci.cancel();
            ServerRamCleanerMod.runCommand(message);
            MinecraftClient.getInstance().inGameHud.getChatHud().addToMessageHistory(message);
        }
    }
}