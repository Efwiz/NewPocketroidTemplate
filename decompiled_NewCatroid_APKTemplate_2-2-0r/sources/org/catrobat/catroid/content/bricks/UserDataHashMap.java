package org.catrobat.catroid.content.bricks;

import java.util.HashMap;
import java.util.Map;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/UserDataHashMap.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UserDataHashMap extends HashMap<Brick.BrickData, UserData> implements Cloneable {
    private static final long serialVersionUID = 9030965461744658052L;

    @Override // java.util.HashMap, java.util.AbstractMap
    public UserDataHashMap clone() {
        UserData userVariable;
        UserDataHashMap userDataHashMap = new UserDataHashMap();
        for (Map.Entry<Brick.BrickData, UserData> entry : entrySet()) {
            if (entry.getValue() == null) {
                userVariable = null;
            } else if (Brick.BrickData.isUserList(entry.getKey())) {
                userVariable = new UserList((UserList) entry.getValue());
            } else {
                userVariable = new UserVariable((UserVariable) entry.getValue());
            }
            userDataHashMap.put(entry.getKey(), userVariable);
        }
        return userDataHashMap;
    }
}
