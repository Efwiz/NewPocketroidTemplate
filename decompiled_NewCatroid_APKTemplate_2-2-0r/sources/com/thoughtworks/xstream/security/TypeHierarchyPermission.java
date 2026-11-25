package com.thoughtworks.xstream.security;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/security/TypeHierarchyPermission.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class TypeHierarchyPermission implements TypePermission {
    private Class type;

    public TypeHierarchyPermission(Class cls) {
        this.type = cls;
    }

    @Override // com.thoughtworks.xstream.security.TypePermission
    public boolean allows(Class cls) {
        if (cls == null) {
            return false;
        }
        return this.type.isAssignableFrom(cls);
    }
}
