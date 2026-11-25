package org.catrobat.catroid.ui.fragment;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.ui.workspace.WorkspaceLayout;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/AddBrickFragmentKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: AddBrickFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b¨\u0006\f"}, d2 = {"addBrickToScript", "", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "activity", "Landroid/app/Activity;", "addBrickListener", "Lorg/catrobat/catroid/ui/fragment/AddBrickFragment$OnAddBrickListener;", "parentFragmentManager", "Landroidx/fragment/app/FragmentManager;", "tag", "", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AddBrickFragmentKt {
    public static final void addBrickToScript(Brick brick, Activity activity, AddBrickFragment.OnAddBrickListener onAddBrickListener, FragmentManager parentFragmentManager, String tag) {
        Intrinsics.checkNotNullParameter(brick, "brick");
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(parentFragmentManager, "parentFragmentManager");
        Intrinsics.checkNotNullParameter(tag, "tag");
        if (ProjectManager.getInstance().getCurrentProject().isCastProject() && CastManager.unsupportedBricks.contains(brick.getClass())) {
            ToastUtil.showError(activity, R.string.error_unsupported_bricks_chromecast);
            return;
        }
        try {
            Brick brickClone = brick.clone();
            Intrinsics.checkNotNullExpressionValue(brickClone, "clone(...)");
            if (onAddBrickListener != null) {
                onAddBrickListener.addBrick(brickClone);
            }
            SnackbarUtil.showHintSnackbar(activity, R.string.hint_scripts);
            View viewFindViewById = activity.findViewById(R.id.workspace_layout);
            if (viewFindViewById != null && viewFindViewById.getVisibility() == 0) {
                WorkspaceLayout workspaceLayout = viewFindViewById instanceof WorkspaceLayout ? (WorkspaceLayout) viewFindViewById : null;
                if (workspaceLayout != null) {
                    workspaceLayout.removeWindow(tag, false);
                    workspaceLayout.removeWindow(BrickCategoryFragment.BRICK_CATEGORY_FRAGMENT_TAG, false);
                    return;
                }
                return;
            }
            FragmentTransaction fragmentTransactionBeginTransaction = parentFragmentManager.beginTransaction();
            Intrinsics.checkNotNullExpressionValue(fragmentTransactionBeginTransaction, "beginTransaction(...)");
            Fragment fragmentFindFragmentByTag = parentFragmentManager.findFragmentByTag(BrickCategoryFragment.BRICK_CATEGORY_FRAGMENT_TAG);
            if (fragmentFindFragmentByTag != null) {
                fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag);
                parentFragmentManager.popBackStack();
            }
            Fragment fragmentFindFragmentByTag2 = parentFragmentManager.findFragmentByTag(tag);
            if (fragmentFindFragmentByTag2 != null) {
                fragmentTransactionBeginTransaction.remove(fragmentFindFragmentByTag2);
                parentFragmentManager.popBackStack();
            }
            fragmentTransactionBeginTransaction.commit();
        } catch (CloneNotSupportedException e) {
            Log.e(tag, e.getLocalizedMessage());
            ToastUtil.showError(activity, R.string.error_adding_brick);
        }
    }
}
