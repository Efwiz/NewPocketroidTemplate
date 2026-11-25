package com.thoughtworks.xstream.core.util;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/FastField.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public final class FastField {
    private final String declaringClass;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f264name;

    public FastField(String str, String str2) {
        this.f264name = str2;
        this.declaringClass = str;
    }

    public FastField(Class cls, String str) {
        this(cls == null ? null : cls.getName(), str);
    }

    public String getName() {
        return this.f264name;
    }

    public String getDeclaringClass() {
        return this.declaringClass;
    }

    public boolean equals(Object obj) {
        String str;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof FastField)) {
            return false;
        }
        FastField fastField = (FastField) obj;
        String str2 = this.declaringClass;
        if ((str2 != null || fastField.declaringClass == null) && (str2 == null || fastField.declaringClass != null)) {
            return this.f264name.equals(fastField.getName()) && ((str = this.declaringClass) == null || str.equals(fastField.getDeclaringClass()));
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = this.f264name.hashCode();
        String str = this.declaringClass;
        return iHashCode ^ (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return new StringBuffer().append(this.declaringClass == null ? "" : new StringBuffer().append(this.declaringClass).append(".").toString()).append(this.f264name).toString();
    }
}
