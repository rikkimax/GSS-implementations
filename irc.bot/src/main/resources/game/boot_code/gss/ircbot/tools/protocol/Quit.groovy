package gss.ircbot.tools.protocol

class Quit {
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

    static String getMessage(String text) {
        String ret = text.substring(text.indexOf(" :") + 2);
        return ret;
    }

    static String recreateOriginal(String user, String ident, String host, String message) {
        if (message == null) message = "";
        return "${user}!${ident}@${host} QUIT :${message}";
    }

    static String createSendTo(String message) {
        if (message == null) message = "";
        return "QUIT :${message}";
    }

    static Boolean isQuit(String message) {
        if (message.contains(" ") && message.contains("QUIT")) {
            String ret = message.substring(message.indexOf(" ") + 1);
            ret = ret.substring(0, ret.indexOf(" "));
            return ret == "QUIT";
        }
        return false;
    }
}
