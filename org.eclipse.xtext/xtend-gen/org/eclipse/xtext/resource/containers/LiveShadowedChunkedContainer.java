/**
 * Copyright (c) 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.resource.containers;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.LiveShadowedChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * @author koehnlein - Initial contribution and API
 * @since 2.14
 */
@Beta
@FinalFieldsConstructor
@SuppressWarnings("all")
public class LiveShadowedChunkedContainer implements IContainer {
  private final LiveShadowedChunkedResourceDescriptions descriptions;
  
  private final String containerName;
  
  protected ChunkedResourceDescriptions getChunkedResourceDescriptions() {
    IResourceDescriptions _globalDescriptions = this.descriptions.getGlobalDescriptions();
    return ((ChunkedResourceDescriptions) _globalDescriptions);
  }
  
  protected ResourceDescriptionsData getChunk() {
    return this.getChunkedResourceDescriptions().getContainer(this.containerName);
  }
  
  protected Iterable<IResourceDescription> getContainedLocalDescriptions() {
    final Function1<IResourceDescription, Boolean> _function = (IResourceDescription it) -> {
      return Boolean.valueOf(this.isContained(it.getURI()));
    };
    return IterableExtensions.<IResourceDescription>filter(this.descriptions.getLocalDescriptions().getAllResourceDescriptions(), _function);
  }
  
  protected boolean isContained(final URI uri) {
    final Function1<IResourceDescription, Boolean> _function = (IResourceDescription it) -> {
      URI _uRI = it.getURI();
      return Boolean.valueOf(Objects.equal(_uRI, uri));
    };
    return IterableExtensions.<IResourceDescription>exists(this.getChunk().getAllResourceDescriptions(), _function);
  }
  
  @Override
  public IResourceDescription getResourceDescription(final URI uri) {
    IResourceDescription _xifexpression = null;
    boolean _isContained = this.isContained(uri);
    if (_isContained) {
      _xifexpression = this.descriptions.getResourceDescription(uri);
    } else {
      _xifexpression = null;
    }
    return _xifexpression;
  }
  
  @Override
  public int getResourceDescriptionCount() {
    return IterableExtensions.size(this.getChunk().getAllResourceDescriptions());
  }
  
  @Override
  public Iterable<IResourceDescription> getResourceDescriptions() {
    Iterable<IResourceDescription> _containedLocalDescriptions = this.getContainedLocalDescriptions();
    Iterable<IResourceDescription> _allResourceDescriptions = this.getChunk().getAllResourceDescriptions();
    return Iterables.<IResourceDescription>concat(_containedLocalDescriptions, _allResourceDescriptions);
  }
  
  @Override
  public boolean hasResourceDescription(final URI uri) {
    IResourceDescription _resourceDescription = this.getChunkedResourceDescriptions().getContainer(this.containerName).getResourceDescription(uri);
    return (_resourceDescription != null);
  }
  
  @Override
  public Iterable<IEObjectDescription> getExportedObjects() {
    final Function1<IResourceDescription, Iterable<IEObjectDescription>> _function = (IResourceDescription it) -> {
      return it.getExportedObjects();
    };
    Iterable<IEObjectDescription> _flatten = Iterables.<IEObjectDescription>concat(IterableExtensions.<IResourceDescription, Iterable<IEObjectDescription>>map(this.getContainedLocalDescriptions(), _function));
    Iterable<IEObjectDescription> _exportedObjects = this.getChunk().getExportedObjects();
    return Iterables.<IEObjectDescription>concat(_flatten, _exportedObjects);
  }
  
  @Override
  public Iterable<IEObjectDescription> getExportedObjects(final EClass type, final QualifiedName name, final boolean ignoreCase) {
    final Function1<IResourceDescription, Iterable<IEObjectDescription>> _function = (IResourceDescription it) -> {
      return it.getExportedObjects(type, name, ignoreCase);
    };
    Iterable<IEObjectDescription> _flatten = Iterables.<IEObjectDescription>concat(IterableExtensions.<IResourceDescription, Iterable<IEObjectDescription>>map(this.getContainedLocalDescriptions(), _function));
    Iterable<IEObjectDescription> _exportedObjects = this.getChunk().getExportedObjects(type, name, ignoreCase);
    return Iterables.<IEObjectDescription>concat(_flatten, _exportedObjects);
  }
  
  @Override
  public Iterable<IEObjectDescription> getExportedObjectsByObject(final EObject object) {
    final Function1<IResourceDescription, Iterable<IEObjectDescription>> _function = (IResourceDescription it) -> {
      return it.getExportedObjectsByObject(object);
    };
    Iterable<IEObjectDescription> _flatten = Iterables.<IEObjectDescription>concat(IterableExtensions.<IResourceDescription, Iterable<IEObjectDescription>>map(this.getContainedLocalDescriptions(), _function));
    Iterable<IEObjectDescription> _exportedObjectsByObject = this.getChunk().getExportedObjectsByObject(object);
    return Iterables.<IEObjectDescription>concat(_flatten, _exportedObjectsByObject);
  }
  
  @Override
  public Iterable<IEObjectDescription> getExportedObjectsByType(final EClass type) {
    final Function1<IResourceDescription, Iterable<IEObjectDescription>> _function = (IResourceDescription it) -> {
      return it.getExportedObjectsByType(type);
    };
    Iterable<IEObjectDescription> _flatten = Iterables.<IEObjectDescription>concat(IterableExtensions.<IResourceDescription, Iterable<IEObjectDescription>>map(this.getContainedLocalDescriptions(), _function));
    Iterable<IEObjectDescription> _exportedObjectsByType = this.getChunk().getExportedObjectsByType(type);
    return Iterables.<IEObjectDescription>concat(_flatten, _exportedObjectsByType);
  }
  
  @Override
  public boolean isEmpty() {
    return (IterableExtensions.isEmpty(this.getContainedLocalDescriptions()) && this.getChunk().isEmpty());
  }
  
  public LiveShadowedChunkedContainer(final LiveShadowedChunkedResourceDescriptions descriptions, final String containerName) {
    super();
    this.descriptions = descriptions;
    this.containerName = containerName;
  }
}
