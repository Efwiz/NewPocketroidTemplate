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
public final class IrLocalDelegatedProperty extends GeneratedMessageLite implements IrLocalDelegatedPropertyOrBuilder {
    public static final int BASE_FIELD_NUMBER = 1;
    public static final int DELEGATE_FIELD_NUMBER = 3;
    public static final int GETTER_FIELD_NUMBER = 4;
    public static final int NAME_TYPE_FIELD_NUMBER = 2;
    public static Parser<IrLocalDelegatedProperty> PARSER = new AbstractParser<IrLocalDelegatedProperty>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedProperty.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrLocalDelegatedProperty parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrLocalDelegatedProperty(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int SETTER_FIELD_NUMBER = 5;
    private static final IrLocalDelegatedProperty defaultInstance;
    private static final long serialVersionUID = 0;
    private IrDeclarationBase base_;
    private int bitField0_;
    private IrVariable delegate_;
    private IrFunction getter_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private long nameType_;
    private IrFunction setter_;
    private final ByteString unknownFields;

    private IrLocalDelegatedProperty(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrLocalDelegatedProperty(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrLocalDelegatedProperty getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrLocalDelegatedProperty getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrLocalDelegatedProperty(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        GeneratedMessageLite.Builder builder;
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
                        if (tag == 10) {
                            builder = (this.bitField0_ & 1) == 1 ? this.base_.toBuilder() : null;
                            IrDeclarationBase irDeclarationBase = (IrDeclarationBase) codedInputStream.readMessage(IrDeclarationBase.PARSER, extensionRegistryLite);
                            this.base_ = irDeclarationBase;
                            if (builder != null) {
                                builder.mergeFrom(irDeclarationBase);
                                this.base_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                        } else if (tag == 16) {
                            this.bitField0_ |= 2;
                            this.nameType_ = codedInputStream.readInt64();
                        } else if (tag == 26) {
                            builder = (this.bitField0_ & 4) == 4 ? this.delegate_.toBuilder() : null;
                            IrVariable irVariable = (IrVariable) codedInputStream.readMessage(IrVariable.PARSER, extensionRegistryLite);
                            this.delegate_ = irVariable;
                            if (builder != null) {
                                builder.mergeFrom(irVariable);
                                this.delegate_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 4;
                        } else if (tag == 34) {
                            builder = (this.bitField0_ & 8) == 8 ? this.getter_.toBuilder() : null;
                            IrFunction irFunction = (IrFunction) codedInputStream.readMessage(IrFunction.PARSER, extensionRegistryLite);
                            this.getter_ = irFunction;
                            if (builder != null) {
                                builder.mergeFrom(irFunction);
                                this.getter_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 8;
                        } else if (tag != 42) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            builder = (this.bitField0_ & 16) == 16 ? this.setter_.toBuilder() : null;
                            IrFunction irFunction2 = (IrFunction) codedInputStream.readMessage(IrFunction.PARSER, extensionRegistryLite);
                            this.setter_ = irFunction2;
                            if (builder != null) {
                                builder.mergeFrom(irFunction2);
                                this.setter_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 16;
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
        IrLocalDelegatedProperty irLocalDelegatedProperty = new IrLocalDelegatedProperty(true);
        defaultInstance = irLocalDelegatedProperty;
        irLocalDelegatedProperty.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrLocalDelegatedProperty> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
    public boolean hasBase() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
    public IrDeclarationBase getBase() {
        return this.base_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
    public boolean hasNameType() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
    public long getNameType() {
        return this.nameType_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
    public boolean hasDelegate() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
    public IrVariable getDelegate() {
        return this.delegate_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
    public boolean hasGetter() {
        return (this.bitField0_ & 8) == 8;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
    public IrFunction getGetter() {
        return this.getter_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
    public boolean hasSetter() {
        return (this.bitField0_ & 16) == 16;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
    public IrFunction getSetter() {
        return this.setter_;
    }

    private void initFields() {
        this.base_ = IrDeclarationBase.getDefaultInstance();
        this.nameType_ = 0L;
        this.delegate_ = IrVariable.getDefaultInstance();
        this.getter_ = IrFunction.getDefaultInstance();
        this.setter_ = IrFunction.getDefaultInstance();
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
        if (!hasNameType()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getBase().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasDelegate() && !getDelegate().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasGetter() && !getGetter().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasSetter() && !getSetter().isInitialized()) {
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
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt64(2, this.nameType_);
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeMessage(3, this.delegate_);
        }
        if ((this.bitField0_ & 8) == 8) {
            codedOutputStream.writeMessage(4, this.getter_);
        }
        if ((this.bitField0_ & 16) == 16) {
            codedOutputStream.writeMessage(5, this.setter_);
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
        if ((this.bitField0_ & 2) == 2) {
            iComputeMessageSize += CodedOutputStream.computeInt64Size(2, this.nameType_);
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(3, this.delegate_);
        }
        if ((this.bitField0_ & 8) == 8) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(4, this.getter_);
        }
        if ((this.bitField0_ & 16) == 16) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(5, this.setter_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrLocalDelegatedProperty parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrLocalDelegatedProperty parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrLocalDelegatedProperty parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrLocalDelegatedProperty parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrLocalDelegatedProperty parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrLocalDelegatedProperty parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrLocalDelegatedProperty parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrLocalDelegatedProperty parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrLocalDelegatedProperty parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrLocalDelegatedProperty parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrLocalDelegatedProperty irLocalDelegatedProperty) {
        return newBuilder().mergeFrom(irLocalDelegatedProperty);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrLocalDelegatedProperty, Builder> implements IrLocalDelegatedPropertyOrBuilder {
        private int bitField0_;
        private long nameType_;
        private IrDeclarationBase base_ = IrDeclarationBase.getDefaultInstance();
        private IrVariable delegate_ = IrVariable.getDefaultInstance();
        private IrFunction getter_ = IrFunction.getDefaultInstance();
        private IrFunction setter_ = IrFunction.getDefaultInstance();

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
            this.base_ = IrDeclarationBase.getDefaultInstance();
            int i = this.bitField0_;
            this.nameType_ = 0L;
            this.bitField0_ = i & (-4);
            this.delegate_ = IrVariable.getDefaultInstance();
            this.bitField0_ &= -5;
            this.getter_ = IrFunction.getDefaultInstance();
            this.bitField0_ &= -9;
            this.setter_ = IrFunction.getDefaultInstance();
            this.bitField0_ &= -17;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrLocalDelegatedProperty getDefaultInstanceForType() {
            return IrLocalDelegatedProperty.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrLocalDelegatedProperty build() {
            IrLocalDelegatedProperty irLocalDelegatedPropertyBuildPartial = buildPartial();
            if (irLocalDelegatedPropertyBuildPartial.isInitialized()) {
                return irLocalDelegatedPropertyBuildPartial;
            }
            throw newUninitializedMessageException(irLocalDelegatedPropertyBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrLocalDelegatedProperty buildPartial() {
            IrLocalDelegatedProperty irLocalDelegatedProperty = new IrLocalDelegatedProperty(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irLocalDelegatedProperty.base_ = this.base_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irLocalDelegatedProperty.nameType_ = this.nameType_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            irLocalDelegatedProperty.delegate_ = this.delegate_;
            if ((i & 8) == 8) {
                i2 |= 8;
            }
            irLocalDelegatedProperty.getter_ = this.getter_;
            if ((i & 16) == 16) {
                i2 |= 16;
            }
            irLocalDelegatedProperty.setter_ = this.setter_;
            irLocalDelegatedProperty.bitField0_ = i2;
            return irLocalDelegatedProperty;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrLocalDelegatedProperty irLocalDelegatedProperty) {
            if (irLocalDelegatedProperty == IrLocalDelegatedProperty.getDefaultInstance()) {
                return this;
            }
            if (irLocalDelegatedProperty.hasBase()) {
                mergeBase(irLocalDelegatedProperty.getBase());
            }
            if (irLocalDelegatedProperty.hasNameType()) {
                setNameType(irLocalDelegatedProperty.getNameType());
            }
            if (irLocalDelegatedProperty.hasDelegate()) {
                mergeDelegate(irLocalDelegatedProperty.getDelegate());
            }
            if (irLocalDelegatedProperty.hasGetter()) {
                mergeGetter(irLocalDelegatedProperty.getGetter());
            }
            if (irLocalDelegatedProperty.hasSetter()) {
                mergeSetter(irLocalDelegatedProperty.getSetter());
            }
            setUnknownFields(getUnknownFields().concat(irLocalDelegatedProperty.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (!hasBase() || !hasNameType() || !getBase().isInitialized()) {
                return false;
            }
            if (hasDelegate() && !getDelegate().isInitialized()) {
                return false;
            }
            if (!hasGetter() || getGetter().isInitialized()) {
                return !hasSetter() || getSetter().isInitialized();
            }
            return false;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrLocalDelegatedProperty irLocalDelegatedProperty = null;
            try {
                try {
                    IrLocalDelegatedProperty partialFrom = IrLocalDelegatedProperty.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrLocalDelegatedProperty irLocalDelegatedProperty2 = (IrLocalDelegatedProperty) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irLocalDelegatedProperty = irLocalDelegatedProperty2;
                        if (irLocalDelegatedProperty != null) {
                            mergeFrom(irLocalDelegatedProperty);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irLocalDelegatedProperty != null) {
                    mergeFrom(irLocalDelegatedProperty);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
        public boolean hasBase() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
        public IrDeclarationBase getBase() {
            return this.base_;
        }

        public Builder setBase(IrDeclarationBase irDeclarationBase) {
            irDeclarationBase.getClass();
            this.base_ = irDeclarationBase;
            this.bitField0_ |= 1;
            return this;
        }

        public Builder setBase(IrDeclarationBase.Builder builder) {
            this.base_ = builder.build();
            this.bitField0_ |= 1;
            return this;
        }

        public Builder mergeBase(IrDeclarationBase irDeclarationBase) {
            if ((this.bitField0_ & 1) == 1 && this.base_ != IrDeclarationBase.getDefaultInstance()) {
                this.base_ = IrDeclarationBase.newBuilder(this.base_).mergeFrom(irDeclarationBase).buildPartial();
            } else {
                this.base_ = irDeclarationBase;
            }
            this.bitField0_ |= 1;
            return this;
        }

        public Builder clearBase() {
            this.base_ = IrDeclarationBase.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
        public boolean hasNameType() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
        public long getNameType() {
            return this.nameType_;
        }

        public Builder setNameType(long j) {
            this.bitField0_ |= 2;
            this.nameType_ = j;
            return this;
        }

        public Builder clearNameType() {
            this.bitField0_ &= -3;
            this.nameType_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
        public boolean hasDelegate() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
        public IrVariable getDelegate() {
            return this.delegate_;
        }

        public Builder setDelegate(IrVariable irVariable) {
            irVariable.getClass();
            this.delegate_ = irVariable;
            this.bitField0_ |= 4;
            return this;
        }

        public Builder setDelegate(IrVariable.Builder builder) {
            this.delegate_ = builder.build();
            this.bitField0_ |= 4;
            return this;
        }

        public Builder mergeDelegate(IrVariable irVariable) {
            if ((this.bitField0_ & 4) == 4 && this.delegate_ != IrVariable.getDefaultInstance()) {
                this.delegate_ = IrVariable.newBuilder(this.delegate_).mergeFrom(irVariable).buildPartial();
            } else {
                this.delegate_ = irVariable;
            }
            this.bitField0_ |= 4;
            return this;
        }

        public Builder clearDelegate() {
            this.delegate_ = IrVariable.getDefaultInstance();
            this.bitField0_ &= -5;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
        public boolean hasGetter() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
        public IrFunction getGetter() {
            return this.getter_;
        }

        public Builder setGetter(IrFunction irFunction) {
            irFunction.getClass();
            this.getter_ = irFunction;
            this.bitField0_ |= 8;
            return this;
        }

        public Builder setGetter(IrFunction.Builder builder) {
            this.getter_ = builder.build();
            this.bitField0_ |= 8;
            return this;
        }

        public Builder mergeGetter(IrFunction irFunction) {
            if ((this.bitField0_ & 8) == 8 && this.getter_ != IrFunction.getDefaultInstance()) {
                this.getter_ = IrFunction.newBuilder(this.getter_).mergeFrom(irFunction).buildPartial();
            } else {
                this.getter_ = irFunction;
            }
            this.bitField0_ |= 8;
            return this;
        }

        public Builder clearGetter() {
            this.getter_ = IrFunction.getDefaultInstance();
            this.bitField0_ &= -9;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
        public boolean hasSetter() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyOrBuilder
        public IrFunction getSetter() {
            return this.setter_;
        }

        public Builder setSetter(IrFunction irFunction) {
            irFunction.getClass();
            this.setter_ = irFunction;
            this.bitField0_ |= 16;
            return this;
        }

        public Builder setSetter(IrFunction.Builder builder) {
            this.setter_ = builder.build();
            this.bitField0_ |= 16;
            return this;
        }

        public Builder mergeSetter(IrFunction irFunction) {
            if ((this.bitField0_ & 16) == 16 && this.setter_ != IrFunction.getDefaultInstance()) {
                this.setter_ = IrFunction.newBuilder(this.setter_).mergeFrom(irFunction).buildPartial();
            } else {
                this.setter_ = irFunction;
            }
            this.bitField0_ |= 16;
            return this;
        }

        public Builder clearSetter() {
            this.setter_ = IrFunction.getDefaultInstance();
            this.bitField0_ &= -17;
            return this;
        }
    }
}
