package org.jetbrains.kotlin.com.intellij.patterns;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.util.ProcessingContext;

/* JADX INFO: loaded from: classes3.dex */
public abstract class InitialPatternCondition<T> {
    private final Class<T> myAcceptedClass;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/patterns/InitialPatternCondition";
        } else if (i != 2) {
            objArr[0] = "aAcceptedClass";
        } else {
            objArr[0] = "builder";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/patterns/InitialPatternCondition";
        } else {
            objArr[1] = "getAcceptedClass";
        }
        if (i != 1) {
            if (i != 2) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "append";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    protected InitialPatternCondition(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(0);
        }
        this.myAcceptedClass = cls;
    }

    public Class<T> getAcceptedClass() {
        Class<T> cls = this.myAcceptedClass;
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
        return cls;
    }

    public boolean accepts(Object obj, ProcessingContext processingContext) {
        return this.myAcceptedClass.isInstance(obj);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        append(sb, "");
        return sb.toString();
    }

    public void append(StringBuilder sb, String str) {
        if (sb == null) {
            $$$reportNull$$$0(2);
        }
        sb.append("instanceOf(").append(this.myAcceptedClass.getSimpleName()).append(Constant.AFTER_QUTO);
    }
}
