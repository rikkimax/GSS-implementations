package gss.ircbot.tools.protocol

class Action {
    static String createSendTo(String to, String message) {
        return Privmsg.createSendTo(to, "\u0001ACTION${message}\u0001");
    }

    static Boolean isAction(String message) {
        if (!Privmsg.isPrivmsg(message))
            return false;
        String ret = Privmsg.getMessage(message);
        return ret.toUpperCase().startsWith("\u0001ACTION") && ret.endsWith("\u0001");
    }
}
