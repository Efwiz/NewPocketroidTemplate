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
public final class NullableIrExpression extends GeneratedMessageLite implements NullableIrExpressionOrBuilder {
    public static final int EXPRESSION_FIELD_NUMBER = 1;
    public static Parser<NullableIrExpression> PARSER = new AbstractParser<NullableIrExpression>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.NullableIrExpression.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public NullableIrExpression parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new NullableIrExpression(codedInputStream, extensionRegistryLite);
        }
    };
    private static final NullableIrExpression defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private IrExpression expression_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    private NullableIrExpression(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private NullableIrExpression(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static NullableIrExpression getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public NullableIrExpression getDefaultInstanceForType() {
        return defaultInstance;
    }

    private NullableIrExpression(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                            if (tag != 10) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                IrExpression.Builder builder = (this.bitField0_ & 1) == 1 ? this.expression_.toBuilder() : null;
                                IrExpression irExpression = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                                this.expression_ = irExpression;
                                if (builder != null) {
                                    builder.mergeFrom(irExpression);
                                    this.expression_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 1;
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
        NullableIrExpression nullableIrExpression = new NullableIrExpression(true);
        defaultInstance = nullableIrExpression;
        nullableIrExpression.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<NullableIrExpression> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.NullableIrExpressionOrBuilder
    public boolean hasExpression() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.NullableIrExpressionOrBuilder
    public IrExpression getExpression() {
        return this.expression_;
    }

    private void initFields() {
        this.expression_ = IrExpression.getDefaultInstance();
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
        if (hasExpression() && !getExpression().isInitialized()) {
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
            codedOutputStream.writeMessage(1, this.expression_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = ((this.bitField0_ & 1) == 1 ? CodedOutputStream.computeMessageSize(1, this.expression_) : 0) + this.unknownFields.size();
        this.memoizedSerializedSize = iComputeMessageSize;
        return iComputeMessageSize;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static NullableIrExpression parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static NullableIrExpression parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static NullableIrExpression parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static NullableIrExpression parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static NullableIrExpression parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static NullableIrExpression parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static NullableIrExpression parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static NullableIrExpression parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static NullableIrExpression parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static NullableIrExpression parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(NullableIrExpression nullableIrExpression) {
        return newBuilder().mergeFrom(nullableIrExpression);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<NullableIrExpression, Builder> implements NullableIrExpressionOrBuilder {
        private int bitField0_;
        private IrExpression expression_ = IrExpression.getDefaultInstance();

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
            this.expression_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public NullableIrExpression getDefaultInstanceForType() {
            return NullableIrExpression.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public NullableIrExpression build() {
            NullableIrExpression nullableIrExpressionBuildPartial = buildPartial();
            if (nullableIrExpressionBuildPartial.isInitialized()) {
                return nullableIrExpressionBuildPartial;
            }
            throw newUninitializedMessageException(nullableIrExpressionBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public NullableIrExpression buildPartial() {
            NullableIrExpression nullableIrExpression = new NullableIrExpression(this);
            int i = (this.bitField0_ & 1) != 1 ? 0 : 1;
            nullableIrExpression.expression_ = this.expression_;
            nullableIrExpression.bitField0_ = i;
            return nullableIrExpression;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(NullableIrExpression nullableIrExpression) {
            if (nullableIrExpression == NullableIrExpression.getDefaultInstance()) {
                return this;
            }
            if (nullableIrExpression.hasExpression()) {
                mergeExpression(nullableIrExpression.getExpression());
            }
            setUnknownFields(getUnknownFields().concat(nullableIrExpression.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return !hasExpression() || getExpression().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            NullableIrExpression nullableIrExpression = null;
            try {
                try {
                    NullableIrExpression partialFrom = NullableIrExpression.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    NullableIrExpression nullableIrExpression2 = (NullableIrExpression) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        nullableIrExpression = nullableIrExpression2;
                        if (nullableIrExpression != null) {
                            mergeFrom(nullableIrExpression);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (nullableIrExpression != null) {
                    mergeFrom(nullableIrExpression);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.NullableIrExpressionOrBuilder
        public boolean hasExpression() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.NullableIrExpressionOrBuilder
        public IrExpression getExpression() {
            return this.expression_;
        }

        public Builder setExpression(IrExpression irExpression) {
            irExpression.getClass();
            this.expression_ = irExpression;
            this.bitField0_ |= 1;
            return this;
        }

        public Builder setExpression(IrExpression.Builder builder) {
            this.expression_ = builder.build();
            this.bitField0_ |= 1;
            return this;
        }

        public Builder mergeExpression(IrExpression irExpression) {
            if ((this.bitField0_ & 1) == 1 && this.expression_ != IrExpression.getDefaultInstance()) {
                this.expression_ = IrExpression.newBuilder(this.expression_).mergeFrom(irExpression).buildPartial();
            } else {
                this.expression_ = irExpression;
            }
            this.bitField0_ |= 1;
            return this;
        }

        public Builder clearExpression() {
            this.expression_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
        }
    }
}
