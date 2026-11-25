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
public final class IrInstanceInitializerCall extends GeneratedMessageLite implements IrInstanceInitializerCallOrBuilder {
    public static Parser<IrInstanceInitializerCall> PARSER = new AbstractParser<IrInstanceInitializerCall>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrInstanceInitializerCall.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrInstanceInitializerCall parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrInstanceInitializerCall(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int SYMBOL_FIELD_NUMBER = 1;
    private static final IrInstanceInitializerCall defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private long symbol_;
    private final ByteString unknownFields;

    private IrInstanceInitializerCall(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrInstanceInitializerCall(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrInstanceInitializerCall getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrInstanceInitializerCall getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrInstanceInitializerCall(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                        if (tag != 8) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            this.bitField0_ |= 1;
                            this.symbol_ = codedInputStream.readInt64();
                        }
                    }
                    z = true;
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
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
        IrInstanceInitializerCall irInstanceInitializerCall = new IrInstanceInitializerCall(true);
        defaultInstance = irInstanceInitializerCall;
        irInstanceInitializerCall.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrInstanceInitializerCall> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrInstanceInitializerCallOrBuilder
    public boolean hasSymbol() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrInstanceInitializerCallOrBuilder
    public long getSymbol() {
        return this.symbol_;
    }

    private void initFields() {
        this.symbol_ = 0L;
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
        this.memoizedIsInitialized = (byte) 1;
        return true;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        getSerializedSize();
        if ((this.bitField0_ & 1) == 1) {
            codedOutputStream.writeInt64(1, this.symbol_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt64Size = ((this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt64Size(1, this.symbol_) : 0) + this.unknownFields.size();
        this.memoizedSerializedSize = iComputeInt64Size;
        return iComputeInt64Size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrInstanceInitializerCall parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrInstanceInitializerCall parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrInstanceInitializerCall parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrInstanceInitializerCall parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrInstanceInitializerCall parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrInstanceInitializerCall parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrInstanceInitializerCall parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrInstanceInitializerCall parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrInstanceInitializerCall parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrInstanceInitializerCall parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrInstanceInitializerCall irInstanceInitializerCall) {
        return newBuilder().mergeFrom(irInstanceInitializerCall);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrInstanceInitializerCall, Builder> implements IrInstanceInitializerCallOrBuilder {
        private int bitField0_;
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
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrInstanceInitializerCall getDefaultInstanceForType() {
            return IrInstanceInitializerCall.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrInstanceInitializerCall build() {
            IrInstanceInitializerCall irInstanceInitializerCallBuildPartial = buildPartial();
            if (irInstanceInitializerCallBuildPartial.isInitialized()) {
                return irInstanceInitializerCallBuildPartial;
            }
            throw newUninitializedMessageException(irInstanceInitializerCallBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrInstanceInitializerCall buildPartial() {
            IrInstanceInitializerCall irInstanceInitializerCall = new IrInstanceInitializerCall(this);
            int i = (this.bitField0_ & 1) != 1 ? 0 : 1;
            irInstanceInitializerCall.symbol_ = this.symbol_;
            irInstanceInitializerCall.bitField0_ = i;
            return irInstanceInitializerCall;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrInstanceInitializerCall irInstanceInitializerCall) {
            if (irInstanceInitializerCall == IrInstanceInitializerCall.getDefaultInstance()) {
                return this;
            }
            if (irInstanceInitializerCall.hasSymbol()) {
                setSymbol(irInstanceInitializerCall.getSymbol());
            }
            setUnknownFields(getUnknownFields().concat(irInstanceInitializerCall.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasSymbol();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrInstanceInitializerCall irInstanceInitializerCall = null;
            try {
                try {
                    IrInstanceInitializerCall partialFrom = IrInstanceInitializerCall.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrInstanceInitializerCall irInstanceInitializerCall2 = (IrInstanceInitializerCall) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irInstanceInitializerCall = irInstanceInitializerCall2;
                        if (irInstanceInitializerCall != null) {
                            mergeFrom(irInstanceInitializerCall);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irInstanceInitializerCall != null) {
                    mergeFrom(irInstanceInitializerCall);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrInstanceInitializerCallOrBuilder
        public boolean hasSymbol() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrInstanceInitializerCallOrBuilder
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
    }
}
