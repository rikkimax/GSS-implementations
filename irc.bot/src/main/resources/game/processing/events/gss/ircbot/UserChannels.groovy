package gss.ircbot

import gss.eventing.Event
import gss.bot.models.Channel
import gss.bot.models.UserAuthentication
import gss.ircbot.models.queues.UserActionQueue
import gss.ircbot.tools.protocol.Join
import gss.ircbot.tools.protocol.Nick
import gss.queueing.QueueManager
import gss.run.Booter
import org.hibernate.Session
import org.hibernate.Transaction
import org.hibernate.criterion.Restrictions
import gss.ircbot.models.UserAutoAuthentication

class UserChannels extends Event {
    private String classToUse = UserActionQueue.getClass().getCanonicalName();

    @Override
    void create(String key, Booter booter) {
        super.create(key, booter);
        this.booter = booter;
        assimilate(UserActionQueue.getClass());
    }

    @Override
    void run(String s, Object o, Object... objects) {
        if (s == classToUse && objects.size() > 0) {
            QueueManager<UserActionQueue> queue = (QueueManager<UserActionQueue>) o;
            UserActionQueue userAction = (UserActionQueue) objects[0];
            Session session = booter.getSession();
            Transaction transaction = session?.beginTransaction();
            if (transaction != null && userAction.getUser() != null && userAction.getIrcServer() != null)
                switch (userAction.action) {
                    case (UserActionQueue.Action.JOIN):
                        // The user joined a channel, add the channel..
                        List<Channel> channels = session.createCriteria(Channel.getClass())
                                .add(Restrictions.eq("chan", Join.getTo(userAction.getRaw())))
                                .add(Restrictions.eq("server", userAction.getIrcServer()))
                                .setMaxResults(1).list();
                        if (channels.size() == 1) {
                            // We have a channel stored that they have joined yippieee!
                            userAction.getUser().addChannel(channels.get(0));
                            session.saveOrUpdate(userAction.getUser());
                        } else {
                            // Oh s**t how do we not have a channel stored on this..
                            // Something wrong! 3 2 1 sell the shares to the data center just blew!
                        }
                        break;
                    case (UserActionQueue.Action.NICK):
                        // Nick was changed
                        // So lets update there auth token if they have one..
                        List<UserAuthentication> userAuthentications = session.createCriteria(UserAuthentication.getClass())
                                .add(Restrictions.eq("user", userAction.getUser()))
                                .add(Restrictions.eq("server", userAction.getIrcServer()))
                                .add(Restrictions.eq("nickname", Nick.getUserName(userAction.getRaw())))
                                .setMaxResults(1).list();
                        if (userAuthentications.size() == 1) {
                            // We are authenticated!
                            userAuthentications.get(0).setNickName(Nick.getChangedTo(userAction.getRaw()));
                            session.saveOrUpdate(userAuthentications.get(0));
                        }
                        break;
                    case (UserActionQueue.Action.PART):
                        // The user parted a channel, remove them from db.. for chan
                        List<Channel> channels = session.createCriteria(Channel.getClass())
                                .add(Restrictions.eq("chan", Join.getTo(userAction.getRaw())))
                                .add(Restrictions.eq("server", userAction.getIrcServer()))
                                .setMaxResults(1).list();
                        if (channels.size() == 1) {
                            // We have a channel stored that they have joined yippieee!
                            userAction.getUser().removeChannel(channels.get(0));
                            session.saveOrUpdate(userAction.getUser());
                        } else {
                            // Oh s**t how do we not have a channel stored on this..
                            // Something wrong! 3 2 1 sell the shares to the data center just blew!
                        }
                        // Now check if we still have any channels
                        // If we don't.. remove their auth token
                        int amountOn = 0;
                        userAction.getUser().getChannels().each {
                            if (it.getServer() == userAction.getIrcServer()) {
                                amountOn += 1;
                            }
                        }
                        if (amountOn == 0) {
                            // Oh dare.. they are not on any channels!
                            // Delete there auth token..
                        } else {
                            // They are still on a channel we are on so stop!
                            break;
                        }
                    case (UserActionQueue.Action.QUIT):
                        // The user quit so remove their auth token if they have one
                        List<UserAuthentication> userAuthentications = session.createCriteria(UserAuthentication.getClass())
                                .add(Restrictions.eq("user", userAction.getUser()))
                                .add(Restrictions.eq("server", userAction.getIrcServer()))
                                .add(Restrictions.eq("nickname", Nick.getUserName(userAction.getRaw())))
                                .setMaxResults(1).list();
                        if (userAuthentications.size() == 1) {
                            // We are authenticated!
                            session.delete(userAuthentications.get(0));
                        }
                        break;
                    default:
                        // unknown.. Action value.. odd
                        break;
                }
            else if (userAction.getAction() == UserActionQueue.Action.JOIN) {
                // Ok so we are definitely not authenticated..
                // But is it possible a user will allow us to auto authenticate them?
                // Based on there nick, ident and host?
                UserAutoAuthentication toAuth = null;
                List<UserAutoAuthentication> autoAuth = session.createCriteria(UserAutoAuthentication.getClass())
                        .add(Restrictions.eq("server", userAction.getIrcServer())).list();
                autoAuth.each {
                    if (it.toAuthenticate(Join.getUserName(userAction.getRaw()),
                            Join.getIdent(userAction.getRaw()),
                            Join.getHost(userAction.getRaw())))
                        toAuth = it;
                }
                if (toAuth != null) {
                    UserAuthentication auth = new UserAuthentication(toAuth.getUser(), userAction.getIrcServer(), Join.getUserName(userAction.getRaw()));
                    toAuth.getUser().addAuthentication(auth);
                    session.saveOrUpdate(auth);
                    session.saveOrUpdate(toAuth.getUser());
                }
            }
            transaction?.commit();
        }
    }

    @Override
    void destroy(String s) {
    }
}
