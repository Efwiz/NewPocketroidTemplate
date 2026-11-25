package org.jetbrains.kotlin.resolve.descriptorUtil;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.MultiFieldValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotated;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.descriptors.impl.DescriptorDerivedFromTypeAlias;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefinerKt;
import org.jetbrains.kotlin.types.checker.TypeRefinementSupport;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.DFS;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: DescriptorUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0098\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\u001a\u001e\u0010c\u001a\n\u0012\u0004\u0012\u00020d\u0018\u00010M2\u0006\u0010e\u001a\u00020\u00032\u0006\u0010f\u001a\u00020\u0003\u001a\u000e\u0010g\u001a\u00020*2\u0006\u0010h\u001a\u00020i\u001a\u0012\u0010j\u001a\b\u0012\u0004\u0012\u00020\u00030R*\u00020\u0003H\u0002\u001a\u001b\u0010k\u001a\u00020*\"\b\b\u0000\u0010l*\u00020m*\u0002HlH\u0002¢\u0006\u0002\u0010n\u001a\n\u0010o\u001a\u00020**\u00020p\u001a\u0016\u0010q\u001a\b\u0012\u0004\u0012\u00020\u00040M*\b\u0012\u0004\u0012\u00020\u00040r\u001a\u0010\u0010s\u001a\b\u0012\u0002\b\u0003\u0018\u00010t*\u00020\u0004\u001a*\u0010u\u001a\u0004\u0018\u00010\\*\u00020\\2\b\b\u0002\u0010v\u001a\u00020*2\u0012\u0010w\u001a\u000e\u0012\u0004\u0012\u00020\\\u0012\u0004\u0012\u00020*0x\u001a\f\u0010y\u001a\u0004\u0018\u00010\"*\u00020\t\u001a\u0012\u0010z\u001a\b\u0012\u0004\u0012\u00020\u00190M*\u00020\u0003H\u0002\u001a\u0010\u0010{\u001a\b\u0012\u0004\u0012\u00020\u000e0R*\u00020\u000e\u001a\u0010\u0010|\u001a\b\u0012\u0004\u0012\u00020\u00030}*\u00020\u0003\u001a\f\u0010~\u001a\u0004\u0018\u00010\u007f*\u00020N\u001a\f\u0010~\u001a\u0004\u0018\u00010\u007f*\u00020\u0004\u001a\u000b\u0010\u0080\u0001\u001a\u00020\u0003*\u00020\u0003\u001a\u000b\u0010\u0081\u0001\u001a\u00020\t*\u00020\t\u001a\u000e\u0010\u0082\u0001\u001a\u00030\u0083\u0001*\u00020EH\u0007\u001a\r\u0010\u0084\u0001\u001a\u0004\u0018\u00010\t*\u00020m\u001a\r\u0010\u0085\u0001\u001a\u0004\u0018\u00010\u0003*\u00020\u0003\u001a\u000b\u0010\u0086\u0001\u001a\u00020\u0003*\u00020\u0003\u001a\u0011\u0010\u0087\u0001\u001a\b\u0012\u0004\u0012\u00020\u00030M*\u00020\u0003\u001a\u001d\u0010\u0088\u0001\u001a\u00020**\u00020\t2\u0007\u0010\u0089\u0001\u001a\u00020\t2\u0007\u0010\u008a\u0001\u001a\u00020*\u001a\u000b\u0010\u008b\u0001\u001a\u00020**\u00020N\u001a\u000b\u0010\u008c\u0001\u001a\u00020**\u00020\t\u001a\u000b\u0010\u008d\u0001\u001a\u00020**\u00020\t\u001a\u000b\u0010\u008e\u0001\u001a\u00020**\u00020N\u001a\f\u0010\u008f\u0001\u001a\u00020**\u00030\u0090\u0001\u001a\f\u0010\u0091\u0001\u001a\u00020**\u00030\u0092\u0001\u001a\u000b\u0010\u0093\u0001\u001a\u00020**\u00020\t\u001a\u000b\u0010\u0094\u0001\u001a\u00020**\u00020\t\u001a\u0014\u0010\u0095\u0001\u001a\u00020**\u00020\u00032\u0007\u0010\u0096\u0001\u001a\u00020\u0003\u001a\u000b\u0010\u0097\u0001\u001a\u00020**\u00020E\u001a\u0019\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u00020\\0R*\u00020\\2\u0006\u0010v\u001a\u00020*\u001a)\u0010\u0099\u0001\u001a\b\u0012\u0004\u0012\u0002Hl0R\"\b\b\u0000\u0010l*\u00020m*\u0002Hl2\u0006\u0010v\u001a\u00020*¢\u0006\u0003\u0010\u009a\u0001\u001a \u0010\u009b\u0001\u001a\u0004\u0018\u00010\u0003*\u00020E2\u0007\u0010\u009c\u0001\u001a\u00020\"2\b\u0010\u009d\u0001\u001a\u00030\u009e\u0001\u001a\u0015\u0010\u009f\u0001\u001a\u00030 \u0001*\u00020\\2\u0007\u0010¡\u0001\u001a\u00020\\\u001a\u001a\u0010¢\u0001\u001a\u00020*\"\b\b\u0000\u0010l*\u00020m*\u0002Hl¢\u0006\u0002\u0010n\u001a\f\u0010£\u0001\u001a\u00030¤\u0001*\u00020m\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0017\u0010\u0002\u001a\u0004\u0018\u00010\u0003*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\"\u0015\u0010\u0007\u001a\u00020\b*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"\u0019\u0010\f\u001a\u0004\u0018\u00010\r*\u0004\u0018\u00010\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\" \u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u00118FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u000f\u0010\u0014\"\u0017\u0010\u0015\u001a\u0004\u0018\u00010\u0003*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017\"\u0017\u0010\u0018\u001a\u0004\u0018\u00010\u0019*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b\"\u0017\u0010\u001c\u001a\u0004\u0018\u00010\u0003*\u00020\u00118F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\"\u0017\u0010\u001f\u001a\u0004\u0018\u00010\u0003*\u00020\u00118F¢\u0006\u0006\u001a\u0004\b \u0010\u001e\"\u0015\u0010!\u001a\u00020\"*\u00020\t8F¢\u0006\u0006\u001a\u0004\b#\u0010$\"\u0015\u0010%\u001a\u00020&*\u00020\t8F¢\u0006\u0006\u001a\u0004\b'\u0010(\"\u0015\u0010)\u001a\u00020**\u00020\u00038F¢\u0006\u0006\u001a\u0004\b+\u0010,\"\u0015\u0010-\u001a\u00020**\u00020\u00118F¢\u0006\u0006\u001a\u0004\b.\u0010/\"\u001f\u00100\u001a\n\u0012\u0004\u0012\u000202\u0018\u000101*\u0004\u0018\u00010\u00038F¢\u0006\u0006\u001a\u0004\b3\u00104\"\u0015\u00105\u001a\u00020**\u0002068F¢\u0006\u0006\u001a\u0004\b5\u00107\"\u0015\u00108\u001a\u00020**\u0002068F¢\u0006\u0006\u001a\u0004\b8\u00107\"\u0015\u00109\u001a\u00020**\u00020\t8F¢\u0006\u0006\u001a\u0004\b9\u0010:\"\u0015\u0010;\u001a\u00020**\u00020\t8F¢\u0006\u0006\u001a\u0004\b;\u0010:\"\u0015\u0010<\u001a\u00020**\u00020\t8F¢\u0006\u0006\u001a\u0004\b<\u0010:\"\u0015\u0010=\u001a\u00020**\u00020\t8F¢\u0006\u0006\u001a\u0004\b=\u0010:\"\u0015\u0010>\u001a\u00020**\u00020\t8F¢\u0006\u0006\u001a\u0004\b>\u0010:\"\u0015\u0010?\u001a\u00020**\u00020\u00048F¢\u0006\u0006\u001a\u0004\b?\u0010@\"\u0015\u0010A\u001a\u00020**\u00020B8F¢\u0006\u0006\u001a\u0004\bA\u0010C\"\u0015\u0010D\u001a\u00020E*\u00020\t8F¢\u0006\u0006\u001a\u0004\bF\u0010G\"\u001f\u0010H\u001a\n\u0012\u0004\u0012\u000202\u0018\u00010I*\u0004\u0018\u00010\u00038F¢\u0006\u0006\u001a\u0004\bJ\u0010K\"\u001b\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00040M*\u00020N8F¢\u0006\u0006\u001a\u0004\bO\u0010P\"\u001b\u0010Q\u001a\b\u0012\u0004\u0012\u00020\t0R*\u00020\t8F¢\u0006\u0006\u001a\u0004\bS\u0010T\"\u001b\u0010U\u001a\b\u0012\u0004\u0012\u00020\t0R*\u00020\t8F¢\u0006\u0006\u001a\u0004\bV\u0010T\"\u0017\u0010W\u001a\u0004\u0018\u00010X*\u00020\t8F¢\u0006\u0006\u001a\u0004\bY\u0010Z\"\u0015\u0010[\u001a\u00020\\*\u00020\\8F¢\u0006\u0006\u001a\u0004\b]\u0010^\"\u001b\u0010_\u001a\b\u0012\u0004\u0012\u00020`0M*\u00020\u00038F¢\u0006\u0006\u001a\u0004\ba\u0010b¨\u0006¥\u0001"}, d2 = {"RETENTION_PARAMETER_NAME", "Lorg/jetbrains/kotlin/name/Name;", "annotationClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "getAnnotationClass", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;)Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getBuiltIns", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getClassId", "(Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;)Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "getClassId$annotations", "(Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;)V", "(Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;)Lorg/jetbrains/kotlin/name/ClassId;", "classValueDescriptor", "getClassValueDescriptor", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classValueType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getClassValueType", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Lorg/jetbrains/kotlin/types/KotlinType;", "classValueTypeDescriptor", "getClassValueTypeDescriptor", "(Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;)Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "denotedClassDescriptor", "getDenotedClassDescriptor", "fqNameSafe", "Lorg/jetbrains/kotlin/name/FqName;", "getFqNameSafe", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Lorg/jetbrains/kotlin/name/FqName;", "fqNameUnsafe", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "getFqNameUnsafe", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "hasClassValueDescriptor", "", "getHasClassValueDescriptor", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Z", "hasCompanionObject", "getHasCompanionObject", "(Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;)Z", "inlineClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/InlineClassRepresentation;", "Lorg/jetbrains/kotlin/types/SimpleType;", "getInlineClassRepresentation", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Lorg/jetbrains/kotlin/descriptors/InlineClassRepresentation;", "isEffectivelyPrivateApi", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;)Z", "isEffectivelyPublicApi", "isExtension", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Z", "isExtensionProperty", "isInsideInterface", "isInsidePrivateClass", "isMemberOfCompanionOfPrivateClass", "isSourceAnnotation", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;)Z", "isUnderscoreNamed", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;)Z", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModule", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "multiFieldValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/MultiFieldValueClassRepresentation;", "getMultiFieldValueClassRepresentation", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Lorg/jetbrains/kotlin/descriptors/MultiFieldValueClassRepresentation;", "nonSourceAnnotations", "", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotated;", "getNonSourceAnnotations", "(Lorg/jetbrains/kotlin/descriptors/annotations/Annotated;)Ljava/util/List;", "parents", "Lkotlin/sequences/Sequence;", "getParents", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Lkotlin/sequences/Sequence;", "parentsWithSelf", "getParentsWithSelf", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Lorg/jetbrains/kotlin/platform/TargetPlatform;", "propertyIfAccessor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "getPropertyIfAccessor", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "secondaryConstructors", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getSecondaryConstructors", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Ljava/util/List;", "findImplicitOuterClassArguments", "Lorg/jetbrains/kotlin/types/TypeProjection;", "scopeOwner", "outerClass", "isParameterOfAnnotation", "parameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "classesFromInnerToOuter", "containsStubTypes", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Z", "declaresOrInheritsDefaultValue", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "filterOutSourceAnnotations", "", "firstArgument", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "firstOverridden", "useOriginal", "predicate", "Lkotlin/Function1;", "fqNameOrNull", "getAllSuperClassesTypesIncludeItself", "getAllSuperClassifiers", "getAllSuperclassesWithoutAny", "Lorg/jetbrains/kotlin/utils/SmartList;", "getAnnotationRetention", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinRetention;", "getClassObjectReferenceTarget", "getImportableDescriptor", "getKotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "getOwnerForEffectiveDispatchReceiverParameter", "getSuperClassNotAny", "getSuperClassOrAny", "getSuperInterfaces", "isAncestorOf", "descriptor", SchemaSymbols.ATTVAL_STRICT, "isAnnotatedWithKotlinRepeatable", "isAnnotationConstructor", "isCompanionObject", "isDocumentedAnnotation", "isEffectivelyExternal", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "isEnumValueOfMethod", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "isPrimaryConstructorOfInlineClass", "isPublishedApi", "isSubclassOf", "superclass", "isTypeRefinementEnabled", "overriddenTreeAsSequence", "overriddenTreeUniqueAsSequence", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;Z)Lkotlin/sequences/Sequence;", "resolveTopLevelClass", "topLevelClassFqName", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "setSingleOverridden", "", "overridden", "shouldBeSubstituteWithStubTypes", "varargParameterPosition", "", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DescriptorUtilsKt {
    private static final Name RETENTION_PARAMETER_NAME;

    /* JADX INFO: compiled from: DescriptorUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.OBJECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.ENUM_ENTRY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "The one below with receiver type ClassifierDescriptor? should be used")
    public static /* synthetic */ void getClassId$annotations(ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters) {
    }

    static {
        Name nameIdentifier = Name.identifier("value");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        RETENTION_PARAMETER_NAME = nameIdentifier;
    }

    public static final ClassDescriptor getClassObjectReferenceTarget(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        ClassDescriptor classDescriptorMo6346getCompanionObjectDescriptor = classDescriptor.mo6346getCompanionObjectDescriptor();
        return classDescriptorMo6346getCompanionObjectDescriptor == null ? classDescriptor : classDescriptorMo6346getCompanionObjectDescriptor;
    }

    public static final DeclarationDescriptor getImportableDescriptor(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        if (declarationDescriptor instanceof DescriptorDerivedFromTypeAlias) {
            return ((DescriptorDerivedFromTypeAlias) declarationDescriptor).getTypeAliasDescriptor();
        }
        if (declarationDescriptor instanceof ConstructorDescriptor) {
            ClassifierDescriptorWithTypeParameters containingDeclaration = ((ConstructorDescriptor) declarationDescriptor).getDeclarationDescriptor();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            return containingDeclaration;
        }
        if (!(declarationDescriptor instanceof PropertyAccessorDescriptor)) {
            return declarationDescriptor;
        }
        PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) declarationDescriptor).getCorrespondingProperty();
        Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
        return correspondingProperty;
    }

    public static final FqNameUnsafe getFqNameUnsafe(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        FqNameUnsafe fqName = DescriptorUtils.getFqName(declarationDescriptor);
        Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
        return fqName;
    }

    public static final FqName getFqNameSafe(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        FqName fqNameSafe = DescriptorUtils.getFqNameSafe(declarationDescriptor);
        Intrinsics.checkNotNullExpressionValue(fqNameSafe, "getFqNameSafe(...)");
        return fqNameSafe;
    }

    public static final boolean isExtension(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return (declarationDescriptor instanceof CallableDescriptor) && ((CallableDescriptor) declarationDescriptor).getExtensionReceiverParameter() != null;
    }

    public static final ModuleDescriptor getModule(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        ModuleDescriptor containingModule = DescriptorUtils.getContainingModule(declarationDescriptor);
        Intrinsics.checkNotNullExpressionValue(containingModule, "getContainingModule(...)");
        return containingModule;
    }

    public static final TargetPlatform getPlatform(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return getModule(declarationDescriptor).getPlatform();
    }

    public static final ClassDescriptor resolveTopLevelClass(ModuleDescriptor moduleDescriptor, FqName topLevelClassFqName, LookupLocation location) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(topLevelClassFqName, "topLevelClassFqName");
        Intrinsics.checkNotNullParameter(location, "location");
        topLevelClassFqName.isRoot();
        FqName fqNameParent = topLevelClassFqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        MemberScope memberScope = moduleDescriptor.getPackage(fqNameParent).getMemberScope();
        Name nameShortName = topLevelClassFqName.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        ClassifierDescriptor contributedClassifier = memberScope.mo7465getContributedClassifier(nameShortName, location);
        if (contributedClassifier instanceof ClassDescriptor) {
            return (ClassDescriptor) contributedClassifier;
        }
        return null;
    }

    public static final ClassDescriptor getDenotedClassDescriptor(ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters) {
        Intrinsics.checkNotNullParameter(classifierDescriptorWithTypeParameters, "<this>");
        if (classifierDescriptorWithTypeParameters instanceof ClassDescriptor) {
            return (ClassDescriptor) classifierDescriptorWithTypeParameters;
        }
        if (classifierDescriptorWithTypeParameters instanceof TypeAliasDescriptor) {
            return ((TypeAliasDescriptor) classifierDescriptorWithTypeParameters).getClassDescriptor();
        }
        throw new UnsupportedOperationException("Unexpected descriptor kind: " + classifierDescriptorWithTypeParameters);
    }

    public static final /* synthetic */ ClassId getClassId(ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters) {
        Intrinsics.checkNotNullParameter(classifierDescriptorWithTypeParameters, "<this>");
        return getClassId((ClassifierDescriptor) classifierDescriptorWithTypeParameters);
    }

    public static final ClassId getClassId(ClassifierDescriptor classifierDescriptor) {
        DeclarationDescriptor containingDeclaration;
        ClassId classId;
        if (classifierDescriptor == null || (containingDeclaration = classifierDescriptor.getDeclarationDescriptor()) == null) {
            return null;
        }
        if (containingDeclaration instanceof PackageFragmentDescriptor) {
            return new ClassId(((PackageFragmentDescriptor) containingDeclaration).getFqName(), classifierDescriptor.getShortName());
        }
        if (!(containingDeclaration instanceof ClassifierDescriptorWithTypeParameters) || (classId = getClassId((ClassifierDescriptor) containingDeclaration)) == null) {
            return null;
        }
        return classId.createNestedClassId(classifierDescriptor.getShortName());
    }

    public static final boolean getHasCompanionObject(ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters) {
        Intrinsics.checkNotNullParameter(classifierDescriptorWithTypeParameters, "<this>");
        ClassDescriptor denotedClassDescriptor = getDenotedClassDescriptor(classifierDescriptorWithTypeParameters);
        return (denotedClassDescriptor != null ? denotedClassDescriptor.mo6346getCompanionObjectDescriptor() : null) != null;
    }

    public static final boolean getHasClassValueDescriptor(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        return getClassValueDescriptor(classDescriptor) != null;
    }

    public static final ClassDescriptor getClassValueDescriptor(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        return classDescriptor.getKind().isSingleton() ? classDescriptor : classDescriptor.mo6346getCompanionObjectDescriptor();
    }

    public static final ClassDescriptor getClassValueTypeDescriptor(ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters) {
        Intrinsics.checkNotNullParameter(classifierDescriptorWithTypeParameters, "<this>");
        ClassDescriptor denotedClassDescriptor = getDenotedClassDescriptor(classifierDescriptorWithTypeParameters);
        if (denotedClassDescriptor == null) {
            return null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[denotedClassDescriptor.getKind().ordinal()];
        if (i == 1) {
            return denotedClassDescriptor;
        }
        if (i == 2) {
            DeclarationDescriptor containingDeclaration = classifierDescriptorWithTypeParameters.getDeclarationDescriptor();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (containingDeclaration instanceof ClassDescriptor) {
                ((ClassDescriptor) containingDeclaration).getKind();
                ClassKind classKind = ClassKind.ENUM_CLASS;
            }
            Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            return (ClassDescriptor) containingDeclaration;
        }
        return denotedClassDescriptor.mo6346getCompanionObjectDescriptor();
    }

    public static final KotlinType getClassValueType(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        ClassDescriptor classValueTypeDescriptor = getClassValueTypeDescriptor(classDescriptor);
        return classValueTypeDescriptor != null ? classValueTypeDescriptor.getDefaultType() : null;
    }

    public static final boolean isEffectivelyPublicApi(DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility) {
        Intrinsics.checkNotNullParameter(declarationDescriptorWithVisibility, "<this>");
        return EffectiveVisibilityUtilsKt.effectiveVisibility$default(declarationDescriptorWithVisibility, (DescriptorVisibility) null, false, 3, (Object) null).getPublicApi();
    }

    public static final boolean isEffectivelyPrivateApi(DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility) {
        Intrinsics.checkNotNullParameter(declarationDescriptorWithVisibility, "<this>");
        return EffectiveVisibilityUtilsKt.effectiveVisibility$default(declarationDescriptorWithVisibility, (DescriptorVisibility) null, false, 3, (Object) null).getPrivateApi();
    }

    public static final boolean isInsidePrivateClass(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        DeclarationDescriptor containingDeclaration = declarationDescriptor.getDeclarationDescriptor();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        return classDescriptor != null && DescriptorVisibilities.isPrivate(classDescriptor.getVisibility());
    }

    public static final boolean isMemberOfCompanionOfPrivateClass(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        DeclarationDescriptor containingDeclaration = declarationDescriptor.getDeclarationDescriptor();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor != null && classDescriptor.isCompanionObject()) {
            return isInsidePrivateClass(classDescriptor);
        }
        return false;
    }

    public static final boolean isInsideInterface(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        DeclarationDescriptor containingDeclaration = declarationDescriptor.getDeclarationDescriptor();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor != null) {
            ClassKind kind = classDescriptor.getKind();
            Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
            if (kind == ClassKind.INTERFACE) {
                return true;
            }
        }
        return false;
    }

    public static final ClassDescriptor getSuperClassNotAny(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        for (KotlinType kotlinType : classDescriptor.getDefaultType().getConstructor().mo7438getSupertypes()) {
            if (!KotlinBuiltIns.isAnyOrNullableAny(kotlinType)) {
                ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().getThis$0();
                if (DescriptorUtils.isClassOrEnumClass(classifierDescriptorMo7434getDeclarationDescriptor)) {
                    Intrinsics.checkNotNull(classifierDescriptorMo7434getDeclarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    return (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor;
                }
            }
        }
        return null;
    }

    public static final ClassDescriptor getSuperClassOrAny(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        ClassDescriptor superClassNotAny = getSuperClassNotAny(classDescriptor);
        if (superClassNotAny != null) {
            return superClassNotAny;
        }
        ClassDescriptor any = getBuiltIns(classDescriptor).getAny();
        Intrinsics.checkNotNullExpressionValue(any, "getAny(...)");
        return any;
    }

    public static final List<ClassDescriptor> getSuperInterfaces(ClassDescriptor classDescriptor) {
        ClassDescriptor classDescriptor2;
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        Collection<KotlinType> collectionMo7438getSupertypes = classDescriptor.getDefaultType().getConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : collectionMo7438getSupertypes) {
            if (!KotlinBuiltIns.isAnyOrNullableAny((KotlinType) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = ((KotlinType) it.next()).getConstructor().getThis$0();
            if (DescriptorUtils.isInterface(classifierDescriptorMo7434getDeclarationDescriptor)) {
                Intrinsics.checkNotNull(classifierDescriptorMo7434getDeclarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                classDescriptor2 = (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor;
            } else {
                classDescriptor2 = null;
            }
            if (classDescriptor2 != null) {
                arrayList2.add(classDescriptor2);
            }
        }
        return arrayList2;
    }

    public static final List<ClassConstructorDescriptor> getSecondaryConstructors(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        Collection<ClassConstructorDescriptor> constructors = classDescriptor.getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : constructors) {
            if (!((ClassConstructorDescriptor) obj).isPrimary()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final KotlinBuiltIns getBuiltIns(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return getModule(declarationDescriptor).getBuiltIns();
    }

    public static final DeclarationDescriptor getOwnerForEffectiveDispatchReceiverParameter(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        if ((callableDescriptor instanceof CallableMemberDescriptor) && ((CallableMemberDescriptor) callableDescriptor).getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            return callableDescriptor.getDeclarationDescriptor();
        }
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = callableDescriptor.mo6753getDispatchReceiverParameter();
        if (receiverParameterDescriptorMo6753getDispatchReceiverParameter != null) {
            return receiverParameterDescriptorMo6753getDispatchReceiverParameter.getDeclarationDescriptor();
        }
        return null;
    }

    public static final boolean declaresOrInheritsDefaultValue(ValueParameterDescriptor valueParameterDescriptor) {
        Intrinsics.checkNotNullParameter(valueParameterDescriptor, "<this>");
        Boolean boolIfAny = DFS.ifAny(CollectionsKt.listOf(valueParameterDescriptor), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return DescriptorUtilsKt.declaresOrInheritsDefaultValue$lambda$5((ValueParameterDescriptor) obj);
            }
        }, C49902.INSTANCE);
        Intrinsics.checkNotNullExpressionValue(boolIfAny, "ifAny(...)");
        return boolIfAny.booleanValue();
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt$declaresOrInheritsDefaultValue$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DescriptorUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C49902 extends FunctionReference implements Function1<ValueParameterDescriptor, Boolean> {
        public static final C49902 INSTANCE = new C49902();

        C49902() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "declaresDefaultValue()Z";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "declaresDefaultValue";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(ValueParameterDescriptor.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(ValueParameterDescriptor p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return Boolean.valueOf(p0.declaresDefaultValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable declaresOrInheritsDefaultValue$lambda$5(ValueParameterDescriptor valueParameterDescriptor) {
        Collection<ValueParameterDescriptor> overriddenDescriptors = valueParameterDescriptor.getOverriddenDescriptors();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overriddenDescriptors, 10));
        Iterator<T> it = overriddenDescriptors.iterator();
        while (it.hasNext()) {
            arrayList.add(((ValueParameterDescriptor) it.next()).getOriginal());
        }
        return arrayList;
    }

    public static final boolean isAnnotatedWithKotlinRepeatable(Annotated annotated) {
        Intrinsics.checkNotNullParameter(annotated, "<this>");
        return annotated.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.repeatable) != null;
    }

    public static final boolean isDocumentedAnnotation(Annotated annotated) {
        Intrinsics.checkNotNullParameter(annotated, "<this>");
        return annotated.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.mustBeDocumented) != null;
    }

    public static final KotlinRetention getAnnotationRetention(Annotated annotated) {
        Intrinsics.checkNotNullParameter(annotated, "<this>");
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = annotated.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.retention);
        if (annotationDescriptorMo6752findAnnotation != null) {
            return getAnnotationRetention(annotationDescriptorMo6752findAnnotation);
        }
        return null;
    }

    public static final KotlinRetention getAnnotationRetention(AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        ConstantValue<?> constantValue = annotationDescriptor.getAllValueArguments().get(RETENTION_PARAMETER_NAME);
        EnumValue enumValue = constantValue instanceof EnumValue ? (EnumValue) constantValue : null;
        if (enumValue == null) {
            return null;
        }
        String strAsString = enumValue.getEnumEntryName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        for (KotlinRetention kotlinRetention : KotlinRetention.values()) {
            if (Intrinsics.areEqual(kotlinRetention.name(), strAsString)) {
                return kotlinRetention;
            }
        }
        return null;
    }

    public static final List<AnnotationDescriptor> getNonSourceAnnotations(Annotated annotated) {
        Intrinsics.checkNotNullParameter(annotated, "<this>");
        return filterOutSourceAnnotations(annotated.getAnnotations());
    }

    public static final boolean isSourceAnnotation(AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        ClassDescriptor annotationClass = getAnnotationClass(annotationDescriptor);
        return annotationClass == null || getAnnotationRetention(annotationClass) == KotlinRetention.SOURCE;
    }

    public static final Sequence<DeclarationDescriptor> getParentsWithSelf(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return SequencesKt.generateSequence(declarationDescriptor, new Function1<DeclarationDescriptor, DeclarationDescriptor>() { // from class: org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt$parentsWithSelf$1
            @Override // kotlin.jvm.functions.Function1
            public final DeclarationDescriptor invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getDeclarationDescriptor();
            }
        });
    }

    public static final Sequence<DeclarationDescriptor> getParents(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return SequencesKt.drop(getParentsWithSelf(declarationDescriptor), 1);
    }

    public static final CallableMemberDescriptor getPropertyIfAccessor(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        if (!(callableMemberDescriptor instanceof PropertyAccessorDescriptor)) {
            return callableMemberDescriptor;
        }
        PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) callableMemberDescriptor).getCorrespondingProperty();
        Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
        return correspondingProperty;
    }

    public static final FqName fqNameOrNull(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        FqNameUnsafe fqNameUnsafe = getFqNameUnsafe(declarationDescriptor);
        if (!fqNameUnsafe.isSafe()) {
            fqNameUnsafe = null;
        }
        if (fqNameUnsafe != null) {
            return fqNameUnsafe.toSafe();
        }
        return null;
    }

    public static /* synthetic */ CallableMemberDescriptor firstOverridden$default(CallableMemberDescriptor callableMemberDescriptor, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return firstOverridden(callableMemberDescriptor, z, function1);
    }

    public static final CallableMemberDescriptor firstOverridden(CallableMemberDescriptor callableMemberDescriptor, final boolean z, final Function1<? super CallableMemberDescriptor, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        return (CallableMemberDescriptor) DFS.dfs(CollectionsKt.listOf(callableMemberDescriptor), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return DescriptorUtilsKt.firstOverridden$lambda$9(z, (CallableMemberDescriptor) obj);
            }
        }, new DFS.AbstractNodeHandler<CallableMemberDescriptor, CallableMemberDescriptor>() { // from class: org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt.firstOverridden.2
            @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
            public boolean beforeChildren(CallableMemberDescriptor current) {
                Intrinsics.checkNotNullParameter(current, "current");
                return objectRef.element == null;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
            public void afterChildren(CallableMemberDescriptor current) {
                Intrinsics.checkNotNullParameter(current, "current");
                if (objectRef.element == null && predicate.invoke(current).booleanValue()) {
                    objectRef.element = current;
                }
            }

            @Override // org.jetbrains.kotlin.utils.DFS.NodeHandler
            public CallableMemberDescriptor result() {
                return objectRef.element;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable firstOverridden$lambda$9(boolean z, CallableMemberDescriptor callableMemberDescriptor) {
        if (z) {
            callableMemberDescriptor = callableMemberDescriptor != null ? callableMemberDescriptor.getOriginal() : null;
        }
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor != null ? callableMemberDescriptor.getOverriddenDescriptors() : null;
        return overriddenDescriptors == null ? CollectionsKt.emptyList() : overriddenDescriptors;
    }

    public static final void setSingleOverridden(CallableMemberDescriptor callableMemberDescriptor, CallableMemberDescriptor overridden) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(overridden, "overridden");
        callableMemberDescriptor.mo7205setOverriddenDescriptors(CollectionsKt.listOf(overridden));
    }

    public static final Sequence<CallableMemberDescriptor> overriddenTreeAsSequence(CallableMemberDescriptor callableMemberDescriptor, final boolean z) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        if (z) {
            callableMemberDescriptor = callableMemberDescriptor.getOriginal();
        }
        Sequence sequenceSequenceOf = SequencesKt.sequenceOf(callableMemberDescriptor);
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        return SequencesKt.plus(sequenceSequenceOf, SequencesKt.flatMap(CollectionsKt.asSequence(overriddenDescriptors), new Function1<CallableMemberDescriptor, Sequence<? extends CallableMemberDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt$overriddenTreeAsSequence$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Sequence<CallableMemberDescriptor> invoke(CallableMemberDescriptor callableMemberDescriptor2) {
                Intrinsics.checkNotNull(callableMemberDescriptor2);
                return DescriptorUtilsKt.overriddenTreeAsSequence(callableMemberDescriptor2, z);
            }
        }));
    }

    public static final <D extends CallableDescriptor> Sequence<D> overriddenTreeUniqueAsSequence(D d, boolean z) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        return overriddenTreeUniqueAsSequence$doBuildOverriddenTreeAsSequence(d, z, new HashSet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <D extends CallableDescriptor> Sequence<D> overriddenTreeUniqueAsSequence$doBuildOverriddenTreeAsSequence(D d, final boolean z, final HashSet<D> hashSet) {
        if (z) {
            d = (D) d.getOriginal();
            Intrinsics.checkNotNull(d, "null cannot be cast to non-null type D of org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt.overriddenTreeUniqueAsSequence$doBuildOverriddenTreeAsSequence");
        }
        if (CollectionsKt.contains(hashSet, d.getOriginal())) {
            return SequencesKt.emptySequence();
        }
        CallableDescriptor original = d.getOriginal();
        Intrinsics.checkNotNull(original, "null cannot be cast to non-null type D of org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt.overriddenTreeUniqueAsSequence$doBuildOverriddenTreeAsSequence$lambda$11");
        hashSet.add(original);
        Sequence sequenceSequenceOf = SequencesKt.sequenceOf(d);
        Collection<? extends CallableDescriptor> overriddenDescriptors = d.getOverriddenDescriptors();
        Intrinsics.checkNotNull(overriddenDescriptors, "null cannot be cast to non-null type kotlin.collections.Collection<D of org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt.overriddenTreeUniqueAsSequence$doBuildOverriddenTreeAsSequence$lambda$11>");
        return SequencesKt.plus(sequenceSequenceOf, SequencesKt.flatMap(CollectionsKt.asSequence(overriddenDescriptors), new Function1<D, Sequence<? extends D>>() { // from class: org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt$overriddenTreeUniqueAsSequence$doBuildOverriddenTreeAsSequence$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Incorrect types in method signature: (TD;)Lkotlin/sequences/Sequence<TD;>; */
            @Override // kotlin.jvm.functions.Function1
            public final Sequence invoke(CallableDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return DescriptorUtilsKt.overriddenTreeUniqueAsSequence$doBuildOverriddenTreeAsSequence(it, z, hashSet);
            }
        }));
    }

    public static final int varargParameterPosition(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        List<ValueParameterDescriptor> valueParameters = callableDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        Iterator<ValueParameterDescriptor> it = valueParameters.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().getVarargElementType() != null) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static final List<TypeProjection> findImplicitOuterClassArguments(ClassDescriptor scopeOwner, ClassDescriptor outerClass) {
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        Intrinsics.checkNotNullParameter(outerClass, "outerClass");
        Iterator<ClassDescriptor> it = classesFromInnerToOuter(scopeOwner).iterator();
        while (it.hasNext()) {
            for (KotlinType kotlinType : getAllSuperClassesTypesIncludeItself(it.next())) {
                ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().getThis$0();
                Intrinsics.checkNotNull(classifierDescriptorMo7434getDeclarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                if (Intrinsics.areEqual((ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor, outerClass)) {
                    return kotlinType.getArguments();
                }
            }
        }
        return null;
    }

    private static final Sequence<ClassDescriptor> classesFromInnerToOuter(ClassDescriptor classDescriptor) {
        return SequencesKt.generateSequence(classDescriptor, new Function1<ClassDescriptor, ClassDescriptor>() { // from class: org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt.classesFromInnerToOuter.1
            @Override // kotlin.jvm.functions.Function1
            public final ClassDescriptor invoke(ClassDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (!it.getIsInner()) {
                    return null;
                }
                DeclarationDescriptor original = it.getDeclarationDescriptor().getOriginal();
                if (original instanceof ClassDescriptor) {
                    return (ClassDescriptor) original;
                }
                return null;
            }
        });
    }

    private static final List<KotlinType> getAllSuperClassesTypesIncludeItself(ClassDescriptor classDescriptor) {
        ArrayList arrayList = new ArrayList();
        SimpleType defaultType = classDescriptor.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        SimpleType simpleTypeSubstitute = defaultType;
        while (!TypeUtilsKt.isAnyOrNullableAny(simpleTypeSubstitute)) {
            arrayList.add(simpleTypeSubstitute);
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = simpleTypeSubstitute.getConstructor().getThis$0();
            Intrinsics.checkNotNull(classifierDescriptorMo7434getDeclarationDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            KotlinType superClassType = DescriptorUtils.getSuperClassType((ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor);
            Intrinsics.checkNotNullExpressionValue(superClassType, "getSuperClassType(...)");
            simpleTypeSubstitute = TypeSubstitutor.create(simpleTypeSubstitute).substitute(superClassType, Variance.INVARIANT);
            if (simpleTypeSubstitute == null) {
                break;
            }
        }
        return arrayList;
    }

    public static final boolean isEnumValueOfMethod(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        SimpleType stringType = getBuiltIns(functionDescriptor).getStringType();
        Intrinsics.checkNotNullExpressionValue(stringType, "getStringType(...)");
        return Intrinsics.areEqual(StandardNames.ENUM_VALUE_OF, functionDescriptor.getShortName()) && valueParameters.size() == 1 && KotlinTypeChecker.DEFAULT.isSubtypeOf(valueParameters.get(0).getType(), TypeUtilsKt.makeNullable(stringType));
    }

    public static final boolean isExtensionProperty(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return (declarationDescriptor instanceof PropertyDescriptor) && ((PropertyDescriptor) declarationDescriptor).getExtensionReceiverParameter() != null;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt$getAllSuperclassesWithoutAny$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DescriptorUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C49921 extends FunctionReference implements Function1<ClassDescriptor, ClassDescriptor> {
        public static final C49921 INSTANCE = new C49921();

        C49921() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "getSuperClassNotAny(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "getSuperClassNotAny";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(DescriptorUtilsKt.class, "descriptors");
        }

        @Override // kotlin.jvm.functions.Function1
        public final ClassDescriptor invoke(ClassDescriptor p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return DescriptorUtilsKt.getSuperClassNotAny(p0);
        }
    }

    public static final SmartList<ClassDescriptor> getAllSuperclassesWithoutAny(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        return (SmartList) SequencesKt.toCollection(SequencesKt.generateSequence(getSuperClassNotAny(classDescriptor), C49921.INSTANCE), new SmartList());
    }

    public static final Sequence<ClassifierDescriptor> getAllSuperClassifiers(ClassifierDescriptor classifierDescriptor) {
        Intrinsics.checkNotNullParameter(classifierDescriptor, "<this>");
        return getAllSuperClassifiers$doGetAllSuperClassesAndInterfaces(classifierDescriptor, new HashSet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence<ClassifierDescriptor> getAllSuperClassifiers$doGetAllSuperClassesAndInterfaces(ClassifierDescriptor classifierDescriptor, final HashSet<ClassifierDescriptor> hashSet) {
        if (hashSet.contains(classifierDescriptor.getOriginal())) {
            return SequencesKt.emptySequence();
        }
        hashSet.add(classifierDescriptor.getOriginal());
        Sequence sequenceSequenceOf = SequencesKt.sequenceOf(classifierDescriptor.getOriginal());
        Collection<KotlinType> collectionMo7438getSupertypes = classifierDescriptor.getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        return SequencesKt.plus(sequenceSequenceOf, SequencesKt.flatMap(CollectionsKt.asSequence(collectionMo7438getSupertypes), new Function1<KotlinType, Sequence<? extends ClassifierDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt$getAllSuperClassifiers$doGetAllSuperClassesAndInterfaces$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Sequence<ClassifierDescriptor> invoke(KotlinType kotlinType) {
                Sequence<ClassifierDescriptor> allSuperClassifiers$doGetAllSuperClassesAndInterfaces;
                ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
                return (this$0 == null || (allSuperClassifiers$doGetAllSuperClassesAndInterfaces = DescriptorUtilsKt.getAllSuperClassifiers$doGetAllSuperClassesAndInterfaces(this$0, hashSet)) == null) ? SequencesKt.sequenceOf(new ClassifierDescriptor[0]) : allSuperClassifiers$doGetAllSuperClassesAndInterfaces;
            }
        }));
    }

    public static final boolean isPublishedApi(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        if (declarationDescriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor directMember = DescriptorUtils.getDirectMember((CallableMemberDescriptor) declarationDescriptor);
            Intrinsics.checkNotNullExpressionValue(directMember, "getDirectMember(...)");
            declarationDescriptor = directMember;
        }
        return declarationDescriptor.getAnnotations().hasAnnotation(StandardNames.FqNames.publishedApi);
    }

    public static final boolean isAncestorOf(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor descriptor, boolean z) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return DescriptorUtils.isAncestor(declarationDescriptor, descriptor, z);
    }

    public static final boolean isCompanionObject(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return DescriptorUtils.isCompanionObject(declarationDescriptor);
    }

    public static final boolean isSubclassOf(ClassDescriptor classDescriptor, ClassDescriptor superclass) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(superclass, "superclass");
        return DescriptorUtils.isSubclass(classDescriptor, superclass);
    }

    public static final ClassDescriptor getAnnotationClass(AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = annotationDescriptor.getType().getConstructor().getThis$0();
        if (classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) {
            return (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor;
        }
        return null;
    }

    public static final ConstantValue<?> firstArgument(AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        return (ConstantValue) CollectionsKt.firstOrNull(annotationDescriptor.getAllValueArguments().values());
    }

    public static final boolean isEffectivelyExternal(MemberDescriptor memberDescriptor) {
        PropertyDescriptor propertyDescriptor;
        PropertyGetterDescriptor getter;
        PropertySetterDescriptor setter;
        Intrinsics.checkNotNullParameter(memberDescriptor, "<this>");
        if (memberDescriptor.isExternal()) {
            return true;
        }
        if (memberDescriptor instanceof PropertyAccessorDescriptor) {
            PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) memberDescriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
            if (isEffectivelyExternal(correspondingProperty)) {
                return true;
            }
        }
        if ((memberDescriptor instanceof PropertyDescriptor) && (getter = (propertyDescriptor = (PropertyDescriptor) memberDescriptor).getGetter()) != null && getter.isExternal() && (!propertyDescriptor.isVar() || ((setter = propertyDescriptor.getSetter()) != null && setter.isExternal()))) {
            return true;
        }
        ClassDescriptor containingClass = DescriptorUtils.getContainingClass(memberDescriptor);
        return containingClass != null && isEffectivelyExternal(containingClass);
    }

    public static final boolean isParameterOfAnnotation(ParameterDescriptor parameterDescriptor) {
        Intrinsics.checkNotNullParameter(parameterDescriptor, "parameterDescriptor");
        DeclarationDescriptor containingDeclaration = parameterDescriptor.getDeclarationDescriptor();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        return isAnnotationConstructor(containingDeclaration);
    }

    public static final boolean isAnnotationConstructor(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return (declarationDescriptor instanceof ConstructorDescriptor) && DescriptorUtils.isAnnotationClass(((ConstructorDescriptor) declarationDescriptor).getConstructedClass());
    }

    public static final boolean isPrimaryConstructorOfInlineClass(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        if (declarationDescriptor instanceof ConstructorDescriptor) {
            ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor) declarationDescriptor;
            if (constructorDescriptor.isPrimary()) {
                ClassDescriptor constructedClass = constructorDescriptor.getConstructedClass();
                Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
                if (InlineClassesUtilsKt.isInlineClass(constructedClass)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final KotlinTypeRefiner getKotlinTypeRefiner(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        org.jetbrains.kotlin.types.checker.Ref ref = (org.jetbrains.kotlin.types.checker.Ref) moduleDescriptor.getCapability(KotlinTypeRefinerKt.getREFINER_CAPABILITY());
        TypeRefinementSupport typeRefinementSupport = ref != null ? (TypeRefinementSupport) ref.getValue() : null;
        return typeRefinementSupport instanceof TypeRefinementSupport.Enabled ? ((TypeRefinementSupport.Enabled) typeRefinementSupport).getTypeRefiner() : KotlinTypeRefiner.Default.INSTANCE;
    }

    public static final boolean isTypeRefinementEnabled(ModuleDescriptor moduleDescriptor) {
        TypeRefinementSupport typeRefinementSupport;
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        org.jetbrains.kotlin.types.checker.Ref ref = (org.jetbrains.kotlin.types.checker.Ref) moduleDescriptor.getCapability(KotlinTypeRefinerKt.getREFINER_CAPABILITY());
        return (ref == null || (typeRefinementSupport = (TypeRefinementSupport) ref.getValue()) == null || !typeRefinementSupport.getIsEnabled()) ? false : true;
    }

    public static final boolean isUnderscoreNamed(VariableDescriptor variableDescriptor) {
        Intrinsics.checkNotNullParameter(variableDescriptor, "<this>");
        return !variableDescriptor.getShortName().isSpecial() && Intrinsics.areEqual(variableDescriptor.getShortName().getIdentifier(), "_");
    }

    private static final <D extends CallableDescriptor> boolean containsStubTypes(D d) {
        KotlinType type;
        KotlinType type2;
        List<ValueParameterDescriptor> valueParameters = d.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                KotlinType type3 = ((ValueParameterDescriptor) it.next()).getType();
                Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                if (TypeUtilsKt.contains(type3, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt$containsStubTypes$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(UnwrappedType it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return Boolean.valueOf(it2 instanceof StubTypeForBuilderInference);
                    }
                })) {
                    return true;
                }
            }
        }
        KotlinType returnType = d.getReturnType();
        if (returnType != null && TypeUtilsKt.contains(returnType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt.containsStubTypes.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(it2 instanceof StubTypeForBuilderInference);
            }
        })) {
            return true;
        }
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = d.mo6753getDispatchReceiverParameter();
        if (receiverParameterDescriptorMo6753getDispatchReceiverParameter != null && (type2 = receiverParameterDescriptorMo6753getDispatchReceiverParameter.getType()) != null && TypeUtilsKt.contains(type2, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt.containsStubTypes.3
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(it2 instanceof StubTypeForBuilderInference);
            }
        })) {
            return true;
        }
        ReceiverParameterDescriptor extensionReceiverParameter = d.getExtensionReceiverParameter();
        return (extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null || !TypeUtilsKt.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt.containsStubTypes.4
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(it2 instanceof StubTypeForBuilderInference);
            }
        })) ? false : true;
    }

    public static final <D extends CallableDescriptor> boolean shouldBeSubstituteWithStubTypes(D d) {
        KotlinType returnType;
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter;
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        KotlinType type2;
        Intrinsics.checkNotNullParameter(d, "<this>");
        List<ValueParameterDescriptor> valueParameters = d.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        if ((list instanceof Collection) && list.isEmpty()) {
            returnType = d.getReturnType();
            return returnType != null ? true : true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            KotlinType type3 = ((ValueParameterDescriptor) it.next()).getType();
            Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
            if (KotlinTypeKt.isError(type3)) {
            }
        }
        returnType = d.getReturnType();
        if ((returnType != null || !KotlinTypeKt.isError(returnType)) && (((receiverParameterDescriptorMo6753getDispatchReceiverParameter = d.mo6753getDispatchReceiverParameter()) == null || (type2 = receiverParameterDescriptorMo6753getDispatchReceiverParameter.getType()) == null || !KotlinTypeKt.isError(type2)) && (((extensionReceiverParameter = d.getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null || !KotlinTypeKt.isError(type)) && containsStubTypes(d)))) {
        }
        return false;
    }

    public static final InlineClassRepresentation<SimpleType> getInlineClassRepresentation(ClassDescriptor classDescriptor) {
        ValueClassRepresentation<SimpleType> valueClassRepresentation = classDescriptor != null ? classDescriptor.getValueClassRepresentation() : null;
        if (valueClassRepresentation instanceof InlineClassRepresentation) {
            return (InlineClassRepresentation) valueClassRepresentation;
        }
        return null;
    }

    public static final MultiFieldValueClassRepresentation<SimpleType> getMultiFieldValueClassRepresentation(ClassDescriptor classDescriptor) {
        ValueClassRepresentation<SimpleType> valueClassRepresentation = classDescriptor != null ? classDescriptor.getValueClassRepresentation() : null;
        if (valueClassRepresentation instanceof MultiFieldValueClassRepresentation) {
            return (MultiFieldValueClassRepresentation) valueClassRepresentation;
        }
        return null;
    }

    public static final List<AnnotationDescriptor> filterOutSourceAnnotations(Iterable<? extends AnnotationDescriptor> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        ArrayList arrayList = new ArrayList();
        for (AnnotationDescriptor annotationDescriptor : iterable) {
            if (!isSourceAnnotation(annotationDescriptor)) {
                arrayList.add(annotationDescriptor);
            }
        }
        return arrayList;
    }
}
