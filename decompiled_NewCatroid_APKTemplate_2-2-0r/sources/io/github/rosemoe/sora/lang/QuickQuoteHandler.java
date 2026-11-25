package io.github.rosemoe.sora.lang;

import io.github.rosemoe.sora.lang.styling.Styles;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.TextRange;

/* JADX INFO: loaded from: classes11.dex */
public interface QuickQuoteHandler {
    HandleResult onHandleTyping(String str, Content content, TextRange textRange, Styles styles);

    public static class HandleResult {
        public static final HandleResult NOT_CONSUMED = new HandleResult(false, null);
        private boolean consumed;
        private TextRange newCursorRange;

        public HandleResult(boolean z, TextRange textRange) {
            this.consumed = z;
            this.newCursorRange = textRange;
        }

        public boolean isConsumed() {
            return this.consumed;
        }

        public void setConsumed(boolean z) {
            this.consumed = z;
        }

        public TextRange getNewCursorRange() {
            return this.newCursorRange;
        }

        public void setNewCursorRange(TextRange textRange) {
            this.newCursorRange = textRange;
        }
    }
}
