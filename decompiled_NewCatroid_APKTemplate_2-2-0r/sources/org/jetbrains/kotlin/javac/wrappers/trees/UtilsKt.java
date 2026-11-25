package org.jetbrains.kotlin.javac.wrappers.trees;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedArrayAnnotationArgument;
import org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedReferenceAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r*\u00020\u000fH\u0000\u001a\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\r*\b\u0012\u0004\u0012\u00020\u00110\r\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0018\u0010\u0005\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0004\"\u0018\u0010\u0006\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0018\u0010\u0007\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0004\"\u0018\u0010\b\u001a\u00020\t*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0012"}, d2 = {"hasDefaultModifier", "", "Lcom/sun/tools/javac/tree/JCTree$JCModifiers;", "getHasDefaultModifier", "(Lcom/sun/tools/javac/tree/JCTree$JCModifiers;)Z", "isAbstract", "isFinal", "isStatic", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "(Lcom/sun/tools/javac/tree/JCTree$JCModifiers;)Lorg/jetbrains/kotlin/descriptors/Visibility;", "annotations", "", "Lcom/sun/tools/javac/tree/JCTree$JCAnnotation;", "Lcom/sun/tools/javac/tree/JCTree;", "filterTypeAnnotations", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "javac-wrapper"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    public static final boolean isAbstract(JCTree.JCModifiers jCModifiers) {
        Intrinsics.checkNotNullParameter(jCModifiers, "<this>");
        return jCModifiers.getFlags().contains(Modifier.ABSTRACT);
    }

    public static final boolean isFinal(JCTree.JCModifiers jCModifiers) {
        Intrinsics.checkNotNullParameter(jCModifiers, "<this>");
        return jCModifiers.getFlags().contains(Modifier.FINAL);
    }

    public static final boolean isStatic(JCTree.JCModifiers jCModifiers) {
        Intrinsics.checkNotNullParameter(jCModifiers, "<this>");
        return jCModifiers.getFlags().contains(Modifier.STATIC);
    }

    public static final boolean getHasDefaultModifier(JCTree.JCModifiers jCModifiers) {
        Intrinsics.checkNotNullParameter(jCModifiers, "<this>");
        return jCModifiers.getFlags().contains(Modifier.DEFAULT);
    }

    public static final Visibility getVisibility(JCTree.JCModifiers jCModifiers) {
        Intrinsics.checkNotNullParameter(jCModifiers, "<this>");
        Set flags = jCModifiers.getFlags();
        Intrinsics.checkNotNullExpressionValue(flags, "getFlags(...)");
        return org.jetbrains.kotlin.javac.wrappers.symbols.UtilsKt.getVisibility((Set<? extends Modifier>) flags);
    }

    public static final Collection<JCTree.JCAnnotation> annotations(JCTree jCTree) {
        Intrinsics.checkNotNullParameter(jCTree, "<this>");
        List list = null;
        if (jCTree instanceof JCTree.JCMethodDecl) {
            JCTree.JCModifiers jCModifiers = ((JCTree.JCMethodDecl) jCTree).mods;
            if (jCModifiers != null) {
                list = jCModifiers.annotations;
            }
        } else if (jCTree instanceof JCTree.JCClassDecl) {
            JCTree.JCModifiers jCModifiers2 = ((JCTree.JCClassDecl) jCTree).mods;
            if (jCModifiers2 != null) {
                list = jCModifiers2.annotations;
            }
        } else if (jCTree instanceof JCTree.JCVariableDecl) {
            JCTree.JCModifiers jCModifiers3 = ((JCTree.JCVariableDecl) jCTree).mods;
            if (jCModifiers3 != null) {
                list = jCModifiers3.annotations;
            }
        } else if (jCTree instanceof JCTree.JCTypeParameter) {
            list = ((JCTree.JCTypeParameter) jCTree).annotations;
        }
        if (list != null) {
            return (Collection) list;
        }
        return CollectionsKt.emptyList();
    }

    public static final Collection<JavaAnnotation> filterTypeAnnotations(Collection<? extends JavaAnnotation> collection) {
        Collection<JavaAnnotation> annotations;
        Object obj;
        Object next;
        JavaAnnotationArgument javaAnnotationArgument;
        VariableElement element;
        Name simpleName;
        org.jetbrains.kotlin.name.Name entryName;
        Intrinsics.checkNotNullParameter(collection, "<this>");
        ArrayList arrayList = new ArrayList();
        ClassId classId = new ClassId(new FqName("java.lang.annotation"), org.jetbrains.kotlin.name.Name.identifier("Target"));
        for (JavaAnnotation javaAnnotation : collection) {
            JavaClass javaClassResolve = javaAnnotation.resolve();
            if (javaClassResolve != null && (annotations = javaClassResolve.mo7252getAnnotations()) != null) {
                Iterator<T> it = annotations.iterator();
                do {
                    obj = null;
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!Intrinsics.areEqual(((JavaAnnotation) next).getClassId(), classId));
                JavaAnnotation javaAnnotation2 = (JavaAnnotation) next;
                if (javaAnnotation2 != null && (javaAnnotationArgument = (JavaAnnotationArgument) CollectionsKt.firstOrNull(javaAnnotation2.getArguments())) != null) {
                    if (javaAnnotationArgument instanceof SymbolBasedArrayAnnotationArgument) {
                        for (Object obj2 : ((SymbolBasedArrayAnnotationArgument) javaAnnotationArgument).getArgs()) {
                            JavaAnnotationArgument javaAnnotationArgument2 = (JavaAnnotationArgument) obj2;
                            SymbolBasedReferenceAnnotationArgument symbolBasedReferenceAnnotationArgument = javaAnnotationArgument2 instanceof SymbolBasedReferenceAnnotationArgument ? (SymbolBasedReferenceAnnotationArgument) javaAnnotationArgument2 : null;
                            if ((symbolBasedReferenceAnnotationArgument == null || (element = symbolBasedReferenceAnnotationArgument.getElement()) == null || (simpleName = element.getSimpleName()) == null) ? false : simpleName.contentEquals("TYPE_USE")) {
                                obj = obj2;
                                break;
                            }
                        }
                        if (((JavaAnnotationArgument) obj) != null) {
                            arrayList.add(javaAnnotation);
                        }
                    } else if (javaAnnotationArgument instanceof SymbolBasedReferenceAnnotationArgument) {
                        Name simpleName2 = ((SymbolBasedReferenceAnnotationArgument) javaAnnotationArgument).getElement().getSimpleName();
                        if ((simpleName2.contentEquals("TYPE_USE") ? simpleName2 : null) != null) {
                            arrayList.add(javaAnnotation);
                        }
                    } else if (javaAnnotationArgument instanceof TreeBasedArrayAnnotationArgument) {
                        for (Object obj3 : ((TreeBasedArrayAnnotationArgument) javaAnnotationArgument).getArgs()) {
                            JavaAnnotationArgument javaAnnotationArgument3 = (JavaAnnotationArgument) obj3;
                            TreeBasedReferenceAnnotationArgument treeBasedReferenceAnnotationArgument = javaAnnotationArgument3 instanceof TreeBasedReferenceAnnotationArgument ? (TreeBasedReferenceAnnotationArgument) javaAnnotationArgument3 : null;
                            if (Intrinsics.areEqual((treeBasedReferenceAnnotationArgument == null || (entryName = treeBasedReferenceAnnotationArgument.getEntryName()) == null) ? null : entryName.asString(), "TYPE_USE")) {
                                obj = obj3;
                                break;
                            }
                        }
                        if (((JavaAnnotationArgument) obj) != null) {
                            arrayList.add(javaAnnotation);
                        }
                    } else if (javaAnnotationArgument instanceof TreeBasedReferenceAnnotationArgument) {
                        org.jetbrains.kotlin.name.Name entryName2 = ((TreeBasedReferenceAnnotationArgument) javaAnnotationArgument).getEntryName();
                        if (Intrinsics.areEqual(entryName2 != null ? entryName2.asString() : null, "TYPE_USE")) {
                            arrayList.add(javaAnnotation);
                        }
                    }
                }
            }
        }
        return arrayList;
    }
}
