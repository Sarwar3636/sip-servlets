/*
 * Mobicents, Communications Middleware, Diameter Base
 * 
 * Copyright (c) 2008, Red Hat Middleware LLC or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Middleware LLC.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 *
 * Boston, MA  02110-1301  USA
 */
package net.java.slee.resource.diameter.base;

/**
 * Class that should be used to indicate Diameter problems.
 * 
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski</a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class DiameterException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public DiameterException() {
    super();
  }

  /**
   * 
   * @param message
   */
  public DiameterException(String message) {
    super(message);
  }

  /**
   * 
   * @param cause
   */
  public DiameterException(Throwable cause) {
    super(cause);
  }

  /**
   * 
   * @param message
   * @param cause
   */
  public DiameterException(String message, Throwable cause) {
    super(message, cause);
  }

}
