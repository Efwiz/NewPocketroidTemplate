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
public final class IrSyntheticBody extends GeneratedMessageLite implements IrSyntheticBodyOrBuilder {
    public static final int KIND_FIELD_NUMBER = 1;
    public static Parser<IrSyntheticBody> PARSER = new AbstractParser<IrSyntheticBody>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrSyntheticBody.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrSyntheticBody parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrSyntheticBody(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrSyntheticBody defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private IrSyntheticBodyKind kind_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    private IrSyntheticBody(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrSyntheticBody(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrSyntheticBody getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrSyntheticBody getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrSyntheticBody(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                int i = codedInputStream.readEnum();
                                IrSyntheticBodyKind irSyntheticBodyKindValueOf = IrSyntheticBodyKind.valueOf(i);
                                if (irSyntheticBodyKindValueOf == null) {
                                    codedOutputStreamNewInstance.writeRawVarint32(tag);
                                    codedOutputStreamNewInstance.writeRawVarint32(i);
                                } else {
                                    this.bitField0_ |= 1;
                                    this.kind_ = irSyntheticBodyKindValueOf;
                                }
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
        IrSyntheticBody irSyntheticBody = new IrSyntheticBody(true);
        defaultInstance = irSyntheticBody;
        irSyntheticBody.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrSyntheticBody> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSyntheticBodyOrBuilder
    public boolean hasKind() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSyntheticBodyOrBuilder
    public IrSyntheticBodyKind getKind() {
        return this.kind_;
    }

    private void initFields() {
        this.kind_ = IrSyntheticBodyKind.ENUM_VALUES;
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
        if (!hasKind()) {
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
            codedOutputStream.writeEnum(1, this.kind_.getNumber());
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeEnumSize = ((this.bitField0_ & 1) == 1 ? CodedOutputStream.computeEnumSize(1, this.kind_.getNumber()) : 0) + this.unknownFields.size();
        this.memoizedSerializedSize = iComputeEnumSize;
        return iComputeEnumSize;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrSyntheticBody parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrSyntheticBody parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrSyntheticBody parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrSyntheticBody parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrSyntheticBody parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrSyntheticBody parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrSyntheticBody parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrSyntheticBody parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrSyntheticBody parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrSyntheticBody parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrSyntheticBody irSyntheticBody) {
        return newBuilder().mergeFrom(irSyntheticBody);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrSyntheticBody, Builder> implements IrSyntheticBodyOrBuilder {
        private int bitField0_;
        private IrSyntheticBodyKind kind_ = IrSyntheticBodyKind.ENUM_VALUES;

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
            this.kind_ = IrSyntheticBodyKind.ENUM_VALUES;
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrSyntheticBody getDefaultInstanceForType() {
            return IrSyntheticBody.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrSyntheticBody build() {
            IrSyntheticBody irSyntheticBodyBuildPartial = buildPartial();
            if (irSyntheticBodyBuildPartial.isInitialized()) {
                return irSyntheticBodyBuildPartial;
            }
            throw newUninitializedMessageException(irSyntheticBodyBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrSyntheticBody buildPartial() {
            IrSyntheticBody irSyntheticBody = new IrSyntheticBody(this);
            int i = (this.bitField0_ & 1) != 1 ? 0 : 1;
            irSyntheticBody.kind_ = this.kind_;
            irSyntheticBody.bitField0_ = i;
            return irSyntheticBody;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrSyntheticBody irSyntheticBody) {
            if (irSyntheticBody == IrSyntheticBody.getDefaultInstance()) {
                return this;
            }
            if (irSyntheticBody.hasKind()) {
                setKind(irSyntheticBody.getKind());
            }
            setUnknownFields(getUnknownFields().concat(irSyntheticBody.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasKind();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrSyntheticBody irSyntheticBody = null;
            try {
                try {
                    IrSyntheticBody partialFrom = IrSyntheticBody.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrSyntheticBody irSyntheticBody2 = (IrSyntheticBody) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irSyntheticBody = irSyntheticBody2;
                        if (irSyntheticBody != null) {
                            mergeFrom(irSyntheticBody);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irSyntheticBody != null) {
                    mergeFrom(irSyntheticBody);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSyntheticBodyOrBuilder
        public boolean hasKind() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSyntheticBodyOrBuilder
        public IrSyntheticBodyKind getKind() {
            return this.kind_;
        }

        public Builder setKind(IrSyntheticBodyKind irSyntheticBodyKind) {
            irSyntheticBodyKind.getClass();
            this.bitField0_ |= 1;
            this.kind_ = irSyntheticBodyKind;
            return this;
        }

        public Builder clearKind() {
            this.bitField0_ &= -2;
            this.kind_ = IrSyntheticBodyKind.ENUM_VALUES;
            return this;
        }
    }
}
