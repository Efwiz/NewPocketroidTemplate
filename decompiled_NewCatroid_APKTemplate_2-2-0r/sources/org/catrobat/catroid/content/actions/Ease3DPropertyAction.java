package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.ranges.RangesKt;
import org.catrobat.catroid.content.EasingFunctions;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.raptor.GameObject;
import org.catrobat.catroid.raptor.SceneManager;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/Ease3DPropertyAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: Ease3DPropertyAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\u0010\u0010\u0017\u001a\u00020\u00152\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0019\u001a\u00020\u00152\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u001a\u001a\u00020\u00152\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u000eJ\u0010\u0010\u001d\u001a\u00020\u00152\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J\u0010\u0010\u001e\u001a\u00020\u00152\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u000eJ\u0010\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u000bH\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/catrobat/catroid/content/actions/Ease3DPropertyAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "durationFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "easingTypes", "", "Lorg/catrobat/catroid/content/EasingFunctions$EasingType;", "[Lorg/catrobat/catroid/content/EasingFunctions$EasingType;", "endFormula", "endVal", "", "objectId", "propertyIndex", "", "scope", "Lorg/catrobat/catroid/content/Scope;", "startFormula", "startVal", "typeIndex", "begin", "", "reset", "setDurationFormula", "formula", "setEndFormula", "setObjectId", "setPropertyIndex", "index", "setScope", "setStartFormula", "setTypeIndex", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Ease3DPropertyAction extends TemporalAction {
    private Formula durationFormula;
    private final EasingFunctions.EasingType[] easingTypes = (EasingFunctions.EasingType[]) EntriesMappings.entries$0.toArray(new EasingFunctions.EasingType[0]);
    private Formula endFormula;
    private float endVal;
    private Formula objectId;
    private int propertyIndex;
    private Scope scope;
    private Formula startFormula;
    private float startVal;
    private int typeIndex;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/Ease3DPropertyAction$EntriesMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: Ease3DPropertyAction.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class EntriesMappings {
        public static final /* synthetic */ EnumEntries<EasingFunctions.EasingType> entries$0 = EnumEntriesKt.enumEntries(EasingFunctions.EasingType.values());
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        try {
            Formula formula = this.durationFormula;
            Float fInterpretFloat = formula != null ? formula.interpretFloat(this.scope) : null;
            setDuration(fInterpretFloat == null ? 1.0f : fInterpretFloat.floatValue());
            Formula formula2 = this.startFormula;
            Float fInterpretFloat2 = formula2 != null ? formula2.interpretFloat(this.scope) : null;
            this.startVal = fInterpretFloat2 == null ? 0.0f : fInterpretFloat2.floatValue();
            Formula formula3 = this.endFormula;
            Float fInterpretFloat3 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
            this.endVal = fInterpretFloat3 == null ? 0.0f : fInterpretFloat3.floatValue();
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation failed in Ease3DPropertyAction.", e);
            setDuration(0.0f);
        }
        super.begin();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener == null) {
        }
        SceneManager sceneManager = activeStageListener.sceneManager;
        ThreeDManager threeDManager = activeStageListener.getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        Formula formula = this.objectId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null || strInterpretString.length() == 0) {
            return;
        }
        float fCoerceAtMost = RangesKt.coerceAtMost(getTime(), getDuration());
        float duration = getDuration();
        int i = this.typeIndex;
        if (i < 0 || i >= this.easingTypes.length) {
            i = 0;
        }
        float fCalculate = EasingFunctions.calculate(this.easingTypes[i], fCoerceAtMost, duration, this.startVal, this.endVal);
        GameObject gameObjectFindGameObject = sceneManager != null ? sceneManager.findGameObject(strInterpretString) : null;
        if (gameObjectFindGameObject != null) {
            switch (this.propertyIndex) {
                case 0:
                    gameObjectFindGameObject.transform.position.x = fCalculate;
                    break;
                case 1:
                    gameObjectFindGameObject.transform.position.y = fCalculate;
                    break;
                case 2:
                    gameObjectFindGameObject.transform.position.z = fCalculate;
                    break;
                case 3:
                case 4:
                case 5:
                    Quaternion quaternion = gameObjectFindGameObject.transform.rotation;
                    float yaw = quaternion.getYaw();
                    float pitch = quaternion.getPitch();
                    float roll = quaternion.getRoll();
                    int i2 = this.propertyIndex;
                    if (i2 == 3) {
                        gameObjectFindGameObject.transform.rotation.setEulerAngles(fCalculate, pitch, roll);
                        break;
                    } else if (i2 == 4) {
                        gameObjectFindGameObject.transform.rotation.setEulerAngles(yaw, fCalculate, roll);
                        break;
                    } else if (i2 == 5) {
                        gameObjectFindGameObject.transform.rotation.setEulerAngles(yaw, pitch, fCalculate);
                        break;
                    }
                    break;
                case 6:
                    gameObjectFindGameObject.transform.scale.x = fCalculate;
                    break;
                case 7:
                    gameObjectFindGameObject.transform.scale.y = fCalculate;
                    break;
                case 8:
                    gameObjectFindGameObject.transform.scale.z = fCalculate;
                    break;
            }
        }
        Vector3 position = threeDManager.getPosition(strInterpretString);
        if (position == null) {
            position = new Vector3();
        }
        Vector3 rotation = threeDManager.getRotation(strInterpretString);
        if (rotation == null) {
            rotation = new Vector3();
        }
        Vector3 scale = threeDManager.getScale(strInterpretString);
        if (scale == null) {
            scale = new Vector3(1.0f, 1.0f, 1.0f);
        }
        switch (this.propertyIndex) {
            case 0:
                threeDManager.setPosition(strInterpretString, fCalculate, position.y, position.z);
                break;
            case 1:
                threeDManager.setPosition(strInterpretString, position.x, fCalculate, position.z);
                break;
            case 2:
                threeDManager.setPosition(strInterpretString, position.x, position.y, fCalculate);
                break;
            case 3:
                threeDManager.setRotation(strInterpretString, fCalculate, rotation.x, rotation.z);
                break;
            case 4:
                threeDManager.setRotation(strInterpretString, rotation.y, fCalculate, rotation.z);
                break;
            case 5:
                threeDManager.setRotation(strInterpretString, rotation.y, rotation.x, fCalculate);
                break;
            case 6:
                threeDManager.setScale(strInterpretString, fCalculate, scale.y, scale.z);
                break;
            case 7:
                threeDManager.setScale(strInterpretString, scale.x, fCalculate, scale.z);
                break;
            case 8:
                threeDManager.setScale(strInterpretString, scale.x, scale.y, fCalculate);
                break;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.scope = null;
        this.objectId = null;
        this.durationFormula = null;
        this.startFormula = null;
        this.endFormula = null;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final void setObjectId(Formula formula) {
        this.objectId = formula;
    }

    public final void setPropertyIndex(int index) {
        this.propertyIndex = index;
    }

    public final void setTypeIndex(int index) {
        this.typeIndex = index;
    }

    public final void setDurationFormula(Formula formula) {
        this.durationFormula = formula;
    }

    public final void setStartFormula(Formula formula) {
        this.startFormula = formula;
    }

    public final void setEndFormula(Formula formula) {
        this.endFormula = formula;
    }
}
