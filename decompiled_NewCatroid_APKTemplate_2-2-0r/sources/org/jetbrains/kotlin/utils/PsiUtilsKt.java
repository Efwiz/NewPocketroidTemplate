package org.jetbrains.kotlin.utils;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;

/* JADX INFO: compiled from: psiUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a'\u0010\u0004\u001a\u0004\u0018\u00010\u0003*\u00020\u00032\u0012\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0006\"\u00020\u0001H\u0002¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"getElementTextWithContext", "", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "parentOfType", "psiClassNames", "", "(Lcom/intellij/psi/PsiElement;[Ljava/lang/String;)Lcom/intellij/psi/PsiElement;", "util"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PsiUtilsKt {
    public static final String getElementTextWithContext(final PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        if (!psiElement.isValid()) {
            return "<invalid element " + psiElement + '>';
        }
        PsiFile containingFile = psiElement.getContainingFile();
        PsiFile psiFileParentOfType = parentOfType(psiElement, "KtImportDirective");
        if (psiFileParentOfType == null && (psiFileParentOfType = parentOfType(psiElement, "KtPackageDirective")) == null && (psiFileParentOfType = parentOfType(psiElement, "KtDeclarationWithBody", "KtClassOrObject", "KtScript")) == null && (psiFileParentOfType = parentOfType(psiElement, "KtProperty")) == null) {
            psiFileParentOfType = containingFile;
        }
        final StringBuilder sb = new StringBuilder();
        final String str = "ELEMENT";
        psiFileParentOfType.accept(new PsiElementVisitor() { // from class: org.jetbrains.kotlin.utils.PsiUtilsKt$getElementTextWithContext$elementTextInContext$1$1
            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
            public void visitElement(PsiElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (element == psiElement) {
                    sb.append("<" + str + '>');
                }
                if (element instanceof LeafPsiElement) {
                    sb.append(((LeafPsiElement) element).getText());
                } else {
                    element.acceptChildren(this);
                }
                if (element == psiElement) {
                    sb.append("</" + str + '>');
                }
            }
        });
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        String string2 = kotlin.text.StringsKt.trim((CharSequence) kotlin.text.StringsKt.trimIndent(string)).toString();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sbAppend = sb2.append("<File name: " + containingFile.getName() + ", Physical: " + containingFile.getIsPhysical() + '>');
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
        sb2.append(string2);
        String string3 = sb2.toString();
        Intrinsics.checkNotNullExpressionValue(string3, "toString(...)");
        return string3;
    }

    private static final boolean parentOfType$acceptsClass(String[] strArr, Class<?> cls) {
        if (ArraysKt.contains(strArr, cls.getSimpleName())) {
            return true;
        }
        Class<? super Object> superclass = cls.getSuperclass();
        if (superclass != null && parentOfType$acceptsClass(strArr, superclass)) {
            return true;
        }
        Class<?>[] interfaces = cls.getInterfaces();
        Intrinsics.checkNotNullExpressionValue(interfaces, "getInterfaces(...)");
        for (Class<?> cls2 : interfaces) {
            Intrinsics.checkNotNull(cls2);
            if (parentOfType$acceptsClass(strArr, cls2)) {
                return true;
            }
        }
        return false;
    }

    private static final PsiElement parentOfType(PsiElement psiElement, String... strArr) {
        Object next;
        Iterator it = SequencesKt.filter(SequencesKt.generateSequence(psiElement, new Function1<PsiElement, PsiElement>() { // from class: org.jetbrains.kotlin.utils.PsiUtilsKt.parentOfType.1
            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(PsiElement it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return it2.getLightParent();
            }
        }), new Function1<PsiElement, Boolean>() { // from class: org.jetbrains.kotlin.utils.PsiUtilsKt.parentOfType.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(PsiElement it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(!(it2 instanceof PsiFile));
            }
        }).iterator();
        while (it.hasNext()) {
            next = it.next();
            if (parentOfType$acceptsClass(strArr, ((PsiElement) next).getClass())) {
                return (PsiElement) next;
            }
        }
        next = null;
        return (PsiElement) next;
    }
}
