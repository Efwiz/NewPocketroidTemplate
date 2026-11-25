package org.catrobat.catroid.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.retrofit.models.ProjectResponse;
import org.catrobat.catroid.ui.recyclerview.FeaturedProjectCallback;
import org.catrobat.catroid.utils.ExtensionsKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/HorizontalProjectResponseAdapter.class
  classes14.dex
 */
/* JADX INFO: compiled from: HorizontalProjectResponseAdapter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0018B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000e\u001a\u00020\nH\u0016J\u001c\u0010\u000f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nH\u0016J\u000e\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0005J\u0014\u0010\u0015\u001a\u00020\f2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/adapter/HorizontalProjectResponseAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lorg/catrobat/catroid/ui/recyclerview/adapter/HorizontalProjectResponseAdapter$ViewHolder;", "()V", "callback", "Lorg/catrobat/catroid/ui/recyclerview/FeaturedProjectCallback;", "data", "", "Lorg/catrobat/catroid/retrofit/models/ProjectResponse;", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setCallback", "featuredProjectCallback", "setItems", FirebaseAnalytics.Param.ITEMS, "", "ViewHolder", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HorizontalProjectResponseAdapter extends RecyclerView.Adapter<ViewHolder> {
    private FeaturedProjectCallback callback;
    private List<ProjectResponse> data = new ArrayList();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/HorizontalProjectResponseAdapter$ViewHolder.class
      classes14.dex
     */
    /* JADX INFO: compiled from: HorizontalProjectResponseAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/adapter/HorizontalProjectResponseAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lorg/catrobat/catroid/ui/recyclerview/adapter/HorizontalProjectResponseAdapter;Landroid/view/View;)V", "imageView", "Landroid/widget/ImageView;", "getImageView", "()Landroid/widget/ImageView;", "setImageView", "(Landroid/widget/ImageView;)V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        final /* synthetic */ HorizontalProjectResponseAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ViewHolder(HorizontalProjectResponseAdapter horizontalProjectResponseAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = horizontalProjectResponseAdapter;
            View viewFindViewById = itemView.findViewById(R.id.project_image_view);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
            this.imageView = (ImageView) viewFindViewById;
        }

        public final ImageView getImageView() {
            return this.imageView;
        }

        public final void setImageView(ImageView imageView) {
            Intrinsics.checkNotNullParameter(imageView, "<set-?>");
            this.imageView = imageView;
        }
    }

    public final void setCallback(FeaturedProjectCallback featuredProjectCallback) {
        Intrinsics.checkNotNullParameter(featuredProjectCallback, "featuredProjectCallback");
        this.callback = featuredProjectCallback;
    }

    public final void setItems(List<ProjectResponse> items) {
        Intrinsics.checkNotNullParameter(items, "items");
        this.data.clear();
        this.data.addAll(items);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_picture_listitem, parent, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        return new ViewHolder(this, viewInflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        final ProjectResponse projectResponse = this.data.get(position);
        ExtensionsKt.loadImageFromUrl(holder.getImageView(), projectResponse.getScreenshotSmall());
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.adapter.HorizontalProjectResponseAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HorizontalProjectResponseAdapter.onBindViewHolder$lambda$0(this.f$0, projectResponse, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(HorizontalProjectResponseAdapter this$0, ProjectResponse item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        FeaturedProjectCallback featuredProjectCallback = this$0.callback;
        if (featuredProjectCallback == null) {
            Intrinsics.throwUninitializedPropertyAccessException("callback");
            featuredProjectCallback = null;
        }
        featuredProjectCallback.onFeatureProjectClicked(item.getProjectUrl());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.data.size();
    }
}
