package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.List;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.Component;
import org.catrobat.catroid.raptor.GameObject;
import org.catrobat.catroid.raptor.ParticleComponent;
import org.catrobat.catroid.raptor.SceneManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateParticlesAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateParticlesAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b7\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010S\u001a\u00020T2\u0006\u0010U\u001a\u00020VH\u0002J\u0010\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0006\"\u0004\b\u001c\u0010\bR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0006\"\u0004\b\u001f\u0010\bR\u001c\u0010 \u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0006\"\u0004\b\"\u0010\bR\u001c\u0010#\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0006\"\u0004\b%\u0010\bR\u001c\u0010&\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u0006\"\u0004\b(\u0010\bR\u001c\u0010)\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0006\"\u0004\b+\u0010\bR\u001c\u0010,\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u0006\"\u0004\b.\u0010\bR\u001c\u0010/\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u0006\"\u0004\b1\u0010\bR\u001c\u00102\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u0006\"\u0004\b4\u0010\bR\u001c\u00105\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\u0006\"\u0004\b7\u0010\bR\u001c\u00108\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\u0006\"\u0004\b:\u0010\bR\u001c\u0010;\u001a\u0004\u0018\u00010<X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u001c\u0010A\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010\u0006\"\u0004\bC\u0010\bR\u001c\u0010D\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010\u0006\"\u0004\bF\u0010\bR\u001c\u0010G\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010\u0006\"\u0004\bI\u0010\bR\u001c\u0010J\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010\u0006\"\u0004\bL\u0010\bR\u001c\u0010M\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010\u0006\"\u0004\bO\u0010\bR\u001c\u0010P\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010\u0006\"\u0004\bR\u0010\b¨\u0006["}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateParticlesAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "coneAngle", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getConeAngle", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setConeAngle", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "coneRadius", "getConeRadius", "setConeRadius", "duration", "getDuration", "setDuration", "emissionRate", "getEmissionRate", "setEmissionRate", "endColor", "getEndColor", "setEndColor", "endSize", "getEndSize", "setEndSize", "gravityModifier", "getGravityModifier", "setGravityModifier", "isAdditive", "setAdditive", "looping", "getLooping", "setLooping", "maxParticles", "getMaxParticles", "setMaxParticles", "particleId", "getParticleId", "setParticleId", "positionX", "getPositionX", "setPositionX", "positionY", "getPositionY", "setPositionY", "positionZ", "getPositionZ", "setPositionZ", "rotationOverLifetime", "getRotationOverLifetime", "setRotationOverLifetime", "rotationPitch", "getRotationPitch", "setRotationPitch", "rotationRoll", "getRotationRoll", "setRotationRoll", "rotationYaw", "getRotationYaw", "setRotationYaw", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "startColor", "getStartColor", "setStartColor", "startLifetime", "getStartLifetime", "setStartLifetime", "startRotation", "getStartRotation", "setStartRotation", "startSize", "getStartSize", "setStartSize", "startSpeed", "getStartSpeed", "setStartSpeed", "texturePath", "getTexturePath", "setTexturePath", "parseHexColor", "Lcom/badlogic/gdx/graphics/Color;", "hex", "", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateParticlesAction extends TemporalAction {
    private Formula coneAngle;
    private Formula coneRadius;
    private Formula duration;
    private Formula emissionRate;
    private Formula endColor;
    private Formula endSize;
    private Formula gravityModifier;
    private Formula isAdditive;
    private Formula looping;
    private Formula maxParticles;
    private Formula particleId;
    private Formula positionX;
    private Formula positionY;
    private Formula positionZ;
    private Formula rotationOverLifetime;
    private Formula rotationPitch;
    private Formula rotationRoll;
    private Formula rotationYaw;
    private Scope scope;
    private Formula startColor;
    private Formula startLifetime;
    private Formula startRotation;
    private Formula startSize;
    private Formula startSpeed;
    private Formula texturePath;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getParticleId() {
        return this.particleId;
    }

    public final void setParticleId(Formula formula) {
        this.particleId = formula;
    }

    public final Formula getLooping() {
        return this.looping;
    }

    public final void setLooping(Formula formula) {
        this.looping = formula;
    }

    public final Formula getDuration() {
        return this.duration;
    }

    public final void setDuration(Formula formula) {
        this.duration = formula;
    }

    public final Formula getStartLifetime() {
        return this.startLifetime;
    }

    public final void setStartLifetime(Formula formula) {
        this.startLifetime = formula;
    }

    public final Formula getStartSpeed() {
        return this.startSpeed;
    }

    public final void setStartSpeed(Formula formula) {
        this.startSpeed = formula;
    }

    public final Formula getStartSize() {
        return this.startSize;
    }

    public final void setStartSize(Formula formula) {
        this.startSize = formula;
    }

    public final Formula getGravityModifier() {
        return this.gravityModifier;
    }

    public final void setGravityModifier(Formula formula) {
        this.gravityModifier = formula;
    }

    public final Formula getMaxParticles() {
        return this.maxParticles;
    }

    public final void setMaxParticles(Formula formula) {
        this.maxParticles = formula;
    }

    public final Formula getEmissionRate() {
        return this.emissionRate;
    }

    public final void setEmissionRate(Formula formula) {
        this.emissionRate = formula;
    }

    public final Formula getConeAngle() {
        return this.coneAngle;
    }

    public final void setConeAngle(Formula formula) {
        this.coneAngle = formula;
    }

    public final Formula getConeRadius() {
        return this.coneRadius;
    }

    public final void setConeRadius(Formula formula) {
        this.coneRadius = formula;
    }

    public final Formula getStartColor() {
        return this.startColor;
    }

    public final void setStartColor(Formula formula) {
        this.startColor = formula;
    }

    public final Formula getEndColor() {
        return this.endColor;
    }

    public final void setEndColor(Formula formula) {
        this.endColor = formula;
    }

    public final Formula getEndSize() {
        return this.endSize;
    }

    public final void setEndSize(Formula formula) {
        this.endSize = formula;
    }

    public final Formula getTexturePath() {
        return this.texturePath;
    }

    public final void setTexturePath(Formula formula) {
        this.texturePath = formula;
    }

    /* JADX INFO: renamed from: isAdditive, reason: from getter */
    public final Formula getIsAdditive() {
        return this.isAdditive;
    }

    public final void setAdditive(Formula formula) {
        this.isAdditive = formula;
    }

    public final Formula getStartRotation() {
        return this.startRotation;
    }

    public final void setStartRotation(Formula formula) {
        this.startRotation = formula;
    }

    public final Formula getRotationOverLifetime() {
        return this.rotationOverLifetime;
    }

    public final void setRotationOverLifetime(Formula formula) {
        this.rotationOverLifetime = formula;
    }

    public final Formula getPositionX() {
        return this.positionX;
    }

    public final void setPositionX(Formula formula) {
        this.positionX = formula;
    }

    public final Formula getPositionY() {
        return this.positionY;
    }

    public final void setPositionY(Formula formula) {
        this.positionY = formula;
    }

    public final Formula getPositionZ() {
        return this.positionZ;
    }

    public final void setPositionZ(Formula formula) {
        this.positionZ = formula;
    }

    public final Formula getRotationPitch() {
        return this.rotationPitch;
    }

    public final void setRotationPitch(Formula formula) {
        this.rotationPitch = formula;
    }

    public final Formula getRotationYaw() {
        return this.rotationYaw;
    }

    public final void setRotationYaw(Formula formula) {
        this.rotationYaw = formula;
    }

    public final Formula getRotationRoll() {
        return this.rotationRoll;
    }

    public final void setRotationRoll(Formula formula) {
        this.rotationRoll = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        SceneManager sceneManager;
        Vector3 vector3;
        Sprite sprite;
        Look look;
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener == null || (sceneManager = activeStageListener.sceneManager) == null) {
            return;
        }
        Formula formula = this.particleId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "particles";
        }
        Log.d("CreateParticlesAction", "Executing for ID: " + strInterpretString);
        GameObject gameObjectFindGameObject = sceneManager.findGameObject(strInterpretString);
        if (gameObjectFindGameObject == null) {
            gameObjectFindGameObject = sceneManager.createGameObject(strInterpretString);
            Log.d("CreateParticlesAction", "Created new GameObject: " + strInterpretString);
        } else {
            List<Component> list = gameObjectFindGameObject.components;
            final AnonymousClass1 anonymousClass1 = new Function1<Component, Boolean>() { // from class: org.catrobat.catroid.content.actions.CreateParticlesAction.update.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Component component) {
                    return Boolean.valueOf(component instanceof ParticleComponent);
                }
            };
            list.removeIf(new Predicate() { // from class: org.catrobat.catroid.content.actions.CreateParticlesAction$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return CreateParticlesAction.update$lambda$0(anonymousClass1, obj);
                }
            });
            Log.d("CreateParticlesAction", "Found existing GameObject, removing old particle component.");
        }
        ParticleComponent particleComponent = new ParticleComponent();
        Formula formula2 = this.looping;
        Integer numInterpretInteger = formula2 != null ? formula2.interpretInteger(this.scope) : null;
        particleComponent.looping = (numInterpretInteger == null ? 1 : numInterpretInteger.intValue()) != 0;
        Formula formula3 = this.duration;
        Float fInterpretFloat = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        particleComponent.duration = fInterpretFloat == null ? 5.0f : fInterpretFloat.floatValue();
        Formula formula4 = this.startLifetime;
        Float fInterpretFloat2 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        particleComponent.startLifetime = fInterpretFloat2 == null ? 2.0f : fInterpretFloat2.floatValue();
        Formula formula5 = this.startSpeed;
        Float fInterpretFloat3 = formula5 != null ? formula5.interpretFloat(this.scope) : null;
        particleComponent.startSpeed = fInterpretFloat3 != null ? fInterpretFloat3.floatValue() : 5.0f;
        Formula formula6 = this.startSize;
        Float fInterpretFloat4 = formula6 != null ? formula6.interpretFloat(this.scope) : null;
        particleComponent.startSize = fInterpretFloat4 == null ? 1.0f : fInterpretFloat4.floatValue();
        Formula formula7 = this.gravityModifier;
        Float fInterpretFloat5 = formula7 != null ? formula7.interpretFloat(this.scope) : null;
        particleComponent.gravityModifier = fInterpretFloat5 == null ? 0.0f : fInterpretFloat5.floatValue();
        Formula formula8 = this.maxParticles;
        Integer numInterpretInteger2 = formula8 != null ? formula8.interpretInteger(this.scope) : null;
        particleComponent.maxParticles = numInterpretInteger2 == null ? 1000 : numInterpretInteger2.intValue();
        Formula formula9 = this.emissionRate;
        Float fInterpretFloat6 = formula9 != null ? formula9.interpretFloat(this.scope) : null;
        particleComponent.emissionRate = fInterpretFloat6 == null ? 10.0f : fInterpretFloat6.floatValue();
        Formula formula10 = this.coneAngle;
        Float fInterpretFloat7 = formula10 != null ? formula10.interpretFloat(this.scope) : null;
        particleComponent.coneAngle = fInterpretFloat7 == null ? 25.0f : fInterpretFloat7.floatValue();
        Formula formula11 = this.coneRadius;
        Float fInterpretFloat8 = formula11 != null ? formula11.interpretFloat(this.scope) : null;
        particleComponent.coneRadius = fInterpretFloat8 == null ? 0.1f : fInterpretFloat8.floatValue();
        Formula formula12 = this.endSize;
        Float fInterpretFloat9 = formula12 != null ? formula12.interpretFloat(this.scope) : null;
        particleComponent.endSize = fInterpretFloat9 != null ? fInterpretFloat9.floatValue() : 2.0f;
        Formula formula13 = this.isAdditive;
        Integer numInterpretInteger3 = formula13 != null ? formula13.interpretInteger(this.scope) : null;
        particleComponent.isAdditive = (numInterpretInteger3 == null ? 1 : numInterpretInteger3.intValue()) != 0;
        Formula formula14 = this.startRotation;
        Float fInterpretFloat10 = formula14 != null ? formula14.interpretFloat(this.scope) : null;
        particleComponent.startRotation = fInterpretFloat10 == null ? 0.0f : fInterpretFloat10.floatValue();
        Formula formula15 = this.rotationOverLifetime;
        Float fInterpretFloat11 = formula15 != null ? formula15.interpretFloat(this.scope) : null;
        particleComponent.rotationOverLifetime = fInterpretFloat11 == null ? 360.0f : fInterpretFloat11.floatValue();
        Formula formula16 = this.startColor;
        String strInterpretString2 = formula16 != null ? formula16.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "#FFFF887F";
        }
        Formula formula17 = this.endColor;
        String strInterpretString3 = formula17 != null ? formula17.interpretString(this.scope) : null;
        if (strInterpretString3 == null) {
            strInterpretString3 = "#FF330000";
        }
        particleComponent.startColor = parseHexColor(strInterpretString2);
        particleComponent.endColor = parseHexColor(strInterpretString3);
        Formula formula18 = this.texturePath;
        String strInterpretString4 = formula18 != null ? formula18.interpretString(this.scope) : null;
        if (strInterpretString4 != null) {
            if (strInterpretString4.length() > 0) {
                particleComponent.texturePath = strInterpretString4;
            }
        }
        gameObjectFindGameObject.addComponent(particleComponent);
        Log.d("CreateParticlesAction", "ParticleComponent added to GameObject.");
        Formula formula19 = this.positionX;
        Float fInterpretFloat12 = formula19 != null ? formula19.interpretFloat(this.scope) : null;
        Formula formula20 = this.positionY;
        Float fInterpretFloat13 = formula20 != null ? formula20.interpretFloat(this.scope) : null;
        Formula formula21 = this.positionZ;
        Float fInterpretFloat14 = formula21 != null ? formula21.interpretFloat(this.scope) : null;
        if (fInterpretFloat12 != null || fInterpretFloat13 != null || fInterpretFloat14 != null) {
            gameObjectFindGameObject.transform.position.set(fInterpretFloat12 != null ? fInterpretFloat12.floatValue() : 0.0f, fInterpretFloat13 != null ? fInterpretFloat13.floatValue() : 0.0f, fInterpretFloat14 != null ? fInterpretFloat14.floatValue() : 0.0f);
        } else {
            Scope scope = this.scope;
            if (scope != null && (sprite = scope.getSprite()) != null && (look = sprite.look) != null) {
                vector3 = new Vector3(look.getX(), look.getY(), 0.0f);
            } else {
                vector3 = Vector3.Zero;
            }
            gameObjectFindGameObject.transform.position.set(vector3);
        }
        Formula formula22 = this.rotationPitch;
        Float fInterpretFloat15 = formula22 != null ? formula22.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat15 == null ? 0.0f : fInterpretFloat15.floatValue();
        Formula formula23 = this.rotationYaw;
        Float fInterpretFloat16 = formula23 != null ? formula23.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat16 == null ? 0.0f : fInterpretFloat16.floatValue();
        Formula formula24 = this.rotationRoll;
        Float fInterpretFloat17 = formula24 != null ? formula24.interpretFloat(this.scope) : null;
        gameObjectFindGameObject.transform.rotation.setEulerAngles(fFloatValue2, fFloatValue, fInterpretFloat17 != null ? fInterpretFloat17.floatValue() : 0.0f);
        sceneManager.rebuildGameObject(gameObjectFindGameObject);
        Log.d("CreateParticlesAction", "rebuildGameObject called for " + strInterpretString + ". Effect should appear on next frame.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean update$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    private final Color parseHexColor(String hex) {
        try {
            if (!StringsKt.startsWith$default(hex, "#", false, 2, (Object) null)) {
                hex = "#" + hex;
            }
            int color = android.graphics.Color.parseColor(hex);
            return new Color(android.graphics.Color.red(color) / 255.0f, android.graphics.Color.green(color) / 255.0f, android.graphics.Color.blue(color) / 255.0f, android.graphics.Color.alpha(color) / 255.0f);
        } catch (Exception unused) {
            Color color2 = Color.MAGENTA;
            Intrinsics.checkNotNull(color2);
            return color2;
        }
    }
}
