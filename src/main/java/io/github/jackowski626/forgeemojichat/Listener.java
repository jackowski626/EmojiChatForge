package io.github.jackowski626.forgeemojichat;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.command.ServerCommand;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.ServerChatEvent;
import org.lwjgl.Sys;

//import java.lang.reflect.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class Listener {
    BufferedReader reader;
    public static String[] emoteArray;
    public static String[] customAliases;
    public static String[] serverEmojis;
    public static String aliasString;

    /*private static Field myReflexionPublicField;
    private static Field myReflexionPrivateField;*/

    public Listener () {
        emoteArray = getConfigList("emoji_list.txt");
        customAliases = getConfigList("emoji_aliases.txt");
        genServerEmojis();
        genAliasString();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onMessage (ServerChatEvent event) {
        //System.out.println(event.message);
        //System.out.println(event.component.getUnformattedText());

        //ChatStyle test = event.component.getChatStyle().createDeepCopy();

        /*try {
            System.out.println("1: " + event.message);
            myReflexionPrivateField = ServerChatEvent.class.getDeclaredField("message");
            myReflexionPrivateField.setAccessible(true);
            String newMsg = "test";
            myReflexionPrivateField.set(event, newMsg);
            System.out.println("2: " + event.message);
        } catch (Exception e) {
            System.out.println(e);
        };*/


        event.component = new ChatComponentTranslation(replaceEmojis(event.component.getUnformattedText()));

    }

    public static void genServerEmojis() {
        serverEmojis = new String[customAliases.length];
        for (int i = emoteArray.length - customAliases.length, j = 0; i < emoteArray.length; i ++, j ++) {
            serverEmojis[j] = emoteArray[i];
        }
    }

    public static void genAliasString() {
        aliasString = "";
        for (int i = 0; i < serverEmojis.length; i ++) {
            if (i != 0) {
                aliasString += "/";
            }
            aliasString += serverEmojis[i] + ":" + customAliases[i];
        }
    }

    public static String[] getConfigList(String type) {
        File emojiChatConfigDir = new File("." + File.separator + "config" + File.separator + "EmojiChat");
        if (!emojiChatConfigDir.exists()) {
            emojiChatConfigDir.mkdirs();
        }
        File emoteList = new File("." + File.separator + "config" + File.separator + "EmojiChat" + File.separator + type);
        try {
            List<String> temp = new ArrayList<String>();
            if (!emoteList.exists()) {
                emoteList.createNewFile();
            }
            InputStream is = new FileInputStream(emoteList);//getClass().getClassLoader().getResourceAsStream("emoji_list.txt");

            //System.out.println(is.toString());
            InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#")) {
                    temp.add(line);
                }
            }

            String res[] = new String[temp.size()];
            for (int i = 0; i < temp.size(); i++) {
                res[i] = temp.get(i);
            }
            return res;
        } catch (IOException e) {

        }
        return null;
    }

    private Serializable getEmoteCode (String emote) {
        //System.out.println(emote);
        char emojiChar = '가';
        for (int i = 0; i < emoteArray.length; i ++) {
            if (emote.equals(emoteArray[i])) {
                return emojiChar;
            }
            emojiChar ++;
        }
        return null;
    }

    private Serializable getAliasCode (String emote) {
        char emojiChar = '가';
        for (int i = 0; i < emoteArray.length - customAliases.length; i ++) {
            emojiChar ++;
        }
        for (int i = 0; i < customAliases.length; i ++) {
            if (emote.equals(customAliases[i])) {
                return emojiChar;
            }
            emojiChar ++;
        }
        return null;
    }

    private String replaceEmojis (String s) {
        for (String e : emoteArray) {
            s = s.replace(":" + e + ":", String.valueOf(getEmoteCode(e)));
        }
        for (String e : customAliases) {
            s = s.replace(":" + e + ":", String.valueOf(getAliasCode(e)));
        }
        //System.out.println(s);
        return s;
    }
}