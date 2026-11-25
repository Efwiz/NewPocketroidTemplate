package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import org.jetbrains.kotlin.protobuf.AbstractParser;
import org.jetbrains.kotlin.protobuf.ByteString;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.CodedOutputStream;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.protobuf.Internal;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.protobuf.Parser;

/* JADX INFO: loaded from: classes2.dex */
public final class IdSignature extends GeneratedMessageLite implements IdSignatureOrBuilder {
    public static final int ACCESSOR_SIG_FIELD_NUMBER = 3;
    public static final int COMPOSITE_SIG_FIELD_NUMBER = 5;
    public static final int FILE_SIG_FIELD_NUMBER = 7;
    public static final int LOCAL_SIG_FIELD_NUMBER = 6;
    public static Parser<IdSignature> PARSER = new AbstractParser<IdSignature>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IdSignature parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IdSignature(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int PRIVATE_SIG_FIELD_NUMBER = 2;
    public static final int PUBLIC_SIG_FIELD_NUMBER = 1;
    public static final int SCOPED_LOCAL_SIG_FIELD_NUMBER = 4;
    private static final IdSignature defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private int idSigCase_;
    private Object idSig_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    private void initFields() {
    }

    private IdSignature(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.idSigCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IdSignature(boolean z) {
        this.idSigCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IdSignature getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IdSignature getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IdSignature(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        GeneratedMessageLite.Builder builder;
        boolean z = false;
        this.idSigCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        initFields();
        ByteString.Output outputNewOutput = ByteString.newOutput();
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
        while (!z) {
            try {
                try {
                    int tag = codedInputStream.readTag();
                    if (tag != 0) {
                        if (tag == 10) {
                            builder = this.idSigCase_ == 1 ? ((CommonIdSignature) this.idSig_).toBuilder() : null;
                            MessageLite message = codedInputStream.readMessage(CommonIdSignature.PARSER, extensionRegistryLite);
                            this.idSig_ = message;
                            if (builder != null) {
                                builder.mergeFrom((CommonIdSignature) message);
                                this.idSig_ = builder.buildPartial();
                            }
                            this.idSigCase_ = 1;
                        } else if (tag == 18) {
                            builder = this.idSigCase_ == 2 ? ((FileLocalIdSignature) this.idSig_).toBuilder() : null;
                            MessageLite message2 = codedInputStream.readMessage(FileLocalIdSignature.PARSER, extensionRegistryLite);
                            this.idSig_ = message2;
                            if (builder != null) {
                                builder.mergeFrom((FileLocalIdSignature) message2);
                                this.idSig_ = builder.buildPartial();
                            }
                            this.idSigCase_ = 2;
                        } else if (tag == 26) {
                            builder = this.idSigCase_ == 3 ? ((AccessorIdSignature) this.idSig_).toBuilder() : null;
                            MessageLite message3 = codedInputStream.readMessage(AccessorIdSignature.PARSER, extensionRegistryLite);
                            this.idSig_ = message3;
                            if (builder != null) {
                                builder.mergeFrom((AccessorIdSignature) message3);
                                this.idSig_ = builder.buildPartial();
                            }
                            this.idSigCase_ = 3;
                        } else if (tag == 32) {
                            this.idSigCase_ = 4;
                            this.idSig_ = Integer.valueOf(codedInputStream.readInt32());
                        } else if (tag == 42) {
                            builder = this.idSigCase_ == 5 ? ((CompositeSignature) this.idSig_).toBuilder() : null;
                            MessageLite message4 = codedInputStream.readMessage(CompositeSignature.PARSER, extensionRegistryLite);
                            this.idSig_ = message4;
                            if (builder != null) {
                                builder.mergeFrom((CompositeSignature) message4);
                                this.idSig_ = builder.buildPartial();
                            }
                            this.idSigCase_ = 5;
                        } else if (tag == 50) {
                            builder = this.idSigCase_ == 6 ? ((LocalSignature) this.idSig_).toBuilder() : null;
                            MessageLite message5 = codedInputStream.readMessage(LocalSignature.PARSER, extensionRegistryLite);
                            this.idSig_ = message5;
                            if (builder != null) {
                                builder.mergeFrom((LocalSignature) message5);
                                this.idSig_ = builder.buildPartial();
                            }
                            this.idSigCase_ = 6;
                        } else if (tag != 58) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            builder = this.idSigCase_ == 7 ? ((FileSignature) this.idSig_).toBuilder() : null;
                            MessageLite message6 = codedInputStream.readMessage(FileSignature.PARSER, extensionRegistryLite);
                            this.idSig_ = message6;
                            if (builder != null) {
                                builder.mergeFrom((FileSignature) message6);
                                this.idSig_ = builder.buildPartial();
                            }
                            this.idSigCase_ = 7;
                        }
                    }
                    z = true;
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                }
            } catch (Throwable th) {
                try {
                    codedOutputStreamNewInstance.flush();
                } catch (IOException unused) {
                } finally {
                    this.unknownFields = outputNewOutput.toByteString();
                }
                makeExtensionsImmutable();
                throw th;
            }
        }
        try {
            codedOutputStreamNewInstance.flush();
        } catch (IOException unused2) {
        } finally {
            this.unknownFields = outputNewOutput.toByteString();
        }
        makeExtensionsImmutable();
    }

    static {
        IdSignature idSignature = new IdSignature(true);
        defaultInstance = idSignature;
        idSignature.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IdSignature> getParserForType() {
        return PARSER;
    }

    public enum IdSigCase implements Internal.EnumLite {
        PUBLIC_SIG(1),
        PRIVATE_SIG(2),
        ACCESSOR_SIG(3),
        SCOPED_LOCAL_SIG(4),
        COMPOSITE_SIG(5),
        LOCAL_SIG(6),
        FILE_SIG(7),
        IDSIG_NOT_SET(0);

        private int value;

        IdSigCase(int i) {
            this.value = i;
        }

        public static IdSigCase valueOf(int i) {
            switch (i) {
                case 0:
                    return IDSIG_NOT_SET;
                case 1:
                    return PUBLIC_SIG;
                case 2:
                    return PRIVATE_SIG;
                case 3:
                    return ACCESSOR_SIG;
                case 4:
                    return SCOPED_LOCAL_SIG;
                case 5:
                    return COMPOSITE_SIG;
                case 6:
                    return LOCAL_SIG;
                case 7:
                    return FILE_SIG;
                default:
                    throw new IllegalArgumentException("Value is undefined for this oneof enum.");
            }
        }

        @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLite
        public int getNumber() {
            return this.value;
        }
    }

    public IdSigCase getIdSigCase() {
        return IdSigCase.valueOf(this.idSigCase_);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
    public boolean hasPublicSig() {
        return this.idSigCase_ == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
    public CommonIdSignature getPublicSig() {
        if (this.idSigCase_ == 1) {
            return (CommonIdSignature) this.idSig_;
        }
        return CommonIdSignature.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
    public boolean hasPrivateSig() {
        return this.idSigCase_ == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
    public FileLocalIdSignature getPrivateSig() {
        if (this.idSigCase_ == 2) {
            return (FileLocalIdSignature) this.idSig_;
        }
        return FileLocalIdSignature.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
    public boolean hasAccessorSig() {
        return this.idSigCase_ == 3;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
    public AccessorIdSignature getAccessorSig() {
        if (this.idSigCase_ == 3) {
            return (AccessorIdSignature) this.idSig_;
        }
        return AccessorIdSignature.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
    public boolean hasScopedLocalSig() {
        return this.idSigCase_ == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
    public int getScopedLocalSig() {
        if (this.idSigCase_ == 4) {
            return ((Integer) this.idSig_).intValue();
        }
        return 0;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
    public boolean hasCompositeSig() {
        return this.idSigCase_ == 5;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
    public CompositeSignature getCompositeSig() {
        if (this.idSigCase_ == 5) {
            return (CompositeSignature) this.idSig_;
        }
        return CompositeSignature.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
    public boolean hasLocalSig() {
        return this.idSigCase_ == 6;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
    public LocalSignature getLocalSig() {
        if (this.idSigCase_ == 6) {
            return (LocalSignature) this.idSig_;
        }
        return LocalSignature.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
    public boolean hasFileSig() {
        return this.idSigCase_ == 7;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
    public FileSignature getFileSig() {
        if (this.idSigCase_ == 7) {
            return (FileSignature) this.idSig_;
        }
        return FileSignature.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public final boolean isInitialized() {
        byte b = this.memoizedIsInitialized;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        if (hasPrivateSig() && !getPrivateSig().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasAccessorSig() && !getAccessorSig().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasCompositeSig() && !getCompositeSig().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        this.memoizedIsInitialized = (byte) 1;
        return true;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        getSerializedSize();
        if (this.idSigCase_ == 1) {
            codedOutputStream.writeMessage(1, (CommonIdSignature) this.idSig_);
        }
        if (this.idSigCase_ == 2) {
            codedOutputStream.writeMessage(2, (FileLocalIdSignature) this.idSig_);
        }
        if (this.idSigCase_ == 3) {
            codedOutputStream.writeMessage(3, (AccessorIdSignature) this.idSig_);
        }
        if (this.idSigCase_ == 4) {
            codedOutputStream.writeInt32(4, ((Integer) this.idSig_).intValue());
        }
        if (this.idSigCase_ == 5) {
            codedOutputStream.writeMessage(5, (CompositeSignature) this.idSig_);
        }
        if (this.idSigCase_ == 6) {
            codedOutputStream.writeMessage(6, (LocalSignature) this.idSig_);
        }
        if (this.idSigCase_ == 7) {
            codedOutputStream.writeMessage(7, (FileSignature) this.idSig_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = this.idSigCase_ == 1 ? CodedOutputStream.computeMessageSize(1, (CommonIdSignature) this.idSig_) : 0;
        if (this.idSigCase_ == 2) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(2, (FileLocalIdSignature) this.idSig_);
        }
        if (this.idSigCase_ == 3) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(3, (AccessorIdSignature) this.idSig_);
        }
        if (this.idSigCase_ == 4) {
            iComputeMessageSize += CodedOutputStream.computeInt32Size(4, ((Integer) this.idSig_).intValue());
        }
        if (this.idSigCase_ == 5) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(5, (CompositeSignature) this.idSig_);
        }
        if (this.idSigCase_ == 6) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(6, (LocalSignature) this.idSig_);
        }
        if (this.idSigCase_ == 7) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(7, (FileSignature) this.idSig_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IdSignature parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IdSignature parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IdSignature parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IdSignature parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IdSignature parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IdSignature parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IdSignature parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IdSignature parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IdSignature parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IdSignature parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IdSignature idSignature) {
        return newBuilder().mergeFrom(idSignature);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IdSignature, Builder> implements IdSignatureOrBuilder {
        private int bitField0_;
        private int idSigCase_ = 0;
        private Object idSig_;

        private void maybeForceBuilderInitialization() {
        }

        private Builder() {
            maybeForceBuilderInitialization();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Builder create() {
            return new Builder();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clear() {
            super.clear();
            this.idSigCase_ = 0;
            this.idSig_ = null;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IdSignature getDefaultInstanceForType() {
            return IdSignature.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IdSignature build() {
            IdSignature idSignatureBuildPartial = buildPartial();
            if (idSignatureBuildPartial.isInitialized()) {
                return idSignatureBuildPartial;
            }
            throw newUninitializedMessageException(idSignatureBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IdSignature buildPartial() {
            IdSignature idSignature = new IdSignature(this);
            if (this.idSigCase_ == 1) {
                idSignature.idSig_ = this.idSig_;
            }
            if (this.idSigCase_ == 2) {
                idSignature.idSig_ = this.idSig_;
            }
            if (this.idSigCase_ == 3) {
                idSignature.idSig_ = this.idSig_;
            }
            if (this.idSigCase_ == 4) {
                idSignature.idSig_ = this.idSig_;
            }
            if (this.idSigCase_ == 5) {
                idSignature.idSig_ = this.idSig_;
            }
            if (this.idSigCase_ == 6) {
                idSignature.idSig_ = this.idSig_;
            }
            if (this.idSigCase_ == 7) {
                idSignature.idSig_ = this.idSig_;
            }
            idSignature.bitField0_ = 0;
            idSignature.idSigCase_ = this.idSigCase_;
            return idSignature;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IdSignature idSignature) {
            if (idSignature == IdSignature.getDefaultInstance()) {
                return this;
            }
            switch (AnonymousClass2.$SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IdSignature$IdSigCase[idSignature.getIdSigCase().ordinal()]) {
                case 1:
                    mergePublicSig(idSignature.getPublicSig());
                    break;
                case 2:
                    mergePrivateSig(idSignature.getPrivateSig());
                    break;
                case 3:
                    mergeAccessorSig(idSignature.getAccessorSig());
                    break;
                case 4:
                    setScopedLocalSig(idSignature.getScopedLocalSig());
                    break;
                case 5:
                    mergeCompositeSig(idSignature.getCompositeSig());
                    break;
                case 6:
                    mergeLocalSig(idSignature.getLocalSig());
                    break;
                case 7:
                    mergeFileSig(idSignature.getFileSig());
                    break;
            }
            setUnknownFields(getUnknownFields().concat(idSignature.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (hasPrivateSig() && !getPrivateSig().isInitialized()) {
                return false;
            }
            if (!hasAccessorSig() || getAccessorSig().isInitialized()) {
                return !hasCompositeSig() || getCompositeSig().isInitialized();
            }
            return false;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IdSignature idSignature = null;
            try {
                try {
                    IdSignature partialFrom = IdSignature.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IdSignature idSignature2 = (IdSignature) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        idSignature = idSignature2;
                        if (idSignature != null) {
                            mergeFrom(idSignature);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (idSignature != null) {
                    mergeFrom(idSignature);
                }
                throw th;
            }
        }

        public IdSigCase getIdSigCase() {
            return IdSigCase.valueOf(this.idSigCase_);
        }

        public Builder clearIdSig() {
            this.idSigCase_ = 0;
            this.idSig_ = null;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
        public boolean hasPublicSig() {
            return this.idSigCase_ == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
        public CommonIdSignature getPublicSig() {
            if (this.idSigCase_ == 1) {
                return (CommonIdSignature) this.idSig_;
            }
            return CommonIdSignature.getDefaultInstance();
        }

        public Builder setPublicSig(CommonIdSignature commonIdSignature) {
            commonIdSignature.getClass();
            this.idSig_ = commonIdSignature;
            this.idSigCase_ = 1;
            return this;
        }

        public Builder setPublicSig(CommonIdSignature.Builder builder) {
            this.idSig_ = builder.build();
            this.idSigCase_ = 1;
            return this;
        }

        public Builder mergePublicSig(CommonIdSignature commonIdSignature) {
            if (this.idSigCase_ == 1 && this.idSig_ != CommonIdSignature.getDefaultInstance()) {
                this.idSig_ = CommonIdSignature.newBuilder((CommonIdSignature) this.idSig_).mergeFrom(commonIdSignature).buildPartial();
            } else {
                this.idSig_ = commonIdSignature;
            }
            this.idSigCase_ = 1;
            return this;
        }

        public Builder clearPublicSig() {
            if (this.idSigCase_ == 1) {
                this.idSigCase_ = 0;
                this.idSig_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
        public boolean hasPrivateSig() {
            return this.idSigCase_ == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
        public FileLocalIdSignature getPrivateSig() {
            if (this.idSigCase_ == 2) {
                return (FileLocalIdSignature) this.idSig_;
            }
            return FileLocalIdSignature.getDefaultInstance();
        }

        public Builder setPrivateSig(FileLocalIdSignature fileLocalIdSignature) {
            fileLocalIdSignature.getClass();
            this.idSig_ = fileLocalIdSignature;
            this.idSigCase_ = 2;
            return this;
        }

        public Builder setPrivateSig(FileLocalIdSignature.Builder builder) {
            this.idSig_ = builder.build();
            this.idSigCase_ = 2;
            return this;
        }

        public Builder mergePrivateSig(FileLocalIdSignature fileLocalIdSignature) {
            if (this.idSigCase_ == 2 && this.idSig_ != FileLocalIdSignature.getDefaultInstance()) {
                this.idSig_ = FileLocalIdSignature.newBuilder((FileLocalIdSignature) this.idSig_).mergeFrom(fileLocalIdSignature).buildPartial();
            } else {
                this.idSig_ = fileLocalIdSignature;
            }
            this.idSigCase_ = 2;
            return this;
        }

        public Builder clearPrivateSig() {
            if (this.idSigCase_ == 2) {
                this.idSigCase_ = 0;
                this.idSig_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
        public boolean hasAccessorSig() {
            return this.idSigCase_ == 3;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
        public AccessorIdSignature getAccessorSig() {
            if (this.idSigCase_ == 3) {
                return (AccessorIdSignature) this.idSig_;
            }
            return AccessorIdSignature.getDefaultInstance();
        }

        public Builder setAccessorSig(AccessorIdSignature accessorIdSignature) {
            accessorIdSignature.getClass();
            this.idSig_ = accessorIdSignature;
            this.idSigCase_ = 3;
            return this;
        }

        public Builder setAccessorSig(AccessorIdSignature.Builder builder) {
            this.idSig_ = builder.build();
            this.idSigCase_ = 3;
            return this;
        }

        public Builder mergeAccessorSig(AccessorIdSignature accessorIdSignature) {
            if (this.idSigCase_ == 3 && this.idSig_ != AccessorIdSignature.getDefaultInstance()) {
                this.idSig_ = AccessorIdSignature.newBuilder((AccessorIdSignature) this.idSig_).mergeFrom(accessorIdSignature).buildPartial();
            } else {
                this.idSig_ = accessorIdSignature;
            }
            this.idSigCase_ = 3;
            return this;
        }

        public Builder clearAccessorSig() {
            if (this.idSigCase_ == 3) {
                this.idSigCase_ = 0;
                this.idSig_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
        public boolean hasScopedLocalSig() {
            return this.idSigCase_ == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
        public int getScopedLocalSig() {
            if (this.idSigCase_ == 4) {
                return ((Integer) this.idSig_).intValue();
            }
            return 0;
        }

        public Builder setScopedLocalSig(int i) {
            this.idSigCase_ = 4;
            this.idSig_ = Integer.valueOf(i);
            return this;
        }

        public Builder clearScopedLocalSig() {
            if (this.idSigCase_ == 4) {
                this.idSigCase_ = 0;
                this.idSig_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
        public boolean hasCompositeSig() {
            return this.idSigCase_ == 5;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
        public CompositeSignature getCompositeSig() {
            if (this.idSigCase_ == 5) {
                return (CompositeSignature) this.idSig_;
            }
            return CompositeSignature.getDefaultInstance();
        }

        public Builder setCompositeSig(CompositeSignature compositeSignature) {
            compositeSignature.getClass();
            this.idSig_ = compositeSignature;
            this.idSigCase_ = 5;
            return this;
        }

        public Builder setCompositeSig(CompositeSignature.Builder builder) {
            this.idSig_ = builder.build();
            this.idSigCase_ = 5;
            return this;
        }

        public Builder mergeCompositeSig(CompositeSignature compositeSignature) {
            if (this.idSigCase_ == 5 && this.idSig_ != CompositeSignature.getDefaultInstance()) {
                this.idSig_ = CompositeSignature.newBuilder((CompositeSignature) this.idSig_).mergeFrom(compositeSignature).buildPartial();
            } else {
                this.idSig_ = compositeSignature;
            }
            this.idSigCase_ = 5;
            return this;
        }

        public Builder clearCompositeSig() {
            if (this.idSigCase_ == 5) {
                this.idSigCase_ = 0;
                this.idSig_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
        public boolean hasLocalSig() {
            return this.idSigCase_ == 6;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
        public LocalSignature getLocalSig() {
            if (this.idSigCase_ == 6) {
                return (LocalSignature) this.idSig_;
            }
            return LocalSignature.getDefaultInstance();
        }

        public Builder setLocalSig(LocalSignature localSignature) {
            localSignature.getClass();
            this.idSig_ = localSignature;
            this.idSigCase_ = 6;
            return this;
        }

        public Builder setLocalSig(LocalSignature.Builder builder) {
            this.idSig_ = builder.build();
            this.idSigCase_ = 6;
            return this;
        }

        public Builder mergeLocalSig(LocalSignature localSignature) {
            if (this.idSigCase_ == 6 && this.idSig_ != LocalSignature.getDefaultInstance()) {
                this.idSig_ = LocalSignature.newBuilder((LocalSignature) this.idSig_).mergeFrom(localSignature).buildPartial();
            } else {
                this.idSig_ = localSignature;
            }
            this.idSigCase_ = 6;
            return this;
        }

        public Builder clearLocalSig() {
            if (this.idSigCase_ == 6) {
                this.idSigCase_ = 0;
                this.idSig_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
        public boolean hasFileSig() {
            return this.idSigCase_ == 7;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder
        public FileSignature getFileSig() {
            if (this.idSigCase_ == 7) {
                return (FileSignature) this.idSig_;
            }
            return FileSignature.getDefaultInstance();
        }

        public Builder setFileSig(FileSignature fileSignature) {
            fileSignature.getClass();
            this.idSig_ = fileSignature;
            this.idSigCase_ = 7;
            return this;
        }

        public Builder setFileSig(FileSignature.Builder builder) {
            this.idSig_ = builder.build();
            this.idSigCase_ = 7;
            return this;
        }

        public Builder mergeFileSig(FileSignature fileSignature) {
            if (this.idSigCase_ == 7 && this.idSig_ != FileSignature.getDefaultInstance()) {
                this.idSig_ = FileSignature.newBuilder((FileSignature) this.idSig_).mergeFrom(fileSignature).buildPartial();
            } else {
                this.idSig_ = fileSignature;
            }
            this.idSigCase_ = 7;
            return this;
        }

        public Builder clearFileSig() {
            if (this.idSigCase_ == 7) {
                this.idSigCase_ = 0;
                this.idSig_ = null;
            }
            return this;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IdSignature$IdSigCase;

        static {
            int[] iArr = new int[IdSigCase.values().length];
            $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IdSignature$IdSigCase = iArr;
            try {
                iArr[IdSigCase.PUBLIC_SIG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IdSignature$IdSigCase[IdSigCase.PRIVATE_SIG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IdSignature$IdSigCase[IdSigCase.ACCESSOR_SIG.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IdSignature$IdSigCase[IdSigCase.SCOPED_LOCAL_SIG.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IdSignature$IdSigCase[IdSigCase.COMPOSITE_SIG.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IdSignature$IdSigCase[IdSigCase.LOCAL_SIG.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IdSignature$IdSigCase[IdSigCase.FILE_SIG.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IdSignature$IdSigCase[IdSigCase.IDSIG_NOT_SET.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }
}
