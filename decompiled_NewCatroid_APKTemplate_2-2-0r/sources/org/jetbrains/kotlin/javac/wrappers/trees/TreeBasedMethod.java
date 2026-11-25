package org.jetbrains.kotlin.javac.wrappers.trees;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.tree.JCTree;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: TreeBasedMethod.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B%\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bR\u0016\u0010\f\u001a\u0004\u0018\u00010\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010!R\u0014\u0010%\u001a\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedMethod;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedMember;", "Lcom/sun/tools/javac/tree/JCTree$JCMethodDecl;", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", Constants.TYPE_TREE, "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Lcom/sun/tools/javac/tree/JCTree$JCMethodDecl;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/load/java/structure/JavaClass;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "annotationParameterDefaultValue", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "getAnnotationParameterDefaultValue", "()Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "isAbstract", "", "()Z", "isFinal", "isStatic", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "returnType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getReturnType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "typeParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "getTypeParameters", "()Ljava/util/List;", "valueParameters", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", "getValueParameters", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedMethod extends TreeBasedMember<JCTree.JCMethodDecl> implements JavaMethod {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TreeBasedMethod(JCTree.JCMethodDecl tree, CompilationUnitTree compilationUnit, JavaClass containingClass, JavacWrapper javac) {
        super((JCTree) tree, compilationUnit, containingClass, javac);
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(javac, "javac");
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        Name nameIdentifier = Name.identifier(((JCTree.JCMethodDecl) getTree()).name.toString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x003a A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Code duplicated, block: B:8:0x0025  */
    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isAbstract */
    public boolean mo7260isAbstract() {
        JCTree.JCModifiers modifiers;
        if (getContainingClass().mo7265isInterface()) {
            JCTree.JCModifiers modifiers2 = ((JCTree.JCMethodDecl) getTree()).getModifiers();
            Intrinsics.checkNotNullExpressionValue(modifiers2, "getModifiers(...)");
            if (UtilsKt.getHasDefaultModifier(modifiers2) || mo7267isStatic()) {
                modifiers = ((JCTree.JCMethodDecl) getTree()).getModifiers();
                Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
                if (UtilsKt.isAbstract(modifiers)) {
                    return false;
                }
            }
        } else {
            modifiers = ((JCTree.JCMethodDecl) getTree()).getModifiers();
            Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
            if (UtilsKt.isAbstract(modifiers)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isStatic */
    public boolean mo7267isStatic() {
        JCTree.JCModifiers modifiers = ((JCTree.JCMethodDecl) getTree()).getModifiers();
        Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
        return UtilsKt.isStatic(modifiers);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isFinal */
    public boolean mo7264isFinal() {
        JCTree.JCModifiers modifiers = ((JCTree.JCMethodDecl) getTree()).getModifiers();
        Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
        return UtilsKt.isFinal(modifiers);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    public Visibility getVisibility() {
        if (getContainingClass().mo7265isInterface()) {
            return Visibilities.Public.INSTANCE;
        }
        JCTree.JCModifiers modifiers = ((JCTree.JCMethodDecl) getTree()).getModifiers();
        Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
        return UtilsKt.getVisibility(modifiers);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameters */
    public List<JavaTypeParameter> mo7258getTypeParameters() {
        Iterable typeParameters = ((JCTree.JCMethodDecl) getTree()).getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        Iterable<JCTree.JCTypeParameter> iterable = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        for (JCTree.JCTypeParameter jCTypeParameter : iterable) {
            Intrinsics.checkNotNull(jCTypeParameter);
            arrayList.add(new TreeBasedTypeParameter(jCTypeParameter, getCompilationUnit(), getJavac(), this));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public List<JavaValueParameter> getValueParameters() {
        Iterable parameters = ((JCTree.JCMethodDecl) getTree()).getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        Iterable<JCTree.JCVariableDecl> iterable = parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        for (JCTree.JCVariableDecl jCVariableDecl : iterable) {
            Intrinsics.checkNotNull(jCVariableDecl);
            arrayList.add(new TreeBasedValueParameter(jCVariableDecl, getCompilationUnit(), getJavac(), this));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public JavaType getReturnType() {
        TreeBasedType.Companion companion = TreeBasedType.INSTANCE;
        JCTree returnType = ((JCTree.JCMethodDecl) getTree()).getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
        return companion.create(returnType, getCompilationUnit(), getJavac(), mo7252getAnnotations(), this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public JavaAnnotationArgument getAnnotationParameterDefaultValue() {
        JCTree.JCExpression jCExpression = ((JCTree.JCMethodDecl) getTree()).defaultValue;
        if (jCExpression == null) {
            return null;
        }
        Name nameIdentifier = Name.identifier("value");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return TreeBasedAnnotationKt.createAnnotationArgument(jCExpression, nameIdentifier, getCompilationUnit(), getJavac(), getContainingClass(), this);
    }
}
