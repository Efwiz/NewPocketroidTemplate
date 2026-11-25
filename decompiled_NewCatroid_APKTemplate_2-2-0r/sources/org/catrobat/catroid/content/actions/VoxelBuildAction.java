package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.raptor.VoxelManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/VoxelBuildAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: VoxelBuildAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\b¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/VoxelBuildAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "atlasH", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getAtlasH", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setAtlasH", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "atlasW", "getAtlasW", "setAtlasW", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "texture", "getTexture", "setTexture", "worldId", "getWorldId", "setWorldId", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VoxelBuildAction extends TemporalAction {
    private Formula atlasH;
    private Formula atlasW;
    private Scope scope;
    private Formula texture;
    private Formula worldId;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getWorldId() {
        return this.worldId;
    }

    public final void setWorldId(Formula formula) {
        this.worldId = formula;
    }

    public final Formula getTexture() {
        return this.texture;
    }

    public final void setTexture(Formula formula) {
        this.texture = formula;
    }

    public final Formula getAtlasW() {
        return this.atlasW;
    }

    public final void setAtlasW(Formula formula) {
        this.atlasW = formula;
    }

    public final Formula getAtlasH() {
        return this.atlasH;
    }

    public final void setAtlasH(Formula formula) {
        this.atlasH = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageListener activeStageListener;
        ThreeDManager threeDManager;
        Double dInterpretDouble;
        Double dInterpretDouble2;
        Formula formula = this.worldId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "chunk1";
        }
        String str = strInterpretString;
        Formula formula2 = this.texture;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        String str2 = strInterpretString2 == null ? "" : strInterpretString2;
        Formula formula3 = this.atlasW;
        int iDoubleValue = (formula3 == null || (dInterpretDouble2 = formula3.interpretDouble(this.scope)) == null) ? 16 : (int) dInterpretDouble2.doubleValue();
        Formula formula4 = this.atlasH;
        int iDoubleValue2 = (formula4 == null || (dInterpretDouble = formula4.interpretDouble(this.scope)) == null) ? 16 : (int) dInterpretDouble.doubleValue();
        VoxelManager.VoxelBuffer buffer = VoxelManager.INSTANCE.getBuffer(str);
        if (buffer == null || (activeStageListener = StageActivity.getActiveStageListener()) == null || (threeDManager = activeStageListener.getThreeDManager()) == null) {
            return;
        }
        threeDManager.updateVoxelMesh(str, buffer, str2, iDoubleValue, iDoubleValue2);
    }
}
