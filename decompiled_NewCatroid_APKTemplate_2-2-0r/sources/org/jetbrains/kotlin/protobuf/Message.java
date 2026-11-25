package org.jetbrains.kotlin.protobuf;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes4.dex */
public interface Message extends MessageLite, MessageOrBuilder {

    public interface Builder extends MessageLite.Builder, MessageOrBuilder {
        Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj);

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        Message build();

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        Message buildPartial();

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        Builder clear();

        Builder clearField(Descriptors.FieldDescriptor fieldDescriptor);

        Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor);

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        Builder clone();

        @Override // org.jetbrains.kotlin.protobuf.MessageOrBuilder
        Descriptors.Descriptor getDescriptorForType();

        Builder getFieldBuilder(Descriptors.FieldDescriptor fieldDescriptor);

        boolean mergeDelimitedFrom(InputStream inputStream) throws IOException;

        boolean mergeDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException;

        Builder mergeFrom(InputStream inputStream) throws IOException;

        Builder mergeFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException;

        Builder mergeFrom(ByteString byteString) throws InvalidProtocolBufferException;

        Builder mergeFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException;

        Builder mergeFrom(CodedInputStream codedInputStream) throws IOException;

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException;

        Builder mergeFrom(Message message);

        Builder mergeFrom(byte[] bArr) throws InvalidProtocolBufferException;

        Builder mergeFrom(byte[] bArr, int i, int i2) throws InvalidProtocolBufferException;

        Builder mergeFrom(byte[] bArr, int i, int i2, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException;

        Builder mergeFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException;

        Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet);

        Builder newBuilderForField(Descriptors.FieldDescriptor fieldDescriptor);

        Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj);

        Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i, Object obj);

        Builder setUnknownFields(UnknownFieldSet unknownFieldSet);
    }

    boolean equals(Object obj);

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    Parser<? extends Message> getParserForType();

    int hashCode();

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    Builder newBuilderForType();

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    Builder toBuilder();

    String toString();
}
