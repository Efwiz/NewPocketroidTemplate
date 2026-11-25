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
public final class IrClassReference extends GeneratedMessageLite implements IrClassReferenceOrBuilder {
    public static final int CLASS_SYMBOL_FIELD_NUMBER = 1;
    public static final int CLASS_TYPE_FIELD_NUMBER = 2;
    public static Parser<IrClassReference> PARSER = new AbstractParser<IrClassReference>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrClassReference.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrClassReference parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrClassReference(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrClassReference defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private long classSymbol_;
    private int classType_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    private IrClassReference(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrClassReference(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrClassReference getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrClassReference getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrClassReference(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                            this.classSymbol_ = codedInputStream.readInt64();
                        } else if (tag != 16) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            this.bitField0_ |= 2;
                            this.classType_ = codedInputStream.readInt32();
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
        IrClassReference irClassReference = new IrClassReference(true);
        defaultInstance = irClassReference;
        irClassReference.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrClassReference> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassReferenceOrBuilder
    public boolean hasClassSymbol() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassReferenceOrBuilder
    public long getClassSymbol() {
        return this.classSymbol_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassReferenceOrBuilder
    public boolean hasClassType() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassReferenceOrBuilder
    public int getClassType() {
        return this.classType_;
    }

    private void initFields() {
        this.classSymbol_ = 0L;
        this.classType_ = 0;
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
        if (!hasClassSymbol()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasClassType()) {
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
            codedOutputStream.writeInt64(1, this.classSymbol_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt32(2, this.classType_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt64Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt64Size(1, this.classSymbol_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeInt64Size += CodedOutputStream.computeInt32Size(2, this.classType_);
        }
        int size = iComputeInt64Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrClassReference parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrClassReference parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrClassReference parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrClassReference parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrClassReference parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrClassReference parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrClassReference parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrClassReference parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrClassReference parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrClassReference parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrClassReference irClassReference) {
        return newBuilder().mergeFrom(irClassReference);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrClassReference, Builder> implements IrClassReferenceOrBuilder {
        private int bitField0_;
        private long classSymbol_;
        private int classType_;

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
            this.classSymbol_ = 0L;
            int i = this.bitField0_;
            this.classType_ = 0;
            this.bitField0_ = i & (-4);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrClassReference getDefaultInstanceForType() {
            return IrClassReference.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrClassReference build() {
            IrClassReference irClassReferenceBuildPartial = buildPartial();
            if (irClassReferenceBuildPartial.isInitialized()) {
                return irClassReferenceBuildPartial;
            }
            throw newUninitializedMessageException(irClassReferenceBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrClassReference buildPartial() {
            IrClassReference irClassReference = new IrClassReference(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irClassReference.classSymbol_ = this.classSymbol_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irClassReference.classType_ = this.classType_;
            irClassReference.bitField0_ = i2;
            return irClassReference;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrClassReference irClassReference) {
            if (irClassReference == IrClassReference.getDefaultInstance()) {
                return this;
            }
            if (irClassReference.hasClassSymbol()) {
                setClassSymbol(irClassReference.getClassSymbol());
            }
            if (irClassReference.hasClassType()) {
                setClassType(irClassReference.getClassType());
            }
            setUnknownFields(getUnknownFields().concat(irClassReference.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasClassSymbol() && hasClassType();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrClassReference irClassReference = null;
            try {
                try {
                    IrClassReference partialFrom = IrClassReference.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrClassReference irClassReference2 = (IrClassReference) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irClassReference = irClassReference2;
                        if (irClassReference != null) {
                            mergeFrom(irClassReference);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irClassReference != null) {
                    mergeFrom(irClassReference);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassReferenceOrBuilder
        public boolean hasClassSymbol() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassReferenceOrBuilder
        public long getClassSymbol() {
            return this.classSymbol_;
        }

        public Builder setClassSymbol(long j) {
            this.bitField0_ |= 1;
            this.classSymbol_ = j;
            return this;
        }

        public Builder clearClassSymbol() {
            this.bitField0_ &= -2;
            this.classSymbol_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassReferenceOrBuilder
        public boolean hasClassType() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrClassReferenceOrBuilder
        public int getClassType() {
            return this.classType_;
        }

        public Builder setClassType(int i) {
            this.bitField0_ |= 2;
            this.classType_ = i;
            return this;
        }

        public Builder clearClassType() {
            this.bitField0_ &= -3;
            this.classType_ = 0;
            return this;
        }
    }
}
