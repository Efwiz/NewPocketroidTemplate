package org.catrobat.catroid.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.TouchHelperAdapterInterface;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/RVAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class RVAdapter<T> extends RecyclerView.Adapter<CheckableViewHolder> implements TouchHelperAdapterInterface {
    public static final int MULTIPLE = 2;
    public static final int PAIRS = 1;
    public static final int SINGLE = 0;
    protected List<T> items;
    private OnItemClickListener<T> onItemClickListener;
    private SelectionListener selectionListener;
    public boolean showCheckBoxes = false;
    public boolean showRipples = true;
    public boolean showSettings = true;
    public int selectionMode = 2;
    MultiSelectionManager selectionManager = new MultiSelectionManager();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/RVAdapter$OnItemClickListener.class
      classes14.dex
     */
    public interface OnItemClickListener<T> {
        void onItemClick(T item, MultiSelectionManager selectionManager);

        void onItemLongClick(T item, CheckableViewHolder holder);

        void onSettingsClick(T item, View view);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/RVAdapter$SelectionListener.class
      classes14.dex
     */
    public interface SelectionListener {
        void onSelectionChanged(int selectedItemCnt);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/RVAdapter$SelectionType.class
      classes14.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface SelectionType {
    }

    static /* synthetic */ boolean lambda$onBindViewHolder$4(View view) {
        return true;
    }

    protected RVAdapter(List<T> items) {
        this.items = items;
    }

    public void setSelectionListener(SelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CheckableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CheckableViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_with_checkbox, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final CheckableViewHolder holder, int position) {
        final T t = this.items.get(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5747x7d98a12a(holder, view);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5748x7ecef409(t, view);
            }
        });
        if (holder.settings != null) {
            holder.settings.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m5749x800546e8(t, view);
                }
            });
        }
        holder.checkBox.setVisibility(this.showCheckBoxes ? 0 : 8);
        holder.checkBox.setChecked(this.selectionManager.isPositionSelected(position));
        ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.ic_ripples);
        if (imageView != null && this.showRipples) {
            imageView.setVisibility(0);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter$$ExternalSyntheticLambda3
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return this.f$0.m5750x813b99c7(t, holder, view);
                }
            });
        } else if (imageView != null && !this.showRipples) {
            imageView.setVisibility(8);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter$$ExternalSyntheticLambda4
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return RVAdapter.lambda$onBindViewHolder$4(view);
                }
            });
        }
        ImageButton imageButton = (ImageButton) holder.itemView.findViewById(R.id.settings_button);
        if (imageButton != null && this.showSettings) {
            imageButton.setVisibility(0);
        } else {
            if (imageButton == null || this.showSettings) {
                return;
            }
            imageButton.setVisibility(8);
        }
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$0$org-catrobat-catroid-ui-recyclerview-adapter-RVAdapter, reason: not valid java name */
    /* synthetic */ void m5747x7d98a12a(CheckableViewHolder checkableViewHolder, View view) {
        onCheckBoxClick(checkableViewHolder.getAdapterPosition());
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$1$org-catrobat-catroid-ui-recyclerview-adapter-RVAdapter, reason: not valid java name */
    /* synthetic */ void m5748x7ecef409(Object obj, View view) {
        this.onItemClickListener.onItemClick(obj, this.selectionManager);
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$2$org-catrobat-catroid-ui-recyclerview-adapter-RVAdapter, reason: not valid java name */
    /* synthetic */ void m5749x800546e8(Object obj, View view) {
        this.onItemClickListener.onSettingsClick(obj, view);
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$3$org-catrobat-catroid-ui-recyclerview-adapter-RVAdapter, reason: not valid java name */
    /* synthetic */ boolean m5750x813b99c7(Object obj, CheckableViewHolder checkableViewHolder, View view) {
        this.onItemClickListener.onItemLongClick(obj, checkableViewHolder);
        return true;
    }

    protected void onCheckBoxClick(int position) {
        int i = this.selectionMode;
        if (i == 0) {
            boolean zIsPositionSelected = this.selectionManager.isPositionSelected(position);
            Iterator<Integer> it = this.selectionManager.getSelectedPositions().iterator();
            while (it.hasNext()) {
                int iIntValue = it.next().intValue();
                this.selectionManager.setSelectionTo(false, iIntValue);
                notifyItemChanged(iIntValue);
            }
            this.selectionManager.setSelectionTo(!zIsPositionSelected, position);
            notifyItemChanged(position);
        } else if (i != 1 || this.selectionManager.getSelectedPositions().size() < 2) {
            this.selectionManager.toggleSelection(position);
        } else {
            this.selectionManager.setSelectionTo(false, position);
            notifyItemChanged(position);
        }
        this.selectionListener.onSelectionChanged(this.selectionManager.getSelectedPositions().size());
    }

    public boolean add(int i, T item) {
        this.items.add(i, item);
        notifyItemInserted(this.items.indexOf(item));
        return true;
    }

    public boolean add(T item) {
        if (!this.items.add(item)) {
            return false;
        }
        notifyItemInserted(this.items.indexOf(item));
        return true;
    }

    public boolean remove(T item) {
        if (!this.items.remove(item)) {
            return false;
        }
        notifyItemRemoved(this.items.indexOf(item));
        return true;
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setItems(List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public boolean onItemMove(int sourcePosition, int targetPosition) {
        if (Math.abs(sourcePosition - targetPosition) <= 1) {
            Collections.swap(this.items, sourcePosition, targetPosition);
        } else {
            T t = this.items.get(sourcePosition);
            this.items.remove(t);
            this.items.add(targetPosition, t);
        }
        notifyItemMoved(sourcePosition, targetPosition);
        this.selectionManager.updateSelection(sourcePosition, targetPosition);
        return true;
    }

    public List<T> getSelectedItems() {
        ArrayList arrayList = new ArrayList();
        Iterator<Integer> it = this.selectionManager.getSelectedPositions().iterator();
        while (it.hasNext()) {
            arrayList.add(this.items.get(it.next().intValue()));
        }
        return arrayList;
    }

    public boolean setSelection(T item, boolean selection) {
        if (this.items.indexOf(item) == -1) {
            return false;
        }
        this.selectionManager.setSelectionTo(selection, this.items.indexOf(item));
        return true;
    }

    public boolean toggleSelection(T item) {
        if (this.items.indexOf(item) == -1) {
            return false;
        }
        this.selectionManager.toggleSelection(this.items.indexOf(item));
        notifyItemChanged(this.items.indexOf(item));
        return true;
    }

    public void toggleSelection() {
        if (this.selectionManager.getSelectedPositions().size() == getSelectableItemCount()) {
            clearSelection();
        } else {
            selectAll();
        }
        this.selectionListener.onSelectionChanged(this.selectionManager.getSelectedPositions().size());
    }

    public void selectAll() {
        Iterator<T> it = this.items.iterator();
        while (it.hasNext()) {
            this.selectionManager.setSelectionTo(true, this.items.indexOf(it.next()));
        }
        notifyDataSetChanged();
    }

    public void clearSelection() {
        this.selectionManager.clearSelection();
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    public int getSelectableItemCount() {
        return getItemCount();
    }

    public int getSelectedItemCount() {
        return this.selectionManager.getSelectedPositions().size();
    }
}
