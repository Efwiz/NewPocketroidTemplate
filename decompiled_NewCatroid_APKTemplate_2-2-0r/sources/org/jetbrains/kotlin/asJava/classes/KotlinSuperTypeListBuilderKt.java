package org.jetbrains.kotlin.asJava.classes;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference;

/* JADX INFO: compiled from: KotlinSuperTypeListBuilder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"nameFromSource", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiQualifiedReference;", "getNameFromSource", "(Lcom/intellij/psi/PsiQualifiedReference;)Ljava/lang/String;", "light-classes-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinSuperTypeListBuilderKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String getNameFromSource(PsiQualifiedReference psiQualifiedReference) {
        String string;
        String referenceName = psiQualifiedReference.getReferenceName();
        if (referenceName == null) {
            return null;
        }
        PsiElement qualifier = psiQualifiedReference.getQualifier();
        PsiQualifiedReference psiQualifiedReference2 = qualifier instanceof PsiQualifiedReference ? (PsiQualifiedReference) qualifier : null;
        return (psiQualifiedReference2 == null || (string = new StringBuilder().append(getNameFromSource(psiQualifiedReference2)).append('.').append(referenceName).toString()) == null) ? referenceName : string;
    }
}
