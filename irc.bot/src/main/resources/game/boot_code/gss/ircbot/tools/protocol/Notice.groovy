package gss.ircbot.tools.protocol

class Notice {
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

    static String getTo(String text) {
        String ret = text.substring(text.indexOf("NOTICE ") + 7);
        ret = ret.substring(0, ret.indexOf(" "));
        return ret;
    }

    static String getMessage(String text) {
        String ret = text.substring(text.indexOf(" :") + 2);
        return ret;
    }

    static String recreateOriginal(String user, String ident, String host, String to, String message) {
        return "${user}!${ident}@${host} NOTICE ${to} :${message}";
    }

    static String createSendTo(String to, String message) {
        return "NOTICE ${to} ${message}";
    }

    static Boolean isNotice(String message) {
        if (message.contains(" ") && message.contains("NOTICE")) {
            String ret = message.substring(message.indexOf(" ") + 1);
            ret = ret.substring(0, ret.indexOf(" "));
            return ret == "NOTICE";
        }
        return false;
    }
}
