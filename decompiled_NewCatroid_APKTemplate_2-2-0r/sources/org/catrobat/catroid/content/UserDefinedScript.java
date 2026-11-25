package org.catrobat.catroid.content;

import java.util.List;
import java.util.UUID;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.UserDefinedReceiverBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.UserDefinedBrickEventId;
import org.catrobat.catroid.formulaeditor.UserData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/UserDefinedScript.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UserDefinedScript extends Script {
    private static final long serialVersionUID = 1;
    private Boolean screenRefresh = true;
    private UUID userDefinedBrickID;
    private List<Object> userDefinedBrickInputs;

    public UserDefinedScript(UUID userDefinedBrickID) {
        this.userDefinedBrickID = userDefinedBrickID;
    }

    public UserDefinedScript() {
    }

    @Override // org.catrobat.catroid.content.Script
    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new UserDefinedReceiverBrick(this);
        }
        return this.scriptBrick;
    }

    @Override // org.catrobat.catroid.content.Script
    public EventId createEventId(Sprite sprite) {
        return new UserDefinedBrickEventId(this.userDefinedBrickID);
    }

    public void setUserDefinedBrickInputs(List<Object> userDefinedBrickInputs) {
        this.userDefinedBrickInputs = userDefinedBrickInputs;
    }

    public UserData getUserDefinedBrickInput(String name2) {
        for (Object obj : this.userDefinedBrickInputs) {
            if (obj instanceof UserData) {
                UserData userData = (UserData) obj;
                if (userData.getName().equals(name2)) {
                    return userData;
                }
            }
        }
        return null;
    }

    public UUID getUserDefinedBrickID() {
        return this.userDefinedBrickID;
    }

    public void setScreenRefresh(Boolean screenRefresh) {
        this.screenRefresh = screenRefresh;
    }

    public Boolean getScreenRefresh() {
        return this.screenRefresh;
    }
}
