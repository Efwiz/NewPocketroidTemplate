package org.jetbrains.kotlin.javac.wrappers.symbols;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SymbolBasedTypeParameter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedTypeParameter;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedClassifier;", "Ljavax/lang/model/element/TypeParameterElement;", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/element/TypeParameterElement;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "upperBounds", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "getUpperBounds", "()Ljava/util/Collection;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolBasedTypeParameter extends SymbolBasedClassifier<TypeParameterElement> implements JavaTypeParameter {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedTypeParameter(TypeParameterElement element, JavacWrapper javac) {
        super((Element) element, javac);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(javac, "javac");
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        Name nameIdentifier = Name.identifier(((TypeParameterElement) getElement()).getSimpleName().toString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameter
    public Collection<JavaClassifierType> getUpperBounds() {
        List bounds = ((TypeParameterElement) getElement()).getBounds();
        Intrinsics.checkNotNullExpressionValue(bounds, "getBounds(...)");
        List list = bounds;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new SymbolBasedClassifierType((TypeMirror) it.next(), getJavac()));
        }
        return arrayList;
    }
}
