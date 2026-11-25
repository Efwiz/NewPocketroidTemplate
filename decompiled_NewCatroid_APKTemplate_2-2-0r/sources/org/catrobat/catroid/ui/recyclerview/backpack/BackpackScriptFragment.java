package org.catrobat.catroid.ui.recyclerview.backpack;

import android.util.Log;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.ScriptAdapter;
import org.catrobat.catroid.ui.recyclerview.controller.ScriptController;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/backpack/BackpackScriptFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BackpackScriptFragment extends BackpackRecyclerViewFragment<String> {
    public static final String TAG = "BackpackScriptFragment";
    private ScriptController scriptController = new ScriptController();

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_scripts;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    public String getItemName(String item) {
        return item;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void initializeAdapter() {
        this.adapter = new ScriptAdapter(BackpackListManager.getInstance().getBackpackedScriptGroups());
        onAdapterReady();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void unpackItems(List<String> selectedItems) {
        setShowProgressBar(true);
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        int i = 0;
        for (String str : selectedItems) {
            List<UserDefinedBrick> list = BackpackListManager.getInstance().getBackpackedUserDefinedBricks().get(str);
            if (list != null) {
                currentSprite.addClonesOfUserDefinedBrickList(list);
            }
            Iterator<Script> it = BackpackListManager.getInstance().getBackpackedScripts().get(str).iterator();
            while (it.hasNext()) {
                try {
                    this.scriptController.unpack(str, it.next(), currentSprite);
                    i++;
                } catch (CloneNotSupportedException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        }
        if (i > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.unpacked_scripts, i, Integer.valueOf(i)));
            getActivity().finish();
        }
        finishActionMode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment
    protected void deleteItems(List<String> list) {
        setShowProgressBar(true);
        for (String str : list) {
            BackpackListManager.getInstance().removeItemFromScriptBackPack(str);
            this.adapter.remove((T) str);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_scripts, list.size(), Integer.valueOf(list.size())));
        BackpackListManager.getInstance().saveBackpack();
        finishActionMode();
        if (this.adapter.getItems().isEmpty()) {
            getActivity().finish();
        }
    }
}
