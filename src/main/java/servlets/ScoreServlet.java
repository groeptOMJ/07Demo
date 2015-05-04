package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;

import service.UserService;
import bean.Score;

/**
 * Demo Servlet to process new scores from game * demo properties * demo log4j * demo MVC
 *
 * @author Stijn Heylen
 *
 */

@WebServlet(value = "/cics", loadOnStartup = 1)
public class ScoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger(ScoreServlet.class);

	private UserService cics;
	protected Properties appProperties;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		// Setup log4j and application properties
		PropertyConfigurator.configure(this.getClass().getClassLoader().getResourceAsStream("log4j.properties"));
		appProperties = new Properties();
		try {
			// here you can load application property file
			appProperties.load(this.getClass().getClassLoader().getResourceAsStream("demo.properties"));
			// load connection data depending used usrironment
			cics = new UserService(appProperties.getProperty("url.dev"), appProperties.getProperty("user.dev"),
					appProperties.getProperty("password.dev"));
		} catch (IOException e) {
			logger.fatal("Properties could not be loaded:" + e.getMessage());
			throw new ServletException(e);
		}

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("text/xml");
		try {
			String usr = request.getParameter("usr");
			// use dummy user if no username was provided
			if (usr == null || usr.isEmpty()) {
				usr = "U1";
			}
			String scr = request.getParameter("scr");

			// get some data from a backend using the provided input
			response.setContentType("text/javascript");
			Score s = cics.getData(usr);

			// add the score if provided in the call
			if (scr != null && !scr.isEmpty()) {
				s.setScore(s.getScore() + Integer.parseInt(scr));
			}
			PrintWriter out = response.getWriter();
			out.println(new JSONObject(s));

		} catch (Exception e) {
			logger.error(e);
			response.sendError(500, "Data could not be fetched.");
		}
	}

	/**
	 * Provide get for testing purpose: use localhost:8080/07Demo/cics to test you servlet first
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
