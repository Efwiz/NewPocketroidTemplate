package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.emitters.Emitter;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsInfluencer;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier;
import com.badlogic.gdx.graphics.g3d.particles.influencers.Influencer;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ScaleInfluencer;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ConfigureParticlesAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ConfigureParticlesAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/ConfigureParticlesAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "configTypeSelection", "", "getConfigTypeSelection", "()I", "setConfigTypeSelection", "(I)V", "particleId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getParticleId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setParticleId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "value", "getValue", "setValue", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConfigureParticlesAction extends TemporalAction {
    private int configTypeSelection;
    private Formula particleId;
    private Scope scope;
    private Formula value;

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

    public final Formula getValue() {
        return this.value;
    }

    public final void setValue(Formula formula) {
        this.value = formula;
    }

    public final int getConfigTypeSelection() {
        return this.configTypeSelection;
    }

    public final void setConfigTypeSelection(int i) {
        this.configTypeSelection = i;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Influencer next;
        Influencer next2;
        Formula formula = this.particleId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        Formula formula2 = this.value;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        String str = strInterpretString2 != null ? strInterpretString2 : "";
        if (strInterpretString.length() == 0) {
            return;
        }
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        ThreeDManager threeDManager = activeStageListener != null ? activeStageListener.getThreeDManager() : null;
        if (threeDManager == null) {
            return;
        }
        try {
            ParticleEffect particleEffect = threeDManager.activeParticleEffects.get(strInterpretString);
            if (particleEffect == null) {
                return;
            }
            Array.ArrayIterator<ParticleController> it = particleEffect.getControllers().iterator();
            while (it.hasNext()) {
                ParticleController next3 = it.next();
                int i = this.configTypeSelection;
                if (i == 0) {
                    Float floatOrNull = StringsKt.toFloatOrNull(str);
                    float fFloatValue = floatOrNull != null ? floatOrNull.floatValue() : 10.0f;
                    Emitter emitter = next3.emitter;
                    if (emitter instanceof RegularEmitter) {
                        ((RegularEmitter) emitter).getEmission().setHigh(fFloatValue);
                        if (fFloatValue <= 0.01f) {
                            ((RegularEmitter) emitter).getEmission().setLow(0.0f);
                        }
                    }
                } else if (i == 1) {
                    Float floatOrNull2 = StringsKt.toFloatOrNull(str);
                    float fFloatValue2 = floatOrNull2 != null ? floatOrNull2.floatValue() : 1.0f;
                    Array<Influencer> influencers = next3.influencers;
                    Intrinsics.checkNotNullExpressionValue(influencers, "influencers");
                    Iterator<Influencer> it2 = influencers.iterator();
                    do {
                        if (!it2.hasNext()) {
                            next2 = null;
                            break;
                        }
                        next2 = it2.next();
                    } while (!(next2 instanceof ScaleInfluencer));
                    Influencer influencer = next2;
                    if (influencer instanceof ScaleInfluencer) {
                        ((ScaleInfluencer) influencer).value.setHigh(fFloatValue2);
                    }
                } else if (i == 2) {
                    Float floatOrNull3 = StringsKt.toFloatOrNull(str);
                    float fFloatValue3 = floatOrNull3 != null ? floatOrNull3.floatValue() : 45.0f;
                    Array<Influencer> influencers2 = next3.influencers;
                    Intrinsics.checkNotNullExpressionValue(influencers2, "influencers");
                    Iterator<Influencer> it3 = influencers2.iterator();
                    do {
                        if (!it3.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it3.next();
                    } while (!(next instanceof DynamicsInfluencer));
                    Influencer influencer2 = next;
                    if (influencer2 instanceof DynamicsInfluencer) {
                        Array.ArrayIterator<DynamicsModifier> it4 = ((DynamicsInfluencer) influencer2).velocities.iterator();
                        while (it4.hasNext()) {
                            DynamicsModifier next4 = it4.next();
                            if (next4 instanceof DynamicsModifier.PolarAcceleration) {
                                ((DynamicsModifier.PolarAcceleration) next4).thetaValue.setHigh(0.0f, fFloatValue3);
                            }
                        }
                    }
                } else if (i == 3) {
                    Integer intOrNull = StringsKt.toIntOrNull(str);
                    next3.emitter.maxParticleCount = intOrNull != null ? intOrNull.intValue() : 100;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
