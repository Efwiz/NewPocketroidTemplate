package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;

/* JADX INFO: compiled from: KtFileClassProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001b\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&¢\u0006\u0002\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtFileClassProvider;", "", "getFileClasses", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Lorg/jetbrains/kotlin/psi/KtFile;)[Lcom/intellij/psi/PsiClass;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtFileClassProvider {
    PsiClass[] getFileClasses(KtFile ktFile);
}
