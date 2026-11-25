package com.thoughtworks.xstream.mapper;

import java.util.HashMap;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/AbstractAttributeAliasingMapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractAttributeAliasingMapper extends MapperWrapper {
    protected final Map aliasToName;
    protected transient Map nameToAlias;

    public AbstractAttributeAliasingMapper(Mapper mapper) {
        super(mapper);
        this.aliasToName = new HashMap();
        this.nameToAlias = new HashMap();
    }

    public void addAliasFor(String str, String str2) {
        this.aliasToName.put(str2, str);
        this.nameToAlias.put(str, str2);
    }

    Object readResolve() {
        this.nameToAlias = new HashMap();
        for (Object obj : this.aliasToName.keySet()) {
            this.nameToAlias.put(this.aliasToName.get(obj), obj);
        }
        return this;
    }
}
