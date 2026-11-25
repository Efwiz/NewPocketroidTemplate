package io.github.rosemoe.sora.lang.completion;

import android.graphics.drawable.Drawable;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.widget.CodeEditor;

/* JADX INFO: loaded from: classes11.dex */
public class SimpleCompletionItem extends CompletionItem {
    public String commitText;

    public SimpleCompletionItem(int i, String str) {
        this(str, i, str);
    }

    public SimpleCompletionItem(CharSequence charSequence, int i, String str) {
        this(charSequence, null, i, str);
    }

    public SimpleCompletionItem(CharSequence charSequence, CharSequence charSequence2, int i, String str) {
        this(charSequence, charSequence2, null, i, str);
    }

    public SimpleCompletionItem(CharSequence charSequence, CharSequence charSequence2, Drawable drawable, int i, String str) {
        super(charSequence, charSequence2, drawable);
        this.commitText = str;
        this.prefixLength = i;
    }

    @Override // io.github.rosemoe.sora.lang.completion.CompletionItem
    public SimpleCompletionItem desc(CharSequence charSequence) {
        super.desc(charSequence);
        return this;
    }

    @Override // io.github.rosemoe.sora.lang.completion.CompletionItem
    public SimpleCompletionItem icon(Drawable drawable) {
        super.icon(drawable);
        return this;
    }

    @Override // io.github.rosemoe.sora.lang.completion.CompletionItem
    public SimpleCompletionItem label(CharSequence charSequence) {
        super.label(charSequence);
        return this;
    }

    @Override // io.github.rosemoe.sora.lang.completion.CompletionItem
    public SimpleCompletionItem kind(CompletionItemKind completionItemKind) {
        super.kind(completionItemKind);
        if (this.icon == null) {
            this.icon = SimpleCompletionIconDrawer.draw(completionItemKind);
        }
        return this;
    }

    public SimpleCompletionItem commit(int i, String str) {
        this.prefixLength = i;
        this.commitText = str;
        return this;
    }

    @Override // io.github.rosemoe.sora.lang.completion.CompletionItem
    public void performCompletion(CodeEditor codeEditor, Content content, int i, int i2) {
        if (this.commitText == null) {
            return;
        }
        if (this.prefixLength == 0) {
            content.insert(i, i2, this.commitText);
        } else {
            content.replace(i, i2 - this.prefixLength, i, i2, this.commitText);
        }
    }
}
