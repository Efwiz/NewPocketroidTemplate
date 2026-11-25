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
public final class FileLocalIdSignature extends GeneratedMessageLite implements FileLocalIdSignatureOrBuilder {
    public static final int CONTAINER_FIELD_NUMBER = 1;
    public static final int DEBUG_INFO_FIELD_NUMBER = 3;
    public static final int LOCAL_ID_FIELD_NUMBER = 2;
    public static Parser<FileLocalIdSignature> PARSER = new AbstractParser<FileLocalIdSignature>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignature.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public FileLocalIdSignature parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new FileLocalIdSignature(codedInputStream, extensionRegistryLite);
        }
    };
    private static final FileLocalIdSignature defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private int container_;
    private int debugInfo_;
    private long localId_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    private FileLocalIdSignature(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private FileLocalIdSignature(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static FileLocalIdSignature getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public FileLocalIdSignature getDefaultInstanceForType() {
        return defaultInstance;
    }

    private FileLocalIdSignature(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                            this.container_ = codedInputStream.readInt32();
                        } else if (tag == 16) {
                            this.bitField0_ |= 2;
                            this.localId_ = codedInputStream.readInt64();
                        } else if (tag != 24) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            this.bitField0_ |= 4;
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
        FileLocalIdSignature fileLocalIdSignature = new FileLocalIdSignature(true);
        defaultInstance = fileLocalIdSignature;
        fileLocalIdSignature.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<FileLocalIdSignature> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignatureOrBuilder
    public boolean hasContainer() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignatureOrBuilder
    public int getContainer() {
        return this.container_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignatureOrBuilder
    public boolean hasLocalId() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignatureOrBuilder
    public long getLocalId() {
        return this.localId_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignatureOrBuilder
    public boolean hasDebugInfo() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignatureOrBuilder
    public int getDebugInfo() {
        return this.debugInfo_;
    }

    private void initFields() {
        this.container_ = 0;
        this.localId_ = 0L;
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
        if (!hasContainer()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasLocalId()) {
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
            codedOutputStream.writeInt32(1, this.container_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt64(2, this.localId_);
        }
        if ((this.bitField0_ & 4) == 4) {
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
        int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.container_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeInt32Size += CodedOutputStream.computeInt64Size(2, this.localId_);
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeInt32Size += CodedOutputStream.computeInt32Size(3, this.debugInfo_);
        }
        int size = iComputeInt32Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static FileLocalIdSignature parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static FileLocalIdSignature parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static FileLocalIdSignature parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static FileLocalIdSignature parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static FileLocalIdSignature parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static FileLocalIdSignature parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static FileLocalIdSignature parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static FileLocalIdSignature parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static FileLocalIdSignature parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static FileLocalIdSignature parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(FileLocalIdSignature fileLocalIdSignature) {
        return newBuilder().mergeFrom(fileLocalIdSignature);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<FileLocalIdSignature, Builder> implements FileLocalIdSignatureOrBuilder {
        private int bitField0_;
        private int container_;
        private int debugInfo_;
        private long localId_;

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
            this.container_ = 0;
            int i = this.bitField0_;
            this.localId_ = 0L;
            this.debugInfo_ = 0;
            this.bitField0_ = i & (-8);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public FileLocalIdSignature getDefaultInstanceForType() {
            return FileLocalIdSignature.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public FileLocalIdSignature build() {
            FileLocalIdSignature fileLocalIdSignatureBuildPartial = buildPartial();
            if (fileLocalIdSignatureBuildPartial.isInitialized()) {
                return fileLocalIdSignatureBuildPartial;
            }
            throw newUninitializedMessageException(fileLocalIdSignatureBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public FileLocalIdSignature buildPartial() {
            FileLocalIdSignature fileLocalIdSignature = new FileLocalIdSignature(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            fileLocalIdSignature.container_ = this.container_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            fileLocalIdSignature.localId_ = this.localId_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            fileLocalIdSignature.debugInfo_ = this.debugInfo_;
            fileLocalIdSignature.bitField0_ = i2;
            return fileLocalIdSignature;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(FileLocalIdSignature fileLocalIdSignature) {
            if (fileLocalIdSignature == FileLocalIdSignature.getDefaultInstance()) {
                return this;
            }
            if (fileLocalIdSignature.hasContainer()) {
                setContainer(fileLocalIdSignature.getContainer());
            }
            if (fileLocalIdSignature.hasLocalId()) {
                setLocalId(fileLocalIdSignature.getLocalId());
            }
            if (fileLocalIdSignature.hasDebugInfo()) {
                setDebugInfo(fileLocalIdSignature.getDebugInfo());
            }
            setUnknownFields(getUnknownFields().concat(fileLocalIdSignature.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasContainer() && hasLocalId();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            FileLocalIdSignature fileLocalIdSignature = null;
            try {
                try {
                    FileLocalIdSignature partialFrom = FileLocalIdSignature.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FileLocalIdSignature fileLocalIdSignature2 = (FileLocalIdSignature) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        fileLocalIdSignature = fileLocalIdSignature2;
                        if (fileLocalIdSignature != null) {
                            mergeFrom(fileLocalIdSignature);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileLocalIdSignature != null) {
                    mergeFrom(fileLocalIdSignature);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignatureOrBuilder
        public boolean hasContainer() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignatureOrBuilder
        public int getContainer() {
            return this.container_;
        }

        public Builder setContainer(int i) {
            this.bitField0_ |= 1;
            this.container_ = i;
            return this;
        }

        public Builder clearContainer() {
            this.bitField0_ &= -2;
            this.container_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignatureOrBuilder
        public boolean hasLocalId() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignatureOrBuilder
        public long getLocalId() {
            return this.localId_;
        }

        public Builder setLocalId(long j) {
            this.bitField0_ |= 2;
            this.localId_ = j;
            return this;
        }

        public Builder clearLocalId() {
            this.bitField0_ &= -3;
            this.localId_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignatureOrBuilder
        public boolean hasDebugInfo() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignatureOrBuilder
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
