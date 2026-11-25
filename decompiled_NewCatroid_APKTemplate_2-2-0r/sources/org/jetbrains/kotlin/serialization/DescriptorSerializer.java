package org.jetbrains.kotlin.serialization;

import androidx.exifinterface.media.ExifInterface;
import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.google.android.gms.cast.MediaError;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchResult;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.Implementation;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.SuspendFunctionTypesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.MultiFieldValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.PossiblyInnerType;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterUtilsKt;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotated;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.extensions.TypeAttributeTranslatorExtension;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.metadata.deserialization.VersionSpecificBehaviorKt;
import org.jetbrains.kotlin.metadata.serialization.Interner;
import org.jetbrains.kotlin.metadata.serialization.MutableTypeTable;
import org.jetbrains.kotlin.metadata.serialization.MutableVersionRequirementTable;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.MemberComparator;
import org.jetbrains.kotlin.resolve.RequireKotlinConstants;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.IntValue;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlags;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlagsUtilsKt;
import org.jetbrains.kotlin.types.AbbreviatedType;
import org.jetbrains.kotlin.types.DefinitelyNotNullType;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.extensions.TypeAttributeTranslators;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: DescriptorSerializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¤\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 h2\u00020\u0001:\u0001hBe\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015¢\u0006\u0002\u0010\u0016J\u000e\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0003H\u0002J\u0010\u0010-\u001a\u00020.2\u0006\u0010*\u001a\u00020'H\u0002J\u0018\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0002J\u0010\u00105\u001a\u0004\u0018\u0001062\u0006\u0010*\u001a\u000207J\u0010\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;H\u0002J\u0010\u0010<\u001a\u0002092\u0006\u0010*\u001a\u00020=H\u0002J\u0010\u0010>\u001a\u0002092\u0006\u0010?\u001a\u00020@H\u0002J\u0010\u0010A\u001a\u0002092\u0006\u0010*\u001a\u00020\u0006H\u0002J\u0010\u0010B\u001a\u00020C2\u0006\u0010*\u001a\u00020DH\u0002J\u001c\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020H2\f\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00030JJ\u0010\u0010K\u001a\u0004\u0018\u00010L2\u0006\u0010*\u001a\u00020MJ\u0012\u0010N\u001a\u0004\u0018\u00010O2\u0006\u0010P\u001a\u00020QH\u0002J\u0010\u0010R\u001a\u00020\u000e2\u0006\u0010*\u001a\u00020SH\u0002J\u0015\u00103\u001a\u0002022\u0006\u00103\u001a\u00020TH\u0000¢\u0006\u0002\bUJ\u0012\u0010V\u001a\u0004\u0018\u00010W2\u0006\u0010*\u001a\u00020XH\u0002J\u0010\u0010Y\u001a\u00020Z2\u0006\u0010[\u001a\u00020\\H\u0002J\u000e\u0010]\u001a\u0002092\u0006\u00103\u001a\u00020TJ\u0010\u0010^\u001a\u00020_2\u0006\u0010^\u001a\u00020\u0006H\u0002J\b\u0010`\u001a\u00020\u000eH\u0002J\u0010\u0010a\u001a\u00020b2\u0006\u0010*\u001a\u00020cH\u0002J\u001a\u0010d\u001a\b\u0012\u0004\u0012\u0002090\u0012*\u00020\f2\u0006\u0010*\u001a\u00020\u0003H\u0002J\u0014\u0010e\u001a\u000209*\u00020\f2\u0006\u0010f\u001a\u00020gH\u0002R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001c\u001a\u00020\u001d8F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0015¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006i"}, d2 = {"Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", "", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "typeParameters", "Lorg/jetbrains/kotlin/metadata/serialization/Interner;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", SchemaSymbols.ATTVAL_EXTENSION, "Lorg/jetbrains/kotlin/serialization/SerializerExtension;", "typeTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableTypeTable;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "serializeTypeTableToFunction", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "plugins", "", "Lorg/jetbrains/kotlin/serialization/DescriptorSerializerPlugin;", "typeAttributeTranslators", "Lorg/jetbrains/kotlin/types/extensions/TypeAttributeTranslators;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/metadata/serialization/Interner;Lorg/jetbrains/kotlin/serialization/SerializerExtension;Lorg/jetbrains/kotlin/metadata/serialization/MutableTypeTable;Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;ZLorg/jetbrains/kotlin/config/LanguageVersionSettings;Ljava/util/List;Lorg/jetbrains/kotlin/types/extensions/TypeAttributeTranslators;)V", "contractSerializer", "Lorg/jetbrains/kotlin/serialization/ContractSerializer;", "metDefinitelyNotNullType", "getPlugins", "()Ljava/util/List;", "stringTable", "Lorg/jetbrains/kotlin/serialization/DescriptorAwareStringTable;", "getStringTable", "()Lorg/jetbrains/kotlin/serialization/DescriptorAwareStringTable;", "getTypeAttributeTranslators", "()Lorg/jetbrains/kotlin/types/extensions/TypeAttributeTranslators;", "getTypeTable", "()Lorg/jetbrains/kotlin/metadata/serialization/MutableTypeTable;", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Builder;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "constructorProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor$Builder;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "createChildSerializer", "enumEntryProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$EnumEntry$Builder;", "fillFromPossiblyInnerType", "", "builder", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Builder;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/descriptors/PossiblyInnerType;", "functionProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getAccessorFlags", "", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "getClassifierId", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "getSimpleNameIndex", "name", "Lorg/jetbrains/kotlin/name/Name;", "getTypeParameterId", "normalizeVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "packagePartProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package$Builder;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "members", "", "propertyProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "serializeVersionRequirementFromRequireKotlin", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$VersionRequirement$Builder;", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "shouldSerializeHasStableParameterNames", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "Lorg/jetbrains/kotlin/types/KotlinType;", "type$serialization", "typeAliasProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias$Builder;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "typeArgument", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Argument$Builder;", "typeProjection", "Lorg/jetbrains/kotlin/types/TypeProjection;", "typeId", "typeParameter", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter$Builder;", "useTypeTable", "valueParameter", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter$Builder;", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "serializeVersionRequirements", "writeVersionRequirement", "languageFeature", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "Companion", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DescriptorSerializer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final DeclarationDescriptor containingDeclaration;
    private final ContractSerializer contractSerializer;
    private final SerializerExtension extension;
    private final LanguageVersionSettings languageVersionSettings;
    private boolean metDefinitelyNotNullType;
    private final List<DescriptorSerializerPlugin> plugins;
    private final boolean serializeTypeTableToFunction;
    private final TypeAttributeTranslators typeAttributeTranslators;
    private final Interner<TypeParameterDescriptor> typeParameters;
    private final MutableTypeTable typeTable;
    private final MutableVersionRequirementTable versionRequirementTable;

    public /* synthetic */ DescriptorSerializer(DeclarationDescriptor declarationDescriptor, Interner interner, SerializerExtension serializerExtension, MutableTypeTable mutableTypeTable, MutableVersionRequirementTable mutableVersionRequirementTable, boolean z, LanguageVersionSettings languageVersionSettings, List list, TypeAttributeTranslators typeAttributeTranslators, DefaultConstructorMarker defaultConstructorMarker) {
        this(declarationDescriptor, interner, serializerExtension, mutableTypeTable, mutableVersionRequirementTable, z, languageVersionSettings, list, typeAttributeTranslators);
    }

    @JvmStatic
    public static final DescriptorSerializer create(ClassDescriptor classDescriptor, SerializerExtension serializerExtension, DescriptorSerializer descriptorSerializer, LanguageVersionSettings languageVersionSettings, Project project) {
        return INSTANCE.create(classDescriptor, serializerExtension, descriptorSerializer, languageVersionSettings, project);
    }

    @JvmStatic
    public static final DescriptorSerializer createForLambda(SerializerExtension serializerExtension, LanguageVersionSettings languageVersionSettings) {
        return INSTANCE.createForLambda(serializerExtension, languageVersionSettings);
    }

    @JvmStatic
    public static final DescriptorSerializer createTopLevel(SerializerExtension serializerExtension, LanguageVersionSettings languageVersionSettings, Project project) {
        return INSTANCE.createTopLevel(serializerExtension, languageVersionSettings, project);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private DescriptorSerializer(DeclarationDescriptor declarationDescriptor, Interner<TypeParameterDescriptor> interner, SerializerExtension serializerExtension, MutableTypeTable mutableTypeTable, MutableVersionRequirementTable mutableVersionRequirementTable, boolean z, LanguageVersionSettings languageVersionSettings, List<? extends DescriptorSerializerPlugin> list, TypeAttributeTranslators typeAttributeTranslators) {
        this.containingDeclaration = declarationDescriptor;
        this.typeParameters = interner;
        this.extension = serializerExtension;
        this.typeTable = mutableTypeTable;
        this.versionRequirementTable = mutableVersionRequirementTable;
        this.serializeTypeTableToFunction = z;
        this.languageVersionSettings = languageVersionSettings;
        this.plugins = list;
        this.typeAttributeTranslators = typeAttributeTranslators;
        this.contractSerializer = new ContractSerializer();
    }

    public final MutableTypeTable getTypeTable() {
        return this.typeTable;
    }

    /* synthetic */ DescriptorSerializer(DeclarationDescriptor declarationDescriptor, Interner interner, SerializerExtension serializerExtension, MutableTypeTable mutableTypeTable, MutableVersionRequirementTable mutableVersionRequirementTable, boolean z, LanguageVersionSettings languageVersionSettings, List list, TypeAttributeTranslators typeAttributeTranslators, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(declarationDescriptor, interner, serializerExtension, mutableTypeTable, mutableVersionRequirementTable, z, languageVersionSettings, (i & 128) != 0 ? CollectionsKt.emptyList() : list, (i & 256) != 0 ? null : typeAttributeTranslators);
    }

    public final List<DescriptorSerializerPlugin> getPlugins() {
        return this.plugins;
    }

    public final TypeAttributeTranslators getTypeAttributeTranslators() {
        return this.typeAttributeTranslators;
    }

    private final DescriptorSerializer createChildSerializer(DeclarationDescriptor descriptor) {
        return new DescriptorSerializer(descriptor, new Interner(this.typeParameters), this.extension, this.typeTable, this.versionRequirementTable, false, this.languageVersionSettings, this.plugins, this.typeAttributeTranslators);
    }

    public final DescriptorAwareStringTable getStringTable() {
        return this.extension.getStringTable();
    }

    private final boolean useTypeTable() {
        return this.extension.shouldUseTypeTable();
    }

    public final ProtoBuf.Class.Builder classProto(ClassDescriptor classDescriptor) {
        List listSort;
        ProtoBuf.Function.Builder builderFunctionProto;
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        ProtoBuf.Class.Builder builderNewBuilder = ProtoBuf.Class.newBuilder();
        boolean z = false;
        boolean z2 = classDescriptor.getKind() == ClassKind.ENUM_CLASS && this.languageVersionSettings.supportsFeature(LanguageFeature.EnumEntries);
        boolean z3 = INSTANCE.hasAnnotations(classDescriptor) || classDescriptor.isInline();
        ProtoBuf.Visibility visibilityDescriptorVisibility = ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, normalizeVisibility(classDescriptor));
        ProtoEnumFlags protoEnumFlags = ProtoEnumFlags.INSTANCE;
        Modality modality = classDescriptor.getModality();
        Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
        ProtoBuf.Modality modality2 = protoEnumFlags.modality(modality);
        ProtoEnumFlags protoEnumFlags2 = ProtoEnumFlags.INSTANCE;
        ClassKind kind = classDescriptor.getKind();
        Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
        ClassDescriptor classDescriptor2 = classDescriptor;
        int classFlags = Flags.getClassFlags(z3, visibilityDescriptorVisibility, modality2, protoEnumFlags2.classKind(kind, classDescriptor.getIsCompanionObject()), classDescriptor.getIsInner(), classDescriptor.isData(), classDescriptor.isExternal(), classDescriptor.get$isExpect(), InlineClassesUtilsKt.isValueClass(classDescriptor2), classDescriptor.isFun(), z2);
        if (classFlags != builderNewBuilder.getFlags()) {
            builderNewBuilder.setFlags(classFlags);
        }
        builderNewBuilder.setFqName(getClassifierId(classDescriptor));
        for (TypeParameterDescriptor typeParameterDescriptor : classDescriptor.getDeclaredTypeParameters()) {
            Intrinsics.checkNotNull(typeParameterDescriptor);
            builderNewBuilder.addTypeParameter(typeParameter(typeParameterDescriptor));
        }
        List<ReceiverParameterDescriptor> contextReceivers = classDescriptor.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        if (useTypeTable()) {
            Iterator<T> it = contextReceivers.iterator();
            while (it.hasNext()) {
                KotlinType type = ((ReceiverParameterDescriptor) it.next()).getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                builderNewBuilder.addContextReceiverTypeId(typeId(type));
            }
        } else {
            Iterator<T> it2 = contextReceivers.iterator();
            while (it2.hasNext()) {
                KotlinType type2 = ((ReceiverParameterDescriptor) it2.next()).getType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                builderNewBuilder.addContextReceiverType(type$serialization(type2));
            }
        }
        if (!KotlinBuiltIns.isSpecialClassWithNoSupertypes(classDescriptor)) {
            for (KotlinType kotlinType : classDescriptor.getTypeConstructor().mo7438getSupertypes()) {
                if (useTypeTable()) {
                    Intrinsics.checkNotNull(kotlinType);
                    builderNewBuilder.addSupertypeId(typeId(kotlinType));
                } else {
                    Intrinsics.checkNotNull(kotlinType);
                    builderNewBuilder.addSupertype(type$serialization(kotlinType));
                }
            }
        }
        if (!DescriptorUtils.isAnonymousObject(classDescriptor2) && classDescriptor.getKind() != ClassKind.ENUM_ENTRY) {
            for (ClassConstructorDescriptor classConstructorDescriptor : classDescriptor.getConstructors()) {
                Intrinsics.checkNotNull(classConstructorDescriptor);
                builderNewBuilder.addConstructor(constructorProto(classConstructorDescriptor));
            }
        }
        SerializerExtension.ClassMembersProducer customClassMembersProducer = this.extension.getCustomClassMembersProducer();
        if (customClassMembersProducer == null || (listSort = customClassMembersProducer.getCallableMembers(classDescriptor)) == null) {
            Companion companion = INSTANCE;
            Collection<DeclarationDescriptor> allDescriptors = DescriptorUtils.getAllDescriptors(classDescriptor.getDefaultType().getMemberScope());
            Intrinsics.checkNotNullExpressionValue(allDescriptors, "getAllDescriptors(...)");
            ArrayList arrayList = new ArrayList();
            for (Object obj : allDescriptors) {
                if (obj instanceof CallableMemberDescriptor) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : arrayList) {
                if (((CallableMemberDescriptor) obj2).getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                    arrayList2.add(obj2);
                }
            }
            listSort = companion.sort(arrayList2);
        }
        for (CallableMemberDescriptor callableMemberDescriptor : listSort) {
            if (callableMemberDescriptor instanceof PropertyDescriptor) {
                ProtoBuf.Property.Builder builderPropertyProto = propertyProto((PropertyDescriptor) callableMemberDescriptor);
                if (builderPropertyProto != null) {
                    builderNewBuilder.addProperty(builderPropertyProto);
                }
            } else if ((callableMemberDescriptor instanceof FunctionDescriptor) && (builderFunctionProto = functionProto((FunctionDescriptor) callableMemberDescriptor)) != null) {
                builderNewBuilder.addFunction(builderFunctionProto);
            }
        }
        Companion companion2 = INSTANCE;
        Collection<DeclarationDescriptor> allDescriptors2 = DescriptorUtils.getAllDescriptors(classDescriptor.getUnsubstitutedInnerClassesScope());
        Intrinsics.checkNotNullExpressionValue(allDescriptors2, "getAllDescriptors(...)");
        for (DeclarationDescriptor declarationDescriptor : companion2.sort(allDescriptors2)) {
            if (declarationDescriptor instanceof TypeAliasDescriptor) {
                ProtoBuf.TypeAlias.Builder builderTypeAliasProto = typeAliasProto((TypeAliasDescriptor) declarationDescriptor);
                if (builderTypeAliasProto != null) {
                    builderNewBuilder.addTypeAlias(builderTypeAliasProto);
                }
            } else {
                Name name2 = declarationDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                int simpleNameIndex = getSimpleNameIndex(name2);
                if (DescriptorUtils.isEnumEntry(declarationDescriptor)) {
                    Intrinsics.checkNotNull(declarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    builderNewBuilder.addEnumEntry(enumEntryProto((ClassDescriptor) declarationDescriptor));
                } else {
                    builderNewBuilder.addNestedClassName(simpleNameIndex);
                }
            }
        }
        for (ClassDescriptor classDescriptor3 : classDescriptor.getSealedSubclasses()) {
            Intrinsics.checkNotNull(classDescriptor3);
            builderNewBuilder.addSealedSubclassFqName(getClassifierId(classDescriptor3));
        }
        ClassDescriptor classDescriptorMo6346getCompanionObjectDescriptor = classDescriptor.mo6346getCompanionObjectDescriptor();
        if (classDescriptorMo6346getCompanionObjectDescriptor != null) {
            Name name3 = classDescriptorMo6346getCompanionObjectDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
            builderNewBuilder.setCompanionObjectName(getSimpleNameIndex(name3));
        }
        InlineClassRepresentation<SimpleType> inlineClassRepresentation = DescriptorUtilsKt.getInlineClassRepresentation(classDescriptor);
        if (inlineClassRepresentation != null) {
            builderNewBuilder.setInlineClassUnderlyingPropertyName(getSimpleNameIndex(inlineClassRepresentation.getUnderlyingPropertyName()));
            Object obj3 = null;
            for (Object obj4 : listSort) {
                CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) obj4;
                if (callableMemberDescriptor2 instanceof PropertyDescriptor) {
                    PropertyDescriptor propertyDescriptor = (PropertyDescriptor) callableMemberDescriptor2;
                    if (propertyDescriptor.getExtensionReceiverParameter() == null && Intrinsics.areEqual(propertyDescriptor.getShortName(), inlineClassRepresentation.getUnderlyingPropertyName())) {
                        if (z) {
                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                        }
                        z = true;
                        obj3 = obj4;
                    }
                }
            }
            if (z) {
                if (!((CallableMemberDescriptor) obj3).getVisibility().isPublicAPI()) {
                    if (useTypeTable()) {
                        builderNewBuilder.setInlineClassUnderlyingTypeId(typeId((KotlinType) inlineClassRepresentation.getUnderlyingType()));
                    } else {
                        builderNewBuilder.setInlineClassUnderlyingType(type$serialization((KotlinType) inlineClassRepresentation.getUnderlyingType()));
                    }
                }
            } else {
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
        }
        MultiFieldValueClassRepresentation<SimpleType> multiFieldValueClassRepresentation = DescriptorUtilsKt.getMultiFieldValueClassRepresentation(classDescriptor);
        if (multiFieldValueClassRepresentation != null) {
            Iterable underlyingPropertyNamesToTypes = multiFieldValueClassRepresentation.getUnderlyingPropertyNamesToTypes();
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(underlyingPropertyNamesToTypes, 10));
            Iterator it3 = underlyingPropertyNamesToTypes.iterator();
            while (it3.hasNext()) {
                arrayList3.add(Integer.valueOf(getSimpleNameIndex((Name) ((Pair) it3.next()).component1())));
            }
            builderNewBuilder.addAllMultiFieldValueClassUnderlyingName(arrayList3);
            if (useTypeTable()) {
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(underlyingPropertyNamesToTypes, 10));
                Iterator it4 = underlyingPropertyNamesToTypes.iterator();
                while (it4.hasNext()) {
                    arrayList4.add(Integer.valueOf(typeId((SimpleType) ((Pair) it4.next()).component2())));
                }
                builderNewBuilder.addAllMultiFieldValueClassUnderlyingTypeId(arrayList4);
            } else {
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(underlyingPropertyNamesToTypes, 10));
                Iterator it5 = underlyingPropertyNamesToTypes.iterator();
                while (it5.hasNext()) {
                    arrayList5.add(type$serialization((SimpleType) ((Pair) it5.next()).component2()).build());
                }
                builderNewBuilder.addAllMultiFieldValueClassUnderlyingType(arrayList5);
            }
        }
        MutableVersionRequirementTable mutableVersionRequirementTable = this.versionRequirementTable;
        if (mutableVersionRequirementTable == null) {
            throw new IllegalStateException(("Version requirements must be serialized for classes: " + classDescriptor).toString());
        }
        builderNewBuilder.addAllVersionRequirement(serializeVersionRequirements(mutableVersionRequirementTable, classDescriptor2));
        SerializerExtension serializerExtension = this.extension;
        Intrinsics.checkNotNull(builderNewBuilder);
        serializerExtension.serializeClass(classDescriptor, builderNewBuilder, this.versionRequirementTable, this);
        Iterator<T> it6 = this.plugins.iterator();
        while (it6.hasNext()) {
            ((DescriptorSerializerPlugin) it6.next()).afterClass(classDescriptor, builderNewBuilder, this.versionRequirementTable, this, this.extension);
        }
        if (this.metDefinitelyNotNullType) {
            builderNewBuilder.addVersionRequirement(INSTANCE.writeLanguageVersionRequirement(LanguageFeature.DefinitelyNonNullableTypes, this.versionRequirementTable));
        }
        ProtoBuf.TypeTable typeTableSerialize = this.typeTable.serialize();
        if (typeTableSerialize != null) {
            builderNewBuilder.setTypeTable(typeTableSerialize);
        }
        ProtoBuf.VersionRequirementTable versionRequirementTableSerialize = this.versionRequirementTable.serialize();
        if (versionRequirementTableSerialize != null) {
            builderNewBuilder.setVersionRequirementTable(versionRequirementTableSerialize);
        }
        return builderNewBuilder;
    }

    public final ProtoBuf.Property.Builder propertyProto(PropertyDescriptor descriptor) {
        boolean z;
        boolean z2;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ProtoBuf.Property.Builder builderNewBuilder = ProtoBuf.Property.newBuilder();
        PropertyDescriptor propertyDescriptor = descriptor;
        DescriptorSerializer descriptorSerializerCreateChildSerializer = createChildSerializer(propertyDescriptor);
        ConstantValue<?> compileTimeInitializer = descriptor.mo6754getCompileTimeInitializer();
        boolean z3 = (compileTimeInitializer == null || (compileTimeInitializer instanceof NullValue)) ? false : true;
        Companion companion = INSTANCE;
        boolean z4 = companion.hasAnnotations(descriptor) || companion.hasAnnotations(descriptor.getBackingField()) || companion.hasAnnotations(descriptor.getDelegateField());
        PropertyDescriptor propertyDescriptor2 = descriptor;
        ProtoBuf.Visibility visibilityDescriptorVisibility = ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, normalizeVisibility(propertyDescriptor2));
        ProtoEnumFlags protoEnumFlags = ProtoEnumFlags.INSTANCE;
        Modality modality = descriptor.getModality();
        Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
        int accessorFlags = Flags.getAccessorFlags(z4, visibilityDescriptorVisibility, protoEnumFlags.modality(modality), false, false, false);
        PropertyGetterDescriptor getter = descriptor.getGetter();
        if (getter != null) {
            int accessorFlags2 = getAccessorFlags(getter);
            if (accessorFlags2 != accessorFlags) {
                builderNewBuilder.setGetterFlags(accessorFlags2);
            }
            z = true;
        } else {
            z = false;
        }
        PropertySetterDescriptor setter = descriptor.getSetter();
        if (setter != null) {
            int accessorFlags3 = getAccessorFlags(setter);
            if (accessorFlags3 != accessorFlags) {
                builderNewBuilder.setSetterFlags(accessorFlags3);
            }
            if (!setter.isDefault()) {
                DescriptorSerializer descriptorSerializerCreateChildSerializer2 = descriptorSerializerCreateChildSerializer.createChildSerializer(setter);
                for (ValueParameterDescriptor valueParameterDescriptor : setter.getValueParameters()) {
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    builderNewBuilder.setSetterValueParameter(descriptorSerializerCreateChildSerializer2.valueParameter(valueParameterDescriptor));
                }
            }
            z2 = true;
        } else {
            z2 = false;
        }
        ProtoBuf.Visibility visibilityDescriptorVisibility2 = ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, normalizeVisibility(propertyDescriptor2));
        ProtoEnumFlags protoEnumFlags2 = ProtoEnumFlags.INSTANCE;
        Modality modality2 = descriptor.getModality();
        Intrinsics.checkNotNullExpressionValue(modality2, "getModality(...)");
        ProtoBuf.Modality modality3 = protoEnumFlags2.modality(modality2);
        ProtoEnumFlags protoEnumFlags3 = ProtoEnumFlags.INSTANCE;
        CallableMemberDescriptor.Kind kind = descriptor.getKind();
        Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
        int propertyFlags = Flags.getPropertyFlags(z4, visibilityDescriptorVisibility2, modality3, ProtoEnumFlagsUtilsKt.memberKind(protoEnumFlags3, kind), descriptor.isVar(), z, z2, z3, descriptor.isConst(), descriptor.isLateInit(), descriptor.isExternal(), descriptor.getIsDelegated(), descriptor.get$isExpect());
        if (propertyFlags != builderNewBuilder.getFlags()) {
            builderNewBuilder.setFlags(propertyFlags);
        }
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        builderNewBuilder.setName(getSimpleNameIndex(name2));
        if (useTypeTable()) {
            KotlinType type = descriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            builderNewBuilder.setReturnTypeId(descriptorSerializerCreateChildSerializer.typeId(type));
        } else {
            KotlinType type2 = descriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            builderNewBuilder.setReturnType(descriptorSerializerCreateChildSerializer.type$serialization(type2));
        }
        for (TypeParameterDescriptor typeParameterDescriptor : descriptor.getTypeParameters()) {
            Intrinsics.checkNotNull(typeParameterDescriptor);
            builderNewBuilder.addTypeParameter(descriptorSerializerCreateChildSerializer.typeParameter(typeParameterDescriptor));
        }
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            if (useTypeTable()) {
                KotlinType type3 = extensionReceiverParameter.getType();
                Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                builderNewBuilder.setReceiverTypeId(descriptorSerializerCreateChildSerializer.typeId(type3));
            } else {
                KotlinType type4 = extensionReceiverParameter.getType();
                Intrinsics.checkNotNullExpressionValue(type4, "getType(...)");
                builderNewBuilder.setReceiverType(descriptorSerializerCreateChildSerializer.type$serialization(type4));
            }
        }
        List<ReceiverParameterDescriptor> contextReceiverParameters = descriptor.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        if (useTypeTable()) {
            Iterator<T> it = contextReceiverParameters.iterator();
            while (it.hasNext()) {
                KotlinType type5 = ((ReceiverParameterDescriptor) it.next()).getType();
                Intrinsics.checkNotNullExpressionValue(type5, "getType(...)");
                builderNewBuilder.addContextReceiverTypeId(descriptorSerializerCreateChildSerializer.typeId(type5));
            }
        } else {
            Iterator<T> it2 = contextReceiverParameters.iterator();
            while (it2.hasNext()) {
                KotlinType type6 = ((ReceiverParameterDescriptor) it2.next()).getType();
                Intrinsics.checkNotNullExpressionValue(type6, "getType(...)");
                builderNewBuilder.addContextReceiverType(descriptorSerializerCreateChildSerializer.type$serialization(type6));
            }
        }
        MutableVersionRequirementTable mutableVersionRequirementTable = this.versionRequirementTable;
        if (mutableVersionRequirementTable != null) {
            builderNewBuilder.addAllVersionRequirement(serializeVersionRequirements(mutableVersionRequirementTable, propertyDescriptor));
            if (descriptorSerializerCreateChildSerializer.metDefinitelyNotNullType) {
                builderNewBuilder.addVersionRequirement(writeVersionRequirement(mutableVersionRequirementTable, LanguageFeature.DefinitelyNonNullableTypes));
            }
        }
        SerializerExtension serializerExtension = this.extension;
        Intrinsics.checkNotNull(builderNewBuilder);
        serializerExtension.serializeProperty(descriptor, builderNewBuilder, this.versionRequirementTable, descriptorSerializerCreateChildSerializer);
        Iterator<T> it3 = this.plugins.iterator();
        while (it3.hasNext()) {
            ((DescriptorSerializerPlugin) it3.next()).afterProperty(descriptor, builderNewBuilder, this.versionRequirementTable, this, this.extension);
        }
        return builderNewBuilder;
    }

    private final DescriptorVisibility normalizeVisibility(DeclarationDescriptorWithVisibility descriptor) {
        if (this.extension.shouldUseNormalizedVisibility()) {
            return descriptor.getVisibility().normalize();
        }
        DescriptorVisibility visibility = descriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        return visibility;
    }

    private final boolean shouldSerializeHasStableParameterNames(CallableMemberDescriptor descriptor) {
        return descriptor.hasStableParameterNames() || descriptor.getKind() == CallableMemberDescriptor.Kind.DELEGATION;
    }

    public final ProtoBuf.Function.Builder functionProto(FunctionDescriptor descriptor) {
        ProtoBuf.TypeTable typeTableSerialize;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ProtoBuf.Function.Builder builderNewBuilder = ProtoBuf.Function.newBuilder();
        FunctionDescriptor functionDescriptor = descriptor;
        DescriptorSerializer descriptorSerializerCreateChildSerializer = createChildSerializer(functionDescriptor);
        boolean zHasAnnotations = INSTANCE.hasAnnotations(descriptor);
        ProtoBuf.Visibility visibilityDescriptorVisibility = ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, normalizeVisibility(descriptor));
        ProtoEnumFlags protoEnumFlags = ProtoEnumFlags.INSTANCE;
        Modality modality = descriptor.getModality();
        Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
        ProtoBuf.Modality modality2 = protoEnumFlags.modality(modality);
        ProtoEnumFlags protoEnumFlags2 = ProtoEnumFlags.INSTANCE;
        CallableMemberDescriptor.Kind kind = descriptor.getKind();
        Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
        int functionFlags = Flags.getFunctionFlags(zHasAnnotations, visibilityDescriptorVisibility, modality2, ProtoEnumFlagsUtilsKt.memberKind(protoEnumFlags2, kind), descriptor.isOperator(), descriptor.isInfix(), descriptor.isInline(), descriptor.isTailrec(), descriptor.isExternal(), descriptor.isSuspend(), descriptor.get$isExpect(), shouldSerializeHasStableParameterNames(descriptor));
        if (functionFlags != builderNewBuilder.getFlags()) {
            builderNewBuilder.setFlags(functionFlags);
        }
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        builderNewBuilder.setName(getSimpleNameIndex(name2));
        if (useTypeTable()) {
            KotlinType returnType = descriptor.getReturnType();
            Intrinsics.checkNotNull(returnType);
            builderNewBuilder.setReturnTypeId(descriptorSerializerCreateChildSerializer.typeId(returnType));
        } else {
            KotlinType returnType2 = descriptor.getReturnType();
            Intrinsics.checkNotNull(returnType2);
            builderNewBuilder.setReturnType(descriptorSerializerCreateChildSerializer.type$serialization(returnType2));
        }
        for (TypeParameterDescriptor typeParameterDescriptor : descriptor.getTypeParameters()) {
            Intrinsics.checkNotNull(typeParameterDescriptor);
            builderNewBuilder.addTypeParameter(descriptorSerializerCreateChildSerializer.typeParameter(typeParameterDescriptor));
        }
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            if (useTypeTable()) {
                KotlinType type = extensionReceiverParameter.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                builderNewBuilder.setReceiverTypeId(descriptorSerializerCreateChildSerializer.typeId(type));
            } else {
                KotlinType type2 = extensionReceiverParameter.getType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                builderNewBuilder.setReceiverType(descriptorSerializerCreateChildSerializer.type$serialization(type2));
            }
        }
        List<ReceiverParameterDescriptor> contextReceiverParameters = descriptor.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        if (useTypeTable()) {
            Iterator<T> it = contextReceiverParameters.iterator();
            while (it.hasNext()) {
                KotlinType type3 = ((ReceiverParameterDescriptor) it.next()).getType();
                Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                builderNewBuilder.addContextReceiverTypeId(descriptorSerializerCreateChildSerializer.typeId(type3));
            }
        } else {
            Iterator<T> it2 = contextReceiverParameters.iterator();
            while (it2.hasNext()) {
                KotlinType type4 = ((ReceiverParameterDescriptor) it2.next()).getType();
                Intrinsics.checkNotNullExpressionValue(type4, "getType(...)");
                builderNewBuilder.addContextReceiverType(descriptorSerializerCreateChildSerializer.type$serialization(type4));
            }
        }
        for (ValueParameterDescriptor valueParameterDescriptor : descriptor.getValueParameters()) {
            Intrinsics.checkNotNull(valueParameterDescriptor);
            builderNewBuilder.addValueParameter(descriptorSerializerCreateChildSerializer.valueParameter(valueParameterDescriptor));
        }
        ContractSerializer contractSerializer = this.contractSerializer;
        Intrinsics.checkNotNull(builderNewBuilder);
        contractSerializer.serializeContractOfFunctionIfAny(descriptor, builderNewBuilder, this);
        this.extension.serializeFunction(descriptor, builderNewBuilder, this.versionRequirementTable, descriptorSerializerCreateChildSerializer);
        Iterator<T> it3 = this.plugins.iterator();
        while (it3.hasNext()) {
            ((DescriptorSerializerPlugin) it3.next()).afterFunction(descriptor, builderNewBuilder, this.versionRequirementTable, this, this.extension);
        }
        if (this.serializeTypeTableToFunction && (typeTableSerialize = this.typeTable.serialize()) != null) {
            builderNewBuilder.setTypeTable(typeTableSerialize);
        }
        MutableVersionRequirementTable mutableVersionRequirementTable = this.versionRequirementTable;
        if (mutableVersionRequirementTable != null) {
            builderNewBuilder.addAllVersionRequirement(serializeVersionRequirements(mutableVersionRequirementTable, functionDescriptor));
            if (descriptorSerializerCreateChildSerializer.metDefinitelyNotNullType) {
                builderNewBuilder.addVersionRequirement(writeVersionRequirement(mutableVersionRequirementTable, LanguageFeature.DefinitelyNonNullableTypes));
            }
        }
        return builderNewBuilder;
    }

    private final ProtoBuf.Constructor.Builder constructorProto(ConstructorDescriptor descriptor) {
        ProtoBuf.Constructor.Builder builderNewBuilder = ProtoBuf.Constructor.newBuilder();
        ConstructorDescriptor constructorDescriptor = descriptor;
        DescriptorSerializer descriptorSerializerCreateChildSerializer = createChildSerializer(constructorDescriptor);
        int constructorFlags = Flags.getConstructorFlags(INSTANCE.hasAnnotations(descriptor), ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, normalizeVisibility(descriptor)), !descriptor.isPrimary(), shouldSerializeHasStableParameterNames(descriptor));
        if (constructorFlags != builderNewBuilder.getFlags()) {
            builderNewBuilder.setFlags(constructorFlags);
        }
        for (ValueParameterDescriptor valueParameterDescriptor : descriptor.getValueParameters()) {
            Intrinsics.checkNotNull(valueParameterDescriptor);
            builderNewBuilder.addValueParameter(descriptorSerializerCreateChildSerializer.valueParameter(valueParameterDescriptor));
        }
        MutableVersionRequirementTable mutableVersionRequirementTable = this.versionRequirementTable;
        if (mutableVersionRequirementTable != null) {
            builderNewBuilder.addAllVersionRequirement(serializeVersionRequirements(mutableVersionRequirementTable, constructorDescriptor));
            if (descriptorSerializerCreateChildSerializer.metDefinitelyNotNullType) {
                builderNewBuilder.addVersionRequirement(writeVersionRequirement(mutableVersionRequirementTable, LanguageFeature.DefinitelyNonNullableTypes));
            }
        }
        SerializerExtension serializerExtension = this.extension;
        Intrinsics.checkNotNull(builderNewBuilder);
        serializerExtension.serializeConstructor(descriptor, builderNewBuilder, descriptorSerializerCreateChildSerializer);
        Iterator<T> it = this.plugins.iterator();
        while (it.hasNext()) {
            ((DescriptorSerializerPlugin) it.next()).afterConstructor(descriptor, builderNewBuilder, this.versionRequirementTable, this, this.extension);
        }
        return builderNewBuilder;
    }

    private final ProtoBuf.TypeAlias.Builder typeAliasProto(TypeAliasDescriptor descriptor) {
        ProtoBuf.TypeAlias.Builder builderNewBuilder = ProtoBuf.TypeAlias.newBuilder();
        TypeAliasDescriptor typeAliasDescriptor = descriptor;
        DescriptorSerializer descriptorSerializerCreateChildSerializer = createChildSerializer(typeAliasDescriptor);
        TypeAliasDescriptor typeAliasDescriptor2 = descriptor;
        int typeAliasFlags = Flags.getTypeAliasFlags(INSTANCE.hasAnnotations(typeAliasDescriptor2), ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, normalizeVisibility(descriptor)));
        if (typeAliasFlags != builderNewBuilder.getFlags()) {
            builderNewBuilder.setFlags(typeAliasFlags);
        }
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        builderNewBuilder.setName(getSimpleNameIndex(name2));
        for (TypeParameterDescriptor typeParameterDescriptor : descriptor.getDeclaredTypeParameters()) {
            Intrinsics.checkNotNull(typeParameterDescriptor);
            builderNewBuilder.addTypeParameter(descriptorSerializerCreateChildSerializer.typeParameter(typeParameterDescriptor));
        }
        SimpleType underlyingType = descriptor.getUnderlyingType();
        if (useTypeTable()) {
            builderNewBuilder.setUnderlyingTypeId(descriptorSerializerCreateChildSerializer.typeId(underlyingType));
        } else {
            builderNewBuilder.setUnderlyingType(descriptorSerializerCreateChildSerializer.type$serialization(underlyingType));
        }
        SimpleType expandedType = descriptor.getExpandedType();
        if (useTypeTable()) {
            builderNewBuilder.setExpandedTypeId(descriptorSerializerCreateChildSerializer.typeId(expandedType));
        } else {
            builderNewBuilder.setExpandedType(descriptorSerializerCreateChildSerializer.type$serialization(expandedType));
        }
        MutableVersionRequirementTable mutableVersionRequirementTable = this.versionRequirementTable;
        if (mutableVersionRequirementTable != null) {
            builderNewBuilder.addAllVersionRequirement(serializeVersionRequirements(mutableVersionRequirementTable, typeAliasDescriptor));
            if (descriptorSerializerCreateChildSerializer.metDefinitelyNotNullType) {
                builderNewBuilder.addVersionRequirement(INSTANCE.writeLanguageVersionRequirement(LanguageFeature.DefinitelyNonNullableTypes, this.versionRequirementTable));
            }
        }
        Iterator<AnnotationDescriptor> it = DescriptorUtilsKt.getNonSourceAnnotations(typeAliasDescriptor2).iterator();
        while (it.hasNext()) {
            ProtoBuf.Annotation annotationSerializeAnnotation = this.extension.getAnnotationSerializer().serializeAnnotation(it.next());
            Intrinsics.checkNotNull(annotationSerializeAnnotation);
            builderNewBuilder.addAnnotation(annotationSerializeAnnotation);
        }
        SerializerExtension serializerExtension = this.extension;
        Intrinsics.checkNotNull(builderNewBuilder);
        serializerExtension.serializeTypeAlias(descriptor, builderNewBuilder);
        Iterator<T> it2 = this.plugins.iterator();
        while (it2.hasNext()) {
            ((DescriptorSerializerPlugin) it2.next()).afterTypealias(descriptor, builderNewBuilder, this.versionRequirementTable, this, this.extension);
        }
        return builderNewBuilder;
    }

    private final ProtoBuf.EnumEntry.Builder enumEntryProto(ClassDescriptor descriptor) {
        ProtoBuf.EnumEntry.Builder builderNewBuilder = ProtoBuf.EnumEntry.newBuilder();
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        builderNewBuilder.setName(getSimpleNameIndex(name2));
        SerializerExtension serializerExtension = this.extension;
        Intrinsics.checkNotNull(builderNewBuilder);
        serializerExtension.serializeEnumEntry(descriptor, builderNewBuilder);
        return builderNewBuilder;
    }

    private final ProtoBuf.ValueParameter.Builder valueParameter(ValueParameterDescriptor descriptor) {
        ProtoBuf.ValueParameter.Builder builderNewBuilder = ProtoBuf.ValueParameter.newBuilder();
        int valueParameterFlags = Flags.getValueParameterFlags(INSTANCE.hasAnnotations(descriptor), descriptor.declaresDefaultValue() || ArgumentsUtilsKt.isActualParameterWithAnyExpectedDefault(descriptor), descriptor.getIsCrossinline(), descriptor.getIsNoinline());
        if (valueParameterFlags != builderNewBuilder.getFlags()) {
            builderNewBuilder.setFlags(valueParameterFlags);
        }
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        builderNewBuilder.setName(getSimpleNameIndex(name2));
        if (useTypeTable()) {
            KotlinType type = descriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            builderNewBuilder.setTypeId(typeId(type));
        } else {
            KotlinType type2 = descriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            builderNewBuilder.setType(type$serialization(type2));
        }
        KotlinType varargElementType = descriptor.getVarargElementType();
        if (varargElementType != null) {
            if (useTypeTable()) {
                builderNewBuilder.setVarargElementTypeId(typeId(varargElementType));
            } else {
                builderNewBuilder.setVarargElementType(type$serialization(varargElementType));
            }
        }
        SerializerExtension serializerExtension = this.extension;
        Intrinsics.checkNotNull(builderNewBuilder);
        serializerExtension.serializeValueParameter(descriptor, builderNewBuilder);
        return builderNewBuilder;
    }

    private final ProtoBuf.TypeParameter.Builder typeParameter(TypeParameterDescriptor typeParameter) {
        ProtoBuf.TypeParameter.Builder builderNewBuilder = ProtoBuf.TypeParameter.newBuilder();
        builderNewBuilder.setId(getTypeParameterId(typeParameter));
        Name name2 = typeParameter.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        builderNewBuilder.setName(getSimpleNameIndex(name2));
        if (typeParameter.isReified() != builderNewBuilder.getReified()) {
            builderNewBuilder.setReified(typeParameter.isReified());
        }
        Companion companion = INSTANCE;
        Variance variance = typeParameter.getVariance();
        Intrinsics.checkNotNullExpressionValue(variance, "getVariance(...)");
        ProtoBuf.TypeParameter.Variance variance2 = companion.variance(variance);
        if (variance2 != builderNewBuilder.getVariance()) {
            builderNewBuilder.setVariance(variance2);
        }
        SerializerExtension serializerExtension = this.extension;
        Intrinsics.checkNotNull(builderNewBuilder);
        serializerExtension.serializeTypeParameter(typeParameter, builderNewBuilder);
        List<KotlinType> upperBounds = typeParameter.getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
        if (upperBounds.size() == 1 && KotlinBuiltIns.isDefaultBound((KotlinType) CollectionsKt.single((List) upperBounds))) {
            return builderNewBuilder;
        }
        for (KotlinType kotlinType : upperBounds) {
            if (useTypeTable()) {
                Intrinsics.checkNotNull(kotlinType);
                builderNewBuilder.addUpperBoundId(typeId(kotlinType));
            } else {
                Intrinsics.checkNotNull(kotlinType);
                builderNewBuilder.addUpperBound(type$serialization(kotlinType));
            }
        }
        return builderNewBuilder;
    }

    public final int typeId(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.typeTable.get(type$serialization(type));
    }

    public final ProtoBuf.Type.Builder type$serialization(KotlinType type) {
        KotlinType kotlinTypeReplaceAnnotations;
        Intrinsics.checkNotNullParameter(type, "type");
        ProtoBuf.Type.Builder builderNewBuilder = ProtoBuf.Type.newBuilder();
        if (KotlinTypeKt.isError(type)) {
            SerializerExtension serializerExtension = this.extension;
            Intrinsics.checkNotNull(builderNewBuilder);
            serializerExtension.serializeErrorType(type, builderNewBuilder);
            return builderNewBuilder;
        }
        if (FlexibleTypesKt.isFlexible(type)) {
            FlexibleType flexibleTypeAsFlexibleType = FlexibleTypesKt.asFlexibleType(type);
            ProtoBuf.Type.Builder builderType$serialization = type$serialization(flexibleTypeAsFlexibleType.getLowerBound());
            ProtoBuf.Type.Builder builderType$serialization2 = type$serialization(flexibleTypeAsFlexibleType.getUpperBound());
            this.extension.serializeFlexibleType(flexibleTypeAsFlexibleType, builderType$serialization, builderType$serialization2);
            if (useTypeTable()) {
                builderType$serialization.setFlexibleUpperBoundId(this.typeTable.get(builderType$serialization2));
            } else {
                builderType$serialization.setFlexibleUpperBound(builderType$serialization2);
            }
            return builderType$serialization;
        }
        if (FunctionTypesKt.isSuspendFunctionType(type)) {
            ProtoBuf.Type.Builder builderType$serialization3 = type$serialization(SuspendFunctionTypesKt.transformSuspendFunctionToRuntimeFunctionType(type));
            builderType$serialization3.setFlags(Flags.getTypeFlags(true, false));
            return builderType$serialization3;
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = type.getConstructor().getThis$0();
        if (classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? true : classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeAliasDescriptor) {
            PossiblyInnerType possiblyInnerTypeBuildPossiblyInnerType = TypeParameterUtilsKt.buildPossiblyInnerType(type);
            if (possiblyInnerTypeBuildPossiblyInnerType == null) {
                throw new IllegalStateException(("possiblyInnerType should not be null: " + type).toString());
            }
            Intrinsics.checkNotNull(builderNewBuilder);
            fillFromPossiblyInnerType(builderNewBuilder, possiblyInnerTypeBuildPossiblyInnerType);
        } else if (classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeParameterDescriptor) {
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) classifierDescriptorMo7434getDeclarationDescriptor;
            if (typeParameterDescriptor.getContainingDeclaration() == this.containingDeclaration) {
                Name name2 = typeParameterDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                builderNewBuilder.setTypeParameterName(getSimpleNameIndex(name2));
            } else {
                builderNewBuilder.setTypeParameter(getTypeParameterId(typeParameterDescriptor));
            }
            if (type.unwrap() instanceof DefinitelyNotNullType) {
                this.metDefinitelyNotNullType = true;
                builderNewBuilder.setFlags(Flags.getTypeFlags(false, true));
            }
            type.getArguments().isEmpty();
        }
        if (type.getIsMarkedNullable() != builderNewBuilder.getNullable()) {
            builderNewBuilder.setNullable(type.getIsMarkedNullable());
        }
        AbbreviatedType abbreviatedType = SpecialTypesKt.getAbbreviatedType(type);
        SimpleType abbreviation = abbreviatedType != null ? abbreviatedType.getAbbreviation() : null;
        if (abbreviation != null) {
            if (useTypeTable()) {
                builderNewBuilder.setAbbreviatedTypeId(typeId(abbreviation));
            } else {
                builderNewBuilder.setAbbreviatedType(type$serialization(abbreviation));
            }
        }
        TypeAttributeTranslators typeAttributeTranslators = this.typeAttributeTranslators;
        if (typeAttributeTranslators != null && (kotlinTypeReplaceAnnotations = TypeUtilsKt.replaceAnnotations(type, typeAttributeTranslators.toAnnotations(type.getAttributes()))) != null) {
            type = kotlinTypeReplaceAnnotations;
        }
        SerializerExtension serializerExtension2 = this.extension;
        Intrinsics.checkNotNull(builderNewBuilder);
        serializerExtension2.serializeType(type, builderNewBuilder);
        return builderNewBuilder;
    }

    private final void fillFromPossiblyInnerType(ProtoBuf.Type.Builder builder, PossiblyInnerType type) {
        ClassifierDescriptorWithTypeParameters classifierDescriptor = type.getClassifierDescriptor();
        int classifierId = getClassifierId(classifierDescriptor);
        if (classifierDescriptor instanceof ClassDescriptor) {
            builder.setClassName(classifierId);
        } else if (classifierDescriptor instanceof TypeAliasDescriptor) {
            builder.setTypeAliasName(classifierId);
        }
        if (getStringTable().isLocalClassIdReplacementKeptGeneric() || !DescriptorUtils.isLocal(classifierDescriptor)) {
            Iterator<TypeProjection> it = type.getArguments().iterator();
            while (it.hasNext()) {
                builder.addArgument(typeArgument(it.next()));
            }
        }
        if (type.getOuterType() != null) {
            ProtoBuf.Type.Builder builderNewBuilder = ProtoBuf.Type.newBuilder();
            Intrinsics.checkNotNull(builderNewBuilder);
            PossiblyInnerType outerType = type.getOuterType();
            Intrinsics.checkNotNull(outerType);
            fillFromPossiblyInnerType(builderNewBuilder, outerType);
            if (useTypeTable()) {
                builder.setOuterTypeId(this.typeTable.get(builderNewBuilder));
            } else {
                builder.setOuterType(builderNewBuilder);
            }
        }
    }

    private final ProtoBuf.Type.Argument.Builder typeArgument(TypeProjection typeProjection) {
        ProtoBuf.Type.Argument.Builder builderNewBuilder = ProtoBuf.Type.Argument.newBuilder();
        if (typeProjection.isStarProjection()) {
            builderNewBuilder.setProjection(ProtoBuf.Type.Argument.Projection.STAR);
        } else {
            Companion companion = INSTANCE;
            Variance projectionKind = typeProjection.getProjectionKind();
            Intrinsics.checkNotNullExpressionValue(projectionKind, "getProjectionKind(...)");
            ProtoBuf.Type.Argument.Projection projection = companion.projection(projectionKind);
            if (projection != builderNewBuilder.getProjection()) {
                builderNewBuilder.setProjection(projection);
            }
            if (useTypeTable()) {
                KotlinType type = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                builderNewBuilder.setTypeId(typeId(type));
            } else {
                KotlinType type2 = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                builderNewBuilder.setType(type$serialization(type2));
            }
        }
        Intrinsics.checkNotNull(builderNewBuilder);
        return builderNewBuilder;
    }

    public final ProtoBuf.Package.Builder packagePartProto(FqName packageFqName, Collection<? extends DeclarationDescriptor> members) {
        ProtoBuf.VersionRequirementTable versionRequirementTableSerialize;
        ProtoBuf.TypeAlias.Builder builderTypeAliasProto;
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(members, "members");
        ProtoBuf.Package.Builder builderNewBuilder = ProtoBuf.Package.newBuilder();
        for (DeclarationDescriptor declarationDescriptor : INSTANCE.sort(members)) {
            if (declarationDescriptor instanceof PropertyDescriptor) {
                ProtoBuf.Property.Builder builderPropertyProto = propertyProto((PropertyDescriptor) declarationDescriptor);
                if (builderPropertyProto != null) {
                    builderNewBuilder.addProperty(builderPropertyProto);
                }
            } else if (declarationDescriptor instanceof FunctionDescriptor) {
                ProtoBuf.Function.Builder builderFunctionProto = functionProto((FunctionDescriptor) declarationDescriptor);
                if (builderFunctionProto != null) {
                    builderNewBuilder.addFunction(builderFunctionProto);
                }
            } else if ((declarationDescriptor instanceof TypeAliasDescriptor) && (builderTypeAliasProto = typeAliasProto((TypeAliasDescriptor) declarationDescriptor)) != null) {
                builderNewBuilder.addTypeAlias(builderTypeAliasProto);
            }
        }
        SerializerExtension serializerExtension = this.extension;
        Intrinsics.checkNotNull(builderNewBuilder);
        serializerExtension.serializePackage(packageFqName, builderNewBuilder);
        ProtoBuf.TypeTable typeTableSerialize = this.typeTable.serialize();
        if (typeTableSerialize != null) {
            builderNewBuilder.setTypeTable(typeTableSerialize);
        }
        MutableVersionRequirementTable mutableVersionRequirementTable = this.versionRequirementTable;
        if (mutableVersionRequirementTable != null && (versionRequirementTableSerialize = mutableVersionRequirementTable.serialize()) != null) {
            builderNewBuilder.setVersionRequirementTable(versionRequirementTableSerialize);
        }
        return builderNewBuilder;
    }

    private final int writeVersionRequirement(MutableVersionRequirementTable mutableVersionRequirementTable, LanguageFeature languageFeature) {
        return INSTANCE.writeLanguageVersionRequirement(languageFeature, mutableVersionRequirementTable);
    }

    private final List<Integer> serializeVersionRequirements(MutableVersionRequirementTable mutableVersionRequirementTable, DeclarationDescriptor declarationDescriptor) {
        Annotations annotations = declarationDescriptor.getAnnotations();
        ArrayList arrayList = new ArrayList();
        for (AnnotationDescriptor annotationDescriptor : annotations) {
            if (Intrinsics.areEqual(annotationDescriptor.getFqName(), RequireKotlinConstants.INSTANCE.getFQ_NAME())) {
                arrayList.add(annotationDescriptor);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ProtoBuf.VersionRequirement.Builder builderSerializeVersionRequirementFromRequireKotlin = serializeVersionRequirementFromRequireKotlin((AnnotationDescriptor) it.next());
            if (builderSerializeVersionRequirementFromRequireKotlin != null) {
                arrayList2.add(builderSerializeVersionRequirementFromRequireKotlin);
            }
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it2 = arrayList3.iterator();
        while (it2.hasNext()) {
            arrayList4.add(Integer.valueOf(mutableVersionRequirementTable.get((ProtoBuf.VersionRequirement.Builder) it2.next())));
        }
        return arrayList4;
    }

    private final ProtoBuf.VersionRequirement.Builder serializeVersionRequirementFromRequireKotlin(AnnotationDescriptor annotation) {
        String value;
        MatchResult matchResultMatchEntire;
        String str;
        Integer intOrNull;
        Name enumEntryName;
        Name enumEntryName2;
        Integer intOrNull2;
        Integer intOrNull3;
        Map<Name, ConstantValue<?>> allValueArguments = annotation.getAllValueArguments();
        ConstantValue<?> constantValue = allValueArguments.get(RequireKotlinConstants.INSTANCE.getVERSION());
        StringValue stringValue = constantValue instanceof StringValue ? (StringValue) constantValue : null;
        if (stringValue == null || (value = stringValue.getValue()) == null || (matchResultMatchEntire = RequireKotlinConstants.INSTANCE.getVERSION_REGEX().matchEntire(value)) == null || (str = (String) CollectionsKt.getOrNull(matchResultMatchEntire.getGroupValues(), 1)) == null || (intOrNull = StringsKt.toIntOrNull(str)) == null) {
            return null;
        }
        int iIntValue = intOrNull.intValue();
        String str2 = (String) CollectionsKt.getOrNull(matchResultMatchEntire.getGroupValues(), 2);
        int iIntValue2 = 0;
        int iIntValue3 = (str2 == null || (intOrNull3 = StringsKt.toIntOrNull(str2)) == null) ? 0 : intOrNull3.intValue();
        String str3 = (String) CollectionsKt.getOrNull(matchResultMatchEntire.getGroupValues(), 4);
        if (str3 != null && (intOrNull2 = StringsKt.toIntOrNull(str3)) != null) {
            iIntValue2 = intOrNull2.intValue();
        }
        final ProtoBuf.VersionRequirement.Builder builderNewBuilder = ProtoBuf.VersionRequirement.newBuilder();
        new VersionRequirement.Version(iIntValue, iIntValue3, iIntValue2).encode(new Function1<Integer, Unit>() { // from class: org.jetbrains.kotlin.serialization.DescriptorSerializer.serializeVersionRequirementFromRequireKotlin.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke(num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                builderNewBuilder.setVersion(i);
            }
        }, new Function1<Integer, Unit>() { // from class: org.jetbrains.kotlin.serialization.DescriptorSerializer.serializeVersionRequirementFromRequireKotlin.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke(num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                builderNewBuilder.setVersionFull(i);
            }
        });
        ConstantValue<?> constantValue2 = allValueArguments.get(RequireKotlinConstants.INSTANCE.getMESSAGE());
        StringValue stringValue2 = constantValue2 instanceof StringValue ? (StringValue) constantValue2 : null;
        String value2 = stringValue2 != null ? stringValue2.getValue() : null;
        if (value2 != null) {
            builderNewBuilder.setMessage(getStringTable().getStringIndex(value2));
        }
        ConstantValue<?> constantValue3 = allValueArguments.get(RequireKotlinConstants.INSTANCE.getLEVEL());
        EnumValue enumValue = constantValue3 instanceof EnumValue ? (EnumValue) constantValue3 : null;
        String strAsString = (enumValue == null || (enumEntryName2 = enumValue.getEnumEntryName()) == null) ? null : enumEntryName2.asString();
        if (strAsString != null) {
            int iHashCode = strAsString.hashCode();
            if (iHashCode == 66247144) {
                strAsString.equals(MediaError.ERROR_TYPE_ERROR);
            } else if (iHashCode != 1842428796) {
                if (iHashCode == 2130809258 && strAsString.equals(VirtualFile.PROP_HIDDEN)) {
                    builderNewBuilder.setLevel(ProtoBuf.VersionRequirement.Level.HIDDEN);
                }
            } else if (strAsString.equals("WARNING")) {
                builderNewBuilder.setLevel(ProtoBuf.VersionRequirement.Level.WARNING);
            }
        }
        ConstantValue<?> constantValue4 = allValueArguments.get(RequireKotlinConstants.INSTANCE.getVERSION_KIND());
        EnumValue enumValue2 = constantValue4 instanceof EnumValue ? (EnumValue) constantValue4 : null;
        String strAsString2 = (enumValue2 == null || (enumEntryName = enumValue2.getEnumEntryName()) == null) ? null : enumEntryName.asString();
        if (strAsString2 != null) {
            int iHashCode2 = strAsString2.hashCode();
            if (iHashCode2 == -1625556271) {
                strAsString2.equals("LANGUAGE_VERSION");
            } else if (iHashCode2 != -1004593896) {
                if (iHashCode2 == 953725811 && strAsString2.equals("API_VERSION")) {
                    builderNewBuilder.setVersionKind(ProtoBuf.VersionRequirement.VersionKind.API_VERSION);
                }
            } else if (strAsString2.equals("COMPILER_VERSION")) {
                builderNewBuilder.setVersionKind(ProtoBuf.VersionRequirement.VersionKind.COMPILER_VERSION);
            }
        }
        ConstantValue<?> constantValue5 = allValueArguments.get(RequireKotlinConstants.INSTANCE.getERROR_CODE());
        IntValue intValue = constantValue5 instanceof IntValue ? (IntValue) constantValue5 : null;
        Integer value3 = intValue != null ? intValue.getValue() : null;
        if (value3 != null && value3.intValue() != -1) {
            builderNewBuilder.setErrorCode(value3.intValue());
        }
        return builderNewBuilder;
    }

    private final int getClassifierId(ClassifierDescriptorWithTypeParameters descriptor) {
        return getStringTable().getFqNameIndex(descriptor);
    }

    private final int getSimpleNameIndex(Name name2) {
        DescriptorAwareStringTable stringTable = getStringTable();
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return stringTable.getStringIndex(strAsString);
    }

    private final int getTypeParameterId(TypeParameterDescriptor descriptor) {
        return this.typeParameters.intern(descriptor);
    }

    private final int getAccessorFlags(PropertyAccessorDescriptor accessor) {
        boolean zHasAnnotations = INSTANCE.hasAnnotations(accessor);
        ProtoBuf.Visibility visibilityDescriptorVisibility = ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, normalizeVisibility(accessor));
        ProtoEnumFlags protoEnumFlags = ProtoEnumFlags.INSTANCE;
        Modality modality = accessor.getModality();
        Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
        return Flags.getAccessorFlags(zHasAnnotations, visibilityDescriptorVisibility, protoEnumFlags.modality(modality), !accessor.isDefault(), accessor.isExternal(), accessor.isInline());
    }

    /* JADX INFO: compiled from: DescriptorSerializer.kt */
    @Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J6\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u0007J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0007J$\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u0007J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0005\u001a\u0004\u0018\u00010\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J$\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00190\u0018\"\b\b\u0000\u0010\u0019*\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00190\u001cJ\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001d\u001a\u00020\u0016H\u0002J\u0016\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$J.\u0010%\u001a\u00020 2\u0006\u0010&\u001a\u00020 2\u0006\u0010'\u001a\u00020 2\u0006\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020*2\u0006\u0010#\u001a\u00020$¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/serialization/DescriptorSerializer$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", SchemaSymbols.ATTVAL_EXTENSION, "Lorg/jetbrains/kotlin/serialization/SerializerExtension;", "parentSerializer", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "createForLambda", "createTopLevel", "hasAnnotations", "", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotated;", "projection", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Argument$Projection;", "projectionKind", "Lorg/jetbrains/kotlin/types/Variance;", "sort", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptors", "", "variance", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter$Variance;", "writeLanguageVersionRequirement", "", "languageFeature", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "writeVersionRequirement", "major", "minor", "patch", "versionKind", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$VersionRequirement$VersionKind;", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: DescriptorSerializer.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Variance.values().length];
                try {
                    iArr[Variance.INVARIANT.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[Variance.IN_VARIANCE.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[Variance.OUT_VARIANCE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ DescriptorSerializer createTopLevel$default(Companion companion, SerializerExtension serializerExtension, LanguageVersionSettings languageVersionSettings, Project project, int i, Object obj) {
            if ((i & 4) != 0) {
                project = null;
            }
            return companion.createTopLevel(serializerExtension, languageVersionSettings, project);
        }

        @JvmStatic
        public final DescriptorSerializer createTopLevel(SerializerExtension extension, LanguageVersionSettings languageVersionSettings, Project project) {
            Intrinsics.checkNotNullParameter(extension, "extension");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            Interner interner = new Interner(null, 1, null);
            MutableTypeTable mutableTypeTable = new MutableTypeTable();
            MutableVersionRequirementTable mutableVersionRequirementTable = new MutableVersionRequirementTable();
            TypeAttributeTranslators typeAttributeTranslatorsCreateTranslators = project != null ? TypeAttributeTranslatorExtension.INSTANCE.createTranslators(project) : null;
            List<DescriptorSerializerPlugin> instances = project != null ? DescriptorSerializerPlugin.INSTANCE.getInstances(project) : null;
            return new DescriptorSerializer(null, interner, extension, mutableTypeTable, mutableVersionRequirementTable, false, languageVersionSettings, instances == null ? CollectionsKt.emptyList() : instances, typeAttributeTranslatorsCreateTranslators, null);
        }

        @JvmStatic
        public final DescriptorSerializer createForLambda(SerializerExtension extension, LanguageVersionSettings languageVersionSettings) {
            Intrinsics.checkNotNullParameter(extension, "extension");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            return new DescriptorSerializer(null, new Interner(null, 1, null), extension, new MutableTypeTable(), null, true, languageVersionSettings, null, null, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, null);
        }

        public static /* synthetic */ DescriptorSerializer create$default(Companion companion, ClassDescriptor classDescriptor, SerializerExtension serializerExtension, DescriptorSerializer descriptorSerializer, LanguageVersionSettings languageVersionSettings, Project project, int i, Object obj) {
            if ((i & 16) != 0) {
                project = null;
            }
            return companion.create(classDescriptor, serializerExtension, descriptorSerializer, languageVersionSettings, project);
        }

        @JvmStatic
        public final DescriptorSerializer create(ClassDescriptor descriptor, SerializerExtension extension, DescriptorSerializer parentSerializer, LanguageVersionSettings languageVersionSettings, Project project) {
            DescriptorSerializer descriptorSerializerCreateTopLevel$default;
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(extension, "extension");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            DeclarationDescriptor moduleDescriptor = descriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(moduleDescriptor, "getContainingDeclaration(...)");
            boolean z = moduleDescriptor instanceof ClassDescriptor;
            if (z) {
                descriptorSerializerCreateTopLevel$default = parentSerializer == null ? create((ClassDescriptor) moduleDescriptor, extension, null, languageVersionSettings, project) : parentSerializer;
            } else {
                descriptorSerializerCreateTopLevel$default = createTopLevel$default(this, extension, languageVersionSettings, null, 4, null);
            }
            List<DescriptorSerializerPlugin> instances = project != null ? DescriptorSerializerPlugin.INSTANCE.getInstances(project) : null;
            if (instances == null) {
                instances = CollectionsKt.emptyList();
            }
            DescriptorSerializer descriptorSerializer = new DescriptorSerializer(descriptor, new Interner(descriptorSerializerCreateTopLevel$default.typeParameters), extension, new MutableTypeTable(), (!z || VersionSpecificBehaviorKt.isVersionRequirementTableWrittenCorrectly(extension.getMetadataVersion())) ? new MutableVersionRequirementTable() : descriptorSerializerCreateTopLevel$default.versionRequirementTable, false, languageVersionSettings, instances, project != null ? TypeAttributeTranslatorExtension.INSTANCE.createTranslators(project) : null, null);
            for (TypeParameterDescriptor typeParameterDescriptor : descriptor.getDeclaredTypeParameters()) {
                Interner interner = descriptorSerializer.typeParameters;
                Intrinsics.checkNotNull(typeParameterDescriptor);
                interner.intern(typeParameterDescriptor);
            }
            return descriptorSerializer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ProtoBuf.TypeParameter.Variance variance(Variance variance) {
            int i = WhenMappings.$EnumSwitchMapping$0[variance.ordinal()];
            if (i == 1) {
                return ProtoBuf.TypeParameter.Variance.INV;
            }
            if (i == 2) {
                return ProtoBuf.TypeParameter.Variance.IN;
            }
            if (i == 3) {
                return ProtoBuf.TypeParameter.Variance.OUT;
            }
            throw new NoWhenBranchMatchedException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ProtoBuf.Type.Argument.Projection projection(Variance projectionKind) {
            int i = WhenMappings.$EnumSwitchMapping$0[projectionKind.ordinal()];
            if (i == 1) {
                return ProtoBuf.Type.Argument.Projection.INV;
            }
            if (i == 2) {
                return ProtoBuf.Type.Argument.Projection.IN;
            }
            if (i == 3) {
                return ProtoBuf.Type.Argument.Projection.OUT;
            }
            throw new NoWhenBranchMatchedException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean hasAnnotations(Annotated descriptor) {
            return (descriptor == null || DescriptorUtilsKt.getNonSourceAnnotations(descriptor).isEmpty()) ? false : true;
        }

        public final <T extends DeclarationDescriptor> List<T> sort(Collection<? extends T> descriptors) {
            Intrinsics.checkNotNullParameter(descriptors, "descriptors");
            ArrayList arrayList = new ArrayList(descriptors);
            Collections.sort(arrayList, MemberComparator.INSTANCE);
            return arrayList;
        }

        public final int writeLanguageVersionRequirement(LanguageFeature languageFeature, MutableVersionRequirementTable versionRequirementTable) {
            Intrinsics.checkNotNullParameter(languageFeature, "languageFeature");
            Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
            LanguageVersion sinceVersion = languageFeature.getSinceVersion();
            Intrinsics.checkNotNull(sinceVersion);
            return writeVersionRequirement(sinceVersion.getMajor(), sinceVersion.getMinor(), 0, ProtoBuf.VersionRequirement.VersionKind.LANGUAGE_VERSION, versionRequirementTable);
        }

        public final int writeVersionRequirement(int major, int minor, int patch, ProtoBuf.VersionRequirement.VersionKind versionKind, MutableVersionRequirementTable versionRequirementTable) {
            Intrinsics.checkNotNullParameter(versionKind, "versionKind");
            Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
            final ProtoBuf.VersionRequirement.Builder builderNewBuilder = ProtoBuf.VersionRequirement.newBuilder();
            new VersionRequirement.Version(major, minor, patch).encode(new Function1<Integer, Unit>() { // from class: org.jetbrains.kotlin.serialization.DescriptorSerializer$Companion$writeVersionRequirement$requirement$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                    invoke(num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(int i) {
                    builderNewBuilder.setVersion(i);
                }
            }, new Function1<Integer, Unit>() { // from class: org.jetbrains.kotlin.serialization.DescriptorSerializer$Companion$writeVersionRequirement$requirement$1$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                    invoke(num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(int i) {
                    builderNewBuilder.setVersionFull(i);
                }
            });
            if (versionKind != builderNewBuilder.getDefaultInstanceForType().getVersionKind()) {
                builderNewBuilder.setVersionKind(versionKind);
            }
            Intrinsics.checkNotNull(builderNewBuilder);
            return versionRequirementTable.get(builderNewBuilder);
        }
    }
}
