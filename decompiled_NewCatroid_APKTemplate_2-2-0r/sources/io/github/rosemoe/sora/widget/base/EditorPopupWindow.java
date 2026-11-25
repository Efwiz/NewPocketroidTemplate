package io.github.rosemoe.sora.widget.base;

import android.view.View;
import android.widget.PopupWindow;
import com.google.android.material.badge.BadgeDrawable;
import io.github.rosemoe.sora.event.Event;
import io.github.rosemoe.sora.event.EventReceiver;
import io.github.rosemoe.sora.event.ScrollEvent;
import io.github.rosemoe.sora.event.Unsubscribe;
import io.github.rosemoe.sora.widget.CodeEditor;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class EditorPopupWindow {
    public static final int FEATURE_HIDE_WHEN_FAST_SCROLL = 4;
    public static final int FEATURE_SCROLL_AS_CONTENT = 1;
    public static final int FEATURE_SHOW_OUTSIDE_VIEW_ALLOWED = 2;
    private final CodeEditor editor;
    private final int features;
    private int height;
    private final int[] locationBuffer = new int[2];
    private int offsetX;
    private int offsetY;
    private View parentView;
    private boolean registerFlag;
    private boolean registered;
    private final EventReceiver<ScrollEvent> scrollListener;
    private int width;
    private final PopupWindow window;
    private int windowX;
    private int windowY;

    public EditorPopupWindow(CodeEditor codeEditor, int i) {
        this.editor = (CodeEditor) Objects.requireNonNull(codeEditor);
        this.features = i;
        this.parentView = codeEditor;
        PopupWindow popupWindow = new PopupWindow();
        this.window = popupWindow;
        popupWindow.setElevation(codeEditor.getDpUnit() * 8.0f);
        this.scrollListener = new EventReceiver() { // from class: io.github.rosemoe.sora.widget.base.EditorPopupWindow$$ExternalSyntheticLambda0
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                this.f$0.lambda$new$0((ScrollEvent) event, unsubscribe);
            }
        };
        register();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ScrollEvent scrollEvent, Unsubscribe unsubscribe) {
        if (!this.registerFlag) {
            unsubscribe.unsubscribe();
            this.registered = false;
            return;
        }
        int cause = scrollEvent.getCause();
        if ((cause == 2 || cause == 3 || cause == 4 || cause == 5) && isFeatureEnabled(4) && ((Math.abs(scrollEvent.getEndX() - scrollEvent.getStartX()) > 80 || Math.abs(scrollEvent.getEndY() - scrollEvent.getStartY()) > 80) && isShowing())) {
            dismiss();
        } else if (isFeatureEnabled(1)) {
            applyWindowAttributes(false);
        }
    }

    public CodeEditor getEditor() {
        return this.editor;
    }

    public boolean isFeatureEnabled(int i) {
        if (Integer.bitCount(i) == 1) {
            return (i & this.features) != 0;
        }
        throw new IllegalArgumentException("Not a valid feature integer");
    }

    public void register() {
        if (!this.registered) {
            this.editor.subscribeEvent(ScrollEvent.class, this.scrollListener);
        }
        this.registerFlag = true;
    }

    public void unregister() {
        this.registerFlag = false;
    }

    public boolean isShowing() {
        return getPopup().isShowing();
    }

    public PopupWindow getPopup() {
        return this.window;
    }

    public void setContentView(View view) {
        this.window.setContentView(view);
    }

    private int wrapHorizontal(int i) {
        return Math.max(0, Math.min(i, this.editor.getWidth()));
    }

    private int wrapVertical(int i) {
        return Math.max(0, Math.min(i, this.editor.getHeight()));
    }

    private void applyWindowAttributes(boolean z) {
        int i;
        int offsetY;
        if (z || isShowing()) {
            boolean zIsFeatureEnabled = isFeatureEnabled(1);
            int offsetX = this.windowX - (zIsFeatureEnabled ? this.editor.getOffsetX() : this.offsetX);
            if (zIsFeatureEnabled) {
                i = this.windowY;
                offsetY = this.editor.getOffsetY();
            } else {
                i = this.windowY;
                offsetY = this.offsetY;
            }
            int iWrapVertical = i - offsetY;
            int iWrapHorizontal = this.width + offsetX;
            int iWrapVertical2 = this.height + iWrapVertical;
            if (!isFeatureEnabled(2)) {
                offsetX = wrapHorizontal(offsetX);
                iWrapHorizontal = wrapHorizontal(iWrapHorizontal);
                iWrapVertical = wrapVertical(iWrapVertical);
                iWrapVertical2 = wrapVertical(iWrapVertical2);
                if (iWrapVertical >= iWrapVertical2 || offsetX >= iWrapHorizontal) {
                    dismiss();
                    return;
                }
            }
            this.editor.getLocationInWindow(this.locationBuffer);
            int i2 = iWrapHorizontal - offsetX;
            int i3 = iWrapVertical2 - iWrapVertical;
            int[] iArr = this.locationBuffer;
            int i4 = offsetX + iArr[0];
            int i5 = iWrapVertical + iArr[1];
            if (this.window.isShowing()) {
                this.window.update(i4, i5, i2, i3);
            } else if (z) {
                this.window.setHeight(i3);
                this.window.setWidth(i2);
                this.window.showAtLocation(this.parentView, BadgeDrawable.TOP_START, i4, i5);
            }
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setSize(int i, int i2) {
        this.width = i;
        this.height = i2;
        applyWindowAttributes(false);
    }

    public void setLocation(int i, int i2) {
        this.windowX = i;
        this.windowY = i2;
        this.offsetY = getEditor().getOffsetY();
        this.offsetX = getEditor().getOffsetX();
        applyWindowAttributes(false);
    }

    public void setLocationAbsolutely(int i, int i2) {
        setLocation(i + this.editor.getOffsetX(), i2 + this.editor.getOffsetY());
    }

    public void show() {
        if (isShowing()) {
            return;
        }
        applyWindowAttributes(true);
    }

    public void dismiss() {
        if (isShowing()) {
            this.window.dismiss();
        }
    }

    public View getParentView() {
        return this.parentView;
    }

    public void setParentView(View view) {
        this.parentView = (View) Objects.requireNonNull(view);
    }
}
