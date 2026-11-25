package org.catrobat.catroid.io;

import java.io.File;
import java.util.List;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.UserData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/DeviceListAccessor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DeviceListAccessor extends DeviceUserDataAccessor {
    private static final Object LOCK = new Object();

    public DeviceListAccessor(File projectDirectory) {
        super(projectDirectory);
    }

    @Override // org.catrobat.catroid.io.DeviceUserDataAccessor
    protected Object getLock() {
        return LOCK;
    }

    @Override // org.catrobat.catroid.io.DeviceUserDataAccessor
    protected String getDeviceFileName() {
        return Constants.DEVICE_LIST_JSON_FILE_NAME;
    }

    @Override // org.catrobat.catroid.io.DeviceUserDataAccessor
    public List<? extends UserData> getUserData(Sprite sprite) {
        return sprite.getUserLists();
    }

    @Override // org.catrobat.catroid.io.DeviceUserDataAccessor
    public List<? extends UserData> getUserData(Project project) {
        return project.getUserLists();
    }
}
