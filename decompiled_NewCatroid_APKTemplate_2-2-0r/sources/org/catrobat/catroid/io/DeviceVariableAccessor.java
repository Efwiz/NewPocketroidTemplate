package org.catrobat.catroid.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.UserData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/DeviceVariableAccessor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DeviceVariableAccessor extends DeviceUserDataAccessor {
    private static final Object LOCK = new Object();

    public DeviceVariableAccessor(File projectDirectory) {
        super(projectDirectory);
    }

    @Override // org.catrobat.catroid.io.DeviceUserDataAccessor
    protected Object getLock() {
        return LOCK;
    }

    @Override // org.catrobat.catroid.io.DeviceUserDataAccessor
    protected String getDeviceFileName() {
        return Constants.DEVICE_VARIABLE_JSON_FILE_NAME;
    }

    @Override // org.catrobat.catroid.io.DeviceUserDataAccessor
    public List<? extends UserData> getUserData(Sprite sprite) {
        return sprite.getUserVariables();
    }

    @Override // org.catrobat.catroid.io.DeviceUserDataAccessor
    public List<? extends UserData> getUserData(Project project) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(project.getUserVariables());
        arrayList.addAll(project.getMultiplayerVariables());
        return arrayList;
    }
}
