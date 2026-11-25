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
public final class IrFunctionExpression extends GeneratedMessageLite implements IrFunctionExpressionOrBuilder {
    public static final int FUNCTION_FIELD_NUMBER = 1;
    public static final int ORIGIN_NAME_FIELD_NUMBER = 2;
    public static Parser<IrFunctionExpression> PARSER = new AbstractParser<IrFunctionExpression>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionExpression.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrFunctionExpression parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrFunctionExpression(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrFunctionExpression defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private IrFunction function_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int originName_;
    private final ByteString unknownFields;

    private IrFunctionExpression(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrFunctionExpression(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrFunctionExpression getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrFunctionExpression getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrFunctionExpression(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                                IrFunction.Builder builder = (this.bitField0_ & 1) == 1 ? this.function_.toBuilder() : null;
                                IrFunction irFunction = (IrFunction) codedInputStream.readMessage(IrFunction.PARSER, extensionRegistryLite);
                                this.function_ = irFunction;
                                if (builder != null) {
                                    builder.mergeFrom(irFunction);
                                    this.function_ = builder.buildPartial();
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
        IrFunctionExpression irFunctionExpression = new IrFunctionExpression(true);
        defaultInstance = irFunctionExpression;
        irFunctionExpression.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrFunctionExpression> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionExpressionOrBuilder
    public boolean hasFunction() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionExpressionOrBuilder
    public IrFunction getFunction() {
        return this.function_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionExpressionOrBuilder
    public boolean hasOriginName() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionExpressionOrBuilder
    public int getOriginName() {
        return this.originName_;
    }

    private void initFields() {
        this.function_ = IrFunction.getDefaultInstance();
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
        if (!hasFunction()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasOriginName()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getFunction().isInitialized()) {
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
            codedOutputStream.writeMessage(1, this.function_);
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
        int iComputeMessageSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeMessageSize(1, this.function_) : 0;
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

    public static IrFunctionExpression parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrFunctionExpression parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrFunctionExpression parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrFunctionExpression parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrFunctionExpression parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrFunctionExpression parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrFunctionExpression parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrFunctionExpression parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrFunctionExpression parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrFunctionExpression parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrFunctionExpression irFunctionExpression) {
        return newBuilder().mergeFrom(irFunctionExpression);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrFunctionExpression, Builder> implements IrFunctionExpressionOrBuilder {
        private int bitField0_;
        private IrFunction function_ = IrFunction.getDefaultInstance();
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
            this.function_ = IrFunction.getDefaultInstance();
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
        public IrFunctionExpression getDefaultInstanceForType() {
            return IrFunctionExpression.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrFunctionExpression build() {
            IrFunctionExpression irFunctionExpressionBuildPartial = buildPartial();
            if (irFunctionExpressionBuildPartial.isInitialized()) {
                return irFunctionExpressionBuildPartial;
            }
            throw newUninitializedMessageException(irFunctionExpressionBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrFunctionExpression buildPartial() {
            IrFunctionExpression irFunctionExpression = new IrFunctionExpression(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irFunctionExpression.function_ = this.function_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irFunctionExpression.originName_ = this.originName_;
            irFunctionExpression.bitField0_ = i2;
            return irFunctionExpression;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrFunctionExpression irFunctionExpression) {
            if (irFunctionExpression == IrFunctionExpression.getDefaultInstance()) {
                return this;
            }
            if (irFunctionExpression.hasFunction()) {
                mergeFunction(irFunctionExpression.getFunction());
            }
            if (irFunctionExpression.hasOriginName()) {
                setOriginName(irFunctionExpression.getOriginName());
            }
            setUnknownFields(getUnknownFields().concat(irFunctionExpression.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasFunction() && hasOriginName() && getFunction().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrFunctionExpression irFunctionExpression = null;
            try {
                try {
                    IrFunctionExpression partialFrom = IrFunctionExpression.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrFunctionExpression irFunctionExpression2 = (IrFunctionExpression) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irFunctionExpression = irFunctionExpression2;
                        if (irFunctionExpression != null) {
                            mergeFrom(irFunctionExpression);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irFunctionExpression != null) {
                    mergeFrom(irFunctionExpression);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionExpressionOrBuilder
        public boolean hasFunction() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionExpressionOrBuilder
        public IrFunction getFunction() {
            return this.function_;
        }

        public Builder setFunction(IrFunction irFunction) {
            irFunction.getClass();
            this.function_ = irFunction;
            this.bitField0_ |= 1;
            return this;
        }

        public Builder setFunction(IrFunction.Builder builder) {
            this.function_ = builder.build();
            this.bitField0_ |= 1;
            return this;
        }

        public Builder mergeFunction(IrFunction irFunction) {
            if ((this.bitField0_ & 1) == 1 && this.function_ != IrFunction.getDefaultInstance()) {
                this.function_ = IrFunction.newBuilder(this.function_).mergeFrom(irFunction).buildPartial();
            } else {
                this.function_ = irFunction;
            }
            this.bitField0_ |= 1;
            return this;
        }

        public Builder clearFunction() {
            this.function_ = IrFunction.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionExpressionOrBuilder
        public boolean hasOriginName() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionExpressionOrBuilder
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
