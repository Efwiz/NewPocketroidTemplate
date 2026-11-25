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
public final class IrWhen extends GeneratedMessageLite implements IrWhenOrBuilder {
    public static final int BRANCH_FIELD_NUMBER = 1;
    public static final int ORIGIN_NAME_FIELD_NUMBER = 2;
    public static Parser<IrWhen> PARSER = new AbstractParser<IrWhen>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrWhen.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrWhen parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrWhen(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrWhen defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private List<IrStatement> branch_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int originName_;
    private final ByteString unknownFields;

    private IrWhen(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrWhen(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrWhen getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrWhen getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrWhen(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                                    this.branch_ = new ArrayList();
                                    z2 = true;
                                }
                                this.branch_.add((IrStatement) codedInputStream.readMessage(IrStatement.PARSER, extensionRegistryLite));
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
                    this.branch_ = Collections.unmodifiableList(this.branch_);
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
            this.branch_ = Collections.unmodifiableList(this.branch_);
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
        IrWhen irWhen = new IrWhen(true);
        defaultInstance = irWhen;
        irWhen.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrWhen> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrWhenOrBuilder
    public List<IrStatement> getBranchList() {
        return this.branch_;
    }

    public List<? extends IrStatementOrBuilder> getBranchOrBuilderList() {
        return this.branch_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrWhenOrBuilder
    public int getBranchCount() {
        return this.branch_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrWhenOrBuilder
    public IrStatement getBranch(int i) {
        return this.branch_.get(i);
    }

    public IrStatementOrBuilder getBranchOrBuilder(int i) {
        return this.branch_.get(i);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrWhenOrBuilder
    public boolean hasOriginName() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrWhenOrBuilder
    public int getOriginName() {
        return this.originName_;
    }

    private void initFields() {
        this.branch_ = Collections.emptyList();
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
        for (int i = 0; i < getBranchCount(); i++) {
            if (!getBranch(i).isInitialized()) {
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
        for (int i = 0; i < this.branch_.size(); i++) {
            codedOutputStream.writeMessage(1, this.branch_.get(i));
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
        for (int i2 = 0; i2 < this.branch_.size(); i2++) {
            iComputeInt32Size += CodedOutputStream.computeMessageSize(1, this.branch_.get(i2));
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

    public static IrWhen parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrWhen parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrWhen parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrWhen parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrWhen parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrWhen parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrWhen parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrWhen parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrWhen parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrWhen parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrWhen irWhen) {
        return newBuilder().mergeFrom(irWhen);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrWhen, Builder> implements IrWhenOrBuilder {
        private int bitField0_;
        private List<IrStatement> branch_ = Collections.emptyList();
        private int originName_;

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
            this.branch_ = Collections.emptyList();
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
        public IrWhen getDefaultInstanceForType() {
            return IrWhen.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrWhen build() {
            IrWhen irWhenBuildPartial = buildPartial();
            if (irWhenBuildPartial.isInitialized()) {
                return irWhenBuildPartial;
            }
            throw newUninitializedMessageException(irWhenBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrWhen buildPartial() {
            IrWhen irWhen = new IrWhen(this);
            int i = this.bitField0_;
            if ((i & 1) == 1) {
                this.branch_ = Collections.unmodifiableList(this.branch_);
                this.bitField0_ &= -2;
            }
            irWhen.branch_ = this.branch_;
            int i2 = (i & 2) != 2 ? 0 : 1;
            irWhen.originName_ = this.originName_;
            irWhen.bitField0_ = i2;
            return irWhen;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrWhen irWhen) {
            if (irWhen == IrWhen.getDefaultInstance()) {
                return this;
            }
            if (!irWhen.branch_.isEmpty()) {
                if (this.branch_.isEmpty()) {
                    this.branch_ = irWhen.branch_;
                    this.bitField0_ &= -2;
                } else {
                    ensureBranchIsMutable();
                    this.branch_.addAll(irWhen.branch_);
                }
            }
            if (irWhen.hasOriginName()) {
                setOriginName(irWhen.getOriginName());
            }
            setUnknownFields(getUnknownFields().concat(irWhen.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            for (int i = 0; i < getBranchCount(); i++) {
                if (!getBranch(i).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrWhen irWhen = null;
            try {
                try {
                    IrWhen partialFrom = IrWhen.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrWhen irWhen2 = (IrWhen) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irWhen = irWhen2;
                        if (irWhen != null) {
                            mergeFrom(irWhen);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irWhen != null) {
                    mergeFrom(irWhen);
                }
                throw th;
            }
        }

        private void ensureBranchIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
                this.branch_ = new ArrayList(this.branch_);
                this.bitField0_ |= 1;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrWhenOrBuilder
        public List<IrStatement> getBranchList() {
            return Collections.unmodifiableList(this.branch_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrWhenOrBuilder
        public int getBranchCount() {
            return this.branch_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrWhenOrBuilder
        public IrStatement getBranch(int i) {
            return this.branch_.get(i);
        }

        public Builder setBranch(int i, IrStatement irStatement) {
            irStatement.getClass();
            ensureBranchIsMutable();
            this.branch_.set(i, irStatement);
            return this;
        }

        public Builder setBranch(int i, IrStatement.Builder builder) {
            ensureBranchIsMutable();
            this.branch_.set(i, builder.build());
            return this;
        }

        public Builder addBranch(IrStatement irStatement) {
            irStatement.getClass();
            ensureBranchIsMutable();
            this.branch_.add(irStatement);
            return this;
        }

        public Builder addBranch(int i, IrStatement irStatement) {
            irStatement.getClass();
            ensureBranchIsMutable();
            this.branch_.add(i, irStatement);
            return this;
        }

        public Builder addBranch(IrStatement.Builder builder) {
            ensureBranchIsMutable();
            this.branch_.add(builder.build());
            return this;
        }

        public Builder addBranch(int i, IrStatement.Builder builder) {
            ensureBranchIsMutable();
            this.branch_.add(i, builder.build());
            return this;
        }

        public Builder addAllBranch(Iterable<? extends IrStatement> iterable) {
            ensureBranchIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.branch_);
            return this;
        }

        public Builder clearBranch() {
            this.branch_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
        }

        public Builder removeBranch(int i) {
            ensureBranchIsMutable();
            this.branch_.remove(i);
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrWhenOrBuilder
        public boolean hasOriginName() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrWhenOrBuilder
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
