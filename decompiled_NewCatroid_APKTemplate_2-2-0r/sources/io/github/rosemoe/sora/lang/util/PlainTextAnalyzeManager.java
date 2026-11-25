package io.github.rosemoe.sora.lang.util;

import io.github.rosemoe.sora.lang.analysis.StyleReceiver;
import io.github.rosemoe.sora.lang.styling.Styles;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.ContentReference;

/* JADX INFO: loaded from: classes11.dex */
public final class PlainTextAnalyzeManager extends BaseAnalyzeManager {
    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void delete(CharPosition charPosition, CharPosition charPosition2, CharSequence charSequence) {
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void insert(CharPosition charPosition, CharPosition charPosition2, CharSequence charSequence) {
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void rerun() {
        StyleReceiver receiver = getReceiver();
        ContentReference contentRef = getContentRef();
        if (receiver == null || contentRef == null) {
            if (receiver != null) {
                receiver.setStyles(this, null);
            }
        } else {
            Styles styles = new Styles();
            styles.spans = new PlainTextSpans(contentRef.getLineCount());
            receiver.setStyles(this, styles);
        }
    }
}
