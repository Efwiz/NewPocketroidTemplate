package org.jetbrains.kotlin.javac.wrappers.symbols;

import javax.lang.model.element.AnnotationMirror;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationAsAnnotationArgument;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: symbolBasedAnnotationArguments.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotationAsAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationAsAnnotationArgument;", "mirror", "Ljavax/lang/model/element/AnnotationMirror;", "name", "Lorg/jetbrains/kotlin/name/Name;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/element/AnnotationMirror;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "getAnnotation", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotation;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolBasedAnnotationAsAnnotationArgument extends SymbolBasedAnnotationArgument implements JavaAnnotationAsAnnotationArgument {
    private final AnnotationMirror mirror;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedAnnotationAsAnnotationArgument(AnnotationMirror mirror, Name name2, JavacWrapper javac) {
        super(name2, javac, null);
        Intrinsics.checkNotNullParameter(mirror, "mirror");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.mirror = mirror;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationAsAnnotationArgument
    public SymbolBasedAnnotation getAnnotation() {
        return new SymbolBasedAnnotation(this.mirror, getJavac());
    }
}
