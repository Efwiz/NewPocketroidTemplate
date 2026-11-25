package io.github.rosemoe.sora.lang.smartEnter;

/* JADX INFO: loaded from: classes11.dex */
public class NewlineHandleResult {
    public final int shiftLeft;
    public final CharSequence text;

    public NewlineHandleResult(CharSequence charSequence, int i) {
        this.text = charSequence;
        this.shiftLeft = i;
        if (i < 0 || i > charSequence.length()) {
            throw new IllegalArgumentException("invalid shiftLeft");
        }
    }
}
