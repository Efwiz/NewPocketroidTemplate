package com.thoughtworks.xstream.mapper;

import java.lang.reflect.Proxy;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/DynamicProxyMapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class DynamicProxyMapper extends MapperWrapper {
    static /* synthetic */ Class class$com$thoughtworks$xstream$mapper$DynamicProxyMapper$DynamicProxy;
    private String alias;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/DynamicProxyMapper$DynamicProxy.class
      classes11.dex
     */
    public static class DynamicProxy {
    }

    public DynamicProxyMapper(Mapper mapper) {
        this(mapper, "dynamic-proxy");
    }

    public DynamicProxyMapper(Mapper mapper, String str) {
        super(mapper);
        this.alias = str;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String str) {
        this.alias = str;
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public String serializedClass(Class cls) {
        if (Proxy.isProxyClass(cls)) {
            return this.alias;
        }
        return super.serializedClass(cls);
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public Class realClass(String str) throws Throwable {
        if (str.equals(this.alias)) {
            Class cls = class$com$thoughtworks$xstream$mapper$DynamicProxyMapper$DynamicProxy;
            if (cls != null) {
                return cls;
            }
            Class clsClass$ = class$("com.thoughtworks.xstream.mapper.DynamicProxyMapper$DynamicProxy");
            class$com$thoughtworks$xstream$mapper$DynamicProxyMapper$DynamicProxy = clsClass$;
            return clsClass$;
        }
        return super.realClass(str);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }
}
