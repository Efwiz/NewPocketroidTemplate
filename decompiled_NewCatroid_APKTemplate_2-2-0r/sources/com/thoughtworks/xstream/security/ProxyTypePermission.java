package com.thoughtworks.xstream.security;

import java.lang.reflect.Proxy;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/security/ProxyTypePermission.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ProxyTypePermission implements TypePermission {
    public static final TypePermission PROXIES = new ProxyTypePermission();
    static /* synthetic */ Class class$com$thoughtworks$xstream$mapper$DynamicProxyMapper$DynamicProxy;
    static /* synthetic */ Class class$com$thoughtworks$xstream$security$ProxyTypePermission;

    public int hashCode() {
        return 17;
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
            if (!Proxy.isProxyClass(cls)) {
                Class clsClass$ = class$com$thoughtworks$xstream$mapper$DynamicProxyMapper$DynamicProxy;
                if (clsClass$ == null) {
                    clsClass$ = class$("com.thoughtworks.xstream.mapper.DynamicProxyMapper$DynamicProxy");
                    class$com$thoughtworks$xstream$mapper$DynamicProxyMapper$DynamicProxy = clsClass$;
                }
                if (cls == clsClass$) {
                }
            }
            return true;
        }
        return false;
    }

    public boolean equals(Object obj) throws Throwable {
        if (obj != null) {
            Class<?> cls = obj.getClass();
            Class<?> clsClass$ = class$com$thoughtworks$xstream$security$ProxyTypePermission;
            if (clsClass$ == null) {
                clsClass$ = class$("com.thoughtworks.xstream.security.ProxyTypePermission");
                class$com$thoughtworks$xstream$security$ProxyTypePermission = clsClass$;
            }
            if (cls == clsClass$) {
                return true;
            }
        }
        return false;
    }
}
