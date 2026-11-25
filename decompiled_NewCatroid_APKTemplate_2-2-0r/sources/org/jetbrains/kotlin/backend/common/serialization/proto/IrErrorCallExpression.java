package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.protobuf.AbstractMessageLite;
import org.jetbrains.kotlin.protobuf.AbstractParser;
import org.jetbrains.kotlin.protobuf.ByteString;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.CodedOutputStream;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.protobuf.Parser;

/* JADX INFO: loaded from: classes2.dex */
public final class IrErrorCallExpression extends GeneratedMessageLite implements IrErrorCallExpressionOrBuilder {
    public static final int DESCRIPTION_FIELD_NUMBER = 1;
    public static Parser<IrErrorCallExpression> PARSER = new AbstractParser<IrErrorCallExpression>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpression.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrErrorCallExpression parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrErrorCallExpression(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int RECEIVER_FIELD_NUMBER = 2;
    public static final int VALUE_ARGUMENT_FIELD_NUMBER = 3;
    private static final IrErrorCallExpression defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private int description_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private IrExpression receiver_;
    private final ByteString unknownFields;
    private List<IrExpression> valueArgument_;

    private IrErrorCallExpression(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrErrorCallExpression(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrErrorCallExpression getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrErrorCallExpression getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrErrorCallExpression(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        initFields();
        ByteString.Output outputNewOutput = ByteString.newOutput();
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
        boolean z = false;
        char c = 0;
        while (!z) {
            try {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 8) {
                                this.bitField0_ |= 1;
                                this.description_ = codedInputStream.readInt32();
                            } else if (tag == 18) {
                                IrExpression.Builder builder = (this.bitField0_ & 2) == 2 ? this.receiver_.toBuilder() : null;
                                IrExpression irExpression = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                                this.receiver_ = irExpression;
                                if (builder != null) {
                                    builder.mergeFrom(irExpression);
                                    this.receiver_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 2;
                            } else if (tag != 26) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                if ((c & 4) != 4) {
                                    this.valueArgument_ = new ArrayList();
                                    c = 4;
                                }
                                this.valueArgument_.add((IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite));
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
                if ((c & 4) == 4) {
                    this.valueArgument_ = Collections.unmodifiableList(this.valueArgument_);
                }
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
        if ((c & 4) == 4) {
            this.valueArgument_ = Collections.unmodifiableList(this.valueArgument_);
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
        IrErrorCallExpression irErrorCallExpression = new IrErrorCallExpression(true);
        defaultInstance = irErrorCallExpression;
        irErrorCallExpression.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrErrorCallExpression> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpressionOrBuilder
    public boolean hasDescription() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpressionOrBuilder
    public int getDescription() {
        return this.description_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpressionOrBuilder
    public boolean hasReceiver() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpressionOrBuilder
    public IrExpression getReceiver() {
        return this.receiver_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpressionOrBuilder
    public List<IrExpression> getValueArgumentList() {
        return this.valueArgument_;
    }

    public List<? extends IrExpressionOrBuilder> getValueArgumentOrBuilderList() {
        return this.valueArgument_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpressionOrBuilder
    public int getValueArgumentCount() {
        return this.valueArgument_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpressionOrBuilder
    public IrExpression getValueArgument(int i) {
        return this.valueArgument_.get(i);
    }

    public IrExpressionOrBuilder getValueArgumentOrBuilder(int i) {
        return this.valueArgument_.get(i);
    }

    private void initFields() {
        this.description_ = 0;
        this.receiver_ = IrExpression.getDefaultInstance();
        this.valueArgument_ = Collections.emptyList();
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
        if (!hasDescription()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasReceiver() && !getReceiver().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        for (int i = 0; i < getValueArgumentCount(); i++) {
            if (!getValueArgument(i).isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
        }
        this.memoizedIsInitialized = (byte) 1;
        return true;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        getSerializedSize();
        if ((this.bitField0_ & 1) == 1) {
            codedOutputStream.writeInt32(1, this.description_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeMessage(2, this.receiver_);
        }
        for (int i = 0; i < this.valueArgument_.size(); i++) {
            codedOutputStream.writeMessage(3, this.valueArgument_.get(i));
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.description_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeInt32Size += CodedOutputStream.computeMessageSize(2, this.receiver_);
        }
        for (int i2 = 0; i2 < this.valueArgument_.size(); i2++) {
            iComputeInt32Size += CodedOutputStream.computeMessageSize(3, this.valueArgument_.get(i2));
        }
        int size = iComputeInt32Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrErrorCallExpression parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrErrorCallExpression parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrErrorCallExpression parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrErrorCallExpression parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrErrorCallExpression parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrErrorCallExpression parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrErrorCallExpression parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrErrorCallExpression parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrErrorCallExpression parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrErrorCallExpression parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrErrorCallExpression irErrorCallExpression) {
        return newBuilder().mergeFrom(irErrorCallExpression);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrErrorCallExpression, Builder> implements IrErrorCallExpressionOrBuilder {
        private int bitField0_;
        private int description_;
        private IrExpression receiver_ = IrExpression.getDefaultInstance();
        private List<IrExpression> valueArgument_ = Collections.emptyList();

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
            this.description_ = 0;
            this.bitField0_ &= -2;
            this.receiver_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -3;
            this.valueArgument_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrErrorCallExpression getDefaultInstanceForType() {
            return IrErrorCallExpression.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrErrorCallExpression build() {
            IrErrorCallExpression irErrorCallExpressionBuildPartial = buildPartial();
            if (irErrorCallExpressionBuildPartial.isInitialized()) {
                return irErrorCallExpressionBuildPartial;
            }
            throw newUninitializedMessageException(irErrorCallExpressionBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrErrorCallExpression buildPartial() {
            IrErrorCallExpression irErrorCallExpression = new IrErrorCallExpression(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irErrorCallExpression.description_ = this.description_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irErrorCallExpression.receiver_ = this.receiver_;
            if ((this.bitField0_ & 4) == 4) {
                this.valueArgument_ = Collections.unmodifiableList(this.valueArgument_);
                this.bitField0_ &= -5;
            }
            irErrorCallExpression.valueArgument_ = this.valueArgument_;
            irErrorCallExpression.bitField0_ = i2;
            return irErrorCallExpression;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrErrorCallExpression irErrorCallExpression) {
            if (irErrorCallExpression == IrErrorCallExpression.getDefaultInstance()) {
                return this;
            }
            if (irErrorCallExpression.hasDescription()) {
                setDescription(irErrorCallExpression.getDescription());
            }
            if (irErrorCallExpression.hasReceiver()) {
                mergeReceiver(irErrorCallExpression.getReceiver());
            }
            if (!irErrorCallExpression.valueArgument_.isEmpty()) {
                if (this.valueArgument_.isEmpty()) {
                    this.valueArgument_ = irErrorCallExpression.valueArgument_;
                    this.bitField0_ &= -5;
                } else {
                    ensureValueArgumentIsMutable();
                    this.valueArgument_.addAll(irErrorCallExpression.valueArgument_);
                }
            }
            setUnknownFields(getUnknownFields().concat(irErrorCallExpression.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (!hasDescription()) {
                return false;
            }
            if (hasReceiver() && !getReceiver().isInitialized()) {
                return false;
            }
            for (int i = 0; i < getValueArgumentCount(); i++) {
                if (!getValueArgument(i).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrErrorCallExpression irErrorCallExpression = null;
            try {
                try {
                    IrErrorCallExpression partialFrom = IrErrorCallExpression.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrErrorCallExpression irErrorCallExpression2 = (IrErrorCallExpression) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irErrorCallExpression = irErrorCallExpression2;
                        if (irErrorCallExpression != null) {
                            mergeFrom(irErrorCallExpression);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irErrorCallExpression != null) {
                    mergeFrom(irErrorCallExpression);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpressionOrBuilder
        public boolean hasDescription() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpressionOrBuilder
        public int getDescription() {
            return this.description_;
        }

        public Builder setDescription(int i) {
            this.bitField0_ |= 1;
            this.description_ = i;
            return this;
        }

        public Builder clearDescription() {
            this.bitField0_ &= -2;
            this.description_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpressionOrBuilder
        public boolean hasReceiver() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpressionOrBuilder
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

        private void ensureValueArgumentIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
                this.valueArgument_ = new ArrayList(this.valueArgument_);
                this.bitField0_ |= 4;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpressionOrBuilder
        public List<IrExpression> getValueArgumentList() {
            return Collections.unmodifiableList(this.valueArgument_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpressionOrBuilder
        public int getValueArgumentCount() {
            return this.valueArgument_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpressionOrBuilder
        public IrExpression getValueArgument(int i) {
            return this.valueArgument_.get(i);
        }

        public Builder setValueArgument(int i, IrExpression irExpression) {
            irExpression.getClass();
            ensureValueArgumentIsMutable();
            this.valueArgument_.set(i, irExpression);
            return this;
        }

        public Builder setValueArgument(int i, IrExpression.Builder builder) {
            ensureValueArgumentIsMutable();
            this.valueArgument_.set(i, builder.build());
            return this;
        }

        public Builder addValueArgument(IrExpression irExpression) {
            irExpression.getClass();
            ensureValueArgumentIsMutable();
            this.valueArgument_.add(irExpression);
            return this;
        }

        public Builder addValueArgument(int i, IrExpression irExpression) {
            irExpression.getClass();
            ensureValueArgumentIsMutable();
            this.valueArgument_.add(i, irExpression);
            return this;
        }

        public Builder addValueArgument(IrExpression.Builder builder) {
            ensureValueArgumentIsMutable();
            this.valueArgument_.add(builder.build());
            return this;
        }

        public Builder addValueArgument(int i, IrExpression.Builder builder) {
            ensureValueArgumentIsMutable();
            this.valueArgument_.add(i, builder.build());
            return this;
        }

        public Builder addAllValueArgument(Iterable<? extends IrExpression> iterable) {
            ensureValueArgumentIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.valueArgument_);
            return this;
        }

        public Builder clearValueArgument() {
            this.valueArgument_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
        }

        public Builder removeValueArgument(int i) {
            ensureValueArgumentIsMutable();
            this.valueArgument_.remove(i);
            return this;
        }
    }
}
