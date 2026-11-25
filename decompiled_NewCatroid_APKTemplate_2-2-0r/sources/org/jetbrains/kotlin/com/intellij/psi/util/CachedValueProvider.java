package org.jetbrains.kotlin.com.intellij.psi.util;

import java.util.Collection;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface CachedValueProvider<T> {
    Result<T> compute();

    public static class Result<T> {
        private static final Logger LOG = Logger.getInstance((Class<?>) Result.class);
        private final Object[] myDependencyItems;
        private final T myValue;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/CachedValueProvider$Result";
            } else if (i == 2) {
                objArr[0] = "dependency";
            } else if (i == 3 || i == 4) {
                objArr[0] = "dependencies";
            } else {
                objArr[0] = "dependencyItems";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/CachedValueProvider$Result";
            } else {
                objArr[1] = "getDependencyItems";
            }
            if (i != 1) {
                if (i == 2) {
                    objArr[2] = "createSingleDependency";
                } else if (i == 3 || i == 4) {
                    objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                } else {
                    objArr[2] = "<init>";
                }
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        public Result(T t, Object... objArr) {
            if (objArr == null) {
                $$$reportNull$$$0(0);
            }
            this.myValue = t;
            this.myDependencyItems = objArr;
            if (objArr.length == 0) {
                LOG.error("No dependencies provided which causes CachedValue to be never recalculated again. If this is intentional, please use ModificationTracker.NEVER_CHANGED");
            }
            for (int i = 0; i < objArr.length; i++) {
                if (objArr[i] == null) {
                    LOG.error("Null dependencies are not allowed, index=" + i);
                }
            }
            CachedValueProfiler.onResultCreated(this, null);
        }

        public T getValue() {
            return this.myValue;
        }

        public Object[] getDependencyItems() {
            Object[] objArr = this.myDependencyItems;
            if (objArr == null) {
                $$$reportNull$$$0(1);
            }
            return objArr;
        }

        public static <T> Result<T> createSingleDependency(T t, Object obj) {
            if (obj == null) {
                $$$reportNull$$$0(2);
            }
            return create(t, obj);
        }

        public static <T> Result<T> create(T t, Object... objArr) {
            if (objArr == null) {
                $$$reportNull$$$0(3);
            }
            return new Result<>(t, objArr);
        }

        public static <T> Result<T> create(T t, Collection<?> collection) {
            if (collection == null) {
                $$$reportNull$$$0(4);
            }
            return new Result<>(t, ArrayUtil.toObjectArray(collection));
        }
    }
}
