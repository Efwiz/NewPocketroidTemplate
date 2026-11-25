package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.VoxelManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/VoxelCreateWorldAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: VoxelCreateWorldAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000eR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000eR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\f\"\u0004\b\u001d\u0010\u000eR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\f\"\u0004\b \u0010\u000e¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/content/actions/VoxelCreateWorldAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "sizeX", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getSizeX", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setSizeX", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "sizeY", "getSizeY", "setSizeY", "sizeZ", "getSizeZ", "setSizeZ", "worldId", "getWorldId", "setWorldId", "worldX", "getWorldX", "setWorldX", "worldY", "getWorldY", "setWorldY", "worldZ", "getWorldZ", "setWorldZ", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VoxelCreateWorldAction extends TemporalAction {
    private Scope scope;
    private Formula sizeX;
    private Formula sizeY;
    private Formula sizeZ;
    private Formula worldId;
    private Formula worldX;
    private Formula worldY;
    private Formula worldZ;

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

    public final Formula getSizeX() {
        return this.sizeX;
    }

    public final void setSizeX(Formula formula) {
        this.sizeX = formula;
    }

    public final Formula getSizeY() {
        return this.sizeY;
    }

    public final void setSizeY(Formula formula) {
        this.sizeY = formula;
    }

    public final Formula getSizeZ() {
        return this.sizeZ;
    }

    public final void setSizeZ(Formula formula) {
        this.sizeZ = formula;
    }

    public final Formula getWorldX() {
        return this.worldX;
    }

    public final void setWorldX(Formula formula) {
        this.worldX = formula;
    }

    public final Formula getWorldY() {
        return this.worldY;
    }

    public final void setWorldY(Formula formula) {
        this.worldY = formula;
    }

    public final Formula getWorldZ() {
        return this.worldZ;
    }

    public final void setWorldZ(Formula formula) {
        this.worldZ = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Double dInterpretDouble;
        Double dInterpretDouble2;
        Double dInterpretDouble3;
        Double dInterpretDouble4;
        Double dInterpretDouble5;
        Double dInterpretDouble6;
        Formula formula = this.worldId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "chunk";
        }
        String str = strInterpretString;
        Formula formula2 = this.sizeX;
        int iDoubleValue = (formula2 == null || (dInterpretDouble6 = formula2.interpretDouble(this.scope)) == null) ? 16 : (int) dInterpretDouble6.doubleValue();
        Formula formula3 = this.sizeY;
        int iDoubleValue2 = (formula3 == null || (dInterpretDouble5 = formula3.interpretDouble(this.scope)) == null) ? 32 : (int) dInterpretDouble5.doubleValue();
        Formula formula4 = this.sizeZ;
        int iDoubleValue3 = (formula4 == null || (dInterpretDouble4 = formula4.interpretDouble(this.scope)) == null) ? 16 : (int) dInterpretDouble4.doubleValue();
        Formula formula5 = this.worldX;
        int iDoubleValue4 = (formula5 == null || (dInterpretDouble3 = formula5.interpretDouble(this.scope)) == null) ? 0 : (int) dInterpretDouble3.doubleValue();
        Formula formula6 = this.worldY;
        int iDoubleValue5 = (formula6 == null || (dInterpretDouble2 = formula6.interpretDouble(this.scope)) == null) ? 0 : (int) dInterpretDouble2.doubleValue();
        Formula formula7 = this.worldZ;
        VoxelManager.INSTANCE.createWorld(str, iDoubleValue, iDoubleValue2, iDoubleValue3, iDoubleValue4, iDoubleValue5, (formula7 == null || (dInterpretDouble = formula7.interpretDouble(this.scope)) == null) ? 0 : (int) dInterpretDouble.doubleValue());
    }
}
