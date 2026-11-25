package org.catrobat.catroid.ui.recyclerview.backpack;

import android.util.Log;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.SoundAdapter;
import org.catrobat.catroid.ui.recyclerview.controller.SoundController;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/backpack/BackpackSoundFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BackpackSoundFragment extends BackpackRecyclerViewFragment<SoundInfo> {
    public static final String TAG = "BackpackSoundFragment";
    private SoundController soundController = new SoundController();

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_sounds;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void initializeAdapter() {
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_SOUNDS_PREFERENCE_KEY;
        this.hasDetails = true;
        this.adapter = new SoundAdapter(BackpackListManager.getInstance().getBackpackedSounds());
        onAdapterReady();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void unpackItems(List<SoundInfo> selectedItems) {
        setShowProgressBar(true);
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        Iterator<SoundInfo> it = selectedItems.iterator();
        int i = 0;
        while (it.hasNext()) {
            try {
                currentSprite.getSoundList().add(this.soundController.unpack(it.next(), ProjectManager.getInstance().getCurrentlyEditedScene(), currentSprite));
                i++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (i > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.unpacked_sounds, i, Integer.valueOf(i)));
            getActivity().finish();
        }
        finishActionMode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void deleteItems(List<SoundInfo> list) {
        setShowProgressBar(true);
        for (SoundInfo soundInfo : list) {
            try {
                this.soundController.delete(soundInfo);
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
            this.adapter.remove((T) soundInfo);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_sounds, list.size(), Integer.valueOf(list.size())));
        BackpackListManager.getInstance().saveBackpack();
        finishActionMode();
        if (this.adapter.getItems().isEmpty()) {
            getActivity().finish();
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    public String getItemName(SoundInfo item) {
        return item.getName();
    }
}
