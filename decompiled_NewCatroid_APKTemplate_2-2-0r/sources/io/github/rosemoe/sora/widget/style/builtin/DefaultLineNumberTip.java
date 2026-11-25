package io.github.rosemoe.sora.widget.style.builtin;

import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.style.LineNumberTipTextProvider;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DefaultLineNumberTip.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lio/github/rosemoe/sora/widget/style/builtin/DefaultLineNumberTip;", "Lio/github/rosemoe/sora/widget/style/LineNumberTipTextProvider;", "()V", "getCurrentText", "", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultLineNumberTip implements LineNumberTipTextProvider {
    public static final DefaultLineNumberTip INSTANCE = new DefaultLineNumberTip();

    private DefaultLineNumberTip() {
    }

    @Override // io.github.rosemoe.sora.widget.style.LineNumberTipTextProvider
    public String getCurrentText(CodeEditor editor) {
        Intrinsics.checkNotNullParameter(editor, "editor");
        return "L" + (editor.getFirstVisibleLine() + 1);
    }
}
