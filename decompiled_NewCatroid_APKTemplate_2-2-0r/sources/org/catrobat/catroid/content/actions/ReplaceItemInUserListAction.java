package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.ArrayList;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ReplaceItemInUserListAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ReplaceItemInUserListAction extends TemporalAction {
    private Formula formulaIndexToReplace;
    private Formula formulaItemToInsert;
    private Scope scope;
    private UserList userList;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        if (this.userList == null) {
            return;
        }
        Formula formula = this.formulaItemToInsert;
        Object objValueOf = formula == null ? Double.valueOf(0.0d) : formula.interpretObject(this.scope);
        int iIntValue = 1;
        try {
            Formula formula2 = this.formulaIndexToReplace;
            if (formula2 != null) {
                iIntValue = formula2.interpretInteger(this.scope).intValue();
            }
        } catch (InterpretationException unused) {
        }
        int i = iIntValue - 1;
        if (i >= this.userList.getValue().size() || i < 0) {
            return;
        }
        ((ArrayList) this.userList.getValue()).set(i, objValueOf);
    }

    public void setUserList(UserList userVariable) {
        this.userList = userVariable;
    }

    public void setFormulaIndexToReplace(Formula formulaIndexToReplace) {
        this.formulaIndexToReplace = formulaIndexToReplace;
    }

    public void setFormulaItemToInsert(Formula formulaItemToInsert) {
        this.formulaItemToInsert = formulaItemToInsert;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
