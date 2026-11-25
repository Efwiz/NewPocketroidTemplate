package org.catrobat.catroid.ui.recyclerview.backpack;

import android.util.Log;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.SceneAdapter;
import org.catrobat.catroid.ui.recyclerview.controller.SceneController;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/backpack/BackpackSceneFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BackpackSceneFragment extends BackpackRecyclerViewFragment<Scene> {
    public static final String TAG = "BackpackSceneFragment";
    private SceneController sceneController = new SceneController();

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_scenes;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void initializeAdapter() {
        List<Scene> scenes = BackpackListManager.getInstance().getScenes();
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_SCENES_PREFERENCE_KEY;
        this.hasDetails = true;
        this.adapter = new SceneAdapter(scenes);
        onAdapterReady();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void unpackItems(List<Scene> selectedItems) {
        setShowProgressBar(true);
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Iterator<Scene> it = selectedItems.iterator();
        int i = 0;
        while (it.hasNext()) {
            try {
                currentProject.addScene(this.sceneController.unpack(it.next(), currentProject));
                i++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (i > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.unpacked_scenes, i, Integer.valueOf(i)));
            getActivity().finish();
        }
        finishActionMode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void deleteItems(List<Scene> list) {
        setShowProgressBar(true);
        for (Scene scene : list) {
            try {
                this.sceneController.delete(scene);
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
            this.adapter.remove((T) scene);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_scenes, list.size(), Integer.valueOf(list.size())));
        BackpackListManager.getInstance().saveBackpack();
        finishActionMode();
        if (this.adapter.getItems().isEmpty()) {
            getActivity().finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    public String getItemName(Scene item) {
        return item.getName();
    }
}
