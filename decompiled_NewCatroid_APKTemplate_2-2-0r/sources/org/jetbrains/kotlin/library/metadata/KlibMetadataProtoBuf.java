package org.jetbrains.kotlin.library.metadata;

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
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.protobuf.LazyStringArrayList;
import org.jetbrains.kotlin.protobuf.LazyStringList;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;
import org.jetbrains.kotlin.protobuf.Parser;
import org.jetbrains.kotlin.protobuf.ProtocolStringList;
import org.jetbrains.kotlin.protobuf.WireFormat;

/* JADX INFO: loaded from: classes5.dex */
public final class KlibMetadataProtoBuf {
    public static final int CLASS_ANNOTATION_FIELD_NUMBER = 170;
    public static final int CLASS_FILE_FIELD_NUMBER = 175;
    public static final int CLASS_KDOC_FIELD_NUMBER = 176;
    public static final int CLASS_NAME_FIELD_NUMBER = 174;
    public static final int CLASS_UNIQ_ID_FIELD_NUMBER = 171;
    public static final int COMPILE_TIME_VALUE_FIELD_NUMBER = 173;
    public static final int CONSTRUCTOR_ANNOTATION_FIELD_NUMBER = 170;
    public static final int CONSTRUCTOR_KDOC_FIELD_NUMBER = 173;
    public static final int CONSTRUCTOR_UNIQ_ID_FIELD_NUMBER = 172;
    public static final int ENUM_ENTRY_ANNOTATION_FIELD_NUMBER = 170;
    public static final int ENUM_ENTRY_ORDINAL_FIELD_NUMBER = 171;
    public static final int ENUM_ENTRY_UNIQ_ID_FIELD_NUMBER = 172;
    public static final int FQ_NAME_FIELD_NUMBER = 173;
    public static final int FUNCTION_ANNOTATION_FIELD_NUMBER = 170;
    public static final int FUNCTION_EXTENSION_RECEIVER_ANNOTATION_FIELD_NUMBER = 171;
    public static final int FUNCTION_FILE_FIELD_NUMBER = 172;
    public static final int FUNCTION_KDOC_FIELD_NUMBER = 174;
    public static final int FUNCTION_UNIQ_ID_FIELD_NUMBER = 173;
    public static final int IS_EMPTY_FIELD_NUMBER = 172;
    public static final int PACKAGE_FQ_NAME_FIELD_NUMBER = 171;
    public static final int PACKAGE_FRAGMENT_FILES_FIELD_NUMBER = 170;
    public static final int PARAMETER_ANNOTATION_FIELD_NUMBER = 170;
    public static final int PROPERTY_ANNOTATION_FIELD_NUMBER = 170;
    public static final int PROPERTY_BACKING_FIELD_ANNOTATION_FIELD_NUMBER = 181;
    public static final int PROPERTY_DELEGATED_FIELD_ANNOTATION_FIELD_NUMBER = 182;
    public static final int PROPERTY_EXTENSION_RECEIVER_ANNOTATION_FIELD_NUMBER = 183;
    public static final int PROPERTY_FILE_FIELD_NUMBER = 176;
    public static final int PROPERTY_GETTER_ANNOTATION_FIELD_NUMBER = 177;
    public static final int PROPERTY_KDOC_FIELD_NUMBER = 180;
    public static final int PROPERTY_SETTER_ANNOTATION_FIELD_NUMBER = 178;
    public static final int PROPERTY_UNIQ_ID_FIELD_NUMBER = 179;
    public static final int TYPE_ALIAS_UNIQ_ID_FIELD_NUMBER = 131;
    public static final int TYPE_ANNOTATION_FIELD_NUMBER = 170;
    public static final int TYPE_PARAMETER_ANNOTATION_FIELD_NUMBER = 170;
    public static final int TYPE_PARAM_UNIQ_ID_FIELD_NUMBER = 171;
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> packageFqName = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Package.getDefaultInstance(), 0, null, null, 171, WireFormat.FieldType.INT32, Integer.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Annotation>> classAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Class.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 170, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, Integer> classFile = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Class.getDefaultInstance(), 0, null, null, 175, WireFormat.FieldType.INT32, Integer.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, String> classKdoc = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Class.getDefaultInstance(), "", null, null, 176, WireFormat.FieldType.STRING, String.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, DescriptorUniqId> classUniqId = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Class.getDefaultInstance(), DescriptorUniqId.getDefaultInstance(), DescriptorUniqId.getDefaultInstance(), null, 171, WireFormat.FieldType.MESSAGE, DescriptorUniqId.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, List<ProtoBuf.Annotation>> constructorAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Constructor.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 170, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, String> constructorKdoc = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Constructor.getDefaultInstance(), "", null, null, 173, WireFormat.FieldType.STRING, String.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, DescriptorUniqId> constructorUniqId = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Constructor.getDefaultInstance(), DescriptorUniqId.getDefaultInstance(), DescriptorUniqId.getDefaultInstance(), null, 172, WireFormat.FieldType.MESSAGE, DescriptorUniqId.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> functionAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Function.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 170, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> functionExtensionReceiverAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Function.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 171, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, Integer> functionFile = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Function.getDefaultInstance(), 0, null, null, 172, WireFormat.FieldType.INT32, Integer.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, String> functionKdoc = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Function.getDefaultInstance(), "", null, null, 174, WireFormat.FieldType.STRING, String.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, DescriptorUniqId> functionUniqId = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Function.getDefaultInstance(), DescriptorUniqId.getDefaultInstance(), DescriptorUniqId.getDefaultInstance(), null, 173, WireFormat.FieldType.MESSAGE, DescriptorUniqId.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 170, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyGetterAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 177, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertySetterAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 178, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyBackingFieldAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 181, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyDelegatedFieldAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 182, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyExtensionReceiverAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 183, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, ProtoBuf.Annotation.Argument.Value> compileTimeValue = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), ProtoBuf.Annotation.Argument.Value.getDefaultInstance(), ProtoBuf.Annotation.Argument.Value.getDefaultInstance(), null, 173, WireFormat.FieldType.MESSAGE, ProtoBuf.Annotation.Argument.Value.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, Integer> propertyFile = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), 0, null, null, 176, WireFormat.FieldType.INT32, Integer.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, String> propertyKdoc = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), "", null, null, 180, WireFormat.FieldType.STRING, String.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, DescriptorUniqId> propertyUniqId = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), DescriptorUniqId.getDefaultInstance(), DescriptorUniqId.getDefaultInstance(), null, 179, WireFormat.FieldType.MESSAGE, DescriptorUniqId.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, List<ProtoBuf.Annotation>> enumEntryAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.EnumEntry.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 170, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, Integer> enumEntryOrdinal = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.EnumEntry.getDefaultInstance(), 0, null, null, 171, WireFormat.FieldType.INT32, Integer.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, DescriptorUniqId> enumEntryUniqId = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.EnumEntry.getDefaultInstance(), DescriptorUniqId.getDefaultInstance(), DescriptorUniqId.getDefaultInstance(), null, 172, WireFormat.FieldType.MESSAGE, DescriptorUniqId.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.ValueParameter, List<ProtoBuf.Annotation>> parameterAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.ValueParameter.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 170, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> typeAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Type.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 170, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> typeParameterAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.TypeParameter.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 170, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, DescriptorUniqId> typeParamUniqId = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.TypeParameter.getDefaultInstance(), DescriptorUniqId.getDefaultInstance(), DescriptorUniqId.getDefaultInstance(), null, 171, WireFormat.FieldType.MESSAGE, DescriptorUniqId.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.PackageFragment, List<Integer>> packageFragmentFiles = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.PackageFragment.getDefaultInstance(), null, null, 170, WireFormat.FieldType.INT32, false, Integer.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.PackageFragment, Boolean> isEmpty = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.PackageFragment.getDefaultInstance(), false, null, null, 172, WireFormat.FieldType.BOOL, Boolean.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.PackageFragment, String> fqName = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.PackageFragment.getDefaultInstance(), "", null, null, 173, WireFormat.FieldType.STRING, String.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.PackageFragment, List<Integer>> className = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.PackageFragment.getDefaultInstance(), null, null, 174, WireFormat.FieldType.INT32, true, Integer.class);
    public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeAlias, DescriptorUniqId> typeAliasUniqId = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.TypeAlias.getDefaultInstance(), DescriptorUniqId.getDefaultInstance(), DescriptorUniqId.getDefaultInstance(), null, 131, WireFormat.FieldType.MESSAGE, DescriptorUniqId.class);

    public interface DescriptorUniqIdOrBuilder extends MessageLiteOrBuilder {
        long getIndex();

        boolean hasIndex();
    }

    public interface FileOrBuilder extends MessageLiteOrBuilder {
        String getName();

        ByteString getNameBytes();

        boolean hasName();
    }

    public interface HeaderOrBuilder extends MessageLiteOrBuilder {
        ProtoBuf.Annotation getAnnotation(int i);

        int getAnnotationCount();

        List<ProtoBuf.Annotation> getAnnotationList();

        String getEmptyPackage(int i);

        ByteString getEmptyPackageBytes(int i);

        int getEmptyPackageCount();

        ProtocolStringList getEmptyPackageList();

        File getFile(int i);

        int getFileCount();

        List<File> getFileList();

        int getFlags();

        String getModuleName();

        ByteString getModuleNameBytes();

        String getPackageFragmentName(int i);

        ByteString getPackageFragmentNameBytes(int i);

        int getPackageFragmentNameCount();

        ProtocolStringList getPackageFragmentNameList();

        ProtoBuf.QualifiedNameTable getQualifiedNames();

        ProtoBuf.StringTable getStrings();

        boolean hasFlags();

        boolean hasModuleName();

        boolean hasQualifiedNames();

        boolean hasStrings();
    }

    private KlibMetadataProtoBuf() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
        extensionRegistryLite.add(packageFqName);
        extensionRegistryLite.add(classAnnotation);
        extensionRegistryLite.add(classFile);
        extensionRegistryLite.add(classKdoc);
        extensionRegistryLite.add(classUniqId);
        extensionRegistryLite.add(constructorAnnotation);
        extensionRegistryLite.add(constructorKdoc);
        extensionRegistryLite.add(constructorUniqId);
        extensionRegistryLite.add(functionAnnotation);
        extensionRegistryLite.add(functionExtensionReceiverAnnotation);
        extensionRegistryLite.add(functionFile);
        extensionRegistryLite.add(functionKdoc);
        extensionRegistryLite.add(functionUniqId);
        extensionRegistryLite.add(propertyAnnotation);
        extensionRegistryLite.add(propertyGetterAnnotation);
        extensionRegistryLite.add(propertySetterAnnotation);
        extensionRegistryLite.add(propertyBackingFieldAnnotation);
        extensionRegistryLite.add(propertyDelegatedFieldAnnotation);
        extensionRegistryLite.add(propertyExtensionReceiverAnnotation);
        extensionRegistryLite.add(compileTimeValue);
        extensionRegistryLite.add(propertyFile);
        extensionRegistryLite.add(propertyKdoc);
        extensionRegistryLite.add(propertyUniqId);
        extensionRegistryLite.add(enumEntryAnnotation);
        extensionRegistryLite.add(enumEntryOrdinal);
        extensionRegistryLite.add(enumEntryUniqId);
        extensionRegistryLite.add(parameterAnnotation);
        extensionRegistryLite.add(typeAnnotation);
        extensionRegistryLite.add(typeParameterAnnotation);
        extensionRegistryLite.add(typeParamUniqId);
        extensionRegistryLite.add(packageFragmentFiles);
        extensionRegistryLite.add(isEmpty);
        extensionRegistryLite.add(fqName);
        extensionRegistryLite.add(className);
        extensionRegistryLite.add(typeAliasUniqId);
    }

    public static final class Header extends GeneratedMessageLite implements HeaderOrBuilder {
        public static final int ANNOTATION_FIELD_NUMBER = 6;
        public static final int EMPTY_PACKAGE_FIELD_NUMBER = 8;
        public static final int FILE_FIELD_NUMBER = 9;
        public static final int FLAGS_FIELD_NUMBER = 2;
        public static final int MODULE_NAME_FIELD_NUMBER = 1;
        public static final int PACKAGE_FRAGMENT_NAME_FIELD_NUMBER = 7;
        public static Parser<Header> PARSER = new AbstractParser<Header>() { // from class: org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.Header.1
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
        private LazyStringList emptyPackage_;
        private List<File> file_;
        private int flags_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object moduleName_;
        private LazyStringList packageFragmentName_;
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
            int i = 0;
            while (!z) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                ByteString bytes = codedInputStream.readBytes();
                                this.bitField0_ |= 1;
                                this.moduleName_ = bytes;
                            } else if (tag != 16) {
                                if (tag == 34) {
                                    builder = (this.bitField0_ & 4) == 4 ? this.strings_.toBuilder() : null;
                                    ProtoBuf.StringTable stringTable = (ProtoBuf.StringTable) codedInputStream.readMessage(ProtoBuf.StringTable.PARSER, extensionRegistryLite);
                                    this.strings_ = stringTable;
                                    if (builder != null) {
                                        builder.mergeFrom(stringTable);
                                        this.strings_ = builder.buildPartial();
                                    }
                                    this.bitField0_ |= 4;
                                } else if (tag == 42) {
                                    builder = (this.bitField0_ & 8) == 8 ? this.qualifiedNames_.toBuilder() : null;
                                    ProtoBuf.QualifiedNameTable qualifiedNameTable = (ProtoBuf.QualifiedNameTable) codedInputStream.readMessage(ProtoBuf.QualifiedNameTable.PARSER, extensionRegistryLite);
                                    this.qualifiedNames_ = qualifiedNameTable;
                                    if (builder != null) {
                                        builder.mergeFrom(qualifiedNameTable);
                                        this.qualifiedNames_ = builder.buildPartial();
                                    }
                                    this.bitField0_ |= 8;
                                } else if (tag == 50) {
                                    if ((i & 16) != 16) {
                                        this.annotation_ = new ArrayList();
                                        i |= 16;
                                    }
                                    this.annotation_.add((ProtoBuf.Annotation) codedInputStream.readMessage(ProtoBuf.Annotation.PARSER, extensionRegistryLite));
                                } else if (tag == 58) {
                                    ByteString bytes2 = codedInputStream.readBytes();
                                    if ((i & 32) != 32) {
                                        this.packageFragmentName_ = new LazyStringArrayList();
                                        i |= 32;
                                    }
                                    this.packageFragmentName_.add(bytes2);
                                } else if (tag == 66) {
                                    ByteString bytes3 = codedInputStream.readBytes();
                                    if ((i & 64) != 64) {
                                        this.emptyPackage_ = new LazyStringArrayList();
                                        i |= 64;
                                    }
                                    this.emptyPackage_.add(bytes3);
                                } else if (tag != 74) {
                                    if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    if ((i & 128) != 128) {
                                        this.file_ = new ArrayList();
                                        i |= 128;
                                    }
                                    this.file_.add((File) codedInputStream.readMessage(File.PARSER, extensionRegistryLite));
                                }
                            } else {
                                this.bitField0_ |= 2;
                                this.flags_ = codedInputStream.readInt32();
                            }
                        }
                        z = true;
                    } catch (Throwable th) {
                        if ((i & 16) == 16) {
                            this.annotation_ = Collections.unmodifiableList(this.annotation_);
                        }
                        if ((i & 32) == 32) {
                            this.packageFragmentName_ = this.packageFragmentName_.getUnmodifiableView();
                        }
                        if ((i & 64) == 64) {
                            this.emptyPackage_ = this.emptyPackage_.getUnmodifiableView();
                        }
                        if ((i & 128) == 128) {
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
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                }
            }
            if ((i & 16) == 16) {
                this.annotation_ = Collections.unmodifiableList(this.annotation_);
            }
            if ((i & 32) == 32) {
                this.packageFragmentName_ = this.packageFragmentName_.getUnmodifiableView();
            }
            if ((i & 64) == 64) {
                this.emptyPackage_ = this.emptyPackage_.getUnmodifiableView();
            }
            if ((i & 128) == 128) {
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
            Header header = new Header(true);
            defaultInstance = header;
            header.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<Header> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public boolean hasModuleName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public String getModuleName() {
            Object obj = this.moduleName_;
            if (obj instanceof String) {
                return (String) obj;
            }
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.moduleName_ = stringUtf8;
            }
            return stringUtf8;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public ByteString getModuleNameBytes() {
            Object obj = this.moduleName_;
            if (obj instanceof String) {
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.moduleName_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }
            return (ByteString) obj;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public boolean hasFlags() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public int getFlags() {
            return this.flags_;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public boolean hasStrings() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public ProtoBuf.StringTable getStrings() {
            return this.strings_;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public boolean hasQualifiedNames() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public ProtoBuf.QualifiedNameTable getQualifiedNames() {
            return this.qualifiedNames_;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public List<ProtoBuf.Annotation> getAnnotationList() {
            return this.annotation_;
        }

        public List<? extends ProtoBuf.AnnotationOrBuilder> getAnnotationOrBuilderList() {
            return this.annotation_;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public int getAnnotationCount() {
            return this.annotation_.size();
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public ProtoBuf.Annotation getAnnotation(int i) {
            return this.annotation_.get(i);
        }

        public ProtoBuf.AnnotationOrBuilder getAnnotationOrBuilder(int i) {
            return this.annotation_.get(i);
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public ProtocolStringList getPackageFragmentNameList() {
            return this.packageFragmentName_;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public int getPackageFragmentNameCount() {
            return this.packageFragmentName_.size();
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public String getPackageFragmentName(int i) {
            return (String) this.packageFragmentName_.get(i);
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public ByteString getPackageFragmentNameBytes(int i) {
            return this.packageFragmentName_.getByteString(i);
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public ProtocolStringList getEmptyPackageList() {
            return this.emptyPackage_;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public int getEmptyPackageCount() {
            return this.emptyPackage_.size();
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public String getEmptyPackage(int i) {
            return (String) this.emptyPackage_.get(i);
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public ByteString getEmptyPackageBytes(int i) {
            return this.emptyPackage_.getByteString(i);
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public List<File> getFileList() {
            return this.file_;
        }

        public List<? extends FileOrBuilder> getFileOrBuilderList() {
            return this.file_;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public int getFileCount() {
            return this.file_.size();
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
        public File getFile(int i) {
            return this.file_.get(i);
        }

        public FileOrBuilder getFileOrBuilder(int i) {
            return this.file_.get(i);
        }

        private void initFields() {
            this.moduleName_ = "";
            this.flags_ = 0;
            this.strings_ = ProtoBuf.StringTable.getDefaultInstance();
            this.qualifiedNames_ = ProtoBuf.QualifiedNameTable.getDefaultInstance();
            this.annotation_ = Collections.emptyList();
            this.packageFragmentName_ = LazyStringArrayList.EMPTY;
            this.emptyPackage_ = LazyStringArrayList.EMPTY;
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
            if (!hasModuleName()) {
                this.memoizedIsInitialized = (byte) 0;
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
            for (int i2 = 0; i2 < getFileCount(); i2++) {
                if (!getFile(i2).isInitialized()) {
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
                codedOutputStream.writeBytes(1, getModuleNameBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                codedOutputStream.writeInt32(2, this.flags_);
            }
            if ((this.bitField0_ & 4) == 4) {
                codedOutputStream.writeMessage(4, this.strings_);
            }
            if ((this.bitField0_ & 8) == 8) {
                codedOutputStream.writeMessage(5, this.qualifiedNames_);
            }
            for (int i = 0; i < this.annotation_.size(); i++) {
                codedOutputStream.writeMessage(6, this.annotation_.get(i));
            }
            for (int i2 = 0; i2 < this.packageFragmentName_.size(); i2++) {
                codedOutputStream.writeBytes(7, this.packageFragmentName_.getByteString(i2));
            }
            for (int i3 = 0; i3 < this.emptyPackage_.size(); i3++) {
                codedOutputStream.writeBytes(8, this.emptyPackage_.getByteString(i3));
            }
            for (int i4 = 0; i4 < this.file_.size(); i4++) {
                codedOutputStream.writeMessage(9, this.file_.get(i4));
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBytesSize = (this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBytesSize(1, getModuleNameBytes()) : 0;
            if ((this.bitField0_ & 2) == 2) {
                iComputeBytesSize += CodedOutputStream.computeInt32Size(2, this.flags_);
            }
            if ((this.bitField0_ & 4) == 4) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(4, this.strings_);
            }
            if ((this.bitField0_ & 8) == 8) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(5, this.qualifiedNames_);
            }
            for (int i2 = 0; i2 < this.annotation_.size(); i2++) {
                iComputeBytesSize += CodedOutputStream.computeMessageSize(6, this.annotation_.get(i2));
            }
            int iComputeBytesSizeNoTag = 0;
            for (int i3 = 0; i3 < this.packageFragmentName_.size(); i3++) {
                iComputeBytesSizeNoTag += CodedOutputStream.computeBytesSizeNoTag(this.packageFragmentName_.getByteString(i3));
            }
            int size = iComputeBytesSize + iComputeBytesSizeNoTag + getPackageFragmentNameList().size();
            int iComputeBytesSizeNoTag2 = 0;
            for (int i4 = 0; i4 < this.emptyPackage_.size(); i4++) {
                iComputeBytesSizeNoTag2 += CodedOutputStream.computeBytesSizeNoTag(this.emptyPackage_.getByteString(i4));
            }
            int size2 = size + iComputeBytesSizeNoTag2 + getEmptyPackageList().size();
            for (int i5 = 0; i5 < this.file_.size(); i5++) {
                size2 += CodedOutputStream.computeMessageSize(9, this.file_.get(i5));
            }
            int size3 = size2 + this.unknownFields.size();
            this.memoizedSerializedSize = size3;
            return size3;
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
            private Object moduleName_ = "";
            private ProtoBuf.StringTable strings_ = ProtoBuf.StringTable.getDefaultInstance();
            private ProtoBuf.QualifiedNameTable qualifiedNames_ = ProtoBuf.QualifiedNameTable.getDefaultInstance();
            private List<ProtoBuf.Annotation> annotation_ = Collections.emptyList();
            private LazyStringList packageFragmentName_ = LazyStringArrayList.EMPTY;
            private LazyStringList emptyPackage_ = LazyStringArrayList.EMPTY;
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
                this.moduleName_ = "";
                int i = this.bitField0_;
                this.flags_ = 0;
                this.bitField0_ = i & (-4);
                this.strings_ = ProtoBuf.StringTable.getDefaultInstance();
                this.bitField0_ &= -5;
                this.qualifiedNames_ = ProtoBuf.QualifiedNameTable.getDefaultInstance();
                this.bitField0_ &= -9;
                this.annotation_ = Collections.emptyList();
                this.bitField0_ &= -17;
                this.packageFragmentName_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -33;
                this.emptyPackage_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -65;
                this.file_ = Collections.emptyList();
                this.bitField0_ &= -129;
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
                header.moduleName_ = this.moduleName_;
                if ((i & 2) == 2) {
                    i2 |= 2;
                }
                header.flags_ = this.flags_;
                if ((i & 4) == 4) {
                    i2 |= 4;
                }
                header.strings_ = this.strings_;
                if ((i & 8) == 8) {
                    i2 |= 8;
                }
                header.qualifiedNames_ = this.qualifiedNames_;
                if ((this.bitField0_ & 16) == 16) {
                    this.annotation_ = Collections.unmodifiableList(this.annotation_);
                    this.bitField0_ &= -17;
                }
                header.annotation_ = this.annotation_;
                if ((this.bitField0_ & 32) == 32) {
                    this.packageFragmentName_ = this.packageFragmentName_.getUnmodifiableView();
                    this.bitField0_ &= -33;
                }
                header.packageFragmentName_ = this.packageFragmentName_;
                if ((this.bitField0_ & 64) == 64) {
                    this.emptyPackage_ = this.emptyPackage_.getUnmodifiableView();
                    this.bitField0_ &= -65;
                }
                header.emptyPackage_ = this.emptyPackage_;
                if ((this.bitField0_ & 128) == 128) {
                    this.file_ = Collections.unmodifiableList(this.file_);
                    this.bitField0_ &= -129;
                }
                header.file_ = this.file_;
                header.bitField0_ = i2;
                return header;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(Header header) {
                if (header == Header.getDefaultInstance()) {
                    return this;
                }
                if (header.hasModuleName()) {
                    this.bitField0_ |= 1;
                    this.moduleName_ = header.moduleName_;
                }
                if (header.hasFlags()) {
                    setFlags(header.getFlags());
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
                        this.bitField0_ &= -17;
                    } else {
                        ensureAnnotationIsMutable();
                        this.annotation_.addAll(header.annotation_);
                    }
                }
                if (!header.packageFragmentName_.isEmpty()) {
                    if (this.packageFragmentName_.isEmpty()) {
                        this.packageFragmentName_ = header.packageFragmentName_;
                        this.bitField0_ &= -33;
                    } else {
                        ensurePackageFragmentNameIsMutable();
                        this.packageFragmentName_.addAll(header.packageFragmentName_);
                    }
                }
                if (!header.emptyPackage_.isEmpty()) {
                    if (this.emptyPackage_.isEmpty()) {
                        this.emptyPackage_ = header.emptyPackage_;
                        this.bitField0_ &= -65;
                    } else {
                        ensureEmptyPackageIsMutable();
                        this.emptyPackage_.addAll(header.emptyPackage_);
                    }
                }
                if (!header.file_.isEmpty()) {
                    if (this.file_.isEmpty()) {
                        this.file_ = header.file_;
                        this.bitField0_ &= -129;
                    } else {
                        ensureFileIsMutable();
                        this.file_.addAll(header.file_);
                    }
                }
                setUnknownFields(getUnknownFields().concat(header.unknownFields));
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                if (!hasModuleName()) {
                    return false;
                }
                if (hasQualifiedNames() && !getQualifiedNames().isInitialized()) {
                    return false;
                }
                for (int i = 0; i < getAnnotationCount(); i++) {
                    if (!getAnnotation(i).isInitialized()) {
                        return false;
                    }
                }
                for (int i2 = 0; i2 < getFileCount(); i2++) {
                    if (!getFile(i2).isInitialized()) {
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

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public boolean hasModuleName() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public String getModuleName() {
                Object obj = this.moduleName_;
                if (!(obj instanceof String)) {
                    ByteString byteString = (ByteString) obj;
                    String stringUtf8 = byteString.toStringUtf8();
                    if (byteString.isValidUtf8()) {
                        this.moduleName_ = stringUtf8;
                    }
                    return stringUtf8;
                }
                return (String) obj;
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public ByteString getModuleNameBytes() {
                Object obj = this.moduleName_;
                if (obj instanceof String) {
                    ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                    this.moduleName_ = byteStringCopyFromUtf8;
                    return byteStringCopyFromUtf8;
                }
                return (ByteString) obj;
            }

            public Builder setModuleName(String str) {
                str.getClass();
                this.bitField0_ |= 1;
                this.moduleName_ = str;
                return this;
            }

            public Builder clearModuleName() {
                this.bitField0_ &= -2;
                this.moduleName_ = Header.getDefaultInstance().getModuleName();
                return this;
            }

            public Builder setModuleNameBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 1;
                this.moduleName_ = byteString;
                return this;
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public boolean hasFlags() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public int getFlags() {
                return this.flags_;
            }

            public Builder setFlags(int i) {
                this.bitField0_ |= 2;
                this.flags_ = i;
                return this;
            }

            public Builder clearFlags() {
                this.bitField0_ &= -3;
                this.flags_ = 0;
                return this;
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public boolean hasStrings() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public ProtoBuf.StringTable getStrings() {
                return this.strings_;
            }

            public Builder setStrings(ProtoBuf.StringTable stringTable) {
                stringTable.getClass();
                this.strings_ = stringTable;
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setStrings(ProtoBuf.StringTable.Builder builder) {
                this.strings_ = builder.build();
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeStrings(ProtoBuf.StringTable stringTable) {
                if ((this.bitField0_ & 4) == 4 && this.strings_ != ProtoBuf.StringTable.getDefaultInstance()) {
                    this.strings_ = ProtoBuf.StringTable.newBuilder(this.strings_).mergeFrom(stringTable).buildPartial();
                } else {
                    this.strings_ = stringTable;
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearStrings() {
                this.strings_ = ProtoBuf.StringTable.getDefaultInstance();
                this.bitField0_ &= -5;
                return this;
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public boolean hasQualifiedNames() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public ProtoBuf.QualifiedNameTable getQualifiedNames() {
                return this.qualifiedNames_;
            }

            public Builder setQualifiedNames(ProtoBuf.QualifiedNameTable qualifiedNameTable) {
                qualifiedNameTable.getClass();
                this.qualifiedNames_ = qualifiedNameTable;
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setQualifiedNames(ProtoBuf.QualifiedNameTable.Builder builder) {
                this.qualifiedNames_ = builder.build();
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeQualifiedNames(ProtoBuf.QualifiedNameTable qualifiedNameTable) {
                if ((this.bitField0_ & 8) == 8 && this.qualifiedNames_ != ProtoBuf.QualifiedNameTable.getDefaultInstance()) {
                    this.qualifiedNames_ = ProtoBuf.QualifiedNameTable.newBuilder(this.qualifiedNames_).mergeFrom(qualifiedNameTable).buildPartial();
                } else {
                    this.qualifiedNames_ = qualifiedNameTable;
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder clearQualifiedNames() {
                this.qualifiedNames_ = ProtoBuf.QualifiedNameTable.getDefaultInstance();
                this.bitField0_ &= -9;
                return this;
            }

            private void ensureAnnotationIsMutable() {
                if ((this.bitField0_ & 16) != 16) {
                    this.annotation_ = new ArrayList(this.annotation_);
                    this.bitField0_ |= 16;
                }
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public List<ProtoBuf.Annotation> getAnnotationList() {
                return Collections.unmodifiableList(this.annotation_);
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public int getAnnotationCount() {
                return this.annotation_.size();
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
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
                this.bitField0_ &= -17;
                return this;
            }

            public Builder removeAnnotation(int i) {
                ensureAnnotationIsMutable();
                this.annotation_.remove(i);
                return this;
            }

            private void ensurePackageFragmentNameIsMutable() {
                if ((this.bitField0_ & 32) != 32) {
                    this.packageFragmentName_ = new LazyStringArrayList(this.packageFragmentName_);
                    this.bitField0_ |= 32;
                }
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public ProtocolStringList getPackageFragmentNameList() {
                return this.packageFragmentName_.getUnmodifiableView();
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public int getPackageFragmentNameCount() {
                return this.packageFragmentName_.size();
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public String getPackageFragmentName(int i) {
                return (String) this.packageFragmentName_.get(i);
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public ByteString getPackageFragmentNameBytes(int i) {
                return this.packageFragmentName_.getByteString(i);
            }

            public Builder setPackageFragmentName(int i, String str) {
                str.getClass();
                ensurePackageFragmentNameIsMutable();
                this.packageFragmentName_.set(i, str);
                return this;
            }

            public Builder addPackageFragmentName(String str) {
                str.getClass();
                ensurePackageFragmentNameIsMutable();
                this.packageFragmentName_.add(str);
                return this;
            }

            public Builder addAllPackageFragmentName(Iterable<String> iterable) {
                ensurePackageFragmentNameIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.packageFragmentName_);
                return this;
            }

            public Builder clearPackageFragmentName() {
                this.packageFragmentName_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -33;
                return this;
            }

            public Builder addPackageFragmentNameBytes(ByteString byteString) {
                byteString.getClass();
                ensurePackageFragmentNameIsMutable();
                this.packageFragmentName_.add(byteString);
                return this;
            }

            private void ensureEmptyPackageIsMutable() {
                if ((this.bitField0_ & 64) != 64) {
                    this.emptyPackage_ = new LazyStringArrayList(this.emptyPackage_);
                    this.bitField0_ |= 64;
                }
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public ProtocolStringList getEmptyPackageList() {
                return this.emptyPackage_.getUnmodifiableView();
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public int getEmptyPackageCount() {
                return this.emptyPackage_.size();
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public String getEmptyPackage(int i) {
                return (String) this.emptyPackage_.get(i);
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public ByteString getEmptyPackageBytes(int i) {
                return this.emptyPackage_.getByteString(i);
            }

            public Builder setEmptyPackage(int i, String str) {
                str.getClass();
                ensureEmptyPackageIsMutable();
                this.emptyPackage_.set(i, str);
                return this;
            }

            public Builder addEmptyPackage(String str) {
                str.getClass();
                ensureEmptyPackageIsMutable();
                this.emptyPackage_.add(str);
                return this;
            }

            public Builder addAllEmptyPackage(Iterable<String> iterable) {
                ensureEmptyPackageIsMutable();
                AbstractMessageLite.Builder.addAll(iterable, this.emptyPackage_);
                return this;
            }

            public Builder clearEmptyPackage() {
                this.emptyPackage_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -65;
                return this;
            }

            public Builder addEmptyPackageBytes(ByteString byteString) {
                byteString.getClass();
                ensureEmptyPackageIsMutable();
                this.emptyPackage_.add(byteString);
                return this;
            }

            private void ensureFileIsMutable() {
                if ((this.bitField0_ & 128) != 128) {
                    this.file_ = new ArrayList(this.file_);
                    this.bitField0_ |= 128;
                }
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public List<File> getFileList() {
                return Collections.unmodifiableList(this.file_);
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
            public int getFileCount() {
                return this.file_.size();
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.HeaderOrBuilder
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
                this.bitField0_ &= -129;
                return this;
            }

            public Builder removeFile(int i) {
                ensureFileIsMutable();
                this.file_.remove(i);
                return this;
            }
        }
    }

    public static final class File extends GeneratedMessageLite implements FileOrBuilder {
        public static final int NAME_FIELD_NUMBER = 1;
        public static Parser<File> PARSER = new AbstractParser<File>() { // from class: org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.File.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public File parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new File(codedInputStream, extensionRegistryLite);
            }
        };
        private static final File defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object name_;
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
            while (!z) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag != 10) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                ByteString bytes = codedInputStream.readBytes();
                                this.bitField0_ |= 1;
                                this.name_ = bytes;
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
            File file = new File(true);
            defaultInstance = file;
            file.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<File> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.FileOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.FileOrBuilder
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

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.FileOrBuilder
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
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBytesSize = ((this.bitField0_ & 1) == 1 ? CodedOutputStream.computeBytesSize(1, getNameBytes()) : 0) + this.unknownFields.size();
            this.memoizedSerializedSize = iComputeBytesSize;
            return iComputeBytesSize;
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
            private int bitField0_;
            private Object name_ = "";

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
                file.name_ = this.name_;
                file.bitField0_ = i;
                return file;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(File file) {
                if (file == File.getDefaultInstance()) {
                    return this;
                }
                if (file.hasName()) {
                    this.bitField0_ |= 1;
                    this.name_ = file.name_;
                }
                setUnknownFields(getUnknownFields().concat(file.unknownFields));
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return hasName();
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

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.FileOrBuilder
            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.FileOrBuilder
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

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.FileOrBuilder
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
                this.name_ = File.getDefaultInstance().getName();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) {
                byteString.getClass();
                this.bitField0_ |= 1;
                this.name_ = byteString;
                return this;
            }
        }
    }

    public static final class DescriptorUniqId extends GeneratedMessageLite implements DescriptorUniqIdOrBuilder {
        public static final int INDEX_FIELD_NUMBER = 1;
        public static Parser<DescriptorUniqId> PARSER = new AbstractParser<DescriptorUniqId>() { // from class: org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.DescriptorUniqId.1
            @Override // org.jetbrains.kotlin.protobuf.Parser
            public DescriptorUniqId parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new DescriptorUniqId(codedInputStream, extensionRegistryLite);
            }
        };
        private static final DescriptorUniqId defaultInstance;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private long index_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private final ByteString unknownFields;

        private DescriptorUniqId(GeneratedMessageLite.Builder builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private DescriptorUniqId(boolean z) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = ByteString.EMPTY;
        }

        public static DescriptorUniqId getDefaultInstance() {
            return defaultInstance;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public DescriptorUniqId getDefaultInstanceForType() {
            return defaultInstance;
        }

        private DescriptorUniqId(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            ByteString.Output outputNewOutput = ByteString.newOutput();
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
            boolean z = false;
            while (!z) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag != 8) {
                                if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.bitField0_ |= 1;
                                this.index_ = codedInputStream.readInt64();
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
            DescriptorUniqId descriptorUniqId = new DescriptorUniqId(true);
            defaultInstance = descriptorUniqId;
            descriptorUniqId.initFields();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
        public Parser<DescriptorUniqId> getParserForType() {
            return PARSER;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.DescriptorUniqIdOrBuilder
        public boolean hasIndex() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.DescriptorUniqIdOrBuilder
        public long getIndex() {
            return this.index_;
        }

        private void initFields() {
            this.index_ = 0L;
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
            if (!hasIndex()) {
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
                codedOutputStream.writeInt64(1, this.index_);
            }
            codedOutputStream.writeRawBytes(this.unknownFields);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeInt64Size = ((this.bitField0_ & 1) == 1 ? CodedOutputStream.computeInt64Size(1, this.index_) : 0) + this.unknownFields.size();
            this.memoizedSerializedSize = iComputeInt64Size;
            return iComputeInt64Size;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static DescriptorUniqId parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        public static DescriptorUniqId parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        public static DescriptorUniqId parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        public static DescriptorUniqId parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static DescriptorUniqId parseFrom(InputStream inputStream) throws IOException {
            return PARSER.parseFrom(inputStream);
        }

        public static DescriptorUniqId parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(inputStream, extensionRegistryLite);
        }

        public static DescriptorUniqId parseDelimitedFrom(InputStream inputStream) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream);
        }

        public static DescriptorUniqId parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
        }

        public static DescriptorUniqId parseFrom(CodedInputStream codedInputStream) throws IOException {
            return PARSER.parseFrom(codedInputStream);
        }

        public static DescriptorUniqId parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(DescriptorUniqId descriptorUniqId) {
            return newBuilder().mergeFrom(descriptorUniqId);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite
        public Builder toBuilder() {
            return newBuilder(this);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<DescriptorUniqId, Builder> implements DescriptorUniqIdOrBuilder {
            private int bitField0_;
            private long index_;

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
                this.index_ = 0L;
                this.bitField0_ &= -2;
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public DescriptorUniqId getDefaultInstanceForType() {
                return DescriptorUniqId.getDefaultInstance();
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public DescriptorUniqId build() {
                DescriptorUniqId descriptorUniqIdBuildPartial = buildPartial();
                if (descriptorUniqIdBuildPartial.isInitialized()) {
                    return descriptorUniqIdBuildPartial;
                }
                throw newUninitializedMessageException(descriptorUniqIdBuildPartial);
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public DescriptorUniqId buildPartial() {
                DescriptorUniqId descriptorUniqId = new DescriptorUniqId(this);
                int i = (this.bitField0_ & 1) != 1 ? 0 : 1;
                descriptorUniqId.index_ = this.index_;
                descriptorUniqId.bitField0_ = i;
                return descriptorUniqId;
            }

            @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
            public Builder mergeFrom(DescriptorUniqId descriptorUniqId) {
                if (descriptorUniqId == DescriptorUniqId.getDefaultInstance()) {
                    return this;
                }
                if (descriptorUniqId.hasIndex()) {
                    setIndex(descriptorUniqId.getIndex());
                }
                setUnknownFields(getUnknownFields().concat(descriptorUniqId.unknownFields));
                return this;
            }

            @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return hasIndex();
            }

            /* JADX WARN: Code duplicated, block: B:15:0x001d  */
            @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
            public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
                DescriptorUniqId descriptorUniqId = null;
                try {
                    try {
                        DescriptorUniqId partialFrom = DescriptorUniqId.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                        if (partialFrom != null) {
                            mergeFrom(partialFrom);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        DescriptorUniqId descriptorUniqId2 = (DescriptorUniqId) e.getUnfinishedMessage();
                        try {
                            throw e;
                        } catch (Throwable th) {
                            th = th;
                            descriptorUniqId = descriptorUniqId2;
                            if (descriptorUniqId != null) {
                                mergeFrom(descriptorUniqId);
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (descriptorUniqId != null) {
                        mergeFrom(descriptorUniqId);
                    }
                    throw th;
                }
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.DescriptorUniqIdOrBuilder
            public boolean hasIndex() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf.DescriptorUniqIdOrBuilder
            public long getIndex() {
                return this.index_;
            }

            public Builder setIndex(long j) {
                this.bitField0_ |= 1;
                this.index_ = j;
                return this;
            }

            public Builder clearIndex() {
                this.bitField0_ &= -2;
                this.index_ = 0L;
                return this;
            }
        }
    }
}
