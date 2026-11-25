package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.UserList;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/AddItemToUserListAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: AddItemToUserListAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/AddItemToUserListAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "formulaItemToAdd", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFormulaItemToAdd", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFormulaItemToAdd", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userList", "Lorg/catrobat/catroid/formulaeditor/UserList;", "getUserList", "()Lorg/catrobat/catroid/formulaeditor/UserList;", "setUserList", "(Lorg/catrobat/catroid/formulaeditor/UserList;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AddItemToUserListAction extends TemporalAction {
    private Formula formulaItemToAdd;
    private Scope scope;
    private UserList userList;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getFormulaItemToAdd() {
        return this.formulaItemToAdd;
    }

    public final void setFormulaItemToAdd(Formula formula) {
        this.formulaItemToAdd = formula;
    }

    public final UserList getUserList() {
        return this.userList;
    }

    public final void setUserList(UserList userList) {
        this.userList = userList;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        FormulaElement root;
        Formula formula = this.formulaItemToAdd;
        Object objInterpretObject = formula != null ? formula.interpretObject(this.scope) : null;
        if (objInterpretObject == null) {
            objInterpretObject = Double.valueOf(0.0d);
        }
        Formula formula2 = this.formulaItemToAdd;
        if (formula2 != null && formula2 != null && (root = formula2.getRoot()) != null && root.isBoolean(this.scope)) {
            objInterpretObject = Boolean.valueOf(!(((Double) objInterpretObject).doubleValue() == 0.0d));
        }
        UserList userList = this.userList;
        if (userList != null) {
            userList.addListItem(objInterpretObject);
        }
    }
}
