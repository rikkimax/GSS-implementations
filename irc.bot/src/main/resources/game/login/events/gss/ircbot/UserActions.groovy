package gss.ircbot

import gss.eventing.Event
import gss.bot.models.BotProfile
import gss.bot.models.Server
import gss.ircbot.models.queues.IrcMessageQueue
import gss.ircbot.models.queues.UserActionQueue
import gss.ircbot.socket.connectors.IrcPlainServerConnection
import gss.ircbot.tools.OtherTools
import gss.login.socket.connectors.PlainServerConnectionMessage
import gss.run.Booter
import org.apache.mina.core.session.IoSession
import org.hibernate.Session
import org.hibernate.Transaction
import gss.ircbot.tools.protocol.*

class UserActions extends Event {
    UserActions() {
        super();
    }

    @Override
    void create(String key, Booter booter) {
        super.create(key, booter);
        assimilate("received");
    }

    @Override
    void run(String s, Object o, Object... objects) {
        if (s == "received") {
            IoSession session = (IoSession) objects[0];
            if (objects[1] instanceof String) {
                String message = objects[1];
                IrcPlainServerConnection server = (IrcPlainServerConnection) ((PlainServerConnectionMessage) o).getServer();
                if (Part.isPart(message)) {
                    Session dbSession = booter.getSession();
                    if (dbSession != null) {
                        Transaction transaction = dbSession.beginTransaction();
                        Server ircServer = getIrcServer(server);
                        UserActionQueue messageSave = new UserActionQueue(OtherTools.getUser(booter, ircServer, Nick?.getUserName(message)), ircServer, UserActionQueue.Action.PART, message);
                        dbSession.save(messageSave);
                        transaction.commit();
                    }
                }
                if (Join.isJoin(message)) {
                    Session dbSession = booter.getSession();
                    if (dbSession != null) {
                        Transaction transaction = dbSession.beginTransaction();
                        Server ircServer = getIrcServer(server);
                        UserActionQueue messageSave = new UserActionQueue(OtherTools.getUser(booter, ircServer, Nick?.getUserName(message)), ircServer, UserActionQueue.Action.JOIN, message);
                        dbSession.save(messageSave);
                        transaction.commit();
                    }
                }
                if (Quit.isQuit(message)) {
                    Session dbSession = booter.getSession();
                    if (dbSession != null) {
                        Transaction transaction = dbSession.beginTransaction();
                        Server ircServer = getIrcServer(server);
                        UserActionQueue messageSave = new UserActionQueue(OtherTools.getUser(booter, ircServer, Nick?.getUserName(message)), ircServer, UserActionQueue.Action.QUIT, message);
                        dbSession.save(messageSave);
                        transaction.commit();
                    }
                }
                if (Nick.isNick(message)) {
                    // User changed nick..
                    Session dbSession = booter.getSession();
                    if (dbSession != null) {
                        Transaction transaction = dbSession.beginTransaction();
                        Server ircServer = getIrcServer(server);
                        UserActionQueue messageSave = new UserActionQueue(OtherTools.getUser(booter, ircServer, Nick?.getUserName(message)), ircServer, UserActionQueue.Action.NICK, message);
                        dbSession.save(messageSave);
                        transaction.commit();
                    }
                }
                if (Privmsg.isPrivmsg(message)) {
                    Session dbSession = booter.getSession();
                    if (dbSession != null) {
                        Transaction transaction = dbSession.beginTransaction();
                        IrcMessageQueue messageSave = new IrcMessageQueue(Privmsg.getTo(message), Privmsg.getUserName(message), Privmsg.getMessage(message));
                        messageSave.setFromHost(Privmsg.getHost(message));
                        messageSave.setFromIdent(Privmsg.getIdent(message));
                        dbSession.save(messageSave);
                        transaction.commit();
                    }
                }
                if (Notice.isNotice(message)) {
                    Session dbSession = booter.getSession();
                    if (dbSession != null) {
                        Transaction transaction = dbSession.beginTransaction();
                        IrcMessageQueue messageSave = new IrcMessageQueue(Privmsg.getTo(message), Privmsg.getUserName(message), Privmsg.getMessage(message));
                        messageSave.setFromHost(Privmsg.getHost(message));
                        messageSave.setFromIdent(Privmsg.getIdent(message));
                        messageSave.setIsNotice(true);
                        dbSession.save(messageSave);
                        transaction.commit();
                    }
                }
            }
        }
    }

    @Override
    void destroy(String s) {

    }

    private BotProfile getBotProfile(IrcPlainServerConnection serverConnection) {
        return OtherTools.getBotProfile(booter, serverConnection);
    }

    private Server getIrcServer(IrcPlainServerConnection serverConnection) {
        return OtherTools.getIrcServer(booter, serverConnection);
    }
}
