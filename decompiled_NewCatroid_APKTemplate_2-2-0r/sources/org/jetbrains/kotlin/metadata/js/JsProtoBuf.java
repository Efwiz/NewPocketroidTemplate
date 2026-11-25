package org.jetbrains.kotlin.metadata.js;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.metadata.ProtoBuf;
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
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;
import org.jetbrains.kotlin.protobuf.Parser;
import org.jetbrains.kotlin.protobuf.ProtocolStringList;
import org.jetbrains.kotlin.protobuf.WireFormat;

/* JADX INFO: loaded from: classes5.dex */
public final class JsProtoBuf {
    public static final int CLASS_ANNOTATION_FIELD_NUMBER = 130;
    public static final int CLASS_CONTAINING_FILE_ID_FIELD_NUMBER = 135;
    public static final int COMPILE_TIME_VALUE_FIELD_NUMBER = 131;
    public static final int CONSTRUCTOR_ANNOTATION_FIELD_NUMBER = 130;
    public static final int ENUM_ENTRY_ANNOTATION_FIELD_NUMBER = 130;
    public static final int FUNCTION_ANNOTATION_FIELD_NUMBER = 130;
    public static final int FUNCTION_CONTAINING_FILE_ID_FIELD_NUMBER = 135;
    public static final int PACKAGE_FQ_NAME_FIELD_NUMBER = 131;
    public static final int PACKAGE_FRAGMENT_FILES_FIELD_NUMBER = 130;
    public static final int PARAMETER_ANNOTATION_FIELD_NUMBER = 130;
    public static final int PROPERTY_ANNOTATION_FIELD_NUMBER = 130;
    public static final int PROPERTY_CONTAINING_FILE_ID_FIELD_NUMBER = 135;
    public static final int PROPERTY_GETTER_ANNOTATION_FIELD_NUMBER = 132;
    public static final int PROPERTY_SETTER_ANNOTATION_FIELD_NUMBER = 133;
    public static final int TYPE_ANNOTATION_FIELD_NUMBER = 130;
    public static final int TYPE_PARAMETER_ANNOTATION_FIELD_NUMBER = 130;
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> packageFqName = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Package.getDefaultInstance(), 0, null, null, 131, WireFormat.FieldType.INT32, Integer.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Annotation>> classAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Class.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 130, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, Integer> classContainingFileId = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Class.getDefaultInstance(), 0, null, null, 135, WireFormat.FieldType.INT32, Integer.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, List<ProtoBuf.Annotation>> constructorAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Constructor.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 130, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> functionAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Function.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 130, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, Integer> functionContainingFileId = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Function.getDefaultInstance(), 0, null, null, 135, WireFormat.FieldType.INT32, Integer.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 130, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyGetterAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 132, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertySetterAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 133, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, ProtoBuf.Annotation.Argument.Value> compileTimeValue = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), ProtoBuf.Annotation.Argument.Value.getDefaultInstance(), ProtoBuf.Annotation.Argument.Value.getDefaultInstance(), null, 131, WireFormat.FieldType.MESSAGE, ProtoBuf.Annotation.Argument.Value.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, Integer> propertyContainingFileId = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), 0, null, null, 135, WireFormat.FieldType.INT32, Integer.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, List<ProtoBuf.Annotation>> enumEntryAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.EnumEntry.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 130, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.ValueParameter, List<ProtoBuf.Annotation>> parameterAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.ValueParameter.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 130, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> typeAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Type.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 130, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> typeParameterAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.TypeParameter.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 130, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.PackageFragment, Files> packageFragmentFiles = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.PackageFragment.getDefaultInstance(), Files.getDefaultInstance(), Files.getDefaultInstance(), null, 130, WireFormat.FieldType.MESSAGE, Files.class);

    public interface ClassesOrBuilder extends MessageLiteOrBuilder {
        int getClassName(int i);

        int getClassNameCount();

        List<Integer> getClassNameList();
    }

    public interface FileOrBuilder extends MessageLiteOrBuilder {
        ProtoBuf.Annotation getAnnotation(int i);

        int getAnnotationCount();

        List<ProtoBuf.Annotation> getAnnotationList();

        int getId();

        boolean hasId();
    }

    public interface FilesOrBuilder extends MessageLiteOrBuilder {
        File getFile(int i);

        int getFileCount();

        List<File> getFileList();
    }

    public interface HeaderOrBuilder extends MessageLiteOrBuilder {
        ProtoBuf.Annotation getAnnotation(int i);

        int getAnnotationCount();

        List<ProtoBuf.Annotation> getAnnotationList();

        int getFlags();

        int getJsCodeBinaryVersion();

        String getPackageFqName();

        ByteString getPackageFqNameBytes();

        ProtoBuf.QualifiedNameTable getQualifiedNames();

        ProtoBuf.StringTable getStrings();

        boolean hasFlags();

        boolean hasJsCodeBinaryVersion();

        boolean hasPackageFqName();

        boolean hasQualifiedNames();

        boolean hasStrings();
    }

    public interface LibraryOrBuilder extends MessageLiteOrBuilder {
        String getImportedModule(int i);

        ByteString getImportedModuleBytes(int i);

        int getImportedModuleCount();

        ProtocolStringList getImportedModuleList();

        Library.Kind getKind();

        ProtoBuf.PackageFragment getPackageFragment(int i);

        int getPackageFragmentCount();

        List<ProtoBuf.PackageFragment> getPackageFragmentList();

        boolean hasKind();
    }

    private JsProtoBuf() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
        extensionRegistryLite.add(packageFqName);
        extensionRegistryLite.add(classAnnotation);
        extensionRegistryLite.add(classContainingFileId);
        extensionRegistryLite.add(constructorAnnotation);
        extensionRegistryLite.add(functionAnnotation);
        extensionRegistryLite.add(functionContainingFileId);
        extensionRegistryLite.add(propertyAnnotation);
        extensionRegistryLite.add(propertyGetterAnnotation);
        extensionRegistryLite.add(propertySetterAnnotation);
        extensionRegistryLite.add(compileTimeValue);
        extensionRegistryLite.add(propertyContainingFileId);
        extensionRegistryLite.add(enumEntryAnnotation);
        extensionRegistryLite.add(parameterAnnotation);
        extensionRegistryLite.add(typeAnnotation);
        extensionRegistryLite.add(typeParameterAnnotation);
        extensionRegistryLite.add(packageFragmentFiles);
    }

    public static final class Header extends GeneratedMessageLite implements HeaderOrBuilder {
        public static final int ANNOTATION_FIELD_NUMBER = 6;
        public static final int FLAGS_FIELD_NUMBER = 1;
        public static final int JS_CODE_BINARY_VERSION_FIELD_NUMBER = 2;
        public static final int PACKAGE_FQ_NAME_FIELD_NUMBER = 3;
        public static Parser<Header> PARSER = new AbstractParser<Header>() { // from class: org.jetbrains.kotlin.metadata.js.JsProtoBuf.Header.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public Header parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Header(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int QUALIFIED_NAMES_FIELD_NUMBER = 5;
        public static final int STRINGS_FIELD_NUMBER = 4;
        private static final Header defaultInstance;
        private static final long serialVersionUID = 0;
        private List<ProtoBuf.Annotation> annotation_;
        private int bitField0_;
        private int flags_;
        private int jsCodeBinaryVersion_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object packageFqName_;
        private ProtoBuf.QualifiedNameTable qualifiedNames_;
        private ProtoBuf.StringTable strings_;
        private final ByteString unknownFields;

        private Header(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Header(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        public static Header getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public Header getDefaultInstanceForType() {
            return defaultInstance;
        }

        private Header(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            GeneratedMessageLite.Builder builder;
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
                                    this.flags_ = codedInputStream.readInt32();
                                } else if (tag == 16) {
                                    this.bitField0_ |= 2;
                                    this.jsCodeBinaryVersion_ = codedInputStream.readInt32();
                                } else if (tag != 26) {
                                    if (tag == 34) {
                                        builder = (this.bitField0_ & 8) == 8 ? this.strings_.toBuilder() : null;
                                        ProtoBuf.StringTable stringTable = (ProtoBuf.StringTable) codedInputStream.readMessage(ProtoBuf.StringTable.PARSER, extensionRegistryLite);
                                        this.strings_ = stringTable;
                                        if (builder != null) {
                                            builder.mergeFrom(stringTable);
                                            this.strings_ = builder.buildPartial();
                                        }
                                        this.bitField0_ |= 8;
                                    } else if (tag == 42) {
                                        builder = (this.bitField0_ & 16) == 16 ? this.qualifiedNames_.toBuilder() : null;
                                        ProtoBuf.QualifiedNameTable qualifiedNameTable = (ProtoBuf.QualifiedNameTable) codedInputStream.readMessage(ProtoBuf.QualifiedNameTable.PARSER, extensionRegistryLite);
                                        this.qualifiedNames_ = qualifiedNameTable;
                                        if (builder != null) {
                                            builder.mergeFrom(qualifiedNameTable);
                                            this.qualifiedNames_ = builder.buildPartial();
                                        }
                                        this.bitField0_ |= 16;
                                    } else if (tag != 50) {
                                        if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                        }
                                    } else {
                                        if ((c & StringUtil.SPACE) != 32) {
                                            this.annotation_ = new ArrayList();
                                            c = ' ';
                                        }
                                        this.annotation_.add((ProtoBuf.Annotation) codedInputStream.readMessage(ProtoBuf.Annotation.PARSER, extensionRegistryLite));
                                    }
                                } else {
                                    ByteString bytes = codedInputStream.readBytes();
                                    this.bitField0_ |= 4;
                                    this.packageFqName_ = bytes;
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
                    if ((c & StringUtil.SPACE) == 32) {
                        this.annotation_ = Collections.unmodifiableList(this.annotation_);
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
            if ((c & StringUtil.SPACE) == 32) {
                this.annotation_ = Collections.unmodifiableList(this.annotation_);
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
            Header header = new Header(true);
            defaultInstance = header;
            header.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<Header> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
        public boolean hasFlags() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
        public int getFlags() {
            return this.flags_;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
        public boolean hasJsCodeBinaryVersion() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
        public int getJsCodeBinaryVersion() {
            return this.jsCodeBinaryVersion_;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
        public boolean hasPackageFqName() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
        public String getPackageFqName() {
            Object obj = this.packageFqName_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.packageFqName_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
        public ByteString getPackageFqNameBytes() {
            Object obj = this.packageFqName_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.packageFqName_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
        public boolean hasStrings() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
        public ProtoBuf.StringTable getStrings() {
            return this.strings_;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
        public boolean hasQualifiedNames() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
        public ProtoBuf.QualifiedNameTable getQualifiedNames() {
            return this.qualifiedNames_;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
        public List<ProtoBuf.Annotation> getAnnotationList() {
            return this.annotation_;
        }

        public List<? extends ProtoBuf.AnnotationOrBuilder> getAnnotationOrBuilderList() {
            return this.annotation_;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
        public int getAnnotationCount() {
            return this.annotation_.size();
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
        public ProtoBuf.Annotation getAnnotation(int i) {
            return this.annotation_.get(i);
        }

        public ProtoBuf.AnnotationOrBuilder getAnnotationOrBuilder(int i) {
            return this.annotation_.get(i);
        }

        private void initFields() {
            this.flags_ = 0;
            this.jsCodeBinaryVersion_ = 1;
            this.packageFqName_ = "";
            this.strings_ = ProtoBuf.StringTable.getDefaultInstance();
            this.qualifiedNames_ = ProtoBuf.QualifiedNameTable.getDefaultInstance();
            this.annotation_ = Collections.emptyList();
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
            if (hasQualifiedNames() && !getQualifiedNames().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            for (int i = 0; i < getAnnotationCount(); i++) {
                if (!getAnnotation(i).isInitialized()) {
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
                codedOutputStream.writeInt32(1, this.flags_);
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeInt32(2, this.jsCodeBinaryVersion_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeBytes(3, getPackageFqNameBytes());
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeMessage(4, this.strings_);
            }
            if ((this.bitField0_ & 16) == 16) {
                codedOutputStream.writeMessage(5, this.qualifiedNames_);
            }
            for (int i = 0; i < this.annotation_.size(); i++) {
                codedOutputStream.writeMessage(6, this.annotation_.get(i));
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.flags_) : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(2, this.jsCodeBinaryVersion_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeInt32Size += CodedOutputStream.computeBytesSize(3, getPackageFqNameBytes());
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(4, this.strings_);
            }
            if ((this.bitField0_ & 16) == 16) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(5, this.qualifiedNames_);
            }
            for (int i2 = 0; i2 < this.annotation_.size(); i2++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(6, this.annotation_.get(i2));
            }
            int size = iComputeInt32Size + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static Header parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static Header parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static Header parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static Header parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static Header parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static Header parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static Header parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static Header parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static Header parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static Header parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(Header header) {
            return newBuilder().mergeFrom(header);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Header, Builder> implements HeaderOrBuilder {
            private int bitField0_;
            private int flags_;
            private int jsCodeBinaryVersion_ = 1;
            private Object packageFqName_ = "";
            private ProtoBuf.StringTable strings_ = ProtoBuf.StringTable.getDefaultInstance();
            private ProtoBuf.QualifiedNameTable qualifiedNames_ = ProtoBuf.QualifiedNameTable.getDefaultInstance();
            private List<ProtoBuf.Annotation> annotation_ = Collections.emptyList();

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
                this.flags_ = 0;
                int i = this.bitField0_;
                this.jsCodeBinaryVersion_ = 1;
                this.packageFqName_ = "";
                this.bitField0_ = i & (-8);
                this.strings_ = ProtoBuf.StringTable.getDefaultInstance();
                this.bitField0_ &= -9;
                this.qualifiedNames_ = ProtoBuf.QualifiedNameTable.getDefaultInstance();
                this.bitField0_ &= -17;
                this.annotation_ = Collections.emptyList();
                this.bitField0_ &= -33;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public Header getDefaultInstanceForType() {
                return Header.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Header build() {
                Header headerBuildPartial = buildPartial();
                if (headerBuildPartial.isInitialized()) {
                    return headerBuildPartial;
                }
                throw newUninitializedMessageException(headerBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Header buildPartial() {
                Header header = new Header(this);
                int i = this.bitField0_;
                int i2 = (i & 1) != 1 ? 0 : 1;
                header.flags_ = this.flags_;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                header.jsCodeBinaryVersion_ = this.jsCodeBinaryVersion_;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                header.packageFqName_ = this.packageFqName_;
                if ((i & 8) == 8) {
                    i2 |= 8;
                }
                header.strings_ = this.strings_;
                if ((i & 16) == 16) {
                    i2 |= 16;
                }
                header.qualifiedNames_ = this.qualifiedNames_;
                if ((this.bitField0_ & 32) == 32) {
                    this.annotation_ = Collections.unmodifiableList(this.annotation_);
                    this.bitField0_ &= -33;
                }
                header.annotation_ = this.annotation_;
                header.bitField0_ = i2;
                return header;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Header header) {
                if (header == Header.getDefaultInstance()) {
                    return this;
                }
                if (header.hasFlags()) {
                    setFlags(header.getFlags());
                }
                if (header.hasJsCodeBinaryVersion()) {
                    setJsCodeBinaryVersion(header.getJsCodeBinaryVersion());
                }
                if (header.hasPackageFqName()) {
                    this.bitField0_ |= 4;
                    this.packageFqName_ = header.packageFqName_;
                }
                if (header.hasStrings()) {
                    mergeStrings(header.getStrings());
                }
                if (header.hasQualifiedNames()) {
                    mergeQualifiedNames(header.getQualifiedNames());
                }
                if (!header.annotation_.isEmpty()) {
                    if (this.annotation_.isEmpty()) {
                        this.annotation_ = header.annotation_;
                        this.bitField0_ &= -33;
                    } else {
                        ensureAnnotationIsMutable();
                        this.annotation_.addAll(header.annotation_);
                    }
                }
                setUnknownFields(getUnknownFields().concat(header.unknownFields));
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                if (hasQualifiedNames() && !getQualifiedNames().isInitialized()) {
                    return false;
                }
                for (int i = 0; i < getAnnotationCount(); i++) {
                    if (!getAnnotation(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                Header header = null;
                try {
                    try {
                        Header partialFrom = Header.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        Header header2 = (Header) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            header = header2;
                            if (header != null) {
                                mergeFrom(header);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (header != null) {
                        mergeFrom(header);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
            public boolean hasFlags() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
            public int getFlags() {
                return this.flags_;
            }

            public Builder setFlags(int i) {
                this.bitField0_ |= 1;
                this.flags_ = i;
                return this;
            }

            public Builder clearFlags() {
                this.bitField0_ &= -2;
                this.flags_ = 0;
                return this;
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
            public boolean hasJsCodeBinaryVersion() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
            public int getJsCodeBinaryVersion() {
                return this.jsCodeBinaryVersion_;
            }

            public Builder setJsCodeBinaryVersion(int i) {
                this.bitField0_ |= 2;
                this.jsCodeBinaryVersion_ = i;
                return this;
            }

            public Builder clearJsCodeBinaryVersion() {
                this.bitField0_ &= -3;
                this.jsCodeBinaryVersion_ = 1;
                return this;
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
            public boolean hasPackageFqName() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
            public String getPackageFqName() {
                Object obj = this.packageFqName_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.packageFqName_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
            public ByteString getPackageFqNameBytes() {
                Object obj = this.packageFqName_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.packageFqName_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setPackageFqName(String str) {
                str.getClass();
                this.bitField0_ |= 4;
                this.packageFqName_ = str;
                return this;
            }

            public Builder clearPackageFqName() {
                this.bitField0_ &= -5;
                this.packageFqName_ = Header.getDefaultInstance().getPackageFqName();
                return this;
            }

            public Builder setPackageFqNameBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 4;
                this.packageFqName_ = byteString;
                return this;
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
            public boolean hasStrings() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
            public ProtoBuf.StringTable getStrings() {
                return this.strings_;
            }

            public Builder setStrings(ProtoBuf.StringTable stringTable) {
                stringTable.getClass();
                this.strings_ = stringTable;
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setStrings(ProtoBuf.StringTable.Builder builder) {
                this.strings_ = builder.build();
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeStrings(ProtoBuf.StringTable stringTable) {
                if ((this.bitField0_ & 8) == 8 && this.strings_ != ProtoBuf.StringTable.getDefaultInstance()) {
                    this.strings_ = ProtoBuf.StringTable.newBuilder(this.strings_).mergeFrom(stringTable).buildPartial();
                } else {
                    this.strings_ = stringTable;
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder clearStrings() {
                this.strings_ = ProtoBuf.StringTable.getDefaultInstance();
                this.bitField0_ &= -9;
                return this;
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
            public boolean hasQualifiedNames() {
                return (this.bitField0_ & 16) == 16;
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
            public ProtoBuf.QualifiedNameTable getQualifiedNames() {
                return this.qualifiedNames_;
            }

            public Builder setQualifiedNames(ProtoBuf.QualifiedNameTable qualifiedNameTable) {
                qualifiedNameTable.getClass();
                this.qualifiedNames_ = qualifiedNameTable;
                this.bitField0_ |= 16;
                return this;
            }

            public Builder setQualifiedNames(ProtoBuf.QualifiedNameTable.Builder builder) {
                this.qualifiedNames_ = builder.build();
                this.bitField0_ |= 16;
                return this;
            }

            public Builder mergeQualifiedNames(ProtoBuf.QualifiedNameTable qualifiedNameTable) {
                if ((this.bitField0_ & 16) == 16 && this.qualifiedNames_ != ProtoBuf.QualifiedNameTable.getDefaultInstance()) {
                    this.qualifiedNames_ = ProtoBuf.QualifiedNameTable.newBuilder(this.qualifiedNames_).mergeFrom(qualifiedNameTable).buildPartial();
                } else {
                    this.qualifiedNames_ = qualifiedNameTable;
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder clearQualifiedNames() {
                this.qualifiedNames_ = ProtoBuf.QualifiedNameTable.getDefaultInstance();
                this.bitField0_ &= -17;
                return this;
            }

            private void ensureAnnotationIsMutable() {
                if ((this.bitField0_ & 32) != 32) {
                    this.annotation_ = new ArrayList(this.annotation_);
                    this.bitField0_ |= 32;
                }
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
            public List<ProtoBuf.Annotation> getAnnotationList() {
                return Collections.unmodifiableList(this.annotation_);
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
            public int getAnnotationCount() {
                return this.annotation_.size();
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.HeaderOrBuilder
            public ProtoBuf.Annotation getAnnotation(int i) {
                return this.annotation_.get(i);
            }

            public Builder setAnnotation(int i, ProtoBuf.Annotation annotation) {
                annotation.getClass();
                ensureAnnotationIsMutable();
                this.annotation_.set(i, annotation);
                return this;
            }

            public Builder setAnnotation(int i, ProtoBuf.Annotation.Builder builder) {
                ensureAnnotationIsMutable();
                this.annotation_.set(i, builder.build());
                return this;
            }

            public Builder addAnnotation(ProtoBuf.Annotation annotation) {
                annotation.getClass();
                ensureAnnotationIsMutable();
                this.annotation_.add(annotation);
                return this;
            }

            public Builder addAnnotation(int i, ProtoBuf.Annotation annotation) {
                annotation.getClass();
                ensureAnnotationIsMutable();
                this.annotation_.add(i, annotation);
                return this;
            }

            public Builder addAnnotation(ProtoBuf.Annotation.Builder builder) {
                ensureAnnotationIsMutable();
                this.annotation_.add(builder.build());
                return this;
            }

            public Builder addAnnotation(int i, ProtoBuf.Annotation.Builder builder) {
                ensureAnnotationIsMutable();
                this.annotation_.add(i, builder.build());
                return this;
            }

            public Builder addAllAnnotation(Iterable<? extends ProtoBuf.Annotation> iterable) {
                ensureAnnotationIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.annotation_);
                return this;
            }

            public Builder clearAnnotation() {
                this.annotation_ = Collections.emptyList();
                this.bitField0_ &= -33;
                return this;
            }

            public Builder removeAnnotation(int i) {
                ensureAnnotationIsMutable();
                this.annotation_.remove(i);
                return this;
            }
        }
    }

    public static final class File extends GeneratedMessageLite implements FileOrBuilder {
        public static final int ANNOTATION_FIELD_NUMBER = 2;
        public static final int ID_FIELD_NUMBER = 1;
        public static Parser<File> PARSER = new AbstractParser<File>() { // from class: org.jetbrains.kotlin.metadata.js.JsProtoBuf.File.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public File parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new File(codedInputStream, extensionRegistryLite);
            }
        };
        private static final File defaultInstance;
        private static final long serialVersionUID = 0;
        private List<ProtoBuf.Annotation> annotation_;
        private int bitField0_;
        private int id_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private final ByteString unknownFields;

        private File(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private File(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        public static File getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public File getDefaultInstanceForType() {
            return defaultInstance;
        }

        private File(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                                    this.id_ = codedInputStream.readInt32();
                                } else if (tag != 18) {
                                    if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    if ((c & 2) != 2) {
                                        this.annotation_ = new ArrayList();
                                        c = 2;
                                    }
                                    this.annotation_.add((ProtoBuf.Annotation) codedInputStream.readMessage(ProtoBuf.Annotation.PARSER, extensionRegistryLite));
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
                        this.annotation_ = Collections.unmodifiableList(this.annotation_);
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
                this.annotation_ = Collections.unmodifiableList(this.annotation_);
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
            File file = new File(true);
            defaultInstance = file;
            file.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<File> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FileOrBuilder
        public boolean hasId() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FileOrBuilder
        public int getId() {
            return this.id_;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FileOrBuilder
        public List<ProtoBuf.Annotation> getAnnotationList() {
            return this.annotation_;
        }

        public List<? extends ProtoBuf.AnnotationOrBuilder> getAnnotationOrBuilderList() {
            return this.annotation_;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FileOrBuilder
        public int getAnnotationCount() {
            return this.annotation_.size();
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FileOrBuilder
        public ProtoBuf.Annotation getAnnotation(int i) {
            return this.annotation_.get(i);
        }

        public ProtoBuf.AnnotationOrBuilder getAnnotationOrBuilder(int i) {
            return this.annotation_.get(i);
        }

        private void initFields() {
            this.id_ = 0;
            this.annotation_ = Collections.emptyList();
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
            for (int i = 0; i < getAnnotationCount(); i++) {
                if (!getAnnotation(i).isInitialized()) {
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
                codedOutputStream.writeInt32(1, this.id_);
            }
            for (int i = 0; i < this.annotation_.size(); i++) {
                codedOutputStream.writeMessage(2, this.annotation_.get(i));
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.id_) : 0;
            for (int i2 = 0; i2 < this.annotation_.size(); i2++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(2, this.annotation_.get(i2));
            }
            int size = iComputeInt32Size + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static File parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static File parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static File parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static File parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static File parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static File parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static File parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static File parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static File parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static File parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(File file) {
            return newBuilder().mergeFrom(file);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<File, Builder> implements FileOrBuilder {
            private List<ProtoBuf.Annotation> annotation_ = Collections.emptyList();
            private int bitField0_;
            private int id_;

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
                this.id_ = 0;
                this.bitField0_ &= -2;
                this.annotation_ = Collections.emptyList();
                this.bitField0_ &= -3;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public File getDefaultInstanceForType() {
                return File.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public File build() {
                File fileBuildPartial = buildPartial();
                if (fileBuildPartial.isInitialized()) {
                    return fileBuildPartial;
                }
                throw newUninitializedMessageException(fileBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public File buildPartial() {
                File file = new File(this);
                int i = (this.bitField0_ & 1) != 1 ? 0 : 1;
                file.id_ = this.id_;
                if ((this.bitField0_ & 2) == 2) {
                    this.annotation_ = Collections.unmodifiableList(this.annotation_);
                    this.bitField0_ &= -3;
                }
                file.annotation_ = this.annotation_;
                file.bitField0_ = i;
                return file;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(File file) {
                if (file == File.getDefaultInstance()) {
                    return this;
                }
                if (file.hasId()) {
                    setId(file.getId());
                }
                if (!file.annotation_.isEmpty()) {
                    if (this.annotation_.isEmpty()) {
                        this.annotation_ = file.annotation_;
                        this.bitField0_ &= -3;
                    } else {
                        ensureAnnotationIsMutable();
                        this.annotation_.addAll(file.annotation_);
                    }
                }
                setUnknownFields(getUnknownFields().concat(file.unknownFields));
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getAnnotationCount(); i++) {
                    if (!getAnnotation(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                File file = null;
                try {
                    try {
                        File partialFrom = File.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        File file2 = (File) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            file = file2;
                            if (file != null) {
                                mergeFrom(file);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (file != null) {
                        mergeFrom(file);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FileOrBuilder
            public boolean hasId() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FileOrBuilder
            public int getId() {
                return this.id_;
            }

            public Builder setId(int i) {
                this.bitField0_ |= 1;
                this.id_ = i;
                return this;
            }

            public Builder clearId() {
                this.bitField0_ &= -2;
                this.id_ = 0;
                return this;
            }

            private void ensureAnnotationIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.annotation_ = new ArrayList(this.annotation_);
                    this.bitField0_ |= 2;
                }
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FileOrBuilder
            public List<ProtoBuf.Annotation> getAnnotationList() {
                return Collections.unmodifiableList(this.annotation_);
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FileOrBuilder
            public int getAnnotationCount() {
                return this.annotation_.size();
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FileOrBuilder
            public ProtoBuf.Annotation getAnnotation(int i) {
                return this.annotation_.get(i);
            }

            public Builder setAnnotation(int i, ProtoBuf.Annotation annotation) {
                annotation.getClass();
                ensureAnnotationIsMutable();
                this.annotation_.set(i, annotation);
                return this;
            }

            public Builder setAnnotation(int i, ProtoBuf.Annotation.Builder builder) {
                ensureAnnotationIsMutable();
                this.annotation_.set(i, builder.build());
                return this;
            }

            public Builder addAnnotation(ProtoBuf.Annotation annotation) {
                annotation.getClass();
                ensureAnnotationIsMutable();
                this.annotation_.add(annotation);
                return this;
            }

            public Builder addAnnotation(int i, ProtoBuf.Annotation annotation) {
                annotation.getClass();
                ensureAnnotationIsMutable();
                this.annotation_.add(i, annotation);
                return this;
            }

            public Builder addAnnotation(ProtoBuf.Annotation.Builder builder) {
                ensureAnnotationIsMutable();
                this.annotation_.add(builder.build());
                return this;
            }

            public Builder addAnnotation(int i, ProtoBuf.Annotation.Builder builder) {
                ensureAnnotationIsMutable();
                this.annotation_.add(i, builder.build());
                return this;
            }

            public Builder addAllAnnotation(Iterable<? extends ProtoBuf.Annotation> iterable) {
                ensureAnnotationIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.annotation_);
                return this;
            }

            public Builder clearAnnotation() {
                this.annotation_ = Collections.emptyList();
                this.bitField0_ &= -3;
                return this;
            }

            public Builder removeAnnotation(int i) {
                ensureAnnotationIsMutable();
                this.annotation_.remove(i);
                return this;
            }
        }
    }

    public static final class Files extends GeneratedMessageLite implements FilesOrBuilder {
        public static final int FILE_FIELD_NUMBER = 1;
        public static Parser<Files> PARSER = new AbstractParser<Files>() { // from class: org.jetbrains.kotlin.metadata.js.JsProtoBuf.Files.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public Files parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Files(codedInputStream, extensionRegistryLite);
            }
        };
        private static final Files defaultInstance;
        private static final long serialVersionUID = 0;
        private List<File> file_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private final ByteString unknownFields;

        private Files(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Files(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        public static Files getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public Files getDefaultInstanceForType() {
            return defaultInstance;
        }

        private Files(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                                        this.file_ = new ArrayList();
                                        z2 = true;
                                    }
                                    this.file_.add((File) codedInputStream.readMessage(File.PARSER, extensionRegistryLite));
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
                        this.file_ = Collections.unmodifiableList(this.file_);
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
                this.file_ = Collections.unmodifiableList(this.file_);
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
            Files files = new Files(true);
            defaultInstance = files;
            files.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<Files> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FilesOrBuilder
        public List<File> getFileList() {
            return this.file_;
        }

        public List<? extends FileOrBuilder> getFileOrBuilderList() {
            return this.file_;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FilesOrBuilder
        public int getFileCount() {
            return this.file_.size();
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FilesOrBuilder
        public File getFile(int i) {
            return this.file_.get(i);
        }

        public FileOrBuilder getFileOrBuilder(int i) {
            return this.file_.get(i);
        }

        private void initFields() {
            this.file_ = Collections.emptyList();
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
            for (int i = 0; i < getFileCount(); i++) {
                if (!getFile(i).isInitialized()) {
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
            for (int i = 0; i < this.file_.size(); i++) {
                codedOutputStream.writeMessage(1, this.file_.get(i));
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
            for (int i2 = 0; i2 < this.file_.size(); i2++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.file_.get(i2));
            }
            int size = iComputeMessageSize + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static Files parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static Files parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static Files parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static Files parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static Files parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static Files parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static Files parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static Files parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static Files parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static Files parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(Files files) {
            return newBuilder().mergeFrom(files);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Files, Builder> implements FilesOrBuilder {
            private int bitField0_;
            private List<File> file_ = Collections.emptyList();

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
                this.file_ = Collections.emptyList();
                this.bitField0_ &= -2;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public Files getDefaultInstanceForType() {
                return Files.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Files build() {
                Files filesBuildPartial = buildPartial();
                if (filesBuildPartial.isInitialized()) {
                    return filesBuildPartial;
                }
                throw newUninitializedMessageException(filesBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Files buildPartial() {
                Files files = new Files(this);
                if ((this.bitField0_ & 1) == 1) {
                    this.file_ = Collections.unmodifiableList(this.file_);
                    this.bitField0_ &= -2;
                }
                files.file_ = this.file_;
                return files;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Files files) {
                if (files == Files.getDefaultInstance()) {
                    return this;
                }
                if (!files.file_.isEmpty()) {
                    if (this.file_.isEmpty()) {
                        this.file_ = files.file_;
                        this.bitField0_ &= -2;
                    } else {
                        ensureFileIsMutable();
                        this.file_.addAll(files.file_);
                    }
                }
                setUnknownFields(getUnknownFields().concat(files.unknownFields));
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getFileCount(); i++) {
                    if (!getFile(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                Files files = null;
                try {
                    try {
                        Files partialFrom = Files.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        Files files2 = (Files) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            files = files2;
                            if (files != null) {
                                mergeFrom(files);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (files != null) {
                        mergeFrom(files);
                    }
                    throw th;
                }
            }

            private void ensureFileIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.file_ = new ArrayList(this.file_);
                    this.bitField0_ |= 1;
                }
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FilesOrBuilder
            public List<File> getFileList() {
                return Collections.unmodifiableList(this.file_);
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FilesOrBuilder
            public int getFileCount() {
                return this.file_.size();
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.FilesOrBuilder
            public File getFile(int i) {
                return this.file_.get(i);
            }

            public Builder setFile(int i, File file) {
                file.getClass();
                ensureFileIsMutable();
                this.file_.set(i, file);
                return this;
            }

            public Builder setFile(int i, File.Builder builder) {
                ensureFileIsMutable();
                this.file_.set(i, builder.build());
                return this;
            }

            public Builder addFile(File file) {
                file.getClass();
                ensureFileIsMutable();
                this.file_.add(file);
                return this;
            }

            public Builder addFile(int i, File file) {
                file.getClass();
                ensureFileIsMutable();
                this.file_.add(i, file);
                return this;
            }

            public Builder addFile(File.Builder builder) {
                ensureFileIsMutable();
                this.file_.add(builder.build());
                return this;
            }

            public Builder addFile(int i, File.Builder builder) {
                ensureFileIsMutable();
                this.file_.add(i, builder.build());
                return this;
            }

            public Builder addAllFile(Iterable<? extends File> iterable) {
                ensureFileIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.file_);
                return this;
            }

            public Builder clearFile() {
                this.file_ = Collections.emptyList();
                this.bitField0_ &= -2;
                return this;
            }

            public Builder removeFile(int i) {
                ensureFileIsMutable();
                this.file_.remove(i);
                return this;
            }
        }
    }

    public static final class Classes extends GeneratedMessageLite implements ClassesOrBuilder {
        public static final int CLASS_NAME_FIELD_NUMBER = 1;
        public static Parser<Classes> PARSER = new AbstractParser<Classes>() { // from class: org.jetbrains.kotlin.metadata.js.JsProtoBuf.Classes.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public Classes parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Classes(codedInputStream, extensionRegistryLite);
            }
        };
        private static final Classes defaultInstance;
        private static final long serialVersionUID = 0;
        private int classNameMemoizedSerializedSize;
        private List<Integer> className_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private final ByteString unknownFields;

        private Classes(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.classNameMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Classes(boolean z) {
            this.classNameMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        public static Classes getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public Classes getDefaultInstanceForType() {
            return defaultInstance;
        }

        private Classes(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.classNameMemoizedSerializedSize = -1;
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
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 8) {
                                if (!z2) {
                                    this.className_ = new ArrayList();
                                    z2 = true;
                                }
                                this.className_.add(Integer.valueOf(codedInputStream.readInt32()));
                            } else if (tag != 10) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                if (!z2 && codedInputStream.getBytesUntilLimit() > 0) {
                                    this.className_ = new ArrayList();
                                    z2 = true;
                                }
                                while (codedInputStream.getBytesUntilLimit() > 0) {
                                    this.className_.add(Integer.valueOf(codedInputStream.readInt32()));
                                }
                                codedInputStream.popLimit(iPushLimit);
                            }
                        }
                        z = true;
                    } catch (Throwable th) {
                        if (z2) {
                            this.className_ = Collections.unmodifiableList(this.className_);
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
            if (z2) {
                this.className_ = Collections.unmodifiableList(this.className_);
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
            Classes classes = new Classes(true);
            defaultInstance = classes;
            classes.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<Classes> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.ClassesOrBuilder
        public List<Integer> getClassNameList() {
            return this.className_;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.ClassesOrBuilder
        public int getClassNameCount() {
            return this.className_.size();
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.ClassesOrBuilder
        public int getClassName(int i) {
            return this.className_.get(i).intValue();
        }

        private void initFields() {
            this.className_ = Collections.emptyList();
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
            if (getClassNameList().size() > 0) {
                codedOutputStream.writeRawVarint32(10);
                codedOutputStream.writeRawVarint32(this.classNameMemoizedSerializedSize);
            }
            for (int i = 0; i < this.className_.size(); i++) {
                codedOutputStream.writeInt32NoTag(this.className_.get(i).intValue());
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
            for (int i2 = 0; i2 < this.className_.size(); i2++) {
                iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.className_.get(i2).intValue());
            }
            int iComputeInt32SizeNoTag2 = !getClassNameList().isEmpty() ? iComputeInt32SizeNoTag + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag) : iComputeInt32SizeNoTag;
            this.classNameMemoizedSerializedSize = iComputeInt32SizeNoTag;
            int size = iComputeInt32SizeNoTag2 + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static Classes parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static Classes parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static Classes parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static Classes parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static Classes parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static Classes parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static Classes parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static Classes parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static Classes parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static Classes parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(Classes classes) {
            return newBuilder().mergeFrom(classes);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Classes, Builder> implements ClassesOrBuilder {
            private int bitField0_;
            private List<Integer> className_ = Collections.emptyList();

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
                this.className_ = Collections.emptyList();
                this.bitField0_ &= -2;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public Classes getDefaultInstanceForType() {
                return Classes.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Classes build() {
                Classes classesBuildPartial = buildPartial();
                if (classesBuildPartial.isInitialized()) {
                    return classesBuildPartial;
                }
                throw newUninitializedMessageException(classesBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Classes buildPartial() {
                Classes classes = new Classes(this);
                if ((this.bitField0_ & 1) == 1) {
                    this.className_ = Collections.unmodifiableList(this.className_);
                    this.bitField0_ &= -2;
                }
                classes.className_ = this.className_;
                return classes;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Classes classes) {
                if (classes == Classes.getDefaultInstance()) {
                    return this;
                }
                if (!classes.className_.isEmpty()) {
                    if (this.className_.isEmpty()) {
                        this.className_ = classes.className_;
                        this.bitField0_ &= -2;
                    } else {
                        ensureClassNameIsMutable();
                        this.className_.addAll(classes.className_);
                    }
                }
                setUnknownFields(getUnknownFields().concat(classes.unknownFields));
                return this;
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                Classes classes = null;
                try {
                    try {
                        Classes partialFrom = Classes.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        Classes classes2 = (Classes) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            classes = classes2;
                            if (classes != null) {
                                mergeFrom(classes);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (classes != null) {
                        mergeFrom(classes);
                    }
                    throw th;
                }
            }

            private void ensureClassNameIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.className_ = new ArrayList(this.className_);
                    this.bitField0_ |= 1;
                }
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.ClassesOrBuilder
            public List<Integer> getClassNameList() {
                return Collections.unmodifiableList(this.className_);
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.ClassesOrBuilder
            public int getClassNameCount() {
                return this.className_.size();
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.ClassesOrBuilder
            public int getClassName(int i) {
                return this.className_.get(i).intValue();
            }

            public Builder setClassName(int i, int i2) {
                ensureClassNameIsMutable();
                this.className_.set(i, Integer.valueOf(i2));
                return this;
            }

            public Builder addClassName(int i) {
                ensureClassNameIsMutable();
                this.className_.add(Integer.valueOf(i));
                return this;
            }

            public Builder addAllClassName(Iterable<? extends Integer> iterable) {
                ensureClassNameIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.className_);
                return this;
            }

            public Builder clearClassName() {
                this.className_ = Collections.emptyList();
                this.bitField0_ &= -2;
                return this;
            }
        }
    }

    public static final class Library extends GeneratedMessageLite implements LibraryOrBuilder {
        public static final int IMPORTED_MODULE_FIELD_NUMBER = 3;
        public static final int KIND_FIELD_NUMBER = 1;
        public static final int PACKAGE_FRAGMENT_FIELD_NUMBER = 2;
        public static Parser<Library> PARSER = new AbstractParser<Library>() { // from class: org.jetbrains.kotlin.metadata.js.JsProtoBuf.Library.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public Library parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Library(codedInputStream, extensionRegistryLite);
            }
        };
        private static final Library defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private LazyStringList importedModule_;
        private Kind kind_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<ProtoBuf.PackageFragment> packageFragment_;
        private final ByteString unknownFields;

        private Library(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Library(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        public static Library getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public Library getDefaultInstanceForType() {
            return defaultInstance;
        }

        private Library(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
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
                                int i2 = codedInputStream.readEnum();
                                Kind kindValueOf = Kind.valueOf(i2);
                                if (kindValueOf == null) {
                                    codedOutputStreamNewInstance.writeRawVarint32(tag);
                                    codedOutputStreamNewInstance.writeRawVarint32(i2);
                                } else {
                                    this.bitField0_ |= 1;
                                    this.kind_ = kindValueOf;
                                }
                            } else if (tag == 18) {
                                if ((i & 2) != 2) {
                                    this.packageFragment_ = new ArrayList();
                                    i |= 2;
                                }
                                this.packageFragment_.add((ProtoBuf.PackageFragment) codedInputStream.readMessage(ProtoBuf.PackageFragment.PARSER, extensionRegistryLite));
                            } else if (tag != 26) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                ByteString bytes = codedInputStream.readBytes();
                                if ((i & 4) != 4) {
                                    this.importedModule_ = new LazyStringArrayList();
                                    i |= 4;
                                }
                                this.importedModule_.add(bytes);
                            }
                        }
                        z = true;
                    } catch (Throwable th) {
                        if ((i & 2) == 2) {
                            this.packageFragment_ = Collections.unmodifiableList(this.packageFragment_);
                        }
                        if ((i & 4) == 4) {
                            this.importedModule_ = this.importedModule_.getUnmodifiableView();
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
            if ((i & 2) == 2) {
                this.packageFragment_ = Collections.unmodifiableList(this.packageFragment_);
            }
            if ((i & 4) == 4) {
                this.importedModule_ = this.importedModule_.getUnmodifiableView();
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
            Library library = new Library(true);
            defaultInstance = library;
            library.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<Library> getParserForType() {
            return PARSER;
        }

        public enum Kind implements Internal.EnumLite {
            PLAIN(0, 1),
            AMD(1, 2),
            COMMON_JS(2, 3),
            UMD(3, 4);

            public static final int AMD_VALUE = 2;
            public static final int COMMON_JS_VALUE = 3;
            public static final int PLAIN_VALUE = 1;
            public static final int UMD_VALUE = 4;
            private static Internal.EnumLiteMap<Kind> internalValueMap = new Internal.EnumLiteMap<Kind>() { // from class: org.jetbrains.kotlin.metadata.js.JsProtoBuf.Library.Kind.1
                @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLiteMap
                public Kind findValueByNumber(int i) {
                    return Kind.valueOf(i);
                }
            };
            private final int value;

            @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLite
            public final int getNumber() {
                return this.value;
            }

            public static Kind valueOf(int i) {
                if (i == 1) {
                    return PLAIN;
                }
                if (i == 2) {
                    return AMD;
                }
                if (i == 3) {
                    return COMMON_JS;
                }
                if (i != 4) {
                    return null;
                }
                return UMD;
            }

            public static Internal.EnumLiteMap<Kind> internalGetValueMap() {
                return internalValueMap;
            }

            Kind(int i, int i2) {
                this.value = i2;
            }
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
        public boolean hasKind() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
        public Kind getKind() {
            return this.kind_;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
        public List<ProtoBuf.PackageFragment> getPackageFragmentList() {
            return this.packageFragment_;
        }

        public List<? extends ProtoBuf.PackageFragmentOrBuilder> getPackageFragmentOrBuilderList() {
            return this.packageFragment_;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
        public int getPackageFragmentCount() {
            return this.packageFragment_.size();
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
        public ProtoBuf.PackageFragment getPackageFragment(int i) {
            return this.packageFragment_.get(i);
        }

        public ProtoBuf.PackageFragmentOrBuilder getPackageFragmentOrBuilder(int i) {
            return this.packageFragment_.get(i);
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
        public ProtocolStringList getImportedModuleList() {
            return this.importedModule_;
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
        public int getImportedModuleCount() {
            return this.importedModule_.size();
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
        public String getImportedModule(int i) {
            return (String) this.importedModule_.get(i);
        }

        @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
        public ByteString getImportedModuleBytes(int i) {
            return this.importedModule_.getByteString(i);
        }

        private void initFields() {
            this.kind_ = Kind.PLAIN;
            this.packageFragment_ = Collections.emptyList();
            this.importedModule_ = LazyStringArrayList.EMPTY;
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
            for (int i = 0; i < getPackageFragmentCount(); i++) {
                if (!getPackageFragment(i).isInitialized()) {
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
                codedOutputStream.writeEnum(1, this.kind_.getNumber());
            }
            for (int i = 0; i < this.packageFragment_.size(); i++) {
                codedOutputStream.writeMessage(2, this.packageFragment_.get(i));
            }
            for (int i2 = 0; i2 < this.importedModule_.size(); i2++) {
                codedOutputStream.writeBytes(3, this.importedModule_.getByteString(i2));
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeEnumSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeEnumSize(1, this.kind_.getNumber()) : 0;
            for (int i2 = 0; i2 < this.packageFragment_.size(); i2++) {
                iComputeEnumSize += CodedOutputStream.computeMessageSize(2, this.packageFragment_.get(i2));
            }
            int iComputeBytesSizeNoTag = 0;
            for (int i3 = 0; i3 < this.importedModule_.size(); i3++) {
                iComputeBytesSizeNoTag += CodedOutputStream.computeBytesSizeNoTag(this.importedModule_.getByteString(i3));
            }
            int size = iComputeEnumSize + iComputeBytesSizeNoTag + getImportedModuleList().size() + this.unknownFields.size();
            this.memoizedSerializedSize = size;
            return size;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static Library parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static Library parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static Library parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static Library parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static Library parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static Library parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static Library parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static Library parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static Library parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static Library parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(Library library) {
            return newBuilder().mergeFrom(library);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Library, Builder> implements LibraryOrBuilder {
            private int bitField0_;
            private Kind kind_ = Kind.PLAIN;
            private List<ProtoBuf.PackageFragment> packageFragment_ = Collections.emptyList();
            private LazyStringList importedModule_ = LazyStringArrayList.EMPTY;

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
                this.kind_ = Kind.PLAIN;
                this.bitField0_ &= -2;
                this.packageFragment_ = Collections.emptyList();
                this.bitField0_ &= -3;
                this.importedModule_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -5;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public Library getDefaultInstanceForType() {
                return Library.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Library build() {
                Library libraryBuildPartial = buildPartial();
                if (libraryBuildPartial.isInitialized()) {
                    return libraryBuildPartial;
                }
                throw newUninitializedMessageException(libraryBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Library buildPartial() {
                Library library = new Library(this);
                int i = (this.bitField0_ & 1) != 1 ? 0 : 1;
                library.kind_ = this.kind_;
                if ((this.bitField0_ & 2) == 2) {
                    this.packageFragment_ = Collections.unmodifiableList(this.packageFragment_);
                    this.bitField0_ &= -3;
                }
                library.packageFragment_ = this.packageFragment_;
                if ((this.bitField0_ & 4) == 4) {
                    this.importedModule_ = this.importedModule_.getUnmodifiableView();
                    this.bitField0_ &= -5;
                }
                library.importedModule_ = this.importedModule_;
                library.bitField0_ = i;
                return library;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Library library) {
                if (library == Library.getDefaultInstance()) {
                    return this;
                }
                if (library.hasKind()) {
                    setKind(library.getKind());
                }
                if (!library.packageFragment_.isEmpty()) {
                    if (this.packageFragment_.isEmpty()) {
                        this.packageFragment_ = library.packageFragment_;
                        this.bitField0_ &= -3;
                    } else {
                        ensurePackageFragmentIsMutable();
                        this.packageFragment_.addAll(library.packageFragment_);
                    }
                }
                if (!library.importedModule_.isEmpty()) {
                    if (this.importedModule_.isEmpty()) {
                        this.importedModule_ = library.importedModule_;
                        this.bitField0_ &= -5;
                    } else {
                        ensureImportedModuleIsMutable();
                        this.importedModule_.addAll(library.importedModule_);
                    }
                }
                setUnknownFields(getUnknownFields().concat(library.unknownFields));
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getPackageFragmentCount(); i++) {
                    if (!getPackageFragment(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                Library library = null;
                try {
                    try {
                        Library partialFrom = Library.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        Library library2 = (Library) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            library = library2;
                            if (library != null) {
                                mergeFrom(library);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (library != null) {
                        mergeFrom(library);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
            public boolean hasKind() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
            public Kind getKind() {
                return this.kind_;
            }

            public Builder setKind(Kind kind) {
                kind.getClass();
                this.bitField0_ |= 1;
                this.kind_ = kind;
                return this;
            }

            public Builder clearKind() {
                this.bitField0_ &= -2;
                this.kind_ = Kind.PLAIN;
                return this;
            }

            private void ensurePackageFragmentIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.packageFragment_ = new ArrayList(this.packageFragment_);
                    this.bitField0_ |= 2;
                }
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
            public List<ProtoBuf.PackageFragment> getPackageFragmentList() {
                return Collections.unmodifiableList(this.packageFragment_);
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
            public int getPackageFragmentCount() {
                return this.packageFragment_.size();
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
            public ProtoBuf.PackageFragment getPackageFragment(int i) {
                return this.packageFragment_.get(i);
            }

            public Builder setPackageFragment(int i, ProtoBuf.PackageFragment packageFragment) {
                packageFragment.getClass();
                ensurePackageFragmentIsMutable();
                this.packageFragment_.set(i, packageFragment);
                return this;
            }

            public Builder setPackageFragment(int i, ProtoBuf.PackageFragment.Builder builder) {
                ensurePackageFragmentIsMutable();
                this.packageFragment_.set(i, builder.build());
                return this;
            }

            public Builder addPackageFragment(ProtoBuf.PackageFragment packageFragment) {
                packageFragment.getClass();
                ensurePackageFragmentIsMutable();
                this.packageFragment_.add(packageFragment);
                return this;
            }

            public Builder addPackageFragment(int i, ProtoBuf.PackageFragment packageFragment) {
                packageFragment.getClass();
                ensurePackageFragmentIsMutable();
                this.packageFragment_.add(i, packageFragment);
                return this;
            }

            public Builder addPackageFragment(ProtoBuf.PackageFragment.Builder builder) {
                ensurePackageFragmentIsMutable();
                this.packageFragment_.add(builder.build());
                return this;
            }

            public Builder addPackageFragment(int i, ProtoBuf.PackageFragment.Builder builder) {
                ensurePackageFragmentIsMutable();
                this.packageFragment_.add(i, builder.build());
                return this;
            }

            public Builder addAllPackageFragment(Iterable<? extends ProtoBuf.PackageFragment> iterable) {
                ensurePackageFragmentIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.packageFragment_);
                return this;
            }

            public Builder clearPackageFragment() {
                this.packageFragment_ = Collections.emptyList();
                this.bitField0_ &= -3;
                return this;
            }

            public Builder removePackageFragment(int i) {
                ensurePackageFragmentIsMutable();
                this.packageFragment_.remove(i);
                return this;
            }

            private void ensureImportedModuleIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.importedModule_ = new LazyStringArrayList(this.importedModule_);
                    this.bitField0_ |= 4;
                }
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
            public ProtocolStringList getImportedModuleList() {
                return this.importedModule_.getUnmodifiableView();
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
            public int getImportedModuleCount() {
                return this.importedModule_.size();
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
            public String getImportedModule(int i) {
                return (String) this.importedModule_.get(i);
            }

            @Override // org.jetbrains.kotlin.metadata.js.JsProtoBuf.LibraryOrBuilder
            public ByteString getImportedModuleBytes(int i) {
                return this.importedModule_.getByteString(i);
            }

            public Builder setImportedModule(int i, String str) {
                str.getClass();
                ensureImportedModuleIsMutable();
                this.importedModule_.set(i, str);
                return this;
            }

            public Builder addImportedModule(String str) {
                str.getClass();
                ensureImportedModuleIsMutable();
                this.importedModule_.add(str);
                return this;
            }

            public Builder addAllImportedModule(Iterable<String> iterable) {
                ensureImportedModuleIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.importedModule_);
                return this;
            }

            public Builder clearImportedModule() {
                this.importedModule_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -5;
                return this;
            }

            public Builder addImportedModuleBytes(ByteString byteString) {
                byteString.getClass();
                ensureImportedModuleIsMutable();
                this.importedModule_.add(byteString);
                return this;
            }
        }
    }
}
