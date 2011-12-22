package gss.ircbot.tools.protocol

class Other {
    static String createUser(String nick, Integer mode, String name) {
        return createUser(nick, mode + "", name);
    }

    static String createUser(String nick, String mode, String name) {
        return "USER ${nick} ${mode} * :${name}";
    }

    static String createOper(String user, String pass) {
        return "OPER ${user} ${pass}";
    }
}
