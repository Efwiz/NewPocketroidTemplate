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
public final class IrSetField extends GeneratedMessageLite implements IrSetFieldOrBuilder {
    public static final int FIELD_ACCESS_FIELD_NUMBER = 1;
    public static final int ORIGIN_NAME_FIELD_NUMBER = 3;
    public static Parser<IrSetField> PARSER = new AbstractParser<IrSetField>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrSetField.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrSetField parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrSetField(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int VALUE_FIELD_NUMBER = 2;
    private static final IrSetField defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private FieldAccessCommon fieldAccess_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int originName_;
    private final ByteString unknownFields;
    private IrExpression value_;

    private IrSetField(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrSetField(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrSetField getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrSetField getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrSetField(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        GeneratedMessageLite.Builder builder;
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
                            builder = (this.bitField0_ & 1) == 1 ? this.fieldAccess_.toBuilder() : null;
                            FieldAccessCommon fieldAccessCommon = (FieldAccessCommon) codedInputStream.readMessage(FieldAccessCommon.PARSER, extensionRegistryLite);
                            this.fieldAccess_ = fieldAccessCommon;
                            if (builder != null) {
                                builder.mergeFrom(fieldAccessCommon);
                                this.fieldAccess_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                        } else if (tag == 18) {
                            builder = (this.bitField0_ & 2) == 2 ? this.value_.toBuilder() : null;
                            IrExpression irExpression = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                            this.value_ = irExpression;
                            if (builder != null) {
                                builder.mergeFrom(irExpression);
                                this.value_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 2;
                        } else if (tag != 24) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            this.bitField0_ |= 4;
                            this.originName_ = codedInputStream.readInt32();
                        }
                    }
                    z = true;
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
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
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
        IrSetField irSetField = new IrSetField(true);
        defaultInstance = irSetField;
        irSetField.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrSetField> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSetFieldOrBuilder
    public boolean hasFieldAccess() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSetFieldOrBuilder
    public FieldAccessCommon getFieldAccess() {
        return this.fieldAccess_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSetFieldOrBuilder
    public boolean hasValue() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSetFieldOrBuilder
    public IrExpression getValue() {
        return this.value_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSetFieldOrBuilder
    public boolean hasOriginName() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSetFieldOrBuilder
    public int getOriginName() {
        return this.originName_;
    }

    private void initFields() {
        this.fieldAccess_ = FieldAccessCommon.getDefaultInstance();
        this.value_ = IrExpression.getDefaultInstance();
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
        if (!hasValue()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getFieldAccess().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getValue().isInitialized()) {
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
            codedOutputStream.writeMessage(2, this.value_);
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeInt32(3, this.originName_);
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
            iComputeMessageSize += CodedOutputStream.computeMessageSize(2, this.value_);
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeMessageSize += CodedOutputStream.computeInt32Size(3, this.originName_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrSetField parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrSetField parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrSetField parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrSetField parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrSetField parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrSetField parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrSetField parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrSetField parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrSetField parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrSetField parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrSetField irSetField) {
        return newBuilder().mergeFrom(irSetField);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrSetField, Builder> implements IrSetFieldOrBuilder {
        private int bitField0_;
        private int originName_;
        private FieldAccessCommon fieldAccess_ = FieldAccessCommon.getDefaultInstance();
        private IrExpression value_ = IrExpression.getDefaultInstance();

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
            this.bitField0_ &= -2;
            this.value_ = IrExpression.getDefaultInstance();
            int i = this.bitField0_;
            this.originName_ = 0;
            this.bitField0_ = i & (-7);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrSetField getDefaultInstanceForType() {
            return IrSetField.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrSetField build() {
            IrSetField irSetFieldBuildPartial = buildPartial();
            if (irSetFieldBuildPartial.isInitialized()) {
                return irSetFieldBuildPartial;
            }
            throw newUninitializedMessageException(irSetFieldBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrSetField buildPartial() {
            IrSetField irSetField = new IrSetField(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irSetField.fieldAccess_ = this.fieldAccess_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irSetField.value_ = this.value_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            irSetField.originName_ = this.originName_;
            irSetField.bitField0_ = i2;
            return irSetField;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrSetField irSetField) {
            if (irSetField == IrSetField.getDefaultInstance()) {
                return this;
            }
            if (irSetField.hasFieldAccess()) {
                mergeFieldAccess(irSetField.getFieldAccess());
            }
            if (irSetField.hasValue()) {
                mergeValue(irSetField.getValue());
            }
            if (irSetField.hasOriginName()) {
                setOriginName(irSetField.getOriginName());
            }
            setUnknownFields(getUnknownFields().concat(irSetField.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasFieldAccess() && hasValue() && getFieldAccess().isInitialized() && getValue().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrSetField irSetField = null;
            try {
                try {
                    IrSetField partialFrom = IrSetField.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrSetField irSetField2 = (IrSetField) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irSetField = irSetField2;
                        if (irSetField != null) {
                            mergeFrom(irSetField);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irSetField != null) {
                    mergeFrom(irSetField);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSetFieldOrBuilder
        public boolean hasFieldAccess() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSetFieldOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSetFieldOrBuilder
        public boolean hasValue() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSetFieldOrBuilder
        public IrExpression getValue() {
            return this.value_;
        }

        public Builder setValue(IrExpression irExpression) {
            irExpression.getClass();
            this.value_ = irExpression;
            this.bitField0_ |= 2;
            return this;
        }

        public Builder setValue(IrExpression.Builder builder) {
            this.value_ = builder.build();
            this.bitField0_ |= 2;
            return this;
        }

        public Builder mergeValue(IrExpression irExpression) {
            if ((this.bitField0_ & 2) == 2 && this.value_ != IrExpression.getDefaultInstance()) {
                this.value_ = IrExpression.newBuilder(this.value_).mergeFrom(irExpression).buildPartial();
            } else {
                this.value_ = irExpression;
            }
            this.bitField0_ |= 2;
            return this;
        }

        public Builder clearValue() {
            this.value_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -3;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSetFieldOrBuilder
        public boolean hasOriginName() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSetFieldOrBuilder
        public int getOriginName() {
            return this.originName_;
        }

        public Builder setOriginName(int i) {
            this.bitField0_ |= 4;
            this.originName_ = i;
            return this;
        }

        public Builder clearOriginName() {
            this.bitField0_ &= -5;
            this.originName_ = 0;
            return this;
        }
    }
}
