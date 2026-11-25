package com.thoughtworks.xstream.security;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/security/NullPermission.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class NullPermission implements TypePermission {
    public static final TypePermission NULL = new NullPermission();
    static /* synthetic */ Class class$com$thoughtworks$xstream$mapper$Mapper$Null;

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.security.TypePermission
    public boolean allows(Class cls) throws Throwable {
        if (cls != null) {
            Class clsClass$ = class$com$thoughtworks$xstream$mapper$Mapper$Null;
            if (clsClass$ == null) {
                clsClass$ = class$("com.thoughtworks.xstream.mapper.Mapper$Null");
                class$com$thoughtworks$xstream$mapper$Mapper$Null = clsClass$;
            }
            if (cls != clsClass$) {
                return false;
            }
        }
        return true;
    }
}
