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
public final class IrBlock extends GeneratedMessageLite implements IrBlockOrBuilder {
    public static final int ORIGIN_NAME_FIELD_NUMBER = 2;
    public static Parser<IrBlock> PARSER = new AbstractParser<IrBlock>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrBlock.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrBlock parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrBlock(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int STATEMENT_FIELD_NUMBER = 1;
    private static final IrBlock defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int originName_;
    private List<IrStatement> statement_;
    private final ByteString unknownFields;

    private IrBlock(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrBlock(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrBlock getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrBlock getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrBlock(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                            if (tag == 10) {
                                if (!z2) {
                                    this.statement_ = new ArrayList();
                                    z2 = true;
                                }
                                this.statement_.add((IrStatement) codedInputStream.readMessage(IrStatement.PARSER, extensionRegistryLite));
                            } else if (tag != 16) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.bitField0_ |= 1;
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
        IrBlock irBlock = new IrBlock(true);
        defaultInstance = irBlock;
        irBlock.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrBlock> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockOrBuilder
    public List<IrStatement> getStatementList() {
        return this.statement_;
    }

    public List<? extends IrStatementOrBuilder> getStatementOrBuilderList() {
        return this.statement_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockOrBuilder
    public int getStatementCount() {
        return this.statement_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockOrBuilder
    public IrStatement getStatement(int i) {
        return this.statement_.get(i);
    }

    public IrStatementOrBuilder getStatementOrBuilder(int i) {
        return this.statement_.get(i);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockOrBuilder
    public boolean hasOriginName() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockOrBuilder
    public int getOriginName() {
        return this.originName_;
    }

    private void initFields() {
        this.statement_ = Collections.emptyList();
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
        if ((this.bitField0_ & 1) == 1) {
            codedOutputStream.writeInt32(2, this.originName_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt32Size = 0;
        for (int i2 = 0; i2 < this.statement_.size(); i2++) {
            iComputeInt32Size += CodedOutputStream.computeMessageSize(1, this.statement_.get(i2));
        }
        if ((this.bitField0_ & 1) == 1) {
            iComputeInt32Size += CodedOutputStream.computeInt32Size(2, this.originName_);
        }
        int size = iComputeInt32Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrBlock parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrBlock parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrBlock parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrBlock parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrBlock parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrBlock parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrBlock parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrBlock parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrBlock parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrBlock parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrBlock irBlock) {
        return newBuilder().mergeFrom(irBlock);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrBlock, Builder> implements IrBlockOrBuilder {
        private int bitField0_;
        private int originName_;
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
            int i = this.bitField0_;
            this.originName_ = 0;
            this.bitField0_ = i & (-4);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrBlock getDefaultInstanceForType() {
            return IrBlock.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrBlock build() {
            IrBlock irBlockBuildPartial = buildPartial();
            if (irBlockBuildPartial.isInitialized()) {
                return irBlockBuildPartial;
            }
            throw newUninitializedMessageException(irBlockBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrBlock buildPartial() {
            IrBlock irBlock = new IrBlock(this);
            int i = this.bitField0_;
            if ((i & 1) == 1) {
                this.statement_ = Collections.unmodifiableList(this.statement_);
                this.bitField0_ &= -2;
            }
            irBlock.statement_ = this.statement_;
            int i2 = (i & 2) != 2 ? 0 : 1;
            irBlock.originName_ = this.originName_;
            irBlock.bitField0_ = i2;
            return irBlock;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrBlock irBlock) {
            if (irBlock == IrBlock.getDefaultInstance()) {
                return this;
            }
            if (!irBlock.statement_.isEmpty()) {
                if (this.statement_.isEmpty()) {
                    this.statement_ = irBlock.statement_;
                    this.bitField0_ &= -2;
                } else {
                    ensureStatementIsMutable();
                    this.statement_.addAll(irBlock.statement_);
                }
            }
            if (irBlock.hasOriginName()) {
                setOriginName(irBlock.getOriginName());
            }
            setUnknownFields(getUnknownFields().concat(irBlock.unknownFields));
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
            IrBlock irBlock = null;
            try {
                try {
                    IrBlock partialFrom = IrBlock.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrBlock irBlock2 = (IrBlock) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irBlock = irBlock2;
                        if (irBlock != null) {
                            mergeFrom(irBlock);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irBlock != null) {
                    mergeFrom(irBlock);
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockOrBuilder
        public List<IrStatement> getStatementList() {
            return Collections.unmodifiableList(this.statement_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockOrBuilder
        public int getStatementCount() {
            return this.statement_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockOrBuilder
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

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockOrBuilder
        public boolean hasOriginName() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockOrBuilder
        public int getOriginName() {
            return this.originName_;
        }

        public Builder setOriginName(int i) {
            this.bitField0_ |= 2;
            this.originName_ = i;
            return this;
        }

        public Builder clearOriginName() {
            this.bitField0_ &= -3;
            this.originName_ = 0;
            return this;
        }
    }
}
