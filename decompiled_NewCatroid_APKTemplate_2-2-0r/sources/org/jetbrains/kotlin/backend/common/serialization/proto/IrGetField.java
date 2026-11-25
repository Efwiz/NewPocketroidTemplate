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
public final class IrGetField extends GeneratedMessageLite implements IrGetFieldOrBuilder {
    public static final int FIELD_ACCESS_FIELD_NUMBER = 1;
    public static final int ORIGIN_NAME_FIELD_NUMBER = 2;
    public static Parser<IrGetField> PARSER = new AbstractParser<IrGetField>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrGetField.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrGetField parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrGetField(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrGetField defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private FieldAccessCommon fieldAccess_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int originName_;
    private final ByteString unknownFields;

    private IrGetField(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrGetField(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrGetField getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrGetField getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrGetField(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                                FieldAccessCommon.Builder builder = (this.bitField0_ & 1) == 1 ? this.fieldAccess_.toBuilder() : null;
                                FieldAccessCommon fieldAccessCommon = (FieldAccessCommon) codedInputStream.readMessage(FieldAccessCommon.PARSER, extensionRegistryLite);
                                this.fieldAccess_ = fieldAccessCommon;
                                if (builder != null) {
                                    builder.mergeFrom(fieldAccessCommon);
                                    this.fieldAccess_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 1;
                            } else if (tag != 16) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.bitField0_ |= 2;
                                this.originName_ = codedInputStream.readInt32();
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

    static {
        IrGetField irGetField = new IrGetField(true);
        defaultInstance = irGetField;
        irGetField.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrGetField> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrGetFieldOrBuilder
    public boolean hasFieldAccess() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrGetFieldOrBuilder
    public FieldAccessCommon getFieldAccess() {
        return this.fieldAccess_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrGetFieldOrBuilder
    public boolean hasOriginName() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrGetFieldOrBuilder
    public int getOriginName() {
        return this.originName_;
    }

    private void initFields() {
        this.fieldAccess_ = FieldAccessCommon.getDefaultInstance();
        this.originName_ = 0;
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
        if (!hasFieldAccess()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getFieldAccess().isInitialized()) {
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
            codedOutputStream.writeMessage(1, this.fieldAccess_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt32(2, this.originName_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeMessageSize(1, this.fieldAccess_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeMessageSize += CodedOutputStream.computeInt32Size(2, this.originName_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrGetField parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrGetField parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrGetField parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrGetField parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrGetField parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrGetField parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrGetField parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrGetField parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrGetField parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrGetField parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrGetField irGetField) {
        return newBuilder().mergeFrom(irGetField);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrGetField, Builder> implements IrGetFieldOrBuilder {
        private int bitField0_;
        private FieldAccessCommon fieldAccess_ = FieldAccessCommon.getDefaultInstance();
        private int originName_;

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
            this.fieldAccess_ = FieldAccessCommon.getDefaultInstance();
            int i = this.bitField0_;
            this.originName_ = 0;
            this.bitField0_ = i & (-4);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrGetField getDefaultInstanceForType() {
            return IrGetField.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrGetField build() {
            IrGetField irGetFieldBuildPartial = buildPartial();
            if (irGetFieldBuildPartial.isInitialized()) {
                return irGetFieldBuildPartial;
            }
            throw newUninitializedMessageException(irGetFieldBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrGetField buildPartial() {
            IrGetField irGetField = new IrGetField(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irGetField.fieldAccess_ = this.fieldAccess_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irGetField.originName_ = this.originName_;
            irGetField.bitField0_ = i2;
            return irGetField;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrGetField irGetField) {
            if (irGetField == IrGetField.getDefaultInstance()) {
                return this;
            }
            if (irGetField.hasFieldAccess()) {
                mergeFieldAccess(irGetField.getFieldAccess());
            }
            if (irGetField.hasOriginName()) {
                setOriginName(irGetField.getOriginName());
            }
            setUnknownFields(getUnknownFields().concat(irGetField.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasFieldAccess() && getFieldAccess().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrGetField irGetField = null;
            try {
                try {
                    IrGetField partialFrom = IrGetField.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrGetField irGetField2 = (IrGetField) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irGetField = irGetField2;
                        if (irGetField != null) {
                            mergeFrom(irGetField);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irGetField != null) {
                    mergeFrom(irGetField);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrGetFieldOrBuilder
        public boolean hasFieldAccess() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrGetFieldOrBuilder
        public FieldAccessCommon getFieldAccess() {
            return this.fieldAccess_;
        }

        public Builder setFieldAccess(FieldAccessCommon fieldAccessCommon) {
            fieldAccessCommon.getClass();
            this.fieldAccess_ = fieldAccessCommon;
            this.bitField0_ |= 1;
            return this;
        }

        public Builder setFieldAccess(FieldAccessCommon.Builder builder) {
            this.fieldAccess_ = builder.build();
            this.bitField0_ |= 1;
            return this;
        }

        public Builder mergeFieldAccess(FieldAccessCommon fieldAccessCommon) {
            if ((this.bitField0_ & 1) == 1 && this.fieldAccess_ != FieldAccessCommon.getDefaultInstance()) {
                this.fieldAccess_ = FieldAccessCommon.newBuilder(this.fieldAccess_).mergeFrom(fieldAccessCommon).buildPartial();
            } else {
                this.fieldAccess_ = fieldAccessCommon;
            }
            this.bitField0_ |= 1;
            return this;
        }

        public Builder clearFieldAccess() {
            this.fieldAccess_ = FieldAccessCommon.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrGetFieldOrBuilder
        public boolean hasOriginName() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrGetFieldOrBuilder
        public int getOriginName() {
            return this.originName_;
        }

        public Builder setOriginName(int i) {
            this.bitField0_ |= 2;
            this.originName_ = i;
            return this;
        }

        public Builder clearOriginName() {
            this.bitField0_ &= -3;
            this.originName_ = 0;
            return this;
        }
    }
}
