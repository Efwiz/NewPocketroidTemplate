package io.github.rosemoe.sora.widget.component;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import io.github.rosemoe.sora.event.ClickEvent;
import io.github.rosemoe.sora.event.ColorSchemeUpdateEvent;
import io.github.rosemoe.sora.event.ContentChangeEvent;
import io.github.rosemoe.sora.event.EditorFocusChangeEvent;
import io.github.rosemoe.sora.event.EditorFormatEvent;
import io.github.rosemoe.sora.event.EditorKeyEvent;
import io.github.rosemoe.sora.event.EditorLanguageChangeEvent;
import io.github.rosemoe.sora.event.EditorReleaseEvent;
import io.github.rosemoe.sora.event.Event;
import io.github.rosemoe.sora.event.EventManager;
import io.github.rosemoe.sora.event.EventReceiver;
import io.github.rosemoe.sora.event.ScrollEvent;
import io.github.rosemoe.sora.event.SelectionChangeEvent;
import io.github.rosemoe.sora.event.SnippetEvent;
import io.github.rosemoe.sora.event.Unsubscribe;
import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.lang.completion.CompletionCancelledException;
import io.github.rosemoe.sora.lang.completion.CompletionItem;
import io.github.rosemoe.sora.lang.completion.CompletionPublisher;
import io.github.rosemoe.sora.lang.styling.StylesUtils;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.ContentReference;
import io.github.rosemoe.sora.text.Cursor;
import io.github.rosemoe.sora.text.TextReference;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.base.EditorPopupWindow;
import java.lang.ref.WeakReference;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* JADX INFO: loaded from: classes11.dex */
public class EditorAutoCompletion extends EditorPopupWindow implements EditorBuiltinComponent {
    private static final long SHOW_PROGRESS_BAR_DELAY = 50;
    public static final int WINDOW_POS_MODE_AUTO = 0;
    public static final int WINDOW_POS_MODE_FOLLOW_CURSOR_ALWAYS = 1;
    public static final int WINDOW_POS_MODE_FULL_WIDTH_ALWAYS = 2;
    protected EditorCompletionAdapter adapter;
    protected boolean cancelShowUp;
    protected CompletionThread completionThread;
    private int completionWndPosMode;
    protected int currentSelection;
    private final CodeEditor editor;
    private boolean enabled;
    protected EventManager eventManager;
    protected WeakReference<List<CompletionItem>> lastAttachedItems;
    protected CompletionLayout layout;
    private boolean loading;
    protected int maxHeight;
    private CharPosition previousSelection;
    protected CompletionPublisher publisher;
    private long requestHide;
    private long requestShow;
    protected long requestTime;

    public EditorAutoCompletion(CodeEditor codeEditor) {
        super(codeEditor, 4);
        this.cancelShowUp = false;
        this.currentSelection = -1;
        this.completionWndPosMode = 0;
        this.requestShow = 0L;
        this.requestHide = -1L;
        this.enabled = true;
        this.loading = false;
        this.editor = codeEditor;
        this.adapter = new DefaultCompletionItemAdapter();
        setLayout(new DefaultCompletionLayout());
        EventManager eventManagerCreateSubEventManager = codeEditor.createSubEventManager();
        this.eventManager = eventManagerCreateSubEventManager;
        eventManagerCreateSubEventManager.subscribeEvent(ColorSchemeUpdateEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$$ExternalSyntheticLambda5
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                this.f$0.onColorSchemeUpdate((ColorSchemeUpdateEvent) event, unsubscribe);
            }
        });
        this.eventManager.subscribeEvent(ContentChangeEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$$ExternalSyntheticLambda6
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                this.f$0.onContentChange((ContentChangeEvent) event, unsubscribe);
            }
        });
        this.eventManager.subscribeEvent(ScrollEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$$ExternalSyntheticLambda7
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                this.f$0.onEditorScroll((ScrollEvent) event, unsubscribe);
            }
        });
        this.eventManager.subscribeEvent(EditorKeyEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$$ExternalSyntheticLambda8
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                this.f$0.onKeyEvent((EditorKeyEvent) event, unsubscribe);
            }
        });
        this.eventManager.subscribeEvent(SelectionChangeEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$$ExternalSyntheticLambda9
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                this.f$0.onSelectionChange((SelectionChangeEvent) event, unsubscribe);
            }
        });
        this.eventManager.subscribeEvent(EditorReleaseEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$$ExternalSyntheticLambda10
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                this.f$0.lambda$new$0((EditorReleaseEvent) event, unsubscribe);
            }
        });
        subscribeEventForHide(EditorFormatEvent.class, new Function1() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((EditorFormatEvent) obj).getIsSuccess());
            }
        });
        subscribeEventForHide(ClickEvent.class, null);
        subscribeEventForHide(EditorLanguageChangeEvent.class, null);
        subscribeEventForHide(EditorFocusChangeEvent.class, new Function1() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(!((EditorFocusChangeEvent) obj).getIsGainFocus());
            }
        });
        subscribeEventForHide(SnippetEvent.class, new Function1() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((SnippetEvent) obj).getAction() == 2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(EditorReleaseEvent editorReleaseEvent, Unsubscribe unsubscribe) {
        setEnabled(false);
    }

    protected <T extends Event> void subscribeEventForHide(Class<T> cls, final Function1<T, Boolean> function1) {
        this.eventManager.subscribeEvent(cls, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$$ExternalSyntheticLambda2
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                this.f$0.lambda$subscribeEventForHide$3(function1, event, unsubscribe);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$subscribeEventForHide$3(Function1 function1, Event event, Unsubscribe unsubscribe) {
        if (function1 == null || ((Boolean) function1.invoke(event)).booleanValue()) {
            hide();
        }
    }

    protected void onColorSchemeUpdate(ColorSchemeUpdateEvent colorSchemeUpdateEvent, Unsubscribe unsubscribe) {
        applyColorScheme();
    }

    protected void onContentChange(ContentChangeEvent contentChangeEvent, Unsubscribe unsubscribe) {
        if (!contentChangeEvent.isCausedByUndoManager() && isEnabled()) {
            boolean z = true;
            if (contentChangeEvent.getAction() != 1) {
                CharPosition changeStart = contentChangeEvent.getChangeStart();
                CharPosition changeEnd = contentChangeEvent.getChangeEnd();
                int action = contentChangeEvent.getAction();
                if (action == 2) {
                    if ((this.editor.hasComposingText() && !this.editor.getProps().autoCompletionOnComposing) || changeEnd.column == 0 || changeStart.line != changeEnd.line) {
                        hide();
                        z = false;
                    }
                    updateCompletionWindowPosition(isShowing());
                } else if (action != 3 || this.editor.hasComposingText() || !isShowing()) {
                    z = false;
                } else if (changeStart.line != changeEnd.line || changeStart.column != changeEnd.column - 1) {
                    hide();
                    z = false;
                } else {
                    updateCompletionWindowPosition();
                }
                if (z) {
                    requireCompletion();
                    return;
                }
                return;
            }
        }
        hide();
    }

    protected void onSelectionChange(SelectionChangeEvent selectionChangeEvent, Unsubscribe unsubscribe) {
        if (selectionChangeEvent.isSelected() || selectionChangeEvent.getCause() == 4 || selectionChangeEvent.getCause() == 2 || selectionChangeEvent.getCause() == 3 || selectionChangeEvent.getCause() == 6 || selectionChangeEvent.getCause() == 0) {
            hide();
            return;
        }
        if (this.previousSelection == null) {
            this.previousSelection = selectionChangeEvent.getLeft().fromThis();
            return;
        }
        if (selectionChangeEvent.getCause() == 7) {
            if (this.previousSelection.line != selectionChangeEvent.getLeft().line) {
                hide();
                return;
            }
            if (!isShowing() || Math.abs(this.previousSelection.column - selectionChangeEvent.getLeft().column) > 1) {
                return;
            }
            if (selectionChangeEvent.getLeft().column > 0) {
                requireCompletion();
            } else {
                hide();
            }
        }
    }

    protected void onEditorScroll(ScrollEvent scrollEvent, Unsubscribe unsubscribe) {
        if (scrollEvent.getCause() == 1) {
            updateCompletionWindowPosition(false);
            return;
        }
        if (scrollEvent.getCause() == 2) {
            float dpUnit = this.editor.getDpUnit() * 2000.0f;
            float flingVelocityX = scrollEvent.getFlingVelocityX();
            float flingVelocityY = scrollEvent.getFlingVelocityY();
            if (Math.abs(flingVelocityX) >= dpUnit || Math.abs(flingVelocityY) >= dpUnit) {
                hide();
            }
        }
    }

    protected void onKeyEvent(EditorKeyEvent editorKeyEvent, Unsubscribe unsubscribe) {
        if (editorKeyEvent.getEventType() != EditorKeyEvent.Type.DOWN || editorKeyEvent.isAltPressed() || editorKeyEvent.isCtrlPressed() || editorKeyEvent.isShiftPressed() || !isShowing()) {
            return;
        }
        int keyCode = editorKeyEvent.getKeyCode();
        if (keyCode == 19) {
            moveUp();
            editorKeyEvent.setResult(true);
            editorKeyEvent.intercept();
            return;
        }
        if (keyCode == 20) {
            moveDown();
            editorKeyEvent.setResult(true);
            editorKeyEvent.intercept();
        } else {
            if (keyCode == 61 || keyCode == 66) {
                if (select()) {
                    editorKeyEvent.setResult(true);
                    editorKeyEvent.intercept();
                    return;
                }
                return;
            }
            if (keyCode == 92 || keyCode == 93) {
                hide();
            }
        }
    }

    public void updateCompletionWindowPosition() {
        updateCompletionWindowPosition(true);
    }

    public void updateCompletionWindowPosition(boolean z) {
        int iMin;
        float dpUnit = this.editor.getDpUnit();
        Cursor cursor = this.editor.getCursor();
        float fUpdateCursorAnchor = this.editor.updateCursorAnchor() + (20.0f * dpUnit);
        float rowHeight = this.editor.getRowHeight();
        float offsetY = (this.editor.getLayout().getCharLayoutOffset(cursor.getRightLine(), cursor.getRightColumn())[0] - this.editor.getOffsetY()) + (rowHeight / 2.0f);
        float height = this.editor.getHeight() - offsetY;
        float f = 200.0f * dpUnit;
        if (height > f) {
            height = f;
        } else {
            float f2 = 100.0f * dpUnit;
            if (height < f2 && z) {
                float f3 = 0.0f;
                while (height < f2 && this.editor.getOffsetY() + f3 + rowHeight <= this.editor.getScrollMaxY()) {
                    height += rowHeight;
                    offsetY -= rowHeight;
                    f3 += rowHeight;
                }
                this.editor.getScroller().startScroll(this.editor.getOffsetX(), this.editor.getOffsetY(), 0, (int) f3, 0);
            }
        }
        if ((this.editor.getWidth() < 500.0f * dpUnit && this.completionWndPosMode == 0) || this.completionWndPosMode == 2) {
            iMin = (this.editor.getWidth() * 7) / 8;
            fUpdateCursorAnchor = (this.editor.getWidth() / 8.0f) / 2.0f;
        } else {
            iMin = (int) Math.min(dpUnit * 300.0f, this.editor.getWidth() / 2.0f);
        }
        int height2 = getHeight();
        setMaxHeight((int) height);
        setLocation(((int) fUpdateCursorAnchor) + this.editor.getOffsetX(), ((int) offsetY) + this.editor.getOffsetY());
        setSize(iMin, height2);
    }

    public int getCompletionWndPositionMode() {
        return this.completionWndPosMode;
    }

    public void setCompletionWndPositionMode(int i) {
        this.completionWndPosMode = i;
        updateCompletionWindowPosition();
    }

    public void setLayout(CompletionLayout completionLayout) {
        this.layout = completionLayout;
        completionLayout.setEditorCompletion(this);
        setContentView(completionLayout.inflate(this.editor.getContext()));
        applyColorScheme();
        if (this.adapter != null) {
            this.layout.getCompletionList().setAdapter(this.adapter);
        }
    }

    @Override // io.github.rosemoe.sora.widget.component.EditorBuiltinComponent
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override // io.github.rosemoe.sora.widget.component.EditorBuiltinComponent
    public void setEnabled(boolean z) {
        this.enabled = z;
        this.eventManager.setEnabled(z);
        if (z) {
            return;
        }
        cancelCompletion();
        hide();
    }

    public boolean isCompletionInProgress() {
        CompletionThread completionThread = this.completionThread;
        return super.isShowing() || this.requestShow > this.requestHide || (completionThread != null && completionThread.isAlive());
    }

    public void setEnabledAnimation(boolean z) {
        this.layout.setEnabledAnimation(z);
    }

    public void setAdapter(EditorCompletionAdapter editorCompletionAdapter) {
        this.adapter = editorCompletionAdapter;
        if (editorCompletionAdapter == null) {
            this.adapter = new DefaultCompletionItemAdapter();
        }
        this.layout.getCompletionList().setAdapter(editorCompletionAdapter);
    }

    @Override // io.github.rosemoe.sora.widget.base.EditorPopupWindow
    public void show() {
        if (this.cancelShowUp || !isEnabled()) {
            return;
        }
        this.requestShow = System.currentTimeMillis();
        final long j = this.requestTime;
        this.editor.postDelayedInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$show$4(j);
            }
        }, 70L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$4(long j) {
        if (this.requestHide >= this.requestShow || this.requestTime != j) {
            return;
        }
        super.show();
    }

    public void hide() {
        super.dismiss();
        cancelCompletion();
        this.requestHide = System.currentTimeMillis();
    }

    public Context getContext() {
        return this.editor.getContext();
    }

    public int getCurrentPosition() {
        return this.currentSelection;
    }

    public void applyColorScheme() {
        this.layout.onApplyColorScheme(this.editor.getColorScheme());
    }

    public void setLoading(boolean z) {
        this.loading = z;
        if (z) {
            this.editor.postDelayedInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setLoading$5();
                }
            }, SHOW_PROGRESS_BAR_DELAY);
        } else {
            this.layout.setLoading(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLoading$5() {
        if (this.loading) {
            this.layout.setLoading(true);
        }
    }

    public void moveDown() {
        AdapterView completionList = this.layout.getCompletionList();
        if (this.currentSelection + 1 >= completionList.getAdapter().getCount()) {
            return;
        }
        this.currentSelection++;
        ((EditorCompletionAdapter) completionList.getAdapter()).notifyDataSetChanged();
        ensurePosition();
    }

    public void moveUp() {
        AdapterView completionList = this.layout.getCompletionList();
        int i = this.currentSelection;
        if (i - 1 < 0) {
            return;
        }
        this.currentSelection = i - 1;
        ((EditorCompletionAdapter) completionList.getAdapter()).notifyDataSetChanged();
        ensurePosition();
    }

    private void ensurePosition() {
        int i = this.currentSelection;
        if (i != -1) {
            this.layout.ensureListPositionVisible(i, this.adapter.getItemHeight());
        }
    }

    public boolean select() {
        return select(this.currentSelection);
    }

    public boolean select(int i) {
        if (i == -1) {
            return false;
        }
        CompletionItem item = ((EditorCompletionAdapter) this.layout.getCompletionList().getAdapter()).getItem(i);
        Cursor cursor = this.editor.getCursor();
        CompletionThread completionThread = this.completionThread;
        if (!cursor.isSelected() && completionThread != null) {
            this.cancelShowUp = true;
            this.editor.beginComposingTextRejection();
            this.editor.getText().beginBatchEdit();
            this.editor.restartInput();
            try {
                CodeEditor codeEditor = this.editor;
                item.performCompletion(codeEditor, codeEditor.getText(), completionThread.requestPosition);
                this.editor.updateCursor();
                this.editor.getText().endBatchEdit();
                this.editor.endComposingTextRejection();
                this.cancelShowUp = false;
                this.editor.restartInput();
            } catch (Throwable th) {
                this.editor.getText().endBatchEdit();
                this.editor.endComposingTextRejection();
                this.cancelShowUp = false;
                throw th;
            }
        }
        hide();
        return true;
    }

    public void cancelCompletion() {
        CompletionThread completionThread = this.completionThread;
        if (completionThread != null && completionThread.isAlive()) {
            completionThread.cancel();
            completionThread.requestTimestamp = -1L;
        }
        this.completionThread = null;
    }

    public boolean checkNoCompletion() {
        return StylesUtils.checkNoCompletion(this.editor.getStyles(), this.editor.getCursor().left());
    }

    public void requireCompletion() {
        if (this.cancelShowUp || !isEnabled()) {
            return;
        }
        if (this.editor.getText().getCursor().isSelected() || checkNoCompletion()) {
            hide();
            return;
        }
        if (System.nanoTime() - this.requestTime < this.editor.getProps().cancelCompletionNs) {
            hide();
            this.requestTime = System.nanoTime();
            return;
        }
        cancelCompletion();
        this.requestTime = System.nanoTime();
        this.currentSelection = -1;
        this.publisher = new CompletionPublisher(this.editor.getHandler(), new Runnable() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requireCompletion$6();
            }
        }, this.editor.getEditorLanguage().getInterruptionLevel());
        this.completionThread = new CompletionThread(this.requestTime, this.publisher);
        setLoading(true);
        this.completionThread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requireCompletion$6() {
        List<CompletionItem> items = this.publisher.getItems();
        WeakReference<List<CompletionItem>> weakReference = this.lastAttachedItems;
        if (weakReference == null || weakReference.get() != items) {
            this.adapter.attachValues(this, items);
            this.adapter.notifyDataSetInvalidated();
            this.lastAttachedItems = new WeakReference<>(items);
        } else {
            this.adapter.notifyDataSetChanged();
        }
        float itemHeight = this.adapter.getItemHeight() * this.adapter.getCount();
        if (itemHeight == 0.0f) {
            hide();
        }
        updateCompletionWindowPosition();
        setSize(getWidth(), (int) Math.min(itemHeight, this.maxHeight));
        if (isShowing()) {
            return;
        }
        show();
    }

    public void setMaxHeight(int i) {
        this.maxHeight = i;
    }

    public class CompletionThread extends Thread implements TextReference.Validator {
        private boolean aborted;
        private final ContentReference contentRef;
        private final Bundle extraData;
        private final CompletionPublisher localPublisher;
        private final CharPosition requestPosition;
        private long requestTimestamp;
        private final Language targetLanguage;

        public CompletionThread(long j, CompletionPublisher completionPublisher) {
            this.requestTimestamp = j;
            this.requestPosition = EditorAutoCompletion.this.editor.getCursor().left();
            this.targetLanguage = EditorAutoCompletion.this.editor.getEditorLanguage();
            ContentReference contentReference = new ContentReference(EditorAutoCompletion.this.editor.getText());
            this.contentRef = contentReference;
            contentReference.setValidator((TextReference.Validator) this);
            this.localPublisher = completionPublisher;
            this.extraData = EditorAutoCompletion.this.editor.getExtraArguments();
            this.aborted = false;
        }

        public void cancel() {
            this.aborted = true;
            if (this.targetLanguage.getInterruptionLevel() == 0) {
                interrupt();
            }
            this.localPublisher.cancel();
        }

        public boolean isCancelled() {
            return this.aborted;
        }

        @Override // io.github.rosemoe.sora.text.TextReference.Validator
        public void validate() {
            if (EditorAutoCompletion.this.requestTime != this.requestTimestamp || this.aborted) {
                throw new CompletionCancelledException();
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                this.targetLanguage.requireAutoComplete(this.contentRef, this.requestPosition, this.localPublisher, this.extraData);
                if (this.localPublisher.hasData()) {
                    if (EditorAutoCompletion.this.completionThread == Thread.currentThread()) {
                        this.localPublisher.updateList(true);
                    }
                } else {
                    CodeEditor codeEditor = EditorAutoCompletion.this.editor;
                    final EditorAutoCompletion editorAutoCompletion = EditorAutoCompletion.this;
                    codeEditor.postInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$CompletionThread$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            editorAutoCompletion.hide();
                        }
                    });
                }
                EditorAutoCompletion.this.editor.postInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.component.EditorAutoCompletion$CompletionThread$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$run$0();
                    }
                });
            } catch (Exception e) {
                if (e instanceof CompletionCancelledException) {
                    Log.v("CompletionThread", "Completion is cancelled");
                } else {
                    Log.e("CompletionThread", "Completion failed", e);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0() {
            EditorAutoCompletion.this.setLoading(false);
        }
    }
}
