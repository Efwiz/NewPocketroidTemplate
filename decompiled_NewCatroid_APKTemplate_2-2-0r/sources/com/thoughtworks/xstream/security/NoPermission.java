package com.thoughtworks.xstream.security;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/security/NoPermission.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class NoPermission implements TypePermission {
    private final TypePermission permission;

    public NoPermission(TypePermission typePermission) {
        this.permission = typePermission;
    }

    @Override // com.thoughtworks.xstream.security.TypePermission
    public boolean allows(Class cls) {
        TypePermission typePermission = this.permission;
        if (typePermission == null || typePermission.allows(cls)) {
            throw new ForbiddenClassException(cls);
        }
        return false;
    }
}
