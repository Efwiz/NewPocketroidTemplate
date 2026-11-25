package io.github.rosemoe.sora.widget.component;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

/* JADX INFO: loaded from: classes11.dex */
public interface CompletionLayout {
    void ensureListPositionVisible(int i, int i2);

    AdapterView getCompletionList();

    View inflate(Context context);

    void onApplyColorScheme(EditorColorScheme editorColorScheme);

    void setEditorCompletion(EditorAutoCompletion editorAutoCompletion);

    default void setEnabledAnimation(boolean z) {
    }

    void setLoading(boolean z);
}
