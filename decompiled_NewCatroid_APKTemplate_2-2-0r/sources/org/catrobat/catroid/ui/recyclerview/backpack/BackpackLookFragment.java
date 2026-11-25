package org.catrobat.catroid.ui.recyclerview.backpack;

import android.util.Log;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.LookAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.controller.LookController;
import org.catrobat.catroid.utils.ToastUtil;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/backpack/BackpackLookFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BackpackLookFragment extends BackpackRecyclerViewFragment<LookData> {
    public static final String TAG = "BackpackLookFragment";
    public final ProjectManager projectManager = (ProjectManager) KoinJavaComponent.inject(ProjectManager.class).getValue();
    private LookController lookController = new LookController();

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_looks;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void initializeAdapter() {
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_LOOKS_PREFERENCE_KEY;
        this.hasDetails = true;
        this.adapter = new LookAdapter(BackpackListManager.getInstance().getBackpackedLooks());
        onAdapterReady();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void unpackItems(List<LookData> selectedItems) {
        setShowProgressBar(true);
        Sprite currentSprite = this.projectManager.getCurrentSprite();
        Iterator<LookData> it = selectedItems.iterator();
        int i = 0;
        while (it.hasNext()) {
            try {
                currentSprite.getLookList().add(this.lookController.unpack(it.next(), ProjectManager.getInstance().getCurrentlyEditedScene(), currentSprite));
                i++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (i > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.unpacked_looks, i, Integer.valueOf(i)));
            getActivity().finish();
        }
        finishActionMode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void deleteItems(List<LookData> list) {
        setShowProgressBar(true);
        for (LookData lookData : list) {
            try {
                this.lookController.delete(lookData);
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
            this.adapter.remove((T) lookData);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_looks, list.size(), Integer.valueOf(list.size())));
        BackpackListManager.getInstance().saveBackpack();
        finishActionMode();
        if (this.adapter.getItems().isEmpty()) {
            getActivity().finish();
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment, org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemClick(final LookData item, MultiSelectionManager selectionManager) {
        super.onItemClick(item, selectionManager);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    public String getItemName(LookData item) {
        return item.getName();
    }
}
