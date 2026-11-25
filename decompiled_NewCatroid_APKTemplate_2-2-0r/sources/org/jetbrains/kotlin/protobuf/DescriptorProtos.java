package org.jetbrains.kotlin.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes4.dex */
public final class DescriptorProtos {
    private static Descriptors.FileDescriptor descriptor;
    private static final Descriptors.Descriptor internal_static_google_protobuf_DescriptorProto_ExtensionRange_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_DescriptorProto_ExtensionRange_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_DescriptorProto_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_DescriptorProto_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_EnumDescriptorProto_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_EnumDescriptorProto_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_EnumOptions_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_EnumOptions_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_EnumValueDescriptorProto_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_EnumValueDescriptorProto_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_EnumValueOptions_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_EnumValueOptions_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_FieldDescriptorProto_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_FieldDescriptorProto_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_FieldOptions_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_FieldOptions_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_FileDescriptorProto_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_FileDescriptorProto_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_FileDescriptorSet_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_FileDescriptorSet_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_FileOptions_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_FileOptions_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_MessageOptions_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_MessageOptions_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_MethodDescriptorProto_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_MethodDescriptorProto_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_MethodOptions_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_MethodOptions_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_OneofDescriptorProto_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_OneofDescriptorProto_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_ServiceDescriptorProto_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_ServiceDescriptorProto_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_ServiceOptions_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_ServiceOptions_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_SourceCodeInfo_Location_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_SourceCodeInfo_Location_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_SourceCodeInfo_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_SourceCodeInfo_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_UninterpretedOption_NamePart_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_UninterpretedOption_NamePart_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_google_protobuf_UninterpretedOption_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_UninterpretedOption_fieldAccessorTable;

    public interface DescriptorProtoOrBuilder extends MessageOrBuilder {
        EnumDescriptorProto getEnumType(int i);

        int getEnumTypeCount();

        List<EnumDescriptorProto> getEnumTypeList();

        EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int i);

        List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList();

        FieldDescriptorProto getExtension(int i);

        int getExtensionCount();

        List<FieldDescriptorProto> getExtensionList();

        FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int i);

        List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList();

        DescriptorProto.ExtensionRange getExtensionRange(int i);

        int getExtensionRangeCount();

        List<DescriptorProto.ExtensionRange> getExtensionRangeList();

        DescriptorProto.ExtensionRangeOrBuilder getExtensionRangeOrBuilder(int i);

        List<? extends DescriptorProto.ExtensionRangeOrBuilder> getExtensionRangeOrBuilderList();

        FieldDescriptorProto getField(int i);

        int getFieldCount();

        List<FieldDescriptorProto> getFieldList();

        FieldDescriptorProtoOrBuilder getFieldOrBuilder(int i);

        List<? extends FieldDescriptorProtoOrBuilder> getFieldOrBuilderList();

        String getName();

        ByteString getNameBytes();

        DescriptorProto getNestedType(int i);

        int getNestedTypeCount();

        List<DescriptorProto> getNestedTypeList();

        DescriptorProtoOrBuilder getNestedTypeOrBuilder(int i);

        List<? extends DescriptorProtoOrBuilder> getNestedTypeOrBuilderList();

        OneofDescriptorProto getOneofDecl(int i);

        int getOneofDeclCount();

        List<OneofDescriptorProto> getOneofDeclList();

        OneofDescriptorProtoOrBuilder getOneofDeclOrBuilder(int i);

        List<? extends OneofDescriptorProtoOrBuilder> getOneofDeclOrBuilderList();

        MessageOptions getOptions();

        MessageOptionsOrBuilder getOptionsOrBuilder();

        boolean hasName();

        boolean hasOptions();
    }

    public interface EnumDescriptorProtoOrBuilder extends MessageOrBuilder {
        String getName();

        ByteString getNameBytes();

        EnumOptions getOptions();

        EnumOptionsOrBuilder getOptionsOrBuilder();

        EnumValueDescriptorProto getValue(int i);

        int getValueCount();

        List<EnumValueDescriptorProto> getValueList();

        EnumValueDescriptorProtoOrBuilder getValueOrBuilder(int i);

        List<? extends EnumValueDescriptorProtoOrBuilder> getValueOrBuilderList();

        boolean hasName();

        boolean hasOptions();
    }

    public interface EnumOptionsOrBuilder extends GeneratedMessage.ExtendableMessageOrBuilder<EnumOptions> {
        boolean getAllowAlias();

        boolean getDeprecated();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i);

        List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList();

        boolean hasAllowAlias();

        boolean hasDeprecated();
    }

    public interface EnumValueDescriptorProtoOrBuilder extends MessageOrBuilder {
        String getName();

        ByteString getNameBytes();

        int getNumber();

        EnumValueOptions getOptions();

        EnumValueOptionsOrBuilder getOptionsOrBuilder();

        boolean hasName();

        boolean hasNumber();

        boolean hasOptions();
    }

    public interface EnumValueOptionsOrBuilder extends GeneratedMessage.ExtendableMessageOrBuilder<EnumValueOptions> {
        boolean getDeprecated();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i);

        List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList();

        boolean hasDeprecated();
    }

    public interface FieldDescriptorProtoOrBuilder extends MessageOrBuilder {
        String getDefaultValue();

        ByteString getDefaultValueBytes();

        String getExtendee();

        ByteString getExtendeeBytes();

        FieldDescriptorProto.Label getLabel();

        String getName();

        ByteString getNameBytes();

        int getNumber();

        int getOneofIndex();

        FieldOptions getOptions();

        FieldOptionsOrBuilder getOptionsOrBuilder();

        FieldDescriptorProto.Type getType();

        String getTypeName();

        ByteString getTypeNameBytes();

        boolean hasDefaultValue();

        boolean hasExtendee();

        boolean hasLabel();

        boolean hasName();

        boolean hasNumber();

        boolean hasOneofIndex();

        boolean hasOptions();

        boolean hasType();

        boolean hasTypeName();
    }

    public interface FieldOptionsOrBuilder extends GeneratedMessage.ExtendableMessageOrBuilder<FieldOptions> {
        FieldOptions.CType getCtype();

        boolean getDeprecated();

        String getExperimentalMapKey();

        ByteString getExperimentalMapKeyBytes();

        boolean getLazy();

        boolean getPacked();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i);

        List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList();

        boolean getWeak();

        boolean hasCtype();

        boolean hasDeprecated();

        boolean hasExperimentalMapKey();

        boolean hasLazy();

        boolean hasPacked();

        boolean hasWeak();
    }

    public interface FileDescriptorProtoOrBuilder extends MessageOrBuilder {
        String getDependency(int i);

        ByteString getDependencyBytes(int i);

        int getDependencyCount();

        ProtocolStringList getDependencyList();

        EnumDescriptorProto getEnumType(int i);

        int getEnumTypeCount();

        List<EnumDescriptorProto> getEnumTypeList();

        EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int i);

        List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList();

        FieldDescriptorProto getExtension(int i);

        int getExtensionCount();

        List<FieldDescriptorProto> getExtensionList();

        FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int i);

        List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList();

        DescriptorProto getMessageType(int i);

        int getMessageTypeCount();

        List<DescriptorProto> getMessageTypeList();

        DescriptorProtoOrBuilder getMessageTypeOrBuilder(int i);

        List<? extends DescriptorProtoOrBuilder> getMessageTypeOrBuilderList();

        String getName();

        ByteString getNameBytes();

        FileOptions getOptions();

        FileOptionsOrBuilder getOptionsOrBuilder();

        String getPackage();

        ByteString getPackageBytes();

        int getPublicDependency(int i);

        int getPublicDependencyCount();

        List<Integer> getPublicDependencyList();

        ServiceDescriptorProto getService(int i);

        int getServiceCount();

        List<ServiceDescriptorProto> getServiceList();

        ServiceDescriptorProtoOrBuilder getServiceOrBuilder(int i);

        List<? extends ServiceDescriptorProtoOrBuilder> getServiceOrBuilderList();

        SourceCodeInfo getSourceCodeInfo();

        SourceCodeInfoOrBuilder getSourceCodeInfoOrBuilder();

        int getWeakDependency(int i);

        int getWeakDependencyCount();

        List<Integer> getWeakDependencyList();

        boolean hasName();

        boolean hasOptions();

        boolean hasPackage();

        boolean hasSourceCodeInfo();
    }

    public interface FileDescriptorSetOrBuilder extends MessageOrBuilder {
        FileDescriptorProto getFile(int i);

        int getFileCount();

        List<FileDescriptorProto> getFileList();

        FileDescriptorProtoOrBuilder getFileOrBuilder(int i);

        List<? extends FileDescriptorProtoOrBuilder> getFileOrBuilderList();
    }

    public interface FileOptionsOrBuilder extends GeneratedMessage.ExtendableMessageOrBuilder<FileOptions> {
        boolean getCcGenericServices();

        boolean getDeprecated();

        String getGoPackage();

        ByteString getGoPackageBytes();

        boolean getJavaGenerateEqualsAndHash();

        boolean getJavaGenericServices();

        boolean getJavaMultipleFiles();

        String getJavaOuterClassname();

        ByteString getJavaOuterClassnameBytes();

        String getJavaPackage();

        ByteString getJavaPackageBytes();

        boolean getJavaStringCheckUtf8();

        FileOptions.OptimizeMode getOptimizeFor();

        boolean getPyGenericServices();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i);

        List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList();

        boolean hasCcGenericServices();

        boolean hasDeprecated();

        boolean hasGoPackage();

        boolean hasJavaGenerateEqualsAndHash();

        boolean hasJavaGenericServices();

        boolean hasJavaMultipleFiles();

        boolean hasJavaOuterClassname();

        boolean hasJavaPackage();

        boolean hasJavaStringCheckUtf8();

        boolean hasOptimizeFor();

        boolean hasPyGenericServices();
    }

    public interface MessageOptionsOrBuilder extends GeneratedMessage.ExtendableMessageOrBuilder<MessageOptions> {
        boolean getDeprecated();

        boolean getMessageSetWireFormat();

        boolean getNoStandardDescriptorAccessor();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i);

        List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList();

        boolean hasDeprecated();

        boolean hasMessageSetWireFormat();

        boolean hasNoStandardDescriptorAccessor();
    }

    public interface MethodDescriptorProtoOrBuilder extends MessageOrBuilder {
        String getInputType();

        ByteString getInputTypeBytes();

        String getName();

        ByteString getNameBytes();

        MethodOptions getOptions();

        MethodOptionsOrBuilder getOptionsOrBuilder();

        String getOutputType();

        ByteString getOutputTypeBytes();

        boolean hasInputType();

        boolean hasName();

        boolean hasOptions();

        boolean hasOutputType();
    }

    public interface MethodOptionsOrBuilder extends GeneratedMessage.ExtendableMessageOrBuilder<MethodOptions> {
        boolean getDeprecated();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i);

        List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList();

        boolean hasDeprecated();
    }

    public interface OneofDescriptorProtoOrBuilder extends MessageOrBuilder {
        String getName();

        ByteString getNameBytes();

        boolean hasName();
    }

    public interface ServiceDescriptorProtoOrBuilder extends MessageOrBuilder {
        MethodDescriptorProto getMethod(int i);

        int getMethodCount();

        List<MethodDescriptorProto> getMethodList();

        MethodDescriptorProtoOrBuilder getMethodOrBuilder(int i);

        List<? extends MethodDescriptorProtoOrBuilder> getMethodOrBuilderList();

        String getName();

        ByteString getNameBytes();

        ServiceOptions getOptions();

        ServiceOptionsOrBuilder getOptionsOrBuilder();

        boolean hasName();

        boolean hasOptions();
    }

    public interface ServiceOptionsOrBuilder extends GeneratedMessage.ExtendableMessageOrBuilder<ServiceOptions> {
        boolean getDeprecated();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i);

        List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList();

        boolean hasDeprecated();
    }

    public interface SourceCodeInfoOrBuilder extends MessageOrBuilder {
        SourceCodeInfo.Location getLocation(int i);

        int getLocationCount();

        List<SourceCodeInfo.Location> getLocationList();

        SourceCodeInfo.LocationOrBuilder getLocationOrBuilder(int i);

        List<? extends SourceCodeInfo.LocationOrBuilder> getLocationOrBuilderList();
    }

    public interface UninterpretedOptionOrBuilder extends MessageOrBuilder {
        String getAggregateValue();

        ByteString getAggregateValueBytes();

        double getDoubleValue();

        String getIdentifierValue();

        ByteString getIdentifierValueBytes();

        UninterpretedOption.NamePart getName(int i);

        int getNameCount();

        List<UninterpretedOption.NamePart> getNameList();

        UninterpretedOption.NamePartOrBuilder getNameOrBuilder(int i);

        List<? extends UninterpretedOption.NamePartOrBuilder> getNameOrBuilderList();

        long getNegativeIntValue();

        long getPositiveIntValue();

        ByteString getStringValue();

        boolean hasAggregateValue();

        boolean hasDoubleValue();

        boolean hasIdentifierValue();

        boolean hasNegativeIntValue();

        boolean hasPositiveIntValue();

        boolean hasStringValue();
    }

    public static void registerAllExtensions(ExtensionRegistry extensionRegistry) {
    }

    private DescriptorProtos() {
    }

    public static final class FileDescriptorSet extends GeneratedMessage implements FileDescriptorSetOrBuilder {
        public static final int FILE_FIELD_NUMBER = 1;
        public static Parser<FileDescriptorSet> PARSER = new AbstractParser<FileDescriptorSet>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorSet.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public FileDescriptorSet parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new FileDescriptorSet(codedInputStream, extensionRegistryLite);
            }
        };
        private static final FileDescriptorSet defaultInstance;
        private static final long serialVersionUID = 0;
        private List<FileDescriptorProto> file_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private final UnknownFieldSet unknownFields;

        private FileDescriptorSet(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private FileDescriptorSet(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static FileDescriptorSet getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public FileDescriptorSet getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileDescriptorSet(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            boolean z2 = false;
            while (!z) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag != 10) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                if (!z2) {
                                    this.file_ = new ArrayList();
                                    z2 = true;
                                }
                                this.file_.add((FileDescriptorProto) codedInputStream.readMessage(FileDescriptorProto.PARSER, extensionRegistryLite));
                            }
                        }
                        z = true;
                    } catch (InvalidProtocolBufferException e) {
                        throw e.setUnfinishedMessage(this);
                    } catch (IOException e2) {
                        throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if (z2) {
                        this.file_ = Collections.unmodifiableList(this.file_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if (z2) {
                this.file_ = Collections.unmodifiableList(this.file_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_FileDescriptorSet_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_FileDescriptorSet_fieldAccessorTable.ensureFieldAccessorsInitialized(FileDescriptorSet.class, Builder.class);
        }

        static {
            FileDescriptorSet fileDescriptorSet = new FileDescriptorSet(true);
            defaultInstance = fileDescriptorSet;
            fileDescriptorSet.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<FileDescriptorSet> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorSetOrBuilder
        public List<FileDescriptorProto> getFileList() {
            return this.file_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorSetOrBuilder
        public List<? extends FileDescriptorProtoOrBuilder> getFileOrBuilderList() {
            return this.file_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorSetOrBuilder
        public int getFileCount() {
            return this.file_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorSetOrBuilder
        public FileDescriptorProto getFile(int i) {
            return this.file_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorSetOrBuilder
        public FileDescriptorProtoOrBuilder getFileOrBuilder(int i) {
            return this.file_.get(i);
        }

        private void initFields() {
            this.file_ = Collections.emptyList();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
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

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            for (int i = 0; i < this.file_.size(); i++) {
                codedOutputStream.writeMessage(1, this.file_.get(i));
            }
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeMessageSize = 0;
            for (int i2 = 0; i2 < this.file_.size(); i2++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.file_.get(i2));
            }
            int serializedSize = iComputeMessageSize + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static FileDescriptorSet parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static FileDescriptorSet parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static FileDescriptorSet parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static FileDescriptorSet parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static FileDescriptorSet parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static FileDescriptorSet parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static FileDescriptorSet parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static FileDescriptorSet parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static FileDescriptorSet parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static FileDescriptorSet parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(FileDescriptorSet fileDescriptorSet) {
            return newBuilder().mergeFrom(fileDescriptorSet);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.Builder<Builder> implements FileDescriptorSetOrBuilder {
            private int bitField0_;
            private RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> fileBuilder_;
            private List<FileDescriptorProto> file_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_FileDescriptorSet_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_FileDescriptorSet_fieldAccessorTable.ensureFieldAccessorsInitialized(FileDescriptorSet.class, Builder.class);
            }

            private Builder() {
                this.file_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.file_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getFileFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.file_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_FileDescriptorSet_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public FileDescriptorSet getDefaultInstanceForType() {
                return FileDescriptorSet.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public FileDescriptorSet build() {
                FileDescriptorSet fileDescriptorSetBuildPartial = buildPartial();
                if (fileDescriptorSetBuildPartial.isInitialized()) {
                    return fileDescriptorSetBuildPartial;
                }
                throw newUninitializedMessageException((Message) fileDescriptorSetBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public FileDescriptorSet buildPartial() {
                FileDescriptorSet fileDescriptorSet = new FileDescriptorSet(this);
                int i = this.bitField0_;
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    if ((i & 1) == 1) {
                        this.file_ = Collections.unmodifiableList(this.file_);
                        this.bitField0_ &= -2;
                    }
                    fileDescriptorSet.file_ = this.file_;
                } else {
                    fileDescriptorSet.file_ = repeatedFieldBuilder.build();
                }
                onBuilt();
                return fileDescriptorSet;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof FileDescriptorSet) {
                    return mergeFrom((FileDescriptorSet) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(FileDescriptorSet fileDescriptorSet) {
                if (fileDescriptorSet == FileDescriptorSet.getDefaultInstance()) {
                    return this;
                }
                if (this.fileBuilder_ == null) {
                    if (!fileDescriptorSet.file_.isEmpty()) {
                        if (this.file_.isEmpty()) {
                            this.file_ = fileDescriptorSet.file_;
                            this.bitField0_ &= -2;
                        } else {
                            ensureFileIsMutable();
                            this.file_.addAll(fileDescriptorSet.file_);
                        }
                        onChanged();
                    }
                } else if (!fileDescriptorSet.file_.isEmpty()) {
                    if (!this.fileBuilder_.isEmpty()) {
                        this.fileBuilder_.addAllMessages(fileDescriptorSet.file_);
                    } else {
                        this.fileBuilder_.dispose();
                        this.fileBuilder_ = null;
                        this.file_ = fileDescriptorSet.file_;
                        this.bitField0_ &= -2;
                        this.fileBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getFileFieldBuilder() : null;
                    }
                }
                mergeUnknownFields(fileDescriptorSet.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getFileCount(); i++) {
                    if (!getFile(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                FileDescriptorSet fileDescriptorSet = null;
                try {
                    try {
                        FileDescriptorSet partialFrom = FileDescriptorSet.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        FileDescriptorSet fileDescriptorSet2 = (FileDescriptorSet) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            fileDescriptorSet = fileDescriptorSet2;
                            if (fileDescriptorSet != null) {
                                mergeFrom(fileDescriptorSet);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileDescriptorSet != null) {
                        mergeFrom(fileDescriptorSet);
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

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorSetOrBuilder
            public List<FileDescriptorProto> getFileList() {
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.file_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorSetOrBuilder
            public int getFileCount() {
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.file_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorSetOrBuilder
            public FileDescriptorProto getFile(int i) {
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.file_.get(i);
                }
                return (FileDescriptorProto) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setFile(int i, FileDescriptorProto fileDescriptorProto) {
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    fileDescriptorProto.getClass();
                    ensureFileIsMutable();
                    this.file_.set(i, fileDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, fileDescriptorProto);
                }
                return this;
            }

            public Builder setFile(int i, FileDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureFileIsMutable();
                    this.file_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addFile(FileDescriptorProto fileDescriptorProto) {
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    fileDescriptorProto.getClass();
                    ensureFileIsMutable();
                    this.file_.add(fileDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(fileDescriptorProto);
                }
                return this;
            }

            public Builder addFile(int i, FileDescriptorProto fileDescriptorProto) {
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    fileDescriptorProto.getClass();
                    ensureFileIsMutable();
                    this.file_.add(i, fileDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, fileDescriptorProto);
                }
                return this;
            }

            public Builder addFile(FileDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureFileIsMutable();
                    this.file_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addFile(int i, FileDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureFileIsMutable();
                    this.file_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllFile(Iterable<? extends FileDescriptorProto> iterable) {
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureFileIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.file_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearFile() {
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.file_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeFile(int i) {
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureFileIsMutable();
                    this.file_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public FileDescriptorProto.Builder getFileBuilder(int i) {
                return (FileDescriptorProto.Builder) getFileFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorSetOrBuilder
            public FileDescriptorProtoOrBuilder getFileOrBuilder(int i) {
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.file_.get(i);
                }
                return (FileDescriptorProtoOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorSetOrBuilder
            public List<? extends FileDescriptorProtoOrBuilder> getFileOrBuilderList() {
                RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fileBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.file_);
            }

            public FileDescriptorProto.Builder addFileBuilder() {
                return (FileDescriptorProto.Builder) getFileFieldBuilder().addBuilder(FileDescriptorProto.getDefaultInstance());
            }

            public FileDescriptorProto.Builder addFileBuilder(int i) {
                return (FileDescriptorProto.Builder) getFileFieldBuilder().addBuilder(i, FileDescriptorProto.getDefaultInstance());
            }

            public List<FileDescriptorProto.Builder> getFileBuilderList() {
                return getFileFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<FileDescriptorProto, FileDescriptorProto.Builder, FileDescriptorProtoOrBuilder> getFileFieldBuilder() {
                if (this.fileBuilder_ == null) {
                    this.fileBuilder_ = new RepeatedFieldBuilder<>(this.file_, (this.bitField0_ & 1) == 1, getParentForChildren(), isClean());
                    this.file_ = null;
                }
                return this.fileBuilder_;
            }
        }
    }

    public static final class FileDescriptorProto extends GeneratedMessage implements FileDescriptorProtoOrBuilder {
        public static final int DEPENDENCY_FIELD_NUMBER = 3;
        public static final int ENUM_TYPE_FIELD_NUMBER = 5;
        public static final int EXTENSION_FIELD_NUMBER = 7;
        public static final int MESSAGE_TYPE_FIELD_NUMBER = 4;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 8;
        public static final int PACKAGE_FIELD_NUMBER = 2;
        public static Parser<FileDescriptorProto> PARSER = new AbstractParser<FileDescriptorProto>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProto.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public FileDescriptorProto parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new FileDescriptorProto(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int PUBLIC_DEPENDENCY_FIELD_NUMBER = 10;
        public static final int SERVICE_FIELD_NUMBER = 6;
        public static final int SOURCE_CODE_INFO_FIELD_NUMBER = 9;
        public static final int WEAK_DEPENDENCY_FIELD_NUMBER = 11;
        private static final FileDescriptorProto defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private LazyStringList dependency_;
        private List<EnumDescriptorProto> enumType_;
        private List<FieldDescriptorProto> extension_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<DescriptorProto> messageType_;
        private Object name_;
        private FileOptions options_;
        private Object package_;
        private List<Integer> publicDependency_;
        private List<ServiceDescriptorProto> service_;
        private SourceCodeInfo sourceCodeInfo_;
        private final UnknownFieldSet unknownFields;
        private List<Integer> weakDependency_;

        private FileDescriptorProto(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private FileDescriptorProto(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static FileDescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public FileDescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileDescriptorProto(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            int i = 0;
            while (!z) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        switch (tag) {
                            case 0:
                                break;
                            case 10:
                                ByteString bytes = codedInputStream.readBytes();
                                this.bitField0_ |= 1;
                                this.name_ = bytes;
                                continue;
                            case 18:
                                ByteString bytes2 = codedInputStream.readBytes();
                                this.bitField0_ |= 2;
                                this.package_ = bytes2;
                                continue;
                            case 26:
                                ByteString bytes3 = codedInputStream.readBytes();
                                int i2 = (i == true ? 1 : 0) & 4;
                                i = i;
                                if (i2 != 4) {
                                    this.dependency_ = new LazyStringArrayList();
                                    i = (i == true ? 1 : 0) | 4;
                                }
                                this.dependency_.add(bytes3);
                                continue;
                            case 34:
                                int i3 = (i == true ? 1 : 0) & 32;
                                i = i;
                                if (i3 != 32) {
                                    this.messageType_ = new ArrayList();
                                    i = (i == true ? 1 : 0) | 32;
                                }
                                this.messageType_.add((DescriptorProto) codedInputStream.readMessage(DescriptorProto.PARSER, extensionRegistryLite));
                                continue;
                            case 42:
                                int i4 = (i == true ? 1 : 0) & 64;
                                i = i;
                                if (i4 != 64) {
                                    this.enumType_ = new ArrayList();
                                    i = (i == true ? 1 : 0) | 64;
                                }
                                this.enumType_.add((EnumDescriptorProto) codedInputStream.readMessage(EnumDescriptorProto.PARSER, extensionRegistryLite));
                                continue;
                            case 50:
                                int i5 = (i == true ? 1 : 0) & 128;
                                i = i;
                                if (i5 != 128) {
                                    this.service_ = new ArrayList();
                                    i = (i == true ? 1 : 0) | 128;
                                }
                                this.service_.add((ServiceDescriptorProto) codedInputStream.readMessage(ServiceDescriptorProto.PARSER, extensionRegistryLite));
                                continue;
                            case 58:
                                int i6 = (i == true ? 1 : 0) & 256;
                                i = i;
                                if (i6 != 256) {
                                    this.extension_ = new ArrayList();
                                    i = (i == true ? 1 : 0) | 256;
                                }
                                this.extension_.add((FieldDescriptorProto) codedInputStream.readMessage(FieldDescriptorProto.PARSER, extensionRegistryLite));
                                continue;
                            case 66:
                                FileOptions.Builder builder = (this.bitField0_ & 4) == 4 ? this.options_.toBuilder() : null;
                                FileOptions fileOptions = (FileOptions) codedInputStream.readMessage(FileOptions.PARSER, extensionRegistryLite);
                                this.options_ = fileOptions;
                                if (builder != null) {
                                    builder.mergeFrom(fileOptions);
                                    this.options_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 4;
                                continue;
                            case 74:
                                SourceCodeInfo.Builder builder2 = (this.bitField0_ & 8) == 8 ? this.sourceCodeInfo_.toBuilder() : null;
                                SourceCodeInfo sourceCodeInfo = (SourceCodeInfo) codedInputStream.readMessage(SourceCodeInfo.PARSER, extensionRegistryLite);
                                this.sourceCodeInfo_ = sourceCodeInfo;
                                if (builder2 != null) {
                                    builder2.mergeFrom(sourceCodeInfo);
                                    this.sourceCodeInfo_ = builder2.buildPartial();
                                }
                                this.bitField0_ |= 8;
                                continue;
                            case 80:
                                int i7 = (i == true ? 1 : 0) & 8;
                                i = i;
                                if (i7 != 8) {
                                    this.publicDependency_ = new ArrayList();
                                    i = (i == true ? 1 : 0) | 8;
                                }
                                this.publicDependency_.add(Integer.valueOf(codedInputStream.readInt32()));
                                continue;
                            case 82:
                                int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                int i8 = (i == true ? 1 : 0) & 8;
                                i = i;
                                if (i8 != 8 && codedInputStream.getBytesUntilLimit() > 0) {
                                    i = i;
                                    this.publicDependency_ = new ArrayList();
                                    i = (i == true ? 1 : 0) | 8;
                                }
                                i = i;
                                while (codedInputStream.getBytesUntilLimit() > 0) {
                                    this.publicDependency_.add(Integer.valueOf(codedInputStream.readInt32()));
                                }
                                codedInputStream.popLimit(iPushLimit);
                                continue;
                            case 88:
                                int i9 = (i == true ? 1 : 0) & 16;
                                i = i;
                                if (i9 != 16) {
                                    this.weakDependency_ = new ArrayList();
                                    i = (i == true ? 1 : 0) | 16;
                                }
                                this.weakDependency_.add(Integer.valueOf(codedInputStream.readInt32()));
                                continue;
                            case 90:
                                int iPushLimit2 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                int i10 = (i == true ? 1 : 0) & 16;
                                i = i;
                                if (i10 != 16 && codedInputStream.getBytesUntilLimit() > 0) {
                                    i = i;
                                    this.weakDependency_ = new ArrayList();
                                    i = (i == true ? 1 : 0) | 16;
                                }
                                i = i;
                                while (codedInputStream.getBytesUntilLimit() > 0) {
                                    this.weakDependency_.add(Integer.valueOf(codedInputStream.readInt32()));
                                }
                                codedInputStream.popLimit(iPushLimit2);
                                continue;
                            default:
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    break;
                                }
                                break;
                        }
                        z = true;
                    } catch (InvalidProtocolBufferException e) {
                        throw e.setUnfinishedMessage(this);
                    } catch (IOException e2) {
                        throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if (((i == true ? 1 : 0) & 4) == 4) {
                        this.dependency_ = this.dependency_.getUnmodifiableView();
                    }
                    if (((i == true ? 1 : 0) & 32) == 32) {
                        this.messageType_ = Collections.unmodifiableList(this.messageType_);
                    }
                    if (((i == true ? 1 : 0) & 64) == 64) {
                        this.enumType_ = Collections.unmodifiableList(this.enumType_);
                    }
                    if (((i == true ? 1 : 0) & 128) == 128) {
                        this.service_ = Collections.unmodifiableList(this.service_);
                    }
                    if (((i == true ? 1 : 0) & 256) == 256) {
                        this.extension_ = Collections.unmodifiableList(this.extension_);
                    }
                    if (((i == true ? 1 : 0) & 8) == 8) {
                        this.publicDependency_ = Collections.unmodifiableList(this.publicDependency_);
                    }
                    if (((i == true ? 1 : 0) & 16) == 16) {
                        this.weakDependency_ = Collections.unmodifiableList(this.weakDependency_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if (((i == true ? 1 : 0) & 4) == 4) {
                this.dependency_ = this.dependency_.getUnmodifiableView();
            }
            if (((i == true ? 1 : 0) & 32) == 32) {
                this.messageType_ = Collections.unmodifiableList(this.messageType_);
            }
            if (((i == true ? 1 : 0) & 64) == 64) {
                this.enumType_ = Collections.unmodifiableList(this.enumType_);
            }
            if (((i == true ? 1 : 0) & 128) == 128) {
                this.service_ = Collections.unmodifiableList(this.service_);
            }
            if (((i == true ? 1 : 0) & 256) == 256) {
                this.extension_ = Collections.unmodifiableList(this.extension_);
            }
            if (((i == true ? 1 : 0) & 8) == 8) {
                this.publicDependency_ = Collections.unmodifiableList(this.publicDependency_);
            }
            if (((i == true ? 1 : 0) & 16) == 16) {
                this.weakDependency_ = Collections.unmodifiableList(this.weakDependency_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_FileDescriptorProto_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_FileDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(FileDescriptorProto.class, Builder.class);
        }

        static {
            FileDescriptorProto fileDescriptorProto = new FileDescriptorProto(true);
            defaultInstance = fileDescriptorProto;
            fileDescriptorProto.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<FileDescriptorProto> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
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

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public ByteString getNameBytes() {
            Object obj = this.name_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.name_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public boolean hasPackage() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public String getPackage() {
            Object obj = this.package_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.package_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public ByteString getPackageBytes() {
            Object obj = this.package_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.package_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public ProtocolStringList getDependencyList() {
            return this.dependency_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public int getDependencyCount() {
            return this.dependency_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public String getDependency(int i) {
            return (String) this.dependency_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public ByteString getDependencyBytes(int i) {
            return this.dependency_.getByteString(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public List<Integer> getPublicDependencyList() {
            return this.publicDependency_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public int getPublicDependencyCount() {
            return this.publicDependency_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public int getPublicDependency(int i) {
            return this.publicDependency_.get(i).intValue();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public List<Integer> getWeakDependencyList() {
            return this.weakDependency_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public int getWeakDependencyCount() {
            return this.weakDependency_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public int getWeakDependency(int i) {
            return this.weakDependency_.get(i).intValue();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public List<DescriptorProto> getMessageTypeList() {
            return this.messageType_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public List<? extends DescriptorProtoOrBuilder> getMessageTypeOrBuilderList() {
            return this.messageType_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public int getMessageTypeCount() {
            return this.messageType_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public DescriptorProto getMessageType(int i) {
            return this.messageType_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public DescriptorProtoOrBuilder getMessageTypeOrBuilder(int i) {
            return this.messageType_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public List<EnumDescriptorProto> getEnumTypeList() {
            return this.enumType_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList() {
            return this.enumType_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public int getEnumTypeCount() {
            return this.enumType_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public EnumDescriptorProto getEnumType(int i) {
            return this.enumType_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int i) {
            return this.enumType_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public List<ServiceDescriptorProto> getServiceList() {
            return this.service_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public List<? extends ServiceDescriptorProtoOrBuilder> getServiceOrBuilderList() {
            return this.service_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public int getServiceCount() {
            return this.service_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public ServiceDescriptorProto getService(int i) {
            return this.service_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public ServiceDescriptorProtoOrBuilder getServiceOrBuilder(int i) {
            return this.service_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public List<FieldDescriptorProto> getExtensionList() {
            return this.extension_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList() {
            return this.extension_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public int getExtensionCount() {
            return this.extension_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public FieldDescriptorProto getExtension(int i) {
            return this.extension_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int i) {
            return this.extension_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public boolean hasOptions() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public FileOptions getOptions() {
            return this.options_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public FileOptionsOrBuilder getOptionsOrBuilder() {
            return this.options_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public boolean hasSourceCodeInfo() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public SourceCodeInfo getSourceCodeInfo() {
            return this.sourceCodeInfo_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
        public SourceCodeInfoOrBuilder getSourceCodeInfoOrBuilder() {
            return this.sourceCodeInfo_;
        }

        private void initFields() {
            this.name_ = "";
            this.package_ = "";
            this.dependency_ = LazyStringArrayList.EMPTY;
            this.publicDependency_ = Collections.emptyList();
            this.weakDependency_ = Collections.emptyList();
            this.messageType_ = Collections.emptyList();
            this.enumType_ = Collections.emptyList();
            this.service_ = Collections.emptyList();
            this.extension_ = Collections.emptyList();
            this.options_ = FileOptions.getDefaultInstance();
            this.sourceCodeInfo_ = SourceCodeInfo.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            for (int i = 0; i < getMessageTypeCount(); i++) {
                if (!getMessageType(i).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i2 = 0; i2 < getEnumTypeCount(); i2++) {
                if (!getEnumType(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i3 = 0; i3 < getServiceCount(); i3++) {
                if (!getService(i3).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i4 = 0; i4 < getExtensionCount(); i4++) {
                if (!getExtension(i4).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (hasOptions() && !getOptions().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBytes(1, getNameBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeBytes(2, getPackageBytes());
            }
            for (int i = 0; i < this.dependency_.size(); i++) {
                codedOutputStream.writeBytes(3, this.dependency_.getByteString(i));
            }
            for (int i2 = 0; i2 < this.messageType_.size(); i2++) {
                codedOutputStream.writeMessage(4, this.messageType_.get(i2));
            }
            for (int i3 = 0; i3 < this.enumType_.size(); i3++) {
                codedOutputStream.writeMessage(5, this.enumType_.get(i3));
            }
            for (int i4 = 0; i4 < this.service_.size(); i4++) {
                codedOutputStream.writeMessage(6, this.service_.get(i4));
            }
            for (int i5 = 0; i5 < this.extension_.size(); i5++) {
                codedOutputStream.writeMessage(7, this.extension_.get(i5));
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeMessage(8, this.options_);
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeMessage(9, this.sourceCodeInfo_);
            }
            for (int i6 = 0; i6 < this.publicDependency_.size(); i6++) {
                codedOutputStream.writeInt32(10, this.publicDependency_.get(i6).intValue());
            }
            for (int i7 = 0; i7 < this.weakDependency_.size(); i7++) {
                codedOutputStream.writeInt32(11, this.weakDependency_.get(i7).intValue());
            }
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBytesSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBytesSize(1, getNameBytes()) : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeBytesSize += CodedOutputStream.computeBytesSize(2, getPackageBytes());
            }
            int iComputeBytesSizeNoTag = 0;
            for (int i2 = 0; i2 < this.dependency_.size(); i2++) {
                iComputeBytesSizeNoTag += CodedOutputStream.computeBytesSizeNoTag(this.dependency_.getByteString(i2));
            }
            int size = iComputeBytesSize + iComputeBytesSizeNoTag + getDependencyList().size();
            for (int i3 = 0; i3 < this.messageType_.size(); i3++) {
                size += CodedOutputStream.computeMessageSize(4, this.messageType_.get(i3));
            }
            for (int i4 = 0; i4 < this.enumType_.size(); i4++) {
                size += CodedOutputStream.computeMessageSize(5, this.enumType_.get(i4));
            }
            for (int i5 = 0; i5 < this.service_.size(); i5++) {
                size += CodedOutputStream.computeMessageSize(6, this.service_.get(i5));
            }
            for (int i6 = 0; i6 < this.extension_.size(); i6++) {
                size += CodedOutputStream.computeMessageSize(7, this.extension_.get(i6));
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeMessageSize(8, this.options_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeMessageSize(9, this.sourceCodeInfo_);
            }
            int iComputeInt32SizeNoTag = 0;
            for (int i7 = 0; i7 < this.publicDependency_.size(); i7++) {
                iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.publicDependency_.get(i7).intValue());
            }
            int size2 = size + iComputeInt32SizeNoTag + getPublicDependencyList().size();
            int iComputeInt32SizeNoTag2 = 0;
            for (int i8 = 0; i8 < this.weakDependency_.size(); i8++) {
                iComputeInt32SizeNoTag2 += CodedOutputStream.computeInt32SizeNoTag(this.weakDependency_.get(i8).intValue());
            }
            int size3 = size2 + iComputeInt32SizeNoTag2 + getWeakDependencyList().size() + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size3;
            return size3;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static FileDescriptorProto parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static FileDescriptorProto parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static FileDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static FileDescriptorProto parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static FileDescriptorProto parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static FileDescriptorProto parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static FileDescriptorProto parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static FileDescriptorProto parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static FileDescriptorProto parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static FileDescriptorProto parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(FileDescriptorProto fileDescriptorProto) {
            return newBuilder().mergeFrom(fileDescriptorProto);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.Builder<Builder> implements FileDescriptorProtoOrBuilder {
            private int bitField0_;
            private LazyStringList dependency_;
            private RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> enumTypeBuilder_;
            private List<EnumDescriptorProto> enumType_;
            private RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> extensionBuilder_;
            private List<FieldDescriptorProto> extension_;
            private RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> messageTypeBuilder_;
            private List<DescriptorProto> messageType_;
            private Object name_;
            private SingleFieldBuilder<FileOptions, FileOptions.Builder, FileOptionsOrBuilder> optionsBuilder_;
            private FileOptions options_;
            private Object package_;
            private List<Integer> publicDependency_;
            private RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> serviceBuilder_;
            private List<ServiceDescriptorProto> service_;
            private SingleFieldBuilder<SourceCodeInfo, SourceCodeInfo.Builder, SourceCodeInfoOrBuilder> sourceCodeInfoBuilder_;
            private SourceCodeInfo sourceCodeInfo_;
            private List<Integer> weakDependency_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_FileDescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_FileDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(FileDescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                this.package_ = "";
                this.dependency_ = LazyStringArrayList.EMPTY;
                this.publicDependency_ = Collections.emptyList();
                this.weakDependency_ = Collections.emptyList();
                this.messageType_ = Collections.emptyList();
                this.enumType_ = Collections.emptyList();
                this.service_ = Collections.emptyList();
                this.extension_ = Collections.emptyList();
                this.options_ = FileOptions.getDefaultInstance();
                this.sourceCodeInfo_ = SourceCodeInfo.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.name_ = "";
                this.package_ = "";
                this.dependency_ = LazyStringArrayList.EMPTY;
                this.publicDependency_ = Collections.emptyList();
                this.weakDependency_ = Collections.emptyList();
                this.messageType_ = Collections.emptyList();
                this.enumType_ = Collections.emptyList();
                this.service_ = Collections.emptyList();
                this.extension_ = Collections.emptyList();
                this.options_ = FileOptions.getDefaultInstance();
                this.sourceCodeInfo_ = SourceCodeInfo.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getMessageTypeFieldBuilder();
                    getEnumTypeFieldBuilder();
                    getServiceFieldBuilder();
                    getExtensionFieldBuilder();
                    getOptionsFieldBuilder();
                    getSourceCodeInfoFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.name_ = "";
                int i = this.bitField0_;
                this.package_ = "";
                this.bitField0_ = i & (-4);
                this.dependency_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -5;
                this.publicDependency_ = Collections.emptyList();
                this.bitField0_ &= -9;
                this.weakDependency_ = Collections.emptyList();
                this.bitField0_ &= -17;
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.messageType_ = Collections.emptyList();
                    this.bitField0_ &= -33;
                } else {
                    repeatedFieldBuilder.clear();
                }
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder2 = this.enumTypeBuilder_;
                if (repeatedFieldBuilder2 == null) {
                    this.enumType_ = Collections.emptyList();
                    this.bitField0_ &= -65;
                } else {
                    repeatedFieldBuilder2.clear();
                }
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder3 = this.serviceBuilder_;
                if (repeatedFieldBuilder3 == null) {
                    this.service_ = Collections.emptyList();
                    this.bitField0_ &= -129;
                } else {
                    repeatedFieldBuilder3.clear();
                }
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder4 = this.extensionBuilder_;
                if (repeatedFieldBuilder4 == null) {
                    this.extension_ = Collections.emptyList();
                    this.bitField0_ &= -257;
                } else {
                    repeatedFieldBuilder4.clear();
                }
                SingleFieldBuilder<FileOptions, FileOptions.Builder, FileOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = FileOptions.getDefaultInstance();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -513;
                SingleFieldBuilder<SourceCodeInfo, SourceCodeInfo.Builder, SourceCodeInfoOrBuilder> singleFieldBuilder2 = this.sourceCodeInfoBuilder_;
                if (singleFieldBuilder2 == null) {
                    this.sourceCodeInfo_ = SourceCodeInfo.getDefaultInstance();
                } else {
                    singleFieldBuilder2.clear();
                }
                this.bitField0_ &= -1025;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_FileDescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public FileDescriptorProto getDefaultInstanceForType() {
                return FileDescriptorProto.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public FileDescriptorProto build() {
                FileDescriptorProto fileDescriptorProtoBuildPartial = buildPartial();
                if (fileDescriptorProtoBuildPartial.isInitialized()) {
                    return fileDescriptorProtoBuildPartial;
                }
                throw newUninitializedMessageException((Message) fileDescriptorProtoBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public FileDescriptorProto buildPartial() {
                FileDescriptorProto fileDescriptorProto = new FileDescriptorProto(this);
                int i = this.bitField0_;
                int i2 = (i & 1) != 1 ? 0 : 1;
                fileDescriptorProto.name_ = this.name_;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                fileDescriptorProto.package_ = this.package_;
                if ((this.bitField0_ & 4) == 4) {
                    this.dependency_ = this.dependency_.getUnmodifiableView();
                    this.bitField0_ &= -5;
                }
                fileDescriptorProto.dependency_ = this.dependency_;
                if ((this.bitField0_ & 8) == 8) {
                    this.publicDependency_ = Collections.unmodifiableList(this.publicDependency_);
                    this.bitField0_ &= -9;
                }
                fileDescriptorProto.publicDependency_ = this.publicDependency_;
                if ((this.bitField0_ & 16) == 16) {
                    this.weakDependency_ = Collections.unmodifiableList(this.weakDependency_);
                    this.bitField0_ &= -17;
                }
                fileDescriptorProto.weakDependency_ = this.weakDependency_;
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder != null) {
                    fileDescriptorProto.messageType_ = repeatedFieldBuilder.build();
                } else {
                    if ((this.bitField0_ & 32) == 32) {
                        this.messageType_ = Collections.unmodifiableList(this.messageType_);
                        this.bitField0_ &= -33;
                    }
                    fileDescriptorProto.messageType_ = this.messageType_;
                }
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder2 = this.enumTypeBuilder_;
                if (repeatedFieldBuilder2 != null) {
                    fileDescriptorProto.enumType_ = repeatedFieldBuilder2.build();
                } else {
                    if ((this.bitField0_ & 64) == 64) {
                        this.enumType_ = Collections.unmodifiableList(this.enumType_);
                        this.bitField0_ &= -65;
                    }
                    fileDescriptorProto.enumType_ = this.enumType_;
                }
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder3 = this.serviceBuilder_;
                if (repeatedFieldBuilder3 != null) {
                    fileDescriptorProto.service_ = repeatedFieldBuilder3.build();
                } else {
                    if ((this.bitField0_ & 128) == 128) {
                        this.service_ = Collections.unmodifiableList(this.service_);
                        this.bitField0_ &= -129;
                    }
                    fileDescriptorProto.service_ = this.service_;
                }
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder4 = this.extensionBuilder_;
                if (repeatedFieldBuilder4 != null) {
                    fileDescriptorProto.extension_ = repeatedFieldBuilder4.build();
                } else {
                    if ((this.bitField0_ & 256) == 256) {
                        this.extension_ = Collections.unmodifiableList(this.extension_);
                        this.bitField0_ &= -257;
                    }
                    fileDescriptorProto.extension_ = this.extension_;
                }
                if ((i & 512) == 512) {
                    i2 |= 4;
                }
                SingleFieldBuilder<FileOptions, FileOptions.Builder, FileOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    fileDescriptorProto.options_ = this.options_;
                } else {
                    fileDescriptorProto.options_ = (FileOptions) singleFieldBuilder.build();
                }
                if ((i & 1024) == 1024) {
                    i2 |= 8;
                }
                SingleFieldBuilder<SourceCodeInfo, SourceCodeInfo.Builder, SourceCodeInfoOrBuilder> singleFieldBuilder2 = this.sourceCodeInfoBuilder_;
                if (singleFieldBuilder2 == null) {
                    fileDescriptorProto.sourceCodeInfo_ = this.sourceCodeInfo_;
                } else {
                    fileDescriptorProto.sourceCodeInfo_ = (SourceCodeInfo) singleFieldBuilder2.build();
                }
                fileDescriptorProto.bitField0_ = i2;
                onBuilt();
                return fileDescriptorProto;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof FileDescriptorProto) {
                    return mergeFrom((FileDescriptorProto) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(FileDescriptorProto fileDescriptorProto) {
                if (fileDescriptorProto == FileDescriptorProto.getDefaultInstance()) {
                    return this;
                }
                if (fileDescriptorProto.hasName()) {
                    this.bitField0_ |= 1;
                    this.name_ = fileDescriptorProto.name_;
                    onChanged();
                }
                if (fileDescriptorProto.hasPackage()) {
                    this.bitField0_ |= 2;
                    this.package_ = fileDescriptorProto.package_;
                    onChanged();
                }
                if (!fileDescriptorProto.dependency_.isEmpty()) {
                    if (this.dependency_.isEmpty()) {
                        this.dependency_ = fileDescriptorProto.dependency_;
                        this.bitField0_ &= -5;
                    } else {
                        ensureDependencyIsMutable();
                        this.dependency_.addAll(fileDescriptorProto.dependency_);
                    }
                    onChanged();
                }
                if (!fileDescriptorProto.publicDependency_.isEmpty()) {
                    if (this.publicDependency_.isEmpty()) {
                        this.publicDependency_ = fileDescriptorProto.publicDependency_;
                        this.bitField0_ &= -9;
                    } else {
                        ensurePublicDependencyIsMutable();
                        this.publicDependency_.addAll(fileDescriptorProto.publicDependency_);
                    }
                    onChanged();
                }
                if (!fileDescriptorProto.weakDependency_.isEmpty()) {
                    if (this.weakDependency_.isEmpty()) {
                        this.weakDependency_ = fileDescriptorProto.weakDependency_;
                        this.bitField0_ &= -17;
                    } else {
                        ensureWeakDependencyIsMutable();
                        this.weakDependency_.addAll(fileDescriptorProto.weakDependency_);
                    }
                    onChanged();
                }
                if (this.messageTypeBuilder_ == null) {
                    if (!fileDescriptorProto.messageType_.isEmpty()) {
                        if (this.messageType_.isEmpty()) {
                            this.messageType_ = fileDescriptorProto.messageType_;
                            this.bitField0_ &= -33;
                        } else {
                            ensureMessageTypeIsMutable();
                            this.messageType_.addAll(fileDescriptorProto.messageType_);
                        }
                        onChanged();
                    }
                } else if (!fileDescriptorProto.messageType_.isEmpty()) {
                    if (!this.messageTypeBuilder_.isEmpty()) {
                        this.messageTypeBuilder_.addAllMessages(fileDescriptorProto.messageType_);
                    } else {
                        this.messageTypeBuilder_.dispose();
                        this.messageTypeBuilder_ = null;
                        this.messageType_ = fileDescriptorProto.messageType_;
                        this.bitField0_ &= -33;
                        this.messageTypeBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getMessageTypeFieldBuilder() : null;
                    }
                }
                if (this.enumTypeBuilder_ == null) {
                    if (!fileDescriptorProto.enumType_.isEmpty()) {
                        if (this.enumType_.isEmpty()) {
                            this.enumType_ = fileDescriptorProto.enumType_;
                            this.bitField0_ &= -65;
                        } else {
                            ensureEnumTypeIsMutable();
                            this.enumType_.addAll(fileDescriptorProto.enumType_);
                        }
                        onChanged();
                    }
                } else if (!fileDescriptorProto.enumType_.isEmpty()) {
                    if (!this.enumTypeBuilder_.isEmpty()) {
                        this.enumTypeBuilder_.addAllMessages(fileDescriptorProto.enumType_);
                    } else {
                        this.enumTypeBuilder_.dispose();
                        this.enumTypeBuilder_ = null;
                        this.enumType_ = fileDescriptorProto.enumType_;
                        this.bitField0_ &= -65;
                        this.enumTypeBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getEnumTypeFieldBuilder() : null;
                    }
                }
                if (this.serviceBuilder_ == null) {
                    if (!fileDescriptorProto.service_.isEmpty()) {
                        if (this.service_.isEmpty()) {
                            this.service_ = fileDescriptorProto.service_;
                            this.bitField0_ &= -129;
                        } else {
                            ensureServiceIsMutable();
                            this.service_.addAll(fileDescriptorProto.service_);
                        }
                        onChanged();
                    }
                } else if (!fileDescriptorProto.service_.isEmpty()) {
                    if (!this.serviceBuilder_.isEmpty()) {
                        this.serviceBuilder_.addAllMessages(fileDescriptorProto.service_);
                    } else {
                        this.serviceBuilder_.dispose();
                        this.serviceBuilder_ = null;
                        this.service_ = fileDescriptorProto.service_;
                        this.bitField0_ &= -129;
                        this.serviceBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getServiceFieldBuilder() : null;
                    }
                }
                if (this.extensionBuilder_ == null) {
                    if (!fileDescriptorProto.extension_.isEmpty()) {
                        if (this.extension_.isEmpty()) {
                            this.extension_ = fileDescriptorProto.extension_;
                            this.bitField0_ &= -257;
                        } else {
                            ensureExtensionIsMutable();
                            this.extension_.addAll(fileDescriptorProto.extension_);
                        }
                        onChanged();
                    }
                } else if (!fileDescriptorProto.extension_.isEmpty()) {
                    if (!this.extensionBuilder_.isEmpty()) {
                        this.extensionBuilder_.addAllMessages(fileDescriptorProto.extension_);
                    } else {
                        this.extensionBuilder_.dispose();
                        this.extensionBuilder_ = null;
                        this.extension_ = fileDescriptorProto.extension_;
                        this.bitField0_ &= -257;
                        this.extensionBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getExtensionFieldBuilder() : null;
                    }
                }
                if (fileDescriptorProto.hasOptions()) {
                    mergeOptions(fileDescriptorProto.getOptions());
                }
                if (fileDescriptorProto.hasSourceCodeInfo()) {
                    mergeSourceCodeInfo(fileDescriptorProto.getSourceCodeInfo());
                }
                mergeUnknownFields(fileDescriptorProto.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getMessageTypeCount(); i++) {
                    if (!getMessageType(i).isInitialized()) {
                        return false;
                    }
                }
                for (int i2 = 0; i2 < getEnumTypeCount(); i2++) {
                    if (!getEnumType(i2).isInitialized()) {
                        return false;
                    }
                }
                for (int i3 = 0; i3 < getServiceCount(); i3++) {
                    if (!getService(i3).isInitialized()) {
                        return false;
                    }
                }
                for (int i4 = 0; i4 < getExtensionCount(); i4++) {
                    if (!getExtension(i4).isInitialized()) {
                        return false;
                    }
                }
                return !hasOptions() || getOptions().isInitialized();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                FileDescriptorProto fileDescriptorProto = null;
                try {
                    try {
                        FileDescriptorProto partialFrom = FileDescriptorProto.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        FileDescriptorProto fileDescriptorProto2 = (FileDescriptorProto) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            fileDescriptorProto = fileDescriptorProto2;
                            if (fileDescriptorProto != null) {
                                mergeFrom(fileDescriptorProto);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileDescriptorProto != null) {
                        mergeFrom(fileDescriptorProto);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
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

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
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
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = FileDescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 1;
                this.name_ = byteString;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public boolean hasPackage() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public String getPackage() {
                Object obj = this.package_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.package_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public ByteString getPackageBytes() {
                Object obj = this.package_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.package_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setPackage(String str) {
                str.getClass();
                this.bitField0_ |= 2;
                this.package_ = str;
                onChanged();
                return this;
            }

            public Builder clearPackage() {
                this.bitField0_ &= -3;
                this.package_ = FileDescriptorProto.getDefaultInstance().getPackage();
                onChanged();
                return this;
            }

            public Builder setPackageBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 2;
                this.package_ = byteString;
                onChanged();
                return this;
            }

            private void ensureDependencyIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.dependency_ = new LazyStringArrayList(this.dependency_);
                    this.bitField0_ |= 4;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public ProtocolStringList getDependencyList() {
                return this.dependency_.getUnmodifiableView();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public int getDependencyCount() {
                return this.dependency_.size();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public String getDependency(int i) {
                return (String) this.dependency_.get(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public ByteString getDependencyBytes(int i) {
                return this.dependency_.getByteString(i);
            }

            public Builder setDependency(int i, String str) {
                str.getClass();
                ensureDependencyIsMutable();
                this.dependency_.set(i, str);
                onChanged();
                return this;
            }

            public Builder addDependency(String str) {
                str.getClass();
                ensureDependencyIsMutable();
                this.dependency_.add(str);
                onChanged();
                return this;
            }

            public Builder addAllDependency(Iterable<String> iterable) {
                ensureDependencyIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.dependency_);
                onChanged();
                return this;
            }

            public Builder clearDependency() {
                this.dependency_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -5;
                onChanged();
                return this;
            }

            public Builder addDependencyBytes(ByteString byteString) {
                byteString.getClass();
                ensureDependencyIsMutable();
                this.dependency_.add(byteString);
                onChanged();
                return this;
            }

            private void ensurePublicDependencyIsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.publicDependency_ = new ArrayList(this.publicDependency_);
                    this.bitField0_ |= 8;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public List<Integer> getPublicDependencyList() {
                return Collections.unmodifiableList(this.publicDependency_);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public int getPublicDependencyCount() {
                return this.publicDependency_.size();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public int getPublicDependency(int i) {
                return this.publicDependency_.get(i).intValue();
            }

            public Builder setPublicDependency(int i, int i2) {
                ensurePublicDependencyIsMutable();
                this.publicDependency_.set(i, Integer.valueOf(i2));
                onChanged();
                return this;
            }

            public Builder addPublicDependency(int i) {
                ensurePublicDependencyIsMutable();
                this.publicDependency_.add(Integer.valueOf(i));
                onChanged();
                return this;
            }

            public Builder addAllPublicDependency(Iterable<? extends Integer> iterable) {
                ensurePublicDependencyIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.publicDependency_);
                onChanged();
                return this;
            }

            public Builder clearPublicDependency() {
                this.publicDependency_ = Collections.emptyList();
                this.bitField0_ &= -9;
                onChanged();
                return this;
            }

            private void ensureWeakDependencyIsMutable() {
                if ((this.bitField0_ & 16) != 16) {
                    this.weakDependency_ = new ArrayList(this.weakDependency_);
                    this.bitField0_ |= 16;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public List<Integer> getWeakDependencyList() {
                return Collections.unmodifiableList(this.weakDependency_);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public int getWeakDependencyCount() {
                return this.weakDependency_.size();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public int getWeakDependency(int i) {
                return this.weakDependency_.get(i).intValue();
            }

            public Builder setWeakDependency(int i, int i2) {
                ensureWeakDependencyIsMutable();
                this.weakDependency_.set(i, Integer.valueOf(i2));
                onChanged();
                return this;
            }

            public Builder addWeakDependency(int i) {
                ensureWeakDependencyIsMutable();
                this.weakDependency_.add(Integer.valueOf(i));
                onChanged();
                return this;
            }

            public Builder addAllWeakDependency(Iterable<? extends Integer> iterable) {
                ensureWeakDependencyIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.weakDependency_);
                onChanged();
                return this;
            }

            public Builder clearWeakDependency() {
                this.weakDependency_ = Collections.emptyList();
                this.bitField0_ &= -17;
                onChanged();
                return this;
            }

            private void ensureMessageTypeIsMutable() {
                if ((this.bitField0_ & 32) != 32) {
                    this.messageType_ = new ArrayList(this.messageType_);
                    this.bitField0_ |= 32;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public List<DescriptorProto> getMessageTypeList() {
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.messageType_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public int getMessageTypeCount() {
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.messageType_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public DescriptorProto getMessageType(int i) {
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.messageType_.get(i);
                }
                return (DescriptorProto) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setMessageType(int i, DescriptorProto descriptorProto) {
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    descriptorProto.getClass();
                    ensureMessageTypeIsMutable();
                    this.messageType_.set(i, descriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, descriptorProto);
                }
                return this;
            }

            public Builder setMessageType(int i, DescriptorProto.Builder builder) {
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureMessageTypeIsMutable();
                    this.messageType_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addMessageType(DescriptorProto descriptorProto) {
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    descriptorProto.getClass();
                    ensureMessageTypeIsMutable();
                    this.messageType_.add(descriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(descriptorProto);
                }
                return this;
            }

            public Builder addMessageType(int i, DescriptorProto descriptorProto) {
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    descriptorProto.getClass();
                    ensureMessageTypeIsMutable();
                    this.messageType_.add(i, descriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, descriptorProto);
                }
                return this;
            }

            public Builder addMessageType(DescriptorProto.Builder builder) {
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureMessageTypeIsMutable();
                    this.messageType_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addMessageType(int i, DescriptorProto.Builder builder) {
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureMessageTypeIsMutable();
                    this.messageType_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllMessageType(Iterable<? extends DescriptorProto> iterable) {
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureMessageTypeIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.messageType_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearMessageType() {
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.messageType_ = Collections.emptyList();
                    this.bitField0_ &= -33;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeMessageType(int i) {
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureMessageTypeIsMutable();
                    this.messageType_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public DescriptorProto.Builder getMessageTypeBuilder(int i) {
                return (DescriptorProto.Builder) getMessageTypeFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public DescriptorProtoOrBuilder getMessageTypeOrBuilder(int i) {
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.messageType_.get(i);
                }
                return (DescriptorProtoOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public List<? extends DescriptorProtoOrBuilder> getMessageTypeOrBuilderList() {
                RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.messageTypeBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.messageType_);
            }

            public DescriptorProto.Builder addMessageTypeBuilder() {
                return (DescriptorProto.Builder) getMessageTypeFieldBuilder().addBuilder(DescriptorProto.getDefaultInstance());
            }

            public DescriptorProto.Builder addMessageTypeBuilder(int i) {
                return (DescriptorProto.Builder) getMessageTypeFieldBuilder().addBuilder(i, DescriptorProto.getDefaultInstance());
            }

            public List<DescriptorProto.Builder> getMessageTypeBuilderList() {
                return getMessageTypeFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<DescriptorProto, DescriptorProto.Builder, DescriptorProtoOrBuilder> getMessageTypeFieldBuilder() {
                if (this.messageTypeBuilder_ == null) {
                    this.messageTypeBuilder_ = new RepeatedFieldBuilder<>(this.messageType_, (this.bitField0_ & 32) == 32, getParentForChildren(), isClean());
                    this.messageType_ = null;
                }
                return this.messageTypeBuilder_;
            }

            private void ensureEnumTypeIsMutable() {
                if ((this.bitField0_ & 64) != 64) {
                    this.enumType_ = new ArrayList(this.enumType_);
                    this.bitField0_ |= 64;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public List<EnumDescriptorProto> getEnumTypeList() {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.enumType_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public int getEnumTypeCount() {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.enumType_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public EnumDescriptorProto getEnumType(int i) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.enumType_.get(i);
                }
                return (EnumDescriptorProto) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setEnumType(int i, EnumDescriptorProto enumDescriptorProto) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    enumDescriptorProto.getClass();
                    ensureEnumTypeIsMutable();
                    this.enumType_.set(i, enumDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, enumDescriptorProto);
                }
                return this;
            }

            public Builder setEnumType(int i, EnumDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addEnumType(EnumDescriptorProto enumDescriptorProto) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    enumDescriptorProto.getClass();
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(enumDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(enumDescriptorProto);
                }
                return this;
            }

            public Builder addEnumType(int i, EnumDescriptorProto enumDescriptorProto) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    enumDescriptorProto.getClass();
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(i, enumDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, enumDescriptorProto);
                }
                return this;
            }

            public Builder addEnumType(EnumDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addEnumType(int i, EnumDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllEnumType(Iterable<? extends EnumDescriptorProto> iterable) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureEnumTypeIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.enumType_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearEnumType() {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.enumType_ = Collections.emptyList();
                    this.bitField0_ &= -65;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeEnumType(int i) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public EnumDescriptorProto.Builder getEnumTypeBuilder(int i) {
                return (EnumDescriptorProto.Builder) getEnumTypeFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int i) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.enumType_.get(i);
                }
                return (EnumDescriptorProtoOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList() {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.enumType_);
            }

            public EnumDescriptorProto.Builder addEnumTypeBuilder() {
                return (EnumDescriptorProto.Builder) getEnumTypeFieldBuilder().addBuilder(EnumDescriptorProto.getDefaultInstance());
            }

            public EnumDescriptorProto.Builder addEnumTypeBuilder(int i) {
                return (EnumDescriptorProto.Builder) getEnumTypeFieldBuilder().addBuilder(i, EnumDescriptorProto.getDefaultInstance());
            }

            public List<EnumDescriptorProto.Builder> getEnumTypeBuilderList() {
                return getEnumTypeFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> getEnumTypeFieldBuilder() {
                if (this.enumTypeBuilder_ == null) {
                    this.enumTypeBuilder_ = new RepeatedFieldBuilder<>(this.enumType_, (this.bitField0_ & 64) == 64, getParentForChildren(), isClean());
                    this.enumType_ = null;
                }
                return this.enumTypeBuilder_;
            }

            private void ensureServiceIsMutable() {
                if ((this.bitField0_ & 128) != 128) {
                    this.service_ = new ArrayList(this.service_);
                    this.bitField0_ |= 128;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public List<ServiceDescriptorProto> getServiceList() {
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder = this.serviceBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.service_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public int getServiceCount() {
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder = this.serviceBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.service_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public ServiceDescriptorProto getService(int i) {
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder = this.serviceBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.service_.get(i);
                }
                return (ServiceDescriptorProto) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setService(int i, ServiceDescriptorProto serviceDescriptorProto) {
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder = this.serviceBuilder_;
                if (repeatedFieldBuilder == null) {
                    serviceDescriptorProto.getClass();
                    ensureServiceIsMutable();
                    this.service_.set(i, serviceDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, serviceDescriptorProto);
                }
                return this;
            }

            public Builder setService(int i, ServiceDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder = this.serviceBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureServiceIsMutable();
                    this.service_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addService(ServiceDescriptorProto serviceDescriptorProto) {
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder = this.serviceBuilder_;
                if (repeatedFieldBuilder == null) {
                    serviceDescriptorProto.getClass();
                    ensureServiceIsMutable();
                    this.service_.add(serviceDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(serviceDescriptorProto);
                }
                return this;
            }

            public Builder addService(int i, ServiceDescriptorProto serviceDescriptorProto) {
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder = this.serviceBuilder_;
                if (repeatedFieldBuilder == null) {
                    serviceDescriptorProto.getClass();
                    ensureServiceIsMutable();
                    this.service_.add(i, serviceDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, serviceDescriptorProto);
                }
                return this;
            }

            public Builder addService(ServiceDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder = this.serviceBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureServiceIsMutable();
                    this.service_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addService(int i, ServiceDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder = this.serviceBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureServiceIsMutable();
                    this.service_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllService(Iterable<? extends ServiceDescriptorProto> iterable) {
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder = this.serviceBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureServiceIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.service_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearService() {
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder = this.serviceBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.service_ = Collections.emptyList();
                    this.bitField0_ &= -129;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeService(int i) {
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder = this.serviceBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureServiceIsMutable();
                    this.service_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public ServiceDescriptorProto.Builder getServiceBuilder(int i) {
                return (ServiceDescriptorProto.Builder) getServiceFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public ServiceDescriptorProtoOrBuilder getServiceOrBuilder(int i) {
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder = this.serviceBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.service_.get(i);
                }
                return (ServiceDescriptorProtoOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public List<? extends ServiceDescriptorProtoOrBuilder> getServiceOrBuilderList() {
                RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> repeatedFieldBuilder = this.serviceBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.service_);
            }

            public ServiceDescriptorProto.Builder addServiceBuilder() {
                return (ServiceDescriptorProto.Builder) getServiceFieldBuilder().addBuilder(ServiceDescriptorProto.getDefaultInstance());
            }

            public ServiceDescriptorProto.Builder addServiceBuilder(int i) {
                return (ServiceDescriptorProto.Builder) getServiceFieldBuilder().addBuilder(i, ServiceDescriptorProto.getDefaultInstance());
            }

            public List<ServiceDescriptorProto.Builder> getServiceBuilderList() {
                return getServiceFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<ServiceDescriptorProto, ServiceDescriptorProto.Builder, ServiceDescriptorProtoOrBuilder> getServiceFieldBuilder() {
                if (this.serviceBuilder_ == null) {
                    this.serviceBuilder_ = new RepeatedFieldBuilder<>(this.service_, (this.bitField0_ & 128) == 128, getParentForChildren(), isClean());
                    this.service_ = null;
                }
                return this.serviceBuilder_;
            }

            private void ensureExtensionIsMutable() {
                if ((this.bitField0_ & 256) != 256) {
                    this.extension_ = new ArrayList(this.extension_);
                    this.bitField0_ |= 256;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public List<FieldDescriptorProto> getExtensionList() {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.extension_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public int getExtensionCount() {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.extension_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public FieldDescriptorProto getExtension(int i) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.extension_.get(i);
                }
                return (FieldDescriptorProto) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setExtension(int i, FieldDescriptorProto fieldDescriptorProto) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    fieldDescriptorProto.getClass();
                    ensureExtensionIsMutable();
                    this.extension_.set(i, fieldDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, fieldDescriptorProto);
                }
                return this;
            }

            public Builder setExtension(int i, FieldDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionIsMutable();
                    this.extension_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addExtension(FieldDescriptorProto fieldDescriptorProto) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    fieldDescriptorProto.getClass();
                    ensureExtensionIsMutable();
                    this.extension_.add(fieldDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(fieldDescriptorProto);
                }
                return this;
            }

            public Builder addExtension(int i, FieldDescriptorProto fieldDescriptorProto) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    fieldDescriptorProto.getClass();
                    ensureExtensionIsMutable();
                    this.extension_.add(i, fieldDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, fieldDescriptorProto);
                }
                return this;
            }

            public Builder addExtension(FieldDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionIsMutable();
                    this.extension_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addExtension(int i, FieldDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionIsMutable();
                    this.extension_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllExtension(Iterable<? extends FieldDescriptorProto> iterable) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.extension_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearExtension() {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.extension_ = Collections.emptyList();
                    this.bitField0_ &= -257;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeExtension(int i) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionIsMutable();
                    this.extension_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public FieldDescriptorProto.Builder getExtensionBuilder(int i) {
                return (FieldDescriptorProto.Builder) getExtensionFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int i) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.extension_.get(i);
                }
                return (FieldDescriptorProtoOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList() {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.extension_);
            }

            public FieldDescriptorProto.Builder addExtensionBuilder() {
                return (FieldDescriptorProto.Builder) getExtensionFieldBuilder().addBuilder(FieldDescriptorProto.getDefaultInstance());
            }

            public FieldDescriptorProto.Builder addExtensionBuilder(int i) {
                return (FieldDescriptorProto.Builder) getExtensionFieldBuilder().addBuilder(i, FieldDescriptorProto.getDefaultInstance());
            }

            public List<FieldDescriptorProto.Builder> getExtensionBuilderList() {
                return getExtensionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> getExtensionFieldBuilder() {
                if (this.extensionBuilder_ == null) {
                    this.extensionBuilder_ = new RepeatedFieldBuilder<>(this.extension_, (this.bitField0_ & 256) == 256, getParentForChildren(), isClean());
                    this.extension_ = null;
                }
                return this.extensionBuilder_;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public boolean hasOptions() {
                return (this.bitField0_ & 512) == 512;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public FileOptions getOptions() {
                SingleFieldBuilder<FileOptions, FileOptions.Builder, FileOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    return this.options_;
                }
                return (FileOptions) singleFieldBuilder.getMessage();
            }

            public Builder setOptions(FileOptions fileOptions) {
                SingleFieldBuilder<FileOptions, FileOptions.Builder, FileOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    fileOptions.getClass();
                    this.options_ = fileOptions;
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(fileOptions);
                }
                this.bitField0_ |= 512;
                return this;
            }

            public Builder setOptions(FileOptions.Builder builder) {
                SingleFieldBuilder<FileOptions, FileOptions.Builder, FileOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = builder.build();
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(builder.build());
                }
                this.bitField0_ |= 512;
                return this;
            }

            public Builder mergeOptions(FileOptions fileOptions) {
                SingleFieldBuilder<FileOptions, FileOptions.Builder, FileOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    if ((this.bitField0_ & 512) == 512 && this.options_ != FileOptions.getDefaultInstance()) {
                        this.options_ = FileOptions.newBuilder(this.options_).mergeFrom(fileOptions).buildPartial();
                    } else {
                        this.options_ = fileOptions;
                    }
                    onChanged();
                } else {
                    singleFieldBuilder.mergeFrom(fileOptions);
                }
                this.bitField0_ |= 512;
                return this;
            }

            public Builder clearOptions() {
                SingleFieldBuilder<FileOptions, FileOptions.Builder, FileOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = FileOptions.getDefaultInstance();
                    onChanged();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -513;
                return this;
            }

            public FileOptions.Builder getOptionsBuilder() {
                this.bitField0_ |= 512;
                onChanged();
                return (FileOptions.Builder) getOptionsFieldBuilder().getBuilder();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public FileOptionsOrBuilder getOptionsOrBuilder() {
                SingleFieldBuilder<FileOptions, FileOptions.Builder, FileOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder != null) {
                    return (FileOptionsOrBuilder) singleFieldBuilder.getMessageOrBuilder();
                }
                return this.options_;
            }

            private SingleFieldBuilder<FileOptions, FileOptions.Builder, FileOptionsOrBuilder> getOptionsFieldBuilder() {
                if (this.optionsBuilder_ == null) {
                    this.optionsBuilder_ = new SingleFieldBuilder<>(getOptions(), getParentForChildren(), isClean());
                    this.options_ = null;
                }
                return this.optionsBuilder_;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public boolean hasSourceCodeInfo() {
                return (this.bitField0_ & 1024) == 1024;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public SourceCodeInfo getSourceCodeInfo() {
                SingleFieldBuilder<SourceCodeInfo, SourceCodeInfo.Builder, SourceCodeInfoOrBuilder> singleFieldBuilder = this.sourceCodeInfoBuilder_;
                if (singleFieldBuilder == null) {
                    return this.sourceCodeInfo_;
                }
                return (SourceCodeInfo) singleFieldBuilder.getMessage();
            }

            public Builder setSourceCodeInfo(SourceCodeInfo sourceCodeInfo) {
                SingleFieldBuilder<SourceCodeInfo, SourceCodeInfo.Builder, SourceCodeInfoOrBuilder> singleFieldBuilder = this.sourceCodeInfoBuilder_;
                if (singleFieldBuilder == null) {
                    sourceCodeInfo.getClass();
                    this.sourceCodeInfo_ = sourceCodeInfo;
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(sourceCodeInfo);
                }
                this.bitField0_ |= 1024;
                return this;
            }

            public Builder setSourceCodeInfo(SourceCodeInfo.Builder builder) {
                SingleFieldBuilder<SourceCodeInfo, SourceCodeInfo.Builder, SourceCodeInfoOrBuilder> singleFieldBuilder = this.sourceCodeInfoBuilder_;
                if (singleFieldBuilder == null) {
                    this.sourceCodeInfo_ = builder.build();
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(builder.build());
                }
                this.bitField0_ |= 1024;
                return this;
            }

            public Builder mergeSourceCodeInfo(SourceCodeInfo sourceCodeInfo) {
                SingleFieldBuilder<SourceCodeInfo, SourceCodeInfo.Builder, SourceCodeInfoOrBuilder> singleFieldBuilder = this.sourceCodeInfoBuilder_;
                if (singleFieldBuilder == null) {
                    if ((this.bitField0_ & 1024) == 1024 && this.sourceCodeInfo_ != SourceCodeInfo.getDefaultInstance()) {
                        this.sourceCodeInfo_ = SourceCodeInfo.newBuilder(this.sourceCodeInfo_).mergeFrom(sourceCodeInfo).buildPartial();
                    } else {
                        this.sourceCodeInfo_ = sourceCodeInfo;
                    }
                    onChanged();
                } else {
                    singleFieldBuilder.mergeFrom(sourceCodeInfo);
                }
                this.bitField0_ |= 1024;
                return this;
            }

            public Builder clearSourceCodeInfo() {
                SingleFieldBuilder<SourceCodeInfo, SourceCodeInfo.Builder, SourceCodeInfoOrBuilder> singleFieldBuilder = this.sourceCodeInfoBuilder_;
                if (singleFieldBuilder == null) {
                    this.sourceCodeInfo_ = SourceCodeInfo.getDefaultInstance();
                    onChanged();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -1025;
                return this;
            }

            public SourceCodeInfo.Builder getSourceCodeInfoBuilder() {
                this.bitField0_ |= 1024;
                onChanged();
                return (SourceCodeInfo.Builder) getSourceCodeInfoFieldBuilder().getBuilder();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileDescriptorProtoOrBuilder
            public SourceCodeInfoOrBuilder getSourceCodeInfoOrBuilder() {
                SingleFieldBuilder<SourceCodeInfo, SourceCodeInfo.Builder, SourceCodeInfoOrBuilder> singleFieldBuilder = this.sourceCodeInfoBuilder_;
                if (singleFieldBuilder != null) {
                    return (SourceCodeInfoOrBuilder) singleFieldBuilder.getMessageOrBuilder();
                }
                return this.sourceCodeInfo_;
            }

            private SingleFieldBuilder<SourceCodeInfo, SourceCodeInfo.Builder, SourceCodeInfoOrBuilder> getSourceCodeInfoFieldBuilder() {
                if (this.sourceCodeInfoBuilder_ == null) {
                    this.sourceCodeInfoBuilder_ = new SingleFieldBuilder<>(getSourceCodeInfo(), getParentForChildren(), isClean());
                    this.sourceCodeInfo_ = null;
                }
                return this.sourceCodeInfoBuilder_;
            }
        }
    }

    public static final class DescriptorProto extends GeneratedMessage implements DescriptorProtoOrBuilder {
        public static final int ENUM_TYPE_FIELD_NUMBER = 4;
        public static final int EXTENSION_FIELD_NUMBER = 6;
        public static final int EXTENSION_RANGE_FIELD_NUMBER = 5;
        public static final int FIELD_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int NESTED_TYPE_FIELD_NUMBER = 3;
        public static final int ONEOF_DECL_FIELD_NUMBER = 8;
        public static final int OPTIONS_FIELD_NUMBER = 7;
        public static Parser<DescriptorProto> PARSER = new AbstractParser<DescriptorProto>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProto.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public DescriptorProto parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new DescriptorProto(codedInputStream, extensionRegistryLite);
            }
        };
        private static final DescriptorProto defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private List<EnumDescriptorProto> enumType_;
        private List<ExtensionRange> extensionRange_;
        private List<FieldDescriptorProto> extension_;
        private List<FieldDescriptorProto> field_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object name_;
        private List<DescriptorProto> nestedType_;
        private List<OneofDescriptorProto> oneofDecl_;
        private MessageOptions options_;
        private final UnknownFieldSet unknownFields;

        public interface ExtensionRangeOrBuilder extends MessageOrBuilder {
            int getEnd();

            int getStart();

            boolean hasEnd();

            boolean hasStart();
        }

        private DescriptorProto(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private DescriptorProto(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static DescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public DescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DescriptorProto(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            int i = 0;
            while (!z) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                ByteString bytes = codedInputStream.readBytes();
                                this.bitField0_ = 1 | this.bitField0_;
                                this.name_ = bytes;
                            } else if (tag == 18) {
                                if ((i & 2) != 2) {
                                    this.field_ = new ArrayList();
                                    i |= 2;
                                }
                                this.field_.add((FieldDescriptorProto) codedInputStream.readMessage(FieldDescriptorProto.PARSER, extensionRegistryLite));
                            } else if (tag == 26) {
                                if ((i & 8) != 8) {
                                    this.nestedType_ = new ArrayList();
                                    i |= 8;
                                }
                                this.nestedType_.add((DescriptorProto) codedInputStream.readMessage(PARSER, extensionRegistryLite));
                            } else if (tag == 34) {
                                if ((i & 16) != 16) {
                                    this.enumType_ = new ArrayList();
                                    i |= 16;
                                }
                                this.enumType_.add((EnumDescriptorProto) codedInputStream.readMessage(EnumDescriptorProto.PARSER, extensionRegistryLite));
                            } else if (tag == 42) {
                                if ((i & 32) != 32) {
                                    this.extensionRange_ = new ArrayList();
                                    i |= 32;
                                }
                                this.extensionRange_.add((ExtensionRange) codedInputStream.readMessage(ExtensionRange.PARSER, extensionRegistryLite));
                            } else if (tag == 50) {
                                if ((i & 4) != 4) {
                                    this.extension_ = new ArrayList();
                                    i |= 4;
                                }
                                this.extension_.add((FieldDescriptorProto) codedInputStream.readMessage(FieldDescriptorProto.PARSER, extensionRegistryLite));
                            } else if (tag == 58) {
                                MessageOptions.Builder builder = (this.bitField0_ & 2) == 2 ? this.options_.toBuilder() : null;
                                MessageOptions messageOptions = (MessageOptions) codedInputStream.readMessage(MessageOptions.PARSER, extensionRegistryLite);
                                this.options_ = messageOptions;
                                if (builder != null) {
                                    builder.mergeFrom(messageOptions);
                                    this.options_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 2;
                            } else if (tag != 66) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                if ((i & 64) != 64) {
                                    this.oneofDecl_ = new ArrayList();
                                    i |= 64;
                                }
                                this.oneofDecl_.add((OneofDescriptorProto) codedInputStream.readMessage(OneofDescriptorProto.PARSER, extensionRegistryLite));
                            }
                        }
                        z = true;
                    } catch (InvalidProtocolBufferException e) {
                        throw e.setUnfinishedMessage(this);
                    } catch (IOException e2) {
                        throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if ((i & 2) == 2) {
                        this.field_ = Collections.unmodifiableList(this.field_);
                    }
                    if ((i & 8) == 8) {
                        this.nestedType_ = Collections.unmodifiableList(this.nestedType_);
                    }
                    if ((i & 16) == 16) {
                        this.enumType_ = Collections.unmodifiableList(this.enumType_);
                    }
                    if ((i & 32) == 32) {
                        this.extensionRange_ = Collections.unmodifiableList(this.extensionRange_);
                    }
                    if ((i & 4) == 4) {
                        this.extension_ = Collections.unmodifiableList(this.extension_);
                    }
                    if ((i & 64) == 64) {
                        this.oneofDecl_ = Collections.unmodifiableList(this.oneofDecl_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((i & 2) == 2) {
                this.field_ = Collections.unmodifiableList(this.field_);
            }
            if ((i & 8) == 8) {
                this.nestedType_ = Collections.unmodifiableList(this.nestedType_);
            }
            if ((i & 16) == 16) {
                this.enumType_ = Collections.unmodifiableList(this.enumType_);
            }
            if ((i & 32) == 32) {
                this.extensionRange_ = Collections.unmodifiableList(this.extensionRange_);
            }
            if ((i & 4) == 4) {
                this.extension_ = Collections.unmodifiableList(this.extension_);
            }
            if ((i & 64) == 64) {
                this.oneofDecl_ = Collections.unmodifiableList(this.oneofDecl_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(DescriptorProto.class, Builder.class);
        }

        static {
            DescriptorProto descriptorProto = new DescriptorProto(true);
            defaultInstance = descriptorProto;
            descriptorProto.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<DescriptorProto> getParserForType() {
            return PARSER;
        }

        public static final class ExtensionRange extends GeneratedMessage implements ExtensionRangeOrBuilder {
            public static final int END_FIELD_NUMBER = 2;
            public static Parser<ExtensionRange> PARSER = new AbstractParser<ExtensionRange>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProto.ExtensionRange.1
                @Override // org.jetbrains.kotlin.protobuf.Parser
                public ExtensionRange parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                    return new ExtensionRange(codedInputStream, extensionRegistryLite);
                }
            };
            public static final int START_FIELD_NUMBER = 1;
            private static final ExtensionRange defaultInstance;
            private static final long serialVersionUID = 0;
            private int bitField0_;
            private int end_;
            private byte memoizedIsInitialized;
            private int memoizedSerializedSize;
            private int start_;
            private final UnknownFieldSet unknownFields;

            private ExtensionRange(GeneratedMessage.Builder<?> builder) {
                super(builder);
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = builder.getUnknownFields();
            }

            private ExtensionRange(boolean z) {
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = UnknownFieldSet.getDefaultInstance();
            }

            public static ExtensionRange getDefaultInstance() {
                return defaultInstance;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public ExtensionRange getDefaultInstanceForType() {
                return defaultInstance;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }

            private ExtensionRange(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                initFields();
                UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
                boolean z = false;
                while (!z) {
                    try {
                        try {
                            try {
                                int tag = codedInputStream.readTag();
                                if (tag != 0) {
                                    if (tag == 8) {
                                        this.bitField0_ |= 1;
                                        this.start_ = codedInputStream.readInt32();
                                    } else if (tag != 16) {
                                        if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                        }
                                    } else {
                                        this.bitField0_ |= 2;
                                        this.end_ = codedInputStream.readInt32();
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
                        this.unknownFields = builderNewBuilder.build();
                        makeExtensionsImmutable();
                        throw th;
                    }
                }
                this.unknownFields = builderNewBuilder.build();
                makeExtensionsImmutable();
            }

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_ExtensionRange_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_ExtensionRange_fieldAccessorTable.ensureFieldAccessorsInitialized(ExtensionRange.class, Builder.class);
            }

            static {
                ExtensionRange extensionRange = new ExtensionRange(true);
                defaultInstance = extensionRange;
                extensionRange.initFields();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
            public Parser<ExtensionRange> getParserForType() {
                return PARSER;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProto.ExtensionRangeOrBuilder
            public boolean hasStart() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProto.ExtensionRangeOrBuilder
            public int getStart() {
                return this.start_;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProto.ExtensionRangeOrBuilder
            public boolean hasEnd() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProto.ExtensionRangeOrBuilder
            public int getEnd() {
                return this.end_;
            }

            private void initFields() {
                this.start_ = 0;
                this.end_ = 0;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
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

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
            public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
                getSerializedSize();
                if ((this.bitField0_ & 1) == 1) {
                    codedOutputStream.writeInt32(1, this.start_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    codedOutputStream.writeInt32(2, this.end_);
                }
                getUnknownFields().writeTo(codedOutputStream);
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
            public int getSerializedSize() {
                int i = this.memoizedSerializedSize;
                if (i != -1) {
                    return i;
                }
                int iComputeInt32Size = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt32Size(1, this.start_) : 0;
                if ((this.bitField0_ & 2) == 2) {
                    iComputeInt32Size += CodedOutputStream.computeInt32Size(2, this.end_);
                }
                int serializedSize = iComputeInt32Size + getUnknownFields().getSerializedSize();
                this.memoizedSerializedSize = serializedSize;
                return serializedSize;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
            protected Object writeReplace() throws ObjectStreamException {
                return super.writeReplace();
            }

            public static ExtensionRange parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(byteString);
            }

            public static ExtensionRange parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(byteString, extensionRegistryLite);
            }

            public static ExtensionRange parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(bArr);
            }

            public static ExtensionRange parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(bArr, extensionRegistryLite);
            }

            public static ExtensionRange parseFrom(InputStream inputStream) throws IOException {
                return PARSER.parseFrom(inputStream);
            }

            public static ExtensionRange parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return PARSER.parseFrom(inputStream, extensionRegistryLite);
            }

            public static ExtensionRange parseDelimitedFrom(InputStream inputStream) throws IOException {
                return PARSER.parseDelimitedFrom(inputStream);
            }

            public static ExtensionRange parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
            }

            public static ExtensionRange parseFrom(CodedInputStream codedInputStream) throws IOException {
                return PARSER.parseFrom(codedInputStream);
            }

            public static ExtensionRange parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
            }

            public static Builder newBuilder() {
                return Builder.create();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite
            public Builder newBuilderForType() {
                return newBuilder();
            }

            public static Builder newBuilder(ExtensionRange extensionRange) {
                return newBuilder().mergeFrom(extensionRange);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite
            public Builder toBuilder() {
                return newBuilder(this);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
            public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
                return new Builder(builderParent);
            }

            public static final class Builder extends GeneratedMessage.Builder<Builder> implements ExtensionRangeOrBuilder {
                private int bitField0_;
                private int end_;
                private int start_;

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
                public final boolean isInitialized() {
                    return true;
                }

                public static final Descriptors.Descriptor getDescriptor() {
                    return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_ExtensionRange_descriptor;
                }

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
                protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                    return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_ExtensionRange_fieldAccessorTable.ensureFieldAccessorsInitialized(ExtensionRange.class, Builder.class);
                }

                private Builder() {
                    maybeForceBuilderInitialization();
                }

                private Builder(GeneratedMessage.BuilderParent builderParent) {
                    super(builderParent);
                    maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                    boolean z = GeneratedMessage.alwaysUseFieldBuilders;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static Builder create() {
                    return new Builder();
                }

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public Builder clear() {
                    super.clear();
                    this.start_ = 0;
                    int i = this.bitField0_;
                    this.end_ = 0;
                    this.bitField0_ = i & (-4);
                    return this;
                }

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public Builder clone() {
                    return create().mergeFrom(buildPartial());
                }

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
                public Descriptors.Descriptor getDescriptorForType() {
                    return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_ExtensionRange_descriptor;
                }

                @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
                public ExtensionRange getDefaultInstanceForType() {
                    return ExtensionRange.getDefaultInstance();
                }

                @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public ExtensionRange build() {
                    ExtensionRange extensionRangeBuildPartial = buildPartial();
                    if (extensionRangeBuildPartial.isInitialized()) {
                        return extensionRangeBuildPartial;
                    }
                    throw newUninitializedMessageException((Message) extensionRangeBuildPartial);
                }

                @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public ExtensionRange buildPartial() {
                    ExtensionRange extensionRange = new ExtensionRange(this);
                    int i = this.bitField0_;
                    int i2 = (i & 1) != 1 ? 0 : 1;
                    extensionRange.start_ = this.start_;
                    if ((i & 2) == 2) {
                        i2 |= 2;
                    }
                    extensionRange.end_ = this.end_;
                    extensionRange.bitField0_ = i2;
                    onBuilt();
                    return extensionRange;
                }

                @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
                public Builder mergeFrom(Message message) {
                    if (message instanceof ExtensionRange) {
                        return mergeFrom((ExtensionRange) message);
                    }
                    super.mergeFrom(message);
                    return this;
                }

                public Builder mergeFrom(ExtensionRange extensionRange) {
                    if (extensionRange == ExtensionRange.getDefaultInstance()) {
                        return this;
                    }
                    if (extensionRange.hasStart()) {
                        setStart(extensionRange.getStart());
                    }
                    if (extensionRange.hasEnd()) {
                        setEnd(extensionRange.getEnd());
                    }
                    mergeUnknownFields(extensionRange.getUnknownFields());
                    return this;
                }

                /* JADX WARN: Code duplicated, block: B:15:0x001d  */
                @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                    ExtensionRange extensionRange = null;
                    try {
                        try {
                            ExtensionRange partialFrom = ExtensionRange.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                            if (partialFrom != null) {
                                mergeFrom(partialFrom);
                            }
                            return this;
                        } catch (InvalidProtocolBufferException e) {
                            ExtensionRange extensionRange2 = (ExtensionRange) e.getUnfinishedMessage();
                            try {
                                throw e;
                            } catch (Throwable th) {
                                th = th;
                                extensionRange = extensionRange2;
                                if (extensionRange != null) {
                                    mergeFrom(extensionRange);
                                }
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (extensionRange != null) {
                            mergeFrom(extensionRange);
                        }
                        throw th;
                    }
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProto.ExtensionRangeOrBuilder
                public boolean hasStart() {
                    return (this.bitField0_ & 1) == 1;
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProto.ExtensionRangeOrBuilder
                public int getStart() {
                    return this.start_;
                }

                public Builder setStart(int i) {
                    this.bitField0_ |= 1;
                    this.start_ = i;
                    onChanged();
                    return this;
                }

                public Builder clearStart() {
                    this.bitField0_ &= -2;
                    this.start_ = 0;
                    onChanged();
                    return this;
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProto.ExtensionRangeOrBuilder
                public boolean hasEnd() {
                    return (this.bitField0_ & 2) == 2;
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProto.ExtensionRangeOrBuilder
                public int getEnd() {
                    return this.end_;
                }

                public Builder setEnd(int i) {
                    this.bitField0_ |= 2;
                    this.end_ = i;
                    onChanged();
                    return this;
                }

                public Builder clearEnd() {
                    this.bitField0_ &= -3;
                    this.end_ = 0;
                    onChanged();
                    return this;
                }
            }
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
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

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public ByteString getNameBytes() {
            Object obj = this.name_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.name_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public List<FieldDescriptorProto> getFieldList() {
            return this.field_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public List<? extends FieldDescriptorProtoOrBuilder> getFieldOrBuilderList() {
            return this.field_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public int getFieldCount() {
            return this.field_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public FieldDescriptorProto getField(int i) {
            return this.field_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public FieldDescriptorProtoOrBuilder getFieldOrBuilder(int i) {
            return this.field_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public List<FieldDescriptorProto> getExtensionList() {
            return this.extension_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList() {
            return this.extension_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public int getExtensionCount() {
            return this.extension_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public FieldDescriptorProto getExtension(int i) {
            return this.extension_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int i) {
            return this.extension_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public List<DescriptorProto> getNestedTypeList() {
            return this.nestedType_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public List<? extends DescriptorProtoOrBuilder> getNestedTypeOrBuilderList() {
            return this.nestedType_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public int getNestedTypeCount() {
            return this.nestedType_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public DescriptorProto getNestedType(int i) {
            return this.nestedType_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public DescriptorProtoOrBuilder getNestedTypeOrBuilder(int i) {
            return this.nestedType_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public List<EnumDescriptorProto> getEnumTypeList() {
            return this.enumType_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList() {
            return this.enumType_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public int getEnumTypeCount() {
            return this.enumType_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public EnumDescriptorProto getEnumType(int i) {
            return this.enumType_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int i) {
            return this.enumType_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public List<ExtensionRange> getExtensionRangeList() {
            return this.extensionRange_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public List<? extends ExtensionRangeOrBuilder> getExtensionRangeOrBuilderList() {
            return this.extensionRange_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public int getExtensionRangeCount() {
            return this.extensionRange_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public ExtensionRange getExtensionRange(int i) {
            return this.extensionRange_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public ExtensionRangeOrBuilder getExtensionRangeOrBuilder(int i) {
            return this.extensionRange_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public List<OneofDescriptorProto> getOneofDeclList() {
            return this.oneofDecl_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public List<? extends OneofDescriptorProtoOrBuilder> getOneofDeclOrBuilderList() {
            return this.oneofDecl_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public int getOneofDeclCount() {
            return this.oneofDecl_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public OneofDescriptorProto getOneofDecl(int i) {
            return this.oneofDecl_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public OneofDescriptorProtoOrBuilder getOneofDeclOrBuilder(int i) {
            return this.oneofDecl_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public boolean hasOptions() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public MessageOptions getOptions() {
            return this.options_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
        public MessageOptionsOrBuilder getOptionsOrBuilder() {
            return this.options_;
        }

        private void initFields() {
            this.name_ = "";
            this.field_ = Collections.emptyList();
            this.extension_ = Collections.emptyList();
            this.nestedType_ = Collections.emptyList();
            this.enumType_ = Collections.emptyList();
            this.extensionRange_ = Collections.emptyList();
            this.oneofDecl_ = Collections.emptyList();
            this.options_ = MessageOptions.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            for (int i = 0; i < getFieldCount(); i++) {
                if (!getField(i).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i2 = 0; i2 < getExtensionCount(); i2++) {
                if (!getExtension(i2).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i3 = 0; i3 < getNestedTypeCount(); i3++) {
                if (!getNestedType(i3).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            for (int i4 = 0; i4 < getEnumTypeCount(); i4++) {
                if (!getEnumType(i4).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (hasOptions() && !getOptions().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBytes(1, getNameBytes());
            }
            for (int i = 0; i < this.field_.size(); i++) {
                codedOutputStream.writeMessage(2, this.field_.get(i));
            }
            for (int i2 = 0; i2 < this.nestedType_.size(); i2++) {
                codedOutputStream.writeMessage(3, this.nestedType_.get(i2));
            }
            for (int i3 = 0; i3 < this.enumType_.size(); i3++) {
                codedOutputStream.writeMessage(4, this.enumType_.get(i3));
            }
            for (int i4 = 0; i4 < this.extensionRange_.size(); i4++) {
                codedOutputStream.writeMessage(5, this.extensionRange_.get(i4));
            }
            for (int i5 = 0; i5 < this.extension_.size(); i5++) {
                codedOutputStream.writeMessage(6, this.extension_.get(i5));
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeMessage(7, this.options_);
            }
            for (int i6 = 0; i6 < this.oneofDecl_.size(); i6++) {
                codedOutputStream.writeMessage(8, this.oneofDecl_.get(i6));
            }
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBytesSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBytesSize(1, getNameBytes()) : 0;
            for (int i2 = 0; i2 < this.field_.size(); i2++) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(2, this.field_.get(i2));
            }
            for (int i3 = 0; i3 < this.nestedType_.size(); i3++) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(3, this.nestedType_.get(i3));
            }
            for (int i4 = 0; i4 < this.enumType_.size(); i4++) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(4, this.enumType_.get(i4));
            }
            for (int i5 = 0; i5 < this.extensionRange_.size(); i5++) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(5, this.extensionRange_.get(i5));
            }
            for (int i6 = 0; i6 < this.extension_.size(); i6++) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(6, this.extension_.get(i6));
            }
            if ((this.bitField0_ & 2) == 2) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(7, this.options_);
            }
            for (int i7 = 0; i7 < this.oneofDecl_.size(); i7++) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(8, this.oneofDecl_.get(i7));
            }
            int serializedSize = iComputeBytesSize + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static DescriptorProto parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static DescriptorProto parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static DescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static DescriptorProto parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static DescriptorProto parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static DescriptorProto parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static DescriptorProto parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static DescriptorProto parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static DescriptorProto parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static DescriptorProto parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(DescriptorProto descriptorProto) {
            return newBuilder().mergeFrom(descriptorProto);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.Builder<Builder> implements DescriptorProtoOrBuilder {
            private int bitField0_;
            private RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> enumTypeBuilder_;
            private List<EnumDescriptorProto> enumType_;
            private RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> extensionBuilder_;
            private RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> extensionRangeBuilder_;
            private List<ExtensionRange> extensionRange_;
            private List<FieldDescriptorProto> extension_;
            private RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> fieldBuilder_;
            private List<FieldDescriptorProto> field_;
            private Object name_;
            private RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> nestedTypeBuilder_;
            private List<DescriptorProto> nestedType_;
            private RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> oneofDeclBuilder_;
            private List<OneofDescriptorProto> oneofDecl_;
            private SingleFieldBuilder<MessageOptions, MessageOptions.Builder, MessageOptionsOrBuilder> optionsBuilder_;
            private MessageOptions options_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(DescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                this.field_ = Collections.emptyList();
                this.extension_ = Collections.emptyList();
                this.nestedType_ = Collections.emptyList();
                this.enumType_ = Collections.emptyList();
                this.extensionRange_ = Collections.emptyList();
                this.oneofDecl_ = Collections.emptyList();
                this.options_ = MessageOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.name_ = "";
                this.field_ = Collections.emptyList();
                this.extension_ = Collections.emptyList();
                this.nestedType_ = Collections.emptyList();
                this.enumType_ = Collections.emptyList();
                this.extensionRange_ = Collections.emptyList();
                this.oneofDecl_ = Collections.emptyList();
                this.options_ = MessageOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getFieldFieldBuilder();
                    getExtensionFieldBuilder();
                    getNestedTypeFieldBuilder();
                    getEnumTypeFieldBuilder();
                    getExtensionRangeFieldBuilder();
                    getOneofDeclFieldBuilder();
                    getOptionsFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= -2;
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.field_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    repeatedFieldBuilder.clear();
                }
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder2 = this.extensionBuilder_;
                if (repeatedFieldBuilder2 == null) {
                    this.extension_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                } else {
                    repeatedFieldBuilder2.clear();
                }
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder3 = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder3 == null) {
                    this.nestedType_ = Collections.emptyList();
                    this.bitField0_ &= -9;
                } else {
                    repeatedFieldBuilder3.clear();
                }
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder4 = this.enumTypeBuilder_;
                if (repeatedFieldBuilder4 == null) {
                    this.enumType_ = Collections.emptyList();
                    this.bitField0_ &= -17;
                } else {
                    repeatedFieldBuilder4.clear();
                }
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder5 = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder5 == null) {
                    this.extensionRange_ = Collections.emptyList();
                    this.bitField0_ &= -33;
                } else {
                    repeatedFieldBuilder5.clear();
                }
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder6 = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder6 == null) {
                    this.oneofDecl_ = Collections.emptyList();
                    this.bitField0_ &= -65;
                } else {
                    repeatedFieldBuilder6.clear();
                }
                SingleFieldBuilder<MessageOptions, MessageOptions.Builder, MessageOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = MessageOptions.getDefaultInstance();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -129;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public DescriptorProto getDefaultInstanceForType() {
                return DescriptorProto.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public DescriptorProto build() {
                DescriptorProto descriptorProtoBuildPartial = buildPartial();
                if (descriptorProtoBuildPartial.isInitialized()) {
                    return descriptorProtoBuildPartial;
                }
                throw newUninitializedMessageException((Message) descriptorProtoBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public DescriptorProto buildPartial() {
                DescriptorProto descriptorProto = new DescriptorProto(this);
                int i = this.bitField0_;
                int i2 = (i & 1) != 1 ? 0 : 1;
                descriptorProto.name_ = this.name_;
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder != null) {
                    descriptorProto.field_ = repeatedFieldBuilder.build();
                } else {
                    if ((this.bitField0_ & 2) == 2) {
                        this.field_ = Collections.unmodifiableList(this.field_);
                        this.bitField0_ &= -3;
                    }
                    descriptorProto.field_ = this.field_;
                }
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder2 = this.extensionBuilder_;
                if (repeatedFieldBuilder2 != null) {
                    descriptorProto.extension_ = repeatedFieldBuilder2.build();
                } else {
                    if ((this.bitField0_ & 4) == 4) {
                        this.extension_ = Collections.unmodifiableList(this.extension_);
                        this.bitField0_ &= -5;
                    }
                    descriptorProto.extension_ = this.extension_;
                }
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder3 = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder3 != null) {
                    descriptorProto.nestedType_ = repeatedFieldBuilder3.build();
                } else {
                    if ((this.bitField0_ & 8) == 8) {
                        this.nestedType_ = Collections.unmodifiableList(this.nestedType_);
                        this.bitField0_ &= -9;
                    }
                    descriptorProto.nestedType_ = this.nestedType_;
                }
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder4 = this.enumTypeBuilder_;
                if (repeatedFieldBuilder4 != null) {
                    descriptorProto.enumType_ = repeatedFieldBuilder4.build();
                } else {
                    if ((this.bitField0_ & 16) == 16) {
                        this.enumType_ = Collections.unmodifiableList(this.enumType_);
                        this.bitField0_ &= -17;
                    }
                    descriptorProto.enumType_ = this.enumType_;
                }
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder5 = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder5 != null) {
                    descriptorProto.extensionRange_ = repeatedFieldBuilder5.build();
                } else {
                    if ((this.bitField0_ & 32) == 32) {
                        this.extensionRange_ = Collections.unmodifiableList(this.extensionRange_);
                        this.bitField0_ &= -33;
                    }
                    descriptorProto.extensionRange_ = this.extensionRange_;
                }
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder6 = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder6 != null) {
                    descriptorProto.oneofDecl_ = repeatedFieldBuilder6.build();
                } else {
                    if ((this.bitField0_ & 64) == 64) {
                        this.oneofDecl_ = Collections.unmodifiableList(this.oneofDecl_);
                        this.bitField0_ &= -65;
                    }
                    descriptorProto.oneofDecl_ = this.oneofDecl_;
                }
                if ((i & 128) == 128) {
                    i2 |= 2;
                }
                SingleFieldBuilder<MessageOptions, MessageOptions.Builder, MessageOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    descriptorProto.options_ = this.options_;
                } else {
                    descriptorProto.options_ = (MessageOptions) singleFieldBuilder.build();
                }
                descriptorProto.bitField0_ = i2;
                onBuilt();
                return descriptorProto;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof DescriptorProto) {
                    return mergeFrom((DescriptorProto) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(DescriptorProto descriptorProto) {
                if (descriptorProto == DescriptorProto.getDefaultInstance()) {
                    return this;
                }
                if (descriptorProto.hasName()) {
                    this.bitField0_ |= 1;
                    this.name_ = descriptorProto.name_;
                    onChanged();
                }
                if (this.fieldBuilder_ == null) {
                    if (!descriptorProto.field_.isEmpty()) {
                        if (this.field_.isEmpty()) {
                            this.field_ = descriptorProto.field_;
                            this.bitField0_ &= -3;
                        } else {
                            ensureFieldIsMutable();
                            this.field_.addAll(descriptorProto.field_);
                        }
                        onChanged();
                    }
                } else if (!descriptorProto.field_.isEmpty()) {
                    if (!this.fieldBuilder_.isEmpty()) {
                        this.fieldBuilder_.addAllMessages(descriptorProto.field_);
                    } else {
                        this.fieldBuilder_.dispose();
                        this.fieldBuilder_ = null;
                        this.field_ = descriptorProto.field_;
                        this.bitField0_ &= -3;
                        this.fieldBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getFieldFieldBuilder() : null;
                    }
                }
                if (this.extensionBuilder_ == null) {
                    if (!descriptorProto.extension_.isEmpty()) {
                        if (this.extension_.isEmpty()) {
                            this.extension_ = descriptorProto.extension_;
                            this.bitField0_ &= -5;
                        } else {
                            ensureExtensionIsMutable();
                            this.extension_.addAll(descriptorProto.extension_);
                        }
                        onChanged();
                    }
                } else if (!descriptorProto.extension_.isEmpty()) {
                    if (!this.extensionBuilder_.isEmpty()) {
                        this.extensionBuilder_.addAllMessages(descriptorProto.extension_);
                    } else {
                        this.extensionBuilder_.dispose();
                        this.extensionBuilder_ = null;
                        this.extension_ = descriptorProto.extension_;
                        this.bitField0_ &= -5;
                        this.extensionBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getExtensionFieldBuilder() : null;
                    }
                }
                if (this.nestedTypeBuilder_ == null) {
                    if (!descriptorProto.nestedType_.isEmpty()) {
                        if (this.nestedType_.isEmpty()) {
                            this.nestedType_ = descriptorProto.nestedType_;
                            this.bitField0_ &= -9;
                        } else {
                            ensureNestedTypeIsMutable();
                            this.nestedType_.addAll(descriptorProto.nestedType_);
                        }
                        onChanged();
                    }
                } else if (!descriptorProto.nestedType_.isEmpty()) {
                    if (!this.nestedTypeBuilder_.isEmpty()) {
                        this.nestedTypeBuilder_.addAllMessages(descriptorProto.nestedType_);
                    } else {
                        this.nestedTypeBuilder_.dispose();
                        this.nestedTypeBuilder_ = null;
                        this.nestedType_ = descriptorProto.nestedType_;
                        this.bitField0_ &= -9;
                        this.nestedTypeBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getNestedTypeFieldBuilder() : null;
                    }
                }
                if (this.enumTypeBuilder_ == null) {
                    if (!descriptorProto.enumType_.isEmpty()) {
                        if (this.enumType_.isEmpty()) {
                            this.enumType_ = descriptorProto.enumType_;
                            this.bitField0_ &= -17;
                        } else {
                            ensureEnumTypeIsMutable();
                            this.enumType_.addAll(descriptorProto.enumType_);
                        }
                        onChanged();
                    }
                } else if (!descriptorProto.enumType_.isEmpty()) {
                    if (!this.enumTypeBuilder_.isEmpty()) {
                        this.enumTypeBuilder_.addAllMessages(descriptorProto.enumType_);
                    } else {
                        this.enumTypeBuilder_.dispose();
                        this.enumTypeBuilder_ = null;
                        this.enumType_ = descriptorProto.enumType_;
                        this.bitField0_ &= -17;
                        this.enumTypeBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getEnumTypeFieldBuilder() : null;
                    }
                }
                if (this.extensionRangeBuilder_ == null) {
                    if (!descriptorProto.extensionRange_.isEmpty()) {
                        if (this.extensionRange_.isEmpty()) {
                            this.extensionRange_ = descriptorProto.extensionRange_;
                            this.bitField0_ &= -33;
                        } else {
                            ensureExtensionRangeIsMutable();
                            this.extensionRange_.addAll(descriptorProto.extensionRange_);
                        }
                        onChanged();
                    }
                } else if (!descriptorProto.extensionRange_.isEmpty()) {
                    if (!this.extensionRangeBuilder_.isEmpty()) {
                        this.extensionRangeBuilder_.addAllMessages(descriptorProto.extensionRange_);
                    } else {
                        this.extensionRangeBuilder_.dispose();
                        this.extensionRangeBuilder_ = null;
                        this.extensionRange_ = descriptorProto.extensionRange_;
                        this.bitField0_ &= -33;
                        this.extensionRangeBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getExtensionRangeFieldBuilder() : null;
                    }
                }
                if (this.oneofDeclBuilder_ == null) {
                    if (!descriptorProto.oneofDecl_.isEmpty()) {
                        if (this.oneofDecl_.isEmpty()) {
                            this.oneofDecl_ = descriptorProto.oneofDecl_;
                            this.bitField0_ &= -65;
                        } else {
                            ensureOneofDeclIsMutable();
                            this.oneofDecl_.addAll(descriptorProto.oneofDecl_);
                        }
                        onChanged();
                    }
                } else if (!descriptorProto.oneofDecl_.isEmpty()) {
                    if (!this.oneofDeclBuilder_.isEmpty()) {
                        this.oneofDeclBuilder_.addAllMessages(descriptorProto.oneofDecl_);
                    } else {
                        this.oneofDeclBuilder_.dispose();
                        this.oneofDeclBuilder_ = null;
                        this.oneofDecl_ = descriptorProto.oneofDecl_;
                        this.bitField0_ &= -65;
                        this.oneofDeclBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getOneofDeclFieldBuilder() : null;
                    }
                }
                if (descriptorProto.hasOptions()) {
                    mergeOptions(descriptorProto.getOptions());
                }
                mergeUnknownFields(descriptorProto.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getFieldCount(); i++) {
                    if (!getField(i).isInitialized()) {
                        return false;
                    }
                }
                for (int i2 = 0; i2 < getExtensionCount(); i2++) {
                    if (!getExtension(i2).isInitialized()) {
                        return false;
                    }
                }
                for (int i3 = 0; i3 < getNestedTypeCount(); i3++) {
                    if (!getNestedType(i3).isInitialized()) {
                        return false;
                    }
                }
                for (int i4 = 0; i4 < getEnumTypeCount(); i4++) {
                    if (!getEnumType(i4).isInitialized()) {
                        return false;
                    }
                }
                return !hasOptions() || getOptions().isInitialized();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                DescriptorProto descriptorProto = null;
                try {
                    try {
                        DescriptorProto partialFrom = DescriptorProto.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        DescriptorProto descriptorProto2 = (DescriptorProto) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            descriptorProto = descriptorProto2;
                            if (descriptorProto != null) {
                                mergeFrom(descriptorProto);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (descriptorProto != null) {
                        mergeFrom(descriptorProto);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
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

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
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
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = DescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 1;
                this.name_ = byteString;
                onChanged();
                return this;
            }

            private void ensureFieldIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.field_ = new ArrayList(this.field_);
                    this.bitField0_ |= 2;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public List<FieldDescriptorProto> getFieldList() {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.field_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public int getFieldCount() {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.field_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public FieldDescriptorProto getField(int i) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.field_.get(i);
                }
                return (FieldDescriptorProto) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setField(int i, FieldDescriptorProto fieldDescriptorProto) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder == null) {
                    fieldDescriptorProto.getClass();
                    ensureFieldIsMutable();
                    this.field_.set(i, fieldDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, fieldDescriptorProto);
                }
                return this;
            }

            public Builder setField(int i, FieldDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureFieldIsMutable();
                    this.field_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addField(FieldDescriptorProto fieldDescriptorProto) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder == null) {
                    fieldDescriptorProto.getClass();
                    ensureFieldIsMutable();
                    this.field_.add(fieldDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(fieldDescriptorProto);
                }
                return this;
            }

            public Builder addField(int i, FieldDescriptorProto fieldDescriptorProto) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder == null) {
                    fieldDescriptorProto.getClass();
                    ensureFieldIsMutable();
                    this.field_.add(i, fieldDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, fieldDescriptorProto);
                }
                return this;
            }

            public Builder addField(FieldDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureFieldIsMutable();
                    this.field_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addField(int i, FieldDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureFieldIsMutable();
                    this.field_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllField(Iterable<? extends FieldDescriptorProto> iterable) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureFieldIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.field_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearField() {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.field_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeField(int i) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureFieldIsMutable();
                    this.field_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public FieldDescriptorProto.Builder getFieldBuilder(int i) {
                return (FieldDescriptorProto.Builder) getFieldFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public FieldDescriptorProtoOrBuilder getFieldOrBuilder(int i) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.field_.get(i);
                }
                return (FieldDescriptorProtoOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public List<? extends FieldDescriptorProtoOrBuilder> getFieldOrBuilderList() {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.fieldBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.field_);
            }

            public FieldDescriptorProto.Builder addFieldBuilder() {
                return (FieldDescriptorProto.Builder) getFieldFieldBuilder().addBuilder(FieldDescriptorProto.getDefaultInstance());
            }

            public FieldDescriptorProto.Builder addFieldBuilder(int i) {
                return (FieldDescriptorProto.Builder) getFieldFieldBuilder().addBuilder(i, FieldDescriptorProto.getDefaultInstance());
            }

            public List<FieldDescriptorProto.Builder> getFieldBuilderList() {
                return getFieldFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> getFieldFieldBuilder() {
                if (this.fieldBuilder_ == null) {
                    this.fieldBuilder_ = new RepeatedFieldBuilder<>(this.field_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.field_ = null;
                }
                return this.fieldBuilder_;
            }

            private void ensureExtensionIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.extension_ = new ArrayList(this.extension_);
                    this.bitField0_ |= 4;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public List<FieldDescriptorProto> getExtensionList() {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.extension_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public int getExtensionCount() {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.extension_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public FieldDescriptorProto getExtension(int i) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.extension_.get(i);
                }
                return (FieldDescriptorProto) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setExtension(int i, FieldDescriptorProto fieldDescriptorProto) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    fieldDescriptorProto.getClass();
                    ensureExtensionIsMutable();
                    this.extension_.set(i, fieldDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, fieldDescriptorProto);
                }
                return this;
            }

            public Builder setExtension(int i, FieldDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionIsMutable();
                    this.extension_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addExtension(FieldDescriptorProto fieldDescriptorProto) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    fieldDescriptorProto.getClass();
                    ensureExtensionIsMutable();
                    this.extension_.add(fieldDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(fieldDescriptorProto);
                }
                return this;
            }

            public Builder addExtension(int i, FieldDescriptorProto fieldDescriptorProto) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    fieldDescriptorProto.getClass();
                    ensureExtensionIsMutable();
                    this.extension_.add(i, fieldDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, fieldDescriptorProto);
                }
                return this;
            }

            public Builder addExtension(FieldDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionIsMutable();
                    this.extension_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addExtension(int i, FieldDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionIsMutable();
                    this.extension_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllExtension(Iterable<? extends FieldDescriptorProto> iterable) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.extension_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearExtension() {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.extension_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeExtension(int i) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionIsMutable();
                    this.extension_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public FieldDescriptorProto.Builder getExtensionBuilder(int i) {
                return (FieldDescriptorProto.Builder) getExtensionFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int i) {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.extension_.get(i);
                }
                return (FieldDescriptorProtoOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList() {
                RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> repeatedFieldBuilder = this.extensionBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.extension_);
            }

            public FieldDescriptorProto.Builder addExtensionBuilder() {
                return (FieldDescriptorProto.Builder) getExtensionFieldBuilder().addBuilder(FieldDescriptorProto.getDefaultInstance());
            }

            public FieldDescriptorProto.Builder addExtensionBuilder(int i) {
                return (FieldDescriptorProto.Builder) getExtensionFieldBuilder().addBuilder(i, FieldDescriptorProto.getDefaultInstance());
            }

            public List<FieldDescriptorProto.Builder> getExtensionBuilderList() {
                return getExtensionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<FieldDescriptorProto, FieldDescriptorProto.Builder, FieldDescriptorProtoOrBuilder> getExtensionFieldBuilder() {
                if (this.extensionBuilder_ == null) {
                    this.extensionBuilder_ = new RepeatedFieldBuilder<>(this.extension_, (this.bitField0_ & 4) == 4, getParentForChildren(), isClean());
                    this.extension_ = null;
                }
                return this.extensionBuilder_;
            }

            private void ensureNestedTypeIsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.nestedType_ = new ArrayList(this.nestedType_);
                    this.bitField0_ |= 8;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public List<DescriptorProto> getNestedTypeList() {
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.nestedType_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public int getNestedTypeCount() {
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.nestedType_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public DescriptorProto getNestedType(int i) {
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.nestedType_.get(i);
                }
                return (DescriptorProto) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setNestedType(int i, DescriptorProto descriptorProto) {
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    descriptorProto.getClass();
                    ensureNestedTypeIsMutable();
                    this.nestedType_.set(i, descriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, descriptorProto);
                }
                return this;
            }

            public Builder setNestedType(int i, Builder builder) {
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureNestedTypeIsMutable();
                    this.nestedType_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addNestedType(DescriptorProto descriptorProto) {
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    descriptorProto.getClass();
                    ensureNestedTypeIsMutable();
                    this.nestedType_.add(descriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(descriptorProto);
                }
                return this;
            }

            public Builder addNestedType(int i, DescriptorProto descriptorProto) {
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    descriptorProto.getClass();
                    ensureNestedTypeIsMutable();
                    this.nestedType_.add(i, descriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, descriptorProto);
                }
                return this;
            }

            public Builder addNestedType(Builder builder) {
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureNestedTypeIsMutable();
                    this.nestedType_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addNestedType(int i, Builder builder) {
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureNestedTypeIsMutable();
                    this.nestedType_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllNestedType(Iterable<? extends DescriptorProto> iterable) {
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureNestedTypeIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.nestedType_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearNestedType() {
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.nestedType_ = Collections.emptyList();
                    this.bitField0_ &= -9;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeNestedType(int i) {
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureNestedTypeIsMutable();
                    this.nestedType_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public Builder getNestedTypeBuilder(int i) {
                return (Builder) getNestedTypeFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public DescriptorProtoOrBuilder getNestedTypeOrBuilder(int i) {
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.nestedType_.get(i);
                }
                return (DescriptorProtoOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public List<? extends DescriptorProtoOrBuilder> getNestedTypeOrBuilderList() {
                RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> repeatedFieldBuilder = this.nestedTypeBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.nestedType_);
            }

            public Builder addNestedTypeBuilder() {
                return (Builder) getNestedTypeFieldBuilder().addBuilder(DescriptorProto.getDefaultInstance());
            }

            public Builder addNestedTypeBuilder(int i) {
                return (Builder) getNestedTypeFieldBuilder().addBuilder(i, DescriptorProto.getDefaultInstance());
            }

            public List<Builder> getNestedTypeBuilderList() {
                return getNestedTypeFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> getNestedTypeFieldBuilder() {
                if (this.nestedTypeBuilder_ == null) {
                    this.nestedTypeBuilder_ = new RepeatedFieldBuilder<>(this.nestedType_, (this.bitField0_ & 8) == 8, getParentForChildren(), isClean());
                    this.nestedType_ = null;
                }
                return this.nestedTypeBuilder_;
            }

            private void ensureEnumTypeIsMutable() {
                if ((this.bitField0_ & 16) != 16) {
                    this.enumType_ = new ArrayList(this.enumType_);
                    this.bitField0_ |= 16;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public List<EnumDescriptorProto> getEnumTypeList() {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.enumType_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public int getEnumTypeCount() {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.enumType_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public EnumDescriptorProto getEnumType(int i) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.enumType_.get(i);
                }
                return (EnumDescriptorProto) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setEnumType(int i, EnumDescriptorProto enumDescriptorProto) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    enumDescriptorProto.getClass();
                    ensureEnumTypeIsMutable();
                    this.enumType_.set(i, enumDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, enumDescriptorProto);
                }
                return this;
            }

            public Builder setEnumType(int i, EnumDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addEnumType(EnumDescriptorProto enumDescriptorProto) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    enumDescriptorProto.getClass();
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(enumDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(enumDescriptorProto);
                }
                return this;
            }

            public Builder addEnumType(int i, EnumDescriptorProto enumDescriptorProto) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    enumDescriptorProto.getClass();
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(i, enumDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, enumDescriptorProto);
                }
                return this;
            }

            public Builder addEnumType(EnumDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addEnumType(int i, EnumDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllEnumType(Iterable<? extends EnumDescriptorProto> iterable) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureEnumTypeIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.enumType_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearEnumType() {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.enumType_ = Collections.emptyList();
                    this.bitField0_ &= -17;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeEnumType(int i) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public EnumDescriptorProto.Builder getEnumTypeBuilder(int i) {
                return (EnumDescriptorProto.Builder) getEnumTypeFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int i) {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.enumType_.get(i);
                }
                return (EnumDescriptorProtoOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList() {
                RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> repeatedFieldBuilder = this.enumTypeBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.enumType_);
            }

            public EnumDescriptorProto.Builder addEnumTypeBuilder() {
                return (EnumDescriptorProto.Builder) getEnumTypeFieldBuilder().addBuilder(EnumDescriptorProto.getDefaultInstance());
            }

            public EnumDescriptorProto.Builder addEnumTypeBuilder(int i) {
                return (EnumDescriptorProto.Builder) getEnumTypeFieldBuilder().addBuilder(i, EnumDescriptorProto.getDefaultInstance());
            }

            public List<EnumDescriptorProto.Builder> getEnumTypeBuilderList() {
                return getEnumTypeFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<EnumDescriptorProto, EnumDescriptorProto.Builder, EnumDescriptorProtoOrBuilder> getEnumTypeFieldBuilder() {
                if (this.enumTypeBuilder_ == null) {
                    this.enumTypeBuilder_ = new RepeatedFieldBuilder<>(this.enumType_, (this.bitField0_ & 16) == 16, getParentForChildren(), isClean());
                    this.enumType_ = null;
                }
                return this.enumTypeBuilder_;
            }

            private void ensureExtensionRangeIsMutable() {
                if ((this.bitField0_ & 32) != 32) {
                    this.extensionRange_ = new ArrayList(this.extensionRange_);
                    this.bitField0_ |= 32;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public List<ExtensionRange> getExtensionRangeList() {
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.extensionRange_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public int getExtensionRangeCount() {
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.extensionRange_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public ExtensionRange getExtensionRange(int i) {
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.extensionRange_.get(i);
                }
                return (ExtensionRange) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setExtensionRange(int i, ExtensionRange extensionRange) {
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder == null) {
                    extensionRange.getClass();
                    ensureExtensionRangeIsMutable();
                    this.extensionRange_.set(i, extensionRange);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, extensionRange);
                }
                return this;
            }

            public Builder setExtensionRange(int i, ExtensionRange.Builder builder) {
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionRangeIsMutable();
                    this.extensionRange_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addExtensionRange(ExtensionRange extensionRange) {
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder == null) {
                    extensionRange.getClass();
                    ensureExtensionRangeIsMutable();
                    this.extensionRange_.add(extensionRange);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(extensionRange);
                }
                return this;
            }

            public Builder addExtensionRange(int i, ExtensionRange extensionRange) {
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder == null) {
                    extensionRange.getClass();
                    ensureExtensionRangeIsMutable();
                    this.extensionRange_.add(i, extensionRange);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, extensionRange);
                }
                return this;
            }

            public Builder addExtensionRange(ExtensionRange.Builder builder) {
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionRangeIsMutable();
                    this.extensionRange_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addExtensionRange(int i, ExtensionRange.Builder builder) {
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionRangeIsMutable();
                    this.extensionRange_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllExtensionRange(Iterable<? extends ExtensionRange> iterable) {
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionRangeIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.extensionRange_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearExtensionRange() {
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.extensionRange_ = Collections.emptyList();
                    this.bitField0_ &= -33;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeExtensionRange(int i) {
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureExtensionRangeIsMutable();
                    this.extensionRange_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public ExtensionRange.Builder getExtensionRangeBuilder(int i) {
                return (ExtensionRange.Builder) getExtensionRangeFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public ExtensionRangeOrBuilder getExtensionRangeOrBuilder(int i) {
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.extensionRange_.get(i);
                }
                return (ExtensionRangeOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public List<? extends ExtensionRangeOrBuilder> getExtensionRangeOrBuilderList() {
                RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> repeatedFieldBuilder = this.extensionRangeBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.extensionRange_);
            }

            public ExtensionRange.Builder addExtensionRangeBuilder() {
                return (ExtensionRange.Builder) getExtensionRangeFieldBuilder().addBuilder(ExtensionRange.getDefaultInstance());
            }

            public ExtensionRange.Builder addExtensionRangeBuilder(int i) {
                return (ExtensionRange.Builder) getExtensionRangeFieldBuilder().addBuilder(i, ExtensionRange.getDefaultInstance());
            }

            public List<ExtensionRange.Builder> getExtensionRangeBuilderList() {
                return getExtensionRangeFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<ExtensionRange, ExtensionRange.Builder, ExtensionRangeOrBuilder> getExtensionRangeFieldBuilder() {
                if (this.extensionRangeBuilder_ == null) {
                    this.extensionRangeBuilder_ = new RepeatedFieldBuilder<>(this.extensionRange_, (this.bitField0_ & 32) == 32, getParentForChildren(), isClean());
                    this.extensionRange_ = null;
                }
                return this.extensionRangeBuilder_;
            }

            private void ensureOneofDeclIsMutable() {
                if ((this.bitField0_ & 64) != 64) {
                    this.oneofDecl_ = new ArrayList(this.oneofDecl_);
                    this.bitField0_ |= 64;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public List<OneofDescriptorProto> getOneofDeclList() {
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.oneofDecl_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public int getOneofDeclCount() {
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.oneofDecl_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public OneofDescriptorProto getOneofDecl(int i) {
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.oneofDecl_.get(i);
                }
                return (OneofDescriptorProto) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setOneofDecl(int i, OneofDescriptorProto oneofDescriptorProto) {
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder == null) {
                    oneofDescriptorProto.getClass();
                    ensureOneofDeclIsMutable();
                    this.oneofDecl_.set(i, oneofDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, oneofDescriptorProto);
                }
                return this;
            }

            public Builder setOneofDecl(int i, OneofDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureOneofDeclIsMutable();
                    this.oneofDecl_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addOneofDecl(OneofDescriptorProto oneofDescriptorProto) {
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder == null) {
                    oneofDescriptorProto.getClass();
                    ensureOneofDeclIsMutable();
                    this.oneofDecl_.add(oneofDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(oneofDescriptorProto);
                }
                return this;
            }

            public Builder addOneofDecl(int i, OneofDescriptorProto oneofDescriptorProto) {
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder == null) {
                    oneofDescriptorProto.getClass();
                    ensureOneofDeclIsMutable();
                    this.oneofDecl_.add(i, oneofDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, oneofDescriptorProto);
                }
                return this;
            }

            public Builder addOneofDecl(OneofDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureOneofDeclIsMutable();
                    this.oneofDecl_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addOneofDecl(int i, OneofDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureOneofDeclIsMutable();
                    this.oneofDecl_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllOneofDecl(Iterable<? extends OneofDescriptorProto> iterable) {
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureOneofDeclIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.oneofDecl_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearOneofDecl() {
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.oneofDecl_ = Collections.emptyList();
                    this.bitField0_ &= -65;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeOneofDecl(int i) {
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureOneofDeclIsMutable();
                    this.oneofDecl_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public OneofDescriptorProto.Builder getOneofDeclBuilder(int i) {
                return (OneofDescriptorProto.Builder) getOneofDeclFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public OneofDescriptorProtoOrBuilder getOneofDeclOrBuilder(int i) {
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.oneofDecl_.get(i);
                }
                return (OneofDescriptorProtoOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public List<? extends OneofDescriptorProtoOrBuilder> getOneofDeclOrBuilderList() {
                RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> repeatedFieldBuilder = this.oneofDeclBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.oneofDecl_);
            }

            public OneofDescriptorProto.Builder addOneofDeclBuilder() {
                return (OneofDescriptorProto.Builder) getOneofDeclFieldBuilder().addBuilder(OneofDescriptorProto.getDefaultInstance());
            }

            public OneofDescriptorProto.Builder addOneofDeclBuilder(int i) {
                return (OneofDescriptorProto.Builder) getOneofDeclFieldBuilder().addBuilder(i, OneofDescriptorProto.getDefaultInstance());
            }

            public List<OneofDescriptorProto.Builder> getOneofDeclBuilderList() {
                return getOneofDeclFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<OneofDescriptorProto, OneofDescriptorProto.Builder, OneofDescriptorProtoOrBuilder> getOneofDeclFieldBuilder() {
                if (this.oneofDeclBuilder_ == null) {
                    this.oneofDeclBuilder_ = new RepeatedFieldBuilder<>(this.oneofDecl_, (this.bitField0_ & 64) == 64, getParentForChildren(), isClean());
                    this.oneofDecl_ = null;
                }
                return this.oneofDeclBuilder_;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public boolean hasOptions() {
                return (this.bitField0_ & 128) == 128;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public MessageOptions getOptions() {
                SingleFieldBuilder<MessageOptions, MessageOptions.Builder, MessageOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    return this.options_;
                }
                return (MessageOptions) singleFieldBuilder.getMessage();
            }

            public Builder setOptions(MessageOptions messageOptions) {
                SingleFieldBuilder<MessageOptions, MessageOptions.Builder, MessageOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    messageOptions.getClass();
                    this.options_ = messageOptions;
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(messageOptions);
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder setOptions(MessageOptions.Builder builder) {
                SingleFieldBuilder<MessageOptions, MessageOptions.Builder, MessageOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = builder.build();
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(builder.build());
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder mergeOptions(MessageOptions messageOptions) {
                SingleFieldBuilder<MessageOptions, MessageOptions.Builder, MessageOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    if ((this.bitField0_ & 128) == 128 && this.options_ != MessageOptions.getDefaultInstance()) {
                        this.options_ = MessageOptions.newBuilder(this.options_).mergeFrom(messageOptions).buildPartial();
                    } else {
                        this.options_ = messageOptions;
                    }
                    onChanged();
                } else {
                    singleFieldBuilder.mergeFrom(messageOptions);
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder clearOptions() {
                SingleFieldBuilder<MessageOptions, MessageOptions.Builder, MessageOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = MessageOptions.getDefaultInstance();
                    onChanged();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -129;
                return this;
            }

            public MessageOptions.Builder getOptionsBuilder() {
                this.bitField0_ |= 128;
                onChanged();
                return (MessageOptions.Builder) getOptionsFieldBuilder().getBuilder();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.DescriptorProtoOrBuilder
            public MessageOptionsOrBuilder getOptionsOrBuilder() {
                SingleFieldBuilder<MessageOptions, MessageOptions.Builder, MessageOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder != null) {
                    return (MessageOptionsOrBuilder) singleFieldBuilder.getMessageOrBuilder();
                }
                return this.options_;
            }

            private SingleFieldBuilder<MessageOptions, MessageOptions.Builder, MessageOptionsOrBuilder> getOptionsFieldBuilder() {
                if (this.optionsBuilder_ == null) {
                    this.optionsBuilder_ = new SingleFieldBuilder<>(getOptions(), getParentForChildren(), isClean());
                    this.options_ = null;
                }
                return this.optionsBuilder_;
            }
        }
    }

    public static final class FieldDescriptorProto extends GeneratedMessage implements FieldDescriptorProtoOrBuilder {
        public static final int DEFAULT_VALUE_FIELD_NUMBER = 7;
        public static final int EXTENDEE_FIELD_NUMBER = 2;
        public static final int LABEL_FIELD_NUMBER = 4;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int NUMBER_FIELD_NUMBER = 3;
        public static final int ONEOF_INDEX_FIELD_NUMBER = 9;
        public static final int OPTIONS_FIELD_NUMBER = 8;
        public static Parser<FieldDescriptorProto> PARSER = new AbstractParser<FieldDescriptorProto>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProto.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public FieldDescriptorProto parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new FieldDescriptorProto(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int TYPE_FIELD_NUMBER = 5;
        public static final int TYPE_NAME_FIELD_NUMBER = 6;
        private static final FieldDescriptorProto defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private Object defaultValue_;
        private Object extendee_;
        private Label label_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object name_;
        private int number_;
        private int oneofIndex_;
        private FieldOptions options_;
        private Object typeName_;
        private Type type_;
        private final UnknownFieldSet unknownFields;

        private FieldDescriptorProto(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private FieldDescriptorProto(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static FieldDescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public FieldDescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FieldDescriptorProto(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            while (!z) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                ByteString bytes = codedInputStream.readBytes();
                                this.bitField0_ = 1 | this.bitField0_;
                                this.name_ = bytes;
                            } else if (tag == 18) {
                                ByteString bytes2 = codedInputStream.readBytes();
                                this.bitField0_ |= 32;
                                this.extendee_ = bytes2;
                            } else if (tag == 24) {
                                this.bitField0_ |= 2;
                                this.number_ = codedInputStream.readInt32();
                            } else if (tag == 32) {
                                int i = codedInputStream.readEnum();
                                Label labelValueOf = Label.valueOf(i);
                                if (labelValueOf == null) {
                                    builderNewBuilder.mergeVarintField(4, i);
                                } else {
                                    this.bitField0_ |= 4;
                                    this.label_ = labelValueOf;
                                }
                            } else if (tag == 40) {
                                int i2 = codedInputStream.readEnum();
                                Type typeValueOf = Type.valueOf(i2);
                                if (typeValueOf == null) {
                                    builderNewBuilder.mergeVarintField(5, i2);
                                } else {
                                    this.bitField0_ |= 8;
                                    this.type_ = typeValueOf;
                                }
                            } else if (tag == 50) {
                                ByteString bytes3 = codedInputStream.readBytes();
                                this.bitField0_ |= 16;
                                this.typeName_ = bytes3;
                            } else if (tag == 58) {
                                ByteString bytes4 = codedInputStream.readBytes();
                                this.bitField0_ |= 64;
                                this.defaultValue_ = bytes4;
                            } else if (tag == 66) {
                                FieldOptions.Builder builder = (this.bitField0_ & 256) == 256 ? this.options_.toBuilder() : null;
                                FieldOptions fieldOptions = (FieldOptions) codedInputStream.readMessage(FieldOptions.PARSER, extensionRegistryLite);
                                this.options_ = fieldOptions;
                                if (builder != null) {
                                    builder.mergeFrom(fieldOptions);
                                    this.options_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 256;
                            } else if (tag != 72) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.bitField0_ |= 128;
                                this.oneofIndex_ = codedInputStream.readInt32();
                            }
                        }
                        z = true;
                    } catch (InvalidProtocolBufferException e) {
                        throw e.setUnfinishedMessage(this);
                    } catch (IOException e2) {
                        throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_FieldDescriptorProto_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_FieldDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldDescriptorProto.class, Builder.class);
        }

        static {
            FieldDescriptorProto fieldDescriptorProto = new FieldDescriptorProto(true);
            defaultInstance = fieldDescriptorProto;
            fieldDescriptorProto.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<FieldDescriptorProto> getParserForType() {
            return PARSER;
        }

        public enum Type implements ProtocolMessageEnum {
            TYPE_DOUBLE(0, 1),
            TYPE_FLOAT(1, 2),
            TYPE_INT64(2, 3),
            TYPE_UINT64(3, 4),
            TYPE_INT32(4, 5),
            TYPE_FIXED64(5, 6),
            TYPE_FIXED32(6, 7),
            TYPE_BOOL(7, 8),
            TYPE_STRING(8, 9),
            TYPE_GROUP(9, 10),
            TYPE_MESSAGE(10, 11),
            TYPE_BYTES(11, 12),
            TYPE_UINT32(12, 13),
            TYPE_ENUM(13, 14),
            TYPE_SFIXED32(14, 15),
            TYPE_SFIXED64(15, 16),
            TYPE_SINT32(16, 17),
            TYPE_SINT64(17, 18);

            public static final int TYPE_BOOL_VALUE = 8;
            public static final int TYPE_BYTES_VALUE = 12;
            public static final int TYPE_DOUBLE_VALUE = 1;
            public static final int TYPE_ENUM_VALUE = 14;
            public static final int TYPE_FIXED32_VALUE = 7;
            public static final int TYPE_FIXED64_VALUE = 6;
            public static final int TYPE_FLOAT_VALUE = 2;
            public static final int TYPE_GROUP_VALUE = 10;
            public static final int TYPE_INT32_VALUE = 5;
            public static final int TYPE_INT64_VALUE = 3;
            public static final int TYPE_MESSAGE_VALUE = 11;
            public static final int TYPE_SFIXED32_VALUE = 15;
            public static final int TYPE_SFIXED64_VALUE = 16;
            public static final int TYPE_SINT32_VALUE = 17;
            public static final int TYPE_SINT64_VALUE = 18;
            public static final int TYPE_STRING_VALUE = 9;
            public static final int TYPE_UINT32_VALUE = 13;
            public static final int TYPE_UINT64_VALUE = 4;
            private final int index;
            private final int value;
            private static Internal.EnumLiteMap<Type> internalValueMap = new Internal.EnumLiteMap<Type>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProto.Type.1
                @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLiteMap
                public Type findValueByNumber(int i) {
                    return Type.valueOf(i);
                }
            };
            private static final Type[] VALUES = values();

            @Override // org.jetbrains.kotlin.protobuf.ProtocolMessageEnum, org.jetbrains.kotlin.protobuf.Internal.EnumLite
            public final int getNumber() {
                return this.value;
            }

            public static Type valueOf(int i) {
                switch (i) {
                    case 1:
                        return TYPE_DOUBLE;
                    case 2:
                        return TYPE_FLOAT;
                    case 3:
                        return TYPE_INT64;
                    case 4:
                        return TYPE_UINT64;
                    case 5:
                        return TYPE_INT32;
                    case 6:
                        return TYPE_FIXED64;
                    case 7:
                        return TYPE_FIXED32;
                    case 8:
                        return TYPE_BOOL;
                    case 9:
                        return TYPE_STRING;
                    case 10:
                        return TYPE_GROUP;
                    case 11:
                        return TYPE_MESSAGE;
                    case 12:
                        return TYPE_BYTES;
                    case 13:
                        return TYPE_UINT32;
                    case 14:
                        return TYPE_ENUM;
                    case 15:
                        return TYPE_SFIXED32;
                    case 16:
                        return TYPE_SFIXED64;
                    case 17:
                        return TYPE_SINT32;
                    case 18:
                        return TYPE_SINT64;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<Type> internalGetValueMap() {
                return internalValueMap;
            }

            @Override // org.jetbrains.kotlin.protobuf.ProtocolMessageEnum
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.index);
            }

            @Override // org.jetbrains.kotlin.protobuf.ProtocolMessageEnum
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return FieldDescriptorProto.getDescriptor().getEnumTypes().get(0);
            }

            public static Type valueOf(Descriptors.EnumValueDescriptor enumValueDescriptor) {
                if (enumValueDescriptor.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return VALUES[enumValueDescriptor.getIndex()];
            }

            Type(int i, int i2) {
                this.index = i;
                this.value = i2;
            }
        }

        public enum Label implements ProtocolMessageEnum {
            LABEL_OPTIONAL(0, 1),
            LABEL_REQUIRED(1, 2),
            LABEL_REPEATED(2, 3);

            public static final int LABEL_OPTIONAL_VALUE = 1;
            public static final int LABEL_REPEATED_VALUE = 3;
            public static final int LABEL_REQUIRED_VALUE = 2;
            private final int index;
            private final int value;
            private static Internal.EnumLiteMap<Label> internalValueMap = new Internal.EnumLiteMap<Label>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProto.Label.1
                @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLiteMap
                public Label findValueByNumber(int i) {
                    return Label.valueOf(i);
                }
            };
            private static final Label[] VALUES = values();

            @Override // org.jetbrains.kotlin.protobuf.ProtocolMessageEnum, org.jetbrains.kotlin.protobuf.Internal.EnumLite
            public final int getNumber() {
                return this.value;
            }

            public static Label valueOf(int i) {
                if (i == 1) {
                    return LABEL_OPTIONAL;
                }
                if (i == 2) {
                    return LABEL_REQUIRED;
                }
                if (i != 3) {
                    return null;
                }
                return LABEL_REPEATED;
            }

            public static Internal.EnumLiteMap<Label> internalGetValueMap() {
                return internalValueMap;
            }

            @Override // org.jetbrains.kotlin.protobuf.ProtocolMessageEnum
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.index);
            }

            @Override // org.jetbrains.kotlin.protobuf.ProtocolMessageEnum
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return FieldDescriptorProto.getDescriptor().getEnumTypes().get(1);
            }

            public static Label valueOf(Descriptors.EnumValueDescriptor enumValueDescriptor) {
                if (enumValueDescriptor.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return VALUES[enumValueDescriptor.getIndex()];
            }

            Label(int i, int i2) {
                this.index = i;
                this.value = i2;
            }
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
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

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public ByteString getNameBytes() {
            Object obj = this.name_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.name_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public boolean hasNumber() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public int getNumber() {
            return this.number_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public boolean hasLabel() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public Label getLabel() {
            return this.label_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public boolean hasType() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public Type getType() {
            return this.type_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public boolean hasTypeName() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public String getTypeName() {
            Object obj = this.typeName_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.typeName_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public ByteString getTypeNameBytes() {
            Object obj = this.typeName_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.typeName_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public boolean hasExtendee() {
            return (this.bitField0_ & 32) == 32;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public String getExtendee() {
            Object obj = this.extendee_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.extendee_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public ByteString getExtendeeBytes() {
            Object obj = this.extendee_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.extendee_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public boolean hasDefaultValue() {
            return (this.bitField0_ & 64) == 64;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public String getDefaultValue() {
            Object obj = this.defaultValue_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.defaultValue_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public ByteString getDefaultValueBytes() {
            Object obj = this.defaultValue_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.defaultValue_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public boolean hasOneofIndex() {
            return (this.bitField0_ & 128) == 128;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public int getOneofIndex() {
            return this.oneofIndex_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public boolean hasOptions() {
            return (this.bitField0_ & 256) == 256;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public FieldOptions getOptions() {
            return this.options_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
        public FieldOptionsOrBuilder getOptionsOrBuilder() {
            return this.options_;
        }

        private void initFields() {
            this.name_ = "";
            this.number_ = 0;
            this.label_ = Label.LABEL_OPTIONAL;
            this.type_ = Type.TYPE_DOUBLE;
            this.typeName_ = "";
            this.extendee_ = "";
            this.defaultValue_ = "";
            this.oneofIndex_ = 0;
            this.options_ = FieldOptions.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            if (hasOptions() && !getOptions().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBytes(1, getNameBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                codedOutputStream.writeBytes(2, getExtendeeBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeInt32(3, this.number_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeEnum(4, this.label_.getNumber());
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeEnum(5, this.type_.getNumber());
            }
            if ((this.bitField0_ & 16) == 16) {
                codedOutputStream.writeBytes(6, getTypeNameBytes());
            }
            if ((this.bitField0_ & 64) == 64) {
                codedOutputStream.writeBytes(7, getDefaultValueBytes());
            }
            if ((this.bitField0_ & 256) == 256) {
                codedOutputStream.writeMessage(8, this.options_);
            }
            if ((this.bitField0_ & 128) == 128) {
                codedOutputStream.writeInt32(9, this.oneofIndex_);
            }
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBytesSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBytesSize(1, getNameBytes()) : 0;
            if ((this.bitField0_ & 32) == 32) {
                iComputeBytesSize += CodedOutputStream.computeBytesSize(2, getExtendeeBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                iComputeBytesSize += CodedOutputStream.computeInt32Size(3, this.number_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeBytesSize += CodedOutputStream.computeEnumSize(4, this.label_.getNumber());
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeBytesSize += CodedOutputStream.computeEnumSize(5, this.type_.getNumber());
            }
            if ((this.bitField0_ & 16) == 16) {
                iComputeBytesSize += CodedOutputStream.computeBytesSize(6, getTypeNameBytes());
            }
            if ((this.bitField0_ & 64) == 64) {
                iComputeBytesSize += CodedOutputStream.computeBytesSize(7, getDefaultValueBytes());
            }
            if ((this.bitField0_ & 256) == 256) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(8, this.options_);
            }
            if ((this.bitField0_ & 128) == 128) {
                iComputeBytesSize += CodedOutputStream.computeInt32Size(9, this.oneofIndex_);
            }
            int serializedSize = iComputeBytesSize + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static FieldDescriptorProto parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static FieldDescriptorProto parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static FieldDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static FieldDescriptorProto parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static FieldDescriptorProto parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static FieldDescriptorProto parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static FieldDescriptorProto parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static FieldDescriptorProto parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static FieldDescriptorProto parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static FieldDescriptorProto parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(FieldDescriptorProto fieldDescriptorProto) {
            return newBuilder().mergeFrom(fieldDescriptorProto);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.Builder<Builder> implements FieldDescriptorProtoOrBuilder {
            private int bitField0_;
            private Object defaultValue_;
            private Object extendee_;
            private Label label_;
            private Object name_;
            private int number_;
            private int oneofIndex_;
            private SingleFieldBuilder<FieldOptions, FieldOptions.Builder, FieldOptionsOrBuilder> optionsBuilder_;
            private FieldOptions options_;
            private Object typeName_;
            private Type type_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_FieldDescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_FieldDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldDescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                this.label_ = Label.LABEL_OPTIONAL;
                this.type_ = Type.TYPE_DOUBLE;
                this.typeName_ = "";
                this.extendee_ = "";
                this.defaultValue_ = "";
                this.options_ = FieldOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.name_ = "";
                this.label_ = Label.LABEL_OPTIONAL;
                this.type_ = Type.TYPE_DOUBLE;
                this.typeName_ = "";
                this.extendee_ = "";
                this.defaultValue_ = "";
                this.options_ = FieldOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getOptionsFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.name_ = "";
                int i = this.bitField0_;
                this.number_ = 0;
                this.bitField0_ = i & (-4);
                this.label_ = Label.LABEL_OPTIONAL;
                this.bitField0_ &= -5;
                this.type_ = Type.TYPE_DOUBLE;
                int i2 = this.bitField0_;
                this.typeName_ = "";
                this.extendee_ = "";
                this.defaultValue_ = "";
                this.oneofIndex_ = 0;
                this.bitField0_ = i2 & (-249);
                SingleFieldBuilder<FieldOptions, FieldOptions.Builder, FieldOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = FieldOptions.getDefaultInstance();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -257;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_FieldDescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public FieldDescriptorProto getDefaultInstanceForType() {
                return FieldDescriptorProto.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public FieldDescriptorProto build() {
                FieldDescriptorProto fieldDescriptorProtoBuildPartial = buildPartial();
                if (fieldDescriptorProtoBuildPartial.isInitialized()) {
                    return fieldDescriptorProtoBuildPartial;
                }
                throw newUninitializedMessageException((Message) fieldDescriptorProtoBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public FieldDescriptorProto buildPartial() {
                FieldDescriptorProto fieldDescriptorProto = new FieldDescriptorProto(this);
                int i = this.bitField0_;
                int i2 = (i & 1) != 1 ? 0 : 1;
                fieldDescriptorProto.name_ = this.name_;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                fieldDescriptorProto.number_ = this.number_;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                fieldDescriptorProto.label_ = this.label_;
                if ((i & 8) == 8) {
                    i2 |= 8;
                }
                fieldDescriptorProto.type_ = this.type_;
                if ((i & 16) == 16) {
                    i2 |= 16;
                }
                fieldDescriptorProto.typeName_ = this.typeName_;
                if ((i & 32) == 32) {
                    i2 |= 32;
                }
                fieldDescriptorProto.extendee_ = this.extendee_;
                if ((i & 64) == 64) {
                    i2 |= 64;
                }
                fieldDescriptorProto.defaultValue_ = this.defaultValue_;
                if ((i & 128) == 128) {
                    i2 |= 128;
                }
                fieldDescriptorProto.oneofIndex_ = this.oneofIndex_;
                if ((i & 256) == 256) {
                    i2 |= 256;
                }
                SingleFieldBuilder<FieldOptions, FieldOptions.Builder, FieldOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    fieldDescriptorProto.options_ = this.options_;
                } else {
                    fieldDescriptorProto.options_ = (FieldOptions) singleFieldBuilder.build();
                }
                fieldDescriptorProto.bitField0_ = i2;
                onBuilt();
                return fieldDescriptorProto;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof FieldDescriptorProto) {
                    return mergeFrom((FieldDescriptorProto) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(FieldDescriptorProto fieldDescriptorProto) {
                if (fieldDescriptorProto == FieldDescriptorProto.getDefaultInstance()) {
                    return this;
                }
                if (fieldDescriptorProto.hasName()) {
                    this.bitField0_ |= 1;
                    this.name_ = fieldDescriptorProto.name_;
                    onChanged();
                }
                if (fieldDescriptorProto.hasNumber()) {
                    setNumber(fieldDescriptorProto.getNumber());
                }
                if (fieldDescriptorProto.hasLabel()) {
                    setLabel(fieldDescriptorProto.getLabel());
                }
                if (fieldDescriptorProto.hasType()) {
                    setType(fieldDescriptorProto.getType());
                }
                if (fieldDescriptorProto.hasTypeName()) {
                    this.bitField0_ |= 16;
                    this.typeName_ = fieldDescriptorProto.typeName_;
                    onChanged();
                }
                if (fieldDescriptorProto.hasExtendee()) {
                    this.bitField0_ |= 32;
                    this.extendee_ = fieldDescriptorProto.extendee_;
                    onChanged();
                }
                if (fieldDescriptorProto.hasDefaultValue()) {
                    this.bitField0_ |= 64;
                    this.defaultValue_ = fieldDescriptorProto.defaultValue_;
                    onChanged();
                }
                if (fieldDescriptorProto.hasOneofIndex()) {
                    setOneofIndex(fieldDescriptorProto.getOneofIndex());
                }
                if (fieldDescriptorProto.hasOptions()) {
                    mergeOptions(fieldDescriptorProto.getOptions());
                }
                mergeUnknownFields(fieldDescriptorProto.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return !hasOptions() || getOptions().isInitialized();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                FieldDescriptorProto fieldDescriptorProto = null;
                try {
                    try {
                        FieldDescriptorProto partialFrom = FieldDescriptorProto.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        FieldDescriptorProto fieldDescriptorProto2 = (FieldDescriptorProto) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            fieldDescriptorProto = fieldDescriptorProto2;
                            if (fieldDescriptorProto != null) {
                                mergeFrom(fieldDescriptorProto);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fieldDescriptorProto != null) {
                        mergeFrom(fieldDescriptorProto);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
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

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
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
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = FieldDescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 1;
                this.name_ = byteString;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public boolean hasNumber() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public int getNumber() {
                return this.number_;
            }

            public Builder setNumber(int i) {
                this.bitField0_ |= 2;
                this.number_ = i;
                onChanged();
                return this;
            }

            public Builder clearNumber() {
                this.bitField0_ &= -3;
                this.number_ = 0;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public boolean hasLabel() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public Label getLabel() {
                return this.label_;
            }

            public Builder setLabel(Label label) {
                label.getClass();
                this.bitField0_ |= 4;
                this.label_ = label;
                onChanged();
                return this;
            }

            public Builder clearLabel() {
                this.bitField0_ &= -5;
                this.label_ = Label.LABEL_OPTIONAL;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public boolean hasType() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public Type getType() {
                return this.type_;
            }

            public Builder setType(Type type) {
                type.getClass();
                this.bitField0_ |= 8;
                this.type_ = type;
                onChanged();
                return this;
            }

            public Builder clearType() {
                this.bitField0_ &= -9;
                this.type_ = Type.TYPE_DOUBLE;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public boolean hasTypeName() {
                return (this.bitField0_ & 16) == 16;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public String getTypeName() {
                Object obj = this.typeName_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.typeName_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public ByteString getTypeNameBytes() {
                Object obj = this.typeName_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.typeName_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setTypeName(String str) {
                str.getClass();
                this.bitField0_ |= 16;
                this.typeName_ = str;
                onChanged();
                return this;
            }

            public Builder clearTypeName() {
                this.bitField0_ &= -17;
                this.typeName_ = FieldDescriptorProto.getDefaultInstance().getTypeName();
                onChanged();
                return this;
            }

            public Builder setTypeNameBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 16;
                this.typeName_ = byteString;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public boolean hasExtendee() {
                return (this.bitField0_ & 32) == 32;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public String getExtendee() {
                Object obj = this.extendee_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.extendee_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public ByteString getExtendeeBytes() {
                Object obj = this.extendee_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.extendee_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setExtendee(String str) {
                str.getClass();
                this.bitField0_ |= 32;
                this.extendee_ = str;
                onChanged();
                return this;
            }

            public Builder clearExtendee() {
                this.bitField0_ &= -33;
                this.extendee_ = FieldDescriptorProto.getDefaultInstance().getExtendee();
                onChanged();
                return this;
            }

            public Builder setExtendeeBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 32;
                this.extendee_ = byteString;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public boolean hasDefaultValue() {
                return (this.bitField0_ & 64) == 64;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public String getDefaultValue() {
                Object obj = this.defaultValue_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.defaultValue_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public ByteString getDefaultValueBytes() {
                Object obj = this.defaultValue_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.defaultValue_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setDefaultValue(String str) {
                str.getClass();
                this.bitField0_ |= 64;
                this.defaultValue_ = str;
                onChanged();
                return this;
            }

            public Builder clearDefaultValue() {
                this.bitField0_ &= -65;
                this.defaultValue_ = FieldDescriptorProto.getDefaultInstance().getDefaultValue();
                onChanged();
                return this;
            }

            public Builder setDefaultValueBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 64;
                this.defaultValue_ = byteString;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public boolean hasOneofIndex() {
                return (this.bitField0_ & 128) == 128;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public int getOneofIndex() {
                return this.oneofIndex_;
            }

            public Builder setOneofIndex(int i) {
                this.bitField0_ |= 128;
                this.oneofIndex_ = i;
                onChanged();
                return this;
            }

            public Builder clearOneofIndex() {
                this.bitField0_ &= -129;
                this.oneofIndex_ = 0;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public boolean hasOptions() {
                return (this.bitField0_ & 256) == 256;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public FieldOptions getOptions() {
                SingleFieldBuilder<FieldOptions, FieldOptions.Builder, FieldOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    return this.options_;
                }
                return (FieldOptions) singleFieldBuilder.getMessage();
            }

            public Builder setOptions(FieldOptions fieldOptions) {
                SingleFieldBuilder<FieldOptions, FieldOptions.Builder, FieldOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    fieldOptions.getClass();
                    this.options_ = fieldOptions;
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(fieldOptions);
                }
                this.bitField0_ |= 256;
                return this;
            }

            public Builder setOptions(FieldOptions.Builder builder) {
                SingleFieldBuilder<FieldOptions, FieldOptions.Builder, FieldOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = builder.build();
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(builder.build());
                }
                this.bitField0_ |= 256;
                return this;
            }

            public Builder mergeOptions(FieldOptions fieldOptions) {
                SingleFieldBuilder<FieldOptions, FieldOptions.Builder, FieldOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    if ((this.bitField0_ & 256) == 256 && this.options_ != FieldOptions.getDefaultInstance()) {
                        this.options_ = FieldOptions.newBuilder(this.options_).mergeFrom(fieldOptions).buildPartial();
                    } else {
                        this.options_ = fieldOptions;
                    }
                    onChanged();
                } else {
                    singleFieldBuilder.mergeFrom(fieldOptions);
                }
                this.bitField0_ |= 256;
                return this;
            }

            public Builder clearOptions() {
                SingleFieldBuilder<FieldOptions, FieldOptions.Builder, FieldOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = FieldOptions.getDefaultInstance();
                    onChanged();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -257;
                return this;
            }

            public FieldOptions.Builder getOptionsBuilder() {
                this.bitField0_ |= 256;
                onChanged();
                return (FieldOptions.Builder) getOptionsFieldBuilder().getBuilder();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldDescriptorProtoOrBuilder
            public FieldOptionsOrBuilder getOptionsOrBuilder() {
                SingleFieldBuilder<FieldOptions, FieldOptions.Builder, FieldOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder != null) {
                    return (FieldOptionsOrBuilder) singleFieldBuilder.getMessageOrBuilder();
                }
                return this.options_;
            }

            private SingleFieldBuilder<FieldOptions, FieldOptions.Builder, FieldOptionsOrBuilder> getOptionsFieldBuilder() {
                if (this.optionsBuilder_ == null) {
                    this.optionsBuilder_ = new SingleFieldBuilder<>(getOptions(), getParentForChildren(), isClean());
                    this.options_ = null;
                }
                return this.optionsBuilder_;
            }
        }
    }

    public static final class OneofDescriptorProto extends GeneratedMessage implements OneofDescriptorProtoOrBuilder {
        public static final int NAME_FIELD_NUMBER = 1;
        public static Parser<OneofDescriptorProto> PARSER = new AbstractParser<OneofDescriptorProto>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.OneofDescriptorProto.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public OneofDescriptorProto parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new OneofDescriptorProto(codedInputStream, extensionRegistryLite);
            }
        };
        private static final OneofDescriptorProto defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object name_;
        private final UnknownFieldSet unknownFields;

        private OneofDescriptorProto(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private OneofDescriptorProto(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static OneofDescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public OneofDescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private OneofDescriptorProto(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            while (!z) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag != 10) {
                                    if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    ByteString bytes = codedInputStream.readBytes();
                                    this.bitField0_ = 1 | this.bitField0_;
                                    this.name_ = bytes;
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
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_OneofDescriptorProto_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_OneofDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(OneofDescriptorProto.class, Builder.class);
        }

        static {
            OneofDescriptorProto oneofDescriptorProto = new OneofDescriptorProto(true);
            defaultInstance = oneofDescriptorProto;
            oneofDescriptorProto.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<OneofDescriptorProto> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.OneofDescriptorProtoOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.OneofDescriptorProtoOrBuilder
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

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.OneofDescriptorProtoOrBuilder
        public ByteString getNameBytes() {
            Object obj = this.name_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.name_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        private void initFields() {
            this.name_ = "";
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
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

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBytes(1, getNameBytes());
            }
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBytesSize = ((this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBytesSize(1, getNameBytes()) : 0) + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = iComputeBytesSize;
            return iComputeBytesSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static OneofDescriptorProto parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static OneofDescriptorProto parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static OneofDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static OneofDescriptorProto parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static OneofDescriptorProto parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static OneofDescriptorProto parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static OneofDescriptorProto parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static OneofDescriptorProto parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static OneofDescriptorProto parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static OneofDescriptorProto parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(OneofDescriptorProto oneofDescriptorProto) {
            return newBuilder().mergeFrom(oneofDescriptorProto);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.Builder<Builder> implements OneofDescriptorProtoOrBuilder {
            private int bitField0_;
            private Object name_;

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_OneofDescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_OneofDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(OneofDescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.name_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                boolean z = GeneratedMessage.alwaysUseFieldBuilders;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= -2;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_OneofDescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public OneofDescriptorProto getDefaultInstanceForType() {
                return OneofDescriptorProto.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public OneofDescriptorProto build() {
                OneofDescriptorProto oneofDescriptorProtoBuildPartial = buildPartial();
                if (oneofDescriptorProtoBuildPartial.isInitialized()) {
                    return oneofDescriptorProtoBuildPartial;
                }
                throw newUninitializedMessageException((Message) oneofDescriptorProtoBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public OneofDescriptorProto buildPartial() {
                OneofDescriptorProto oneofDescriptorProto = new OneofDescriptorProto(this);
                int i = (this.bitField0_ & 1) != 1 ? 0 : 1;
                oneofDescriptorProto.name_ = this.name_;
                oneofDescriptorProto.bitField0_ = i;
                onBuilt();
                return oneofDescriptorProto;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof OneofDescriptorProto) {
                    return mergeFrom((OneofDescriptorProto) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(OneofDescriptorProto oneofDescriptorProto) {
                if (oneofDescriptorProto == OneofDescriptorProto.getDefaultInstance()) {
                    return this;
                }
                if (oneofDescriptorProto.hasName()) {
                    this.bitField0_ |= 1;
                    this.name_ = oneofDescriptorProto.name_;
                    onChanged();
                }
                mergeUnknownFields(oneofDescriptorProto.getUnknownFields());
                return this;
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                OneofDescriptorProto oneofDescriptorProto = null;
                try {
                    try {
                        OneofDescriptorProto partialFrom = OneofDescriptorProto.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        OneofDescriptorProto oneofDescriptorProto2 = (OneofDescriptorProto) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            oneofDescriptorProto = oneofDescriptorProto2;
                            if (oneofDescriptorProto != null) {
                                mergeFrom(oneofDescriptorProto);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (oneofDescriptorProto != null) {
                        mergeFrom(oneofDescriptorProto);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.OneofDescriptorProtoOrBuilder
            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.OneofDescriptorProtoOrBuilder
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

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.OneofDescriptorProtoOrBuilder
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
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = OneofDescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 1;
                this.name_ = byteString;
                onChanged();
                return this;
            }
        }
    }

    public static final class EnumDescriptorProto extends GeneratedMessage implements EnumDescriptorProtoOrBuilder {
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 3;
        public static Parser<EnumDescriptorProto> PARSER = new AbstractParser<EnumDescriptorProto>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProto.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public EnumDescriptorProto parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new EnumDescriptorProto(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int VALUE_FIELD_NUMBER = 2;
        private static final EnumDescriptorProto defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object name_;
        private EnumOptions options_;
        private final UnknownFieldSet unknownFields;
        private List<EnumValueDescriptorProto> value_;

        private EnumDescriptorProto(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private EnumDescriptorProto(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static EnumDescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public EnumDescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private EnumDescriptorProto(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            char c = 0;
            while (!z) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                ByteString bytes = codedInputStream.readBytes();
                                this.bitField0_ = 1 | this.bitField0_;
                                this.name_ = bytes;
                            } else if (tag == 18) {
                                if ((c & 2) != 2) {
                                    this.value_ = new ArrayList();
                                    c = 2;
                                }
                                this.value_.add((EnumValueDescriptorProto) codedInputStream.readMessage(EnumValueDescriptorProto.PARSER, extensionRegistryLite));
                            } else if (tag != 26) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                EnumOptions.Builder builder = (this.bitField0_ & 2) == 2 ? this.options_.toBuilder() : null;
                                EnumOptions enumOptions = (EnumOptions) codedInputStream.readMessage(EnumOptions.PARSER, extensionRegistryLite);
                                this.options_ = enumOptions;
                                if (builder != null) {
                                    builder.mergeFrom(enumOptions);
                                    this.options_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 2;
                            }
                        }
                        z = true;
                    } catch (InvalidProtocolBufferException e) {
                        throw e.setUnfinishedMessage(this);
                    } catch (IOException e2) {
                        throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if ((c & 2) == 2) {
                        this.value_ = Collections.unmodifiableList(this.value_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((c & 2) == 2) {
                this.value_ = Collections.unmodifiableList(this.value_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_EnumDescriptorProto_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_EnumDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumDescriptorProto.class, Builder.class);
        }

        static {
            EnumDescriptorProto enumDescriptorProto = new EnumDescriptorProto(true);
            defaultInstance = enumDescriptorProto;
            enumDescriptorProto.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<EnumDescriptorProto> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
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

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
        public ByteString getNameBytes() {
            Object obj = this.name_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.name_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
        public List<EnumValueDescriptorProto> getValueList() {
            return this.value_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
        public List<? extends EnumValueDescriptorProtoOrBuilder> getValueOrBuilderList() {
            return this.value_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
        public int getValueCount() {
            return this.value_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
        public EnumValueDescriptorProto getValue(int i) {
            return this.value_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
        public EnumValueDescriptorProtoOrBuilder getValueOrBuilder(int i) {
            return this.value_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
        public boolean hasOptions() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
        public EnumOptions getOptions() {
            return this.options_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
        public EnumOptionsOrBuilder getOptionsOrBuilder() {
            return this.options_;
        }

        private void initFields() {
            this.name_ = "";
            this.value_ = Collections.emptyList();
            this.options_ = EnumOptions.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            for (int i = 0; i < getValueCount(); i++) {
                if (!getValue(i).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (hasOptions() && !getOptions().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBytes(1, getNameBytes());
            }
            for (int i = 0; i < this.value_.size(); i++) {
                codedOutputStream.writeMessage(2, this.value_.get(i));
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeMessage(3, this.options_);
            }
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBytesSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBytesSize(1, getNameBytes()) : 0;
            for (int i2 = 0; i2 < this.value_.size(); i2++) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(2, this.value_.get(i2));
            }
            if ((this.bitField0_ & 2) == 2) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(3, this.options_);
            }
            int serializedSize = iComputeBytesSize + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static EnumDescriptorProto parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static EnumDescriptorProto parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static EnumDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static EnumDescriptorProto parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static EnumDescriptorProto parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static EnumDescriptorProto parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static EnumDescriptorProto parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static EnumDescriptorProto parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static EnumDescriptorProto parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static EnumDescriptorProto parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(EnumDescriptorProto enumDescriptorProto) {
            return newBuilder().mergeFrom(enumDescriptorProto);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.Builder<Builder> implements EnumDescriptorProtoOrBuilder {
            private int bitField0_;
            private Object name_;
            private SingleFieldBuilder<EnumOptions, EnumOptions.Builder, EnumOptionsOrBuilder> optionsBuilder_;
            private EnumOptions options_;
            private RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> valueBuilder_;
            private List<EnumValueDescriptorProto> value_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_EnumDescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_EnumDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumDescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                this.value_ = Collections.emptyList();
                this.options_ = EnumOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.name_ = "";
                this.value_ = Collections.emptyList();
                this.options_ = EnumOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getValueFieldBuilder();
                    getOptionsFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= -2;
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    repeatedFieldBuilder.clear();
                }
                SingleFieldBuilder<EnumOptions, EnumOptions.Builder, EnumOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = EnumOptions.getDefaultInstance();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_EnumDescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public EnumDescriptorProto getDefaultInstanceForType() {
                return EnumDescriptorProto.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public EnumDescriptorProto build() {
                EnumDescriptorProto enumDescriptorProtoBuildPartial = buildPartial();
                if (enumDescriptorProtoBuildPartial.isInitialized()) {
                    return enumDescriptorProtoBuildPartial;
                }
                throw newUninitializedMessageException((Message) enumDescriptorProtoBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public EnumDescriptorProto buildPartial() {
                EnumDescriptorProto enumDescriptorProto = new EnumDescriptorProto(this);
                int i = this.bitField0_;
                int i2 = (i & 1) != 1 ? 0 : 1;
                enumDescriptorProto.name_ = this.name_;
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder != null) {
                    enumDescriptorProto.value_ = repeatedFieldBuilder.build();
                } else {
                    if ((this.bitField0_ & 2) == 2) {
                        this.value_ = Collections.unmodifiableList(this.value_);
                        this.bitField0_ &= -3;
                    }
                    enumDescriptorProto.value_ = this.value_;
                }
                if ((i & 4) == 4) {
                    i2 |= 2;
                }
                SingleFieldBuilder<EnumOptions, EnumOptions.Builder, EnumOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    enumDescriptorProto.options_ = this.options_;
                } else {
                    enumDescriptorProto.options_ = (EnumOptions) singleFieldBuilder.build();
                }
                enumDescriptorProto.bitField0_ = i2;
                onBuilt();
                return enumDescriptorProto;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof EnumDescriptorProto) {
                    return mergeFrom((EnumDescriptorProto) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(EnumDescriptorProto enumDescriptorProto) {
                if (enumDescriptorProto == EnumDescriptorProto.getDefaultInstance()) {
                    return this;
                }
                if (enumDescriptorProto.hasName()) {
                    this.bitField0_ |= 1;
                    this.name_ = enumDescriptorProto.name_;
                    onChanged();
                }
                if (this.valueBuilder_ == null) {
                    if (!enumDescriptorProto.value_.isEmpty()) {
                        if (this.value_.isEmpty()) {
                            this.value_ = enumDescriptorProto.value_;
                            this.bitField0_ &= -3;
                        } else {
                            ensureValueIsMutable();
                            this.value_.addAll(enumDescriptorProto.value_);
                        }
                        onChanged();
                    }
                } else if (!enumDescriptorProto.value_.isEmpty()) {
                    if (!this.valueBuilder_.isEmpty()) {
                        this.valueBuilder_.addAllMessages(enumDescriptorProto.value_);
                    } else {
                        this.valueBuilder_.dispose();
                        this.valueBuilder_ = null;
                        this.value_ = enumDescriptorProto.value_;
                        this.bitField0_ &= -3;
                        this.valueBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getValueFieldBuilder() : null;
                    }
                }
                if (enumDescriptorProto.hasOptions()) {
                    mergeOptions(enumDescriptorProto.getOptions());
                }
                mergeUnknownFields(enumDescriptorProto.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getValueCount(); i++) {
                    if (!getValue(i).isInitialized()) {
                        return false;
                    }
                }
                return !hasOptions() || getOptions().isInitialized();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                EnumDescriptorProto enumDescriptorProto = null;
                try {
                    try {
                        EnumDescriptorProto partialFrom = EnumDescriptorProto.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        EnumDescriptorProto enumDescriptorProto2 = (EnumDescriptorProto) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            enumDescriptorProto = enumDescriptorProto2;
                            if (enumDescriptorProto != null) {
                                mergeFrom(enumDescriptorProto);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (enumDescriptorProto != null) {
                        mergeFrom(enumDescriptorProto);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
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

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
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
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = EnumDescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 1;
                this.name_ = byteString;
                onChanged();
                return this;
            }

            private void ensureValueIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.value_ = new ArrayList(this.value_);
                    this.bitField0_ |= 2;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
            public List<EnumValueDescriptorProto> getValueList() {
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.value_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
            public int getValueCount() {
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.value_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
            public EnumValueDescriptorProto getValue(int i) {
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.value_.get(i);
                }
                return (EnumValueDescriptorProto) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setValue(int i, EnumValueDescriptorProto enumValueDescriptorProto) {
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder == null) {
                    enumValueDescriptorProto.getClass();
                    ensureValueIsMutable();
                    this.value_.set(i, enumValueDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, enumValueDescriptorProto);
                }
                return this;
            }

            public Builder setValue(int i, EnumValueDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureValueIsMutable();
                    this.value_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addValue(EnumValueDescriptorProto enumValueDescriptorProto) {
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder == null) {
                    enumValueDescriptorProto.getClass();
                    ensureValueIsMutable();
                    this.value_.add(enumValueDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(enumValueDescriptorProto);
                }
                return this;
            }

            public Builder addValue(int i, EnumValueDescriptorProto enumValueDescriptorProto) {
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder == null) {
                    enumValueDescriptorProto.getClass();
                    ensureValueIsMutable();
                    this.value_.add(i, enumValueDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, enumValueDescriptorProto);
                }
                return this;
            }

            public Builder addValue(EnumValueDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureValueIsMutable();
                    this.value_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addValue(int i, EnumValueDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureValueIsMutable();
                    this.value_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllValue(Iterable<? extends EnumValueDescriptorProto> iterable) {
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureValueIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.value_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearValue() {
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeValue(int i) {
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureValueIsMutable();
                    this.value_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public EnumValueDescriptorProto.Builder getValueBuilder(int i) {
                return (EnumValueDescriptorProto.Builder) getValueFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
            public EnumValueDescriptorProtoOrBuilder getValueOrBuilder(int i) {
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.value_.get(i);
                }
                return (EnumValueDescriptorProtoOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
            public List<? extends EnumValueDescriptorProtoOrBuilder> getValueOrBuilderList() {
                RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> repeatedFieldBuilder = this.valueBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.value_);
            }

            public EnumValueDescriptorProto.Builder addValueBuilder() {
                return (EnumValueDescriptorProto.Builder) getValueFieldBuilder().addBuilder(EnumValueDescriptorProto.getDefaultInstance());
            }

            public EnumValueDescriptorProto.Builder addValueBuilder(int i) {
                return (EnumValueDescriptorProto.Builder) getValueFieldBuilder().addBuilder(i, EnumValueDescriptorProto.getDefaultInstance());
            }

            public List<EnumValueDescriptorProto.Builder> getValueBuilderList() {
                return getValueFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<EnumValueDescriptorProto, EnumValueDescriptorProto.Builder, EnumValueDescriptorProtoOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = new RepeatedFieldBuilder<>(this.value_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.value_ = null;
                }
                return this.valueBuilder_;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
            public boolean hasOptions() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
            public EnumOptions getOptions() {
                SingleFieldBuilder<EnumOptions, EnumOptions.Builder, EnumOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    return this.options_;
                }
                return (EnumOptions) singleFieldBuilder.getMessage();
            }

            public Builder setOptions(EnumOptions enumOptions) {
                SingleFieldBuilder<EnumOptions, EnumOptions.Builder, EnumOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    enumOptions.getClass();
                    this.options_ = enumOptions;
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(enumOptions);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setOptions(EnumOptions.Builder builder) {
                SingleFieldBuilder<EnumOptions, EnumOptions.Builder, EnumOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = builder.build();
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(builder.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeOptions(EnumOptions enumOptions) {
                SingleFieldBuilder<EnumOptions, EnumOptions.Builder, EnumOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    if ((this.bitField0_ & 4) == 4 && this.options_ != EnumOptions.getDefaultInstance()) {
                        this.options_ = EnumOptions.newBuilder(this.options_).mergeFrom(enumOptions).buildPartial();
                    } else {
                        this.options_ = enumOptions;
                    }
                    onChanged();
                } else {
                    singleFieldBuilder.mergeFrom(enumOptions);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearOptions() {
                SingleFieldBuilder<EnumOptions, EnumOptions.Builder, EnumOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = EnumOptions.getDefaultInstance();
                    onChanged();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public EnumOptions.Builder getOptionsBuilder() {
                this.bitField0_ |= 4;
                onChanged();
                return (EnumOptions.Builder) getOptionsFieldBuilder().getBuilder();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumDescriptorProtoOrBuilder
            public EnumOptionsOrBuilder getOptionsOrBuilder() {
                SingleFieldBuilder<EnumOptions, EnumOptions.Builder, EnumOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder != null) {
                    return (EnumOptionsOrBuilder) singleFieldBuilder.getMessageOrBuilder();
                }
                return this.options_;
            }

            private SingleFieldBuilder<EnumOptions, EnumOptions.Builder, EnumOptionsOrBuilder> getOptionsFieldBuilder() {
                if (this.optionsBuilder_ == null) {
                    this.optionsBuilder_ = new SingleFieldBuilder<>(getOptions(), getParentForChildren(), isClean());
                    this.options_ = null;
                }
                return this.optionsBuilder_;
            }
        }
    }

    public static final class EnumValueDescriptorProto extends GeneratedMessage implements EnumValueDescriptorProtoOrBuilder {
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int NUMBER_FIELD_NUMBER = 2;
        public static final int OPTIONS_FIELD_NUMBER = 3;
        public static Parser<EnumValueDescriptorProto> PARSER = new AbstractParser<EnumValueDescriptorProto>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProto.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public EnumValueDescriptorProto parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new EnumValueDescriptorProto(codedInputStream, extensionRegistryLite);
            }
        };
        private static final EnumValueDescriptorProto defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object name_;
        private int number_;
        private EnumValueOptions options_;
        private final UnknownFieldSet unknownFields;

        private EnumValueDescriptorProto(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private EnumValueDescriptorProto(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static EnumValueDescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public EnumValueDescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private EnumValueDescriptorProto(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            while (!z) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 10) {
                                    ByteString bytes = codedInputStream.readBytes();
                                    this.bitField0_ = 1 | this.bitField0_;
                                    this.name_ = bytes;
                                } else if (tag == 16) {
                                    this.bitField0_ |= 2;
                                    this.number_ = codedInputStream.readInt32();
                                } else if (tag != 26) {
                                    if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    EnumValueOptions.Builder builder = (this.bitField0_ & 4) == 4 ? this.options_.toBuilder() : null;
                                    EnumValueOptions enumValueOptions = (EnumValueOptions) codedInputStream.readMessage(EnumValueOptions.PARSER, extensionRegistryLite);
                                    this.options_ = enumValueOptions;
                                    if (builder != null) {
                                        builder.mergeFrom(enumValueOptions);
                                        this.options_ = builder.buildPartial();
                                    }
                                    this.bitField0_ |= 4;
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw e.setUnfinishedMessage(this);
                        }
                    } catch (IOException e2) {
                        throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_EnumValueDescriptorProto_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_EnumValueDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumValueDescriptorProto.class, Builder.class);
        }

        static {
            EnumValueDescriptorProto enumValueDescriptorProto = new EnumValueDescriptorProto(true);
            defaultInstance = enumValueDescriptorProto;
            enumValueDescriptorProto.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<EnumValueDescriptorProto> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
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

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
        public ByteString getNameBytes() {
            Object obj = this.name_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.name_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
        public boolean hasNumber() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
        public int getNumber() {
            return this.number_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
        public boolean hasOptions() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
        public EnumValueOptions getOptions() {
            return this.options_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
        public EnumValueOptionsOrBuilder getOptionsOrBuilder() {
            return this.options_;
        }

        private void initFields() {
            this.name_ = "";
            this.number_ = 0;
            this.options_ = EnumValueOptions.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            if (hasOptions() && !getOptions().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBytes(1, getNameBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeInt32(2, this.number_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeMessage(3, this.options_);
            }
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBytesSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBytesSize(1, getNameBytes()) : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeBytesSize += CodedOutputStream.computeInt32Size(2, this.number_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(3, this.options_);
            }
            int serializedSize = iComputeBytesSize + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static EnumValueDescriptorProto parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static EnumValueDescriptorProto parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static EnumValueDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static EnumValueDescriptorProto parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static EnumValueDescriptorProto parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static EnumValueDescriptorProto parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static EnumValueDescriptorProto parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static EnumValueDescriptorProto parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static EnumValueDescriptorProto parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static EnumValueDescriptorProto parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(EnumValueDescriptorProto enumValueDescriptorProto) {
            return newBuilder().mergeFrom(enumValueDescriptorProto);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.Builder<Builder> implements EnumValueDescriptorProtoOrBuilder {
            private int bitField0_;
            private Object name_;
            private int number_;
            private SingleFieldBuilder<EnumValueOptions, EnumValueOptions.Builder, EnumValueOptionsOrBuilder> optionsBuilder_;
            private EnumValueOptions options_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_EnumValueDescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_EnumValueDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumValueDescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                this.options_ = EnumValueOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.name_ = "";
                this.options_ = EnumValueOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getOptionsFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.name_ = "";
                int i = this.bitField0_;
                this.number_ = 0;
                this.bitField0_ = i & (-4);
                SingleFieldBuilder<EnumValueOptions, EnumValueOptions.Builder, EnumValueOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = EnumValueOptions.getDefaultInstance();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_EnumValueDescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public EnumValueDescriptorProto getDefaultInstanceForType() {
                return EnumValueDescriptorProto.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public EnumValueDescriptorProto build() {
                EnumValueDescriptorProto enumValueDescriptorProtoBuildPartial = buildPartial();
                if (enumValueDescriptorProtoBuildPartial.isInitialized()) {
                    return enumValueDescriptorProtoBuildPartial;
                }
                throw newUninitializedMessageException((Message) enumValueDescriptorProtoBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public EnumValueDescriptorProto buildPartial() {
                EnumValueDescriptorProto enumValueDescriptorProto = new EnumValueDescriptorProto(this);
                int i = this.bitField0_;
                int i2 = (i & 1) != 1 ? 0 : 1;
                enumValueDescriptorProto.name_ = this.name_;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                enumValueDescriptorProto.number_ = this.number_;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                SingleFieldBuilder<EnumValueOptions, EnumValueOptions.Builder, EnumValueOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    enumValueDescriptorProto.options_ = this.options_;
                } else {
                    enumValueDescriptorProto.options_ = (EnumValueOptions) singleFieldBuilder.build();
                }
                enumValueDescriptorProto.bitField0_ = i2;
                onBuilt();
                return enumValueDescriptorProto;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof EnumValueDescriptorProto) {
                    return mergeFrom((EnumValueDescriptorProto) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(EnumValueDescriptorProto enumValueDescriptorProto) {
                if (enumValueDescriptorProto == EnumValueDescriptorProto.getDefaultInstance()) {
                    return this;
                }
                if (enumValueDescriptorProto.hasName()) {
                    this.bitField0_ |= 1;
                    this.name_ = enumValueDescriptorProto.name_;
                    onChanged();
                }
                if (enumValueDescriptorProto.hasNumber()) {
                    setNumber(enumValueDescriptorProto.getNumber());
                }
                if (enumValueDescriptorProto.hasOptions()) {
                    mergeOptions(enumValueDescriptorProto.getOptions());
                }
                mergeUnknownFields(enumValueDescriptorProto.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return !hasOptions() || getOptions().isInitialized();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                EnumValueDescriptorProto enumValueDescriptorProto = null;
                try {
                    try {
                        EnumValueDescriptorProto partialFrom = EnumValueDescriptorProto.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        EnumValueDescriptorProto enumValueDescriptorProto2 = (EnumValueDescriptorProto) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            enumValueDescriptorProto = enumValueDescriptorProto2;
                            if (enumValueDescriptorProto != null) {
                                mergeFrom(enumValueDescriptorProto);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (enumValueDescriptorProto != null) {
                        mergeFrom(enumValueDescriptorProto);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
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

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
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
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = EnumValueDescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 1;
                this.name_ = byteString;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
            public boolean hasNumber() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
            public int getNumber() {
                return this.number_;
            }

            public Builder setNumber(int i) {
                this.bitField0_ |= 2;
                this.number_ = i;
                onChanged();
                return this;
            }

            public Builder clearNumber() {
                this.bitField0_ &= -3;
                this.number_ = 0;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
            public boolean hasOptions() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
            public EnumValueOptions getOptions() {
                SingleFieldBuilder<EnumValueOptions, EnumValueOptions.Builder, EnumValueOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    return this.options_;
                }
                return (EnumValueOptions) singleFieldBuilder.getMessage();
            }

            public Builder setOptions(EnumValueOptions enumValueOptions) {
                SingleFieldBuilder<EnumValueOptions, EnumValueOptions.Builder, EnumValueOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    enumValueOptions.getClass();
                    this.options_ = enumValueOptions;
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(enumValueOptions);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setOptions(EnumValueOptions.Builder builder) {
                SingleFieldBuilder<EnumValueOptions, EnumValueOptions.Builder, EnumValueOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = builder.build();
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(builder.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeOptions(EnumValueOptions enumValueOptions) {
                SingleFieldBuilder<EnumValueOptions, EnumValueOptions.Builder, EnumValueOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    if ((this.bitField0_ & 4) == 4 && this.options_ != EnumValueOptions.getDefaultInstance()) {
                        this.options_ = EnumValueOptions.newBuilder(this.options_).mergeFrom(enumValueOptions).buildPartial();
                    } else {
                        this.options_ = enumValueOptions;
                    }
                    onChanged();
                } else {
                    singleFieldBuilder.mergeFrom(enumValueOptions);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearOptions() {
                SingleFieldBuilder<EnumValueOptions, EnumValueOptions.Builder, EnumValueOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = EnumValueOptions.getDefaultInstance();
                    onChanged();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public EnumValueOptions.Builder getOptionsBuilder() {
                this.bitField0_ |= 4;
                onChanged();
                return (EnumValueOptions.Builder) getOptionsFieldBuilder().getBuilder();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueDescriptorProtoOrBuilder
            public EnumValueOptionsOrBuilder getOptionsOrBuilder() {
                SingleFieldBuilder<EnumValueOptions, EnumValueOptions.Builder, EnumValueOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder != null) {
                    return (EnumValueOptionsOrBuilder) singleFieldBuilder.getMessageOrBuilder();
                }
                return this.options_;
            }

            private SingleFieldBuilder<EnumValueOptions, EnumValueOptions.Builder, EnumValueOptionsOrBuilder> getOptionsFieldBuilder() {
                if (this.optionsBuilder_ == null) {
                    this.optionsBuilder_ = new SingleFieldBuilder<>(getOptions(), getParentForChildren(), isClean());
                    this.options_ = null;
                }
                return this.optionsBuilder_;
            }
        }
    }

    public static final class ServiceDescriptorProto extends GeneratedMessage implements ServiceDescriptorProtoOrBuilder {
        public static final int METHOD_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 3;
        public static Parser<ServiceDescriptorProto> PARSER = new AbstractParser<ServiceDescriptorProto>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProto.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public ServiceDescriptorProto parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new ServiceDescriptorProto(codedInputStream, extensionRegistryLite);
            }
        };
        private static final ServiceDescriptorProto defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<MethodDescriptorProto> method_;
        private Object name_;
        private ServiceOptions options_;
        private final UnknownFieldSet unknownFields;

        private ServiceDescriptorProto(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private ServiceDescriptorProto(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static ServiceDescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public ServiceDescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ServiceDescriptorProto(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            char c = 0;
            while (!z) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                ByteString bytes = codedInputStream.readBytes();
                                this.bitField0_ = 1 | this.bitField0_;
                                this.name_ = bytes;
                            } else if (tag == 18) {
                                if ((c & 2) != 2) {
                                    this.method_ = new ArrayList();
                                    c = 2;
                                }
                                this.method_.add((MethodDescriptorProto) codedInputStream.readMessage(MethodDescriptorProto.PARSER, extensionRegistryLite));
                            } else if (tag != 26) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                ServiceOptions.Builder builder = (this.bitField0_ & 2) == 2 ? this.options_.toBuilder() : null;
                                ServiceOptions serviceOptions = (ServiceOptions) codedInputStream.readMessage(ServiceOptions.PARSER, extensionRegistryLite);
                                this.options_ = serviceOptions;
                                if (builder != null) {
                                    builder.mergeFrom(serviceOptions);
                                    this.options_ = builder.buildPartial();
                                }
                                this.bitField0_ |= 2;
                            }
                        }
                        z = true;
                    } catch (InvalidProtocolBufferException e) {
                        throw e.setUnfinishedMessage(this);
                    } catch (IOException e2) {
                        throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if ((c & 2) == 2) {
                        this.method_ = Collections.unmodifiableList(this.method_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((c & 2) == 2) {
                this.method_ = Collections.unmodifiableList(this.method_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_ServiceDescriptorProto_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_ServiceDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(ServiceDescriptorProto.class, Builder.class);
        }

        static {
            ServiceDescriptorProto serviceDescriptorProto = new ServiceDescriptorProto(true);
            defaultInstance = serviceDescriptorProto;
            serviceDescriptorProto.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<ServiceDescriptorProto> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
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

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
        public ByteString getNameBytes() {
            Object obj = this.name_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.name_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
        public List<MethodDescriptorProto> getMethodList() {
            return this.method_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
        public List<? extends MethodDescriptorProtoOrBuilder> getMethodOrBuilderList() {
            return this.method_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
        public int getMethodCount() {
            return this.method_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
        public MethodDescriptorProto getMethod(int i) {
            return this.method_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
        public MethodDescriptorProtoOrBuilder getMethodOrBuilder(int i) {
            return this.method_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
        public boolean hasOptions() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
        public ServiceOptions getOptions() {
            return this.options_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
        public ServiceOptionsOrBuilder getOptionsOrBuilder() {
            return this.options_;
        }

        private void initFields() {
            this.name_ = "";
            this.method_ = Collections.emptyList();
            this.options_ = ServiceOptions.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            for (int i = 0; i < getMethodCount(); i++) {
                if (!getMethod(i).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (hasOptions() && !getOptions().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBytes(1, getNameBytes());
            }
            for (int i = 0; i < this.method_.size(); i++) {
                codedOutputStream.writeMessage(2, this.method_.get(i));
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeMessage(3, this.options_);
            }
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBytesSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBytesSize(1, getNameBytes()) : 0;
            for (int i2 = 0; i2 < this.method_.size(); i2++) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(2, this.method_.get(i2));
            }
            if ((this.bitField0_ & 2) == 2) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(3, this.options_);
            }
            int serializedSize = iComputeBytesSize + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static ServiceDescriptorProto parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static ServiceDescriptorProto parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static ServiceDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static ServiceDescriptorProto parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static ServiceDescriptorProto parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static ServiceDescriptorProto parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static ServiceDescriptorProto parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static ServiceDescriptorProto parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static ServiceDescriptorProto parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static ServiceDescriptorProto parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(ServiceDescriptorProto serviceDescriptorProto) {
            return newBuilder().mergeFrom(serviceDescriptorProto);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.Builder<Builder> implements ServiceDescriptorProtoOrBuilder {
            private int bitField0_;
            private RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> methodBuilder_;
            private List<MethodDescriptorProto> method_;
            private Object name_;
            private SingleFieldBuilder<ServiceOptions, ServiceOptions.Builder, ServiceOptionsOrBuilder> optionsBuilder_;
            private ServiceOptions options_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_ServiceDescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_ServiceDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(ServiceDescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                this.method_ = Collections.emptyList();
                this.options_ = ServiceOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.name_ = "";
                this.method_ = Collections.emptyList();
                this.options_ = ServiceOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getMethodFieldBuilder();
                    getOptionsFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= -2;
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.method_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    repeatedFieldBuilder.clear();
                }
                SingleFieldBuilder<ServiceOptions, ServiceOptions.Builder, ServiceOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = ServiceOptions.getDefaultInstance();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_ServiceDescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public ServiceDescriptorProto getDefaultInstanceForType() {
                return ServiceDescriptorProto.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public ServiceDescriptorProto build() {
                ServiceDescriptorProto serviceDescriptorProtoBuildPartial = buildPartial();
                if (serviceDescriptorProtoBuildPartial.isInitialized()) {
                    return serviceDescriptorProtoBuildPartial;
                }
                throw newUninitializedMessageException((Message) serviceDescriptorProtoBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public ServiceDescriptorProto buildPartial() {
                ServiceDescriptorProto serviceDescriptorProto = new ServiceDescriptorProto(this);
                int i = this.bitField0_;
                int i2 = (i & 1) != 1 ? 0 : 1;
                serviceDescriptorProto.name_ = this.name_;
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder != null) {
                    serviceDescriptorProto.method_ = repeatedFieldBuilder.build();
                } else {
                    if ((this.bitField0_ & 2) == 2) {
                        this.method_ = Collections.unmodifiableList(this.method_);
                        this.bitField0_ &= -3;
                    }
                    serviceDescriptorProto.method_ = this.method_;
                }
                if ((i & 4) == 4) {
                    i2 |= 2;
                }
                SingleFieldBuilder<ServiceOptions, ServiceOptions.Builder, ServiceOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    serviceDescriptorProto.options_ = this.options_;
                } else {
                    serviceDescriptorProto.options_ = (ServiceOptions) singleFieldBuilder.build();
                }
                serviceDescriptorProto.bitField0_ = i2;
                onBuilt();
                return serviceDescriptorProto;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof ServiceDescriptorProto) {
                    return mergeFrom((ServiceDescriptorProto) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(ServiceDescriptorProto serviceDescriptorProto) {
                if (serviceDescriptorProto == ServiceDescriptorProto.getDefaultInstance()) {
                    return this;
                }
                if (serviceDescriptorProto.hasName()) {
                    this.bitField0_ |= 1;
                    this.name_ = serviceDescriptorProto.name_;
                    onChanged();
                }
                if (this.methodBuilder_ == null) {
                    if (!serviceDescriptorProto.method_.isEmpty()) {
                        if (this.method_.isEmpty()) {
                            this.method_ = serviceDescriptorProto.method_;
                            this.bitField0_ &= -3;
                        } else {
                            ensureMethodIsMutable();
                            this.method_.addAll(serviceDescriptorProto.method_);
                        }
                        onChanged();
                    }
                } else if (!serviceDescriptorProto.method_.isEmpty()) {
                    if (!this.methodBuilder_.isEmpty()) {
                        this.methodBuilder_.addAllMessages(serviceDescriptorProto.method_);
                    } else {
                        this.methodBuilder_.dispose();
                        this.methodBuilder_ = null;
                        this.method_ = serviceDescriptorProto.method_;
                        this.bitField0_ &= -3;
                        this.methodBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getMethodFieldBuilder() : null;
                    }
                }
                if (serviceDescriptorProto.hasOptions()) {
                    mergeOptions(serviceDescriptorProto.getOptions());
                }
                mergeUnknownFields(serviceDescriptorProto.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getMethodCount(); i++) {
                    if (!getMethod(i).isInitialized()) {
                        return false;
                    }
                }
                return !hasOptions() || getOptions().isInitialized();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                ServiceDescriptorProto serviceDescriptorProto = null;
                try {
                    try {
                        ServiceDescriptorProto partialFrom = ServiceDescriptorProto.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        ServiceDescriptorProto serviceDescriptorProto2 = (ServiceDescriptorProto) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            serviceDescriptorProto = serviceDescriptorProto2;
                            if (serviceDescriptorProto != null) {
                                mergeFrom(serviceDescriptorProto);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (serviceDescriptorProto != null) {
                        mergeFrom(serviceDescriptorProto);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
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

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
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
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = ServiceDescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 1;
                this.name_ = byteString;
                onChanged();
                return this;
            }

            private void ensureMethodIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.method_ = new ArrayList(this.method_);
                    this.bitField0_ |= 2;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
            public List<MethodDescriptorProto> getMethodList() {
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.method_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
            public int getMethodCount() {
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.method_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
            public MethodDescriptorProto getMethod(int i) {
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.method_.get(i);
                }
                return (MethodDescriptorProto) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setMethod(int i, MethodDescriptorProto methodDescriptorProto) {
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder == null) {
                    methodDescriptorProto.getClass();
                    ensureMethodIsMutable();
                    this.method_.set(i, methodDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, methodDescriptorProto);
                }
                return this;
            }

            public Builder setMethod(int i, MethodDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureMethodIsMutable();
                    this.method_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addMethod(MethodDescriptorProto methodDescriptorProto) {
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder == null) {
                    methodDescriptorProto.getClass();
                    ensureMethodIsMutable();
                    this.method_.add(methodDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(methodDescriptorProto);
                }
                return this;
            }

            public Builder addMethod(int i, MethodDescriptorProto methodDescriptorProto) {
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder == null) {
                    methodDescriptorProto.getClass();
                    ensureMethodIsMutable();
                    this.method_.add(i, methodDescriptorProto);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, methodDescriptorProto);
                }
                return this;
            }

            public Builder addMethod(MethodDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureMethodIsMutable();
                    this.method_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addMethod(int i, MethodDescriptorProto.Builder builder) {
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureMethodIsMutable();
                    this.method_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllMethod(Iterable<? extends MethodDescriptorProto> iterable) {
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureMethodIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.method_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearMethod() {
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.method_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeMethod(int i) {
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureMethodIsMutable();
                    this.method_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public MethodDescriptorProto.Builder getMethodBuilder(int i) {
                return (MethodDescriptorProto.Builder) getMethodFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
            public MethodDescriptorProtoOrBuilder getMethodOrBuilder(int i) {
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.method_.get(i);
                }
                return (MethodDescriptorProtoOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
            public List<? extends MethodDescriptorProtoOrBuilder> getMethodOrBuilderList() {
                RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> repeatedFieldBuilder = this.methodBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.method_);
            }

            public MethodDescriptorProto.Builder addMethodBuilder() {
                return (MethodDescriptorProto.Builder) getMethodFieldBuilder().addBuilder(MethodDescriptorProto.getDefaultInstance());
            }

            public MethodDescriptorProto.Builder addMethodBuilder(int i) {
                return (MethodDescriptorProto.Builder) getMethodFieldBuilder().addBuilder(i, MethodDescriptorProto.getDefaultInstance());
            }

            public List<MethodDescriptorProto.Builder> getMethodBuilderList() {
                return getMethodFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<MethodDescriptorProto, MethodDescriptorProto.Builder, MethodDescriptorProtoOrBuilder> getMethodFieldBuilder() {
                if (this.methodBuilder_ == null) {
                    this.methodBuilder_ = new RepeatedFieldBuilder<>(this.method_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.method_ = null;
                }
                return this.methodBuilder_;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
            public boolean hasOptions() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
            public ServiceOptions getOptions() {
                SingleFieldBuilder<ServiceOptions, ServiceOptions.Builder, ServiceOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    return this.options_;
                }
                return (ServiceOptions) singleFieldBuilder.getMessage();
            }

            public Builder setOptions(ServiceOptions serviceOptions) {
                SingleFieldBuilder<ServiceOptions, ServiceOptions.Builder, ServiceOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    serviceOptions.getClass();
                    this.options_ = serviceOptions;
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(serviceOptions);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setOptions(ServiceOptions.Builder builder) {
                SingleFieldBuilder<ServiceOptions, ServiceOptions.Builder, ServiceOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = builder.build();
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(builder.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeOptions(ServiceOptions serviceOptions) {
                SingleFieldBuilder<ServiceOptions, ServiceOptions.Builder, ServiceOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    if ((this.bitField0_ & 4) == 4 && this.options_ != ServiceOptions.getDefaultInstance()) {
                        this.options_ = ServiceOptions.newBuilder(this.options_).mergeFrom(serviceOptions).buildPartial();
                    } else {
                        this.options_ = serviceOptions;
                    }
                    onChanged();
                } else {
                    singleFieldBuilder.mergeFrom(serviceOptions);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearOptions() {
                SingleFieldBuilder<ServiceOptions, ServiceOptions.Builder, ServiceOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = ServiceOptions.getDefaultInstance();
                    onChanged();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public ServiceOptions.Builder getOptionsBuilder() {
                this.bitField0_ |= 4;
                onChanged();
                return (ServiceOptions.Builder) getOptionsFieldBuilder().getBuilder();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceDescriptorProtoOrBuilder
            public ServiceOptionsOrBuilder getOptionsOrBuilder() {
                SingleFieldBuilder<ServiceOptions, ServiceOptions.Builder, ServiceOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder != null) {
                    return (ServiceOptionsOrBuilder) singleFieldBuilder.getMessageOrBuilder();
                }
                return this.options_;
            }

            private SingleFieldBuilder<ServiceOptions, ServiceOptions.Builder, ServiceOptionsOrBuilder> getOptionsFieldBuilder() {
                if (this.optionsBuilder_ == null) {
                    this.optionsBuilder_ = new SingleFieldBuilder<>(getOptions(), getParentForChildren(), isClean());
                    this.options_ = null;
                }
                return this.optionsBuilder_;
            }
        }
    }

    public static final class MethodDescriptorProto extends GeneratedMessage implements MethodDescriptorProtoOrBuilder {
        public static final int INPUT_TYPE_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 4;
        public static final int OUTPUT_TYPE_FIELD_NUMBER = 3;
        public static Parser<MethodDescriptorProto> PARSER = new AbstractParser<MethodDescriptorProto>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProto.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public MethodDescriptorProto parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new MethodDescriptorProto(codedInputStream, extensionRegistryLite);
            }
        };
        private static final MethodDescriptorProto defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private Object inputType_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object name_;
        private MethodOptions options_;
        private Object outputType_;
        private final UnknownFieldSet unknownFields;

        private MethodDescriptorProto(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private MethodDescriptorProto(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static MethodDescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public MethodDescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MethodDescriptorProto(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            while (!z) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 10) {
                                    ByteString bytes = codedInputStream.readBytes();
                                    this.bitField0_ = 1 | this.bitField0_;
                                    this.name_ = bytes;
                                } else if (tag == 18) {
                                    ByteString bytes2 = codedInputStream.readBytes();
                                    this.bitField0_ |= 2;
                                    this.inputType_ = bytes2;
                                } else if (tag == 26) {
                                    ByteString bytes3 = codedInputStream.readBytes();
                                    this.bitField0_ |= 4;
                                    this.outputType_ = bytes3;
                                } else if (tag != 34) {
                                    if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    MethodOptions.Builder builder = (this.bitField0_ & 8) == 8 ? this.options_.toBuilder() : null;
                                    MethodOptions methodOptions = (MethodOptions) codedInputStream.readMessage(MethodOptions.PARSER, extensionRegistryLite);
                                    this.options_ = methodOptions;
                                    if (builder != null) {
                                        builder.mergeFrom(methodOptions);
                                        this.options_ = builder.buildPartial();
                                    }
                                    this.bitField0_ |= 8;
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
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_MethodDescriptorProto_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_MethodDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MethodDescriptorProto.class, Builder.class);
        }

        static {
            MethodDescriptorProto methodDescriptorProto = new MethodDescriptorProto(true);
            defaultInstance = methodDescriptorProto;
            methodDescriptorProto.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<MethodDescriptorProto> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
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

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
        public ByteString getNameBytes() {
            Object obj = this.name_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.name_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
        public boolean hasInputType() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
        public String getInputType() {
            Object obj = this.inputType_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.inputType_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
        public ByteString getInputTypeBytes() {
            Object obj = this.inputType_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.inputType_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
        public boolean hasOutputType() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
        public String getOutputType() {
            Object obj = this.outputType_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.outputType_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
        public ByteString getOutputTypeBytes() {
            Object obj = this.outputType_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.outputType_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
        public boolean hasOptions() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
        public MethodOptions getOptions() {
            return this.options_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
        public MethodOptionsOrBuilder getOptionsOrBuilder() {
            return this.options_;
        }

        private void initFields() {
            this.name_ = "";
            this.inputType_ = "";
            this.outputType_ = "";
            this.options_ = MethodOptions.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            if (hasOptions() && !getOptions().isInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBytes(1, getNameBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeBytes(2, getInputTypeBytes());
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeBytes(3, getOutputTypeBytes());
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeMessage(4, this.options_);
            }
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBytesSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBytesSize(1, getNameBytes()) : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeBytesSize += CodedOutputStream.computeBytesSize(2, getInputTypeBytes());
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeBytesSize += CodedOutputStream.computeBytesSize(3, getOutputTypeBytes());
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(4, this.options_);
            }
            int serializedSize = iComputeBytesSize + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static MethodDescriptorProto parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static MethodDescriptorProto parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static MethodDescriptorProto parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static MethodDescriptorProto parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static MethodDescriptorProto parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static MethodDescriptorProto parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static MethodDescriptorProto parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static MethodDescriptorProto parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static MethodDescriptorProto parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static MethodDescriptorProto parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(MethodDescriptorProto methodDescriptorProto) {
            return newBuilder().mergeFrom(methodDescriptorProto);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.Builder<Builder> implements MethodDescriptorProtoOrBuilder {
            private int bitField0_;
            private Object inputType_;
            private Object name_;
            private SingleFieldBuilder<MethodOptions, MethodOptions.Builder, MethodOptionsOrBuilder> optionsBuilder_;
            private MethodOptions options_;
            private Object outputType_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_MethodDescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_MethodDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MethodDescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                this.inputType_ = "";
                this.outputType_ = "";
                this.options_ = MethodOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.name_ = "";
                this.inputType_ = "";
                this.outputType_ = "";
                this.options_ = MethodOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getOptionsFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.name_ = "";
                int i = this.bitField0_;
                this.inputType_ = "";
                this.outputType_ = "";
                this.bitField0_ = i & (-8);
                SingleFieldBuilder<MethodOptions, MethodOptions.Builder, MethodOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = MethodOptions.getDefaultInstance();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -9;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_MethodDescriptorProto_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public MethodDescriptorProto getDefaultInstanceForType() {
                return MethodDescriptorProto.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public MethodDescriptorProto build() {
                MethodDescriptorProto methodDescriptorProtoBuildPartial = buildPartial();
                if (methodDescriptorProtoBuildPartial.isInitialized()) {
                    return methodDescriptorProtoBuildPartial;
                }
                throw newUninitializedMessageException((Message) methodDescriptorProtoBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public MethodDescriptorProto buildPartial() {
                MethodDescriptorProto methodDescriptorProto = new MethodDescriptorProto(this);
                int i = this.bitField0_;
                int i2 = (i & 1) != 1 ? 0 : 1;
                methodDescriptorProto.name_ = this.name_;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                methodDescriptorProto.inputType_ = this.inputType_;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                methodDescriptorProto.outputType_ = this.outputType_;
                if ((i & 8) == 8) {
                    i2 |= 8;
                }
                SingleFieldBuilder<MethodOptions, MethodOptions.Builder, MethodOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    methodDescriptorProto.options_ = this.options_;
                } else {
                    methodDescriptorProto.options_ = (MethodOptions) singleFieldBuilder.build();
                }
                methodDescriptorProto.bitField0_ = i2;
                onBuilt();
                return methodDescriptorProto;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof MethodDescriptorProto) {
                    return mergeFrom((MethodDescriptorProto) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(MethodDescriptorProto methodDescriptorProto) {
                if (methodDescriptorProto == MethodDescriptorProto.getDefaultInstance()) {
                    return this;
                }
                if (methodDescriptorProto.hasName()) {
                    this.bitField0_ |= 1;
                    this.name_ = methodDescriptorProto.name_;
                    onChanged();
                }
                if (methodDescriptorProto.hasInputType()) {
                    this.bitField0_ |= 2;
                    this.inputType_ = methodDescriptorProto.inputType_;
                    onChanged();
                }
                if (methodDescriptorProto.hasOutputType()) {
                    this.bitField0_ |= 4;
                    this.outputType_ = methodDescriptorProto.outputType_;
                    onChanged();
                }
                if (methodDescriptorProto.hasOptions()) {
                    mergeOptions(methodDescriptorProto.getOptions());
                }
                mergeUnknownFields(methodDescriptorProto.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return !hasOptions() || getOptions().isInitialized();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                MethodDescriptorProto methodDescriptorProto = null;
                try {
                    try {
                        MethodDescriptorProto partialFrom = MethodDescriptorProto.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        MethodDescriptorProto methodDescriptorProto2 = (MethodDescriptorProto) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            methodDescriptorProto = methodDescriptorProto2;
                            if (methodDescriptorProto != null) {
                                mergeFrom(methodDescriptorProto);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (methodDescriptorProto != null) {
                        mergeFrom(methodDescriptorProto);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
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

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
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
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = MethodDescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 1;
                this.name_ = byteString;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
            public boolean hasInputType() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
            public String getInputType() {
                Object obj = this.inputType_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.inputType_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
            public ByteString getInputTypeBytes() {
                Object obj = this.inputType_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.inputType_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setInputType(String str) {
                str.getClass();
                this.bitField0_ |= 2;
                this.inputType_ = str;
                onChanged();
                return this;
            }

            public Builder clearInputType() {
                this.bitField0_ &= -3;
                this.inputType_ = MethodDescriptorProto.getDefaultInstance().getInputType();
                onChanged();
                return this;
            }

            public Builder setInputTypeBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 2;
                this.inputType_ = byteString;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
            public boolean hasOutputType() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
            public String getOutputType() {
                Object obj = this.outputType_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.outputType_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
            public ByteString getOutputTypeBytes() {
                Object obj = this.outputType_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.outputType_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setOutputType(String str) {
                str.getClass();
                this.bitField0_ |= 4;
                this.outputType_ = str;
                onChanged();
                return this;
            }

            public Builder clearOutputType() {
                this.bitField0_ &= -5;
                this.outputType_ = MethodDescriptorProto.getDefaultInstance().getOutputType();
                onChanged();
                return this;
            }

            public Builder setOutputTypeBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 4;
                this.outputType_ = byteString;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
            public boolean hasOptions() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
            public MethodOptions getOptions() {
                SingleFieldBuilder<MethodOptions, MethodOptions.Builder, MethodOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    return this.options_;
                }
                return (MethodOptions) singleFieldBuilder.getMessage();
            }

            public Builder setOptions(MethodOptions methodOptions) {
                SingleFieldBuilder<MethodOptions, MethodOptions.Builder, MethodOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    methodOptions.getClass();
                    this.options_ = methodOptions;
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(methodOptions);
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setOptions(MethodOptions.Builder builder) {
                SingleFieldBuilder<MethodOptions, MethodOptions.Builder, MethodOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = builder.build();
                    onChanged();
                } else {
                    singleFieldBuilder.setMessage(builder.build());
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeOptions(MethodOptions methodOptions) {
                SingleFieldBuilder<MethodOptions, MethodOptions.Builder, MethodOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    if ((this.bitField0_ & 8) == 8 && this.options_ != MethodOptions.getDefaultInstance()) {
                        this.options_ = MethodOptions.newBuilder(this.options_).mergeFrom(methodOptions).buildPartial();
                    } else {
                        this.options_ = methodOptions;
                    }
                    onChanged();
                } else {
                    singleFieldBuilder.mergeFrom(methodOptions);
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder clearOptions() {
                SingleFieldBuilder<MethodOptions, MethodOptions.Builder, MethodOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder == null) {
                    this.options_ = MethodOptions.getDefaultInstance();
                    onChanged();
                } else {
                    singleFieldBuilder.clear();
                }
                this.bitField0_ &= -9;
                return this;
            }

            public MethodOptions.Builder getOptionsBuilder() {
                this.bitField0_ |= 8;
                onChanged();
                return (MethodOptions.Builder) getOptionsFieldBuilder().getBuilder();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodDescriptorProtoOrBuilder
            public MethodOptionsOrBuilder getOptionsOrBuilder() {
                SingleFieldBuilder<MethodOptions, MethodOptions.Builder, MethodOptionsOrBuilder> singleFieldBuilder = this.optionsBuilder_;
                if (singleFieldBuilder != null) {
                    return (MethodOptionsOrBuilder) singleFieldBuilder.getMessageOrBuilder();
                }
                return this.options_;
            }

            private SingleFieldBuilder<MethodOptions, MethodOptions.Builder, MethodOptionsOrBuilder> getOptionsFieldBuilder() {
                if (this.optionsBuilder_ == null) {
                    this.optionsBuilder_ = new SingleFieldBuilder<>(getOptions(), getParentForChildren(), isClean());
                    this.options_ = null;
                }
                return this.optionsBuilder_;
            }
        }
    }

    public static final class FileOptions extends GeneratedMessage.ExtendableMessage<FileOptions> implements FileOptionsOrBuilder {
        public static final int CC_GENERIC_SERVICES_FIELD_NUMBER = 16;
        public static final int DEPRECATED_FIELD_NUMBER = 23;
        public static final int GO_PACKAGE_FIELD_NUMBER = 11;
        public static final int JAVA_GENERATE_EQUALS_AND_HASH_FIELD_NUMBER = 20;
        public static final int JAVA_GENERIC_SERVICES_FIELD_NUMBER = 17;
        public static final int JAVA_MULTIPLE_FILES_FIELD_NUMBER = 10;
        public static final int JAVA_OUTER_CLASSNAME_FIELD_NUMBER = 8;
        public static final int JAVA_PACKAGE_FIELD_NUMBER = 1;
        public static final int JAVA_STRING_CHECK_UTF8_FIELD_NUMBER = 27;
        public static final int OPTIMIZE_FOR_FIELD_NUMBER = 9;
        public static Parser<FileOptions> PARSER = new AbstractParser<FileOptions>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptions.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public FileOptions parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new FileOptions(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int PY_GENERIC_SERVICES_FIELD_NUMBER = 18;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private static final FileOptions defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private boolean ccGenericServices_;
        private boolean deprecated_;
        private Object goPackage_;
        private boolean javaGenerateEqualsAndHash_;
        private boolean javaGenericServices_;
        private boolean javaMultipleFiles_;
        private Object javaOuterClassname_;
        private Object javaPackage_;
        private boolean javaStringCheckUtf8_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private OptimizeMode optimizeFor_;
        private boolean pyGenericServices_;
        private List<UninterpretedOption> uninterpretedOption_;
        private final UnknownFieldSet unknownFields;

        private FileOptions(GeneratedMessage.ExtendableBuilder<FileOptions, ?> extendableBuilder) {
            super(extendableBuilder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private FileOptions(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static FileOptions getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public FileOptions getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileOptions(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            char c = 0;
            while (!z) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            switch (tag) {
                                case 0:
                                    break;
                                case 10:
                                    ByteString bytes = codedInputStream.readBytes();
                                    this.bitField0_ = 1 | this.bitField0_;
                                    this.javaPackage_ = bytes;
                                    continue;
                                case 66:
                                    ByteString bytes2 = codedInputStream.readBytes();
                                    this.bitField0_ |= 2;
                                    this.javaOuterClassname_ = bytes2;
                                    continue;
                                case 72:
                                    int i = codedInputStream.readEnum();
                                    OptimizeMode optimizeModeValueOf = OptimizeMode.valueOf(i);
                                    if (optimizeModeValueOf == null) {
                                        builderNewBuilder.mergeVarintField(9, i);
                                    } else {
                                        this.bitField0_ |= 32;
                                        this.optimizeFor_ = optimizeModeValueOf;
                                        continue;
                                    }
                                    break;
                                case 80:
                                    this.bitField0_ |= 4;
                                    this.javaMultipleFiles_ = codedInputStream.readBool();
                                    continue;
                                case 90:
                                    ByteString bytes3 = codedInputStream.readBytes();
                                    this.bitField0_ |= 64;
                                    this.goPackage_ = bytes3;
                                    continue;
                                case 128:
                                    this.bitField0_ |= 128;
                                    this.ccGenericServices_ = codedInputStream.readBool();
                                    continue;
                                case 136:
                                    this.bitField0_ |= 256;
                                    this.javaGenericServices_ = codedInputStream.readBool();
                                    continue;
                                case 144:
                                    this.bitField0_ |= 512;
                                    this.pyGenericServices_ = codedInputStream.readBool();
                                    continue;
                                case 160:
                                    this.bitField0_ |= 8;
                                    this.javaGenerateEqualsAndHash_ = codedInputStream.readBool();
                                    continue;
                                case 184:
                                    this.bitField0_ |= 1024;
                                    this.deprecated_ = codedInputStream.readBool();
                                    continue;
                                case 216:
                                    this.bitField0_ |= 16;
                                    this.javaStringCheckUtf8_ = codedInputStream.readBool();
                                    continue;
                                case 7994:
                                    if ((c & 2048) != 2048) {
                                        this.uninterpretedOption_ = new ArrayList();
                                        c = 2048;
                                    }
                                    this.uninterpretedOption_.add((UninterpretedOption) codedInputStream.readMessage(UninterpretedOption.PARSER, extensionRegistryLite));
                                    continue;
                                default:
                                    if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                        break;
                                    }
                                    break;
                            }
                            z = true;
                        } catch (IOException e) {
                            throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(this);
                        }
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if ((c & 2048) == 2048) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((c & 2048) == 2048) {
                this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_FileOptions_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_FileOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(FileOptions.class, Builder.class);
        }

        static {
            FileOptions fileOptions = new FileOptions(true);
            defaultInstance = fileOptions;
            fileOptions.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<FileOptions> getParserForType() {
            return PARSER;
        }

        public enum OptimizeMode implements ProtocolMessageEnum {
            SPEED(0, 1),
            CODE_SIZE(1, 2),
            LITE_RUNTIME(2, 3);

            public static final int CODE_SIZE_VALUE = 2;
            public static final int LITE_RUNTIME_VALUE = 3;
            public static final int SPEED_VALUE = 1;
            private final int index;
            private final int value;
            private static Internal.EnumLiteMap<OptimizeMode> internalValueMap = new Internal.EnumLiteMap<OptimizeMode>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptions.OptimizeMode.1
                @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLiteMap
                public OptimizeMode findValueByNumber(int i) {
                    return OptimizeMode.valueOf(i);
                }
            };
            private static final OptimizeMode[] VALUES = values();

            @Override // org.jetbrains.kotlin.protobuf.ProtocolMessageEnum, org.jetbrains.kotlin.protobuf.Internal.EnumLite
            public final int getNumber() {
                return this.value;
            }

            public static OptimizeMode valueOf(int i) {
                if (i == 1) {
                    return SPEED;
                }
                if (i == 2) {
                    return CODE_SIZE;
                }
                if (i != 3) {
                    return null;
                }
                return LITE_RUNTIME;
            }

            public static Internal.EnumLiteMap<OptimizeMode> internalGetValueMap() {
                return internalValueMap;
            }

            @Override // org.jetbrains.kotlin.protobuf.ProtocolMessageEnum
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.index);
            }

            @Override // org.jetbrains.kotlin.protobuf.ProtocolMessageEnum
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return FileOptions.getDescriptor().getEnumTypes().get(0);
            }

            public static OptimizeMode valueOf(Descriptors.EnumValueDescriptor enumValueDescriptor) {
                if (enumValueDescriptor.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return VALUES[enumValueDescriptor.getIndex()];
            }

            OptimizeMode(int i, int i2) {
                this.index = i;
                this.value = i2;
            }
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean hasJavaPackage() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public String getJavaPackage() {
            Object obj = this.javaPackage_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.javaPackage_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public ByteString getJavaPackageBytes() {
            Object obj = this.javaPackage_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.javaPackage_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean hasJavaOuterClassname() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public String getJavaOuterClassname() {
            Object obj = this.javaOuterClassname_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.javaOuterClassname_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public ByteString getJavaOuterClassnameBytes() {
            Object obj = this.javaOuterClassname_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.javaOuterClassname_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean hasJavaMultipleFiles() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean getJavaMultipleFiles() {
            return this.javaMultipleFiles_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean hasJavaGenerateEqualsAndHash() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean getJavaGenerateEqualsAndHash() {
            return this.javaGenerateEqualsAndHash_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean hasJavaStringCheckUtf8() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean getJavaStringCheckUtf8() {
            return this.javaStringCheckUtf8_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean hasOptimizeFor() {
            return (this.bitField0_ & 32) == 32;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public OptimizeMode getOptimizeFor() {
            return this.optimizeFor_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean hasGoPackage() {
            return (this.bitField0_ & 64) == 64;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public String getGoPackage() {
            Object obj = this.goPackage_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.goPackage_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public ByteString getGoPackageBytes() {
            Object obj = this.goPackage_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.goPackage_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean hasCcGenericServices() {
            return (this.bitField0_ & 128) == 128;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean getCcGenericServices() {
            return this.ccGenericServices_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean hasJavaGenericServices() {
            return (this.bitField0_ & 256) == 256;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean getJavaGenericServices() {
            return this.javaGenericServices_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean hasPyGenericServices() {
            return (this.bitField0_ & 512) == 512;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean getPyGenericServices() {
            return this.pyGenericServices_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean hasDeprecated() {
            return (this.bitField0_ & 1024) == 1024;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public boolean getDeprecated() {
            return this.deprecated_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public UninterpretedOption getUninterpretedOption(int i) {
            return this.uninterpretedOption_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i) {
            return this.uninterpretedOption_.get(i);
        }

        private void initFields() {
            this.javaPackage_ = "";
            this.javaOuterClassname_ = "";
            this.javaMultipleFiles_ = false;
            this.javaGenerateEqualsAndHash_ = false;
            this.javaStringCheckUtf8_ = false;
            this.optimizeFor_ = OptimizeMode.SPEED;
            this.goPackage_ = "";
            this.ccGenericServices_ = false;
            this.javaGenericServices_ = false;
            this.pyGenericServices_ = false;
            this.deprecated_ = false;
            this.uninterpretedOption_ = Collections.emptyList();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessage, org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                if (!getUninterpretedOption(i).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (!extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessage.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBytes(1, getJavaPackageBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeBytes(8, getJavaOuterClassnameBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                codedOutputStream.writeEnum(9, this.optimizeFor_.getNumber());
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeBool(10, this.javaMultipleFiles_);
            }
            if ((this.bitField0_ & 64) == 64) {
                codedOutputStream.writeBytes(11, getGoPackageBytes());
            }
            if ((this.bitField0_ & 128) == 128) {
                codedOutputStream.writeBool(16, this.ccGenericServices_);
            }
            if ((this.bitField0_ & 256) == 256) {
                codedOutputStream.writeBool(17, this.javaGenericServices_);
            }
            if ((this.bitField0_ & 512) == 512) {
                codedOutputStream.writeBool(18, this.pyGenericServices_);
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeBool(20, this.javaGenerateEqualsAndHash_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                codedOutputStream.writeBool(23, this.deprecated_);
            }
            if ((this.bitField0_ & 16) == 16) {
                codedOutputStream.writeBool(27, this.javaStringCheckUtf8_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                codedOutputStream.writeMessage(999, this.uninterpretedOption_.get(i));
            }
            extensionWriterNewExtensionWriter.writeUntil(536870912, codedOutputStream);
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBytesSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBytesSize(1, getJavaPackageBytes()) : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeBytesSize += CodedOutputStream.computeBytesSize(8, getJavaOuterClassnameBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                iComputeBytesSize += CodedOutputStream.computeEnumSize(9, this.optimizeFor_.getNumber());
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeBytesSize += CodedOutputStream.computeBoolSize(10, this.javaMultipleFiles_);
            }
            if ((this.bitField0_ & 64) == 64) {
                iComputeBytesSize += CodedOutputStream.computeBytesSize(11, getGoPackageBytes());
            }
            if ((this.bitField0_ & 128) == 128) {
                iComputeBytesSize += CodedOutputStream.computeBoolSize(16, this.ccGenericServices_);
            }
            if ((this.bitField0_ & 256) == 256) {
                iComputeBytesSize += CodedOutputStream.computeBoolSize(17, this.javaGenericServices_);
            }
            if ((this.bitField0_ & 512) == 512) {
                iComputeBytesSize += CodedOutputStream.computeBoolSize(18, this.pyGenericServices_);
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeBytesSize += CodedOutputStream.computeBoolSize(20, this.javaGenerateEqualsAndHash_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                iComputeBytesSize += CodedOutputStream.computeBoolSize(23, this.deprecated_);
            }
            if ((this.bitField0_ & 16) == 16) {
                iComputeBytesSize += CodedOutputStream.computeBoolSize(27, this.javaStringCheckUtf8_);
            }
            for (int i2 = 0; i2 < this.uninterpretedOption_.size(); i2++) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(999, this.uninterpretedOption_.get(i2));
            }
            int iExtensionsSerializedSize = iComputeBytesSize + extensionsSerializedSize() + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = iExtensionsSerializedSize;
            return iExtensionsSerializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static FileOptions parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static FileOptions parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static FileOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static FileOptions parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static FileOptions parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static FileOptions parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static FileOptions parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static FileOptions parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static FileOptions parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static FileOptions parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(FileOptions fileOptions) {
            return newBuilder().mergeFrom(fileOptions);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.ExtendableBuilder<FileOptions, Builder> implements FileOptionsOrBuilder {
            private int bitField0_;
            private boolean ccGenericServices_;
            private boolean deprecated_;
            private Object goPackage_;
            private boolean javaGenerateEqualsAndHash_;
            private boolean javaGenericServices_;
            private boolean javaMultipleFiles_;
            private Object javaOuterClassname_;
            private Object javaPackage_;
            private boolean javaStringCheckUtf8_;
            private OptimizeMode optimizeFor_;
            private boolean pyGenericServices_;
            private RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> uninterpretedOptionBuilder_;
            private List<UninterpretedOption> uninterpretedOption_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_FileOptions_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_FileOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(FileOptions.class, Builder.class);
            }

            private Builder() {
                this.javaPackage_ = "";
                this.javaOuterClassname_ = "";
                this.optimizeFor_ = OptimizeMode.SPEED;
                this.goPackage_ = "";
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.javaPackage_ = "";
                this.javaOuterClassname_ = "";
                this.optimizeFor_ = OptimizeMode.SPEED;
                this.goPackage_ = "";
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getUninterpretedOptionFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.javaPackage_ = "";
                int i = this.bitField0_;
                this.javaOuterClassname_ = "";
                this.javaMultipleFiles_ = false;
                this.javaGenerateEqualsAndHash_ = false;
                this.javaStringCheckUtf8_ = false;
                this.bitField0_ = i & (-32);
                this.optimizeFor_ = OptimizeMode.SPEED;
                int i2 = this.bitField0_;
                this.goPackage_ = "";
                this.ccGenericServices_ = false;
                this.javaGenericServices_ = false;
                this.pyGenericServices_ = false;
                this.deprecated_ = false;
                this.bitField0_ = i2 & (-2017);
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -2049;
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_FileOptions_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public FileOptions getDefaultInstanceForType() {
                return FileOptions.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public FileOptions build() {
                FileOptions fileOptionsBuildPartial = buildPartial();
                if (fileOptionsBuildPartial.isInitialized()) {
                    return fileOptionsBuildPartial;
                }
                throw newUninitializedMessageException((Message) fileOptionsBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public FileOptions buildPartial() {
                FileOptions fileOptions = new FileOptions(this);
                int i = this.bitField0_;
                int i2 = (i & 1) != 1 ? 0 : 1;
                fileOptions.javaPackage_ = this.javaPackage_;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                fileOptions.javaOuterClassname_ = this.javaOuterClassname_;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                fileOptions.javaMultipleFiles_ = this.javaMultipleFiles_;
                if ((i & 8) == 8) {
                    i2 |= 8;
                }
                fileOptions.javaGenerateEqualsAndHash_ = this.javaGenerateEqualsAndHash_;
                if ((i & 16) == 16) {
                    i2 |= 16;
                }
                fileOptions.javaStringCheckUtf8_ = this.javaStringCheckUtf8_;
                if ((i & 32) == 32) {
                    i2 |= 32;
                }
                fileOptions.optimizeFor_ = this.optimizeFor_;
                if ((i & 64) == 64) {
                    i2 |= 64;
                }
                fileOptions.goPackage_ = this.goPackage_;
                if ((i & 128) == 128) {
                    i2 |= 128;
                }
                fileOptions.ccGenericServices_ = this.ccGenericServices_;
                if ((i & 256) == 256) {
                    i2 |= 256;
                }
                fileOptions.javaGenericServices_ = this.javaGenericServices_;
                if ((i & 512) == 512) {
                    i2 |= 512;
                }
                fileOptions.pyGenericServices_ = this.pyGenericServices_;
                if ((i & 1024) == 1024) {
                    i2 |= 1024;
                }
                fileOptions.deprecated_ = this.deprecated_;
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder != null) {
                    fileOptions.uninterpretedOption_ = repeatedFieldBuilder.build();
                } else {
                    if ((this.bitField0_ & 2048) == 2048) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                        this.bitField0_ &= -2049;
                    }
                    fileOptions.uninterpretedOption_ = this.uninterpretedOption_;
                }
                fileOptions.bitField0_ = i2;
                onBuilt();
                return fileOptions;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof FileOptions) {
                    return mergeFrom((FileOptions) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(FileOptions fileOptions) {
                if (fileOptions == FileOptions.getDefaultInstance()) {
                    return this;
                }
                if (fileOptions.hasJavaPackage()) {
                    this.bitField0_ |= 1;
                    this.javaPackage_ = fileOptions.javaPackage_;
                    onChanged();
                }
                if (fileOptions.hasJavaOuterClassname()) {
                    this.bitField0_ |= 2;
                    this.javaOuterClassname_ = fileOptions.javaOuterClassname_;
                    onChanged();
                }
                if (fileOptions.hasJavaMultipleFiles()) {
                    setJavaMultipleFiles(fileOptions.getJavaMultipleFiles());
                }
                if (fileOptions.hasJavaGenerateEqualsAndHash()) {
                    setJavaGenerateEqualsAndHash(fileOptions.getJavaGenerateEqualsAndHash());
                }
                if (fileOptions.hasJavaStringCheckUtf8()) {
                    setJavaStringCheckUtf8(fileOptions.getJavaStringCheckUtf8());
                }
                if (fileOptions.hasOptimizeFor()) {
                    setOptimizeFor(fileOptions.getOptimizeFor());
                }
                if (fileOptions.hasGoPackage()) {
                    this.bitField0_ |= 64;
                    this.goPackage_ = fileOptions.goPackage_;
                    onChanged();
                }
                if (fileOptions.hasCcGenericServices()) {
                    setCcGenericServices(fileOptions.getCcGenericServices());
                }
                if (fileOptions.hasJavaGenericServices()) {
                    setJavaGenericServices(fileOptions.getJavaGenericServices());
                }
                if (fileOptions.hasPyGenericServices()) {
                    setPyGenericServices(fileOptions.getPyGenericServices());
                }
                if (fileOptions.hasDeprecated()) {
                    setDeprecated(fileOptions.getDeprecated());
                }
                if (this.uninterpretedOptionBuilder_ == null) {
                    if (!fileOptions.uninterpretedOption_.isEmpty()) {
                        if (this.uninterpretedOption_.isEmpty()) {
                            this.uninterpretedOption_ = fileOptions.uninterpretedOption_;
                            this.bitField0_ &= -2049;
                        } else {
                            ensureUninterpretedOptionIsMutable();
                            this.uninterpretedOption_.addAll(fileOptions.uninterpretedOption_);
                        }
                        onChanged();
                    }
                } else if (!fileOptions.uninterpretedOption_.isEmpty()) {
                    if (!this.uninterpretedOptionBuilder_.isEmpty()) {
                        this.uninterpretedOptionBuilder_.addAllMessages(fileOptions.uninterpretedOption_);
                    } else {
                        this.uninterpretedOptionBuilder_.dispose();
                        this.uninterpretedOptionBuilder_ = null;
                        this.uninterpretedOption_ = fileOptions.uninterpretedOption_;
                        this.bitField0_ &= -2049;
                        this.uninterpretedOptionBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getUninterpretedOptionFieldBuilder() : null;
                    }
                }
                mergeExtensionFields(fileOptions);
                mergeUnknownFields(fileOptions.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                    if (!getUninterpretedOption(i).isInitialized()) {
                        return false;
                    }
                }
                return extensionsAreInitialized();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                FileOptions fileOptions = null;
                try {
                    try {
                        FileOptions partialFrom = FileOptions.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        FileOptions fileOptions2 = (FileOptions) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            fileOptions = fileOptions2;
                            if (fileOptions != null) {
                                mergeFrom(fileOptions);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOptions != null) {
                        mergeFrom(fileOptions);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean hasJavaPackage() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public String getJavaPackage() {
                Object obj = this.javaPackage_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.javaPackage_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public ByteString getJavaPackageBytes() {
                Object obj = this.javaPackage_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.javaPackage_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setJavaPackage(String str) {
                str.getClass();
                this.bitField0_ |= 1;
                this.javaPackage_ = str;
                onChanged();
                return this;
            }

            public Builder clearJavaPackage() {
                this.bitField0_ &= -2;
                this.javaPackage_ = FileOptions.getDefaultInstance().getJavaPackage();
                onChanged();
                return this;
            }

            public Builder setJavaPackageBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 1;
                this.javaPackage_ = byteString;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean hasJavaOuterClassname() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public String getJavaOuterClassname() {
                Object obj = this.javaOuterClassname_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.javaOuterClassname_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public ByteString getJavaOuterClassnameBytes() {
                Object obj = this.javaOuterClassname_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.javaOuterClassname_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setJavaOuterClassname(String str) {
                str.getClass();
                this.bitField0_ |= 2;
                this.javaOuterClassname_ = str;
                onChanged();
                return this;
            }

            public Builder clearJavaOuterClassname() {
                this.bitField0_ &= -3;
                this.javaOuterClassname_ = FileOptions.getDefaultInstance().getJavaOuterClassname();
                onChanged();
                return this;
            }

            public Builder setJavaOuterClassnameBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 2;
                this.javaOuterClassname_ = byteString;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean hasJavaMultipleFiles() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean getJavaMultipleFiles() {
                return this.javaMultipleFiles_;
            }

            public Builder setJavaMultipleFiles(boolean z) {
                this.bitField0_ |= 4;
                this.javaMultipleFiles_ = z;
                onChanged();
                return this;
            }

            public Builder clearJavaMultipleFiles() {
                this.bitField0_ &= -5;
                this.javaMultipleFiles_ = false;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean hasJavaGenerateEqualsAndHash() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean getJavaGenerateEqualsAndHash() {
                return this.javaGenerateEqualsAndHash_;
            }

            public Builder setJavaGenerateEqualsAndHash(boolean z) {
                this.bitField0_ |= 8;
                this.javaGenerateEqualsAndHash_ = z;
                onChanged();
                return this;
            }

            public Builder clearJavaGenerateEqualsAndHash() {
                this.bitField0_ &= -9;
                this.javaGenerateEqualsAndHash_ = false;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean hasJavaStringCheckUtf8() {
                return (this.bitField0_ & 16) == 16;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean getJavaStringCheckUtf8() {
                return this.javaStringCheckUtf8_;
            }

            public Builder setJavaStringCheckUtf8(boolean z) {
                this.bitField0_ |= 16;
                this.javaStringCheckUtf8_ = z;
                onChanged();
                return this;
            }

            public Builder clearJavaStringCheckUtf8() {
                this.bitField0_ &= -17;
                this.javaStringCheckUtf8_ = false;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean hasOptimizeFor() {
                return (this.bitField0_ & 32) == 32;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public OptimizeMode getOptimizeFor() {
                return this.optimizeFor_;
            }

            public Builder setOptimizeFor(OptimizeMode optimizeMode) {
                optimizeMode.getClass();
                this.bitField0_ |= 32;
                this.optimizeFor_ = optimizeMode;
                onChanged();
                return this;
            }

            public Builder clearOptimizeFor() {
                this.bitField0_ &= -33;
                this.optimizeFor_ = OptimizeMode.SPEED;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean hasGoPackage() {
                return (this.bitField0_ & 64) == 64;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public String getGoPackage() {
                Object obj = this.goPackage_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.goPackage_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public ByteString getGoPackageBytes() {
                Object obj = this.goPackage_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.goPackage_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setGoPackage(String str) {
                str.getClass();
                this.bitField0_ |= 64;
                this.goPackage_ = str;
                onChanged();
                return this;
            }

            public Builder clearGoPackage() {
                this.bitField0_ &= -65;
                this.goPackage_ = FileOptions.getDefaultInstance().getGoPackage();
                onChanged();
                return this;
            }

            public Builder setGoPackageBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 64;
                this.goPackage_ = byteString;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean hasCcGenericServices() {
                return (this.bitField0_ & 128) == 128;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean getCcGenericServices() {
                return this.ccGenericServices_;
            }

            public Builder setCcGenericServices(boolean z) {
                this.bitField0_ |= 128;
                this.ccGenericServices_ = z;
                onChanged();
                return this;
            }

            public Builder clearCcGenericServices() {
                this.bitField0_ &= -129;
                this.ccGenericServices_ = false;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean hasJavaGenericServices() {
                return (this.bitField0_ & 256) == 256;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean getJavaGenericServices() {
                return this.javaGenericServices_;
            }

            public Builder setJavaGenericServices(boolean z) {
                this.bitField0_ |= 256;
                this.javaGenericServices_ = z;
                onChanged();
                return this;
            }

            public Builder clearJavaGenericServices() {
                this.bitField0_ &= -257;
                this.javaGenericServices_ = false;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean hasPyGenericServices() {
                return (this.bitField0_ & 512) == 512;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean getPyGenericServices() {
                return this.pyGenericServices_;
            }

            public Builder setPyGenericServices(boolean z) {
                this.bitField0_ |= 512;
                this.pyGenericServices_ = z;
                onChanged();
                return this;
            }

            public Builder clearPyGenericServices() {
                this.bitField0_ &= -513;
                this.pyGenericServices_ = false;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean hasDeprecated() {
                return (this.bitField0_ & 1024) == 1024;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public boolean getDeprecated() {
                return this.deprecated_;
            }

            public Builder setDeprecated(boolean z) {
                this.bitField0_ |= 1024;
                this.deprecated_ = z;
                onChanged();
                return this;
            }

            public Builder clearDeprecated() {
                this.bitField0_ &= -1025;
                this.deprecated_ = false;
                onChanged();
                return this;
            }

            private void ensureUninterpretedOptionIsMutable() {
                if ((this.bitField0_ & 2048) != 2048) {
                    this.uninterpretedOption_ = new ArrayList(this.uninterpretedOption_);
                    this.bitField0_ |= 2048;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public List<UninterpretedOption> getUninterpretedOptionList() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.uninterpretedOption_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public int getUninterpretedOptionCount() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public UninterpretedOption getUninterpretedOption(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.get(i);
                }
                return (UninterpretedOption) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setUninterpretedOption(int i, UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(i, uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, uninterpretedOption);
                }
                return this;
            }

            public Builder setUninterpretedOption(int i, UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(uninterpretedOption);
                }
                return this;
            }

            public Builder addUninterpretedOption(int i, UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(i, uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, uninterpretedOption);
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(int i, UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> iterable) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.uninterpretedOption_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearUninterpretedOption() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -2049;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeUninterpretedOption(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public UninterpretedOption.Builder getUninterpretedOptionBuilder(int i) {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.get(i);
                }
                return (UninterpretedOptionOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FileOptionsOrBuilder
            public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.uninterpretedOption_);
            }

            public UninterpretedOption.Builder addUninterpretedOptionBuilder() {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().addBuilder(UninterpretedOption.getDefaultInstance());
            }

            public UninterpretedOption.Builder addUninterpretedOptionBuilder(int i) {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().addBuilder(i, UninterpretedOption.getDefaultInstance());
            }

            public List<UninterpretedOption.Builder> getUninterpretedOptionBuilderList() {
                return getUninterpretedOptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> getUninterpretedOptionFieldBuilder() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOptionBuilder_ = new RepeatedFieldBuilder<>(this.uninterpretedOption_, (this.bitField0_ & 2048) == 2048, getParentForChildren(), isClean());
                    this.uninterpretedOption_ = null;
                }
                return this.uninterpretedOptionBuilder_;
            }
        }
    }

    public static final class MessageOptions extends GeneratedMessage.ExtendableMessage<MessageOptions> implements MessageOptionsOrBuilder {
        public static final int DEPRECATED_FIELD_NUMBER = 3;
        public static final int MESSAGE_SET_WIRE_FORMAT_FIELD_NUMBER = 1;
        public static final int NO_STANDARD_DESCRIPTOR_ACCESSOR_FIELD_NUMBER = 2;
        public static Parser<MessageOptions> PARSER = new AbstractParser<MessageOptions>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptions.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public MessageOptions parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new MessageOptions(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private static final MessageOptions defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private boolean deprecated_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private boolean messageSetWireFormat_;
        private boolean noStandardDescriptorAccessor_;
        private List<UninterpretedOption> uninterpretedOption_;
        private final UnknownFieldSet unknownFields;

        private MessageOptions(GeneratedMessage.ExtendableBuilder<MessageOptions, ?> extendableBuilder) {
            super(extendableBuilder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private MessageOptions(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static MessageOptions getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public MessageOptions getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MessageOptions(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
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
                                    this.messageSetWireFormat_ = codedInputStream.readBool();
                                } else if (tag == 16) {
                                    this.bitField0_ |= 2;
                                    this.noStandardDescriptorAccessor_ = codedInputStream.readBool();
                                } else if (tag == 24) {
                                    this.bitField0_ |= 4;
                                    this.deprecated_ = codedInputStream.readBool();
                                } else if (tag != 7994) {
                                    if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    if ((c & '\b') != 8) {
                                        this.uninterpretedOption_ = new ArrayList();
                                        c = '\b';
                                    }
                                    this.uninterpretedOption_.add((UninterpretedOption) codedInputStream.readMessage(UninterpretedOption.PARSER, extensionRegistryLite));
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
                    if ((c & '\b') == 8) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((c & '\b') == 8) {
                this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_MessageOptions_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_MessageOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(MessageOptions.class, Builder.class);
        }

        static {
            MessageOptions messageOptions = new MessageOptions(true);
            defaultInstance = messageOptions;
            messageOptions.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<MessageOptions> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
        public boolean hasMessageSetWireFormat() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
        public boolean getMessageSetWireFormat() {
            return this.messageSetWireFormat_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
        public boolean hasNoStandardDescriptorAccessor() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
        public boolean getNoStandardDescriptorAccessor() {
            return this.noStandardDescriptorAccessor_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
        public boolean hasDeprecated() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
        public boolean getDeprecated() {
            return this.deprecated_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
        public UninterpretedOption getUninterpretedOption(int i) {
            return this.uninterpretedOption_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i) {
            return this.uninterpretedOption_.get(i);
        }

        private void initFields() {
            this.messageSetWireFormat_ = false;
            this.noStandardDescriptorAccessor_ = false;
            this.deprecated_ = false;
            this.uninterpretedOption_ = Collections.emptyList();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessage, org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                if (!getUninterpretedOption(i).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (!extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessage.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBool(1, this.messageSetWireFormat_);
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeBool(2, this.noStandardDescriptorAccessor_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeBool(3, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                codedOutputStream.writeMessage(999, this.uninterpretedOption_.get(i));
            }
            extensionWriterNewExtensionWriter.writeUntil(536870912, codedOutputStream);
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBoolSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBoolSize(1, this.messageSetWireFormat_) : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeBoolSize += CodedOutputStream.computeBoolSize(2, this.noStandardDescriptorAccessor_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeBoolSize += CodedOutputStream.computeBoolSize(3, this.deprecated_);
            }
            for (int i2 = 0; i2 < this.uninterpretedOption_.size(); i2++) {
                iComputeBoolSize += CodedOutputStream.computeMessageSize(999, this.uninterpretedOption_.get(i2));
            }
            int iExtensionsSerializedSize = iComputeBoolSize + extensionsSerializedSize() + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = iExtensionsSerializedSize;
            return iExtensionsSerializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static MessageOptions parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static MessageOptions parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static MessageOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static MessageOptions parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static MessageOptions parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static MessageOptions parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static MessageOptions parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static MessageOptions parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static MessageOptions parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static MessageOptions parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(MessageOptions messageOptions) {
            return newBuilder().mergeFrom(messageOptions);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.ExtendableBuilder<MessageOptions, Builder> implements MessageOptionsOrBuilder {
            private int bitField0_;
            private boolean deprecated_;
            private boolean messageSetWireFormat_;
            private boolean noStandardDescriptorAccessor_;
            private RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> uninterpretedOptionBuilder_;
            private List<UninterpretedOption> uninterpretedOption_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_MessageOptions_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_MessageOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(MessageOptions.class, Builder.class);
            }

            private Builder() {
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getUninterpretedOptionFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.messageSetWireFormat_ = false;
                int i = this.bitField0_;
                this.noStandardDescriptorAccessor_ = false;
                this.deprecated_ = false;
                this.bitField0_ = i & (-8);
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -9;
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_MessageOptions_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public MessageOptions getDefaultInstanceForType() {
                return MessageOptions.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public MessageOptions build() {
                MessageOptions messageOptionsBuildPartial = buildPartial();
                if (messageOptionsBuildPartial.isInitialized()) {
                    return messageOptionsBuildPartial;
                }
                throw newUninitializedMessageException((Message) messageOptionsBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public MessageOptions buildPartial() {
                MessageOptions messageOptions = new MessageOptions(this);
                int i = this.bitField0_;
                int i2 = (i & 1) != 1 ? 0 : 1;
                messageOptions.messageSetWireFormat_ = this.messageSetWireFormat_;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                messageOptions.noStandardDescriptorAccessor_ = this.noStandardDescriptorAccessor_;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                messageOptions.deprecated_ = this.deprecated_;
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder != null) {
                    messageOptions.uninterpretedOption_ = repeatedFieldBuilder.build();
                } else {
                    if ((this.bitField0_ & 8) == 8) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                        this.bitField0_ &= -9;
                    }
                    messageOptions.uninterpretedOption_ = this.uninterpretedOption_;
                }
                messageOptions.bitField0_ = i2;
                onBuilt();
                return messageOptions;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof MessageOptions) {
                    return mergeFrom((MessageOptions) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(MessageOptions messageOptions) {
                if (messageOptions == MessageOptions.getDefaultInstance()) {
                    return this;
                }
                if (messageOptions.hasMessageSetWireFormat()) {
                    setMessageSetWireFormat(messageOptions.getMessageSetWireFormat());
                }
                if (messageOptions.hasNoStandardDescriptorAccessor()) {
                    setNoStandardDescriptorAccessor(messageOptions.getNoStandardDescriptorAccessor());
                }
                if (messageOptions.hasDeprecated()) {
                    setDeprecated(messageOptions.getDeprecated());
                }
                if (this.uninterpretedOptionBuilder_ == null) {
                    if (!messageOptions.uninterpretedOption_.isEmpty()) {
                        if (this.uninterpretedOption_.isEmpty()) {
                            this.uninterpretedOption_ = messageOptions.uninterpretedOption_;
                            this.bitField0_ &= -9;
                        } else {
                            ensureUninterpretedOptionIsMutable();
                            this.uninterpretedOption_.addAll(messageOptions.uninterpretedOption_);
                        }
                        onChanged();
                    }
                } else if (!messageOptions.uninterpretedOption_.isEmpty()) {
                    if (!this.uninterpretedOptionBuilder_.isEmpty()) {
                        this.uninterpretedOptionBuilder_.addAllMessages(messageOptions.uninterpretedOption_);
                    } else {
                        this.uninterpretedOptionBuilder_.dispose();
                        this.uninterpretedOptionBuilder_ = null;
                        this.uninterpretedOption_ = messageOptions.uninterpretedOption_;
                        this.bitField0_ &= -9;
                        this.uninterpretedOptionBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getUninterpretedOptionFieldBuilder() : null;
                    }
                }
                mergeExtensionFields(messageOptions);
                mergeUnknownFields(messageOptions.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                    if (!getUninterpretedOption(i).isInitialized()) {
                        return false;
                    }
                }
                return extensionsAreInitialized();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                MessageOptions messageOptions = null;
                try {
                    try {
                        MessageOptions partialFrom = MessageOptions.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        MessageOptions messageOptions2 = (MessageOptions) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            messageOptions = messageOptions2;
                            if (messageOptions != null) {
                                mergeFrom(messageOptions);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (messageOptions != null) {
                        mergeFrom(messageOptions);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
            public boolean hasMessageSetWireFormat() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
            public boolean getMessageSetWireFormat() {
                return this.messageSetWireFormat_;
            }

            public Builder setMessageSetWireFormat(boolean z) {
                this.bitField0_ |= 1;
                this.messageSetWireFormat_ = z;
                onChanged();
                return this;
            }

            public Builder clearMessageSetWireFormat() {
                this.bitField0_ &= -2;
                this.messageSetWireFormat_ = false;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
            public boolean hasNoStandardDescriptorAccessor() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
            public boolean getNoStandardDescriptorAccessor() {
                return this.noStandardDescriptorAccessor_;
            }

            public Builder setNoStandardDescriptorAccessor(boolean z) {
                this.bitField0_ |= 2;
                this.noStandardDescriptorAccessor_ = z;
                onChanged();
                return this;
            }

            public Builder clearNoStandardDescriptorAccessor() {
                this.bitField0_ &= -3;
                this.noStandardDescriptorAccessor_ = false;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
            public boolean hasDeprecated() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
            public boolean getDeprecated() {
                return this.deprecated_;
            }

            public Builder setDeprecated(boolean z) {
                this.bitField0_ |= 4;
                this.deprecated_ = z;
                onChanged();
                return this;
            }

            public Builder clearDeprecated() {
                this.bitField0_ &= -5;
                this.deprecated_ = false;
                onChanged();
                return this;
            }

            private void ensureUninterpretedOptionIsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.uninterpretedOption_ = new ArrayList(this.uninterpretedOption_);
                    this.bitField0_ |= 8;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
            public List<UninterpretedOption> getUninterpretedOptionList() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.uninterpretedOption_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
            public int getUninterpretedOptionCount() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
            public UninterpretedOption getUninterpretedOption(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.get(i);
                }
                return (UninterpretedOption) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setUninterpretedOption(int i, UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(i, uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, uninterpretedOption);
                }
                return this;
            }

            public Builder setUninterpretedOption(int i, UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(uninterpretedOption);
                }
                return this;
            }

            public Builder addUninterpretedOption(int i, UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(i, uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, uninterpretedOption);
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(int i, UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> iterable) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.uninterpretedOption_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearUninterpretedOption() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -9;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeUninterpretedOption(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public UninterpretedOption.Builder getUninterpretedOptionBuilder(int i) {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
            public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.get(i);
                }
                return (UninterpretedOptionOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MessageOptionsOrBuilder
            public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.uninterpretedOption_);
            }

            public UninterpretedOption.Builder addUninterpretedOptionBuilder() {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().addBuilder(UninterpretedOption.getDefaultInstance());
            }

            public UninterpretedOption.Builder addUninterpretedOptionBuilder(int i) {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().addBuilder(i, UninterpretedOption.getDefaultInstance());
            }

            public List<UninterpretedOption.Builder> getUninterpretedOptionBuilderList() {
                return getUninterpretedOptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> getUninterpretedOptionFieldBuilder() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOptionBuilder_ = new RepeatedFieldBuilder<>(this.uninterpretedOption_, (this.bitField0_ & 8) == 8, getParentForChildren(), isClean());
                    this.uninterpretedOption_ = null;
                }
                return this.uninterpretedOptionBuilder_;
            }
        }
    }

    public static final class FieldOptions extends GeneratedMessage.ExtendableMessage<FieldOptions> implements FieldOptionsOrBuilder {
        public static final int CTYPE_FIELD_NUMBER = 1;
        public static final int DEPRECATED_FIELD_NUMBER = 3;
        public static final int EXPERIMENTAL_MAP_KEY_FIELD_NUMBER = 9;
        public static final int LAZY_FIELD_NUMBER = 5;
        public static final int PACKED_FIELD_NUMBER = 2;
        public static Parser<FieldOptions> PARSER = new AbstractParser<FieldOptions>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptions.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public FieldOptions parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new FieldOptions(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        public static final int WEAK_FIELD_NUMBER = 10;
        private static final FieldOptions defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private CType ctype_;
        private boolean deprecated_;
        private Object experimentalMapKey_;
        private boolean lazy_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private boolean packed_;
        private List<UninterpretedOption> uninterpretedOption_;
        private final UnknownFieldSet unknownFields;
        private boolean weak_;

        private FieldOptions(GeneratedMessage.ExtendableBuilder<FieldOptions, ?> extendableBuilder) {
            super(extendableBuilder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private FieldOptions(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static FieldOptions getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public FieldOptions getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FieldOptions(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            char c = 0;
            while (!z) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 8) {
                                    int i = codedInputStream.readEnum();
                                    CType cTypeValueOf = CType.valueOf(i);
                                    if (cTypeValueOf == null) {
                                        builderNewBuilder.mergeVarintField(1, i);
                                    } else {
                                        this.bitField0_ |= 1;
                                        this.ctype_ = cTypeValueOf;
                                    }
                                } else if (tag == 16) {
                                    this.bitField0_ |= 2;
                                    this.packed_ = codedInputStream.readBool();
                                } else if (tag == 24) {
                                    this.bitField0_ |= 8;
                                    this.deprecated_ = codedInputStream.readBool();
                                } else if (tag == 40) {
                                    this.bitField0_ |= 4;
                                    this.lazy_ = codedInputStream.readBool();
                                } else if (tag == 74) {
                                    ByteString bytes = codedInputStream.readBytes();
                                    this.bitField0_ |= 16;
                                    this.experimentalMapKey_ = bytes;
                                } else if (tag == 80) {
                                    this.bitField0_ |= 32;
                                    this.weak_ = codedInputStream.readBool();
                                } else if (tag != 7994) {
                                    if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    if ((c & '@') != 64) {
                                        this.uninterpretedOption_ = new ArrayList();
                                        c = '@';
                                    }
                                    this.uninterpretedOption_.add((UninterpretedOption) codedInputStream.readMessage(UninterpretedOption.PARSER, extensionRegistryLite));
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
                    if ((c & '@') == 64) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((c & '@') == 64) {
                this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_FieldOptions_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_FieldOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldOptions.class, Builder.class);
        }

        static {
            FieldOptions fieldOptions = new FieldOptions(true);
            defaultInstance = fieldOptions;
            fieldOptions.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<FieldOptions> getParserForType() {
            return PARSER;
        }

        public enum CType implements ProtocolMessageEnum {
            STRING(0, 0),
            CORD(1, 1),
            STRING_PIECE(2, 2);

            public static final int CORD_VALUE = 1;
            public static final int STRING_PIECE_VALUE = 2;
            public static final int STRING_VALUE = 0;
            private final int index;
            private final int value;
            private static Internal.EnumLiteMap<CType> internalValueMap = new Internal.EnumLiteMap<CType>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptions.CType.1
                @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLiteMap
                public CType findValueByNumber(int i) {
                    return CType.valueOf(i);
                }
            };
            private static final CType[] VALUES = values();

            @Override // org.jetbrains.kotlin.protobuf.ProtocolMessageEnum, org.jetbrains.kotlin.protobuf.Internal.EnumLite
            public final int getNumber() {
                return this.value;
            }

            public static CType valueOf(int i) {
                if (i == 0) {
                    return STRING;
                }
                if (i == 1) {
                    return CORD;
                }
                if (i != 2) {
                    return null;
                }
                return STRING_PIECE;
            }

            public static Internal.EnumLiteMap<CType> internalGetValueMap() {
                return internalValueMap;
            }

            @Override // org.jetbrains.kotlin.protobuf.ProtocolMessageEnum
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.index);
            }

            @Override // org.jetbrains.kotlin.protobuf.ProtocolMessageEnum
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return FieldOptions.getDescriptor().getEnumTypes().get(0);
            }

            public static CType valueOf(Descriptors.EnumValueDescriptor enumValueDescriptor) {
                if (enumValueDescriptor.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return VALUES[enumValueDescriptor.getIndex()];
            }

            CType(int i, int i2) {
                this.index = i;
                this.value = i2;
            }
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public boolean hasCtype() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public CType getCtype() {
            return this.ctype_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public boolean hasPacked() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public boolean getPacked() {
            return this.packed_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public boolean hasLazy() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public boolean getLazy() {
            return this.lazy_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public boolean hasDeprecated() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public boolean getDeprecated() {
            return this.deprecated_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public boolean hasExperimentalMapKey() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public String getExperimentalMapKey() {
            Object obj = this.experimentalMapKey_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.experimentalMapKey_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public ByteString getExperimentalMapKeyBytes() {
            Object obj = this.experimentalMapKey_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.experimentalMapKey_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public boolean hasWeak() {
            return (this.bitField0_ & 32) == 32;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public boolean getWeak() {
            return this.weak_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public UninterpretedOption getUninterpretedOption(int i) {
            return this.uninterpretedOption_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i) {
            return this.uninterpretedOption_.get(i);
        }

        private void initFields() {
            this.ctype_ = CType.STRING;
            this.packed_ = false;
            this.lazy_ = false;
            this.deprecated_ = false;
            this.experimentalMapKey_ = "";
            this.weak_ = false;
            this.uninterpretedOption_ = Collections.emptyList();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessage, org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                if (!getUninterpretedOption(i).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (!extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessage.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeEnum(1, this.ctype_.getNumber());
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeBool(2, this.packed_);
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeBool(3, this.deprecated_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeBool(5, this.lazy_);
            }
            if ((this.bitField0_ & 16) == 16) {
                codedOutputStream.writeBytes(9, getExperimentalMapKeyBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                codedOutputStream.writeBool(10, this.weak_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                codedOutputStream.writeMessage(999, this.uninterpretedOption_.get(i));
            }
            extensionWriterNewExtensionWriter.writeUntil(536870912, codedOutputStream);
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeEnumSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeEnumSize(1, this.ctype_.getNumber()) : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeEnumSize += CodedOutputStream.computeBoolSize(2, this.packed_);
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeEnumSize += CodedOutputStream.computeBoolSize(3, this.deprecated_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeEnumSize += CodedOutputStream.computeBoolSize(5, this.lazy_);
            }
            if ((this.bitField0_ & 16) == 16) {
                iComputeEnumSize += CodedOutputStream.computeBytesSize(9, getExperimentalMapKeyBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                iComputeEnumSize += CodedOutputStream.computeBoolSize(10, this.weak_);
            }
            for (int i2 = 0; i2 < this.uninterpretedOption_.size(); i2++) {
                iComputeEnumSize += CodedOutputStream.computeMessageSize(999, this.uninterpretedOption_.get(i2));
            }
            int iExtensionsSerializedSize = iComputeEnumSize + extensionsSerializedSize() + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = iExtensionsSerializedSize;
            return iExtensionsSerializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static FieldOptions parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static FieldOptions parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static FieldOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static FieldOptions parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static FieldOptions parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static FieldOptions parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static FieldOptions parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static FieldOptions parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static FieldOptions parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static FieldOptions parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(FieldOptions fieldOptions) {
            return newBuilder().mergeFrom(fieldOptions);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.ExtendableBuilder<FieldOptions, Builder> implements FieldOptionsOrBuilder {
            private int bitField0_;
            private CType ctype_;
            private boolean deprecated_;
            private Object experimentalMapKey_;
            private boolean lazy_;
            private boolean packed_;
            private RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> uninterpretedOptionBuilder_;
            private List<UninterpretedOption> uninterpretedOption_;
            private boolean weak_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_FieldOptions_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_FieldOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldOptions.class, Builder.class);
            }

            private Builder() {
                this.ctype_ = CType.STRING;
                this.experimentalMapKey_ = "";
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.ctype_ = CType.STRING;
                this.experimentalMapKey_ = "";
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getUninterpretedOptionFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.ctype_ = CType.STRING;
                int i = this.bitField0_;
                this.packed_ = false;
                this.lazy_ = false;
                this.deprecated_ = false;
                this.experimentalMapKey_ = "";
                this.weak_ = false;
                this.bitField0_ = i & (-64);
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -65;
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_FieldOptions_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public FieldOptions getDefaultInstanceForType() {
                return FieldOptions.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public FieldOptions build() {
                FieldOptions fieldOptionsBuildPartial = buildPartial();
                if (fieldOptionsBuildPartial.isInitialized()) {
                    return fieldOptionsBuildPartial;
                }
                throw newUninitializedMessageException((Message) fieldOptionsBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public FieldOptions buildPartial() {
                FieldOptions fieldOptions = new FieldOptions(this);
                int i = this.bitField0_;
                int i2 = (i & 1) != 1 ? 0 : 1;
                fieldOptions.ctype_ = this.ctype_;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                fieldOptions.packed_ = this.packed_;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                fieldOptions.lazy_ = this.lazy_;
                if ((i & 8) == 8) {
                    i2 |= 8;
                }
                fieldOptions.deprecated_ = this.deprecated_;
                if ((i & 16) == 16) {
                    i2 |= 16;
                }
                fieldOptions.experimentalMapKey_ = this.experimentalMapKey_;
                if ((i & 32) == 32) {
                    i2 |= 32;
                }
                fieldOptions.weak_ = this.weak_;
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder != null) {
                    fieldOptions.uninterpretedOption_ = repeatedFieldBuilder.build();
                } else {
                    if ((this.bitField0_ & 64) == 64) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                        this.bitField0_ &= -65;
                    }
                    fieldOptions.uninterpretedOption_ = this.uninterpretedOption_;
                }
                fieldOptions.bitField0_ = i2;
                onBuilt();
                return fieldOptions;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof FieldOptions) {
                    return mergeFrom((FieldOptions) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(FieldOptions fieldOptions) {
                if (fieldOptions == FieldOptions.getDefaultInstance()) {
                    return this;
                }
                if (fieldOptions.hasCtype()) {
                    setCtype(fieldOptions.getCtype());
                }
                if (fieldOptions.hasPacked()) {
                    setPacked(fieldOptions.getPacked());
                }
                if (fieldOptions.hasLazy()) {
                    setLazy(fieldOptions.getLazy());
                }
                if (fieldOptions.hasDeprecated()) {
                    setDeprecated(fieldOptions.getDeprecated());
                }
                if (fieldOptions.hasExperimentalMapKey()) {
                    this.bitField0_ |= 16;
                    this.experimentalMapKey_ = fieldOptions.experimentalMapKey_;
                    onChanged();
                }
                if (fieldOptions.hasWeak()) {
                    setWeak(fieldOptions.getWeak());
                }
                if (this.uninterpretedOptionBuilder_ == null) {
                    if (!fieldOptions.uninterpretedOption_.isEmpty()) {
                        if (this.uninterpretedOption_.isEmpty()) {
                            this.uninterpretedOption_ = fieldOptions.uninterpretedOption_;
                            this.bitField0_ &= -65;
                        } else {
                            ensureUninterpretedOptionIsMutable();
                            this.uninterpretedOption_.addAll(fieldOptions.uninterpretedOption_);
                        }
                        onChanged();
                    }
                } else if (!fieldOptions.uninterpretedOption_.isEmpty()) {
                    if (!this.uninterpretedOptionBuilder_.isEmpty()) {
                        this.uninterpretedOptionBuilder_.addAllMessages(fieldOptions.uninterpretedOption_);
                    } else {
                        this.uninterpretedOptionBuilder_.dispose();
                        this.uninterpretedOptionBuilder_ = null;
                        this.uninterpretedOption_ = fieldOptions.uninterpretedOption_;
                        this.bitField0_ &= -65;
                        this.uninterpretedOptionBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getUninterpretedOptionFieldBuilder() : null;
                    }
                }
                mergeExtensionFields(fieldOptions);
                mergeUnknownFields(fieldOptions.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                    if (!getUninterpretedOption(i).isInitialized()) {
                        return false;
                    }
                }
                return extensionsAreInitialized();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                FieldOptions fieldOptions = null;
                try {
                    try {
                        FieldOptions partialFrom = FieldOptions.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        FieldOptions fieldOptions2 = (FieldOptions) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            fieldOptions = fieldOptions2;
                            if (fieldOptions != null) {
                                mergeFrom(fieldOptions);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fieldOptions != null) {
                        mergeFrom(fieldOptions);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public boolean hasCtype() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public CType getCtype() {
                return this.ctype_;
            }

            public Builder setCtype(CType cType) {
                cType.getClass();
                this.bitField0_ |= 1;
                this.ctype_ = cType;
                onChanged();
                return this;
            }

            public Builder clearCtype() {
                this.bitField0_ &= -2;
                this.ctype_ = CType.STRING;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public boolean hasPacked() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public boolean getPacked() {
                return this.packed_;
            }

            public Builder setPacked(boolean z) {
                this.bitField0_ |= 2;
                this.packed_ = z;
                onChanged();
                return this;
            }

            public Builder clearPacked() {
                this.bitField0_ &= -3;
                this.packed_ = false;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public boolean hasLazy() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public boolean getLazy() {
                return this.lazy_;
            }

            public Builder setLazy(boolean z) {
                this.bitField0_ |= 4;
                this.lazy_ = z;
                onChanged();
                return this;
            }

            public Builder clearLazy() {
                this.bitField0_ &= -5;
                this.lazy_ = false;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public boolean hasDeprecated() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public boolean getDeprecated() {
                return this.deprecated_;
            }

            public Builder setDeprecated(boolean z) {
                this.bitField0_ |= 8;
                this.deprecated_ = z;
                onChanged();
                return this;
            }

            public Builder clearDeprecated() {
                this.bitField0_ &= -9;
                this.deprecated_ = false;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public boolean hasExperimentalMapKey() {
                return (this.bitField0_ & 16) == 16;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public String getExperimentalMapKey() {
                Object obj = this.experimentalMapKey_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.experimentalMapKey_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public ByteString getExperimentalMapKeyBytes() {
                Object obj = this.experimentalMapKey_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.experimentalMapKey_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setExperimentalMapKey(String str) {
                str.getClass();
                this.bitField0_ |= 16;
                this.experimentalMapKey_ = str;
                onChanged();
                return this;
            }

            public Builder clearExperimentalMapKey() {
                this.bitField0_ &= -17;
                this.experimentalMapKey_ = FieldOptions.getDefaultInstance().getExperimentalMapKey();
                onChanged();
                return this;
            }

            public Builder setExperimentalMapKeyBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 16;
                this.experimentalMapKey_ = byteString;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public boolean hasWeak() {
                return (this.bitField0_ & 32) == 32;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public boolean getWeak() {
                return this.weak_;
            }

            public Builder setWeak(boolean z) {
                this.bitField0_ |= 32;
                this.weak_ = z;
                onChanged();
                return this;
            }

            public Builder clearWeak() {
                this.bitField0_ &= -33;
                this.weak_ = false;
                onChanged();
                return this;
            }

            private void ensureUninterpretedOptionIsMutable() {
                if ((this.bitField0_ & 64) != 64) {
                    this.uninterpretedOption_ = new ArrayList(this.uninterpretedOption_);
                    this.bitField0_ |= 64;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public List<UninterpretedOption> getUninterpretedOptionList() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.uninterpretedOption_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public int getUninterpretedOptionCount() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public UninterpretedOption getUninterpretedOption(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.get(i);
                }
                return (UninterpretedOption) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setUninterpretedOption(int i, UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(i, uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, uninterpretedOption);
                }
                return this;
            }

            public Builder setUninterpretedOption(int i, UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(uninterpretedOption);
                }
                return this;
            }

            public Builder addUninterpretedOption(int i, UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(i, uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, uninterpretedOption);
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(int i, UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> iterable) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.uninterpretedOption_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearUninterpretedOption() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -65;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeUninterpretedOption(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public UninterpretedOption.Builder getUninterpretedOptionBuilder(int i) {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.get(i);
                }
                return (UninterpretedOptionOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.FieldOptionsOrBuilder
            public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.uninterpretedOption_);
            }

            public UninterpretedOption.Builder addUninterpretedOptionBuilder() {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().addBuilder(UninterpretedOption.getDefaultInstance());
            }

            public UninterpretedOption.Builder addUninterpretedOptionBuilder(int i) {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().addBuilder(i, UninterpretedOption.getDefaultInstance());
            }

            public List<UninterpretedOption.Builder> getUninterpretedOptionBuilderList() {
                return getUninterpretedOptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> getUninterpretedOptionFieldBuilder() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOptionBuilder_ = new RepeatedFieldBuilder<>(this.uninterpretedOption_, (this.bitField0_ & 64) == 64, getParentForChildren(), isClean());
                    this.uninterpretedOption_ = null;
                }
                return this.uninterpretedOptionBuilder_;
            }
        }
    }

    public static final class EnumOptions extends GeneratedMessage.ExtendableMessage<EnumOptions> implements EnumOptionsOrBuilder {
        public static final int ALLOW_ALIAS_FIELD_NUMBER = 2;
        public static final int DEPRECATED_FIELD_NUMBER = 3;
        public static Parser<EnumOptions> PARSER = new AbstractParser<EnumOptions>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptions.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public EnumOptions parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new EnumOptions(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private static final EnumOptions defaultInstance;
        private static final long serialVersionUID = 0;
        private boolean allowAlias_;
        private int bitField0_;
        private boolean deprecated_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<UninterpretedOption> uninterpretedOption_;
        private final UnknownFieldSet unknownFields;

        private EnumOptions(GeneratedMessage.ExtendableBuilder<EnumOptions, ?> extendableBuilder) {
            super(extendableBuilder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private EnumOptions(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static EnumOptions getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public EnumOptions getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private EnumOptions(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            char c = 0;
            while (!z) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 16) {
                                    this.bitField0_ |= 1;
                                    this.allowAlias_ = codedInputStream.readBool();
                                } else if (tag == 24) {
                                    this.bitField0_ |= 2;
                                    this.deprecated_ = codedInputStream.readBool();
                                } else if (tag != 7994) {
                                    if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    if ((c & 4) != 4) {
                                        this.uninterpretedOption_ = new ArrayList();
                                        c = 4;
                                    }
                                    this.uninterpretedOption_.add((UninterpretedOption) codedInputStream.readMessage(UninterpretedOption.PARSER, extensionRegistryLite));
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
                    if ((c & 4) == 4) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((c & 4) == 4) {
                this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_EnumOptions_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_EnumOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumOptions.class, Builder.class);
        }

        static {
            EnumOptions enumOptions = new EnumOptions(true);
            defaultInstance = enumOptions;
            enumOptions.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<EnumOptions> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
        public boolean hasAllowAlias() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
        public boolean getAllowAlias() {
            return this.allowAlias_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
        public boolean hasDeprecated() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
        public boolean getDeprecated() {
            return this.deprecated_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
        public UninterpretedOption getUninterpretedOption(int i) {
            return this.uninterpretedOption_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i) {
            return this.uninterpretedOption_.get(i);
        }

        private void initFields() {
            this.allowAlias_ = false;
            this.deprecated_ = false;
            this.uninterpretedOption_ = Collections.emptyList();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessage, org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                if (!getUninterpretedOption(i).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (!extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessage.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBool(2, this.allowAlias_);
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeBool(3, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                codedOutputStream.writeMessage(999, this.uninterpretedOption_.get(i));
            }
            extensionWriterNewExtensionWriter.writeUntil(536870912, codedOutputStream);
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBoolSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBoolSize(2, this.allowAlias_) : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeBoolSize += CodedOutputStream.computeBoolSize(3, this.deprecated_);
            }
            for (int i2 = 0; i2 < this.uninterpretedOption_.size(); i2++) {
                iComputeBoolSize += CodedOutputStream.computeMessageSize(999, this.uninterpretedOption_.get(i2));
            }
            int iExtensionsSerializedSize = iComputeBoolSize + extensionsSerializedSize() + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = iExtensionsSerializedSize;
            return iExtensionsSerializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static EnumOptions parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static EnumOptions parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static EnumOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static EnumOptions parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static EnumOptions parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static EnumOptions parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static EnumOptions parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static EnumOptions parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static EnumOptions parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static EnumOptions parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(EnumOptions enumOptions) {
            return newBuilder().mergeFrom(enumOptions);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.ExtendableBuilder<EnumOptions, Builder> implements EnumOptionsOrBuilder {
            private boolean allowAlias_;
            private int bitField0_;
            private boolean deprecated_;
            private RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> uninterpretedOptionBuilder_;
            private List<UninterpretedOption> uninterpretedOption_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_EnumOptions_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_EnumOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumOptions.class, Builder.class);
            }

            private Builder() {
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getUninterpretedOptionFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.allowAlias_ = false;
                int i = this.bitField0_;
                this.deprecated_ = false;
                this.bitField0_ = i & (-4);
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_EnumOptions_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public EnumOptions getDefaultInstanceForType() {
                return EnumOptions.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public EnumOptions build() {
                EnumOptions enumOptionsBuildPartial = buildPartial();
                if (enumOptionsBuildPartial.isInitialized()) {
                    return enumOptionsBuildPartial;
                }
                throw newUninitializedMessageException((Message) enumOptionsBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public EnumOptions buildPartial() {
                EnumOptions enumOptions = new EnumOptions(this);
                int i = this.bitField0_;
                int i2 = (i & 1) != 1 ? 0 : 1;
                enumOptions.allowAlias_ = this.allowAlias_;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                enumOptions.deprecated_ = this.deprecated_;
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder != null) {
                    enumOptions.uninterpretedOption_ = repeatedFieldBuilder.build();
                } else {
                    if ((this.bitField0_ & 4) == 4) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                        this.bitField0_ &= -5;
                    }
                    enumOptions.uninterpretedOption_ = this.uninterpretedOption_;
                }
                enumOptions.bitField0_ = i2;
                onBuilt();
                return enumOptions;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof EnumOptions) {
                    return mergeFrom((EnumOptions) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(EnumOptions enumOptions) {
                if (enumOptions == EnumOptions.getDefaultInstance()) {
                    return this;
                }
                if (enumOptions.hasAllowAlias()) {
                    setAllowAlias(enumOptions.getAllowAlias());
                }
                if (enumOptions.hasDeprecated()) {
                    setDeprecated(enumOptions.getDeprecated());
                }
                if (this.uninterpretedOptionBuilder_ == null) {
                    if (!enumOptions.uninterpretedOption_.isEmpty()) {
                        if (this.uninterpretedOption_.isEmpty()) {
                            this.uninterpretedOption_ = enumOptions.uninterpretedOption_;
                            this.bitField0_ &= -5;
                        } else {
                            ensureUninterpretedOptionIsMutable();
                            this.uninterpretedOption_.addAll(enumOptions.uninterpretedOption_);
                        }
                        onChanged();
                    }
                } else if (!enumOptions.uninterpretedOption_.isEmpty()) {
                    if (!this.uninterpretedOptionBuilder_.isEmpty()) {
                        this.uninterpretedOptionBuilder_.addAllMessages(enumOptions.uninterpretedOption_);
                    } else {
                        this.uninterpretedOptionBuilder_.dispose();
                        this.uninterpretedOptionBuilder_ = null;
                        this.uninterpretedOption_ = enumOptions.uninterpretedOption_;
                        this.bitField0_ &= -5;
                        this.uninterpretedOptionBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getUninterpretedOptionFieldBuilder() : null;
                    }
                }
                mergeExtensionFields(enumOptions);
                mergeUnknownFields(enumOptions.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                    if (!getUninterpretedOption(i).isInitialized()) {
                        return false;
                    }
                }
                return extensionsAreInitialized();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                EnumOptions enumOptions = null;
                try {
                    try {
                        EnumOptions partialFrom = EnumOptions.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        EnumOptions enumOptions2 = (EnumOptions) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            enumOptions = enumOptions2;
                            if (enumOptions != null) {
                                mergeFrom(enumOptions);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (enumOptions != null) {
                        mergeFrom(enumOptions);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
            public boolean hasAllowAlias() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
            public boolean getAllowAlias() {
                return this.allowAlias_;
            }

            public Builder setAllowAlias(boolean z) {
                this.bitField0_ |= 1;
                this.allowAlias_ = z;
                onChanged();
                return this;
            }

            public Builder clearAllowAlias() {
                this.bitField0_ &= -2;
                this.allowAlias_ = false;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
            public boolean hasDeprecated() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
            public boolean getDeprecated() {
                return this.deprecated_;
            }

            public Builder setDeprecated(boolean z) {
                this.bitField0_ |= 2;
                this.deprecated_ = z;
                onChanged();
                return this;
            }

            public Builder clearDeprecated() {
                this.bitField0_ &= -3;
                this.deprecated_ = false;
                onChanged();
                return this;
            }

            private void ensureUninterpretedOptionIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.uninterpretedOption_ = new ArrayList(this.uninterpretedOption_);
                    this.bitField0_ |= 4;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
            public List<UninterpretedOption> getUninterpretedOptionList() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.uninterpretedOption_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
            public int getUninterpretedOptionCount() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
            public UninterpretedOption getUninterpretedOption(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.get(i);
                }
                return (UninterpretedOption) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setUninterpretedOption(int i, UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(i, uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, uninterpretedOption);
                }
                return this;
            }

            public Builder setUninterpretedOption(int i, UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(uninterpretedOption);
                }
                return this;
            }

            public Builder addUninterpretedOption(int i, UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(i, uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, uninterpretedOption);
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(int i, UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> iterable) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.uninterpretedOption_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearUninterpretedOption() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeUninterpretedOption(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public UninterpretedOption.Builder getUninterpretedOptionBuilder(int i) {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
            public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.get(i);
                }
                return (UninterpretedOptionOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumOptionsOrBuilder
            public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.uninterpretedOption_);
            }

            public UninterpretedOption.Builder addUninterpretedOptionBuilder() {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().addBuilder(UninterpretedOption.getDefaultInstance());
            }

            public UninterpretedOption.Builder addUninterpretedOptionBuilder(int i) {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().addBuilder(i, UninterpretedOption.getDefaultInstance());
            }

            public List<UninterpretedOption.Builder> getUninterpretedOptionBuilderList() {
                return getUninterpretedOptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> getUninterpretedOptionFieldBuilder() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOptionBuilder_ = new RepeatedFieldBuilder<>(this.uninterpretedOption_, (this.bitField0_ & 4) == 4, getParentForChildren(), isClean());
                    this.uninterpretedOption_ = null;
                }
                return this.uninterpretedOptionBuilder_;
            }
        }
    }

    public static final class EnumValueOptions extends GeneratedMessage.ExtendableMessage<EnumValueOptions> implements EnumValueOptionsOrBuilder {
        public static final int DEPRECATED_FIELD_NUMBER = 1;
        public static Parser<EnumValueOptions> PARSER = new AbstractParser<EnumValueOptions>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptions.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public EnumValueOptions parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new EnumValueOptions(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private static final EnumValueOptions defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private boolean deprecated_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<UninterpretedOption> uninterpretedOption_;
        private final UnknownFieldSet unknownFields;

        private EnumValueOptions(GeneratedMessage.ExtendableBuilder<EnumValueOptions, ?> extendableBuilder) {
            super(extendableBuilder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private EnumValueOptions(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static EnumValueOptions getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public EnumValueOptions getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private EnumValueOptions(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
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
                                    this.deprecated_ = codedInputStream.readBool();
                                } else if (tag != 7994) {
                                    if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    if ((c & 2) != 2) {
                                        this.uninterpretedOption_ = new ArrayList();
                                        c = 2;
                                    }
                                    this.uninterpretedOption_.add((UninterpretedOption) codedInputStream.readMessage(UninterpretedOption.PARSER, extensionRegistryLite));
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw e.setUnfinishedMessage(this);
                        }
                    } catch (IOException e2) {
                        throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if ((c & 2) == 2) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((c & 2) == 2) {
                this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_EnumValueOptions_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_EnumValueOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumValueOptions.class, Builder.class);
        }

        static {
            EnumValueOptions enumValueOptions = new EnumValueOptions(true);
            defaultInstance = enumValueOptions;
            enumValueOptions.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<EnumValueOptions> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptionsOrBuilder
        public boolean hasDeprecated() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptionsOrBuilder
        public boolean getDeprecated() {
            return this.deprecated_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptionsOrBuilder
        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptionsOrBuilder
        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptionsOrBuilder
        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptionsOrBuilder
        public UninterpretedOption getUninterpretedOption(int i) {
            return this.uninterpretedOption_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptionsOrBuilder
        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i) {
            return this.uninterpretedOption_.get(i);
        }

        private void initFields() {
            this.deprecated_ = false;
            this.uninterpretedOption_ = Collections.emptyList();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessage, org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                if (!getUninterpretedOption(i).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (!extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessage.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBool(1, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                codedOutputStream.writeMessage(999, this.uninterpretedOption_.get(i));
            }
            extensionWriterNewExtensionWriter.writeUntil(536870912, codedOutputStream);
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBoolSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBoolSize(1, this.deprecated_) : 0;
            for (int i2 = 0; i2 < this.uninterpretedOption_.size(); i2++) {
                iComputeBoolSize += CodedOutputStream.computeMessageSize(999, this.uninterpretedOption_.get(i2));
            }
            int iExtensionsSerializedSize = iComputeBoolSize + extensionsSerializedSize() + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = iExtensionsSerializedSize;
            return iExtensionsSerializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static EnumValueOptions parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static EnumValueOptions parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static EnumValueOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static EnumValueOptions parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static EnumValueOptions parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static EnumValueOptions parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static EnumValueOptions parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static EnumValueOptions parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static EnumValueOptions parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static EnumValueOptions parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(EnumValueOptions enumValueOptions) {
            return newBuilder().mergeFrom(enumValueOptions);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.ExtendableBuilder<EnumValueOptions, Builder> implements EnumValueOptionsOrBuilder {
            private int bitField0_;
            private boolean deprecated_;
            private RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> uninterpretedOptionBuilder_;
            private List<UninterpretedOption> uninterpretedOption_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_EnumValueOptions_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_EnumValueOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumValueOptions.class, Builder.class);
            }

            private Builder() {
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getUninterpretedOptionFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.deprecated_ = false;
                this.bitField0_ &= -2;
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_EnumValueOptions_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public EnumValueOptions getDefaultInstanceForType() {
                return EnumValueOptions.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public EnumValueOptions build() {
                EnumValueOptions enumValueOptionsBuildPartial = buildPartial();
                if (enumValueOptionsBuildPartial.isInitialized()) {
                    return enumValueOptionsBuildPartial;
                }
                throw newUninitializedMessageException((Message) enumValueOptionsBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public EnumValueOptions buildPartial() {
                EnumValueOptions enumValueOptions = new EnumValueOptions(this);
                int i = (this.bitField0_ & 1) != 1 ? 0 : 1;
                enumValueOptions.deprecated_ = this.deprecated_;
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder != null) {
                    enumValueOptions.uninterpretedOption_ = repeatedFieldBuilder.build();
                } else {
                    if ((this.bitField0_ & 2) == 2) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                        this.bitField0_ &= -3;
                    }
                    enumValueOptions.uninterpretedOption_ = this.uninterpretedOption_;
                }
                enumValueOptions.bitField0_ = i;
                onBuilt();
                return enumValueOptions;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof EnumValueOptions) {
                    return mergeFrom((EnumValueOptions) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(EnumValueOptions enumValueOptions) {
                if (enumValueOptions == EnumValueOptions.getDefaultInstance()) {
                    return this;
                }
                if (enumValueOptions.hasDeprecated()) {
                    setDeprecated(enumValueOptions.getDeprecated());
                }
                if (this.uninterpretedOptionBuilder_ == null) {
                    if (!enumValueOptions.uninterpretedOption_.isEmpty()) {
                        if (this.uninterpretedOption_.isEmpty()) {
                            this.uninterpretedOption_ = enumValueOptions.uninterpretedOption_;
                            this.bitField0_ &= -3;
                        } else {
                            ensureUninterpretedOptionIsMutable();
                            this.uninterpretedOption_.addAll(enumValueOptions.uninterpretedOption_);
                        }
                        onChanged();
                    }
                } else if (!enumValueOptions.uninterpretedOption_.isEmpty()) {
                    if (!this.uninterpretedOptionBuilder_.isEmpty()) {
                        this.uninterpretedOptionBuilder_.addAllMessages(enumValueOptions.uninterpretedOption_);
                    } else {
                        this.uninterpretedOptionBuilder_.dispose();
                        this.uninterpretedOptionBuilder_ = null;
                        this.uninterpretedOption_ = enumValueOptions.uninterpretedOption_;
                        this.bitField0_ &= -3;
                        this.uninterpretedOptionBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getUninterpretedOptionFieldBuilder() : null;
                    }
                }
                mergeExtensionFields(enumValueOptions);
                mergeUnknownFields(enumValueOptions.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                    if (!getUninterpretedOption(i).isInitialized()) {
                        return false;
                    }
                }
                return extensionsAreInitialized();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                EnumValueOptions enumValueOptions = null;
                try {
                    try {
                        EnumValueOptions partialFrom = EnumValueOptions.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        EnumValueOptions enumValueOptions2 = (EnumValueOptions) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            enumValueOptions = enumValueOptions2;
                            if (enumValueOptions != null) {
                                mergeFrom(enumValueOptions);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (enumValueOptions != null) {
                        mergeFrom(enumValueOptions);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptionsOrBuilder
            public boolean hasDeprecated() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptionsOrBuilder
            public boolean getDeprecated() {
                return this.deprecated_;
            }

            public Builder setDeprecated(boolean z) {
                this.bitField0_ |= 1;
                this.deprecated_ = z;
                onChanged();
                return this;
            }

            public Builder clearDeprecated() {
                this.bitField0_ &= -2;
                this.deprecated_ = false;
                onChanged();
                return this;
            }

            private void ensureUninterpretedOptionIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.uninterpretedOption_ = new ArrayList(this.uninterpretedOption_);
                    this.bitField0_ |= 2;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptionsOrBuilder
            public List<UninterpretedOption> getUninterpretedOptionList() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.uninterpretedOption_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptionsOrBuilder
            public int getUninterpretedOptionCount() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptionsOrBuilder
            public UninterpretedOption getUninterpretedOption(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.get(i);
                }
                return (UninterpretedOption) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setUninterpretedOption(int i, UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(i, uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, uninterpretedOption);
                }
                return this;
            }

            public Builder setUninterpretedOption(int i, UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(uninterpretedOption);
                }
                return this;
            }

            public Builder addUninterpretedOption(int i, UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(i, uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, uninterpretedOption);
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(int i, UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> iterable) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.uninterpretedOption_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearUninterpretedOption() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeUninterpretedOption(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public UninterpretedOption.Builder getUninterpretedOptionBuilder(int i) {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptionsOrBuilder
            public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.get(i);
                }
                return (UninterpretedOptionOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.EnumValueOptionsOrBuilder
            public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.uninterpretedOption_);
            }

            public UninterpretedOption.Builder addUninterpretedOptionBuilder() {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().addBuilder(UninterpretedOption.getDefaultInstance());
            }

            public UninterpretedOption.Builder addUninterpretedOptionBuilder(int i) {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().addBuilder(i, UninterpretedOption.getDefaultInstance());
            }

            public List<UninterpretedOption.Builder> getUninterpretedOptionBuilderList() {
                return getUninterpretedOptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> getUninterpretedOptionFieldBuilder() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOptionBuilder_ = new RepeatedFieldBuilder<>(this.uninterpretedOption_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.uninterpretedOption_ = null;
                }
                return this.uninterpretedOptionBuilder_;
            }
        }
    }

    public static final class ServiceOptions extends GeneratedMessage.ExtendableMessage<ServiceOptions> implements ServiceOptionsOrBuilder {
        public static final int DEPRECATED_FIELD_NUMBER = 33;
        public static Parser<ServiceOptions> PARSER = new AbstractParser<ServiceOptions>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptions.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public ServiceOptions parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new ServiceOptions(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private static final ServiceOptions defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private boolean deprecated_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<UninterpretedOption> uninterpretedOption_;
        private final UnknownFieldSet unknownFields;

        private ServiceOptions(GeneratedMessage.ExtendableBuilder<ServiceOptions, ?> extendableBuilder) {
            super(extendableBuilder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private ServiceOptions(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static ServiceOptions getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public ServiceOptions getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ServiceOptions(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            char c = 0;
            while (!z) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 264) {
                                    this.bitField0_ |= 1;
                                    this.deprecated_ = codedInputStream.readBool();
                                } else if (tag != 7994) {
                                    if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    if ((c & 2) != 2) {
                                        this.uninterpretedOption_ = new ArrayList();
                                        c = 2;
                                    }
                                    this.uninterpretedOption_.add((UninterpretedOption) codedInputStream.readMessage(UninterpretedOption.PARSER, extensionRegistryLite));
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw e.setUnfinishedMessage(this);
                        }
                    } catch (IOException e2) {
                        throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if ((c & 2) == 2) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((c & 2) == 2) {
                this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_ServiceOptions_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_ServiceOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(ServiceOptions.class, Builder.class);
        }

        static {
            ServiceOptions serviceOptions = new ServiceOptions(true);
            defaultInstance = serviceOptions;
            serviceOptions.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<ServiceOptions> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptionsOrBuilder
        public boolean hasDeprecated() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptionsOrBuilder
        public boolean getDeprecated() {
            return this.deprecated_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptionsOrBuilder
        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptionsOrBuilder
        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptionsOrBuilder
        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptionsOrBuilder
        public UninterpretedOption getUninterpretedOption(int i) {
            return this.uninterpretedOption_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptionsOrBuilder
        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i) {
            return this.uninterpretedOption_.get(i);
        }

        private void initFields() {
            this.deprecated_ = false;
            this.uninterpretedOption_ = Collections.emptyList();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessage, org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                if (!getUninterpretedOption(i).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (!extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessage.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBool(33, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                codedOutputStream.writeMessage(999, this.uninterpretedOption_.get(i));
            }
            extensionWriterNewExtensionWriter.writeUntil(536870912, codedOutputStream);
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBoolSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBoolSize(33, this.deprecated_) : 0;
            for (int i2 = 0; i2 < this.uninterpretedOption_.size(); i2++) {
                iComputeBoolSize += CodedOutputStream.computeMessageSize(999, this.uninterpretedOption_.get(i2));
            }
            int iExtensionsSerializedSize = iComputeBoolSize + extensionsSerializedSize() + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = iExtensionsSerializedSize;
            return iExtensionsSerializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static ServiceOptions parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static ServiceOptions parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static ServiceOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static ServiceOptions parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static ServiceOptions parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static ServiceOptions parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static ServiceOptions parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static ServiceOptions parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static ServiceOptions parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static ServiceOptions parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(ServiceOptions serviceOptions) {
            return newBuilder().mergeFrom(serviceOptions);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.ExtendableBuilder<ServiceOptions, Builder> implements ServiceOptionsOrBuilder {
            private int bitField0_;
            private boolean deprecated_;
            private RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> uninterpretedOptionBuilder_;
            private List<UninterpretedOption> uninterpretedOption_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_ServiceOptions_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_ServiceOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(ServiceOptions.class, Builder.class);
            }

            private Builder() {
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getUninterpretedOptionFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.deprecated_ = false;
                this.bitField0_ &= -2;
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_ServiceOptions_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public ServiceOptions getDefaultInstanceForType() {
                return ServiceOptions.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public ServiceOptions build() {
                ServiceOptions serviceOptionsBuildPartial = buildPartial();
                if (serviceOptionsBuildPartial.isInitialized()) {
                    return serviceOptionsBuildPartial;
                }
                throw newUninitializedMessageException((Message) serviceOptionsBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public ServiceOptions buildPartial() {
                ServiceOptions serviceOptions = new ServiceOptions(this);
                int i = (this.bitField0_ & 1) != 1 ? 0 : 1;
                serviceOptions.deprecated_ = this.deprecated_;
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder != null) {
                    serviceOptions.uninterpretedOption_ = repeatedFieldBuilder.build();
                } else {
                    if ((this.bitField0_ & 2) == 2) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                        this.bitField0_ &= -3;
                    }
                    serviceOptions.uninterpretedOption_ = this.uninterpretedOption_;
                }
                serviceOptions.bitField0_ = i;
                onBuilt();
                return serviceOptions;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof ServiceOptions) {
                    return mergeFrom((ServiceOptions) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(ServiceOptions serviceOptions) {
                if (serviceOptions == ServiceOptions.getDefaultInstance()) {
                    return this;
                }
                if (serviceOptions.hasDeprecated()) {
                    setDeprecated(serviceOptions.getDeprecated());
                }
                if (this.uninterpretedOptionBuilder_ == null) {
                    if (!serviceOptions.uninterpretedOption_.isEmpty()) {
                        if (this.uninterpretedOption_.isEmpty()) {
                            this.uninterpretedOption_ = serviceOptions.uninterpretedOption_;
                            this.bitField0_ &= -3;
                        } else {
                            ensureUninterpretedOptionIsMutable();
                            this.uninterpretedOption_.addAll(serviceOptions.uninterpretedOption_);
                        }
                        onChanged();
                    }
                } else if (!serviceOptions.uninterpretedOption_.isEmpty()) {
                    if (!this.uninterpretedOptionBuilder_.isEmpty()) {
                        this.uninterpretedOptionBuilder_.addAllMessages(serviceOptions.uninterpretedOption_);
                    } else {
                        this.uninterpretedOptionBuilder_.dispose();
                        this.uninterpretedOptionBuilder_ = null;
                        this.uninterpretedOption_ = serviceOptions.uninterpretedOption_;
                        this.bitField0_ &= -3;
                        this.uninterpretedOptionBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getUninterpretedOptionFieldBuilder() : null;
                    }
                }
                mergeExtensionFields(serviceOptions);
                mergeUnknownFields(serviceOptions.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                    if (!getUninterpretedOption(i).isInitialized()) {
                        return false;
                    }
                }
                return extensionsAreInitialized();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                ServiceOptions serviceOptions = null;
                try {
                    try {
                        ServiceOptions partialFrom = ServiceOptions.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        ServiceOptions serviceOptions2 = (ServiceOptions) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            serviceOptions = serviceOptions2;
                            if (serviceOptions != null) {
                                mergeFrom(serviceOptions);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (serviceOptions != null) {
                        mergeFrom(serviceOptions);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptionsOrBuilder
            public boolean hasDeprecated() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptionsOrBuilder
            public boolean getDeprecated() {
                return this.deprecated_;
            }

            public Builder setDeprecated(boolean z) {
                this.bitField0_ |= 1;
                this.deprecated_ = z;
                onChanged();
                return this;
            }

            public Builder clearDeprecated() {
                this.bitField0_ &= -2;
                this.deprecated_ = false;
                onChanged();
                return this;
            }

            private void ensureUninterpretedOptionIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.uninterpretedOption_ = new ArrayList(this.uninterpretedOption_);
                    this.bitField0_ |= 2;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptionsOrBuilder
            public List<UninterpretedOption> getUninterpretedOptionList() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.uninterpretedOption_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptionsOrBuilder
            public int getUninterpretedOptionCount() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptionsOrBuilder
            public UninterpretedOption getUninterpretedOption(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.get(i);
                }
                return (UninterpretedOption) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setUninterpretedOption(int i, UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(i, uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, uninterpretedOption);
                }
                return this;
            }

            public Builder setUninterpretedOption(int i, UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(uninterpretedOption);
                }
                return this;
            }

            public Builder addUninterpretedOption(int i, UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(i, uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, uninterpretedOption);
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(int i, UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> iterable) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.uninterpretedOption_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearUninterpretedOption() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeUninterpretedOption(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public UninterpretedOption.Builder getUninterpretedOptionBuilder(int i) {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptionsOrBuilder
            public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.get(i);
                }
                return (UninterpretedOptionOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.ServiceOptionsOrBuilder
            public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.uninterpretedOption_);
            }

            public UninterpretedOption.Builder addUninterpretedOptionBuilder() {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().addBuilder(UninterpretedOption.getDefaultInstance());
            }

            public UninterpretedOption.Builder addUninterpretedOptionBuilder(int i) {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().addBuilder(i, UninterpretedOption.getDefaultInstance());
            }

            public List<UninterpretedOption.Builder> getUninterpretedOptionBuilderList() {
                return getUninterpretedOptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> getUninterpretedOptionFieldBuilder() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOptionBuilder_ = new RepeatedFieldBuilder<>(this.uninterpretedOption_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.uninterpretedOption_ = null;
                }
                return this.uninterpretedOptionBuilder_;
            }
        }
    }

    public static final class MethodOptions extends GeneratedMessage.ExtendableMessage<MethodOptions> implements MethodOptionsOrBuilder {
        public static final int DEPRECATED_FIELD_NUMBER = 33;
        public static Parser<MethodOptions> PARSER = new AbstractParser<MethodOptions>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptions.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public MethodOptions parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new MethodOptions(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private static final MethodOptions defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private boolean deprecated_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<UninterpretedOption> uninterpretedOption_;
        private final UnknownFieldSet unknownFields;

        private MethodOptions(GeneratedMessage.ExtendableBuilder<MethodOptions, ?> extendableBuilder) {
            super(extendableBuilder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = extendableBuilder.getUnknownFields();
        }

        private MethodOptions(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static MethodOptions getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public MethodOptions getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MethodOptions(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            char c = 0;
            while (!z) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 264) {
                                    this.bitField0_ |= 1;
                                    this.deprecated_ = codedInputStream.readBool();
                                } else if (tag != 7994) {
                                    if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    if ((c & 2) != 2) {
                                        this.uninterpretedOption_ = new ArrayList();
                                        c = 2;
                                    }
                                    this.uninterpretedOption_.add((UninterpretedOption) codedInputStream.readMessage(UninterpretedOption.PARSER, extensionRegistryLite));
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw e.setUnfinishedMessage(this);
                        }
                    } catch (IOException e2) {
                        throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if ((c & 2) == 2) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if ((c & 2) == 2) {
                this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_MethodOptions_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_MethodOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(MethodOptions.class, Builder.class);
        }

        static {
            MethodOptions methodOptions = new MethodOptions(true);
            defaultInstance = methodOptions;
            methodOptions.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<MethodOptions> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptionsOrBuilder
        public boolean hasDeprecated() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptionsOrBuilder
        public boolean getDeprecated() {
            return this.deprecated_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptionsOrBuilder
        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptionsOrBuilder
        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptionsOrBuilder
        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptionsOrBuilder
        public UninterpretedOption getUninterpretedOption(int i) {
            return this.uninterpretedOption_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptionsOrBuilder
        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i) {
            return this.uninterpretedOption_.get(i);
        }

        private void initFields() {
            this.deprecated_ = false;
            this.uninterpretedOption_ = Collections.emptyList();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableMessage, org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                if (!getUninterpretedOption(i).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (!extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            GeneratedMessage.ExtendableMessage<MessageType>.ExtensionWriter extensionWriterNewExtensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBool(33, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                codedOutputStream.writeMessage(999, this.uninterpretedOption_.get(i));
            }
            extensionWriterNewExtensionWriter.writeUntil(536870912, codedOutputStream);
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBoolSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBoolSize(33, this.deprecated_) : 0;
            for (int i2 = 0; i2 < this.uninterpretedOption_.size(); i2++) {
                iComputeBoolSize += CodedOutputStream.computeMessageSize(999, this.uninterpretedOption_.get(i2));
            }
            int iExtensionsSerializedSize = iComputeBoolSize + extensionsSerializedSize() + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = iExtensionsSerializedSize;
            return iExtensionsSerializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static MethodOptions parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static MethodOptions parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static MethodOptions parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static MethodOptions parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static MethodOptions parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static MethodOptions parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static MethodOptions parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static MethodOptions parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static MethodOptions parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static MethodOptions parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(MethodOptions methodOptions) {
            return newBuilder().mergeFrom(methodOptions);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.ExtendableBuilder<MethodOptions, Builder> implements MethodOptionsOrBuilder {
            private int bitField0_;
            private boolean deprecated_;
            private RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> uninterpretedOptionBuilder_;
            private List<UninterpretedOption> uninterpretedOption_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_MethodOptions_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_MethodOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(MethodOptions.class, Builder.class);
            }

            private Builder() {
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getUninterpretedOptionFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                this.deprecated_ = false;
                this.bitField0_ &= -2;
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_MethodOptions_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public MethodOptions getDefaultInstanceForType() {
                return MethodOptions.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public MethodOptions build() {
                MethodOptions methodOptionsBuildPartial = buildPartial();
                if (methodOptionsBuildPartial.isInitialized()) {
                    return methodOptionsBuildPartial;
                }
                throw newUninitializedMessageException((Message) methodOptionsBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public MethodOptions buildPartial() {
                MethodOptions methodOptions = new MethodOptions(this);
                int i = (this.bitField0_ & 1) != 1 ? 0 : 1;
                methodOptions.deprecated_ = this.deprecated_;
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder != null) {
                    methodOptions.uninterpretedOption_ = repeatedFieldBuilder.build();
                } else {
                    if ((this.bitField0_ & 2) == 2) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                        this.bitField0_ &= -3;
                    }
                    methodOptions.uninterpretedOption_ = this.uninterpretedOption_;
                }
                methodOptions.bitField0_ = i;
                onBuilt();
                return methodOptions;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof MethodOptions) {
                    return mergeFrom((MethodOptions) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(MethodOptions methodOptions) {
                if (methodOptions == MethodOptions.getDefaultInstance()) {
                    return this;
                }
                if (methodOptions.hasDeprecated()) {
                    setDeprecated(methodOptions.getDeprecated());
                }
                if (this.uninterpretedOptionBuilder_ == null) {
                    if (!methodOptions.uninterpretedOption_.isEmpty()) {
                        if (this.uninterpretedOption_.isEmpty()) {
                            this.uninterpretedOption_ = methodOptions.uninterpretedOption_;
                            this.bitField0_ &= -3;
                        } else {
                            ensureUninterpretedOptionIsMutable();
                            this.uninterpretedOption_.addAll(methodOptions.uninterpretedOption_);
                        }
                        onChanged();
                    }
                } else if (!methodOptions.uninterpretedOption_.isEmpty()) {
                    if (!this.uninterpretedOptionBuilder_.isEmpty()) {
                        this.uninterpretedOptionBuilder_.addAllMessages(methodOptions.uninterpretedOption_);
                    } else {
                        this.uninterpretedOptionBuilder_.dispose();
                        this.uninterpretedOptionBuilder_ = null;
                        this.uninterpretedOption_ = methodOptions.uninterpretedOption_;
                        this.bitField0_ &= -3;
                        this.uninterpretedOptionBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getUninterpretedOptionFieldBuilder() : null;
                    }
                }
                mergeExtensionFields(methodOptions);
                mergeUnknownFields(methodOptions.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.ExtendableBuilder, org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                    if (!getUninterpretedOption(i).isInitialized()) {
                        return false;
                    }
                }
                return extensionsAreInitialized();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                MethodOptions methodOptions = null;
                try {
                    try {
                        MethodOptions partialFrom = MethodOptions.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        MethodOptions methodOptions2 = (MethodOptions) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            methodOptions = methodOptions2;
                            if (methodOptions != null) {
                                mergeFrom(methodOptions);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (methodOptions != null) {
                        mergeFrom(methodOptions);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptionsOrBuilder
            public boolean hasDeprecated() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptionsOrBuilder
            public boolean getDeprecated() {
                return this.deprecated_;
            }

            public Builder setDeprecated(boolean z) {
                this.bitField0_ |= 1;
                this.deprecated_ = z;
                onChanged();
                return this;
            }

            public Builder clearDeprecated() {
                this.bitField0_ &= -2;
                this.deprecated_ = false;
                onChanged();
                return this;
            }

            private void ensureUninterpretedOptionIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.uninterpretedOption_ = new ArrayList(this.uninterpretedOption_);
                    this.bitField0_ |= 2;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptionsOrBuilder
            public List<UninterpretedOption> getUninterpretedOptionList() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.uninterpretedOption_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptionsOrBuilder
            public int getUninterpretedOptionCount() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptionsOrBuilder
            public UninterpretedOption getUninterpretedOption(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.get(i);
                }
                return (UninterpretedOption) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setUninterpretedOption(int i, UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(i, uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, uninterpretedOption);
                }
                return this;
            }

            public Builder setUninterpretedOption(int i, UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(uninterpretedOption);
                }
                return this;
            }

            public Builder addUninterpretedOption(int i, UninterpretedOption uninterpretedOption) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    uninterpretedOption.getClass();
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(i, uninterpretedOption);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, uninterpretedOption);
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(int i, UninterpretedOption.Builder builder) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> iterable) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.uninterpretedOption_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearUninterpretedOption() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeUninterpretedOption(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public UninterpretedOption.Builder getUninterpretedOptionBuilder(int i) {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptionsOrBuilder
            public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i) {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.uninterpretedOption_.get(i);
                }
                return (UninterpretedOptionOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.MethodOptionsOrBuilder
            public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
                RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> repeatedFieldBuilder = this.uninterpretedOptionBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.uninterpretedOption_);
            }

            public UninterpretedOption.Builder addUninterpretedOptionBuilder() {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().addBuilder(UninterpretedOption.getDefaultInstance());
            }

            public UninterpretedOption.Builder addUninterpretedOptionBuilder(int i) {
                return (UninterpretedOption.Builder) getUninterpretedOptionFieldBuilder().addBuilder(i, UninterpretedOption.getDefaultInstance());
            }

            public List<UninterpretedOption.Builder> getUninterpretedOptionBuilderList() {
                return getUninterpretedOptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<UninterpretedOption, UninterpretedOption.Builder, UninterpretedOptionOrBuilder> getUninterpretedOptionFieldBuilder() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOptionBuilder_ = new RepeatedFieldBuilder<>(this.uninterpretedOption_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.uninterpretedOption_ = null;
                }
                return this.uninterpretedOptionBuilder_;
            }
        }
    }

    public static final class UninterpretedOption extends GeneratedMessage implements UninterpretedOptionOrBuilder {
        public static final int AGGREGATE_VALUE_FIELD_NUMBER = 8;
        public static final int DOUBLE_VALUE_FIELD_NUMBER = 6;
        public static final int IDENTIFIER_VALUE_FIELD_NUMBER = 3;
        public static final int NAME_FIELD_NUMBER = 2;
        public static final int NEGATIVE_INT_VALUE_FIELD_NUMBER = 5;
        public static Parser<UninterpretedOption> PARSER = new AbstractParser<UninterpretedOption>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOption.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public UninterpretedOption parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new UninterpretedOption(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int POSITIVE_INT_VALUE_FIELD_NUMBER = 4;
        public static final int STRING_VALUE_FIELD_NUMBER = 7;
        private static final UninterpretedOption defaultInstance;
        private static final long serialVersionUID = 0;
        private Object aggregateValue_;
        private int bitField0_;
        private double doubleValue_;
        private Object identifierValue_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<NamePart> name_;
        private long negativeIntValue_;
        private long positiveIntValue_;
        private ByteString stringValue_;
        private final UnknownFieldSet unknownFields;

        public interface NamePartOrBuilder extends MessageOrBuilder {
            boolean getIsExtension();

            String getNamePart();

            ByteString getNamePartBytes();

            boolean hasIsExtension();

            boolean hasNamePart();
        }

        private UninterpretedOption(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private UninterpretedOption(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static UninterpretedOption getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public UninterpretedOption getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private UninterpretedOption(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            boolean z2 = false;
            while (!z) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 18) {
                                    if (!z2) {
                                        this.name_ = new ArrayList();
                                        z2 = true;
                                    }
                                    this.name_.add((NamePart) codedInputStream.readMessage(NamePart.PARSER, extensionRegistryLite));
                                } else if (tag == 26) {
                                    ByteString bytes = codedInputStream.readBytes();
                                    this.bitField0_ |= 1;
                                    this.identifierValue_ = bytes;
                                } else if (tag == 32) {
                                    this.bitField0_ |= 2;
                                    this.positiveIntValue_ = codedInputStream.readUInt64();
                                } else if (tag == 40) {
                                    this.bitField0_ |= 4;
                                    this.negativeIntValue_ = codedInputStream.readInt64();
                                } else if (tag == 49) {
                                    this.bitField0_ |= 8;
                                    this.doubleValue_ = codedInputStream.readDouble();
                                } else if (tag == 58) {
                                    this.bitField0_ |= 16;
                                    this.stringValue_ = codedInputStream.readBytes();
                                } else if (tag != 66) {
                                    if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    ByteString bytes2 = codedInputStream.readBytes();
                                    this.bitField0_ = 32 | this.bitField0_;
                                    this.aggregateValue_ = bytes2;
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
                        this.name_ = Collections.unmodifiableList(this.name_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if (z2) {
                this.name_ = Collections.unmodifiableList(this.name_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_fieldAccessorTable.ensureFieldAccessorsInitialized(UninterpretedOption.class, Builder.class);
        }

        static {
            UninterpretedOption uninterpretedOption = new UninterpretedOption(true);
            defaultInstance = uninterpretedOption;
            uninterpretedOption.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<UninterpretedOption> getParserForType() {
            return PARSER;
        }

        public static final class NamePart extends GeneratedMessage implements NamePartOrBuilder {
            public static final int IS_EXTENSION_FIELD_NUMBER = 2;
            public static final int NAME_PART_FIELD_NUMBER = 1;
            public static Parser<NamePart> PARSER = new AbstractParser<NamePart>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOption.NamePart.1
                @Override // org.jetbrains.kotlin.protobuf.Parser
                public NamePart parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                    return new NamePart(codedInputStream, extensionRegistryLite);
                }
            };
            private static final NamePart defaultInstance;
            private static final long serialVersionUID = 0;
            private int bitField0_;
            private boolean isExtension_;
            private byte memoizedIsInitialized;
            private int memoizedSerializedSize;
            private Object namePart_;
            private final UnknownFieldSet unknownFields;

            private NamePart(GeneratedMessage.Builder<?> builder) {
                super(builder);
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = builder.getUnknownFields();
            }

            private NamePart(boolean z) {
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = UnknownFieldSet.getDefaultInstance();
            }

            public static NamePart getDefaultInstance() {
                return defaultInstance;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public NamePart getDefaultInstanceForType() {
                return defaultInstance;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }

            private NamePart(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                initFields();
                UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
                boolean z = false;
                while (!z) {
                    try {
                        try {
                            try {
                                int tag = codedInputStream.readTag();
                                if (tag != 0) {
                                    if (tag == 10) {
                                        ByteString bytes = codedInputStream.readBytes();
                                        this.bitField0_ = 1 | this.bitField0_;
                                        this.namePart_ = bytes;
                                    } else if (tag != 16) {
                                        if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                        }
                                    } else {
                                        this.bitField0_ |= 2;
                                        this.isExtension_ = codedInputStream.readBool();
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
                        this.unknownFields = builderNewBuilder.build();
                        makeExtensionsImmutable();
                        throw th;
                    }
                }
                this.unknownFields = builderNewBuilder.build();
                makeExtensionsImmutable();
            }

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_NamePart_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_NamePart_fieldAccessorTable.ensureFieldAccessorsInitialized(NamePart.class, Builder.class);
            }

            static {
                NamePart namePart = new NamePart(true);
                defaultInstance = namePart;
                namePart.initFields();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
            public Parser<NamePart> getParserForType() {
                return PARSER;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOption.NamePartOrBuilder
            public boolean hasNamePart() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOption.NamePartOrBuilder
            public String getNamePart() {
                Object obj = this.namePart_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                ByteString byteString = (ByteString) obj;
                String stringUtf8 = byteString.toStringUtf8();
                if (byteString.isValidUtf8()) {
                    this.namePart_ = stringUtf8;
                }
                return stringUtf8;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOption.NamePartOrBuilder
            public ByteString getNamePartBytes() {
                Object obj = this.namePart_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.namePart_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOption.NamePartOrBuilder
            public boolean hasIsExtension() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOption.NamePartOrBuilder
            public boolean getIsExtension() {
                return this.isExtension_;
            }

            private void initFields() {
                this.namePart_ = "";
                this.isExtension_ = false;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                byte b = this.memoizedIsInitialized;
                if (b == 1) {
                    return true;
                }
                if (b == 0) {
                    return false;
                }
                if (!hasNamePart()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
                if (!hasIsExtension()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
            public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
                getSerializedSize();
                if ((this.bitField0_ & 1) == 1) {
                    codedOutputStream.writeBytes(1, getNamePartBytes());
                }
                if ((this.bitField0_ & 2) == 2) {
                    codedOutputStream.writeBool(2, this.isExtension_);
                }
                getUnknownFields().writeTo(codedOutputStream);
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
            public int getSerializedSize() {
                int i = this.memoizedSerializedSize;
                if (i != -1) {
                    return i;
                }
                int iComputeBytesSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBytesSize(1, getNamePartBytes()) : 0;
                if ((this.bitField0_ & 2) == 2) {
                    iComputeBytesSize += CodedOutputStream.computeBoolSize(2, this.isExtension_);
                }
                int serializedSize = iComputeBytesSize + getUnknownFields().getSerializedSize();
                this.memoizedSerializedSize = serializedSize;
                return serializedSize;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
            protected Object writeReplace() throws ObjectStreamException {
                return super.writeReplace();
            }

            public static NamePart parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(byteString);
            }

            public static NamePart parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(byteString, extensionRegistryLite);
            }

            public static NamePart parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(bArr);
            }

            public static NamePart parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(bArr, extensionRegistryLite);
            }

            public static NamePart parseFrom(InputStream inputStream) throws IOException {
                return PARSER.parseFrom(inputStream);
            }

            public static NamePart parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return PARSER.parseFrom(inputStream, extensionRegistryLite);
            }

            public static NamePart parseDelimitedFrom(InputStream inputStream) throws IOException {
                return PARSER.parseDelimitedFrom(inputStream);
            }

            public static NamePart parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
            }

            public static NamePart parseFrom(CodedInputStream codedInputStream) throws IOException {
                return PARSER.parseFrom(codedInputStream);
            }

            public static NamePart parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
            }

            public static Builder newBuilder() {
                return Builder.create();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite
            public Builder newBuilderForType() {
                return newBuilder();
            }

            public static Builder newBuilder(NamePart namePart) {
                return newBuilder().mergeFrom(namePart);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite
            public Builder toBuilder() {
                return newBuilder(this);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
            public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
                return new Builder(builderParent);
            }

            public static final class Builder extends GeneratedMessage.Builder<Builder> implements NamePartOrBuilder {
                private int bitField0_;
                private boolean isExtension_;
                private Object namePart_;

                public static final Descriptors.Descriptor getDescriptor() {
                    return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_NamePart_descriptor;
                }

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
                protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                    return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_NamePart_fieldAccessorTable.ensureFieldAccessorsInitialized(NamePart.class, Builder.class);
                }

                private Builder() {
                    this.namePart_ = "";
                    maybeForceBuilderInitialization();
                }

                private Builder(GeneratedMessage.BuilderParent builderParent) {
                    super(builderParent);
                    this.namePart_ = "";
                    maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                    boolean z = GeneratedMessage.alwaysUseFieldBuilders;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static Builder create() {
                    return new Builder();
                }

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public Builder clear() {
                    super.clear();
                    this.namePart_ = "";
                    int i = this.bitField0_;
                    this.isExtension_ = false;
                    this.bitField0_ = i & (-4);
                    return this;
                }

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public Builder clone() {
                    return create().mergeFrom(buildPartial());
                }

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
                public Descriptors.Descriptor getDescriptorForType() {
                    return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_NamePart_descriptor;
                }

                @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
                public NamePart getDefaultInstanceForType() {
                    return NamePart.getDefaultInstance();
                }

                @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public NamePart build() {
                    NamePart namePartBuildPartial = buildPartial();
                    if (namePartBuildPartial.isInitialized()) {
                        return namePartBuildPartial;
                    }
                    throw newUninitializedMessageException((Message) namePartBuildPartial);
                }

                @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public NamePart buildPartial() {
                    NamePart namePart = new NamePart(this);
                    int i = this.bitField0_;
                    int i2 = (i & 1) != 1 ? 0 : 1;
                    namePart.namePart_ = this.namePart_;
                    if ((i & 2) == 2) {
                        i2 |= 2;
                    }
                    namePart.isExtension_ = this.isExtension_;
                    namePart.bitField0_ = i2;
                    onBuilt();
                    return namePart;
                }

                @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
                public Builder mergeFrom(Message message) {
                    if (message instanceof NamePart) {
                        return mergeFrom((NamePart) message);
                    }
                    super.mergeFrom(message);
                    return this;
                }

                public Builder mergeFrom(NamePart namePart) {
                    if (namePart == NamePart.getDefaultInstance()) {
                        return this;
                    }
                    if (namePart.hasNamePart()) {
                        this.bitField0_ |= 1;
                        this.namePart_ = namePart.namePart_;
                        onChanged();
                    }
                    if (namePart.hasIsExtension()) {
                        setIsExtension(namePart.getIsExtension());
                    }
                    mergeUnknownFields(namePart.getUnknownFields());
                    return this;
                }

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
                public final boolean isInitialized() {
                    return hasNamePart() && hasIsExtension();
                }

                /* JADX WARN: Code duplicated, block: B:15:0x001d  */
                @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                    NamePart namePart = null;
                    try {
                        try {
                            NamePart partialFrom = NamePart.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                            if (partialFrom != null) {
                                mergeFrom(partialFrom);
                            }
                            return this;
                        } catch (InvalidProtocolBufferException e) {
                            NamePart namePart2 = (NamePart) e.getUnfinishedMessage();
                            try {
                                throw e;
                            } catch (Throwable th) {
                                th = th;
                                namePart = namePart2;
                                if (namePart != null) {
                                    mergeFrom(namePart);
                                }
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (namePart != null) {
                            mergeFrom(namePart);
                        }
                        throw th;
                    }
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOption.NamePartOrBuilder
                public boolean hasNamePart() {
                    return (this.bitField0_ & 1) == 1;
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOption.NamePartOrBuilder
                public String getNamePart() {
                    Object obj = this.namePart_;
                    if (!(obj instanceof String)) {
                        ByteString byteString = (ByteString) obj;
                        String stringUtf8 = byteString.toStringUtf8();
                        if (byteString.isValidUtf8()) {
                            this.namePart_ = stringUtf8;
                        }
                        return stringUtf8;
                    }
                    return (String) obj;
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOption.NamePartOrBuilder
                public ByteString getNamePartBytes() {
                    Object obj = this.namePart_;
                    if (obj instanceof String) {
                        ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                        this.namePart_ = byteStringCopyFromUtf8;
                        return byteStringCopyFromUtf8;
                    }
                    return (ByteString) obj;
                }

                public Builder setNamePart(String str) {
                    str.getClass();
                    this.bitField0_ |= 1;
                    this.namePart_ = str;
                    onChanged();
                    return this;
                }

                public Builder clearNamePart() {
                    this.bitField0_ &= -2;
                    this.namePart_ = NamePart.getDefaultInstance().getNamePart();
                    onChanged();
                    return this;
                }

                public Builder setNamePartBytes(ByteString byteString) {
                    byteString.getClass();
                    this.bitField0_ |= 1;
                    this.namePart_ = byteString;
                    onChanged();
                    return this;
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOption.NamePartOrBuilder
                public boolean hasIsExtension() {
                    return (this.bitField0_ & 2) == 2;
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOption.NamePartOrBuilder
                public boolean getIsExtension() {
                    return this.isExtension_;
                }

                public Builder setIsExtension(boolean z) {
                    this.bitField0_ |= 2;
                    this.isExtension_ = z;
                    onChanged();
                    return this;
                }

                public Builder clearIsExtension() {
                    this.bitField0_ &= -3;
                    this.isExtension_ = false;
                    onChanged();
                    return this;
                }
            }
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public List<NamePart> getNameList() {
            return this.name_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public List<? extends NamePartOrBuilder> getNameOrBuilderList() {
            return this.name_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public int getNameCount() {
            return this.name_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public NamePart getName(int i) {
            return this.name_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public NamePartOrBuilder getNameOrBuilder(int i) {
            return this.name_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public boolean hasIdentifierValue() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public String getIdentifierValue() {
            Object obj = this.identifierValue_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.identifierValue_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public ByteString getIdentifierValueBytes() {
            Object obj = this.identifierValue_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.identifierValue_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public boolean hasPositiveIntValue() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public long getPositiveIntValue() {
            return this.positiveIntValue_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public boolean hasNegativeIntValue() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public long getNegativeIntValue() {
            return this.negativeIntValue_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public boolean hasDoubleValue() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public double getDoubleValue() {
            return this.doubleValue_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public boolean hasStringValue() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public ByteString getStringValue() {
            return this.stringValue_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public boolean hasAggregateValue() {
            return (this.bitField0_ & 32) == 32;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public String getAggregateValue() {
            Object obj = this.aggregateValue_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.aggregateValue_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
        public ByteString getAggregateValueBytes() {
            Object obj = this.aggregateValue_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.aggregateValue_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        private void initFields() {
            this.name_ = Collections.emptyList();
            this.identifierValue_ = "";
            this.positiveIntValue_ = 0L;
            this.negativeIntValue_ = 0L;
            this.doubleValue_ = 0.0d;
            this.stringValue_ = ByteString.EMPTY;
            this.aggregateValue_ = "";
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b = this.memoizedIsInitialized;
            if (b == 1) {
                return true;
            }
            if (b == 0) {
                return false;
            }
            for (int i = 0; i < getNameCount(); i++) {
                if (!getName(i).isInitialized()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            for (int i = 0; i < this.name_.size(); i++) {
                codedOutputStream.writeMessage(2, this.name_.get(i));
            }
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeBytes(3, getIdentifierValueBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeUInt64(4, this.positiveIntValue_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeInt64(5, this.negativeIntValue_);
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeDouble(6, this.doubleValue_);
            }
            if ((this.bitField0_ & 16) == 16) {
                codedOutputStream.writeBytes(7, this.stringValue_);
            }
            if ((this.bitField0_ & 32) == 32) {
                codedOutputStream.writeBytes(8, getAggregateValueBytes());
            }
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBytesSize = 0;
            for (int i2 = 0; i2 < this.name_.size(); i2++) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(2, this.name_.get(i2));
            }
            if ((this.bitField0_ & 1) == 1) {
                iComputeBytesSize += CodedOutputStream.computeBytesSize(3, getIdentifierValueBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                iComputeBytesSize += CodedOutputStream.computeUInt64Size(4, this.positiveIntValue_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeBytesSize += CodedOutputStream.computeInt64Size(5, this.negativeIntValue_);
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeBytesSize += CodedOutputStream.computeDoubleSize(6, this.doubleValue_);
            }
            if ((this.bitField0_ & 16) == 16) {
                iComputeBytesSize += CodedOutputStream.computeBytesSize(7, this.stringValue_);
            }
            if ((this.bitField0_ & 32) == 32) {
                iComputeBytesSize += CodedOutputStream.computeBytesSize(8, getAggregateValueBytes());
            }
            int serializedSize = iComputeBytesSize + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static UninterpretedOption parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static UninterpretedOption parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static UninterpretedOption parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static UninterpretedOption parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static UninterpretedOption parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static UninterpretedOption parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static UninterpretedOption parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static UninterpretedOption parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static UninterpretedOption parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static UninterpretedOption parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(UninterpretedOption uninterpretedOption) {
            return newBuilder().mergeFrom(uninterpretedOption);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.Builder<Builder> implements UninterpretedOptionOrBuilder {
            private Object aggregateValue_;
            private int bitField0_;
            private double doubleValue_;
            private Object identifierValue_;
            private RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> nameBuilder_;
            private List<NamePart> name_;
            private long negativeIntValue_;
            private long positiveIntValue_;
            private ByteString stringValue_;

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_fieldAccessorTable.ensureFieldAccessorsInitialized(UninterpretedOption.class, Builder.class);
            }

            private Builder() {
                this.name_ = Collections.emptyList();
                this.identifierValue_ = "";
                this.stringValue_ = ByteString.EMPTY;
                this.aggregateValue_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.name_ = Collections.emptyList();
                this.identifierValue_ = "";
                this.stringValue_ = ByteString.EMPTY;
                this.aggregateValue_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getNameFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.name_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                } else {
                    repeatedFieldBuilder.clear();
                }
                this.identifierValue_ = "";
                int i = this.bitField0_;
                this.positiveIntValue_ = 0L;
                this.negativeIntValue_ = 0L;
                this.doubleValue_ = 0.0d;
                this.bitField0_ = i & (-31);
                this.stringValue_ = ByteString.EMPTY;
                int i2 = this.bitField0_;
                this.aggregateValue_ = "";
                this.bitField0_ = i2 & (-97);
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public UninterpretedOption getDefaultInstanceForType() {
                return UninterpretedOption.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public UninterpretedOption build() {
                UninterpretedOption uninterpretedOptionBuildPartial = buildPartial();
                if (uninterpretedOptionBuildPartial.isInitialized()) {
                    return uninterpretedOptionBuildPartial;
                }
                throw newUninitializedMessageException((Message) uninterpretedOptionBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public UninterpretedOption buildPartial() {
                UninterpretedOption uninterpretedOption = new UninterpretedOption(this);
                int i = this.bitField0_;
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    if ((i & 1) == 1) {
                        this.name_ = Collections.unmodifiableList(this.name_);
                        this.bitField0_ &= -2;
                    }
                    uninterpretedOption.name_ = this.name_;
                } else {
                    uninterpretedOption.name_ = repeatedFieldBuilder.build();
                }
                int i2 = (i & 2) != 2 ? 0 : 1;
                uninterpretedOption.identifierValue_ = this.identifierValue_;
                if ((i & 4) == 4) {
                    i2 |= 2;
                }
                uninterpretedOption.positiveIntValue_ = this.positiveIntValue_;
                if ((i & 8) == 8) {
                    i2 |= 4;
                }
                uninterpretedOption.negativeIntValue_ = this.negativeIntValue_;
                if ((i & 16) == 16) {
                    i2 |= 8;
                }
                uninterpretedOption.doubleValue_ = this.doubleValue_;
                if ((i & 32) == 32) {
                    i2 |= 16;
                }
                uninterpretedOption.stringValue_ = this.stringValue_;
                if ((i & 64) == 64) {
                    i2 |= 32;
                }
                uninterpretedOption.aggregateValue_ = this.aggregateValue_;
                uninterpretedOption.bitField0_ = i2;
                onBuilt();
                return uninterpretedOption;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof UninterpretedOption) {
                    return mergeFrom((UninterpretedOption) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(UninterpretedOption uninterpretedOption) {
                if (uninterpretedOption == UninterpretedOption.getDefaultInstance()) {
                    return this;
                }
                if (this.nameBuilder_ == null) {
                    if (!uninterpretedOption.name_.isEmpty()) {
                        if (this.name_.isEmpty()) {
                            this.name_ = uninterpretedOption.name_;
                            this.bitField0_ &= -2;
                        } else {
                            ensureNameIsMutable();
                            this.name_.addAll(uninterpretedOption.name_);
                        }
                        onChanged();
                    }
                } else if (!uninterpretedOption.name_.isEmpty()) {
                    if (!this.nameBuilder_.isEmpty()) {
                        this.nameBuilder_.addAllMessages(uninterpretedOption.name_);
                    } else {
                        this.nameBuilder_.dispose();
                        this.nameBuilder_ = null;
                        this.name_ = uninterpretedOption.name_;
                        this.bitField0_ &= -2;
                        this.nameBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getNameFieldBuilder() : null;
                    }
                }
                if (uninterpretedOption.hasIdentifierValue()) {
                    this.bitField0_ |= 2;
                    this.identifierValue_ = uninterpretedOption.identifierValue_;
                    onChanged();
                }
                if (uninterpretedOption.hasPositiveIntValue()) {
                    setPositiveIntValue(uninterpretedOption.getPositiveIntValue());
                }
                if (uninterpretedOption.hasNegativeIntValue()) {
                    setNegativeIntValue(uninterpretedOption.getNegativeIntValue());
                }
                if (uninterpretedOption.hasDoubleValue()) {
                    setDoubleValue(uninterpretedOption.getDoubleValue());
                }
                if (uninterpretedOption.hasStringValue()) {
                    setStringValue(uninterpretedOption.getStringValue());
                }
                if (uninterpretedOption.hasAggregateValue()) {
                    this.bitField0_ |= 64;
                    this.aggregateValue_ = uninterpretedOption.aggregateValue_;
                    onChanged();
                }
                mergeUnknownFields(uninterpretedOption.getUnknownFields());
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                for (int i = 0; i < getNameCount(); i++) {
                    if (!getName(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                UninterpretedOption uninterpretedOption = null;
                try {
                    try {
                        UninterpretedOption partialFrom = UninterpretedOption.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        UninterpretedOption uninterpretedOption2 = (UninterpretedOption) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            uninterpretedOption = uninterpretedOption2;
                            if (uninterpretedOption != null) {
                                mergeFrom(uninterpretedOption);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (uninterpretedOption != null) {
                        mergeFrom(uninterpretedOption);
                    }
                    throw th;
                }
            }

            private void ensureNameIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.name_ = new ArrayList(this.name_);
                    this.bitField0_ |= 1;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public List<NamePart> getNameList() {
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.name_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public int getNameCount() {
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.name_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public NamePart getName(int i) {
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.name_.get(i);
                }
                return (NamePart) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setName(int i, NamePart namePart) {
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    namePart.getClass();
                    ensureNameIsMutable();
                    this.name_.set(i, namePart);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, namePart);
                }
                return this;
            }

            public Builder setName(int i, NamePart.Builder builder) {
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureNameIsMutable();
                    this.name_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addName(NamePart namePart) {
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    namePart.getClass();
                    ensureNameIsMutable();
                    this.name_.add(namePart);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(namePart);
                }
                return this;
            }

            public Builder addName(int i, NamePart namePart) {
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    namePart.getClass();
                    ensureNameIsMutable();
                    this.name_.add(i, namePart);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, namePart);
                }
                return this;
            }

            public Builder addName(NamePart.Builder builder) {
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureNameIsMutable();
                    this.name_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addName(int i, NamePart.Builder builder) {
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureNameIsMutable();
                    this.name_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllName(Iterable<? extends NamePart> iterable) {
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureNameIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.name_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearName() {
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.name_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeName(int i) {
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureNameIsMutable();
                    this.name_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public NamePart.Builder getNameBuilder(int i) {
                return (NamePart.Builder) getNameFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public NamePartOrBuilder getNameOrBuilder(int i) {
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.name_.get(i);
                }
                return (NamePartOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public List<? extends NamePartOrBuilder> getNameOrBuilderList() {
                RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> repeatedFieldBuilder = this.nameBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.name_);
            }

            public NamePart.Builder addNameBuilder() {
                return (NamePart.Builder) getNameFieldBuilder().addBuilder(NamePart.getDefaultInstance());
            }

            public NamePart.Builder addNameBuilder(int i) {
                return (NamePart.Builder) getNameFieldBuilder().addBuilder(i, NamePart.getDefaultInstance());
            }

            public List<NamePart.Builder> getNameBuilderList() {
                return getNameFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<NamePart, NamePart.Builder, NamePartOrBuilder> getNameFieldBuilder() {
                if (this.nameBuilder_ == null) {
                    this.nameBuilder_ = new RepeatedFieldBuilder<>(this.name_, (this.bitField0_ & 1) == 1, getParentForChildren(), isClean());
                    this.name_ = null;
                }
                return this.nameBuilder_;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public boolean hasIdentifierValue() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public String getIdentifierValue() {
                Object obj = this.identifierValue_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.identifierValue_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public ByteString getIdentifierValueBytes() {
                Object obj = this.identifierValue_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.identifierValue_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setIdentifierValue(String str) {
                str.getClass();
                this.bitField0_ |= 2;
                this.identifierValue_ = str;
                onChanged();
                return this;
            }

            public Builder clearIdentifierValue() {
                this.bitField0_ &= -3;
                this.identifierValue_ = UninterpretedOption.getDefaultInstance().getIdentifierValue();
                onChanged();
                return this;
            }

            public Builder setIdentifierValueBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 2;
                this.identifierValue_ = byteString;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public boolean hasPositiveIntValue() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public long getPositiveIntValue() {
                return this.positiveIntValue_;
            }

            public Builder setPositiveIntValue(long j) {
                this.bitField0_ |= 4;
                this.positiveIntValue_ = j;
                onChanged();
                return this;
            }

            public Builder clearPositiveIntValue() {
                this.bitField0_ &= -5;
                this.positiveIntValue_ = 0L;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public boolean hasNegativeIntValue() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public long getNegativeIntValue() {
                return this.negativeIntValue_;
            }

            public Builder setNegativeIntValue(long j) {
                this.bitField0_ |= 8;
                this.negativeIntValue_ = j;
                onChanged();
                return this;
            }

            public Builder clearNegativeIntValue() {
                this.bitField0_ &= -9;
                this.negativeIntValue_ = 0L;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public boolean hasDoubleValue() {
                return (this.bitField0_ & 16) == 16;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public double getDoubleValue() {
                return this.doubleValue_;
            }

            public Builder setDoubleValue(double d) {
                this.bitField0_ |= 16;
                this.doubleValue_ = d;
                onChanged();
                return this;
            }

            public Builder clearDoubleValue() {
                this.bitField0_ &= -17;
                this.doubleValue_ = 0.0d;
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public boolean hasStringValue() {
                return (this.bitField0_ & 32) == 32;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public ByteString getStringValue() {
                return this.stringValue_;
            }

            public Builder setStringValue(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 32;
                this.stringValue_ = byteString;
                onChanged();
                return this;
            }

            public Builder clearStringValue() {
                this.bitField0_ &= -33;
                this.stringValue_ = UninterpretedOption.getDefaultInstance().getStringValue();
                onChanged();
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public boolean hasAggregateValue() {
                return (this.bitField0_ & 64) == 64;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public String getAggregateValue() {
                Object obj = this.aggregateValue_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.aggregateValue_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.UninterpretedOptionOrBuilder
            public ByteString getAggregateValueBytes() {
                Object obj = this.aggregateValue_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.aggregateValue_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setAggregateValue(String str) {
                str.getClass();
                this.bitField0_ |= 64;
                this.aggregateValue_ = str;
                onChanged();
                return this;
            }

            public Builder clearAggregateValue() {
                this.bitField0_ &= -65;
                this.aggregateValue_ = UninterpretedOption.getDefaultInstance().getAggregateValue();
                onChanged();
                return this;
            }

            public Builder setAggregateValueBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 64;
                this.aggregateValue_ = byteString;
                onChanged();
                return this;
            }
        }
    }

    public static final class SourceCodeInfo extends GeneratedMessage implements SourceCodeInfoOrBuilder {
        public static final int LOCATION_FIELD_NUMBER = 1;
        public static Parser<SourceCodeInfo> PARSER = new AbstractParser<SourceCodeInfo>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public SourceCodeInfo parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new SourceCodeInfo(codedInputStream, extensionRegistryLite);
            }
        };
        private static final SourceCodeInfo defaultInstance;
        private static final long serialVersionUID = 0;
        private List<Location> location_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private final UnknownFieldSet unknownFields;

        public interface LocationOrBuilder extends MessageOrBuilder {
            String getLeadingComments();

            ByteString getLeadingCommentsBytes();

            int getPath(int i);

            int getPathCount();

            List<Integer> getPathList();

            int getSpan(int i);

            int getSpanCount();

            List<Integer> getSpanList();

            String getTrailingComments();

            ByteString getTrailingCommentsBytes();

            boolean hasLeadingComments();

            boolean hasTrailingComments();
        }

        private SourceCodeInfo(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private SourceCodeInfo(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static SourceCodeInfo getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public SourceCodeInfo getDefaultInstanceForType() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SourceCodeInfo(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z = false;
            boolean z2 = false;
            while (!z) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag != 10) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                if (!z2) {
                                    this.location_ = new ArrayList();
                                    z2 = true;
                                }
                                this.location_.add((Location) codedInputStream.readMessage(Location.PARSER, extensionRegistryLite));
                            }
                        }
                        z = true;
                    } catch (InvalidProtocolBufferException e) {
                        throw e.setUnfinishedMessage(this);
                    } catch (IOException e2) {
                        throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if (z2) {
                        this.location_ = Collections.unmodifiableList(this.location_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if (z2) {
                this.location_ = Collections.unmodifiableList(this.location_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_descriptor;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(SourceCodeInfo.class, Builder.class);
        }

        static {
            SourceCodeInfo sourceCodeInfo = new SourceCodeInfo(true);
            defaultInstance = sourceCodeInfo;
            sourceCodeInfo.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<SourceCodeInfo> getParserForType() {
            return PARSER;
        }

        public static final class Location extends GeneratedMessage implements LocationOrBuilder {
            public static final int LEADING_COMMENTS_FIELD_NUMBER = 3;
            public static Parser<Location> PARSER = new AbstractParser<Location>() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.Location.1
                @Override // org.jetbrains.kotlin.protobuf.Parser
                public Location parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                    return new Location(codedInputStream, extensionRegistryLite);
                }
            };
            public static final int PATH_FIELD_NUMBER = 1;
            public static final int SPAN_FIELD_NUMBER = 2;
            public static final int TRAILING_COMMENTS_FIELD_NUMBER = 4;
            private static final Location defaultInstance;
            private static final long serialVersionUID = 0;
            private int bitField0_;
            private Object leadingComments_;
            private byte memoizedIsInitialized;
            private int memoizedSerializedSize;
            private int pathMemoizedSerializedSize;
            private List<Integer> path_;
            private int spanMemoizedSerializedSize;
            private List<Integer> span_;
            private Object trailingComments_;
            private final UnknownFieldSet unknownFields;

            private Location(GeneratedMessage.Builder<?> builder) {
                super(builder);
                this.pathMemoizedSerializedSize = -1;
                this.spanMemoizedSerializedSize = -1;
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = builder.getUnknownFields();
            }

            private Location(boolean z) {
                this.pathMemoizedSerializedSize = -1;
                this.spanMemoizedSerializedSize = -1;
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = UnknownFieldSet.getDefaultInstance();
            }

            public static Location getDefaultInstance() {
                return defaultInstance;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public Location getDefaultInstanceForType() {
                return defaultInstance;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }

            private Location(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                this.pathMemoizedSerializedSize = -1;
                this.spanMemoizedSerializedSize = -1;
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                initFields();
                UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
                boolean z = false;
                int i = 0;
                while (!z) {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 8) {
                                    if ((i & 1) != 1) {
                                        this.path_ = new ArrayList();
                                        i |= 1;
                                    }
                                    this.path_.add(Integer.valueOf(codedInputStream.readInt32()));
                                } else if (tag == 10) {
                                    int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                    if ((i & 1) != 1 && codedInputStream.getBytesUntilLimit() > 0) {
                                        this.path_ = new ArrayList();
                                        i |= 1;
                                    }
                                    while (codedInputStream.getBytesUntilLimit() > 0) {
                                        this.path_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    }
                                    codedInputStream.popLimit(iPushLimit);
                                } else if (tag == 16) {
                                    if ((i & 2) != 2) {
                                        this.span_ = new ArrayList();
                                        i |= 2;
                                    }
                                    this.span_.add(Integer.valueOf(codedInputStream.readInt32()));
                                } else if (tag == 18) {
                                    int iPushLimit2 = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                                    if ((i & 2) != 2 && codedInputStream.getBytesUntilLimit() > 0) {
                                        this.span_ = new ArrayList();
                                        i |= 2;
                                    }
                                    while (codedInputStream.getBytesUntilLimit() > 0) {
                                        this.span_.add(Integer.valueOf(codedInputStream.readInt32()));
                                    }
                                    codedInputStream.popLimit(iPushLimit2);
                                } else if (tag == 26) {
                                    ByteString bytes = codedInputStream.readBytes();
                                    this.bitField0_ |= 1;
                                    this.leadingComments_ = bytes;
                                } else if (tag != 34) {
                                    if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    ByteString bytes2 = codedInputStream.readBytes();
                                    this.bitField0_ |= 2;
                                    this.trailingComments_ = bytes2;
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw e.setUnfinishedMessage(this);
                        } catch (IOException e2) {
                            throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                        }
                    } catch (Throwable th) {
                        if ((i & 1) == 1) {
                            this.path_ = Collections.unmodifiableList(this.path_);
                        }
                        if ((i & 2) == 2) {
                            this.span_ = Collections.unmodifiableList(this.span_);
                        }
                        this.unknownFields = builderNewBuilder.build();
                        makeExtensionsImmutable();
                        throw th;
                    }
                }
                if ((i & 1) == 1) {
                    this.path_ = Collections.unmodifiableList(this.path_);
                }
                if ((i & 2) == 2) {
                    this.span_ = Collections.unmodifiableList(this.span_);
                }
                this.unknownFields = builderNewBuilder.build();
                makeExtensionsImmutable();
            }

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_Location_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_Location_fieldAccessorTable.ensureFieldAccessorsInitialized(Location.class, Builder.class);
            }

            static {
                Location location = new Location(true);
                defaultInstance = location;
                location.initFields();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.MessageLite
            public Parser<Location> getParserForType() {
                return PARSER;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
            public List<Integer> getPathList() {
                return this.path_;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
            public int getPathCount() {
                return this.path_.size();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
            public int getPath(int i) {
                return this.path_.get(i).intValue();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
            public List<Integer> getSpanList() {
                return this.span_;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
            public int getSpanCount() {
                return this.span_.size();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
            public int getSpan(int i) {
                return this.span_.get(i).intValue();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
            public boolean hasLeadingComments() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
            public String getLeadingComments() {
                Object obj = this.leadingComments_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                ByteString byteString = (ByteString) obj;
                String stringUtf8 = byteString.toStringUtf8();
                if (byteString.isValidUtf8()) {
                    this.leadingComments_ = stringUtf8;
                }
                return stringUtf8;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
            public ByteString getLeadingCommentsBytes() {
                Object obj = this.leadingComments_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.leadingComments_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
            public boolean hasTrailingComments() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
            public String getTrailingComments() {
                Object obj = this.trailingComments_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                ByteString byteString = (ByteString) obj;
                String stringUtf8 = byteString.toStringUtf8();
                if (byteString.isValidUtf8()) {
                    this.trailingComments_ = stringUtf8;
                }
                return stringUtf8;
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
            public ByteString getTrailingCommentsBytes() {
                Object obj = this.trailingComments_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.trailingComments_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            private void initFields() {
                this.path_ = Collections.emptyList();
                this.span_ = Collections.emptyList();
                this.leadingComments_ = "";
                this.trailingComments_ = "";
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
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

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
            public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
                getSerializedSize();
                if (getPathList().size() > 0) {
                    codedOutputStream.writeRawVarint32(10);
                    codedOutputStream.writeRawVarint32(this.pathMemoizedSerializedSize);
                }
                for (int i = 0; i < this.path_.size(); i++) {
                    codedOutputStream.writeInt32NoTag(this.path_.get(i).intValue());
                }
                if (getSpanList().size() > 0) {
                    codedOutputStream.writeRawVarint32(18);
                    codedOutputStream.writeRawVarint32(this.spanMemoizedSerializedSize);
                }
                for (int i2 = 0; i2 < this.span_.size(); i2++) {
                    codedOutputStream.writeInt32NoTag(this.span_.get(i2).intValue());
                }
                if ((this.bitField0_ & 1) == 1) {
                    codedOutputStream.writeBytes(3, getLeadingCommentsBytes());
                }
                if ((this.bitField0_ & 2) == 2) {
                    codedOutputStream.writeBytes(4, getTrailingCommentsBytes());
                }
                getUnknownFields().writeTo(codedOutputStream);
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
            public int getSerializedSize() {
                int i = this.memoizedSerializedSize;
                if (i != -1) {
                    return i;
                }
                int iComputeInt32SizeNoTag = 0;
                for (int i2 = 0; i2 < this.path_.size(); i2++) {
                    iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(this.path_.get(i2).intValue());
                }
                int iComputeInt32SizeNoTag2 = !getPathList().isEmpty() ? iComputeInt32SizeNoTag + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag) : iComputeInt32SizeNoTag;
                this.pathMemoizedSerializedSize = iComputeInt32SizeNoTag;
                int iComputeInt32SizeNoTag3 = 0;
                for (int i3 = 0; i3 < this.span_.size(); i3++) {
                    iComputeInt32SizeNoTag3 += CodedOutputStream.computeInt32SizeNoTag(this.span_.get(i3).intValue());
                }
                int iComputeBytesSize = iComputeInt32SizeNoTag2 + iComputeInt32SizeNoTag3;
                if (!getSpanList().isEmpty()) {
                    iComputeBytesSize = iComputeBytesSize + 1 + CodedOutputStream.computeInt32SizeNoTag(iComputeInt32SizeNoTag3);
                }
                this.spanMemoizedSerializedSize = iComputeInt32SizeNoTag3;
                if ((this.bitField0_ & 1) == 1) {
                    iComputeBytesSize += CodedOutputStream.computeBytesSize(3, getLeadingCommentsBytes());
                }
                if ((this.bitField0_ & 2) == 2) {
                    iComputeBytesSize += CodedOutputStream.computeBytesSize(4, getTrailingCommentsBytes());
                }
                int serializedSize = iComputeBytesSize + getUnknownFields().getSerializedSize();
                this.memoizedSerializedSize = serializedSize;
                return serializedSize;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
            protected Object writeReplace() throws ObjectStreamException {
                return super.writeReplace();
            }

            public static Location parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(byteString);
            }

            public static Location parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(byteString, extensionRegistryLite);
            }

            public static Location parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(bArr);
            }

            public static Location parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(bArr, extensionRegistryLite);
            }

            public static Location parseFrom(InputStream inputStream) throws IOException {
                return PARSER.parseFrom(inputStream);
            }

            public static Location parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return PARSER.parseFrom(inputStream, extensionRegistryLite);
            }

            public static Location parseDelimitedFrom(InputStream inputStream) throws IOException {
                return PARSER.parseDelimitedFrom(inputStream);
            }

            public static Location parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
            }

            public static Location parseFrom(CodedInputStream codedInputStream) throws IOException {
                return PARSER.parseFrom(codedInputStream);
            }

            public static Location parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
            }

            public static Builder newBuilder() {
                return Builder.create();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite
            public Builder newBuilderForType() {
                return newBuilder();
            }

            public static Builder newBuilder(Location location) {
                return newBuilder().mergeFrom(location);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite
            public Builder toBuilder() {
                return newBuilder(this);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
            public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
                return new Builder(builderParent);
            }

            public static final class Builder extends GeneratedMessage.Builder<Builder> implements LocationOrBuilder {
                private int bitField0_;
                private Object leadingComments_;
                private List<Integer> path_;
                private List<Integer> span_;
                private Object trailingComments_;

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
                public final boolean isInitialized() {
                    return true;
                }

                public static final Descriptors.Descriptor getDescriptor() {
                    return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_Location_descriptor;
                }

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
                protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                    return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_Location_fieldAccessorTable.ensureFieldAccessorsInitialized(Location.class, Builder.class);
                }

                private Builder() {
                    this.path_ = Collections.emptyList();
                    this.span_ = Collections.emptyList();
                    this.leadingComments_ = "";
                    this.trailingComments_ = "";
                    maybeForceBuilderInitialization();
                }

                private Builder(GeneratedMessage.BuilderParent builderParent) {
                    super(builderParent);
                    this.path_ = Collections.emptyList();
                    this.span_ = Collections.emptyList();
                    this.leadingComments_ = "";
                    this.trailingComments_ = "";
                    maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                    boolean z = GeneratedMessage.alwaysUseFieldBuilders;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public static Builder create() {
                    return new Builder();
                }

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public Builder clear() {
                    super.clear();
                    this.path_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                    this.span_ = Collections.emptyList();
                    int i = this.bitField0_;
                    this.leadingComments_ = "";
                    this.trailingComments_ = "";
                    this.bitField0_ = i & (-15);
                    return this;
                }

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public Builder clone() {
                    return create().mergeFrom(buildPartial());
                }

                @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
                public Descriptors.Descriptor getDescriptorForType() {
                    return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_Location_descriptor;
                }

                @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
                public Location getDefaultInstanceForType() {
                    return Location.getDefaultInstance();
                }

                @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public Location build() {
                    Location locationBuildPartial = buildPartial();
                    if (locationBuildPartial.isInitialized()) {
                        return locationBuildPartial;
                    }
                    throw newUninitializedMessageException((Message) locationBuildPartial);
                }

                @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public Location buildPartial() {
                    Location location = new Location(this);
                    int i = this.bitField0_;
                    if ((i & 1) == 1) {
                        this.path_ = Collections.unmodifiableList(this.path_);
                        this.bitField0_ &= -2;
                    }
                    location.path_ = this.path_;
                    if ((this.bitField0_ & 2) == 2) {
                        this.span_ = Collections.unmodifiableList(this.span_);
                        this.bitField0_ &= -3;
                    }
                    location.span_ = this.span_;
                    int i2 = (i & 4) != 4 ? 0 : 1;
                    location.leadingComments_ = this.leadingComments_;
                    if ((i & 8) == 8) {
                        i2 |= 2;
                    }
                    location.trailingComments_ = this.trailingComments_;
                    location.bitField0_ = i2;
                    onBuilt();
                    return location;
                }

                @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
                public Builder mergeFrom(Message message) {
                    if (message instanceof Location) {
                        return mergeFrom((Location) message);
                    }
                    super.mergeFrom(message);
                    return this;
                }

                public Builder mergeFrom(Location location) {
                    if (location == Location.getDefaultInstance()) {
                        return this;
                    }
                    if (!location.path_.isEmpty()) {
                        if (this.path_.isEmpty()) {
                            this.path_ = location.path_;
                            this.bitField0_ &= -2;
                        } else {
                            ensurePathIsMutable();
                            this.path_.addAll(location.path_);
                        }
                        onChanged();
                    }
                    if (!location.span_.isEmpty()) {
                        if (this.span_.isEmpty()) {
                            this.span_ = location.span_;
                            this.bitField0_ &= -3;
                        } else {
                            ensureSpanIsMutable();
                            this.span_.addAll(location.span_);
                        }
                        onChanged();
                    }
                    if (location.hasLeadingComments()) {
                        this.bitField0_ |= 4;
                        this.leadingComments_ = location.leadingComments_;
                        onChanged();
                    }
                    if (location.hasTrailingComments()) {
                        this.bitField0_ |= 8;
                        this.trailingComments_ = location.trailingComments_;
                        onChanged();
                    }
                    mergeUnknownFields(location.getUnknownFields());
                    return this;
                }

                /* JADX WARN: Code duplicated, block: B:15:0x001d  */
                @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
                public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                    Location location = null;
                    try {
                        try {
                            Location partialFrom = Location.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                            if (partialFrom != null) {
                                mergeFrom(partialFrom);
                            }
                            return this;
                        } catch (InvalidProtocolBufferException e) {
                            Location location2 = (Location) e.getUnfinishedMessage();
                            try {
                                throw e;
                            } catch (Throwable th) {
                                th = th;
                                location = location2;
                                if (location != null) {
                                    mergeFrom(location);
                                }
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (location != null) {
                            mergeFrom(location);
                        }
                        throw th;
                    }
                }

                private void ensurePathIsMutable() {
                    if ((this.bitField0_ & 1) != 1) {
                        this.path_ = new ArrayList(this.path_);
                        this.bitField0_ |= 1;
                    }
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
                public List<Integer> getPathList() {
                    return Collections.unmodifiableList(this.path_);
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
                public int getPathCount() {
                    return this.path_.size();
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
                public int getPath(int i) {
                    return this.path_.get(i).intValue();
                }

                public Builder setPath(int i, int i2) {
                    ensurePathIsMutable();
                    this.path_.set(i, Integer.valueOf(i2));
                    onChanged();
                    return this;
                }

                public Builder addPath(int i) {
                    ensurePathIsMutable();
                    this.path_.add(Integer.valueOf(i));
                    onChanged();
                    return this;
                }

                public Builder addAllPath(Iterable<? extends Integer> iterable) {
                    ensurePathIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.path_);
                    onChanged();
                    return this;
                }

                public Builder clearPath() {
                    this.path_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                    onChanged();
                    return this;
                }

                private void ensureSpanIsMutable() {
                    if ((this.bitField0_ & 2) != 2) {
                        this.span_ = new ArrayList(this.span_);
                        this.bitField0_ |= 2;
                    }
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
                public List<Integer> getSpanList() {
                    return Collections.unmodifiableList(this.span_);
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
                public int getSpanCount() {
                    return this.span_.size();
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
                public int getSpan(int i) {
                    return this.span_.get(i).intValue();
                }

                public Builder setSpan(int i, int i2) {
                    ensureSpanIsMutable();
                    this.span_.set(i, Integer.valueOf(i2));
                    onChanged();
                    return this;
                }

                public Builder addSpan(int i) {
                    ensureSpanIsMutable();
                    this.span_.add(Integer.valueOf(i));
                    onChanged();
                    return this;
                }

                public Builder addAllSpan(Iterable<? extends Integer> iterable) {
                    ensureSpanIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.span_);
                    onChanged();
                    return this;
                }

                public Builder clearSpan() {
                    this.span_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                    return this;
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
                public boolean hasLeadingComments() {
                    return (this.bitField0_ & 4) == 4;
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
                public String getLeadingComments() {
                    Object obj = this.leadingComments_;
                    if (!(obj instanceof String)) {
                        ByteString byteString = (ByteString) obj;
                        String stringUtf8 = byteString.toStringUtf8();
                        if (byteString.isValidUtf8()) {
                            this.leadingComments_ = stringUtf8;
                        }
                        return stringUtf8;
                    }
                    return (String) obj;
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
                public ByteString getLeadingCommentsBytes() {
                    Object obj = this.leadingComments_;
                    if (obj instanceof String) {
                        ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                        this.leadingComments_ = byteStringCopyFromUtf8;
                        return byteStringCopyFromUtf8;
                    }
                    return (ByteString) obj;
                }

                public Builder setLeadingComments(String str) {
                    str.getClass();
                    this.bitField0_ |= 4;
                    this.leadingComments_ = str;
                    onChanged();
                    return this;
                }

                public Builder clearLeadingComments() {
                    this.bitField0_ &= -5;
                    this.leadingComments_ = Location.getDefaultInstance().getLeadingComments();
                    onChanged();
                    return this;
                }

                public Builder setLeadingCommentsBytes(ByteString byteString) {
                    byteString.getClass();
                    this.bitField0_ |= 4;
                    this.leadingComments_ = byteString;
                    onChanged();
                    return this;
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
                public boolean hasTrailingComments() {
                    return (this.bitField0_ & 8) == 8;
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
                public String getTrailingComments() {
                    Object obj = this.trailingComments_;
                    if (!(obj instanceof String)) {
                        ByteString byteString = (ByteString) obj;
                        String stringUtf8 = byteString.toStringUtf8();
                        if (byteString.isValidUtf8()) {
                            this.trailingComments_ = stringUtf8;
                        }
                        return stringUtf8;
                    }
                    return (String) obj;
                }

                @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfo.LocationOrBuilder
                public ByteString getTrailingCommentsBytes() {
                    Object obj = this.trailingComments_;
                    if (obj instanceof String) {
                        ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                        this.trailingComments_ = byteStringCopyFromUtf8;
                        return byteStringCopyFromUtf8;
                    }
                    return (ByteString) obj;
                }

                public Builder setTrailingComments(String str) {
                    str.getClass();
                    this.bitField0_ |= 8;
                    this.trailingComments_ = str;
                    onChanged();
                    return this;
                }

                public Builder clearTrailingComments() {
                    this.bitField0_ &= -9;
                    this.trailingComments_ = Location.getDefaultInstance().getTrailingComments();
                    onChanged();
                    return this;
                }

                public Builder setTrailingCommentsBytes(ByteString byteString) {
                    byteString.getClass();
                    this.bitField0_ |= 8;
                    this.trailingComments_ = byteString;
                    onChanged();
                    return this;
                }
            }
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfoOrBuilder
        public List<Location> getLocationList() {
            return this.location_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfoOrBuilder
        public List<? extends LocationOrBuilder> getLocationOrBuilderList() {
            return this.location_;
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfoOrBuilder
        public int getLocationCount() {
            return this.location_.size();
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfoOrBuilder
        public Location getLocation(int i) {
            return this.location_.get(i);
        }

        @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfoOrBuilder
        public LocationOrBuilder getLocationOrBuilder(int i) {
            return this.location_.get(i);
        }

        private void initFields() {
            this.location_ = Collections.emptyList();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage, org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
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

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            getSerializedSize();
            for (int i = 0; i < this.location_.size(); i++) {
                codedOutputStream.writeMessage(1, this.location_.get(i));
            }
            getUnknownFields().writeTo(codedOutputStream);
        }

        @Override // org.jetbrains.kotlin.protobuf.AbstractMessage, org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeMessageSize = 0;
            for (int i2 = 0; i2 < this.location_.size(); i2++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.location_.get(i2));
            }
            int serializedSize = iComputeMessageSize + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static SourceCodeInfo parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static SourceCodeInfo parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static SourceCodeInfo parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static SourceCodeInfo parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static SourceCodeInfo parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static SourceCodeInfo parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static SourceCodeInfo parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static SourceCodeInfo parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static SourceCodeInfo parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static SourceCodeInfo parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(SourceCodeInfo sourceCodeInfo) {
            return newBuilder().mergeFrom(sourceCodeInfo);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage
        public Builder newBuilderForType(GeneratedMessage.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static final class Builder extends GeneratedMessage.Builder<Builder> implements SourceCodeInfoOrBuilder {
            private int bitField0_;
            private RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> locationBuilder_;
            private List<Location> location_;

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            public static final Descriptors.Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder
            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(SourceCodeInfo.class, Builder.class);
            }

            private Builder() {
                this.location_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent builderParent) {
                super(builderParent);
                this.location_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getLocationFieldBuilder();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clear() {
                super.clear();
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.location_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder, org.jetbrains.kotlin.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_descriptor;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public SourceCodeInfo getDefaultInstanceForType() {
                return SourceCodeInfo.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public SourceCodeInfo build() {
                SourceCodeInfo sourceCodeInfoBuildPartial = buildPartial();
                if (sourceCodeInfoBuildPartial.isInitialized()) {
                    return sourceCodeInfoBuildPartial;
                }
                throw newUninitializedMessageException((Message) sourceCodeInfoBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public SourceCodeInfo buildPartial() {
                SourceCodeInfo sourceCodeInfo = new SourceCodeInfo(this);
                int i = this.bitField0_;
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    if ((i & 1) == 1) {
                        this.location_ = Collections.unmodifiableList(this.location_);
                        this.bitField0_ &= -2;
                    }
                    sourceCodeInfo.location_ = this.location_;
                } else {
                    sourceCodeInfo.location_ = repeatedFieldBuilder.build();
                }
                onBuilt();
                return sourceCodeInfo;
            }

            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof SourceCodeInfo) {
                    return mergeFrom((SourceCodeInfo) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(SourceCodeInfo sourceCodeInfo) {
                if (sourceCodeInfo == SourceCodeInfo.getDefaultInstance()) {
                    return this;
                }
                if (this.locationBuilder_ == null) {
                    if (!sourceCodeInfo.location_.isEmpty()) {
                        if (this.location_.isEmpty()) {
                            this.location_ = sourceCodeInfo.location_;
                            this.bitField0_ &= -2;
                        } else {
                            ensureLocationIsMutable();
                            this.location_.addAll(sourceCodeInfo.location_);
                        }
                        onChanged();
                    }
                } else if (!sourceCodeInfo.location_.isEmpty()) {
                    if (!this.locationBuilder_.isEmpty()) {
                        this.locationBuilder_.addAllMessages(sourceCodeInfo.location_);
                    } else {
                        this.locationBuilder_.dispose();
                        this.locationBuilder_ = null;
                        this.location_ = sourceCodeInfo.location_;
                        this.bitField0_ &= -2;
                        this.locationBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getLocationFieldBuilder() : null;
                    }
                }
                mergeUnknownFields(sourceCodeInfo.getUnknownFields());
                return this;
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessage.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                SourceCodeInfo sourceCodeInfo = null;
                try {
                    try {
                        SourceCodeInfo partialFrom = SourceCodeInfo.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        SourceCodeInfo sourceCodeInfo2 = (SourceCodeInfo) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            sourceCodeInfo = sourceCodeInfo2;
                            if (sourceCodeInfo != null) {
                                mergeFrom(sourceCodeInfo);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (sourceCodeInfo != null) {
                        mergeFrom(sourceCodeInfo);
                    }
                    throw th;
                }
            }

            private void ensureLocationIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.location_ = new ArrayList(this.location_);
                    this.bitField0_ |= 1;
                }
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfoOrBuilder
            public List<Location> getLocationList() {
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    return Collections.unmodifiableList(this.location_);
                }
                return repeatedFieldBuilder.getMessageList();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfoOrBuilder
            public int getLocationCount() {
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.location_.size();
                }
                return repeatedFieldBuilder.getCount();
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfoOrBuilder
            public Location getLocation(int i) {
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.location_.get(i);
                }
                return (Location) repeatedFieldBuilder.getMessage(i);
            }

            public Builder setLocation(int i, Location location) {
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    location.getClass();
                    ensureLocationIsMutable();
                    this.location_.set(i, location);
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, location);
                }
                return this;
            }

            public Builder setLocation(int i, Location.Builder builder) {
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureLocationIsMutable();
                    this.location_.set(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.setMessage(i, builder.build());
                }
                return this;
            }

            public Builder addLocation(Location location) {
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    location.getClass();
                    ensureLocationIsMutable();
                    this.location_.add(location);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(location);
                }
                return this;
            }

            public Builder addLocation(int i, Location location) {
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    location.getClass();
                    ensureLocationIsMutable();
                    this.location_.add(i, location);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, location);
                }
                return this;
            }

            public Builder addLocation(Location.Builder builder) {
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureLocationIsMutable();
                    this.location_.add(builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(builder.build());
                }
                return this;
            }

            public Builder addLocation(int i, Location.Builder builder) {
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureLocationIsMutable();
                    this.location_.add(i, builder.build());
                    onChanged();
                } else {
                    repeatedFieldBuilder.addMessage(i, builder.build());
                }
                return this;
            }

            public Builder addAllLocation(Iterable<? extends Location> iterable) {
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureLocationIsMutable();
                    AbstractMessageLite.Builder.addAll(iterable, this.location_);
                    onChanged();
                } else {
                    repeatedFieldBuilder.addAllMessages(iterable);
                }
                return this;
            }

            public Builder clearLocation() {
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    this.location_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                    onChanged();
                } else {
                    repeatedFieldBuilder.clear();
                }
                return this;
            }

            public Builder removeLocation(int i) {
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    ensureLocationIsMutable();
                    this.location_.remove(i);
                    onChanged();
                } else {
                    repeatedFieldBuilder.remove(i);
                }
                return this;
            }

            public Location.Builder getLocationBuilder(int i) {
                return (Location.Builder) getLocationFieldBuilder().getBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfoOrBuilder
            public LocationOrBuilder getLocationOrBuilder(int i) {
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder == null) {
                    return this.location_.get(i);
                }
                return (LocationOrBuilder) repeatedFieldBuilder.getMessageOrBuilder(i);
            }

            @Override // org.jetbrains.kotlin.protobuf.DescriptorProtos.SourceCodeInfoOrBuilder
            public List<? extends LocationOrBuilder> getLocationOrBuilderList() {
                RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> repeatedFieldBuilder = this.locationBuilder_;
                if (repeatedFieldBuilder != null) {
                    return repeatedFieldBuilder.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.location_);
            }

            public Location.Builder addLocationBuilder() {
                return (Location.Builder) getLocationFieldBuilder().addBuilder(Location.getDefaultInstance());
            }

            public Location.Builder addLocationBuilder(int i) {
                return (Location.Builder) getLocationFieldBuilder().addBuilder(i, Location.getDefaultInstance());
            }

            public List<Location.Builder> getLocationBuilderList() {
                return getLocationFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<Location, Location.Builder, LocationOrBuilder> getLocationFieldBuilder() {
                if (this.locationBuilder_ == null) {
                    this.locationBuilder_ = new RepeatedFieldBuilder<>(this.location_, (this.bitField0_ & 1) == 1, getParentForChildren(), isClean());
                    this.location_ = null;
                }
                return this.locationBuilder_;
            }
        }
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n google/protobuf/descriptor.proto\u0012\u000fgoogle.protobuf\"G\n\u0011FileDescriptorSet\u00122\n\u0004file\u0018\u0001 \u0003(\u000b2$.google.protobuf.FileDescriptorProto\"Ë\u0003\n\u0013FileDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u000f\n\u0007package\u0018\u0002 \u0001(\t\u0012\u0012\n\ndependency\u0018\u0003 \u0003(\t\u0012\u0019\n\u0011public_dependency\u0018\n \u0003(\u0005\u0012\u0017\n\u000fweak_dependency\u0018\u000b \u0003(\u0005\u00126\n\fmessage_type\u0018\u0004 \u0003(\u000b2 .google.protobuf.DescriptorProto\u00127\n\tenum_type\u0018\u0005 \u0003(\u000b2$.google.protobuf.EnumDescriptorProto\u00128\n\u0007service\u0018\u0006 \u0003(\u000b2'.google.protobuf.", "ServiceDescriptorProto\u00128\n\textension\u0018\u0007 \u0003(\u000b2%.google.protobuf.FieldDescriptorProto\u0012-\n\u0007options\u0018\b \u0001(\u000b2\u001c.google.protobuf.FileOptions\u00129\n\u0010source_code_info\u0018\t \u0001(\u000b2\u001f.google.protobuf.SourceCodeInfo\"ä\u0003\n\u000fDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u00124\n\u0005field\u0018\u0002 \u0003(\u000b2%.google.protobuf.FieldDescriptorProto\u00128\n\textension\u0018\u0006 \u0003(\u000b2%.google.protobuf.FieldDescriptorProto\u00125\n\u000bnested_type\u0018\u0003 \u0003(\u000b2 .google.protobuf.DescriptorProto\u00127\n\tenum_type", "\u0018\u0004 \u0003(\u000b2$.google.protobuf.EnumDescriptorProto\u0012H\n\u000fextension_range\u0018\u0005 \u0003(\u000b2/.google.protobuf.DescriptorProto.ExtensionRange\u00129\n\noneof_decl\u0018\b \u0003(\u000b2%.google.protobuf.OneofDescriptorProto\u00120\n\u0007options\u0018\u0007 \u0001(\u000b2\u001f.google.protobuf.MessageOptions\u001a,\n\u000eExtensionRange\u0012\r\n\u0005start\u0018\u0001 \u0001(\u0005\u0012\u000b\n\u0003end\u0018\u0002 \u0001(\u0005\"©\u0005\n\u0014FieldDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u000e\n\u0006number\u0018\u0003 \u0001(\u0005\u0012:\n\u0005label\u0018\u0004 \u0001(\u000e2+.google.protobuf.FieldDescriptorProto.Label\u00128\n\u0004type\u0018\u0005 \u0001", "(\u000e2*.google.protobuf.FieldDescriptorProto.Type\u0012\u0011\n\ttype_name\u0018\u0006 \u0001(\t\u0012\u0010\n\bextendee\u0018\u0002 \u0001(\t\u0012\u0015\n\rdefault_value\u0018\u0007 \u0001(\t\u0012\u0013\n\u000boneof_index\u0018\t \u0001(\u0005\u0012.\n\u0007options\u0018\b \u0001(\u000b2\u001d.google.protobuf.FieldOptions\"¶\u0002\n\u0004Type\u0012\u000f\n\u000bTYPE_DOUBLE\u0010\u0001\u0012\u000e\n\nTYPE_FLOAT\u0010\u0002\u0012\u000e\n\nTYPE_INT64\u0010\u0003\u0012\u000f\n\u000bTYPE_UINT64\u0010\u0004\u0012\u000e\n\nTYPE_INT32\u0010\u0005\u0012\u0010\n\fTYPE_FIXED64\u0010\u0006\u0012\u0010\n\fTYPE_FIXED32\u0010\u0007\u0012\r\n\tTYPE_BOOL\u0010\b\u0012\u000f\n\u000bTYPE_STRING\u0010\t\u0012\u000e\n\nTYPE_GROUP\u0010\n\u0012\u0010\n\fTYPE_MESSAGE\u0010\u000b\u0012\u000e\n\nTYPE_BYTES\u0010\f\u0012\u000f\n\u000bTYPE_UINT32\u0010", "\r\u0012\r\n\tTYPE_ENUM\u0010\u000e\u0012\u0011\n\rTYPE_SFIXED32\u0010\u000f\u0012\u0011\n\rTYPE_SFIXED64\u0010\u0010\u0012\u000f\n\u000bTYPE_SINT32\u0010\u0011\u0012\u000f\n\u000bTYPE_SINT64\u0010\u0012\"C\n\u0005Label\u0012\u0012\n\u000eLABEL_OPTIONAL\u0010\u0001\u0012\u0012\n\u000eLABEL_REQUIRED\u0010\u0002\u0012\u0012\n\u000eLABEL_REPEATED\u0010\u0003\"$\n\u0014OneofDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\"\u008c\u0001\n\u0013EnumDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u00128\n\u0005value\u0018\u0002 \u0003(\u000b2).google.protobuf.EnumValueDescriptorProto\u0012-\n\u0007options\u0018\u0003 \u0001(\u000b2\u001c.google.protobuf.EnumOptions\"l\n\u0018EnumValueDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u000e\n\u0006number\u0018\u0002 \u0001(\u0005\u00122\n\u0007", "options\u0018\u0003 \u0001(\u000b2!.google.protobuf.EnumValueOptions\"\u0090\u0001\n\u0016ServiceDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u00126\n\u0006method\u0018\u0002 \u0003(\u000b2&.google.protobuf.MethodDescriptorProto\u00120\n\u0007options\u0018\u0003 \u0001(\u000b2\u001f.google.protobuf.ServiceOptions\"\u007f\n\u0015MethodDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u0012\n\ninput_type\u0018\u0002 \u0001(\t\u0012\u0013\n\u000boutput_type\u0018\u0003 \u0001(\t\u0012/\n\u0007options\u0018\u0004 \u0001(\u000b2\u001e.google.protobuf.MethodOptions\"«\u0004\n\u000bFileOptions\u0012\u0014\n\fjava_package\u0018\u0001 \u0001(\t\u0012\u001c\n\u0014java_outer_classname\u0018\b \u0001(\t\u0012\"\n\u0013java", "_multiple_files\u0018\n \u0001(\b:\u0005false\u0012,\n\u001djava_generate_equals_and_hash\u0018\u0014 \u0001(\b:\u0005false\u0012%\n\u0016java_string_check_utf8\u0018\u001b \u0001(\b:\u0005false\u0012F\n\foptimize_for\u0018\t \u0001(\u000e2).google.protobuf.FileOptions.OptimizeMode:\u0005SPEED\u0012\u0012\n\ngo_package\u0018\u000b \u0001(\t\u0012\"\n\u0013cc_generic_services\u0018\u0010 \u0001(\b:\u0005false\u0012$\n\u0015java_generic_services\u0018\u0011 \u0001(\b:\u0005false\u0012\"\n\u0013py_generic_services\u0018\u0012 \u0001(\b:\u0005false\u0012\u0019\n\ndeprecated\u0018\u0017 \u0001(\b:\u0005false\u0012C\n\u0014uninterpreted_option\u0018ç\u0007 \u0003(\u000b2$.google.protobuf.Uninterp", "retedOption\":\n\fOptimizeMode\u0012\t\n\u0005SPEED\u0010\u0001\u0012\r\n\tCODE_SIZE\u0010\u0002\u0012\u0010\n\fLITE_RUNTIME\u0010\u0003*\t\bè\u0007\u0010\u0080\u0080\u0080\u0080\u0002\"Ó\u0001\n\u000eMessageOptions\u0012&\n\u0017message_set_wire_format\u0018\u0001 \u0001(\b:\u0005false\u0012.\n\u001fno_standard_descriptor_accessor\u0018\u0002 \u0001(\b:\u0005false\u0012\u0019\n\ndeprecated\u0018\u0003 \u0001(\b:\u0005false\u0012C\n\u0014uninterpreted_option\u0018ç\u0007 \u0003(\u000b2$.google.protobuf.UninterpretedOption*\t\bè\u0007\u0010\u0080\u0080\u0080\u0080\u0002\"¾\u0002\n\fFieldOptions\u0012:\n\u0005ctype\u0018\u0001 \u0001(\u000e2#.google.protobuf.FieldOptions.CType:\u0006STRING\u0012\u000e\n\u0006packed\u0018\u0002 \u0001(\b\u0012\u0013\n\u0004lazy\u0018\u0005 ", "\u0001(\b:\u0005false\u0012\u0019\n\ndeprecated\u0018\u0003 \u0001(\b:\u0005false\u0012\u001c\n\u0014experimental_map_key\u0018\t \u0001(\t\u0012\u0013\n\u0004weak\u0018\n \u0001(\b:\u0005false\u0012C\n\u0014uninterpreted_option\u0018ç\u0007 \u0003(\u000b2$.google.protobuf.UninterpretedOption\"/\n\u0005CType\u0012\n\n\u0006STRING\u0010\u0000\u0012\b\n\u0004CORD\u0010\u0001\u0012\u0010\n\fSTRING_PIECE\u0010\u0002*\t\bè\u0007\u0010\u0080\u0080\u0080\u0080\u0002\"\u008d\u0001\n\u000bEnumOptions\u0012\u0013\n\u000ballow_alias\u0018\u0002 \u0001(\b\u0012\u0019\n\ndeprecated\u0018\u0003 \u0001(\b:\u0005false\u0012C\n\u0014uninterpreted_option\u0018ç\u0007 \u0003(\u000b2$.google.protobuf.UninterpretedOption*\t\bè\u0007\u0010\u0080\u0080\u0080\u0080\u0002\"}\n\u0010EnumValueOptions\u0012\u0019\n\ndeprecated\u0018\u0001 \u0001(", "\b:\u0005false\u0012C\n\u0014uninterpreted_option\u0018ç\u0007 \u0003(\u000b2$.google.protobuf.UninterpretedOption*\t\bè\u0007\u0010\u0080\u0080\u0080\u0080\u0002\"{\n\u000eServiceOptions\u0012\u0019\n\ndeprecated\u0018! \u0001(\b:\u0005false\u0012C\n\u0014uninterpreted_option\u0018ç\u0007 \u0003(\u000b2$.google.protobuf.UninterpretedOption*\t\bè\u0007\u0010\u0080\u0080\u0080\u0080\u0002\"z\n\rMethodOptions\u0012\u0019\n\ndeprecated\u0018! \u0001(\b:\u0005false\u0012C\n\u0014uninterpreted_option\u0018ç\u0007 \u0003(\u000b2$.google.protobuf.UninterpretedOption*\t\bè\u0007\u0010\u0080\u0080\u0080\u0080\u0002\"\u009e\u0002\n\u0013UninterpretedOption\u0012;\n\u0004name\u0018\u0002 \u0003(\u000b2-.google.protobuf.Uninte", "rpretedOption.NamePart\u0012\u0018\n\u0010identifier_value\u0018\u0003 \u0001(\t\u0012\u001a\n\u0012positive_int_value\u0018\u0004 \u0001(\u0004\u0012\u001a\n\u0012negative_int_value\u0018\u0005 \u0001(\u0003\u0012\u0014\n\fdouble_value\u0018\u0006 \u0001(\u0001\u0012\u0014\n\fstring_value\u0018\u0007 \u0001(\f\u0012\u0017\n\u000faggregate_value\u0018\b \u0001(\t\u001a3\n\bNamePart\u0012\u0011\n\tname_part\u0018\u0001 \u0002(\t\u0012\u0014\n\fis_extension\u0018\u0002 \u0002(\b\"±\u0001\n\u000eSourceCodeInfo\u0012:\n\blocation\u0018\u0001 \u0003(\u000b2(.google.protobuf.SourceCodeInfo.Location\u001ac\n\bLocation\u0012\u0010\n\u0004path\u0018\u0001 \u0003(\u0005B\u0002\u0010\u0001\u0012\u0010\n\u0004span\u0018\u0002 \u0003(\u0005B\u0002\u0010\u0001\u0012\u0018\n\u0010leading_comments\u0018\u0003 \u0001(\t\u0012\u0019\n\u0011trailing_comments", "\u0018\u0004 \u0001(\tB)\n\u0013com.google.protobufB\u0010DescriptorProtosH\u0001"}, new Descriptors.FileDescriptor[0], new Descriptors.FileDescriptor.InternalDescriptorAssigner() { // from class: org.jetbrains.kotlin.protobuf.DescriptorProtos.1
            @Override // org.jetbrains.kotlin.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner
            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor fileDescriptor) {
                Descriptors.FileDescriptor unused = DescriptorProtos.descriptor = fileDescriptor;
                return null;
            }
        });
        Descriptors.Descriptor descriptor2 = getDescriptor().getMessageTypes().get(0);
        internal_static_google_protobuf_FileDescriptorSet_descriptor = descriptor2;
        internal_static_google_protobuf_FileDescriptorSet_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor2, new String[]{"File"});
        Descriptors.Descriptor descriptor3 = getDescriptor().getMessageTypes().get(1);
        internal_static_google_protobuf_FileDescriptorProto_descriptor = descriptor3;
        internal_static_google_protobuf_FileDescriptorProto_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor3, new String[]{SchemaSymbols.ATTVAL_NAME, "Package", "Dependency", "PublicDependency", "WeakDependency", "MessageType", "EnumType", "Service", "Extension", "Options", "SourceCodeInfo"});
        Descriptors.Descriptor descriptor4 = getDescriptor().getMessageTypes().get(2);
        internal_static_google_protobuf_DescriptorProto_descriptor = descriptor4;
        internal_static_google_protobuf_DescriptorProto_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor4, new String[]{SchemaSymbols.ATTVAL_NAME, "Field", "Extension", "NestedType", "EnumType", "ExtensionRange", "OneofDecl", "Options"});
        Descriptors.Descriptor descriptor5 = descriptor4.getNestedTypes().get(0);
        internal_static_google_protobuf_DescriptorProto_ExtensionRange_descriptor = descriptor5;
        internal_static_google_protobuf_DescriptorProto_ExtensionRange_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor5, new String[]{"Start", "End"});
        Descriptors.Descriptor descriptor6 = getDescriptor().getMessageTypes().get(3);
        internal_static_google_protobuf_FieldDescriptorProto_descriptor = descriptor6;
        internal_static_google_protobuf_FieldDescriptorProto_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor6, new String[]{SchemaSymbols.ATTVAL_NAME, "Number", "Label", "Type", "TypeName", "Extendee", "DefaultValue", "OneofIndex", "Options"});
        Descriptors.Descriptor descriptor7 = getDescriptor().getMessageTypes().get(4);
        internal_static_google_protobuf_OneofDescriptorProto_descriptor = descriptor7;
        internal_static_google_protobuf_OneofDescriptorProto_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor7, new String[]{SchemaSymbols.ATTVAL_NAME});
        Descriptors.Descriptor descriptor8 = getDescriptor().getMessageTypes().get(5);
        internal_static_google_protobuf_EnumDescriptorProto_descriptor = descriptor8;
        internal_static_google_protobuf_EnumDescriptorProto_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor8, new String[]{SchemaSymbols.ATTVAL_NAME, "Value", "Options"});
        Descriptors.Descriptor descriptor9 = getDescriptor().getMessageTypes().get(6);
        internal_static_google_protobuf_EnumValueDescriptorProto_descriptor = descriptor9;
        internal_static_google_protobuf_EnumValueDescriptorProto_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor9, new String[]{SchemaSymbols.ATTVAL_NAME, "Number", "Options"});
        Descriptors.Descriptor descriptor10 = getDescriptor().getMessageTypes().get(7);
        internal_static_google_protobuf_ServiceDescriptorProto_descriptor = descriptor10;
        internal_static_google_protobuf_ServiceDescriptorProto_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor10, new String[]{SchemaSymbols.ATTVAL_NAME, "Method", "Options"});
        Descriptors.Descriptor descriptor11 = getDescriptor().getMessageTypes().get(8);
        internal_static_google_protobuf_MethodDescriptorProto_descriptor = descriptor11;
        internal_static_google_protobuf_MethodDescriptorProto_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor11, new String[]{SchemaSymbols.ATTVAL_NAME, "InputType", "OutputType", "Options"});
        Descriptors.Descriptor descriptor12 = getDescriptor().getMessageTypes().get(9);
        internal_static_google_protobuf_FileOptions_descriptor = descriptor12;
        internal_static_google_protobuf_FileOptions_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor12, new String[]{"JavaPackage", "JavaOuterClassname", "JavaMultipleFiles", "JavaGenerateEqualsAndHash", "JavaStringCheckUtf8", "OptimizeFor", "GoPackage", "CcGenericServices", "JavaGenericServices", "PyGenericServices", "Deprecated", "UninterpretedOption"});
        Descriptors.Descriptor descriptor13 = getDescriptor().getMessageTypes().get(10);
        internal_static_google_protobuf_MessageOptions_descriptor = descriptor13;
        internal_static_google_protobuf_MessageOptions_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor13, new String[]{"MessageSetWireFormat", "NoStandardDescriptorAccessor", "Deprecated", "UninterpretedOption"});
        Descriptors.Descriptor descriptor14 = getDescriptor().getMessageTypes().get(11);
        internal_static_google_protobuf_FieldOptions_descriptor = descriptor14;
        internal_static_google_protobuf_FieldOptions_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor14, new String[]{"Ctype", "Packed", "Lazy", "Deprecated", "ExperimentalMapKey", "Weak", "UninterpretedOption"});
        Descriptors.Descriptor descriptor15 = getDescriptor().getMessageTypes().get(12);
        internal_static_google_protobuf_EnumOptions_descriptor = descriptor15;
        internal_static_google_protobuf_EnumOptions_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor15, new String[]{"AllowAlias", "Deprecated", "UninterpretedOption"});
        Descriptors.Descriptor descriptor16 = getDescriptor().getMessageTypes().get(13);
        internal_static_google_protobuf_EnumValueOptions_descriptor = descriptor16;
        internal_static_google_protobuf_EnumValueOptions_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor16, new String[]{"Deprecated", "UninterpretedOption"});
        Descriptors.Descriptor descriptor17 = getDescriptor().getMessageTypes().get(14);
        internal_static_google_protobuf_ServiceOptions_descriptor = descriptor17;
        internal_static_google_protobuf_ServiceOptions_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor17, new String[]{"Deprecated", "UninterpretedOption"});
        Descriptors.Descriptor descriptor18 = getDescriptor().getMessageTypes().get(15);
        internal_static_google_protobuf_MethodOptions_descriptor = descriptor18;
        internal_static_google_protobuf_MethodOptions_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor18, new String[]{"Deprecated", "UninterpretedOption"});
        Descriptors.Descriptor descriptor19 = getDescriptor().getMessageTypes().get(16);
        internal_static_google_protobuf_UninterpretedOption_descriptor = descriptor19;
        internal_static_google_protobuf_UninterpretedOption_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor19, new String[]{SchemaSymbols.ATTVAL_NAME, "IdentifierValue", "PositiveIntValue", "NegativeIntValue", "DoubleValue", "StringValue", "AggregateValue"});
        Descriptors.Descriptor descriptor20 = descriptor19.getNestedTypes().get(0);
        internal_static_google_protobuf_UninterpretedOption_NamePart_descriptor = descriptor20;
        internal_static_google_protobuf_UninterpretedOption_NamePart_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor20, new String[]{"NamePart", "IsExtension"});
        Descriptors.Descriptor descriptor21 = getDescriptor().getMessageTypes().get(17);
        internal_static_google_protobuf_SourceCodeInfo_descriptor = descriptor21;
        internal_static_google_protobuf_SourceCodeInfo_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor21, new String[]{"Location"});
        Descriptors.Descriptor descriptor22 = descriptor21.getNestedTypes().get(0);
        internal_static_google_protobuf_SourceCodeInfo_Location_descriptor = descriptor22;
        internal_static_google_protobuf_SourceCodeInfo_Location_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(descriptor22, new String[]{"Path", "Span", "LeadingComments", "TrailingComments"});
    }
}
