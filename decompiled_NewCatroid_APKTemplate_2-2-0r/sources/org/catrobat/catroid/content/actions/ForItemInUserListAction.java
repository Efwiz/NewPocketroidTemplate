package org.catrobat.catroid.content.actions;

import java.util.List;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ForItemInUserListAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ForItemInUserListAction extends LoopAction {
    private UserVariable currentItemVariable;
    private UserList userList;
    private boolean isCurrentLoopInitialized = false;
    private int index = 0;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.RepeatAction, com.badlogic.gdx.scenes.scene2d.actions.DelegateAction
    public boolean delegate(float delta) {
        List<Object> value;
        if (!this.isCurrentLoopInitialized) {
            setCurrentTime(0.0f);
            this.isCurrentLoopInitialized = true;
        }
        UserList userList = this.userList;
        if (userList == null || (value = userList.getValue()) == null || this.index >= value.size()) {
            return true;
        }
        setCurrentItemVariable(value.get(this.index));
        setCurrentTime(getCurrentTime() + delta);
        if (this.action != null && this.action.act(delta) && !isLoopDelayNeeded()) {
            this.index++;
            this.isCurrentLoopInitialized = false;
            this.action.restart();
        }
        return false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.RepeatAction, com.badlogic.gdx.scenes.scene2d.actions.DelegateAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.isCurrentLoopInitialized = false;
        this.index = 0;
        super.restart();
    }

    public void setUserList(UserList userList) {
        this.userList = userList;
    }

    public void setCurrentItemVariable(UserVariable variable) {
        this.currentItemVariable = variable;
    }

    private void setCurrentItemVariable(Object listItem) {
        this.currentItemVariable.setValue(listItem);
    }
}
