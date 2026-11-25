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
public final class IrInlineClassRepresentation extends GeneratedMessageLite implements IrInlineClassRepresentationOrBuilder {
    public static Parser<IrInlineClassRepresentation> PARSER = new AbstractParser<IrInlineClassRepresentation>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrInlineClassRepresentation.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrInlineClassRepresentation parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrInlineClassRepresentation(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int UNDERLYING_PROPERTY_NAME_FIELD_NUMBER = 1;
    public static final int UNDERLYING_PROPERTY_TYPE_FIELD_NUMBER = 2;
    private static final IrInlineClassRepresentation defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int underlyingPropertyName_;
    private int underlyingPropertyType_;
    private final ByteString unknownFields;

    private IrInlineClassRepresentation(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrInlineClassRepresentation(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrInlineClassRepresentation getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrInlineClassRepresentation getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrInlineClassRepresentation(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                        if (tag == 8) {
                            this.bitField0_ |= 1;
                            this.underlyingPropertyName_ = codedInputStream.readInt32();
                        } else if (tag != 16) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            this.bitField0_ |= 2;
                            this.underlyingPropertyType_ = codedInputStream.readInt32();
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
        IrInlineClassRepresentation irInlineClassRepresentation = new IrInlineClassRepresentation(true);
        defaultInstance = irInlineClassRepresentation;
        irInlineClassRepresentation.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrInlineClassRepresentation> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrInlineClassRepresentationOrBuilder
    public boolean hasUnderlyingPropertyName() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrInlineClassRepresentationOrBuilder
    public int getUnderlyingPropertyName() {
        return this.underlyingPropertyName_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrInlineClassRepresentationOrBuilder
    public boolean hasUnderlyingPropertyType() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrInlineClassRepresentationOrBuilder
    public int getUnderlyingPropertyType() {
        return this.underlyingPropertyType_;
    }

    private void initFields() {
        this.underlyingPropertyName_ = 0;
        this.underlyingPropertyType_ = 0;
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
        if (!hasUnderlyingPropertyName()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasUnderlyingPropertyType()) {
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
            codedOutputStream.writeInt32(1, this.underlyingPropertyName_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt32(2, this.underlyingPropertyType_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.underlyingPropertyName_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeInt32Size += CodedOutputStream.computeInt32Size(2, this.underlyingPropertyType_);
        }
        int size = iComputeInt32Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrInlineClassRepresentation parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrInlineClassRepresentation parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrInlineClassRepresentation parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrInlineClassRepresentation parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrInlineClassRepresentation parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrInlineClassRepresentation parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrInlineClassRepresentation parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrInlineClassRepresentation parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrInlineClassRepresentation parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrInlineClassRepresentation parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrInlineClassRepresentation irInlineClassRepresentation) {
        return newBuilder().mergeFrom(irInlineClassRepresentation);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrInlineClassRepresentation, Builder> implements IrInlineClassRepresentationOrBuilder {
        private int bitField0_;
        private int underlyingPropertyName_;
        private int underlyingPropertyType_;

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
            this.underlyingPropertyName_ = 0;
            int i = this.bitField0_;
            this.underlyingPropertyType_ = 0;
            this.bitField0_ = i & (-4);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrInlineClassRepresentation getDefaultInstanceForType() {
            return IrInlineClassRepresentation.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrInlineClassRepresentation build() {
            IrInlineClassRepresentation irInlineClassRepresentationBuildPartial = buildPartial();
            if (irInlineClassRepresentationBuildPartial.isInitialized()) {
                return irInlineClassRepresentationBuildPartial;
            }
            throw newUninitializedMessageException(irInlineClassRepresentationBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrInlineClassRepresentation buildPartial() {
            IrInlineClassRepresentation irInlineClassRepresentation = new IrInlineClassRepresentation(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irInlineClassRepresentation.underlyingPropertyName_ = this.underlyingPropertyName_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irInlineClassRepresentation.underlyingPropertyType_ = this.underlyingPropertyType_;
            irInlineClassRepresentation.bitField0_ = i2;
            return irInlineClassRepresentation;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrInlineClassRepresentation irInlineClassRepresentation) {
            if (irInlineClassRepresentation == IrInlineClassRepresentation.getDefaultInstance()) {
                return this;
            }
            if (irInlineClassRepresentation.hasUnderlyingPropertyName()) {
                setUnderlyingPropertyName(irInlineClassRepresentation.getUnderlyingPropertyName());
            }
            if (irInlineClassRepresentation.hasUnderlyingPropertyType()) {
                setUnderlyingPropertyType(irInlineClassRepresentation.getUnderlyingPropertyType());
            }
            setUnknownFields(getUnknownFields().concat(irInlineClassRepresentation.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasUnderlyingPropertyName() && hasUnderlyingPropertyType();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrInlineClassRepresentation irInlineClassRepresentation = null;
            try {
                try {
                    IrInlineClassRepresentation partialFrom = IrInlineClassRepresentation.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrInlineClassRepresentation irInlineClassRepresentation2 = (IrInlineClassRepresentation) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irInlineClassRepresentation = irInlineClassRepresentation2;
                        if (irInlineClassRepresentation != null) {
                            mergeFrom(irInlineClassRepresentation);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irInlineClassRepresentation != null) {
                    mergeFrom(irInlineClassRepresentation);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrInlineClassRepresentationOrBuilder
        public boolean hasUnderlyingPropertyName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrInlineClassRepresentationOrBuilder
        public int getUnderlyingPropertyName() {
            return this.underlyingPropertyName_;
        }

        public Builder setUnderlyingPropertyName(int i) {
            this.bitField0_ |= 1;
            this.underlyingPropertyName_ = i;
            return this;
        }

        public Builder clearUnderlyingPropertyName() {
            this.bitField0_ &= -2;
            this.underlyingPropertyName_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrInlineClassRepresentationOrBuilder
        public boolean hasUnderlyingPropertyType() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrInlineClassRepresentationOrBuilder
        public int getUnderlyingPropertyType() {
            return this.underlyingPropertyType_;
        }

        public Builder setUnderlyingPropertyType(int i) {
            this.bitField0_ |= 2;
            this.underlyingPropertyType_ = i;
            return this;
        }

        public Builder clearUnderlyingPropertyType() {
            this.bitField0_ &= -3;
            this.underlyingPropertyType_ = 0;
            return this;
        }
    }
}
