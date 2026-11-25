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
public final class FieldAccessCommon extends GeneratedMessageLite implements FieldAccessCommonOrBuilder {
    public static Parser<FieldAccessCommon> PARSER = new AbstractParser<FieldAccessCommon>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommon.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public FieldAccessCommon parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new FieldAccessCommon(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int RECEIVER_FIELD_NUMBER = 3;
    public static final int SUPER_FIELD_NUMBER = 2;
    public static final int SYMBOL_FIELD_NUMBER = 1;
    private static final FieldAccessCommon defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private IrExpression receiver_;
    private long super_;
    private long symbol_;
    private final ByteString unknownFields;

    private FieldAccessCommon(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private FieldAccessCommon(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static FieldAccessCommon getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public FieldAccessCommon getDefaultInstanceForType() {
        return defaultInstance;
    }

    private FieldAccessCommon(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                                this.symbol_ = codedInputStream.readInt64();
                            } else if (tag == 16) {
                                this.bitField0_ |= 2;
                                this.super_ = codedInputStream.readInt64();
                            } else if (tag != 26) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                IrExpression.Builder builder = (this.bitField0_ & 4) == 4 ? this.receiver_.toBuilder() : null;
                                IrExpression irExpression = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                                this.receiver_ = irExpression;
                                if (builder != null) {
                                    builder.mergeFrom(irExpression);
                                    this.receiver_ = builder.buildPartial();
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
        FieldAccessCommon fieldAccessCommon = new FieldAccessCommon(true);
        defaultInstance = fieldAccessCommon;
        fieldAccessCommon.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<FieldAccessCommon> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommonOrBuilder
    public boolean hasSymbol() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommonOrBuilder
    public long getSymbol() {
        return this.symbol_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommonOrBuilder
    public boolean hasSuper() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommonOrBuilder
    public long getSuper() {
        return this.super_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommonOrBuilder
    public boolean hasReceiver() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommonOrBuilder
    public IrExpression getReceiver() {
        return this.receiver_;
    }

    private void initFields() {
        this.symbol_ = 0L;
        this.super_ = 0L;
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
        if (!hasSymbol()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasReceiver() && !getReceiver().isInitialized()) {
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
            codedOutputStream.writeInt64(1, this.symbol_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt64(2, this.super_);
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeMessage(3, this.receiver_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt64Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt64Size(1, this.symbol_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeInt64Size += CodedOutputStream.computeInt64Size(2, this.super_);
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeInt64Size += CodedOutputStream.computeMessageSize(3, this.receiver_);
        }
        int size = iComputeInt64Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static FieldAccessCommon parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static FieldAccessCommon parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static FieldAccessCommon parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static FieldAccessCommon parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static FieldAccessCommon parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static FieldAccessCommon parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static FieldAccessCommon parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static FieldAccessCommon parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static FieldAccessCommon parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static FieldAccessCommon parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(FieldAccessCommon fieldAccessCommon) {
        return newBuilder().mergeFrom(fieldAccessCommon);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<FieldAccessCommon, Builder> implements FieldAccessCommonOrBuilder {
        private int bitField0_;
        private IrExpression receiver_ = IrExpression.getDefaultInstance();
        private long super_;
        private long symbol_;

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
            this.symbol_ = 0L;
            int i = this.bitField0_;
            this.super_ = 0L;
            this.bitField0_ = i & (-4);
            this.receiver_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -5;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public FieldAccessCommon getDefaultInstanceForType() {
            return FieldAccessCommon.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public FieldAccessCommon build() {
            FieldAccessCommon fieldAccessCommonBuildPartial = buildPartial();
            if (fieldAccessCommonBuildPartial.isInitialized()) {
                return fieldAccessCommonBuildPartial;
            }
            throw newUninitializedMessageException(fieldAccessCommonBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public FieldAccessCommon buildPartial() {
            FieldAccessCommon fieldAccessCommon = new FieldAccessCommon(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            fieldAccessCommon.symbol_ = this.symbol_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            fieldAccessCommon.super_ = this.super_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            fieldAccessCommon.receiver_ = this.receiver_;
            fieldAccessCommon.bitField0_ = i2;
            return fieldAccessCommon;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(FieldAccessCommon fieldAccessCommon) {
            if (fieldAccessCommon == FieldAccessCommon.getDefaultInstance()) {
                return this;
            }
            if (fieldAccessCommon.hasSymbol()) {
                setSymbol(fieldAccessCommon.getSymbol());
            }
            if (fieldAccessCommon.hasSuper()) {
                setSuper(fieldAccessCommon.getSuper());
            }
            if (fieldAccessCommon.hasReceiver()) {
                mergeReceiver(fieldAccessCommon.getReceiver());
            }
            setUnknownFields(getUnknownFields().concat(fieldAccessCommon.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (hasSymbol()) {
                return !hasReceiver() || getReceiver().isInitialized();
            }
            return false;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            FieldAccessCommon fieldAccessCommon = null;
            try {
                try {
                    FieldAccessCommon partialFrom = FieldAccessCommon.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FieldAccessCommon fieldAccessCommon2 = (FieldAccessCommon) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        fieldAccessCommon = fieldAccessCommon2;
                        if (fieldAccessCommon != null) {
                            mergeFrom(fieldAccessCommon);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (fieldAccessCommon != null) {
                    mergeFrom(fieldAccessCommon);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommonOrBuilder
        public boolean hasSymbol() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommonOrBuilder
        public long getSymbol() {
            return this.symbol_;
        }

        public Builder setSymbol(long j) {
            this.bitField0_ |= 1;
            this.symbol_ = j;
            return this;
        }

        public Builder clearSymbol() {
            this.bitField0_ &= -2;
            this.symbol_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommonOrBuilder
        public boolean hasSuper() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommonOrBuilder
        public long getSuper() {
            return this.super_;
        }

        public Builder setSuper(long j) {
            this.bitField0_ |= 2;
            this.super_ = j;
            return this;
        }

        public Builder clearSuper() {
            this.bitField0_ &= -3;
            this.super_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommonOrBuilder
        public boolean hasReceiver() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommonOrBuilder
        public IrExpression getReceiver() {
            return this.receiver_;
        }

        public Builder setReceiver(IrExpression irExpression) {
            irExpression.getClass();
            this.receiver_ = irExpression;
            this.bitField0_ |= 4;
            return this;
        }

        public Builder setReceiver(IrExpression.Builder builder) {
            this.receiver_ = builder.build();
            this.bitField0_ |= 4;
            return this;
        }

        public Builder mergeReceiver(IrExpression irExpression) {
            if ((this.bitField0_ & 4) == 4 && this.receiver_ != IrExpression.getDefaultInstance()) {
                this.receiver_ = IrExpression.newBuilder(this.receiver_).mergeFrom(irExpression).buildPartial();
            } else {
                this.receiver_ = irExpression;
            }
            this.bitField0_ |= 4;
            return this;
        }

        public Builder clearReceiver() {
            this.receiver_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -5;
            return this;
        }
    }
}
