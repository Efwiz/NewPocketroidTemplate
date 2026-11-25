package org.jetbrains.kotlin.javac.wrappers.trees;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.tree.JCTree;
import java.util.Collection;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: TreeBasedAnnotation.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0015\u001a\u0004\u0018\u00010\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedReferenceAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaEnumValueAnnotationArgument;", "name", "Lorg/jetbrains/kotlin/name/Name;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "field", "Lcom/sun/tools/javac/tree/JCTree$JCFieldAccess;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "onElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "(Lorg/jetbrains/kotlin/name/Name;Lcom/sun/source/tree/CompilationUnitTree;Lcom/sun/tools/javac/tree/JCTree$JCFieldAccess;Lorg/jetbrains/kotlin/javac/JavacWrapper;Lorg/jetbrains/kotlin/load/java/structure/JavaElement;)V", "entryName", "getEntryName", "()Lorg/jetbrains/kotlin/name/Name;", "enumClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "getEnumClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "javaField", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "getJavaField", "()Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "javaField$delegate", "Lkotlin/Lazy;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedReferenceAnnotationArgument extends TreeBasedAnnotationArgument implements JavaEnumValueAnnotationArgument {
    private final CompilationUnitTree compilationUnit;
    private final JCTree.JCFieldAccess field;

    /* JADX INFO: renamed from: javaField$delegate, reason: from kotlin metadata */
    private final Lazy javaField;
    private final JavaElement onElement;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TreeBasedReferenceAnnotationArgument(Name name2, CompilationUnitTree compilationUnit, JCTree.JCFieldAccess field, final JavacWrapper javac, JavaElement onElement) {
        super(name2, javac, null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(onElement, "onElement");
        this.compilationUnit = compilationUnit;
        this.field = field;
        this.onElement = onElement;
        this.javaField = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<JavaField>() { // from class: org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedReferenceAnnotationArgument$javaField$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JavaField invoke() {
                Collection<JavaField> collectionMo7254getFields;
                JavacWrapper javacWrapper = javac;
                JCTree.JCExpression selected = this.field.selected;
                Intrinsics.checkNotNullExpressionValue(selected, "selected");
                JavaClassifier javaClassifierResolve = javacWrapper.resolve((JCTree) selected, this.compilationUnit, this.onElement);
                Object obj = null;
                JavaClass javaClass = javaClassifierResolve instanceof JavaClass ? (JavaClass) javaClassifierResolve : null;
                Name nameIdentifier = Name.identifier(this.field.name.toString());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                if (javaClass == null || (collectionMo7254getFields = javaClass.mo7254getFields()) == null) {
                    return null;
                }
                for (Object obj2 : collectionMo7254getFields) {
                    if (Intrinsics.areEqual(((JavaField) obj2).getName(), nameIdentifier)) {
                        obj = obj2;
                        break;
                    }
                }
                return (JavaField) obj;
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
