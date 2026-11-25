package org.jetbrains.kotlin.utils;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinExceptionWithAttachments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001aC\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\bH\u0086\bø\u0001\u0000\u0082\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\n"}, d2 = {"checkWithAttachment", "", "value", "", "lazyMessage", "Lkotlin/Function0;", "", "attachments", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/utils/KotlinExceptionWithAttachments;", "util"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinExceptionWithAttachmentsKt {
    public static /* synthetic */ void checkWithAttachment$default(boolean z, Function0 lazyMessage, Function1 attachments, int i, Object obj) {
        if ((i & 4) != 0) {
            attachments = new Function1<KotlinExceptionWithAttachments, Unit>() { // from class: org.jetbrains.kotlin.utils.KotlinExceptionWithAttachmentsKt.checkWithAttachment.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(KotlinExceptionWithAttachments it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(KotlinExceptionWithAttachments kotlinExceptionWithAttachments) {
                    invoke2(kotlinExceptionWithAttachments);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(lazyMessage, "lazyMessage");
        Intrinsics.checkNotNullParameter(attachments, "attachments");
        if (z) {
            return;
        }
        KotlinExceptionWithAttachments kotlinExceptionWithAttachments = new KotlinExceptionWithAttachments((String) lazyMessage.invoke());
        attachments.invoke(kotlinExceptionWithAttachments);
        throw kotlinExceptionWithAttachments;
    }

    public static final void checkWithAttachment(boolean z, Function0<String> lazyMessage, Function1<? super KotlinExceptionWithAttachments, Unit> attachments) {
        Intrinsics.checkNotNullParameter(lazyMessage, "lazyMessage");
        Intrinsics.checkNotNullParameter(attachments, "attachments");
        if (z) {
            return;
        }
        KotlinExceptionWithAttachments kotlinExceptionWithAttachments = new KotlinExceptionWithAttachments(lazyMessage.invoke());
        attachments.invoke(kotlinExceptionWithAttachments);
        throw kotlinExceptionWithAttachments;
    }
}
