package org.jetbrains.kotlin.javac.wrappers.symbols;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SymbolBasedValueParameter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B%\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0012R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedValueParameter;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedElement;", "Ljavax/lang/model/element/VariableElement;", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "elementName", "", "isVararg", "", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/element/VariableElement;Ljava/lang/String;ZLorg/jetbrains/kotlin/javac/JavacWrapper;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "isDeprecatedInJavaDoc", "()Z", "isFromSource", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "findAnnotation", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotation;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolBasedValueParameter extends SymbolBasedElement<VariableElement> implements JavaValueParameter {
    private final String elementName;
    private final boolean isVararg;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public boolean isFromSource() {
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    /* JADX INFO: renamed from: isVararg, reason: from getter */
    public boolean getIsVararg() {
        return this.isVararg;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedValueParameter(VariableElement element, String elementName, boolean z, JavacWrapper javac) {
        super((Element) element, javac);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(elementName, "elementName");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.elementName = elementName;
        this.isVararg = z;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        List annotationMirrors = getElement().getAnnotationMirrors();
        Intrinsics.checkNotNullExpressionValue(annotationMirrors, "getAnnotationMirrors(...)");
        List<AnnotationMirror> list = annotationMirrors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (AnnotationMirror annotationMirror : list) {
            Intrinsics.checkNotNull(annotationMirror);
            arrayList.add(new SymbolBasedAnnotation(annotationMirror, getJavac()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public SymbolBasedAnnotation mo7251findAnnotation(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return UtilsKt.findAnnotation(getElement(), fqName, getJavac());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return getJavac().isDeprecated((Element) getElement());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public Name getName() {
        Name nameIdentifier = Name.identifier(this.elementName);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public JavaType getType() {
        return SymbolBasedType.INSTANCE.create(getElement().asType(), getJavac());
    }
}
