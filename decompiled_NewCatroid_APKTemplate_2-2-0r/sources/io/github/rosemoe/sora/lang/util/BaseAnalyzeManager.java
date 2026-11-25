package io.github.rosemoe.sora.lang.util;

import android.os.Bundle;
import io.github.rosemoe.sora.lang.analysis.AnalyzeManager;
import io.github.rosemoe.sora.lang.analysis.StyleReceiver;
import io.github.rosemoe.sora.text.ContentReference;

/* JADX INFO: loaded from: classes11.dex */
public abstract class BaseAnalyzeManager implements AnalyzeManager {
    private ContentReference contentRef;
    private Bundle extraArguments;
    private StyleReceiver receiver;

    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void setReceiver(StyleReceiver styleReceiver) {
        this.receiver = styleReceiver;
    }

    public StyleReceiver getReceiver() {
        return this.receiver;
    }

    public Bundle getExtraArguments() {
        return this.extraArguments;
    }

    public ContentReference getContentRef() {
        return this.contentRef;
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void reset(ContentReference contentReference, Bundle bundle) {
        this.extraArguments = bundle;
        this.contentRef = contentReference;
        rerun();
    }

    @Override // io.github.rosemoe.sora.lang.analysis.AnalyzeManager
    public void destroy() {
        this.receiver = null;
        this.contentRef = null;
        this.extraArguments = null;
    }
}
