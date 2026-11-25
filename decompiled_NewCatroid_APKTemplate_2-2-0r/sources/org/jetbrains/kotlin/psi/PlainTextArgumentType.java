package org.jetbrains.kotlin.psi;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: createByPattern.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B'\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tR\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/psi/PlainTextArgumentType;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/psi/ArgumentType;", "klass", "Ljava/lang/Class;", "toPlainText", "Lkotlin/Function1;", "", "(Ljava/lang/Class;Lkotlin/jvm/functions/Function1;)V", "getToPlainText", "()Lkotlin/jvm/functions/Function1;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PlainTextArgumentType<T> extends ArgumentType<T> {
    private final Function1<T, String> toPlainText;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public PlainTextArgumentType(Class<T> klass, Function1<? super T, String> toPlainText) {
        super(klass);
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(toPlainText, "toPlainText");
        this.toPlainText = toPlainText;
    }

    public final Function1<T, String> getToPlainText() {
        return this.toPlainText;
    }
}
