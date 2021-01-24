package io.github.jackowski626.forgeemojichat.common.commands;

import io.github.jackowski626.forgeemojichat.Listener;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class CommandAliases extends CommandBase {
    @Override
    public String getCommandName() {
        return "alias";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/alias <emoji> | all";
    }

    @Override
    public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
        if (p_71515_2_.length != 1) {
            p_71515_1_.addChatMessage(new ChatComponentText("Invalid number of arguments").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }
        if (p_71515_2_[0].equals("all")) {
            //p_71515_1_.addChatMessage(new ChatComponentText(Listener.aliasString));
            for (int i = 0; i < Listener.serverEmojis.length; i ++) {
                p_71515_1_.addChatMessage(new ChatComponentText(Listener.serverEmojis[i] + ": " + Listener.customAliases[i]));
            }
        } else {
            for (int i = 0; i < Listener.serverEmojis.length; i ++) {
                if (Listener.serverEmojis[i].equals(p_71515_2_[0]) || Listener.serverEmojis[i].equals(p_71515_2_[0].substring(1, p_71515_2_[0].length() - 1))) {
                    p_71515_1_.addChatMessage(new ChatComponentText("Alias for :" + Listener.serverEmojis[i] + ": is :" + Listener.customAliases[i] + ":"));
                    return;
                }
            }
            p_71515_1_.addChatMessage(new ChatComponentText("Emoji not found in server emojis (Standard emojis have no aliases)").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
        }
    }
}
