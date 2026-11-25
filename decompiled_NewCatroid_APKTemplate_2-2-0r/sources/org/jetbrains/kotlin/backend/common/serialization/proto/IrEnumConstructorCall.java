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
public final class IrEnumConstructorCall extends GeneratedMessageLite implements IrEnumConstructorCallOrBuilder {
    public static final int MEMBER_ACCESS_FIELD_NUMBER = 2;
    public static Parser<IrEnumConstructorCall> PARSER = new AbstractParser<IrEnumConstructorCall>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumConstructorCall.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrEnumConstructorCall parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrEnumConstructorCall(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int SYMBOL_FIELD_NUMBER = 1;
    private static final IrEnumConstructorCall defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private MemberAccessCommon memberAccess_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private long symbol_;
    private final ByteString unknownFields;

    private IrEnumConstructorCall(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrEnumConstructorCall(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrEnumConstructorCall getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrEnumConstructorCall getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrEnumConstructorCall(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                            } else if (tag != 18) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                MemberAccessCommon.Builder builder = (this.bitField0_ & 2) == 2 ? this.memberAccess_.toBuilder() : null;
                                MemberAccessCommon memberAccessCommon = (MemberAccessCommon) codedInputStream.readMessage(MemberAccessCommon.PARSER, extensionRegistryLite);
                                this.memberAccess_ = memberAccessCommon;
                                if (builder != null) {
                                    builder.mergeFrom(memberAccessCommon);
                                    this.memberAccess_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 2;
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
        IrEnumConstructorCall irEnumConstructorCall = new IrEnumConstructorCall(true);
        defaultInstance = irEnumConstructorCall;
        irEnumConstructorCall.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrEnumConstructorCall> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumConstructorCallOrBuilder
    public boolean hasSymbol() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumConstructorCallOrBuilder
    public long getSymbol() {
        return this.symbol_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumConstructorCallOrBuilder
    public boolean hasMemberAccess() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumConstructorCallOrBuilder
    public MemberAccessCommon getMemberAccess() {
        return this.memberAccess_;
    }

    private void initFields() {
        this.symbol_ = 0L;
        this.memberAccess_ = MemberAccessCommon.getDefaultInstance();
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
        int size = iComputeInt64Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrEnumConstructorCall parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrEnumConstructorCall parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrEnumConstructorCall parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrEnumConstructorCall parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrEnumConstructorCall parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrEnumConstructorCall parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrEnumConstructorCall parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrEnumConstructorCall parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrEnumConstructorCall parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrEnumConstructorCall parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrEnumConstructorCall irEnumConstructorCall) {
        return newBuilder().mergeFrom(irEnumConstructorCall);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrEnumConstructorCall, Builder> implements IrEnumConstructorCallOrBuilder {
        private int bitField0_;
        private MemberAccessCommon memberAccess_ = MemberAccessCommon.getDefaultInstance();
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
            this.bitField0_ &= -3;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrEnumConstructorCall getDefaultInstanceForType() {
            return IrEnumConstructorCall.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrEnumConstructorCall build() {
            IrEnumConstructorCall irEnumConstructorCallBuildPartial = buildPartial();
            if (irEnumConstructorCallBuildPartial.isInitialized()) {
                return irEnumConstructorCallBuildPartial;
            }
            throw newUninitializedMessageException(irEnumConstructorCallBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrEnumConstructorCall buildPartial() {
            IrEnumConstructorCall irEnumConstructorCall = new IrEnumConstructorCall(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irEnumConstructorCall.symbol_ = this.symbol_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irEnumConstructorCall.memberAccess_ = this.memberAccess_;
            irEnumConstructorCall.bitField0_ = i2;
            return irEnumConstructorCall;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrEnumConstructorCall irEnumConstructorCall) {
            if (irEnumConstructorCall == IrEnumConstructorCall.getDefaultInstance()) {
                return this;
            }
            if (irEnumConstructorCall.hasSymbol()) {
                setSymbol(irEnumConstructorCall.getSymbol());
            }
            if (irEnumConstructorCall.hasMemberAccess()) {
                mergeMemberAccess(irEnumConstructorCall.getMemberAccess());
            }
            setUnknownFields(getUnknownFields().concat(irEnumConstructorCall.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasSymbol() && hasMemberAccess() && getMemberAccess().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrEnumConstructorCall irEnumConstructorCall = null;
            try {
                try {
                    IrEnumConstructorCall partialFrom = IrEnumConstructorCall.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrEnumConstructorCall irEnumConstructorCall2 = (IrEnumConstructorCall) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irEnumConstructorCall = irEnumConstructorCall2;
                        if (irEnumConstructorCall != null) {
                            mergeFrom(irEnumConstructorCall);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irEnumConstructorCall != null) {
                    mergeFrom(irEnumConstructorCall);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumConstructorCallOrBuilder
        public boolean hasSymbol() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumConstructorCallOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumConstructorCallOrBuilder
        public boolean hasMemberAccess() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumConstructorCallOrBuilder
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
    }
}
