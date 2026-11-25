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
public final class IrTry extends GeneratedMessageLite implements IrTryOrBuilder {
    public static final int CATCH_FIELD_NUMBER = 2;
    public static final int FINALLY_FIELD_NUMBER = 3;
    public static Parser<IrTry> PARSER = new AbstractParser<IrTry>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrTry.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrTry parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrTry(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int RESULT_FIELD_NUMBER = 1;
    private static final IrTry defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private List<IrStatement> catch_;
    private IrExpression finally_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private IrExpression result_;
    private final ByteString unknownFields;

    private IrTry(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrTry(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrTry getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrTry getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrTry(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        IrExpression.Builder builder;
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
                        if (tag == 10) {
                            builder = (this.bitField0_ & 1) == 1 ? this.result_.toBuilder() : null;
                            IrExpression irExpression = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                            this.result_ = irExpression;
                            if (builder != null) {
                                builder.mergeFrom(irExpression);
                                this.result_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                        } else if (tag == 18) {
                            if ((c & 2) != 2) {
                                this.catch_ = new ArrayList();
                                c = 2;
                            }
                            this.catch_.add((IrStatement) codedInputStream.readMessage(IrStatement.PARSER, extensionRegistryLite));
                        } else if (tag != 26) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            builder = (this.bitField0_ & 2) == 2 ? this.finally_.toBuilder() : null;
                            IrExpression irExpression2 = (IrExpression) codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                            this.finally_ = irExpression2;
                            if (builder != null) {
                                builder.mergeFrom(irExpression2);
                                this.finally_ = builder.buildPartial();
                            }
                            this.bitField0_ |= 2;
                        }
                    }
                    z = true;
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                }
            } catch (Throwable th) {
                if ((c & 2) == 2) {
                    this.catch_ = Collections.unmodifiableList(this.catch_);
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
        }
        if ((c & 2) == 2) {
            this.catch_ = Collections.unmodifiableList(this.catch_);
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
        IrTry irTry = new IrTry(true);
        defaultInstance = irTry;
        irTry.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrTry> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTryOrBuilder
    public boolean hasResult() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTryOrBuilder
    public IrExpression getResult() {
        return this.result_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTryOrBuilder
    public List<IrStatement> getCatchList() {
        return this.catch_;
    }

    public List<? extends IrStatementOrBuilder> getCatchOrBuilderList() {
        return this.catch_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTryOrBuilder
    public int getCatchCount() {
        return this.catch_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTryOrBuilder
    public IrStatement getCatch(int i) {
        return this.catch_.get(i);
    }

    public IrStatementOrBuilder getCatchOrBuilder(int i) {
        return this.catch_.get(i);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTryOrBuilder
    public boolean hasFinally() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTryOrBuilder
    public IrExpression getFinally() {
        return this.finally_;
    }

    private void initFields() {
        this.result_ = IrExpression.getDefaultInstance();
        this.catch_ = Collections.emptyList();
        this.finally_ = IrExpression.getDefaultInstance();
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
        if (!hasResult()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getResult().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        for (int i = 0; i < getCatchCount(); i++) {
            if (!getCatch(i).isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
        }
        if (hasFinally() && !getFinally().isInitialized()) {
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
            codedOutputStream.writeMessage(1, this.result_);
        }
        for (int i = 0; i < this.catch_.size(); i++) {
            codedOutputStream.writeMessage(2, this.catch_.get(i));
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeMessage(3, this.finally_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeMessageSize(1, this.result_) : 0;
        for (int i2 = 0; i2 < this.catch_.size(); i2++) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(2, this.catch_.get(i2));
        }
        if ((this.bitField0_ & 2) == 2) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(3, this.finally_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrTry parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrTry parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrTry parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrTry parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrTry parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrTry parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrTry parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrTry parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrTry parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrTry parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrTry irTry) {
        return newBuilder().mergeFrom(irTry);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrTry, Builder> implements IrTryOrBuilder {
        private int bitField0_;
        private IrExpression result_ = IrExpression.getDefaultInstance();
        private List<IrStatement> catch_ = Collections.emptyList();
        private IrExpression finally_ = IrExpression.getDefaultInstance();

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
            this.result_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -2;
            this.catch_ = Collections.emptyList();
            this.bitField0_ &= -3;
            this.finally_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -5;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrTry getDefaultInstanceForType() {
            return IrTry.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrTry build() {
            IrTry irTryBuildPartial = buildPartial();
            if (irTryBuildPartial.isInitialized()) {
                return irTryBuildPartial;
            }
            throw newUninitializedMessageException(irTryBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrTry buildPartial() {
            IrTry irTry = new IrTry(this);
            int i = this.bitField0_;
            int i2 = (i & 1) != 1 ? 0 : 1;
            irTry.result_ = this.result_;
            if ((this.bitField0_ & 2) == 2) {
                this.catch_ = Collections.unmodifiableList(this.catch_);
                this.bitField0_ &= -3;
            }
            irTry.catch_ = this.catch_;
            if ((i & 4) == 4) {
                i2 |= 2;
            }
            irTry.finally_ = this.finally_;
            irTry.bitField0_ = i2;
            return irTry;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrTry irTry) {
            if (irTry == IrTry.getDefaultInstance()) {
                return this;
            }
            if (irTry.hasResult()) {
                mergeResult(irTry.getResult());
            }
            if (!irTry.catch_.isEmpty()) {
                if (this.catch_.isEmpty()) {
                    this.catch_ = irTry.catch_;
                    this.bitField0_ &= -3;
                } else {
                    ensureCatchIsMutable();
                    this.catch_.addAll(irTry.catch_);
                }
            }
            if (irTry.hasFinally()) {
                mergeFinally(irTry.getFinally());
            }
            setUnknownFields(getUnknownFields().concat(irTry.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (!hasResult() || !getResult().isInitialized()) {
                return false;
            }
            for (int i = 0; i < getCatchCount(); i++) {
                if (!getCatch(i).isInitialized()) {
                    return false;
                }
            }
            return !hasFinally() || getFinally().isInitialized();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrTry irTry = null;
            try {
                try {
                    IrTry partialFrom = IrTry.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrTry irTry2 = (IrTry) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irTry = irTry2;
                        if (irTry != null) {
                            mergeFrom(irTry);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irTry != null) {
                    mergeFrom(irTry);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTryOrBuilder
        public boolean hasResult() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTryOrBuilder
        public IrExpression getResult() {
            return this.result_;
        }

        public Builder setResult(IrExpression irExpression) {
            irExpression.getClass();
            this.result_ = irExpression;
            this.bitField0_ |= 1;
            return this;
        }

        public Builder setResult(IrExpression.Builder builder) {
            this.result_ = builder.build();
            this.bitField0_ |= 1;
            return this;
        }

        public Builder mergeResult(IrExpression irExpression) {
            if ((this.bitField0_ & 1) == 1 && this.result_ != IrExpression.getDefaultInstance()) {
                this.result_ = IrExpression.newBuilder(this.result_).mergeFrom(irExpression).buildPartial();
            } else {
                this.result_ = irExpression;
            }
            this.bitField0_ |= 1;
            return this;
        }

        public Builder clearResult() {
            this.result_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -2;
            return this;
        }

        private void ensureCatchIsMutable() {
            if ((this.bitField0_ & 2) != 2) {
                this.catch_ = new ArrayList(this.catch_);
                this.bitField0_ |= 2;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTryOrBuilder
        public List<IrStatement> getCatchList() {
            return Collections.unmodifiableList(this.catch_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTryOrBuilder
        public int getCatchCount() {
            return this.catch_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTryOrBuilder
        public IrStatement getCatch(int i) {
            return this.catch_.get(i);
        }

        public Builder setCatch(int i, IrStatement irStatement) {
            irStatement.getClass();
            ensureCatchIsMutable();
            this.catch_.set(i, irStatement);
            return this;
        }

        public Builder setCatch(int i, IrStatement.Builder builder) {
            ensureCatchIsMutable();
            this.catch_.set(i, builder.build());
            return this;
        }

        public Builder addCatch(IrStatement irStatement) {
            irStatement.getClass();
            ensureCatchIsMutable();
            this.catch_.add(irStatement);
            return this;
        }

        public Builder addCatch(int i, IrStatement irStatement) {
            irStatement.getClass();
            ensureCatchIsMutable();
            this.catch_.add(i, irStatement);
            return this;
        }

        public Builder addCatch(IrStatement.Builder builder) {
            ensureCatchIsMutable();
            this.catch_.add(builder.build());
            return this;
        }

        public Builder addCatch(int i, IrStatement.Builder builder) {
            ensureCatchIsMutable();
            this.catch_.add(i, builder.build());
            return this;
        }

        public Builder addAllCatch(Iterable<? extends IrStatement> iterable) {
            ensureCatchIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.catch_);
            return this;
        }

        public Builder clearCatch() {
            this.catch_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
        }

        public Builder removeCatch(int i) {
            ensureCatchIsMutable();
            this.catch_.remove(i);
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTryOrBuilder
        public boolean hasFinally() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrTryOrBuilder
        public IrExpression getFinally() {
            return this.finally_;
        }

        public Builder setFinally(IrExpression irExpression) {
            irExpression.getClass();
            this.finally_ = irExpression;
            this.bitField0_ |= 4;
            return this;
        }

        public Builder setFinally(IrExpression.Builder builder) {
            this.finally_ = builder.build();
            this.bitField0_ |= 4;
            return this;
        }

        public Builder mergeFinally(IrExpression irExpression) {
            if ((this.bitField0_ & 4) == 4 && this.finally_ != IrExpression.getDefaultInstance()) {
                this.finally_ = IrExpression.newBuilder(this.finally_).mergeFrom(irExpression).buildPartial();
            } else {
                this.finally_ = irExpression;
            }
            this.bitField0_ |= 4;
            return this;
        }

        public Builder clearFinally() {
            this.finally_ = IrExpression.getDefaultInstance();
            this.bitField0_ &= -5;
            return this;
        }
    }
}
