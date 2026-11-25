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
public final class IrDefinitelyNotNullType extends GeneratedMessageLite implements IrDefinitelyNotNullTypeOrBuilder {
    public static Parser<IrDefinitelyNotNullType> PARSER = new AbstractParser<IrDefinitelyNotNullType>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrDefinitelyNotNullType.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrDefinitelyNotNullType parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrDefinitelyNotNullType(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int TYPES_FIELD_NUMBER = 1;
    private static final IrDefinitelyNotNullType defaultInstance;
    private static final long serialVersionUID = 0;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int typesMemoizedSerializedSize;
    private List<Integer> types_;
    private final ByteString unknownFields;

    private IrDefinitelyNotNullType(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.typesMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrDefinitelyNotNullType(boolean z) {
        this.typesMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrDefinitelyNotNullType getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrDefinitelyNotNullType getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrDefinitelyNotNullType(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.typesMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        initFields();
        ByteString.Output outputNewOutput = ByteString.newOutput();
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            try {
                try {
                    int tag = codedInputStream.readTag();
                    if (tag != 0) {
                        if (tag == 8) {
                            if (!z2) {
                                this.types_ = new ArrayList();
                                z2 = true;
                            }
                            this.types_.add(Integer.valueOf(codedInputStream.readInt32()));
                        } else if (tag != 10) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                            if (!z2 && codedInputStream.getBytesUntilLimit() > 0) {
                                this.types_ = new ArrayList();
                                z2 = true;
                            }
                            while (codedInputStream.getBytesUntilLimit() > 0) {
                                this.types_.add(Integer.valueOf(codedInputStream.readInt32()));
                            }
                            codedInputStream.popLimit(iPushLimit);
                        }
                    }
                    z = true;
                } catch (Throwable th) {
                    if (z2) {
                        this.types_ = Collections.unmodifiableList(this.types_);
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
        if (z2) {
            this.types_ = Collections.unmodifiableList(this.types_);
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
        IrDefinitelyNotNullType irDefinitelyNotNullType = new IrDefinitelyNotNullType(true);
        defaultInstance = irDefinitelyNotNullType;
        irDefinitelyNotNullType.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrDefinitelyNotNullType> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDefinitelyNotNullTypeOrBuilder
    public List<Integer> getTypesList() {
        return this.types_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDefinitelyNotNullTypeOrBuilder
    public int getTypesCount() {
        return this.types_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDefinitelyNotNullTypeOrBuilder
    public int getTypes(int i) {
        return this.types_.get(i).intValue();
    }

    private void initFields() {
        this.types_ = Collections.emptyList();
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
        if (getTypesList().size() > 0) {
            codedOutputStream.writeRawVarint32(10);
            codedOutputStream.writeRawVarint32(this.typesMemoizedSerializedSize);
        }
        for (int i = 0; i < this.types_.size(); i++) {
            codedOutputStream.writeInt32NoTag(this.types_.get(i).intValue());
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
        for (int i2 = 0; i2 < this.types_.size(); i2++) {
            iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.types_.get(i2).intValue());
        }
        int iComputeInt32SizeNoTag2 = !getTypesList().isEmpty() ? iComputeInt32SizeNoTag + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag) : iComputeInt32SizeNoTag;
        this.typesMemoizedSerializedSize = iComputeInt32SizeNoTag;
        int size = iComputeInt32SizeNoTag2 + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrDefinitelyNotNullType parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrDefinitelyNotNullType parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrDefinitelyNotNullType parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrDefinitelyNotNullType parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrDefinitelyNotNullType parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrDefinitelyNotNullType parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrDefinitelyNotNullType parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrDefinitelyNotNullType parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrDefinitelyNotNullType parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrDefinitelyNotNullType parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrDefinitelyNotNullType irDefinitelyNotNullType) {
        return newBuilder().mergeFrom(irDefinitelyNotNullType);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrDefinitelyNotNullType, Builder> implements IrDefinitelyNotNullTypeOrBuilder {
        private int bitField0_;
        private List<Integer> types_ = Collections.emptyList();

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
            this.types_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrDefinitelyNotNullType getDefaultInstanceForType() {
            return IrDefinitelyNotNullType.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrDefinitelyNotNullType build() {
            IrDefinitelyNotNullType irDefinitelyNotNullTypeBuildPartial = buildPartial();
            if (irDefinitelyNotNullTypeBuildPartial.isInitialized()) {
                return irDefinitelyNotNullTypeBuildPartial;
            }
            throw newUninitializedMessageException(irDefinitelyNotNullTypeBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrDefinitelyNotNullType buildPartial() {
            IrDefinitelyNotNullType irDefinitelyNotNullType = new IrDefinitelyNotNullType(this);
            if ((this.bitField0_ & 1) == 1) {
                this.types_ = Collections.unmodifiableList(this.types_);
                this.bitField0_ &= -2;
            }
            irDefinitelyNotNullType.types_ = this.types_;
            return irDefinitelyNotNullType;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrDefinitelyNotNullType irDefinitelyNotNullType) {
            if (irDefinitelyNotNullType == IrDefinitelyNotNullType.getDefaultInstance()) {
                return this;
            }
            if (!irDefinitelyNotNullType.types_.isEmpty()) {
                if (this.types_.isEmpty()) {
                    this.types_ = irDefinitelyNotNullType.types_;
                    this.bitField0_ &= -2;
                } else {
                    ensureTypesIsMutable();
                    this.types_.addAll(irDefinitelyNotNullType.types_);
                }
            }
            setUnknownFields(getUnknownFields().concat(irDefinitelyNotNullType.unknownFields));
            return this;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrDefinitelyNotNullType irDefinitelyNotNullType = null;
            try {
                try {
                    IrDefinitelyNotNullType partialFrom = IrDefinitelyNotNullType.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrDefinitelyNotNullType irDefinitelyNotNullType2 = (IrDefinitelyNotNullType) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irDefinitelyNotNullType = irDefinitelyNotNullType2;
                        if (irDefinitelyNotNullType != null) {
                            mergeFrom(irDefinitelyNotNullType);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irDefinitelyNotNullType != null) {
                    mergeFrom(irDefinitelyNotNullType);
                }
                throw th;
            }
        }

        private void ensureTypesIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
                this.types_ = new ArrayList(this.types_);
                this.bitField0_ |= 1;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDefinitelyNotNullTypeOrBuilder
        public List<Integer> getTypesList() {
            return Collections.unmodifiableList(this.types_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDefinitelyNotNullTypeOrBuilder
        public int getTypesCount() {
            return this.types_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDefinitelyNotNullTypeOrBuilder
        public int getTypes(int i) {
            return this.types_.get(i).intValue();
        }

        public Builder setTypes(int i, int i2) {
            ensureTypesIsMutable();
            this.types_.set(i, Integer.valueOf(i2));
            return this;
        }

        public Builder addTypes(int i) {
            ensureTypesIsMutable();
            this.types_.add(Integer.valueOf(i));
            return this;
        }

        public Builder addAllTypes(Iterable<? extends Integer> iterable) {
            ensureTypesIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.types_);
            return this;
        }

        public Builder clearTypes() {
            this.types_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
        }
    }
}
