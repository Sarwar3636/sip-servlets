/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
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

package org.mobicents.protocols.ss7.map.dialog;

import java.io.IOException;

import org.mobicents.protocols.asn.AsnException;
import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.ss7.map.api.MAPDialog;
import org.mobicents.protocols.ss7.map.api.MAPException;
import org.mobicents.protocols.ss7.map.api.dialog.MAPAcceptInfo;

/**
 * map-accept [1] IMPLICIT SEQUENCE {
 *   ... ,
 *  extensionContainer SEQUENCE {
 *     privateExtensionList [0] IMPLICIT SEQUENCE SIZE (1 ..
 *        SEQUENCE {
 *           extId      MAP-EXTENSION .&extensionId ( {
 *              ,
 *              ...} ) ,
 *           extType    MAP-EXTENSION .&ExtensionType ( {
 *              ,
 *              ...} { @extId   } ) OPTIONAL} OPTIONAL,
 *     pcs-Extensions [1] IMPLICIT SEQUENCE {
 *        ... } OPTIONAL,
 *     ... } OPTIONAL},
 *
 * 
 * @author amit bhayani
 *
 */
public class MAPAcceptInfoImpl implements MAPAcceptInfo {
	
	protected static final int MAP_ACCEPT_INFO_TAG = 0x01;
	
	private MAPDialog mapDialog = null;

	public MAPDialog getMAPDialog() {
		return this.mapDialog;
	}

	public void setMAPDialog(MAPDialog mapDialog) {
		this.mapDialog = mapDialog;
	}
	
	public void decode(AsnInputStream ais) throws AsnException, IOException, MAPException{
		//TODO I donno what is here?
	}
	
	public void encode(AsnOutputStream asnOS) throws IOException, MAPException{
		//TODO I donno what is here?
	}

}