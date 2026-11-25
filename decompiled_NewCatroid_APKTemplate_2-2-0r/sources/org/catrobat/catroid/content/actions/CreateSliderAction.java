package org.catrobat.catroid.content.actions;

import android.widget.FrameLayout;
import android.widget.SeekBar;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.NativeViewBindingManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateSliderAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateSliderAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010'\u001a\u00020\n2\u0006\u0010(\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020+H\u0016J\b\u0010,\u001a\u00020+H\u0016J\b\u0010-\u001a\u00020+H\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0006\"\u0004\b\r\u0010\bR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0006\"\u0004\b\u0010\u0010\bR\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0006\"\u0004\b\u0013\u0010\bR\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001c\u0010$\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\b¨\u0006."}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateSliderAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "currentValue", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getCurrentValue", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setCurrentValue", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "finished", "", "height", "getHeight", "setHeight", "maxValue", "getMaxValue", "setMaxValue", "minValue", "getMinValue", "setMinValue", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "viewId", "getViewId", "setViewId", "width", "getWidth", "setWidth", "x", "getX", "setX", "y", "getY", "setY", "act", "delta", "", "reset", "", "restart", "runAsyncCreate", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateSliderAction extends Action {
    private Formula currentValue;
    private volatile boolean finished;
    private Formula height;
    private Formula maxValue;
    private Formula minValue;
    private Scope scope;
    private boolean started;
    private Formula viewId;
    private Formula width;
    private Formula x;
    private Formula y;

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

    public final Formula getMinValue() {
        return this.minValue;
    }

    public final void setMinValue(Formula formula) {
        this.minValue = formula;
    }

    public final Formula getMaxValue() {
        return this.maxValue;
    }

    public final void setMaxValue(Formula formula) {
        this.maxValue = formula;
    }

    public final Formula getCurrentValue() {
        return this.currentValue;
    }

    public final void setCurrentValue(Formula formula) {
        this.currentValue = formula;
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

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runAsyncCreate();
        }
        return this.finished;
    }

    private final void runAsyncCreate() {
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        StageActivity stageActivity = weakReference != null ? weakReference.get() : null;
        if (stageActivity == null) {
            this.finished = true;
            return;
        }
        Formula formula = this.viewId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        final String str = strInterpretString;
        Formula formula2 = this.minValue;
        Float fInterpretFloat = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        final float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula3 = this.maxValue;
        Float fInterpretFloat2 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        final float fFloatValue2 = fInterpretFloat2 == null ? 100.0f : fInterpretFloat2.floatValue();
        Formula formula4 = this.currentValue;
        Float fInterpretFloat3 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        final float fFloatValue3 = fInterpretFloat3 != null ? fInterpretFloat3.floatValue() : 0.0f;
        Formula formula5 = this.x;
        Integer numInterpretInteger = formula5 != null ? formula5.interpretInteger(this.scope) : null;
        final int iIntValue = numInterpretInteger == null ? 0 : numInterpretInteger.intValue();
        Formula formula6 = this.y;
        Integer numInterpretInteger2 = formula6 != null ? formula6.interpretInteger(this.scope) : null;
        final int iIntValue2 = numInterpretInteger2 == null ? 0 : numInterpretInteger2.intValue();
        Formula formula7 = this.width;
        Integer numInterpretInteger3 = formula7 != null ? formula7.interpretInteger(this.scope) : null;
        final int iIntValue3 = numInterpretInteger3 == null ? 300 : numInterpretInteger3.intValue();
        Formula formula8 = this.height;
        Integer numInterpretInteger4 = formula8 != null ? formula8.interpretInteger(this.scope) : null;
        final int iIntValue4 = numInterpretInteger4 == null ? 60 : numInterpretInteger4.intValue();
        if (str.length() == 0) {
            this.finished = true;
        } else {
            final StageActivity stageActivity2 = stageActivity;
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.CreateSliderAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CreateSliderAction.runAsyncCreate$lambda$1(stageActivity2, fFloatValue3, fFloatValue, fFloatValue2, iIntValue3, iIntValue4, str, this, iIntValue, iIntValue2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void runAsyncCreate$lambda$1(StageActivity stageActivity, float f, float f2, float f3, int i, int i2, String id2, CreateSliderAction this$0, int i3, int i4) {
        Intrinsics.checkNotNullParameter(id2, "$id");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            SeekBar seekBar = new SeekBar(stageActivity);
            seekBar.setMax(1000);
            seekBar.setProgress(Math.max(0, Math.min(1000, (int) (((f - f2) / (f3 - f2)) * 1000))));
            seekBar.setTag(new Pair(Float.valueOf(f2), Float.valueOf(f3)));
            seekBar.setVisibility(NativeViewBindingManager.getDefaultVisibility());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, i2);
            layoutParams.leftMargin = i3;
            layoutParams.topMargin = i4;
            stageActivity.addViewToStage(id2, seekBar, layoutParams);
        } finally {
            this$0.finished = true;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.started = false;
        this.finished = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.started = false;
        this.finished = false;
        this.scope = null;
        this.viewId = null;
        this.minValue = null;
        this.maxValue = null;
        this.currentValue = null;
        this.x = null;
        this.y = null;
        this.width = null;
        this.height = null;
    }
}
