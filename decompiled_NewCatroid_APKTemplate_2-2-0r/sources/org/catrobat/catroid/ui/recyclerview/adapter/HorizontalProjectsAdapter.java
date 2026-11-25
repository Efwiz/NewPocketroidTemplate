package org.catrobat.catroid.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.ProjectData;
import org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader;
import org.catrobat.catroid.ui.recyclerview.ProjectListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/HorizontalProjectsAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HorizontalProjectsAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final int THUMBNAIL_SIZE = 150;
    private List<ProjectData> items;
    private ProjectListener listener;

    public HorizontalProjectsAdapter(ProjectListener listener) {
        this.listener = listener;
    }

    public void setItems(List<ProjectData> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.project_picture_listitem, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProjectData projectData = this.items.get(position);
        ProjectAndSceneScreenshotLoader projectAndSceneScreenshotLoader = new ProjectAndSceneScreenshotLoader(150, 150);
        projectAndSceneScreenshotLoader.loadAndShowScreenshot(projectData.getName(), projectAndSceneScreenshotLoader.getScreenshotSceneName(projectData.getDirectory()), false, holder.image);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<ProjectData> list = this.items;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/HorizontalProjectsAdapter$ViewHolder.class
      classes14.dex
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.project_image_view);
            itemView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            HorizontalProjectsAdapter.this.listener.onProjectClick((ProjectData) HorizontalProjectsAdapter.this.items.get(getAdapterPosition()));
        }
    }
}
