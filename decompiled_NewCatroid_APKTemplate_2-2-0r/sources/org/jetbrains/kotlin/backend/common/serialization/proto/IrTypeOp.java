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
public final class IrTypeOp extends GeneratedMessageLite implements IrTypeOpOrBuilder {
    public static final int ARGUMENT_FIELD_NUMBER = 3;
    public static final int OPERAND_FIELD_NUMBER = 2;
    public static final int OPERATOR_FIELD_NUMBER = 1;
    public static Parser<IrTypeOp> PARSER = new AbstractParser<IrTypeOp>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOp.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrTypeOp parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrTypeOp(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrTypeOp defaultInstance;
    private static final long serialVersionUID = 0;
    private IrExpression argument_;
    private int bitField0_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int operand_;
    private IrTypeOperator operator_;
    private final ByteString unknownFields;

    private IrTypeOp(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrTypeOp(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrTypeOp getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrTypeOp getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrTypeOp(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                            if (tag == 8) {
                                int i = codedInputStream.readEnum();
                                IrTypeOperator irTypeOperatorValueOf = IrTypeOperator.valueOf(i);
                                if (irTypeOperatorValueOf == null) {
                                    codedOutputStreamNewInstance.writeRawVarint32(tag);
                                    codedOutputStreamNewInstance.writeRawVarint32(i);
                                } else {
                                    this.bitField0_ |= 1;
                                    this.operator_ = irTypeOperatorValueOf;
                                }
                            } else if (tag == 16) {
                                this.bitField0_ |= 2;
                                this.operand_ = codedInputStream.readInt32();
                            } else if (tag != 26) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                IrExpression.Builder builder = (this.bitField0_ & 4) == 4 ? this.argument_.toBuilder() : null;
                                IrExpression irExpression = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                                this.argument_ = irExpression;
                                if (builder != null) {
                                    builder.mergeFrom(irExpression);
                                    this.argument_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 4;
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
        IrTypeOp irTypeOp = new IrTypeOp(true);
        defaultInstance = irTypeOp;
        irTypeOp.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrTypeOp> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOpOrBuilder
    public boolean hasOperator() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOpOrBuilder
    public IrTypeOperator getOperator() {
        return this.operator_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOpOrBuilder
    public boolean hasOperand() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOpOrBuilder
    public int getOperand() {
        return this.operand_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOpOrBuilder
    public boolean hasArgument() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOpOrBuilder
    public IrExpression getArgument() {
        return this.argument_;
    }

    private void initFields() {
        this.operator_ = IrTypeOperator.CAST;
        this.operand_ = 0;
        this.argument_ = IrExpression.getDefaultInstance();
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
        if (!hasOperator()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasOperand()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasArgument()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getArgument().isInitialized()) {
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
            codedOutputStream.writeEnum(1, this.operator_.getNumber());
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt32(2, this.operand_);
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeMessage(3, this.argument_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeEnumSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeEnumSize(1, this.operator_.getNumber()) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeEnumSize += CodedOutputStream.computeInt32Size(2, this.operand_);
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeEnumSize += CodedOutputStream.computeMessageSize(3, this.argument_);
        }
        int size = iComputeEnumSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrTypeOp parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrTypeOp parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrTypeOp parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrTypeOp parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrTypeOp parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrTypeOp parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrTypeOp parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrTypeOp parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrTypeOp parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrTypeOp parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrTypeOp irTypeOp) {
        return newBuilder().mergeFrom(irTypeOp);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrTypeOp, Builder> implements IrTypeOpOrBuilder {
        private int bitField0_;
        private int operand_;
        private IrTypeOperator operator_ = IrTypeOperator.CAST;
        private IrExpression argument_ = IrExpression.getDefaultInstance();

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
            this.operator_ = IrTypeOperator.CAST;
            int i = this.bitField0_;
            this.operand_ = 0;
            this.bitField0_ = i & (-4);
            this.argument_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -5;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrTypeOp getDefaultInstanceForType() {
            return IrTypeOp.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrTypeOp build() {
            IrTypeOp irTypeOpBuildPartial = buildPartial();
            if (irTypeOpBuildPartial.isInitialized()) {
                return irTypeOpBuildPartial;
            }
            throw newUninitializedMessageException(irTypeOpBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrTypeOp buildPartial() {
            IrTypeOp irTypeOp = new IrTypeOp(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irTypeOp.operator_ = this.operator_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irTypeOp.operand_ = this.operand_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            irTypeOp.argument_ = this.argument_;
            irTypeOp.bitField0_ = i2;
            return irTypeOp;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrTypeOp irTypeOp) {
            if (irTypeOp == IrTypeOp.getDefaultInstance()) {
                return this;
            }
            if (irTypeOp.hasOperator()) {
                setOperator(irTypeOp.getOperator());
            }
            if (irTypeOp.hasOperand()) {
                setOperand(irTypeOp.getOperand());
            }
            if (irTypeOp.hasArgument()) {
                mergeArgument(irTypeOp.getArgument());
            }
            setUnknownFields(getUnknownFields().concat(irTypeOp.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasOperator() && hasOperand() && hasArgument() && getArgument().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrTypeOp irTypeOp = null;
            try {
                try {
                    IrTypeOp partialFrom = IrTypeOp.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrTypeOp irTypeOp2 = (IrTypeOp) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irTypeOp = irTypeOp2;
                        if (irTypeOp != null) {
                            mergeFrom(irTypeOp);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irTypeOp != null) {
                    mergeFrom(irTypeOp);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOpOrBuilder
        public boolean hasOperator() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOpOrBuilder
        public IrTypeOperator getOperator() {
            return this.operator_;
        }

        public Builder setOperator(IrTypeOperator irTypeOperator) {
            irTypeOperator.getClass();
            this.bitField0_ |= 1;
            this.operator_ = irTypeOperator;
            return this;
        }

        public Builder clearOperator() {
            this.bitField0_ &= -2;
            this.operator_ = IrTypeOperator.CAST;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOpOrBuilder
        public boolean hasOperand() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOpOrBuilder
        public int getOperand() {
            return this.operand_;
        }

        public Builder setOperand(int i) {
            this.bitField0_ |= 2;
            this.operand_ = i;
            return this;
        }

        public Builder clearOperand() {
            this.bitField0_ &= -3;
            this.operand_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOpOrBuilder
        public boolean hasArgument() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOpOrBuilder
        public IrExpression getArgument() {
            return this.argument_;
        }

        public Builder setArgument(IrExpression irExpression) {
            irExpression.getClass();
            this.argument_ = irExpression;
            this.bitField0_ |= 4;
            return this;
        }

        public Builder setArgument(IrExpression.Builder builder) {
            this.argument_ = builder.build();
            this.bitField0_ |= 4;
            return this;
        }

        public Builder mergeArgument(IrExpression irExpression) {
            if ((this.bitField0_ & 4) == 4 && this.argument_ != IrExpression.getDefaultInstance()) {
                this.argument_ = IrExpression.newBuilder(this.argument_).mergeFrom(irExpression).buildPartial();
            } else {
                this.argument_ = irExpression;
            }
            this.bitField0_ |= 4;
            return this;
        }

        public Builder clearArgument() {
            this.argument_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -5;
            return this;
        }
    }
}
