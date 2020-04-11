package com.thinkerwolf.frameworkstudy.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.proxy.dwr.Util;

@RemoteProxy
public class ChatManager {

    /**
     * 保存当前在线用户列表
     */
    public static List<User> users = new ArrayList<User>();

    /**
     * 更新在线用户列表
     *
     * @param username 待添加到列表的用户名
     * @param flag     是添加用户到列表,还是只获得当前列表
     * @param request
     * @return 用户userid
     */
    @RemoteMethod
    public String updateUsersList(String username, boolean flag, HttpServletRequest request) {
        User user = null;
        if (flag) {
            // 这里取会话(HttpSession)的id为用户id
            user = new User(request.getSession().getId(), username);
            //保存用户到列表
            users.add(user);
            //将用户id和页面脚本session绑定
            this.setScriptSessionFlag(user.getUserid());
        }
        //获得DWR上下文
        ServletContext sc = request.getSession().getServletContext();
        ServerContext sctx = ServerContextFactory.get(sc);
        //获得当前浏览 index.jsp 页面的所有脚本session
        Collection sessions = sctx.getScriptSessionsByPage("/dwr.html");
        Util util = new Util(sessions);
        //处理这些页面中的一些元素
        util.removeAllOptions("users");
        util.addOptions("users", users, "username");
        util.removeAllOptions("receiver");
        util.addOptions("receiver", users, "userid", "username");
        if (!flag) {
            return null;
        }
        return user.getUserid();
    }

    /**
     * 将用户id和页面脚本session绑定
     *
     * @param userid
     */
    @RemoteMethod
    public void setScriptSessionFlag(String userid) {
        WebContextFactory.get().getScriptSession().setAttribute("userid", userid);
    }

    /**
     * 根据用户id获得指定用户的页面脚本session
     *
     * @param userid
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RemoteMethod
    public ScriptSession getScriptSession(String userid, HttpServletRequest request) {
        ScriptSession scriptSessions = null;
        Collection<ScriptSession> sessions = new HashSet<ScriptSession>();
        sessions.addAll(ServerContextFactory.get(request.getSession().getServletContext())
                .getScriptSessionsByPage("/dwr.html"));
        for (ScriptSession session : sessions) {
            String xuserid = (String) session.getAttribute("userid");
            if (xuserid != null && xuserid.equals(userid)) {
                scriptSessions = session;
            }
        }
        return scriptSessions;
    }

    /**
     * 发送消息
     *
     * @param sender     发送者
     * @param receiverid 接收者id
     * @param msg        消息内容
     * @param request
     */
    @RemoteMethod
    public void send(String sender, String receiverid, String msg, HttpServletRequest request) {
        ScriptSession session = this.getScriptSession(receiverid, request);
        Util util = new Util(session);
        util.setStyle("showMessage", "display", "");
        util.setValue("sender", sender);
        util.setValue("msg", msg);
    }
}
