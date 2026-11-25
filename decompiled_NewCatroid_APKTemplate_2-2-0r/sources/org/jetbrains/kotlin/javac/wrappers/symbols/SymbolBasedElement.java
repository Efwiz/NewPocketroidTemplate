package org.jetbrains.kotlin.javac.wrappers.symbols;

import androidx.exifinterface.media.ExifInterface;
import javax.lang.model.element.Element;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.structure.JavaElement;

/* JADX INFO: compiled from: SymbolBasedElement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedElement;", ExifInterface.GPS_DIRECTION_TRUE, "Ljavax/lang/model/element/Element;", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/element/Element;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "getElement", "()Ljavax/lang/model/element/Element;", "Ljavax/lang/model/element/Element;", "getJavac", "()Lorg/jetbrains/kotlin/javac/JavacWrapper;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SymbolBasedElement<T extends Element> implements JavaElement {
    private final T element;
    private final JavacWrapper javac;

    public SymbolBasedElement(T element, JavacWrapper javac) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.element = element;
        this.javac = javac;
    }

    public final T getElement() {
        return this.element;
    }

    public final JavacWrapper getJavac() {
        return this.javac;
    }

    public boolean equals(Object other) {
        SymbolBasedElement symbolBasedElement = other instanceof SymbolBasedElement ? (SymbolBasedElement) other : null;
        return Intrinsics.areEqual(symbolBasedElement != null ? symbolBasedElement.element : null, this.element);
    }

    public int hashCode() {
        return this.element.hashCode();
    }

    public String toString() {
        return this.element.getSimpleName().toString();
    }
}
