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
public final class IrCall extends GeneratedMessageLite implements IrCallOrBuilder {
    public static final int MEMBER_ACCESS_FIELD_NUMBER = 2;
    public static final int ORIGIN_NAME_FIELD_NUMBER = 4;
    public static Parser<IrCall> PARSER = new AbstractParser<IrCall>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrCall.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrCall parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrCall(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int SUPER_FIELD_NUMBER = 3;
    public static final int SYMBOL_FIELD_NUMBER = 1;
    private static final IrCall defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private MemberAccessCommon memberAccess_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int originName_;
    private long super_;
    private long symbol_;
    private final ByteString unknownFields;

    private IrCall(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrCall(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrCall getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrCall getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrCall(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                            } else if (tag == 18) {
                                MemberAccessCommon.Builder builder = (this.bitField0_ & 2) == 2 ? this.memberAccess_.toBuilder() : null;
                                MemberAccessCommon memberAccessCommon = (MemberAccessCommon) codedInputStream.readMessage(MemberAccessCommon.PARSER, extensionRegistryLite);
                                this.memberAccess_ = memberAccessCommon;
                                if (builder != null) {
                                    builder.mergeFrom(memberAccessCommon);
                                    this.memberAccess_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 2;
                            } else if (tag == 24) {
                                this.bitField0_ |= 4;
                                this.super_ = codedInputStream.readInt64();
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
        IrCall irCall = new IrCall(true);
        defaultInstance = irCall;
        irCall.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrCall> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
    public boolean hasSymbol() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
    public long getSymbol() {
        return this.symbol_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
    public boolean hasMemberAccess() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
    public MemberAccessCommon getMemberAccess() {
        return this.memberAccess_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
    public boolean hasSuper() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
    public long getSuper() {
        return this.super_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
    public boolean hasOriginName() {
        return (this.bitField0_ & 8) == 8;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
    public int getOriginName() {
        return this.originName_;
    }

    private void initFields() {
        this.symbol_ = 0L;
        this.memberAccess_ = MemberAccessCommon.getDefaultInstance();
        this.super_ = 0L;
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
            codedOutputStream.writeMessage(2, this.memberAccess_);
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeInt64(3, this.super_);
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
            iComputeInt64Size += CodedOutputStream.computeMessageSize(2, this.memberAccess_);
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeInt64Size += CodedOutputStream.computeInt64Size(3, this.super_);
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

    public static IrCall parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrCall parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrCall parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrCall parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrCall parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrCall parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrCall parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrCall parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrCall parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrCall parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrCall irCall) {
        return newBuilder().mergeFrom(irCall);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrCall, Builder> implements IrCallOrBuilder {
        private int bitField0_;
        private MemberAccessCommon memberAccess_ = MemberAccessCommon.getDefaultInstance();
        private int originName_;
        private long super_;
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
            this.bitField0_ &= -2;
            this.memberAccess_ = MemberAccessCommon.getDefaultInstance();
            int i = this.bitField0_;
            this.super_ = 0L;
            this.originName_ = 0;
            this.bitField0_ = i & (-15);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrCall getDefaultInstanceForType() {
            return IrCall.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrCall build() {
            IrCall irCallBuildPartial = buildPartial();
            if (irCallBuildPartial.isInitialized()) {
                return irCallBuildPartial;
            }
            throw newUninitializedMessageException(irCallBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrCall buildPartial() {
            IrCall irCall = new IrCall(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irCall.symbol_ = this.symbol_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irCall.memberAccess_ = this.memberAccess_;
            if ((i & 4) == 4) {
                i2 |= 4;
            }
            irCall.super_ = this.super_;
            if ((i & 8) == 8) {
                i2 |= 8;
            }
            irCall.originName_ = this.originName_;
            irCall.bitField0_ = i2;
            return irCall;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrCall irCall) {
            if (irCall == IrCall.getDefaultInstance()) {
                return this;
            }
            if (irCall.hasSymbol()) {
                setSymbol(irCall.getSymbol());
            }
            if (irCall.hasMemberAccess()) {
                mergeMemberAccess(irCall.getMemberAccess());
            }
            if (irCall.hasSuper()) {
                setSuper(irCall.getSuper());
            }
            if (irCall.hasOriginName()) {
                setOriginName(irCall.getOriginName());
            }
            setUnknownFields(getUnknownFields().concat(irCall.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasSymbol() && hasMemberAccess() && getMemberAccess().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrCall irCall = null;
            try {
                try {
                    IrCall partialFrom = IrCall.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrCall irCall2 = (IrCall) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irCall = irCall2;
                        if (irCall != null) {
                            mergeFrom(irCall);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irCall != null) {
                    mergeFrom(irCall);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
        public boolean hasSymbol() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
        public boolean hasMemberAccess() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
        public MemberAccessCommon getMemberAccess() {
            return this.memberAccess_;
        }

        public Builder setMemberAccess(MemberAccessCommon memberAccessCommon) {
            memberAccessCommon.getClass();
            this.memberAccess_ = memberAccessCommon;
            this.bitField0_ |= 2;
            return this;
        }

        public Builder setMemberAccess(MemberAccessCommon.Builder builder) {
            this.memberAccess_ = builder.build();
            this.bitField0_ |= 2;
            return this;
        }

        public Builder mergeMemberAccess(MemberAccessCommon memberAccessCommon) {
            if ((this.bitField0_ & 2) == 2 && this.memberAccess_ != MemberAccessCommon.getDefaultInstance()) {
                this.memberAccess_ = MemberAccessCommon.newBuilder(this.memberAccess_).mergeFrom(memberAccessCommon).buildPartial();
            } else {
                this.memberAccess_ = memberAccessCommon;
            }
            this.bitField0_ |= 2;
            return this;
        }

        public Builder clearMemberAccess() {
            this.memberAccess_ = MemberAccessCommon.getDefaultInstance();
            this.bitField0_ &= -3;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
        public boolean hasSuper() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
        public long getSuper() {
            return this.super_;
        }

        public Builder setSuper(long j) {
            this.bitField0_ |= 4;
            this.super_ = j;
            return this;
        }

        public Builder clearSuper() {
            this.bitField0_ &= -5;
            this.super_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
        public boolean hasOriginName() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrCallOrBuilder
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
