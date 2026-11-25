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
public final class IrTypeParameter extends GeneratedMessageLite implements IrTypeParameterOrBuilder {
    public static final int BASE_FIELD_NUMBER = 1;
    public static final int NAME_FIELD_NUMBER = 2;
    public static Parser<IrTypeParameter> PARSER = new AbstractParser<IrTypeParameter>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameter.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrTypeParameter parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrTypeParameter(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int SUPER_TYPE_FIELD_NUMBER = 3;
    private static final IrTypeParameter defaultInstance;
    private static final long serialVersionUID = 0;
    private IrDeclarationBase base_;
    private int bitField0_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int name_;
    private int superTypeMemoizedSerializedSize;
    private List<Integer> superType_;
    private final ByteString unknownFields;

    private IrTypeParameter(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.superTypeMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrTypeParameter(boolean z) {
        this.superTypeMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrTypeParameter getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrTypeParameter getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrTypeParameter(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.superTypeMemoizedSerializedSize = -1;
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
                            this.name_ = codedInputStream.readInt32();
                        } else if (tag == 24) {
                            if ((c & 4) != 4) {
                                this.superType_ = new ArrayList();
                                c = 4;
                            }
                            this.superType_.add(Integer.valueOf(codedInputStream.readInt32()));
                        } else if (tag != 26) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                            if ((c & 4) != 4 && codedInputStream.getBytesUntilLimit() > 0) {
                                this.superType_ = new ArrayList();
                                c = 4;
                            }
                            while (codedInputStream.getBytesUntilLimit() > 0) {
                                this.superType_.add(Integer.valueOf(codedInputStream.readInt32()));
                            }
                            codedInputStream.popLimit(iPushLimit);
                        }
                    }
                    z = true;
                } catch (Throwable th) {
                    if ((c & 4) == 4) {
                        this.superType_ = Collections.unmodifiableList(this.superType_);
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
        if ((c & 4) == 4) {
            this.superType_ = Collections.unmodifiableList(this.superType_);
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
        IrTypeParameter irTypeParameter = new IrTypeParameter(true);
        defaultInstance = irTypeParameter;
        irTypeParameter.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrTypeParameter> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameterOrBuilder
    public boolean hasBase() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameterOrBuilder
    public IrDeclarationBase getBase() {
        return this.base_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameterOrBuilder
    public boolean hasName() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameterOrBuilder
    public int getName() {
        return this.name_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameterOrBuilder
    public List<Integer> getSuperTypeList() {
        return this.superType_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameterOrBuilder
    public int getSuperTypeCount() {
        return this.superType_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameterOrBuilder
    public int getSuperType(int i) {
        return this.superType_.get(i).intValue();
    }

    private void initFields() {
        this.base_ = IrDeclarationBase.getDefaultInstance();
        this.name_ = 0;
        this.superType_ = Collections.emptyList();
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
        if (!hasName()) {
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
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt32(2, this.name_);
        }
        if (getSuperTypeList().size() > 0) {
            codedOutputStream.writeRawVarint32(26);
            codedOutputStream.writeRawVarint32(this.superTypeMemoizedSerializedSize);
        }
        for (int i = 0; i < this.superType_.size(); i++) {
            codedOutputStream.writeInt32NoTag(this.superType_.get(i).intValue());
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
            iComputeMessageSize += CodedOutputStream.computeInt32Size(2, this.name_);
        }
        int iComputeInt32SizeNoTag = 0;
        for (int i2 = 0; i2 < this.superType_.size(); i2++) {
            iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.superType_.get(i2).intValue());
        }
        int iComputeInt32SizeNoTag2 = iComputeMessageSize + iComputeInt32SizeNoTag;
        if (!getSuperTypeList().isEmpty()) {
            iComputeInt32SizeNoTag2 = iComputeInt32SizeNoTag2 + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag);
        }
        this.superTypeMemoizedSerializedSize = iComputeInt32SizeNoTag;
        int size = iComputeInt32SizeNoTag2 + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrTypeParameter parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrTypeParameter parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrTypeParameter parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrTypeParameter parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrTypeParameter parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrTypeParameter parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrTypeParameter parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrTypeParameter parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrTypeParameter parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrTypeParameter parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrTypeParameter irTypeParameter) {
        return newBuilder().mergeFrom(irTypeParameter);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrTypeParameter, Builder> implements IrTypeParameterOrBuilder {
        private int bitField0_;
        private int name_;
        private IrDeclarationBase base_ = IrDeclarationBase.getDefaultInstance();
        private List<Integer> superType_ = Collections.emptyList();

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
            this.name_ = 0;
            this.bitField0_ = i & (-4);
            this.superType_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrTypeParameter getDefaultInstanceForType() {
            return IrTypeParameter.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrTypeParameter build() {
            IrTypeParameter irTypeParameterBuildPartial = buildPartial();
            if (irTypeParameterBuildPartial.isInitialized()) {
                return irTypeParameterBuildPartial;
            }
            throw newUninitializedMessageException(irTypeParameterBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrTypeParameter buildPartial() {
            IrTypeParameter irTypeParameter = new IrTypeParameter(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irTypeParameter.base_ = this.base_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irTypeParameter.name_ = this.name_;
            if ((this.bitField0_ & 4) == 4) {
                this.superType_ = Collections.unmodifiableList(this.superType_);
                this.bitField0_ &= -5;
            }
            irTypeParameter.superType_ = this.superType_;
            irTypeParameter.bitField0_ = i2;
            return irTypeParameter;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrTypeParameter irTypeParameter) {
            if (irTypeParameter == IrTypeParameter.getDefaultInstance()) {
                return this;
            }
            if (irTypeParameter.hasBase()) {
                mergeBase(irTypeParameter.getBase());
            }
            if (irTypeParameter.hasName()) {
                setName(irTypeParameter.getName());
            }
            if (!irTypeParameter.superType_.isEmpty()) {
                if (this.superType_.isEmpty()) {
                    this.superType_ = irTypeParameter.superType_;
                    this.bitField0_ &= -5;
                } else {
                    ensureSuperTypeIsMutable();
                    this.superType_.addAll(irTypeParameter.superType_);
                }
            }
            setUnknownFields(getUnknownFields().concat(irTypeParameter.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasBase() && hasName() && getBase().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrTypeParameter irTypeParameter = null;
            try {
                try {
                    IrTypeParameter partialFrom = IrTypeParameter.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrTypeParameter irTypeParameter2 = (IrTypeParameter) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irTypeParameter = irTypeParameter2;
                        if (irTypeParameter != null) {
                            mergeFrom(irTypeParameter);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irTypeParameter != null) {
                    mergeFrom(irTypeParameter);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameterOrBuilder
        public boolean hasBase() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameterOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameterOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameterOrBuilder
        public int getName() {
            return this.name_;
        }

        public Builder setName(int i) {
            this.bitField0_ |= 2;
            this.name_ = i;
            return this;
        }

        public Builder clearName() {
            this.bitField0_ &= -3;
            this.name_ = 0;
            return this;
        }

        private void ensureSuperTypeIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
                this.superType_ = new ArrayList(this.superType_);
                this.bitField0_ |= 4;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameterOrBuilder
        public List<Integer> getSuperTypeList() {
            return Collections.unmodifiableList(this.superType_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameterOrBuilder
        public int getSuperTypeCount() {
            return this.superType_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameterOrBuilder
        public int getSuperType(int i) {
            return this.superType_.get(i).intValue();
        }

        public Builder setSuperType(int i, int i2) {
            ensureSuperTypeIsMutable();
            this.superType_.set(i, Integer.valueOf(i2));
            return this;
        }

        public Builder addSuperType(int i) {
            ensureSuperTypeIsMutable();
            this.superType_.add(Integer.valueOf(i));
            return this;
        }

        public Builder addAllSuperType(Iterable<? extends Integer> iterable) {
            ensureSuperTypeIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.superType_);
            return this;
        }

        public Builder clearSuperType() {
            this.superType_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
        }
    }
}
