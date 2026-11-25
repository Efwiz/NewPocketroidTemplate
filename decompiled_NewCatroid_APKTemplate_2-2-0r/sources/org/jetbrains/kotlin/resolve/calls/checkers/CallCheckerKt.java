package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.DeferredType;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: CallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"isComputingDeferredType", "", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallCheckerKt {
    public static final boolean isComputingDeferredType(CallChecker callChecker, KotlinType type) {
        Intrinsics.checkNotNullParameter(callChecker, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return (type instanceof DeferredType) && ((DeferredType) type).isComputing();
    }
}
