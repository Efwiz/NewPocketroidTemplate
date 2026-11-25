package org.jetbrains.kotlin.javac.wrappers.symbols;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SymbolBasedMethod.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0016\u0010\n\u001a\u0004\u0018\u00010\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0016¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedMethod;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedMember;", "Ljavax/lang/model/element/ExecutableElement;", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/element/ExecutableElement;Lorg/jetbrains/kotlin/load/java/structure/JavaClass;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "annotationParameterDefaultValue", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "getAnnotationParameterDefaultValue", "()Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "returnType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getReturnType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "typeParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "getTypeParameters", "()Ljava/util/List;", "valueParameters", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", "getValueParameters", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolBasedMethod extends SymbolBasedMember<ExecutableElement> implements JavaMethod {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedMethod(ExecutableElement element, JavaClass containingClass, JavacWrapper javac) {
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

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public List<JavaValueParameter> getValueParameters() {
        return UtilsKt.valueParameters((ExecutableElement) getElement(), getJavac());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public JavaType getReturnType() {
        return SymbolBasedType.INSTANCE.create(((ExecutableElement) getElement()).getReturnType(), getJavac());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public JavaAnnotationArgument getAnnotationParameterDefaultValue() {
        AnnotationValue defaultValue = ((ExecutableElement) getElement()).getDefaultValue();
        if (defaultValue == null) {
            return null;
        }
        SymbolBasedAnnotationArgument.Companion companion = SymbolBasedAnnotationArgument.INSTANCE;
        Name nameIdentifier = Name.identifier("value");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return companion.create(defaultValue, nameIdentifier, getJavac());
    }
}
