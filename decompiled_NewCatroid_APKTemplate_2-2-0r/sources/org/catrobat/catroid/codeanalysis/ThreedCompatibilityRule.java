package org.catrobat.catroid.codeanalysis;

import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.CreateCubeBrick;
import org.catrobat.catroid.content.bricks.CreateParticlesBrick;
import org.catrobat.catroid.content.bricks.CreateSphereBrick;
import org.catrobat.catroid.content.bricks.DeleteParticlesBrick;
import org.catrobat.catroid.content.bricks.EnablePbrRenderBrick;
import org.catrobat.catroid.content.bricks.RemovePbrLightBrick;
import org.catrobat.catroid.content.bricks.SetAmbientLightBrick;
import org.catrobat.catroid.content.bricks.SetAnisotropicFilterBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundLightBrick;
import org.catrobat.catroid.content.bricks.SetDirectionalLight2Brick;
import org.catrobat.catroid.content.bricks.SetDirectionalLightBrick;
import org.catrobat.catroid.content.bricks.SetMaterialBrick;
import org.catrobat.catroid.content.bricks.SetObjectColorBrick;
import org.catrobat.catroid.content.bricks.SetObjectTextureBrick;
import org.catrobat.catroid.content.bricks.SetParticleEmissionBrick;
import org.catrobat.catroid.content.bricks.SetPointLightBrick;
import org.catrobat.catroid.content.bricks.SetPostProcessingBrick;
import org.catrobat.catroid.content.bricks.SetPostProcessingNewBrick;
import org.catrobat.catroid.content.bricks.SetShaderCodeBrick;
import org.catrobat.catroid.content.bricks.SetShaderUniformFloatBrick;
import org.catrobat.catroid.content.bricks.SetShaderUniformVec3Brick;
import org.catrobat.catroid.content.bricks.SetShadowQualityBrick;
import org.catrobat.catroid.content.bricks.SetSkyboxBrick;
import org.catrobat.catroid.content.bricks.SetSpotLightBrick;
import org.catrobat.catroid.content.bricks.SetTextureTilingBrick;
import org.catrobat.catroid.content.bricks.ThreedCreateCylinderBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/ThreedCompatibilityRule.class
  classes12.dex
 */
/* JADX INFO: compiled from: ThreedCompatibilityRule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/ThreedCompatibilityRule;", "Lorg/catrobat/catroid/codeanalysis/AnalysisRule;", "context2", "Landroid/content/Context;", "(Landroid/content/Context;)V", "analyze", "Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "context", "Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ThreedCompatibilityRule implements AnalysisRule {
    private final Context context2;

    public ThreedCompatibilityRule(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context2");
        this.context2 = context2;
    }

    @Override // org.catrobat.catroid.codeanalysis.AnalysisRule
    public AnalysisResult analyze(Brick brick, GlobalAnalysisContext context) {
        Intrinsics.checkNotNullParameter(brick, "brick");
        Intrinsics.checkNotNullParameter(context, "context");
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        if (currentSprite == null) {
            return null;
        }
        boolean isPbrEnabledInProject = context.getIsPbrEnabledInProject();
        ArrayList arrayList = new ArrayList();
        Iterator<Script> it = currentSprite.getScriptList().iterator();
        while (it.hasNext()) {
            it.next().addToFlatList(arrayList);
        }
        Iterator it2 = arrayList.iterator();
        int i = 0;
        while (true) {
            if (!it2.hasNext()) {
                i = -1;
                break;
            }
            Brick brick2 = (Brick) it2.next();
            if ((brick2 instanceof EnablePbrRenderBrick) && !CodeAnalyzer.INSTANCE.isBrickCommented(brick2)) {
                break;
            }
            i++;
        }
        boolean z = i != -1;
        if (isPbrEnabledInProject) {
            if ((brick instanceof SetObjectColorBrick) || (brick instanceof SetObjectTextureBrick) || (brick instanceof SetAmbientLightBrick) || (brick instanceof SetDirectionalLightBrick) || (brick instanceof SetShaderCodeBrick) || (brick instanceof SetShaderUniformFloatBrick) || (brick instanceof SetShaderUniformVec3Brick)) {
                Severity severity = Severity.WARNING;
                String string = this.context2.getString(R.string.analysis_3d_render_1_only);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                return new AnalysisResult(severity, string);
            }
            if ((brick instanceof CreateCubeBrick) || (brick instanceof CreateSphereBrick) || (brick instanceof ThreedCreateCylinderBrick)) {
                int iIndexOf = arrayList.indexOf(brick);
                if (z && iIndexOf != -1 && iIndexOf < i) {
                    Severity severity2 = Severity.ERROR;
                    String string2 = this.context2.getString(R.string.analysis_3d_primitive_before_pbr);
                    Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                    return new AnalysisResult(severity2, string2);
                }
            }
        } else if ((brick instanceof SetMaterialBrick) || (brick instanceof SetPostProcessingNewBrick) || (brick instanceof SetPostProcessingBrick) || (brick instanceof SetShadowQualityBrick) || (brick instanceof SetSkyboxBrick) || (brick instanceof SetPointLightBrick) || (brick instanceof SetSpotLightBrick) || (brick instanceof SetDirectionalLight2Brick) || (brick instanceof SetBackgroundLightBrick) || (brick instanceof RemovePbrLightBrick) || (brick instanceof SetTextureTilingBrick) || (brick instanceof SetAnisotropicFilterBrick) || (brick instanceof CreateParticlesBrick) || (brick instanceof SetParticleEmissionBrick) || (brick instanceof DeleteParticlesBrick)) {
            Severity severity3 = Severity.ERROR;
            String string3 = this.context2.getString(R.string.analysis_3d_render_2_only);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            return new AnalysisResult(severity3, string3);
        }
        return null;
    }
}
