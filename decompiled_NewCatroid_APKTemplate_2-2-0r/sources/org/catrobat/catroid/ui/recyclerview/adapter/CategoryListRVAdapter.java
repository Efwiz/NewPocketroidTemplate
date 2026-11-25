package org.catrobat.catroid.ui.recyclerview.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.recyclerview.viewholder.ViewHolder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/CategoryListRVAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CategoryListRVAdapter extends RecyclerView.Adapter<ViewHolder> {
    public static final int COLLISION = 1;
    public static final int DEFAULT = 0;
    public static final int EV3 = 3;
    public static final int NXT = 2;
    private static final Set<String> globalCollapsedHeaders = new HashSet();
    private static boolean isFirstLaunch = true;
    private Set<String> activeCollapsedHeaders;
    private OnItemClickListener onItemClickListener;
    private List<CategoryListItem> originalItems;
    private List<AdapterItem> visibleItems;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/CategoryListRVAdapter$CategoryListItemType.class
      classes14.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface CategoryListItemType {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/CategoryListRVAdapter$OnItemClickListener.class
      classes14.dex
     */
    public interface OnItemClickListener {
        void onItemClick(CategoryListItem item);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/CategoryListRVAdapter$CategoryListItem.class
      classes14.dex
     */
    public static class CategoryListItem {
        public String header;
        public int nameResId;
        public String text;
        public int type;
        public boolean isCustomFunction = false;
        public String customFunctionName = null;

        public CategoryListItem(int nameResId, String text, int type) {
            if (nameResId == R.string.formula_editor_function_regex_assistant) {
                this.text = "\t\t\t\t\t" + text;
            } else {
                this.text = text;
            }
            this.nameResId = nameResId;
            this.type = type;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/CategoryListRVAdapter$AdapterItem.class
      classes14.dex
     */
    static class AdapterItem {
        CategoryListItem categoryListItem;
        String headerTitle;
        boolean isHeader = true;

        AdapterItem(String headerTitle) {
            this.headerTitle = headerTitle;
        }

        AdapterItem(CategoryListItem item) {
            this.categoryListItem = item;
        }
    }

    public CategoryListRVAdapter(List<CategoryListItem> items) {
        this(items, false);
    }

    public CategoryListRVAdapter(List<CategoryListItem> items, boolean isSearchMode) {
        this.visibleItems = new ArrayList();
        this.originalItems = items;
        if (isFirstLaunch && !isSearchMode) {
            for (CategoryListItem categoryListItem : items) {
                if (categoryListItem.header != null) {
                    globalCollapsedHeaders.add(categoryListItem.header);
                }
            }
            isFirstLaunch = false;
        }
        if (isSearchMode) {
            this.activeCollapsedHeaders = new HashSet();
        } else {
            this.activeCollapsedHeaders = globalCollapsedHeaders;
        }
        buildVisibleItems();
    }

    private void buildVisibleItems() {
        this.visibleItems.clear();
        String str = null;
        for (CategoryListItem categoryListItem : this.originalItems) {
            if (categoryListItem.header != null) {
                str = categoryListItem.header;
                this.visibleItems.add(new AdapterItem(str));
            }
            if (str == null || !this.activeCollapsedHeaders.contains(str)) {
                CategoryListItem categoryListItem2 = new CategoryListItem(categoryListItem.nameResId, categoryListItem.text, categoryListItem.type);
                categoryListItem2.isCustomFunction = categoryListItem.isCustomFunction;
                categoryListItem2.customFunctionName = categoryListItem.customFunctionName;
                categoryListItem2.header = null;
                this.visibleItems.add(new AdapterItem(categoryListItem2));
            }
        }
    }

    private void toggleCollapse(String header, int position) {
        boolean zContains = this.activeCollapsedHeaders.contains(header);
        ArrayList arrayList = new ArrayList();
        String str = null;
        for (CategoryListItem categoryListItem : this.originalItems) {
            if (categoryListItem.header != null) {
                str = categoryListItem.header;
            }
            if (header.equals(str)) {
                CategoryListItem categoryListItem2 = new CategoryListItem(categoryListItem.nameResId, categoryListItem.text, categoryListItem.type);
                categoryListItem2.isCustomFunction = categoryListItem.isCustomFunction;
                categoryListItem2.customFunctionName = categoryListItem.customFunctionName;
                categoryListItem2.header = null;
                arrayList.add(new AdapterItem(categoryListItem2));
            }
        }
        int size = arrayList.size();
        if (zContains) {
            this.activeCollapsedHeaders.remove(header);
            int i = position + 1;
            this.visibleItems.addAll(i, arrayList);
            notifyItemChanged(position);
            notifyItemRangeInserted(i, size);
            return;
        }
        this.activeCollapsedHeaders.add(header);
        for (int i2 = 0; i2 < size; i2++) {
            this.visibleItems.remove(position + 1);
        }
        notifyItemChanged(position);
        notifyItemRangeRemoved(position + 1, size);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AdapterItem adapterItem = this.visibleItems.get(position);
        float f = holder.itemView.getContext().getResources().getDisplayMetrics().density;
        if (adapterItem.isHeader) {
            TextView textView = (TextView) holder.itemView.findViewById(R.id.headline);
            if (textView != null) {
                textView.setVisibility(0);
                textView.setText(adapterItem.headerTitle.toUpperCase(Locale.getDefault()) + "   " + (this.activeCollapsedHeaders.contains(adapterItem.headerTitle) ? "▶" : "▼"));
                textView.setTextSize(15.0f);
                textView.setTypeface(Typeface.create("sans-serif-medium", 0));
                textView.setBackgroundColor(218103808);
                int i = (int) (16.0f * f);
                int i2 = (int) (f * 12.0f);
                textView.setPadding(i, i2, i, i2);
            }
            if (holder.title != null) {
                holder.title.setVisibility(8);
                View view = (View) holder.title.getParent();
                if (view != null && view != holder.itemView) {
                    view.setVisibility(8);
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.adapter.CategoryListRVAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m5741x2158eb4e(holder, adapterItem, view2);
                }
            });
            return;
        }
        final CategoryListItem categoryListItem = adapterItem.categoryListItem;
        TextView textView2 = (TextView) holder.itemView.findViewById(R.id.headline);
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
        if (holder.title != null) {
            holder.title.setVisibility(0);
            View view2 = (View) holder.title.getParent();
            if (view2 != null && view2 != holder.itemView) {
                view2.setVisibility(0);
            }
            holder.title.setText(categoryListItem.text);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.adapter.CategoryListRVAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.m5742xbdc6e7ad(categoryListItem, view3);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$0$org-catrobat-catroid-ui-recyclerview-adapter-CategoryListRVAdapter, reason: not valid java name */
    /* synthetic */ void m5741x2158eb4e(ViewHolder viewHolder, AdapterItem adapterItem, View view) {
        int adapterPosition = viewHolder.getAdapterPosition();
        if (adapterPosition != -1) {
            toggleCollapse(adapterItem.headerTitle, adapterPosition);
        }
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$1$org-catrobat-catroid-ui-recyclerview-adapter-CategoryListRVAdapter, reason: not valid java name */
    /* synthetic */ void m5742xbdc6e7ad(CategoryListItem categoryListItem, View view) {
        OnItemClickListener onItemClickListener = this.onItemClickListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(categoryListItem);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return this.visibleItems.get(position).isHeader ? R.layout.view_holder_category_list_item_with_headline : R.layout.view_holder_category_list_item;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.visibleItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
