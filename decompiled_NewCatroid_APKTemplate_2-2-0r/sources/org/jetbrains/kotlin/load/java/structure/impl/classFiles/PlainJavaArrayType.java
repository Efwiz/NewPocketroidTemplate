package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaArrayType;
import org.jetbrains.kotlin.load.java.structure.JavaType;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/PlainJavaArrayType;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/JavaPlainType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaArrayType;", "componentType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "(Lorg/jetbrains/kotlin/load/java/structure/JavaType;)V", "getComponentType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlainJavaArrayType extends JavaPlainType implements JavaArrayType {
    private final JavaType componentType;

    public PlainJavaArrayType(JavaType componentType) {
        Intrinsics.checkNotNullParameter(componentType, "componentType");
        this.componentType = componentType;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaArrayType
    public JavaType getComponentType() {
        return this.componentType;
    }
}
