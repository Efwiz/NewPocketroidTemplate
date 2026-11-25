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
public final class IrTypeAlias extends GeneratedMessageLite implements IrTypeAliasOrBuilder {
    public static final int BASE_FIELD_NUMBER = 1;
    public static final int NAME_TYPE_FIELD_NUMBER = 2;
    public static Parser<IrTypeAlias> PARSER = new AbstractParser<IrTypeAlias>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAlias.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrTypeAlias parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrTypeAlias(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int TYPE_PARAMETER_FIELD_NUMBER = 3;
    private static final IrTypeAlias defaultInstance;
    private static final long serialVersionUID = 0;
    private IrDeclarationBase base_;
    private int bitField0_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private long nameType_;
    private List<IrTypeParameter> typeParameter_;
    private final ByteString unknownFields;

    private IrTypeAlias(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrTypeAlias(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrTypeAlias getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrTypeAlias getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrTypeAlias(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                            } else if (tag != 26) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                if ((c & 4) != 4) {
                                    this.typeParameter_ = new ArrayList();
                                    c = 4;
                                }
                                this.typeParameter_.add((IrTypeParameter) codedInputStream.readMessage(IrTypeParameter.PARSER, extensionRegistryLite));
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
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
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
            this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
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
        IrTypeAlias irTypeAlias = new IrTypeAlias(true);
        defaultInstance = irTypeAlias;
        irTypeAlias.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrTypeAlias> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAliasOrBuilder
    public boolean hasBase() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAliasOrBuilder
    public IrDeclarationBase getBase() {
        return this.base_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAliasOrBuilder
    public boolean hasNameType() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAliasOrBuilder
    public long getNameType() {
        return this.nameType_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAliasOrBuilder
    public List<IrTypeParameter> getTypeParameterList() {
        return this.typeParameter_;
    }

    public List<? extends IrTypeParameterOrBuilder> getTypeParameterOrBuilderList() {
        return this.typeParameter_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAliasOrBuilder
    public int getTypeParameterCount() {
        return this.typeParameter_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAliasOrBuilder
    public IrTypeParameter getTypeParameter(int i) {
        return this.typeParameter_.get(i);
    }

    public IrTypeParameterOrBuilder getTypeParameterOrBuilder(int i) {
        return this.typeParameter_.get(i);
    }

    private void initFields() {
        this.base_ = IrDeclarationBase.getDefaultInstance();
        this.nameType_ = 0L;
        this.typeParameter_ = Collections.emptyList();
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
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrTypeAlias parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrTypeAlias parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrTypeAlias parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrTypeAlias parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrTypeAlias parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrTypeAlias parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrTypeAlias parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrTypeAlias parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrTypeAlias parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrTypeAlias parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrTypeAlias irTypeAlias) {
        return newBuilder().mergeFrom(irTypeAlias);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrTypeAlias, Builder> implements IrTypeAliasOrBuilder {
        private int bitField0_;
        private long nameType_;
        private IrDeclarationBase base_ = IrDeclarationBase.getDefaultInstance();
        private List<IrTypeParameter> typeParameter_ = Collections.emptyList();

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
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrTypeAlias getDefaultInstanceForType() {
            return IrTypeAlias.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrTypeAlias build() {
            IrTypeAlias irTypeAliasBuildPartial = buildPartial();
            if (irTypeAliasBuildPartial.isInitialized()) {
                return irTypeAliasBuildPartial;
            }
            throw newUninitializedMessageException(irTypeAliasBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrTypeAlias buildPartial() {
            IrTypeAlias irTypeAlias = new IrTypeAlias(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irTypeAlias.base_ = this.base_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irTypeAlias.nameType_ = this.nameType_;
            if ((this.bitField0_ & 4) == 4) {
                this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                this.bitField0_ &= -5;
            }
            irTypeAlias.typeParameter_ = this.typeParameter_;
            irTypeAlias.bitField0_ = i2;
            return irTypeAlias;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrTypeAlias irTypeAlias) {
            if (irTypeAlias == IrTypeAlias.getDefaultInstance()) {
                return this;
            }
            if (irTypeAlias.hasBase()) {
                mergeBase(irTypeAlias.getBase());
            }
            if (irTypeAlias.hasNameType()) {
                setNameType(irTypeAlias.getNameType());
            }
            if (!irTypeAlias.typeParameter_.isEmpty()) {
                if (this.typeParameter_.isEmpty()) {
                    this.typeParameter_ = irTypeAlias.typeParameter_;
                    this.bitField0_ &= -5;
                } else {
                    ensureTypeParameterIsMutable();
                    this.typeParameter_.addAll(irTypeAlias.typeParameter_);
                }
            }
            setUnknownFields(getUnknownFields().concat(irTypeAlias.unknownFields));
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
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrTypeAlias irTypeAlias = null;
            try {
                try {
                    IrTypeAlias partialFrom = IrTypeAlias.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrTypeAlias irTypeAlias2 = (IrTypeAlias) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irTypeAlias = irTypeAlias2;
                        if (irTypeAlias != null) {
                            mergeFrom(irTypeAlias);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irTypeAlias != null) {
                    mergeFrom(irTypeAlias);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAliasOrBuilder
        public boolean hasBase() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAliasOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAliasOrBuilder
        public boolean hasNameType() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAliasOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAliasOrBuilder
        public List<IrTypeParameter> getTypeParameterList() {
            return Collections.unmodifiableList(this.typeParameter_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAliasOrBuilder
        public int getTypeParameterCount() {
            return this.typeParameter_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAliasOrBuilder
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
    }
}
