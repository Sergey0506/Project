package com.restaurant.command;

import static java.nio.file.StandardCopyOption.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import com.restaurant.controller.Controller;
import com.restaurant.dao.AppException;
import com.restaurant.dao.DAOFactory;
import com.restaurant.dao.DBException;
import com.restaurant.dao.Pool;
import com.restaurant.dao.entity.User;

/**
 * Command for Add Food to DB.
 * 
 * @author Serhii Yakovenko.
 * 
 */

public class AddFoodCommand implements Command {

	private static final Logger log = Logger.getLogger(Controller.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		ServletContext sc = req.getServletContext();
		Locale locale = Locale.forLanguageTag((String) sc.getAttribute("defaultLocale"));
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			locale = Locale.forLanguageTag(user.getLocale());
		}
		ResourceBundle exampleBundle = ResourceBundle.getBundle("resources", locale);

		String address = "controller?command=menu&button=addFood";

		String name = req.getParameter("name");
		if (name.length() > 100) {
			session.setAttribute("error", exampleBundle.getString("error_The_title_is_too_long"));
			return address;
		}

		String category = req.getParameter("category");

		String description = req.getParameter("description");
		if (description.length() > 200) {
			session.setAttribute("error", exampleBundle.getString("error_Too_long_description"));
			return address;
		}

		int price = Integer.parseInt(req.getParameter("price"));

		String status = req.getParameter("status");
		if (status == null) {
			status = "not avalable";
		}

		Connection con = Pool.getInstance().getConnection();
		
		if(con == null) {
			session.setAttribute("error", exampleBundle.getString("error_Data"));
			return "error";
		}

		int dishId;

		try {
			if (req.getParameter("type") == null) {

				dishId = DAOFactory.getInstatance().getDishDAO().createDish(con, name, category, description, price);

			} else {
				dishId = Integer.parseInt(req.getParameter("id"));
				DAOFactory.getInstatance().getDishDAO().editDish(con, dishId, name, category, description, price,
						status);
			}
		} catch (DBException e) {
			log.error("Error adding/changing menu! " + e.getMessage());
			throw new AppException(exampleBundle.getString("error_Error_adding/changing_menu")
					+ exampleBundle.getString(e.getMessage()), e);
		}

		Part filePart;
		try {
			filePart = req.getPart("file");
		} catch (IOException | ServletException e) {
			log.error("Part-error! " + e.getMessage());
			throw new AppException("Part-error", e);
		}
		String fileName = filePart.getSubmittedFileName();

		if (!fileName.isEmpty()) {

			Long size = filePart.getSize();

			String type = fileName.substring(fileName.lastIndexOf(".") + 1);

			List<String> types = new ArrayList<>();
			types.add("jpg");
			types.add("jpeg");
			types.add("png");

			if (!types.contains(type)) {
				session.setAttribute("error", exampleBundle.getString("error_Wrong_file_type"));
				return address;
			}
			int maxSize = 102400;
			if (size > maxSize) {
				session.setAttribute("error", exampleBundle.getString("error_The_file_is_too_big"));
				return address;
			}

			String root = sc.getRealPath("img") + File.separator;
			String test = root + "test.jpg";

			try {
				Files.deleteIfExists(Paths.get(test));
				filePart.write(test);
			} catch (IOException e) {
				log.error("Error writing temporary file! " + e.getMessage());
				throw new AppException(exampleBundle.getString("error_Error_writing_temporary_file")
						+ exampleBundle.getString(e.getMessage()), e);

			}

			BufferedImage bufImage;
			try {
				bufImage = ImageIO.read(new File(test));
				if (bufImage.getHeight() != bufImage.getWidth()) {
					session.setAttribute("error", exampleBundle.getString("error_File_not_in_1*1_format"));
					return address;
				}

				if (bufImage.getHeight() > 256 || bufImage.getWidth() > 256 || bufImage.getHeight() < 64
						|| bufImage.getWidth() < 64) {
					session.setAttribute("error", exampleBundle.getString("error_Wrong_image_resolution"));
					return address;
				}
			} catch (IOException e) {
				log.error("Error reading temporary file! " + e.getMessage());
				throw new AppException(exampleBundle.getString("error_Error_reading_temporary_file")
						+ exampleBundle.getString(e.getMessage()), e);
			}

			String dest = root + "menu" + File.separator + "food" + dishId + ".jpg";

			try {
				Files.deleteIfExists(Paths.get(dest));
				Files.copy(Path.of(test), Path.of(dest), REPLACE_EXISTING);
			} catch (IOException e) {
				log.error("Error copying temporary file! " + e.getMessage());
				throw new AppException(exampleBundle.getString("error_Error_copying_temporary_file")
						+ exampleBundle.getString(e.getMessage()), e);
			}
		}

		if (req.getParameter("type") == null) {
			session.setAttribute("info", exampleBundle.getString("info_The_item_has_been_added_to_the_menu"));
		} else {
			session.setAttribute("info", exampleBundle.getString("info_Changes_applied"));

			address = "index";

			session.removeAttribute("details");
			session.removeAttribute("error");
		}
		return address;

	}

}
