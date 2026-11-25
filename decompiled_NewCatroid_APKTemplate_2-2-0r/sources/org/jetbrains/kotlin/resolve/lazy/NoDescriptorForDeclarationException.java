package org.jetbrains.kotlin.resolve.lazy;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;

/* JADX INFO: compiled from: AbsentDescriptorHandler.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/NoDescriptorForDeclarationException;", "Lorg/jetbrains/kotlin/utils/KotlinExceptionWithAttachments;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "additionalDetails", "", "(Lorg/jetbrains/kotlin/psi/KtDeclaration;Ljava/lang/String;)V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NoDescriptorForDeclarationException extends KotlinExceptionWithAttachments {
    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public NoDescriptorForDeclarationException(KtDeclaration declaration) {
        this(declaration, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(declaration, "declaration");
    }

    public /* synthetic */ NoDescriptorForDeclarationException(KtDeclaration ktDeclaration, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktDeclaration, (i & 2) != 0 ? null : str);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public NoDescriptorForDeclarationException(KtDeclaration declaration, String str) {
        String string;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        super("Descriptor wasn't found for declaration " + declaration + ((str == null || (string = new StringBuilder("\n---------------------------------------------------\n").append(str).toString()) == null) ? "" : string));
        withPsiAttachment("declaration.kt", declaration);
    }
}
