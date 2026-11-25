package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.psi.KtModifierList;

/* JADX INFO: compiled from: JvmRecordApplicabilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0002\u001a'\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00062\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\b\"\u00020\tH\u0002¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"areRecordsAllowed", "", "Lorg/jetbrains/kotlin/config/JvmTarget;", "enableJvmPreview", "findOneOfModifiers", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "modifierTokens", "", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "(Lorg/jetbrains/kotlin/psi/KtModifierList;[Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;)Lcom/intellij/psi/PsiElement;", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmRecordApplicabilityCheckerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final PsiElement findOneOfModifiers(KtModifierList ktModifierList, KtModifierKeywordToken... ktModifierKeywordTokenArr) {
        for (KtModifierKeywordToken ktModifierKeywordToken : ktModifierKeywordTokenArr) {
            PsiElement modifier = ktModifierList.getModifier(ktModifierKeywordToken);
            if (modifier != null) {
                return modifier;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean areRecordsAllowed(JvmTarget jvmTarget, boolean z) {
        if (jvmTarget.getMajorVersion() < JvmTarget.JVM_15.getMajorVersion()) {
            return false;
        }
        return z || jvmTarget.getMajorVersion() > JvmTarget.JVM_15.getMajorVersion();
    }
}
