package org.jetbrains.kotlin.com.intellij.psi.util;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.ServerPageFile;

/* JADX INFO: loaded from: classes3.dex */
public final class FileTypeUtils {
    public static boolean isInServerPageFile(PsiElement psiElement) {
        return PsiUtilCore.getTemplateLanguageFile(psiElement) instanceof ServerPageFile;
    }
}
