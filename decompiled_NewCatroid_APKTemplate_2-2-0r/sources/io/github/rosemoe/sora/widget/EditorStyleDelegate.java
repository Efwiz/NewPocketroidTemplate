package io.github.rosemoe.sora.widget;

import android.os.Looper;
import io.github.rosemoe.sora.event.Event;
import io.github.rosemoe.sora.event.EventReceiver;
import io.github.rosemoe.sora.event.SelectionChangeEvent;
import io.github.rosemoe.sora.event.Unsubscribe;
import io.github.rosemoe.sora.lang.analysis.AnalyzeManager;
import io.github.rosemoe.sora.lang.analysis.StyleReceiver;
import io.github.rosemoe.sora.lang.analysis.StyleUpdateRange;
import io.github.rosemoe.sora.lang.brackets.BracketsProvider;
import io.github.rosemoe.sora.lang.brackets.PairedBracket;
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticsContainer;
import io.github.rosemoe.sora.lang.styling.Styles;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes11.dex */
public class EditorStyleDelegate implements StyleReceiver {
    private BracketsProvider bracketsProvider;
    private final WeakReference<CodeEditor> editorRef;
    private PairedBracket foundPair;

    void onTextChange() {
    }

    EditorStyleDelegate(CodeEditor codeEditor) {
        this.editorRef = new WeakReference<>(codeEditor);
        codeEditor.subscribeEvent(SelectionChangeEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.EditorStyleDelegate$$ExternalSyntheticLambda1
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                this.f$0.lambda$new$0((SelectionChangeEvent) event, unsubscribe);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(SelectionChangeEvent selectionChangeEvent, Unsubscribe unsubscribe) {
        if (selectionChangeEvent.isSelected()) {
            return;
        }
        postUpdateBracketPair();
    }

    void postUpdateBracketPair() {
        runOnUiThread(new Runnable() { // from class: io.github.rosemoe.sora.widget.EditorStyleDelegate$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$postUpdateBracketPair$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postUpdateBracketPair$1() {
        BracketsProvider bracketsProvider = this.bracketsProvider;
        CodeEditor codeEditor = this.editorRef.get();
        if (bracketsProvider == null || codeEditor == null || codeEditor.getCursor().isSelected() || !codeEditor.isHighlightBracketPair()) {
            return;
        }
        this.foundPair = bracketsProvider.getPairedBracketAt(codeEditor.getText(), codeEditor.getCursor().getLeft());
        codeEditor.invalidate();
    }

    public PairedBracket getFoundBracketPair() {
        return this.foundPair;
    }

    void reset() {
        this.foundPair = null;
        this.bracketsProvider = null;
    }

    private void runOnUiThread(Runnable runnable) {
        CodeEditor codeEditor = this.editorRef.get();
        if (codeEditor == null) {
            return;
        }
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
        } else {
            codeEditor.postInLifecycle(runnable);
        }
    }

    @Override // io.github.rosemoe.sora.lang.analysis.StyleReceiver
    public void setStyles(AnalyzeManager analyzeManager, Styles styles) {
        setStyles(analyzeManager, styles, null);
    }

    @Override // io.github.rosemoe.sora.lang.analysis.StyleReceiver
    public void setStyles(AnalyzeManager analyzeManager, final Styles styles, final Runnable runnable) {
        final CodeEditor codeEditor = this.editorRef.get();
        if (codeEditor == null || analyzeManager != codeEditor.getEditorLanguage().getAnalyzeManager()) {
            return;
        }
        runOnUiThread(new Runnable() { // from class: io.github.rosemoe.sora.widget.EditorStyleDelegate$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                EditorStyleDelegate.lambda$setStyles$2(runnable, codeEditor, styles);
            }
        });
    }

    static /* synthetic */ void lambda$setStyles$2(Runnable runnable, CodeEditor codeEditor, Styles styles) {
        if (runnable != null) {
            runnable.run();
        }
        codeEditor.setStyles(styles);
    }

    @Override // io.github.rosemoe.sora.lang.analysis.StyleReceiver
    public void setDiagnostics(AnalyzeManager analyzeManager, final DiagnosticsContainer diagnosticsContainer) {
        final CodeEditor codeEditor = this.editorRef.get();
        if (codeEditor == null || analyzeManager != codeEditor.getEditorLanguage().getAnalyzeManager()) {
            return;
        }
        runOnUiThread(new Runnable() { // from class: io.github.rosemoe.sora.widget.EditorStyleDelegate$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                codeEditor.setDiagnostics(diagnosticsContainer);
            }
        });
    }

    @Override // io.github.rosemoe.sora.lang.analysis.StyleReceiver
    public void updateBracketProvider(AnalyzeManager analyzeManager, BracketsProvider bracketsProvider) {
        CodeEditor codeEditor = this.editorRef.get();
        if (codeEditor == null || analyzeManager != codeEditor.getEditorLanguage().getAnalyzeManager() || this.bracketsProvider == bracketsProvider) {
            return;
        }
        this.bracketsProvider = bracketsProvider;
        postUpdateBracketPair();
    }

    @Override // io.github.rosemoe.sora.lang.analysis.StyleReceiver
    public void updateStyles(AnalyzeManager analyzeManager, final Styles styles, final StyleUpdateRange styleUpdateRange) {
        final CodeEditor codeEditor = this.editorRef.get();
        if (codeEditor == null || analyzeManager != codeEditor.getEditorLanguage().getAnalyzeManager()) {
            return;
        }
        runOnUiThread(new Runnable() { // from class: io.github.rosemoe.sora.widget.EditorStyleDelegate$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                codeEditor.updateStyles(styles, styleUpdateRange);
            }
        });
    }

    public void clearFoundBracketPair() {
        this.foundPair = null;
    }
}
