package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.VoxelManager;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/VoxelSetBlockAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: VoxelSetBlockAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\b¨\u0006\""}, d2 = {"Lorg/catrobat/catroid/content/actions/VoxelSetBlockAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "data", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getData", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setData", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ModuleXmlParser.TYPE, "getType", "setType", "worldId", "getWorldId", "setWorldId", "x", "getX", "setX", "y", "getY", "setY", CompressorStreamFactory.Z, "getZ", "setZ", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VoxelSetBlockAction extends TemporalAction {
    private Formula data;
    private Scope scope;
    private Formula type;
    private Formula worldId;
    private Formula x;
    private Formula y;
    private Formula z;

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

    public final Formula getX() {
        return this.x;
    }

    public final void setX(Formula formula) {
        this.x = formula;
    }

    public final Formula getY() {
        return this.y;
    }

    public final void setY(Formula formula) {
        this.y = formula;
    }

    public final Formula getZ() {
        return this.z;
    }

    public final void setZ(Formula formula) {
        this.z = formula;
    }

    public final Formula getType() {
        return this.type;
    }

    public final void setType(Formula formula) {
        this.type = formula;
    }

    public final Formula getData() {
        return this.data;
    }

    public final void setData(Formula formula) {
        this.data = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Double dInterpretDouble;
        Double dInterpretDouble2;
        Double dInterpretDouble3;
        Double dInterpretDouble4;
        Double dInterpretDouble5;
        Formula formula = this.worldId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "chunk1";
        }
        String str = strInterpretString;
        Formula formula2 = this.x;
        int iDoubleValue = (formula2 == null || (dInterpretDouble5 = formula2.interpretDouble(this.scope)) == null) ? 0 : (int) dInterpretDouble5.doubleValue();
        Formula formula3 = this.y;
        int iDoubleValue2 = (formula3 == null || (dInterpretDouble4 = formula3.interpretDouble(this.scope)) == null) ? 0 : (int) dInterpretDouble4.doubleValue();
        Formula formula4 = this.z;
        int iDoubleValue3 = (formula4 == null || (dInterpretDouble3 = formula4.interpretDouble(this.scope)) == null) ? 0 : (int) dInterpretDouble3.doubleValue();
        Formula formula5 = this.type;
        short sDoubleValue = (formula5 == null || (dInterpretDouble2 = formula5.interpretDouble(this.scope)) == null) ? (short) 1 : (short) dInterpretDouble2.doubleValue();
        Formula formula6 = this.data;
        VoxelManager.INSTANCE.setBlock(str, iDoubleValue, iDoubleValue2, iDoubleValue3, sDoubleValue, (formula6 == null || (dInterpretDouble = formula6.interpretDouble(this.scope)) == null) ? (byte) 0 : (byte) dInterpretDouble.doubleValue());
    }
}
