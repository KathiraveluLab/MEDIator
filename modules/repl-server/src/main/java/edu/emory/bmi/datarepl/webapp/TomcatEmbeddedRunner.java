/*
 * Title:        Data Replication Server
 * Description:  Data Replication / Synchronization Tools.
 * Licence:      Apache License Version 2.0 - http://www.apache.org/licenses/
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datarepl.webapp;

import java.io.File;

import edu.emory.bmi.datarepl.constants.CommonConstants;
import edu.emory.bmi.datarepl.webapp.servlets.*;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

/**
 * Defines the Embedded Tomcat Instance and registers the servlets and mappings.
 */
public class TomcatEmbeddedRunner {
	public void startServer() throws LifecycleException {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(CommonConstants.EMBEDDED_TOMCAT_PORT);
		File base = new File(System.getProperty("user.dir") + File.separator + "webapp");

        Context rootCtx = tomcat.addContext("/mediator", base.getAbsolutePath());

        /*Create Servlet Mappings*/
		Tomcat.addServlet(rootCtx, "initServlet", new InitServlet());
        rootCtx.addServletMappingDecoded("/init", "initServlet");

		Tomcat.addServlet(rootCtx, "createRsServlet", new CreateRsServlet());
        rootCtx.addServletMappingDecoded("/createRs", "createRsServlet");

		Tomcat.addServlet(rootCtx, "appendRsServlet", new AppendRsServlet());
        rootCtx.addServletMappingDecoded("/appendRs", "appendRsServlet");

		Tomcat.addServlet(rootCtx, "retrieveRsServlet", new RetrieveRsServlet());
        rootCtx.addServletMappingDecoded("/retrieveRs", "retrieveRsServlet");

        Tomcat.addServlet(rootCtx, "deleteRsServlet", new DeleteRsServlet());
        rootCtx.addServletMappingDecoded("/deleteRs", "deleteRsServlet");

        Tomcat.addServlet(rootCtx, "duplicateRsServlet", new DuplicateRsServlet());
        rootCtx.addServletMappingDecoded("/duplicateRs", "duplicateRsServlet");

        Tomcat.addServlet(rootCtx, "listRsServlet", new ListRsServlet());
        rootCtx.addServletMappingDecoded("/listRs", "listRsServlet");

        Tomcat.addServlet(rootCtx, "listUsersServlet", new ListUsersServlet());
        rootCtx.addServletMappingDecoded("/listUsers", "listUsersServlet");

        Wrapper defaultServlet = rootCtx.createWrapper();
        defaultServlet.setName("default");
        defaultServlet.setServletClass("org.apache.catalina.servlets.DefaultServlet");
        defaultServlet.addInitParameter("debug", "0");
        defaultServlet.addInitParameter("listings", "false");
        defaultServlet.setLoadOnStartup(1);

        /*Add welcome page*/
        rootCtx.addChild(defaultServlet);
        rootCtx.addServletMappingDecoded("/", "default");
        rootCtx.addWelcomeFile("init.html");

        tomcat.start();
		tomcat.getServer().await();
	}
}
