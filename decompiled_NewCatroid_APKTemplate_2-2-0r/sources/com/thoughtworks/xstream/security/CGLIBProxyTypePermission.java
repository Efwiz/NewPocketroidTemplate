package com.thoughtworks.xstream.security;

import net.sf.cglib.proxy.Proxy;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/security/CGLIBProxyTypePermission.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class CGLIBProxyTypePermission implements TypePermission {
    public static final TypePermission PROXIES = new CGLIBProxyTypePermission();
    static /* synthetic */ Class class$com$thoughtworks$xstream$security$CGLIBProxyTypePermission;
    static /* synthetic */ Class class$java$lang$Object;
    static /* synthetic */ Class class$net$sf$cglib$proxy$Proxy;

    public int hashCode() {
        return 19;
    }

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
            Class clsClass$ = class$java$lang$Object;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_LANG_OBJECT);
                class$java$lang$Object = clsClass$;
            }
            if (cls != clsClass$ && !cls.isInterface()) {
                if (!Proxy.isProxyClass(cls)) {
                    String name2 = cls.getName();
                    StringBuffer stringBuffer = new StringBuffer();
                    Class clsClass$2 = class$net$sf$cglib$proxy$Proxy;
                    if (clsClass$2 == null) {
                        clsClass$2 = class$("net.sf.cglib.proxy.Proxy");
                        class$net$sf$cglib$proxy$Proxy = clsClass$2;
                    }
                    if (name2.startsWith(stringBuffer.append(clsClass$2.getPackage().getName()).append(".").toString())) {
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object obj) throws Throwable {
        if (obj != null) {
            Class<?> cls = obj.getClass();
            Class<?> clsClass$ = class$com$thoughtworks$xstream$security$CGLIBProxyTypePermission;
            if (clsClass$ == null) {
                clsClass$ = class$("com.thoughtworks.xstream.security.CGLIBProxyTypePermission");
                class$com$thoughtworks$xstream$security$CGLIBProxyTypePermission = clsClass$;
            }
            if (cls == clsClass$) {
                return true;
            }
        }
        return false;
    }
}
