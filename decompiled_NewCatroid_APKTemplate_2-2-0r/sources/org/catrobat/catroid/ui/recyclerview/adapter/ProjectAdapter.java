package org.catrobat.catroid.ui.recyclerview.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.ImageView;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.ProjectData;
import org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedViewHolder;
import org.catrobat.catroid.utils.FileMetaDataExtractor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/ProjectAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ProjectAdapter extends ExtendedRVAdapter<ProjectData> {
    public ProjectAdapter(List<ProjectData> items) {
        super(items);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter
    public void onBindViewHolder(ExtendedViewHolder holder, int position) {
        String string;
        Context context = holder.itemView.getContext();
        ProjectAndSceneScreenshotLoader projectAndSceneScreenshotLoader = new ProjectAndSceneScreenshotLoader(context.getResources().getDimensionPixelSize(R.dimen.project_thumbnail_width), context.getResources().getDimensionPixelSize(R.dimen.project_thumbnail_height));
        ProjectData projectData = (ProjectData) this.items.get(position);
        holder.title.setText(projectData.getName());
        projectAndSceneScreenshotLoader.loadAndShowScreenshot(projectData.getDirectory().getName(), projectAndSceneScreenshotLoader.getScreenshotSceneName(projectData.getDirectory()), false, holder.image);
        ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.ic_ripples);
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        if (this.showDetails) {
            Date date = new Date(projectData.getLastUsed());
            if (DateUtils.isToday(projectData.getLastUsed())) {
                string = context.getString(R.string.last_access_today, DateFormat.getTimeInstance(3).format(date));
            } else {
                string = DateFormat.getDateInstance(2).format(date);
            }
            holder.details.setText(String.format(Locale.getDefault(), context.getString(R.string.project_details), string, FileMetaDataExtractor.getSizeAsString(projectData.getDirectory(), context)));
            holder.details.setVisibility(0);
            return;
        }
        holder.details.setVisibility(8);
    }
}
