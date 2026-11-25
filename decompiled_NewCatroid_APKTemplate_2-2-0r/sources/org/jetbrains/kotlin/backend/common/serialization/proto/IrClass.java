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
public final class IrClass extends GeneratedMessageLite implements IrClassOrBuilder {
    public static final int BASE_FIELD_NUMBER = 1;
    public static final int DECLARATION_FIELD_NUMBER = 5;
    public static final int INLINE_CLASS_REPRESENTATION_FIELD_NUMBER = 7;
    public static final int MULTI_FIELD_VALUE_CLASS_REPRESENTATION_FIELD_NUMBER = 9;
    public static final int NAME_FIELD_NUMBER = 2;
    public static Parser<IrClass> PARSER = new AbstractParser<IrClass>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrClass.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrClass parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrClass(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int SEALED_SUBCLASS_FIELD_NUMBER = 8;
    public static final int SUPER_TYPE_FIELD_NUMBER = 6;
    public static final int THIS_RECEIVER_FIELD_NUMBER = 3;
    public static final int TYPE_PARAMETER_FIELD_NUMBER = 4;
    private static final IrClass defaultInstance;
    private static final long serialVersionUID = 0;
    private IrDeclarationBase base_;
    private int bitField0_;
    private List<IrDeclaration> declaration_;
    private IrInlineClassRepresentation inlineClassRepresentation_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private IrMultiFieldValueClassRepresentation multiFieldValueClassRepresentation_;
    private int name_;
    private int sealedSubclassMemoizedSerializedSize;
    private List<Long> sealedSubclass_;
    private int superTypeMemoizedSerializedSize;
    private List<Integer> superType_;
    private IrValueParameter thisReceiver_;
    private List<IrTypeParameter> typeParameter_;
    private final ByteString unknownFields;

    private IrClass(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.superTypeMemoizedSerializedSize = -1;
        this.sealedSubclassMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrClass(boolean z) {
        this.superTypeMemoizedSerializedSize = -1;
        this.sealedSubclassMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrClass getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrClass getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrClass(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        GeneratedMessageLite.Builder builder;
        this.superTypeMemoizedSerializedSize = -1;
        this.sealedSubclassMemoizedSerializedSize = -1;
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
                    switch (tag) {
                        case 0:
                            break;
                        case 10:
                            builder = (this.bitField0_ & 1) == 1 ? this.base_.toBuilder() : null;
                            IrDeclarationBase irDeclarationBase = (IrDeclarationBase) codedInputStream.readMessage(IrDeclarationBase.PARSER, extensionRegistryLite);
                            this.base_ = irDeclarationBase;
                            if (builder != null) {
                                builder.mergeFrom(irDeclarationBase);
                                this.base_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            continue;
                        case 16:
                            this.bitField0_ |= 2;
                            this.name_ = codedInputStream.readInt32();
                            continue;
                        case 26:
                            builder = (this.bitField0_ & 4) == 4 ? this.thisReceiver_.toBuilder() : null;
                            IrValueParameter irValueParameter = (IrValueParameter) codedInputStream.readMessage(IrValueParameter.PARSER, extensionRegistryLite);
                            this.thisReceiver_ = irValueParameter;
                            if (builder != null) {
                                builder.mergeFrom(irValueParameter);
                                this.thisReceiver_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 4;
                            continue;
                        case 34:
                            if ((i & 8) != 8) {
                                this.typeParameter_ = new ArrayList();
                                i |= 8;
                            }
                            this.typeParameter_.add((IrTypeParameter) codedInputStream.readMessage(IrTypeParameter.PARSER, extensionRegistryLite));
                            continue;
                        case 42:
                            if ((i & 16) != 16) {
                                this.declaration_ = new ArrayList();
                                i |= 16;
                            }
                            this.declaration_.add((IrDeclaration) codedInputStream.readMessage(IrDeclaration.PARSER, extensionRegistryLite));
                            continue;
                        case 48:
                            if ((i & 32) != 32) {
                                this.superType_ = new ArrayList();
                                i |= 32;
                            }
                            this.superType_.add(Integer.valueOf(codedInputStream.readInt32()));
                            continue;
                        case 50:
                            int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                            if ((i & 32) != 32 && codedInputStream.getBytesUntilLimit() > 0) {
                                this.superType_ = new ArrayList();
                                i |= 32;
                            }
                            while (codedInputStream.getBytesUntilLimit() > 0) {
                                this.superType_.add(Integer.valueOf(codedInputStream.readInt32()));
                            }
                            codedInputStream.popLimit(iPushLimit);
                            continue;
                        case 58:
                            builder = (this.bitField0_ & 8) == 8 ? this.inlineClassRepresentation_.toBuilder() : null;
                            IrInlineClassRepresentation irInlineClassRepresentation = (IrInlineClassRepresentation) codedInputStream.readMessage(IrInlineClassRepresentation.PARSER, extensionRegistryLite);
                            this.inlineClassRepresentation_ = irInlineClassRepresentation;
                            if (builder != null) {
                                builder.mergeFrom(irInlineClassRepresentation);
                                this.inlineClassRepresentation_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 8;
                            continue;
                        case 64:
                            if ((i & 256) != 256) {
                                this.sealedSubclass_ = new ArrayList();
                                i |= 256;
                            }
                            this.sealedSubclass_.add(Long.valueOf(codedInputStream.readInt64()));
                            continue;
                        case 66:
                            int iPushLimit2 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                            if ((i & 256) != 256 && codedInputStream.getBytesUntilLimit() > 0) {
                                this.sealedSubclass_ = new ArrayList();
                                i |= 256;
                            }
                            while (codedInputStream.getBytesUntilLimit() > 0) {
                                this.sealedSubclass_.add(Long.valueOf(codedInputStream.readInt64()));
                            }
                            codedInputStream.popLimit(iPushLimit2);
                            continue;
                        case 74:
                            builder = (this.bitField0_ & 16) == 16 ? this.multiFieldValueClassRepresentation_.toBuilder() : null;
                            IrMultiFieldValueClassRepresentation irMultiFieldValueClassRepresentation = (IrMultiFieldValueClassRepresentation) codedInputStream.readMessage(IrMultiFieldValueClassRepresentation.PARSER, extensionRegistryLite);
                            this.multiFieldValueClassRepresentation_ = irMultiFieldValueClassRepresentation;
                            if (builder != null) {
                                builder.mergeFrom(irMultiFieldValueClassRepresentation);
                                this.multiFieldValueClassRepresentation_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 16;
                            continue;
                        default:
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                break;
                            }
                            break;
                    }
                    z = true;
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                }
            } catch (Throwable th) {
                if ((i & 8) == 8) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                }
                if ((i & 16) == 16) {
                    this.declaration_ = Collections.unmodifiableList(this.declaration_);
                }
                if ((i & 32) == 32) {
                    this.superType_ = Collections.unmodifiableList(this.superType_);
                }
                if ((i & 256) == 256) {
                    this.sealedSubclass_ = Collections.unmodifiableList(this.sealedSubclass_);
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
        if ((i & 8) == 8) {
            this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
        }
        if ((i & 16) == 16) {
            this.declaration_ = Collections.unmodifiableList(this.declaration_);
        }
        if ((i & 32) == 32) {
            this.superType_ = Collections.unmodifiableList(this.superType_);
        }
        if ((i & 256) == 256) {
            this.sealedSubclass_ = Collections.unmodifiableList(this.sealedSubclass_);
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
        IrClass irClass = new IrClass(true);
        defaultInstance = irClass;
        irClass.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrClass> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public boolean hasBase() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public IrDeclarationBase getBase() {
        return this.base_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public boolean hasName() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public int getName() {
        return this.name_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public boolean hasThisReceiver() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public IrValueParameter getThisReceiver() {
        return this.thisReceiver_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public List<IrTypeParameter> getTypeParameterList() {
        return this.typeParameter_;
    }

    public List<? extends IrTypeParameterOrBuilder> getTypeParameterOrBuilderList() {
        return this.typeParameter_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public int getTypeParameterCount() {
        return this.typeParameter_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public IrTypeParameter getTypeParameter(int i) {
        return this.typeParameter_.get(i);
    }

    public IrTypeParameterOrBuilder getTypeParameterOrBuilder(int i) {
        return this.typeParameter_.get(i);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public List<IrDeclaration> getDeclarationList() {
        return this.declaration_;
    }

    public List<? extends IrDeclarationOrBuilder> getDeclarationOrBuilderList() {
        return this.declaration_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public int getDeclarationCount() {
        return this.declaration_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public IrDeclaration getDeclaration(int i) {
        return this.declaration_.get(i);
    }

    public IrDeclarationOrBuilder getDeclarationOrBuilder(int i) {
        return this.declaration_.get(i);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public List<Integer> getSuperTypeList() {
        return this.superType_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public int getSuperTypeCount() {
        return this.superType_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public int getSuperType(int i) {
        return this.superType_.get(i).intValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public boolean hasInlineClassRepresentation() {
        return (this.bitField0_ & 8) == 8;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public IrInlineClassRepresentation getInlineClassRepresentation() {
        return this.inlineClassRepresentation_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public boolean hasMultiFieldValueClassRepresentation() {
        return (this.bitField0_ & 16) == 16;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public IrMultiFieldValueClassRepresentation getMultiFieldValueClassRepresentation() {
        return this.multiFieldValueClassRepresentation_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public List<Long> getSealedSubclassList() {
        return this.sealedSubclass_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public int getSealedSubclassCount() {
        return this.sealedSubclass_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
    public long getSealedSubclass(int i) {
        return this.sealedSubclass_.get(i).longValue();
    }

    private void initFields() {
        this.base_ = IrDeclarationBase.getDefaultInstance();
        this.name_ = 0;
        this.thisReceiver_ = IrValueParameter.getDefaultInstance();
        this.typeParameter_ = Collections.emptyList();
        this.declaration_ = Collections.emptyList();
        this.superType_ = Collections.emptyList();
        this.inlineClassRepresentation_ = IrInlineClassRepresentation.getDefaultInstance();
        this.multiFieldValueClassRepresentation_ = IrMultiFieldValueClassRepresentation.getDefaultInstance();
        this.sealedSubclass_ = Collections.emptyList();
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
        if (hasThisReceiver() && !getThisReceiver().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        for (int i = 0; i < getTypeParameterCount(); i++) {
            if (!getTypeParameter(i).isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
        }
        for (int i2 = 0; i2 < getDeclarationCount(); i2++) {
            if (!getDeclaration(i2).isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
        }
        if (hasInlineClassRepresentation() && !getInlineClassRepresentation().isInitialized()) {
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
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeMessage(3, this.thisReceiver_);
        }
        for (int i = 0; i < this.typeParameter_.size(); i++) {
            codedOutputStream.writeMessage(4, this.typeParameter_.get(i));
        }
        for (int i2 = 0; i2 < this.declaration_.size(); i2++) {
            codedOutputStream.writeMessage(5, this.declaration_.get(i2));
        }
        if (getSuperTypeList().size() > 0) {
            codedOutputStream.writeRawVarint32(50);
            codedOutputStream.writeRawVarint32(this.superTypeMemoizedSerializedSize);
        }
        for (int i3 = 0; i3 < this.superType_.size(); i3++) {
            codedOutputStream.writeInt32NoTag(this.superType_.get(i3).intValue());
        }
        if ((this.bitField0_ & 8) == 8) {
            codedOutputStream.writeMessage(7, this.inlineClassRepresentation_);
        }
        if (getSealedSubclassList().size() > 0) {
            codedOutputStream.writeRawVarint32(66);
            codedOutputStream.writeRawVarint32(this.sealedSubclassMemoizedSerializedSize);
        }
        for (int i4 = 0; i4 < this.sealedSubclass_.size(); i4++) {
            codedOutputStream.writeInt64NoTag(this.sealedSubclass_.get(i4).longValue());
        }
        if ((this.bitField0_ & 16) == 16) {
            codedOutputStream.writeMessage(9, this.multiFieldValueClassRepresentation_);
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
        if ((this.bitField0_ & 4) == 4) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(3, this.thisReceiver_);
        }
        for (int i2 = 0; i2 < this.typeParameter_.size(); i2++) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(4, this.typeParameter_.get(i2));
        }
        for (int i3 = 0; i3 < this.declaration_.size(); i3++) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(5, this.declaration_.get(i3));
        }
        int iComputeInt32SizeNoTag = 0;
        for (int i4 = 0; i4 < this.superType_.size(); i4++) {
            iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.superType_.get(i4).intValue());
        }
        int iComputeMessageSize2 = iComputeMessageSize + iComputeInt32SizeNoTag;
        if (!getSuperTypeList().isEmpty()) {
            iComputeMessageSize2 = iComputeMessageSize2 + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag);
        }
        this.superTypeMemoizedSerializedSize = iComputeInt32SizeNoTag;
        if ((this.bitField0_ & 8) == 8) {
            iComputeMessageSize2 += CodedOutputStream.computeMessageSize(7, this.inlineClassRepresentation_);
        }
        int iComputeInt64SizeNoTag = 0;
        for (int i5 = 0; i5 < this.sealedSubclass_.size(); i5++) {
            iComputeInt64SizeNoTag += CodedOutputStream.computeInt64SizeNoTag(this.sealedSubclass_.get(i5).longValue());
        }
        int iComputeMessageSize3 = iComputeMessageSize2 + iComputeInt64SizeNoTag;
        if (!getSealedSubclassList().isEmpty()) {
            iComputeMessageSize3 = iComputeMessageSize3 + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt64SizeNoTag);
        }
        this.sealedSubclassMemoizedSerializedSize = iComputeInt64SizeNoTag;
        if ((this.bitField0_ & 16) == 16) {
            iComputeMessageSize3 += CodedOutputStream.computeMessageSize(9, this.multiFieldValueClassRepresentation_);
        }
        int size = iComputeMessageSize3 + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrClass parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrClass parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrClass parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrClass parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrClass parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrClass parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrClass parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrClass parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrClass parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrClass parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrClass irClass) {
        return newBuilder().mergeFrom(irClass);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrClass, Builder> implements IrClassOrBuilder {
        private int bitField0_;
        private int name_;
        private IrDeclarationBase base_ = IrDeclarationBase.getDefaultInstance();
        private IrValueParameter thisReceiver_ = IrValueParameter.getDefaultInstance();
        private List<IrTypeParameter> typeParameter_ = Collections.emptyList();
        private List<IrDeclaration> declaration_ = Collections.emptyList();
        private List<Integer> superType_ = Collections.emptyList();
        private IrInlineClassRepresentation inlineClassRepresentation_ = IrInlineClassRepresentation.getDefaultInstance();
        private IrMultiFieldValueClassRepresentation multiFieldValueClassRepresentation_ = IrMultiFieldValueClassRepresentation.getDefaultInstance();
        private List<Long> sealedSubclass_ = Collections.emptyList();

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
            this.thisReceiver_ = IrValueParameter.getDefaultInstance();
            this.bitField0_ &= -5;
            this.typeParameter_ = Collections.emptyList();
            this.bitField0_ &= -9;
            this.declaration_ = Collections.emptyList();
            this.bitField0_ &= -17;
            this.superType_ = Collections.emptyList();
            this.bitField0_ &= -33;
            this.inlineClassRepresentation_ = IrInlineClassRepresentation.getDefaultInstance();
            this.bitField0_ &= -65;
            this.multiFieldValueClassRepresentation_ = IrMultiFieldValueClassRepresentation.getDefaultInstance();
            this.bitField0_ &= -129;
            this.sealedSubclass_ = Collections.emptyList();
            this.bitField0_ &= -257;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrClass getDefaultInstanceForType() {
            return IrClass.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrClass build() {
            IrClass irClassBuildPartial = buildPartial();
            if (irClassBuildPartial.isInitialized()) {
                return irClassBuildPartial;
            }
            throw newUninitializedMessageException(irClassBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrClass buildPartial() {
            IrClass irClass = new IrClass(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irClass.base_ = this.base_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irClass.name_ = this.name_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            irClass.thisReceiver_ = this.thisReceiver_;
            if ((this.bitField0_ & 8) == 8) {
                this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                this.bitField0_ &= -9;
            }
            irClass.typeParameter_ = this.typeParameter_;
            if ((this.bitField0_ & 16) == 16) {
                this.declaration_ = Collections.unmodifiableList(this.declaration_);
                this.bitField0_ &= -17;
            }
            irClass.declaration_ = this.declaration_;
            if ((this.bitField0_ & 32) == 32) {
                this.superType_ = Collections.unmodifiableList(this.superType_);
                this.bitField0_ &= -33;
            }
            irClass.superType_ = this.superType_;
            if ((i & 64) == 64) {
                i2 |= 8;
            }
            irClass.inlineClassRepresentation_ = this.inlineClassRepresentation_;
            if ((i & 128) == 128) {
                i2 |= 16;
            }
            irClass.multiFieldValueClassRepresentation_ = this.multiFieldValueClassRepresentation_;
            if ((this.bitField0_ & 256) == 256) {
                this.sealedSubclass_ = Collections.unmodifiableList(this.sealedSubclass_);
                this.bitField0_ &= -257;
            }
            irClass.sealedSubclass_ = this.sealedSubclass_;
            irClass.bitField0_ = i2;
            return irClass;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrClass irClass) {
            if (irClass == IrClass.getDefaultInstance()) {
                return this;
            }
            if (irClass.hasBase()) {
                mergeBase(irClass.getBase());
            }
            if (irClass.hasName()) {
                setName(irClass.getName());
            }
            if (irClass.hasThisReceiver()) {
                mergeThisReceiver(irClass.getThisReceiver());
            }
            if (!irClass.typeParameter_.isEmpty()) {
                if (this.typeParameter_.isEmpty()) {
                    this.typeParameter_ = irClass.typeParameter_;
                    this.bitField0_ &= -9;
                } else {
                    ensureTypeParameterIsMutable();
                    this.typeParameter_.addAll(irClass.typeParameter_);
                }
            }
            if (!irClass.declaration_.isEmpty()) {
                if (this.declaration_.isEmpty()) {
                    this.declaration_ = irClass.declaration_;
                    this.bitField0_ &= -17;
                } else {
                    ensureDeclarationIsMutable();
                    this.declaration_.addAll(irClass.declaration_);
                }
            }
            if (!irClass.superType_.isEmpty()) {
                if (this.superType_.isEmpty()) {
                    this.superType_ = irClass.superType_;
                    this.bitField0_ &= -33;
                } else {
                    ensureSuperTypeIsMutable();
                    this.superType_.addAll(irClass.superType_);
                }
            }
            if (irClass.hasInlineClassRepresentation()) {
                mergeInlineClassRepresentation(irClass.getInlineClassRepresentation());
            }
            if (irClass.hasMultiFieldValueClassRepresentation()) {
                mergeMultiFieldValueClassRepresentation(irClass.getMultiFieldValueClassRepresentation());
            }
            if (!irClass.sealedSubclass_.isEmpty()) {
                if (this.sealedSubclass_.isEmpty()) {
                    this.sealedSubclass_ = irClass.sealedSubclass_;
                    this.bitField0_ &= -257;
                } else {
                    ensureSealedSubclassIsMutable();
                    this.sealedSubclass_.addAll(irClass.sealedSubclass_);
                }
            }
            setUnknownFields(getUnknownFields().concat(irClass.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (!hasBase() || !hasName() || !getBase().isInitialized()) {
                return false;
            }
            if (hasThisReceiver() && !getThisReceiver().isInitialized()) {
                return false;
            }
            for (int i = 0; i < getTypeParameterCount(); i++) {
                if (!getTypeParameter(i).isInitialized()) {
                    return false;
                }
            }
            for (int i2 = 0; i2 < getDeclarationCount(); i2++) {
                if (!getDeclaration(i2).isInitialized()) {
                    return false;
                }
            }
            return !hasInlineClassRepresentation() || getInlineClassRepresentation().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrClass irClass = null;
            try {
                try {
                    IrClass partialFrom = IrClass.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrClass irClass2 = (IrClass) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irClass = irClass2;
                        if (irClass != null) {
                            mergeFrom(irClass);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irClass != null) {
                    mergeFrom(irClass);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public boolean hasBase() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public boolean hasThisReceiver() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public IrValueParameter getThisReceiver() {
            return this.thisReceiver_;
        }

        public Builder setThisReceiver(IrValueParameter irValueParameter) {
            irValueParameter.getClass();
            this.thisReceiver_ = irValueParameter;
            this.bitField0_ |= 4;
            return this;
        }

        public Builder setThisReceiver(IrValueParameter.Builder builder) {
            this.thisReceiver_ = builder.build();
            this.bitField0_ |= 4;
            return this;
        }

        public Builder mergeThisReceiver(IrValueParameter irValueParameter) {
            if ((this.bitField0_ & 4) == 4 && this.thisReceiver_ != IrValueParameter.getDefaultInstance()) {
                this.thisReceiver_ = IrValueParameter.newBuilder(this.thisReceiver_).mergeFrom(irValueParameter).buildPartial();
            } else {
                this.thisReceiver_ = irValueParameter;
            }
            this.bitField0_ |= 4;
            return this;
        }

        public Builder clearThisReceiver() {
            this.thisReceiver_ = IrValueParameter.getDefaultInstance();
            this.bitField0_ &= -5;
            return this;
        }

        private void ensureTypeParameterIsMutable() {
            if ((this.bitField0_ & 8) != 8) {
                this.typeParameter_ = new ArrayList(this.typeParameter_);
                this.bitField0_ |= 8;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public List<IrTypeParameter> getTypeParameterList() {
            return Collections.unmodifiableList(this.typeParameter_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public int getTypeParameterCount() {
            return this.typeParameter_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
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
            this.bitField0_ &= -9;
            return this;
        }

        public Builder removeTypeParameter(int i) {
            ensureTypeParameterIsMutable();
            this.typeParameter_.remove(i);
            return this;
        }

        private void ensureDeclarationIsMutable() {
            if ((this.bitField0_ & 16) != 16) {
                this.declaration_ = new ArrayList(this.declaration_);
                this.bitField0_ |= 16;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public List<IrDeclaration> getDeclarationList() {
            return Collections.unmodifiableList(this.declaration_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public int getDeclarationCount() {
            return this.declaration_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public IrDeclaration getDeclaration(int i) {
            return this.declaration_.get(i);
        }

        public Builder setDeclaration(int i, IrDeclaration irDeclaration) {
            irDeclaration.getClass();
            ensureDeclarationIsMutable();
            this.declaration_.set(i, irDeclaration);
            return this;
        }

        public Builder setDeclaration(int i, IrDeclaration.Builder builder) {
            ensureDeclarationIsMutable();
            this.declaration_.set(i, builder.build());
            return this;
        }

        public Builder addDeclaration(IrDeclaration irDeclaration) {
            irDeclaration.getClass();
            ensureDeclarationIsMutable();
            this.declaration_.add(irDeclaration);
            return this;
        }

        public Builder addDeclaration(int i, IrDeclaration irDeclaration) {
            irDeclaration.getClass();
            ensureDeclarationIsMutable();
            this.declaration_.add(i, irDeclaration);
            return this;
        }

        public Builder addDeclaration(IrDeclaration.Builder builder) {
            ensureDeclarationIsMutable();
            this.declaration_.add(builder.build());
            return this;
        }

        public Builder addDeclaration(int i, IrDeclaration.Builder builder) {
            ensureDeclarationIsMutable();
            this.declaration_.add(i, builder.build());
            return this;
        }

        public Builder addAllDeclaration(Iterable<? extends IrDeclaration> iterable) {
            ensureDeclarationIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.declaration_);
            return this;
        }

        public Builder clearDeclaration() {
            this.declaration_ = Collections.emptyList();
            this.bitField0_ &= -17;
            return this;
        }

        public Builder removeDeclaration(int i) {
            ensureDeclarationIsMutable();
            this.declaration_.remove(i);
            return this;
        }

        private void ensureSuperTypeIsMutable() {
            if ((this.bitField0_ & 32) != 32) {
                this.superType_ = new ArrayList(this.superType_);
                this.bitField0_ |= 32;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public List<Integer> getSuperTypeList() {
            return Collections.unmodifiableList(this.superType_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public int getSuperTypeCount() {
            return this.superType_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
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
            this.bitField0_ &= -33;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public boolean hasInlineClassRepresentation() {
            return (this.bitField0_ & 64) == 64;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public IrInlineClassRepresentation getInlineClassRepresentation() {
            return this.inlineClassRepresentation_;
        }

        public Builder setInlineClassRepresentation(IrInlineClassRepresentation irInlineClassRepresentation) {
            irInlineClassRepresentation.getClass();
            this.inlineClassRepresentation_ = irInlineClassRepresentation;
            this.bitField0_ |= 64;
            return this;
        }

        public Builder setInlineClassRepresentation(IrInlineClassRepresentation.Builder builder) {
            this.inlineClassRepresentation_ = builder.build();
            this.bitField0_ |= 64;
            return this;
        }

        public Builder mergeInlineClassRepresentation(IrInlineClassRepresentation irInlineClassRepresentation) {
            if ((this.bitField0_ & 64) == 64 && this.inlineClassRepresentation_ != IrInlineClassRepresentation.getDefaultInstance()) {
                this.inlineClassRepresentation_ = IrInlineClassRepresentation.newBuilder(this.inlineClassRepresentation_).mergeFrom(irInlineClassRepresentation).buildPartial();
            } else {
                this.inlineClassRepresentation_ = irInlineClassRepresentation;
            }
            this.bitField0_ |= 64;
            return this;
        }

        public Builder clearInlineClassRepresentation() {
            this.inlineClassRepresentation_ = IrInlineClassRepresentation.getDefaultInstance();
            this.bitField0_ &= -65;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public boolean hasMultiFieldValueClassRepresentation() {
            return (this.bitField0_ & 128) == 128;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public IrMultiFieldValueClassRepresentation getMultiFieldValueClassRepresentation() {
            return this.multiFieldValueClassRepresentation_;
        }

        public Builder setMultiFieldValueClassRepresentation(IrMultiFieldValueClassRepresentation irMultiFieldValueClassRepresentation) {
            irMultiFieldValueClassRepresentation.getClass();
            this.multiFieldValueClassRepresentation_ = irMultiFieldValueClassRepresentation;
            this.bitField0_ |= 128;
            return this;
        }

        public Builder setMultiFieldValueClassRepresentation(IrMultiFieldValueClassRepresentation.Builder builder) {
            this.multiFieldValueClassRepresentation_ = builder.build();
            this.bitField0_ |= 128;
            return this;
        }

        public Builder mergeMultiFieldValueClassRepresentation(IrMultiFieldValueClassRepresentation irMultiFieldValueClassRepresentation) {
            if ((this.bitField0_ & 128) == 128 && this.multiFieldValueClassRepresentation_ != IrMultiFieldValueClassRepresentation.getDefaultInstance()) {
                this.multiFieldValueClassRepresentation_ = IrMultiFieldValueClassRepresentation.newBuilder(this.multiFieldValueClassRepresentation_).mergeFrom(irMultiFieldValueClassRepresentation).buildPartial();
            } else {
                this.multiFieldValueClassRepresentation_ = irMultiFieldValueClassRepresentation;
            }
            this.bitField0_ |= 128;
            return this;
        }

        public Builder clearMultiFieldValueClassRepresentation() {
            this.multiFieldValueClassRepresentation_ = IrMultiFieldValueClassRepresentation.getDefaultInstance();
            this.bitField0_ &= -129;
            return this;
        }

        private void ensureSealedSubclassIsMutable() {
            if ((this.bitField0_ & 256) != 256) {
                this.sealedSubclass_ = new ArrayList(this.sealedSubclass_);
                this.bitField0_ |= 256;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public List<Long> getSealedSubclassList() {
            return Collections.unmodifiableList(this.sealedSubclass_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public int getSealedSubclassCount() {
            return this.sealedSubclass_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassOrBuilder
        public long getSealedSubclass(int i) {
            return this.sealedSubclass_.get(i).longValue();
        }

        public Builder setSealedSubclass(int i, long j) {
            ensureSealedSubclassIsMutable();
            this.sealedSubclass_.set(i, Long.valueOf(j));
            return this;
        }

        public Builder addSealedSubclass(long j) {
            ensureSealedSubclassIsMutable();
            this.sealedSubclass_.add(Long.valueOf(j));
            return this;
        }

        public Builder addAllSealedSubclass(Iterable<? extends Long> iterable) {
            ensureSealedSubclassIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.sealedSubclass_);
            return this;
        }

        public Builder clearSealedSubclass() {
            this.sealedSubclass_ = Collections.emptyList();
            this.bitField0_ &= -257;
            return this;
        }
    }
}
