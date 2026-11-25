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
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.SpriteAdapter;
import org.catrobat.catroid.ui.recyclerview.controller.SpriteController;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/backpack/BackpackSpriteFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BackpackSpriteFragment extends BackpackRecyclerViewFragment<Sprite> {
    public static final String TAG = "BackpackSpriteFragment";
    private SpriteController spriteController = new SpriteController();

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_sprites;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void initializeAdapter() {
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_SPRITES_PREFERENCE_KEY;
        this.hasDetails = true;
        this.adapter = new SpriteAdapter(BackpackListManager.getInstance().getSprites());
        onAdapterReady();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void unpackItems(List<Sprite> selectedItems) {
        setShowProgressBar(true);
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Scene currentlyEditedScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        Iterator<Sprite> it = selectedItems.iterator();
        int i = 0;
        while (it.hasNext()) {
            try {
                currentlyEditedScene.getSpriteList().add(this.spriteController.unpack(it.next(), currentProject, currentlyEditedScene));
                i++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (i > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.unpacked_sprites, i, Integer.valueOf(i)));
            getActivity().finish();
        }
        finishActionMode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void deleteItems(List<Sprite> list) {
        setShowProgressBar(true);
        for (Sprite sprite : list) {
            this.spriteController.delete(sprite);
            this.adapter.remove((T) sprite);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_sprites, list.size(), Integer.valueOf(list.size())));
        BackpackListManager.getInstance().saveBackpack();
        finishActionMode();
        if (this.adapter.getItems().isEmpty()) {
            getActivity().finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    public String getItemName(Sprite item) {
        return item.getName();
    }
}
