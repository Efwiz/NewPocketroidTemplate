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
public final class IrFunctionBase extends GeneratedMessageLite implements IrFunctionBaseOrBuilder {
    public static final int BASE_FIELD_NUMBER = 1;
    public static final int BODY_FIELD_NUMBER = 7;
    public static final int CONTEXT_RECEIVER_PARAMETERS_COUNT_FIELD_NUMBER = 8;
    public static final int DISPATCH_RECEIVER_FIELD_NUMBER = 4;
    public static final int EXTENSION_RECEIVER_FIELD_NUMBER = 5;
    public static final int NAME_TYPE_FIELD_NUMBER = 2;
    public static Parser<IrFunctionBase> PARSER = new AbstractParser<IrFunctionBase>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBase.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrFunctionBase parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrFunctionBase(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int TYPE_PARAMETER_FIELD_NUMBER = 3;
    public static final int VALUE_PARAMETER_FIELD_NUMBER = 6;
    private static final IrFunctionBase defaultInstance;
    private static final long serialVersionUID = 0;
    private IrDeclarationBase base_;
    private int bitField0_;
    private int body_;
    private int contextReceiverParametersCount_;
    private IrValueParameter dispatchReceiver_;
    private IrValueParameter extensionReceiver_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private long nameType_;
    private List<IrTypeParameter> typeParameter_;
    private final ByteString unknownFields;
    private List<IrValueParameter> valueParameter_;

    private IrFunctionBase(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrFunctionBase(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrFunctionBase getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrFunctionBase getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrFunctionBase(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        initFields();
        ByteString.Output outputNewOutput = ByteString.newOutput();
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
        boolean z = false;
        int i = 0;
        while (!z) {
            try {
                try {
                    int tag = codedInputStream.readTag();
                    if (tag != 0) {
                        if (tag == 10) {
                            IrDeclarationBase.Builder builder = (this.bitField0_ & 1) == 1 ? this.base_.toBuilder() : null;
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
                            int i2 = (i == true ? 1 : 0) & 4;
                            i = i;
                            if (i2 != 4) {
                                this.typeParameter_ = new ArrayList();
                                i = (i == true ? 1 : 0) | 4;
                            }
                            this.typeParameter_.add((IrTypeParameter) codedInputStream.readMessage(IrTypeParameter.PARSER, extensionRegistryLite));
                        } else if (tag == 34) {
                            IrValueParameter.Builder builder2 = (this.bitField0_ & 4) == 4 ? this.dispatchReceiver_.toBuilder() : null;
                            IrValueParameter irValueParameter = (IrValueParameter) codedInputStream.readMessage(IrValueParameter.PARSER, extensionRegistryLite);
                            this.dispatchReceiver_ = irValueParameter;
                            if (builder2 != null) {
                                builder2.mergeFrom(irValueParameter);
                                this.dispatchReceiver_ = builder2.buildPartial();
                            }
                            this.bitField0_ |= 4;
                        } else if (tag == 42) {
                            IrValueParameter.Builder builder3 = (this.bitField0_ & 8) == 8 ? this.extensionReceiver_.toBuilder() : null;
                            IrValueParameter irValueParameter2 = (IrValueParameter) codedInputStream.readMessage(IrValueParameter.PARSER, extensionRegistryLite);
                            this.extensionReceiver_ = irValueParameter2;
                            if (builder3 != null) {
                                builder3.mergeFrom(irValueParameter2);
                                this.extensionReceiver_ = builder3.buildPartial();
                            }
                            this.bitField0_ |= 8;
                        } else if (tag == 50) {
                            int i3 = (i == true ? 1 : 0) & 64;
                            i = i;
                            if (i3 != 64) {
                                this.valueParameter_ = new ArrayList();
                                i = (i == true ? 1 : 0) | 64;
                            }
                            this.valueParameter_.add((IrValueParameter) codedInputStream.readMessage(IrValueParameter.PARSER, extensionRegistryLite));
                        } else if (tag == 56) {
                            this.bitField0_ |= 32;
                            this.body_ = codedInputStream.readInt32();
                        } else if (tag != 64) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            this.bitField0_ |= 16;
                            this.contextReceiverParametersCount_ = codedInputStream.readInt32();
                        }
                    }
                    z = true;
                } catch (Throwable th) {
                    if (((i == true ? 1 : 0) & 4) == 4) {
                        this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                    }
                    if (((i == true ? 1 : 0) & 64) == 64) {
                        this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
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
        if (((i == true ? 1 : 0) & 4) == 4) {
            this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
        }
        if (((i == true ? 1 : 0) & 64) == 64) {
            this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
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
        IrFunctionBase irFunctionBase = new IrFunctionBase(true);
        defaultInstance = irFunctionBase;
        irFunctionBase.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrFunctionBase> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public boolean hasBase() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public IrDeclarationBase getBase() {
        return this.base_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public boolean hasNameType() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public long getNameType() {
        return this.nameType_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public List<IrTypeParameter> getTypeParameterList() {
        return this.typeParameter_;
    }

    public List<? extends IrTypeParameterOrBuilder> getTypeParameterOrBuilderList() {
        return this.typeParameter_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public int getTypeParameterCount() {
        return this.typeParameter_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public IrTypeParameter getTypeParameter(int i) {
        return this.typeParameter_.get(i);
    }

    public IrTypeParameterOrBuilder getTypeParameterOrBuilder(int i) {
        return this.typeParameter_.get(i);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public boolean hasDispatchReceiver() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public IrValueParameter getDispatchReceiver() {
        return this.dispatchReceiver_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public boolean hasExtensionReceiver() {
        return (this.bitField0_ & 8) == 8;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public IrValueParameter getExtensionReceiver() {
        return this.extensionReceiver_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public boolean hasContextReceiverParametersCount() {
        return (this.bitField0_ & 16) == 16;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public int getContextReceiverParametersCount() {
        return this.contextReceiverParametersCount_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public List<IrValueParameter> getValueParameterList() {
        return this.valueParameter_;
    }

    public List<? extends IrValueParameterOrBuilder> getValueParameterOrBuilderList() {
        return this.valueParameter_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public int getValueParameterCount() {
        return this.valueParameter_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public IrValueParameter getValueParameter(int i) {
        return this.valueParameter_.get(i);
    }

    public IrValueParameterOrBuilder getValueParameterOrBuilder(int i) {
        return this.valueParameter_.get(i);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public boolean hasBody() {
        return (this.bitField0_ & 32) == 32;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
    public int getBody() {
        return this.body_;
    }

    private void initFields() {
        this.base_ = IrDeclarationBase.getDefaultInstance();
        this.nameType_ = 0L;
        this.typeParameter_ = Collections.emptyList();
        this.dispatchReceiver_ = IrValueParameter.getDefaultInstance();
        this.extensionReceiver_ = IrValueParameter.getDefaultInstance();
        this.contextReceiverParametersCount_ = 0;
        this.valueParameter_ = Collections.emptyList();
        this.body_ = 0;
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
        for (int i = 0; i < getTypeParameterCount(); i++) {
            if (!getTypeParameter(i).isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
        }
        if (hasDispatchReceiver() && !getDispatchReceiver().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasExtensionReceiver() && !getExtensionReceiver().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        for (int i2 = 0; i2 < getValueParameterCount(); i2++) {
            if (!getValueParameter(i2).isInitialized()) {
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
            codedOutputStream.writeMessage(1, this.base_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt64(2, this.nameType_);
        }
        for (int i = 0; i < this.typeParameter_.size(); i++) {
            codedOutputStream.writeMessage(3, this.typeParameter_.get(i));
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeMessage(4, this.dispatchReceiver_);
        }
        if ((this.bitField0_ & 8) == 8) {
            codedOutputStream.writeMessage(5, this.extensionReceiver_);
        }
        for (int i2 = 0; i2 < this.valueParameter_.size(); i2++) {
            codedOutputStream.writeMessage(6, this.valueParameter_.get(i2));
        }
        if ((this.bitField0_ & 32) == 32) {
            codedOutputStream.writeInt32(7, this.body_);
        }
        if ((this.bitField0_ & 16) == 16) {
            codedOutputStream.writeInt32(8, this.contextReceiverParametersCount_);
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
        for (int i2 = 0; i2 < this.typeParameter_.size(); i2++) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(3, this.typeParameter_.get(i2));
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(4, this.dispatchReceiver_);
        }
        if ((this.bitField0_ & 8) == 8) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(5, this.extensionReceiver_);
        }
        for (int i3 = 0; i3 < this.valueParameter_.size(); i3++) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(6, this.valueParameter_.get(i3));
        }
        if ((this.bitField0_ & 32) == 32) {
            iComputeMessageSize += CodedOutputStream.computeInt32Size(7, this.body_);
        }
        if ((this.bitField0_ & 16) == 16) {
            iComputeMessageSize += CodedOutputStream.computeInt32Size(8, this.contextReceiverParametersCount_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrFunctionBase parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrFunctionBase parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrFunctionBase parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrFunctionBase parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrFunctionBase parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrFunctionBase parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrFunctionBase parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrFunctionBase parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrFunctionBase parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrFunctionBase parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrFunctionBase irFunctionBase) {
        return newBuilder().mergeFrom(irFunctionBase);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrFunctionBase, Builder> implements IrFunctionBaseOrBuilder {
        private int bitField0_;
        private int body_;
        private int contextReceiverParametersCount_;
        private long nameType_;
        private IrDeclarationBase base_ = IrDeclarationBase.getDefaultInstance();
        private List<IrTypeParameter> typeParameter_ = Collections.emptyList();
        private IrValueParameter dispatchReceiver_ = IrValueParameter.getDefaultInstance();
        private IrValueParameter extensionReceiver_ = IrValueParameter.getDefaultInstance();
        private List<IrValueParameter> valueParameter_ = Collections.emptyList();

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
            this.typeParameter_ = Collections.emptyList();
            this.bitField0_ &= -5;
            this.dispatchReceiver_ = IrValueParameter.getDefaultInstance();
            this.bitField0_ &= -9;
            this.extensionReceiver_ = IrValueParameter.getDefaultInstance();
            int i2 = this.bitField0_;
            this.contextReceiverParametersCount_ = 0;
            this.bitField0_ = i2 & (-49);
            this.valueParameter_ = Collections.emptyList();
            int i3 = this.bitField0_;
            this.body_ = 0;
            this.bitField0_ = i3 & (-193);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrFunctionBase getDefaultInstanceForType() {
            return IrFunctionBase.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrFunctionBase build() {
            IrFunctionBase irFunctionBaseBuildPartial = buildPartial();
            if (irFunctionBaseBuildPartial.isInitialized()) {
                return irFunctionBaseBuildPartial;
            }
            throw newUninitializedMessageException(irFunctionBaseBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrFunctionBase buildPartial() {
            IrFunctionBase irFunctionBase = new IrFunctionBase(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irFunctionBase.base_ = this.base_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irFunctionBase.nameType_ = this.nameType_;
            if ((this.bitField0_ & 4) == 4) {
                this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                this.bitField0_ &= -5;
            }
            irFunctionBase.typeParameter_ = this.typeParameter_;
            if ((i & 8) == 8) {
                i2 |= 4;
            }
            irFunctionBase.dispatchReceiver_ = this.dispatchReceiver_;
            if ((i & 16) == 16) {
                i2 |= 8;
            }
            irFunctionBase.extensionReceiver_ = this.extensionReceiver_;
            if ((i & 32) == 32) {
                i2 |= 16;
            }
            irFunctionBase.contextReceiverParametersCount_ = this.contextReceiverParametersCount_;
            if ((this.bitField0_ & 64) == 64) {
                this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
                this.bitField0_ &= -65;
            }
            irFunctionBase.valueParameter_ = this.valueParameter_;
            if ((i & 128) == 128) {
                i2 |= 32;
            }
            irFunctionBase.body_ = this.body_;
            irFunctionBase.bitField0_ = i2;
            return irFunctionBase;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrFunctionBase irFunctionBase) {
            if (irFunctionBase == IrFunctionBase.getDefaultInstance()) {
                return this;
            }
            if (irFunctionBase.hasBase()) {
                mergeBase(irFunctionBase.getBase());
            }
            if (irFunctionBase.hasNameType()) {
                setNameType(irFunctionBase.getNameType());
            }
            if (!irFunctionBase.typeParameter_.isEmpty()) {
                if (this.typeParameter_.isEmpty()) {
                    this.typeParameter_ = irFunctionBase.typeParameter_;
                    this.bitField0_ &= -5;
                } else {
                    ensureTypeParameterIsMutable();
                    this.typeParameter_.addAll(irFunctionBase.typeParameter_);
                }
            }
            if (irFunctionBase.hasDispatchReceiver()) {
                mergeDispatchReceiver(irFunctionBase.getDispatchReceiver());
            }
            if (irFunctionBase.hasExtensionReceiver()) {
                mergeExtensionReceiver(irFunctionBase.getExtensionReceiver());
            }
            if (irFunctionBase.hasContextReceiverParametersCount()) {
                setContextReceiverParametersCount(irFunctionBase.getContextReceiverParametersCount());
            }
            if (!irFunctionBase.valueParameter_.isEmpty()) {
                if (this.valueParameter_.isEmpty()) {
                    this.valueParameter_ = irFunctionBase.valueParameter_;
                    this.bitField0_ &= -65;
                } else {
                    ensureValueParameterIsMutable();
                    this.valueParameter_.addAll(irFunctionBase.valueParameter_);
                }
            }
            if (irFunctionBase.hasBody()) {
                setBody(irFunctionBase.getBody());
            }
            setUnknownFields(getUnknownFields().concat(irFunctionBase.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (!hasBase() || !hasNameType() || !getBase().isInitialized()) {
                return false;
            }
            for (int i = 0; i < getTypeParameterCount(); i++) {
                if (!getTypeParameter(i).isInitialized()) {
                    return false;
                }
            }
            if (hasDispatchReceiver() && !getDispatchReceiver().isInitialized()) {
                return false;
            }
            if (hasExtensionReceiver() && !getExtensionReceiver().isInitialized()) {
                return false;
            }
            for (int i2 = 0; i2 < getValueParameterCount(); i2++) {
                if (!getValueParameter(i2).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrFunctionBase irFunctionBase = null;
            try {
                try {
                    IrFunctionBase partialFrom = IrFunctionBase.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrFunctionBase irFunctionBase2 = (IrFunctionBase) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irFunctionBase = irFunctionBase2;
                        if (irFunctionBase != null) {
                            mergeFrom(irFunctionBase);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irFunctionBase != null) {
                    mergeFrom(irFunctionBase);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public boolean hasBase() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public boolean hasNameType() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
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

        private void ensureTypeParameterIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
                this.typeParameter_ = new ArrayList(this.typeParameter_);
                this.bitField0_ |= 4;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public List<IrTypeParameter> getTypeParameterList() {
            return Collections.unmodifiableList(this.typeParameter_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public int getTypeParameterCount() {
            return this.typeParameter_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public IrTypeParameter getTypeParameter(int i) {
            return this.typeParameter_.get(i);
        }

        public Builder setTypeParameter(int i, IrTypeParameter irTypeParameter) {
            irTypeParameter.getClass();
            ensureTypeParameterIsMutable();
            this.typeParameter_.set(i, irTypeParameter);
            return this;
        }

        public Builder setTypeParameter(int i, IrTypeParameter.Builder builder) {
            ensureTypeParameterIsMutable();
            this.typeParameter_.set(i, builder.build());
            return this;
        }

        public Builder addTypeParameter(IrTypeParameter irTypeParameter) {
            irTypeParameter.getClass();
            ensureTypeParameterIsMutable();
            this.typeParameter_.add(irTypeParameter);
            return this;
        }

        public Builder addTypeParameter(int i, IrTypeParameter irTypeParameter) {
            irTypeParameter.getClass();
            ensureTypeParameterIsMutable();
            this.typeParameter_.add(i, irTypeParameter);
            return this;
        }

        public Builder addTypeParameter(IrTypeParameter.Builder builder) {
            ensureTypeParameterIsMutable();
            this.typeParameter_.add(builder.build());
            return this;
        }

        public Builder addTypeParameter(int i, IrTypeParameter.Builder builder) {
            ensureTypeParameterIsMutable();
            this.typeParameter_.add(i, builder.build());
            return this;
        }

        public Builder addAllTypeParameter(Iterable<? extends IrTypeParameter> iterable) {
            ensureTypeParameterIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.typeParameter_);
            return this;
        }

        public Builder clearTypeParameter() {
            this.typeParameter_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
        }

        public Builder removeTypeParameter(int i) {
            ensureTypeParameterIsMutable();
            this.typeParameter_.remove(i);
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public boolean hasDispatchReceiver() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public IrValueParameter getDispatchReceiver() {
            return this.dispatchReceiver_;
        }

        public Builder setDispatchReceiver(IrValueParameter irValueParameter) {
            irValueParameter.getClass();
            this.dispatchReceiver_ = irValueParameter;
            this.bitField0_ |= 8;
            return this;
        }

        public Builder setDispatchReceiver(IrValueParameter.Builder builder) {
            this.dispatchReceiver_ = builder.build();
            this.bitField0_ |= 8;
            return this;
        }

        public Builder mergeDispatchReceiver(IrValueParameter irValueParameter) {
            if ((this.bitField0_ & 8) == 8 && this.dispatchReceiver_ != IrValueParameter.getDefaultInstance()) {
                this.dispatchReceiver_ = IrValueParameter.newBuilder(this.dispatchReceiver_).mergeFrom(irValueParameter).buildPartial();
            } else {
                this.dispatchReceiver_ = irValueParameter;
            }
            this.bitField0_ |= 8;
            return this;
        }

        public Builder clearDispatchReceiver() {
            this.dispatchReceiver_ = IrValueParameter.getDefaultInstance();
            this.bitField0_ &= -9;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public boolean hasExtensionReceiver() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public IrValueParameter getExtensionReceiver() {
            return this.extensionReceiver_;
        }

        public Builder setExtensionReceiver(IrValueParameter irValueParameter) {
            irValueParameter.getClass();
            this.extensionReceiver_ = irValueParameter;
            this.bitField0_ |= 16;
            return this;
        }

        public Builder setExtensionReceiver(IrValueParameter.Builder builder) {
            this.extensionReceiver_ = builder.build();
            this.bitField0_ |= 16;
            return this;
        }

        public Builder mergeExtensionReceiver(IrValueParameter irValueParameter) {
            if ((this.bitField0_ & 16) == 16 && this.extensionReceiver_ != IrValueParameter.getDefaultInstance()) {
                this.extensionReceiver_ = IrValueParameter.newBuilder(this.extensionReceiver_).mergeFrom(irValueParameter).buildPartial();
            } else {
                this.extensionReceiver_ = irValueParameter;
            }
            this.bitField0_ |= 16;
            return this;
        }

        public Builder clearExtensionReceiver() {
            this.extensionReceiver_ = IrValueParameter.getDefaultInstance();
            this.bitField0_ &= -17;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public boolean hasContextReceiverParametersCount() {
            return (this.bitField0_ & 32) == 32;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public int getContextReceiverParametersCount() {
            return this.contextReceiverParametersCount_;
        }

        public Builder setContextReceiverParametersCount(int i) {
            this.bitField0_ |= 32;
            this.contextReceiverParametersCount_ = i;
            return this;
        }

        public Builder clearContextReceiverParametersCount() {
            this.bitField0_ &= -33;
            this.contextReceiverParametersCount_ = 0;
            return this;
        }

        private void ensureValueParameterIsMutable() {
            if ((this.bitField0_ & 64) != 64) {
                this.valueParameter_ = new ArrayList(this.valueParameter_);
                this.bitField0_ |= 64;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public List<IrValueParameter> getValueParameterList() {
            return Collections.unmodifiableList(this.valueParameter_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public int getValueParameterCount() {
            return this.valueParameter_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public IrValueParameter getValueParameter(int i) {
            return this.valueParameter_.get(i);
        }

        public Builder setValueParameter(int i, IrValueParameter irValueParameter) {
            irValueParameter.getClass();
            ensureValueParameterIsMutable();
            this.valueParameter_.set(i, irValueParameter);
            return this;
        }

        public Builder setValueParameter(int i, IrValueParameter.Builder builder) {
            ensureValueParameterIsMutable();
            this.valueParameter_.set(i, builder.build());
            return this;
        }

        public Builder addValueParameter(IrValueParameter irValueParameter) {
            irValueParameter.getClass();
            ensureValueParameterIsMutable();
            this.valueParameter_.add(irValueParameter);
            return this;
        }

        public Builder addValueParameter(int i, IrValueParameter irValueParameter) {
            irValueParameter.getClass();
            ensureValueParameterIsMutable();
            this.valueParameter_.add(i, irValueParameter);
            return this;
        }

        public Builder addValueParameter(IrValueParameter.Builder builder) {
            ensureValueParameterIsMutable();
            this.valueParameter_.add(builder.build());
            return this;
        }

        public Builder addValueParameter(int i, IrValueParameter.Builder builder) {
            ensureValueParameterIsMutable();
            this.valueParameter_.add(i, builder.build());
            return this;
        }

        public Builder addAllValueParameter(Iterable<? extends IrValueParameter> iterable) {
            ensureValueParameterIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.valueParameter_);
            return this;
        }

        public Builder clearValueParameter() {
            this.valueParameter_ = Collections.emptyList();
            this.bitField0_ &= -65;
            return this;
        }

        public Builder removeValueParameter(int i) {
            ensureValueParameterIsMutable();
            this.valueParameter_.remove(i);
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public boolean hasBody() {
            return (this.bitField0_ & 128) == 128;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBaseOrBuilder
        public int getBody() {
            return this.body_;
        }

        public Builder setBody(int i) {
            this.bitField0_ |= 128;
            this.body_ = i;
            return this;
        }

        public Builder clearBody() {
            this.bitField0_ &= -129;
            this.body_ = 0;
            return this;
        }
    }
}
