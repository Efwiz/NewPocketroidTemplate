package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.core.Caching;
import com.thoughtworks.xstream.security.ForbiddenClassException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/CachingMapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class CachingMapper extends MapperWrapper implements Caching {
    private transient Map realClassCache;

    public CachingMapper(Mapper mapper) {
        super(mapper);
        readResolve();
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public Class realClass(String str) {
        Object obj = this.realClassCache.get(str);
        if (obj != null) {
            if (obj instanceof Class) {
                return (Class) obj;
            }
            throw ((XStreamException) obj);
        }
        try {
            Class clsRealClass = super.realClass(str);
            this.realClassCache.put(str, clsRealClass);
            return clsRealClass;
        } catch (CannotResolveClassException e) {
            this.realClassCache.put(str, e);
            throw e;
        } catch (ForbiddenClassException e2) {
            this.realClassCache.put(str, e2);
            throw e2;
        }
    }

    @Override // com.thoughtworks.xstream.core.Caching
    public void flushCache() {
        this.realClassCache.clear();
    }

    private Object readResolve() {
        this.realClassCache = Collections.synchronizedMap(new HashMap(128));
        return this;
    }
}
