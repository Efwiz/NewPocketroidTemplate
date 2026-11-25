package org.catrobat.catroid.content.actions;

import android.graphics.Color;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.NativeViewBindingManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateScrollViewAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateScrollViewAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010-\u001a\u00020.H\u0016J\u0010\u0010/\u001a\u00020.2\u0006\u00100\u001a\u000201H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u000f\"\u0004\b\u001d\u0010\u0011R\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001c\u0010$\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\bR\u001c\u0010'\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0006\"\u0004\b)\u0010\bR\u001c\u0010*\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0006\"\u0004\b,\u0010\b¨\u00062"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateScrollViewAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "bgColor", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBgColor", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBgColor", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "height", "getHeight", "setHeight", "overScrollMode", "", "getOverScrollMode", "()I", "setOverScrollMode", "(I)V", "padding", "getPadding", "setPadding", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "scrollMode", "getScrollMode", "setScrollMode", "showBars", "getShowBars", "setShowBars", "viewId", "getViewId", "setViewId", "width", "getWidth", "setWidth", "x", "getX", "setX", "y", "getY", "setY", "restart", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateScrollViewAction extends TemporalAction {
    private Formula bgColor;
    private Formula height;
    private Formula padding;
    private Scope scope;
    private Formula showBars;
    private Formula viewId;
    private Formula width;
    private Formula x;
    private Formula y;
    private int scrollMode = 1;
    private int overScrollMode = 1;

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

    public final int getScrollMode() {
        return this.scrollMode;
    }

    public final void setScrollMode(int i) {
        this.scrollMode = i;
    }

    public final Formula getBgColor() {
        return this.bgColor;
    }

    public final void setBgColor(Formula formula) {
        this.bgColor = formula;
    }

    public final Formula getShowBars() {
        return this.showBars;
    }

    public final void setShowBars(Formula formula) {
        this.showBars = formula;
    }

    public final Formula getPadding() {
        return this.padding;
    }

    public final void setPadding(Formula formula) {
        this.padding = formula;
    }

    public final int getOverScrollMode() {
        return this.overScrollMode;
    }

    public final void setOverScrollMode(int i) {
        this.overScrollMode = i;
    }

    public final Formula getX() {
        return this.x;
    }

    public final void setX(Formula formula) {
        this.x = formula;
    }

    public final Formula getY() {
        return this.y;
    }

    public final void setY(Formula formula) {
        this.y = formula;
    }

    public final Formula getWidth() {
        return this.width;
    }

    public final void setWidth(Formula formula) {
        this.width = formula;
    }

    public final Formula getHeight() {
        return this.height;
    }

    public final void setHeight(Formula formula) {
        this.height = formula;
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
        Formula formula2 = this.bgColor;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "#00000000";
        }
        final String str = strInterpretString2;
        Formula formula3 = this.showBars;
        final boolean zInterpretBoolean = formula3 != null ? formula3.interpretBoolean(this.scope) : true;
        Formula formula4 = this.padding;
        Integer numInterpretInteger = formula4 != null ? formula4.interpretInteger(this.scope) : null;
        int iIntValue = numInterpretInteger == null ? 0 : numInterpretInteger.intValue();
        Formula formula5 = this.x;
        Integer numInterpretInteger2 = formula5 != null ? formula5.interpretInteger(this.scope) : null;
        final int iIntValue2 = numInterpretInteger2 == null ? 0 : numInterpretInteger2.intValue();
        Formula formula6 = this.y;
        Integer numInterpretInteger3 = formula6 != null ? formula6.interpretInteger(this.scope) : null;
        final int iIntValue3 = numInterpretInteger3 == null ? 0 : numInterpretInteger3.intValue();
        Formula formula7 = this.width;
        Integer numInterpretInteger4 = formula7 != null ? formula7.interpretInteger(this.scope) : null;
        final int iIntValue4 = numInterpretInteger4 == null ? 300 : numInterpretInteger4.intValue();
        Formula formula8 = this.height;
        Integer numInterpretInteger5 = formula8 != null ? formula8.interpretInteger(this.scope) : null;
        final int iIntValue5 = numInterpretInteger5 == null ? 300 : numInterpretInteger5.intValue();
        final int i = iIntValue;
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.CreateScrollViewAction$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CreateScrollViewAction.update$lambda$1(stageActivity, str, i, this, zInterpretBoolean, iIntValue4, iIntValue5, strInterpretString, iIntValue2, iIntValue3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$1(StageActivity stage, String bgHex, int i, CreateScrollViewAction this$0, boolean z, int i2, int i3, String id2, int i4, int i5) {
        Intrinsics.checkNotNullParameter(stage, "$stage");
        Intrinsics.checkNotNullParameter(bgHex, "$bgHex");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(id2, "$id");
        StageActivity stageActivity = stage;
        FrameLayout frameLayout = new FrameLayout(stageActivity);
        int i6 = 0;
        try {
            frameLayout.setBackgroundColor(Color.parseColor(bgHex));
        } catch (Exception unused) {
            frameLayout.setBackgroundColor(0);
        }
        FrameLayout frameLayout2 = new FrameLayout(stageActivity);
        frameLayout2.setTag("scroll_content");
        frameLayout2.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        frameLayout2.setPadding(i, i, i, i);
        HorizontalScrollView horizontalScrollView = frameLayout2;
        int i7 = this$0.overScrollMode;
        if (i7 != 0) {
            i6 = i7 != 2 ? 1 : 2;
        }
        int i8 = this$0.scrollMode;
        if (i8 == 2 || i8 == 3) {
            HorizontalScrollView horizontalScrollView2 = new HorizontalScrollView(stageActivity);
            horizontalScrollView2.setFillViewport(true);
            horizontalScrollView2.setHorizontalScrollBarEnabled(z);
            horizontalScrollView2.setOverScrollMode(i6);
            horizontalScrollView2.addView(horizontalScrollView);
            horizontalScrollView = horizontalScrollView2;
        }
        int i9 = this$0.scrollMode;
        if (i9 == 1 || i9 == 3) {
            ScrollView scrollView = new ScrollView(stageActivity);
            scrollView.setFillViewport(true);
            scrollView.setVerticalScrollBarEnabled(z);
            scrollView.setOverScrollMode(i6);
            scrollView.addView(horizontalScrollView);
            horizontalScrollView = scrollView;
        }
        frameLayout.addView(horizontalScrollView, new FrameLayout.LayoutParams(-1, -1));
        frameLayout.setVisibility(NativeViewBindingManager.getDefaultVisibility());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i2, i3);
        layoutParams.leftMargin = i4;
        layoutParams.topMargin = i5;
        stage.addViewToStage(id2, frameLayout, layoutParams);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
    }
}
