package io.github.rosemoe.sora.lang.smartEnter;

import io.github.rosemoe.sora.lang.styling.Styles;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;

/* JADX INFO: loaded from: classes11.dex */
public interface NewlineHandler {
    NewlineHandleResult handleNewline(Content content, CharPosition charPosition, Styles styles, int i);

    boolean matchesRequirement(Content content, CharPosition charPosition, Styles styles);
}
