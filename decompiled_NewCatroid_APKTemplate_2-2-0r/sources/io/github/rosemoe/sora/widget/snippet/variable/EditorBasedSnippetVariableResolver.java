package io.github.rosemoe.sora.widget.snippet.variable;

import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.Cursor;
import io.github.rosemoe.sora.text.ICUUtils;
import io.github.rosemoe.sora.util.IntPair;
import io.github.rosemoe.sora.widget.CodeEditor;

/* JADX INFO: loaded from: classes11.dex */
public class EditorBasedSnippetVariableResolver implements ISnippetVariableResolver {
    private final CodeEditor editor;

    public EditorBasedSnippetVariableResolver(CodeEditor codeEditor) {
        this.editor = codeEditor;
    }

    @Override // io.github.rosemoe.sora.widget.snippet.variable.ISnippetVariableResolver
    public String[] getResolvableNames() {
        return new String[]{"TM_CURRENT_LINE", "TM_LINE_INDEX", "TM_LINE_NUMBER", "CURSOR_INDEX", "CURSOR_NUMBER", "TM_CURRENT_WORD", "SELECTION", "TM_SELECTED_TEXT"};
    }

    @Override // io.github.rosemoe.sora.widget.snippet.variable.ISnippetVariableResolver
    public String resolve(String str) {
        str.hashCode();
        switch (str) {
            case "TM_CURRENT_LINE":
            case "TM_LINE_NUMBER":
                return Integer.toString(this.editor.getCursor().getLeftLine() + 1);
            case "TM_CURRENT_WORD":
                Content text = this.editor.getText();
                long wordRange = ICUUtils.getWordRange(text.getLine(text.getCursor().getLeftLine()), text.getCursor().getLeftColumn(), true);
                return text.getLine(text.getCursor().getLeftLine()).subSequence(IntPair.getFirst(wordRange), IntPair.getSecond(wordRange)).toString();
            case "TM_SELECTED_TEXT":
            case "SELECTION":
                Cursor cursor = this.editor.getCursor();
                return this.editor.getText().subSequence(cursor.getLeft(), cursor.getRight()).toString();
            case "CURSOR_NUMBER":
                return Integer.toString(this.editor.getCursor().getLeft() + 1);
            case "TM_LINE_INDEX":
                return Integer.toString(this.editor.getCursor().getLeftLine());
            case "CURSOR_INDEX":
                return Integer.toString(this.editor.getCursor().getLeft());
            default:
                throw new IllegalArgumentException("Unsupported variable name:" + str);
        }
    }
}
