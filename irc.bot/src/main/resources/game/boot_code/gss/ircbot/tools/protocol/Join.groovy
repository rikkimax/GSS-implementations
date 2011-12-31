package gss.ircbot.tools.protocol

class Join {
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
        String ret = text.substring(text.indexOf(" :") + 2);
        return ret;
    }

    static String createJoinChannel(String channel, String key) {
        return "JOIN ${channel} ${key}";
    }

    static String createJoinChannel(String channel) {
        if (!channel.startsWith("#"))
            channel = "#" + channel;
        return "JOIN ${channel}";
    }

    static String createJoinChannel(String[] channels) {
        String channel = "";
        channels.each {
            if (it.startsWith("#"))
                channel += ",${it}";
            else
                channel += ",#${it}";
        }
        if (channel != "")
            channel = channel.substring(1);
        return createJoinChannel(channel);
    }

    static String createJoinChannel(String[] channels, String[] keys) {
        String channel = "";
        channels.each {
            if (it.startsWith("#"))
                channel += ",${it}";
            else
                channel += ",#${it}";
        }
        if (channel != "")
            channel = channel.substring(1);
        String key = "";
        keys.each {
            key += ",${it}";
        }
        if (key != "")
            key = key.substring(1);
        return createJoinChannel(channel, key);
    }

    static String createPartAll() {
        return "JOIN 0";
    }

    static Boolean isJoin(String message) {
        if (message.contains(" ") && message.contains("JOIN")) {
            String ret = message.substring(message.indexOf(" ") + 1);
            ret = ret.substring(0, ret.indexOf(" "));
            return ret == "JOIN";
        }
        return false;
    }
}
