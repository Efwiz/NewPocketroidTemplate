package io.github.rosemoe.sora.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import io.github.rosemoe.sora.R;

/* JADX INFO: loaded from: classes11.dex */
public class SymbolInputView extends LinearLayout {
    private CodeEditor editor;
    private int textColor;

    public interface ButtonConsumer {
        void accept(Button button);
    }

    public SymbolInputView(Context context) {
        super(context);
        init();
    }

    public SymbolInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public SymbolInputView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public SymbolInputView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }

    private void init() {
        setBackgroundColor(getContext().getResources().getColor(R.color.defaultSymbolInputBackgroundColor));
        setOrientation(0);
        setTextColor(getContext().getResources().getColor(R.color.defaultSymbolInputTextColor));
    }

    public void bindEditor(CodeEditor codeEditor) {
        this.editor = codeEditor;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public void setTextColor(int i) {
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            ((Button) getChildAt(i2)).setTextColor(i);
        }
        this.textColor = i;
    }

    public void removeSymbols() {
        removeAllViews();
    }

    public void addSymbols(String[] strArr, final String[] strArr2) {
        int iMax = Math.max(strArr.length, strArr2.length);
        for (final int i = 0; i < iMax; i++) {
            Button button = new Button(getContext(), null, android.R.attr.buttonStyleSmall);
            button.setText(strArr[i]);
            button.setBackground(new ColorDrawable(0));
            button.setTextColor(this.textColor);
            addView(button, new LinearLayout.LayoutParams(-2, -1));
            button.setOnClickListener(new View.OnClickListener() { // from class: io.github.rosemoe.sora.widget.SymbolInputView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$addSymbols$0(strArr2, i, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addSymbols$0(String[] strArr, int i, View view) {
        CodeEditor codeEditor = this.editor;
        if (codeEditor == null || !codeEditor.isEditable()) {
            return;
        }
        if ("\t".equals(strArr[i])) {
            if (this.editor.getSnippetController().isInSnippet()) {
                this.editor.getSnippetController().shiftToNextTabStop();
                return;
            } else {
                this.editor.indentOrCommitTab();
                return;
            }
        }
        this.editor.insertText(strArr[i], 1);
    }

    public void forEachButton(ButtonConsumer buttonConsumer) {
        for (int i = 0; i < getChildCount(); i++) {
            buttonConsumer.accept((Button) getChildAt(i));
        }
    }
}
