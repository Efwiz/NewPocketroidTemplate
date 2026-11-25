package org.jetbrains.kotlin.psi;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.psi.psiUtil.PsiChildRange;

/* JADX INFO: compiled from: createByPattern.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006J%\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00028\u00002\u0006\u0010\n\u001a\u00028\u00002\u0006\u0010\u000b\u001a\u00020\fH\u0016¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/psi/PsiElementArgumentType;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/psi/PsiElementPlaceholderArgumentType;", "klass", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "replacePlaceholderElement", "Lorg/jetbrains/kotlin/psi/psiUtil/PsiChildRange;", "placeholder", "argument", "reformat", "", "(Lcom/intellij/psi/PsiElement;Lcom/intellij/psi/PsiElement;Z)Lorg/jetbrains/kotlin/psi/psiUtil/PsiChildRange;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PsiElementArgumentType<T extends PsiElement> extends PsiElementPlaceholderArgumentType<T, T> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiElementArgumentType(Class<T> klass) {
        super(klass, klass);
        Intrinsics.checkNotNullParameter(klass, "klass");
    }

    @Override // org.jetbrains.kotlin.psi.PsiElementPlaceholderArgumentType
    public PsiChildRange replacePlaceholderElement(T placeholder, T argument, boolean z) {
        PsiElement psiElementReplace;
        Intrinsics.checkNotNullParameter(placeholder, "placeholder");
        Intrinsics.checkNotNullParameter(argument, "argument");
        if (placeholder instanceof KtExpressionImplStub) {
            psiElementReplace = KtExpressionImpl.INSTANCE.replaceExpression((KtExpression) placeholder, argument, z, new PsiElementArgumentType$replacePlaceholderElement$result$1(placeholder));
        } else {
            psiElementReplace = placeholder.replace(argument);
        }
        if (CodeEditUtil.isNodeGenerated(argument.getNode())) {
            psiElementReplace = CodeStyleManager.getInstance(psiElementReplace.getProject()).reformat(psiElementReplace, true);
        }
        PsiChildRange.Companion companion = PsiChildRange.INSTANCE;
        Intrinsics.checkNotNull(psiElementReplace);
        return companion.singleElement(psiElementReplace);
    }
}
