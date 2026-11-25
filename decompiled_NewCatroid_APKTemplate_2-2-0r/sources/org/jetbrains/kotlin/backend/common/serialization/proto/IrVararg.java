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
public final class IrVararg extends GeneratedMessageLite implements IrVarargOrBuilder {
    public static final int ELEMENT_FIELD_NUMBER = 2;
    public static final int ELEMENT_TYPE_FIELD_NUMBER = 1;
    public static Parser<IrVararg> PARSER = new AbstractParser<IrVararg>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrVararg.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrVararg parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrVararg(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrVararg defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private int elementType_;
    private List<IrVarargElement> element_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    private IrVararg(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrVararg(boolean z) {
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrVararg getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrVararg getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrVararg(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 8) {
                                this.bitField0_ |= 1;
                                this.elementType_ = codedInputStream.readInt32();
                            } else if (tag != 18) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                if ((c & 2) != 2) {
                                    this.element_ = new ArrayList();
                                    c = 2;
                                }
                                this.element_.add((IrVarargElement) codedInputStream.readMessage(IrVarargElement.PARSER, extensionRegistryLite));
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
                if ((c & 2) == 2) {
                    this.element_ = Collections.unmodifiableList(this.element_);
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
            this.element_ = Collections.unmodifiableList(this.element_);
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
        IrVararg irVararg = new IrVararg(true);
        defaultInstance = irVararg;
        irVararg.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrVararg> getParserForType() {
        return PARSER;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargOrBuilder
    public boolean hasElementType() {
        return (this.bitField0_ & 1) == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargOrBuilder
    public int getElementType() {
        return this.elementType_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargOrBuilder
    public List<IrVarargElement> getElementList() {
        return this.element_;
    }

    public List<? extends IrVarargElementOrBuilder> getElementOrBuilderList() {
        return this.element_;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargOrBuilder
    public int getElementCount() {
        return this.element_.size();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargOrBuilder
    public IrVarargElement getElement(int i) {
        return this.element_.get(i);
    }

    public IrVarargElementOrBuilder getElementOrBuilder(int i) {
        return this.element_.get(i);
    }

    private void initFields() {
        this.elementType_ = 0;
        this.element_ = Collections.emptyList();
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
        if (!hasElementType()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        for (int i = 0; i < getElementCount(); i++) {
            if (!getElement(i).isInitialized()) {
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
        if ((this.bitField0_ & 1) == 1) {
            codedOutputStream.writeInt32(1, this.elementType_);
        }
        for (int i = 0; i < this.element_.size(); i++) {
            codedOutputStream.writeMessage(2, this.element_.get(i));
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.elementType_) : 0;
        for (int i2 = 0; i2 < this.element_.size(); i2++) {
            iComputeInt32Size += CodedOutputStream.computeMessageSize(2, this.element_.get(i2));
        }
        int size = iComputeInt32Size + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrVararg parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrVararg parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrVararg parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrVararg parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrVararg parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrVararg parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrVararg parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrVararg parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrVararg parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrVararg parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrVararg irVararg) {
        return newBuilder().mergeFrom(irVararg);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrVararg, Builder> implements IrVarargOrBuilder {
        private int bitField0_;
        private int elementType_;
        private List<IrVarargElement> element_ = Collections.emptyList();

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
            this.elementType_ = 0;
            this.bitField0_ &= -2;
            this.element_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrVararg getDefaultInstanceForType() {
            return IrVararg.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrVararg build() {
            IrVararg irVarargBuildPartial = buildPartial();
            if (irVarargBuildPartial.isInitialized()) {
                return irVarargBuildPartial;
            }
            throw newUninitializedMessageException(irVarargBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrVararg buildPartial() {
            IrVararg irVararg = new IrVararg(this);
            int i = (this.bitField0_ & 1) != 1 ? 0 : 1;
            irVararg.elementType_ = this.elementType_;
            if ((this.bitField0_ & 2) == 2) {
                this.element_ = Collections.unmodifiableList(this.element_);
                this.bitField0_ &= -3;
            }
            irVararg.element_ = this.element_;
            irVararg.bitField0_ = i;
            return irVararg;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrVararg irVararg) {
            if (irVararg == IrVararg.getDefaultInstance()) {
                return this;
            }
            if (irVararg.hasElementType()) {
                setElementType(irVararg.getElementType());
            }
            if (!irVararg.element_.isEmpty()) {
                if (this.element_.isEmpty()) {
                    this.element_ = irVararg.element_;
                    this.bitField0_ &= -3;
                } else {
                    ensureElementIsMutable();
                    this.element_.addAll(irVararg.element_);
                }
            }
            setUnknownFields(getUnknownFields().concat(irVararg.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (!hasElementType()) {
                return false;
            }
            for (int i = 0; i < getElementCount(); i++) {
                if (!getElement(i).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrVararg irVararg = null;
            try {
                try {
                    IrVararg partialFrom = IrVararg.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrVararg irVararg2 = (IrVararg) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irVararg = irVararg2;
                        if (irVararg != null) {
                            mergeFrom(irVararg);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irVararg != null) {
                    mergeFrom(irVararg);
                }
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargOrBuilder
        public boolean hasElementType() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargOrBuilder
        public int getElementType() {
            return this.elementType_;
        }

        public Builder setElementType(int i) {
            this.bitField0_ |= 1;
            this.elementType_ = i;
            return this;
        }

        public Builder clearElementType() {
            this.bitField0_ &= -2;
            this.elementType_ = 0;
            return this;
        }

        private void ensureElementIsMutable() {
            if ((this.bitField0_ & 2) != 2) {
                this.element_ = new ArrayList(this.element_);
                this.bitField0_ |= 2;
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargOrBuilder
        public List<IrVarargElement> getElementList() {
            return Collections.unmodifiableList(this.element_);
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargOrBuilder
        public int getElementCount() {
            return this.element_.size();
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargOrBuilder
        public IrVarargElement getElement(int i) {
            return this.element_.get(i);
        }

        public Builder setElement(int i, IrVarargElement irVarargElement) {
            irVarargElement.getClass();
            ensureElementIsMutable();
            this.element_.set(i, irVarargElement);
            return this;
        }

        public Builder setElement(int i, IrVarargElement.Builder builder) {
            ensureElementIsMutable();
            this.element_.set(i, builder.build());
            return this;
        }

        public Builder addElement(IrVarargElement irVarargElement) {
            irVarargElement.getClass();
            ensureElementIsMutable();
            this.element_.add(irVarargElement);
            return this;
        }

        public Builder addElement(int i, IrVarargElement irVarargElement) {
            irVarargElement.getClass();
            ensureElementIsMutable();
            this.element_.add(i, irVarargElement);
            return this;
        }

        public Builder addElement(IrVarargElement.Builder builder) {
            ensureElementIsMutable();
            this.element_.add(builder.build());
            return this;
        }

        public Builder addElement(int i, IrVarargElement.Builder builder) {
            ensureElementIsMutable();
            this.element_.add(i, builder.build());
            return this;
        }

        public Builder addAllElement(Iterable<? extends IrVarargElement> iterable) {
            ensureElementIsMutable();
            AbstractMessageLite.Builder.addAll(iterable, this.element_);
            return this;
        }

        public Builder clearElement() {
            this.element_ = Collections.emptyList();
            this.bitField0_ &= -3;
            return this;
        }

        public Builder removeElement(int i) {
            ensureElementIsMutable();
            this.element_.remove(i);
            return this;
        }
    }
}
