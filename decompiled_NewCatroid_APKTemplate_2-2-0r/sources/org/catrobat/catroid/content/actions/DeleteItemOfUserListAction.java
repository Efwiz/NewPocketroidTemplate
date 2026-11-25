package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserList;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/DeleteItemOfUserListAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: DeleteItemOfUserListAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/DeleteItemOfUserListAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "formulaIndexToDelete", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFormulaIndexToDelete", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFormulaIndexToDelete", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userList", "Lorg/catrobat/catroid/formulaeditor/UserList;", "getUserList", "()Lorg/catrobat/catroid/formulaeditor/UserList;", "setUserList", "(Lorg/catrobat/catroid/formulaeditor/UserList;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeleteItemOfUserListAction extends TemporalAction {
    private Formula formulaIndexToDelete;
    private Scope scope;
    private UserList userList;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getFormulaIndexToDelete() {
        return this.formulaIndexToDelete;
    }

    public final void setFormulaIndexToDelete(Formula formula) {
        this.formulaIndexToDelete = formula;
    }

    public final UserList getUserList() {
        return this.userList;
    }

    public final void setUserList(UserList userList) {
        this.userList = userList;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        int iIntValue;
        Integer numInterpretInteger;
        List<Object> value;
        UserList userList = this.userList;
        Integer numValueOf = (userList == null || (value = userList.getValue()) == null) ? null : Integer.valueOf(value.size());
        Integer num = (numValueOf == null || numValueOf.intValue() != 0) ? numValueOf : null;
        if (num != null) {
            int iIntValue2 = num.intValue();
            try {
                Formula formula = this.formulaIndexToDelete;
                iIntValue = (formula == null || (numInterpretInteger = formula.interpretInteger(this.scope)) == null) ? 0 : numInterpretInteger.intValue() - 1;
            } catch (InterpretationException e) {
                Log.e(getClass().getSimpleName(), "Interpreting formula as integer failed", e);
                iIntValue = -1;
            }
            if (iIntValue < 0 || iIntValue >= iIntValue2) {
                return;
            }
            UserList userList2 = this.userList;
            Intrinsics.checkNotNull(userList2);
            userList2.getValue().remove(iIntValue);
        }
    }
}
