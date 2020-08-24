package bsu.chatbot.useractivites.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.auth.LoginConfig;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;


/**
 * @author Mohamed Gawad
 *
 *         BSU-Chatbot
 */
@ApplicationPath("/")
@Path("/")
@RequestScoped
public class UserActivityController extends Application {
	
	private static boolean staticInitialized = false;
	public  static short   consecutiveErrors = 0; //used in health check
	
	private static Logger logger = Logger.getLogger(UserActivityController.class.getName());

	private static final double ERROR            = -1.0;
	private static final int    CONFLICT         = 409;         //odd that JAX-RS has no ConflictException
	private static final short  MAX_ERRORS       = 3;           //health check will fail if this threshold is met
	private static final String FAIL             = "FAIL";
	
	private static DataSource datasource = null;
	
//	@Inject
//	private UserProfileService userProfileService;
	
	public static boolean isReady() { //determines answer to readiness probe
		if (!staticInitialized) try {
			staticInitialize();
		} catch (Throwable t) {
			logException(t);
		}

		return (datasource!=null); //the only hard prereq for Portfolio is that JDBC is configured properly
	}

	public static boolean isHealthy() { //determines answer to livenesss probe
		return consecutiveErrors<MAX_ERRORS;
	}

	private static void staticInitialize() throws NamingException {
		if (!staticInitialized) try {
			logger.info("Obtaining JDBC Datasource");

			InitialContext context = new InitialContext();
			datasource = (DataSource) context.lookup("jdbc/UserManagement/UserManagementDB");

			logger.info("JDBC Datasource successfully obtained!"); //exception would have occurred otherwise

			staticInitialized = true;
		} catch (NamingException ne) {
			logger.warning("JNDI lookup failed.  Initialization did NOT complete.  Expect severe failures!");
			logException(ne);
			throw ne;
		} catch (RuntimeException re) {
			logger.warning("Runtime exception.  Initialization did NOT complete.  Expect severe failures!");
			logException(re);
			throw re;
		}
	}
	
//	@POST
//	@Path("/addUserProfile")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Counted(name="addUserProfile", displayName="addUserProfile")
//	@Transactional
//	public UserProfileDto addMsngerUserProfile(@RequestBody UserProfileDto userProfile) throws SQLException {
//		try {
//			logger.info("addMsngerUserProfile started");
//			UserProfileDto addedSubscriber = userProfileService.addUser(userProfile);
//			if (addedSubscriber != null) {
//				logger.info("addMsngerUserProfile completed");
//				return addedSubscriber;
//			} else {
//				return addedSubscriber;
//			}
//		}catch (WebApplicationException e) {
////			logException(e);
//			logger.warning(e.getMessage());
//			return userProfile;
//		}
//		
//		catch (Exception e) {
//			logException(e);
//			logger.warning(e.getMessage());
//			throw e;
//		}
//	}
	
//	@GET
//	@Path("/getUserProfile/{userId}")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Counted(name="getUserProfile", displayName="getUserProfile")
//	@Transactional
//	public UserProfileDto getMsngerUserProfile(@PathParam("userId") String userId) throws SQLException {
//		UserProfileDto userProfile = null;
//		try {
//			logger.info("getMsngerUserProfile started");
//			userProfile = userProfileService.getUserById(userId);
//			if (userProfile != null) {
//				logger.info("getMsngerUserProfile completed");
//				return userProfile;
//			} else {
//				logger.info("getMsngerUserProfile NULL.............");
//				return userProfile;
//			}
//		}catch (WebApplicationException e) {
////			logException(e);
//			logger.warning(e.getMessage());
//			return userProfile;
//		}
//		catch (Exception e) {
//			logException(e);
//			logger.warning(e.getMessage());
//			throw e;
//		}
//	}


	private static void logException(Throwable t) {
		logger.warning(t.getClass().getName()+": "+t.getMessage());

		//only log the stack trace if the level has been set to at least INFO
		if (logger.isLoggable(Level.INFO)) {
			StringWriter writer = new StringWriter();
			t.printStackTrace(new PrintWriter(writer));
			logger.info(writer.toString());
		}
	}

}
