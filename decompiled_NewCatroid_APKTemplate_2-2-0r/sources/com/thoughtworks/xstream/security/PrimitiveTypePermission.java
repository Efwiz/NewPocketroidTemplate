package com.thoughtworks.xstream.security;

import com.thoughtworks.xstream.core.util.Primitives;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/security/PrimitiveTypePermission.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class PrimitiveTypePermission implements TypePermission {
    public static final TypePermission PRIMITIVES = new PrimitiveTypePermission();
    static /* synthetic */ Class class$com$thoughtworks$xstream$security$PrimitiveTypePermission;
    static /* synthetic */ Class class$java$lang$Void;

    public int hashCode() {
        return 7;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x001a  */
    /* JADX WARN: Code duplicated, block: B:14:0x0022 A[ORIG_RETURN, RETURN] */
    @Override // com.thoughtworks.xstream.security.TypePermission
    public boolean allows(Class cls) throws Throwable {
        if (cls != null && cls != Void.TYPE) {
            Class clsClass$ = class$java$lang$Void;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_LANG_VOID);
                class$java$lang$Void = clsClass$;
            }
            if (cls == clsClass$ || !cls.isPrimitive()) {
                if (Primitives.isBoxed(cls)) {
                    return false;
                }
            }
        } else if (Primitives.isBoxed(cls)) {
            return false;
        }
        return true;
    }

    public boolean equals(Object obj) throws Throwable {
        if (obj != null) {
            Class<?> cls = obj.getClass();
            Class<?> clsClass$ = class$com$thoughtworks$xstream$security$PrimitiveTypePermission;
            if (clsClass$ == null) {
                clsClass$ = class$("com.thoughtworks.xstream.security.PrimitiveTypePermission");
                class$com$thoughtworks$xstream$security$PrimitiveTypePermission = clsClass$;
            }
            if (cls == clsClass$) {
                return true;
            }
        }
        return false;
    }
}
