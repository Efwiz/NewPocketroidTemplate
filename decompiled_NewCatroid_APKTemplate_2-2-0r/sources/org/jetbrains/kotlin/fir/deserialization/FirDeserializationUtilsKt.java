package org.jetbrains.kotlin.fir.deserialization;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: compiled from: FirDeserializationUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\"\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0000¨\u0006\b"}, d2 = {CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement$Companion;", "proto", "Lorg/jetbrains/kotlin/protobuf/MessageLiteOrBuilder;", "context", "Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;", "fir-deserialization"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirDeserializationUtilsKt {
    public static final List<VersionRequirement> create(VersionRequirement.Companion companion, MessageLiteOrBuilder proto, FirDeserializationContext context) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(context, "context");
        return companion.create(proto, context.getNameResolver(), context.getVersionRequirementTable());
    }
}
