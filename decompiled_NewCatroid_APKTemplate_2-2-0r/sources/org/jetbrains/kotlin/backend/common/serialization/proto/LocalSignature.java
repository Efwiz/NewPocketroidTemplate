package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.protobuf.AbstractMessageLite;
import org.jetbrains.kotlin.protobuf.AbstractParser;
import org.jetbrains.kotlin.protobuf.ByteString;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.CodedOutputStream;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.protobuf.Parser;

/* JADX INFO: loaded from: classes2.dex */
public final class LocalSignature extends GeneratedMessageLite implements LocalSignatureOrBuilder {
    public static final int DEBUG_INFO_FIELD_NUMBER = 3;
    public static final int LOCAL_FQ_NAME_FIELD_NUMBER = 1;
    public static final int LOCAL_HASH_FIELD_NUMBER = 2;
    public static Parser<LocalSignature> PARSER = new AbstractParser<LocalSignature>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignature.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public LocalSignature parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new LocalSignature(codedInputStream, extensionRegistryLite);
        }
    };
    private static final LocalSignature defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private int debugInfo_;
    private int localFqNameMemoizedSerializedSize;
    private List<Integer> localFqName_;
    private long localHash_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    private LocalSignature(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.localFqNameMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private LocalSignature(boolean z) {
        this.localFqNameMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static LocalSignature getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public LocalSignature getDefaultInstanceForType() {
        return defaultInstance;
    }

    private LocalSignature(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.localFqNameMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        initFields();
        ByteString.Output outputNewOutput = ByteString.newOutput();
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            try {
                try {
                    int tag = codedInputStream.readTag();
                    if (tag != 0) {
                        if (tag == 8) {
                            if (!z2) {
                                this.localFqName_ = new ArrayList();
                                z2 = true;
                            }
                            this.localFqName_.add(Integer.valueOf(codedInputStream.readInt32()));
                        } else if (tag == 10) {
                            int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                            if (!z2 && codedInputStream.getBytesUntilLimit() > 0) {
                                this.localFqName_ = new ArrayList();
                                z2 = true;
                            }
                            while (codedInputStream.getBytesUntilLimit() > 0) {
                                this.localFqName_.add(Integer.valueOf(codedInputStream.readInt32()));
                            }
                            codedInputStream.popLimit(iPushLimit);
                        } else if (tag == 16) {
                            this.bitField0_ |= 1;
                            this.localHash_ = codedInputStream.readInt64();
                        } else if (tag != 24) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            this.bitField0_ |= 2;
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
                if (z2) {
                    this.localFqName_ = Collections.unmodifiableList(this.localFqName_);
                }
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
        if (z2) {
            this.localFqName_ = Collections.unmodifiableList(this.localFqName_);
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
        LocalSignature localSignature = new LocalSignature(true);
        defaultInstance = localSignature;
        localSignature.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<LocalSignature> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignatureOrBuilder
    public List<Integer> getLocalFqNameList() {
        return this.localFqName_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignatureOrBuilder
    public int getLocalFqNameCount() {
        return this.localFqName_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignatureOrBuilder
    public int getLocalFqName(int i) {
        return this.localFqName_.get(i).intValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignatureOrBuilder
    public boolean hasLocalHash() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignatureOrBuilder
    public long getLocalHash() {
        return this.localHash_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignatureOrBuilder
    public boolean hasDebugInfo() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignatureOrBuilder
    public int getDebugInfo() {
        return this.debugInfo_;
    }

    private void initFields() {
        this.localFqName_ = Collections.emptyList();
        this.localHash_ = 0L;
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
        this.memoizedIsInitialized = (byte) 1;
        return true;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        getSerializedSize();
        if (getLocalFqNameList().size() > 0) {
            codedOutputStream.writeRawVarint32(10);
            codedOutputStream.writeRawVarint32(this.localFqNameMemoizedSerializedSize);
        }
        for (int i = 0; i < this.localFqName_.size(); i++) {
            codedOutputStream.writeInt32NoTag(this.localFqName_.get(i).intValue());
        }
        if ((this.bitField0_ & 1) == 1) {
            codedOutputStream.writeInt64(2, this.localHash_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt32(3, this.debugInfo_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt32SizeNoTag = 0;
        for (int i2 = 0; i2 < this.localFqName_.size(); i2++) {
            iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.localFqName_.get(i2).intValue());
        }
        int iComputeInt32SizeNoTag2 = !getLocalFqNameList().isEmpty() ? iComputeInt32SizeNoTag + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag) : iComputeInt32SizeNoTag;
        this.localFqNameMemoizedSerializedSize = iComputeInt32SizeNoTag;
        if ((this.bitField0_ & 1) == 1) {
            iComputeInt32SizeNoTag2 += CodedOutputStream.computeInt64Size(2, this.localHash_);
        }
        if ((this.bitField0_ & 2) == 2) {
            iComputeInt32SizeNoTag2 += CodedOutputStream.computeInt32Size(3, this.debugInfo_);
        }
        int size = iComputeInt32SizeNoTag2 + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static LocalSignature parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static LocalSignature parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static LocalSignature parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static LocalSignature parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static LocalSignature parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static LocalSignature parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static LocalSignature parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static LocalSignature parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static LocalSignature parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static LocalSignature parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(LocalSignature localSignature) {
        return newBuilder().mergeFrom(localSignature);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<LocalSignature, Builder> implements LocalSignatureOrBuilder {
        private int bitField0_;
        private int debugInfo_;
        private List<Integer> localFqName_ = Collections.emptyList();
        private long localHash_;

        private void maybeForceBuilderInitialization() {
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return true;
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
            this.localFqName_ = Collections.emptyList();
            int i = this.bitField0_;
            this.localHash_ = 0L;
            this.debugInfo_ = 0;
            this.bitField0_ = i & (-8);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public LocalSignature getDefaultInstanceForType() {
            return LocalSignature.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public LocalSignature build() {
            LocalSignature localSignatureBuildPartial = buildPartial();
            if (localSignatureBuildPartial.isInitialized()) {
                return localSignatureBuildPartial;
            }
            throw newUninitializedMessageException(localSignatureBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public LocalSignature buildPartial() {
            LocalSignature localSignature = new LocalSignature(this);
            int i = this.bitField0_;
            if ((i & 1) == 1) {
                this.localFqName_ = Collections.unmodifiableList(this.localFqName_);
                this.bitField0_ &= -2;
            }
            localSignature.localFqName_ = this.localFqName_;
            int i2 = (i & 2) != 2 ? 0 : 1;
            localSignature.localHash_ = this.localHash_;
            if ((i & 4) == 4) {
                i2 |= 2;
            }
            localSignature.debugInfo_ = this.debugInfo_;
            localSignature.bitField0_ = i2;
            return localSignature;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(LocalSignature localSignature) {
            if (localSignature == LocalSignature.getDefaultInstance()) {
                return this;
            }
            if (!localSignature.localFqName_.isEmpty()) {
                if (this.localFqName_.isEmpty()) {
                    this.localFqName_ = localSignature.localFqName_;
                    this.bitField0_ &= -2;
                } else {
                    ensureLocalFqNameIsMutable();
                    this.localFqName_.addAll(localSignature.localFqName_);
                }
            }
            if (localSignature.hasLocalHash()) {
                setLocalHash(localSignature.getLocalHash());
            }
            if (localSignature.hasDebugInfo()) {
                setDebugInfo(localSignature.getDebugInfo());
            }
            setUnknownFields(getUnknownFields().concat(localSignature.unknownFields));
            return this;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            LocalSignature localSignature = null;
            try {
                try {
                    LocalSignature partialFrom = LocalSignature.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    LocalSignature localSignature2 = (LocalSignature) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        localSignature = localSignature2;
                        if (localSignature != null) {
                            mergeFrom(localSignature);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (localSignature != null) {
                    mergeFrom(localSignature);
                }
                throw th;
            }
        }

        private void ensureLocalFqNameIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
                this.localFqName_ = new ArrayList(this.localFqName_);
                this.bitField0_ |= 1;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignatureOrBuilder
        public List<Integer> getLocalFqNameList() {
            return Collections.unmodifiableList(this.localFqName_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignatureOrBuilder
        public int getLocalFqNameCount() {
            return this.localFqName_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignatureOrBuilder
        public int getLocalFqName(int i) {
            return this.localFqName_.get(i).intValue();
        }

        public Builder setLocalFqName(int i, int i2) {
            ensureLocalFqNameIsMutable();
            this.localFqName_.set(i, Integer.valueOf(i2));
            return this;
        }

        public Builder addLocalFqName(int i) {
            ensureLocalFqNameIsMutable();
            this.localFqName_.add(Integer.valueOf(i));
            return this;
        }

        public Builder addAllLocalFqName(Iterable<? extends Integer> iterable) {
            ensureLocalFqNameIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.localFqName_);
            return this;
        }

        public Builder clearLocalFqName() {
            this.localFqName_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignatureOrBuilder
        public boolean hasLocalHash() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignatureOrBuilder
        public long getLocalHash() {
            return this.localHash_;
        }

        public Builder setLocalHash(long j) {
            this.bitField0_ |= 2;
            this.localHash_ = j;
            return this;
        }

        public Builder clearLocalHash() {
            this.bitField0_ &= -3;
            this.localHash_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignatureOrBuilder
        public boolean hasDebugInfo() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignatureOrBuilder
        public int getDebugInfo() {
            return this.debugInfo_;
        }

        public Builder setDebugInfo(int i) {
            this.bitField0_ |= 4;
            this.debugInfo_ = i;
            return this;
        }

        public Builder clearDebugInfo() {
            this.bitField0_ &= -5;
            this.debugInfo_ = 0;
            return this;
        }
    }
}
