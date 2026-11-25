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
public final class IrSimpleType extends GeneratedMessageLite implements IrSimpleTypeOrBuilder {
    public static final int ABBREVIATION_FIELD_NUMBER = 5;
    public static final int ANNOTATION_FIELD_NUMBER = 1;
    public static final int ARGUMENT_FIELD_NUMBER = 4;
    public static final int CLASSIFIER_FIELD_NUMBER = 2;
    public static final int NULLABILITY_FIELD_NUMBER = 3;
    public static Parser<IrSimpleType> PARSER = new AbstractParser<IrSimpleType>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleType.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrSimpleType parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrSimpleType(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrSimpleType defaultInstance;
    private static final long serialVersionUID = 0;
    private IrTypeAbbreviation abbreviation_;
    private List<IrConstructorCall> annotation_;
    private int argumentMemoizedSerializedSize;
    private List<Long> argument_;
    private int bitField0_;
    private long classifier_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private IrSimpleTypeNullability nullability_;
    private final ByteString unknownFields;

    private IrSimpleType(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.argumentMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrSimpleType(boolean z) {
        this.argumentMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrSimpleType getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrSimpleType getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrSimpleType(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.argumentMemoizedSerializedSize = -1;
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
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                if ((i & 1) != 1) {
                                    this.annotation_ = new ArrayList();
                                    i |= 1;
                                }
                                this.annotation_.add((IrConstructorCall) codedInputStream.readMessage(IrConstructorCall.PARSER, extensionRegistryLite));
                            } else if (tag == 16) {
                                this.bitField0_ |= 1;
                                this.classifier_ = codedInputStream.readInt64();
                            } else if (tag == 24) {
                                int i2 = codedInputStream.readEnum();
                                IrSimpleTypeNullability irSimpleTypeNullabilityValueOf = IrSimpleTypeNullability.valueOf(i2);
                                if (irSimpleTypeNullabilityValueOf == null) {
                                    codedOutputStreamNewInstance.writeRawVarint32(tag);
                                    codedOutputStreamNewInstance.writeRawVarint32(i2);
                                } else {
                                    this.bitField0_ |= 2;
                                    this.nullability_ = irSimpleTypeNullabilityValueOf;
                                }
                            } else if (tag == 32) {
                                if ((i & 8) != 8) {
                                    this.argument_ = new ArrayList();
                                    i |= 8;
                                }
                                this.argument_.add(Long.valueOf(codedInputStream.readInt64()));
                            } else if (tag == 34) {
                                int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                if ((i & 8) != 8 && codedInputStream.getBytesUntilLimit() > 0) {
                                    this.argument_ = new ArrayList();
                                    i |= 8;
                                }
                                while (codedInputStream.getBytesUntilLimit() > 0) {
                                    this.argument_.add(Long.valueOf(codedInputStream.readInt64()));
                                }
                                codedInputStream.popLimit(iPushLimit);
                            } else if (tag != 42) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                IrTypeAbbreviation.Builder builder = (this.bitField0_ & 4) == 4 ? this.abbreviation_.toBuilder() : null;
                                IrTypeAbbreviation irTypeAbbreviation = (IrTypeAbbreviation) codedInputStream.readMessage(IrTypeAbbreviation.PARSER, extensionRegistryLite);
                                this.abbreviation_ = irTypeAbbreviation;
                                if (builder != null) {
                                    builder.mergeFrom(irTypeAbbreviation);
                                    this.abbreviation_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 4;
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
                if ((i & 1) == 1) {
                    this.annotation_ = Collections.unmodifiableList(this.annotation_);
                }
                if ((i & 8) == 8) {
                    this.argument_ = Collections.unmodifiableList(this.argument_);
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
        if ((i & 1) == 1) {
            this.annotation_ = Collections.unmodifiableList(this.annotation_);
        }
        if ((i & 8) == 8) {
            this.argument_ = Collections.unmodifiableList(this.argument_);
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
        IrSimpleType irSimpleType = new IrSimpleType(true);
        defaultInstance = irSimpleType;
        irSimpleType.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrSimpleType> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
    public List<IrConstructorCall> getAnnotationList() {
        return this.annotation_;
    }

    public List<? extends IrConstructorCallOrBuilder> getAnnotationOrBuilderList() {
        return this.annotation_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
    public int getAnnotationCount() {
        return this.annotation_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
    public IrConstructorCall getAnnotation(int i) {
        return this.annotation_.get(i);
    }

    public IrConstructorCallOrBuilder getAnnotationOrBuilder(int i) {
        return this.annotation_.get(i);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
    public boolean hasClassifier() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
    public long getClassifier() {
        return this.classifier_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
    public boolean hasNullability() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
    public IrSimpleTypeNullability getNullability() {
        return this.nullability_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
    public List<Long> getArgumentList() {
        return this.argument_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
    public int getArgumentCount() {
        return this.argument_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
    public long getArgument(int i) {
        return this.argument_.get(i).longValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
    public boolean hasAbbreviation() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
    public IrTypeAbbreviation getAbbreviation() {
        return this.abbreviation_;
    }

    private void initFields() {
        this.annotation_ = Collections.emptyList();
        this.classifier_ = 0L;
        this.nullability_ = IrSimpleTypeNullability.NOT_SPECIFIED;
        this.argument_ = Collections.emptyList();
        this.abbreviation_ = IrTypeAbbreviation.getDefaultInstance();
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
        if (!hasClassifier()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        for (int i = 0; i < getAnnotationCount(); i++) {
            if (!getAnnotation(i).isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
        }
        if (hasAbbreviation() && !getAbbreviation().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        this.memoizedIsInitialized = (byte) 1;
        return true;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        getSerializedSize();
        for (int i = 0; i < this.annotation_.size(); i++) {
            codedOutputStream.writeMessage(1, this.annotation_.get(i));
        }
        if ((this.bitField0_ & 1) == 1) {
            codedOutputStream.writeInt64(2, this.classifier_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeEnum(3, this.nullability_.getNumber());
        }
        if (getArgumentList().size() > 0) {
            codedOutputStream.writeRawVarint32(34);
            codedOutputStream.writeRawVarint32(this.argumentMemoizedSerializedSize);
        }
        for (int i2 = 0; i2 < this.argument_.size(); i2++) {
            codedOutputStream.writeInt64NoTag(this.argument_.get(i2).longValue());
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeMessage(5, this.abbreviation_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeEnumSize = 0;
        for (int i2 = 0; i2 < this.annotation_.size(); i2++) {
            iComputeEnumSize += CodedOutputStream.computeMessageSize(1, this.annotation_.get(i2));
        }
        if ((this.bitField0_ & 1) == 1) {
            iComputeEnumSize += CodedOutputStream.computeInt64Size(2, this.classifier_);
        }
        if ((this.bitField0_ & 2) == 2) {
            iComputeEnumSize += CodedOutputStream.computeEnumSize(3, this.nullability_.getNumber());
        }
        int iComputeInt64SizeNoTag = 0;
        for (int i3 = 0; i3 < this.argument_.size(); i3++) {
            iComputeInt64SizeNoTag += CodedOutputStream.computeInt64SizeNoTag(this.argument_.get(i3).longValue());
        }
        int iComputeMessageSize = iComputeEnumSize + iComputeInt64SizeNoTag;
        if (!getArgumentList().isEmpty()) {
            iComputeMessageSize = iComputeMessageSize + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt64SizeNoTag);
        }
        this.argumentMemoizedSerializedSize = iComputeInt64SizeNoTag;
        if ((this.bitField0_ & 4) == 4) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(5, this.abbreviation_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrSimpleType parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrSimpleType parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrSimpleType parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrSimpleType parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrSimpleType parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrSimpleType parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrSimpleType parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrSimpleType parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrSimpleType parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrSimpleType parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrSimpleType irSimpleType) {
        return newBuilder().mergeFrom(irSimpleType);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrSimpleType, Builder> implements IrSimpleTypeOrBuilder {
        private int bitField0_;
        private long classifier_;
        private List<IrConstructorCall> annotation_ = Collections.emptyList();
        private IrSimpleTypeNullability nullability_ = IrSimpleTypeNullability.NOT_SPECIFIED;
        private List<Long> argument_ = Collections.emptyList();
        private IrTypeAbbreviation abbreviation_ = IrTypeAbbreviation.getDefaultInstance();

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
            this.annotation_ = Collections.emptyList();
            int i = this.bitField0_;
            this.classifier_ = 0L;
            this.bitField0_ = i & (-4);
            this.nullability_ = IrSimpleTypeNullability.NOT_SPECIFIED;
            this.bitField0_ &= -5;
            this.argument_ = Collections.emptyList();
            this.bitField0_ &= -9;
            this.abbreviation_ = IrTypeAbbreviation.getDefaultInstance();
            this.bitField0_ &= -17;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrSimpleType getDefaultInstanceForType() {
            return IrSimpleType.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrSimpleType build() {
            IrSimpleType irSimpleTypeBuildPartial = buildPartial();
            if (irSimpleTypeBuildPartial.isInitialized()) {
                return irSimpleTypeBuildPartial;
            }
            throw newUninitializedMessageException(irSimpleTypeBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrSimpleType buildPartial() {
            IrSimpleType irSimpleType = new IrSimpleType(this);
            int i = this.bitField0_;
            if ((i & 1) == 1) {
                this.annotation_ = Collections.unmodifiableList(this.annotation_);
                this.bitField0_ &= -2;
            }
            irSimpleType.annotation_ = this.annotation_;
            int i2 = (i & 2) != 2 ? 0 : 1;
            irSimpleType.classifier_ = this.classifier_;
            if ((i & 4) == 4) {
                i2 |= 2;
            }
            irSimpleType.nullability_ = this.nullability_;
            if ((this.bitField0_ & 8) == 8) {
                this.argument_ = Collections.unmodifiableList(this.argument_);
                this.bitField0_ &= -9;
            }
            irSimpleType.argument_ = this.argument_;
            if ((i & 16) == 16) {
                i2 |= 4;
            }
            irSimpleType.abbreviation_ = this.abbreviation_;
            irSimpleType.bitField0_ = i2;
            return irSimpleType;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrSimpleType irSimpleType) {
            if (irSimpleType == IrSimpleType.getDefaultInstance()) {
                return this;
            }
            if (!irSimpleType.annotation_.isEmpty()) {
                if (this.annotation_.isEmpty()) {
                    this.annotation_ = irSimpleType.annotation_;
                    this.bitField0_ &= -2;
                } else {
                    ensureAnnotationIsMutable();
                    this.annotation_.addAll(irSimpleType.annotation_);
                }
            }
            if (irSimpleType.hasClassifier()) {
                setClassifier(irSimpleType.getClassifier());
            }
            if (irSimpleType.hasNullability()) {
                setNullability(irSimpleType.getNullability());
            }
            if (!irSimpleType.argument_.isEmpty()) {
                if (this.argument_.isEmpty()) {
                    this.argument_ = irSimpleType.argument_;
                    this.bitField0_ &= -9;
                } else {
                    ensureArgumentIsMutable();
                    this.argument_.addAll(irSimpleType.argument_);
                }
            }
            if (irSimpleType.hasAbbreviation()) {
                mergeAbbreviation(irSimpleType.getAbbreviation());
            }
            setUnknownFields(getUnknownFields().concat(irSimpleType.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (!hasClassifier()) {
                return false;
            }
            for (int i = 0; i < getAnnotationCount(); i++) {
                if (!getAnnotation(i).isInitialized()) {
                    return false;
                }
            }
            return !hasAbbreviation() || getAbbreviation().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrSimpleType irSimpleType = null;
            try {
                try {
                    IrSimpleType partialFrom = IrSimpleType.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrSimpleType irSimpleType2 = (IrSimpleType) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irSimpleType = irSimpleType2;
                        if (irSimpleType != null) {
                            mergeFrom(irSimpleType);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irSimpleType != null) {
                    mergeFrom(irSimpleType);
                }
                throw th;
            }
        }

        private void ensureAnnotationIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
                this.annotation_ = new ArrayList(this.annotation_);
                this.bitField0_ |= 1;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
        public List<IrConstructorCall> getAnnotationList() {
            return Collections.unmodifiableList(this.annotation_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
        public int getAnnotationCount() {
            return this.annotation_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
        public IrConstructorCall getAnnotation(int i) {
            return this.annotation_.get(i);
        }

        public Builder setAnnotation(int i, IrConstructorCall irConstructorCall) {
            irConstructorCall.getClass();
            ensureAnnotationIsMutable();
            this.annotation_.set(i, irConstructorCall);
            return this;
        }

        public Builder setAnnotation(int i, IrConstructorCall.Builder builder) {
            ensureAnnotationIsMutable();
            this.annotation_.set(i, builder.build());
            return this;
        }

        public Builder addAnnotation(IrConstructorCall irConstructorCall) {
            irConstructorCall.getClass();
            ensureAnnotationIsMutable();
            this.annotation_.add(irConstructorCall);
            return this;
        }

        public Builder addAnnotation(int i, IrConstructorCall irConstructorCall) {
            irConstructorCall.getClass();
            ensureAnnotationIsMutable();
            this.annotation_.add(i, irConstructorCall);
            return this;
        }

        public Builder addAnnotation(IrConstructorCall.Builder builder) {
            ensureAnnotationIsMutable();
            this.annotation_.add(builder.build());
            return this;
        }

        public Builder addAnnotation(int i, IrConstructorCall.Builder builder) {
            ensureAnnotationIsMutable();
            this.annotation_.add(i, builder.build());
            return this;
        }

        public Builder addAllAnnotation(Iterable<? extends IrConstructorCall> iterable) {
            ensureAnnotationIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.annotation_);
            return this;
        }

        public Builder clearAnnotation() {
            this.annotation_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
        }

        public Builder removeAnnotation(int i) {
            ensureAnnotationIsMutable();
            this.annotation_.remove(i);
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
        public boolean hasClassifier() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
        public long getClassifier() {
            return this.classifier_;
        }

        public Builder setClassifier(long j) {
            this.bitField0_ |= 2;
            this.classifier_ = j;
            return this;
        }

        public Builder clearClassifier() {
            this.bitField0_ &= -3;
            this.classifier_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
        public boolean hasNullability() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
        public IrSimpleTypeNullability getNullability() {
            return this.nullability_;
        }

        public Builder setNullability(IrSimpleTypeNullability irSimpleTypeNullability) {
            irSimpleTypeNullability.getClass();
            this.bitField0_ |= 4;
            this.nullability_ = irSimpleTypeNullability;
            return this;
        }

        public Builder clearNullability() {
            this.bitField0_ &= -5;
            this.nullability_ = IrSimpleTypeNullability.NOT_SPECIFIED;
            return this;
        }

        private void ensureArgumentIsMutable() {
            if ((this.bitField0_ & 8) != 8) {
                this.argument_ = new ArrayList(this.argument_);
                this.bitField0_ |= 8;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
        public List<Long> getArgumentList() {
            return Collections.unmodifiableList(this.argument_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
        public int getArgumentCount() {
            return this.argument_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
        public long getArgument(int i) {
            return this.argument_.get(i).longValue();
        }

        public Builder setArgument(int i, long j) {
            ensureArgumentIsMutable();
            this.argument_.set(i, Long.valueOf(j));
            return this;
        }

        public Builder addArgument(long j) {
            ensureArgumentIsMutable();
            this.argument_.add(Long.valueOf(j));
            return this;
        }

        public Builder addAllArgument(Iterable<? extends Long> iterable) {
            ensureArgumentIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.argument_);
            return this;
        }

        public Builder clearArgument() {
            this.argument_ = Collections.emptyList();
            this.bitField0_ &= -9;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
        public boolean hasAbbreviation() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeOrBuilder
        public IrTypeAbbreviation getAbbreviation() {
            return this.abbreviation_;
        }

        public Builder setAbbreviation(IrTypeAbbreviation irTypeAbbreviation) {
            irTypeAbbreviation.getClass();
            this.abbreviation_ = irTypeAbbreviation;
            this.bitField0_ |= 16;
            return this;
        }

        public Builder setAbbreviation(IrTypeAbbreviation.Builder builder) {
            this.abbreviation_ = builder.build();
            this.bitField0_ |= 16;
            return this;
        }

        public Builder mergeAbbreviation(IrTypeAbbreviation irTypeAbbreviation) {
            if ((this.bitField0_ & 16) == 16 && this.abbreviation_ != IrTypeAbbreviation.getDefaultInstance()) {
                this.abbreviation_ = IrTypeAbbreviation.newBuilder(this.abbreviation_).mergeFrom(irTypeAbbreviation).buildPartial();
            } else {
                this.abbreviation_ = irTypeAbbreviation;
            }
            this.bitField0_ |= 16;
            return this;
        }

        public Builder clearAbbreviation() {
            this.abbreviation_ = IrTypeAbbreviation.getDefaultInstance();
            this.bitField0_ &= -17;
            return this;
        }
    }
}
