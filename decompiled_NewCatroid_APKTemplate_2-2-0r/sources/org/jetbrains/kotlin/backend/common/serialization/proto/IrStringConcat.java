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
public final class IrStringConcat extends GeneratedMessageLite implements IrStringConcatOrBuilder {
    public static final int ARGUMENT_FIELD_NUMBER = 1;
    public static Parser<IrStringConcat> PARSER = new AbstractParser<IrStringConcat>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrStringConcat.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrStringConcat parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrStringConcat(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrStringConcat defaultInstance;
    private static final long serialVersionUID = 0;
    private List<IrExpression> argument_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    private IrStringConcat(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrStringConcat(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrStringConcat getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrStringConcat getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrStringConcat(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        initFields();
        ByteString.Output outputNewOutput = ByteString.newOutput();
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
        boolean z = false;
        boolean z2 = false;
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
                                if (!z2) {
                                    this.argument_ = new ArrayList();
                                    z2 = true;
                                }
                                this.argument_.add((IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite));
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
                if (z2) {
                    this.argument_ = Collections.unmodifiableList(this.argument_);
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
        if (z2) {
            this.argument_ = Collections.unmodifiableList(this.argument_);
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
        IrStringConcat irStringConcat = new IrStringConcat(true);
        defaultInstance = irStringConcat;
        irStringConcat.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrStringConcat> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStringConcatOrBuilder
    public List<IrExpression> getArgumentList() {
        return this.argument_;
    }

    public List<? extends IrExpressionOrBuilder> getArgumentOrBuilderList() {
        return this.argument_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStringConcatOrBuilder
    public int getArgumentCount() {
        return this.argument_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStringConcatOrBuilder
    public IrExpression getArgument(int i) {
        return this.argument_.get(i);
    }

    public IrExpressionOrBuilder getArgumentOrBuilder(int i) {
        return this.argument_.get(i);
    }

    private void initFields() {
        this.argument_ = Collections.emptyList();
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
        for (int i = 0; i < getArgumentCount(); i++) {
            if (!getArgument(i).isInitialized()) {
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
        for (int i = 0; i < this.argument_.size(); i++) {
            codedOutputStream.writeMessage(1, this.argument_.get(i));
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = 0;
        for (int i2 = 0; i2 < this.argument_.size(); i2++) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.argument_.get(i2));
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrStringConcat parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrStringConcat parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrStringConcat parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrStringConcat parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrStringConcat parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrStringConcat parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrStringConcat parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrStringConcat parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrStringConcat parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrStringConcat parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrStringConcat irStringConcat) {
        return newBuilder().mergeFrom(irStringConcat);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrStringConcat, Builder> implements IrStringConcatOrBuilder {
        private List<IrExpression> argument_ = Collections.emptyList();
        private int bitField0_;

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
            this.argument_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrStringConcat getDefaultInstanceForType() {
            return IrStringConcat.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrStringConcat build() {
            IrStringConcat irStringConcatBuildPartial = buildPartial();
            if (irStringConcatBuildPartial.isInitialized()) {
                return irStringConcatBuildPartial;
            }
            throw newUninitializedMessageException(irStringConcatBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrStringConcat buildPartial() {
            IrStringConcat irStringConcat = new IrStringConcat(this);
            if ((this.bitField0_ & 1) == 1) {
                this.argument_ = Collections.unmodifiableList(this.argument_);
                this.bitField0_ &= -2;
            }
            irStringConcat.argument_ = this.argument_;
            return irStringConcat;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrStringConcat irStringConcat) {
            if (irStringConcat == IrStringConcat.getDefaultInstance()) {
                return this;
            }
            if (!irStringConcat.argument_.isEmpty()) {
                if (this.argument_.isEmpty()) {
                    this.argument_ = irStringConcat.argument_;
                    this.bitField0_ &= -2;
                } else {
                    ensureArgumentIsMutable();
                    this.argument_.addAll(irStringConcat.argument_);
                }
            }
            setUnknownFields(getUnknownFields().concat(irStringConcat.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            for (int i = 0; i < getArgumentCount(); i++) {
                if (!getArgument(i).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrStringConcat irStringConcat = null;
            try {
                try {
                    IrStringConcat partialFrom = IrStringConcat.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrStringConcat irStringConcat2 = (IrStringConcat) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irStringConcat = irStringConcat2;
                        if (irStringConcat != null) {
                            mergeFrom(irStringConcat);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irStringConcat != null) {
                    mergeFrom(irStringConcat);
                }
                throw th;
            }
        }

        private void ensureArgumentIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
                this.argument_ = new ArrayList(this.argument_);
                this.bitField0_ |= 1;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStringConcatOrBuilder
        public List<IrExpression> getArgumentList() {
            return Collections.unmodifiableList(this.argument_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStringConcatOrBuilder
        public int getArgumentCount() {
            return this.argument_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStringConcatOrBuilder
        public IrExpression getArgument(int i) {
            return this.argument_.get(i);
        }

        public Builder setArgument(int i, IrExpression irExpression) {
            irExpression.getClass();
            ensureArgumentIsMutable();
            this.argument_.set(i, irExpression);
            return this;
        }

        public Builder setArgument(int i, IrExpression.Builder builder) {
            ensureArgumentIsMutable();
            this.argument_.set(i, builder.build());
            return this;
        }

        public Builder addArgument(IrExpression irExpression) {
            irExpression.getClass();
            ensureArgumentIsMutable();
            this.argument_.add(irExpression);
            return this;
        }

        public Builder addArgument(int i, IrExpression irExpression) {
            irExpression.getClass();
            ensureArgumentIsMutable();
            this.argument_.add(i, irExpression);
            return this;
        }

        public Builder addArgument(IrExpression.Builder builder) {
            ensureArgumentIsMutable();
            this.argument_.add(builder.build());
            return this;
        }

        public Builder addArgument(int i, IrExpression.Builder builder) {
            ensureArgumentIsMutable();
            this.argument_.add(i, builder.build());
            return this;
        }

        public Builder addAllArgument(Iterable<? extends IrExpression> iterable) {
            ensureArgumentIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.argument_);
            return this;
        }

        public Builder clearArgument() {
            this.argument_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
        }

        public Builder removeArgument(int i) {
            ensureArgumentIsMutable();
            this.argument_.remove(i);
            return this;
        }
    }
}
