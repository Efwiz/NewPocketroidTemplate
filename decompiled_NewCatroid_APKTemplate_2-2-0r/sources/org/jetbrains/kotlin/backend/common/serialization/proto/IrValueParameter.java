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
public final class IrValueParameter extends GeneratedMessageLite implements IrValueParameterOrBuilder {
    public static final int BASE_FIELD_NUMBER = 1;
    public static final int DEFAULT_VALUE_FIELD_NUMBER = 4;
    public static final int NAME_TYPE_FIELD_NUMBER = 2;
    public static Parser<IrValueParameter> PARSER = new AbstractParser<IrValueParameter>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrValueParameter parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrValueParameter(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int VARARG_ELEMENT_TYPE_FIELD_NUMBER = 3;
    private static final IrValueParameter defaultInstance;
    private static final long serialVersionUID = 0;
    private IrDeclarationBase base_;
    private int bitField0_;
    private int defaultValue_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private long nameType_;
    private final ByteString unknownFields;
    private int varargElementType_;

    private IrValueParameter(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrValueParameter(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrValueParameter getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrValueParameter getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrValueParameter(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                        if (tag == 10) {
                            IrDeclarationBase.Builder builder = (this.bitField0_ & 1) == 1 ? this.base_.toBuilder() : null;
                            IrDeclarationBase irDeclarationBase = (IrDeclarationBase) codedInputStream.readMessage(IrDeclarationBase.PARSER, extensionRegistryLite);
                            this.base_ = irDeclarationBase;
                            if (builder != null) {
                                builder.mergeFrom(irDeclarationBase);
                                this.base_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                        } else if (tag == 16) {
                            this.bitField0_ |= 2;
                            this.nameType_ = codedInputStream.readInt64();
                        } else if (tag == 24) {
                            this.bitField0_ |= 4;
                            this.varargElementType_ = codedInputStream.readInt32();
                        } else if (tag != 32) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            this.bitField0_ |= 8;
                            this.defaultValue_ = codedInputStream.readInt32();
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
        IrValueParameter irValueParameter = new IrValueParameter(true);
        defaultInstance = irValueParameter;
        irValueParameter.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrValueParameter> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
    public boolean hasBase() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
    public IrDeclarationBase getBase() {
        return this.base_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
    public boolean hasNameType() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
    public long getNameType() {
        return this.nameType_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
    public boolean hasVarargElementType() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
    public int getVarargElementType() {
        return this.varargElementType_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
    public boolean hasDefaultValue() {
        return (this.bitField0_ & 8) == 8;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
    public int getDefaultValue() {
        return this.defaultValue_;
    }

    private void initFields() {
        this.base_ = IrDeclarationBase.getDefaultInstance();
        this.nameType_ = 0L;
        this.varargElementType_ = 0;
        this.defaultValue_ = 0;
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
        if (!hasBase()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasNameType()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getBase().isInitialized()) {
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
            codedOutputStream.writeMessage(1, this.base_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt64(2, this.nameType_);
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeInt32(3, this.varargElementType_);
        }
        if ((this.bitField0_ & 8) == 8) {
            codedOutputStream.writeInt32(4, this.defaultValue_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeMessageSize(1, this.base_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeMessageSize += CodedOutputStream.computeInt64Size(2, this.nameType_);
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeMessageSize += CodedOutputStream.computeInt32Size(3, this.varargElementType_);
        }
        if ((this.bitField0_ & 8) == 8) {
            iComputeMessageSize += CodedOutputStream.computeInt32Size(4, this.defaultValue_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrValueParameter parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrValueParameter parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrValueParameter parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrValueParameter parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrValueParameter parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrValueParameter parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrValueParameter parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrValueParameter parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrValueParameter parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrValueParameter parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrValueParameter irValueParameter) {
        return newBuilder().mergeFrom(irValueParameter);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrValueParameter, Builder> implements IrValueParameterOrBuilder {
        private IrDeclarationBase base_ = IrDeclarationBase.getDefaultInstance();
        private int bitField0_;
        private int defaultValue_;
        private long nameType_;
        private int varargElementType_;

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
            this.base_ = IrDeclarationBase.getDefaultInstance();
            int i = this.bitField0_;
            this.nameType_ = 0L;
            this.varargElementType_ = 0;
            this.defaultValue_ = 0;
            this.bitField0_ = i & (-16);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrValueParameter getDefaultInstanceForType() {
            return IrValueParameter.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrValueParameter build() {
            IrValueParameter irValueParameterBuildPartial = buildPartial();
            if (irValueParameterBuildPartial.isInitialized()) {
                return irValueParameterBuildPartial;
            }
            throw newUninitializedMessageException(irValueParameterBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrValueParameter buildPartial() {
            IrValueParameter irValueParameter = new IrValueParameter(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irValueParameter.base_ = this.base_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irValueParameter.nameType_ = this.nameType_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            irValueParameter.varargElementType_ = this.varargElementType_;
            if ((i & 8) == 8) {
                i2 |= 8;
            }
            irValueParameter.defaultValue_ = this.defaultValue_;
            irValueParameter.bitField0_ = i2;
            return irValueParameter;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrValueParameter irValueParameter) {
            if (irValueParameter == IrValueParameter.getDefaultInstance()) {
                return this;
            }
            if (irValueParameter.hasBase()) {
                mergeBase(irValueParameter.getBase());
            }
            if (irValueParameter.hasNameType()) {
                setNameType(irValueParameter.getNameType());
            }
            if (irValueParameter.hasVarargElementType()) {
                setVarargElementType(irValueParameter.getVarargElementType());
            }
            if (irValueParameter.hasDefaultValue()) {
                setDefaultValue(irValueParameter.getDefaultValue());
            }
            setUnknownFields(getUnknownFields().concat(irValueParameter.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasBase() && hasNameType() && getBase().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrValueParameter irValueParameter = null;
            try {
                try {
                    IrValueParameter partialFrom = IrValueParameter.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrValueParameter irValueParameter2 = (IrValueParameter) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irValueParameter = irValueParameter2;
                        if (irValueParameter != null) {
                            mergeFrom(irValueParameter);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irValueParameter != null) {
                    mergeFrom(irValueParameter);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
        public boolean hasBase() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
        public IrDeclarationBase getBase() {
            return this.base_;
        }

        public Builder setBase(IrDeclarationBase irDeclarationBase) {
            irDeclarationBase.getClass();
            this.base_ = irDeclarationBase;
            this.bitField0_ |= 1;
            return this;
        }

        public Builder setBase(IrDeclarationBase.Builder builder) {
            this.base_ = builder.build();
            this.bitField0_ |= 1;
            return this;
        }

        public Builder mergeBase(IrDeclarationBase irDeclarationBase) {
            if ((this.bitField0_ & 1) == 1 && this.base_ != IrDeclarationBase.getDefaultInstance()) {
                this.base_ = IrDeclarationBase.newBuilder(this.base_).mergeFrom(irDeclarationBase).buildPartial();
            } else {
                this.base_ = irDeclarationBase;
            }
            this.bitField0_ |= 1;
            return this;
        }

        public Builder clearBase() {
            this.base_ = IrDeclarationBase.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
        public boolean hasNameType() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
        public long getNameType() {
            return this.nameType_;
        }

        public Builder setNameType(long j) {
            this.bitField0_ |= 2;
            this.nameType_ = j;
            return this;
        }

        public Builder clearNameType() {
            this.bitField0_ &= -3;
            this.nameType_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
        public boolean hasVarargElementType() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
        public int getVarargElementType() {
            return this.varargElementType_;
        }

        public Builder setVarargElementType(int i) {
            this.bitField0_ |= 4;
            this.varargElementType_ = i;
            return this;
        }

        public Builder clearVarargElementType() {
            this.bitField0_ &= -5;
            this.varargElementType_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
        public boolean hasDefaultValue() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameterOrBuilder
        public int getDefaultValue() {
            return this.defaultValue_;
        }

        public Builder setDefaultValue(int i) {
            this.bitField0_ |= 8;
            this.defaultValue_ = i;
            return this;
        }

        public Builder clearDefaultValue() {
            this.bitField0_ &= -9;
            this.defaultValue_ = 0;
            return this;
        }
    }
}
