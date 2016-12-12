package cn.edu.hdu.infosys.core.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import cn.edu.hdu.infosys.common.log.Logger;

/**
 * 
 * 
 * @author Pi Chen
 * @version infosys V1.0.0, 2016年5月23日
 * @see
 * @since infosys V1.0.0
 */
public class InitialListener extends ContextLoaderListener
{
    private static Logger logger = Logger.getLogger(InitialListener.class);

    /**
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     * @param arg0
     */
    @Override
    public void contextDestroyed(ServletContextEvent event)
    {
        logger.info("start contextDestroyed.");
        super.contextDestroyed(event);

    }

    /**
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     * @param arg0
     */
    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        logger.info("start contextInitialized.");

        super.contextInitialized(event);

    }

}
