package org.jetbrains.kotlin.asJava.classes;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationContainer;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: KtLightClassForSourceDeclaration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u000e\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0010\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0002\u001a\u0010\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\f\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u0003\u001a\n\u0010\u000b\u001a\u00020\u0001*\u00020\f\u001a\n\u0010\r\u001a\u00020\u0001*\u00020\u0003¨\u0006\u000e"}, d2 = {"classDeclaredInUnexpectedPosition", "", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getOutermostClassOrObject", "hasParseErrorsAround", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "isEnumEntryWithoutBody", "defaultJavaAncestorQualifiedName", "", "isPossiblyAffectedByAllOpen", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForSourceDeclaration;", "shouldNotBeVisibleAsLightClass", "light-classes-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtLightClassForSourceDeclarationKt {
    public static final boolean isPossiblyAffectedByAllOpen(KtLightClassForSourceDeclaration ktLightClassForSourceDeclaration) {
        Intrinsics.checkNotNullParameter(ktLightClassForSourceDeclaration, "<this>");
        if (!ktLightClassForSourceDeclaration.isAnnotationType() && !ktLightClassForSourceDeclaration.isInterface()) {
            List<KtAnnotationEntry> annotationEntries = ktLightClassForSourceDeclaration.getKotlinOrigin().getAnnotationEntries();
            Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
            if (!annotationEntries.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static final KtClassOrObject getOutermostClassOrObject(KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        KtClassOrObject outermostClassOrObject = KtPsiUtil.getOutermostClassOrObject(classOrObject);
        if (outermostClassOrObject != null) {
            return outermostClassOrObject;
        }
        throw new IllegalStateException("Attempt to build a light class for a local class: " + classOrObject.getText());
    }

    public static final String defaultJavaAncestorQualifiedName(KtClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        if (!(ktClassOrObject instanceof KtClass)) {
            return CommonClassNames.JAVA_LANG_OBJECT;
        }
        if (ktClassOrObject.isAnnotation()) {
            return CommonClassNames.JAVA_LANG_ANNOTATION_ANNOTATION;
        }
        KtClass ktClass = (KtClass) ktClassOrObject;
        if (ktClass.isEnum()) {
            return CommonClassNames.JAVA_LANG_ENUM;
        }
        ktClass.isInterface();
        return CommonClassNames.JAVA_LANG_OBJECT;
    }

    public static final boolean shouldNotBeVisibleAsLightClass(KtClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        PsiFile containingFile = ktClassOrObject.getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        if (containingFile instanceof KtCodeFragment) {
            return true;
        }
        KtFile ktFile = containingFile instanceof KtFile ? (KtFile) containingFile : null;
        if (ktFile != null && ktFile.getIsCompiled() && Intrinsics.areEqual(((KtFile) containingFile).getVirtualFile().getExtension(), BuiltInSerializerProtocol.BUILTINS_FILE_EXTENSION)) {
            return true;
        }
        KtClassOrObject ktClassOrObject2 = ktClassOrObject;
        Sequence sequenceFilter = SequencesKt.filter(PsiUtilsKt.getParentsWithSelf(ktClassOrObject2), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclarationKt$shouldNotBeVisibleAsLightClass$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof KtClassOrObject);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        Iterator it = sequenceFilter.iterator();
        while (it.hasNext()) {
            if (PsiUtilsKt.hasExpectModifier((KtClassOrObject) it.next())) {
                return true;
            }
        }
        return (ktClassOrObject.isLocal() && (containingFile.getVirtualFile() == null || hasParseErrorsAround(ktClassOrObject2) || PsiUtilCore.hasErrorElementChild(ktClassOrObject2) || classDeclaredInUnexpectedPosition(ktClassOrObject))) || isEnumEntryWithoutBody(ktClassOrObject);
    }

    private static final boolean classDeclaredInUnexpectedPosition(KtClassOrObject ktClassOrObject) {
        if (ktClassOrObject instanceof KtObjectDeclaration) {
            return false;
        }
        PsiElement parent = ktClassOrObject.getLightParent();
        return ((parent instanceof KtBlockExpression) || (parent instanceof KtDeclarationContainer)) ? false : true;
    }

    private static final boolean isEnumEntryWithoutBody(KtClassOrObject ktClassOrObject) {
        List<KtDeclaration> declarations;
        if (!(ktClassOrObject instanceof KtEnumEntry)) {
            return false;
        }
        KtClassBody body = ktClassOrObject.getBody();
        if (body == null || (declarations = body.getDeclarations()) == null) {
            return true;
        }
        return declarations.isEmpty();
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002e, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r1 != null ? r1.getElementType() : null, org.jetbrains.kotlin.com.intellij.psi.TokenType.ERROR_ELEMENT) != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final boolean hasParseErrorsAround(org.jetbrains.kotlin.com.intellij.psi.PsiElement r6) {
        /*
            org.jetbrains.kotlin.com.intellij.lang.ASTNode r6 = r6.getNode()
            r0 = 0
            if (r6 != 0) goto L8
            return r0
        L8:
            org.jetbrains.kotlin.com.intellij.lang.ASTNode r1 = org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil.nextLeaf(r6)
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L31
            org.jetbrains.kotlin.com.intellij.psi.tree.IElementType r4 = r1.getElementType()
            org.jetbrains.kotlin.com.intellij.psi.tree.IElementType r5 = org.jetbrains.kotlin.com.intellij.psi.TokenType.ERROR_ELEMENT
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)
            if (r4 != 0) goto L30
            org.jetbrains.kotlin.com.intellij.lang.ASTNode r1 = r1.getTreePrev()
            if (r1 == 0) goto L27
            org.jetbrains.kotlin.com.intellij.psi.tree.IElementType r1 = r1.getElementType()
            goto L28
        L27:
            r1 = r2
        L28:
            org.jetbrains.kotlin.com.intellij.psi.tree.IElementType r4 = org.jetbrains.kotlin.com.intellij.psi.TokenType.ERROR_ELEMENT
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r4)
            if (r1 == 0) goto L31
        L30:
            return r3
        L31:
            org.jetbrains.kotlin.com.intellij.lang.ASTNode r6 = org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil.prevLeaf(r6)
            if (r6 == 0) goto L56
            org.jetbrains.kotlin.com.intellij.psi.tree.IElementType r1 = r6.getElementType()
            org.jetbrains.kotlin.com.intellij.psi.tree.IElementType r4 = org.jetbrains.kotlin.com.intellij.psi.TokenType.ERROR_ELEMENT
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r4)
            if (r1 != 0) goto L55
            org.jetbrains.kotlin.com.intellij.lang.ASTNode r6 = r6.getTreeNext()
            if (r6 == 0) goto L4d
            org.jetbrains.kotlin.com.intellij.psi.tree.IElementType r2 = r6.getElementType()
        L4d:
            org.jetbrains.kotlin.com.intellij.psi.tree.IElementType r6 = org.jetbrains.kotlin.com.intellij.psi.TokenType.ERROR_ELEMENT
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r6)
            if (r6 == 0) goto L56
        L55:
            return r3
        L56:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclarationKt.hasParseErrorsAround(org.jetbrains.kotlin.com.intellij.psi.PsiElement):boolean");
    }
}
