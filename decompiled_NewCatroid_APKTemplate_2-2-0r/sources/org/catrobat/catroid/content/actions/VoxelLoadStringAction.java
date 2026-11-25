package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.huawei.hms.framework.common.ContainerUtils;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.VoxelManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/VoxelLoadStringAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: VoxelLoadStringAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\b¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/VoxelLoadStringAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "dataStr", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getDataStr", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setDataStr", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "delimX", "getDelimX", "setDelimX", "delimY", "getDelimY", "setDelimY", "delimZ", "getDelimZ", "setDelimZ", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "worldId", "getWorldId", "setWorldId", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VoxelLoadStringAction extends TemporalAction {
    private Formula dataStr;
    private Formula delimX;
    private Formula delimY;
    private Formula delimZ;
    private Scope scope;
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

    public final Formula getDataStr() {
        return this.dataStr;
    }

    public final void setDataStr(Formula formula) {
        this.dataStr = formula;
    }

    public final Formula getDelimX() {
        return this.delimX;
    }

    public final void setDelimX(Formula formula) {
        this.delimX = formula;
    }

    public final Formula getDelimY() {
        return this.delimY;
    }

    public final void setDelimY(Formula formula) {
        this.delimY = formula;
    }

    public final Formula getDelimZ() {
        return this.delimZ;
    }

    public final void setDelimZ(Formula formula) {
        this.delimZ = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.worldId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "chunk1";
        }
        String str = strInterpretString;
        Formula formula2 = this.dataStr;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "";
        }
        String str2 = strInterpretString2;
        Formula formula3 = this.delimX;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        if (strInterpretString3 == null) {
            strInterpretString3 = "$";
        }
        String str3 = strInterpretString3;
        Formula formula4 = this.delimY;
        String strInterpretString4 = formula4 != null ? formula4.interpretString(this.scope) : null;
        if (strInterpretString4 == null) {
            strInterpretString4 = "#";
        }
        String str4 = strInterpretString4;
        Formula formula5 = this.delimZ;
        String strInterpretString5 = formula5 != null ? formula5.interpretString(this.scope) : null;
        VoxelManager.INSTANCE.loadFromString(str, str2, str3, str4, strInterpretString5 == null ? ContainerUtils.FIELD_DELIMITER : strInterpretString5);
    }
}
