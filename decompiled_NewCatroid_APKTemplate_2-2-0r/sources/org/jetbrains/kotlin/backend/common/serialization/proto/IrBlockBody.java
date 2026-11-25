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
public final class IrBlockBody extends GeneratedMessageLite implements IrBlockBodyOrBuilder {
    public static Parser<IrBlockBody> PARSER = new AbstractParser<IrBlockBody>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockBody.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrBlockBody parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrBlockBody(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int STATEMENT_FIELD_NUMBER = 1;
    private static final IrBlockBody defaultInstance;
    private static final long serialVersionUID = 0;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private List<IrStatement> statement_;
    private final ByteString unknownFields;

    private IrBlockBody(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrBlockBody(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrBlockBody getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrBlockBody getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrBlockBody(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        initFields();
        ByteString.Output outputNewOutput = ByteString.newOutput();
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            try {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag != 10) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                if (!z2) {
                                    this.statement_ = new ArrayList();
                                    z2 = true;
                                }
                                this.statement_.add((IrStatement) codedInputStream.readMessage(IrStatement.PARSER, extensionRegistryLite));
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
                if (z2) {
                    this.statement_ = Collections.unmodifiableList(this.statement_);
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
        if (z2) {
            this.statement_ = Collections.unmodifiableList(this.statement_);
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
        IrBlockBody irBlockBody = new IrBlockBody(true);
        defaultInstance = irBlockBody;
        irBlockBody.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrBlockBody> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockBodyOrBuilder
    public List<IrStatement> getStatementList() {
        return this.statement_;
    }

    public List<? extends IrStatementOrBuilder> getStatementOrBuilderList() {
        return this.statement_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockBodyOrBuilder
    public int getStatementCount() {
        return this.statement_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockBodyOrBuilder
    public IrStatement getStatement(int i) {
        return this.statement_.get(i);
    }

    public IrStatementOrBuilder getStatementOrBuilder(int i) {
        return this.statement_.get(i);
    }

    private void initFields() {
        this.statement_ = Collections.emptyList();
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
        for (int i = 0; i < getStatementCount(); i++) {
            if (!getStatement(i).isInitialized()) {
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
        for (int i = 0; i < this.statement_.size(); i++) {
            codedOutputStream.writeMessage(1, this.statement_.get(i));
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = 0;
        for (int i2 = 0; i2 < this.statement_.size(); i2++) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.statement_.get(i2));
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrBlockBody parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrBlockBody parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrBlockBody parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrBlockBody parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrBlockBody parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrBlockBody parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrBlockBody parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrBlockBody parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrBlockBody parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrBlockBody parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrBlockBody irBlockBody) {
        return newBuilder().mergeFrom(irBlockBody);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrBlockBody, Builder> implements IrBlockBodyOrBuilder {
        private int bitField0_;
        private List<IrStatement> statement_ = Collections.emptyList();

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
            this.statement_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrBlockBody getDefaultInstanceForType() {
            return IrBlockBody.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrBlockBody build() {
            IrBlockBody irBlockBodyBuildPartial = buildPartial();
            if (irBlockBodyBuildPartial.isInitialized()) {
                return irBlockBodyBuildPartial;
            }
            throw newUninitializedMessageException(irBlockBodyBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrBlockBody buildPartial() {
            IrBlockBody irBlockBody = new IrBlockBody(this);
            if ((this.bitField0_ & 1) == 1) {
                this.statement_ = Collections.unmodifiableList(this.statement_);
                this.bitField0_ &= -2;
            }
            irBlockBody.statement_ = this.statement_;
            return irBlockBody;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrBlockBody irBlockBody) {
            if (irBlockBody == IrBlockBody.getDefaultInstance()) {
                return this;
            }
            if (!irBlockBody.statement_.isEmpty()) {
                if (this.statement_.isEmpty()) {
                    this.statement_ = irBlockBody.statement_;
                    this.bitField0_ &= -2;
                } else {
                    ensureStatementIsMutable();
                    this.statement_.addAll(irBlockBody.statement_);
                }
            }
            setUnknownFields(getUnknownFields().concat(irBlockBody.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            for (int i = 0; i < getStatementCount(); i++) {
                if (!getStatement(i).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrBlockBody irBlockBody = null;
            try {
                try {
                    IrBlockBody partialFrom = IrBlockBody.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrBlockBody irBlockBody2 = (IrBlockBody) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irBlockBody = irBlockBody2;
                        if (irBlockBody != null) {
                            mergeFrom(irBlockBody);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irBlockBody != null) {
                    mergeFrom(irBlockBody);
                }
                throw th;
            }
        }

        private void ensureStatementIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
                this.statement_ = new ArrayList(this.statement_);
                this.bitField0_ |= 1;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockBodyOrBuilder
        public List<IrStatement> getStatementList() {
            return Collections.unmodifiableList(this.statement_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockBodyOrBuilder
        public int getStatementCount() {
            return this.statement_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockBodyOrBuilder
        public IrStatement getStatement(int i) {
            return this.statement_.get(i);
        }

        public Builder setStatement(int i, IrStatement irStatement) {
            irStatement.getClass();
            ensureStatementIsMutable();
            this.statement_.set(i, irStatement);
            return this;
        }

        public Builder setStatement(int i, IrStatement.Builder builder) {
            ensureStatementIsMutable();
            this.statement_.set(i, builder.build());
            return this;
        }

        public Builder addStatement(IrStatement irStatement) {
            irStatement.getClass();
            ensureStatementIsMutable();
            this.statement_.add(irStatement);
            return this;
        }

        public Builder addStatement(int i, IrStatement irStatement) {
            irStatement.getClass();
            ensureStatementIsMutable();
            this.statement_.add(i, irStatement);
            return this;
        }

        public Builder addStatement(IrStatement.Builder builder) {
            ensureStatementIsMutable();
            this.statement_.add(builder.build());
            return this;
        }

        public Builder addStatement(int i, IrStatement.Builder builder) {
            ensureStatementIsMutable();
            this.statement_.add(i, builder.build());
            return this;
        }

        public Builder addAllStatement(Iterable<? extends IrStatement> iterable) {
            ensureStatementIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.statement_);
            return this;
        }

        public Builder clearStatement() {
            this.statement_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
        }

        public Builder removeStatement(int i) {
            ensureStatementIsMutable();
            this.statement_.remove(i);
            return this;
        }
    }
}
