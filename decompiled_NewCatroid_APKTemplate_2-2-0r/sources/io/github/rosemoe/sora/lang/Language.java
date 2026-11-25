package io.github.rosemoe.sora.lang;

import android.os.Bundle;
import io.github.rosemoe.sora.lang.analysis.AnalyzeManager;
import io.github.rosemoe.sora.lang.completion.CompletionCancelledException;
import io.github.rosemoe.sora.lang.completion.CompletionPublisher;
import io.github.rosemoe.sora.lang.format.Formatter;
import io.github.rosemoe.sora.lang.smartEnter.NewlineHandler;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.ContentReference;
import io.github.rosemoe.sora.widget.SymbolPairMatch;

/* JADX INFO: loaded from: classes11.dex */
public interface Language {
    public static final int INTERRUPTION_LEVEL_NONE = 2;
    public static final int INTERRUPTION_LEVEL_SLIGHT = 1;
    public static final int INTERRUPTION_LEVEL_STRONG = 0;

    void destroy();

    AnalyzeManager getAnalyzeManager();

    Formatter getFormatter();

    int getIndentAdvance(ContentReference contentReference, int i, int i2);

    int getInterruptionLevel();

    NewlineHandler[] getNewlineHandlers();

    default QuickQuoteHandler getQuickQuoteHandler() {
        return null;
    }

    SymbolPairMatch getSymbolPairs();

    void requireAutoComplete(ContentReference contentReference, CharPosition charPosition, CompletionPublisher completionPublisher, Bundle bundle) throws CompletionCancelledException;

    boolean useTab();
}
