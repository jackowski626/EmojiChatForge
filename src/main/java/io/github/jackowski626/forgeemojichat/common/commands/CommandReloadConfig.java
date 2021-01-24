package io.github.jackowski626.forgeemojichat.common.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;


public class CommandReloadConfig extends CommandBase {

    @Override
    public String getCommandName() {
        return "emojichatre";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/emojichatre";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        io.github.jackowski626.forgeemojichat.Listener.emoteArray = io.github.jackowski626.forgeemojichat.Listener.getConfigList("emoji_list.txt");
        io.github.jackowski626.forgeemojichat.Listener.customAliases = io.github.jackowski626.forgeemojichat.Listener.getConfigList("emoji_aliases.txt");
        io.github.jackowski626.forgeemojichat.Listener.genServerEmojis();
        io.github.jackowski626.forgeemojichat.Listener.genAliasString();
        sender.addChatMessage(new ChatComponentText("Reloaded emojichat emoji lists"));
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 3;
    }
}