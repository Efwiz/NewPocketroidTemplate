package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: NumberOperatorCallsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0002"}, d2 = {"HASH_CODE_NAME", "Lorg/jetbrains/kotlin/name/Name;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NumberOperatorCallsTransformerKt {
    private static final Name HASH_CODE_NAME;

    static {
        Name nameIdentifier = Name.identifier("hashCode");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        HASH_CODE_NAME = nameIdentifier;
    }
}
