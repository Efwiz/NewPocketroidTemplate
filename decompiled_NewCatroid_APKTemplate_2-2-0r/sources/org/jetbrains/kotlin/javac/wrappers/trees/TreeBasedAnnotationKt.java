package org.jetbrains.kotlin.javac.wrappers.trees;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.tree.JCTree;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.javac.resolve.ConstantEvaluator;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: TreeBasedAnnotation.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a<\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0000\u001a&\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0002\u001a4\u0010\u0012\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u0013"}, d2 = {"createAnnotationArgument", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "argument", "Lcom/sun/tools/javac/tree/JCTree$JCExpression;", "name", "Lorg/jetbrains/kotlin/name/Name;", "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "onElement", "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "createAnnotationArguments", "", "annotation", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedAnnotation;", "resolveArgumentValue", "javac-wrapper"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedAnnotationKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Collection<JavaAnnotationArgument> createAnnotationArguments(TreeBasedAnnotation treeBasedAnnotation, JavacWrapper javacWrapper, JavaElement javaElement) {
        Iterable<JCTree.JCAssign> arguments = treeBasedAnnotation.getAnnotation().getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        ArrayList arrayList = new ArrayList();
        for (JCTree.JCAssign jCAssign : arguments) {
            Name nameIdentifier = Name.identifier(jCAssign instanceof JCTree.JCAssign ? jCAssign.lhs.toString() : "value");
            Intrinsics.checkNotNull(nameIdentifier);
            Intrinsics.checkNotNull(jCAssign);
            JavaAnnotationArgument javaAnnotationArgumentCreateAnnotationArgument = createAnnotationArgument(jCAssign, nameIdentifier, treeBasedAnnotation.getCompilationUnit(), javacWrapper, treeBasedAnnotation.resolve(), javaElement);
            if (javaAnnotationArgumentCreateAnnotationArgument != null) {
                arrayList.add(javaAnnotationArgumentCreateAnnotationArgument);
            }
        }
        return arrayList;
    }

    public static final JavaAnnotationArgument createAnnotationArgument(JCTree.JCExpression argument, Name name2, CompilationUnitTree compilationUnit, JavacWrapper javac, JavaClass javaClass, JavaElement onElement) {
        TreeBasedReferenceAnnotationArgument treeBasedReferenceAnnotationArgument;
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(javac, "javac");
        Intrinsics.checkNotNullParameter(onElement, "onElement");
        if (argument instanceof JCTree.JCLiteral) {
            return new TreeBasedLiteralAnnotationArgument(name2, ((JCTree.JCLiteral) argument).value, javac);
        }
        if (argument instanceof JCTree.JCFieldAccess) {
            JCTree.JCFieldAccess jCFieldAccess = (JCTree.JCFieldAccess) argument;
            if (jCFieldAccess.name.contentEquals("class")) {
                JCTree.JCExpression selected = jCFieldAccess.selected;
                Intrinsics.checkNotNullExpressionValue(selected, "selected");
                treeBasedReferenceAnnotationArgument = new TreeBasedJavaClassObjectAnnotationArgument(selected, name2, compilationUnit, javac, onElement);
            } else {
                treeBasedReferenceAnnotationArgument = new TreeBasedReferenceAnnotationArgument(name2, compilationUnit, jCFieldAccess, javac, onElement);
            }
            return treeBasedReferenceAnnotationArgument;
        }
        if (argument instanceof JCTree.JCAssign) {
            JCTree.JCExpression rhs = ((JCTree.JCAssign) argument).rhs;
            Intrinsics.checkNotNullExpressionValue(rhs, "rhs");
            return createAnnotationArgument(rhs, name2, compilationUnit, javac, javaClass, onElement);
        }
        if (!(argument instanceof JCTree.JCNewArray)) {
            if (argument instanceof JCTree.JCAnnotation) {
                return new TreeBasedAnnotationAsAnnotationArgument((JCTree.JCAnnotation) argument, name2, compilationUnit, javac, onElement);
            }
            if (argument instanceof JCTree.JCParens) {
                JCTree.JCExpression expr = ((JCTree.JCParens) argument).expr;
                Intrinsics.checkNotNullExpressionValue(expr, "expr");
                return createAnnotationArgument(expr, name2, compilationUnit, javac, javaClass, onElement);
            }
            if (!(argument instanceof JCTree.JCBinary) && !(argument instanceof JCTree.JCUnary)) {
                throw new UnsupportedOperationException("Unknown annotation argument " + argument);
            }
            return resolveArgumentValue(argument, javaClass, name2, compilationUnit, javac);
        }
        Iterable<JCTree.JCExpression> elems = ((JCTree.JCNewArray) argument).elems;
        Intrinsics.checkNotNullExpressionValue(elems, "elems");
        ArrayList arrayList = new ArrayList();
        for (JCTree.JCExpression jCExpression : elems) {
            Intrinsics.checkNotNull(jCExpression);
            JavaAnnotationArgument javaAnnotationArgumentCreateAnnotationArgument = createAnnotationArgument(jCExpression, name2, compilationUnit, javac, javaClass, onElement);
            if (javaAnnotationArgumentCreateAnnotationArgument != null) {
                arrayList.add(javaAnnotationArgumentCreateAnnotationArgument);
            }
        }
        return new TreeBasedArrayAnnotationArgument(arrayList, name2, javac);
    }

    private static final JavaAnnotationArgument resolveArgumentValue(JCTree.JCExpression jCExpression, JavaClass javaClass, Name name2, CompilationUnitTree compilationUnitTree, JavacWrapper javacWrapper) {
        if (javaClass == null) {
            return null;
        }
        Object value = new ConstantEvaluator(javaClass, javacWrapper, compilationUnitTree).getValue(jCExpression);
        return value != null ? new TreeBasedLiteralAnnotationArgument(name2, value, javacWrapper) : null;
    }
}
