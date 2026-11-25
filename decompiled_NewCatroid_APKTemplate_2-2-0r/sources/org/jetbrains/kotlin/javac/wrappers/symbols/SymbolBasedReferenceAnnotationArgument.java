package org.jetbrains.kotlin.javac.wrappers.symbols;

import com.sun.tools.javac.code.Symbol;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: symbolBasedAnnotationArguments.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0013\u001a\u0004\u0018\u00010\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedReferenceAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaEnumValueAnnotationArgument;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Ljavax/lang/model/element/VariableElement;", "name", "Lorg/jetbrains/kotlin/name/Name;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/element/VariableElement;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "getElement", "()Ljavax/lang/model/element/VariableElement;", "entryName", "getEntryName", "()Lorg/jetbrains/kotlin/name/Name;", "enumClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "getEnumClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "javaField", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "getJavaField", "()Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "javaField$delegate", "Lkotlin/Lazy;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolBasedReferenceAnnotationArgument extends SymbolBasedAnnotationArgument implements JavaEnumValueAnnotationArgument {
    private final VariableElement element;

    /* JADX INFO: renamed from: javaField$delegate, reason: from kotlin metadata */
    private final Lazy javaField;

    public final VariableElement getElement() {
        return this.element;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedReferenceAnnotationArgument(VariableElement element, Name name2, final JavacWrapper javac) {
        super(name2, javac, null);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.element = element;
        this.javaField = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<SymbolBasedField>() { // from class: org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedReferenceAnnotationArgument$javaField$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SymbolBasedField invoke() {
                TypeElement enclosingElement = this.this$0.getElement().getEnclosingElement();
                Intrinsics.checkNotNull(enclosingElement, "null cannot be cast to non-null type com.sun.tools.javac.code.Symbol.ClassSymbol");
                TypeElement typeElement = (Symbol.ClassSymbol) enclosingElement;
                return new SymbolBasedField(this.this$0.getElement(), new SymbolBasedClass(typeElement, javac, null, ((Symbol.ClassSymbol) typeElement).classfile), javac);
            }
        });
    }

    private final JavaField getJavaField() {
        return (JavaField) this.javaField.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument
    public ClassId getEnumClassId() {
        JavaClass containingClass;
        JavaField javaField = getJavaField();
        if (javaField == null || (containingClass = javaField.getContainingClass()) == null) {
            return null;
        }
        return JavaElementsKt.getClassId(containingClass);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument
    public Name getEntryName() {
        JavaField javaField = getJavaField();
        if (javaField != null) {
            return javaField.getName();
        }
        return null;
    }
}
