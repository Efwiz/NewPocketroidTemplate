package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.VoxelManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/VoxelConfigAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: VoxelConfigAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\b¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/VoxelConfigAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "atlasX", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getAtlasX", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setAtlasX", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "atlasY", "getAtlasY", "setAtlasY", "blockId", "getBlockId", "setBlockId", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "shape", "getShape", "setShape", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VoxelConfigAction extends TemporalAction {
    private Formula atlasX;
    private Formula atlasY;
    private Formula blockId;
    private Scope scope;
    private Formula shape;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getBlockId() {
        return this.blockId;
    }

    public final void setBlockId(Formula formula) {
        this.blockId = formula;
    }

    public final Formula getShape() {
        return this.shape;
    }

    public final void setShape(Formula formula) {
        this.shape = formula;
    }

    public final Formula getAtlasX() {
        return this.atlasX;
    }

    public final void setAtlasX(Formula formula) {
        this.atlasX = formula;
    }

    public final Formula getAtlasY() {
        return this.atlasY;
    }

    public final void setAtlasY(Formula formula) {
        this.atlasY = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Double dInterpretDouble;
        Double dInterpretDouble2;
        Double dInterpretDouble3;
        Double dInterpretDouble4;
        Formula formula = this.blockId;
        int iDoubleValue = 0;
        short sDoubleValue = (formula == null || (dInterpretDouble4 = formula.interpretDouble(this.scope)) == null) ? (short) 0 : (short) dInterpretDouble4.doubleValue();
        Formula formula2 = this.shape;
        int iDoubleValue2 = (formula2 == null || (dInterpretDouble3 = formula2.interpretDouble(this.scope)) == null) ? 0 : (int) dInterpretDouble3.doubleValue();
        Formula formula3 = this.atlasX;
        int iDoubleValue3 = (formula3 == null || (dInterpretDouble2 = formula3.interpretDouble(this.scope)) == null) ? 0 : (int) dInterpretDouble2.doubleValue();
        Formula formula4 = this.atlasY;
        if (formula4 != null && (dInterpretDouble = formula4.interpretDouble(this.scope)) != null) {
            iDoubleValue = (int) dInterpretDouble.doubleValue();
        }
        VoxelManager.INSTANCE.configureBlock(sDoubleValue, iDoubleValue2, iDoubleValue3, iDoubleValue);
    }
}
