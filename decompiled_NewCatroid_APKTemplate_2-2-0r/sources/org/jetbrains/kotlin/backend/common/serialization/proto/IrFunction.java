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
public final class IrFunction extends GeneratedMessageLite implements IrFunctionOrBuilder {
    public static final int BASE_FIELD_NUMBER = 1;
    public static final int OVERRIDDEN_FIELD_NUMBER = 2;
    public static Parser<IrFunction> PARSER = new AbstractParser<IrFunction>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrFunction.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrFunction parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrFunction(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrFunction defaultInstance;
    private static final long serialVersionUID = 0;
    private IrFunctionBase base_;
    private int bitField0_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int overriddenMemoizedSerializedSize;
    private List<Long> overridden_;
    private final ByteString unknownFields;

    private IrFunction(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.overriddenMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrFunction(boolean z) {
        this.overriddenMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrFunction getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrFunction getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrFunction(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.overriddenMemoizedSerializedSize = -1;
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
                    int tag = codedInputStream.readTag();
                    if (tag != 0) {
                        if (tag == 10) {
                            IrFunctionBase.Builder builder = (this.bitField0_ & 1) == 1 ? this.base_.toBuilder() : null;
                            IrFunctionBase irFunctionBase = (IrFunctionBase) codedInputStream.readMessage(IrFunctionBase.PARSER, extensionRegistryLite);
                            this.base_ = irFunctionBase;
                            if (builder != null) {
                                builder.mergeFrom(irFunctionBase);
                                this.base_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                        } else if (tag == 16) {
                            if ((c & 2) != 2) {
                                this.overridden_ = new ArrayList();
                                c = 2;
                            }
                            this.overridden_.add(Long.valueOf(codedInputStream.readInt64()));
                        } else if (tag != 18) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                            if ((c & 2) != 2 && codedInputStream.getBytesUntilLimit() > 0) {
                                this.overridden_ = new ArrayList();
                                c = 2;
                            }
                            while (codedInputStream.getBytesUntilLimit() > 0) {
                                this.overridden_.add(Long.valueOf(codedInputStream.readInt64()));
                            }
                            codedInputStream.popLimit(iPushLimit);
                        }
                    }
                    z = true;
                } catch (Throwable th) {
                    if ((c & 2) == 2) {
                        this.overridden_ = Collections.unmodifiableList(this.overridden_);
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
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
            }
        }
        if ((c & 2) == 2) {
            this.overridden_ = Collections.unmodifiableList(this.overridden_);
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
        IrFunction irFunction = new IrFunction(true);
        defaultInstance = irFunction;
        irFunction.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrFunction> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionOrBuilder
    public boolean hasBase() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionOrBuilder
    public IrFunctionBase getBase() {
        return this.base_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionOrBuilder
    public List<Long> getOverriddenList() {
        return this.overridden_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionOrBuilder
    public int getOverriddenCount() {
        return this.overridden_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionOrBuilder
    public long getOverridden(int i) {
        return this.overridden_.get(i).longValue();
    }

    private void initFields() {
        this.base_ = IrFunctionBase.getDefaultInstance();
        this.overridden_ = Collections.emptyList();
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
        if (!hasBase()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getBase().isInitialized()) {
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
            codedOutputStream.writeMessage(1, this.base_);
        }
        if (getOverriddenList().size() > 0) {
            codedOutputStream.writeRawVarint32(18);
            codedOutputStream.writeRawVarint32(this.overriddenMemoizedSerializedSize);
        }
        for (int i = 0; i < this.overridden_.size(); i++) {
            codedOutputStream.writeInt64NoTag(this.overridden_.get(i).longValue());
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeMessageSize(1, this.base_) : 0;
        int iComputeInt64SizeNoTag = 0;
        for (int i2 = 0; i2 < this.overridden_.size(); i2++) {
            iComputeInt64SizeNoTag += CodedOutputStream.computeInt64SizeNoTag(this.overridden_.get(i2).longValue());
        }
        int iComputeInt32SizeNoTag = iComputeMessageSize + iComputeInt64SizeNoTag;
        if (!getOverriddenList().isEmpty()) {
            iComputeInt32SizeNoTag = iComputeInt32SizeNoTag + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt64SizeNoTag);
        }
        this.overriddenMemoizedSerializedSize = iComputeInt64SizeNoTag;
        int size = iComputeInt32SizeNoTag + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrFunction parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrFunction parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrFunction parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrFunction parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrFunction parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrFunction parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrFunction parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrFunction parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrFunction parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrFunction parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrFunction irFunction) {
        return newBuilder().mergeFrom(irFunction);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrFunction, Builder> implements IrFunctionOrBuilder {
        private int bitField0_;
        private IrFunctionBase base_ = IrFunctionBase.getDefaultInstance();
        private List<Long> overridden_ = Collections.emptyList();

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
            this.base_ = IrFunctionBase.getDefaultInstance();
            this.bitField0_ &= -2;
            this.overridden_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrFunction getDefaultInstanceForType() {
            return IrFunction.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrFunction build() {
            IrFunction irFunctionBuildPartial = buildPartial();
            if (irFunctionBuildPartial.isInitialized()) {
                return irFunctionBuildPartial;
            }
            throw newUninitializedMessageException(irFunctionBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrFunction buildPartial() {
            IrFunction irFunction = new IrFunction(this);
            int i = (this.bitField0_ & 1) != 1 ? 0 : 1;
            irFunction.base_ = this.base_;
            if ((this.bitField0_ & 2) == 2) {
                this.overridden_ = Collections.unmodifiableList(this.overridden_);
                this.bitField0_ &= -3;
            }
            irFunction.overridden_ = this.overridden_;
            irFunction.bitField0_ = i;
            return irFunction;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrFunction irFunction) {
            if (irFunction == IrFunction.getDefaultInstance()) {
                return this;
            }
            if (irFunction.hasBase()) {
                mergeBase(irFunction.getBase());
            }
            if (!irFunction.overridden_.isEmpty()) {
                if (this.overridden_.isEmpty()) {
                    this.overridden_ = irFunction.overridden_;
                    this.bitField0_ &= -3;
                } else {
                    ensureOverriddenIsMutable();
                    this.overridden_.addAll(irFunction.overridden_);
                }
            }
            setUnknownFields(getUnknownFields().concat(irFunction.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasBase() && getBase().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrFunction irFunction = null;
            try {
                try {
                    IrFunction partialFrom = IrFunction.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrFunction irFunction2 = (IrFunction) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irFunction = irFunction2;
                        if (irFunction != null) {
                            mergeFrom(irFunction);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irFunction != null) {
                    mergeFrom(irFunction);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionOrBuilder
        public boolean hasBase() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionOrBuilder
        public IrFunctionBase getBase() {
            return this.base_;
        }

        public Builder setBase(IrFunctionBase irFunctionBase) {
            irFunctionBase.getClass();
            this.base_ = irFunctionBase;
            this.bitField0_ |= 1;
            return this;
        }

        public Builder setBase(IrFunctionBase.Builder builder) {
            this.base_ = builder.build();
            this.bitField0_ |= 1;
            return this;
        }

        public Builder mergeBase(IrFunctionBase irFunctionBase) {
            if ((this.bitField0_ & 1) == 1 && this.base_ != IrFunctionBase.getDefaultInstance()) {
                this.base_ = IrFunctionBase.newBuilder(this.base_).mergeFrom(irFunctionBase).buildPartial();
            } else {
                this.base_ = irFunctionBase;
            }
            this.bitField0_ |= 1;
            return this;
        }

        public Builder clearBase() {
            this.base_ = IrFunctionBase.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
        }

        private void ensureOverriddenIsMutable() {
            if ((this.bitField0_ & 2) != 2) {
                this.overridden_ = new ArrayList(this.overridden_);
                this.bitField0_ |= 2;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionOrBuilder
        public List<Long> getOverriddenList() {
            return Collections.unmodifiableList(this.overridden_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionOrBuilder
        public int getOverriddenCount() {
            return this.overridden_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionOrBuilder
        public long getOverridden(int i) {
            return this.overridden_.get(i).longValue();
        }

        public Builder setOverridden(int i, long j) {
            ensureOverriddenIsMutable();
            this.overridden_.set(i, Long.valueOf(j));
            return this;
        }

        public Builder addOverridden(long j) {
            ensureOverriddenIsMutable();
            this.overridden_.add(Long.valueOf(j));
            return this;
        }

        public Builder addAllOverridden(Iterable<? extends Long> iterable) {
            ensureOverriddenIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.overridden_);
            return this;
        }

        public Builder clearOverridden() {
            this.overridden_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
        }
    }
}
