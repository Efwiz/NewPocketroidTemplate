package io.github.rosemoe.sora.text;

import android.os.Build;
import android.text.DynamicLayout;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import java.lang.reflect.Field;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

/* JADX INFO: loaded from: classes11.dex */
public class TextLayoutHelper {
    private static final int CHAR_FACTOR = 64;
    private static final ThreadLocal<TextLayoutHelper> sLocal = new ThreadLocal<>();
    private final DynamicLayout layout;
    private final Editable text;

    private TextLayoutHelper() {
        Editable editableNewEditable = Editable.Factory.getInstance().newEditable("");
        this.text = editableNewEditable;
        if (Build.VERSION.SDK_INT <= 28) {
            DynamicLayout dynamicLayout = new DynamicLayout(editableNewEditable, new TextPaint(), LockFreeTaskQueueCore.MAX_CAPACITY_MASK, Layout.Alignment.ALIGN_NORMAL, 0.0f, 0.0f, true);
            this.layout = dynamicLayout;
            try {
                Field declaredField = Layout.class.getDeclaredField("mTextDir");
                declaredField.setAccessible(true);
                declaredField.set(dynamicLayout, TextDirectionHeuristics.LTR);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        this.layout = DynamicLayout.Builder.obtain(editableNewEditable, new TextPaint(), LockFreeTaskQueueCore.MAX_CAPACITY_MASK).setIncludePad(true).setLineSpacing(0.0f, 0.0f).setTextDirection(TextDirectionHeuristics.LTR).setAlignment(Layout.Alignment.ALIGN_NORMAL).build();
    }

    public static TextLayoutHelper get() {
        ThreadLocal<TextLayoutHelper> threadLocal = sLocal;
        TextLayoutHelper textLayoutHelper = threadLocal.get();
        if (textLayoutHelper != null) {
            return textLayoutHelper;
        }
        TextLayoutHelper textLayoutHelper2 = new TextLayoutHelper();
        threadLocal.set(textLayoutHelper2);
        return textLayoutHelper2;
    }

    public int getCurPosLeft(int i, CharSequence charSequence) {
        int iMax = Math.max(0, i - 64);
        this.text.append(charSequence, iMax, Math.min(charSequence.length(), i + 65));
        Selection.setSelection(this.text, Math.min(i - iMax, this.text.length()));
        Selection.moveLeft(this.text, this.layout);
        int selectionStart = Selection.getSelectionStart(this.text);
        this.text.clear();
        Selection.removeSelection(this.text);
        return iMax + selectionStart;
    }

    public int getCurPosRight(int i, CharSequence charSequence) {
        int iMax = Math.max(0, i - 64);
        this.text.append(charSequence, iMax, Math.min(charSequence.length(), i + 65));
        Selection.setSelection(this.text, Math.min(i - iMax, this.text.length()));
        Selection.moveRight(this.text, this.layout);
        int selectionStart = Selection.getSelectionStart(this.text);
        this.text.clear();
        Selection.removeSelection(this.text);
        return iMax + selectionStart;
    }
}
