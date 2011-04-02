/*******************************************************************************
 * Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.formatting;

import java.util.Collection;

import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.formatting.impl.ElementMatcherProvider;
import org.eclipse.xtext.util.Pair;

import com.google.common.base.Predicate;
import com.google.inject.ImplementedBy;

/**
 * @author Moritz Eysholdt - Initial contribution and API
 */
@ImplementedBy(ElementMatcherProvider.class)
public interface IElementMatcherProvider {

	public interface IAfterElement extends IElementPattern {
		AbstractElement matchAfter();
	}

	public interface IBeforeElement extends IElementPattern {
		AbstractElement matchBefore();
	}

	public interface IBetweenElements extends IElementPattern {
		Pair<AbstractElement, AbstractElement> matchBetween();
	}

	public interface IElementMatcher<T extends IElementPattern> {
		Pair<Integer, RuleCall> findTopmostRuleCall(Predicate<RuleCall> predicate);

		Collection<T> finish();

		Collection<T> matchNext(AbstractElement nextElement);
	}

	/**
	 * @since 2.0
	 */
	public interface IElementMatcherEx1<T extends IElementPattern> extends IElementMatcher<T> {
		void init(ParserRule rule);
	}

	public interface IElementPattern {
	}

	<T extends IElementPattern> IElementMatcher<T> createMatcher(Iterable<T> rules);

}
