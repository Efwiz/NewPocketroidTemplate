package org.jetbrains.kotlin.backend.jvm.serialization.proto;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.IdSignatureOrBuilder;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclaration;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrStatement;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrType;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOrBuilder;
import org.jetbrains.kotlin.protobuf.AbstractMessageLite;
import org.jetbrains.kotlin.protobuf.AbstractParser;
import org.jetbrains.kotlin.protobuf.ByteString;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.CodedOutputStream;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.protobuf.Internal;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.protobuf.LazyStringArrayList;
import org.jetbrains.kotlin.protobuf.LazyStringList;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;
import org.jetbrains.kotlin.protobuf.Parser;
import org.jetbrains.kotlin.protobuf.ProtocolStringList;

/* JADX INFO: loaded from: classes2.dex */
public final class JvmIr {

    public interface ClassOrFileOrBuilder extends MessageLiteOrBuilder {
        XStatementOrExpression getBody(int i);

        int getBodyCount();

        List<XStatementOrExpression> getBodyList();

        String getDebugInfo(int i);

        ByteString getDebugInfoBytes(int i);

        int getDebugInfoCount();

        ProtocolStringList getDebugInfoList();

        IrDeclaration getDeclaration(int i);

        int getDeclarationCount();

        List<IrDeclaration> getDeclarationList();

        String getFileFacadeFqName();

        ByteString getFileFacadeFqNameBytes();

        IdSignature getSignature(int i);

        int getSignatureCount();

        List<IdSignature> getSignatureList();

        String getString(int i);

        ByteString getStringBytes(int i);

        int getStringCount();

        ProtocolStringList getStringList();

        IrType getType(int i);

        int getTypeCount();

        List<IrType> getTypeList();

        boolean hasFileFacadeFqName();
    }

    public interface XStatementOrExpressionOrBuilder extends MessageLiteOrBuilder {
        IrExpression getExpression();

        IrStatement getStatement();

        boolean hasExpression();

        boolean hasStatement();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private JvmIr() {
    }

    public static final class XStatementOrExpression extends GeneratedMessageLite implements XStatementOrExpressionOrBuilder {
        public static final int EXPRESSION_FIELD_NUMBER = 2;
        public static Parser<XStatementOrExpression> PARSER = new AbstractParser<XStatementOrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.XStatementOrExpression.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public XStatementOrExpression parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new XStatementOrExpression(codedInputStream, extensionRegistryLite, null);
            }
        };
        public static final int STATEMENT_FIELD_NUMBER = 1;
        private static final XStatementOrExpression defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private int kindCase_;
        private Object kind_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private final ByteString unknownFields;

        private void initFields() {
        }

        /* synthetic */ XStatementOrExpression(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, AnonymousClass1 anonymousClass1) throws InvalidProtocolBufferException {
            this(codedInputStream, extensionRegistryLite);
        }

        /* synthetic */ XStatementOrExpression(GeneratedMessageLite.Builder builder, AnonymousClass1 anonymousClass1) {
            this(builder);
        }

        private XStatementOrExpression(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.kindCase_ = 0;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private XStatementOrExpression(boolean z) {
            this.kindCase_ = 0;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        public static XStatementOrExpression getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public XStatementOrExpression getDefaultInstanceForType() {
            return defaultInstance;
        }

        private XStatementOrExpression(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            GeneratedMessageLite.Builder builder;
            boolean z = false;
            this.kindCase_ = 0;
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
                            if (tag == 10) {
                                builder = this.kindCase_ == 1 ? ((IrStatement) this.kind_).toBuilder() : null;
                                MessageLite message = codedInputStream.readMessage(IrStatement.PARSER, extensionRegistryLite);
                                this.kind_ = message;
                                if (builder != null) {
                                    builder.mergeFrom((IrStatement) message);
                                    this.kind_ = builder.buildPartial();
                                }
                                this.kindCase_ = 1;
                            } else if (tag != 18) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                builder = this.kindCase_ == 2 ? ((IrExpression) this.kind_).toBuilder() : null;
                                MessageLite message2 = codedInputStream.readMessage(IrExpression.PARSER, extensionRegistryLite);
                                this.kind_ = message2;
                                if (builder != null) {
                                    builder.mergeFrom((IrExpression) message2);
                                    this.kind_ = builder.buildPartial();
                                }
                                this.kindCase_ = 2;
                            }
                        }
                        z = true;
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
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
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
            XStatementOrExpression xStatementOrExpression = new XStatementOrExpression(true);
            defaultInstance = xStatementOrExpression;
            xStatementOrExpression.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<XStatementOrExpression> getParserForType() {
            return PARSER;
        }

        public enum KindCase implements Internal.EnumLite {
            STATEMENT(1),
            EXPRESSION(2),
            KIND_NOT_SET(0);

            private int value;

            KindCase(int i) {
                this.value = i;
            }

            public static KindCase valueOf(int i) {
                if (i == 0) {
                    return KIND_NOT_SET;
                }
                if (i == 1) {
                    return STATEMENT;
                }
                if (i == 2) {
                    return EXPRESSION;
                }
                throw new IllegalArgumentException("Value is undefined for this oneof enum.");
            }

            @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLite
            public int getNumber() {
                return this.value;
            }
        }

        public KindCase getKindCase() {
            return KindCase.valueOf(this.kindCase_);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.XStatementOrExpressionOrBuilder
        public boolean hasStatement() {
            return this.kindCase_ == 1;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.XStatementOrExpressionOrBuilder
        public IrStatement getStatement() {
            if (this.kindCase_ == 1) {
                return (IrStatement) this.kind_;
            }
            return IrStatement.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.XStatementOrExpressionOrBuilder
        public boolean hasExpression() {
            return this.kindCase_ == 2;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.XStatementOrExpressionOrBuilder
        public IrExpression getExpression() {
            if (this.kindCase_ == 2) {
                return (IrExpression) this.kind_;
            }
            return IrExpression.getDefaultInstance();
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
            if (hasStatement() && !getStatement().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            if (hasExpression() && !getExpression().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            if (this.kindCase_ == 1) {
                codedOutputStream.writeMessage(1, (IrStatement) this.kind_);
            }
            if (this.kindCase_ == 2) {
                codedOutputStream.writeMessage(2, (IrExpression) this.kind_);
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeMessageSize = this.kindCase_ == 1 ? CodedOutputStream.computeMessageSize(1, (IrStatement) this.kind_) : 0;
            if (this.kindCase_ == 2) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(2, (IrExpression) this.kind_);
            }
            int size = iComputeMessageSize + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static XStatementOrExpression parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static XStatementOrExpression parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static XStatementOrExpression parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static XStatementOrExpression parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static XStatementOrExpression parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static XStatementOrExpression parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static XStatementOrExpression parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static XStatementOrExpression parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static XStatementOrExpression parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static XStatementOrExpression parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(XStatementOrExpression xStatementOrExpression) {
            return newBuilder().mergeFrom(xStatementOrExpression);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<XStatementOrExpression, Builder> implements XStatementOrExpressionOrBuilder {
            private int bitField0_;
            private int kindCase_ = 0;
            private Object kind_;

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
                this.kindCase_ = 0;
                this.kind_ = null;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public XStatementOrExpression getDefaultInstanceForType() {
                return XStatementOrExpression.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public XStatementOrExpression build() {
                XStatementOrExpression xStatementOrExpressionBuildPartial = buildPartial();
                if (xStatementOrExpressionBuildPartial.isInitialized()) {
                    return xStatementOrExpressionBuildPartial;
                }
                throw newUninitializedMessageException(xStatementOrExpressionBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public XStatementOrExpression buildPartial() {
                XStatementOrExpression xStatementOrExpression = new XStatementOrExpression(this, (AnonymousClass1) null);
                if (this.kindCase_ == 1) {
                    xStatementOrExpression.kind_ = this.kind_;
                }
                if (this.kindCase_ == 2) {
                    xStatementOrExpression.kind_ = this.kind_;
                }
                xStatementOrExpression.bitField0_ = 0;
                xStatementOrExpression.kindCase_ = this.kindCase_;
                return xStatementOrExpression;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(XStatementOrExpression xStatementOrExpression) {
                if (xStatementOrExpression == XStatementOrExpression.getDefaultInstance()) {
                    return this;
                }
                int i = AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$backend$jvm$serialization$proto$JvmIr$XStatementOrExpression$KindCase[xStatementOrExpression.getKindCase().ordinal()];
                if (i == 1) {
                    mergeStatement(xStatementOrExpression.getStatement());
                } else if (i == 2) {
                    mergeExpression(xStatementOrExpression.getExpression());
                }
                setUnknownFields(getUnknownFields().concat(xStatementOrExpression.unknownFields));
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                if (!hasStatement() || getStatement().isInitialized()) {
                    return !hasExpression() || getExpression().isInitialized();
                }
                return false;
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                XStatementOrExpression xStatementOrExpression = null;
                try {
                    try {
                        XStatementOrExpression partialFrom = XStatementOrExpression.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        XStatementOrExpression xStatementOrExpression2 = (XStatementOrExpression) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            xStatementOrExpression = xStatementOrExpression2;
                            if (xStatementOrExpression != null) {
                                mergeFrom(xStatementOrExpression);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (xStatementOrExpression != null) {
                        mergeFrom(xStatementOrExpression);
                    }
                    throw th;
                }
            }

            public KindCase getKindCase() {
                return KindCase.valueOf(this.kindCase_);
            }

            public Builder clearKind() {
                this.kindCase_ = 0;
                this.kind_ = null;
                return this;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.XStatementOrExpressionOrBuilder
            public boolean hasStatement() {
                return this.kindCase_ == 1;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.XStatementOrExpressionOrBuilder
            public IrStatement getStatement() {
                if (this.kindCase_ == 1) {
                    return (IrStatement) this.kind_;
                }
                return IrStatement.getDefaultInstance();
            }

            public Builder setStatement(IrStatement irStatement) {
                irStatement.getClass();
                this.kind_ = irStatement;
                this.kindCase_ = 1;
                return this;
            }

            public Builder setStatement(IrStatement.Builder builder) {
                this.kind_ = builder.build();
                this.kindCase_ = 1;
                return this;
            }

            public Builder mergeStatement(IrStatement irStatement) {
                if (this.kindCase_ == 1 && this.kind_ != IrStatement.getDefaultInstance()) {
                    this.kind_ = IrStatement.newBuilder((IrStatement) this.kind_).mergeFrom(irStatement).buildPartial();
                } else {
                    this.kind_ = irStatement;
                }
                this.kindCase_ = 1;
                return this;
            }

            public Builder clearStatement() {
                if (this.kindCase_ == 1) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                }
                return this;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.XStatementOrExpressionOrBuilder
            public boolean hasExpression() {
                return this.kindCase_ == 2;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.XStatementOrExpressionOrBuilder
            public IrExpression getExpression() {
                if (this.kindCase_ == 2) {
                    return (IrExpression) this.kind_;
                }
                return IrExpression.getDefaultInstance();
            }

            public Builder setExpression(IrExpression irExpression) {
                irExpression.getClass();
                this.kind_ = irExpression;
                this.kindCase_ = 2;
                return this;
            }

            public Builder setExpression(IrExpression.Builder builder) {
                this.kind_ = builder.build();
                this.kindCase_ = 2;
                return this;
            }

            public Builder mergeExpression(IrExpression irExpression) {
                if (this.kindCase_ == 2 && this.kind_ != IrExpression.getDefaultInstance()) {
                    this.kind_ = IrExpression.newBuilder((IrExpression) this.kind_).mergeFrom(irExpression).buildPartial();
                } else {
                    this.kind_ = irExpression;
                }
                this.kindCase_ = 2;
                return this;
            }

            public Builder clearExpression() {
                if (this.kindCase_ == 2) {
                    this.kindCase_ = 0;
                    this.kind_ = null;
                }
                return this;
            }
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$backend$jvm$serialization$proto$JvmIr$XStatementOrExpression$KindCase;

        static {
            int[] iArr = new int[XStatementOrExpression.KindCase.values().length];
            $SwitchMap$org$jetbrains$kotlin$backend$jvm$serialization$proto$JvmIr$XStatementOrExpression$KindCase = iArr;
            try {
                iArr[XStatementOrExpression.KindCase.STATEMENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$jvm$serialization$proto$JvmIr$XStatementOrExpression$KindCase[XStatementOrExpression.KindCase.EXPRESSION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$jvm$serialization$proto$JvmIr$XStatementOrExpression$KindCase[XStatementOrExpression.KindCase.KIND_NOT_SET.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static final class ClassOrFile extends GeneratedMessageLite implements ClassOrFileOrBuilder {
        public static final int BODY_FIELD_NUMBER = 5;
        public static final int DEBUG_INFO_FIELD_NUMBER = 6;
        public static final int DECLARATION_FIELD_NUMBER = 1;
        public static final int FILE_FACADE_FQ_NAME_FIELD_NUMBER = 7;
        public static Parser<ClassOrFile> PARSER = new AbstractParser<ClassOrFile>() { // from class: org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFile.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public ClassOrFile parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new ClassOrFile(codedInputStream, extensionRegistryLite, null);
            }
        };
        public static final int SIGNATURE_FIELD_NUMBER = 3;
        public static final int STRING_FIELD_NUMBER = 4;
        public static final int TYPE_FIELD_NUMBER = 2;
        private static final ClassOrFile defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private List<XStatementOrExpression> body_;
        private LazyStringList debugInfo_;
        private List<IrDeclaration> declaration_;
        private Object fileFacadeFqName_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<IdSignature> signature_;
        private LazyStringList string_;
        private List<IrType> type_;
        private final ByteString unknownFields;

        /* synthetic */ ClassOrFile(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, AnonymousClass1 anonymousClass1) throws InvalidProtocolBufferException {
            this(codedInputStream, extensionRegistryLite);
        }

        /* synthetic */ ClassOrFile(GeneratedMessageLite.Builder builder, AnonymousClass1 anonymousClass1) {
            this(builder);
        }

        private ClassOrFile(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private ClassOrFile(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        public static ClassOrFile getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public ClassOrFile getDefaultInstanceForType() {
            return defaultInstance;
        }

        private ClassOrFile(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                            if (tag == 10) {
                                if ((i & 1) != 1) {
                                    this.declaration_ = new ArrayList();
                                    i |= 1;
                                }
                                this.declaration_.add((IrDeclaration) codedInputStream.readMessage(IrDeclaration.PARSER, extensionRegistryLite));
                            } else if (tag == 18) {
                                if ((i & 2) != 2) {
                                    this.type_ = new ArrayList();
                                    i |= 2;
                                }
                                this.type_.add((IrType) codedInputStream.readMessage(IrType.PARSER, extensionRegistryLite));
                            } else if (tag == 26) {
                                if ((i & 4) != 4) {
                                    this.signature_ = new ArrayList();
                                    i |= 4;
                                }
                                this.signature_.add((IdSignature) codedInputStream.readMessage(IdSignature.PARSER, extensionRegistryLite));
                            } else if (tag == 34) {
                                ByteString bytes = codedInputStream.readBytes();
                                if ((i & 8) != 8) {
                                    this.string_ = new LazyStringArrayList();
                                    i |= 8;
                                }
                                this.string_.add(bytes);
                            } else if (tag == 42) {
                                if ((i & 16) != 16) {
                                    this.body_ = new ArrayList();
                                    i |= 16;
                                }
                                this.body_.add((XStatementOrExpression) codedInputStream.readMessage(XStatementOrExpression.PARSER, extensionRegistryLite));
                            } else if (tag == 50) {
                                ByteString bytes2 = codedInputStream.readBytes();
                                if ((i & 32) != 32) {
                                    this.debugInfo_ = new LazyStringArrayList();
                                    i |= 32;
                                }
                                this.debugInfo_.add(bytes2);
                            } else if (tag != 58) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                ByteString bytes3 = codedInputStream.readBytes();
                                this.bitField0_ |= 1;
                                this.fileFacadeFqName_ = bytes3;
                            }
                        }
                        z = true;
                    } catch (Throwable th) {
                        if ((i & 1) == 1) {
                            this.declaration_ = Collections.unmodifiableList(this.declaration_);
                        }
                        if ((i & 2) == 2) {
                            this.type_ = Collections.unmodifiableList(this.type_);
                        }
                        if ((i & 4) == 4) {
                            this.signature_ = Collections.unmodifiableList(this.signature_);
                        }
                        if ((i & 8) == 8) {
                            this.string_ = this.string_.getUnmodifiableView();
                        }
                        if ((i & 16) == 16) {
                            this.body_ = Collections.unmodifiableList(this.body_);
                        }
                        if ((i & 32) == 32) {
                            this.debugInfo_ = this.debugInfo_.getUnmodifiableView();
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
                this.declaration_ = Collections.unmodifiableList(this.declaration_);
            }
            if ((i & 2) == 2) {
                this.type_ = Collections.unmodifiableList(this.type_);
            }
            if ((i & 4) == 4) {
                this.signature_ = Collections.unmodifiableList(this.signature_);
            }
            if ((i & 8) == 8) {
                this.string_ = this.string_.getUnmodifiableView();
            }
            if ((i & 16) == 16) {
                this.body_ = Collections.unmodifiableList(this.body_);
            }
            if ((i & 32) == 32) {
                this.debugInfo_ = this.debugInfo_.getUnmodifiableView();
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
            ClassOrFile classOrFile = new ClassOrFile(true);
            defaultInstance = classOrFile;
            classOrFile.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<ClassOrFile> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public List<IrDeclaration> getDeclarationList() {
            return this.declaration_;
        }

        public List<? extends IrDeclarationOrBuilder> getDeclarationOrBuilderList() {
            return this.declaration_;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public int getDeclarationCount() {
            return this.declaration_.size();
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public IrDeclaration getDeclaration(int i) {
            return this.declaration_.get(i);
        }

        public IrDeclarationOrBuilder getDeclarationOrBuilder(int i) {
            return this.declaration_.get(i);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public List<IrType> getTypeList() {
            return this.type_;
        }

        public List<? extends IrTypeOrBuilder> getTypeOrBuilderList() {
            return this.type_;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public int getTypeCount() {
            return this.type_.size();
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public IrType getType(int i) {
            return this.type_.get(i);
        }

        public IrTypeOrBuilder getTypeOrBuilder(int i) {
            return this.type_.get(i);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public List<IdSignature> getSignatureList() {
            return this.signature_;
        }

        public List<? extends IdSignatureOrBuilder> getSignatureOrBuilderList() {
            return this.signature_;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public int getSignatureCount() {
            return this.signature_.size();
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public IdSignature getSignature(int i) {
            return this.signature_.get(i);
        }

        public IdSignatureOrBuilder getSignatureOrBuilder(int i) {
            return this.signature_.get(i);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public ProtocolStringList getStringList() {
            return this.string_;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public int getStringCount() {
            return this.string_.size();
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public String getString(int i) {
            return (String) this.string_.get(i);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public ByteString getStringBytes(int i) {
            return this.string_.getByteString(i);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public List<XStatementOrExpression> getBodyList() {
            return this.body_;
        }

        public List<? extends XStatementOrExpressionOrBuilder> getBodyOrBuilderList() {
            return this.body_;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public int getBodyCount() {
            return this.body_.size();
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public XStatementOrExpression getBody(int i) {
            return this.body_.get(i);
        }

        public XStatementOrExpressionOrBuilder getBodyOrBuilder(int i) {
            return this.body_.get(i);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public ProtocolStringList getDebugInfoList() {
            return this.debugInfo_;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public int getDebugInfoCount() {
            return this.debugInfo_.size();
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public String getDebugInfo(int i) {
            return (String) this.debugInfo_.get(i);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public ByteString getDebugInfoBytes(int i) {
            return this.debugInfo_.getByteString(i);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public boolean hasFileFacadeFqName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public String getFileFacadeFqName() {
            Object obj = this.fileFacadeFqName_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.fileFacadeFqName_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
        public ByteString getFileFacadeFqNameBytes() {
            Object obj = this.fileFacadeFqName_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.fileFacadeFqName_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        private void initFields() {
            this.declaration_ = Collections.emptyList();
            this.type_ = Collections.emptyList();
            this.signature_ = Collections.emptyList();
            this.string_ = LazyStringArrayList.EMPTY;
            this.body_ = Collections.emptyList();
            this.debugInfo_ = LazyStringArrayList.EMPTY;
            this.fileFacadeFqName_ = "";
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
            if (!hasFileFacadeFqName()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i = 0; i < getDeclarationCount(); i++) {
                if (!getDeclaration(i).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i2 = 0; i2 < getTypeCount(); i2++) {
                if (!getType(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i3 = 0; i3 < getSignatureCount(); i3++) {
                if (!getSignature(i3).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i4 = 0; i4 < getBodyCount(); i4++) {
                if (!getBody(i4).isInitialized()) {
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
            for (int i = 0; i < this.declaration_.size(); i++) {
                codedOutputStream.writeMessage(1, this.declaration_.get(i));
            }
            for (int i2 = 0; i2 < this.type_.size(); i2++) {
                codedOutputStream.writeMessage(2, this.type_.get(i2));
            }
            for (int i3 = 0; i3 < this.signature_.size(); i3++) {
                codedOutputStream.writeMessage(3, this.signature_.get(i3));
            }
            for (int i4 = 0; i4 < this.string_.size(); i4++) {
                codedOutputStream.writeBytes(4, this.string_.getByteString(i4));
            }
            for (int i5 = 0; i5 < this.body_.size(); i5++) {
                codedOutputStream.writeMessage(5, this.body_.get(i5));
            }
            for (int i6 = 0; i6 < this.debugInfo_.size(); i6++) {
                codedOutputStream.writeBytes(6, this.debugInfo_.getByteString(i6));
            }
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBytes(7, getFileFacadeFqNameBytes());
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
            for (int i2 = 0; i2 < this.declaration_.size(); i2++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.declaration_.get(i2));
            }
            for (int i3 = 0; i3 < this.type_.size(); i3++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(2, this.type_.get(i3));
            }
            for (int i4 = 0; i4 < this.signature_.size(); i4++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(3, this.signature_.get(i4));
            }
            int iComputeBytesSizeNoTag = 0;
            for (int i5 = 0; i5 < this.string_.size(); i5++) {
                iComputeBytesSizeNoTag += CodedOutputStream.computeBytesSizeNoTag(this.string_.getByteString(i5));
            }
            int size = iComputeMessageSize + iComputeBytesSizeNoTag + getStringList().size();
            for (int i6 = 0; i6 < this.body_.size(); i6++) {
                size += CodedOutputStream.computeMessageSize(5, this.body_.get(i6));
            }
            int iComputeBytesSizeNoTag2 = 0;
            for (int i7 = 0; i7 < this.debugInfo_.size(); i7++) {
                iComputeBytesSizeNoTag2 += CodedOutputStream.computeBytesSizeNoTag(this.debugInfo_.getByteString(i7));
            }
            int size2 = size + iComputeBytesSizeNoTag2 + getDebugInfoList().size();
            if ((this.bitField0_ & 1) == 1) {
                size2 += CodedOutputStream.computeBytesSize(7, getFileFacadeFqNameBytes());
            }
            int size3 = size2 + this.unknownFields.size();
            this.memoizedSerializedSize = size3;
            return size3;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static ClassOrFile parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static ClassOrFile parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static ClassOrFile parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static ClassOrFile parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static ClassOrFile parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static ClassOrFile parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static ClassOrFile parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static ClassOrFile parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static ClassOrFile parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static ClassOrFile parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(ClassOrFile classOrFile) {
            return newBuilder().mergeFrom(classOrFile);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ClassOrFile, Builder> implements ClassOrFileOrBuilder {
            private int bitField0_;
            private List<IrDeclaration> declaration_ = Collections.emptyList();
            private List<IrType> type_ = Collections.emptyList();
            private List<IdSignature> signature_ = Collections.emptyList();
            private LazyStringList string_ = LazyStringArrayList.EMPTY;
            private List<XStatementOrExpression> body_ = Collections.emptyList();
            private LazyStringList debugInfo_ = LazyStringArrayList.EMPTY;
            private Object fileFacadeFqName_ = "";

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
                this.declaration_ = Collections.emptyList();
                this.bitField0_ &= -2;
                this.type_ = Collections.emptyList();
                this.bitField0_ &= -3;
                this.signature_ = Collections.emptyList();
                this.bitField0_ &= -5;
                this.string_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -9;
                this.body_ = Collections.emptyList();
                this.bitField0_ &= -17;
                this.debugInfo_ = LazyStringArrayList.EMPTY;
                int i = this.bitField0_;
                this.fileFacadeFqName_ = "";
                this.bitField0_ = i & (-97);
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public ClassOrFile getDefaultInstanceForType() {
                return ClassOrFile.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public ClassOrFile build() {
                ClassOrFile classOrFileBuildPartial = buildPartial();
                if (classOrFileBuildPartial.isInitialized()) {
                    return classOrFileBuildPartial;
                }
                throw newUninitializedMessageException(classOrFileBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public ClassOrFile buildPartial() {
                ClassOrFile classOrFile = new ClassOrFile(this, (AnonymousClass1) null);
                int i = this.bitField0_;
                if ((i & 1) == 1) {
                    this.declaration_ = Collections.unmodifiableList(this.declaration_);
                    this.bitField0_ &= -2;
                }
                classOrFile.declaration_ = this.declaration_;
                if ((this.bitField0_ & 2) == 2) {
                    this.type_ = Collections.unmodifiableList(this.type_);
                    this.bitField0_ &= -3;
                }
                classOrFile.type_ = this.type_;
                if ((this.bitField0_ & 4) == 4) {
                    this.signature_ = Collections.unmodifiableList(this.signature_);
                    this.bitField0_ &= -5;
                }
                classOrFile.signature_ = this.signature_;
                if ((this.bitField0_ & 8) == 8) {
                    this.string_ = this.string_.getUnmodifiableView();
                    this.bitField0_ &= -9;
                }
                classOrFile.string_ = this.string_;
                if ((this.bitField0_ & 16) == 16) {
                    this.body_ = Collections.unmodifiableList(this.body_);
                    this.bitField0_ &= -17;
                }
                classOrFile.body_ = this.body_;
                if ((this.bitField0_ & 32) == 32) {
                    this.debugInfo_ = this.debugInfo_.getUnmodifiableView();
                    this.bitField0_ &= -33;
                }
                classOrFile.debugInfo_ = this.debugInfo_;
                int i2 = (i & 64) != 64 ? 0 : 1;
                classOrFile.fileFacadeFqName_ = this.fileFacadeFqName_;
                classOrFile.bitField0_ = i2;
                return classOrFile;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(ClassOrFile classOrFile) {
                if (classOrFile == ClassOrFile.getDefaultInstance()) {
                    return this;
                }
                if (!classOrFile.declaration_.isEmpty()) {
                    if (this.declaration_.isEmpty()) {
                        this.declaration_ = classOrFile.declaration_;
                        this.bitField0_ &= -2;
                    } else {
                        ensureDeclarationIsMutable();
                        this.declaration_.addAll(classOrFile.declaration_);
                    }
                }
                if (!classOrFile.type_.isEmpty()) {
                    if (this.type_.isEmpty()) {
                        this.type_ = classOrFile.type_;
                        this.bitField0_ &= -3;
                    } else {
                        ensureTypeIsMutable();
                        this.type_.addAll(classOrFile.type_);
                    }
                }
                if (!classOrFile.signature_.isEmpty()) {
                    if (this.signature_.isEmpty()) {
                        this.signature_ = classOrFile.signature_;
                        this.bitField0_ &= -5;
                    } else {
                        ensureSignatureIsMutable();
                        this.signature_.addAll(classOrFile.signature_);
                    }
                }
                if (!classOrFile.string_.isEmpty()) {
                    if (this.string_.isEmpty()) {
                        this.string_ = classOrFile.string_;
                        this.bitField0_ &= -9;
                    } else {
                        ensureStringIsMutable();
                        this.string_.addAll(classOrFile.string_);
                    }
                }
                if (!classOrFile.body_.isEmpty()) {
                    if (this.body_.isEmpty()) {
                        this.body_ = classOrFile.body_;
                        this.bitField0_ &= -17;
                    } else {
                        ensureBodyIsMutable();
                        this.body_.addAll(classOrFile.body_);
                    }
                }
                if (!classOrFile.debugInfo_.isEmpty()) {
                    if (this.debugInfo_.isEmpty()) {
                        this.debugInfo_ = classOrFile.debugInfo_;
                        this.bitField0_ &= -33;
                    } else {
                        ensureDebugInfoIsMutable();
                        this.debugInfo_.addAll(classOrFile.debugInfo_);
                    }
                }
                if (classOrFile.hasFileFacadeFqName()) {
                    this.bitField0_ |= 64;
                    this.fileFacadeFqName_ = classOrFile.fileFacadeFqName_;
                }
                setUnknownFields(getUnknownFields().concat(classOrFile.unknownFields));
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                if (!hasFileFacadeFqName()) {
                    return false;
                }
                for (int i = 0; i < getDeclarationCount(); i++) {
                    if (!getDeclaration(i).isInitialized()) {
                        return false;
                    }
                }
                for (int i2 = 0; i2 < getTypeCount(); i2++) {
                    if (!getType(i2).isInitialized()) {
                        return false;
                    }
                }
                for (int i3 = 0; i3 < getSignatureCount(); i3++) {
                    if (!getSignature(i3).isInitialized()) {
                        return false;
                    }
                }
                for (int i4 = 0; i4 < getBodyCount(); i4++) {
                    if (!getBody(i4).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                ClassOrFile classOrFile = null;
                try {
                    try {
                        ClassOrFile partialFrom = ClassOrFile.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        ClassOrFile classOrFile2 = (ClassOrFile) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            classOrFile = classOrFile2;
                            if (classOrFile != null) {
                                mergeFrom(classOrFile);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (classOrFile != null) {
                        mergeFrom(classOrFile);
                    }
                    throw th;
                }
            }

            private void ensureDeclarationIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.declaration_ = new ArrayList(this.declaration_);
                    this.bitField0_ |= 1;
                }
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public List<IrDeclaration> getDeclarationList() {
                return Collections.unmodifiableList(this.declaration_);
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public int getDeclarationCount() {
                return this.declaration_.size();
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public IrDeclaration getDeclaration(int i) {
                return this.declaration_.get(i);
            }

            public Builder setDeclaration(int i, IrDeclaration irDeclaration) {
                irDeclaration.getClass();
                ensureDeclarationIsMutable();
                this.declaration_.set(i, irDeclaration);
                return this;
            }

            public Builder setDeclaration(int i, IrDeclaration.Builder builder) {
                ensureDeclarationIsMutable();
                this.declaration_.set(i, builder.build());
                return this;
            }

            public Builder addDeclaration(IrDeclaration irDeclaration) {
                irDeclaration.getClass();
                ensureDeclarationIsMutable();
                this.declaration_.add(irDeclaration);
                return this;
            }

            public Builder addDeclaration(int i, IrDeclaration irDeclaration) {
                irDeclaration.getClass();
                ensureDeclarationIsMutable();
                this.declaration_.add(i, irDeclaration);
                return this;
            }

            public Builder addDeclaration(IrDeclaration.Builder builder) {
                ensureDeclarationIsMutable();
                this.declaration_.add(builder.build());
                return this;
            }

            public Builder addDeclaration(int i, IrDeclaration.Builder builder) {
                ensureDeclarationIsMutable();
                this.declaration_.add(i, builder.build());
                return this;
            }

            public Builder addAllDeclaration(Iterable<? extends IrDeclaration> iterable) {
                ensureDeclarationIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.declaration_);
                return this;
            }

            public Builder clearDeclaration() {
                this.declaration_ = Collections.emptyList();
                this.bitField0_ &= -2;
                return this;
            }

            public Builder removeDeclaration(int i) {
                ensureDeclarationIsMutable();
                this.declaration_.remove(i);
                return this;
            }

            private void ensureTypeIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.type_ = new ArrayList(this.type_);
                    this.bitField0_ |= 2;
                }
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public List<IrType> getTypeList() {
                return Collections.unmodifiableList(this.type_);
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public int getTypeCount() {
                return this.type_.size();
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public IrType getType(int i) {
                return this.type_.get(i);
            }

            public Builder setType(int i, IrType irType) {
                irType.getClass();
                ensureTypeIsMutable();
                this.type_.set(i, irType);
                return this;
            }

            public Builder setType(int i, IrType.Builder builder) {
                ensureTypeIsMutable();
                this.type_.set(i, builder.build());
                return this;
            }

            public Builder addType(IrType irType) {
                irType.getClass();
                ensureTypeIsMutable();
                this.type_.add(irType);
                return this;
            }

            public Builder addType(int i, IrType irType) {
                irType.getClass();
                ensureTypeIsMutable();
                this.type_.add(i, irType);
                return this;
            }

            public Builder addType(IrType.Builder builder) {
                ensureTypeIsMutable();
                this.type_.add(builder.build());
                return this;
            }

            public Builder addType(int i, IrType.Builder builder) {
                ensureTypeIsMutable();
                this.type_.add(i, builder.build());
                return this;
            }

            public Builder addAllType(Iterable<? extends IrType> iterable) {
                ensureTypeIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.type_);
                return this;
            }

            public Builder clearType() {
                this.type_ = Collections.emptyList();
                this.bitField0_ &= -3;
                return this;
            }

            public Builder removeType(int i) {
                ensureTypeIsMutable();
                this.type_.remove(i);
                return this;
            }

            private void ensureSignatureIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.signature_ = new ArrayList(this.signature_);
                    this.bitField0_ |= 4;
                }
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public List<IdSignature> getSignatureList() {
                return Collections.unmodifiableList(this.signature_);
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public int getSignatureCount() {
                return this.signature_.size();
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public IdSignature getSignature(int i) {
                return this.signature_.get(i);
            }

            public Builder setSignature(int i, IdSignature idSignature) {
                idSignature.getClass();
                ensureSignatureIsMutable();
                this.signature_.set(i, idSignature);
                return this;
            }

            public Builder setSignature(int i, IdSignature.Builder builder) {
                ensureSignatureIsMutable();
                this.signature_.set(i, builder.build());
                return this;
            }

            public Builder addSignature(IdSignature idSignature) {
                idSignature.getClass();
                ensureSignatureIsMutable();
                this.signature_.add(idSignature);
                return this;
            }

            public Builder addSignature(int i, IdSignature idSignature) {
                idSignature.getClass();
                ensureSignatureIsMutable();
                this.signature_.add(i, idSignature);
                return this;
            }

            public Builder addSignature(IdSignature.Builder builder) {
                ensureSignatureIsMutable();
                this.signature_.add(builder.build());
                return this;
            }

            public Builder addSignature(int i, IdSignature.Builder builder) {
                ensureSignatureIsMutable();
                this.signature_.add(i, builder.build());
                return this;
            }

            public Builder addAllSignature(Iterable<? extends IdSignature> iterable) {
                ensureSignatureIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.signature_);
                return this;
            }

            public Builder clearSignature() {
                this.signature_ = Collections.emptyList();
                this.bitField0_ &= -5;
                return this;
            }

            public Builder removeSignature(int i) {
                ensureSignatureIsMutable();
                this.signature_.remove(i);
                return this;
            }

            private void ensureStringIsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.string_ = new LazyStringArrayList(this.string_);
                    this.bitField0_ |= 8;
                }
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public ProtocolStringList getStringList() {
                return this.string_.getUnmodifiableView();
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public int getStringCount() {
                return this.string_.size();
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public String getString(int i) {
                return (String) this.string_.get(i);
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public ByteString getStringBytes(int i) {
                return this.string_.getByteString(i);
            }

            public Builder setString(int i, String str) {
                str.getClass();
                ensureStringIsMutable();
                this.string_.set(i, str);
                return this;
            }

            public Builder addString(String str) {
                str.getClass();
                ensureStringIsMutable();
                this.string_.add(str);
                return this;
            }

            public Builder addAllString(Iterable<String> iterable) {
                ensureStringIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.string_);
                return this;
            }

            public Builder clearString() {
                this.string_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -9;
                return this;
            }

            public Builder addStringBytes(ByteString byteString) {
                byteString.getClass();
                ensureStringIsMutable();
                this.string_.add(byteString);
                return this;
            }

            private void ensureBodyIsMutable() {
                if ((this.bitField0_ & 16) != 16) {
                    this.body_ = new ArrayList(this.body_);
                    this.bitField0_ |= 16;
                }
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public List<XStatementOrExpression> getBodyList() {
                return Collections.unmodifiableList(this.body_);
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public int getBodyCount() {
                return this.body_.size();
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public XStatementOrExpression getBody(int i) {
                return this.body_.get(i);
            }

            public Builder setBody(int i, XStatementOrExpression xStatementOrExpression) {
                xStatementOrExpression.getClass();
                ensureBodyIsMutable();
                this.body_.set(i, xStatementOrExpression);
                return this;
            }

            public Builder setBody(int i, XStatementOrExpression.Builder builder) {
                ensureBodyIsMutable();
                this.body_.set(i, builder.build());
                return this;
            }

            public Builder addBody(XStatementOrExpression xStatementOrExpression) {
                xStatementOrExpression.getClass();
                ensureBodyIsMutable();
                this.body_.add(xStatementOrExpression);
                return this;
            }

            public Builder addBody(int i, XStatementOrExpression xStatementOrExpression) {
                xStatementOrExpression.getClass();
                ensureBodyIsMutable();
                this.body_.add(i, xStatementOrExpression);
                return this;
            }

            public Builder addBody(XStatementOrExpression.Builder builder) {
                ensureBodyIsMutable();
                this.body_.add(builder.build());
                return this;
            }

            public Builder addBody(int i, XStatementOrExpression.Builder builder) {
                ensureBodyIsMutable();
                this.body_.add(i, builder.build());
                return this;
            }

            public Builder addAllBody(Iterable<? extends XStatementOrExpression> iterable) {
                ensureBodyIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.body_);
                return this;
            }

            public Builder clearBody() {
                this.body_ = Collections.emptyList();
                this.bitField0_ &= -17;
                return this;
            }

            public Builder removeBody(int i) {
                ensureBodyIsMutable();
                this.body_.remove(i);
                return this;
            }

            private void ensureDebugInfoIsMutable() {
                if ((this.bitField0_ & 32) != 32) {
                    this.debugInfo_ = new LazyStringArrayList(this.debugInfo_);
                    this.bitField0_ |= 32;
                }
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public ProtocolStringList getDebugInfoList() {
                return this.debugInfo_.getUnmodifiableView();
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public int getDebugInfoCount() {
                return this.debugInfo_.size();
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public String getDebugInfo(int i) {
                return (String) this.debugInfo_.get(i);
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public ByteString getDebugInfoBytes(int i) {
                return this.debugInfo_.getByteString(i);
            }

            public Builder setDebugInfo(int i, String str) {
                str.getClass();
                ensureDebugInfoIsMutable();
                this.debugInfo_.set(i, str);
                return this;
            }

            public Builder addDebugInfo(String str) {
                str.getClass();
                ensureDebugInfoIsMutable();
                this.debugInfo_.add(str);
                return this;
            }

            public Builder addAllDebugInfo(Iterable<String> iterable) {
                ensureDebugInfoIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.debugInfo_);
                return this;
            }

            public Builder clearDebugInfo() {
                this.debugInfo_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -33;
                return this;
            }

            public Builder addDebugInfoBytes(ByteString byteString) {
                byteString.getClass();
                ensureDebugInfoIsMutable();
                this.debugInfo_.add(byteString);
                return this;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public boolean hasFileFacadeFqName() {
                return (this.bitField0_ & 64) == 64;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public String getFileFacadeFqName() {
                Object obj = this.fileFacadeFqName_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.fileFacadeFqName_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr.ClassOrFileOrBuilder
            public ByteString getFileFacadeFqNameBytes() {
                Object obj = this.fileFacadeFqName_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.fileFacadeFqName_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setFileFacadeFqName(String str) {
                str.getClass();
                this.bitField0_ |= 64;
                this.fileFacadeFqName_ = str;
                return this;
            }

            public Builder clearFileFacadeFqName() {
                this.bitField0_ &= -65;
                this.fileFacadeFqName_ = ClassOrFile.getDefaultInstance().getFileFacadeFqName();
                return this;
            }

            public Builder setFileFacadeFqNameBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 64;
                this.fileFacadeFqName_ = byteString;
                return this;
            }
        }
    }
}
