package org.catrobat.catroid.editor;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.raptor.GameObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/HierarchyAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HierarchyAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final OnItemClickListener listener;
    private final List<HierarchyItem> items = new ArrayList();
    private GameObject selectedObject = null;
    private List<HierarchyItem> fullList = new ArrayList();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/HierarchyAdapter$OnItemClickListener.class
      classes14.dex
     */
    public interface OnItemClickListener {
        void onItemClick(GameObject gameObject);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/HierarchyAdapter$HierarchyItem.class
      classes14.dex
     */
    public static class HierarchyItem {
        public final String displayName;
        public final GameObject gameObject;

        public HierarchyItem(GameObject gameObject, String displayName) {
            this.gameObject = gameObject;
            this.displayName = displayName;
        }
    }

    public HierarchyAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item_1_white_text, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        HierarchyItem hierarchyItem = this.items.get(position);
        holder.textView.setText(hierarchyItem.displayName);
        holder.bind(hierarchyItem.gameObject, this.listener);
        if (hierarchyItem.gameObject.isActive) {
            holder.textView.setTextColor(-1);
        } else {
            holder.textView.setTextColor(-2130706433);
        }
        if (hierarchyItem.gameObject == this.selectedObject) {
            holder.itemView.setBackgroundColor(Color.parseColor("#559999FF"));
        } else {
            holder.itemView.setBackgroundColor(0);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    public void updateData(List<HierarchyItem> newItems) {
        this.fullList = new ArrayList(newItems);
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    public List<HierarchyItem> getItems() {
        return this.items;
    }

    public void setSelectedObject(GameObject go) {
        if (this.selectedObject == go) {
            return;
        }
        this.selectedObject = go;
        notifyDataSetChanged();
    }

    public void filter(String query) {
        this.items.clear();
        if (query.isEmpty()) {
            this.items.addAll(this.fullList);
        } else {
            String strTrim = query.toLowerCase().trim();
            for (HierarchyItem hierarchyItem : this.fullList) {
                if (hierarchyItem.gameObject.f592name.toLowerCase().contains(strTrim)) {
                    this.items.add(hierarchyItem);
                }
            }
        }
        notifyDataSetChanged();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/HierarchyAdapter$ViewHolder.class
      classes14.dex
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(android.R.id.text1);
            itemView.setClickable(true);
            itemView.setFocusable(true);
        }

        public void bind(final GameObject gameObject, final OnItemClickListener listener) {
            this.itemView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.HierarchyAdapter$ViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    listener.onItemClick(gameObject);
                }
            });
        }
    }
}
