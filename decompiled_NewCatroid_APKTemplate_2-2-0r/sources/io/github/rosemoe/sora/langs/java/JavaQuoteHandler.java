package io.github.rosemoe.sora.langs.java;

import io.github.rosemoe.sora.lang.QuickQuoteHandler;
import io.github.rosemoe.sora.lang.styling.Styles;
import io.github.rosemoe.sora.lang.styling.StylesUtils;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.TextRange;

/* JADX INFO: loaded from: classes11.dex */
public class JavaQuoteHandler implements QuickQuoteHandler {
    @Override // io.github.rosemoe.sora.lang.QuickQuoteHandler
    public QuickQuoteHandler.HandleResult onHandleTyping(String str, Content content, TextRange textRange, Styles styles) {
        if (!StylesUtils.checkNoCompletion(styles, textRange.getStart()) && !StylesUtils.checkNoCompletion(styles, textRange.getEnd()) && "\"".equals(str) && textRange.getStart().line == textRange.getEnd().line) {
            content.insert(textRange.getStart().line, textRange.getStart().column, "\"");
            content.insert(textRange.getEnd().line, textRange.getEnd().column + 1, "\"");
            return new QuickQuoteHandler.HandleResult(true, new TextRange(content.getIndexer().getCharPosition(textRange.getStartIndex() + 1), content.getIndexer().getCharPosition(textRange.getEndIndex() + 1)));
        }
        return QuickQuoteHandler.HandleResult.NOT_CONSUMED;
    }
}
