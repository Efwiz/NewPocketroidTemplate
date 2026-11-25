package org.jetbrains.kotlin.utils;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: compiled from: KotlinExceptionWithAttachments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00060\u0001j\u0002`\u00022\u00020\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u001b\b\u0016\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u001a\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u00052\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\u0018\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/utils/KotlinExceptionWithAttachments;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "Lorg/jetbrains/kotlin/utils/exceptions/KotlinExceptionWithAttachments;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "mutableAttachments", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Attachment;", "getMutableAttachments", "()Ljava/util/List;", "withAttachment", "name", FirebaseAnalytics.Param.CONTENT, "", "withPsiAttachment", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinExceptionWithAttachments extends RuntimeException implements org.jetbrains.kotlin.utils.exceptions.KotlinExceptionWithAttachments {
    private final List<Attachment> mutableAttachments;

    @Override // org.jetbrains.kotlin.utils.exceptions.KotlinExceptionWithAttachments
    public List<Attachment> getMutableAttachments() {
        return this.mutableAttachments;
    }

    @Override // org.jetbrains.kotlin.utils.exceptions.KotlinExceptionWithAttachments
    public KotlinExceptionWithAttachments withAttachment(String name2, Object content) {
        Intrinsics.checkNotNullParameter(name2, "name");
        org.jetbrains.kotlin.utils.exceptions.KotlinExceptionWithAttachments kotlinExceptionWithAttachmentsWithAttachment = super.withAttachment(name2, content);
        Intrinsics.checkNotNull(kotlinExceptionWithAttachmentsWithAttachment, "null cannot be cast to non-null type org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments");
        return (KotlinExceptionWithAttachments) kotlinExceptionWithAttachmentsWithAttachment;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinExceptionWithAttachments(String message) {
        super(message);
        Intrinsics.checkNotNullParameter(message, "message");
        this.mutableAttachments = new ArrayList();
    }

    public KotlinExceptionWithAttachments(String str, Throwable th) {
        super(str, th);
        this.mutableAttachments = new ArrayList();
        org.jetbrains.kotlin.utils.exceptions.KotlinExceptionWithAttachments.INSTANCE.withAttachmentsFrom$util(this, th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String withPsiAttachment$lambda$1$lambda$0(PsiElement psiElement) {
        if (psiElement != null) {
            return PsiUtilsKt.getElementTextWithContext(psiElement);
        }
        return null;
    }

    public final KotlinExceptionWithAttachments withPsiAttachment(String name2, final PsiElement element) {
        Object objM2898constructorimpl;
        Intrinsics.checkNotNullParameter(name2, "name");
        try {
            Result.Companion companion = Result.INSTANCE;
            objM2898constructorimpl = Result.m2898constructorimpl((String) ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                public final Object compute() {
                    return KotlinExceptionWithAttachments.withPsiAttachment$lambda$1$lambda$0(element);
                }
            }));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m2904isFailureimpl(objM2898constructorimpl)) {
            objM2898constructorimpl = null;
        }
        String str = (String) objM2898constructorimpl;
        if (str != null) {
            withAttachment(name2, (Object) str);
        }
        return this;
    }
}
