package org.jetbrains.kotlin.javac.wrappers.trees;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.tree.JCTree;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClassObjectAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: TreeBasedAnnotation.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedJavaClassObjectAnnotationArgument;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedAnnotationArgument;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassObjectAnnotationArgument;", ModuleXmlParser.TYPE, "Lcom/sun/tools/javac/tree/JCTree$JCExpression;", "name", "Lorg/jetbrains/kotlin/name/Name;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "onElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "(Lcom/sun/tools/javac/tree/JCTree$JCExpression;Lorg/jetbrains/kotlin/name/Name;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/javac/JavacWrapper;Lorg/jetbrains/kotlin/load/java/structure/JavaElement;)V", "getReferencedType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedJavaClassObjectAnnotationArgument extends TreeBasedAnnotationArgument implements JavaClassObjectAnnotationArgument {
    private final CompilationUnitTree compilationUnit;
    private final JavaElement onElement;
    private final JCTree.JCExpression type;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TreeBasedJavaClassObjectAnnotationArgument(JCTree.JCExpression type, Name name2, CompilationUnitTree compilationUnit, JavacWrapper javac, JavaElement onElement) {
        super(name2, javac, null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(onElement, "onElement");
        this.type = type;
        this.compilationUnit = compilationUnit;
        this.onElement = onElement;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassObjectAnnotationArgument
    public JavaType getReferencedType() {
        return TreeBasedType.INSTANCE.create((JCTree) this.type, this.compilationUnit, getJavac(), CollectionsKt.emptyList(), this.onElement);
    }
}
