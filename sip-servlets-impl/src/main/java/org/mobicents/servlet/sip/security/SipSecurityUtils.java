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
package org.mobicents.servlet.sip.security;

import javax.servlet.sip.SipServletResponse;

import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mobicents.servlet.sip.message.SipServletRequestImpl;
import org.mobicents.servlet.sip.message.SipServletResponseImpl;
import org.mobicents.servlet.sip.security.authentication.DigestAuthenticator;
import org.mobicents.servlet.sip.startup.SipStandardContext;
import org.mobicents.servlet.sip.startup.loading.SipLoginConfig;
import org.mobicents.servlet.sip.startup.loading.SipSecurityCollection;
import org.mobicents.servlet.sip.startup.loading.SipSecurityConstraint;

public class SipSecurityUtils {
	
	private static Log log = LogFactory.getLog(SipSecurityUtils.class);
	
	public static boolean authenticate(SipStandardContext sipStandardContext,
			SipServletRequestImpl request,
			SipSecurityConstraint sipConstraint)
	{
		boolean authenticated = false;
		SipLoginConfig loginConfig = sipStandardContext.getSipLoginConfig();
		try
		{
			if(loginConfig != null) {
				String authMethod = loginConfig.getAuthMethod();
				if(authMethod != null)
				{
					if(authMethod.equalsIgnoreCase("DIGEST")) {
						DigestAuthenticator auth = new DigestAuthenticator();
						auth.setContext(sipStandardContext);
						SipServletResponseImpl response = createErrorResponse(request, sipConstraint);
						authenticated = auth.authenticate(request, (SipServletResponseImpl) response, loginConfig);		
						request.setUserPrincipal(auth.getPrincipal());
					} else if(authMethod.equalsIgnoreCase("BASIC")) {
						throw new IllegalStateException("Basic authentication not supported in JSR 289");
					}
				}
			}
			else {
				log.debug("No login configuration found in sip.xml. We won't authenticate.");
				return true; // There is no auth config in sip.xml. So don't authenticate.
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return authenticated;
	}
	
	private static SipServletResponseImpl createErrorResponse(SipServletRequestImpl request, SipSecurityConstraint sipConstraint)
	{
		SipServletResponse response = null;
    	if(sipConstraint.isProxyAuthentication()) {
    		response = (SipServletResponseImpl) 
    			request.createResponse(SipServletResponseImpl.SC_PROXY_AUTHENTICATION_REQUIRED);
    	} else {
    		response = (SipServletResponseImpl) 
    			request.createResponse(SipServletResponseImpl.SC_UNAUTHORIZED);
    	}
    	return (SipServletResponseImpl) response;
	}
	
	public static boolean authorize(SipStandardContext sipStandardContext, SipServletRequestImpl request)
	{
		boolean allConstrainsSatisfied = true;
		SecurityConstraint[] constraints = sipStandardContext.findConstraints();
		
		// If we have no constraints, just authorize the request;
		if(constraints.length == 0) return true;
		
		for(SecurityConstraint constraint:constraints)
		{
			if(constraint instanceof SipSecurityConstraint)
			{
				SipSecurityConstraint sipConstraint = (SipSecurityConstraint) constraint;
				for(org.apache.catalina.deploy.SecurityCollection security:sipConstraint.findCollections()) {
					
					// For each secured resource see if it's bound to the current 
					// request method and servlet name.
					SipSecurityCollection sipSecurity = (SipSecurityCollection)security;
					String servletName = request.getSipSession().getHandler();
					if(sipSecurity.findMethod(request.getMethod())
							&& sipSecurity.findServletName(servletName)) {
						boolean constraintSatisfied = false;
						// If yes, see if the current user is in a role compatible with the
						// required roles for the resource.
						if(authenticate(sipStandardContext, request, sipConstraint)) {
							GenericPrincipal principal = (GenericPrincipal) request.getUserPrincipal();
							if(principal == null) return false;
							
							for(String assignedRole:constraint.findAuthRoles()) {
								if(principal.hasRole(assignedRole)) {
									constraintSatisfied = true;
									break;
								}
							}
						}
						if(!constraintSatisfied) {
							allConstrainsSatisfied = false;
							log.error("Constraint \"" + constraint.getDisplayName() + "\" not satifsied");
						}
					}
				}
			}
		}
		return allConstrainsSatisfied;
	}
}
