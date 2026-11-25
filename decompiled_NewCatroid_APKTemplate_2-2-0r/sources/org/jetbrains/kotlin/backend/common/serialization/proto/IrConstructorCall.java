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
public final class IrConstructorCall extends GeneratedMessageLite implements IrConstructorCallOrBuilder {
    public static final int CONSTRUCTOR_TYPE_ARGUMENTS_COUNT_FIELD_NUMBER = 2;
    public static final int MEMBER_ACCESS_FIELD_NUMBER = 3;
    public static final int ORIGIN_NAME_FIELD_NUMBER = 4;
    public static Parser<IrConstructorCall> PARSER = new AbstractParser<IrConstructorCall>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrConstructorCall parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrConstructorCall(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int SYMBOL_FIELD_NUMBER = 1;
    private static final IrConstructorCall defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private int constructorTypeArgumentsCount_;
    private MemberAccessCommon memberAccess_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int originName_;
    private long symbol_;
    private final ByteString unknownFields;

    private IrConstructorCall(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrConstructorCall(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrConstructorCall getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrConstructorCall getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrConstructorCall(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                            if (tag == 8) {
                                this.bitField0_ |= 1;
                                this.symbol_ = codedInputStream.readInt64();
                            } else if (tag == 16) {
                                this.bitField0_ |= 2;
                                this.constructorTypeArgumentsCount_ = codedInputStream.readInt32();
                            } else if (tag == 26) {
                                MemberAccessCommon.Builder builder = (this.bitField0_ & 4) == 4 ? this.memberAccess_.toBuilder() : null;
                                MemberAccessCommon memberAccessCommon = (MemberAccessCommon) codedInputStream.readMessage(MemberAccessCommon.PARSER, extensionRegistryLite);
                                this.memberAccess_ = memberAccessCommon;
                                if (builder != null) {
                                    builder.mergeFrom(memberAccessCommon);
                                    this.memberAccess_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 4;
                            } else if (tag != 32) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.bitField0_ |= 8;
                                this.originName_ = codedInputStream.readInt32();
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
        IrConstructorCall irConstructorCall = new IrConstructorCall(true);
        defaultInstance = irConstructorCall;
        irConstructorCall.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrConstructorCall> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
    public boolean hasSymbol() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
    public long getSymbol() {
        return this.symbol_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
    public boolean hasConstructorTypeArgumentsCount() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
    public int getConstructorTypeArgumentsCount() {
        return this.constructorTypeArgumentsCount_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
    public boolean hasMemberAccess() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
    public MemberAccessCommon getMemberAccess() {
        return this.memberAccess_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
    public boolean hasOriginName() {
        return (this.bitField0_ & 8) == 8;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
    public int getOriginName() {
        return this.originName_;
    }

    private void initFields() {
        this.symbol_ = 0L;
        this.constructorTypeArgumentsCount_ = 0;
        this.memberAccess_ = MemberAccessCommon.getDefaultInstance();
        this.originName_ = 0;
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
        if (!hasConstructorTypeArgumentsCount()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasMemberAccess()) {
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
            codedOutputStream.writeInt64(1, this.symbol_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt32(2, this.constructorTypeArgumentsCount_);
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeMessage(3, this.memberAccess_);
        }
        if ((this.bitField0_ & 8) == 8) {
            codedOutputStream.writeInt32(4, this.originName_);
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
            iComputeInt64Size += CodedOutputStream.computeInt32Size(2, this.constructorTypeArgumentsCount_);
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeInt64Size += CodedOutputStream.computeMessageSize(3, this.memberAccess_);
        }
        if ((this.bitField0_ & 8) == 8) {
            iComputeInt64Size += CodedOutputStream.computeInt32Size(4, this.originName_);
        }
        int size = iComputeInt64Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrConstructorCall parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrConstructorCall parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrConstructorCall parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrConstructorCall parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrConstructorCall parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrConstructorCall parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrConstructorCall parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrConstructorCall parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrConstructorCall parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrConstructorCall parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrConstructorCall irConstructorCall) {
        return newBuilder().mergeFrom(irConstructorCall);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrConstructorCall, Builder> implements IrConstructorCallOrBuilder {
        private int bitField0_;
        private int constructorTypeArgumentsCount_;
        private MemberAccessCommon memberAccess_ = MemberAccessCommon.getDefaultInstance();
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
            this.constructorTypeArgumentsCount_ = 0;
            this.bitField0_ = i & (-4);
            this.memberAccess_ = MemberAccessCommon.getDefaultInstance();
            int i2 = this.bitField0_;
            this.originName_ = 0;
            this.bitField0_ = i2 & (-13);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrConstructorCall getDefaultInstanceForType() {
            return IrConstructorCall.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrConstructorCall build() {
            IrConstructorCall irConstructorCallBuildPartial = buildPartial();
            if (irConstructorCallBuildPartial.isInitialized()) {
                return irConstructorCallBuildPartial;
            }
            throw newUninitializedMessageException(irConstructorCallBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrConstructorCall buildPartial() {
            IrConstructorCall irConstructorCall = new IrConstructorCall(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irConstructorCall.symbol_ = this.symbol_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irConstructorCall.constructorTypeArgumentsCount_ = this.constructorTypeArgumentsCount_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            irConstructorCall.memberAccess_ = this.memberAccess_;
            if ((i & 8) == 8) {
                i2 |= 8;
            }
            irConstructorCall.originName_ = this.originName_;
            irConstructorCall.bitField0_ = i2;
            return irConstructorCall;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrConstructorCall irConstructorCall) {
            if (irConstructorCall == IrConstructorCall.getDefaultInstance()) {
                return this;
            }
            if (irConstructorCall.hasSymbol()) {
                setSymbol(irConstructorCall.getSymbol());
            }
            if (irConstructorCall.hasConstructorTypeArgumentsCount()) {
                setConstructorTypeArgumentsCount(irConstructorCall.getConstructorTypeArgumentsCount());
            }
            if (irConstructorCall.hasMemberAccess()) {
                mergeMemberAccess(irConstructorCall.getMemberAccess());
            }
            if (irConstructorCall.hasOriginName()) {
                setOriginName(irConstructorCall.getOriginName());
            }
            setUnknownFields(getUnknownFields().concat(irConstructorCall.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasSymbol() && hasConstructorTypeArgumentsCount() && hasMemberAccess() && getMemberAccess().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrConstructorCall irConstructorCall = null;
            try {
                try {
                    IrConstructorCall partialFrom = IrConstructorCall.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrConstructorCall irConstructorCall2 = (IrConstructorCall) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irConstructorCall = irConstructorCall2;
                        if (irConstructorCall != null) {
                            mergeFrom(irConstructorCall);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irConstructorCall != null) {
                    mergeFrom(irConstructorCall);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
        public boolean hasSymbol() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
        public boolean hasConstructorTypeArgumentsCount() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
        public int getConstructorTypeArgumentsCount() {
            return this.constructorTypeArgumentsCount_;
        }

        public Builder setConstructorTypeArgumentsCount(int i) {
            this.bitField0_ |= 2;
            this.constructorTypeArgumentsCount_ = i;
            return this;
        }

        public Builder clearConstructorTypeArgumentsCount() {
            this.bitField0_ &= -3;
            this.constructorTypeArgumentsCount_ = 0;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
        public boolean hasMemberAccess() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
        public MemberAccessCommon getMemberAccess() {
            return this.memberAccess_;
        }

        public Builder setMemberAccess(MemberAccessCommon memberAccessCommon) {
            memberAccessCommon.getClass();
            this.memberAccess_ = memberAccessCommon;
            this.bitField0_ |= 4;
            return this;
        }

        public Builder setMemberAccess(MemberAccessCommon.Builder builder) {
            this.memberAccess_ = builder.build();
            this.bitField0_ |= 4;
            return this;
        }

        public Builder mergeMemberAccess(MemberAccessCommon memberAccessCommon) {
            if ((this.bitField0_ & 4) == 4 && this.memberAccess_ != MemberAccessCommon.getDefaultInstance()) {
                this.memberAccess_ = MemberAccessCommon.newBuilder(this.memberAccess_).mergeFrom(memberAccessCommon).buildPartial();
            } else {
                this.memberAccess_ = memberAccessCommon;
            }
            this.bitField0_ |= 4;
            return this;
        }

        public Builder clearMemberAccess() {
            this.memberAccess_ = MemberAccessCommon.getDefaultInstance();
            this.bitField0_ &= -5;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
        public boolean hasOriginName() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCallOrBuilder
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
    }
}
