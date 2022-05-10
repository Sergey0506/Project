package com.restaurant.listener;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import com.restaurant.dao.Pool;

/**
 * ContextListener
 * 
 * @author Serhii Yakovenko.
 * 
 */
@WebListener
public class ContextListener implements ServletContextListener {

	private static final Logger log = Logger.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		log.info("Context destruction");
	}

	/**
	 * contextInitialized.
	 * 
	 * @param sce ServletContextEvent
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		initLog4J(servletContext);
		initCommandContainer();
		initI18N(servletContext);

		try (Connection con = Pool.getInstance().getConnection()) {
			if(con == null) throw new SQLException();
			log.info("Connection -> " + con);
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot initialize DB subsystem", e);
		}

		servletContext.setAttribute("app", servletContext.getContextPath());
		log.info("Application START");
	}

	/**
	 * Initializes log4j.
	 * 
	 * @param servletContext
	 */
	private void initLog4J(ServletContext servletContext) {

		PropertyConfigurator.configure(servletContext.getRealPath("WEB-INF/log4j.properties"));
		log.info("Log4J initialization");

	}

	/**
	 * Initializes CommandContainer.
	 * 
	 */
	private void initCommandContainer() {
		log.info("Command container initialization");
		try {
			Class.forName("com.restaurant.command.CommandContainer");
		} catch (ClassNotFoundException e) {
			log.error("CommandContainer class not found!");
			throw new IllegalStateException("CommandContainer class not found!", e);
		}
	}

	/**
	 * Initializes i18n.
	 * 
	 * @param servletContext
	 */
	private void initI18N(ServletContext servletContext) {
		log.info("I18N subsystem initialization");

		String localesValue = servletContext.getInitParameter("locales");
		String defaultLocale = servletContext.getInitParameter("defaultLocale");

		if (localesValue == null || localesValue.isEmpty()) {
			log.warn("'locales' init parameter is empty, the default encoding will be used");
			return;
		}

		List<String> locales = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(localesValue);
		while (st.hasMoreTokens()) {
			String localeName = st.nextToken();
			locales.add(localeName);
		}

		log.info("Set locales:" + locales);
		log.info("Default locale:" + defaultLocale);
		servletContext.setAttribute("locales", locales);
		servletContext.setAttribute("defaultLocale", defaultLocale); 
		

	}
}
