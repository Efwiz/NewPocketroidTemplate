package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.VoxelManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/VoxelSetTransparentAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: VoxelSetTransparentAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u0006\"\u0004\b\n\u0010\bR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/content/actions/VoxelSetTransparentAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "blockId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBlockId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBlockId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "isTransparent", "setTransparent", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VoxelSetTransparentAction extends TemporalAction {
    private Formula blockId;
    private Formula isTransparent;
    private Scope scope;

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

    /* JADX INFO: renamed from: isTransparent, reason: from getter */
    public final Formula getIsTransparent() {
        return this.isTransparent;
    }

    public final void setTransparent(Formula formula) {
        this.isTransparent = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Double dInterpretDouble;
        Double dInterpretDouble2;
        Formula formula = this.blockId;
        boolean z = false;
        short sDoubleValue = (formula == null || (dInterpretDouble2 = formula.interpretDouble(this.scope)) == null) ? (short) 0 : (short) dInterpretDouble2.doubleValue();
        Formula formula2 = this.isTransparent;
        if (formula2 != null && (dInterpretDouble = formula2.interpretDouble(this.scope)) != null && ((int) dInterpretDouble.doubleValue()) == 1) {
            z = true;
        }
        VoxelManager.INSTANCE.setTransparent(sDoubleValue, z);
    }
}
