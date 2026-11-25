package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/TapAtAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: TapAtAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u0000 ,2\u00020\u0001:\u0001,B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010%\u001a\u00020&H\u0014J\b\u0010'\u001a\u00020&H\u0014J\b\u0010(\u001a\u00020&H\u0016J\u0010\u0010)\u001a\u00020&2\u0006\u0010*\u001a\u00020+H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0006\"\u0004\b\u0010\u0010\bR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001a\u0010!\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u000e\u0010$\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lorg/catrobat/catroid/content/actions/TapAtAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "changeX", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getChangeX", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setChangeX", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "changeY", "getChangeY", "setChangeY", "dragCoords", "Lcom/badlogic/gdx/math/Vector2;", "durationFormula", "getDurationFormula", "setDurationFormula", "errorDetected", "", "pointer", "", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "skipUpdate", "stage", "Lcom/badlogic/gdx/scenes/scene2d/Stage;", "startX", "getStartX", "setStartX", "startY", "getStartY", "setStartY", "touchCoords", "begin", "", "end", "restart", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TapAtAction extends TemporalAction {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "TapAtAction";
    private Formula changeX;
    private Formula changeY;
    private Vector2 dragCoords;
    private Formula durationFormula;
    private boolean errorDetected;
    public Scope scope;
    private Stage stage;
    public Formula startX;
    public Formula startY;
    private Vector2 touchCoords = new Vector2(0.0f, 0.0f);
    private int pointer = -1;
    private boolean skipUpdate = true;

    public final Scope getScope() {
        Scope scope = this.scope;
        if (scope != null) {
            return scope;
        }
        Intrinsics.throwUninitializedPropertyAccessException("scope");
        return null;
    }

    public final void setScope(Scope scope) {
        Intrinsics.checkNotNullParameter(scope, "<set-?>");
        this.scope = scope;
    }

    public final Formula getStartX() {
        Formula formula = this.startX;
        if (formula != null) {
            return formula;
        }
        Intrinsics.throwUninitializedPropertyAccessException("startX");
        return null;
    }

    public final void setStartX(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "<set-?>");
        this.startX = formula;
    }

    public final Formula getStartY() {
        Formula formula = this.startY;
        if (formula != null) {
            return formula;
        }
        Intrinsics.throwUninitializedPropertyAccessException("startY");
        return null;
    }

    public final void setStartY(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "<set-?>");
        this.startY = formula;
    }

    public final Formula getChangeX() {
        return this.changeX;
    }

    public final void setChangeX(Formula formula) {
        this.changeX = formula;
    }

    public final Formula getChangeY() {
        return this.changeY;
    }

    public final void setChangeY(Formula formula) {
        this.changeY = formula;
    }

    public final Formula getDurationFormula() {
        return this.durationFormula;
    }

    public final void setDurationFormula(Formula formula) {
        this.durationFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        StageListener stageListener;
        super.begin();
        Stage stage = null;
        try {
            Float fInterpretFloat = getStartX().interpretFloat(getScope());
            Intrinsics.checkNotNullExpressionValue(fInterpretFloat, "interpretFloat(...)");
            float fFloatValue = fInterpretFloat.floatValue();
            Float fInterpretFloat2 = getStartY().interpretFloat(getScope());
            Intrinsics.checkNotNullExpressionValue(fInterpretFloat2, "interpretFloat(...)");
            this.touchCoords = new Vector2(fFloatValue, fInterpretFloat2.floatValue());
            if (this.changeX != null && this.changeY != null) {
                Formula formula = this.changeX;
                Float fInterpretFloat3 = formula != null ? formula.interpretFloat(getScope()) : null;
                float fFloatValue2 = fInterpretFloat3 == null ? 0.0f : fInterpretFloat3.floatValue();
                Formula formula2 = this.changeY;
                Float fInterpretFloat4 = formula2 != null ? formula2.interpretFloat(getScope()) : null;
                this.dragCoords = new Vector2(fFloatValue2, fInterpretFloat4 == null ? 0.0f : fInterpretFloat4.floatValue());
                this.skipUpdate = false;
            }
            Formula formula3 = this.durationFormula;
            Float fInterpretFloat5 = formula3 != null ? formula3.interpretFloat(getScope()) : null;
            setDuration(fInterpretFloat5 == null ? 0.0f : fInterpretFloat5.floatValue());
        } catch (InterpretationException e) {
            Log.d(TAG, "Position not valid", e);
            this.errorDetected = true;
        }
        if (!this.errorDetected) {
            this.pointer = getScope().getSprite().getUnusedPointer();
            StageActivity stageActivity = StageActivity.activeStageActivity.get();
            Stage stage2 = (stageActivity == null || (stageListener = stageActivity.stageListener) == null) ? null : stageListener.getStage();
            Intrinsics.checkNotNull(stage2);
            this.stage = stage2;
            if (!this.skipUpdate) {
                if (stage2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("stage");
                    stage2 = null;
                }
                stage2.stageToScreenCoordinates(this.dragCoords);
            }
            Stage stage3 = this.stage;
            if (stage3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("stage");
                stage3 = null;
            }
            stage3.stageToScreenCoordinates(this.touchCoords);
            Stage stage4 = this.stage;
            if (stage4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("stage");
            } else {
                stage = stage4;
            }
            stage.touchDown((int) this.touchCoords.x, (int) this.touchCoords.y, this.pointer, 0);
            return;
        }
        setDuration(0.0f);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        if (this.skipUpdate) {
            return;
        }
        float f = 1 - percent;
        float f2 = this.touchCoords.x * f;
        Vector2 vector2 = this.dragCoords;
        int i = (int) (f2 + (vector2 != null ? vector2.x * percent : 0.0f));
        float f3 = this.touchCoords.y * f;
        Vector2 vector3 = this.dragCoords;
        int i2 = (int) (f3 + (vector3 != null ? vector3.y * percent : 0.0f));
        Stage stage = this.stage;
        if (stage == null) {
            Intrinsics.throwUninitializedPropertyAccessException("stage");
            stage = null;
        }
        stage.touchDragged(i, i2, this.pointer);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void end() {
        if (this.errorDetected) {
            return;
        }
        Stage stage = null;
        if (this.skipUpdate) {
            Stage stage2 = this.stage;
            if (stage2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("stage");
            } else {
                stage = stage2;
            }
            stage.touchUp((int) this.touchCoords.x, (int) this.touchCoords.y, this.pointer, 0);
        } else {
            Stage stage3 = this.stage;
            if (stage3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("stage");
            } else {
                stage = stage3;
            }
            Vector2 vector2 = this.dragCoords;
            if (vector2 == null) {
                vector2 = this.touchCoords;
            }
            int i = (int) vector2.x;
            Vector2 vector3 = this.dragCoords;
            if (vector3 == null) {
                vector3 = this.touchCoords;
            }
            stage.touchUp(i, (int) vector3.y, this.pointer, 0);
        }
        getScope().getSprite().releaseUsedPointer(this.pointer);
        this.pointer = -1;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        Sprite sprite;
        if (this.pointer != -1) {
            Stage stage = this.stage;
            if (stage == null) {
                Intrinsics.throwUninitializedPropertyAccessException("stage");
                stage = null;
            }
            stage.touchUp((int) this.touchCoords.x, (int) this.touchCoords.y, this.pointer, 0);
            Scope scope = getScope();
            if (scope != null && (sprite = scope.getSprite()) != null) {
                sprite.releaseUsedPointer(this.pointer);
            }
            this.pointer = -1;
        }
        super.restart();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/TapAtAction$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: TapAtAction.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/content/actions/TapAtAction$Companion;", "", "()V", "TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return TapAtAction.TAG;
        }
    }
}
