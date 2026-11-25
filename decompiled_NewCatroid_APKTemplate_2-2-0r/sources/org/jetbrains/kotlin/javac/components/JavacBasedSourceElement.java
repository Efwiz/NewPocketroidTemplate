package org.jetbrains.kotlin.javac.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.load.java.sources.JavaSourceElement;
import org.jetbrains.kotlin.load.java.structure.JavaElement;

/* JADX INFO: compiled from: JavacBasedSourceElement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/javac/components/JavacBasedSourceElement;", "Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElement;", "javaElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "(Lorg/jetbrains/kotlin/load/java/structure/JavaElement;)V", "getJavaElement", "()Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "getContainingFile", "Lorg/jetbrains/kotlin/descriptors/SourceFile;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavacBasedSourceElement implements JavaSourceElement {
    private final JavaElement javaElement;

    public JavacBasedSourceElement(JavaElement javaElement) {
        Intrinsics.checkNotNullParameter(javaElement, "javaElement");
        this.javaElement = javaElement;
    }

    @Override // org.jetbrains.kotlin.load.java.sources.JavaSourceElement
    public JavaElement getJavaElement() {
        return this.javaElement;
    }

    @Override // org.jetbrains.kotlin.descriptors.SourceElement
    public SourceFile getContainingFile() {
        SourceFile NO_SOURCE_FILE = SourceFile.NO_SOURCE_FILE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE_FILE, "NO_SOURCE_FILE");
        return NO_SOURCE_FILE;
    }
}
