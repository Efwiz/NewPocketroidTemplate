package com.thoughtworks.xstream.mapper;

import net.sf.cglib.proxy.Enhancer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/CGLIBMapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class CGLIBMapper extends MapperWrapper {
    private static String DEFAULT_NAMING_MARKER = "$$EnhancerByCGLIB$$";
    static /* synthetic */ Class class$com$thoughtworks$xstream$mapper$CGLIBMapper$Marker;
    private final String alias;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/CGLIBMapper$Marker.class
      classes11.dex
     */
    public interface Marker {
    }

    public CGLIBMapper(Mapper mapper) {
        this(mapper, "CGLIB-enhanced-proxy");
    }

    public CGLIBMapper(Mapper mapper, String str) {
        super(mapper);
        this.alias = str;
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public String serializedClass(Class cls) {
        String strSerializedClass = super.serializedClass(cls);
        if (cls == null) {
            return strSerializedClass;
        }
        String name2 = cls.getName();
        return (name2.equals(strSerializedClass) && name2.indexOf(DEFAULT_NAMING_MARKER) > 0 && Enhancer.isEnhanced(cls)) ? this.alias : strSerializedClass;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public Class realClass(String str) throws Throwable {
        if (!str.equals(this.alias)) {
            return super.realClass(str);
        }
        Class cls = class$com$thoughtworks$xstream$mapper$CGLIBMapper$Marker;
        if (cls != null) {
            return cls;
        }
        Class clsClass$ = class$("com.thoughtworks.xstream.mapper.CGLIBMapper$Marker");
        class$com$thoughtworks$xstream$mapper$CGLIBMapper$Marker = clsClass$;
        return clsClass$;
    }
}
