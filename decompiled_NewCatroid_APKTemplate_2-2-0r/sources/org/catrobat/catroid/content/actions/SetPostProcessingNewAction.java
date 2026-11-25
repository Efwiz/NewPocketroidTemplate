package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.PostProcessingComponent;
import org.catrobat.catroid.raptor.PostProcessingData;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetPostProcessingNewAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetPostProcessingNewAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u001a\u0010!\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0004H\u0002J\u0010\u0010%\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u001eH\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006'"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetPostProcessingNewAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "effectIndex", "", "getEffectIndex", "()I", "setEffectIndex", "(I)V", "paramIndex", "getParamIndex", "setParamIndex", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "valueFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getValueFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setValueFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "applyParamExhaustive", "", "data", "Lorg/catrobat/catroid/raptor/PostProcessingData;", "pIdx", "fVal", "", "bVal", "", "getEffectByEffectIndex", "config", "Lorg/catrobat/catroid/raptor/PostProcessingComponent;", "idx", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetPostProcessingNewAction extends TemporalAction {
    private int effectIndex;
    private int paramIndex;
    private Scope scope;
    private Formula valueFormula;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final int getEffectIndex() {
        return this.effectIndex;
    }

    public final void setEffectIndex(int i) {
        this.effectIndex = i;
    }

    public final int getParamIndex() {
        return this.paramIndex;
    }

    public final void setParamIndex(int i) {
        this.paramIndex = i;
    }

    public final Formula getValueFormula() {
        return this.valueFormula;
    }

    public final void setValueFormula(Formula formula) {
        this.valueFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager;
        PostProcessingComponent postProcessingComponent;
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener == null || (threeDManager = activeStageListener.getThreeDManager()) == null || (postProcessingComponent = threeDManager.currentConfig) == null) {
            return;
        }
        Formula formula = this.valueFormula;
        Float fInterpretFloat = formula != null ? formula.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        boolean z = fFloatValue > 0.5f;
        int i = this.effectIndex;
        if (i == 0) {
            int i2 = this.paramIndex;
            if (i2 == 0) {
                postProcessingComponent.isActive = z;
            } else if (i2 == 1) {
                postProcessingComponent.qualityScale = RangesKt.coerceIn(fFloatValue, 0.01f, 1.0f);
            }
        } else {
            PostProcessingData effectByEffectIndex = getEffectByEffectIndex(postProcessingComponent, i);
            if (effectByEffectIndex == null) {
                return;
            } else {
                applyParamExhaustive(effectByEffectIndex, this.paramIndex, fFloatValue, z);
            }
        }
        threeDManager.updatePostProcessing(postProcessingComponent);
    }

    /* JADX WARN: Code duplicated, block: B:48:0x0078 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:55:? A[RETURN, SYNTHETIC] */
    private final PostProcessingData getEffectByEffectIndex(PostProcessingComponent config, int idx) {
        Class cls;
        PostProcessingData postProcessingData;
        PostProcessingData postProcessingData2;
        Object obj = null;
        switch (idx) {
            case 1:
                cls = PostProcessingData.Bloom.class;
                break;
            case 2:
                cls = PostProcessingData.Vignette.class;
                break;
            case 3:
                cls = PostProcessingData.Levels.class;
                break;
            case 4:
                cls = PostProcessingData.Grain.class;
                break;
            case 5:
                cls = PostProcessingData.Fxaa.class;
                break;
            case 6:
                cls = PostProcessingData.Chromatic.class;
                break;
            case 7:
                cls = PostProcessingData.RadialBlur.class;
                break;
            case 8:
                cls = PostProcessingData.OldTv.class;
                break;
            case 9:
                cls = PostProcessingData.Crt.class;
                break;
            case 10:
                cls = PostProcessingData.Fisheye.class;
                break;
            case 11:
                cls = PostProcessingData.Water.class;
                break;
            case 12:
                cls = PostProcessingData.MotionBlur.class;
                break;
            case 13:
                cls = PostProcessingData.LensFlare.class;
                break;
            case 14:
                cls = PostProcessingData.Gaussian.class;
                break;
            case 15:
                cls = PostProcessingData.Zoom.class;
                break;
            case 16:
                cls = PostProcessingData.ACES.class;
                break;
            case 17:
                cls = PostProcessingData.EyeAdaptation.class;
                break;
            case 18:
                cls = PostProcessingData.RayTracing.class;
                break;
            case 19:
                cls = PostProcessingData.SSAO.class;
                break;
            case 20:
                cls = PostProcessingData.HeightFog.class;
                break;
            case 21:
                cls = PostProcessingData.DepthOfField.class;
                break;
            case 22:
                cls = PostProcessingData.GodRays.class;
                break;
            case 23:
                cls = PostProcessingData.VolumetricFog.class;
                break;
            case 24:
                cls = PostProcessingData.Upscaler.class;
                break;
            case 25:
                cls = PostProcessingData.SSGI.class;
                break;
            default:
                cls = null;
                break;
        }
        if (cls == null) {
            return null;
        }
        List<PostProcessingData> effects = config.effects;
        Intrinsics.checkNotNullExpressionValue(effects, "effects");
        for (Object obj2 : effects) {
            if (cls.isInstance((PostProcessingData) obj2)) {
                obj = obj2;
                postProcessingData = (PostProcessingData) obj;
                if (postProcessingData == null) {
                    return postProcessingData;
                }
                try {
                    postProcessingData2 = (PostProcessingData) cls.newInstance();
                    try {
                        config.effects.add(postProcessingData2);
                        return postProcessingData2;
                    } catch (Exception e) {
                        e = e;
                        postProcessingData = postProcessingData2;
                        e.printStackTrace();
                        return postProcessingData;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            }
        }
        postProcessingData = (PostProcessingData) obj;
        if (postProcessingData == null) {
            return postProcessingData;
        }
        postProcessingData2 = (PostProcessingData) cls.newInstance();
        config.effects.add(postProcessingData2);
        return postProcessingData2;
    }

    private final void applyParamExhaustive(PostProcessingData data, int pIdx, float fVal, boolean bVal) {
        if (pIdx == 0) {
            data.isEnabled = bVal;
            return;
        }
        if (data instanceof PostProcessingData.Bloom) {
            if (pIdx == 1) {
                ((PostProcessingData.Bloom) data).intensity = fVal;
                return;
            }
            if (pIdx == 2) {
                ((PostProcessingData.Bloom) data).threshold = fVal;
                return;
            }
            if (pIdx == 3) {
                ((PostProcessingData.Bloom) data).blurAmount = fVal;
                return;
            }
            if (pIdx == 4) {
                ((PostProcessingData.Bloom) data).size = fVal;
                return;
            } else {
                if (pIdx != 5) {
                    return;
                }
                ((PostProcessingData.Bloom) data).blurPasses = Math.max(1, (int) fVal);
                return;
            }
        }
        if (data instanceof PostProcessingData.Vignette) {
            if (pIdx == 1) {
                ((PostProcessingData.Vignette) data).intensity = fVal;
                return;
            } else {
                if (pIdx != 2) {
                    return;
                }
                ((PostProcessingData.Vignette) data).saturation = fVal;
                return;
            }
        }
        if (data instanceof PostProcessingData.Levels) {
            if (pIdx == 1) {
                ((PostProcessingData.Levels) data).contrast = fVal;
                return;
            } else if (pIdx == 2) {
                ((PostProcessingData.Levels) data).saturation = fVal;
                return;
            } else {
                if (pIdx != 3) {
                    return;
                }
                ((PostProcessingData.Levels) data).gamma = fVal;
                return;
            }
        }
        if (data instanceof PostProcessingData.Grain) {
            if (pIdx == 1) {
                ((PostProcessingData.Grain) data).amount = fVal;
                return;
            }
            return;
        }
        if (data instanceof PostProcessingData.Chromatic) {
            if (pIdx == 1) {
                ((PostProcessingData.Chromatic) data).strength = fVal;
                return;
            } else {
                if (pIdx != 2) {
                    return;
                }
                ((PostProcessingData.Chromatic) data).maxDistortion = fVal;
                return;
            }
        }
        if (data instanceof PostProcessingData.RadialBlur) {
            if (pIdx == 1) {
                ((PostProcessingData.RadialBlur) data).strength = fVal;
                return;
            }
            if (pIdx == 2) {
                ((PostProcessingData.RadialBlur) data).size = fVal;
                return;
            } else {
                if (pIdx != 3) {
                    return;
                }
                ((PostProcessingData.RadialBlur) data).blurPasses = Math.max(1, (int) fVal);
                return;
            }
        }
        if (data instanceof PostProcessingData.OldTv) {
            if (pIdx == 1) {
                ((PostProcessingData.OldTv) data).strength = fVal;
                return;
            }
            return;
        }
        if (data instanceof PostProcessingData.Crt) {
            if (pIdx == 1) {
                ((PostProcessingData.Crt) data).distortion = fVal;
                return;
            } else {
                if (pIdx != 2) {
                    return;
                }
                ((PostProcessingData.Crt) data).zoom = fVal;
                return;
            }
        }
        if (data instanceof PostProcessingData.Fisheye) {
            if (pIdx == 1) {
                ((PostProcessingData.Fisheye) data).intensity = fVal;
                return;
            }
            return;
        }
        if (data instanceof PostProcessingData.Water) {
            if (pIdx == 1) {
                ((PostProcessingData.Water) data).amount = fVal;
                return;
            } else {
                if (pIdx != 2) {
                    return;
                }
                ((PostProcessingData.Water) data).speed = fVal;
                return;
            }
        }
        if (data instanceof PostProcessingData.MotionBlur) {
            if (pIdx == 1) {
                ((PostProcessingData.MotionBlur) data).blurOpacity = RangesKt.coerceIn(fVal, 0.0f, 0.99f);
                return;
            }
            return;
        }
        if (data instanceof PostProcessingData.LensFlare) {
            if (pIdx == 1) {
                ((PostProcessingData.LensFlare) data).intensity = fVal;
                return;
            }
            if (pIdx == 2) {
                ((PostProcessingData.LensFlare) data).threshold = fVal;
                return;
            } else if (pIdx == 3) {
                ((PostProcessingData.LensFlare) data).dispersal = fVal;
                return;
            } else {
                if (pIdx != 4) {
                    return;
                }
                ((PostProcessingData.LensFlare) data).size = fVal;
                return;
            }
        }
        if (data instanceof PostProcessingData.Gaussian) {
            if (pIdx == 1) {
                ((PostProcessingData.Gaussian) data).amount = fVal;
                return;
            }
            if (pIdx == 2) {
                ((PostProcessingData.Gaussian) data).size = fVal;
                return;
            } else {
                if (pIdx != 3) {
                    return;
                }
                ((PostProcessingData.Gaussian) data).passes = Math.max(1, (int) fVal);
                return;
            }
        }
        if (data instanceof PostProcessingData.Zoom) {
            if (pIdx == 1) {
                ((PostProcessingData.Zoom) data).zoom = fVal;
                return;
            } else if (pIdx == 2) {
                ((PostProcessingData.Zoom) data).originX = fVal;
                return;
            } else {
                if (pIdx != 3) {
                    return;
                }
                ((PostProcessingData.Zoom) data).originY = fVal;
                return;
            }
        }
        if (data instanceof PostProcessingData.EyeAdaptation) {
            if (pIdx == 1) {
                ((PostProcessingData.EyeAdaptation) data).targetLuminance = fVal;
                return;
            }
            if (pIdx == 2) {
                ((PostProcessingData.EyeAdaptation) data).speed = fVal;
                return;
            } else if (pIdx == 3) {
                ((PostProcessingData.EyeAdaptation) data).minExposure = fVal;
                return;
            } else {
                if (pIdx != 4) {
                    return;
                }
                ((PostProcessingData.EyeAdaptation) data).maxExposure = fVal;
                return;
            }
        }
        if (data instanceof PostProcessingData.RayTracing) {
            switch (pIdx) {
                case 1:
                    ((PostProcessingData.RayTracing) data).reflectivity = fVal;
                    break;
                case 2:
                    ((PostProcessingData.RayTracing) data).steps = Math.max(1, (int) fVal);
                    break;
                case 3:
                    ((PostProcessingData.RayTracing) data).thickness = fVal;
                    break;
                case 4:
                    ((PostProcessingData.RayTracing) data).maxDistance = fVal;
                    break;
                case 5:
                    ((PostProcessingData.RayTracing) data).stride = fVal;
                    break;
                case 6:
                    ((PostProcessingData.RayTracing) data).jitter = fVal;
                    break;
                case 7:
                    ((PostProcessingData.RayTracing) data).edgeFade = fVal;
                    break;
            }
            return;
        }
        if (data instanceof PostProcessingData.SSAO) {
            if (pIdx == 1) {
                ((PostProcessingData.SSAO) data).intensity = fVal;
                return;
            } else if (pIdx == 2) {
                ((PostProcessingData.SSAO) data).radius = fVal;
                return;
            } else {
                if (pIdx != 3) {
                    return;
                }
                ((PostProcessingData.SSAO) data).bias = fVal;
                return;
            }
        }
        if (data instanceof PostProcessingData.SSGI) {
            switch (pIdx) {
                case 1:
                    ((PostProcessingData.SSGI) data).intensity = fVal;
                    break;
                case 2:
                    ((PostProcessingData.SSGI) data).radius = fVal;
                    break;
                case 3:
                    ((PostProcessingData.SSGI) data).bias = fVal;
                    break;
                case 4:
                    ((PostProcessingData.SSGI) data).ssaoStrength = fVal;
                    break;
                case 5:
                    ((PostProcessingData.SSGI) data).baseAlbedoR = RangesKt.coerceIn(fVal, 0.0f, 1.0f);
                    break;
                case 6:
                    ((PostProcessingData.SSGI) data).baseAlbedoG = RangesKt.coerceIn(fVal, 0.0f, 1.0f);
                    break;
                case 7:
                    ((PostProcessingData.SSGI) data).baseAlbedoB = RangesKt.coerceIn(fVal, 0.0f, 1.0f);
                    break;
                case 8:
                    ((PostProcessingData.SSGI) data).flipDepth = bVal;
                    break;
            }
            return;
        }
        if (data instanceof PostProcessingData.HeightFog) {
            if (pIdx == 1) {
                ((PostProcessingData.HeightFog) data).density = fVal;
                return;
            } else if (pIdx == 2) {
                ((PostProcessingData.HeightFog) data).falloff = fVal;
                return;
            } else {
                if (pIdx != 3) {
                    return;
                }
                ((PostProcessingData.HeightFog) data).height = fVal;
                return;
            }
        }
        if (data instanceof PostProcessingData.DepthOfField) {
            switch (pIdx) {
                case 1:
                    ((PostProcessingData.DepthOfField) data).focusDistance = fVal;
                    break;
                case 2:
                    ((PostProcessingData.DepthOfField) data).focusRange = fVal;
                    break;
                case 3:
                    ((PostProcessingData.DepthOfField) data).blurSize = fVal;
                    break;
                case 4:
                    ((PostProcessingData.DepthOfField) data).transition = fVal;
                    break;
                case 5:
                    ((PostProcessingData.DepthOfField) data).autoFocus = bVal;
                    break;
                case 6:
                    ((PostProcessingData.DepthOfField) data).autoFocusSpeed = fVal;
                    break;
            }
            return;
        }
        if (data instanceof PostProcessingData.GodRays) {
            if (pIdx == 1) {
                ((PostProcessingData.GodRays) data).exposure = fVal;
                return;
            }
            if (pIdx == 2) {
                ((PostProcessingData.GodRays) data).decay = fVal;
                return;
            } else if (pIdx == 3) {
                ((PostProcessingData.GodRays) data).density = fVal;
                return;
            } else {
                if (pIdx != 4) {
                    return;
                }
                ((PostProcessingData.GodRays) data).weight = fVal;
                return;
            }
        }
        if (!(data instanceof PostProcessingData.VolumetricFog)) {
            if ((data instanceof PostProcessingData.Upscaler) && pIdx == 1) {
                ((PostProcessingData.Upscaler) data).sharpness = fVal;
                return;
            }
            return;
        }
        if (pIdx == 1) {
            ((PostProcessingData.VolumetricFog) data).density = fVal;
            return;
        }
        if (pIdx == 2) {
            ((PostProcessingData.VolumetricFog) data).scattering = fVal;
            return;
        }
        if (pIdx == 3) {
            ((PostProcessingData.VolumetricFog) data).steps = Math.max(1, (int) fVal);
        } else {
            if (pIdx != 4) {
                return;
            }
            ((PostProcessingData.VolumetricFog) data).maxDistance = fVal;
        }
    }
}
