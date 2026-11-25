package org.jetbrains.kotlin.com.intellij.util;

import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;
import org.jetbrains.kotlin.com.intellij.util.containers.JBTreeTraverser;

/* JADX INFO: loaded from: classes3.dex */
public final class JBIterableClassTraverser {
    private static final JBTreeTraverser<Class<?>> CLASS_TRAVERSER = JBTreeTraverser.from(new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.JBIterableClassTraverser$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            Class cls = (Class) obj;
            return JBIterable.of(cls.getSuperclass()).append((Object[]) cls.getInterfaces());
        }
    });

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/util/JBIterableClassTraverser", "classTraverser"));
    }

    public static JBTreeTraverser<Class<?>> classTraverser(Class<?> cls) {
        JBTreeTraverser<Class<?>> jBTreeTraverser = (JBTreeTraverser) CLASS_TRAVERSER.unique().withRoot(cls);
        if (jBTreeTraverser == null) {
            $$$reportNull$$$0(0);
        }
        return jBTreeTraverser;
    }
}
