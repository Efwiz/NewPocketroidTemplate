package org.jetbrains.kotlin.javac.wrappers.symbols;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaConstructor;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;

/* JADX INFO: compiled from: SymbolBasedConstructor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000e¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedConstructor;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedMember;", "Ljavax/lang/model/element/ExecutableElement;", "Lorg/jetbrains/kotlin/load/java/structure/JavaConstructor;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/element/ExecutableElement;Lorg/jetbrains/kotlin/load/java/structure/JavaClass;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "typeParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "getTypeParameters", "()Ljava/util/List;", "valueParameters", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", "getValueParameters", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolBasedConstructor extends SymbolBasedMember<ExecutableElement> implements JavaConstructor {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedConstructor(ExecutableElement element, JavaClass containingClass, JavacWrapper javac) {
        super((Element) element, containingClass, javac);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(javac, "javac");
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameters */
    public List<JavaTypeParameter> mo7258getTypeParameters() {
        List typeParameters = ((ExecutableElement) getElement()).getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterElement> list = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (TypeParameterElement typeParameterElement : list) {
            Intrinsics.checkNotNull(typeParameterElement);
            arrayList.add(new SymbolBasedTypeParameter(typeParameterElement, getJavac()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaConstructor
    public List<JavaValueParameter> getValueParameters() {
        return UtilsKt.valueParameters((ExecutableElement) getElement(), getJavac());
    }
}
