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
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.protobuf.Parser;

/* JADX INFO: loaded from: classes2.dex */
public final class AccessorIdSignature extends GeneratedMessageLite implements AccessorIdSignatureOrBuilder {
    public static final int ACCESSOR_HASH_ID_FIELD_NUMBER = 3;
    public static final int DEBUG_INFO_FIELD_NUMBER = 5;
    public static final int FLAGS_FIELD_NUMBER = 4;
    public static final int NAME_FIELD_NUMBER = 2;
    public static Parser<AccessorIdSignature> PARSER = new AbstractParser<AccessorIdSignature>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignature.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public AccessorIdSignature parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new AccessorIdSignature(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int PROPERTY_SIGNATURE_FIELD_NUMBER = 1;
    private static final AccessorIdSignature defaultInstance;
    private static final long serialVersionUID = 0;
    private long accessorHashId_;
    private int bitField0_;
    private int debugInfo_;
    private long flags_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int name_;
    private int propertySignature_;
    private final ByteString unknownFields;

    private AccessorIdSignature(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private AccessorIdSignature(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static AccessorIdSignature getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public AccessorIdSignature getDefaultInstanceForType() {
        return defaultInstance;
    }

    private AccessorIdSignature(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        initFields();
        ByteString.Output outputNewOutput = ByteString.newOutput();
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
        boolean z = false;
        while (!z) {
            try {
                try {
                    int tag = codedInputStream.readTag();
                    if (tag != 0) {
                        if (tag == 8) {
                            this.bitField0_ |= 1;
                            this.propertySignature_ = codedInputStream.readInt32();
                        } else if (tag == 16) {
                            this.bitField0_ |= 2;
                            this.name_ = codedInputStream.readInt32();
                        } else if (tag == 24) {
                            this.bitField0_ |= 4;
                            this.accessorHashId_ = codedInputStream.readInt64();
                        } else if (tag == 32) {
                            this.bitField0_ |= 8;
                            this.flags_ = codedInputStream.readInt64();
                        } else if (tag != 40) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            this.bitField0_ |= 16;
                            this.debugInfo_ = codedInputStream.readInt32();
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
        AccessorIdSignature accessorIdSignature = new AccessorIdSignature(true);
        defaultInstance = accessorIdSignature;
        accessorIdSignature.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<AccessorIdSignature> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
    public boolean hasPropertySignature() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
    public int getPropertySignature() {
        return this.propertySignature_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
    public boolean hasName() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
    public int getName() {
        return this.name_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
    public boolean hasAccessorHashId() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
    public long getAccessorHashId() {
        return this.accessorHashId_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
    public boolean hasFlags() {
        return (this.bitField0_ & 8) == 8;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
    public long getFlags() {
        return this.flags_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
    public boolean hasDebugInfo() {
        return (this.bitField0_ & 16) == 16;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
    public int getDebugInfo() {
        return this.debugInfo_;
    }

    private void initFields() {
        this.propertySignature_ = 0;
        this.name_ = 0;
        this.accessorHashId_ = 0L;
        this.flags_ = 0L;
        this.debugInfo_ = 0;
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
        if (!hasPropertySignature()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasName()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasAccessorHashId()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        this.memoizedIsInitialized = (byte) 1;
        return true;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        getSerializedSize();
        if ((this.bitField0_ & 1) == 1) {
            codedOutputStream.writeInt32(1, this.propertySignature_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt32(2, this.name_);
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeInt64(3, this.accessorHashId_);
        }
        if ((this.bitField0_ & 8) == 8) {
            codedOutputStream.writeInt64(4, this.flags_);
        }
        if ((this.bitField0_ & 16) == 16) {
            codedOutputStream.writeInt32(5, this.debugInfo_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.propertySignature_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeInt32Size += CodedOutputStream.computeInt32Size(2, this.name_);
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeInt32Size += CodedOutputStream.computeInt64Size(3, this.accessorHashId_);
        }
        if ((this.bitField0_ & 8) == 8) {
            iComputeInt32Size += CodedOutputStream.computeInt64Size(4, this.flags_);
        }
        if ((this.bitField0_ & 16) == 16) {
            iComputeInt32Size += CodedOutputStream.computeInt32Size(5, this.debugInfo_);
        }
        int size = iComputeInt32Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static AccessorIdSignature parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static AccessorIdSignature parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static AccessorIdSignature parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static AccessorIdSignature parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static AccessorIdSignature parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static AccessorIdSignature parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static AccessorIdSignature parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static AccessorIdSignature parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static AccessorIdSignature parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static AccessorIdSignature parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(AccessorIdSignature accessorIdSignature) {
        return newBuilder().mergeFrom(accessorIdSignature);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<AccessorIdSignature, Builder> implements AccessorIdSignatureOrBuilder {
        private long accessorHashId_;
        private int bitField0_;
        private int debugInfo_;
        private long flags_;
        private int name_;
        private int propertySignature_;

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
            this.propertySignature_ = 0;
            int i = this.bitField0_;
            this.name_ = 0;
            this.accessorHashId_ = 0L;
            this.flags_ = 0L;
            this.debugInfo_ = 0;
            this.bitField0_ = i & (-32);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public AccessorIdSignature getDefaultInstanceForType() {
            return AccessorIdSignature.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public AccessorIdSignature build() {
            AccessorIdSignature accessorIdSignatureBuildPartial = buildPartial();
            if (accessorIdSignatureBuildPartial.isInitialized()) {
                return accessorIdSignatureBuildPartial;
            }
            throw newUninitializedMessageException(accessorIdSignatureBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public AccessorIdSignature buildPartial() {
            AccessorIdSignature accessorIdSignature = new AccessorIdSignature(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            accessorIdSignature.propertySignature_ = this.propertySignature_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            accessorIdSignature.name_ = this.name_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            accessorIdSignature.accessorHashId_ = this.accessorHashId_;
            if ((i & 8) == 8) {
                i2 |= 8;
            }
            accessorIdSignature.flags_ = this.flags_;
            if ((i & 16) == 16) {
                i2 |= 16;
            }
            accessorIdSignature.debugInfo_ = this.debugInfo_;
            accessorIdSignature.bitField0_ = i2;
            return accessorIdSignature;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(AccessorIdSignature accessorIdSignature) {
            if (accessorIdSignature == AccessorIdSignature.getDefaultInstance()) {
                return this;
            }
            if (accessorIdSignature.hasPropertySignature()) {
                setPropertySignature(accessorIdSignature.getPropertySignature());
            }
            if (accessorIdSignature.hasName()) {
                setName(accessorIdSignature.getName());
            }
            if (accessorIdSignature.hasAccessorHashId()) {
                setAccessorHashId(accessorIdSignature.getAccessorHashId());
            }
            if (accessorIdSignature.hasFlags()) {
                setFlags(accessorIdSignature.getFlags());
            }
            if (accessorIdSignature.hasDebugInfo()) {
                setDebugInfo(accessorIdSignature.getDebugInfo());
            }
            setUnknownFields(getUnknownFields().concat(accessorIdSignature.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasPropertySignature() && hasName() && hasAccessorHashId();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            AccessorIdSignature accessorIdSignature = null;
            try {
                try {
                    AccessorIdSignature partialFrom = AccessorIdSignature.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    AccessorIdSignature accessorIdSignature2 = (AccessorIdSignature) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        accessorIdSignature = accessorIdSignature2;
                        if (accessorIdSignature != null) {
                            mergeFrom(accessorIdSignature);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (accessorIdSignature != null) {
                    mergeFrom(accessorIdSignature);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
        public boolean hasPropertySignature() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
        public int getPropertySignature() {
            return this.propertySignature_;
        }

        public Builder setPropertySignature(int i) {
            this.bitField0_ |= 1;
            this.propertySignature_ = i;
            return this;
        }

        public Builder clearPropertySignature() {
            this.bitField0_ &= -2;
            this.propertySignature_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
        public int getName() {
            return this.name_;
        }

        public Builder setName(int i) {
            this.bitField0_ |= 2;
            this.name_ = i;
            return this;
        }

        public Builder clearName() {
            this.bitField0_ &= -3;
            this.name_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
        public boolean hasAccessorHashId() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
        public long getAccessorHashId() {
            return this.accessorHashId_;
        }

        public Builder setAccessorHashId(long j) {
            this.bitField0_ |= 4;
            this.accessorHashId_ = j;
            return this;
        }

        public Builder clearAccessorHashId() {
            this.bitField0_ &= -5;
            this.accessorHashId_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
        public boolean hasFlags() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
        public long getFlags() {
            return this.flags_;
        }

        public Builder setFlags(long j) {
            this.bitField0_ |= 8;
            this.flags_ = j;
            return this;
        }

        public Builder clearFlags() {
            this.bitField0_ &= -9;
            this.flags_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
        public boolean hasDebugInfo() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignatureOrBuilder
        public int getDebugInfo() {
            return this.debugInfo_;
        }

        public Builder setDebugInfo(int i) {
            this.bitField0_ |= 16;
            this.debugInfo_ = i;
            return this;
        }

        public Builder clearDebugInfo() {
            this.bitField0_ &= -17;
            this.debugInfo_ = 0;
            return this;
        }
    }
}
