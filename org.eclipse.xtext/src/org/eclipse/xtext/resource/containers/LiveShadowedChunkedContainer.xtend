/*******************************************************************************
 * Copyright (c) 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.resource.containers

import com.google.common.annotations.Beta
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions
import org.eclipse.xtext.resource.impl.LiveShadowedChunkedResourceDescriptions

/**
 * @author koehnlein - Initial contribution and API
 * @since 2.14
 */
@Beta 
@FinalFieldsConstructor
class LiveShadowedChunkedContainer implements IContainer {
	
	val LiveShadowedChunkedResourceDescriptions descriptions

	val String containerName
	
	protected def getChunkedResourceDescriptions() {
		descriptions.globalDescriptions as ChunkedResourceDescriptions
	}
	
	protected def getChunk() {
		chunkedResourceDescriptions.getContainer(containerName) 
	}
	
	protected def getContainedLocalDescriptions() {
		descriptions.localDescriptions.allResourceDescriptions.filter[URI.contained]
	}
	
	protected def boolean isContained(URI uri) {
		chunk
			.allResourceDescriptions
			.exists[URI == uri]
	}
	
	override getResourceDescription(URI uri) {
		if(uri.contained) 
			descriptions.getResourceDescription(uri)
		else
			null
	}
	
	override getResourceDescriptionCount() {
		chunk.allResourceDescriptions.size
	}
	
	override getResourceDescriptions() {
		containedLocalDescriptions
		+ chunk.allResourceDescriptions
	}
	
	override hasResourceDescription(URI uri) {
		chunkedResourceDescriptions.getContainer(containerName).getResourceDescription(uri) !== null
	}
	
	override getExportedObjects() {
		containedLocalDescriptions.map[exportedObjects].flatten 
		+ chunk.exportedObjects
	}
	
	override getExportedObjects(EClass type, QualifiedName name, boolean ignoreCase) {
		containedLocalDescriptions.map[getExportedObjects(type, name, ignoreCase)].flatten
		+ chunk.getExportedObjects(type, name, ignoreCase)
	}
	
	override getExportedObjectsByObject(EObject object) {
		containedLocalDescriptions.map[getExportedObjectsByObject(object)].flatten
		+ chunk.getExportedObjectsByObject(object)
	}
	
	override getExportedObjectsByType(EClass type) {
		containedLocalDescriptions.map[getExportedObjectsByType(type)].flatten
		+ chunk.getExportedObjectsByType(type)
	}
	
	override isEmpty() {
		containedLocalDescriptions.empty && chunk.empty
	}
	
}