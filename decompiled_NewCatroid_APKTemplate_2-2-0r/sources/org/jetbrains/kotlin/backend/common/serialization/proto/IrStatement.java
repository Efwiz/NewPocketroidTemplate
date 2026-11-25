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
public final class IrStatement extends GeneratedMessageLite implements IrStatementOrBuilder {
    public static final int BLOCK_BODY_FIELD_NUMBER = 4;
    public static final int BRANCH_FIELD_NUMBER = 5;
    public static final int CATCH_FIELD_NUMBER = 6;
    public static final int COORDINATES_FIELD_NUMBER = 1;
    public static final int DECLARATION_FIELD_NUMBER = 2;
    public static final int EXPRESSION_FIELD_NUMBER = 3;
    public static Parser<IrStatement> PARSER = new AbstractParser<IrStatement>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrStatement.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrStatement parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrStatement(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int SYNTHETIC_BODY_FIELD_NUMBER = 7;
    private static final IrStatement defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private long coordinates_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int statementCase_;
    private Object statement_;
    private final ByteString unknownFields;

    private IrStatement(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.statementCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrStatement(boolean z) {
        this.statementCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrStatement getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrStatement getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrStatement(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        boolean z = false;
        this.statementCase_ = 0;
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
                        if (tag != 8) {
                            if (tag == 18) {
                                IrDeclaration.Builder builder = this.statementCase_ == 2 ? ((IrDeclaration) this.statement_).toBuilder() : null;
                                MessageLite message = codedInputStream.readMessage(IrDeclaration.PARSER, extensionRegistryLite);
                                this.statement_ = message;
                                if (builder != null) {
                                    builder.mergeFrom((IrDeclaration) message);
                                    this.statement_ = builder.buildPartial();
                                }
                                this.statementCase_ = 2;
                            } else if (tag == 26) {
                                IrExpression.Builder builder2 = this.statementCase_ == 3 ? ((IrExpression) this.statement_).toBuilder() : null;
                                MessageLite message2 = codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                                this.statement_ = message2;
                                if (builder2 != null) {
                                    builder2.mergeFrom((IrExpression) message2);
                                    this.statement_ = builder2.buildPartial();
                                }
                                this.statementCase_ = 3;
                            } else if (tag == 34) {
                                IrBlockBody.Builder builder3 = this.statementCase_ == 4 ? ((IrBlockBody) this.statement_).toBuilder() : null;
                                MessageLite message3 = codedInputStream.readMessage(IrBlockBody.PARSER, extensionRegistryLite);
                                this.statement_ = message3;
                                if (builder3 != null) {
                                    builder3.mergeFrom((IrBlockBody) message3);
                                    this.statement_ = builder3.buildPartial();
                                }
                                this.statementCase_ = 4;
                            } else if (tag == 42) {
                                IrBranch.Builder builder4 = this.statementCase_ == 5 ? ((IrBranch) this.statement_).toBuilder() : null;
                                MessageLite message4 = codedInputStream.readMessage(IrBranch.PARSER, extensionRegistryLite);
                                this.statement_ = message4;
                                if (builder4 != null) {
                                    builder4.mergeFrom((IrBranch) message4);
                                    this.statement_ = builder4.buildPartial();
                                }
                                this.statementCase_ = 5;
                            } else if (tag == 50) {
                                IrCatch.Builder builder5 = this.statementCase_ == 6 ? ((IrCatch) this.statement_).toBuilder() : null;
                                MessageLite message5 = codedInputStream.readMessage(IrCatch.PARSER, extensionRegistryLite);
                                this.statement_ = message5;
                                if (builder5 != null) {
                                    builder5.mergeFrom((IrCatch) message5);
                                    this.statement_ = builder5.buildPartial();
                                }
                                this.statementCase_ = 6;
                            } else if (tag != 58) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                IrSyntheticBody.Builder builder6 = this.statementCase_ == 7 ? ((IrSyntheticBody) this.statement_).toBuilder() : null;
                                MessageLite message6 = codedInputStream.readMessage(IrSyntheticBody.PARSER, extensionRegistryLite);
                                this.statement_ = message6;
                                if (builder6 != null) {
                                    builder6.mergeFrom((IrSyntheticBody) message6);
                                    this.statement_ = builder6.buildPartial();
                                }
                                this.statementCase_ = 7;
                            }
                        } else {
                            this.bitField0_ |= 1;
                            this.coordinates_ = codedInputStream.readInt64();
                        }
                    }
                    z = true;
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
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
        IrStatement irStatement = new IrStatement(true);
        defaultInstance = irStatement;
        irStatement.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrStatement> getParserForType() {
        return PARSER;
    }

    public enum StatementCase implements Internal.EnumLite {
        DECLARATION(2),
        EXPRESSION(3),
        BLOCK_BODY(4),
        BRANCH(5),
        CATCH(6),
        SYNTHETIC_BODY(7),
        STATEMENT_NOT_SET(0);

        private int value;

        StatementCase(int i) {
            this.value = i;
        }

        public static StatementCase valueOf(int i) {
            if (i != 0) {
                switch (i) {
                    case 2:
                        return DECLARATION;
                    case 3:
                        return EXPRESSION;
                    case 4:
                        return BLOCK_BODY;
                    case 5:
                        return BRANCH;
                    case 6:
                        return CATCH;
                    case 7:
                        return SYNTHETIC_BODY;
                    default:
                        throw new IllegalArgumentException("Value is undefined for this oneof enum.");
                }
            }
            return STATEMENT_NOT_SET;
        }

        @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLite
        public int getNumber() {
            return this.value;
        }
    }

    public StatementCase getStatementCase() {
        return StatementCase.valueOf(this.statementCase_);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
    public boolean hasCoordinates() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
    public long getCoordinates() {
        return this.coordinates_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
    public boolean hasDeclaration() {
        return this.statementCase_ == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
    public IrDeclaration getDeclaration() {
        if (this.statementCase_ == 2) {
            return (IrDeclaration) this.statement_;
        }
        return IrDeclaration.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
    public boolean hasExpression() {
        return this.statementCase_ == 3;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
    public IrExpression getExpression() {
        if (this.statementCase_ == 3) {
            return (IrExpression) this.statement_;
        }
        return IrExpression.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
    public boolean hasBlockBody() {
        return this.statementCase_ == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
    public IrBlockBody getBlockBody() {
        if (this.statementCase_ == 4) {
            return (IrBlockBody) this.statement_;
        }
        return IrBlockBody.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
    public boolean hasBranch() {
        return this.statementCase_ == 5;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
    public IrBranch getBranch() {
        if (this.statementCase_ == 5) {
            return (IrBranch) this.statement_;
        }
        return IrBranch.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
    public boolean hasCatch() {
        return this.statementCase_ == 6;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
    public IrCatch getCatch() {
        if (this.statementCase_ == 6) {
            return (IrCatch) this.statement_;
        }
        return IrCatch.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
    public boolean hasSyntheticBody() {
        return this.statementCase_ == 7;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
    public IrSyntheticBody getSyntheticBody() {
        if (this.statementCase_ == 7) {
            return (IrSyntheticBody) this.statement_;
        }
        return IrSyntheticBody.getDefaultInstance();
    }

    private void initFields() {
        this.coordinates_ = 0L;
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
        if (!hasCoordinates()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasDeclaration() && !getDeclaration().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasExpression() && !getExpression().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasBlockBody() && !getBlockBody().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasBranch() && !getBranch().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasCatch() && !getCatch().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasSyntheticBody() && !getSyntheticBody().isInitialized()) {
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
            codedOutputStream.writeInt64(1, this.coordinates_);
        }
        if (this.statementCase_ == 2) {
            codedOutputStream.writeMessage(2, (IrDeclaration) this.statement_);
        }
        if (this.statementCase_ == 3) {
            codedOutputStream.writeMessage(3, (IrExpression) this.statement_);
        }
        if (this.statementCase_ == 4) {
            codedOutputStream.writeMessage(4, (IrBlockBody) this.statement_);
        }
        if (this.statementCase_ == 5) {
            codedOutputStream.writeMessage(5, (IrBranch) this.statement_);
        }
        if (this.statementCase_ == 6) {
            codedOutputStream.writeMessage(6, (IrCatch) this.statement_);
        }
        if (this.statementCase_ == 7) {
            codedOutputStream.writeMessage(7, (IrSyntheticBody) this.statement_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt64Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt64Size(1, this.coordinates_) : 0;
        if (this.statementCase_ == 2) {
            iComputeInt64Size += CodedOutputStream.computeMessageSize(2, (IrDeclaration) this.statement_);
        }
        if (this.statementCase_ == 3) {
            iComputeInt64Size += CodedOutputStream.computeMessageSize(3, (IrExpression) this.statement_);
        }
        if (this.statementCase_ == 4) {
            iComputeInt64Size += CodedOutputStream.computeMessageSize(4, (IrBlockBody) this.statement_);
        }
        if (this.statementCase_ == 5) {
            iComputeInt64Size += CodedOutputStream.computeMessageSize(5, (IrBranch) this.statement_);
        }
        if (this.statementCase_ == 6) {
            iComputeInt64Size += CodedOutputStream.computeMessageSize(6, (IrCatch) this.statement_);
        }
        if (this.statementCase_ == 7) {
            iComputeInt64Size += CodedOutputStream.computeMessageSize(7, (IrSyntheticBody) this.statement_);
        }
        int size = iComputeInt64Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrStatement parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrStatement parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrStatement parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrStatement parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrStatement parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrStatement parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrStatement parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrStatement parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrStatement parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrStatement parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrStatement irStatement) {
        return newBuilder().mergeFrom(irStatement);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrStatement, Builder> implements IrStatementOrBuilder {
        private int bitField0_;
        private long coordinates_;
        private int statementCase_ = 0;
        private Object statement_;

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
            this.coordinates_ = 0L;
            this.bitField0_ &= -2;
            this.statementCase_ = 0;
            this.statement_ = null;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrStatement getDefaultInstanceForType() {
            return IrStatement.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrStatement build() {
            IrStatement irStatementBuildPartial = buildPartial();
            if (irStatementBuildPartial.isInitialized()) {
                return irStatementBuildPartial;
            }
            throw newUninitializedMessageException(irStatementBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrStatement buildPartial() {
            IrStatement irStatement = new IrStatement(this);
            int i = (this.bitField0_ & 1) != 1 ? 0 : 1;
            irStatement.coordinates_ = this.coordinates_;
            if (this.statementCase_ == 2) {
                irStatement.statement_ = this.statement_;
            }
            if (this.statementCase_ == 3) {
                irStatement.statement_ = this.statement_;
            }
            if (this.statementCase_ == 4) {
                irStatement.statement_ = this.statement_;
            }
            if (this.statementCase_ == 5) {
                irStatement.statement_ = this.statement_;
            }
            if (this.statementCase_ == 6) {
                irStatement.statement_ = this.statement_;
            }
            if (this.statementCase_ == 7) {
                irStatement.statement_ = this.statement_;
            }
            irStatement.bitField0_ = i;
            irStatement.statementCase_ = this.statementCase_;
            return irStatement;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrStatement irStatement) {
            if (irStatement == IrStatement.getDefaultInstance()) {
                return this;
            }
            if (irStatement.hasCoordinates()) {
                setCoordinates(irStatement.getCoordinates());
            }
            switch (AnonymousClass2.$SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrStatement$StatementCase[irStatement.getStatementCase().ordinal()]) {
                case 1:
                    mergeDeclaration(irStatement.getDeclaration());
                    break;
                case 2:
                    mergeExpression(irStatement.getExpression());
                    break;
                case 3:
                    mergeBlockBody(irStatement.getBlockBody());
                    break;
                case 4:
                    mergeBranch(irStatement.getBranch());
                    break;
                case 5:
                    mergeCatch(irStatement.getCatch());
                    break;
                case 6:
                    mergeSyntheticBody(irStatement.getSyntheticBody());
                    break;
            }
            setUnknownFields(getUnknownFields().concat(irStatement.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (!hasCoordinates()) {
                return false;
            }
            if (hasDeclaration() && !getDeclaration().isInitialized()) {
                return false;
            }
            if (hasExpression() && !getExpression().isInitialized()) {
                return false;
            }
            if (hasBlockBody() && !getBlockBody().isInitialized()) {
                return false;
            }
            if (hasBranch() && !getBranch().isInitialized()) {
                return false;
            }
            if (!hasCatch() || getCatch().isInitialized()) {
                return !hasSyntheticBody() || getSyntheticBody().isInitialized();
            }
            return false;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrStatement irStatement = null;
            try {
                try {
                    IrStatement partialFrom = IrStatement.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrStatement irStatement2 = (IrStatement) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irStatement = irStatement2;
                        if (irStatement != null) {
                            mergeFrom(irStatement);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irStatement != null) {
                    mergeFrom(irStatement);
                }
                throw th;
            }
        }

        public StatementCase getStatementCase() {
            return StatementCase.valueOf(this.statementCase_);
        }

        public Builder clearStatement() {
            this.statementCase_ = 0;
            this.statement_ = null;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
        public boolean hasCoordinates() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
        public long getCoordinates() {
            return this.coordinates_;
        }

        public Builder setCoordinates(long j) {
            this.bitField0_ |= 1;
            this.coordinates_ = j;
            return this;
        }

        public Builder clearCoordinates() {
            this.bitField0_ &= -2;
            this.coordinates_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
        public boolean hasDeclaration() {
            return this.statementCase_ == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
        public IrDeclaration getDeclaration() {
            if (this.statementCase_ == 2) {
                return (IrDeclaration) this.statement_;
            }
            return IrDeclaration.getDefaultInstance();
        }

        public Builder setDeclaration(IrDeclaration irDeclaration) {
            irDeclaration.getClass();
            this.statement_ = irDeclaration;
            this.statementCase_ = 2;
            return this;
        }

        public Builder setDeclaration(IrDeclaration.Builder builder) {
            this.statement_ = builder.build();
            this.statementCase_ = 2;
            return this;
        }

        public Builder mergeDeclaration(IrDeclaration irDeclaration) {
            if (this.statementCase_ == 2 && this.statement_ != IrDeclaration.getDefaultInstance()) {
                this.statement_ = IrDeclaration.newBuilder((IrDeclaration) this.statement_).mergeFrom(irDeclaration).buildPartial();
            } else {
                this.statement_ = irDeclaration;
            }
            this.statementCase_ = 2;
            return this;
        }

        public Builder clearDeclaration() {
            if (this.statementCase_ == 2) {
                this.statementCase_ = 0;
                this.statement_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
        public boolean hasExpression() {
            return this.statementCase_ == 3;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
        public IrExpression getExpression() {
            if (this.statementCase_ == 3) {
                return (IrExpression) this.statement_;
            }
            return IrExpression.getDefaultInstance();
        }

        public Builder setExpression(IrExpression irExpression) {
            irExpression.getClass();
            this.statement_ = irExpression;
            this.statementCase_ = 3;
            return this;
        }

        public Builder setExpression(IrExpression.Builder builder) {
            this.statement_ = builder.build();
            this.statementCase_ = 3;
            return this;
        }

        public Builder mergeExpression(IrExpression irExpression) {
            if (this.statementCase_ == 3 && this.statement_ != IrExpression.getDefaultInstance()) {
                this.statement_ = IrExpression.newBuilder((IrExpression) this.statement_).mergeFrom(irExpression).buildPartial();
            } else {
                this.statement_ = irExpression;
            }
            this.statementCase_ = 3;
            return this;
        }

        public Builder clearExpression() {
            if (this.statementCase_ == 3) {
                this.statementCase_ = 0;
                this.statement_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
        public boolean hasBlockBody() {
            return this.statementCase_ == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
        public IrBlockBody getBlockBody() {
            if (this.statementCase_ == 4) {
                return (IrBlockBody) this.statement_;
            }
            return IrBlockBody.getDefaultInstance();
        }

        public Builder setBlockBody(IrBlockBody irBlockBody) {
            irBlockBody.getClass();
            this.statement_ = irBlockBody;
            this.statementCase_ = 4;
            return this;
        }

        public Builder setBlockBody(IrBlockBody.Builder builder) {
            this.statement_ = builder.build();
            this.statementCase_ = 4;
            return this;
        }

        public Builder mergeBlockBody(IrBlockBody irBlockBody) {
            if (this.statementCase_ == 4 && this.statement_ != IrBlockBody.getDefaultInstance()) {
                this.statement_ = IrBlockBody.newBuilder((IrBlockBody) this.statement_).mergeFrom(irBlockBody).buildPartial();
            } else {
                this.statement_ = irBlockBody;
            }
            this.statementCase_ = 4;
            return this;
        }

        public Builder clearBlockBody() {
            if (this.statementCase_ == 4) {
                this.statementCase_ = 0;
                this.statement_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
        public boolean hasBranch() {
            return this.statementCase_ == 5;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
        public IrBranch getBranch() {
            if (this.statementCase_ == 5) {
                return (IrBranch) this.statement_;
            }
            return IrBranch.getDefaultInstance();
        }

        public Builder setBranch(IrBranch irBranch) {
            irBranch.getClass();
            this.statement_ = irBranch;
            this.statementCase_ = 5;
            return this;
        }

        public Builder setBranch(IrBranch.Builder builder) {
            this.statement_ = builder.build();
            this.statementCase_ = 5;
            return this;
        }

        public Builder mergeBranch(IrBranch irBranch) {
            if (this.statementCase_ == 5 && this.statement_ != IrBranch.getDefaultInstance()) {
                this.statement_ = IrBranch.newBuilder((IrBranch) this.statement_).mergeFrom(irBranch).buildPartial();
            } else {
                this.statement_ = irBranch;
            }
            this.statementCase_ = 5;
            return this;
        }

        public Builder clearBranch() {
            if (this.statementCase_ == 5) {
                this.statementCase_ = 0;
                this.statement_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
        public boolean hasCatch() {
            return this.statementCase_ == 6;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
        public IrCatch getCatch() {
            if (this.statementCase_ == 6) {
                return (IrCatch) this.statement_;
            }
            return IrCatch.getDefaultInstance();
        }

        public Builder setCatch(IrCatch irCatch) {
            irCatch.getClass();
            this.statement_ = irCatch;
            this.statementCase_ = 6;
            return this;
        }

        public Builder setCatch(IrCatch.Builder builder) {
            this.statement_ = builder.build();
            this.statementCase_ = 6;
            return this;
        }

        public Builder mergeCatch(IrCatch irCatch) {
            if (this.statementCase_ == 6 && this.statement_ != IrCatch.getDefaultInstance()) {
                this.statement_ = IrCatch.newBuilder((IrCatch) this.statement_).mergeFrom(irCatch).buildPartial();
            } else {
                this.statement_ = irCatch;
            }
            this.statementCase_ = 6;
            return this;
        }

        public Builder clearCatch() {
            if (this.statementCase_ == 6) {
                this.statementCase_ = 0;
                this.statement_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
        public boolean hasSyntheticBody() {
            return this.statementCase_ == 7;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrStatementOrBuilder
        public IrSyntheticBody getSyntheticBody() {
            if (this.statementCase_ == 7) {
                return (IrSyntheticBody) this.statement_;
            }
            return IrSyntheticBody.getDefaultInstance();
        }

        public Builder setSyntheticBody(IrSyntheticBody irSyntheticBody) {
            irSyntheticBody.getClass();
            this.statement_ = irSyntheticBody;
            this.statementCase_ = 7;
            return this;
        }

        public Builder setSyntheticBody(IrSyntheticBody.Builder builder) {
            this.statement_ = builder.build();
            this.statementCase_ = 7;
            return this;
        }

        public Builder mergeSyntheticBody(IrSyntheticBody irSyntheticBody) {
            if (this.statementCase_ == 7 && this.statement_ != IrSyntheticBody.getDefaultInstance()) {
                this.statement_ = IrSyntheticBody.newBuilder((IrSyntheticBody) this.statement_).mergeFrom(irSyntheticBody).buildPartial();
            } else {
                this.statement_ = irSyntheticBody;
            }
            this.statementCase_ = 7;
            return this;
        }

        public Builder clearSyntheticBody() {
            if (this.statementCase_ == 7) {
                this.statementCase_ = 0;
                this.statement_ = null;
            }
            return this;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.common.serialization.proto.IrStatement$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrStatement$StatementCase;

        static {
            int[] iArr = new int[StatementCase.values().length];
            $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrStatement$StatementCase = iArr;
            try {
                iArr[StatementCase.DECLARATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrStatement$StatementCase[StatementCase.EXPRESSION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrStatement$StatementCase[StatementCase.BLOCK_BODY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrStatement$StatementCase[StatementCase.BRANCH.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrStatement$StatementCase[StatementCase.CATCH.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrStatement$StatementCase[StatementCase.SYNTHETIC_BODY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrStatement$StatementCase[StatementCase.STATEMENT_NOT_SET.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }
}
