package org.jetbrains.kotlin.descriptors.runtime.structure;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaLiteralAnnotationArgument;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ReflectJavaAnnotationArguments.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaLiteralAnnotationArgument;", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/ReflectJavaAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaLiteralAnnotationArgument;", "name", "Lorg/jetbrains/kotlin/name/Name;", "value", "", "(Lorg/jetbrains/kotlin/name/Name;Ljava/lang/Object;)V", "getValue", "()Ljava/lang/Object;", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectJavaLiteralAnnotationArgument extends ReflectJavaAnnotationArgument implements JavaLiteralAnnotationArgument {
    private final Object value;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaLiteralAnnotationArgument
    public Object getValue() {
        return this.value;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReflectJavaLiteralAnnotationArgument(Name name2, Object value) {
        super(name2, null);
        Intrinsics.checkNotNullParameter(value, "value");
        this.value = value;
    }
}
