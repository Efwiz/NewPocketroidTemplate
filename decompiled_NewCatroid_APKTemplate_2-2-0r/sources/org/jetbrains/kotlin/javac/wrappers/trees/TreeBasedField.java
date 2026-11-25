package org.jetbrains.kotlin.javac.wrappers.trees;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.tree.JCTree;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.javac.resolve.ConstantEvaluator;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: TreeBasedField.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B%\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000fR\u0014\u0010\u0015\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u000fR\u0014\u0010\u0016\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u000fR\u0014\u0010\u0017\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u000fR\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedField;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedMember;", "Lcom/sun/tools/javac/tree/JCTree$JCVariableDecl;", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", Constants.TYPE_TREE, "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Lcom/sun/tools/javac/tree/JCTree$JCVariableDecl;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/load/java/structure/JavaClass;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "hasConstantNotNullInitializer", "", "getHasConstantNotNullInitializer", "()Z", "initializerValue", "", "getInitializerValue", "()Ljava/lang/Object;", "isAbstract", "isEnumEntry", "isFinal", "isStatic", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedField extends TreeBasedMember<JCTree.JCVariableDecl> implements JavaField {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TreeBasedField(JCTree.JCVariableDecl tree, CompilationUnitTree compilationUnit, JavaClass containingClass, JavacWrapper javac) {
        super((JCTree) tree, compilationUnit, containingClass, javac);
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(javac, "javac");
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        Name nameIdentifier = Name.identifier(((JCTree.JCVariableDecl) getTree()).name.toString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isAbstract */
    public boolean mo7260isAbstract() {
        JCTree.JCModifiers modifiers = ((JCTree.JCVariableDecl) getTree()).getModifiers();
        Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
        return UtilsKt.isAbstract(modifiers);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isStatic */
    public boolean mo7267isStatic() {
        if (!getContainingClass().mo7265isInterface()) {
            JCTree.JCModifiers modifiers = ((JCTree.JCVariableDecl) getTree()).getModifiers();
            Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
            if (!UtilsKt.isStatic(modifiers)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isFinal */
    public boolean mo7264isFinal() {
        if (!getContainingClass().mo7265isInterface()) {
            JCTree.JCModifiers modifiers = ((JCTree.JCVariableDecl) getTree()).getModifiers();
            Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
            if (!UtilsKt.isFinal(modifiers)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    public Visibility getVisibility() {
        if (getContainingClass().mo7265isInterface()) {
            return Visibilities.Public.INSTANCE;
        }
        JCTree.JCModifiers modifiers = ((JCTree.JCVariableDecl) getTree()).getModifiers();
        Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
        return UtilsKt.getVisibility(modifiers);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    /* JADX INFO: renamed from: isEnumEntry */
    public boolean getIsEnumEntry() {
        return (((JCTree.JCVariableDecl) getTree()).getModifiers().flags & 16384) != 0;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    public JavaType getType() {
        TreeBasedType.Companion companion = TreeBasedType.INSTANCE;
        JCTree type = ((JCTree.JCVariableDecl) getTree()).getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return companion.create(type, getCompilationUnit(), getJavac(), mo7252getAnnotations(), getContainingClass());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    public Object getInitializerValue() {
        JCTree.JCExpression jCExpression = ((JCTree.JCVariableDecl) getTree()).init;
        if (jCExpression == null || !mo7273getHasConstantNotNullInitializer()) {
            return null;
        }
        return new ConstantEvaluator(getContainingClass(), getJavac(), getCompilationUnit()).getValue(jCExpression);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    /* JADX INFO: renamed from: getHasConstantNotNullInitializer */
    public boolean mo7273getHasConstantNotNullInitializer() {
        JCTree.JCLiteral jCLiteral = ((JCTree.JCVariableDecl) getTree()).init;
        if (jCLiteral == null) {
            return false;
        }
        if ((jCLiteral instanceof JCTree.JCLiteral) && jCLiteral.value == null) {
            return false;
        }
        JavaType type = getType();
        if (mo7264isFinal()) {
            return (type instanceof TreeBasedPrimitiveType) || ((type instanceof TreeBasedNonGenericClassifierType) && Intrinsics.areEqual(((TreeBasedNonGenericClassifierType) type).getClassifierQualifiedName(), CommonClassNames.JAVA_LANG_STRING));
        }
        return false;
    }
}
