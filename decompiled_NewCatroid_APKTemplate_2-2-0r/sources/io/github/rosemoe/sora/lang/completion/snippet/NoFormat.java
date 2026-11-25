package io.github.rosemoe.sora.lang.completion.snippet;

/* JADX INFO: loaded from: classes11.dex */
public class NoFormat implements FormatString {
    private String text;

    public NoFormat(String str) {
        setText(str);
    }

    public void setText(String str) {
        this.text = str;
    }

    public String getText() {
        return this.text;
    }
}
