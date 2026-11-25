package org.catrobat.catroid.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedViewHolder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/ExtendedRVAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class ExtendedRVAdapter<T> extends RVAdapter<T> {
    public boolean projectsSorted;
    public boolean showDetails;

    public abstract void onBindViewHolder(ExtendedViewHolder holder, int position);

    public void stopSound() {
    }

    ExtendedRVAdapter(List<T> items) {
        super(items);
        this.showDetails = false;
        this.projectsSorted = false;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public CheckableViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new ExtendedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_with_checkbox, parent, false));
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(CheckableViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        onBindViewHolder((ExtendedViewHolder) holder, position);
    }
}
