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
public final class IrDeclarationBase extends GeneratedMessageLite implements IrDeclarationBaseOrBuilder {
    public static final int ANNOTATION_FIELD_NUMBER = 5;
    public static final int COORDINATES_FIELD_NUMBER = 3;
    public static final int FLAGS_FIELD_NUMBER = 4;
    public static final int ORIGIN_NAME_FIELD_NUMBER = 2;
    public static Parser<IrDeclarationBase> PARSER = new AbstractParser<IrDeclarationBase>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrDeclarationBase parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrDeclarationBase(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int SYMBOL_FIELD_NUMBER = 1;
    private static final IrDeclarationBase defaultInstance;
    private static final long serialVersionUID = 0;
    private List<IrConstructorCall> annotation_;
    private int bitField0_;
    private long coordinates_;
    private long flags_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int originName_;
    private long symbol_;
    private final ByteString unknownFields;

    private IrDeclarationBase(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrDeclarationBase(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrDeclarationBase getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrDeclarationBase getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrDeclarationBase(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                        if (tag == 8) {
                            this.bitField0_ |= 1;
                            this.symbol_ = codedInputStream.readInt64();
                        } else if (tag == 16) {
                            this.bitField0_ |= 2;
                            this.originName_ = codedInputStream.readInt32();
                        } else if (tag == 24) {
                            this.bitField0_ |= 4;
                            this.coordinates_ = codedInputStream.readInt64();
                        } else if (tag == 32) {
                            this.bitField0_ |= 8;
                            this.flags_ = codedInputStream.readInt64();
                        } else if (tag != 42) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            if ((c & 16) != 16) {
                                this.annotation_ = new ArrayList();
                                c = 16;
                            }
                            this.annotation_.add((IrConstructorCall) codedInputStream.readMessage(IrConstructorCall.PARSER, extensionRegistryLite));
                        }
                    }
                    z = true;
                } catch (Throwable th) {
                    if ((c & 16) == 16) {
                        this.annotation_ = Collections.unmodifiableList(this.annotation_);
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
        if ((c & 16) == 16) {
            this.annotation_ = Collections.unmodifiableList(this.annotation_);
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
        IrDeclarationBase irDeclarationBase = new IrDeclarationBase(true);
        defaultInstance = irDeclarationBase;
        irDeclarationBase.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrDeclarationBase> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
    public boolean hasSymbol() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
    public long getSymbol() {
        return this.symbol_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
    public boolean hasOriginName() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
    public int getOriginName() {
        return this.originName_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
    public boolean hasCoordinates() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
    public long getCoordinates() {
        return this.coordinates_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
    public boolean hasFlags() {
        return (this.bitField0_ & 8) == 8;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
    public long getFlags() {
        return this.flags_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
    public List<IrConstructorCall> getAnnotationList() {
        return this.annotation_;
    }

    public List<? extends IrConstructorCallOrBuilder> getAnnotationOrBuilderList() {
        return this.annotation_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
    public int getAnnotationCount() {
        return this.annotation_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
    public IrConstructorCall getAnnotation(int i) {
        return this.annotation_.get(i);
    }

    public IrConstructorCallOrBuilder getAnnotationOrBuilder(int i) {
        return this.annotation_.get(i);
    }

    private void initFields() {
        this.symbol_ = 0L;
        this.originName_ = 0;
        this.coordinates_ = 0L;
        this.flags_ = 0L;
        this.annotation_ = Collections.emptyList();
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
        if (!hasSymbol()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasOriginName()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasCoordinates()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        for (int i = 0; i < getAnnotationCount(); i++) {
            if (!getAnnotation(i).isInitialized()) {
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
            codedOutputStream.writeInt64(1, this.symbol_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt32(2, this.originName_);
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeInt64(3, this.coordinates_);
        }
        if ((this.bitField0_ & 8) == 8) {
            codedOutputStream.writeInt64(4, this.flags_);
        }
        for (int i = 0; i < this.annotation_.size(); i++) {
            codedOutputStream.writeMessage(5, this.annotation_.get(i));
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt64Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt64Size(1, this.symbol_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeInt64Size += CodedOutputStream.computeInt32Size(2, this.originName_);
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeInt64Size += CodedOutputStream.computeInt64Size(3, this.coordinates_);
        }
        if ((this.bitField0_ & 8) == 8) {
            iComputeInt64Size += CodedOutputStream.computeInt64Size(4, this.flags_);
        }
        for (int i2 = 0; i2 < this.annotation_.size(); i2++) {
            iComputeInt64Size += CodedOutputStream.computeMessageSize(5, this.annotation_.get(i2));
        }
        int size = iComputeInt64Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrDeclarationBase parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrDeclarationBase parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrDeclarationBase parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrDeclarationBase parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrDeclarationBase parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrDeclarationBase parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrDeclarationBase parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrDeclarationBase parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrDeclarationBase parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrDeclarationBase parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrDeclarationBase irDeclarationBase) {
        return newBuilder().mergeFrom(irDeclarationBase);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrDeclarationBase, Builder> implements IrDeclarationBaseOrBuilder {
        private List<IrConstructorCall> annotation_ = Collections.emptyList();
        private int bitField0_;
        private long coordinates_;
        private long flags_;
        private int originName_;
        private long symbol_;

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
            this.symbol_ = 0L;
            int i = this.bitField0_;
            this.originName_ = 0;
            this.coordinates_ = 0L;
            this.flags_ = 0L;
            this.bitField0_ = i & (-16);
            this.annotation_ = Collections.emptyList();
            this.bitField0_ &= -17;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrDeclarationBase getDefaultInstanceForType() {
            return IrDeclarationBase.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrDeclarationBase build() {
            IrDeclarationBase irDeclarationBaseBuildPartial = buildPartial();
            if (irDeclarationBaseBuildPartial.isInitialized()) {
                return irDeclarationBaseBuildPartial;
            }
            throw newUninitializedMessageException(irDeclarationBaseBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrDeclarationBase buildPartial() {
            IrDeclarationBase irDeclarationBase = new IrDeclarationBase(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irDeclarationBase.symbol_ = this.symbol_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irDeclarationBase.originName_ = this.originName_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            irDeclarationBase.coordinates_ = this.coordinates_;
            if ((i & 8) == 8) {
                i2 |= 8;
            }
            irDeclarationBase.flags_ = this.flags_;
            if ((this.bitField0_ & 16) == 16) {
                this.annotation_ = Collections.unmodifiableList(this.annotation_);
                this.bitField0_ &= -17;
            }
            irDeclarationBase.annotation_ = this.annotation_;
            irDeclarationBase.bitField0_ = i2;
            return irDeclarationBase;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrDeclarationBase irDeclarationBase) {
            if (irDeclarationBase == IrDeclarationBase.getDefaultInstance()) {
                return this;
            }
            if (irDeclarationBase.hasSymbol()) {
                setSymbol(irDeclarationBase.getSymbol());
            }
            if (irDeclarationBase.hasOriginName()) {
                setOriginName(irDeclarationBase.getOriginName());
            }
            if (irDeclarationBase.hasCoordinates()) {
                setCoordinates(irDeclarationBase.getCoordinates());
            }
            if (irDeclarationBase.hasFlags()) {
                setFlags(irDeclarationBase.getFlags());
            }
            if (!irDeclarationBase.annotation_.isEmpty()) {
                if (this.annotation_.isEmpty()) {
                    this.annotation_ = irDeclarationBase.annotation_;
                    this.bitField0_ &= -17;
                } else {
                    ensureAnnotationIsMutable();
                    this.annotation_.addAll(irDeclarationBase.annotation_);
                }
            }
            setUnknownFields(getUnknownFields().concat(irDeclarationBase.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (!hasSymbol() || !hasOriginName() || !hasCoordinates()) {
                return false;
            }
            for (int i = 0; i < getAnnotationCount(); i++) {
                if (!getAnnotation(i).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrDeclarationBase irDeclarationBase = null;
            try {
                try {
                    IrDeclarationBase partialFrom = IrDeclarationBase.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrDeclarationBase irDeclarationBase2 = (IrDeclarationBase) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irDeclarationBase = irDeclarationBase2;
                        if (irDeclarationBase != null) {
                            mergeFrom(irDeclarationBase);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irDeclarationBase != null) {
                    mergeFrom(irDeclarationBase);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
        public boolean hasSymbol() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
        public long getSymbol() {
            return this.symbol_;
        }

        public Builder setSymbol(long j) {
            this.bitField0_ |= 1;
            this.symbol_ = j;
            return this;
        }

        public Builder clearSymbol() {
            this.bitField0_ &= -2;
            this.symbol_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
        public boolean hasOriginName() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
        public int getOriginName() {
            return this.originName_;
        }

        public Builder setOriginName(int i) {
            this.bitField0_ |= 2;
            this.originName_ = i;
            return this;
        }

        public Builder clearOriginName() {
            this.bitField0_ &= -3;
            this.originName_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
        public boolean hasCoordinates() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
        public long getCoordinates() {
            return this.coordinates_;
        }

        public Builder setCoordinates(long j) {
            this.bitField0_ |= 4;
            this.coordinates_ = j;
            return this;
        }

        public Builder clearCoordinates() {
            this.bitField0_ &= -5;
            this.coordinates_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
        public boolean hasFlags() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
        public long getFlags() {
            return this.flags_;
        }

        public Builder setFlags(long j) {
            this.bitField0_ |= 8;
            this.flags_ = j;
            return this;
        }

        public Builder clearFlags() {
            this.bitField0_ &= -9;
            this.flags_ = 0L;
            return this;
        }

        private void ensureAnnotationIsMutable() {
            if ((this.bitField0_ & 16) != 16) {
                this.annotation_ = new ArrayList(this.annotation_);
                this.bitField0_ |= 16;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
        public List<IrConstructorCall> getAnnotationList() {
            return Collections.unmodifiableList(this.annotation_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
        public int getAnnotationCount() {
            return this.annotation_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBaseOrBuilder
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
            this.bitField0_ &= -17;
            return this;
        }

        public Builder removeAnnotation(int i) {
            ensureAnnotationIsMutable();
            this.annotation_.remove(i);
            return this;
        }
    }
}
