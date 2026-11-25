package org.catrobat.catroid.content.actions;

import android.content.Context;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.reandroid.apk.ApkUtil;
import kotlin.Metadata;
import org.antlr.stringtemplate.language.ASTExpr;
import org.catrobat.catroid.content.FireBaseManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WriteBaseAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: WriteBaseAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0006\"\u0004\b\r\u0010\bR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0006\"\u0004\b\u0016\u0010\b¨\u0006\u001b"}, d2 = {"Lorg/catrobat/catroid/content/actions/WriteBaseAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", ApkUtil.DEF_MODULE_NAME, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBase", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBase", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "contextt", "Landroid/content/Context;", ASTExpr.DEFAULT_MAP_KEY_NAME, "getKey", "setKey", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "value", "getValue", "setValue", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WriteBaseAction extends TemporalAction {
    private Formula base;
    private Context contextt;
    private Formula key;
    private Scope scope;
    private Formula value;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getBase() {
        return this.base;
    }

    public final void setBase(Formula formula) {
        this.base = formula;
    }

    public final Formula getKey() {
        return this.key;
    }

    public final void setKey(Formula formula) {
        this.key = formula;
    }

    public final Formula getValue() {
        return this.value;
    }

    public final void setValue(Formula formula) {
        this.value = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.base;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        Formula formula2 = this.key;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "";
        }
        Formula formula3 = this.value;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        FireBaseManager.INSTANCE.writeToDatabase(strInterpretString, strInterpretString2, strInterpretString3 != null ? strInterpretString3 : "");
    }
}
