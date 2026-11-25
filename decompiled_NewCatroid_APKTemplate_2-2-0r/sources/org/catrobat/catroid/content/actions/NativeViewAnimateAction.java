package org.catrobat.catroid.content.actions;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.EasingFunctions;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/NativeViewAnimateAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: NativeViewAnimateAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001#B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\"H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\b¨\u0006$"}, d2 = {"Lorg/catrobat/catroid/content/actions/NativeViewAnimateAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "durationMs", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getDurationMs", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setDurationMs", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "easingSelection", "", "getEasingSelection", "()I", "setEasingSelection", "(I)V", "propertySelection", "getPropertySelection", "setPropertySelection", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "value", "getValue", "setValue", "viewId", "getViewId", "setViewId", "restart", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "CustomEasingInterpolator", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeViewAnimateAction extends TemporalAction {
    private Formula durationMs;
    private int easingSelection;
    private int propertySelection;
    private Scope scope;
    private Formula value;
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

    public final Formula getValue() {
        return this.value;
    }

    public final void setValue(Formula formula) {
        this.value = formula;
    }

    public final Formula getDurationMs() {
        return this.durationMs;
    }

    public final void setDurationMs(Formula formula) {
        this.durationMs = formula;
    }

    public final int getPropertySelection() {
        return this.propertySelection;
    }

    public final void setPropertySelection(int i) {
        this.propertySelection = i;
    }

    public final int getEasingSelection() {
        return this.easingSelection;
    }

    public final void setEasingSelection(int i) {
        this.easingSelection = i;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/NativeViewAnimateAction$CustomEasingInterpolator.class
      classes14.dex
     */
    /* JADX INFO: compiled from: NativeViewAnimateAction.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/content/actions/NativeViewAnimateAction$CustomEasingInterpolator;", "Landroid/view/animation/Interpolator;", "easingType", "Lorg/catrobat/catroid/content/EasingFunctions$EasingType;", "(Lorg/catrobat/catroid/content/EasingFunctions$EasingType;)V", "getInterpolation", "", "input", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CustomEasingInterpolator implements Interpolator {
        private final EasingFunctions.EasingType easingType;

        public CustomEasingInterpolator(EasingFunctions.EasingType easingType) {
            Intrinsics.checkNotNullParameter(easingType, "easingType");
            this.easingType = easingType;
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float input) {
            return EasingFunctions.calculate(this.easingType, input, 1.0f, 0.0f, 1.0f);
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        final StageActivity stageActivity;
        Integer numInterpretInteger;
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        if (weakReference == null || (stageActivity = weakReference.get()) == null) {
            return;
        }
        Formula formula = this.viewId;
        final String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.value;
        Float fInterpretFloat = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        final float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula3 = this.durationMs;
        final long jIntValue = (formula3 == null || (numInterpretInteger = formula3.interpretInteger(this.scope)) == null) ? 300L : numInterpretInteger.intValue();
        final View viewFromStage = stageActivity.getViewFromStage(strInterpretString);
        if (viewFromStage == null) {
            return;
        }
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.NativeViewAnimateAction$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                NativeViewAnimateAction.update$lambda$2(this.f$0, stageActivity, strInterpretString, fFloatValue, viewFromStage, jIntValue);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$2(final NativeViewAnimateAction this$0, final StageActivity stage, final String idStr, float f, View view, long j) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(stage, "$stage");
        Intrinsics.checkNotNullParameter(idStr, "$idStr");
        Intrinsics.checkNotNullParameter(view, "$view");
        try {
            EasingFunctions.EasingType easingType = EasingFunctions.EasingType.values()[this$0.easingSelection];
            int i = this$0.propertySelection;
            if (6 <= i && i < 8) {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(i == 6 ? stage.getViewX(idStr) : stage.getViewY(idStr), f);
                valueAnimatorOfFloat.setDuration(j);
                valueAnimatorOfFloat.setInterpolator(new CustomEasingInterpolator(easingType));
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.content.actions.NativeViewAnimateAction$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        NativeViewAnimateAction.update$lambda$2$lambda$1$lambda$0(this.f$0, stage, idStr, valueAnimator);
                    }
                });
                valueAnimatorOfFloat.start();
                return;
            }
            ViewPropertyAnimator interpolator = view.animate().setDuration(j).setInterpolator(new CustomEasingInterpolator(easingType));
            Intrinsics.checkNotNullExpressionValue(interpolator, "setInterpolator(...)");
            int i2 = this$0.propertySelection;
            if (i2 == 0) {
                interpolator.alpha(f);
            } else if (i2 == 1) {
                interpolator.rotation(f);
            } else if (i2 == 2) {
                interpolator.scaleX(f);
            } else if (i2 == 3) {
                interpolator.scaleY(f);
            } else if (i2 == 4) {
                interpolator.translationX(f);
            } else if (i2 == 5) {
                interpolator.translationY(f);
            }
            interpolator.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$2$lambda$1$lambda$0(NativeViewAnimateAction this$0, StageActivity stage, String idStr, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(stage, "$stage");
        Intrinsics.checkNotNullParameter(idStr, "$idStr");
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float fFloatValue = ((Float) animatedValue).floatValue();
        if (this$0.propertySelection == 6) {
            stage.setViewPosition(idStr, (int) fFloatValue, (int) stage.getViewY(idStr));
        } else {
            stage.setViewPosition(idStr, (int) stage.getViewX(idStr), (int) fFloatValue);
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
    }
}
