package org.jetbrains.kotlin.serialization.deserialization.descriptors;

import java.io.ByteArrayInputStream;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.protobuf.Parser;

/* JADX INFO: Add missing generic type declarations: [M] */
/* JADX INFO: compiled from: DeserializedMemberScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002\"\b\b\u0001\u0010\u0003*\u00020\u0004H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "M", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "D", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "invoke", "()Lorg/jetbrains/kotlin/protobuf/MessageLite;"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class DeserializedMemberScope$OptimizedImplementation$computeDescriptors$1$1<M> extends Lambda implements Function0<M> {
    final /* synthetic */ ByteArrayInputStream $inputStream;
    final /* synthetic */ Parser<M> $parser;
    final /* synthetic */ DeserializedMemberScope this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeserializedMemberScope$OptimizedImplementation$computeDescriptors$1$1(Parser<M> parser, ByteArrayInputStream byteArrayInputStream, DeserializedMemberScope deserializedMemberScope) {
        super(0);
        this.$parser = parser;
        this.$inputStream = byteArrayInputStream;
        this.this$0 = deserializedMemberScope;
    }

    /* JADX WARN: Incorrect return type in method signature: ()TM; */
    @Override // kotlin.jvm.functions.Function0
    public final MessageLite invoke() {
        return (MessageLite) this.$parser.parseDelimitedFrom(this.$inputStream, this.this$0.getC().getComponents().getExtensionRegistryLite());
    }
}
