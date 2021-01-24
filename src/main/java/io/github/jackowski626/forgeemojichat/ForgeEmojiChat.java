package io.github.jackowski626.forgeemojichat;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import io.github.jackowski626.forgeemojichat.common.commands.CommandReloadConfig;
import io.github.jackowski626.forgeemojichat.common.commands.CommandAliases;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

import static io.github.jackowski626.forgeemojichat.Reference.*;

@Mod(modid = MODID, name = Reference.NAME, version = VERSION)
public class ForgeEmojiChat
{
    private Listener listener = new Listener();

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(listener);
        FMLCommonHandler.instance().bus().register(listener);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandReloadConfig());
        event.registerServerCommand(new CommandAliases());
    }
}
