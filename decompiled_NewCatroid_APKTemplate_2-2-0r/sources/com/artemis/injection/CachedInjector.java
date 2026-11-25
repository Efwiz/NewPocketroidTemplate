package com.artemis.injection;

import com.artemis.MundaneWireException;
import com.artemis.World;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Field;
import com.artemis.utils.reflect.ReflectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public final class CachedInjector implements Injector {
    private InjectionCache cache = InjectionCache.sharedCache.get();
    private FieldHandler fieldHandler;
    private Map<String, Object> injectables;

    @Override // com.artemis.injection.Injector
    public Injector setFieldHandler(FieldHandler fieldHandler) {
        this.fieldHandler = fieldHandler;
        return this;
    }

    @Override // com.artemis.injection.Injector
    public <T> T getRegistered(String str) {
        return (T) this.injectables.get(str);
    }

    @Override // com.artemis.injection.Injector
    public <T> T getRegistered(Class<T> cls) {
        return (T) getRegistered(cls.getName());
    }

    @Override // com.artemis.injection.Injector
    public void initialize(World world, Map<String, Object> map) {
        this.injectables = map;
        if (this.fieldHandler == null) {
            this.fieldHandler = new FieldHandler(this.cache);
        }
        this.fieldHandler.initialize(world, map);
    }

    @Override // com.artemis.injection.Injector
    public boolean isInjectable(Object obj) {
        try {
            return this.cache.getCachedClass(obj.getClass()).wireType == WireType.WIRE;
        } catch (ReflectionException e) {
            throw new MundaneWireException("Error while wiring", e);
        }
    }

    @Override // com.artemis.injection.Injector
    public void inject(Object obj) throws RuntimeException {
        try {
            CachedClass cachedClass = this.cache.getCachedClass(obj.getClass());
            if (cachedClass.wireType == WireType.WIRE) {
                injectValidFields(obj, cachedClass);
            } else {
                injectAnnotatedFields(obj, cachedClass);
            }
        } catch (ReflectionException e) {
            throw new MundaneWireException("Error while wiring " + obj.getClass().getName(), e);
        } catch (RuntimeException e2) {
            throw new MundaneWireException("Error while wiring " + obj.getClass().getName(), e2);
        }
    }

    private void injectValidFields(Object obj, CachedClass cachedClass) throws ReflectionException {
        Field[] allInjectableFields = getAllInjectableFields(cachedClass);
        int length = allInjectableFields.length;
        for (int i = 0; length > i; i++) {
            injectField(obj, allInjectableFields[i], cachedClass.failOnNull);
        }
    }

    private Field[] getAllInjectableFields(CachedClass cachedClass) {
        Field[] fieldArr = cachedClass.allFields;
        if (fieldArr != null) {
            return fieldArr;
        }
        ArrayList arrayList = new ArrayList();
        Class<?> superclass = cachedClass.clazz;
        collectDeclaredInjectableFields(arrayList, superclass);
        while (cachedClass.injectInherited && (superclass = superclass.getSuperclass()) != Object.class) {
            collectDeclaredInjectableFields(arrayList, superclass);
        }
        Field[] fieldArr2 = (Field[]) arrayList.toArray(new Field[arrayList.size()]);
        cachedClass.allFields = fieldArr2;
        return fieldArr2;
    }

    private void collectDeclaredInjectableFields(List<Field> list, Class<?> cls) {
        try {
            if (this.cache.getCachedClass(cls).wireType != WireType.SKIPWIRE) {
                Field[] declaredFields = ClassReflection.getDeclaredFields(cls);
                for (int i = 0; i < declaredFields.length; i++) {
                    if (isWireable(declaredFields[i])) {
                        list.add(declaredFields[i]);
                    }
                }
            }
        } catch (ReflectionException e) {
            throw new MundaneWireException("Error while wiring", e);
        }
    }

    private boolean isWireable(Field field) {
        return this.cache.getCachedField(field).wireType != WireType.SKIPWIRE;
    }

    private void injectAnnotatedFields(Object obj, CachedClass cachedClass) throws ReflectionException {
        injectClass(obj, cachedClass);
    }

    private void injectClass(Object obj, CachedClass cachedClass) throws ReflectionException {
        Field[] allInjectableFields = getAllInjectableFields(cachedClass);
        int length = allInjectableFields.length;
        for (int i = 0; length > i; i++) {
            Field field = allInjectableFields[i];
            CachedField cachedField = this.cache.getCachedField(field);
            if (cachedField.wireType != WireType.IGNORED) {
                injectField(obj, field, cachedField.wireType == WireType.WIRE);
            }
        }
    }

    private void injectField(Object obj, Field field, boolean z) throws ReflectionException {
        try {
            Class<?> type = field.getType();
            Object objResolve = this.fieldHandler.resolve(obj, type, field);
            if (objResolve != null) {
                setField(obj, field, objResolve);
            }
            if (objResolve == null && z && this.cache.getFieldClassType(type) != ClassType.CUSTOM) {
                throw onFailedInjection(type.getSimpleName(), field);
            }
        } catch (RuntimeException unused) {
        }
    }

    private void setField(Object obj, Field field, Object obj2) throws ReflectionException {
        field.setAccessible(true);
        field.set(obj, obj2);
    }

    private MundaneWireException onFailedInjection(String str, Field field) {
        return new MundaneWireException("Failed to inject " + field.getType().getName() + " into " + field.getDeclaringClass().getName() + ": " + str + " not registered with world.");
    }
}
