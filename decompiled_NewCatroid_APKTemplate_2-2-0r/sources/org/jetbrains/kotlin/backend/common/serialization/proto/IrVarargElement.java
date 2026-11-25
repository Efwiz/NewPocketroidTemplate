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
public final class IrVarargElement extends GeneratedMessageLite implements IrVarargElementOrBuilder {
    public static final int EXPRESSION_FIELD_NUMBER = 1;
    public static Parser<IrVarargElement> PARSER = new AbstractParser<IrVarargElement>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElement.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrVarargElement parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrVarargElement(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int SPREAD_ELEMENT_FIELD_NUMBER = 2;
    private static final IrVarargElement defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;
    private int varargElementCase_;
    private Object varargElement_;

    private void initFields() {
    }

    private IrVarargElement(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.varargElementCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrVarargElement(boolean z) {
        this.varargElementCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrVarargElement getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrVarargElement getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrVarargElement(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        GeneratedMessageLite.Builder builder;
        boolean z = false;
        this.varargElementCase_ = 0;
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
                            builder = this.varargElementCase_ == 1 ? ((IrExpression) this.varargElement_).toBuilder() : null;
                            MessageLite message = codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                            this.varargElement_ = message;
                            if (builder != null) {
                                builder.mergeFrom((IrExpression) message);
                                this.varargElement_ = builder.buildPartial();
                            }
                            this.varargElementCase_ = 1;
                        } else if (tag != 18) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            builder = this.varargElementCase_ == 2 ? ((IrSpreadElement) this.varargElement_).toBuilder() : null;
                            MessageLite message2 = codedInputStream.readMessage(IrSpreadElement.PARSER, extensionRegistryLite);
                            this.varargElement_ = message2;
                            if (builder != null) {
                                builder.mergeFrom((IrSpreadElement) message2);
                                this.varargElement_ = builder.buildPartial();
                            }
                            this.varargElementCase_ = 2;
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
        IrVarargElement irVarargElement = new IrVarargElement(true);
        defaultInstance = irVarargElement;
        irVarargElement.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrVarargElement> getParserForType() {
        return PARSER;
    }

    public enum VarargElementCase implements Internal.EnumLite {
        EXPRESSION(1),
        SPREAD_ELEMENT(2),
        VARARGELEMENT_NOT_SET(0);

        private int value;

        VarargElementCase(int i) {
            this.value = i;
        }

        public static VarargElementCase valueOf(int i) {
            if (i == 0) {
                return VARARGELEMENT_NOT_SET;
            }
            if (i == 1) {
                return EXPRESSION;
            }
            if (i == 2) {
                return SPREAD_ELEMENT;
            }
            throw new IllegalArgumentException("Value is undefined for this oneof enum.");
        }

        @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLite
        public int getNumber() {
            return this.value;
        }
    }

    public VarargElementCase getVarargElementCase() {
        return VarargElementCase.valueOf(this.varargElementCase_);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElementOrBuilder
    public boolean hasExpression() {
        return this.varargElementCase_ == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElementOrBuilder
    public IrExpression getExpression() {
        if (this.varargElementCase_ == 1) {
            return (IrExpression) this.varargElement_;
        }
        return IrExpression.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElementOrBuilder
    public boolean hasSpreadElement() {
        return this.varargElementCase_ == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElementOrBuilder
    public IrSpreadElement getSpreadElement() {
        if (this.varargElementCase_ == 2) {
            return (IrSpreadElement) this.varargElement_;
        }
        return IrSpreadElement.getDefaultInstance();
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
        if (hasExpression() && !getExpression().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasSpreadElement() && !getSpreadElement().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        this.memoizedIsInitialized = (byte) 1;
        return true;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        getSerializedSize();
        if (this.varargElementCase_ == 1) {
            codedOutputStream.writeMessage(1, (IrExpression) this.varargElement_);
        }
        if (this.varargElementCase_ == 2) {
            codedOutputStream.writeMessage(2, (IrSpreadElement) this.varargElement_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = this.varargElementCase_ == 1 ? CodedOutputStream.computeMessageSize(1, (IrExpression) this.varargElement_) : 0;
        if (this.varargElementCase_ == 2) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(2, (IrSpreadElement) this.varargElement_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrVarargElement parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrVarargElement parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrVarargElement parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrVarargElement parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrVarargElement parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrVarargElement parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrVarargElement parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrVarargElement parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrVarargElement parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrVarargElement parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrVarargElement irVarargElement) {
        return newBuilder().mergeFrom(irVarargElement);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrVarargElement, Builder> implements IrVarargElementOrBuilder {
        private int bitField0_;
        private int varargElementCase_ = 0;
        private Object varargElement_;

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
            this.varargElementCase_ = 0;
            this.varargElement_ = null;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrVarargElement getDefaultInstanceForType() {
            return IrVarargElement.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrVarargElement build() {
            IrVarargElement irVarargElementBuildPartial = buildPartial();
            if (irVarargElementBuildPartial.isInitialized()) {
                return irVarargElementBuildPartial;
            }
            throw newUninitializedMessageException(irVarargElementBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrVarargElement buildPartial() {
            IrVarargElement irVarargElement = new IrVarargElement(this);
            if (this.varargElementCase_ == 1) {
                irVarargElement.varargElement_ = this.varargElement_;
            }
            if (this.varargElementCase_ == 2) {
                irVarargElement.varargElement_ = this.varargElement_;
            }
            irVarargElement.bitField0_ = 0;
            irVarargElement.varargElementCase_ = this.varargElementCase_;
            return irVarargElement;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrVarargElement irVarargElement) {
            if (irVarargElement == IrVarargElement.getDefaultInstance()) {
                return this;
            }
            int i = AnonymousClass2.$SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrVarargElement$VarargElementCase[irVarargElement.getVarargElementCase().ordinal()];
            if (i == 1) {
                mergeExpression(irVarargElement.getExpression());
            } else if (i == 2) {
                mergeSpreadElement(irVarargElement.getSpreadElement());
            }
            setUnknownFields(getUnknownFields().concat(irVarargElement.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (!hasExpression() || getExpression().isInitialized()) {
                return !hasSpreadElement() || getSpreadElement().isInitialized();
            }
            return false;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrVarargElement irVarargElement = null;
            try {
                try {
                    IrVarargElement partialFrom = IrVarargElement.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrVarargElement irVarargElement2 = (IrVarargElement) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irVarargElement = irVarargElement2;
                        if (irVarargElement != null) {
                            mergeFrom(irVarargElement);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irVarargElement != null) {
                    mergeFrom(irVarargElement);
                }
                throw th;
            }
        }

        public VarargElementCase getVarargElementCase() {
            return VarargElementCase.valueOf(this.varargElementCase_);
        }

        public Builder clearVarargElement() {
            this.varargElementCase_ = 0;
            this.varargElement_ = null;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElementOrBuilder
        public boolean hasExpression() {
            return this.varargElementCase_ == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElementOrBuilder
        public IrExpression getExpression() {
            if (this.varargElementCase_ == 1) {
                return (IrExpression) this.varargElement_;
            }
            return IrExpression.getDefaultInstance();
        }

        public Builder setExpression(IrExpression irExpression) {
            irExpression.getClass();
            this.varargElement_ = irExpression;
            this.varargElementCase_ = 1;
            return this;
        }

        public Builder setExpression(IrExpression.Builder builder) {
            this.varargElement_ = builder.build();
            this.varargElementCase_ = 1;
            return this;
        }

        public Builder mergeExpression(IrExpression irExpression) {
            if (this.varargElementCase_ == 1 && this.varargElement_ != IrExpression.getDefaultInstance()) {
                this.varargElement_ = IrExpression.newBuilder((IrExpression) this.varargElement_).mergeFrom(irExpression).buildPartial();
            } else {
                this.varargElement_ = irExpression;
            }
            this.varargElementCase_ = 1;
            return this;
        }

        public Builder clearExpression() {
            if (this.varargElementCase_ == 1) {
                this.varargElementCase_ = 0;
                this.varargElement_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElementOrBuilder
        public boolean hasSpreadElement() {
            return this.varargElementCase_ == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElementOrBuilder
        public IrSpreadElement getSpreadElement() {
            if (this.varargElementCase_ == 2) {
                return (IrSpreadElement) this.varargElement_;
            }
            return IrSpreadElement.getDefaultInstance();
        }

        public Builder setSpreadElement(IrSpreadElement irSpreadElement) {
            irSpreadElement.getClass();
            this.varargElement_ = irSpreadElement;
            this.varargElementCase_ = 2;
            return this;
        }

        public Builder setSpreadElement(IrSpreadElement.Builder builder) {
            this.varargElement_ = builder.build();
            this.varargElementCase_ = 2;
            return this;
        }

        public Builder mergeSpreadElement(IrSpreadElement irSpreadElement) {
            if (this.varargElementCase_ == 2 && this.varargElement_ != IrSpreadElement.getDefaultInstance()) {
                this.varargElement_ = IrSpreadElement.newBuilder((IrSpreadElement) this.varargElement_).mergeFrom(irSpreadElement).buildPartial();
            } else {
                this.varargElement_ = irSpreadElement;
            }
            this.varargElementCase_ = 2;
            return this;
        }

        public Builder clearSpreadElement() {
            if (this.varargElementCase_ == 2) {
                this.varargElementCase_ = 0;
                this.varargElement_ = null;
            }
            return this;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElement$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrVarargElement$VarargElementCase;

        static {
            int[] iArr = new int[VarargElementCase.values().length];
            $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrVarargElement$VarargElementCase = iArr;
            try {
                iArr[VarargElementCase.EXPRESSION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrVarargElement$VarargElementCase[VarargElementCase.SPREAD_ELEMENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrVarargElement$VarargElementCase[VarargElementCase.VARARGELEMENT_NOT_SET.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}
