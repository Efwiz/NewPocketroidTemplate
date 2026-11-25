package io.github.rosemoe.sora.widget.component;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import io.github.rosemoe.sora.lang.completion.CompletionItem;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public abstract class EditorCompletionAdapter extends BaseAdapter implements Adapter {
    private List<CompletionItem> items;
    private EditorAutoCompletion window;

    public abstract int getItemHeight();

    protected abstract View getView(int i, View view, ViewGroup viewGroup, boolean z);

    public void attachValues(EditorAutoCompletion editorAutoCompletion, List<CompletionItem> list) {
        this.window = editorAutoCompletion;
        this.items = list;
    }

    @Override // android.widget.Adapter
    public CompletionItem getItem(int i) {
        return this.items.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return getItem(i).hashCode();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<CompletionItem> list = this.items;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        return getView(i, view, viewGroup, i == this.window.getCurrentPosition());
    }

    protected EditorColorScheme getColorScheme() {
        return this.window.getEditor().getColorScheme();
    }

    protected int getThemeColor(int i) {
        return getColorScheme().getColor(i);
    }

    protected Context getContext() {
        return this.window.getContext();
    }
}
