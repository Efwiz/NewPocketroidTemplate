package org.catrobat.catroid.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.retrofit.models.ProjectCategoryWithResponses;
import org.catrobat.catroid.ui.recyclerview.CategoryTitleCallback;
import org.catrobat.catroid.ui.recyclerview.FeaturedProjectCallback;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/CategoriesAdapter.class
  classes14.dex
 */
/* JADX INFO: compiled from: CategoriesAdapter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0006\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u001cB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u000b\u001a\u00020\fH\u0016J\u001c\u0010\r\u001a\u00020\u000e2\n\u0010\u000f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0010\u001a\u00020\fH\u0016J\u001c\u0010\u0011\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fH\u0016J\u0014\u0010\u0015\u001a\u00020\u000e2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u0017J\u000e\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u0005J\u000e\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/adapter/CategoriesAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lorg/catrobat/catroid/ui/recyclerview/adapter/CategoriesAdapter$ViewHolder;", "()V", "categoryTitleCallback", "Lorg/catrobat/catroid/ui/recyclerview/CategoryTitleCallback;", "data", "", "Lorg/catrobat/catroid/retrofit/models/ProjectCategoryWithResponses;", "projectCallback", "Lorg/catrobat/catroid/ui/recyclerview/FeaturedProjectCallback;", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setItems", FirebaseAnalytics.Param.ITEMS, "", "setOnCategoryTitleClickCallback", "callback", "setOnProjectClickCallback", "featuredProjectCallback", "ViewHolder", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CategoriesAdapter extends RecyclerView.Adapter<ViewHolder> {
    private CategoryTitleCallback categoryTitleCallback;
    private List<ProjectCategoryWithResponses> data = new ArrayList();
    private FeaturedProjectCallback projectCallback;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/CategoriesAdapter$ViewHolder.class
      classes14.dex
     */
    /* JADX INFO: compiled from: CategoriesAdapter.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/adapter/CategoriesAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lorg/catrobat/catroid/ui/recyclerview/adapter/CategoriesAdapter;Landroid/view/View;)V", "categoryRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getCategoryRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "categoryTitle", "Landroid/widget/TextView;", "getCategoryTitle", "()Landroid/widget/TextView;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class ViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView categoryRecyclerView;
        private final TextView categoryTitle;
        final /* synthetic */ CategoriesAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ViewHolder(CategoriesAdapter categoriesAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = categoriesAdapter;
            View viewFindViewById = itemView.findViewById(R.id.categoryTitle);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
            this.categoryTitle = (TextView) viewFindViewById;
            View viewFindViewById2 = itemView.findViewById(R.id.categoryRecyclerView);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
            this.categoryRecyclerView = (RecyclerView) viewFindViewById2;
        }

        public final TextView getCategoryTitle() {
            return this.categoryTitle;
        }

        public final RecyclerView getCategoryRecyclerView() {
            return this.categoryRecyclerView;
        }
    }

    public final void setOnProjectClickCallback(FeaturedProjectCallback featuredProjectCallback) {
        Intrinsics.checkNotNullParameter(featuredProjectCallback, "featuredProjectCallback");
        this.projectCallback = featuredProjectCallback;
    }

    public final void setOnCategoryTitleClickCallback(CategoryTitleCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.categoryTitleCallback = callback;
    }

    public final void setItems(List<ProjectCategoryWithResponses> items) {
        Intrinsics.checkNotNullParameter(items, "items");
        this.data.clear();
        this.data.addAll(items);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_category_item_view, parent, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        return new ViewHolder(this, viewInflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        final ProjectCategoryWithResponses projectCategoryWithResponses = this.data.get(position);
        TextView categoryTitle = holder.getCategoryTitle();
        categoryTitle.setText(projectCategoryWithResponses.getCategory().getName());
        categoryTitle.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.adapter.CategoriesAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CategoriesAdapter.onBindViewHolder$lambda$1$lambda$0(this.f$0, projectCategoryWithResponses, view);
            }
        });
        RecyclerView categoryRecyclerView = holder.getCategoryRecyclerView();
        categoryRecyclerView.setHasFixedSize(true);
        HorizontalProjectResponseAdapter horizontalProjectResponseAdapter = new HorizontalProjectResponseAdapter();
        horizontalProjectResponseAdapter.setItems(projectCategoryWithResponses.getProjectsList());
        FeaturedProjectCallback featuredProjectCallback = this.projectCallback;
        if (featuredProjectCallback == null) {
            Intrinsics.throwUninitializedPropertyAccessException("projectCallback");
            featuredProjectCallback = null;
        }
        horizontalProjectResponseAdapter.setCallback(featuredProjectCallback);
        categoryRecyclerView.setAdapter(horizontalProjectResponseAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$1$lambda$0(CategoriesAdapter this$0, ProjectCategoryWithResponses item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        CategoryTitleCallback categoryTitleCallback = this$0.categoryTitleCallback;
        if (categoryTitleCallback == null) {
            Intrinsics.throwUninitializedPropertyAccessException("categoryTitleCallback");
            categoryTitleCallback = null;
        }
        categoryTitleCallback.onCategoryTitleClicked(item.getCategory().getType());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.data.size();
    }
}
