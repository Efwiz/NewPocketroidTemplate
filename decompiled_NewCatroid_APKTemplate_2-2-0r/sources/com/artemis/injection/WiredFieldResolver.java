package com.artemis.injection;

import com.artemis.MundaneWireException;
import com.artemis.World;
import com.artemis.utils.reflect.Field;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public class WiredFieldResolver implements UseInjectionCache, PojoFieldResolver {
    private InjectionCache cache;
    private Map<String, Object> pojos = new HashMap();
    private World world;

    @Override // com.artemis.injection.FieldResolver
    public void initialize(World world) {
        this.world = world;
    }

    @Override // com.artemis.injection.FieldResolver
    public Object resolve(Object obj, Class<?> cls, Field field) {
        ClassType fieldClassType = this.cache.getFieldClassType(cls);
        CachedField cachedField = this.cache.getCachedField(field);
        if ((fieldClassType != ClassType.CUSTOM && fieldClassType != ClassType.WORLD) || cachedField.wireType != WireType.WIRE) {
            return null;
        }
        String name2 = cachedField.f81name;
        if ("".equals(name2)) {
            name2 = field.getType().getName();
        }
        if (!this.pojos.containsKey(name2) && cachedField.failOnNull) {
            throw new MundaneWireException("Not registered: " + name2 + ContainerUtils.KEY_VALUE_DELIMITER + cls);
        }
        return this.pojos.get(name2);
    }

    @Override // com.artemis.injection.UseInjectionCache
    public void setCache(InjectionCache injectionCache) {
        this.cache = injectionCache;
    }

    @Override // com.artemis.injection.PojoFieldResolver
    public void setPojos(Map<String, Object> map) {
        this.pojos = map;
    }
}
