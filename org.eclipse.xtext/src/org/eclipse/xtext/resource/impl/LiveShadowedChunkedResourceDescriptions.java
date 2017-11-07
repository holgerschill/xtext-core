/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.resource.impl;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.xtext.EcoreUtil2;

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.14
 */
public class LiveShadowedChunkedResourceDescriptions extends LiveShadowedResourceDescriptions {
	
	@Override
	public void setContext(Notifier ctx) {
		localDescriptions.setContext(ctx);
		localDescriptions.setData(null);
		globalDescriptions = ChunkedResourceDescriptions.findInEmfObject(EcoreUtil2.getResourceSet(ctx));
	}
}
