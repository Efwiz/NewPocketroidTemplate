package org.catrobat.catroid.ui.recyclerview.adapter;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedViewHolder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/SceneAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SceneAdapter extends ExtendedRVAdapter<Scene> {
    public SceneAdapter(List<Scene> items) {
        super(items);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter
    public void onBindViewHolder(ExtendedViewHolder holder, int position) {
        ProjectAndSceneScreenshotLoader projectAndSceneScreenshotLoader = new ProjectAndSceneScreenshotLoader(holder.itemView.getContext().getResources().getDimensionPixelSize(R.dimen.project_thumbnail_width), holder.itemView.getContext().getResources().getDimensionPixelSize(R.dimen.project_thumbnail_height));
        Scene scene = (Scene) this.items.get(position);
        File directory = ProjectManager.getInstance().getCurrentProject().getDirectory();
        holder.title.setText(scene.getName());
        projectAndSceneScreenshotLoader.loadAndShowScreenshot(directory.getName(), scene.getDirectory().getName(), false, holder.image);
        if (this.showDetails) {
            holder.details.setText(String.format(Locale.getDefault(), holder.itemView.getContext().getString(R.string.scene_details), Integer.valueOf(scene.getSpriteList().size()), Integer.valueOf(getLookCount(scene)), Integer.valueOf(getSoundCount(scene))));
            holder.details.setVisibility(0);
        } else {
            holder.details.setVisibility(8);
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter, org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.TouchHelperAdapterInterface
    public boolean onItemMove(int sourcePosition, int targetPosition) {
        boolean zOnItemMove = super.onItemMove(sourcePosition, targetPosition);
        ProjectManager.getInstance().setCurrentlyEditedScene((Scene) this.items.get(0));
        return zOnItemMove;
    }

    private int getLookCount(Scene scene) {
        Iterator<Sprite> it = scene.getSpriteList().iterator();
        int size = 0;
        while (it.hasNext()) {
            size += it.next().getLookList().size();
        }
        return size;
    }

    private int getSoundCount(Scene scene) {
        Iterator<Sprite> it = scene.getSpriteList().iterator();
        int size = 0;
        while (it.hasNext()) {
            size += it.next().getSoundList().size();
        }
        return size;
    }
}
