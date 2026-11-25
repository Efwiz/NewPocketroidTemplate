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
public final class IrBranch extends GeneratedMessageLite implements IrBranchOrBuilder {
    public static final int CONDITION_FIELD_NUMBER = 1;
    public static Parser<IrBranch> PARSER = new AbstractParser<IrBranch>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrBranch.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrBranch parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrBranch(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int RESULT_FIELD_NUMBER = 2;
    private static final IrBranch defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private IrExpression condition_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private IrExpression result_;
    private final ByteString unknownFields;

    private IrBranch(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrBranch(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrBranch getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrBranch getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrBranch(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        IrExpression.Builder builder;
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
                            builder = (this.bitField0_ & 1) == 1 ? this.condition_.toBuilder() : null;
                            IrExpression irExpression = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                            this.condition_ = irExpression;
                            if (builder != null) {
                                builder.mergeFrom(irExpression);
                                this.condition_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                        } else if (tag != 18) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            builder = (this.bitField0_ & 2) == 2 ? this.result_.toBuilder() : null;
                            IrExpression irExpression2 = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                            this.result_ = irExpression2;
                            if (builder != null) {
                                builder.mergeFrom(irExpression2);
                                this.result_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 2;
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
        IrBranch irBranch = new IrBranch(true);
        defaultInstance = irBranch;
        irBranch.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrBranch> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBranchOrBuilder
    public boolean hasCondition() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBranchOrBuilder
    public IrExpression getCondition() {
        return this.condition_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBranchOrBuilder
    public boolean hasResult() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBranchOrBuilder
    public IrExpression getResult() {
        return this.result_;
    }

    private void initFields() {
        this.condition_ = IrExpression.getDefaultInstance();
        this.result_ = IrExpression.getDefaultInstance();
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
        if (!hasCondition()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasResult()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getCondition().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getResult().isInitialized()) {
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
            codedOutputStream.writeMessage(1, this.condition_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeMessage(2, this.result_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeMessageSize(1, this.condition_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(2, this.result_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrBranch parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrBranch parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrBranch parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrBranch parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrBranch parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrBranch parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrBranch parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrBranch parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrBranch parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrBranch parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrBranch irBranch) {
        return newBuilder().mergeFrom(irBranch);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrBranch, Builder> implements IrBranchOrBuilder {
        private int bitField0_;
        private IrExpression condition_ = IrExpression.getDefaultInstance();
        private IrExpression result_ = IrExpression.getDefaultInstance();

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
            this.condition_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -2;
            this.result_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -3;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrBranch getDefaultInstanceForType() {
            return IrBranch.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrBranch build() {
            IrBranch irBranchBuildPartial = buildPartial();
            if (irBranchBuildPartial.isInitialized()) {
                return irBranchBuildPartial;
            }
            throw newUninitializedMessageException(irBranchBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrBranch buildPartial() {
            IrBranch irBranch = new IrBranch(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irBranch.condition_ = this.condition_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irBranch.result_ = this.result_;
            irBranch.bitField0_ = i2;
            return irBranch;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrBranch irBranch) {
            if (irBranch == IrBranch.getDefaultInstance()) {
                return this;
            }
            if (irBranch.hasCondition()) {
                mergeCondition(irBranch.getCondition());
            }
            if (irBranch.hasResult()) {
                mergeResult(irBranch.getResult());
            }
            setUnknownFields(getUnknownFields().concat(irBranch.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasCondition() && hasResult() && getCondition().isInitialized() && getResult().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrBranch irBranch = null;
            try {
                try {
                    IrBranch partialFrom = IrBranch.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrBranch irBranch2 = (IrBranch) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irBranch = irBranch2;
                        if (irBranch != null) {
                            mergeFrom(irBranch);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irBranch != null) {
                    mergeFrom(irBranch);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBranchOrBuilder
        public boolean hasCondition() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBranchOrBuilder
        public IrExpression getCondition() {
            return this.condition_;
        }

        public Builder setCondition(IrExpression irExpression) {
            irExpression.getClass();
            this.condition_ = irExpression;
            this.bitField0_ |= 1;
            return this;
        }

        public Builder setCondition(IrExpression.Builder builder) {
            this.condition_ = builder.build();
            this.bitField0_ |= 1;
            return this;
        }

        public Builder mergeCondition(IrExpression irExpression) {
            if ((this.bitField0_ & 1) == 1 && this.condition_ != IrExpression.getDefaultInstance()) {
                this.condition_ = IrExpression.newBuilder(this.condition_).mergeFrom(irExpression).buildPartial();
            } else {
                this.condition_ = irExpression;
            }
            this.bitField0_ |= 1;
            return this;
        }

        public Builder clearCondition() {
            this.condition_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBranchOrBuilder
        public boolean hasResult() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBranchOrBuilder
        public IrExpression getResult() {
            return this.result_;
        }

        public Builder setResult(IrExpression irExpression) {
            irExpression.getClass();
            this.result_ = irExpression;
            this.bitField0_ |= 2;
            return this;
        }

        public Builder setResult(IrExpression.Builder builder) {
            this.result_ = builder.build();
            this.bitField0_ |= 2;
            return this;
        }

        public Builder mergeResult(IrExpression irExpression) {
            if ((this.bitField0_ & 2) == 2 && this.result_ != IrExpression.getDefaultInstance()) {
                this.result_ = IrExpression.newBuilder(this.result_).mergeFrom(irExpression).buildPartial();
            } else {
                this.result_ = irExpression;
            }
            this.bitField0_ |= 2;
            return this;
        }

        public Builder clearResult() {
            this.result_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -3;
            return this;
        }
    }
}
