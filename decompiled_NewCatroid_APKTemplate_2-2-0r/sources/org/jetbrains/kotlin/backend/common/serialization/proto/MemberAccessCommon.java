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
public final class MemberAccessCommon extends GeneratedMessageLite implements MemberAccessCommonOrBuilder {
    public static final int DISPATCH_RECEIVER_FIELD_NUMBER = 1;
    public static final int EXTENSION_RECEIVER_FIELD_NUMBER = 2;
    public static Parser<MemberAccessCommon> PARSER = new AbstractParser<MemberAccessCommon>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommon.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public MemberAccessCommon parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new MemberAccessCommon(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int TYPE_ARGUMENT_FIELD_NUMBER = 4;
    public static final int VALUE_ARGUMENT_FIELD_NUMBER = 3;
    private static final MemberAccessCommon defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private IrExpression dispatchReceiver_;
    private IrExpression extensionReceiver_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int typeArgumentMemoizedSerializedSize;
    private List<Integer> typeArgument_;
    private final ByteString unknownFields;
    private List<NullableIrExpression> valueArgument_;

    private MemberAccessCommon(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.typeArgumentMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private MemberAccessCommon(boolean z) {
        this.typeArgumentMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static MemberAccessCommon getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public MemberAccessCommon getDefaultInstanceForType() {
        return defaultInstance;
    }

    private MemberAccessCommon(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        IrExpression.Builder builder;
        this.typeArgumentMemoizedSerializedSize = -1;
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
                    int tag = codedInputStream.readTag();
                    if (tag != 0) {
                        if (tag == 10) {
                            builder = (this.bitField0_ & 1) == 1 ? this.dispatchReceiver_.toBuilder() : null;
                            IrExpression irExpression = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                            this.dispatchReceiver_ = irExpression;
                            if (builder != null) {
                                builder.mergeFrom(irExpression);
                                this.dispatchReceiver_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                        } else if (tag == 18) {
                            builder = (this.bitField0_ & 2) == 2 ? this.extensionReceiver_.toBuilder() : null;
                            IrExpression irExpression2 = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                            this.extensionReceiver_ = irExpression2;
                            if (builder != null) {
                                builder.mergeFrom(irExpression2);
                                this.extensionReceiver_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 2;
                        } else if (tag == 26) {
                            if ((i & 4) != 4) {
                                this.valueArgument_ = new ArrayList();
                                i |= 4;
                            }
                            this.valueArgument_.add((NullableIrExpression) codedInputStream.readMessage(NullableIrExpression.PARSER, extensionRegistryLite));
                        } else if (tag == 32) {
                            if ((i & 8) != 8) {
                                this.typeArgument_ = new ArrayList();
                                i |= 8;
                            }
                            this.typeArgument_.add(Integer.valueOf(codedInputStream.readInt32()));
                        } else if (tag != 34) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                            if ((i & 8) != 8 && codedInputStream.getBytesUntilLimit() > 0) {
                                this.typeArgument_ = new ArrayList();
                                i |= 8;
                            }
                            while (codedInputStream.getBytesUntilLimit() > 0) {
                                this.typeArgument_.add(Integer.valueOf(codedInputStream.readInt32()));
                            }
                            codedInputStream.popLimit(iPushLimit);
                        }
                    }
                    z = true;
                } catch (Throwable th) {
                    if ((i & 4) == 4) {
                        this.valueArgument_ = Collections.unmodifiableList(this.valueArgument_);
                    }
                    if ((i & 8) == 8) {
                        this.typeArgument_ = Collections.unmodifiableList(this.typeArgument_);
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
        if ((i & 4) == 4) {
            this.valueArgument_ = Collections.unmodifiableList(this.valueArgument_);
        }
        if ((i & 8) == 8) {
            this.typeArgument_ = Collections.unmodifiableList(this.typeArgument_);
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
        MemberAccessCommon memberAccessCommon = new MemberAccessCommon(true);
        defaultInstance = memberAccessCommon;
        memberAccessCommon.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<MemberAccessCommon> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
    public boolean hasDispatchReceiver() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
    public IrExpression getDispatchReceiver() {
        return this.dispatchReceiver_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
    public boolean hasExtensionReceiver() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
    public IrExpression getExtensionReceiver() {
        return this.extensionReceiver_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
    public List<NullableIrExpression> getValueArgumentList() {
        return this.valueArgument_;
    }

    public List<? extends NullableIrExpressionOrBuilder> getValueArgumentOrBuilderList() {
        return this.valueArgument_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
    public int getValueArgumentCount() {
        return this.valueArgument_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
    public NullableIrExpression getValueArgument(int i) {
        return this.valueArgument_.get(i);
    }

    public NullableIrExpressionOrBuilder getValueArgumentOrBuilder(int i) {
        return this.valueArgument_.get(i);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
    public List<Integer> getTypeArgumentList() {
        return this.typeArgument_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
    public int getTypeArgumentCount() {
        return this.typeArgument_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
    public int getTypeArgument(int i) {
        return this.typeArgument_.get(i).intValue();
    }

    private void initFields() {
        this.dispatchReceiver_ = IrExpression.getDefaultInstance();
        this.extensionReceiver_ = IrExpression.getDefaultInstance();
        this.valueArgument_ = Collections.emptyList();
        this.typeArgument_ = Collections.emptyList();
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
        if (hasDispatchReceiver() && !getDispatchReceiver().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasExtensionReceiver() && !getExtensionReceiver().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        for (int i = 0; i < getValueArgumentCount(); i++) {
            if (!getValueArgument(i).isInitialized()) {
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
            codedOutputStream.writeMessage(1, this.dispatchReceiver_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeMessage(2, this.extensionReceiver_);
        }
        for (int i = 0; i < this.valueArgument_.size(); i++) {
            codedOutputStream.writeMessage(3, this.valueArgument_.get(i));
        }
        if (getTypeArgumentList().size() > 0) {
            codedOutputStream.writeRawVarint32(34);
            codedOutputStream.writeRawVarint32(this.typeArgumentMemoizedSerializedSize);
        }
        for (int i2 = 0; i2 < this.typeArgument_.size(); i2++) {
            codedOutputStream.writeInt32NoTag(this.typeArgument_.get(i2).intValue());
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeMessageSize(1, this.dispatchReceiver_) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(2, this.extensionReceiver_);
        }
        for (int i2 = 0; i2 < this.valueArgument_.size(); i2++) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(3, this.valueArgument_.get(i2));
        }
        int iComputeInt32SizeNoTag = 0;
        for (int i3 = 0; i3 < this.typeArgument_.size(); i3++) {
            iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.typeArgument_.get(i3).intValue());
        }
        int iComputeInt32SizeNoTag2 = iComputeMessageSize + iComputeInt32SizeNoTag;
        if (!getTypeArgumentList().isEmpty()) {
            iComputeInt32SizeNoTag2 = iComputeInt32SizeNoTag2 + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag);
        }
        this.typeArgumentMemoizedSerializedSize = iComputeInt32SizeNoTag;
        int size = iComputeInt32SizeNoTag2 + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static MemberAccessCommon parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static MemberAccessCommon parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static MemberAccessCommon parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static MemberAccessCommon parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static MemberAccessCommon parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static MemberAccessCommon parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static MemberAccessCommon parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static MemberAccessCommon parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static MemberAccessCommon parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static MemberAccessCommon parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(MemberAccessCommon memberAccessCommon) {
        return newBuilder().mergeFrom(memberAccessCommon);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<MemberAccessCommon, Builder> implements MemberAccessCommonOrBuilder {
        private int bitField0_;
        private IrExpression dispatchReceiver_ = IrExpression.getDefaultInstance();
        private IrExpression extensionReceiver_ = IrExpression.getDefaultInstance();
        private List<NullableIrExpression> valueArgument_ = Collections.emptyList();
        private List<Integer> typeArgument_ = Collections.emptyList();

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
            this.dispatchReceiver_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -2;
            this.extensionReceiver_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -3;
            this.valueArgument_ = Collections.emptyList();
            this.bitField0_ &= -5;
            this.typeArgument_ = Collections.emptyList();
            this.bitField0_ &= -9;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public MemberAccessCommon getDefaultInstanceForType() {
            return MemberAccessCommon.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public MemberAccessCommon build() {
            MemberAccessCommon memberAccessCommonBuildPartial = buildPartial();
            if (memberAccessCommonBuildPartial.isInitialized()) {
                return memberAccessCommonBuildPartial;
            }
            throw newUninitializedMessageException(memberAccessCommonBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public MemberAccessCommon buildPartial() {
            MemberAccessCommon memberAccessCommon = new MemberAccessCommon(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            memberAccessCommon.dispatchReceiver_ = this.dispatchReceiver_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            memberAccessCommon.extensionReceiver_ = this.extensionReceiver_;
            if ((this.bitField0_ & 4) == 4) {
                this.valueArgument_ = Collections.unmodifiableList(this.valueArgument_);
                this.bitField0_ &= -5;
            }
            memberAccessCommon.valueArgument_ = this.valueArgument_;
            if ((this.bitField0_ & 8) == 8) {
                this.typeArgument_ = Collections.unmodifiableList(this.typeArgument_);
                this.bitField0_ &= -9;
            }
            memberAccessCommon.typeArgument_ = this.typeArgument_;
            memberAccessCommon.bitField0_ = i2;
            return memberAccessCommon;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(MemberAccessCommon memberAccessCommon) {
            if (memberAccessCommon == MemberAccessCommon.getDefaultInstance()) {
                return this;
            }
            if (memberAccessCommon.hasDispatchReceiver()) {
                mergeDispatchReceiver(memberAccessCommon.getDispatchReceiver());
            }
            if (memberAccessCommon.hasExtensionReceiver()) {
                mergeExtensionReceiver(memberAccessCommon.getExtensionReceiver());
            }
            if (!memberAccessCommon.valueArgument_.isEmpty()) {
                if (this.valueArgument_.isEmpty()) {
                    this.valueArgument_ = memberAccessCommon.valueArgument_;
                    this.bitField0_ &= -5;
                } else {
                    ensureValueArgumentIsMutable();
                    this.valueArgument_.addAll(memberAccessCommon.valueArgument_);
                }
            }
            if (!memberAccessCommon.typeArgument_.isEmpty()) {
                if (this.typeArgument_.isEmpty()) {
                    this.typeArgument_ = memberAccessCommon.typeArgument_;
                    this.bitField0_ &= -9;
                } else {
                    ensureTypeArgumentIsMutable();
                    this.typeArgument_.addAll(memberAccessCommon.typeArgument_);
                }
            }
            setUnknownFields(getUnknownFields().concat(memberAccessCommon.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (hasDispatchReceiver() && !getDispatchReceiver().isInitialized()) {
                return false;
            }
            if (hasExtensionReceiver() && !getExtensionReceiver().isInitialized()) {
                return false;
            }
            for (int i = 0; i < getValueArgumentCount(); i++) {
                if (!getValueArgument(i).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            MemberAccessCommon memberAccessCommon = null;
            try {
                try {
                    MemberAccessCommon partialFrom = MemberAccessCommon.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    MemberAccessCommon memberAccessCommon2 = (MemberAccessCommon) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        memberAccessCommon = memberAccessCommon2;
                        if (memberAccessCommon != null) {
                            mergeFrom(memberAccessCommon);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (memberAccessCommon != null) {
                    mergeFrom(memberAccessCommon);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
        public boolean hasDispatchReceiver() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
        public IrExpression getDispatchReceiver() {
            return this.dispatchReceiver_;
        }

        public Builder setDispatchReceiver(IrExpression irExpression) {
            irExpression.getClass();
            this.dispatchReceiver_ = irExpression;
            this.bitField0_ |= 1;
            return this;
        }

        public Builder setDispatchReceiver(IrExpression.Builder builder) {
            this.dispatchReceiver_ = builder.build();
            this.bitField0_ |= 1;
            return this;
        }

        public Builder mergeDispatchReceiver(IrExpression irExpression) {
            if ((this.bitField0_ & 1) == 1 && this.dispatchReceiver_ != IrExpression.getDefaultInstance()) {
                this.dispatchReceiver_ = IrExpression.newBuilder(this.dispatchReceiver_).mergeFrom(irExpression).buildPartial();
            } else {
                this.dispatchReceiver_ = irExpression;
            }
            this.bitField0_ |= 1;
            return this;
        }

        public Builder clearDispatchReceiver() {
            this.dispatchReceiver_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
        public boolean hasExtensionReceiver() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
        public IrExpression getExtensionReceiver() {
            return this.extensionReceiver_;
        }

        public Builder setExtensionReceiver(IrExpression irExpression) {
            irExpression.getClass();
            this.extensionReceiver_ = irExpression;
            this.bitField0_ |= 2;
            return this;
        }

        public Builder setExtensionReceiver(IrExpression.Builder builder) {
            this.extensionReceiver_ = builder.build();
            this.bitField0_ |= 2;
            return this;
        }

        public Builder mergeExtensionReceiver(IrExpression irExpression) {
            if ((this.bitField0_ & 2) == 2 && this.extensionReceiver_ != IrExpression.getDefaultInstance()) {
                this.extensionReceiver_ = IrExpression.newBuilder(this.extensionReceiver_).mergeFrom(irExpression).buildPartial();
            } else {
                this.extensionReceiver_ = irExpression;
            }
            this.bitField0_ |= 2;
            return this;
        }

        public Builder clearExtensionReceiver() {
            this.extensionReceiver_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -3;
            return this;
        }

        private void ensureValueArgumentIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
                this.valueArgument_ = new ArrayList(this.valueArgument_);
                this.bitField0_ |= 4;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
        public List<NullableIrExpression> getValueArgumentList() {
            return Collections.unmodifiableList(this.valueArgument_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
        public int getValueArgumentCount() {
            return this.valueArgument_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
        public NullableIrExpression getValueArgument(int i) {
            return this.valueArgument_.get(i);
        }

        public Builder setValueArgument(int i, NullableIrExpression nullableIrExpression) {
            nullableIrExpression.getClass();
            ensureValueArgumentIsMutable();
            this.valueArgument_.set(i, nullableIrExpression);
            return this;
        }

        public Builder setValueArgument(int i, NullableIrExpression.Builder builder) {
            ensureValueArgumentIsMutable();
            this.valueArgument_.set(i, builder.build());
            return this;
        }

        public Builder addValueArgument(NullableIrExpression nullableIrExpression) {
            nullableIrExpression.getClass();
            ensureValueArgumentIsMutable();
            this.valueArgument_.add(nullableIrExpression);
            return this;
        }

        public Builder addValueArgument(int i, NullableIrExpression nullableIrExpression) {
            nullableIrExpression.getClass();
            ensureValueArgumentIsMutable();
            this.valueArgument_.add(i, nullableIrExpression);
            return this;
        }

        public Builder addValueArgument(NullableIrExpression.Builder builder) {
            ensureValueArgumentIsMutable();
            this.valueArgument_.add(builder.build());
            return this;
        }

        public Builder addValueArgument(int i, NullableIrExpression.Builder builder) {
            ensureValueArgumentIsMutable();
            this.valueArgument_.add(i, builder.build());
            return this;
        }

        public Builder addAllValueArgument(Iterable<? extends NullableIrExpression> iterable) {
            ensureValueArgumentIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.valueArgument_);
            return this;
        }

        public Builder clearValueArgument() {
            this.valueArgument_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
        }

        public Builder removeValueArgument(int i) {
            ensureValueArgumentIsMutable();
            this.valueArgument_.remove(i);
            return this;
        }

        private void ensureTypeArgumentIsMutable() {
            if ((this.bitField0_ & 8) != 8) {
                this.typeArgument_ = new ArrayList(this.typeArgument_);
                this.bitField0_ |= 8;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
        public List<Integer> getTypeArgumentList() {
            return Collections.unmodifiableList(this.typeArgument_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
        public int getTypeArgumentCount() {
            return this.typeArgument_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommonOrBuilder
        public int getTypeArgument(int i) {
            return this.typeArgument_.get(i).intValue();
        }

        public Builder setTypeArgument(int i, int i2) {
            ensureTypeArgumentIsMutable();
            this.typeArgument_.set(i, Integer.valueOf(i2));
            return this;
        }

        public Builder addTypeArgument(int i) {
            ensureTypeArgumentIsMutable();
            this.typeArgument_.add(Integer.valueOf(i));
            return this;
        }

        public Builder addAllTypeArgument(Iterable<? extends Integer> iterable) {
            ensureTypeArgumentIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.typeArgument_);
            return this;
        }

        public Builder clearTypeArgument() {
            this.typeArgument_ = Collections.emptyList();
            this.bitField0_ &= -9;
            return this;
        }
    }
}
