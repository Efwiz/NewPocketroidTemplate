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
public final class IrCatch extends GeneratedMessageLite implements IrCatchOrBuilder {
    public static final int CATCH_PARAMETER_FIELD_NUMBER = 1;
    public static Parser<IrCatch> PARSER = new AbstractParser<IrCatch>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrCatch.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrCatch parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrCatch(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int RESULT_FIELD_NUMBER = 2;
    private static final IrCatch defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private IrVariable catchParameter_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private IrExpression result_;
    private final ByteString unknownFields;

    private IrCatch(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrCatch(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrCatch getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrCatch getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrCatch(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                            IrVariable.Builder builder = (this.bitField0_ & 1) == 1 ? this.catchParameter_.toBuilder() : null;
                            IrVariable irVariable = (IrVariable) codedInputStream.readMessage(IrVariable.PARSER, extensionRegistryLite);
                            this.catchParameter_ = irVariable;
                            if (builder != null) {
                                builder.mergeFrom(irVariable);
                                this.catchParameter_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                        } else if (tag != 18) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            IrExpression.Builder builder2 = (this.bitField0_ & 2) == 2 ? this.result_.toBuilder() : null;
                            IrExpression irExpression = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                            this.result_ = irExpression;
                            if (builder2 != null) {
                                builder2.mergeFrom(irExpression);
                                this.result_ = builder2.buildPartial();
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
        IrCatch irCatch = new IrCatch(true);
        defaultInstance = irCatch;
        irCatch.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrCatch> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCatchOrBuilder
    public boolean hasCatchParameter() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCatchOrBuilder
    public IrVariable getCatchParameter() {
        return this.catchParameter_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCatchOrBuilder
    public boolean hasResult() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCatchOrBuilder
    public IrExpression getResult() {
        return this.result_;
    }

    private void initFields() {
        this.catchParameter_ = IrVariable.getDefaultInstance();
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
        if (!hasCatchParameter()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasResult()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getCatchParameter().isInitialized()) {
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
            codedOutputStream.writeMessage(1, this.catchParameter_);
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
        int iComputeMessageSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeMessageSize(1, this.catchParameter_) : 0;
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

    public static IrCatch parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrCatch parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrCatch parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrCatch parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrCatch parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrCatch parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrCatch parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrCatch parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrCatch parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrCatch parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrCatch irCatch) {
        return newBuilder().mergeFrom(irCatch);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrCatch, Builder> implements IrCatchOrBuilder {
        private int bitField0_;
        private IrVariable catchParameter_ = IrVariable.getDefaultInstance();
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
            this.catchParameter_ = IrVariable.getDefaultInstance();
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
        public IrCatch getDefaultInstanceForType() {
            return IrCatch.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrCatch build() {
            IrCatch irCatchBuildPartial = buildPartial();
            if (irCatchBuildPartial.isInitialized()) {
                return irCatchBuildPartial;
            }
            throw newUninitializedMessageException(irCatchBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrCatch buildPartial() {
            IrCatch irCatch = new IrCatch(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irCatch.catchParameter_ = this.catchParameter_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irCatch.result_ = this.result_;
            irCatch.bitField0_ = i2;
            return irCatch;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrCatch irCatch) {
            if (irCatch == IrCatch.getDefaultInstance()) {
                return this;
            }
            if (irCatch.hasCatchParameter()) {
                mergeCatchParameter(irCatch.getCatchParameter());
            }
            if (irCatch.hasResult()) {
                mergeResult(irCatch.getResult());
            }
            setUnknownFields(getUnknownFields().concat(irCatch.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasCatchParameter() && hasResult() && getCatchParameter().isInitialized() && getResult().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrCatch irCatch = null;
            try {
                try {
                    IrCatch partialFrom = IrCatch.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrCatch irCatch2 = (IrCatch) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irCatch = irCatch2;
                        if (irCatch != null) {
                            mergeFrom(irCatch);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irCatch != null) {
                    mergeFrom(irCatch);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCatchOrBuilder
        public boolean hasCatchParameter() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCatchOrBuilder
        public IrVariable getCatchParameter() {
            return this.catchParameter_;
        }

        public Builder setCatchParameter(IrVariable irVariable) {
            irVariable.getClass();
            this.catchParameter_ = irVariable;
            this.bitField0_ |= 1;
            return this;
        }

        public Builder setCatchParameter(IrVariable.Builder builder) {
            this.catchParameter_ = builder.build();
            this.bitField0_ |= 1;
            return this;
        }

        public Builder mergeCatchParameter(IrVariable irVariable) {
            if ((this.bitField0_ & 1) == 1 && this.catchParameter_ != IrVariable.getDefaultInstance()) {
                this.catchParameter_ = IrVariable.newBuilder(this.catchParameter_).mergeFrom(irVariable).buildPartial();
            } else {
                this.catchParameter_ = irVariable;
            }
            this.bitField0_ |= 1;
            return this;
        }

        public Builder clearCatchParameter() {
            this.catchParameter_ = IrVariable.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCatchOrBuilder
        public boolean hasResult() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCatchOrBuilder
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
