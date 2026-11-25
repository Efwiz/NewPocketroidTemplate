package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetPostProcessingAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetPostProcessingAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u001a\u0010!\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0004H\u0002J\u0010\u0010%\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u001eH\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006'"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetPostProcessingAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "effectIndex", "", "getEffectIndex", "()I", "setEffectIndex", "(I)V", "paramIndex", "getParamIndex", "setParamIndex", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "valueFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getValueFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setValueFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "applyParam", "", "data", "Lorg/catrobat/catroid/raptor/PostProcessingData;", "paramIdx", "valFloat", "", "valBool", "", "findOrCreateEffect", "config", "Lorg/catrobat/catroid/raptor/PostProcessingComponent;", "typeIndex", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetPostProcessingAction extends TemporalAction {
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
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener == null || (threeDManager = activeStageListener.getThreeDManager()) == null) {
            return;
        }
        PostProcessingComponent postProcessingComponent = threeDManager.currentConfig;
        if (postProcessingComponent == null) {
            postProcessingComponent = new PostProcessingComponent();
            threeDManager.updatePostProcessing(postProcessingComponent);
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
            } else if (i2 == 5) {
                postProcessingComponent.qualityScale = RangesKt.coerceIn(fFloatValue, 0.1f, 1.0f);
            }
        } else {
            PostProcessingData postProcessingDataFindOrCreateEffect = findOrCreateEffect(postProcessingComponent, i);
            if (postProcessingDataFindOrCreateEffect != null) {
                applyParam(postProcessingDataFindOrCreateEffect, this.paramIndex, fFloatValue, z);
            }
        }
        threeDManager.updatePostProcessing(postProcessingComponent);
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0060 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:38:0x0061 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r2v5 java.lang.Object, still in use, count: 2, list:
          (r2v5 java.lang.Object) from 0x005c: PHI (r2 I:??) = (r2v2 java.lang.Object), (r2v5 java.lang.Object) binds: [B:29:0x005b, B:41:0x005c] A[DONT_GENERATE, DONT_INLINE]
          (r2v5 java.lang.Object) from 0x0052: CHECK_CAST (org.catrobat.catroid.raptor.PostProcessingData) (r2v5 java.lang.Object)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:93)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:132)
        	at jadx.core.dex.visitors.regions.TernaryMod.processRegion(TernaryMod.java:67)
        	at jadx.core.dex.visitors.regions.TernaryMod.enterRegion(TernaryMod.java:50)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:96)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.TernaryMod.process(TernaryMod.java:36)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.process(IfRegionVisitor.java:44)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:30)
        */
    private final org.catrobat.catroid.raptor.PostProcessingData findOrCreateEffect(org.catrobat.catroid.raptor.PostProcessingComponent r5, int r6) {
        /*
            r4 = this;
            r0 = 0
            switch(r6) {
                case 1: goto L38;
                case 2: goto L35;
                case 3: goto L32;
                case 4: goto L2f;
                case 5: goto L2c;
                case 6: goto L29;
                case 7: goto L26;
                case 8: goto L23;
                case 9: goto L20;
                case 10: goto L1d;
                case 11: goto L1a;
                case 12: goto L17;
                case 13: goto L14;
                case 14: goto L11;
                case 15: goto Le;
                case 16: goto Lb;
                case 17: goto L8;
                case 18: goto L5;
                default: goto L4;
            }
        L4:
            return r0
        L5:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$RayTracing> r6 = org.catrobat.catroid.raptor.PostProcessingData.RayTracing.class
            goto L3a
        L8:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$EyeAdaptation> r6 = org.catrobat.catroid.raptor.PostProcessingData.EyeAdaptation.class
            goto L3a
        Lb:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$ACES> r6 = org.catrobat.catroid.raptor.PostProcessingData.ACES.class
            goto L3a
        Le:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$Zoom> r6 = org.catrobat.catroid.raptor.PostProcessingData.Zoom.class
            goto L3a
        L11:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$Gaussian> r6 = org.catrobat.catroid.raptor.PostProcessingData.Gaussian.class
            goto L3a
        L14:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$LensFlare> r6 = org.catrobat.catroid.raptor.PostProcessingData.LensFlare.class
            goto L3a
        L17:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$MotionBlur> r6 = org.catrobat.catroid.raptor.PostProcessingData.MotionBlur.class
            goto L3a
        L1a:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$Water> r6 = org.catrobat.catroid.raptor.PostProcessingData.Water.class
            goto L3a
        L1d:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$Fisheye> r6 = org.catrobat.catroid.raptor.PostProcessingData.Fisheye.class
            goto L3a
        L20:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$Crt> r6 = org.catrobat.catroid.raptor.PostProcessingData.Crt.class
            goto L3a
        L23:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$OldTv> r6 = org.catrobat.catroid.raptor.PostProcessingData.OldTv.class
            goto L3a
        L26:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$RadialBlur> r6 = org.catrobat.catroid.raptor.PostProcessingData.RadialBlur.class
            goto L3a
        L29:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$Chromatic> r6 = org.catrobat.catroid.raptor.PostProcessingData.Chromatic.class
            goto L3a
        L2c:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$Fxaa> r6 = org.catrobat.catroid.raptor.PostProcessingData.Fxaa.class
            goto L3a
        L2f:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$Grain> r6 = org.catrobat.catroid.raptor.PostProcessingData.Grain.class
            goto L3a
        L32:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$Levels> r6 = org.catrobat.catroid.raptor.PostProcessingData.Levels.class
            goto L3a
        L35:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$Vignette> r6 = org.catrobat.catroid.raptor.PostProcessingData.Vignette.class
            goto L3a
        L38:
            java.lang.Class<org.catrobat.catroid.raptor.PostProcessingData$Bloom> r6 = org.catrobat.catroid.raptor.PostProcessingData.Bloom.class
        L3a:
            java.util.List<org.catrobat.catroid.raptor.PostProcessingData> r1 = r5.effects
            java.lang.String r2 = "effects"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            java.util.Iterator r1 = r1.iterator()
        L47:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L5b
            java.lang.Object r2 = r1.next()
            r3 = r2
            org.catrobat.catroid.raptor.PostProcessingData r3 = (org.catrobat.catroid.raptor.PostProcessingData) r3
            boolean r3 = r6.isInstance(r3)
            if (r3 == 0) goto L47
            goto L5c
        L5b:
            r2 = r0
        L5c:
            org.catrobat.catroid.raptor.PostProcessingData r2 = (org.catrobat.catroid.raptor.PostProcessingData) r2
            if (r2 == 0) goto L61
            return r2
        L61:
            java.lang.Object r6 = r6.newInstance()     // Catch: java.lang.Exception -> L71
            org.catrobat.catroid.raptor.PostProcessingData r6 = (org.catrobat.catroid.raptor.PostProcessingData) r6     // Catch: java.lang.Exception -> L71
            r1 = 1
            r6.isEnabled = r1     // Catch: java.lang.Exception -> L71
            java.util.List<org.catrobat.catroid.raptor.PostProcessingData> r5 = r5.effects     // Catch: java.lang.Exception -> L71
            r5.add(r6)     // Catch: java.lang.Exception -> L71
            r0 = r6
            goto L75
        L71:
            r5 = move-exception
            r5.printStackTrace()
        L75:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.content.actions.SetPostProcessingAction.findOrCreateEffect(org.catrobat.catroid.raptor.PostProcessingComponent, int):org.catrobat.catroid.raptor.PostProcessingData");
    }

    private final void applyParam(PostProcessingData data, int paramIdx, float valFloat, boolean valBool) {
        if (paramIdx == 0) {
            data.isEnabled = valBool;
            return;
        }
        if (data instanceof PostProcessingData.RayTracing) {
            if (paramIdx == 1) {
                ((PostProcessingData.RayTracing) data).reflectivity = valFloat;
                return;
            }
            if (paramIdx == 2) {
                ((PostProcessingData.RayTracing) data).steps = (int) valFloat;
                return;
            }
            if (paramIdx == 9) {
                ((PostProcessingData.RayTracing) data).thickness = valFloat;
                return;
            }
            if (paramIdx == 10) {
                ((PostProcessingData.RayTracing) data).stride = valFloat;
                return;
            } else if (paramIdx == 13) {
                ((PostProcessingData.RayTracing) data).jitter = valFloat;
                return;
            } else {
                if (paramIdx != 14) {
                    return;
                }
                ((PostProcessingData.RayTracing) data).maxDistance = valFloat;
                return;
            }
        }
        if (data instanceof PostProcessingData.Bloom) {
            if (paramIdx == 1) {
                ((PostProcessingData.Bloom) data).intensity = valFloat;
                return;
            }
            if (paramIdx == 2) {
                ((PostProcessingData.Bloom) data).threshold = valFloat;
                return;
            }
            if (paramIdx == 3) {
                ((PostProcessingData.Bloom) data).blurAmount = valFloat;
                return;
            } else {
                if (paramIdx != 4) {
                    return;
                }
                ((PostProcessingData.Bloom) data).blurPasses = Math.max(1, (int) valFloat);
                return;
            }
        }
        if (data instanceof PostProcessingData.Levels) {
            if (paramIdx == 6) {
                ((PostProcessingData.Levels) data).contrast = valFloat;
                return;
            } else if (paramIdx == 7) {
                ((PostProcessingData.Levels) data).saturation = valFloat;
                return;
            } else {
                if (paramIdx != 8) {
                    return;
                }
                ((PostProcessingData.Levels) data).gamma = valFloat;
                return;
            }
        }
        if (data instanceof PostProcessingData.Vignette) {
            if (paramIdx == 1) {
                ((PostProcessingData.Vignette) data).intensity = valFloat;
                return;
            } else {
                if (paramIdx != 7) {
                    return;
                }
                ((PostProcessingData.Vignette) data).saturation = valFloat;
                return;
            }
        }
        if (data instanceof PostProcessingData.Grain) {
            if (paramIdx == 1) {
                ((PostProcessingData.Grain) data).amount = valFloat;
                return;
            }
            return;
        }
        if (data instanceof PostProcessingData.Chromatic) {
            if (paramIdx == 1) {
                ((PostProcessingData.Chromatic) data).strength = valFloat;
                return;
            } else {
                if (paramIdx != 10) {
                    return;
                }
                ((PostProcessingData.Chromatic) data).maxDistortion = valFloat;
                return;
            }
        }
        if (data instanceof PostProcessingData.RadialBlur) {
            if (paramIdx == 1) {
                ((PostProcessingData.RadialBlur) data).strength = valFloat;
                return;
            }
            if (paramIdx == 3) {
                ((PostProcessingData.RadialBlur) data).size = valFloat;
                return;
            } else {
                if (paramIdx != 4) {
                    return;
                }
                ((PostProcessingData.RadialBlur) data).blurPasses = Math.max(1, (int) valFloat);
                return;
            }
        }
        if (data instanceof PostProcessingData.OldTv) {
            if (paramIdx == 1 || paramIdx == 13) {
                ((PostProcessingData.OldTv) data).strength = valFloat;
                return;
            }
            return;
        }
        if ((data instanceof PostProcessingData.Crt) || (data instanceof PostProcessingData.Fisheye)) {
            return;
        }
        if (data instanceof PostProcessingData.Water) {
            if (paramIdx == 1) {
                ((PostProcessingData.Water) data).amount = valFloat;
                return;
            } else {
                if (paramIdx != 9) {
                    return;
                }
                ((PostProcessingData.Water) data).speed = valFloat;
                return;
            }
        }
        if (data instanceof PostProcessingData.MotionBlur) {
            if (paramIdx == 1) {
                ((PostProcessingData.MotionBlur) data).blurOpacity = RangesKt.coerceIn(valFloat, 0.0f, 0.99f);
                return;
            }
            return;
        }
        if (data instanceof PostProcessingData.LensFlare) {
            if (paramIdx == 1) {
                ((PostProcessingData.LensFlare) data).intensity = valFloat;
                return;
            } else {
                if (paramIdx != 2) {
                    return;
                }
                ((PostProcessingData.LensFlare) data).threshold = valFloat;
                return;
            }
        }
        if (data instanceof PostProcessingData.Gaussian) {
            if (paramIdx == 1) {
                ((PostProcessingData.Gaussian) data).amount = valFloat;
                return;
            }
            if (paramIdx == 3) {
                ((PostProcessingData.Gaussian) data).size = valFloat;
                return;
            } else {
                if (paramIdx != 4) {
                    return;
                }
                ((PostProcessingData.Gaussian) data).passes = Math.max(1, (int) valFloat);
                return;
            }
        }
        if (data instanceof PostProcessingData.Zoom) {
            if (paramIdx == 1) {
                ((PostProcessingData.Zoom) data).zoom = valFloat;
                return;
            } else if (paramIdx == 11) {
                ((PostProcessingData.Zoom) data).originX = valFloat;
                return;
            } else {
                if (paramIdx != 12) {
                    return;
                }
                ((PostProcessingData.Zoom) data).originY = valFloat;
                return;
            }
        }
        if ((data instanceof PostProcessingData.ACES) || !(data instanceof PostProcessingData.EyeAdaptation)) {
            return;
        }
        if (paramIdx == 1) {
            ((PostProcessingData.EyeAdaptation) data).targetLuminance = valFloat;
            return;
        }
        if (paramIdx == 9) {
            ((PostProcessingData.EyeAdaptation) data).speed = valFloat;
        } else if (paramIdx == 14) {
            ((PostProcessingData.EyeAdaptation) data).maxExposure = valFloat;
        } else {
            if (paramIdx != 15) {
                return;
            }
            ((PostProcessingData.EyeAdaptation) data).minExposure = valFloat;
        }
    }
}
