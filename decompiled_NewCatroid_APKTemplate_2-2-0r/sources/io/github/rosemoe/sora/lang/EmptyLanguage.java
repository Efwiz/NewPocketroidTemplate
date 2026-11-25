package io.github.rosemoe.sora.lang;

import android.os.Bundle;
import io.github.rosemoe.sora.lang.analysis.AnalyzeManager;
import io.github.rosemoe.sora.lang.completion.CompletionPublisher;
import io.github.rosemoe.sora.lang.format.Formatter;
import io.github.rosemoe.sora.lang.smartEnter.NewlineHandler;
import io.github.rosemoe.sora.lang.util.BaseAnalyzeManager;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentReference;
import io.github.rosemoe.sora.text.TextRange;
import io.github.rosemoe.sora.widget.SymbolPairMatch;

/* JADX INFO: loaded from: classes11.dex */
public class EmptyLanguage implements Language {
    public static final SymbolPairMatch EMPTY_SYMBOL_PAIRS = new SymbolPairMatch();

    public static class EmptyAnalyzeManager extends BaseAnalyzeManager {
        public static final EmptyAnalyzeManager INSTANCE = new EmptyAnalyzeManager();

        @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
        public void delete(CharPosition charPosition, CharPosition charPosition2, CharSequence charSequence) {
        }

        @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
        public void insert(CharPosition charPosition, CharPosition charPosition2, CharSequence charSequence) {
        }

        @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
        public void rerun() {
        }
    }

    public static class EmptyFormatter implements Formatter {
        public static final EmptyFormatter INSTANCE = new EmptyFormatter();

        @Override // io.github.rosemoe.sora.lang.format.Formatter
        public void destroy() {
        }

        @Override // io.github.rosemoe.sora.lang.format.Formatter
        public void format(Content content, TextRange textRange) {
        }

        @Override // io.github.rosemoe.sora.lang.format.Formatter
        public void formatRegion(Content content, TextRange textRange, TextRange textRange2) {
        }

        @Override // io.github.rosemoe.sora.lang.format.Formatter
        public boolean isRunning() {
            return false;
        }

        @Override // io.github.rosemoe.sora.lang.format.Formatter
        public void setReceiver(Formatter.FormatResultReceiver formatResultReceiver) {
        }
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public void destroy() {
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public int getIndentAdvance(ContentReference contentReference, int i, int i2) {
        return 0;
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public int getInterruptionLevel() {
        return 0;
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public void requireAutoComplete(ContentReference contentReference, CharPosition charPosition, CompletionPublisher completionPublisher, Bundle bundle) {
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public boolean useTab() {
        return false;
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public Formatter getFormatter() {
        return EmptyFormatter.INSTANCE;
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public SymbolPairMatch getSymbolPairs() {
        return EMPTY_SYMBOL_PAIRS;
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public NewlineHandler[] getNewlineHandlers() {
        return new NewlineHandler[0];
    }

    @Override // io.github.rosemoe.sora.lang.Language
    public AnalyzeManager getAnalyzeManager() {
        return EmptyAnalyzeManager.INSTANCE;
    }
}
