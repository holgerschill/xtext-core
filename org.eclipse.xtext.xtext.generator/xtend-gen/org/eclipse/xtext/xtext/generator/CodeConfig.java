/**
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.xtext.generator;

import com.google.common.base.Objects;
import com.google.inject.Injector;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.XtextVersion;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xtext.generator.IGuiceAwareGeneratorComponent;
import org.eclipse.xtext.xtext.generator.model.TypeReference;
import org.eclipse.xtext.xtext.generator.model.annotations.IClassAnnotation;

/**
 * Configuration object for generated code.
 * @noextend
 */
@SuppressWarnings("all")
public class CodeConfig implements IGuiceAwareGeneratorComponent {
  private final static String FILE_HEADER_VAR_TIME = "${time}";
  
  private final static String FILE_HEADER_VAR_DATE = "${date}";
  
  private final static String FILE_HEADER_VAR_YEAR = "${year}";
  
  private final static String FILE_HEADER_VAR_USER = "${user}";
  
  private final static String FILE_HEADER_VAR_VERSION = "${version}";
  
  @Accessors
  private String encoding = Charset.defaultCharset().name();
  
  @Accessors
  private String lineDelimiter = Strings.newLine();
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private String fileHeader;
  
  private String fileHeaderTemplate = "/*\n * generated by Xtext\n */";
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private final List<IClassAnnotation> classAnnotations = CollectionLiterals.<IClassAnnotation>newArrayList();
  
  @Accessors
  private boolean preferXtendStubs = true;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private XtextVersion xtextVersion;
  
  /**
   * Configure a template for file headers. The template can contain variables:
   * <ul>
   *   <li><code>${time}</code> - the current time of the day (hour:minute:second)</li>
   *   <li><code>${date}</code> - the current date (month day, year)</li>
   *   <li><code>${year}</code> - the current year</li>
   *   <li><code>${user}</code> - the content of the 'user.name' system property</li>
   *   <li><code>${version}</code> - the generator plug-in version</li>
   * </ul>
   */
  public void setFileHeader(final String fileHeaderTemplate) {
    this.fileHeaderTemplate = fileHeaderTemplate;
  }
  
  /**
   * Class annotations are used to configure specific Java annotations to be added to each generated class.
   */
  public void addClassAnnotation(final IClassAnnotation annotation) {
    this.classAnnotations.add(annotation);
  }
  
  @Override
  public void initialize(final Injector injector) {
    injector.injectMembers(this);
    XtextVersion _current = XtextVersion.getCurrent();
    this.xtextVersion = _current;
    if ((this.lineDelimiter == null)) {
      this.lineDelimiter = "\n";
    }
    String fileHeader = this.fileHeaderTemplate;
    boolean _notEquals = (!Objects.equal(fileHeader, null));
    if (_notEquals) {
      boolean _contains = fileHeader.contains(CodeConfig.FILE_HEADER_VAR_TIME);
      if (_contains) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date _date = new Date();
        final String time = dateFormat.format(_date);
        String _replace = fileHeader.replace(CodeConfig.FILE_HEADER_VAR_TIME, time);
        fileHeader = _replace;
      }
      boolean _contains_1 = fileHeader.contains(CodeConfig.FILE_HEADER_VAR_DATE);
      if (_contains_1) {
        final SimpleDateFormat dateFormat_1 = new SimpleDateFormat("MMM d, yyyy");
        Date _date_1 = new Date();
        final String date = dateFormat_1.format(_date_1);
        String _replace_1 = fileHeader.replace(CodeConfig.FILE_HEADER_VAR_DATE, date);
        fileHeader = _replace_1;
      }
      boolean _contains_2 = fileHeader.contains(CodeConfig.FILE_HEADER_VAR_YEAR);
      if (_contains_2) {
        final SimpleDateFormat dateFormat_2 = new SimpleDateFormat("yyyy");
        Date _date_2 = new Date();
        final String year = dateFormat_2.format(_date_2);
        String _replace_2 = fileHeader.replace(CodeConfig.FILE_HEADER_VAR_YEAR, year);
        fileHeader = _replace_2;
      }
      boolean _contains_3 = fileHeader.contains(CodeConfig.FILE_HEADER_VAR_USER);
      if (_contains_3) {
        final String user = System.getProperty("user.name");
        boolean _notEquals_1 = (!Objects.equal(user, null));
        if (_notEquals_1) {
          String _replace_3 = fileHeader.replace(CodeConfig.FILE_HEADER_VAR_USER, user);
          fileHeader = _replace_3;
        }
      }
      boolean _contains_4 = fileHeader.contains(CodeConfig.FILE_HEADER_VAR_VERSION);
      if (_contains_4) {
        String _string = this.xtextVersion.toString();
        String _replace_4 = fileHeader.replace(CodeConfig.FILE_HEADER_VAR_VERSION, _string);
        fileHeader = _replace_4;
      }
    }
    this.fileHeader = fileHeader;
  }
  
  public String getClassAnnotationsAsString() {
    boolean _isEmpty = this.classAnnotations.isEmpty();
    if (_isEmpty) {
      return null;
    }
    final StringBuilder stringBuilder = new StringBuilder();
    for (final IClassAnnotation annotation : this.classAnnotations) {
      String _string = annotation.toString();
      StringBuilder _append = stringBuilder.append(_string);
      String _newLine = Strings.newLine();
      _append.append(_newLine);
    }
    return stringBuilder.toString();
  }
  
  public String getAnnotationImportsAsString() {
    boolean _isEmpty = this.classAnnotations.isEmpty();
    if (_isEmpty) {
      return null;
    }
    final StringBuilder stringBuilder = new StringBuilder();
    for (final IClassAnnotation annotation : this.classAnnotations) {
      {
        final TypeReference importString = annotation.getAnnotationImport();
        if ((importString != null)) {
          StringBuilder _append = stringBuilder.append("import ");
          StringBuilder _append_1 = _append.append(importString);
          StringBuilder _append_2 = _append_1.append(";");
          String _newLine = Strings.newLine();
          _append_2.append(_newLine);
        }
      }
    }
    return stringBuilder.toString();
  }
  
  @Pure
  public String getEncoding() {
    return this.encoding;
  }
  
  public void setEncoding(final String encoding) {
    this.encoding = encoding;
  }
  
  @Pure
  public String getLineDelimiter() {
    return this.lineDelimiter;
  }
  
  public void setLineDelimiter(final String lineDelimiter) {
    this.lineDelimiter = lineDelimiter;
  }
  
  @Pure
  public String getFileHeader() {
    return this.fileHeader;
  }
  
  @Pure
  public List<IClassAnnotation> getClassAnnotations() {
    return this.classAnnotations;
  }
  
  @Pure
  public boolean isPreferXtendStubs() {
    return this.preferXtendStubs;
  }
  
  public void setPreferXtendStubs(final boolean preferXtendStubs) {
    this.preferXtendStubs = preferXtendStubs;
  }
  
  @Pure
  public XtextVersion getXtextVersion() {
    return this.xtextVersion;
  }
}