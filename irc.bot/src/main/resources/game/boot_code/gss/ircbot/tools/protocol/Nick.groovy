package gss.ircbot.tools.protocol

class Nick {
    static String getUserName(String text) {
        String ret = text.substring(1);
        ret = ret.substring(0, ret.indexOf("!"));
        return ret;
    }

    static String getIdent(String text) {
        String ret = text.substring(text.indexOf("!") + 1);
        ret = ret.substring(0, ret.indexOf("@"));
        return ret;
    }

    static String getHost(String text) {
        String ret = text.substring(text.indexOf("@") + 1);
        ret = ret.substring(0, ret.indexOf(" "));
        return ret;
    }

    static String getChangedTo(String text) {
        String ret = text.substring(text.indexOf(" :") + 2);
        return ret;
    }

    static String recreateOriginal(String user, String ident, String host, String nickTo) {
        return "${user}!${ident}@${host} NICK :${nickTo}";
    }

    static String createSendNick(String nickTo) {
        return "NICK ${nickTo}";
    }

    static Boolean isNick(String message) {
        if (message.contains(" ") && message.contains("NICK")) {
            String ret = message.substring(message.indexOf(" ") + 1);
            ret = ret.substring(0, ret.indexOf(" "));
            return ret == "NICK";
        }
        return false;
    }
}
