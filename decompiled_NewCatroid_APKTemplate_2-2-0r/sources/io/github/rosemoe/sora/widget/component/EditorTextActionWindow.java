package io.github.rosemoe.sora.widget.component;

import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import io.github.rosemoe.sora.R;
import io.github.rosemoe.sora.event.EditorFocusChangeEvent;
import io.github.rosemoe.sora.event.EditorReleaseEvent;
import io.github.rosemoe.sora.event.Event;
import io.github.rosemoe.sora.event.EventManager;
import io.github.rosemoe.sora.event.HandleStateChangeEvent;
import io.github.rosemoe.sora.event.LongPressEvent;
import io.github.rosemoe.sora.event.ScrollEvent;
import io.github.rosemoe.sora.event.SelectionChangeEvent;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.EditorTouchEventHandler;
import io.github.rosemoe.sora.widget.base.EditorPopupWindow;
import kotlin.time.DurationKt;
import org.apache.commons.compress.compressors.bzip2.BZip2Constants;

/* JADX INFO: loaded from: classes11.dex */
public class EditorTextActionWindow extends EditorPopupWindow implements View.OnClickListener, EditorBuiltinComponent {
    private static final long CHECK_FOR_DISMISS_INTERVAL = 100;
    private static final long DELAY = 200;
    private final ImageButton copyBtn;
    private final ImageButton cutBtn;
    private final CodeEditor editor;
    private boolean enabled;
    private final EventManager eventManager;
    private final EditorTouchEventHandler handler;
    private int lastCause;
    private int lastPosition;
    private long lastScroll;
    private final ImageButton longSelectBtn;
    private final ImageButton pasteBtn;
    private final View rootView;

    public EditorTextActionWindow(CodeEditor codeEditor) {
        super(codeEditor, 2);
        this.enabled = true;
        this.editor = codeEditor;
        this.handler = codeEditor.getEventHandler();
        this.eventManager = codeEditor.createSubEventManager();
        View viewInflate = LayoutInflater.from(codeEditor.getContext()).inflate(R.layout.text_compose_panel, (ViewGroup) null);
        this.rootView = viewInflate;
        ImageButton imageButton = (ImageButton) viewInflate.findViewById(R.id.panel_btn_select_all);
        ImageButton imageButton2 = (ImageButton) viewInflate.findViewById(R.id.panel_btn_cut);
        this.cutBtn = imageButton2;
        ImageButton imageButton3 = (ImageButton) viewInflate.findViewById(R.id.panel_btn_copy);
        this.copyBtn = imageButton3;
        ImageButton imageButton4 = (ImageButton) viewInflate.findViewById(R.id.panel_btn_long_select);
        this.longSelectBtn = imageButton4;
        ImageButton imageButton5 = (ImageButton) viewInflate.findViewById(R.id.panel_btn_paste);
        this.pasteBtn = imageButton5;
        imageButton.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton5.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(codeEditor.getDpUnit() * 5.0f);
        gradientDrawable.setColor(-1);
        viewInflate.setBackground(gradientDrawable);
        setContentView(viewInflate);
        setSize(0, (int) (codeEditor.getDpUnit() * 48.0f));
        getPopup().setAnimationStyle(R.style.text_action_popup_animation);
        subscribeEvents();
    }

    protected void subscribeEvents() {
        this.eventManager.subscribeAlways(SelectionChangeEvent.class, new EventManager.NoUnsubscribeReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorTextActionWindow$$ExternalSyntheticLambda0
            @Override // io.github.rosemoe.sora.event.EventManager.NoUnsubscribeReceiver
            public final void onReceive(Event event) {
                this.f$0.onSelectionChange((SelectionChangeEvent) event);
            }
        });
        this.eventManager.subscribeAlways(ScrollEvent.class, new EventManager.NoUnsubscribeReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorTextActionWindow$$ExternalSyntheticLambda1
            @Override // io.github.rosemoe.sora.event.EventManager.NoUnsubscribeReceiver
            public final void onReceive(Event event) {
                this.f$0.onEditorScroll((ScrollEvent) event);
            }
        });
        this.eventManager.subscribeAlways(HandleStateChangeEvent.class, new EventManager.NoUnsubscribeReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorTextActionWindow$$ExternalSyntheticLambda2
            @Override // io.github.rosemoe.sora.event.EventManager.NoUnsubscribeReceiver
            public final void onReceive(Event event) {
                this.f$0.onHandleStateChange((HandleStateChangeEvent) event);
            }
        });
        this.eventManager.subscribeAlways(LongPressEvent.class, new EventManager.NoUnsubscribeReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorTextActionWindow$$ExternalSyntheticLambda3
            @Override // io.github.rosemoe.sora.event.EventManager.NoUnsubscribeReceiver
            public final void onReceive(Event event) {
                this.f$0.onEditorLongPress((LongPressEvent) event);
            }
        });
        this.eventManager.subscribeAlways(EditorFocusChangeEvent.class, new EventManager.NoUnsubscribeReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorTextActionWindow$$ExternalSyntheticLambda4
            @Override // io.github.rosemoe.sora.event.EventManager.NoUnsubscribeReceiver
            public final void onReceive(Event event) {
                this.f$0.onEditorFocusChange((EditorFocusChangeEvent) event);
            }
        });
        this.eventManager.subscribeAlways(EditorReleaseEvent.class, new EventManager.NoUnsubscribeReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorTextActionWindow$$ExternalSyntheticLambda5
            @Override // io.github.rosemoe.sora.event.EventManager.NoUnsubscribeReceiver
            public final void onReceive(Event event) {
                this.f$0.onEditorRelease((EditorReleaseEvent) event);
            }
        });
    }

    protected void onEditorFocusChange(EditorFocusChangeEvent editorFocusChangeEvent) {
        if (editorFocusChangeEvent.getIsGainFocus()) {
            return;
        }
        dismiss();
    }

    protected void onEditorRelease(EditorReleaseEvent editorReleaseEvent) {
        setEnabled(false);
    }

    protected void onEditorLongPress(LongPressEvent longPressEvent) {
        if (this.editor.getCursor().isSelected() && this.lastCause == 6) {
            int index = longPressEvent.getIndex();
            if (index >= this.editor.getCursor().getLeft() && index <= this.editor.getCursor().getRight()) {
                this.lastCause = 0;
                displayWindow();
            }
            longPressEvent.intercept(2);
        }
    }

    protected void onEditorScroll(ScrollEvent scrollEvent) {
        long j = this.lastScroll;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.lastScroll = jCurrentTimeMillis;
        if (jCurrentTimeMillis - j >= DELAY || this.lastCause == 6) {
            return;
        }
        postDisplay();
    }

    protected void onHandleStateChange(HandleStateChangeEvent handleStateChangeEvent) {
        if (handleStateChangeEvent.isHeld()) {
            postDisplay();
        }
        if (handleStateChangeEvent.getEditor().getCursor().isSelected() || handleStateChangeEvent.getHandleType() != 0 || handleStateChangeEvent.isHeld()) {
            return;
        }
        displayWindow();
        this.editor.postDelayedInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.component.EditorTextActionWindow.1
            @Override // java.lang.Runnable
            public void run() {
                if (!EditorTextActionWindow.this.editor.getEventHandler().shouldDrawInsertHandle() && !EditorTextActionWindow.this.editor.getCursor().isSelected()) {
                    EditorTextActionWindow.this.dismiss();
                } else {
                    if (EditorTextActionWindow.this.editor.getCursor().isSelected()) {
                        return;
                    }
                    EditorTextActionWindow.this.editor.postDelayedInLifecycle(this, EditorTextActionWindow.CHECK_FOR_DISMISS_INTERVAL);
                }
            }
        }, CHECK_FOR_DISMISS_INTERVAL);
    }

    protected void onSelectionChange(SelectionChangeEvent selectionChangeEvent) {
        boolean z;
        if (this.handler.hasAnyHeldHandle()) {
            return;
        }
        this.lastCause = selectionChangeEvent.getCause();
        if (selectionChangeEvent.isSelected()) {
            if (selectionChangeEvent.getCause() != 6) {
                this.editor.postInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.component.EditorTextActionWindow$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.displayWindow();
                    }
                });
            } else {
                dismiss();
            }
            this.lastPosition = -1;
            return;
        }
        if (selectionChangeEvent.getCause() == 3 && selectionChangeEvent.getLeft().index == this.lastPosition && !isShowing() && !this.editor.getText().isInBatchEdit() && this.editor.isEditable()) {
            this.editor.postInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.component.EditorTextActionWindow$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.displayWindow();
                }
            });
            z = true;
        } else {
            dismiss();
            z = false;
        }
        if (selectionChangeEvent.getCause() == 3 && !z) {
            this.lastPosition = selectionChangeEvent.getLeft().index;
        } else {
            this.lastPosition = -1;
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
        dismiss();
    }

    public ViewGroup getView() {
        return (ViewGroup) getPopup().getContentView();
    }

    private void postDisplay() {
        if (isShowing()) {
            dismiss();
            if (this.editor.getCursor().isSelected()) {
                this.editor.postDelayedInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.component.EditorTextActionWindow.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!EditorTextActionWindow.this.handler.hasAnyHeldHandle() && !EditorTextActionWindow.this.editor.getSnippetController().isInSnippet() && System.currentTimeMillis() - EditorTextActionWindow.this.lastScroll > EditorTextActionWindow.DELAY && EditorTextActionWindow.this.editor.getScroller().isFinished()) {
                            EditorTextActionWindow.this.displayWindow();
                        } else {
                            EditorTextActionWindow.this.editor.postDelayedInLifecycle(this, EditorTextActionWindow.DELAY);
                        }
                    }
                }, DELAY);
            }
        }
    }

    private int selectTop(RectF rectF) {
        float height;
        int rowHeight = this.editor.getRowHeight();
        int i = rowHeight * 3;
        if (rectF.top - (i / 2.0f) > getHeight()) {
            height = (rectF.top - (i / 2)) - getHeight();
        } else {
            height = rectF.bottom + (rowHeight / 2);
        }
        return (int) height;
    }

    public void displayWindow() {
        int iSelectTop;
        updateBtnState();
        if (this.editor.getCursor().isSelected()) {
            iSelectTop = Math.min(selectTop(this.editor.getLeftHandleDescriptor().position), selectTop(this.editor.getRightHandleDescriptor().position));
        } else {
            iSelectTop = selectTop(this.editor.getInsertHandleDescriptor().position);
        }
        int iMax = Math.max(0, Math.min(iSelectTop, (this.editor.getHeight() - getHeight()) - 5));
        CodeEditor codeEditor = this.editor;
        float offset = codeEditor.getOffset(codeEditor.getCursor().getLeftLine(), this.editor.getCursor().getLeftColumn());
        CodeEditor codeEditor2 = this.editor;
        setLocationAbsolutely((int) (((offset + codeEditor2.getOffset(codeEditor2.getCursor().getRightLine(), this.editor.getCursor().getRightColumn())) / 2.0f) - (this.rootView.getMeasuredWidth() / 2.0f)), iMax);
        show();
    }

    private void updateBtnState() {
        this.pasteBtn.setEnabled(this.editor.hasClip());
        this.copyBtn.setVisibility(this.editor.getCursor().isSelected() ? 0 : 8);
        this.pasteBtn.setVisibility(this.editor.isEditable() ? 0 : 8);
        this.cutBtn.setVisibility((this.editor.getCursor().isSelected() && this.editor.isEditable()) ? 0 : 8);
        this.longSelectBtn.setVisibility((this.editor.getCursor().isSelected() || !this.editor.isEditable()) ? 8 : 0);
        this.rootView.measure(View.MeasureSpec.makeMeasureSpec(DurationKt.NANOS_IN_MILLIS, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(BZip2Constants.BASEBLOCKSIZE, Integer.MIN_VALUE));
        setSize(Math.min(this.rootView.getMeasuredWidth(), (int) (this.editor.getDpUnit() * 230.0f)), getHeight());
    }

    @Override // io.github.rosemoe.sora.widget.base.EditorPopupWindow
    public void show() {
        if (!this.enabled || this.editor.getSnippetController().isInSnippet() || !this.editor.hasFocus() || this.editor.isInMouseMode()) {
            return;
        }
        super.show();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.panel_btn_select_all) {
            this.editor.selectAll();
            return;
        }
        if (id2 == R.id.panel_btn_cut) {
            if (this.editor.getCursor().isSelected()) {
                this.editor.cutText();
            }
        } else if (id2 == R.id.panel_btn_paste) {
            this.editor.pasteText();
            CodeEditor codeEditor = this.editor;
            codeEditor.setSelection(codeEditor.getCursor().getRightLine(), this.editor.getCursor().getRightColumn());
        } else if (id2 == R.id.panel_btn_copy) {
            this.editor.copyText();
            CodeEditor codeEditor2 = this.editor;
            codeEditor2.setSelection(codeEditor2.getCursor().getRightLine(), this.editor.getCursor().getRightColumn());
        } else if (id2 == R.id.panel_btn_long_select) {
            this.editor.beginLongSelect();
        }
        dismiss();
    }
}
