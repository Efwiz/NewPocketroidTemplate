package org.catrobat.catroid.ui.recyclerview.adapter.draganddrop;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/draganddrop/TouchHelperCallback.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TouchHelperCallback extends ItemTouchHelper.Callback {
    public static final String TAG = "TouchHelperCallback";
    private TouchHelperAdapterInterface adapterInterface;

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    public TouchHelperCallback(TouchHelperAdapterInterface adapterInterface) {
        this.adapterInterface = adapterInterface;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(3, 0);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return this.adapterInterface.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != 2) {
            return;
        }
        viewHolder.itemView.setBackgroundResource(R.drawable.button_background_pressed);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundResource(R.drawable.button_background_selector);
    }
}
