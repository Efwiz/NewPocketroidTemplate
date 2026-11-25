package org.catrobat.catroid.content.actions;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetNativeParentAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetNativeParentAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\b¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetNativeParentAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "parentId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getParentId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setParentId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "viewId", "getViewId", "setViewId", "restart", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetNativeParentAction extends TemporalAction {
    private Formula parentId;
    private Scope scope;
    private Formula viewId;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getViewId() {
        return this.viewId;
    }

    public final void setViewId(Formula formula) {
        this.viewId = formula;
    }

    public final Formula getParentId() {
        return this.parentId;
    }

    public final void setParentId(Formula formula) {
        this.parentId = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        final StageActivity stageActivity;
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        if (weakReference == null || (stageActivity = weakReference.get()) == null) {
            return;
        }
        Formula formula = this.viewId;
        final String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.parentId;
        final String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "";
        }
        final View viewFromStage = stageActivity.getViewFromStage(strInterpretString);
        if (viewFromStage == null) {
            return;
        }
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.SetNativeParentAction$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SetNativeParentAction.update$lambda$0(viewFromStage, strInterpretString2, stageActivity, strInterpretString);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$0(View childView, String pId, StageActivity stage, String vId) {
        Intrinsics.checkNotNullParameter(childView, "$childView");
        Intrinsics.checkNotNullParameter(pId, "$pId");
        Intrinsics.checkNotNullParameter(stage, "$stage");
        Intrinsics.checkNotNullParameter(vId, "$vId");
        ViewParent parent = childView.getParent();
        ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (viewGroup != null) {
            viewGroup.removeView(childView);
        }
        if (pId.length() == 0) {
            ViewGroup.LayoutParams layoutParams = childView.getLayoutParams();
            FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
            if (layoutParams2 == null) {
                layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
            }
            stage.addViewToStage(vId, childView, layoutParams2);
            return;
        }
        View viewFromStage = stage.getViewFromStage(pId);
        if (viewFromStage instanceof ViewGroup) {
            ViewGroup viewGroup2 = (ViewGroup) viewFromStage.findViewWithTag("scroll_content");
            if (viewGroup2 == null) {
                viewGroup2 = (ViewGroup) viewFromStage;
            }
            viewGroup2.addView(childView, childView.getLayoutParams());
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
    }
}
