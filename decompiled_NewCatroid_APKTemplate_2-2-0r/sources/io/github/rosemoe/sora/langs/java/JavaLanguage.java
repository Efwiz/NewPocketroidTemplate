package io.github.rosemoe.sora.langs.java;

import android.os.Bundle;
import io.github.rosemoe.sora.lang.EmptyLanguage;
import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.lang.QuickQuoteHandler;
import io.github.rosemoe.sora.lang.analysis.AnalyzeManager;
import io.github.rosemoe.sora.lang.completion.CompletionHelper;
import io.github.rosemoe.sora.lang.completion.CompletionPublisher;
import io.github.rosemoe.sora.lang.completion.IdentifierAutoComplete;
import io.github.rosemoe.sora.lang.completion.SimpleSnippetCompletionItem;
import io.github.rosemoe.sora.lang.completion.SnippetDescription;
import io.github.rosemoe.sora.lang.completion.snippet.CodeSnippet;
import io.github.rosemoe.sora.lang.completion.snippet.parser.CodeSnippetParser;
import io.github.rosemoe.sora.lang.format.Formatter;
import io.github.rosemoe.sora.lang.smartEnter.NewlineHandleResult;
import io.github.rosemoe.sora.lang.smartEnter.NewlineHandler;
import io.github.rosemoe.sora.lang.styling.Styles;
import io.github.rosemoe.sora.lang.styling.StylesUtils;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentLine;
import io.github.rosemoe.sora.text.ContentReference;
import io.github.rosemoe.sora.text.TextUtils;
import io.github.rosemoe.sora.util.MyCharacter;
import io.github.rosemoe.sora.widget.SymbolPairMatch;

/* JADX INFO: loaded from: classes11.dex */
public class JavaLanguage implements Language {
    private static final CodeSnippet FOR_SNIPPET = CodeSnippetParser.parse("for(int ${1:i} = 0;$1 < ${2:count};$1++) {\n    $0\n}");
    private static final CodeSnippet STATIC_CONST_SNIPPET = CodeSnippetParser.parse("private final static ${1:type} ${2/(.*)/${1:/upcase}/} = ${3:value};");
    private static final CodeSnippet CLIPBOARD_SNIPPET = CodeSnippetParser.parse("${1:${CLIPBOARD}}");
    private final JavaQuoteHandler javaQuoteHandler = new JavaQuoteHandler();
    private final NewlineHandler[] newlineHandlers = {new BraceHandler()};
    private IdentifierAutoComplete autoComplete = new IdentifierAutoComplete(JavaTextTokenizer.sKeywords);
    private final JavaIncrementalAnalyzeManager manager = new JavaIncrementalAnalyzeManager();

    @Override // io.github.rosemoe.sora.lang.Language
    public int getInterruptionLevel() {
        return 0;
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public boolean useTab() {
        return false;
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public AnalyzeManager getAnalyzeManager() {
        return this.manager;
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public QuickQuoteHandler getQuickQuoteHandler() {
        return this.javaQuoteHandler;
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public void destroy() {
        this.autoComplete = null;
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public void requireAutoComplete(ContentReference contentReference, CharPosition charPosition, CompletionPublisher completionPublisher, Bundle bundle) {
        String strComputePrefix = CompletionHelper.computePrefix(contentReference, charPosition, new CompletionHelper.PrefixChecker() { // from class: io.github.rosemoe.sora.langs.java.JavaLanguage$$ExternalSyntheticLambda0
            @Override // io.github.rosemoe.sora.lang.completion.CompletionHelper.PrefixChecker
            public final boolean check(char c) {
                return MyCharacter.isJavaIdentifierPart(c);
            }
        });
        IdentifierAutoComplete.SyncIdentifiers syncIdentifiers = this.manager.identifiers;
        if (syncIdentifiers != null) {
            this.autoComplete.requireAutoComplete(contentReference, charPosition, strComputePrefix, completionPublisher, syncIdentifiers);
        }
        if ("fori".startsWith(strComputePrefix) && strComputePrefix.length() > 0) {
            completionPublisher.addItem(new SimpleSnippetCompletionItem("fori", "Snippet - For loop on index", new SnippetDescription(strComputePrefix.length(), FOR_SNIPPET, true)));
        }
        if ("sconst".startsWith(strComputePrefix) && strComputePrefix.length() > 0) {
            completionPublisher.addItem(new SimpleSnippetCompletionItem("sconst", "Snippet - Static Constant", new SnippetDescription(strComputePrefix.length(), STATIC_CONST_SNIPPET, true)));
        }
        if (!"clip".startsWith(strComputePrefix) || strComputePrefix.length() <= 0) {
            return;
        }
        completionPublisher.addItem(new SimpleSnippetCompletionItem("clip", "Snippet - Clipboard contents", new SnippetDescription(strComputePrefix.length(), CLIPBOARD_SNIPPET, true)));
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public int getIndentAdvance(ContentReference contentReference, int i, int i2) {
        return getIndentAdvance(contentReference.getLine(i).substring(0, i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getIndentAdvance(String str) {
        JavaTextTokenizer javaTextTokenizer = new JavaTextTokenizer(str);
        int i = 0;
        while (true) {
            Tokens tokensNextToken = javaTextTokenizer.nextToken();
            if (tokensNextToken != Tokens.EOF) {
                if (tokensNextToken == Tokens.LBRACE) {
                    i++;
                }
            } else {
                return Math.max(0, i) * 4;
            }
        }
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public Formatter getFormatter() {
        return EmptyLanguage.EmptyFormatter.INSTANCE;
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public SymbolPairMatch getSymbolPairs() {
        return new SymbolPairMatch.DefaultSymbolPairs();
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public NewlineHandler[] getNewlineHandlers() {
        return this.newlineHandlers;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getNonEmptyTextBefore(CharSequence charSequence, int i, int i2) {
        while (i > 0 && Character.isWhitespace(charSequence.charAt(i - 1))) {
            i--;
        }
        return charSequence.subSequence(Math.max(0, i - i2), i).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getNonEmptyTextAfter(CharSequence charSequence, int i, int i2) {
        while (i < charSequence.length() && Character.isWhitespace(charSequence.charAt(i))) {
            i++;
        }
        return charSequence.subSequence(i, Math.min(i2 + i, charSequence.length())).toString();
    }

    class BraceHandler implements NewlineHandler {
        BraceHandler() {
        }

        @Override // io.github.rosemoe.sora.lang.smartEnter.NewlineHandler
        public boolean matchesRequirement(Content content, CharPosition charPosition, Styles styles) {
            ContentLine line = content.getLine(charPosition.line);
            return !StylesUtils.checkNoCompletion(styles, charPosition) && JavaLanguage.getNonEmptyTextBefore(line, charPosition.column, 1).equals("{") && JavaLanguage.getNonEmptyTextAfter(line, charPosition.column, 1).equals("}");
        }

        @Override // io.github.rosemoe.sora.lang.smartEnter.NewlineHandler
        public NewlineHandleResult handleNewline(Content content, CharPosition charPosition, Styles styles, int i) {
            ContentLine line = content.getLine(charPosition.line);
            int i2 = charPosition.column;
            return handleNewline(line.subSequence(0, i2).toString(), line.subSequence(i2, line.length()).toString(), i);
        }

        public NewlineHandleResult handleNewline(String str, String str2, int i) {
            int iCountLeadingSpaceCount = TextUtils.countLeadingSpaceCount(str, i);
            int indentAdvance = JavaLanguage.this.getIndentAdvance(str);
            int indentAdvance2 = JavaLanguage.this.getIndentAdvance(str2);
            StringBuilder sbAppend = new StringBuilder("\n").append(TextUtils.createIndent(indentAdvance + iCountLeadingSpaceCount, i, JavaLanguage.this.useTab())).append('\n');
            String strCreateIndent = TextUtils.createIndent(iCountLeadingSpaceCount + indentAdvance2, i, JavaLanguage.this.useTab());
            return new NewlineHandleResult(sbAppend.append(strCreateIndent), strCreateIndent.length() + 1);
        }
    }
}
