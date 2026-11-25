package org.catrobat.catroid.ui.recyclerview.backpack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.TouchHelperCallback;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/backpack/ActorAndObjectRecyclerViewFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class ActorAndObjectRecyclerViewFragment<T> extends Fragment implements RVAdapter.OnItemClickListener<T> {
    protected ExtendedRVAdapter<T> adapter;
    protected View parentView;
    protected RecyclerView recyclerView;
    protected ItemTouchHelper touchHelper;
    protected String sharedPreferenceDetailsKey = "";
    public boolean hasDetails = false;

    protected abstract void deleteItems(List<T> selectedItems);

    protected abstract int getDeleteAlertTitleId();

    protected abstract void initializeAdapter();

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onSettingsClick(T item, View view) {
    }

    protected abstract void unpackItems(List<T> selectedItems);

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.fragment_list_view, container, false);
        this.parentView = viewInflate;
        this.recyclerView = (RecyclerView) viewInflate.findViewById(R.id.recycler_view);
        setHasOptionsMenu(false);
        return this.parentView;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        initializeAdapter();
    }

    public void onAdapterReady() {
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.setOnItemClickListener(this);
        this.adapter.showRipples = false;
        this.adapter.showSettings = false;
        this.adapter.showDetails = false;
        setHasOptionsMenu(false);
        this.adapter.notifyDataSetChanged();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelperCallback(this.adapter));
        this.touchHelper = itemTouchHelper;
        itemTouchHelper.attachToRecyclerView(this.recyclerView);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.adapter.notifyDataSetChanged();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        finishActionMode();
    }

    protected void finishActionMode() {
        this.adapter.clearSelection();
        setShowProgressBar(false);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemClick(final T item, MultiSelectionManager selectionManager) {
        unpackItems(new ArrayList(Collections.singletonList(item)));
    }

    public void setShowProgressBar(boolean show) {
        this.parentView.findViewById(R.id.progress_bar).setVisibility(show ? 0 : 8);
        this.recyclerView.setVisibility(show ? 8 : 0);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemLongClick(T item, CheckableViewHolder holder) {
        onItemClick(item, null);
    }
}
