package org.catrobat.catroid.editor;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/DraggableLinearLayoutManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DraggableLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled;

    public DraggableLinearLayoutManager(Context context) {
        super(context);
        this.isScrollEnabled = true;
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.isScrollEnabled && super.canScrollVertically();
    }
}
