package org.catrobat.catroid.formulaeditor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/UserList.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UserList implements Serializable, UserData<List<Object>> {
    private static final long serialVersionUID = 1;
    private UUID deviceListKey;
    private int initialIndex;
    private transient List<Object> list;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f574name;

    public UserList() {
        this.initialIndex = -1;
        this.list = new ArrayList();
    }

    public UserList(final String name2) {
        this.initialIndex = -1;
        this.f574name = name2;
        this.list = new ArrayList();
        this.deviceListKey = UUID.randomUUID();
    }

    public UserList(final String name2, final List<Object> value) {
        this.initialIndex = -1;
        this.f574name = name2;
        this.list = value;
        this.deviceListKey = UUID.randomUUID();
    }

    public UserList(UserList userList) {
        this.initialIndex = -1;
        this.f574name = userList.f574name;
        this.list = new ArrayList(userList.list);
        this.deviceListKey = UUID.randomUUID();
    }

    public int getInitialIndex() {
        return this.initialIndex;
    }

    public void setInitialIndex(int initialIndex) {
        this.initialIndex = initialIndex;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.f574name;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public void setName(String name2) {
        this.f574name = name2;
    }

    @Override // org.catrobat.catroid.formulaeditor.UserData
    public List<Object> getValue() {
        return this.list;
    }

    @Override // org.catrobat.catroid.formulaeditor.UserData
    public void setValue(List<Object> list) {
        this.list = list;
    }

    public void addListItem(Object listItem) {
        this.list.add(listItem);
    }

    public int getIndexOf(Object listItem) {
        return this.list.indexOf(listItem);
    }

    @Override // org.catrobat.catroid.formulaeditor.UserData
    public void reset() {
        this.list.clear();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && getClass().equals(obj.getClass())) {
            return ((UserList) obj).f574name.equals(this.f574name);
        }
        return false;
    }

    public boolean hasSameListSize(UserList listToCheck) {
        return listToCheck.list.size() == this.list.size();
    }

    public int hashCode() {
        return this.f574name.hashCode();
    }

    @Override // org.catrobat.catroid.formulaeditor.UserData
    public UUID getDeviceKey() {
        return this.deviceListKey;
    }

    public void setDeviceListKey(UUID deviceListFileName) {
        this.deviceListKey = deviceListFileName;
    }
}
