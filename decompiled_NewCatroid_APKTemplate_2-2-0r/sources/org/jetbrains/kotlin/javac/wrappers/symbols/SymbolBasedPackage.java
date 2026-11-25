package org.jetbrains.kotlin.javac.wrappers.symbols;

import javax.lang.model.element.PackageElement;
import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.load.java.structure.MapBasedJavaAnnotationOwner;

/* JADX INFO: compiled from: SymbolBasedPackage.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u00012\u00020\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedPackage;", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "Lorg/jetbrains/kotlin/load/java/structure/MapBasedJavaAnnotationOwner;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Ljavax/lang/model/element/PackageElement;", "getElement", "()Ljavax/lang/model/element/PackageElement;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SymbolBasedPackage extends JavaPackage, MapBasedJavaAnnotationOwner {
    PackageElement getElement();
}
