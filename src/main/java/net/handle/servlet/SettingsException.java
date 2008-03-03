/*
 * Created by: <a href="mailto:c.townson@nature.com">Christopher Townson</a>
 * Date: 2 Mar 2008
 * Time: 12:49:31
 *
 * <p>Copyright (C) 2008 Nature Publishing Group, Inc.</p>
 *
 * <p><abbr title="Nature Publishing Group">NPG</abbr> reserves all rights in this
 * program. The program or any portion thereof may not be reproduced in any form
 * whatsoever without the written consent of
 * <abbr title="Nature Publishing Group">NPG</abbr>.</p>
 *
 * <p>Nature Publishing Group is a division of Macmillan Publishers Limited, of
 * Brunel Road, Houndmills, Basingstoke, Hampshire, RG21 6XS. Registered Number
 * 785998 England.</p>
 */
package net.handle.servlet;


/**
 * <p>
 * Exception thrown when the app configuration is missing required properties or
 * is otherwise invalid.
 * </p>
 *
 * @author <a href="mailto:c.townson@nature.com">Christopher Townson</a>
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
