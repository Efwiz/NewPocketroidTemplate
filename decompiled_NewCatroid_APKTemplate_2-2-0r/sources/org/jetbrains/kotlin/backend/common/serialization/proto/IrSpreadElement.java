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
public final class IrSpreadElement extends GeneratedMessageLite implements IrSpreadElementOrBuilder {
    public static final int COORDINATES_FIELD_NUMBER = 2;
    public static final int EXPRESSION_FIELD_NUMBER = 1;
    public static Parser<IrSpreadElement> PARSER = new AbstractParser<IrSpreadElement>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrSpreadElement.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrSpreadElement parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrSpreadElement(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrSpreadElement defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private long coordinates_;
    private IrExpression expression_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    private IrSpreadElement(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrSpreadElement(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrSpreadElement getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrSpreadElement getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrSpreadElement(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                                IrExpression.Builder builder = (this.bitField0_ & 1) == 1 ? this.expression_.toBuilder() : null;
                                IrExpression irExpression = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                                this.expression_ = irExpression;
                                if (builder != null) {
                                    builder.mergeFrom(irExpression);
                                    this.expression_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 1;
                            } else if (tag != 16) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.bitField0_ |= 2;
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
        IrSpreadElement irSpreadElement = new IrSpreadElement(true);
        defaultInstance = irSpreadElement;
        irSpreadElement.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrSpreadElement> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSpreadElementOrBuilder
    public boolean hasExpression() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSpreadElementOrBuilder
    public IrExpression getExpression() {
        return this.expression_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSpreadElementOrBuilder
    public boolean hasCoordinates() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSpreadElementOrBuilder
    public long getCoordinates() {
        return this.coordinates_;
    }

    private void initFields() {
        this.expression_ = IrExpression.getDefaultInstance();
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
        if (!hasExpression()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasCoordinates()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getExpression().isInitialized()) {
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
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt64(2, this.coordinates_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeMessageSize(1, this.expression_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeMessageSize += CodedOutputStream.computeInt64Size(2, this.coordinates_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrSpreadElement parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrSpreadElement parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrSpreadElement parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrSpreadElement parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrSpreadElement parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrSpreadElement parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrSpreadElement parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrSpreadElement parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrSpreadElement parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrSpreadElement parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrSpreadElement irSpreadElement) {
        return newBuilder().mergeFrom(irSpreadElement);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrSpreadElement, Builder> implements IrSpreadElementOrBuilder {
        private int bitField0_;
        private long coordinates_;
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
            int i = this.bitField0_;
            this.coordinates_ = 0L;
            this.bitField0_ = i & (-4);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrSpreadElement getDefaultInstanceForType() {
            return IrSpreadElement.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrSpreadElement build() {
            IrSpreadElement irSpreadElementBuildPartial = buildPartial();
            if (irSpreadElementBuildPartial.isInitialized()) {
                return irSpreadElementBuildPartial;
            }
            throw newUninitializedMessageException(irSpreadElementBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrSpreadElement buildPartial() {
            IrSpreadElement irSpreadElement = new IrSpreadElement(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irSpreadElement.expression_ = this.expression_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irSpreadElement.coordinates_ = this.coordinates_;
            irSpreadElement.bitField0_ = i2;
            return irSpreadElement;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrSpreadElement irSpreadElement) {
            if (irSpreadElement == IrSpreadElement.getDefaultInstance()) {
                return this;
            }
            if (irSpreadElement.hasExpression()) {
                mergeExpression(irSpreadElement.getExpression());
            }
            if (irSpreadElement.hasCoordinates()) {
                setCoordinates(irSpreadElement.getCoordinates());
            }
            setUnknownFields(getUnknownFields().concat(irSpreadElement.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasExpression() && hasCoordinates() && getExpression().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrSpreadElement irSpreadElement = null;
            try {
                try {
                    IrSpreadElement partialFrom = IrSpreadElement.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrSpreadElement irSpreadElement2 = (IrSpreadElement) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irSpreadElement = irSpreadElement2;
                        if (irSpreadElement != null) {
                            mergeFrom(irSpreadElement);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irSpreadElement != null) {
                    mergeFrom(irSpreadElement);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSpreadElementOrBuilder
        public boolean hasExpression() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSpreadElementOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSpreadElementOrBuilder
        public boolean hasCoordinates() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSpreadElementOrBuilder
        public long getCoordinates() {
            return this.coordinates_;
        }

        public Builder setCoordinates(long j) {
            this.bitField0_ |= 2;
            this.coordinates_ = j;
            return this;
        }

        public Builder clearCoordinates() {
            this.bitField0_ &= -3;
            this.coordinates_ = 0L;
            return this;
        }
    }
}
