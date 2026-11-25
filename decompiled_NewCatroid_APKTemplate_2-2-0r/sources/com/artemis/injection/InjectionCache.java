package com.artemis.injection;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.SkipWire;
import com.artemis.annotations.Wire;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Field;
import com.artemis.utils.reflect.ReflectionException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public class InjectionCache {
    public static final SharedInjectionCache sharedCache = new SharedInjectionCache();
    private static final Wire DEFAULT_WIRE = new Wire() { // from class: com.artemis.injection.InjectionCache.1
        @Override // com.artemis.annotations.Wire
        public boolean failOnNull() {
            return true;
        }

        @Override // com.artemis.annotations.Wire
        public boolean injectInherited() {
            return true;
        }

        @Override // com.artemis.annotations.Wire
        public String name() {
            return null;
        }

        @Override // java.lang.annotation.Annotation
        public Class<? extends Annotation> annotationType() {
            return Wire.class;
        }
    };
    private final Map<Class<?>, CachedClass> classCache = new HashMap();
    private final Map<Class<?>, ClassType> fieldClassTypeCache = new HashMap();
    private final Map<Field, CachedField> namedWireCache = new HashMap();
    private final Map<Field, Class<?>> genericsCache = new HashMap();

    public CachedClass getCachedClass(Class<?> cls) throws ReflectionException {
        CachedClass cachedClass = this.classCache.get(cls);
        if (cachedClass == null) {
            cachedClass = new CachedClass(cls);
            cachedClass.wireType = getWireType(cls);
            if (cachedClass.wireType == WireType.IGNORED && cls != Object.class) {
                setWireAnnotation(cachedClass, DEFAULT_WIRE);
            } else if (cachedClass.wireType == WireType.WIRE) {
                setWireAnnotation(cachedClass, (Wire) ClassReflection.getAnnotation(cls, Wire.class));
            }
            this.classCache.put(cls, cachedClass);
        }
        return cachedClass;
    }

    private void setWireAnnotation(CachedClass cachedClass, Wire wire) {
        cachedClass.wireType = WireType.WIRE;
        cachedClass.wireAnnotation = wire;
        cachedClass.failOnNull = wire.failOnNull();
        cachedClass.injectInherited = wire.injectInherited();
    }

    private WireType getWireType(Class<?> cls) {
        if (ClassReflection.isAnnotationPresent(cls, Wire.class)) {
            return WireType.WIRE;
        }
        return ClassReflection.isAnnotationPresent(cls, SkipWire.class) ? WireType.SKIPWIRE : WireType.IGNORED;
    }

    public CachedField getCachedField(Field field) {
        CachedField cachedField = this.namedWireCache.get(field);
        if (cachedField == null) {
            if (field.isAnnotationPresent(Wire.class)) {
                Wire wire = (Wire) field.getAnnotation(Wire.class);
                cachedField = new CachedField(field, WireType.WIRE, wire.name(), wire.failOnNull());
            } else if (field.isAnnotationPresent(SkipWire.class)) {
                cachedField = new CachedField(field, WireType.SKIPWIRE, null, false);
            } else {
                cachedField = new CachedField(field, WireType.IGNORED, null, false);
            }
            this.namedWireCache.put(field, cachedField);
        }
        return cachedField;
    }

    public ClassType getFieldClassType(Class<?> cls) {
        ClassType classType = this.fieldClassTypeCache.get(cls);
        if (classType == null) {
            if (ClassReflection.isAssignableFrom(ComponentMapper.class, cls)) {
                classType = ClassType.MAPPER;
            } else if (ClassReflection.isAssignableFrom(BaseSystem.class, cls)) {
                classType = ClassType.SYSTEM;
            } else if (ClassReflection.isAssignableFrom(World.class, cls)) {
                classType = ClassType.WORLD;
            } else {
                classType = ClassType.CUSTOM;
            }
            this.fieldClassTypeCache.put(cls, classType);
        }
        return classType;
    }

    public Class<?> getGenericType(Field field) {
        Class<?> cls = this.genericsCache.get(field);
        if (cls != null) {
            return cls;
        }
        Class<?> elementType = field.getElementType(0);
        this.genericsCache.put(field, elementType);
        return elementType;
    }
}
