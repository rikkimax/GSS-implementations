package gss.ircbot.tools.protocol

class Ctcp {
    static String createSendTo(String to, String message) {
        return Privmsg.createSendTo(to, "\u0001${message}\u0001");
    }

    static String createReplyTo(String to, String message) {
        return Notice.createSendTo(to, "\u0001${message}\u0001");
    }

    static Boolean isCtcpSend(String message) {
        if (!Privmsg.isPrivmsg(message))
            return false;
        String ret = Privmsg.getMessage(message);
        return ret.startsWith("\u0001") && ret.endsWith("\u0001");
    }

    static Boolean isCtcpReply(String message) {
        if (!Notice.isNotice(message))
            return false;
        String ret = Notice.getMessage(message);
        return ret.startsWith("\u0001") && ret.endsWith("\u0001");
    }
}
