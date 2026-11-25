package org.jetbrains.kotlin.protobuf;

/* JADX INFO: loaded from: classes4.dex */
public interface ProtocolMessageEnum extends Internal.EnumLite {
    Descriptors.EnumDescriptor getDescriptorForType();

    @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLite
    int getNumber();

    Descriptors.EnumValueDescriptor getValueDescriptor();
}
