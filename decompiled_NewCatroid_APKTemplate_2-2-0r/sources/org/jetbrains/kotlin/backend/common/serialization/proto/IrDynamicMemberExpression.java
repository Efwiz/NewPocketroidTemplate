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
public final class IrDynamicMemberExpression extends GeneratedMessageLite implements IrDynamicMemberExpressionOrBuilder {
    public static final int MEMBER_NAME_FIELD_NUMBER = 1;
    public static Parser<IrDynamicMemberExpression> PARSER = new AbstractParser<IrDynamicMemberExpression>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicMemberExpression.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrDynamicMemberExpression parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrDynamicMemberExpression(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int RECEIVER_FIELD_NUMBER = 2;
    private static final IrDynamicMemberExpression defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private int memberName_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private IrExpression receiver_;
    private final ByteString unknownFields;

    private IrDynamicMemberExpression(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrDynamicMemberExpression(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrDynamicMemberExpression getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrDynamicMemberExpression getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrDynamicMemberExpression(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                                this.bitField0_ |= 1;
                                this.memberName_ = codedInputStream.readInt32();
                            } else if (tag != 18) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                IrExpression.Builder builder = (this.bitField0_ & 2) == 2 ? this.receiver_.toBuilder() : null;
                                IrExpression irExpression = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                                this.receiver_ = irExpression;
                                if (builder != null) {
                                    builder.mergeFrom(irExpression);
                                    this.receiver_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 2;
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
        IrDynamicMemberExpression irDynamicMemberExpression = new IrDynamicMemberExpression(true);
        defaultInstance = irDynamicMemberExpression;
        irDynamicMemberExpression.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrDynamicMemberExpression> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicMemberExpressionOrBuilder
    public boolean hasMemberName() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicMemberExpressionOrBuilder
    public int getMemberName() {
        return this.memberName_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicMemberExpressionOrBuilder
    public boolean hasReceiver() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicMemberExpressionOrBuilder
    public IrExpression getReceiver() {
        return this.receiver_;
    }

    private void initFields() {
        this.memberName_ = 0;
        this.receiver_ = IrExpression.getDefaultInstance();
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
        if (!hasMemberName()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasReceiver()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getReceiver().isInitialized()) {
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
            codedOutputStream.writeInt32(1, this.memberName_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeMessage(2, this.receiver_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.memberName_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeInt32Size += CodedOutputStream.computeMessageSize(2, this.receiver_);
        }
        int size = iComputeInt32Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrDynamicMemberExpression parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrDynamicMemberExpression parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrDynamicMemberExpression parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrDynamicMemberExpression parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrDynamicMemberExpression parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrDynamicMemberExpression parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrDynamicMemberExpression parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrDynamicMemberExpression parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrDynamicMemberExpression parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrDynamicMemberExpression parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrDynamicMemberExpression irDynamicMemberExpression) {
        return newBuilder().mergeFrom(irDynamicMemberExpression);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrDynamicMemberExpression, Builder> implements IrDynamicMemberExpressionOrBuilder {
        private int bitField0_;
        private int memberName_;
        private IrExpression receiver_ = IrExpression.getDefaultInstance();

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
            this.memberName_ = 0;
            this.bitField0_ &= -2;
            this.receiver_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -3;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrDynamicMemberExpression getDefaultInstanceForType() {
            return IrDynamicMemberExpression.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrDynamicMemberExpression build() {
            IrDynamicMemberExpression irDynamicMemberExpressionBuildPartial = buildPartial();
            if (irDynamicMemberExpressionBuildPartial.isInitialized()) {
                return irDynamicMemberExpressionBuildPartial;
            }
            throw newUninitializedMessageException(irDynamicMemberExpressionBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrDynamicMemberExpression buildPartial() {
            IrDynamicMemberExpression irDynamicMemberExpression = new IrDynamicMemberExpression(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irDynamicMemberExpression.memberName_ = this.memberName_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irDynamicMemberExpression.receiver_ = this.receiver_;
            irDynamicMemberExpression.bitField0_ = i2;
            return irDynamicMemberExpression;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrDynamicMemberExpression irDynamicMemberExpression) {
            if (irDynamicMemberExpression == IrDynamicMemberExpression.getDefaultInstance()) {
                return this;
            }
            if (irDynamicMemberExpression.hasMemberName()) {
                setMemberName(irDynamicMemberExpression.getMemberName());
            }
            if (irDynamicMemberExpression.hasReceiver()) {
                mergeReceiver(irDynamicMemberExpression.getReceiver());
            }
            setUnknownFields(getUnknownFields().concat(irDynamicMemberExpression.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasMemberName() && hasReceiver() && getReceiver().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrDynamicMemberExpression irDynamicMemberExpression = null;
            try {
                try {
                    IrDynamicMemberExpression partialFrom = IrDynamicMemberExpression.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrDynamicMemberExpression irDynamicMemberExpression2 = (IrDynamicMemberExpression) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irDynamicMemberExpression = irDynamicMemberExpression2;
                        if (irDynamicMemberExpression != null) {
                            mergeFrom(irDynamicMemberExpression);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irDynamicMemberExpression != null) {
                    mergeFrom(irDynamicMemberExpression);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicMemberExpressionOrBuilder
        public boolean hasMemberName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicMemberExpressionOrBuilder
        public int getMemberName() {
            return this.memberName_;
        }

        public Builder setMemberName(int i) {
            this.bitField0_ |= 1;
            this.memberName_ = i;
            return this;
        }

        public Builder clearMemberName() {
            this.bitField0_ &= -2;
            this.memberName_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicMemberExpressionOrBuilder
        public boolean hasReceiver() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicMemberExpressionOrBuilder
        public IrExpression getReceiver() {
            return this.receiver_;
        }

        public Builder setReceiver(IrExpression irExpression) {
            irExpression.getClass();
            this.receiver_ = irExpression;
            this.bitField0_ |= 2;
            return this;
        }

        public Builder setReceiver(IrExpression.Builder builder) {
            this.receiver_ = builder.build();
            this.bitField0_ |= 2;
            return this;
        }

        public Builder mergeReceiver(IrExpression irExpression) {
            if ((this.bitField0_ & 2) == 2 && this.receiver_ != IrExpression.getDefaultInstance()) {
                this.receiver_ = IrExpression.newBuilder(this.receiver_).mergeFrom(irExpression).buildPartial();
            } else {
                this.receiver_ = irExpression;
            }
            this.bitField0_ |= 2;
            return this;
        }

        public Builder clearReceiver() {
            this.receiver_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -3;
            return this;
        }
    }
}
