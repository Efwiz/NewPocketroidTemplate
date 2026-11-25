package io.github.rosemoe.sora.lang.completion;

import android.graphics.drawable.Drawable;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.widget.CodeEditor;

/* JADX INFO: loaded from: classes11.dex */
public class SimpleSnippetCompletionItem extends CompletionItem {
    private final SnippetDescription snippet;

    @Override // io.github.rosemoe.sora.lang.completion.CompletionItem
    public void performCompletion(CodeEditor codeEditor, Content content, int i, int i2) {
    }

    public SimpleSnippetCompletionItem(CharSequence charSequence, SnippetDescription snippetDescription) {
        this(charSequence, null, snippetDescription);
    }

    public SimpleSnippetCompletionItem(CharSequence charSequence, CharSequence charSequence2, SnippetDescription snippetDescription) {
        this(charSequence, charSequence2, null, snippetDescription);
    }

    public SimpleSnippetCompletionItem(CharSequence charSequence, CharSequence charSequence2, Drawable drawable, SnippetDescription snippetDescription) {
        super(charSequence, charSequence2, drawable);
        this.snippet = snippetDescription;
        kind(CompletionItemKind.Snippet);
    }

    @Override // io.github.rosemoe.sora.lang.completion.CompletionItem
    public void performCompletion(CodeEditor codeEditor, Content content, CharPosition charPosition) {
        int selectedLength = this.snippet.getSelectedLength();
        String string = content.subSequence(charPosition.index - selectedLength, charPosition.index).toString();
        int i = charPosition.index;
        if (this.snippet.getDeleteSelected()) {
            content.delete(charPosition.index - selectedLength, charPosition.index);
            i -= selectedLength;
        }
        codeEditor.getSnippetController().startSnippet(i, this.snippet.getSnippet(), string);
    }
}
