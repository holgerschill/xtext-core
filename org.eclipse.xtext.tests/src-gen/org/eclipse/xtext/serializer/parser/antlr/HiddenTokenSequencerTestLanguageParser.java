/*
 * generated by Xtext
 */
package org.eclipse.xtext.serializer.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.serializer.parser.antlr.internal.InternalHiddenTokenSequencerTestLanguageParser;
import org.eclipse.xtext.serializer.services.HiddenTokenSequencerTestLanguageGrammarAccess;

public class HiddenTokenSequencerTestLanguageParser extends AbstractAntlrParser {

	@Inject
	private HiddenTokenSequencerTestLanguageGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalHiddenTokenSequencerTestLanguageParser createParser(XtextTokenStream stream) {
		return new InternalHiddenTokenSequencerTestLanguageParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "Model";
	}

	public HiddenTokenSequencerTestLanguageGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(HiddenTokenSequencerTestLanguageGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
