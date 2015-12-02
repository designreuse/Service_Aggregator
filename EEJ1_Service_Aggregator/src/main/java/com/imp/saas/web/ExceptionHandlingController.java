package com.imp.saas.web;


import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.imp.saas.exception.ConfigExceptions;
import com.imp.saas.exception.DatabaseCustomException;
import com.imp.saas.exception.SupportInfoException;
import com.imp.saas.exception.UnhandledException;

/**
 * A controller whose request-handler methods deliberately throw exceptions
 * <p>
 * Contains its own <tt>@ExceptionHandler</tt> methods to handle (most of) the
 * exceptions it raises.
 * 
 */
@Controller
@RequestMapping("/")
public class ExceptionHandlingController extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  protected Logger logger;

  public ExceptionHandlingController() {
    logger = LoggerFactory.getLogger(getClass());
  }

  /**
   * Simulates a database exception by always throwing
   * <tt>DatabaseException</tt>. Handled by
   * <tt>SimpleMappingExceptionResolver</tt>.
   * 
   * @return Nothing - it always throws the exception.
   * @throws DatabaseException
   *             Always thrown.
   */
  @RequestMapping("/databaseException")
  String throwDatabaseException() throws Exception {
    logger.info("Throw InvalidCreditCardException");
    throw new DatabaseCustomException("Database not found: info.db");
  }

  /**
   * Always throws a <tt>SupportInfoException</tt>. Must be caught by an
   * exception handler.
   * 
   * @return Nothing - it always throws the exception.
   * @throws SupportInfoException
   *             Always thrown.
   */
  @RequestMapping("/supportInfoException")
  String throwCustomException() throws Exception {
    logger.info("Throw SupportInfoException");
    throw new SupportInfoException("Custom exception occurred");
  }

  /**
   * Simulates a database exception by always throwing
   * <tt>UnhandledException</tt>. Must be caught by an exception handler.
   * 
   * @return Nothing - it always throws the exception.
   * @throws UnhandledException
   *             Always thrown.
   */
  @RequestMapping("/unhandledException")
  String throwUnhandledException() throws Exception {
    logger.info("Throw UnhandledException");
    throw new UnhandledException("Some exception occurred");
  }
  /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
  /* . . . . . . . . . . . . . EXCEPTION HANDLERS . . . . . . . . . . . . .. */
  /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

  /**
   * Convert a predefined exception to an HTTP Status code and specify the
   * name of a specific view that will be used to display the error.
   * 
   * @return Exception view.
   */
  @ExceptionHandler({ SQLException.class, DataAccessException.class ,DatabaseCustomException.class})
  public String databaseError(Exception exception) {
    // Nothing to do. Return value 'databaseError' used as logical view name
    // of an error page, passed to view-resolver(s) in usual way.
    logger.error("Request raised " + exception.getClass().getSimpleName());
    return "databaseError";
  }

  /**
   * Demonstrates how to take total control - setup a model, add useful
   * information and return the "support" view name. This method explicitly
   * creates and returns
   * 
   * @param req
   *            Current HTTP request.
   * @param exception
   *            The exception thrown - always {@link SupportInfoException}.
   * @return The model and view used by the DispatcherServlet to generate
   *         output.
   * @throws Exception
   */
  @ExceptionHandler({SupportInfoException.class,ConfigExceptions.class})
  public ModelAndView handleError(HttpServletRequest req, Exception exception)
      throws Exception {

    // Rethrow annotated exceptions or they will be processed here instead.
    if (AnnotationUtils.findAnnotation(exception.getClass(),
        ResponseStatus.class) != null)
      throw exception;

    logger.error("Request: " + req.getRequestURI() + " raised " + exception);

    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", exception);
    mav.addObject("url", req.getRequestURL());
    mav.addObject("timestamp", new Date().toString());
    mav.addObject("status", 500);

    mav.setViewName("support");
    return mav;
  }
}