package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IdSignatureOrBuilder extends MessageLiteOrBuilder {
    AccessorIdSignature getAccessorSig();

    CompositeSignature getCompositeSig();

    FileSignature getFileSig();

    LocalSignature getLocalSig();

    FileLocalIdSignature getPrivateSig();

    CommonIdSignature getPublicSig();

    int getScopedLocalSig();

    boolean hasAccessorSig();

    boolean hasCompositeSig();

    boolean hasFileSig();

    boolean hasLocalSig();

    boolean hasPrivateSig();

    boolean hasPublicSig();

    boolean hasScopedLocalSig();
}
