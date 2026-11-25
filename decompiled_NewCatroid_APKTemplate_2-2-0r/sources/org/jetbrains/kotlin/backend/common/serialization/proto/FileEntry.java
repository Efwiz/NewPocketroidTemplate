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
public final class FileEntry extends GeneratedMessageLite implements FileEntryOrBuilder {
    public static final int LINE_START_OFFSET_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    public static Parser<FileEntry> PARSER = new AbstractParser<FileEntry>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.FileEntry.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public FileEntry parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new FileEntry(codedInputStream, extensionRegistryLite);
        }
    };
    private static final FileEntry defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private int lineStartOffsetMemoizedSerializedSize;
    private List<Integer> lineStartOffset_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private Object name_;
    private final ByteString unknownFields;

    private FileEntry(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.lineStartOffsetMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private FileEntry(boolean z) {
        this.lineStartOffsetMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static FileEntry getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public FileEntry getDefaultInstanceForType() {
        return defaultInstance;
    }

    private FileEntry(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.lineStartOffsetMemoizedSerializedSize = -1;
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
                            ByteString bytes = codedInputStream.readBytes();
                            this.bitField0_ |= 1;
                            this.name_ = bytes;
                        } else if (tag == 16) {
                            if ((c & 2) != 2) {
                                this.lineStartOffset_ = new ArrayList();
                                c = 2;
                            }
                            this.lineStartOffset_.add(Integer.valueOf(codedInputStream.readInt32()));
                        } else if (tag != 18) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                            if ((c & 2) != 2 && codedInputStream.getBytesUntilLimit() > 0) {
                                this.lineStartOffset_ = new ArrayList();
                                c = 2;
                            }
                            while (codedInputStream.getBytesUntilLimit() > 0) {
                                this.lineStartOffset_.add(Integer.valueOf(codedInputStream.readInt32()));
                            }
                            codedInputStream.popLimit(iPushLimit);
                        }
                    }
                    z = true;
                } catch (Throwable th) {
                    if ((c & 2) == 2) {
                        this.lineStartOffset_ = Collections.unmodifiableList(this.lineStartOffset_);
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
        if ((c & 2) == 2) {
            this.lineStartOffset_ = Collections.unmodifiableList(this.lineStartOffset_);
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
        FileEntry fileEntry = new FileEntry(true);
        defaultInstance = fileEntry;
        fileEntry.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<FileEntry> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileEntryOrBuilder
    public boolean hasName() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileEntryOrBuilder
    public String getName() {
        Object obj = this.name_;
        if (obj instanceof String) {
            return (String) obj;
        }
        ByteString byteString = (ByteString) obj;
        String stringUtf8 = byteString.toStringUtf8();
        if (byteString.isValidUtf8()) {
            this.name_ = stringUtf8;
        }
        return stringUtf8;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileEntryOrBuilder
    public ByteString getNameBytes() {
        Object obj = this.name_;
        if (obj instanceof String) {
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.name_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }
        return (ByteString) obj;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileEntryOrBuilder
    public List<Integer> getLineStartOffsetList() {
        return this.lineStartOffset_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileEntryOrBuilder
    public int getLineStartOffsetCount() {
        return this.lineStartOffset_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileEntryOrBuilder
    public int getLineStartOffset(int i) {
        return this.lineStartOffset_.get(i).intValue();
    }

    private void initFields() {
        this.name_ = "";
        this.lineStartOffset_ = Collections.emptyList();
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
        if (!hasName()) {
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
            codedOutputStream.writeBytes(1, getNameBytes());
        }
        if (getLineStartOffsetList().size() > 0) {
            codedOutputStream.writeRawVarint32(18);
            codedOutputStream.writeRawVarint32(this.lineStartOffsetMemoizedSerializedSize);
        }
        for (int i = 0; i < this.lineStartOffset_.size(); i++) {
            codedOutputStream.writeInt32NoTag(this.lineStartOffset_.get(i).intValue());
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeBytesSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBytesSize(1, getNameBytes()) : 0;
        int iComputeInt32SizeNoTag = 0;
        for (int i2 = 0; i2 < this.lineStartOffset_.size(); i2++) {
            iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.lineStartOffset_.get(i2).intValue());
        }
        int iComputeInt32SizeNoTag2 = iComputeBytesSize + iComputeInt32SizeNoTag;
        if (!getLineStartOffsetList().isEmpty()) {
            iComputeInt32SizeNoTag2 = iComputeInt32SizeNoTag2 + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag);
        }
        this.lineStartOffsetMemoizedSerializedSize = iComputeInt32SizeNoTag;
        int size = iComputeInt32SizeNoTag2 + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static FileEntry parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static FileEntry parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static FileEntry parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static FileEntry parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static FileEntry parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static FileEntry parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static FileEntry parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static FileEntry parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static FileEntry parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static FileEntry parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(FileEntry fileEntry) {
        return newBuilder().mergeFrom(fileEntry);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<FileEntry, Builder> implements FileEntryOrBuilder {
        private int bitField0_;
        private Object name_ = "";
        private List<Integer> lineStartOffset_ = Collections.emptyList();

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
            this.name_ = "";
            this.bitField0_ &= -2;
            this.lineStartOffset_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public FileEntry getDefaultInstanceForType() {
            return FileEntry.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public FileEntry build() {
            FileEntry fileEntryBuildPartial = buildPartial();
            if (fileEntryBuildPartial.isInitialized()) {
                return fileEntryBuildPartial;
            }
            throw newUninitializedMessageException(fileEntryBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public FileEntry buildPartial() {
            FileEntry fileEntry = new FileEntry(this);
            int i = (this.bitField0_ & 1) != 1 ? 0 : 1;
            fileEntry.name_ = this.name_;
            if ((this.bitField0_ & 2) == 2) {
                this.lineStartOffset_ = Collections.unmodifiableList(this.lineStartOffset_);
                this.bitField0_ &= -3;
            }
            fileEntry.lineStartOffset_ = this.lineStartOffset_;
            fileEntry.bitField0_ = i;
            return fileEntry;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(FileEntry fileEntry) {
            if (fileEntry == FileEntry.getDefaultInstance()) {
                return this;
            }
            if (fileEntry.hasName()) {
                this.bitField0_ |= 1;
                this.name_ = fileEntry.name_;
            }
            if (!fileEntry.lineStartOffset_.isEmpty()) {
                if (this.lineStartOffset_.isEmpty()) {
                    this.lineStartOffset_ = fileEntry.lineStartOffset_;
                    this.bitField0_ &= -3;
                } else {
                    ensureLineStartOffsetIsMutable();
                    this.lineStartOffset_.addAll(fileEntry.lineStartOffset_);
                }
            }
            setUnknownFields(getUnknownFields().concat(fileEntry.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return hasName();
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            FileEntry fileEntry = null;
            try {
                try {
                    FileEntry partialFrom = FileEntry.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FileEntry fileEntry2 = (FileEntry) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        fileEntry = fileEntry2;
                        if (fileEntry != null) {
                            mergeFrom(fileEntry);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileEntry != null) {
                    mergeFrom(fileEntry);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileEntryOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileEntryOrBuilder
        public String getName() {
            Object obj = this.name_;
            if (!(obj instanceof String)) {
                ByteString byteString = (ByteString) obj;
                String stringUtf8 = byteString.toStringUtf8();
                if (byteString.isValidUtf8()) {
                    this.name_ = stringUtf8;
                }
                return stringUtf8;
            }
            return (String) obj;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileEntryOrBuilder
        public ByteString getNameBytes() {
            Object obj = this.name_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.name_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        public Builder setName(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.name_ = str;
            return this;
        }

        public Builder clearName() {
            this.bitField0_ &= -2;
            this.name_ = FileEntry.getDefaultInstance().getName();
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 1;
            this.name_ = byteString;
            return this;
        }

        private void ensureLineStartOffsetIsMutable() {
            if ((this.bitField0_ & 2) != 2) {
                this.lineStartOffset_ = new ArrayList(this.lineStartOffset_);
                this.bitField0_ |= 2;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileEntryOrBuilder
        public List<Integer> getLineStartOffsetList() {
            return Collections.unmodifiableList(this.lineStartOffset_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileEntryOrBuilder
        public int getLineStartOffsetCount() {
            return this.lineStartOffset_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.FileEntryOrBuilder
        public int getLineStartOffset(int i) {
            return this.lineStartOffset_.get(i).intValue();
        }

        public Builder setLineStartOffset(int i, int i2) {
            ensureLineStartOffsetIsMutable();
            this.lineStartOffset_.set(i, Integer.valueOf(i2));
            return this;
        }

        public Builder addLineStartOffset(int i) {
            ensureLineStartOffsetIsMutable();
            this.lineStartOffset_.add(Integer.valueOf(i));
            return this;
        }

        public Builder addAllLineStartOffset(Iterable<? extends Integer> iterable) {
            ensureLineStartOffsetIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.lineStartOffset_);
            return this;
        }

        public Builder clearLineStartOffset() {
            this.lineStartOffset_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
        }
    }
}
