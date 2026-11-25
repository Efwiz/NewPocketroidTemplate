package com.artemis.injection;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.utils.reflect.Field;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public class ArtemisFieldResolver implements FieldResolver, UseInjectionCache {
    private InjectionCache cache;
    private Map<Class<?>, Class<?>> systems = new IdentityHashMap();
    private World world;

    @Override // com.artemis.injection.FieldResolver
    public void initialize(World world) {
        this.world = world;
        Iterator<BaseSystem> it = world.getSystems().iterator();
        while (it.hasNext()) {
            Class<?> cls = it.next().getClass();
            Class<?> superclass = cls;
            do {
                this.systems.put(superclass, cls);
                superclass = superclass.getSuperclass();
            } while (superclass != Object.class);
        }
    }

    /* JADX INFO: renamed from: com.artemis.injection.ArtemisFieldResolver$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$artemis$injection$ClassType;

        static {
            int[] iArr = new int[ClassType.values().length];
            $SwitchMap$com$artemis$injection$ClassType = iArr;
            try {
                iArr[ClassType.MAPPER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$artemis$injection$ClassType[ClassType.SYSTEM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$artemis$injection$ClassType[ClassType.WORLD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // com.artemis.injection.FieldResolver
    public Object resolve(Object obj, Class<?> cls, Field field) {
        int i = AnonymousClass1.$SwitchMap$com$artemis$injection$ClassType[this.cache.getFieldClassType(cls).ordinal()];
        if (i == 1) {
            return getComponentMapper(field);
        }
        if (i == 2) {
            return this.world.getSystem(this.systems.get(cls));
        }
        if (i != 3) {
            return null;
        }
        return this.world;
    }

    private ComponentMapper<?> getComponentMapper(Field field) {
        return this.world.getMapper(this.cache.getGenericType(field));
    }

    @Override // com.artemis.injection.UseInjectionCache
    public void setCache(InjectionCache injectionCache) {
        this.cache = injectionCache;
    }
}
