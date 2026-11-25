package com.artemis.injection;

import com.artemis.InjectionException;
import com.artemis.World;
import com.artemis.utils.Bag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Field;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public class FieldHandler {
    private InjectionCache cache;
    protected Bag<FieldResolver> fieldResolvers;

    public FieldHandler(InjectionCache injectionCache, Bag<FieldResolver> bag) {
        this.cache = injectionCache;
        this.fieldResolvers = bag;
    }

    public FieldHandler(InjectionCache injectionCache) {
        this.fieldResolvers = new Bag<>(FieldResolver.class);
        this.cache = injectionCache;
        addFieldResolver(new WiredFieldResolver());
        addFieldResolver(new ArtemisFieldResolver());
        addFieldResolver(new AspectFieldResolver());
    }

    public void initialize(World world, Map<String, Object> map) {
        int size = this.fieldResolvers.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            FieldResolver fieldResolver = this.fieldResolvers.get(i);
            if (ClassReflection.isInstance(UseInjectionCache.class, fieldResolver)) {
                ((UseInjectionCache) fieldResolver).setCache(this.cache);
            }
            if (ClassReflection.isInstance(PojoFieldResolver.class, fieldResolver)) {
                ((PojoFieldResolver) fieldResolver).setPojos(map);
                z = true;
            }
            fieldResolver.initialize(world);
        }
        if (map != null && !map.isEmpty() && !z) {
            throw new InjectionException("FieldHandler lacks resolver capable of dealing with your custom injectables. Register a WiredFieldResolver or PojoFieldResolver with your FieldHandler.");
        }
    }

    public Object resolve(Object obj, Class<?> cls, Field field) {
        int size = this.fieldResolvers.size();
        for (int i = 0; i < size; i++) {
            Object objResolve = this.fieldResolvers.get(i).resolve(obj, cls, field);
            if (objResolve != null) {
                return objResolve;
            }
        }
        return null;
    }

    public final void addFieldResolver(FieldResolver fieldResolver) {
        this.fieldResolvers.add(fieldResolver);
    }

    public Bag<FieldResolver> getFieldResolvers() {
        return this.fieldResolvers;
    }
}
