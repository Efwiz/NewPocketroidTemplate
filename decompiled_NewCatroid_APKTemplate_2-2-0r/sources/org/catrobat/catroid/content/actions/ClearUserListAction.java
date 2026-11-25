package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.formulaeditor.UserList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ClearUserListAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ClearUserListAction extends TemporalAction {
    private UserList userList;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        UserList userList = this.userList;
        if (userList == null) {
            return;
        }
        userList.reset();
    }

    public void setUserList(UserList userVariable) {
        this.userList = userVariable;
    }
}
