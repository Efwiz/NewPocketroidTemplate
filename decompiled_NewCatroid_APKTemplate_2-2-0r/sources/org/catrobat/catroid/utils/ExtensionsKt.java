package org.catrobat.catroid.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.retrofit.models.ProjectCategoryWithResponses;
import org.catrobat.catroid.retrofit.models.ProjectResponse;
import org.catrobat.catroid.retrofit.models.ProjectResponseApi;
import org.catrobat.catroid.retrofit.models.ProjectsCategory;
import org.catrobat.catroid.retrofit.models.ProjectsCategoryApi;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ExtensionsKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: Extensions.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000P\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u0012\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0004\u001a\u0012\u0010\u0007\u001a\u00020\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b\u001a\u0012\u0010\f\u001a\u00020\b*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f\u001a\n\u0010\u0010\u001a\u00020\u0011*\u00020\u0002\u001a\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0013*\b\u0012\u0004\u0012\u00020\u00020\u0014\u001a\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0014*\b\u0012\u0004\u0012\u00020\u00170\u00142\u0006\u0010\u0018\u001a\u00020\u000b¨\u0006\u0019"}, d2 = {"convertToProjectsCategory", "Lorg/catrobat/catroid/retrofit/models/ProjectsCategory;", "Lorg/catrobat/catroid/retrofit/models/ProjectsCategoryApi;", "dpToPx", "", "Landroid/content/Context;", "dp", "loadImageFromUrl", "", "Landroid/widget/ImageView;", "url", "", "setVisibleOrGone", "Landroid/view/View;", "show", "", "toProjectCategoryWithResponses", "Lorg/catrobat/catroid/retrofit/models/ProjectCategoryWithResponses;", "toProjectCategoryWithResponsesList", "", "", "toProjectResponsesList", "Lorg/catrobat/catroid/retrofit/models/ProjectResponse;", "Lorg/catrobat/catroid/retrofit/models/ProjectResponseApi;", "projectType", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExtensionsKt {
    public static final void loadImageFromUrl(ImageView imageView, String url) {
        Intrinsics.checkNotNullParameter(imageView, "<this>");
        Intrinsics.checkNotNullParameter(url, "url");
        RequestBuilder<Drawable> requestBuilderLoad = Glide.with(imageView.getContext()).load(url);
        new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        requestBuilderLoad.centerInside().transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
    }

    public static final float dpToPx(Context context, float f) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return f * (context.getResources().getDisplayMetrics().xdpi / 160);
    }

    public static final void setVisibleOrGone(View view, boolean z) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setVisibility(z ? 0 : 8);
    }

    public static final List<ProjectResponse> toProjectResponsesList(List<ProjectResponseApi> list, String projectType) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(projectType, "projectType");
        List<ProjectResponseApi> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (Iterator it = list2.iterator(); it.hasNext(); it = it) {
            ProjectResponseApi projectResponseApi = (ProjectResponseApi) it.next();
            arrayList.add(new ProjectResponse(projectResponseApi.getId(), projectResponseApi.getName(), projectResponseApi.getAuthor(), projectResponseApi.getDescription(), projectResponseApi.getVersion(), projectResponseApi.getViews(), projectResponseApi.getDownload(), projectResponseApi.getPrivate(), projectResponseApi.getFlavor(), projectResponseApi.getTags(), projectResponseApi.getUploaded(), projectResponseApi.getUploaded_string(), projectResponseApi.getScreenshot_large(), projectResponseApi.getScreenshot_small(), projectResponseApi.getProject_url(), projectResponseApi.getDownload_url(), projectResponseApi.getFilesize(), projectType));
        }
        return CollectionsKt.toMutableList((Collection) arrayList);
    }

    public static final ProjectsCategory convertToProjectsCategory(ProjectsCategoryApi projectsCategoryApi) {
        Intrinsics.checkNotNullParameter(projectsCategoryApi, "<this>");
        return new ProjectsCategory(projectsCategoryApi.getType(), projectsCategoryApi.getName());
    }

    public static final ProjectCategoryWithResponses toProjectCategoryWithResponses(ProjectsCategoryApi projectsCategoryApi) {
        Intrinsics.checkNotNullParameter(projectsCategoryApi, "<this>");
        return new ProjectCategoryWithResponses(convertToProjectsCategory(projectsCategoryApi), toProjectResponsesList(projectsCategoryApi.getProjectsList(), projectsCategoryApi.getType()));
    }

    public static final List<ProjectCategoryWithResponses> toProjectCategoryWithResponsesList(List<ProjectsCategoryApi> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        List<ProjectsCategoryApi> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(toProjectCategoryWithResponses((ProjectsCategoryApi) it.next()));
        }
        return CollectionsKt.toMutableList((Collection) arrayList);
    }
}
