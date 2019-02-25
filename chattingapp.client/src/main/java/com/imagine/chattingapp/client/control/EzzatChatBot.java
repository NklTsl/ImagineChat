
package com.imagine.chattingapp.client.control;

import java.io.File;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;


public class EzzatChatBot {
    private static final boolean TRACE_MODE = false;
    static String botName = "Ezzat";
    String resourcesPath;
    Bot bot ;
    Chat chatSession;
    public EzzatChatBot(){
    
        resourcesPath = getResourcesPath();
        MagicBooleans.trace_mode = TRACE_MODE;
        bot = new Bot("super", resourcesPath);
        chatSession = new Chat(bot);
         bot.brain.nodeStats();
    }
    public  String processUserInput(String input){
    
                String response=null;
                if ((input == null) || (input.length() < 1))
                    input = MagicStrings.null_input;
                if (input.equals("q")) {
                    System.exit(0);
                } else if (input.equals("wq")) {
                    bot.writeQuit();
                    System.exit(0);
                } else {
                    String request = input;
                    if (MagicBooleans.trace_mode)
                        System.out.println("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
                     response= chatSession.multisentenceRespond(request);
                    if (response.contains("&lt;") && !response.isEmpty())
                        response = response.replace("&lt;", "<");
                    if (response.contains("&gt;"))
                        response = response.replace("&gt;", ">");
                    
                }
            
        return response;
    }
 
    
 
    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }
}
