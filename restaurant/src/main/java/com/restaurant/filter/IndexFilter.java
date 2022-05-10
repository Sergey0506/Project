package com.restaurant.filter;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.restaurant.dao.DAOFactory;
import com.restaurant.dao.DBException;
import com.restaurant.dao.Pool;
import com.restaurant.dao.entity.Dish;
import com.restaurant.dao.entity.Menu;

/**
 * This filter adjusts the display of the menu on the main page of the
 * application. It also includes setting session attributes to configure the
 * page display in relation to the user's role.
 * 
 * @author Serhii Yakovenko.
 * 
 */
@WebFilter("/index")
public class IndexFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 5401728312602693827L;

	private static final Logger log = Logger.getLogger(IndexFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		request.setCharacterEncoding("UTF-8");

		if (session.getAttribute("page") == null) {
			String onPage;
			String searchType;
			String sortBy;
			String sortType;

			if (session.getAttribute("search") != null) {
				log.info("IndexFilter:do search");
				@SuppressWarnings("unchecked")
				Map<String, String> search = (Map<String, String>) session.getAttribute("search");
				onPage = search.get("onPage");
				searchType = search.get("searchType");
				sortBy = search.get("sortBy");
				sortType = search.get("sortType");

				if (searchType.equals("full")) {
					searchType = null;
					sortBy = "category, " + sortBy;
				}
			} else {
				onPage = "20";
				searchType = null;
				sortBy = "category, name";
				sortType = "ASC";
			}

			Connection con = Pool.getInstance().getConnection();
			if (con != null) {

				List<String> category = null;
				List<Dish> start = null;
				try {
					category = DAOFactory.getInstatance().getDishDAO().findCategoriesValues(con);
					start = DAOFactory.getInstatance().getDishDAO().findAllFromDishesSortBy(con, searchType, sortBy,
							sortType);

				} catch (DBException e) {
					log.info("Database access error!");
					session.setAttribute("error", e.getMessage());
					resp.sendRedirect("error");

				}
				Menu m = new Menu();
				for (int i = 0; i < start.size(); i += Integer.valueOf(onPage)) {
					List<Dish> pageItems = start.stream().skip(i * 1).limit(Integer.valueOf(onPage))
							.collect(Collectors.toList());
					Map<String, List<Dish>> page = m.createPage();
					for (Dish item : pageItems) {
						page.computeIfAbsent(item.getCategory(), value -> new ArrayList<>()).add(item);
					}
					m.addPage(page);
				}

				if (session.getAttribute("form") == null) {
					Map<String, String> form = new HashMap<>();
					form.put("name", "checked=\"checked\"");
					form.put("ASC", "checked=\"checked\"");
					form.put("full", "selected");
					form.put("20", "selected");
					session.setAttribute("form", form);
				}

				session.setAttribute("menu", m.getMenu());
				session.setAttribute("category", category);
			}
			session.setAttribute("page", 1);
		}
		Map<String, String> show = new HashMap<>();
		show.put("main", "main");
		session.setAttribute("show", show);

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		log.info("Init IndexFilter");
	}

}
