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
public final class IrEnumEntry extends GeneratedMessageLite implements IrEnumEntryOrBuilder {
    public static final int BASE_FIELD_NUMBER = 1;
    public static final int CORRESPONDING_CLASS_FIELD_NUMBER = 4;
    public static final int INITIALIZER_FIELD_NUMBER = 3;
    public static final int NAME_FIELD_NUMBER = 2;
    public static Parser<IrEnumEntry> PARSER = new AbstractParser<IrEnumEntry>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntry.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrEnumEntry parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrEnumEntry(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrEnumEntry defaultInstance;
    private static final long serialVersionUID = 0;
    private IrDeclarationBase base_;
    private int bitField0_;
    private IrClass correspondingClass_;
    private int initializer_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int name_;
    private final ByteString unknownFields;

    private IrEnumEntry(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrEnumEntry(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrEnumEntry getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrEnumEntry getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrEnumEntry(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        GeneratedMessageLite.Builder builder;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        initFields();
        ByteString.Output outputNewOutput = ByteString.newOutput();
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
        boolean z = false;
        while (!z) {
            try {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                builder = (this.bitField0_ & 1) == 1 ? this.base_.toBuilder() : null;
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
                                this.bitField0_ |= 4;
                                this.initializer_ = codedInputStream.readInt32();
                            } else if (tag != 34) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                builder = (this.bitField0_ & 8) == 8 ? this.correspondingClass_.toBuilder() : null;
                                IrClass irClass = (IrClass) codedInputStream.readMessage(IrClass.PARSER, extensionRegistryLite);
                                this.correspondingClass_ = irClass;
                                if (builder != null) {
                                    builder.mergeFrom(irClass);
                                    this.correspondingClass_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 8;
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
        IrEnumEntry irEnumEntry = new IrEnumEntry(true);
        defaultInstance = irEnumEntry;
        irEnumEntry.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrEnumEntry> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
    public boolean hasBase() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
    public IrDeclarationBase getBase() {
        return this.base_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
    public boolean hasName() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
    public int getName() {
        return this.name_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
    public boolean hasInitializer() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
    public int getInitializer() {
        return this.initializer_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
    public boolean hasCorrespondingClass() {
        return (this.bitField0_ & 8) == 8;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
    public IrClass getCorrespondingClass() {
        return this.correspondingClass_;
    }

    private void initFields() {
        this.base_ = IrDeclarationBase.getDefaultInstance();
        this.name_ = 0;
        this.initializer_ = 0;
        this.correspondingClass_ = IrClass.getDefaultInstance();
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
        if (hasCorrespondingClass() && !getCorrespondingClass().isInitialized()) {
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
            codedOutputStream.writeInt32(3, this.initializer_);
        }
        if ((this.bitField0_ & 8) == 8) {
            codedOutputStream.writeMessage(4, this.correspondingClass_);
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
            iComputeMessageSize += CodedOutputStream.computeInt32Size(3, this.initializer_);
        }
        if ((this.bitField0_ & 8) == 8) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(4, this.correspondingClass_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrEnumEntry parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrEnumEntry parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrEnumEntry parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrEnumEntry parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrEnumEntry parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrEnumEntry parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrEnumEntry parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrEnumEntry parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrEnumEntry parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrEnumEntry parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrEnumEntry irEnumEntry) {
        return newBuilder().mergeFrom(irEnumEntry);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrEnumEntry, Builder> implements IrEnumEntryOrBuilder {
        private int bitField0_;
        private int initializer_;
        private int name_;
        private IrDeclarationBase base_ = IrDeclarationBase.getDefaultInstance();
        private IrClass correspondingClass_ = IrClass.getDefaultInstance();

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
            this.initializer_ = 0;
            this.bitField0_ = i & (-8);
            this.correspondingClass_ = IrClass.getDefaultInstance();
            this.bitField0_ &= -9;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrEnumEntry getDefaultInstanceForType() {
            return IrEnumEntry.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrEnumEntry build() {
            IrEnumEntry irEnumEntryBuildPartial = buildPartial();
            if (irEnumEntryBuildPartial.isInitialized()) {
                return irEnumEntryBuildPartial;
            }
            throw newUninitializedMessageException(irEnumEntryBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrEnumEntry buildPartial() {
            IrEnumEntry irEnumEntry = new IrEnumEntry(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irEnumEntry.base_ = this.base_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irEnumEntry.name_ = this.name_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            irEnumEntry.initializer_ = this.initializer_;
            if ((i & 8) == 8) {
                i2 |= 8;
            }
            irEnumEntry.correspondingClass_ = this.correspondingClass_;
            irEnumEntry.bitField0_ = i2;
            return irEnumEntry;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrEnumEntry irEnumEntry) {
            if (irEnumEntry == IrEnumEntry.getDefaultInstance()) {
                return this;
            }
            if (irEnumEntry.hasBase()) {
                mergeBase(irEnumEntry.getBase());
            }
            if (irEnumEntry.hasName()) {
                setName(irEnumEntry.getName());
            }
            if (irEnumEntry.hasInitializer()) {
                setInitializer(irEnumEntry.getInitializer());
            }
            if (irEnumEntry.hasCorrespondingClass()) {
                mergeCorrespondingClass(irEnumEntry.getCorrespondingClass());
            }
            setUnknownFields(getUnknownFields().concat(irEnumEntry.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (hasBase() && hasName() && getBase().isInitialized()) {
                return !hasCorrespondingClass() || getCorrespondingClass().isInitialized();
            }
            return false;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrEnumEntry irEnumEntry = null;
            try {
                try {
                    IrEnumEntry partialFrom = IrEnumEntry.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrEnumEntry irEnumEntry2 = (IrEnumEntry) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irEnumEntry = irEnumEntry2;
                        if (irEnumEntry != null) {
                            mergeFrom(irEnumEntry);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irEnumEntry != null) {
                    mergeFrom(irEnumEntry);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
        public boolean hasBase() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
        public boolean hasInitializer() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
        public int getInitializer() {
            return this.initializer_;
        }

        public Builder setInitializer(int i) {
            this.bitField0_ |= 4;
            this.initializer_ = i;
            return this;
        }

        public Builder clearInitializer() {
            this.bitField0_ &= -5;
            this.initializer_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
        public boolean hasCorrespondingClass() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntryOrBuilder
        public IrClass getCorrespondingClass() {
            return this.correspondingClass_;
        }

        public Builder setCorrespondingClass(IrClass irClass) {
            irClass.getClass();
            this.correspondingClass_ = irClass;
            this.bitField0_ |= 8;
            return this;
        }

        public Builder setCorrespondingClass(IrClass.Builder builder) {
            this.correspondingClass_ = builder.build();
            this.bitField0_ |= 8;
            return this;
        }

        public Builder mergeCorrespondingClass(IrClass irClass) {
            if ((this.bitField0_ & 8) == 8 && this.correspondingClass_ != IrClass.getDefaultInstance()) {
                this.correspondingClass_ = IrClass.newBuilder(this.correspondingClass_).mergeFrom(irClass).buildPartial();
            } else {
                this.correspondingClass_ = irClass;
            }
            this.bitField0_ |= 8;
            return this;
        }

        public Builder clearCorrespondingClass() {
            this.correspondingClass_ = IrClass.getDefaultInstance();
            this.bitField0_ &= -9;
            return this;
        }
    }
}
