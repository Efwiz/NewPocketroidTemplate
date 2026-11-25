package org.jetbrains.kotlin.psi.psiUtil;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: compiled from: psiUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003\"\u000e\b\u0001\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0006\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "C", "", "it", "invoke", "(Lcom/intellij/psi/PsiElement;)V", "org/jetbrains/kotlin/psi/psiUtil/PsiUtilsKt$collectDescendantsOfTypeTo$2"}, k = 3, mv = {1, 9, 0}, xi = 176)
public final class PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfTypeTo$1<T> extends Lambda implements Function1<T, Unit> {
    final /* synthetic */ Function1 $predicate;
    final /* synthetic */ Collection $to;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiUtilsKt$collectDescendantsOfType$$inlined$collectDescendantsOfTypeTo$1(Function1 function1, Collection collection) {
        super(1);
        this.$predicate = function1;
        this.$to = collection;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
        invoke((PsiElement) obj);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Incorrect types in method signature: (TT;)V */
    public final void invoke(PsiElement it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (((Boolean) this.$predicate.invoke(it)).booleanValue()) {
            this.$to.add(it);
        }
    }
}
