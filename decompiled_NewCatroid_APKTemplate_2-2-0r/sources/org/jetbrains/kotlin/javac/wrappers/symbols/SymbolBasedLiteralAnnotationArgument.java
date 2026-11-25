package org.jetbrains.kotlin.javac.wrappers.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaLiteralAnnotationArgument;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: symbolBasedAnnotationArguments.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedLiteralAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaLiteralAnnotationArgument;", "value", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "getValue", "()Ljava/lang/Object;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolBasedLiteralAnnotationArgument extends SymbolBasedAnnotationArgument implements JavaLiteralAnnotationArgument {
    private final Object value;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaLiteralAnnotationArgument
    public Object getValue() {
        return this.value;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedLiteralAnnotationArgument(Object value, Name name2, JavacWrapper javac) {
        super(name2, javac, null);
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.value = value;
    }
}
