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
public final class IrFile extends GeneratedMessageLite implements IrFileOrBuilder {
    public static final int ACTUAL_FIELD_NUMBER = 6;
    public static final int ANNOTATION_FIELD_NUMBER = 4;
    public static final int DECLARATION_ID_FIELD_NUMBER = 1;
    public static final int EXPLICITLY_EXPORTED_TO_COMPILER_FIELD_NUMBER = 5;
    public static final int FILE_ENTRY_FIELD_NUMBER = 2;
    public static final int FQ_NAME_FIELD_NUMBER = 3;
    public static Parser<IrFile> PARSER = new AbstractParser<IrFile>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrFile.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrFile parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrFile(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrFile defaultInstance;
    private static final long serialVersionUID = 0;
    private List<Actual> actual_;
    private List<IrConstructorCall> annotation_;
    private int bitField0_;
    private int declarationIdMemoizedSerializedSize;
    private List<Integer> declarationId_;
    private int explicitlyExportedToCompilerMemoizedSerializedSize;
    private List<Long> explicitlyExportedToCompiler_;
    private FileEntry fileEntry_;
    private int fqNameMemoizedSerializedSize;
    private List<Integer> fqName_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    private IrFile(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.declarationIdMemoizedSerializedSize = -1;
        this.fqNameMemoizedSerializedSize = -1;
        this.explicitlyExportedToCompilerMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrFile(boolean z) {
        this.declarationIdMemoizedSerializedSize = -1;
        this.fqNameMemoizedSerializedSize = -1;
        this.explicitlyExportedToCompilerMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrFile getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrFile getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrFile(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.declarationIdMemoizedSerializedSize = -1;
        this.fqNameMemoizedSerializedSize = -1;
        this.explicitlyExportedToCompilerMemoizedSerializedSize = -1;
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
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 8) {
                                if ((i & 1) != 1) {
                                    this.declarationId_ = new ArrayList();
                                    i |= 1;
                                }
                                this.declarationId_.add(Integer.valueOf(codedInputStream.readInt32()));
                            } else if (tag == 10) {
                                int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                if ((i & 1) != 1 && codedInputStream.getBytesUntilLimit() > 0) {
                                    this.declarationId_ = new ArrayList();
                                    i |= 1;
                                }
                                while (codedInputStream.getBytesUntilLimit() > 0) {
                                    this.declarationId_.add(Integer.valueOf(codedInputStream.readInt32()));
                                }
                                codedInputStream.popLimit(iPushLimit);
                            } else if (tag == 18) {
                                FileEntry.Builder builder = (this.bitField0_ & 1) == 1 ? this.fileEntry_.toBuilder() : null;
                                FileEntry fileEntry = (FileEntry) codedInputStream.readMessage(FileEntry.PARSER, extensionRegistryLite);
                                this.fileEntry_ = fileEntry;
                                if (builder != null) {
                                    builder.mergeFrom(fileEntry);
                                    this.fileEntry_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 1;
                            } else if (tag == 24) {
                                if ((i & 4) != 4) {
                                    this.fqName_ = new ArrayList();
                                    i |= 4;
                                }
                                this.fqName_.add(Integer.valueOf(codedInputStream.readInt32()));
                            } else if (tag == 26) {
                                int iPushLimit2 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                if ((i & 4) != 4 && codedInputStream.getBytesUntilLimit() > 0) {
                                    this.fqName_ = new ArrayList();
                                    i |= 4;
                                }
                                while (codedInputStream.getBytesUntilLimit() > 0) {
                                    this.fqName_.add(Integer.valueOf(codedInputStream.readInt32()));
                                }
                                codedInputStream.popLimit(iPushLimit2);
                            } else if (tag == 34) {
                                if ((i & 8) != 8) {
                                    this.annotation_ = new ArrayList();
                                    i |= 8;
                                }
                                this.annotation_.add((IrConstructorCall) codedInputStream.readMessage(IrConstructorCall.PARSER, extensionRegistryLite));
                            } else if (tag == 40) {
                                if ((i & 16) != 16) {
                                    this.explicitlyExportedToCompiler_ = new ArrayList();
                                    i |= 16;
                                }
                                this.explicitlyExportedToCompiler_.add(Long.valueOf(codedInputStream.readInt64()));
                            } else if (tag == 42) {
                                int iPushLimit3 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                if ((i & 16) != 16 && codedInputStream.getBytesUntilLimit() > 0) {
                                    this.explicitlyExportedToCompiler_ = new ArrayList();
                                    i |= 16;
                                }
                                while (codedInputStream.getBytesUntilLimit() > 0) {
                                    this.explicitlyExportedToCompiler_.add(Long.valueOf(codedInputStream.readInt64()));
                                }
                                codedInputStream.popLimit(iPushLimit3);
                            } else if (tag != 50) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                if ((i & 32) != 32) {
                                    this.actual_ = new ArrayList();
                                    i |= 32;
                                }
                                this.actual_.add((Actual) codedInputStream.readMessage(Actual.PARSER, extensionRegistryLite));
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
                if ((i & 1) == 1) {
                    this.declarationId_ = Collections.unmodifiableList(this.declarationId_);
                }
                if ((i & 4) == 4) {
                    this.fqName_ = Collections.unmodifiableList(this.fqName_);
                }
                if ((i & 8) == 8) {
                    this.annotation_ = Collections.unmodifiableList(this.annotation_);
                }
                if ((i & 16) == 16) {
                    this.explicitlyExportedToCompiler_ = Collections.unmodifiableList(this.explicitlyExportedToCompiler_);
                }
                if ((i & 32) == 32) {
                    this.actual_ = Collections.unmodifiableList(this.actual_);
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
        if ((i & 1) == 1) {
            this.declarationId_ = Collections.unmodifiableList(this.declarationId_);
        }
        if ((i & 4) == 4) {
            this.fqName_ = Collections.unmodifiableList(this.fqName_);
        }
        if ((i & 8) == 8) {
            this.annotation_ = Collections.unmodifiableList(this.annotation_);
        }
        if ((i & 16) == 16) {
            this.explicitlyExportedToCompiler_ = Collections.unmodifiableList(this.explicitlyExportedToCompiler_);
        }
        if ((i & 32) == 32) {
            this.actual_ = Collections.unmodifiableList(this.actual_);
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
        IrFile irFile = new IrFile(true);
        defaultInstance = irFile;
        irFile.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrFile> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public List<Integer> getDeclarationIdList() {
        return this.declarationId_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public int getDeclarationIdCount() {
        return this.declarationId_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public int getDeclarationId(int i) {
        return this.declarationId_.get(i).intValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public boolean hasFileEntry() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public FileEntry getFileEntry() {
        return this.fileEntry_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public List<Integer> getFqNameList() {
        return this.fqName_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public int getFqNameCount() {
        return this.fqName_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public int getFqName(int i) {
        return this.fqName_.get(i).intValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public List<IrConstructorCall> getAnnotationList() {
        return this.annotation_;
    }

    public List<? extends IrConstructorCallOrBuilder> getAnnotationOrBuilderList() {
        return this.annotation_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public int getAnnotationCount() {
        return this.annotation_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public IrConstructorCall getAnnotation(int i) {
        return this.annotation_.get(i);
    }

    public IrConstructorCallOrBuilder getAnnotationOrBuilder(int i) {
        return this.annotation_.get(i);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public List<Long> getExplicitlyExportedToCompilerList() {
        return this.explicitlyExportedToCompiler_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public int getExplicitlyExportedToCompilerCount() {
        return this.explicitlyExportedToCompiler_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public long getExplicitlyExportedToCompiler(int i) {
        return this.explicitlyExportedToCompiler_.get(i).longValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public List<Actual> getActualList() {
        return this.actual_;
    }

    public List<? extends ActualOrBuilder> getActualOrBuilderList() {
        return this.actual_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public int getActualCount() {
        return this.actual_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
    public Actual getActual(int i) {
        return this.actual_.get(i);
    }

    public ActualOrBuilder getActualOrBuilder(int i) {
        return this.actual_.get(i);
    }

    private void initFields() {
        this.declarationId_ = Collections.emptyList();
        this.fileEntry_ = FileEntry.getDefaultInstance();
        this.fqName_ = Collections.emptyList();
        this.annotation_ = Collections.emptyList();
        this.explicitlyExportedToCompiler_ = Collections.emptyList();
        this.actual_ = Collections.emptyList();
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
        if (!hasFileEntry()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (!getFileEntry().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        for (int i = 0; i < getAnnotationCount(); i++) {
            if (!getAnnotation(i).isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
        }
        for (int i2 = 0; i2 < getActualCount(); i2++) {
            if (!getActual(i2).isInitialized()) {
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
        if (getDeclarationIdList().size() > 0) {
            codedOutputStream.writeRawVarint32(10);
            codedOutputStream.writeRawVarint32(this.declarationIdMemoizedSerializedSize);
        }
        for (int i = 0; i < this.declarationId_.size(); i++) {
            codedOutputStream.writeInt32NoTag(this.declarationId_.get(i).intValue());
        }
        if ((this.bitField0_ & 1) == 1) {
            codedOutputStream.writeMessage(2, this.fileEntry_);
        }
        if (getFqNameList().size() > 0) {
            codedOutputStream.writeRawVarint32(26);
            codedOutputStream.writeRawVarint32(this.fqNameMemoizedSerializedSize);
        }
        for (int i2 = 0; i2 < this.fqName_.size(); i2++) {
            codedOutputStream.writeInt32NoTag(this.fqName_.get(i2).intValue());
        }
        for (int i3 = 0; i3 < this.annotation_.size(); i3++) {
            codedOutputStream.writeMessage(4, this.annotation_.get(i3));
        }
        if (getExplicitlyExportedToCompilerList().size() > 0) {
            codedOutputStream.writeRawVarint32(42);
            codedOutputStream.writeRawVarint32(this.explicitlyExportedToCompilerMemoizedSerializedSize);
        }
        for (int i4 = 0; i4 < this.explicitlyExportedToCompiler_.size(); i4++) {
            codedOutputStream.writeInt64NoTag(this.explicitlyExportedToCompiler_.get(i4).longValue());
        }
        for (int i5 = 0; i5 < this.actual_.size(); i5++) {
            codedOutputStream.writeMessage(6, this.actual_.get(i5));
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt32SizeNoTag = 0;
        for (int i2 = 0; i2 < this.declarationId_.size(); i2++) {
            iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.declarationId_.get(i2).intValue());
        }
        int iComputeInt32SizeNoTag2 = !getDeclarationIdList().isEmpty() ? iComputeInt32SizeNoTag + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag) : iComputeInt32SizeNoTag;
        this.declarationIdMemoizedSerializedSize = iComputeInt32SizeNoTag;
        if ((this.bitField0_ & 1) == 1) {
            iComputeInt32SizeNoTag2 += CodedOutputStream.computeMessageSize(2, this.fileEntry_);
        }
        int iComputeInt32SizeNoTag3 = 0;
        for (int i3 = 0; i3 < this.fqName_.size(); i3++) {
            iComputeInt32SizeNoTag3 += CodedOutputStream.computeInt32SizeNoTag(this.fqName_.get(i3).intValue());
        }
        int iComputeMessageSize = iComputeInt32SizeNoTag2 + iComputeInt32SizeNoTag3;
        if (!getFqNameList().isEmpty()) {
            iComputeMessageSize = iComputeMessageSize + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag3);
        }
        this.fqNameMemoizedSerializedSize = iComputeInt32SizeNoTag3;
        for (int i4 = 0; i4 < this.annotation_.size(); i4++) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(4, this.annotation_.get(i4));
        }
        int iComputeInt64SizeNoTag = 0;
        for (int i5 = 0; i5 < this.explicitlyExportedToCompiler_.size(); i5++) {
            iComputeInt64SizeNoTag += CodedOutputStream.computeInt64SizeNoTag(this.explicitlyExportedToCompiler_.get(i5).longValue());
        }
        int iComputeMessageSize2 = iComputeMessageSize + iComputeInt64SizeNoTag;
        if (!getExplicitlyExportedToCompilerList().isEmpty()) {
            iComputeMessageSize2 = iComputeMessageSize2 + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt64SizeNoTag);
        }
        this.explicitlyExportedToCompilerMemoizedSerializedSize = iComputeInt64SizeNoTag;
        for (int i6 = 0; i6 < this.actual_.size(); i6++) {
            iComputeMessageSize2 += CodedOutputStream.computeMessageSize(6, this.actual_.get(i6));
        }
        int size = iComputeMessageSize2 + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrFile parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrFile parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrFile parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrFile parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrFile parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrFile parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrFile parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrFile parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrFile parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrFile parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrFile irFile) {
        return newBuilder().mergeFrom(irFile);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrFile, Builder> implements IrFileOrBuilder {
        private int bitField0_;
        private List<Integer> declarationId_ = Collections.emptyList();
        private FileEntry fileEntry_ = FileEntry.getDefaultInstance();
        private List<Integer> fqName_ = Collections.emptyList();
        private List<IrConstructorCall> annotation_ = Collections.emptyList();
        private List<Long> explicitlyExportedToCompiler_ = Collections.emptyList();
        private List<Actual> actual_ = Collections.emptyList();

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
            this.declarationId_ = Collections.emptyList();
            this.bitField0_ &= -2;
            this.fileEntry_ = FileEntry.getDefaultInstance();
            this.bitField0_ &= -3;
            this.fqName_ = Collections.emptyList();
            this.bitField0_ &= -5;
            this.annotation_ = Collections.emptyList();
            this.bitField0_ &= -9;
            this.explicitlyExportedToCompiler_ = Collections.emptyList();
            this.bitField0_ &= -17;
            this.actual_ = Collections.emptyList();
            this.bitField0_ &= -33;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrFile getDefaultInstanceForType() {
            return IrFile.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrFile build() {
            IrFile irFileBuildPartial = buildPartial();
            if (irFileBuildPartial.isInitialized()) {
                return irFileBuildPartial;
            }
            throw newUninitializedMessageException(irFileBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrFile buildPartial() {
            IrFile irFile = new IrFile(this);
            int i = this.bitField0_;
            if ((i & 1) == 1) {
                this.declarationId_ = Collections.unmodifiableList(this.declarationId_);
                this.bitField0_ &= -2;
            }
            irFile.declarationId_ = this.declarationId_;
            int i2 = (i & 2) != 2 ? 0 : 1;
            irFile.fileEntry_ = this.fileEntry_;
            if ((this.bitField0_ & 4) == 4) {
                this.fqName_ = Collections.unmodifiableList(this.fqName_);
                this.bitField0_ &= -5;
            }
            irFile.fqName_ = this.fqName_;
            if ((this.bitField0_ & 8) == 8) {
                this.annotation_ = Collections.unmodifiableList(this.annotation_);
                this.bitField0_ &= -9;
            }
            irFile.annotation_ = this.annotation_;
            if ((this.bitField0_ & 16) == 16) {
                this.explicitlyExportedToCompiler_ = Collections.unmodifiableList(this.explicitlyExportedToCompiler_);
                this.bitField0_ &= -17;
            }
            irFile.explicitlyExportedToCompiler_ = this.explicitlyExportedToCompiler_;
            if ((this.bitField0_ & 32) == 32) {
                this.actual_ = Collections.unmodifiableList(this.actual_);
                this.bitField0_ &= -33;
            }
            irFile.actual_ = this.actual_;
            irFile.bitField0_ = i2;
            return irFile;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrFile irFile) {
            if (irFile == IrFile.getDefaultInstance()) {
                return this;
            }
            if (!irFile.declarationId_.isEmpty()) {
                if (this.declarationId_.isEmpty()) {
                    this.declarationId_ = irFile.declarationId_;
                    this.bitField0_ &= -2;
                } else {
                    ensureDeclarationIdIsMutable();
                    this.declarationId_.addAll(irFile.declarationId_);
                }
            }
            if (irFile.hasFileEntry()) {
                mergeFileEntry(irFile.getFileEntry());
            }
            if (!irFile.fqName_.isEmpty()) {
                if (this.fqName_.isEmpty()) {
                    this.fqName_ = irFile.fqName_;
                    this.bitField0_ &= -5;
                } else {
                    ensureFqNameIsMutable();
                    this.fqName_.addAll(irFile.fqName_);
                }
            }
            if (!irFile.annotation_.isEmpty()) {
                if (this.annotation_.isEmpty()) {
                    this.annotation_ = irFile.annotation_;
                    this.bitField0_ &= -9;
                } else {
                    ensureAnnotationIsMutable();
                    this.annotation_.addAll(irFile.annotation_);
                }
            }
            if (!irFile.explicitlyExportedToCompiler_.isEmpty()) {
                if (this.explicitlyExportedToCompiler_.isEmpty()) {
                    this.explicitlyExportedToCompiler_ = irFile.explicitlyExportedToCompiler_;
                    this.bitField0_ &= -17;
                } else {
                    ensureExplicitlyExportedToCompilerIsMutable();
                    this.explicitlyExportedToCompiler_.addAll(irFile.explicitlyExportedToCompiler_);
                }
            }
            if (!irFile.actual_.isEmpty()) {
                if (this.actual_.isEmpty()) {
                    this.actual_ = irFile.actual_;
                    this.bitField0_ &= -33;
                } else {
                    ensureActualIsMutable();
                    this.actual_.addAll(irFile.actual_);
                }
            }
            setUnknownFields(getUnknownFields().concat(irFile.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (!hasFileEntry() || !getFileEntry().isInitialized()) {
                return false;
            }
            for (int i = 0; i < getAnnotationCount(); i++) {
                if (!getAnnotation(i).isInitialized()) {
                    return false;
                }
            }
            for (int i2 = 0; i2 < getActualCount(); i2++) {
                if (!getActual(i2).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrFile irFile = null;
            try {
                try {
                    IrFile partialFrom = IrFile.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrFile irFile2 = (IrFile) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irFile = irFile2;
                        if (irFile != null) {
                            mergeFrom(irFile);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irFile != null) {
                    mergeFrom(irFile);
                }
                throw th;
            }
        }

        private void ensureDeclarationIdIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
                this.declarationId_ = new ArrayList(this.declarationId_);
                this.bitField0_ |= 1;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public List<Integer> getDeclarationIdList() {
            return Collections.unmodifiableList(this.declarationId_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public int getDeclarationIdCount() {
            return this.declarationId_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public int getDeclarationId(int i) {
            return this.declarationId_.get(i).intValue();
        }

        public Builder setDeclarationId(int i, int i2) {
            ensureDeclarationIdIsMutable();
            this.declarationId_.set(i, Integer.valueOf(i2));
            return this;
        }

        public Builder addDeclarationId(int i) {
            ensureDeclarationIdIsMutable();
            this.declarationId_.add(Integer.valueOf(i));
            return this;
        }

        public Builder addAllDeclarationId(Iterable<? extends Integer> iterable) {
            ensureDeclarationIdIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.declarationId_);
            return this;
        }

        public Builder clearDeclarationId() {
            this.declarationId_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public boolean hasFileEntry() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public FileEntry getFileEntry() {
            return this.fileEntry_;
        }

        public Builder setFileEntry(FileEntry fileEntry) {
            fileEntry.getClass();
            this.fileEntry_ = fileEntry;
            this.bitField0_ |= 2;
            return this;
        }

        public Builder setFileEntry(FileEntry.Builder builder) {
            this.fileEntry_ = builder.build();
            this.bitField0_ |= 2;
            return this;
        }

        public Builder mergeFileEntry(FileEntry fileEntry) {
            if ((this.bitField0_ & 2) == 2 && this.fileEntry_ != FileEntry.getDefaultInstance()) {
                this.fileEntry_ = FileEntry.newBuilder(this.fileEntry_).mergeFrom(fileEntry).buildPartial();
            } else {
                this.fileEntry_ = fileEntry;
            }
            this.bitField0_ |= 2;
            return this;
        }

        public Builder clearFileEntry() {
            this.fileEntry_ = FileEntry.getDefaultInstance();
            this.bitField0_ &= -3;
            return this;
        }

        private void ensureFqNameIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
                this.fqName_ = new ArrayList(this.fqName_);
                this.bitField0_ |= 4;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public List<Integer> getFqNameList() {
            return Collections.unmodifiableList(this.fqName_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public int getFqNameCount() {
            return this.fqName_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public int getFqName(int i) {
            return this.fqName_.get(i).intValue();
        }

        public Builder setFqName(int i, int i2) {
            ensureFqNameIsMutable();
            this.fqName_.set(i, Integer.valueOf(i2));
            return this;
        }

        public Builder addFqName(int i) {
            ensureFqNameIsMutable();
            this.fqName_.add(Integer.valueOf(i));
            return this;
        }

        public Builder addAllFqName(Iterable<? extends Integer> iterable) {
            ensureFqNameIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.fqName_);
            return this;
        }

        public Builder clearFqName() {
            this.fqName_ = Collections.emptyList();
            this.bitField0_ &= -5;
            return this;
        }

        private void ensureAnnotationIsMutable() {
            if ((this.bitField0_ & 8) != 8) {
                this.annotation_ = new ArrayList(this.annotation_);
                this.bitField0_ |= 8;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public List<IrConstructorCall> getAnnotationList() {
            return Collections.unmodifiableList(this.annotation_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public int getAnnotationCount() {
            return this.annotation_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public IrConstructorCall getAnnotation(int i) {
            return this.annotation_.get(i);
        }

        public Builder setAnnotation(int i, IrConstructorCall irConstructorCall) {
            irConstructorCall.getClass();
            ensureAnnotationIsMutable();
            this.annotation_.set(i, irConstructorCall);
            return this;
        }

        public Builder setAnnotation(int i, IrConstructorCall.Builder builder) {
            ensureAnnotationIsMutable();
            this.annotation_.set(i, builder.build());
            return this;
        }

        public Builder addAnnotation(IrConstructorCall irConstructorCall) {
            irConstructorCall.getClass();
            ensureAnnotationIsMutable();
            this.annotation_.add(irConstructorCall);
            return this;
        }

        public Builder addAnnotation(int i, IrConstructorCall irConstructorCall) {
            irConstructorCall.getClass();
            ensureAnnotationIsMutable();
            this.annotation_.add(i, irConstructorCall);
            return this;
        }

        public Builder addAnnotation(IrConstructorCall.Builder builder) {
            ensureAnnotationIsMutable();
            this.annotation_.add(builder.build());
            return this;
        }

        public Builder addAnnotation(int i, IrConstructorCall.Builder builder) {
            ensureAnnotationIsMutable();
            this.annotation_.add(i, builder.build());
            return this;
        }

        public Builder addAllAnnotation(Iterable<? extends IrConstructorCall> iterable) {
            ensureAnnotationIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.annotation_);
            return this;
        }

        public Builder clearAnnotation() {
            this.annotation_ = Collections.emptyList();
            this.bitField0_ &= -9;
            return this;
        }

        public Builder removeAnnotation(int i) {
            ensureAnnotationIsMutable();
            this.annotation_.remove(i);
            return this;
        }

        private void ensureExplicitlyExportedToCompilerIsMutable() {
            if ((this.bitField0_ & 16) != 16) {
                this.explicitlyExportedToCompiler_ = new ArrayList(this.explicitlyExportedToCompiler_);
                this.bitField0_ |= 16;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public List<Long> getExplicitlyExportedToCompilerList() {
            return Collections.unmodifiableList(this.explicitlyExportedToCompiler_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public int getExplicitlyExportedToCompilerCount() {
            return this.explicitlyExportedToCompiler_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public long getExplicitlyExportedToCompiler(int i) {
            return this.explicitlyExportedToCompiler_.get(i).longValue();
        }

        public Builder setExplicitlyExportedToCompiler(int i, long j) {
            ensureExplicitlyExportedToCompilerIsMutable();
            this.explicitlyExportedToCompiler_.set(i, Long.valueOf(j));
            return this;
        }

        public Builder addExplicitlyExportedToCompiler(long j) {
            ensureExplicitlyExportedToCompilerIsMutable();
            this.explicitlyExportedToCompiler_.add(Long.valueOf(j));
            return this;
        }

        public Builder addAllExplicitlyExportedToCompiler(Iterable<? extends Long> iterable) {
            ensureExplicitlyExportedToCompilerIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.explicitlyExportedToCompiler_);
            return this;
        }

        public Builder clearExplicitlyExportedToCompiler() {
            this.explicitlyExportedToCompiler_ = Collections.emptyList();
            this.bitField0_ &= -17;
            return this;
        }

        private void ensureActualIsMutable() {
            if ((this.bitField0_ & 32) != 32) {
                this.actual_ = new ArrayList(this.actual_);
                this.bitField0_ |= 32;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public List<Actual> getActualList() {
            return Collections.unmodifiableList(this.actual_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public int getActualCount() {
            return this.actual_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrFileOrBuilder
        public Actual getActual(int i) {
            return this.actual_.get(i);
        }

        public Builder setActual(int i, Actual actual) {
            actual.getClass();
            ensureActualIsMutable();
            this.actual_.set(i, actual);
            return this;
        }

        public Builder setActual(int i, Actual.Builder builder) {
            ensureActualIsMutable();
            this.actual_.set(i, builder.build());
            return this;
        }

        public Builder addActual(Actual actual) {
            actual.getClass();
            ensureActualIsMutable();
            this.actual_.add(actual);
            return this;
        }

        public Builder addActual(int i, Actual actual) {
            actual.getClass();
            ensureActualIsMutable();
            this.actual_.add(i, actual);
            return this;
        }

        public Builder addActual(Actual.Builder builder) {
            ensureActualIsMutable();
            this.actual_.add(builder.build());
            return this;
        }

        public Builder addActual(int i, Actual.Builder builder) {
            ensureActualIsMutable();
            this.actual_.add(i, builder.build());
            return this;
        }

        public Builder addAllActual(Iterable<? extends Actual> iterable) {
            ensureActualIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.actual_);
            return this;
        }

        public Builder clearActual() {
            this.actual_ = Collections.emptyList();
            this.bitField0_ &= -33;
            return this;
        }

        public Builder removeActual(int i) {
            ensureActualIsMutable();
            this.actual_.remove(i);
            return this;
        }
    }
}
