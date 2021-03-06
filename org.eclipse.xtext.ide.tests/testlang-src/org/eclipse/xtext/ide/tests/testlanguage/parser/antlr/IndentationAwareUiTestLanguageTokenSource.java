/*
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.ide.tests.testlanguage.parser.antlr;

import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.eclipse.xtext.ide.tests.testlanguage.parser.antlr.internal.InternalIndentationAwareUiTestLanguageParser;
import org.eclipse.xtext.parser.antlr.AbstractIndentationTokenSource;

public class IndentationAwareUiTestLanguageTokenSource extends AbstractIndentationTokenSource {

	public IndentationAwareUiTestLanguageTokenSource(TokenSource delegate) {
		super(delegate);
	}

	@Override
	protected boolean shouldSplitTokenImpl(Token token) {
		// TODO Review assumption
		return token.getType() == InternalIndentationAwareUiTestLanguageParser.RULE_WS;
	}

	@Override
	protected int getBeginTokenType() {
		// TODO Review assumption
		return InternalIndentationAwareUiTestLanguageParser.RULE_INDENT;
	}

	@Override
	protected int getEndTokenType() {
		// TODO Review assumption
		return InternalIndentationAwareUiTestLanguageParser.RULE_DEDENT;
	}

}
