package org.jetbrains.kotlin.backend.common.serialization.mangle;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: mangleUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001aM\u0010\u0004\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u00072\n\u0010\b\u001a\u00060\tj\u0002`\n2\u0006\u0010\u000b\u001a\u00020\f2!\u0010\r\u001a\u001d\u0012\b\u0012\u00060\tj\u0002`\n\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u00020\u00050\u000e¢\u0006\u0002\b\u000f\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0010"}, d2 = {"publishedApiAnnotation", "Lorg/jetbrains/kotlin/name/FqName;", "getPublishedApiAnnotation", "()Lorg/jetbrains/kotlin/name/FqName;", "collectForMangler", "", ExifInterface.GPS_DIRECTION_TRUE, "", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "params", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleConstant;", "collect", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MangleUtilsKt {
    private static final FqName publishedApiAnnotation = new FqName("kotlin.PublishedApi");

    public static final <T> void collectForMangler(Iterable<? extends T> iterable, StringBuilder builder, MangleConstant params, Function2<? super StringBuilder, ? super T, Unit> collect) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(params, "params");
        Intrinsics.checkNotNullParameter(collect, "collect");
        builder.append(params.getPrefix());
        boolean z = true;
        boolean z2 = true;
        for (T t : iterable) {
            if (z2) {
                z2 = false;
            } else if (z) {
                builder.append(params.getSeparator());
            }
            int length = builder.length();
            collect.invoke(builder, t);
            z = length < builder.length();
        }
        if (!z) {
            StringBuilder sb = builder;
            if (StringsKt.last(sb) == params.getSeparator()) {
                builder.deleteCharAt(StringsKt.getLastIndex(sb));
            }
        }
        builder.append(params.getSuffix());
    }

    public static final FqName getPublishedApiAnnotation() {
        return publishedApiAnnotation;
    }
}
