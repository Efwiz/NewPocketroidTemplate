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
import org.jetbrains.kotlin.protobuf.Internal;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.protobuf.Parser;

/* JADX INFO: loaded from: classes2.dex */
public final class IrDynamicOperatorExpression extends GeneratedMessageLite implements IrDynamicOperatorExpressionOrBuilder {
    public static final int ARGUMENT_FIELD_NUMBER = 3;
    public static final int OPERATOR_FIELD_NUMBER = 1;
    public static Parser<IrDynamicOperatorExpression> PARSER = new AbstractParser<IrDynamicOperatorExpression>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpression.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrDynamicOperatorExpression parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrDynamicOperatorExpression(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int RECEIVER_FIELD_NUMBER = 2;
    private static final IrDynamicOperatorExpression defaultInstance;
    private static final long serialVersionUID = 0;
    private List<IrExpression> argument_;
    private int bitField0_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private IrDynamicOperator operator_;
    private IrExpression receiver_;
    private final ByteString unknownFields;

    private IrDynamicOperatorExpression(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrDynamicOperatorExpression(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrDynamicOperatorExpression getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrDynamicOperatorExpression getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrDynamicOperatorExpression(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                            int i = codedInputStream.readEnum();
                            IrDynamicOperator irDynamicOperatorValueOf = IrDynamicOperator.valueOf(i);
                            if (irDynamicOperatorValueOf == null) {
                                codedOutputStreamNewInstance.writeRawVarint32(tag);
                                codedOutputStreamNewInstance.writeRawVarint32(i);
                            } else {
                                this.bitField0_ |= 1;
                                this.operator_ = irDynamicOperatorValueOf;
                            }
                        } else if (tag == 18) {
                            IrExpression.Builder builder = (this.bitField0_ & 2) == 2 ? this.receiver_.toBuilder() : null;
                            IrExpression irExpression = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                            this.receiver_ = irExpression;
                            if (builder != null) {
                                builder.mergeFrom(irExpression);
                                this.receiver_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 2;
                        } else if (tag != 26) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            if ((c & 4) != 4) {
                                this.argument_ = new ArrayList();
                                c = 4;
                            }
                            this.argument_.add((IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite));
                        }
                    }
                    z = true;
                } catch (Throwable th) {
                    if ((c & 4) == 4) {
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
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
            }
        }
        if ((c & 4) == 4) {
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
        IrDynamicOperatorExpression irDynamicOperatorExpression = new IrDynamicOperatorExpression(true);
        defaultInstance = irDynamicOperatorExpression;
        irDynamicOperatorExpression.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrDynamicOperatorExpression> getParserForType() {
        return PARSER;
    }

    public enum IrDynamicOperator implements Internal.EnumLite {
        UNARY_PLUS(0, 1),
        UNARY_MINUS(1, 2),
        EXCL(2, 3),
        PREFIX_INCREMENT(3, 4),
        POSTFIX_INCREMENT(4, 5),
        PREFIX_DECREMENT(5, 6),
        POSTFIX_DECREMENT(6, 7),
        BINARY_PLUS(7, 8),
        BINARY_MINUS(8, 9),
        MUL(9, 10),
        DIV(10, 11),
        MOD(11, 12),
        GT(12, 13),
        LT(13, 14),
        GE(14, 15),
        LE(15, 16),
        EQEQ(16, 17),
        EXCLEQ(17, 18),
        EQEQEQ(18, 19),
        EXCLEQEQ(19, 20),
        ANDAND(20, 21),
        OROR(21, 22),
        EQ(22, 23),
        PLUSEQ(23, 24),
        MINUSEQ(24, 25),
        MULEQ(25, 26),
        DIVEQ(26, 27),
        MODEQ(27, 28),
        ARRAY_ACCESS(28, 29),
        INVOKE(29, 30);

        public static final int ANDAND_VALUE = 21;
        public static final int ARRAY_ACCESS_VALUE = 29;
        public static final int BINARY_MINUS_VALUE = 9;
        public static final int BINARY_PLUS_VALUE = 8;
        public static final int DIVEQ_VALUE = 27;
        public static final int DIV_VALUE = 11;
        public static final int EQEQEQ_VALUE = 19;
        public static final int EQEQ_VALUE = 17;
        public static final int EQ_VALUE = 23;
        public static final int EXCLEQEQ_VALUE = 20;
        public static final int EXCLEQ_VALUE = 18;
        public static final int EXCL_VALUE = 3;
        public static final int GE_VALUE = 15;
        public static final int GT_VALUE = 13;
        public static final int INVOKE_VALUE = 30;
        public static final int LE_VALUE = 16;
        public static final int LT_VALUE = 14;
        public static final int MINUSEQ_VALUE = 25;
        public static final int MODEQ_VALUE = 28;
        public static final int MOD_VALUE = 12;
        public static final int MULEQ_VALUE = 26;
        public static final int MUL_VALUE = 10;
        public static final int OROR_VALUE = 22;
        public static final int PLUSEQ_VALUE = 24;
        public static final int POSTFIX_DECREMENT_VALUE = 7;
        public static final int POSTFIX_INCREMENT_VALUE = 5;
        public static final int PREFIX_DECREMENT_VALUE = 6;
        public static final int PREFIX_INCREMENT_VALUE = 4;
        public static final int UNARY_MINUS_VALUE = 2;
        public static final int UNARY_PLUS_VALUE = 1;
        private static Internal.EnumLiteMap<IrDynamicOperator> internalValueMap = new Internal.EnumLiteMap<IrDynamicOperator>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpression.IrDynamicOperator.1
            @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLiteMap
            public IrDynamicOperator findValueByNumber(int i) {
                return IrDynamicOperator.valueOf(i);
            }
        };
        private final int value;

        @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLite
        public final int getNumber() {
            return this.value;
        }

        public static IrDynamicOperator valueOf(int i) {
            switch (i) {
                case 1:
                    return UNARY_PLUS;
                case 2:
                    return UNARY_MINUS;
                case 3:
                    return EXCL;
                case 4:
                    return PREFIX_INCREMENT;
                case 5:
                    return POSTFIX_INCREMENT;
                case 6:
                    return PREFIX_DECREMENT;
                case 7:
                    return POSTFIX_DECREMENT;
                case 8:
                    return BINARY_PLUS;
                case 9:
                    return BINARY_MINUS;
                case 10:
                    return MUL;
                case 11:
                    return DIV;
                case 12:
                    return MOD;
                case 13:
                    return GT;
                case 14:
                    return LT;
                case 15:
                    return GE;
                case 16:
                    return LE;
                case 17:
                    return EQEQ;
                case 18:
                    return EXCLEQ;
                case 19:
                    return EQEQEQ;
                case 20:
                    return EXCLEQEQ;
                case 21:
                    return ANDAND;
                case 22:
                    return OROR;
                case 23:
                    return EQ;
                case 24:
                    return PLUSEQ;
                case 25:
                    return MINUSEQ;
                case 26:
                    return MULEQ;
                case 27:
                    return DIVEQ;
                case 28:
                    return MODEQ;
                case 29:
                    return ARRAY_ACCESS;
                case 30:
                    return INVOKE;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<IrDynamicOperator> internalGetValueMap() {
            return internalValueMap;
        }

        IrDynamicOperator(int i, int i2) {
            this.value = i2;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpressionOrBuilder
    public boolean hasOperator() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpressionOrBuilder
    public IrDynamicOperator getOperator() {
        return this.operator_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpressionOrBuilder
    public boolean hasReceiver() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpressionOrBuilder
    public IrExpression getReceiver() {
        return this.receiver_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpressionOrBuilder
    public List<IrExpression> getArgumentList() {
        return this.argument_;
    }

    public List<? extends IrExpressionOrBuilder> getArgumentOrBuilderList() {
        return this.argument_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpressionOrBuilder
    public int getArgumentCount() {
        return this.argument_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpressionOrBuilder
    public IrExpression getArgument(int i) {
        return this.argument_.get(i);
    }

    public IrExpressionOrBuilder getArgumentOrBuilder(int i) {
        return this.argument_.get(i);
    }

    private void initFields() {
        this.operator_ = IrDynamicOperator.UNARY_PLUS;
        this.receiver_ = IrExpression.getDefaultInstance();
        this.argument_ = Collections.emptyList();
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
        if (!hasOperator()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!hasReceiver()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getReceiver().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        for (int i = 0; i < getArgumentCount(); i++) {
            if (!getArgument(i).isInitialized()) {
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
            codedOutputStream.writeEnum(1, this.operator_.getNumber());
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeMessage(2, this.receiver_);
        }
        for (int i = 0; i < this.argument_.size(); i++) {
            codedOutputStream.writeMessage(3, this.argument_.get(i));
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeEnumSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeEnumSize(1, this.operator_.getNumber()) : 0;
        if ((this.bitField0_ & 2) == 2) {
            iComputeEnumSize += CodedOutputStream.computeMessageSize(2, this.receiver_);
        }
        for (int i2 = 0; i2 < this.argument_.size(); i2++) {
            iComputeEnumSize += CodedOutputStream.computeMessageSize(3, this.argument_.get(i2));
        }
        int size = iComputeEnumSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrDynamicOperatorExpression parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrDynamicOperatorExpression parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrDynamicOperatorExpression parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrDynamicOperatorExpression parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrDynamicOperatorExpression parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrDynamicOperatorExpression parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrDynamicOperatorExpression parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrDynamicOperatorExpression parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrDynamicOperatorExpression parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrDynamicOperatorExpression parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrDynamicOperatorExpression irDynamicOperatorExpression) {
        return newBuilder().mergeFrom(irDynamicOperatorExpression);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrDynamicOperatorExpression, Builder> implements IrDynamicOperatorExpressionOrBuilder {
        private int bitField0_;
        private IrDynamicOperator operator_ = IrDynamicOperator.UNARY_PLUS;
        private IrExpression receiver_ = IrExpression.getDefaultInstance();
        private List<IrExpression> argument_ = Collections.emptyList();

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
            this.operator_ = IrDynamicOperator.UNARY_PLUS;
            this.bitField0_ &= -2;
            this.receiver_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -3;
            this.argument_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrDynamicOperatorExpression getDefaultInstanceForType() {
            return IrDynamicOperatorExpression.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrDynamicOperatorExpression build() {
            IrDynamicOperatorExpression irDynamicOperatorExpressionBuildPartial = buildPartial();
            if (irDynamicOperatorExpressionBuildPartial.isInitialized()) {
                return irDynamicOperatorExpressionBuildPartial;
            }
            throw newUninitializedMessageException(irDynamicOperatorExpressionBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrDynamicOperatorExpression buildPartial() {
            IrDynamicOperatorExpression irDynamicOperatorExpression = new IrDynamicOperatorExpression(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irDynamicOperatorExpression.operator_ = this.operator_;
            if ((i & 2) == 2) {
                i2 |= 2;
            }
            irDynamicOperatorExpression.receiver_ = this.receiver_;
            if ((this.bitField0_ & 4) == 4) {
                this.argument_ = Collections.unmodifiableList(this.argument_);
                this.bitField0_ &= -5;
            }
            irDynamicOperatorExpression.argument_ = this.argument_;
            irDynamicOperatorExpression.bitField0_ = i2;
            return irDynamicOperatorExpression;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrDynamicOperatorExpression irDynamicOperatorExpression) {
            if (irDynamicOperatorExpression == IrDynamicOperatorExpression.getDefaultInstance()) {
                return this;
            }
            if (irDynamicOperatorExpression.hasOperator()) {
                setOperator(irDynamicOperatorExpression.getOperator());
            }
            if (irDynamicOperatorExpression.hasReceiver()) {
                mergeReceiver(irDynamicOperatorExpression.getReceiver());
            }
            if (!irDynamicOperatorExpression.argument_.isEmpty()) {
                if (this.argument_.isEmpty()) {
                    this.argument_ = irDynamicOperatorExpression.argument_;
                    this.bitField0_ &= -5;
                } else {
                    ensureArgumentIsMutable();
                    this.argument_.addAll(irDynamicOperatorExpression.argument_);
                }
            }
            setUnknownFields(getUnknownFields().concat(irDynamicOperatorExpression.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (!hasOperator() || !hasReceiver() || !getReceiver().isInitialized()) {
                return false;
            }
            for (int i = 0; i < getArgumentCount(); i++) {
                if (!getArgument(i).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrDynamicOperatorExpression irDynamicOperatorExpression = null;
            try {
                try {
                    IrDynamicOperatorExpression partialFrom = IrDynamicOperatorExpression.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrDynamicOperatorExpression irDynamicOperatorExpression2 = (IrDynamicOperatorExpression) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irDynamicOperatorExpression = irDynamicOperatorExpression2;
                        if (irDynamicOperatorExpression != null) {
                            mergeFrom(irDynamicOperatorExpression);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irDynamicOperatorExpression != null) {
                    mergeFrom(irDynamicOperatorExpression);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpressionOrBuilder
        public boolean hasOperator() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpressionOrBuilder
        public IrDynamicOperator getOperator() {
            return this.operator_;
        }

        public Builder setOperator(IrDynamicOperator irDynamicOperator) {
            irDynamicOperator.getClass();
            this.bitField0_ |= 1;
            this.operator_ = irDynamicOperator;
            return this;
        }

        public Builder clearOperator() {
            this.bitField0_ &= -2;
            this.operator_ = IrDynamicOperator.UNARY_PLUS;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpressionOrBuilder
        public boolean hasReceiver() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpressionOrBuilder
        public IrExpression getReceiver() {
            return this.receiver_;
        }

        public Builder setReceiver(IrExpression irExpression) {
            irExpression.getClass();
            this.receiver_ = irExpression;
            this.bitField0_ |= 2;
            return this;
        }

        public Builder setReceiver(IrExpression.Builder builder) {
            this.receiver_ = builder.build();
            this.bitField0_ |= 2;
            return this;
        }

        public Builder mergeReceiver(IrExpression irExpression) {
            if ((this.bitField0_ & 2) == 2 && this.receiver_ != IrExpression.getDefaultInstance()) {
                this.receiver_ = IrExpression.newBuilder(this.receiver_).mergeFrom(irExpression).buildPartial();
            } else {
                this.receiver_ = irExpression;
            }
            this.bitField0_ |= 2;
            return this;
        }

        public Builder clearReceiver() {
            this.receiver_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -3;
            return this;
        }

        private void ensureArgumentIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
                this.argument_ = new ArrayList(this.argument_);
                this.bitField0_ |= 4;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpressionOrBuilder
        public List<IrExpression> getArgumentList() {
            return Collections.unmodifiableList(this.argument_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpressionOrBuilder
        public int getArgumentCount() {
            return this.argument_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpressionOrBuilder
        public IrExpression getArgument(int i) {
            return this.argument_.get(i);
        }

        public Builder setArgument(int i, IrExpression irExpression) {
            irExpression.getClass();
            ensureArgumentIsMutable();
            this.argument_.set(i, irExpression);
            return this;
        }

        public Builder setArgument(int i, IrExpression.Builder builder) {
            ensureArgumentIsMutable();
            this.argument_.set(i, builder.build());
            return this;
        }

        public Builder addArgument(IrExpression irExpression) {
            irExpression.getClass();
            ensureArgumentIsMutable();
            this.argument_.add(irExpression);
            return this;
        }

        public Builder addArgument(int i, IrExpression irExpression) {
            irExpression.getClass();
            ensureArgumentIsMutable();
            this.argument_.add(i, irExpression);
            return this;
        }

        public Builder addArgument(IrExpression.Builder builder) {
            ensureArgumentIsMutable();
            this.argument_.add(builder.build());
            return this;
        }

        public Builder addArgument(int i, IrExpression.Builder builder) {
            ensureArgumentIsMutable();
            this.argument_.add(i, builder.build());
            return this;
        }

        public Builder addAllArgument(Iterable<? extends IrExpression> iterable) {
            ensureArgumentIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.argument_);
            return this;
        }

        public Builder clearArgument() {
            this.argument_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
        }

        public Builder removeArgument(int i) {
            ensureArgumentIsMutable();
            this.argument_.remove(i);
            return this;
        }
    }
}
