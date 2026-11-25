package io.github.rosemoe.sora.widget.snippet;

import android.util.Log;
import io.github.rosemoe.sora.event.ContentChangeEvent;
import io.github.rosemoe.sora.event.Event;
import io.github.rosemoe.sora.event.EventReceiver;
import io.github.rosemoe.sora.event.SelectionChangeEvent;
import io.github.rosemoe.sora.event.SnippetEvent;
import io.github.rosemoe.sora.event.Unsubscribe;
import io.github.rosemoe.sora.lang.completion.snippet.CodeSnippet;
import io.github.rosemoe.sora.lang.completion.snippet.InterpolatedShellItem;
import io.github.rosemoe.sora.lang.completion.snippet.PlaceHolderElement;
import io.github.rosemoe.sora.lang.completion.snippet.PlaceholderDefinition;
import io.github.rosemoe.sora.lang.completion.snippet.PlaceholderItem;
import io.github.rosemoe.sora.lang.completion.snippet.PlainPlaceholderElement;
import io.github.rosemoe.sora.lang.completion.snippet.PlainTextItem;
import io.github.rosemoe.sora.lang.completion.snippet.SnippetItem;
import io.github.rosemoe.sora.lang.completion.snippet.VariableItem;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentLine;
import io.github.rosemoe.sora.text.Indexer;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;
import io.github.rosemoe.sora.widget.component.EditorBuiltinComponent;
import io.github.rosemoe.sora.widget.snippet.variable.ClipboardBasedSnippetVariableResolver;
import io.github.rosemoe.sora.widget.snippet.variable.CommentBasedSnippetVariableResolver;
import io.github.rosemoe.sora.widget.snippet.variable.CompositeSnippetVariableResolver;
import io.github.rosemoe.sora.widget.snippet.variable.EditorBasedSnippetVariableResolver;
import io.github.rosemoe.sora.widget.snippet.variable.FileBasedSnippetVariableResolver;
import io.github.rosemoe.sora.widget.snippet.variable.RandomBasedSnippetVariableResolver;
import io.github.rosemoe.sora.widget.snippet.variable.TimeBasedSnippetVariableResolver;
import io.github.rosemoe.sora.widget.snippet.variable.WorkspaceBasedSnippetVariableResolver;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: SnippetController.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 >2\u00020\u0001:\u0001>B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010&\u001a\u00020\u00152\u0006\u0010'\u001a\u00020\fH\u0002J\f\u0010(\u001a\b\u0012\u0004\u0012\u00020*0)J\b\u0010+\u001a\u0004\u0018\u00010\u001dJ\f\u0010,\u001a\b\u0012\u0004\u0012\u00020*0)J\u0010\u0010-\u001a\u0004\u0018\u00010\u001d2\u0006\u0010'\u001a\u00020\fJ\u0006\u0010.\u001a\u00020\fJ\u000e\u0010/\u001a\u00020\u00152\u0006\u00100\u001a\u00020*J\u0006\u00101\u001a\u00020\u0015J\u0018\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\f2\u0006\u00105\u001a\u00020\fH\u0002J\u0006\u00106\u001a\u000203J\u0006\u00107\u001a\u000203J\u0010\u00108\u001a\u0002032\u0006\u0010'\u001a\u00020\fH\u0002J \u00109\u001a\u0002032\u0006\u0010'\u001a\u00020\f2\u0006\u0010:\u001a\u00020\n2\b\b\u0002\u0010;\u001a\u00020<J\u0006\u0010=\u001a\u000203R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u000e@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0016\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010!\u001a\u0004\u0018\u00010 2\b\u0010\r\u001a\u0004\u0018\u00010 @FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%¨\u0006?"}, d2 = {"Lio/github/rosemoe/sora/widget/snippet/SnippetController;", "", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", "(Lio/github/rosemoe/sora/widget/CodeEditor;)V", "commentVariableResolver", "Lio/github/rosemoe/sora/widget/snippet/variable/CommentBasedSnippetVariableResolver;", "getCommentVariableResolver", "()Lio/github/rosemoe/sora/widget/snippet/variable/CommentBasedSnippetVariableResolver;", "currentSnippet", "Lio/github/rosemoe/sora/lang/completion/snippet/CodeSnippet;", "currentTabStopIndex", "", "value", "Lio/github/rosemoe/sora/widget/snippet/variable/FileBasedSnippetVariableResolver;", "fileVariableResolver", "getFileVariableResolver", "()Lio/github/rosemoe/sora/widget/snippet/variable/FileBasedSnippetVariableResolver;", "setFileVariableResolver", "(Lio/github/rosemoe/sora/widget/snippet/variable/FileBasedSnippetVariableResolver;)V", "inSequenceEdits", "", "snippetIndex", "getSnippetIndex", "()I", "setSnippetIndex", "(I)V", "tabStops", "", "Lio/github/rosemoe/sora/lang/completion/snippet/PlaceholderItem;", "variableResolver", "Lio/github/rosemoe/sora/widget/snippet/variable/CompositeSnippetVariableResolver;", "Lio/github/rosemoe/sora/widget/snippet/variable/WorkspaceBasedSnippetVariableResolver;", "workspaceVariableResolver", "getWorkspaceVariableResolver", "()Lio/github/rosemoe/sora/widget/snippet/variable/WorkspaceBasedSnippetVariableResolver;", "setWorkspaceVariableResolver", "(Lio/github/rosemoe/sora/widget/snippet/variable/WorkspaceBasedSnippetVariableResolver;)V", "checkIndex", "index", "getEditingRelatedTabStops", "", "Lio/github/rosemoe/sora/lang/completion/snippet/SnippetItem;", "getEditingTabStop", "getInactiveTabStops", "getTabStopAt", "getTabStopCount", "isEditingRelated", "it", "isInSnippet", "shiftItemsFrom", "", "itemIndex", "deltaIndex", "shiftToNextTabStop", "shiftToPreviousTabStop", "shiftToTabStop", "startSnippet", "snippet", "selectedText", "", "stopSnippet", "Companion", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SnippetController {
    private static final Regex lineSeparatorRegex = new Regex("\\r|\\n|\\r\\n");
    private final CommentBasedSnippetVariableResolver commentVariableResolver;
    private CodeSnippet currentSnippet;
    private int currentTabStopIndex;
    private final CodeEditor editor;
    private FileBasedSnippetVariableResolver fileVariableResolver;
    private boolean inSequenceEdits;
    private int snippetIndex;
    private List<PlaceholderItem> tabStops;
    private final CompositeSnippetVariableResolver variableResolver;
    private WorkspaceBasedSnippetVariableResolver workspaceVariableResolver;

    public SnippetController(CodeEditor editor) {
        Intrinsics.checkNotNullParameter(editor, "editor");
        this.editor = editor;
        CommentBasedSnippetVariableResolver commentBasedSnippetVariableResolver = new CommentBasedSnippetVariableResolver(null);
        this.commentVariableResolver = commentBasedSnippetVariableResolver;
        this.snippetIndex = -1;
        this.currentTabStopIndex = -1;
        CompositeSnippetVariableResolver compositeSnippetVariableResolver = new CompositeSnippetVariableResolver();
        compositeSnippetVariableResolver.addResolver(new ClipboardBasedSnippetVariableResolver(editor.getClipboardManager()));
        compositeSnippetVariableResolver.addResolver(new EditorBasedSnippetVariableResolver(editor));
        compositeSnippetVariableResolver.addResolver(new RandomBasedSnippetVariableResolver());
        compositeSnippetVariableResolver.addResolver(new TimeBasedSnippetVariableResolver());
        compositeSnippetVariableResolver.addResolver(commentBasedSnippetVariableResolver);
        this.variableResolver = compositeSnippetVariableResolver;
        Intrinsics.checkNotNullExpressionValue(editor.subscribeEvent(SelectionChangeEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.snippet.SnippetController$$ExternalSyntheticLambda1
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                SnippetController._init_$lambda$1(this.f$0, (SelectionChangeEvent) event, unsubscribe);
            }
        }), "subscribeEvent(...)");
        Intrinsics.checkNotNullExpressionValue(editor.subscribeEvent(ContentChangeEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.snippet.SnippetController$$ExternalSyntheticLambda2
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                SnippetController._init_$lambda$7(this.f$0, (ContentChangeEvent) event, unsubscribe);
            }
        }), "subscribeEvent(...)");
    }

    public final CommentBasedSnippetVariableResolver getCommentVariableResolver() {
        return this.commentVariableResolver;
    }

    public final FileBasedSnippetVariableResolver getFileVariableResolver() {
        return this.fileVariableResolver;
    }

    public final void setFileVariableResolver(FileBasedSnippetVariableResolver fileBasedSnippetVariableResolver) {
        FileBasedSnippetVariableResolver fileBasedSnippetVariableResolver2 = this.fileVariableResolver;
        if (fileBasedSnippetVariableResolver2 != null) {
            this.variableResolver.removeResolver(fileBasedSnippetVariableResolver2);
        }
        this.fileVariableResolver = fileBasedSnippetVariableResolver;
        if (fileBasedSnippetVariableResolver != null) {
            this.variableResolver.addResolver(fileBasedSnippetVariableResolver);
        }
    }

    public final WorkspaceBasedSnippetVariableResolver getWorkspaceVariableResolver() {
        return this.workspaceVariableResolver;
    }

    public final void setWorkspaceVariableResolver(WorkspaceBasedSnippetVariableResolver workspaceBasedSnippetVariableResolver) {
        WorkspaceBasedSnippetVariableResolver workspaceBasedSnippetVariableResolver2 = this.workspaceVariableResolver;
        if (workspaceBasedSnippetVariableResolver2 != null) {
            this.variableResolver.removeResolver(workspaceBasedSnippetVariableResolver2);
        }
        this.workspaceVariableResolver = workspaceBasedSnippetVariableResolver;
        if (workspaceBasedSnippetVariableResolver != null) {
            this.variableResolver.addResolver(workspaceBasedSnippetVariableResolver);
        }
    }

    public final int getSnippetIndex() {
        return this.snippetIndex;
    }

    public final void setSnippetIndex(int i) {
        this.snippetIndex = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$1(SnippetController this$0, SelectionChangeEvent event, Unsubscribe unsubscribe) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(unsubscribe, "<anonymous parameter 1>");
        if (this$0.isInSnippet()) {
            if (this$0.checkIndex(event.getLeft().index) && this$0.checkIndex(event.getRight().index)) {
                return;
            }
            this$0.stopSnippet();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$7(SnippetController this$0, ContentChangeEvent event, Unsubscribe unsubscribe) {
        int length;
        boolean z;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(unsubscribe, "<anonymous parameter 1>");
        if (!this$0.isInSnippet() || this$0.inSequenceEdits) {
            return;
        }
        if (event.getAction() == 1) {
            this$0.stopSnippet();
            return;
        }
        if (event.getAction() == 2) {
            if (this$0.checkIndex(event.getChangeStart().index)) {
                CharSequence changedText = event.getChangedText();
                Intrinsics.checkNotNullExpressionValue(changedText, "getChangedText(...)");
                if (lineSeparatorRegex.containsMatchIn(changedText)) {
                    CharSequence changedText2 = event.getChangedText();
                    Intrinsics.checkNotNullExpressionValue(changedText2, "getChangedText(...)");
                    int length2 = changedText2.length();
                    length = 0;
                    while (true) {
                        if (length >= length2) {
                            length = -1;
                            break;
                        }
                        char cCharAt = changedText2.charAt(length);
                        if (cCharAt == '\r' || cCharAt == '\n') {
                            break;
                        } else {
                            length++;
                        }
                    }
                    z = true;
                } else {
                    length = event.getChangedText().length();
                    z = false;
                }
                PlaceholderItem editingTabStop = this$0.getEditingTabStop();
                Intrinsics.checkNotNull(editingTabStop);
                editingTabStop.setIndex(editingTabStop.getStartIndex(), editingTabStop.getEndIndex() + length);
                CodeSnippet codeSnippet = this$0.currentSnippet;
                Intrinsics.checkNotNull(codeSnippet);
                List<SnippetItem> items = codeSnippet.getItems();
                Intrinsics.checkNotNullExpressionValue(items, "getItems(...)");
                boolean z2 = false;
                for (SnippetItem snippetItem : items) {
                    if (z2) {
                        snippetItem.shiftIndex(event.getChangedText().length());
                    } else if (Intrinsics.areEqual(snippetItem, editingTabStop)) {
                        z2 = true;
                    }
                }
                this$0.inSequenceEdits = true;
                Content text = this$0.editor.getText();
                Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                String strSubstring = text.substring(editingTabStop.getStartIndex(), editingTabStop.getEndIndex());
                text.beginBatchEdit();
                CodeSnippet codeSnippet2 = this$0.currentSnippet;
                Intrinsics.checkNotNull(codeSnippet2);
                List<SnippetItem> items2 = codeSnippet2.getItems();
                Intrinsics.checkNotNullExpressionValue(items2, "getItems(...)");
                int i = 0;
                boolean z3 = false;
                for (Object obj : items2) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    SnippetItem snippetItem2 = (SnippetItem) obj;
                    Intrinsics.checkNotNull(snippetItem2);
                    if (this$0.isEditingRelated(snippetItem2)) {
                        int length3 = strSubstring.length() - (snippetItem2.getEndIndex() - snippetItem2.getStartIndex());
                        text.replace(snippetItem2.getStartIndex(), snippetItem2.getEndIndex(), strSubstring);
                        snippetItem2.setIndex(snippetItem2.getStartIndex(), snippetItem2.getEndIndex() + length3);
                        this$0.shiftItemsFrom(i2, length3);
                        z3 = true;
                    }
                    i = i2;
                }
                text.endBatchEdit();
                this$0.inSequenceEdits = false;
                if (z) {
                    this$0.stopSnippet();
                    return;
                } else {
                    if (z3) {
                        EditorBuiltinComponent component = this$0.editor.getComponent(EditorAutoCompletion.class);
                        Intrinsics.checkNotNullExpressionValue(component, "getComponent(...)");
                        ((EditorAutoCompletion) component).requireCompletion();
                        return;
                    }
                    return;
                }
            }
            this$0.stopSnippet();
            return;
        }
        if (event.getAction() == 3) {
            if (!this$0.checkIndex(event.getChangeStart().index) || !this$0.checkIndex(event.getChangeEnd().index)) {
                this$0.stopSnippet();
                return;
            }
            this$0.editor.getText().getUndoManager().exitReplaceMode();
            int length4 = event.getChangedText().length();
            PlaceholderItem editingTabStop2 = this$0.getEditingTabStop();
            Intrinsics.checkNotNull(editingTabStop2);
            editingTabStop2.setIndex(editingTabStop2.getStartIndex(), editingTabStop2.getEndIndex() - length4);
            CodeSnippet codeSnippet3 = this$0.currentSnippet;
            Intrinsics.checkNotNull(codeSnippet3);
            List<SnippetItem> items3 = codeSnippet3.getItems();
            Intrinsics.checkNotNullExpressionValue(items3, "getItems(...)");
            boolean z4 = false;
            for (SnippetItem snippetItem3 : items3) {
                if (z4) {
                    snippetItem3.shiftIndex(-length4);
                } else if (Intrinsics.areEqual(snippetItem3, editingTabStop2)) {
                    z4 = true;
                }
            }
            this$0.inSequenceEdits = true;
            Content text2 = this$0.editor.getText();
            Intrinsics.checkNotNullExpressionValue(text2, "getText(...)");
            String strSubstring2 = text2.substring(editingTabStop2.getStartIndex(), editingTabStop2.getEndIndex());
            text2.beginBatchEdit();
            CodeSnippet codeSnippet4 = this$0.currentSnippet;
            Intrinsics.checkNotNull(codeSnippet4);
            List<SnippetItem> items4 = codeSnippet4.getItems();
            Intrinsics.checkNotNullExpressionValue(items4, "getItems(...)");
            int i3 = 0;
            for (Object obj2 : items4) {
                int i4 = i3 + 1;
                if (i3 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                SnippetItem snippetItem4 = (SnippetItem) obj2;
                Intrinsics.checkNotNull(snippetItem4);
                if (this$0.isEditingRelated(snippetItem4)) {
                    int length5 = strSubstring2.length() - (snippetItem4.getEndIndex() - snippetItem4.getStartIndex());
                    text2.replace(snippetItem4.getStartIndex(), snippetItem4.getEndIndex(), strSubstring2);
                    snippetItem4.setIndex(snippetItem4.getStartIndex(), snippetItem4.getEndIndex() + length5);
                    this$0.shiftItemsFrom(i4, length5);
                }
                i3 = i4;
            }
            text2.endBatchEdit();
            this$0.inSequenceEdits = false;
        }
    }

    private final boolean checkIndex(int index) {
        PlaceholderItem editingTabStop = getEditingTabStop();
        Intrinsics.checkNotNull(editingTabStop);
        return index >= editingTabStop.getStartIndex() && index <= editingTabStop.getEndIndex();
    }

    public static /* synthetic */ void startSnippet$default(SnippetController snippetController, int i, CodeSnippet codeSnippet, String str, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            str = "";
        }
        snippetController.startSnippet(i, codeSnippet, str);
    }

    /* JADX WARN: Code duplicated, block: B:100:0x034b  */
    /* JADX WARN: Code duplicated, block: B:103:0x035e  */
    /* JADX WARN: Code duplicated, block: B:106:0x0376  */
    /* JADX WARN: Code duplicated, block: B:109:0x0399  */
    /* JADX WARN: Code duplicated, block: B:113:0x03ad  */
    /* JADX WARN: Code duplicated, block: B:114:0x03b8  */
    /* JADX WARN: Code duplicated, block: B:116:0x03c2  */
    /* JADX WARN: Code duplicated, block: B:117:0x03c5  */
    /* JADX WARN: Code duplicated, block: B:119:0x03cb  */
    /* JADX WARN: Code duplicated, block: B:120:0x03d0  */
    /* JADX WARN: Code duplicated, block: B:122:0x03d3  */
    /* JADX WARN: Code duplicated, block: B:123:0x03e3  */
    /* JADX WARN: Code duplicated, block: B:127:0x0420 A[PHI: r18
      0x0420: PHI (r18v3 java.lang.String) = (r18v1 java.lang.String), (r18v1 java.lang.String), (r18v4 java.lang.String) binds: [B:99:0x0349, B:101:0x035a, B:97:0x031e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:211:0x039d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:212:0x0386 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:213:0x03fb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:98:0x0345  */
    public final void startSnippet(int index, CodeSnippet snippet, String selectedText) {
        String str;
        Object obj;
        PlaceholderDefinition definition;
        String text;
        Object next;
        String str2;
        PlaceholderItem placeholderItem;
        PlaceholderDefinition definition2;
        StringBuilder sb;
        int length;
        String str3;
        List<SnippetItem> list;
        int i;
        VariableItem variableItem;
        String defaultValue;
        int length2;
        String strSubstring;
        String defaultValue2;
        PlaceholderDefinition placeholderDefinition;
        Intrinsics.checkNotNullParameter(snippet, "snippet");
        Intrinsics.checkNotNullParameter(selectedText, "selectedText");
        if (this.snippetIndex != -1) {
            stopSnippet();
        }
        if (!snippet.checkContent() || snippet.getItems().size() == 0) {
            Log.e("SnippetController", "invalid code snippet");
            return;
        }
        CodeSnippet codeSnippetM2800clone = snippet.m2800clone();
        Intrinsics.checkNotNullExpressionValue(codeSnippetM2800clone, "clone(...)");
        this.currentSnippet = codeSnippetM2800clone;
        this.currentTabStopIndex = -1;
        this.snippetIndex = index;
        List<SnippetItem> items = codeSnippetM2800clone.getItems();
        Intrinsics.checkNotNull(items);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int id2 = 0;
        for (SnippetItem snippetItem : items) {
            if (snippetItem instanceof PlaceholderItem) {
                PlaceholderItem placeholderItem2 = (PlaceholderItem) snippetItem;
                if (placeholderItem2.getDefinition().getId() > id2) {
                    id2 = placeholderItem2.getDefinition().getId();
                }
            }
        }
        int size = items.size();
        for (int i2 = 0; i2 < size; i2++) {
            SnippetItem snippetItem2 = items.get(i2);
            if (snippetItem2 instanceof VariableItem) {
                VariableItem variableItem2 = (VariableItem) snippetItem2;
                if (this.variableResolver.canResolve(variableItem2.getName())) {
                    defaultValue2 = this.variableResolver.resolve(variableItem2.getName());
                } else if (Intrinsics.areEqual(variableItem2.getName(), "selection")) {
                    defaultValue2 = selectedText;
                } else {
                    defaultValue2 = variableItem2.getDefaultValue() != null ? variableItem2.getDefaultValue() : null;
                }
                if (defaultValue2 != null) {
                    String strDoTransform = TransformApplier.doTransform(defaultValue2, variableItem2.getTransform());
                    int length3 = strDoTransform.length() - (variableItem2.getEndIndex() - variableItem2.getStartIndex());
                    items.set(i2, new PlainTextItem(strDoTransform, variableItem2.getStartIndex(), variableItem2.getStartIndex() + strDoTransform.length()));
                    shiftItemsFrom(i2 + 1, length3);
                } else {
                    String name2 = variableItem2.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    if (linkedHashMap.containsKey(name2)) {
                        Object obj2 = linkedHashMap.get(variableItem2.getName());
                        Intrinsics.checkNotNull(obj2);
                        placeholderDefinition = (PlaceholderDefinition) obj2;
                    } else {
                        id2++;
                        placeholderDefinition = new PlaceholderDefinition(id2, null, null, null, 14, null);
                        placeholderDefinition.setText$editor_release(variableItem2.getName());
                        String name3 = variableItem2.getName();
                        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                        linkedHashMap.put(name3, placeholderDefinition);
                    }
                    items.set(i2, new PlaceholderItem(placeholderDefinition, variableItem2.getStartIndex()));
                    shiftItemsFrom(i2 + 1, variableItem2.getName().length() - (variableItem2.getEndIndex() - variableItem2.getStartIndex()));
                }
            } else if (snippetItem2 instanceof InterpolatedShellItem) {
                try {
                    Process processExec = Runtime.getRuntime().exec("sh");
                    OutputStream outputStream = processExec.getOutputStream();
                    String shellCode = ((InterpolatedShellItem) snippetItem2).getShellCode();
                    Intrinsics.checkNotNullExpressionValue(shellCode, "getShellCode(...)");
                    outputStream.write(StringsKt.encodeToByteArray(shellCode));
                    outputStream.write(StringsKt.encodeToByteArray("\nexit\n"));
                    outputStream.flush();
                    InputStream inputStream = processExec.getInputStream();
                    Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
                    Reader inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8);
                    strSubstring = TextStreamsKt.readText(inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192));
                } catch (Exception e) {
                    e.printStackTrace();
                    strSubstring = "";
                }
                String str4 = strSubstring;
                char cCharAt = strSubstring.charAt(StringsKt.getLastIndex(str4));
                if ((str4.length() > 0 && cCharAt == '\n') || cCharAt == '\r') {
                    if (cCharAt == '\r' || ((StringsKt.getLastIndex(str4) > 0 && strSubstring.charAt(StringsKt.getLastIndex(str4) - 1) != '\r') || StringsKt.getLastIndex(str4) == 0)) {
                        strSubstring = strSubstring.substring(0, StringsKt.getLastIndex(str4));
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    } else {
                        strSubstring = strSubstring.substring(0, StringsKt.getLastIndex(str4) - 1);
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    }
                }
                InterpolatedShellItem interpolatedShellItem = (InterpolatedShellItem) snippetItem2;
                int length4 = strSubstring.length() - (interpolatedShellItem.getEndIndex() - interpolatedShellItem.getStartIndex());
                items.set(i2, new PlainTextItem(strSubstring, interpolatedShellItem.getStartIndex(), interpolatedShellItem.getStartIndex() + strSubstring.length()));
                shiftItemsFrom(i2 + 1, length4);
            }
        }
        Iterator<SnippetItem> it = codeSnippetM2800clone.getItems().iterator();
        while (true) {
            str = "getText(...)";
            if (!it.hasNext()) {
                break;
            }
            SnippetItem next2 = it.next();
            if (next2 instanceof PlainTextItem) {
                String text2 = ((PlainTextItem) next2).getText();
                Intrinsics.checkNotNullExpressionValue(text2, "getText(...)");
                if (text2.length() == 0) {
                    it.remove();
                }
            }
        }
        shiftItemsFrom(0, index);
        Content text3 = this.editor.getText();
        Intrinsics.checkNotNullExpressionValue(text3, "getText(...)");
        CharPosition charPosition = text3.getIndexer().getCharPosition(index);
        Intrinsics.checkNotNullExpressionValue(charPosition, "getCharPosition(...)");
        ContentLine line = text3.getLine(charPosition.line);
        int i3 = charPosition.column;
        int i4 = 0;
        for (int i5 = 0; i5 < i3 && (line.value[i5] == ' ' || line.value[i5] == '\t'); i5++) {
            i4++;
        }
        Intrinsics.checkNotNull(line);
        String string = line.subSequence(0, i4).toString();
        List<SnippetItem> items2 = codeSnippetM2800clone.getItems();
        int size2 = items2.size();
        int i6 = 0;
        while (i6 < size2) {
            SnippetItem snippetItem3 = items2.get(i6);
            if (snippetItem3 instanceof PlainTextItem) {
                PlainTextItem plainTextItem = (PlainTextItem) snippetItem3;
                String text4 = plainTextItem.getText();
                Intrinsics.checkNotNullExpressionValue(text4, str);
                Regex regex = lineSeparatorRegex;
                if (regex.containsMatchIn(text4)) {
                    StringBuilder sb2 = new StringBuilder();
                    String text5 = plainTextItem.getText();
                    Intrinsics.checkNotNullExpressionValue(text5, str);
                    str2 = str;
                    boolean z = true;
                    for (Iterator it2 = regex.split(text5, 0).iterator(); it2.hasNext(); it2 = it2) {
                        String str5 = (String) it2.next();
                        if (z) {
                            sb2.append(str5);
                            z = false;
                        } else {
                            sb2.append(this.editor.getLineSeparator().getContent()).append(string).append(str5);
                        }
                    }
                    int length5 = sb2.length() - plainTextItem.getText().length();
                    plainTextItem.setText(sb2.toString());
                    snippetItem3.setIndex(plainTextItem.getStartIndex(), plainTextItem.getEndIndex() + length5);
                    shiftItemsFrom(i6 + 1, length5);
                } else {
                    str2 = str;
                    if (snippetItem3 instanceof PlaceholderItem) {
                        placeholderItem = (PlaceholderItem) snippetItem3;
                        definition2 = placeholderItem.getDefinition();
                        if (definition2.getElements().isEmpty()) {
                            sb = new StringBuilder();
                            length = 0;
                            for (PlaceHolderElement placeHolderElement : placeholderItem.getDefinition().getElements()) {
                                string = string;
                                items2 = items2;
                                if (placeHolderElement instanceof PlainPlaceholderElement) {
                                    PlainPlaceholderElement plainPlaceholderElement = (PlainPlaceholderElement) placeHolderElement;
                                    sb.append(plainPlaceholderElement.getText());
                                    length += plainPlaceholderElement.getText().length();
                                } else if (placeHolderElement instanceof VariableItem) {
                                    variableItem = (VariableItem) placeHolderElement;
                                    int i7 = size2;
                                    if (this.variableResolver.canResolve(variableItem.getName())) {
                                        defaultValue = this.variableResolver.resolve(variableItem.getName());
                                    } else if (Intrinsics.areEqual(variableItem.getName(), "selection")) {
                                        defaultValue = selectedText;
                                    } else if (variableItem.getDefaultValue() != null) {
                                        defaultValue = variableItem.getDefaultValue();
                                    } else {
                                        defaultValue = null;
                                    }
                                    if (defaultValue != null) {
                                        String strDoTransform2 = TransformApplier.doTransform(defaultValue, variableItem.getTransform());
                                        sb.append(strDoTransform2);
                                        length2 = strDoTransform2.length();
                                    } else {
                                        sb.append(variableItem.getName());
                                        length2 = variableItem.getName().length();
                                    }
                                    length += length2;
                                    size2 = i7;
                                }
                            }
                            str3 = string;
                            list = items2;
                            i = size2;
                            definition2.setText$editor_release(sb.toString());
                            snippetItem3.setIndex(placeholderItem.getStartIndex(), placeholderItem.getEndIndex() + length);
                            shiftItemsFrom(i6 + 1, length);
                        }
                    }
                }
                str3 = string;
                list = items2;
                i = size2;
            } else {
                str2 = str;
                if (snippetItem3 instanceof PlaceholderItem) {
                    placeholderItem = (PlaceholderItem) snippetItem3;
                    definition2 = placeholderItem.getDefinition();
                    if (definition2.getElements().isEmpty()) {
                        sb = new StringBuilder();
                        length = 0;
                        while (r12.hasNext()) {
                            string = string;
                            items2 = items2;
                            if (placeHolderElement instanceof PlainPlaceholderElement) {
                                PlainPlaceholderElement plainPlaceholderElement2 = (PlainPlaceholderElement) placeHolderElement;
                                sb.append(plainPlaceholderElement2.getText());
                                length += plainPlaceholderElement2.getText().length();
                            } else if (placeHolderElement instanceof VariableItem) {
                                variableItem = (VariableItem) placeHolderElement;
                                int i8 = size2;
                                if (this.variableResolver.canResolve(variableItem.getName())) {
                                    defaultValue = this.variableResolver.resolve(variableItem.getName());
                                } else if (Intrinsics.areEqual(variableItem.getName(), "selection")) {
                                    defaultValue = selectedText;
                                } else if (variableItem.getDefaultValue() != null) {
                                    defaultValue = variableItem.getDefaultValue();
                                } else {
                                    defaultValue = null;
                                }
                                if (defaultValue != null) {
                                    String strDoTransform3 = TransformApplier.doTransform(defaultValue, variableItem.getTransform());
                                    sb.append(strDoTransform3);
                                    length2 = strDoTransform3.length();
                                } else {
                                    sb.append(variableItem.getName());
                                    length2 = variableItem.getName().length();
                                }
                                length += length2;
                                size2 = i8;
                            }
                        }
                        str3 = string;
                        list = items2;
                        i = size2;
                        definition2.setText$editor_release(sb.toString());
                        snippetItem3.setIndex(placeholderItem.getStartIndex(), placeholderItem.getEndIndex() + length);
                        shiftItemsFrom(i6 + 1, length);
                    } else {
                        str3 = string;
                        list = items2;
                        i = size2;
                    }
                } else {
                    str3 = string;
                    list = items2;
                    i = size2;
                }
            }
            i6++;
            string = str3;
            str = str2;
            items2 = list;
            size2 = i;
        }
        ArrayList arrayList = new ArrayList();
        List<SnippetItem> items3 = codeSnippetM2800clone.getItems();
        Intrinsics.checkNotNullExpressionValue(items3, "getItems(...)");
        for (SnippetItem snippetItem4 : items3) {
            if (snippetItem4 instanceof PlaceholderItem) {
                PlaceholderItem placeholderItem3 = (PlaceholderItem) snippetItem4;
                if (placeholderItem3.getDefinition().getId() != 0) {
                    Iterator it3 = arrayList.iterator();
                    do {
                        if (!it3.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it3.next();
                    } while (!Intrinsics.areEqual(((PlaceholderItem) next).getDefinition(), placeholderItem3.getDefinition()));
                    if (next == null) {
                        arrayList.add(snippetItem4);
                    }
                }
            }
        }
        final AnonymousClass4 anonymousClass4 = new Function2<PlaceholderItem, PlaceholderItem, Integer>() { // from class: io.github.rosemoe.sora.widget.snippet.SnippetController.startSnippet.4
            @Override // kotlin.jvm.functions.Function2
            public final Integer invoke(PlaceholderItem placeholderItem4, PlaceholderItem placeholderItem5) {
                return Integer.valueOf(Intrinsics.compare(placeholderItem4.getDefinition().getId(), placeholderItem5.getDefinition().getId()));
            }
        };
        CollectionsKt.sortWith(arrayList, new Comparator() { // from class: io.github.rosemoe.sora.widget.snippet.SnippetController$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj3, Object obj4) {
                return SnippetController.startSnippet$lambda$13(anonymousClass4, obj3, obj4);
            }
        });
        List<SnippetItem> items4 = codeSnippetM2800clone.getItems();
        Intrinsics.checkNotNullExpressionValue(items4, "getItems(...)");
        Iterator<T> it4 = items4.iterator();
        while (true) {
            if (!it4.hasNext()) {
                obj = null;
                break;
            }
            Object next3 = it4.next();
            SnippetItem snippetItem5 = (SnippetItem) next3;
            if ((snippetItem5 instanceof PlaceholderItem) && ((PlaceholderItem) snippetItem5).getDefinition().getId() == 0) {
                obj = next3;
                break;
            }
        }
        PlaceholderItem placeholderItem4 = (PlaceholderItem) obj;
        if (placeholderItem4 == null) {
            placeholderItem4 = new PlaceholderItem(new PlaceholderDefinition(0, null, null, null, 14, null), ((SnippetItem) CollectionsKt.last((List) items)).getEndIndex());
            codeSnippetM2800clone.getItems().add(placeholderItem4);
        }
        arrayList.add(placeholderItem4);
        this.tabStops = arrayList;
        StringBuilder sb3 = new StringBuilder();
        List<SnippetItem> items5 = codeSnippetM2800clone.getItems();
        Intrinsics.checkNotNullExpressionValue(items5, "getItems(...)");
        for (SnippetItem snippetItem6 : items5) {
            if (snippetItem6 instanceof PlainTextItem) {
                sb3.append(((PlainTextItem) snippetItem6).getText());
            } else if ((snippetItem6 instanceof PlaceholderItem) && (text = (definition = ((PlaceholderItem) snippetItem6).getDefinition()).getText()) != null && text.length() != 0) {
                sb3.append(definition.getText());
            }
        }
        text3.insert(charPosition.line, charPosition.column, sb3);
        if ((this.editor.dispatchEvent(new SnippetEvent(this.editor, 1, this.currentTabStopIndex, arrayList.size())) & 2) != 0) {
            stopSnippet();
        } else {
            shiftToTabStop(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int startSnippet$lambda$13(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Number) tmp0.invoke(obj, obj2)).intValue();
    }

    public final boolean isInSnippet() {
        return (this.snippetIndex == -1 || this.currentTabStopIndex == -1) ? false : true;
    }

    public final PlaceholderItem getEditingTabStop() {
        if (this.snippetIndex == -1) {
            return null;
        }
        List<PlaceholderItem> list = this.tabStops;
        Intrinsics.checkNotNull(list);
        return list.get(this.currentTabStopIndex);
    }

    public final PlaceholderItem getTabStopAt(int index) {
        List<PlaceholderItem> list = this.tabStops;
        if (list != null) {
            return list.get(index);
        }
        return null;
    }

    public final int getTabStopCount() {
        List<PlaceholderItem> list = this.tabStops;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public final List<SnippetItem> getEditingRelatedTabStops() {
        PlaceholderItem editingTabStop = getEditingTabStop();
        if (editingTabStop != null) {
            CodeSnippet codeSnippet = this.currentSnippet;
            Intrinsics.checkNotNull(codeSnippet);
            List<SnippetItem> items = codeSnippet.getItems();
            Intrinsics.checkNotNull(items);
            ArrayList arrayList = new ArrayList();
            for (Object obj : items) {
                SnippetItem snippetItem = (SnippetItem) obj;
                if ((snippetItem instanceof PlaceholderItem) && Intrinsics.areEqual(((PlaceholderItem) snippetItem).getDefinition(), editingTabStop.getDefinition()) && !Intrinsics.areEqual(snippetItem, editingTabStop)) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }
        return CollectionsKt.emptyList();
    }

    public final boolean isEditingRelated(SnippetItem it) {
        Intrinsics.checkNotNullParameter(it, "it");
        PlaceholderItem editingTabStop = getEditingTabStop();
        return editingTabStop != null && (it instanceof PlaceholderItem) && Intrinsics.areEqual(((PlaceholderItem) it).getDefinition(), editingTabStop.getDefinition()) && !Intrinsics.areEqual(it, editingTabStop);
    }

    public final List<SnippetItem> getInactiveTabStops() {
        PlaceholderItem editingTabStop = getEditingTabStop();
        if (editingTabStop != null) {
            CodeSnippet codeSnippet = this.currentSnippet;
            Intrinsics.checkNotNull(codeSnippet);
            List<SnippetItem> items = codeSnippet.getItems();
            Intrinsics.checkNotNull(items);
            ArrayList arrayList = new ArrayList();
            for (Object obj : items) {
                SnippetItem snippetItem = (SnippetItem) obj;
                if ((snippetItem instanceof PlaceholderItem) && !Intrinsics.areEqual(((PlaceholderItem) snippetItem).getDefinition(), editingTabStop.getDefinition())) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }
        return CollectionsKt.emptyList();
    }

    public final void shiftToPreviousTabStop() {
        int i;
        if (this.snippetIndex == -1 || (i = this.currentTabStopIndex) <= 0) {
            return;
        }
        shiftToTabStop(i - 1);
    }

    public final void shiftToNextTabStop() {
        if (this.snippetIndex != -1) {
            int i = this.currentTabStopIndex;
            List<PlaceholderItem> list = this.tabStops;
            Intrinsics.checkNotNull(list);
            if (i < list.size() - 1) {
                shiftToTabStop(this.currentTabStopIndex + 1);
            }
        }
    }

    private final void shiftToTabStop(int index) {
        if (this.snippetIndex == -1) {
            return;
        }
        int i = this.currentTabStopIndex;
        if (index != i && i != -1) {
            List<PlaceholderItem> list = this.tabStops;
            Intrinsics.checkNotNull(list);
            PlaceholderItem placeholderItem = list.get(this.currentTabStopIndex);
            if (placeholderItem.getDefinition().getTransform() != null) {
                this.editor.getText().replace(placeholderItem.getStartIndex(), placeholderItem.getEndIndex(), TransformApplier.doTransform(this.editor.getText().substring(placeholderItem.getStartIndex(), placeholderItem.getEndIndex()), placeholderItem.getDefinition().getTransform()));
            }
        }
        List<PlaceholderItem> list2 = this.tabStops;
        Intrinsics.checkNotNull(list2);
        PlaceholderItem placeholderItem2 = list2.get(index);
        Indexer indexer = this.editor.getText().getIndexer();
        CharPosition charPosition = indexer.getCharPosition(placeholderItem2.getStartIndex());
        Intrinsics.checkNotNullExpressionValue(charPosition, "getCharPosition(...)");
        CharPosition charPosition2 = indexer.getCharPosition(placeholderItem2.getEndIndex());
        Intrinsics.checkNotNullExpressionValue(charPosition2, "getCharPosition(...)");
        this.currentTabStopIndex = index;
        this.editor.setSelectionRegion(charPosition.line, charPosition.column, charPosition2.line, charPosition2.column);
        CodeEditor codeEditor = this.editor;
        CodeEditor codeEditor2 = this.editor;
        int i2 = this.currentTabStopIndex;
        List<PlaceholderItem> list3 = this.tabStops;
        Intrinsics.checkNotNull(list3);
        codeEditor.dispatchEvent(new SnippetEvent(codeEditor2, 2, i2, list3.size()));
        List<PlaceholderItem> list4 = this.tabStops;
        Intrinsics.checkNotNull(list4);
        if (index == list4.size() - 1) {
            stopSnippet();
        }
    }

    private final void shiftItemsFrom(int itemIndex, int deltaIndex) {
        if (deltaIndex == 0) {
            return;
        }
        CodeSnippet codeSnippet = this.currentSnippet;
        Intrinsics.checkNotNull(codeSnippet);
        List<SnippetItem> items = codeSnippet.getItems();
        Intrinsics.checkNotNull(items);
        int size = items.size();
        while (itemIndex < size) {
            items.get(itemIndex).shiftIndex(deltaIndex);
            itemIndex++;
        }
    }

    public final void stopSnippet() {
        if (isInSnippet()) {
            this.currentSnippet = null;
            this.snippetIndex = -1;
            this.tabStops = null;
            this.currentTabStopIndex = -1;
            this.editor.dispatchEvent(new SnippetEvent(this.editor, 3, this.currentTabStopIndex, 0));
            this.editor.invalidate();
        }
    }
}
