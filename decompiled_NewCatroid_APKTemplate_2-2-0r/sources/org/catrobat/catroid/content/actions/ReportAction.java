package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ReportAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ReportAction extends Action {
    private Script currentScript;
    private Formula reportFormula;
    private Scope scope;

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setCurrentScript(Script currentScript) {
        this.currentScript = currentScript;
    }

    public void setReportFormula(Formula reportFormula) {
        this.reportFormula = reportFormula;
    }

    public Formula getReportFormula() {
        return this.reportFormula;
    }

    public Scope getScope() {
        return this.scope;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!(this.actor instanceof Look)) {
            return true;
        }
        ((Look) this.actor).stopThreadWithScript(this.currentScript);
        return true;
    }
}
