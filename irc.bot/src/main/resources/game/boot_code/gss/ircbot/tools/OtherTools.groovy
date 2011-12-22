package gss.ircbot.tools

import gss.ircbot.models.BotProfile
import gss.ircbot.models.IrcServer
import gss.ircbot.models.User
import gss.ircbot.models.UserAuthentication
import gss.ircbot.socket.connectors.IrcPlainServerConnection
import gss.run.Booter
import org.hibernate.Session
import org.hibernate.Transaction
import org.hibernate.criterion.Restrictions

class OtherTools {
    static BotProfile getBotProfile(Booter booter, IrcPlainServerConnection serverConnection) {
        String profile = serverConnection.getOtherSettings().get("profile", "");
        String bot = serverConnection.getOtherSettings().get("bot", "");
        int botId = (Integer) serverConnection.getOtherSettings().get("bot_id", 0);
        if (bot != "" && botId >= 0) {
            Session session = booter.getSession();
            session.beginTransaction();
            List list = session.createCriteria(BotProfile.class).add(Restrictions.eq("nick", bot)).add(Restrictions.eq("id", botId)).list();
            return (BotProfile) list.get(0);
        } else if (profile != "") {
            Session session = booter.getSession();
            session.beginTransaction();
            List list = session.createCriteria(IrcServer).add(Restrictions.eq("name", profile)).list();
            return ((IrcServer) list.get(0)).getDefaultProfile();
        }
        return null;
    }

    static IrcServer getIrcServer(Booter booter, IrcPlainServerConnection serverConnection) {
        String profile = serverConnection.getOtherSettings().get("profile", "");
        if (profile != "") {
            Session session = booter.getSession();
            session.beginTransaction();
            List list = session.createCriteria(IrcServer).add(Restrictions.eq("name", profile)).list();
            return (IrcServer) list.get(0);
        }
        return null;
    }

    static User getUser(Booter booter, IrcServer ircServer, String nick) {
        Session dbSession = booter.getSession();
        if (dbSession != null) {
            Transaction transaction = dbSession.beginTransaction();
            List<UserAuthentication> authentications = dbSession.createCriteria(UserAuthentication.class).add(Restrictions.eq("server", ircServer)).add(Restrictions.eq("nickname", nick)).list();
            if (authentications.size() > 0) {
                return authentications.get(0).getUser();
            }
        }
        return null;
    }
}
