package org.jetbrains.kotlin.javac.wrappers.symbols;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.java.JavaVisibilities;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0006\u001a\u0004\u0018\u00010\u0007*\u00020\bH\u0000\u001a\u001e\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000\u001a\f\u0010\u0010\u001a\u00020\u0011*\u00020\u0002H\u0000\u001a\u0012\u0010\u0010\u001a\u00020\u0011*\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0000\u001a\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015*\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u000fH\u0000\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0018\u0010\u0004\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003\"\u0018\u0010\u0005\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0003¨\u0006\u0018"}, d2 = {"isAbstract", "", "Ljavax/lang/model/element/Element;", "(Ljavax/lang/model/element/Element;)Z", "isFinal", "isStatic", "computeClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "Ljavax/lang/model/element/TypeElement;", "findAnnotation", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotation;", "Ljavax/lang/model/AnnotatedConstruct;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "getVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "", "Ljavax/lang/model/element/Modifier;", "valueParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", "Ljavax/lang/model/element/ExecutableElement;", "javac-wrapper"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    public static final boolean isAbstract(Element element) {
        Intrinsics.checkNotNullParameter(element, "<this>");
        return element.getModifiers().contains(Modifier.ABSTRACT);
    }

    public static final boolean isStatic(Element element) {
        Intrinsics.checkNotNullParameter(element, "<this>");
        return element.getModifiers().contains(Modifier.STATIC);
    }

    public static final boolean isFinal(Element element) {
        Intrinsics.checkNotNullParameter(element, "<this>");
        return element.getModifiers().contains(Modifier.FINAL);
    }

    public static final Visibility getVisibility(Element element) {
        Intrinsics.checkNotNullParameter(element, "<this>");
        Set modifiers = element.getModifiers();
        Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
        return getVisibility((Set<? extends Modifier>) modifiers);
    }

    public static final Visibility getVisibility(Set<? extends Modifier> set) {
        Intrinsics.checkNotNullParameter(set, "<this>");
        if (set.contains(Modifier.PUBLIC)) {
            return Visibilities.Public.INSTANCE;
        }
        if (set.contains(Modifier.PRIVATE)) {
            return Visibilities.Private.INSTANCE;
        }
        if (set.contains(Modifier.PROTECTED)) {
            if (set.contains(Modifier.STATIC)) {
                return JavaVisibilities.ProtectedStaticVisibility.INSTANCE;
            }
            return JavaVisibilities.ProtectedAndPackage.INSTANCE;
        }
        return JavaVisibilities.PackageVisibility.INSTANCE;
    }

    public static final ClassId computeClassId(TypeElement typeElement) {
        Intrinsics.checkNotNullParameter(typeElement, "<this>");
        TypeElement enclosingElement = typeElement.getEnclosingElement();
        if (enclosingElement.getKind() != ElementKind.PACKAGE) {
            Intrinsics.checkNotNull(enclosingElement, "null cannot be cast to non-null type javax.lang.model.element.TypeElement");
            ClassId classIdComputeClassId = computeClassId(enclosingElement);
            if (classIdComputeClassId == null) {
                return null;
            }
            return classIdComputeClassId.createNestedClassId(Name.identifier(typeElement.getSimpleName().toString()));
        }
        return ClassId.topLevel(new FqName(typeElement.getQualifiedName().toString()));
    }

    public static final List<JavaValueParameter> valueParameters(ExecutableElement executableElement, JavacWrapper javac) {
        Intrinsics.checkNotNullParameter(executableElement, "<this>");
        Intrinsics.checkNotNullParameter(javac, "javac");
        List parameters = executableElement.getParameters();
        boolean zIsVarArgs = executableElement.isVarArgs();
        Intrinsics.checkNotNull(parameters);
        int lastIndex = CollectionsKt.getLastIndex(parameters);
        List list = parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            VariableElement variableElement = (VariableElement) obj;
            String string = variableElement.getSimpleName().toString();
            Intrinsics.checkNotNull(variableElement);
            if (string.contentEquals(ParameterDescription.NAME_PREFIX + i)) {
                string = "p" + i;
            }
            arrayList.add(new SymbolBasedValueParameter(variableElement, string, i == lastIndex && zIsVarArgs, javac));
            i = i2;
        }
        return arrayList;
    }

    public static final SymbolBasedAnnotation findAnnotation(AnnotatedConstruct annotatedConstruct, FqName fqName, JavacWrapper javac) {
        Object next;
        TypeElement typeElementAsElement;
        Intrinsics.checkNotNullParameter(annotatedConstruct, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(javac, "javac");
        List annotationMirrors = annotatedConstruct.getAnnotationMirrors();
        Intrinsics.checkNotNullExpressionValue(annotationMirrors, "getAnnotationMirrors(...)");
        Iterator it = annotationMirrors.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            typeElementAsElement = ((AnnotationMirror) next).getAnnotationType().asElement();
            Intrinsics.checkNotNull(typeElementAsElement, "null cannot be cast to non-null type javax.lang.model.element.TypeElement");
        } while (!Intrinsics.areEqual(typeElementAsElement.getQualifiedName().toString(), fqName.asString()));
        AnnotationMirror annotationMirror = (AnnotationMirror) next;
        if (annotationMirror != null) {
            return new SymbolBasedAnnotation(annotationMirror, javac);
        }
        return null;
    }
}
