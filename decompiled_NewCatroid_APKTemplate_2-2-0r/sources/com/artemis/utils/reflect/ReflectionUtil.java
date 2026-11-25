package com.artemis.utils.reflect;

import com.artemis.BaseEntitySystem;
import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.Manager;
import com.artemis.utils.IntBag;
import java.util.Arrays;

/* JADX INFO: loaded from: classes8.dex */
public final class ReflectionUtil {
    private static final Class<?>[] PARAM_ENTITY = {Entity.class};
    private static final Class<?>[] PARAM_ID = {Integer.TYPE};
    private static final Class<?>[] PARAM_IDS = {IntBag.class};

    private ReflectionUtil() {
    }

    public static boolean implementsObserver(BaseSystem baseSystem, String str) {
        try {
            Class declaringClass = ClassReflection.getMethod(baseSystem.getClass(), str, PARAM_ENTITY).getDeclaringClass();
            return (Manager.class.equals(declaringClass) || EntitySystem.class.equals(declaringClass)) ? false : true;
        } catch (ReflectionException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean implementsAnyObserver(BaseEntitySystem baseEntitySystem) {
        if (ClassReflection.isInstance(Manager.class, baseEntitySystem) || ClassReflection.isInstance(EntitySystem.class, baseEntitySystem)) {
            return true;
        }
        Class<?> superclass = baseEntitySystem.getClass();
        while (true) {
            if (superclass == BaseEntitySystem.class) {
                return false;
            }
            for (Method method : ClassReflection.getDeclaredMethods(superclass)) {
                if (isObserver(method)) {
                    return true;
                }
            }
            superclass = superclass.getSuperclass();
        }
    }

    private static boolean isObserver(Method method) {
        String name2 = method.getName();
        if (!"inserted".equals(name2) && !"removed".equals(name2)) {
            return false;
        }
        Class[] parameterTypes = method.getParameterTypes();
        return Arrays.equals(PARAM_ID, parameterTypes) || Arrays.equals(PARAM_IDS, parameterTypes);
    }

    public static boolean isGenericType(Field field, Class<?> cls, Class cls2) {
        return cls == field.getType() && cls2 == field.getElementType(0);
    }
}
