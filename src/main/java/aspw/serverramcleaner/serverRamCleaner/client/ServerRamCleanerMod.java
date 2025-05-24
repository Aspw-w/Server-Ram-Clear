package aspw.serverramcleaner.serverRamCleaner.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.awt.*;

public class ServerRamCleanerMod {

    public static String modVersion = "1.0.0";
    public static String commandPrefix = ".";

    public static void runCommand(String commandWithPrefix) {
        String commandLowercase = commandWithPrefix.substring(commandPrefix.length()).toLowerCase();

        if (commandLowercase.startsWith("placelog ")) {
            String[] cmdParts = commandLowercase.replace("placelog ", "").split(" ");
            if (cmdParts.length == 2) {
                int amount;
                int delay;

                try {
                    amount = Integer.parseInt(cmdParts[0]);
                    delay = Integer.parseInt(cmdParts[1]);
                } catch (NumberFormatException e) {
                    printChat("Missing command", Color.RED);
                    return;
                }

                CrashFeatures.executePlaceLogPacket(amount, delay);
            }
            return;
        }

        switch (commandLowercase) {
            case "help", "commands":
                printHelp();
                break;

            case "completion":
                logCrash(CrashFeatures.executeCompletionCrash());
                break;

            case "bundle":
                Boolean isCrashedOrNull = CrashFeatures.executeBundleCrash();
                if (isCrashedOrNull != null)
                    logCrash(isCrashedOrNull);
                break;

            case "multiversecore":
                logCrash(CrashFeatures.executeMultiverseCoreCrash());
                break;

            default:
                printChat("Missing command", Color.RED);
                break;
        }
    }

    private static void logCrash(boolean isCrashSuccess) {
        String text = isCrashSuccess ? "Crashing..." : "Failed to send packet!";
        printChat(text, Color.RED);
    }

    public static void printChat(String string, Color color) {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("[Server Ram Cleaner] " + string).withColor(color.getRGB()));
    }

    public static void printHelp() {
        printChat("Commands:", Color.GREEN);
        printChat(" help/commands", Color.GREEN);
        printChat(" completion", Color.GREEN);
        printChat(" bundle", Color.GREEN);
        printChat(" multiversecore", Color.GREEN);
        printChat(" placelog <amount> <delay>", Color.GREEN);

        if (!UpdatesChecker.isLatestClient)
            printChat("[Warning] Your mod is outdated! (" + modVersion + ") please update on https://modrinth.com/project/server-ram-clear", Color.WHITE);
    }
}