package com.android.dx.cf.attrib;

import com.android.dx.cf.iface.Attribute;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseAttribute implements Attribute {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f66name;

    public BaseAttribute(String str) {
        if (str == null) {
            throw new NullPointerException("name == null");
        }
        this.f66name = str;
    }

    @Override // com.android.dx.cf.iface.Attribute
    public String getName() {
        return this.f66name;
    }
}
