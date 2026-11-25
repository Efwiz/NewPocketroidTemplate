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
import org.jetbrains.kotlin.protobuf.Internal;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.protobuf.Parser;

/* JADX INFO: loaded from: classes2.dex */
public final class IrType extends GeneratedMessageLite implements IrTypeOrBuilder {
    public static final int DNN_FIELD_NUMBER = 4;
    public static final int DYNAMIC_FIELD_NUMBER = 2;
    public static final int ERROR_FIELD_NUMBER = 3;
    public static final int LEGACYSIMPLE_FIELD_NUMBER = 1;
    public static Parser<IrType> PARSER = new AbstractParser<IrType>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrType.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrType parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrType(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int SIMPLE_FIELD_NUMBER = 5;
    private static final IrType defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private int kindCase_;
    private Object kind_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    private void initFields() {
    }

    private IrType(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.kindCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrType(boolean z) {
        this.kindCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrType getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrType getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrType(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        GeneratedMessageLite.Builder builder;
        boolean z = false;
        this.kindCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        initFields();
        ByteString.Output outputNewOutput = ByteString.newOutput();
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
        while (!z) {
            try {
                try {
                    int tag = codedInputStream.readTag();
                    if (tag != 0) {
                        if (tag == 10) {
                            builder = this.kindCase_ == 1 ? ((IrSimpleTypeLegacy) this.kind_).toBuilder() : null;
                            MessageLite message = codedInputStream.readMessage(IrSimpleTypeLegacy.PARSER, extensionRegistryLite);
                            this.kind_ = message;
                            if (builder != null) {
                                builder.mergeFrom((IrSimpleTypeLegacy) message);
                                this.kind_ = builder.buildPartial();
                            }
                            this.kindCase_ = 1;
                        } else if (tag == 18) {
                            builder = this.kindCase_ == 2 ? ((IrDynamicType) this.kind_).toBuilder() : null;
                            MessageLite message2 = codedInputStream.readMessage(IrDynamicType.PARSER, extensionRegistryLite);
                            this.kind_ = message2;
                            if (builder != null) {
                                builder.mergeFrom((IrDynamicType) message2);
                                this.kind_ = builder.buildPartial();
                            }
                            this.kindCase_ = 2;
                        } else if (tag == 26) {
                            builder = this.kindCase_ == 3 ? ((IrErrorType) this.kind_).toBuilder() : null;
                            MessageLite message3 = codedInputStream.readMessage(IrErrorType.PARSER, extensionRegistryLite);
                            this.kind_ = message3;
                            if (builder != null) {
                                builder.mergeFrom((IrErrorType) message3);
                                this.kind_ = builder.buildPartial();
                            }
                            this.kindCase_ = 3;
                        } else if (tag == 34) {
                            builder = this.kindCase_ == 4 ? ((IrDefinitelyNotNullType) this.kind_).toBuilder() : null;
                            MessageLite message4 = codedInputStream.readMessage(IrDefinitelyNotNullType.PARSER, extensionRegistryLite);
                            this.kind_ = message4;
                            if (builder != null) {
                                builder.mergeFrom((IrDefinitelyNotNullType) message4);
                                this.kind_ = builder.buildPartial();
                            }
                            this.kindCase_ = 4;
                        } else if (tag != 42) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            builder = this.kindCase_ == 5 ? ((IrSimpleType) this.kind_).toBuilder() : null;
                            MessageLite message5 = codedInputStream.readMessage(IrSimpleType.PARSER, extensionRegistryLite);
                            this.kind_ = message5;
                            if (builder != null) {
                                builder.mergeFrom((IrSimpleType) message5);
                                this.kind_ = builder.buildPartial();
                            }
                            this.kindCase_ = 5;
                        }
                    }
                    z = true;
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
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
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
        IrType irType = new IrType(true);
        defaultInstance = irType;
        irType.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrType> getParserForType() {
        return PARSER;
    }

    public enum KindCase implements Internal.EnumLite {
        LEGACYSIMPLE(1),
        DYNAMIC(2),
        ERROR(3),
        DNN(4),
        SIMPLE(5),
        KIND_NOT_SET(0);

        private int value;

        KindCase(int i) {
            this.value = i;
        }

        public static KindCase valueOf(int i) {
            if (i == 0) {
                return KIND_NOT_SET;
            }
            if (i == 1) {
                return LEGACYSIMPLE;
            }
            if (i == 2) {
                return DYNAMIC;
            }
            if (i == 3) {
                return ERROR;
            }
            if (i == 4) {
                return DNN;
            }
            if (i == 5) {
                return SIMPLE;
            }
            throw new IllegalArgumentException("Value is undefined for this oneof enum.");
        }

        @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLite
        public int getNumber() {
            return this.value;
        }
    }

    public KindCase getKindCase() {
        return KindCase.valueOf(this.kindCase_);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
    public boolean hasLegacySimple() {
        return this.kindCase_ == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
    public IrSimpleTypeLegacy getLegacySimple() {
        if (this.kindCase_ == 1) {
            return (IrSimpleTypeLegacy) this.kind_;
        }
        return IrSimpleTypeLegacy.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
    public boolean hasDynamic() {
        return this.kindCase_ == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
    public IrDynamicType getDynamic() {
        if (this.kindCase_ == 2) {
            return (IrDynamicType) this.kind_;
        }
        return IrDynamicType.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
    public boolean hasError() {
        return this.kindCase_ == 3;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
    public IrErrorType getError() {
        if (this.kindCase_ == 3) {
            return (IrErrorType) this.kind_;
        }
        return IrErrorType.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
    public boolean hasDnn() {
        return this.kindCase_ == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
    public IrDefinitelyNotNullType getDnn() {
        if (this.kindCase_ == 4) {
            return (IrDefinitelyNotNullType) this.kind_;
        }
        return IrDefinitelyNotNullType.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
    public boolean hasSimple() {
        return this.kindCase_ == 5;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
    public IrSimpleType getSimple() {
        if (this.kindCase_ == 5) {
            return (IrSimpleType) this.kind_;
        }
        return IrSimpleType.getDefaultInstance();
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
        if (hasLegacySimple() && !getLegacySimple().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasDynamic() && !getDynamic().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasError() && !getError().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasSimple() && !getSimple().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        this.memoizedIsInitialized = (byte) 1;
        return true;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        getSerializedSize();
        if (this.kindCase_ == 1) {
            codedOutputStream.writeMessage(1, (IrSimpleTypeLegacy) this.kind_);
        }
        if (this.kindCase_ == 2) {
            codedOutputStream.writeMessage(2, (IrDynamicType) this.kind_);
        }
        if (this.kindCase_ == 3) {
            codedOutputStream.writeMessage(3, (IrErrorType) this.kind_);
        }
        if (this.kindCase_ == 4) {
            codedOutputStream.writeMessage(4, (IrDefinitelyNotNullType) this.kind_);
        }
        if (this.kindCase_ == 5) {
            codedOutputStream.writeMessage(5, (IrSimpleType) this.kind_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = this.kindCase_ == 1 ? CodedOutputStream.computeMessageSize(1, (IrSimpleTypeLegacy) this.kind_) : 0;
        if (this.kindCase_ == 2) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(2, (IrDynamicType) this.kind_);
        }
        if (this.kindCase_ == 3) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(3, (IrErrorType) this.kind_);
        }
        if (this.kindCase_ == 4) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(4, (IrDefinitelyNotNullType) this.kind_);
        }
        if (this.kindCase_ == 5) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(5, (IrSimpleType) this.kind_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrType parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrType parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrType parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrType parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrType parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrType parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrType parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrType parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrType parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrType parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrType irType) {
        return newBuilder().mergeFrom(irType);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrType, Builder> implements IrTypeOrBuilder {
        private int bitField0_;
        private int kindCase_ = 0;
        private Object kind_;

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
            this.kindCase_ = 0;
            this.kind_ = null;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrType getDefaultInstanceForType() {
            return IrType.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrType build() {
            IrType irTypeBuildPartial = buildPartial();
            if (irTypeBuildPartial.isInitialized()) {
                return irTypeBuildPartial;
            }
            throw newUninitializedMessageException(irTypeBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrType buildPartial() {
            IrType irType = new IrType(this);
            if (this.kindCase_ == 1) {
                irType.kind_ = this.kind_;
            }
            if (this.kindCase_ == 2) {
                irType.kind_ = this.kind_;
            }
            if (this.kindCase_ == 3) {
                irType.kind_ = this.kind_;
            }
            if (this.kindCase_ == 4) {
                irType.kind_ = this.kind_;
            }
            if (this.kindCase_ == 5) {
                irType.kind_ = this.kind_;
            }
            irType.bitField0_ = 0;
            irType.kindCase_ = this.kindCase_;
            return irType;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrType irType) {
            if (irType == IrType.getDefaultInstance()) {
                return this;
            }
            int i = AnonymousClass2.$SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrType$KindCase[irType.getKindCase().ordinal()];
            if (i == 1) {
                mergeLegacySimple(irType.getLegacySimple());
            } else if (i == 2) {
                mergeDynamic(irType.getDynamic());
            } else if (i == 3) {
                mergeError(irType.getError());
            } else if (i == 4) {
                mergeDnn(irType.getDnn());
            } else if (i == 5) {
                mergeSimple(irType.getSimple());
            }
            setUnknownFields(getUnknownFields().concat(irType.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (hasLegacySimple() && !getLegacySimple().isInitialized()) {
                return false;
            }
            if (hasDynamic() && !getDynamic().isInitialized()) {
                return false;
            }
            if (!hasError() || getError().isInitialized()) {
                return !hasSimple() || getSimple().isInitialized();
            }
            return false;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrType irType = null;
            try {
                try {
                    IrType partialFrom = IrType.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrType irType2 = (IrType) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irType = irType2;
                        if (irType != null) {
                            mergeFrom(irType);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irType != null) {
                    mergeFrom(irType);
                }
                throw th;
            }
        }

        public KindCase getKindCase() {
            return KindCase.valueOf(this.kindCase_);
        }

        public Builder clearKind() {
            this.kindCase_ = 0;
            this.kind_ = null;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
        public boolean hasLegacySimple() {
            return this.kindCase_ == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
        public IrSimpleTypeLegacy getLegacySimple() {
            if (this.kindCase_ == 1) {
                return (IrSimpleTypeLegacy) this.kind_;
            }
            return IrSimpleTypeLegacy.getDefaultInstance();
        }

        public Builder setLegacySimple(IrSimpleTypeLegacy irSimpleTypeLegacy) {
            irSimpleTypeLegacy.getClass();
            this.kind_ = irSimpleTypeLegacy;
            this.kindCase_ = 1;
            return this;
        }

        public Builder setLegacySimple(IrSimpleTypeLegacy.Builder builder) {
            this.kind_ = builder.build();
            this.kindCase_ = 1;
            return this;
        }

        public Builder mergeLegacySimple(IrSimpleTypeLegacy irSimpleTypeLegacy) {
            if (this.kindCase_ == 1 && this.kind_ != IrSimpleTypeLegacy.getDefaultInstance()) {
                this.kind_ = IrSimpleTypeLegacy.newBuilder((IrSimpleTypeLegacy) this.kind_).mergeFrom(irSimpleTypeLegacy).buildPartial();
            } else {
                this.kind_ = irSimpleTypeLegacy;
            }
            this.kindCase_ = 1;
            return this;
        }

        public Builder clearLegacySimple() {
            if (this.kindCase_ == 1) {
                this.kindCase_ = 0;
                this.kind_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
        public boolean hasDynamic() {
            return this.kindCase_ == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
        public IrDynamicType getDynamic() {
            if (this.kindCase_ == 2) {
                return (IrDynamicType) this.kind_;
            }
            return IrDynamicType.getDefaultInstance();
        }

        public Builder setDynamic(IrDynamicType irDynamicType) {
            irDynamicType.getClass();
            this.kind_ = irDynamicType;
            this.kindCase_ = 2;
            return this;
        }

        public Builder setDynamic(IrDynamicType.Builder builder) {
            this.kind_ = builder.build();
            this.kindCase_ = 2;
            return this;
        }

        public Builder mergeDynamic(IrDynamicType irDynamicType) {
            if (this.kindCase_ == 2 && this.kind_ != IrDynamicType.getDefaultInstance()) {
                this.kind_ = IrDynamicType.newBuilder((IrDynamicType) this.kind_).mergeFrom(irDynamicType).buildPartial();
            } else {
                this.kind_ = irDynamicType;
            }
            this.kindCase_ = 2;
            return this;
        }

        public Builder clearDynamic() {
            if (this.kindCase_ == 2) {
                this.kindCase_ = 0;
                this.kind_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
        public boolean hasError() {
            return this.kindCase_ == 3;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
        public IrErrorType getError() {
            if (this.kindCase_ == 3) {
                return (IrErrorType) this.kind_;
            }
            return IrErrorType.getDefaultInstance();
        }

        public Builder setError(IrErrorType irErrorType) {
            irErrorType.getClass();
            this.kind_ = irErrorType;
            this.kindCase_ = 3;
            return this;
        }

        public Builder setError(IrErrorType.Builder builder) {
            this.kind_ = builder.build();
            this.kindCase_ = 3;
            return this;
        }

        public Builder mergeError(IrErrorType irErrorType) {
            if (this.kindCase_ == 3 && this.kind_ != IrErrorType.getDefaultInstance()) {
                this.kind_ = IrErrorType.newBuilder((IrErrorType) this.kind_).mergeFrom(irErrorType).buildPartial();
            } else {
                this.kind_ = irErrorType;
            }
            this.kindCase_ = 3;
            return this;
        }

        public Builder clearError() {
            if (this.kindCase_ == 3) {
                this.kindCase_ = 0;
                this.kind_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
        public boolean hasDnn() {
            return this.kindCase_ == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
        public IrDefinitelyNotNullType getDnn() {
            if (this.kindCase_ == 4) {
                return (IrDefinitelyNotNullType) this.kind_;
            }
            return IrDefinitelyNotNullType.getDefaultInstance();
        }

        public Builder setDnn(IrDefinitelyNotNullType irDefinitelyNotNullType) {
            irDefinitelyNotNullType.getClass();
            this.kind_ = irDefinitelyNotNullType;
            this.kindCase_ = 4;
            return this;
        }

        public Builder setDnn(IrDefinitelyNotNullType.Builder builder) {
            this.kind_ = builder.build();
            this.kindCase_ = 4;
            return this;
        }

        public Builder mergeDnn(IrDefinitelyNotNullType irDefinitelyNotNullType) {
            if (this.kindCase_ == 4 && this.kind_ != IrDefinitelyNotNullType.getDefaultInstance()) {
                this.kind_ = IrDefinitelyNotNullType.newBuilder((IrDefinitelyNotNullType) this.kind_).mergeFrom(irDefinitelyNotNullType).buildPartial();
            } else {
                this.kind_ = irDefinitelyNotNullType;
            }
            this.kindCase_ = 4;
            return this;
        }

        public Builder clearDnn() {
            if (this.kindCase_ == 4) {
                this.kindCase_ = 0;
                this.kind_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
        public boolean hasSimple() {
            return this.kindCase_ == 5;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder
        public IrSimpleType getSimple() {
            if (this.kindCase_ == 5) {
                return (IrSimpleType) this.kind_;
            }
            return IrSimpleType.getDefaultInstance();
        }

        public Builder setSimple(IrSimpleType irSimpleType) {
            irSimpleType.getClass();
            this.kind_ = irSimpleType;
            this.kindCase_ = 5;
            return this;
        }

        public Builder setSimple(IrSimpleType.Builder builder) {
            this.kind_ = builder.build();
            this.kindCase_ = 5;
            return this;
        }

        public Builder mergeSimple(IrSimpleType irSimpleType) {
            if (this.kindCase_ == 5 && this.kind_ != IrSimpleType.getDefaultInstance()) {
                this.kind_ = IrSimpleType.newBuilder((IrSimpleType) this.kind_).mergeFrom(irSimpleType).buildPartial();
            } else {
                this.kind_ = irSimpleType;
            }
            this.kindCase_ = 5;
            return this;
        }

        public Builder clearSimple() {
            if (this.kindCase_ == 5) {
                this.kindCase_ = 0;
                this.kind_ = null;
            }
            return this;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.common.serialization.proto.IrType$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrType$KindCase;

        static {
            int[] iArr = new int[KindCase.values().length];
            $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrType$KindCase = iArr;
            try {
                iArr[KindCase.LEGACYSIMPLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrType$KindCase[KindCase.DYNAMIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrType$KindCase[KindCase.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrType$KindCase[KindCase.DNN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrType$KindCase[KindCase.SIMPLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrType$KindCase[KindCase.KIND_NOT_SET.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }
}
