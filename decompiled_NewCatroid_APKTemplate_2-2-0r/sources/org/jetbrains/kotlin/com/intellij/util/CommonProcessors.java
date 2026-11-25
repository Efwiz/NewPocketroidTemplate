package org.jetbrains.kotlin.com.intellij.util;

import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: loaded from: classes3.dex */
public final class CommonProcessors {
    private static final Processor<Object> FALSE = new Processor() { // from class: org.jetbrains.kotlin.com.intellij.util.CommonProcessors$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.Processor
        public final boolean process(Object obj) {
            return CommonProcessors.lambda$static$1(obj);
        }
    };
    private static final Processor<Object> TRUE = new Processor() { // from class: org.jetbrains.kotlin.com.intellij.util.CommonProcessors$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.com.intellij.util.Processor
        public final boolean process(Object obj) {
            return CommonProcessors.lambda$static$2(obj);
        }
    };

    public static class FindFirstProcessor<T> extends FindProcessor<T> {
        @Override // org.jetbrains.kotlin.com.intellij.util.CommonProcessors.FindProcessor
        protected boolean accept(T t) {
            return true;
        }
    }

    static /* synthetic */ boolean lambda$static$1(Object obj) {
        return false;
    }

    static /* synthetic */ boolean lambda$static$2(Object obj) {
        return true;
    }

    public static class CollectProcessor<T> implements Processor<T> {
        private final Collection<T> myCollection;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "a";
            } else if (i == 2 || i == 3) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/CommonProcessors$CollectProcessor";
            } else {
                objArr[0] = "collection";
            }
            if (i == 2) {
                objArr[1] = "toArray";
            } else if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/CommonProcessors$CollectProcessor";
            } else {
                objArr[1] = "getResults";
            }
            if (i == 1) {
                objArr[2] = "toArray";
            } else if (i != 2 && i != 3) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 2 && i != 3) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        protected boolean accept(T t) {
            return true;
        }

        public CollectProcessor(Collection<T> collection) {
            if (collection == null) {
                $$$reportNull$$$0(0);
            }
            this.myCollection = collection;
        }

        public CollectProcessor() {
            this.myCollection = new ArrayList();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.Processor
        public boolean process(T t) {
            if (!accept(t)) {
                return true;
            }
            this.myCollection.add(t);
            return true;
        }
    }

    public static abstract class FindProcessor<T> implements Processor<T> {
        private T myValue;

        protected abstract boolean accept(T t);

        public boolean isFound() {
            return this.myValue != null;
        }

        public T getFoundValue() {
            return this.myValue;
        }

        public T reset() {
            T t = this.myValue;
            this.myValue = null;
            return t;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.Processor
        public boolean process(T t) {
            if (!accept(t)) {
                return true;
            }
            this.myValue = t;
            return false;
        }
    }
}
