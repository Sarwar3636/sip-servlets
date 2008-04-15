/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.mobicents.servlet.sip.core.session;

import java.io.Serializable;
import java.text.ParseException;

import javax.servlet.sip.SipApplicationSession;
import javax.servlet.sip.SipSessionsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Jean Deruelle
 *
 */
public class SipSessionsUtilImpl implements SipSessionsUtil, Serializable {
	private static transient Log logger = LogFactory.getLog(SipSessionsUtilImpl.class);
	
	private transient SessionManager sessionManager;
	private String applicationName;

	public SipSessionsUtilImpl(SessionManager sessionManager, String applicationName) {
		this.sessionManager = sessionManager;
		this.applicationName = applicationName;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.sip.SipSessionsUtil#getApplicationSession(java.lang.String)
	 */
	public SipApplicationSession getApplicationSession(String applicationSessionId) {
		if(applicationSessionId == null) {
			throw new NullPointerException("the given id is null !");
		}
		SipApplicationSessionKey applicationSessionKey;
		try {
			applicationSessionKey = SessionManager.parseSipApplicationSessionKey(applicationSessionId);
		} catch (ParseException e) {
			logger.error("the given application session id : " + applicationSessionId + 
					" couldn't be parsed correctly ",e);
			return null;
		}
		if(applicationSessionKey.getApplicationName().equals(applicationName)) {
			return sessionManager.getSipApplicationSession(applicationSessionKey, false, null);
		} else {
			logger.warn("the given application session id : " + applicationSessionId + 
					" tried to be retrieved from incorret application " + applicationName);
			return null;
		}
	}

}
