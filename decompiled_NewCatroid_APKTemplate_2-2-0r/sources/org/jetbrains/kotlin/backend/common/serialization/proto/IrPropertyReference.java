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
public final class IrPropertyReference extends GeneratedMessageLite implements IrPropertyReferenceOrBuilder {
    public static final int FIELD_FIELD_NUMBER = 1;
    public static final int GETTER_FIELD_NUMBER = 2;
    public static final int MEMBER_ACCESS_FIELD_NUMBER = 5;
    public static final int ORIGIN_NAME_FIELD_NUMBER = 4;
    public static Parser<IrPropertyReference> PARSER = new AbstractParser<IrPropertyReference>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReference.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrPropertyReference parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrPropertyReference(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int SETTER_FIELD_NUMBER = 3;
    public static final int SYMBOL_FIELD_NUMBER = 6;
    private static final IrPropertyReference defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private long field_;
    private long getter_;
    private MemberAccessCommon memberAccess_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int originName_;
    private long setter_;
    private long symbol_;
    private final ByteString unknownFields;

    private IrPropertyReference(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrPropertyReference(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrPropertyReference getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrPropertyReference getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrPropertyReference(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                            this.field_ = codedInputStream.readInt64();
                        } else if (tag == 16) {
                            this.bitField0_ |= 2;
                            this.getter_ = codedInputStream.readInt64();
                        } else if (tag == 24) {
                            this.bitField0_ |= 4;
                            this.setter_ = codedInputStream.readInt64();
                        } else if (tag == 32) {
                            this.bitField0_ |= 8;
                            this.originName_ = codedInputStream.readInt32();
                        } else if (tag == 42) {
                            MemberAccessCommon.Builder builder = (this.bitField0_ & 16) == 16 ? this.memberAccess_.toBuilder() : null;
                            MemberAccessCommon memberAccessCommon = (MemberAccessCommon) codedInputStream.readMessage(MemberAccessCommon.PARSER, extensionRegistryLite);
                            this.memberAccess_ = memberAccessCommon;
                            if (builder != null) {
                                builder.mergeFrom(memberAccessCommon);
                                this.memberAccess_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 16;
                        } else if (tag != 48) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            this.bitField0_ |= 32;
                            this.symbol_ = codedInputStream.readInt64();
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
        IrPropertyReference irPropertyReference = new IrPropertyReference(true);
        defaultInstance = irPropertyReference;
        irPropertyReference.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrPropertyReference> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
    public boolean hasField() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
    public long getField() {
        return this.field_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
    public boolean hasGetter() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
    public long getGetter() {
        return this.getter_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
    public boolean hasSetter() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
    public long getSetter() {
        return this.setter_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
    public boolean hasOriginName() {
        return (this.bitField0_ & 8) == 8;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
    public int getOriginName() {
        return this.originName_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
    public boolean hasMemberAccess() {
        return (this.bitField0_ & 16) == 16;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
    public MemberAccessCommon getMemberAccess() {
        return this.memberAccess_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
    public boolean hasSymbol() {
        return (this.bitField0_ & 32) == 32;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
    public long getSymbol() {
        return this.symbol_;
    }

    private void initFields() {
        this.field_ = 0L;
        this.getter_ = 0L;
        this.setter_ = 0L;
        this.originName_ = 0;
        this.memberAccess_ = MemberAccessCommon.getDefaultInstance();
        this.symbol_ = 0L;
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
        if (!hasMemberAccess()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasSymbol()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getMemberAccess().isInitialized()) {
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
            codedOutputStream.writeInt64(1, this.field_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt64(2, this.getter_);
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeInt64(3, this.setter_);
        }
        if ((this.bitField0_ & 8) == 8) {
            codedOutputStream.writeInt32(4, this.originName_);
        }
        if ((this.bitField0_ & 16) == 16) {
            codedOutputStream.writeMessage(5, this.memberAccess_);
        }
        if ((this.bitField0_ & 32) == 32) {
            codedOutputStream.writeInt64(6, this.symbol_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt64Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt64Size(1, this.field_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeInt64Size += CodedOutputStream.computeInt64Size(2, this.getter_);
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeInt64Size += CodedOutputStream.computeInt64Size(3, this.setter_);
        }
        if ((this.bitField0_ & 8) == 8) {
            iComputeInt64Size += CodedOutputStream.computeInt32Size(4, this.originName_);
        }
        if ((this.bitField0_ & 16) == 16) {
            iComputeInt64Size += CodedOutputStream.computeMessageSize(5, this.memberAccess_);
        }
        if ((this.bitField0_ & 32) == 32) {
            iComputeInt64Size += CodedOutputStream.computeInt64Size(6, this.symbol_);
        }
        int size = iComputeInt64Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrPropertyReference parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrPropertyReference parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrPropertyReference parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrPropertyReference parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrPropertyReference parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrPropertyReference parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrPropertyReference parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrPropertyReference parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrPropertyReference parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrPropertyReference parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrPropertyReference irPropertyReference) {
        return newBuilder().mergeFrom(irPropertyReference);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrPropertyReference, Builder> implements IrPropertyReferenceOrBuilder {
        private int bitField0_;
        private long field_;
        private long getter_;
        private MemberAccessCommon memberAccess_ = MemberAccessCommon.getDefaultInstance();
        private int originName_;
        private long setter_;
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
            this.field_ = 0L;
            int i = this.bitField0_;
            this.getter_ = 0L;
            this.setter_ = 0L;
            this.originName_ = 0;
            this.bitField0_ = i & (-16);
            this.memberAccess_ = MemberAccessCommon.getDefaultInstance();
            int i2 = this.bitField0_;
            this.symbol_ = 0L;
            this.bitField0_ = i2 & (-49);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrPropertyReference getDefaultInstanceForType() {
            return IrPropertyReference.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrPropertyReference build() {
            IrPropertyReference irPropertyReferenceBuildPartial = buildPartial();
            if (irPropertyReferenceBuildPartial.isInitialized()) {
                return irPropertyReferenceBuildPartial;
            }
            throw newUninitializedMessageException(irPropertyReferenceBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrPropertyReference buildPartial() {
            IrPropertyReference irPropertyReference = new IrPropertyReference(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irPropertyReference.field_ = this.field_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irPropertyReference.getter_ = this.getter_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            irPropertyReference.setter_ = this.setter_;
            if ((i & 8) == 8) {
                i2 |= 8;
            }
            irPropertyReference.originName_ = this.originName_;
            if ((i & 16) == 16) {
                i2 |= 16;
            }
            irPropertyReference.memberAccess_ = this.memberAccess_;
            if ((i & 32) == 32) {
                i2 |= 32;
            }
            irPropertyReference.symbol_ = this.symbol_;
            irPropertyReference.bitField0_ = i2;
            return irPropertyReference;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrPropertyReference irPropertyReference) {
            if (irPropertyReference == IrPropertyReference.getDefaultInstance()) {
                return this;
            }
            if (irPropertyReference.hasField()) {
                setField(irPropertyReference.getField());
            }
            if (irPropertyReference.hasGetter()) {
                setGetter(irPropertyReference.getGetter());
            }
            if (irPropertyReference.hasSetter()) {
                setSetter(irPropertyReference.getSetter());
            }
            if (irPropertyReference.hasOriginName()) {
                setOriginName(irPropertyReference.getOriginName());
            }
            if (irPropertyReference.hasMemberAccess()) {
                mergeMemberAccess(irPropertyReference.getMemberAccess());
            }
            if (irPropertyReference.hasSymbol()) {
                setSymbol(irPropertyReference.getSymbol());
            }
            setUnknownFields(getUnknownFields().concat(irPropertyReference.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasMemberAccess() && hasSymbol() && getMemberAccess().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrPropertyReference irPropertyReference = null;
            try {
                try {
                    IrPropertyReference partialFrom = IrPropertyReference.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrPropertyReference irPropertyReference2 = (IrPropertyReference) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irPropertyReference = irPropertyReference2;
                        if (irPropertyReference != null) {
                            mergeFrom(irPropertyReference);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irPropertyReference != null) {
                    mergeFrom(irPropertyReference);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
        public boolean hasField() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
        public long getField() {
            return this.field_;
        }

        public Builder setField(long j) {
            this.bitField0_ |= 1;
            this.field_ = j;
            return this;
        }

        public Builder clearField() {
            this.bitField0_ &= -2;
            this.field_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
        public boolean hasGetter() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
        public long getGetter() {
            return this.getter_;
        }

        public Builder setGetter(long j) {
            this.bitField0_ |= 2;
            this.getter_ = j;
            return this;
        }

        public Builder clearGetter() {
            this.bitField0_ &= -3;
            this.getter_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
        public boolean hasSetter() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
        public long getSetter() {
            return this.setter_;
        }

        public Builder setSetter(long j) {
            this.bitField0_ |= 4;
            this.setter_ = j;
            return this;
        }

        public Builder clearSetter() {
            this.bitField0_ &= -5;
            this.setter_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
        public boolean hasOriginName() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
        public int getOriginName() {
            return this.originName_;
        }

        public Builder setOriginName(int i) {
            this.bitField0_ |= 8;
            this.originName_ = i;
            return this;
        }

        public Builder clearOriginName() {
            this.bitField0_ &= -9;
            this.originName_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
        public boolean hasMemberAccess() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
        public MemberAccessCommon getMemberAccess() {
            return this.memberAccess_;
        }

        public Builder setMemberAccess(MemberAccessCommon memberAccessCommon) {
            memberAccessCommon.getClass();
            this.memberAccess_ = memberAccessCommon;
            this.bitField0_ |= 16;
            return this;
        }

        public Builder setMemberAccess(MemberAccessCommon.Builder builder) {
            this.memberAccess_ = builder.build();
            this.bitField0_ |= 16;
            return this;
        }

        public Builder mergeMemberAccess(MemberAccessCommon memberAccessCommon) {
            if ((this.bitField0_ & 16) == 16 && this.memberAccess_ != MemberAccessCommon.getDefaultInstance()) {
                this.memberAccess_ = MemberAccessCommon.newBuilder(this.memberAccess_).mergeFrom(memberAccessCommon).buildPartial();
            } else {
                this.memberAccess_ = memberAccessCommon;
            }
            this.bitField0_ |= 16;
            return this;
        }

        public Builder clearMemberAccess() {
            this.memberAccess_ = MemberAccessCommon.getDefaultInstance();
            this.bitField0_ &= -17;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
        public boolean hasSymbol() {
            return (this.bitField0_ & 32) == 32;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReferenceOrBuilder
        public long getSymbol() {
            return this.symbol_;
        }

        public Builder setSymbol(long j) {
            this.bitField0_ |= 32;
            this.symbol_ = j;
            return this;
        }

        public Builder clearSymbol() {
            this.bitField0_ &= -33;
            this.symbol_ = 0L;
            return this;
        }
    }
}
