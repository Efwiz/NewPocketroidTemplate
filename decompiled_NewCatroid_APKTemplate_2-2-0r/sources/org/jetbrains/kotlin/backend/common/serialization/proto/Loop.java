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
public final class Loop extends GeneratedMessageLite implements LoopOrBuilder {
    public static final int BODY_FIELD_NUMBER = 4;
    public static final int CONDITION_FIELD_NUMBER = 2;
    public static final int LABEL_FIELD_NUMBER = 3;
    public static final int LOOP_ID_FIELD_NUMBER = 1;
    public static final int ORIGIN_NAME_FIELD_NUMBER = 5;
    public static Parser<Loop> PARSER = new AbstractParser<Loop>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.Loop.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public Loop parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new Loop(codedInputStream, extensionRegistryLite);
        }
    };
    private static final Loop defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private IrExpression body_;
    private IrExpression condition_;
    private int label_;
    private int loopId_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int originName_;
    private final ByteString unknownFields;

    private Loop(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private Loop(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static Loop getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public Loop getDefaultInstanceForType() {
        return defaultInstance;
    }

    private Loop(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag != 8) {
                                if (tag == 18) {
                                    builder = (this.bitField0_ & 2) == 2 ? this.condition_.toBuilder() : null;
                                    IrExpression irExpression = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                                    this.condition_ = irExpression;
                                    if (builder != null) {
                                        builder.mergeFrom(irExpression);
                                        this.condition_ = builder.buildPartial();
                                    }
                                    this.bitField0_ |= 2;
                                } else if (tag == 24) {
                                    this.bitField0_ |= 4;
                                    this.label_ = codedInputStream.readInt32();
                                } else if (tag == 34) {
                                    builder = (this.bitField0_ & 8) == 8 ? this.body_.toBuilder() : null;
                                    IrExpression irExpression2 = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                                    this.body_ = irExpression2;
                                    if (builder != null) {
                                        builder.mergeFrom(irExpression2);
                                        this.body_ = builder.buildPartial();
                                    }
                                    this.bitField0_ |= 8;
                                } else if (tag != 40) {
                                    if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    this.bitField0_ |= 16;
                                    this.originName_ = codedInputStream.readInt32();
                                }
                            } else {
                                this.bitField0_ |= 1;
                                this.loopId_ = codedInputStream.readInt32();
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
        Loop loop = new Loop(true);
        defaultInstance = loop;
        loop.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<Loop> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
    public boolean hasLoopId() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
    public int getLoopId() {
        return this.loopId_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
    public boolean hasCondition() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
    public IrExpression getCondition() {
        return this.condition_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
    public boolean hasLabel() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
    public int getLabel() {
        return this.label_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
    public boolean hasBody() {
        return (this.bitField0_ & 8) == 8;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
    public IrExpression getBody() {
        return this.body_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
    public boolean hasOriginName() {
        return (this.bitField0_ & 16) == 16;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
    public int getOriginName() {
        return this.originName_;
    }

    private void initFields() {
        this.loopId_ = 0;
        this.condition_ = IrExpression.getDefaultInstance();
        this.label_ = 0;
        this.body_ = IrExpression.getDefaultInstance();
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
        if (!hasLoopId()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasCondition()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getCondition().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasBody() && !getBody().isInitialized()) {
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
            codedOutputStream.writeInt32(1, this.loopId_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeMessage(2, this.condition_);
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeInt32(3, this.label_);
        }
        if ((this.bitField0_ & 8) == 8) {
            codedOutputStream.writeMessage(4, this.body_);
        }
        if ((this.bitField0_ & 16) == 16) {
            codedOutputStream.writeInt32(5, this.originName_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.loopId_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeInt32Size += CodedOutputStream.computeMessageSize(2, this.condition_);
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeInt32Size += CodedOutputStream.computeInt32Size(3, this.label_);
        }
        if ((this.bitField0_ & 8) == 8) {
            iComputeInt32Size += CodedOutputStream.computeMessageSize(4, this.body_);
        }
        if ((this.bitField0_ & 16) == 16) {
            iComputeInt32Size += CodedOutputStream.computeInt32Size(5, this.originName_);
        }
        int size = iComputeInt32Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static Loop parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static Loop parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static Loop parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static Loop parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static Loop parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static Loop parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static Loop parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static Loop parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static Loop parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static Loop parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(Loop loop) {
        return newBuilder().mergeFrom(loop);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Loop, Builder> implements LoopOrBuilder {
        private int bitField0_;
        private int label_;
        private int loopId_;
        private int originName_;
        private IrExpression condition_ = IrExpression.getDefaultInstance();
        private IrExpression body_ = IrExpression.getDefaultInstance();

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
            this.loopId_ = 0;
            this.bitField0_ &= -2;
            this.condition_ = IrExpression.getDefaultInstance();
            int i = this.bitField0_;
            this.label_ = 0;
            this.bitField0_ = i & (-7);
            this.body_ = IrExpression.getDefaultInstance();
            int i2 = this.bitField0_;
            this.originName_ = 0;
            this.bitField0_ = i2 & (-25);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public Loop getDefaultInstanceForType() {
            return Loop.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Loop build() {
            Loop loopBuildPartial = buildPartial();
            if (loopBuildPartial.isInitialized()) {
                return loopBuildPartial;
            }
            throw newUninitializedMessageException(loopBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Loop buildPartial() {
            Loop loop = new Loop(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            loop.loopId_ = this.loopId_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            loop.condition_ = this.condition_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            loop.label_ = this.label_;
            if ((i & 8) == 8) {
                i2 |= 8;
            }
            loop.body_ = this.body_;
            if ((i & 16) == 16) {
                i2 |= 16;
            }
            loop.originName_ = this.originName_;
            loop.bitField0_ = i2;
            return loop;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(Loop loop) {
            if (loop == Loop.getDefaultInstance()) {
                return this;
            }
            if (loop.hasLoopId()) {
                setLoopId(loop.getLoopId());
            }
            if (loop.hasCondition()) {
                mergeCondition(loop.getCondition());
            }
            if (loop.hasLabel()) {
                setLabel(loop.getLabel());
            }
            if (loop.hasBody()) {
                mergeBody(loop.getBody());
            }
            if (loop.hasOriginName()) {
                setOriginName(loop.getOriginName());
            }
            setUnknownFields(getUnknownFields().concat(loop.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (hasLoopId() && hasCondition() && getCondition().isInitialized()) {
                return !hasBody() || getBody().isInitialized();
            }
            return false;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            Loop loop = null;
            try {
                try {
                    Loop partialFrom = Loop.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    Loop loop2 = (Loop) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        loop = loop2;
                        if (loop != null) {
                            mergeFrom(loop);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (loop != null) {
                    mergeFrom(loop);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
        public boolean hasLoopId() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
        public int getLoopId() {
            return this.loopId_;
        }

        public Builder setLoopId(int i) {
            this.bitField0_ |= 1;
            this.loopId_ = i;
            return this;
        }

        public Builder clearLoopId() {
            this.bitField0_ &= -2;
            this.loopId_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
        public boolean hasCondition() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
        public IrExpression getCondition() {
            return this.condition_;
        }

        public Builder setCondition(IrExpression irExpression) {
            irExpression.getClass();
            this.condition_ = irExpression;
            this.bitField0_ |= 2;
            return this;
        }

        public Builder setCondition(IrExpression.Builder builder) {
            this.condition_ = builder.build();
            this.bitField0_ |= 2;
            return this;
        }

        public Builder mergeCondition(IrExpression irExpression) {
            if ((this.bitField0_ & 2) == 2 && this.condition_ != IrExpression.getDefaultInstance()) {
                this.condition_ = IrExpression.newBuilder(this.condition_).mergeFrom(irExpression).buildPartial();
            } else {
                this.condition_ = irExpression;
            }
            this.bitField0_ |= 2;
            return this;
        }

        public Builder clearCondition() {
            this.condition_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -3;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
        public boolean hasLabel() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
        public int getLabel() {
            return this.label_;
        }

        public Builder setLabel(int i) {
            this.bitField0_ |= 4;
            this.label_ = i;
            return this;
        }

        public Builder clearLabel() {
            this.bitField0_ &= -5;
            this.label_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
        public boolean hasBody() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
        public IrExpression getBody() {
            return this.body_;
        }

        public Builder setBody(IrExpression irExpression) {
            irExpression.getClass();
            this.body_ = irExpression;
            this.bitField0_ |= 8;
            return this;
        }

        public Builder setBody(IrExpression.Builder builder) {
            this.body_ = builder.build();
            this.bitField0_ |= 8;
            return this;
        }

        public Builder mergeBody(IrExpression irExpression) {
            if ((this.bitField0_ & 8) == 8 && this.body_ != IrExpression.getDefaultInstance()) {
                this.body_ = IrExpression.newBuilder(this.body_).mergeFrom(irExpression).buildPartial();
            } else {
                this.body_ = irExpression;
            }
            this.bitField0_ |= 8;
            return this;
        }

        public Builder clearBody() {
            this.body_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -9;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
        public boolean hasOriginName() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.LoopOrBuilder
        public int getOriginName() {
            return this.originName_;
        }

        public Builder setOriginName(int i) {
            this.bitField0_ |= 16;
            this.originName_ = i;
            return this;
        }

        public Builder clearOriginName() {
            this.bitField0_ &= -17;
            this.originName_ = 0;
            return this;
        }
    }
}
