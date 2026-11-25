package com.artemis.link;

import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;

/* JADX INFO: loaded from: classes8.dex */
final class MutatorUtil {
    private MutatorUtil() {
    }

    static <T> T getGeneratedMutator(LinkSite linkSite) {
        Class<?>[] declaredClasses = linkSite.field.getDeclaringClass().getDeclaredClasses();
        String str = "Mutator_" + linkSite.field.getName();
        int length = declaredClasses.length;
        for (int i = 0; length > i; i++) {
            if (str.equals(declaredClasses[i].getSimpleName())) {
                try {
                    return (T) ClassReflection.newInstance(declaredClasses[i]);
                } catch (ReflectionException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}
