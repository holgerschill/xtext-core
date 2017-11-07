/*
 * generated by Xtext
 */
package org.eclipse.xtext.testlanguages.backtracking.ide;

import org.eclipse.xtext.testlanguages.backtracking.SimpleBeeLangTestLanguageRuntimeModule;
import org.eclipse.xtext.testlanguages.backtracking.SimpleBeeLangTestLanguageStandaloneSetup;
import org.eclipse.xtext.util.Modules2;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
public class SimpleBeeLangTestLanguageIdeSetup extends SimpleBeeLangTestLanguageStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new SimpleBeeLangTestLanguageRuntimeModule(), new SimpleBeeLangTestLanguageIdeModule()));
	}
}
