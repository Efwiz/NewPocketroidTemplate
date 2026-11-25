package org.jetbrains.kotlin.serialization.deserialization;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ContractProvider;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;

/* JADX INFO: compiled from: ContractDeserializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eJ:\u0010\u0002\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/ContractDeserializer;", "", "deserializeContractFromFunction", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "Lorg/jetbrains/kotlin/descriptors/ContractProvider;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "ownerFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "typeDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/TypeDeserializer;", "Companion", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ContractDeserializer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    Pair<CallableDescriptor.UserDataKey<?>, ContractProvider> deserializeContractFromFunction(ProtoBuf.Function proto, FunctionDescriptor ownerFunction, TypeTable typeTable, TypeDeserializer typeDeserializer);

    /* JADX INFO: compiled from: ContractDeserializer.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/ContractDeserializer$Companion;", "", "()V", "DEFAULT", "Lorg/jetbrains/kotlin/serialization/deserialization/ContractDeserializer;", "getDEFAULT", "()Lorg/jetbrains/kotlin/serialization/deserialization/ContractDeserializer;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final ContractDeserializer DEFAULT = new ContractDeserializer() { // from class: org.jetbrains.kotlin.serialization.deserialization.ContractDeserializer$Companion$DEFAULT$1
            @Override // org.jetbrains.kotlin.serialization.deserialization.ContractDeserializer
            public Pair deserializeContractFromFunction(ProtoBuf.Function proto, FunctionDescriptor ownerFunction, TypeTable typeTable, TypeDeserializer typeDeserializer) {
                Intrinsics.checkNotNullParameter(proto, "proto");
                Intrinsics.checkNotNullParameter(ownerFunction, "ownerFunction");
                Intrinsics.checkNotNullParameter(typeTable, "typeTable");
                Intrinsics.checkNotNullParameter(typeDeserializer, "typeDeserializer");
                return null;
            }
        };

        private Companion() {
        }

        public final ContractDeserializer getDEFAULT() {
            return DEFAULT;
        }
    }
}
