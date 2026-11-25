package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.antlr.stringtemplate.language.ASTExpr;
import org.catrobat.catroid.common.NewCatroidHttpManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/HttpConfigAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: HttpConfigAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000e¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/HttpConfigAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "configTypeSelection", "", "getConfigTypeSelection", "()I", "setConfigTypeSelection", "(I)V", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getKey", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setKey", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "requestId", "getRequestId", "setRequestId", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "value", "getValue", "setValue", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HttpConfigAction extends TemporalAction {
    private int configTypeSelection;
    private Formula key;
    private Formula requestId;
    private Scope scope;
    private Formula value;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getRequestId() {
        return this.requestId;
    }

    public final void setRequestId(Formula formula) {
        this.requestId = formula;
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

    public final int getConfigTypeSelection() {
        return this.configTypeSelection;
    }

    public final void setConfigTypeSelection(int i) {
        this.configTypeSelection = i;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.requestId;
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
        String str = strInterpretString3 != null ? strInterpretString3 : "";
        if (strInterpretString.length() == 0) {
            return;
        }
        String str2 = "header";
        switch (this.configTypeSelection) {
            case 1:
                str2 = "query";
                break;
            case 2:
                str2 = "timeout_connect";
                break;
            case 3:
                str2 = "timeout_read";
                break;
            case 4:
                str2 = "timeout_write";
                break;
            case 5:
                str2 = "follow_redirects";
                break;
            case 6:
                str2 = "trust_all_certs";
                break;
            case 7:
                str2 = "use_cookies";
                break;
            case 8:
                str2 = "proxy_host";
                break;
            case 9:
                str2 = "proxy_port";
                break;
        }
        NewCatroidHttpManager.INSTANCE.setConfig(strInterpretString, str2, strInterpretString2, str);
    }
}
