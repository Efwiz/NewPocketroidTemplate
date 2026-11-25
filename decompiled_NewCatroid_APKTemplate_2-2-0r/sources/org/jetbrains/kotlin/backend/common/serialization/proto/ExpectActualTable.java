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
public final class ExpectActualTable extends GeneratedMessageLite implements ExpectActualTableOrBuilder {
    public static final int EXPECTTOACTUALS_FIELD_NUMBER = 1;
    public static Parser<ExpectActualTable> PARSER = new AbstractParser<ExpectActualTable>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.ExpectActualTable.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public ExpectActualTable parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new ExpectActualTable(codedInputStream, extensionRegistryLite);
        }
    };
    private static final ExpectActualTable defaultInstance;
    private static final long serialVersionUID = 0;
    private List<ExpectToActual> expectToActuals_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    private ExpectActualTable(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private ExpectActualTable(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static ExpectActualTable getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public ExpectActualTable getDefaultInstanceForType() {
        return defaultInstance;
    }

    private ExpectActualTable(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                                    this.expectToActuals_ = new ArrayList();
                                    z2 = true;
                                }
                                this.expectToActuals_.add((ExpectToActual) codedInputStream.readMessage(ExpectToActual.PARSER, extensionRegistryLite));
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
                    this.expectToActuals_ = Collections.unmodifiableList(this.expectToActuals_);
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
            this.expectToActuals_ = Collections.unmodifiableList(this.expectToActuals_);
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
        ExpectActualTable expectActualTable = new ExpectActualTable(true);
        defaultInstance = expectActualTable;
        expectActualTable.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<ExpectActualTable> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.ExpectActualTableOrBuilder
    public List<ExpectToActual> getExpectToActualsList() {
        return this.expectToActuals_;
    }

    public List<? extends ExpectToActualOrBuilder> getExpectToActualsOrBuilderList() {
        return this.expectToActuals_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.ExpectActualTableOrBuilder
    public int getExpectToActualsCount() {
        return this.expectToActuals_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.ExpectActualTableOrBuilder
    public ExpectToActual getExpectToActuals(int i) {
        return this.expectToActuals_.get(i);
    }

    public ExpectToActualOrBuilder getExpectToActualsOrBuilder(int i) {
        return this.expectToActuals_.get(i);
    }

    private void initFields() {
        this.expectToActuals_ = Collections.emptyList();
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
        for (int i = 0; i < getExpectToActualsCount(); i++) {
            if (!getExpectToActuals(i).isInitialized()) {
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
        for (int i = 0; i < this.expectToActuals_.size(); i++) {
            codedOutputStream.writeMessage(1, this.expectToActuals_.get(i));
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
        for (int i2 = 0; i2 < this.expectToActuals_.size(); i2++) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.expectToActuals_.get(i2));
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static ExpectActualTable parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static ExpectActualTable parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static ExpectActualTable parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static ExpectActualTable parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static ExpectActualTable parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static ExpectActualTable parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static ExpectActualTable parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static ExpectActualTable parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static ExpectActualTable parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static ExpectActualTable parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(ExpectActualTable expectActualTable) {
        return newBuilder().mergeFrom(expectActualTable);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<ExpectActualTable, Builder> implements ExpectActualTableOrBuilder {
        private int bitField0_;
        private List<ExpectToActual> expectToActuals_ = Collections.emptyList();

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
            this.expectToActuals_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public ExpectActualTable getDefaultInstanceForType() {
            return ExpectActualTable.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public ExpectActualTable build() {
            ExpectActualTable expectActualTableBuildPartial = buildPartial();
            if (expectActualTableBuildPartial.isInitialized()) {
                return expectActualTableBuildPartial;
            }
            throw newUninitializedMessageException(expectActualTableBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public ExpectActualTable buildPartial() {
            ExpectActualTable expectActualTable = new ExpectActualTable(this);
            if ((this.bitField0_ & 1) == 1) {
                this.expectToActuals_ = Collections.unmodifiableList(this.expectToActuals_);
                this.bitField0_ &= -2;
            }
            expectActualTable.expectToActuals_ = this.expectToActuals_;
            return expectActualTable;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(ExpectActualTable expectActualTable) {
            if (expectActualTable == ExpectActualTable.getDefaultInstance()) {
                return this;
            }
            if (!expectActualTable.expectToActuals_.isEmpty()) {
                if (this.expectToActuals_.isEmpty()) {
                    this.expectToActuals_ = expectActualTable.expectToActuals_;
                    this.bitField0_ &= -2;
                } else {
                    ensureExpectToActualsIsMutable();
                    this.expectToActuals_.addAll(expectActualTable.expectToActuals_);
                }
            }
            setUnknownFields(getUnknownFields().concat(expectActualTable.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            for (int i = 0; i < getExpectToActualsCount(); i++) {
                if (!getExpectToActuals(i).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            ExpectActualTable expectActualTable = null;
            try {
                try {
                    ExpectActualTable partialFrom = ExpectActualTable.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    ExpectActualTable expectActualTable2 = (ExpectActualTable) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        expectActualTable = expectActualTable2;
                        if (expectActualTable != null) {
                            mergeFrom(expectActualTable);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (expectActualTable != null) {
                    mergeFrom(expectActualTable);
                }
                throw th;
            }
        }

        private void ensureExpectToActualsIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
                this.expectToActuals_ = new ArrayList(this.expectToActuals_);
                this.bitField0_ |= 1;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.ExpectActualTableOrBuilder
        public List<ExpectToActual> getExpectToActualsList() {
            return Collections.unmodifiableList(this.expectToActuals_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.ExpectActualTableOrBuilder
        public int getExpectToActualsCount() {
            return this.expectToActuals_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.ExpectActualTableOrBuilder
        public ExpectToActual getExpectToActuals(int i) {
            return this.expectToActuals_.get(i);
        }

        public Builder setExpectToActuals(int i, ExpectToActual expectToActual) {
            expectToActual.getClass();
            ensureExpectToActualsIsMutable();
            this.expectToActuals_.set(i, expectToActual);
            return this;
        }

        public Builder setExpectToActuals(int i, ExpectToActual.Builder builder) {
            ensureExpectToActualsIsMutable();
            this.expectToActuals_.set(i, builder.build());
            return this;
        }

        public Builder addExpectToActuals(ExpectToActual expectToActual) {
            expectToActual.getClass();
            ensureExpectToActualsIsMutable();
            this.expectToActuals_.add(expectToActual);
            return this;
        }

        public Builder addExpectToActuals(int i, ExpectToActual expectToActual) {
            expectToActual.getClass();
            ensureExpectToActualsIsMutable();
            this.expectToActuals_.add(i, expectToActual);
            return this;
        }

        public Builder addExpectToActuals(ExpectToActual.Builder builder) {
            ensureExpectToActualsIsMutable();
            this.expectToActuals_.add(builder.build());
            return this;
        }

        public Builder addExpectToActuals(int i, ExpectToActual.Builder builder) {
            ensureExpectToActualsIsMutable();
            this.expectToActuals_.add(i, builder.build());
            return this;
        }

        public Builder addAllExpectToActuals(Iterable<? extends ExpectToActual> iterable) {
            ensureExpectToActualsIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.expectToActuals_);
            return this;
        }

        public Builder clearExpectToActuals() {
            this.expectToActuals_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
        }

        public Builder removeExpectToActuals(int i) {
            ensureExpectToActualsIsMutable();
            this.expectToActuals_.remove(i);
            return this;
        }
    }
}
