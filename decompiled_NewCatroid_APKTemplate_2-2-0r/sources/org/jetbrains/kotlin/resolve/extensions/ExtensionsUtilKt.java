package org.jetbrains.kotlin.resolve.extensions;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;

/* JADX INFO: compiled from: extensionsUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aB\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\u0004\b\u0001\u0010\u00012\u0006\u0010\u0004\u001a\u0002H\u00022\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u0006¢\u0006\u0002\b\u0007H\u0080\bø\u0001\u0000¢\u0006\u0002\u0010\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"withLinkageErrorLogger", "R", ExifInterface.GPS_DIRECTION_TRUE, "", "receiver", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExtensionsUtilKt {
    public static final <T, R> R withLinkageErrorLogger(T receiver, Function1<? super T, ? extends R> block) {
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(block, "block");
        try {
            return block.invoke(receiver);
        } catch (LinkageError e) {
            Logger logger = Logger.getInstance(receiver.getClass());
            Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
            logger.error(receiver.getClass().getName() + " caused LinkageError", e);
            throw e;
        }
    }
}
