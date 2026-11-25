package org.jetbrains.kotlin.javac.wrappers.symbols;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaArrayType;
import org.jetbrains.kotlin.load.java.structure.JavaType;

/* JADX INFO: compiled from: symbolBasedTypes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedArrayType;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedType;", "Ljavax/lang/model/type/ArrayType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaArrayType;", "typeMirror", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/type/ArrayType;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "componentType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getComponentType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolBasedArrayType extends SymbolBasedType<ArrayType> implements JavaArrayType {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedArrayType(ArrayType typeMirror, JavacWrapper javac) {
        super((TypeMirror) typeMirror, javac, null);
        Intrinsics.checkNotNullParameter(typeMirror, "typeMirror");
        Intrinsics.checkNotNullParameter(javac, "javac");
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaArrayType
    public JavaType getComponentType() {
        return SymbolBasedType.INSTANCE.create(getTypeMirror().getComponentType(), getJavac());
    }
}
