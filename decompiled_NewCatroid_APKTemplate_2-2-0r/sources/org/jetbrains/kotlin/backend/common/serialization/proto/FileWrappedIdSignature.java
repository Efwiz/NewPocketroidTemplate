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
public final class FileWrappedIdSignature extends GeneratedMessageLite implements FileWrappedIdSignatureOrBuilder {
    public static final int DELEGATE_FIELD_NUMBER = 1;
    public static final int FILE_FIELD_NUMBER = 2;
    public static Parser<FileWrappedIdSignature> PARSER = new AbstractParser<FileWrappedIdSignature>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.FileWrappedIdSignature.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public FileWrappedIdSignature parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new FileWrappedIdSignature(codedInputStream, extensionRegistryLite);
        }
    };
    private static final FileWrappedIdSignature defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private IdSignature delegate_;
    private int file_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    static {
        FileWrappedIdSignature fileWrappedIdSignature = new FileWrappedIdSignature(true);
        defaultInstance = fileWrappedIdSignature;
        fileWrappedIdSignature.initFields();
    }

    private FileWrappedIdSignature(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private FileWrappedIdSignature(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    private FileWrappedIdSignature(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        initFields();
        ByteString.Output outputNewOutput = ByteString.newOutput();
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
        boolean z = false;
        while (!z) {
            try {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                IdSignature.Builder builder = (this.bitField0_ & 1) == 1 ? this.delegate_.toBuilder() : null;
                                IdSignature idSignature = (IdSignature) codedInputStream.readMessage(IdSignature.PARSER, extensionRegistryLite);
                                this.delegate_ = idSignature;
                                if (builder != null) {
                                    builder.mergeFrom(idSignature);
                                    this.delegate_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 1;
                            } else if (tag != 16) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.bitField0_ |= 2;
                                this.file_ = codedInputStream.readInt32();
                            }
                        }
                        z = true;
                    } catch (IOException e) {
                        throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (InvalidProtocolBufferException e2) {
                    throw e2.setUnfinishedMessage(this);
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

    public static FileWrappedIdSignature getDefaultInstance() {
        return defaultInstance;
    }

    public static FileWrappedIdSignature parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static FileWrappedIdSignature parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static FileWrappedIdSignature parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static FileWrappedIdSignature parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static FileWrappedIdSignature parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static FileWrappedIdSignature parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static FileWrappedIdSignature parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static FileWrappedIdSignature parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static FileWrappedIdSignature parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static FileWrappedIdSignature parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    public static Builder newBuilder(FileWrappedIdSignature fileWrappedIdSignature) {
        return newBuilder().mergeFrom(fileWrappedIdSignature);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public FileWrappedIdSignature getDefaultInstanceForType() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<FileWrappedIdSignature> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileWrappedIdSignatureOrBuilder
    public boolean hasDelegate() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileWrappedIdSignatureOrBuilder
    public IdSignature getDelegate() {
        return this.delegate_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileWrappedIdSignatureOrBuilder
    public boolean hasFile() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileWrappedIdSignatureOrBuilder
    public int getFile() {
        return this.file_;
    }

    private void initFields() {
        this.delegate_ = IdSignature.getDefaultInstance();
        this.file_ = 0;
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
        if (!hasDelegate()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasFile()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getDelegate().isInitialized()) {
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
            codedOutputStream.writeMessage(1, this.delegate_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt32(2, this.file_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeMessageSize(1, this.delegate_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeMessageSize += CodedOutputStream.computeInt32Size(2, this.file_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<FileWrappedIdSignature, Builder> implements FileWrappedIdSignatureOrBuilder {
        private int bitField0_;
        private IdSignature delegate_ = IdSignature.getDefaultInstance();
        private int file_;

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
            this.delegate_ = IdSignature.getDefaultInstance();
            int i = this.bitField0_;
            this.file_ = 0;
            this.bitField0_ = i & (-4);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public FileWrappedIdSignature getDefaultInstanceForType() {
            return FileWrappedIdSignature.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public FileWrappedIdSignature build() {
            FileWrappedIdSignature fileWrappedIdSignatureBuildPartial = buildPartial();
            if (fileWrappedIdSignatureBuildPartial.isInitialized()) {
                return fileWrappedIdSignatureBuildPartial;
            }
            throw newUninitializedMessageException(fileWrappedIdSignatureBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public FileWrappedIdSignature buildPartial() {
            FileWrappedIdSignature fileWrappedIdSignature = new FileWrappedIdSignature(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            fileWrappedIdSignature.delegate_ = this.delegate_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            fileWrappedIdSignature.file_ = this.file_;
            fileWrappedIdSignature.bitField0_ = i2;
            return fileWrappedIdSignature;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(FileWrappedIdSignature fileWrappedIdSignature) {
            if (fileWrappedIdSignature == FileWrappedIdSignature.getDefaultInstance()) {
                return this;
            }
            if (fileWrappedIdSignature.hasDelegate()) {
                mergeDelegate(fileWrappedIdSignature.getDelegate());
            }
            if (fileWrappedIdSignature.hasFile()) {
                setFile(fileWrappedIdSignature.getFile());
            }
            setUnknownFields(getUnknownFields().concat(fileWrappedIdSignature.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasDelegate() && hasFile() && getDelegate().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            FileWrappedIdSignature fileWrappedIdSignature = null;
            try {
                try {
                    FileWrappedIdSignature partialFrom = FileWrappedIdSignature.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FileWrappedIdSignature fileWrappedIdSignature2 = (FileWrappedIdSignature) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        fileWrappedIdSignature = fileWrappedIdSignature2;
                        if (fileWrappedIdSignature != null) {
                            mergeFrom(fileWrappedIdSignature);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileWrappedIdSignature != null) {
                    mergeFrom(fileWrappedIdSignature);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileWrappedIdSignatureOrBuilder
        public boolean hasDelegate() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileWrappedIdSignatureOrBuilder
        public IdSignature getDelegate() {
            return this.delegate_;
        }

        public Builder setDelegate(IdSignature idSignature) {
            idSignature.getClass();
            this.delegate_ = idSignature;
            this.bitField0_ |= 1;
            return this;
        }

        public Builder setDelegate(IdSignature.Builder builder) {
            this.delegate_ = builder.build();
            this.bitField0_ |= 1;
            return this;
        }

        public Builder mergeDelegate(IdSignature idSignature) {
            if ((this.bitField0_ & 1) == 1 && this.delegate_ != IdSignature.getDefaultInstance()) {
                this.delegate_ = IdSignature.newBuilder(this.delegate_).mergeFrom(idSignature).buildPartial();
            } else {
                this.delegate_ = idSignature;
            }
            this.bitField0_ |= 1;
            return this;
        }

        public Builder clearDelegate() {
            this.delegate_ = IdSignature.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileWrappedIdSignatureOrBuilder
        public boolean hasFile() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileWrappedIdSignatureOrBuilder
        public int getFile() {
            return this.file_;
        }

        public Builder setFile(int i) {
            this.bitField0_ |= 2;
            this.file_ = i;
            return this;
        }

        public Builder clearFile() {
            this.bitField0_ &= -3;
            this.file_ = 0;
            return this;
        }
    }
}
