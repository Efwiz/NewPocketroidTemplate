package com.thoughtworks.xstream.security;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/security/InterfaceTypePermission.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class InterfaceTypePermission implements TypePermission {
    public static final TypePermission INTERFACES = new InterfaceTypePermission();
    static /* synthetic */ Class class$com$thoughtworks$xstream$security$InterfaceTypePermission;

    public int hashCode() {
        return 31;
    }

    @Override // com.thoughtworks.xstream.security.TypePermission
    public boolean allows(Class cls) {
        return cls != null && cls.isInterface();
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public boolean equals(Object obj) throws Throwable {
        if (obj != null) {
            Class<?> cls = obj.getClass();
            Class<?> clsClass$ = class$com$thoughtworks$xstream$security$InterfaceTypePermission;
            if (clsClass$ == null) {
                clsClass$ = class$("com.thoughtworks.xstream.security.InterfaceTypePermission");
                class$com$thoughtworks$xstream$security$InterfaceTypePermission = clsClass$;
            }
            if (cls == clsClass$) {
                return true;
            }
        }
        return false;
    }
}
