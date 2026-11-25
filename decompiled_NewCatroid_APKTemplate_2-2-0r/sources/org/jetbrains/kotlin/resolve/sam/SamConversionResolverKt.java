package org.jetbrains.kotlin.resolve.sam;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SamConversionResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"SAM_LOOKUP_NAME", "Lorg/jetbrains/kotlin/name/Name;", "getSAM_LOOKUP_NAME", "()Lorg/jetbrains/kotlin/name/Name;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SamConversionResolverKt {
    private static final Name SAM_LOOKUP_NAME;

    static {
        Name nameSpecial = Name.special("<SAM-CONSTRUCTOR>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        SAM_LOOKUP_NAME = nameSpecial;
    }

    public static final Name getSAM_LOOKUP_NAME() {
        return SAM_LOOKUP_NAME;
    }
}
