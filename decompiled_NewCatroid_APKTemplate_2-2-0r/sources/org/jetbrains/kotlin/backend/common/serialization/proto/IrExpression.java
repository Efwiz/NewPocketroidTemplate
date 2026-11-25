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
public final class IrExpression extends GeneratedMessageLite implements IrExpressionOrBuilder {
    public static final int COORDINATES_FIELD_NUMBER = 3;
    public static final int OPERATION_FIELD_NUMBER = 1;
    public static Parser<IrExpression> PARSER = new AbstractParser<IrExpression>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrExpression parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrExpression(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int TYPE_FIELD_NUMBER = 2;
    private static final IrExpression defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private long coordinates_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private IrOperation operation_;
    private int type_;
    private final ByteString unknownFields;

    private IrExpression(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrExpression(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrExpression getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrExpression getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrExpression(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                                IrOperation.Builder builder = (this.bitField0_ & 1) == 1 ? this.operation_.toBuilder() : null;
                                IrOperation irOperation = (IrOperation) codedInputStream.readMessage(IrOperation.PARSER, extensionRegistryLite);
                                this.operation_ = irOperation;
                                if (builder != null) {
                                    builder.mergeFrom(irOperation);
                                    this.operation_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 1;
                            } else if (tag == 16) {
                                this.bitField0_ |= 2;
                                this.type_ = codedInputStream.readInt32();
                            } else if (tag != 24) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.bitField0_ |= 4;
                                this.coordinates_ = codedInputStream.readInt64();
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
        IrExpression irExpression = new IrExpression(true);
        defaultInstance = irExpression;
        irExpression.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrExpression> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrExpressionOrBuilder
    public boolean hasOperation() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrExpressionOrBuilder
    public IrOperation getOperation() {
        return this.operation_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrExpressionOrBuilder
    public boolean hasType() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrExpressionOrBuilder
    public int getType() {
        return this.type_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrExpressionOrBuilder
    public boolean hasCoordinates() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrExpressionOrBuilder
    public long getCoordinates() {
        return this.coordinates_;
    }

    private void initFields() {
        this.operation_ = IrOperation.getDefaultInstance();
        this.type_ = 0;
        this.coordinates_ = 0L;
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
        if (!hasOperation()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasType()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasCoordinates()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getOperation().isInitialized()) {
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
            codedOutputStream.writeMessage(1, this.operation_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt32(2, this.type_);
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeInt64(3, this.coordinates_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeMessageSize(1, this.operation_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeMessageSize += CodedOutputStream.computeInt32Size(2, this.type_);
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeMessageSize += CodedOutputStream.computeInt64Size(3, this.coordinates_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrExpression parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrExpression parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrExpression parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrExpression parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrExpression parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrExpression parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrExpression parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrExpression parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrExpression parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrExpression parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrExpression irExpression) {
        return newBuilder().mergeFrom(irExpression);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrExpression, Builder> implements IrExpressionOrBuilder {
        private int bitField0_;
        private long coordinates_;
        private IrOperation operation_ = IrOperation.getDefaultInstance();
        private int type_;

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
            this.operation_ = IrOperation.getDefaultInstance();
            int i = this.bitField0_;
            this.type_ = 0;
            this.coordinates_ = 0L;
            this.bitField0_ = i & (-8);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrExpression getDefaultInstanceForType() {
            return IrExpression.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrExpression build() {
            IrExpression irExpressionBuildPartial = buildPartial();
            if (irExpressionBuildPartial.isInitialized()) {
                return irExpressionBuildPartial;
            }
            throw newUninitializedMessageException(irExpressionBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrExpression buildPartial() {
            IrExpression irExpression = new IrExpression(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irExpression.operation_ = this.operation_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irExpression.type_ = this.type_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            irExpression.coordinates_ = this.coordinates_;
            irExpression.bitField0_ = i2;
            return irExpression;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrExpression irExpression) {
            if (irExpression == IrExpression.getDefaultInstance()) {
                return this;
            }
            if (irExpression.hasOperation()) {
                mergeOperation(irExpression.getOperation());
            }
            if (irExpression.hasType()) {
                setType(irExpression.getType());
            }
            if (irExpression.hasCoordinates()) {
                setCoordinates(irExpression.getCoordinates());
            }
            setUnknownFields(getUnknownFields().concat(irExpression.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasOperation() && hasType() && hasCoordinates() && getOperation().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrExpression irExpression = null;
            try {
                try {
                    IrExpression partialFrom = IrExpression.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrExpression irExpression2 = (IrExpression) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irExpression = irExpression2;
                        if (irExpression != null) {
                            mergeFrom(irExpression);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irExpression != null) {
                    mergeFrom(irExpression);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrExpressionOrBuilder
        public boolean hasOperation() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrExpressionOrBuilder
        public IrOperation getOperation() {
            return this.operation_;
        }

        public Builder setOperation(IrOperation irOperation) {
            irOperation.getClass();
            this.operation_ = irOperation;
            this.bitField0_ |= 1;
            return this;
        }

        public Builder setOperation(IrOperation.Builder builder) {
            this.operation_ = builder.build();
            this.bitField0_ |= 1;
            return this;
        }

        public Builder mergeOperation(IrOperation irOperation) {
            if ((this.bitField0_ & 1) == 1 && this.operation_ != IrOperation.getDefaultInstance()) {
                this.operation_ = IrOperation.newBuilder(this.operation_).mergeFrom(irOperation).buildPartial();
            } else {
                this.operation_ = irOperation;
            }
            this.bitField0_ |= 1;
            return this;
        }

        public Builder clearOperation() {
            this.operation_ = IrOperation.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrExpressionOrBuilder
        public boolean hasType() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrExpressionOrBuilder
        public int getType() {
            return this.type_;
        }

        public Builder setType(int i) {
            this.bitField0_ |= 2;
            this.type_ = i;
            return this;
        }

        public Builder clearType() {
            this.bitField0_ &= -3;
            this.type_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrExpressionOrBuilder
        public boolean hasCoordinates() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrExpressionOrBuilder
        public long getCoordinates() {
            return this.coordinates_;
        }

        public Builder setCoordinates(long j) {
            this.bitField0_ |= 4;
            this.coordinates_ = j;
            return this;
        }

        public Builder clearCoordinates() {
            this.bitField0_ &= -5;
            this.coordinates_ = 0L;
            return this;
        }
    }
}
