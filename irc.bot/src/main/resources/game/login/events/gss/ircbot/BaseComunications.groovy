package gss.ircbot

import gss.eventing.Event
import gss.ircbot.models.BotProfile
import gss.ircbot.models.IrcServer
import gss.ircbot.socket.connectors.IrcPlainServerConnection
import gss.ircbot.tools.OtherTools
import gss.ircbot.tools.protocol.Join
import gss.ircbot.tools.protocol.Nick
import gss.ircbot.tools.protocol.Other
import gss.ircbot.tools.protocol.Privmsg
import gss.login.socket.ServerConnection
import gss.login.socket.ServerConnectionMessage
import gss.login.socket.connectors.PlainServerConnection
import gss.login.socket.connectors.PlainServerConnectionMessage
import gss.run.Booter
import gss.run.LoginNode
import org.apache.mina.core.session.IoSession
import org.hibernate.Session
import org.hibernate.Transaction

class BaseComunications extends Event {
    BaseComunications() {
        super();
    }

    @Override
    void create(String key, Booter booter) {
        super.create(key, booter);
        this.booter = booter;
        checkProfilesExists();
        assimilate("created");
        assimilate("destroyed");
        assimilate("received");
        if (booter instanceof LoginNode) {
            LoginNode loginNode = (LoginNode) booter;
            loginNode?.getServersConnections()?.getSockets()?.each {
                if (it instanceof PlainServerConnection)
                    if (it.session == null) {
                        it.setValues();
                        it.start();
                    }
            }
        }
    }

    @Override
    void run(String s, Object o, Object... objects) {
        println(s + "," + o + "," + objects);
        if (o instanceof ServerConnection || o instanceof ServerConnectionMessage)
            if (s == "created") {
                IrcPlainServerConnection server = o;
                IoSession session = (IoSession) objects[0];
                // tell the server who we are.
                BotProfile botProfile = getBotProfile(server);
                server.sendMessage(Other.createUser(botProfile.nick, botProfile.mode, botProfile.name));
                server.sendMessage(Nick.createSendNick(botProfile.nick));
            } else if (s == "destroyed") {
                // reconnect in 1 second.
                sleep(1000);
                PlainServerConnection server = o;
                server.start();
            } else if (s == "received") {
                IoSession session = (IoSession) objects[0];
                PlainServerConnectionMessage serverMessage = o;
                if (objects[1] instanceof String) {
                    String message = objects[1];
                    if (message?.contains("PING"))
                        serverMessage.sendMessage("PONG " + message.split(" ")[1]);
                    if (message?.contains("376")) {
                        String[] parts = message.split(" ");
                        if (parts.size() > 1)
                            if (parts[1] == "376") {
                                BotProfile botProfile = getBotProfile((IrcPlainServerConnection) serverMessage.getServer());
                                if (botProfile.nickServPass != null)
                                    if (botProfile.nickServPass != "")
                                        serverMessage.sendMessage(Privmsg.createSendTo("NICKSERV", "identify ${botProfile.nickServPass}"));
                                if (botProfile.operUser != null && botProfile.operPass != null)
                                    if (botProfile.operUser != "" && botProfile.operPass != "")
                                        serverMessage.sendMessage(Other.createOper(botProfile.operUser, botProfile.operPass));
                                serverMessage.sendMessage(Join.createJoinChannel("#fbcbot"));
                            }
                    }
                }
            }
    }

    @Override
    void destroy(String s) {
    }

    private void checkProfilesExists() {
        Session session = booter.getSession();
        if (session != null) {
            Transaction transaction = session.beginTransaction();
            if (transaction != null) {
                List list = session.createCriteria(BotProfile.class).list();
                if (list.size() == 0) {
                    BotProfile profile = new BotProfile("bot", 0, "gss irc bot", 0, null, "rikki", "password");
                    IrcServer server = new IrcServer("localhost_test_server", "localhost", 6667, false, "gss.ircbot.socket.connectors.IrcPlainServerConnectorHandler", profile);
                    session.save(profile);
                    session.save(server);
                    transaction.commit();
                }
            }
        }
    }

    private BotProfile getBotProfile(IrcPlainServerConnection serverConnection) {
        return OtherTools.getBotProfile(booter, serverConnection);
    }
}
