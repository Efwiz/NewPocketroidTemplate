package org.jetbrains.kotlin.fir.backend;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: CodeFragmentUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/CodeFragmentConversionData;", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "methodName", "Lorg/jetbrains/kotlin/name/Name;", "injectedValues", "", "Lorg/jetbrains/kotlin/fir/backend/InjectedValue;", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/name/Name;Ljava/util/List;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getInjectedValues", "()Ljava/util/List;", "getMethodName", "()Lorg/jetbrains/kotlin/name/Name;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CodeFragmentConversionData {
    private final ClassId classId;
    private final List<InjectedValue> injectedValues;
    private final Name methodName;

    public CodeFragmentConversionData(ClassId classId, Name methodName, List<InjectedValue> injectedValues) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(methodName, "methodName");
        Intrinsics.checkNotNullParameter(injectedValues, "injectedValues");
        this.classId = classId;
        this.methodName = methodName;
        this.injectedValues = injectedValues;
    }

    public final ClassId getClassId() {
        return this.classId;
    }

    public final Name getMethodName() {
        return this.methodName;
    }

    public final List<InjectedValue> getInjectedValues() {
        return this.injectedValues;
    }
}
