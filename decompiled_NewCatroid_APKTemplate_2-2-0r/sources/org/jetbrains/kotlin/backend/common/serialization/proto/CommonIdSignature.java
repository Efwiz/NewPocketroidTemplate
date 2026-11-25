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
public final class CommonIdSignature extends GeneratedMessageLite implements CommonIdSignatureOrBuilder {
    public static final int DEBUG_INFO_FIELD_NUMBER = 5;
    public static final int DECLARATION_FQ_NAME_FIELD_NUMBER = 2;
    public static final int FLAGS_FIELD_NUMBER = 4;
    public static final int MEMBER_UNIQ_ID_FIELD_NUMBER = 3;
    public static final int PACKAGE_FQ_NAME_FIELD_NUMBER = 1;
    public static Parser<CommonIdSignature> PARSER = new AbstractParser<CommonIdSignature>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignature.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public CommonIdSignature parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new CommonIdSignature(codedInputStream, extensionRegistryLite);
        }
    };
    private static final CommonIdSignature defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private int debugInfo_;
    private int declarationFqNameMemoizedSerializedSize;
    private List<Integer> declarationFqName_;
    private long flags_;
    private long memberUniqId_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int packageFqNameMemoizedSerializedSize;
    private List<Integer> packageFqName_;
    private final ByteString unknownFields;

    private CommonIdSignature(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.packageFqNameMemoizedSerializedSize = -1;
        this.declarationFqNameMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private CommonIdSignature(boolean z) {
        this.packageFqNameMemoizedSerializedSize = -1;
        this.declarationFqNameMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static CommonIdSignature getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public CommonIdSignature getDefaultInstanceForType() {
        return defaultInstance;
    }

    private CommonIdSignature(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this.packageFqNameMemoizedSerializedSize = -1;
        this.declarationFqNameMemoizedSerializedSize = -1;
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
                    int tag = codedInputStream.readTag();
                    if (tag != 0) {
                        if (tag == 8) {
                            if ((i & 1) != 1) {
                                this.packageFqName_ = new ArrayList();
                                i |= 1;
                            }
                            this.packageFqName_.add(Integer.valueOf(codedInputStream.readInt32()));
                        } else if (tag == 10) {
                            int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                            if ((i & 1) != 1 && codedInputStream.getBytesUntilLimit() > 0) {
                                this.packageFqName_ = new ArrayList();
                                i |= 1;
                            }
                            while (codedInputStream.getBytesUntilLimit() > 0) {
                                this.packageFqName_.add(Integer.valueOf(codedInputStream.readInt32()));
                            }
                            codedInputStream.popLimit(iPushLimit);
                        } else if (tag == 16) {
                            if ((i & 2) != 2) {
                                this.declarationFqName_ = new ArrayList();
                                i |= 2;
                            }
                            this.declarationFqName_.add(Integer.valueOf(codedInputStream.readInt32()));
                        } else if (tag == 18) {
                            int iPushLimit2 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                            if ((i & 2) != 2 && codedInputStream.getBytesUntilLimit() > 0) {
                                this.declarationFqName_ = new ArrayList();
                                i |= 2;
                            }
                            while (codedInputStream.getBytesUntilLimit() > 0) {
                                this.declarationFqName_.add(Integer.valueOf(codedInputStream.readInt32()));
                            }
                            codedInputStream.popLimit(iPushLimit2);
                        } else if (tag == 24) {
                            this.bitField0_ |= 1;
                            this.memberUniqId_ = codedInputStream.readInt64();
                        } else if (tag == 32) {
                            this.bitField0_ |= 2;
                            this.flags_ = codedInputStream.readInt64();
                        } else if (tag != 40) {
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                            }
                        } else {
                            this.bitField0_ |= 4;
                            this.debugInfo_ = codedInputStream.readInt32();
                        }
                    }
                    z = true;
                } catch (Throwable th) {
                    if ((i & 1) == 1) {
                        this.packageFqName_ = Collections.unmodifiableList(this.packageFqName_);
                    }
                    if ((i & 2) == 2) {
                        this.declarationFqName_ = Collections.unmodifiableList(this.declarationFqName_);
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
        if ((i & 1) == 1) {
            this.packageFqName_ = Collections.unmodifiableList(this.packageFqName_);
        }
        if ((i & 2) == 2) {
            this.declarationFqName_ = Collections.unmodifiableList(this.declarationFqName_);
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
        CommonIdSignature commonIdSignature = new CommonIdSignature(true);
        defaultInstance = commonIdSignature;
        commonIdSignature.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<CommonIdSignature> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
    public List<Integer> getPackageFqNameList() {
        return this.packageFqName_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
    public int getPackageFqNameCount() {
        return this.packageFqName_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
    public int getPackageFqName(int i) {
        return this.packageFqName_.get(i).intValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
    public List<Integer> getDeclarationFqNameList() {
        return this.declarationFqName_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
    public int getDeclarationFqNameCount() {
        return this.declarationFqName_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
    public int getDeclarationFqName(int i) {
        return this.declarationFqName_.get(i).intValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
    public boolean hasMemberUniqId() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
    public long getMemberUniqId() {
        return this.memberUniqId_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
    public boolean hasFlags() {
        return (this.bitField0_ & 2) == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
    public long getFlags() {
        return this.flags_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
    public boolean hasDebugInfo() {
        return (this.bitField0_ & 4) == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
    public int getDebugInfo() {
        return this.debugInfo_;
    }

    private void initFields() {
        this.packageFqName_ = Collections.emptyList();
        this.declarationFqName_ = Collections.emptyList();
        this.memberUniqId_ = 0L;
        this.flags_ = 0L;
        this.debugInfo_ = 0;
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
        this.memoizedIsInitialized = (byte) 1;
        return true;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        getSerializedSize();
        if (getPackageFqNameList().size() > 0) {
            codedOutputStream.writeRawVarint32(10);
            codedOutputStream.writeRawVarint32(this.packageFqNameMemoizedSerializedSize);
        }
        for (int i = 0; i < this.packageFqName_.size(); i++) {
            codedOutputStream.writeInt32NoTag(this.packageFqName_.get(i).intValue());
        }
        if (getDeclarationFqNameList().size() > 0) {
            codedOutputStream.writeRawVarint32(18);
            codedOutputStream.writeRawVarint32(this.declarationFqNameMemoizedSerializedSize);
        }
        for (int i2 = 0; i2 < this.declarationFqName_.size(); i2++) {
            codedOutputStream.writeInt32NoTag(this.declarationFqName_.get(i2).intValue());
        }
        if ((this.bitField0_ & 1) == 1) {
            codedOutputStream.writeInt64(3, this.memberUniqId_);
        }
        if ((this.bitField0_ & 2) == 2) {
            codedOutputStream.writeInt64(4, this.flags_);
        }
        if ((this.bitField0_ & 4) == 4) {
            codedOutputStream.writeInt32(5, this.debugInfo_);
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
        for (int i2 = 0; i2 < this.packageFqName_.size(); i2++) {
            iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.packageFqName_.get(i2).intValue());
        }
        int iComputeInt32SizeNoTag2 = !getPackageFqNameList().isEmpty() ? iComputeInt32SizeNoTag + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag) : iComputeInt32SizeNoTag;
        this.packageFqNameMemoizedSerializedSize = iComputeInt32SizeNoTag;
        int iComputeInt32SizeNoTag3 = 0;
        for (int i3 = 0; i3 < this.declarationFqName_.size(); i3++) {
            iComputeInt32SizeNoTag3 += CodedOutputStream.computeInt32SizeNoTag(this.declarationFqName_.get(i3).intValue());
        }
        int iComputeInt32Size = iComputeInt32SizeNoTag2 + iComputeInt32SizeNoTag3;
        if (!getDeclarationFqNameList().isEmpty()) {
            iComputeInt32Size = iComputeInt32Size + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag3);
        }
        this.declarationFqNameMemoizedSerializedSize = iComputeInt32SizeNoTag3;
        if ((this.bitField0_ & 1) == 1) {
            iComputeInt32Size += CodedOutputStream.computeInt64Size(3, this.memberUniqId_);
        }
        if ((this.bitField0_ & 2) == 2) {
            iComputeInt32Size += CodedOutputStream.computeInt64Size(4, this.flags_);
        }
        if ((this.bitField0_ & 4) == 4) {
            iComputeInt32Size += CodedOutputStream.computeInt32Size(5, this.debugInfo_);
        }
        int size = iComputeInt32Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static CommonIdSignature parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static CommonIdSignature parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static CommonIdSignature parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static CommonIdSignature parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static CommonIdSignature parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static CommonIdSignature parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static CommonIdSignature parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static CommonIdSignature parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static CommonIdSignature parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static CommonIdSignature parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(CommonIdSignature commonIdSignature) {
        return newBuilder().mergeFrom(commonIdSignature);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<CommonIdSignature, Builder> implements CommonIdSignatureOrBuilder {
        private int bitField0_;
        private int debugInfo_;
        private long flags_;
        private long memberUniqId_;
        private List<Integer> packageFqName_ = Collections.emptyList();
        private List<Integer> declarationFqName_ = Collections.emptyList();

        private void maybeForceBuilderInitialization() {
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return true;
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
            this.packageFqName_ = Collections.emptyList();
            this.bitField0_ &= -2;
            this.declarationFqName_ = Collections.emptyList();
            int i = this.bitField0_;
            this.memberUniqId_ = 0L;
            this.flags_ = 0L;
            this.debugInfo_ = 0;
            this.bitField0_ = i & (-31);
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public CommonIdSignature getDefaultInstanceForType() {
            return CommonIdSignature.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public CommonIdSignature build() {
            CommonIdSignature commonIdSignatureBuildPartial = buildPartial();
            if (commonIdSignatureBuildPartial.isInitialized()) {
                return commonIdSignatureBuildPartial;
            }
            throw newUninitializedMessageException(commonIdSignatureBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public CommonIdSignature buildPartial() {
            CommonIdSignature commonIdSignature = new CommonIdSignature(this);
            int i = this.bitField0_;
            if ((i & 1) == 1) {
                this.packageFqName_ = Collections.unmodifiableList(this.packageFqName_);
                this.bitField0_ &= -2;
            }
            commonIdSignature.packageFqName_ = this.packageFqName_;
            if ((this.bitField0_ & 2) == 2) {
                this.declarationFqName_ = Collections.unmodifiableList(this.declarationFqName_);
                this.bitField0_ &= -3;
            }
            commonIdSignature.declarationFqName_ = this.declarationFqName_;
            int i2 = (i & 4) != 4 ? 0 : 1;
            commonIdSignature.memberUniqId_ = this.memberUniqId_;
            if ((i & 8) == 8) {
                i2 |= 2;
            }
            commonIdSignature.flags_ = this.flags_;
            if ((i & 16) == 16) {
                i2 |= 4;
            }
            commonIdSignature.debugInfo_ = this.debugInfo_;
            commonIdSignature.bitField0_ = i2;
            return commonIdSignature;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(CommonIdSignature commonIdSignature) {
            if (commonIdSignature == CommonIdSignature.getDefaultInstance()) {
                return this;
            }
            if (!commonIdSignature.packageFqName_.isEmpty()) {
                if (this.packageFqName_.isEmpty()) {
                    this.packageFqName_ = commonIdSignature.packageFqName_;
                    this.bitField0_ &= -2;
                } else {
                    ensurePackageFqNameIsMutable();
                    this.packageFqName_.addAll(commonIdSignature.packageFqName_);
                }
            }
            if (!commonIdSignature.declarationFqName_.isEmpty()) {
                if (this.declarationFqName_.isEmpty()) {
                    this.declarationFqName_ = commonIdSignature.declarationFqName_;
                    this.bitField0_ &= -3;
                } else {
                    ensureDeclarationFqNameIsMutable();
                    this.declarationFqName_.addAll(commonIdSignature.declarationFqName_);
                }
            }
            if (commonIdSignature.hasMemberUniqId()) {
                setMemberUniqId(commonIdSignature.getMemberUniqId());
            }
            if (commonIdSignature.hasFlags()) {
                setFlags(commonIdSignature.getFlags());
            }
            if (commonIdSignature.hasDebugInfo()) {
                setDebugInfo(commonIdSignature.getDebugInfo());
            }
            setUnknownFields(getUnknownFields().concat(commonIdSignature.unknownFields));
            return this;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            CommonIdSignature commonIdSignature = null;
            try {
                try {
                    CommonIdSignature partialFrom = CommonIdSignature.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    CommonIdSignature commonIdSignature2 = (CommonIdSignature) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        commonIdSignature = commonIdSignature2;
                        if (commonIdSignature != null) {
                            mergeFrom(commonIdSignature);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (commonIdSignature != null) {
                    mergeFrom(commonIdSignature);
                }
                throw th;
            }
        }

        private void ensurePackageFqNameIsMutable() {
            if ((this.bitField0_ & 1) != 1) {
                this.packageFqName_ = new ArrayList(this.packageFqName_);
                this.bitField0_ |= 1;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
        public List<Integer> getPackageFqNameList() {
            return Collections.unmodifiableList(this.packageFqName_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
        public int getPackageFqNameCount() {
            return this.packageFqName_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
        public int getPackageFqName(int i) {
            return this.packageFqName_.get(i).intValue();
        }

        public Builder setPackageFqName(int i, int i2) {
            ensurePackageFqNameIsMutable();
            this.packageFqName_.set(i, Integer.valueOf(i2));
            return this;
        }

        public Builder addPackageFqName(int i) {
            ensurePackageFqNameIsMutable();
            this.packageFqName_.add(Integer.valueOf(i));
            return this;
        }

        public Builder addAllPackageFqName(Iterable<? extends Integer> iterable) {
            ensurePackageFqNameIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.packageFqName_);
            return this;
        }

        public Builder clearPackageFqName() {
            this.packageFqName_ = Collections.emptyList();
            this.bitField0_ &= -2;
            return this;
        }

        private void ensureDeclarationFqNameIsMutable() {
            if ((this.bitField0_ & 2) != 2) {
                this.declarationFqName_ = new ArrayList(this.declarationFqName_);
                this.bitField0_ |= 2;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
        public List<Integer> getDeclarationFqNameList() {
            return Collections.unmodifiableList(this.declarationFqName_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
        public int getDeclarationFqNameCount() {
            return this.declarationFqName_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
        public int getDeclarationFqName(int i) {
            return this.declarationFqName_.get(i).intValue();
        }

        public Builder setDeclarationFqName(int i, int i2) {
            ensureDeclarationFqNameIsMutable();
            this.declarationFqName_.set(i, Integer.valueOf(i2));
            return this;
        }

        public Builder addDeclarationFqName(int i) {
            ensureDeclarationFqNameIsMutable();
            this.declarationFqName_.add(Integer.valueOf(i));
            return this;
        }

        public Builder addAllDeclarationFqName(Iterable<? extends Integer> iterable) {
            ensureDeclarationFqNameIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.declarationFqName_);
            return this;
        }

        public Builder clearDeclarationFqName() {
            this.declarationFqName_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
        public boolean hasMemberUniqId() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
        public long getMemberUniqId() {
            return this.memberUniqId_;
        }

        public Builder setMemberUniqId(long j) {
            this.bitField0_ |= 4;
            this.memberUniqId_ = j;
            return this;
        }

        public Builder clearMemberUniqId() {
            this.bitField0_ &= -5;
            this.memberUniqId_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
        public boolean hasFlags() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
        public long getFlags() {
            return this.flags_;
        }

        public Builder setFlags(long j) {
            this.bitField0_ |= 8;
            this.flags_ = j;
            return this;
        }

        public Builder clearFlags() {
            this.bitField0_ &= -9;
            this.flags_ = 0L;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
        public boolean hasDebugInfo() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignatureOrBuilder
        public int getDebugInfo() {
            return this.debugInfo_;
        }

        public Builder setDebugInfo(int i) {
            this.bitField0_ |= 16;
            this.debugInfo_ = i;
            return this;
        }

        public Builder clearDebugInfo() {
            this.bitField0_ &= -17;
            this.debugInfo_ = 0;
            return this;
        }
    }
}
