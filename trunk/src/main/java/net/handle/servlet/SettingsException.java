/*
 * Created by: christopher
 * Date: 2 Mar 2008
 * Time: 12:49:31
 *
 * <p>Copyright (C) 2008 Nature Publishing Group, Inc.</p>
 *
 * <p><a rel="license" href="http://creativecommons.org/licenses/GPL/2.0/">
 * <img alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/GPL/2.0/88x62.png" /></a><br />
 * This work is licensed under a <a rel="license"
 * href="http://creativecommons.org/licenses/GPL/2.0/">Creative Commons GNU
 * General Public License License</a>.</p>
 */
package net.handle.servlet;


/**
 * <p>
 * Exception thrown when the app configuration is missing required properties or
 * is otherwise invalid.
 * </p>
 *
 * @author <a href="mailto:christopher.townson@googlemail.com">Christopher Townson</a>
 */
public class SettingsException extends Exception {

    private static final long serialVersionUID = 2918862132391711925L;

    /**
     * <p>
     * </p>
     *
     * @param message
     */
    public SettingsException(String message) {
        super(message);
    }

    /**
     * <p>
     * </p>
     *
     * @param cause
     */
    public SettingsException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * </p>
     *
     * @param message
     * @param cause
     */
    public SettingsException(String message, Throwable cause) {
        super(message, cause);
    }

}
