package org.catrobat.catroid.formulaeditor;

import java.io.Serializable;
import java.util.UUID;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/UserVariable.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UserVariable implements Serializable, UserData<Object> {
    private static final long serialVersionUID = 1;
    private UUID deviceValueKey;
    private transient boolean dummy;
    private int initialIndex;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f575name;
    private transient Object value;
    private transient boolean visible;

    public UserVariable() {
        this.initialIndex = -1;
        this.visible = true;
        this.dummy = false;
        this.value = Double.valueOf(0.0d);
    }

    public UserVariable(String name2) {
        this.initialIndex = -1;
        this.visible = true;
        this.dummy = false;
        this.f575name = name2;
        this.value = Double.valueOf(0.0d);
        this.deviceValueKey = UUID.randomUUID();
    }

    public UserVariable(final String name2, final Object value) {
        this.initialIndex = -1;
        this.visible = true;
        this.dummy = false;
        this.f575name = name2;
        this.value = value;
        this.deviceValueKey = UUID.randomUUID();
    }

    public UserVariable(UserVariable variable) {
        this.initialIndex = -1;
        this.visible = true;
        this.dummy = false;
        this.f575name = variable.f575name;
        this.value = variable.value;
        this.deviceValueKey = UUID.randomUUID();
    }

    public int getInitialIndex() {
        return this.initialIndex;
    }

    public void setInitialIndex(int initialIndex) {
        this.initialIndex = initialIndex;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.f575name;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public void setName(String name2) {
        this.f575name = name2;
    }

    @Override // org.catrobat.catroid.formulaeditor.UserData
    public Object getValue() {
        return this.value;
    }

    @Override // org.catrobat.catroid.formulaeditor.UserData
    public void setValue(Object value) {
        this.value = value;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isDummy() {
        return this.dummy;
    }

    public void setDummy(boolean dummy) {
        this.dummy = dummy;
    }

    @Override // org.catrobat.catroid.formulaeditor.UserData
    public void reset() {
        this.value = Double.valueOf(0.0d);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && getClass().equals(obj.getClass())) {
            return ((UserVariable) obj).f575name.equals(this.f575name);
        }
        return false;
    }

    public boolean hasSameValue(UserVariable variableToCheck) {
        return variableToCheck.value.equals(this.value);
    }

    public int hashCode() {
        return this.f575name.hashCode();
    }

    @Override // org.catrobat.catroid.formulaeditor.UserData
    public UUID getDeviceKey() {
        return this.deviceValueKey;
    }

    public void setDeviceValueKey(UUID deviceValueFileName) {
        this.deviceValueKey = deviceValueFileName;
    }
}
