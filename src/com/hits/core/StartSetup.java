package com.hits.core;


import com.hits.common.config.Globals;
import com.hits.common.config.XMLConfigFactory;
import com.hits.common.util.DateUtil;
import com.hits.common.util.DecodeUtil;
import com.hits.modules.sys.bean.*;
import org.apache.velocity.app.Velocity;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.filepool.NutFilePool;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * 类描述： 创建人：Wizzer 联系方式：www.wizzer.cn 创建时间：2013-11-26 下午2:11:13
 */

public class StartSetup implements Setup {
    private final Log log = Logs.get();

    @Override
    public void destroy(NutConfig config) {

    }

    @Override
    public void init(NutConfig config) {
        try {
            Dao dao = Mvcs.getIoc().get(Dao.class);
            initDB(dao);
            velocityInit(config.getAppRoot());
            Globals.APP_BASE_PATH = Strings.sNull(config.getAppRoot());//项目路径
            Globals.APP_BASE_NAME = Strings.sNull(config.getServletContext().getContextPath());//部署名
            Globals.APP_NAME = XMLConfigFactory.GetName("description");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
    }

    private void velocityInit(String appPath) throws IOException {
        log.info("Veloctiy引擎初始化...");
        Properties p = new Properties();
        p.setProperty("resource.loader", "file,classloader");
        p.setProperty("file.resource.loader.path", appPath);
        p.setProperty("file", "org.apache.velocity.tools.view.WebappResourceLoader");
        p.setProperty("classloader.resource.loader.class", "com.hits.common.view.VelocityResourceLoader");
        p.setProperty("classloader.resource.loader.path", appPath);
        p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        p.setProperty("velocimacro.library.autoreload", "false");
        p.setProperty("classloader.resource.loader.root", appPath);
        p.setProperty("velocimarco.library.autoreload", "true");
        p.setProperty("runtime.log.error.stacktrace", "false");
        p.setProperty("runtime.log.warn.stacktrace", "false");
        p.setProperty("runtime.log.info.stacktrace", "false");
        p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
        p.setProperty("runtime.log.logsystem.log4j.category", "velocity_log");
        Velocity.init(p);
        log.info("Veloctiy引擎初始化完成...");
    }

    public void initDB(Dao dao) {
        //dao.drop(Sys_user.class);
        if (!dao.exists(Sys_user.class)) {
            log.info("数据库始化...");
            Daos.createTablesInPackage(dao, "com.hits.modules", true);
            //初始化单位
            Sys_unit unit = new Sys_unit();
            unit.setId("0001");
            unit.setName("系统管理");
            unit.setLocation(0);
            unit.setUnitcode("01");
            unit.setDescript("合肥信息技术服务有限公司");
            unit.setAddress("合肥蜀山区肥西路66号");
            unit.setTelephone("65334801");
            unit.setWebsite("http://www.hfits.com.cn/");
            dao.insert(unit);
            //初始化用户
            Sys_user user = new Sys_user();
            user.setUserid(10000);
            user.setLoginname("superadmin");
            user.setRealname("超级管理员");
            String hashedPasswordBase64 = DecodeUtil.Encrypt("11");
            user.setPassword(hashedPasswordBase64);
            user.setState(0);
            user.setMobile("13695696244");
            user.setEmail("numbgui@163.com");
            user.setLocation(0);
            user.setLogintype(0);
            user.setLogincount(0);
            user.setLogintime(DateUtil.getCurDateTime());
            user.setUnitid(unit.getId());
            dao.insert(user);
            //初始化角色表
            Sys_role role = new Sys_role();
            role.setId(1);
            role.setName("公共角色");
            role.setDescript("分配用户公共资源");
            dao.insert(role);
            role.setId(2);
            role.setName("系统管理");
            role.setDescript("分配系统管理员管理资源");
            dao.insert(role);
            //初始化菜单
            Sys_resource resource = new Sys_resource();
            resource.setId("0001");
            resource.setName("系统配置");
            resource.setUrl("");
            resource.setLocation(0);
            resource.setSubtype(0);
            dao.insert(resource);
            resource.setId("00010001");
            resource.setName("系统管理");
            resource.setUrl("");
            resource.setLocation(1);
            resource.setSubtype(0);
            dao.insert(resource);
            resource.setId("000100010001");
            resource.setName("机构管理");
            resource.setUrl("/private/sys/unit");
            resource.setLocation(2);
            resource.setButton("增加/BtnAdd;删除/BtnDel;修改/BtnUpdate;排序/BtnSort;");
            resource.setSubtype(0);
            dao.insert(resource);
            resource.setId("000100010002");
            resource.setName("用户管理");
            resource.setUrl("/private/sys/user");
            resource.setLocation(3);
            resource.setButton("增加/BtnAdd;修改/BtnUpdate;删除/BtnDel;禁用/BtnLocked;启用/BtnUnlocked;");
            resource.setSubtype(0);
            dao.insert(resource);
            resource.setId("000100010003");
            resource.setName("角色管理");
            resource.setUrl("/private/sys/role");
            resource.setLocation(4);
            resource.setButton("增加/BtnAdd;删除/BtnDel;修改/BtnUpdate;添加用户到角色/BtnAddRole;从角色中删除用户/BtnDelRole;分配权限/BtnMenu;");
            resource.setSubtype(0);
            dao.insert(resource);
            resource.setId("000100010004");
            resource.setName("资源管理");
            resource.setUrl("/private/sys/res");
            resource.setLocation(5);
            resource.setButton("增加/BtnAdd;删除/BtnDel;修改/BtnUpdate;排序/BtnSort;");
            resource.setSubtype(0);
            dao.insert(resource);
            resource.setId("000100010005");
            resource.setName("安全管理");
            resource.setUrl("");
            resource.setButton("");
            resource.setLocation(6);
            resource.setSubtype(0);
            dao.insert(resource);
            resource.setId("0001000100050001");
            resource.setName("访问控制");
            resource.setUrl("/private/sys/safe");
            resource.setLocation(7);
            resource.setSubtype(0);
            dao.insert(resource);
            resource.setId("0001000100050002");
            resource.setName("登陆日志");
            resource.setUrl("/private/sys/user/log");
            resource.setLocation(8);
            resource.setSubtype(0);
            dao.insert(resource);
            resource.setId("00010002");
            resource.setName("数据元管理");
            resource.setUrl("");
            resource.setLocation(9);
            resource.setSubtype(0);
            dao.insert(resource);
            resource.setId("000100020001");
            resource.setName("数据元集");
            resource.setUrl("/private/dmjfl/t_dmjfl");
            resource.setLocation(10);
            resource.setButton("增加/BtnAdd;删除/BtnDel;修改/BtnUpdate;");
            resource.setSubtype(0);
            dao.insert(resource);
            resource.setId("000100020002");
            resource.setName("代码集");
            resource.setUrl("/private/dmj/t_daimjb");
            resource.setLocation(11);
            resource.setButton("增加/BtnAdd;删除/BtnDel;修改/BtnUpdate;排序/BtnSort;");
            resource.setSubtype(0);
            dao.insert(resource);
            resource.setId("0002");
            resource.setName("业务系统");
            resource.setUrl("");
            resource.setButton("");
            resource.setSubtype(1);
            resource.setLocation(12);
            dao.insert(resource);
            resource.setId("00020011");
            resource.setName("行政办公");
            resource.setSubtype(1);
            resource.setLocation(13);
            dao.insert(resource);
            resource.setId("000200110001");
            resource.setName("通知公告");
            resource.setSubtype(1);
            resource.setLocation(14);
            dao.insert(resource);
            resource.setId("0002001100010001");
            resource.setName("已发公告");
            resource.setUrl("/private/msg/msgInfo/mymsg_info");
            resource.setLocation(15);
            resource.setButton("增加/BtnAdd;删除/BtnDel;修改/BtnUpdate;排序/BtnSort;");
            resource.setSubtype(1);
            dao.insert(resource);
            resource.setId("0002001100010002");
            resource.setName("所有公告");
            resource.setUrl("/private/msg/msgInfo/msg_info");
            resource.setLocation(16);
            resource.setButton("查看/BtnView;");
            resource.setSubtype(1);
            dao.insert(resource);
            resource.setId("000200110003");
            resource.setName("站内消息");
            resource.setUrl("");
            resource.setLocation(17);
            resource.setButton("");
            resource.setSubtype(1);
            dao.insert(resource);
            resource.setId("0002001100030001");
            resource.setName("已发消息");
            resource.setUrl("/private/msg/msgInfo/myMsgMessage");
            resource.setLocation(18);
            resource.setButton("新增/BtnAdd;编辑/BtnUpdate;撤销/BtnBack;删除/BtnDel;查看/BtnView;");
            resource.setSubtype(1);
            dao.insert(resource);
            resource.setId("0002001100030002");
            resource.setName("所有消息");
            resource.setUrl("/private/msg/msgInfo/msgMessage");
            resource.setLocation(19);
            resource.setButton("查看/BtnView;");
            resource.setSubtype(1);
            dao.insert(resource);
            //初始化角色资源
            Sys_role_resource roleResource = new Sys_role_resource();
            roleResource.setRoleid(2);
            roleResource.setResourceid("0001");
            roleResource.setButton("");
            dao.insert(roleResource);
            roleResource.setRoleid(2);
            roleResource.setResourceid("00010001");
            roleResource.setButton("");
            dao.insert(roleResource);
            roleResource.setRoleid(2);
            roleResource.setResourceid("000100010001");
            roleResource.setButton("BtnAdd,BtnDel,BtnUpdate,BtnSort,");
            dao.insert(roleResource);
            roleResource.setRoleid(2);
            roleResource.setResourceid("000100010002");
            roleResource.setButton("BtnAdd,BtnUpdate,BtnDel,BtnLocked,BtnUnlocked,");
            dao.insert(roleResource);
            roleResource.setRoleid(2);
            roleResource.setResourceid("000100010003");
            roleResource.setButton("BtnAdd,BtnDel,BtnUpdate,BtnAddRole,BtnDelRole,BtnMenu,");
            dao.insert(roleResource);
            roleResource.setRoleid(2);
            roleResource.setResourceid("000100010004");
            roleResource.setButton("BtnAdd,BtnDel,BtnUpdate,BtnSort,");
            dao.insert(roleResource);
            roleResource.setRoleid(2);
            roleResource.setResourceid("000100010005");
            roleResource.setButton("");
            dao.insert(roleResource);
            roleResource.setRoleid(2);
            roleResource.setResourceid("0001000100050001");
            roleResource.setButton("");
            dao.insert(roleResource);
            roleResource.setRoleid(2);
            roleResource.setResourceid("0001000100050002");
            roleResource.setButton("");
            dao.insert(roleResource);
            roleResource.setRoleid(2);
            roleResource.setResourceid("00010002");
            roleResource.setButton("");
            dao.insert(roleResource);
            roleResource.setRoleid(2);
            roleResource.setResourceid("000100020001");
            roleResource.setButton("BtnAdd,BtnDel,BtnUpdate,");
            dao.insert(roleResource);
            roleResource.setRoleid(2);
            roleResource.setResourceid("000100020002");
            roleResource.setButton("BtnAdd,BtnDel,BtnUpdate,BtnSort,");
            dao.insert(roleResource);
            //初始化角色用户
            Sys_user_role userRole = new Sys_user_role();
            userRole.setRoleid(2);
            userRole.setUserid(1);
            dao.insert(userRole);
            //初始化用户样式表
            Cms_user_style style = new Cms_user_style();
            style.setId(1);
            style.setStylename(String.valueOf(user.getUserid()));
            style.setStylename("bluesky");
            dao.insert(style);
            //初始化访问控制表
            Sys_safeconfig safeconfig = new Sys_safeconfig();
            safeconfig.setId(1);
            safeconfig.setState(1);
            safeconfig.setType(1);
            safeconfig.setNote("10.10.10.1");
            dao.insert(safeconfig);
            safeconfig.setId(2);
            safeconfig.setState(0);
            safeconfig.setType(0);
            safeconfig.setNote("10.10.10.2");
            dao.insert(safeconfig);
            //Oracle创建序列
            if (dao.meta().isOracle()) {
                dao.execute(Sqls.create("CREATE SEQUENCE SYS_USER_S  INCREMENT BY 1 START WITH 10001 NOMAXVALUE NOCYCLE  CACHE 10;"));
                dao.execute(Sqls.create("CREATE SEQUENCE SYS_CONFIG_S  INCREMENT BY 1 START WITH 1 NOMAXVALUE NOCYCLE  CACHE 10;"));
                dao.execute(Sqls.create("CREATE SEQUENCE SYS_ROLE_S  INCREMENT BY 1 START WITH 3 NOMAXVALUE NOCYCLE  CACHE 10;"));
                dao.execute(Sqls.create("CREATE SEQUENCE SYS_TASK_S  INCREMENT BY 1 START WITH 1 NOMAXVALUE NOCYCLE  CACHE 10;"));
                dao.execute(Sqls.create("CREATE SEQUENCE SYS_USER_LOG_S  INCREMENT BY 1 START WITH 1 NOMAXVALUE NOCYCLE  CACHE 10;"));
            }
            FileSqlManager fm = new FileSqlManager("init_sys_dict.sql");
            List<Sql> sqlList = fm.createCombo(fm.keys());
            dao.execute(sqlList.toArray(new Sql[sqlList.size()]));
            log.info("数据库初始化完成...");
        }
    }
}
