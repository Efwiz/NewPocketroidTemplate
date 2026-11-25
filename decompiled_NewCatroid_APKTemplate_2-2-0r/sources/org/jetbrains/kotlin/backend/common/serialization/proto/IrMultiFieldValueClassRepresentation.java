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
public final class IrMultiFieldValueClassRepresentation extends GeneratedMessageLite implements IrMultiFieldValueClassRepresentationOrBuilder {
    public static Parser<IrMultiFieldValueClassRepresentation> PARSER = new AbstractParser<IrMultiFieldValueClassRepresentation>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentation.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrMultiFieldValueClassRepresentation parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrMultiFieldValueClassRepresentation(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int UNDERLYING_PROPERTY_NAME_FIELD_NUMBER = 1;
    public static final int UNDERLYING_PROPERTY_TYPE_FIELD_NUMBER = 2;
    private static final IrMultiFieldValueClassRepresentation defaultInstance;
    private static final long serialVersionUID = 0;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int underlyingPropertyNameMemoizedSerializedSize;
    private List<Integer> underlyingPropertyName_;
    private int underlyingPropertyTypeMemoizedSerializedSize;
    private List<Integer> underlyingPropertyType_;
    private final ByteString unknownFields;

    private IrMultiFieldValueClassRepresentation(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.underlyingPropertyNameMemoizedSerializedSize = -1;
        this.underlyingPropertyTypeMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrMultiFieldValueClassRepresentation(boolean z) {
        this.underlyingPropertyNameMemoizedSerializedSize = -1;
        this.underlyingPropertyTypeMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrMultiFieldValueClassRepresentation getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrMultiFieldValueClassRepresentation getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrMultiFieldValueClassRepresentation(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.underlyingPropertyNameMemoizedSerializedSize = -1;
        this.underlyingPropertyTypeMemoizedSerializedSize = -1;
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
                        if (tag == 8) {
                            if ((i & 1) != 1) {
                                this.underlyingPropertyName_ = new ArrayList();
                                i |= 1;
                            }
                            this.underlyingPropertyName_.add(Integer.valueOf(codedInputStream.readInt32()));
                        } else if (tag == 10) {
                            int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                            if ((i & 1) != 1 && codedInputStream.getBytesUntilLimit() > 0) {
                                this.underlyingPropertyName_ = new ArrayList();
                                i |= 1;
                            }
                            while (codedInputStream.getBytesUntilLimit() > 0) {
                                this.underlyingPropertyName_.add(Integer.valueOf(codedInputStream.readInt32()));
                            }
                            codedInputStream.popLimit(iPushLimit);
                        } else if (tag == 16) {
                            if ((i & 2) != 2) {
                                this.underlyingPropertyType_ = new ArrayList();
                                i |= 2;
                            }
                            this.underlyingPropertyType_.add(Integer.valueOf(codedInputStream.readInt32()));
                        } else if (tag != 18) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            int iPushLimit2 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                            if ((i & 2) != 2 && codedInputStream.getBytesUntilLimit() > 0) {
                                this.underlyingPropertyType_ = new ArrayList();
                                i |= 2;
                            }
                            while (codedInputStream.getBytesUntilLimit() > 0) {
                                this.underlyingPropertyType_.add(Integer.valueOf(codedInputStream.readInt32()));
                            }
                            codedInputStream.popLimit(iPushLimit2);
                        }
                    }
                    z = true;
                } catch (Throwable th) {
                    if ((i & 1) == 1) {
                        this.underlyingPropertyName_ = Collections.unmodifiableList(this.underlyingPropertyName_);
                    }
                    if ((i & 2) == 2) {
                        this.underlyingPropertyType_ = Collections.unmodifiableList(this.underlyingPropertyType_);
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
        if ((i & 1) == 1) {
            this.underlyingPropertyName_ = Collections.unmodifiableList(this.underlyingPropertyName_);
        }
        if ((i & 2) == 2) {
            this.underlyingPropertyType_ = Collections.unmodifiableList(this.underlyingPropertyType_);
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
        IrMultiFieldValueClassRepresentation irMultiFieldValueClassRepresentation = new IrMultiFieldValueClassRepresentation(true);
        defaultInstance = irMultiFieldValueClassRepresentation;
        irMultiFieldValueClassRepresentation.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrMultiFieldValueClassRepresentation> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentationOrBuilder
    public List<Integer> getUnderlyingPropertyNameList() {
        return this.underlyingPropertyName_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentationOrBuilder
    public int getUnderlyingPropertyNameCount() {
        return this.underlyingPropertyName_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentationOrBuilder
    public int getUnderlyingPropertyName(int i) {
        return this.underlyingPropertyName_.get(i).intValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentationOrBuilder
    public List<Integer> getUnderlyingPropertyTypeList() {
        return this.underlyingPropertyType_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentationOrBuilder
    public int getUnderlyingPropertyTypeCount() {
        return this.underlyingPropertyType_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentationOrBuilder
    public int getUnderlyingPropertyType(int i) {
        return this.underlyingPropertyType_.get(i).intValue();
    }

    private void initFields() {
        this.underlyingPropertyName_ = Collections.emptyList();
        this.underlyingPropertyType_ = Collections.emptyList();
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
        this.memoizedIsInitialized = (byte) 1;
        return true;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        getSerializedSize();
        if (getUnderlyingPropertyNameList().size() > 0) {
            codedOutputStream.writeRawVarint32(10);
            codedOutputStream.writeRawVarint32(this.underlyingPropertyNameMemoizedSerializedSize);
        }
        for (int i = 0; i < this.underlyingPropertyName_.size(); i++) {
            codedOutputStream.writeInt32NoTag(this.underlyingPropertyName_.get(i).intValue());
        }
        if (getUnderlyingPropertyTypeList().size() > 0) {
            codedOutputStream.writeRawVarint32(18);
            codedOutputStream.writeRawVarint32(this.underlyingPropertyTypeMemoizedSerializedSize);
        }
        for (int i2 = 0; i2 < this.underlyingPropertyType_.size(); i2++) {
            codedOutputStream.writeInt32NoTag(this.underlyingPropertyType_.get(i2).intValue());
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt32SizeNoTag = 0;
        for (int i2 = 0; i2 < this.underlyingPropertyName_.size(); i2++) {
            iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.underlyingPropertyName_.get(i2).intValue());
        }
        int iComputeInt32SizeNoTag2 = !getUnderlyingPropertyNameList().isEmpty() ? iComputeInt32SizeNoTag + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag) : iComputeInt32SizeNoTag;
        this.underlyingPropertyNameMemoizedSerializedSize = iComputeInt32SizeNoTag;
        int iComputeInt32SizeNoTag3 = 0;
        for (int i3 = 0; i3 < this.underlyingPropertyType_.size(); i3++) {
            iComputeInt32SizeNoTag3 += CodedOutputStream.computeInt32SizeNoTag(this.underlyingPropertyType_.get(i3).intValue());
        }
        int iComputeInt32SizeNoTag4 = iComputeInt32SizeNoTag2 + iComputeInt32SizeNoTag3;
        if (!getUnderlyingPropertyTypeList().isEmpty()) {
            iComputeInt32SizeNoTag4 = iComputeInt32SizeNoTag4 + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag3);
        }
        this.underlyingPropertyTypeMemoizedSerializedSize = iComputeInt32SizeNoTag3;
        int size = iComputeInt32SizeNoTag4 + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrMultiFieldValueClassRepresentation parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrMultiFieldValueClassRepresentation parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrMultiFieldValueClassRepresentation parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrMultiFieldValueClassRepresentation parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrMultiFieldValueClassRepresentation parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrMultiFieldValueClassRepresentation parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrMultiFieldValueClassRepresentation parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrMultiFieldValueClassRepresentation parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrMultiFieldValueClassRepresentation parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrMultiFieldValueClassRepresentation parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrMultiFieldValueClassRepresentation irMultiFieldValueClassRepresentation) {
        return newBuilder().mergeFrom(irMultiFieldValueClassRepresentation);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrMultiFieldValueClassRepresentation, Builder> implements IrMultiFieldValueClassRepresentationOrBuilder {
        private int bitField0_;
        private List<Integer> underlyingPropertyName_ = Collections.emptyList();
        private List<Integer> underlyingPropertyType_ = Collections.emptyList();

        private void maybeForceBuilderInitialization() {
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return true;
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
            this.underlyingPropertyName_ = Collections.emptyList();
            this.bitField0_ &= -2;
            this.underlyingPropertyType_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrMultiFieldValueClassRepresentation getDefaultInstanceForType() {
            return IrMultiFieldValueClassRepresentation.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrMultiFieldValueClassRepresentation build() {
            IrMultiFieldValueClassRepresentation irMultiFieldValueClassRepresentationBuildPartial = buildPartial();
            if (irMultiFieldValueClassRepresentationBuildPartial.isInitialized()) {
                return irMultiFieldValueClassRepresentationBuildPartial;
            }
            throw newUninitializedMessageException(irMultiFieldValueClassRepresentationBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrMultiFieldValueClassRepresentation buildPartial() {
            IrMultiFieldValueClassRepresentation irMultiFieldValueClassRepresentation = new IrMultiFieldValueClassRepresentation(this);
            if ((this.bitField0_ & 1) == 1) {
                this.underlyingPropertyName_ = Collections.unmodifiableList(this.underlyingPropertyName_);
                this.bitField0_ &= -2;
            }
            irMultiFieldValueClassRepresentation.underlyingPropertyName_ = this.underlyingPropertyName_;
            if ((this.bitField0_ & 2) == 2) {
                this.underlyingPropertyType_ = Collections.unmodifiableList(this.underlyingPropertyType_);
                this.bitField0_ &= -3;
            }
            irMultiFieldValueClassRepresentation.underlyingPropertyType_ = this.underlyingPropertyType_;
            return irMultiFieldValueClassRepresentation;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrMultiFieldValueClassRepresentation irMultiFieldValueClassRepresentation) {
            if (irMultiFieldValueClassRepresentation == IrMultiFieldValueClassRepresentation.getDefaultInstance()) {
                return this;
            }
            if (!irMultiFieldValueClassRepresentation.underlyingPropertyName_.isEmpty()) {
                if (this.underlyingPropertyName_.isEmpty()) {
                    this.underlyingPropertyName_ = irMultiFieldValueClassRepresentation.underlyingPropertyName_;
                    this.bitField0_ &= -2;
                } else {
                    ensureUnderlyingPropertyNameIsMutable();
                    this.underlyingPropertyName_.addAll(irMultiFieldValueClassRepresentation.underlyingPropertyName_);
                }
            }
            if (!irMultiFieldValueClassRepresentation.underlyingPropertyType_.isEmpty()) {
                if (this.underlyingPropertyType_.isEmpty()) {
                    this.underlyingPropertyType_ = irMultiFieldValueClassRepresentation.underlyingPropertyType_;
                    this.bitField0_ &= -3;
                } else {
                    ensureUnderlyingPropertyTypeIsMutable();
                    this.underlyingPropertyType_.addAll(irMultiFieldValueClassRepresentation.underlyingPropertyType_);
                }
            }
            setUnknownFields(getUnknownFields().concat(irMultiFieldValueClassRepresentation.unknownFields));
            return this;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrMultiFieldValueClassRepresentation irMultiFieldValueClassRepresentation = null;
            try {
                try {
                    IrMultiFieldValueClassRepresentation partialFrom = IrMultiFieldValueClassRepresentation.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrMultiFieldValueClassRepresentation irMultiFieldValueClassRepresentation2 = (IrMultiFieldValueClassRepresentation) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irMultiFieldValueClassRepresentation = irMultiFieldValueClassRepresentation2;
                        if (irMultiFieldValueClassRepresentation != null) {
                            mergeFrom(irMultiFieldValueClassRepresentation);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irMultiFieldValueClassRepresentation != null) {
                    mergeFrom(irMultiFieldValueClassRepresentation);
                }
                throw th;
            }
        }

        private void ensureUnderlyingPropertyNameIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
                this.underlyingPropertyName_ = new ArrayList(this.underlyingPropertyName_);
                this.bitField0_ |= 1;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentationOrBuilder
        public List<Integer> getUnderlyingPropertyNameList() {
            return Collections.unmodifiableList(this.underlyingPropertyName_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentationOrBuilder
        public int getUnderlyingPropertyNameCount() {
            return this.underlyingPropertyName_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentationOrBuilder
        public int getUnderlyingPropertyName(int i) {
            return this.underlyingPropertyName_.get(i).intValue();
        }

        public Builder setUnderlyingPropertyName(int i, int i2) {
            ensureUnderlyingPropertyNameIsMutable();
            this.underlyingPropertyName_.set(i, Integer.valueOf(i2));
            return this;
        }

        public Builder addUnderlyingPropertyName(int i) {
            ensureUnderlyingPropertyNameIsMutable();
            this.underlyingPropertyName_.add(Integer.valueOf(i));
            return this;
        }

        public Builder addAllUnderlyingPropertyName(Iterable<? extends Integer> iterable) {
            ensureUnderlyingPropertyNameIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.underlyingPropertyName_);
            return this;
        }

        public Builder clearUnderlyingPropertyName() {
            this.underlyingPropertyName_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
        }

        private void ensureUnderlyingPropertyTypeIsMutable() {
            if ((this.bitField0_ & 2) != 2) {
                this.underlyingPropertyType_ = new ArrayList(this.underlyingPropertyType_);
                this.bitField0_ |= 2;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentationOrBuilder
        public List<Integer> getUnderlyingPropertyTypeList() {
            return Collections.unmodifiableList(this.underlyingPropertyType_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentationOrBuilder
        public int getUnderlyingPropertyTypeCount() {
            return this.underlyingPropertyType_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentationOrBuilder
        public int getUnderlyingPropertyType(int i) {
            return this.underlyingPropertyType_.get(i).intValue();
        }

        public Builder setUnderlyingPropertyType(int i, int i2) {
            ensureUnderlyingPropertyTypeIsMutable();
            this.underlyingPropertyType_.set(i, Integer.valueOf(i2));
            return this;
        }

        public Builder addUnderlyingPropertyType(int i) {
            ensureUnderlyingPropertyTypeIsMutable();
            this.underlyingPropertyType_.add(Integer.valueOf(i));
            return this;
        }

        public Builder addAllUnderlyingPropertyType(Iterable<? extends Integer> iterable) {
            ensureUnderlyingPropertyTypeIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.underlyingPropertyType_);
            return this;
        }

        public Builder clearUnderlyingPropertyType() {
            this.underlyingPropertyType_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
        }
    }
}
