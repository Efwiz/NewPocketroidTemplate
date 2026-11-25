package org.catrobat.catroid.formulaeditor;

import java.util.UUID;
import org.catrobat.catroid.common.Nameable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/UserData.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface UserData<T> extends Nameable {
    UUID getDeviceKey();

    T getValue();

    void reset();

    void setValue(T value);
}
