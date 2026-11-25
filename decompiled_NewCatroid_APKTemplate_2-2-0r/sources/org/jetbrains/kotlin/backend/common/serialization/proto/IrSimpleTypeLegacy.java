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
public final class IrSimpleTypeLegacy extends GeneratedMessageLite implements IrSimpleTypeLegacyOrBuilder {
    public static final int ABBREVIATION_FIELD_NUMBER = 5;
    public static final int ANNOTATION_FIELD_NUMBER = 1;
    public static final int ARGUMENT_FIELD_NUMBER = 4;
    public static final int CLASSIFIER_FIELD_NUMBER = 2;
    public static final int HAS_QUESTION_MARK_FIELD_NUMBER = 3;
    public static Parser<IrSimpleTypeLegacy> PARSER = new AbstractParser<IrSimpleTypeLegacy>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacy.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrSimpleTypeLegacy parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrSimpleTypeLegacy(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrSimpleTypeLegacy defaultInstance;
    private static final long serialVersionUID = 0;
    private IrTypeAbbreviation abbreviation_;
    private List<IrConstructorCall> annotation_;
    private int argumentMemoizedSerializedSize;
    private List<Long> argument_;
    private int bitField0_;
    private long classifier_;
    private boolean hasQuestionMark_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    private IrSimpleTypeLegacy(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.argumentMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrSimpleTypeLegacy(boolean z) {
        this.argumentMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrSimpleTypeLegacy getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrSimpleTypeLegacy getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrSimpleTypeLegacy(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                                this.bitField0_ |= 2;
                                this.hasQuestionMark_ = codedInputStream.readBool();
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
        IrSimpleTypeLegacy irSimpleTypeLegacy = new IrSimpleTypeLegacy(true);
        defaultInstance = irSimpleTypeLegacy;
        irSimpleTypeLegacy.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrSimpleTypeLegacy> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
    public List<IrConstructorCall> getAnnotationList() {
        return this.annotation_;
    }

    public List<? extends IrConstructorCallOrBuilder> getAnnotationOrBuilderList() {
        return this.annotation_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
    public int getAnnotationCount() {
        return this.annotation_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
    public IrConstructorCall getAnnotation(int i) {
        return this.annotation_.get(i);
    }

    public IrConstructorCallOrBuilder getAnnotationOrBuilder(int i) {
        return this.annotation_.get(i);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
    public boolean hasClassifier() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
    public long getClassifier() {
        return this.classifier_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
    public boolean hasHasQuestionMark() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
    public boolean getHasQuestionMark() {
        return this.hasQuestionMark_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
    public List<Long> getArgumentList() {
        return this.argument_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
    public int getArgumentCount() {
        return this.argument_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
    public long getArgument(int i) {
        return this.argument_.get(i).longValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
    public boolean hasAbbreviation() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
    public IrTypeAbbreviation getAbbreviation() {
        return this.abbreviation_;
    }

    private void initFields() {
        this.annotation_ = Collections.emptyList();
        this.classifier_ = 0L;
        this.hasQuestionMark_ = false;
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
        if (!hasHasQuestionMark()) {
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
            codedOutputStream.writeBool(3, this.hasQuestionMark_);
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
        int iComputeBoolSize = 0;
        for (int i2 = 0; i2 < this.annotation_.size(); i2++) {
            iComputeBoolSize += CodedOutputStream.computeMessageSize(1, this.annotation_.get(i2));
        }
        if ((this.bitField0_ & 1) == 1) {
            iComputeBoolSize += CodedOutputStream.computeInt64Size(2, this.classifier_);
        }
        if ((this.bitField0_ & 2) == 2) {
            iComputeBoolSize += CodedOutputStream.computeBoolSize(3, this.hasQuestionMark_);
        }
        int iComputeInt64SizeNoTag = 0;
        for (int i3 = 0; i3 < this.argument_.size(); i3++) {
            iComputeInt64SizeNoTag += CodedOutputStream.computeInt64SizeNoTag(this.argument_.get(i3).longValue());
        }
        int iComputeMessageSize = iComputeBoolSize + iComputeInt64SizeNoTag;
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

    public static IrSimpleTypeLegacy parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrSimpleTypeLegacy parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrSimpleTypeLegacy parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrSimpleTypeLegacy parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrSimpleTypeLegacy parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrSimpleTypeLegacy parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrSimpleTypeLegacy parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrSimpleTypeLegacy parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrSimpleTypeLegacy parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrSimpleTypeLegacy parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrSimpleTypeLegacy irSimpleTypeLegacy) {
        return newBuilder().mergeFrom(irSimpleTypeLegacy);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrSimpleTypeLegacy, Builder> implements IrSimpleTypeLegacyOrBuilder {
        private int bitField0_;
        private long classifier_;
        private boolean hasQuestionMark_;
        private List<IrConstructorCall> annotation_ = Collections.emptyList();
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
            this.hasQuestionMark_ = false;
            this.bitField0_ = i & (-8);
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
        public IrSimpleTypeLegacy getDefaultInstanceForType() {
            return IrSimpleTypeLegacy.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrSimpleTypeLegacy build() {
            IrSimpleTypeLegacy irSimpleTypeLegacyBuildPartial = buildPartial();
            if (irSimpleTypeLegacyBuildPartial.isInitialized()) {
                return irSimpleTypeLegacyBuildPartial;
            }
            throw newUninitializedMessageException(irSimpleTypeLegacyBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrSimpleTypeLegacy buildPartial() {
            IrSimpleTypeLegacy irSimpleTypeLegacy = new IrSimpleTypeLegacy(this);
            int i = this.bitField0_;
            if ((i & 1) == 1) {
                this.annotation_ = Collections.unmodifiableList(this.annotation_);
                this.bitField0_ &= -2;
            }
            irSimpleTypeLegacy.annotation_ = this.annotation_;
            int i2 = (i & 2) != 2 ? 0 : 1;
            irSimpleTypeLegacy.classifier_ = this.classifier_;
            if ((i & 4) == 4) {
                i2 |= 2;
            }
            irSimpleTypeLegacy.hasQuestionMark_ = this.hasQuestionMark_;
            if ((this.bitField0_ & 8) == 8) {
                this.argument_ = Collections.unmodifiableList(this.argument_);
                this.bitField0_ &= -9;
            }
            irSimpleTypeLegacy.argument_ = this.argument_;
            if ((i & 16) == 16) {
                i2 |= 4;
            }
            irSimpleTypeLegacy.abbreviation_ = this.abbreviation_;
            irSimpleTypeLegacy.bitField0_ = i2;
            return irSimpleTypeLegacy;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrSimpleTypeLegacy irSimpleTypeLegacy) {
            if (irSimpleTypeLegacy == IrSimpleTypeLegacy.getDefaultInstance()) {
                return this;
            }
            if (!irSimpleTypeLegacy.annotation_.isEmpty()) {
                if (this.annotation_.isEmpty()) {
                    this.annotation_ = irSimpleTypeLegacy.annotation_;
                    this.bitField0_ &= -2;
                } else {
                    ensureAnnotationIsMutable();
                    this.annotation_.addAll(irSimpleTypeLegacy.annotation_);
                }
            }
            if (irSimpleTypeLegacy.hasClassifier()) {
                setClassifier(irSimpleTypeLegacy.getClassifier());
            }
            if (irSimpleTypeLegacy.hasHasQuestionMark()) {
                setHasQuestionMark(irSimpleTypeLegacy.getHasQuestionMark());
            }
            if (!irSimpleTypeLegacy.argument_.isEmpty()) {
                if (this.argument_.isEmpty()) {
                    this.argument_ = irSimpleTypeLegacy.argument_;
                    this.bitField0_ &= -9;
                } else {
                    ensureArgumentIsMutable();
                    this.argument_.addAll(irSimpleTypeLegacy.argument_);
                }
            }
            if (irSimpleTypeLegacy.hasAbbreviation()) {
                mergeAbbreviation(irSimpleTypeLegacy.getAbbreviation());
            }
            setUnknownFields(getUnknownFields().concat(irSimpleTypeLegacy.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (!hasClassifier() || !hasHasQuestionMark()) {
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
            IrSimpleTypeLegacy irSimpleTypeLegacy = null;
            try {
                try {
                    IrSimpleTypeLegacy partialFrom = IrSimpleTypeLegacy.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrSimpleTypeLegacy irSimpleTypeLegacy2 = (IrSimpleTypeLegacy) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irSimpleTypeLegacy = irSimpleTypeLegacy2;
                        if (irSimpleTypeLegacy != null) {
                            mergeFrom(irSimpleTypeLegacy);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irSimpleTypeLegacy != null) {
                    mergeFrom(irSimpleTypeLegacy);
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
        public List<IrConstructorCall> getAnnotationList() {
            return Collections.unmodifiableList(this.annotation_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
        public int getAnnotationCount() {
            return this.annotation_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
        public boolean hasClassifier() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
        public boolean hasHasQuestionMark() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
        public boolean getHasQuestionMark() {
            return this.hasQuestionMark_;
        }

        public Builder setHasQuestionMark(boolean z) {
            this.bitField0_ |= 4;
            this.hasQuestionMark_ = z;
            return this;
        }

        public Builder clearHasQuestionMark() {
            this.bitField0_ &= -5;
            this.hasQuestionMark_ = false;
            return this;
        }

        private void ensureArgumentIsMutable() {
            if ((this.bitField0_ & 8) != 8) {
                this.argument_ = new ArrayList(this.argument_);
                this.bitField0_ |= 8;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
        public List<Long> getArgumentList() {
            return Collections.unmodifiableList(this.argument_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
        public int getArgumentCount() {
            return this.argument_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
        public boolean hasAbbreviation() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacyOrBuilder
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
