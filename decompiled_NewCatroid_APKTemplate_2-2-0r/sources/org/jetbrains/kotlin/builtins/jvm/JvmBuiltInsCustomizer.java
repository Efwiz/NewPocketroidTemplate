package org.jetbrains.kotlin.builtins.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilter;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilterKt;
import org.jetbrains.kotlin.descriptors.impl.ClassDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PackageFragmentDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.load.java.components.JavaResolverCache;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassDescriptor;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassMemberScope;
import org.jetbrains.kotlin.load.kotlin.MethodSignatureBuildingUtilsKt;
import org.jetbrains.kotlin.load.kotlin.MethodSignatureMappingKt;
import org.jetbrains.kotlin.load.kotlin.SignatureBuildingComponents;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import org.jetbrains.kotlin.storage.CacheWithNotNullValues;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.LazyWrappedType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.utils.DFS;
import org.jetbrains.kotlin.utils.SmartSet;

/* JADX INFO: compiled from: JvmBuiltInsCustomizer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002:\u0001FB#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\u0018\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020'H\u0002J0\u0010+\u001a\b\u0012\u0004\u0012\u00020'0,2\u0006\u0010-\u001a\u00020\u001b2\u0018\u0010.\u001a\u0014\u0012\u0004\u0012\u000200\u0012\n\u0012\b\u0012\u0004\u0012\u00020'0,0/H\u0002J\u0016\u00101\u001a\b\u0012\u0004\u0012\u0002020,2\u0006\u0010-\u001a\u00020\u001bH\u0016J\u001e\u00103\u001a\b\u0012\u0004\u0012\u00020'0,2\u0006\u00104\u001a\u0002052\u0006\u0010-\u001a\u00020\u001bH\u0016J\u0016\u00106\u001a\b\u0012\u0004\u0012\u000205072\u0006\u0010-\u001a\u00020\u001bH\u0016J\u0016\u00108\u001a\b\u0012\u0004\u0012\u00020\u001d0,2\u0006\u0010-\u001a\u00020\u001bH\u0016J\u0018\u00109\u001a\u00020:2\u0006\u0010-\u001a\u00020\u001b2\u0006\u0010;\u001a\u00020'H\u0016J\f\u0010<\u001a\u00020\u001d*\u00020\u0006H\u0002J\u000e\u0010=\u001a\u0004\u0018\u00010>*\u00020\u001bH\u0002J\f\u0010?\u001a\u00020@*\u00020AH\u0002J\u0014\u0010B\u001a\u00020:*\u00020'2\u0006\u0010C\u001a\u00020:H\u0002J\u0014\u0010D\u001a\u00020:*\u00020E2\u0006\u0010-\u001a\u00020\u001bH\u0002R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR&\u0010\u0011\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140\u0013\u0012\u0004\u0012\u00020\u00150\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001e\u001a\u00020\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\u0010\u001a\u0004\b\u001f\u0010 R\u001b\u0010\"\u001a\u00020\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\u0010\u001a\u0004\b#\u0010$¨\u0006G"}, d2 = {"Lorg/jetbrains/kotlin/builtins/jvm/JvmBuiltInsCustomizer;", "Lorg/jetbrains/kotlin/descriptors/deserialization/AdditionalClassPartsProvider;", "Lorg/jetbrains/kotlin/descriptors/deserialization/PlatformDependentDeclarationFilter;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "settingsComputation", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/builtins/jvm/JvmBuiltIns$Settings;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/storage/StorageManager;Lkotlin/jvm/functions/Function0;)V", "cloneableType", "Lorg/jetbrains/kotlin/types/SimpleType;", "getCloneableType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "cloneableType$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "deprecationForSomeOfTheListMethods", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "j2kClassMapper", "Lorg/jetbrains/kotlin/builtins/jvm/JavaToKotlinClassMapper;", "javaAnalogueClassesWithCustomSupertypeCache", "Lorg/jetbrains/kotlin/storage/CacheWithNotNullValues;", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "mockSerializableType", "Lorg/jetbrains/kotlin/types/KotlinType;", "notConsideredDeprecation", "getNotConsideredDeprecation", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "notConsideredDeprecation$delegate", "settings", "getSettings", "()Lorg/jetbrains/kotlin/builtins/jvm/JvmBuiltIns$Settings;", "settings$delegate", "createCloneForArray", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "arrayClassDescriptor", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor;", "cloneFromCloneable", "getAdditionalFunctions", "", "classDescriptor", "functionsByScope", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getConstructors", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getFunctions", "name", "Lorg/jetbrains/kotlin/name/Name;", "getFunctionsNames", "", "getSupertypes", "isFunctionAvailable", "", "functionDescriptor", "createMockJavaIoSerializableType", "getJavaAnalogue", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaClassDescriptor;", "getJdkMethodStatus", "Lorg/jetbrains/kotlin/builtins/jvm/JvmBuiltInsCustomizer$JDKMemberStatus;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "isMutabilityViolation", "isMutable", "isTrivialCopyConstructorFor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "JDKMemberStatus", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmBuiltInsCustomizer implements AdditionalClassPartsProvider, PlatformDependentDeclarationFilter {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBuiltInsCustomizer.class), "settings", "getSettings()Lorg/jetbrains/kotlin/builtins/jvm/JvmBuiltIns$Settings;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBuiltInsCustomizer.class), "cloneableType", "getCloneableType()Lorg/jetbrains/kotlin/types/SimpleType;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBuiltInsCustomizer.class), "notConsideredDeprecation", "getNotConsideredDeprecation()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;"))};

    /* JADX INFO: renamed from: cloneableType$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue cloneableType;
    private final MemoizedFunctionToNotNull<Pair<String, String>, Annotations> deprecationForSomeOfTheListMethods;
    private final JavaToKotlinClassMapper j2kClassMapper;
    private final CacheWithNotNullValues<FqName, ClassDescriptor> javaAnalogueClassesWithCustomSupertypeCache;
    private final KotlinType mockSerializableType;
    private final ModuleDescriptor moduleDescriptor;

    /* JADX INFO: renamed from: notConsideredDeprecation$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue notConsideredDeprecation;

    /* JADX INFO: renamed from: settings$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue settings;

    /* JADX INFO: compiled from: JvmBuiltInsCustomizer.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/builtins/jvm/JvmBuiltInsCustomizer$JDKMemberStatus;", "", "(Ljava/lang/String;I)V", VirtualFile.PROP_HIDDEN, "VISIBLE", "DEPRECATED_LIST_METHODS", "NOT_CONSIDERED", "DROP", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum JDKMemberStatus {
        HIDDEN,
        VISIBLE,
        DEPRECATED_LIST_METHODS,
        NOT_CONSIDERED,
        DROP;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<JDKMemberStatus> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: JvmBuiltInsCustomizer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JDKMemberStatus.values().length];
            try {
                iArr[JDKMemberStatus.HIDDEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JDKMemberStatus.DEPRECATED_LIST_METHODS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JDKMemberStatus.NOT_CONSIDERED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[JDKMemberStatus.DROP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[JDKMemberStatus.VISIBLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public JvmBuiltInsCustomizer(ModuleDescriptor moduleDescriptor, final StorageManager storageManager, Function0<JvmBuiltIns.Settings> settingsComputation) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(settingsComputation, "settingsComputation");
        this.moduleDescriptor = moduleDescriptor;
        this.j2kClassMapper = JavaToKotlinClassMapper.INSTANCE;
        this.settings = storageManager.createLazyValue(settingsComputation);
        this.mockSerializableType = createMockJavaIoSerializableType(storageManager);
        this.cloneableType = storageManager.createLazyValue(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer$cloneableType$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleType invoke() {
                return FindClassInModuleKt.findNonGenericClassAcrossDependencies(this.this$0.getSettings().getOwnerModuleDescriptor(), JvmBuiltInClassDescriptorFactory.INSTANCE.getCLONEABLE_CLASS_ID(), new NotFoundClasses(storageManager, this.this$0.getSettings().getOwnerModuleDescriptor())).getDefaultType();
            }
        });
        this.javaAnalogueClassesWithCustomSupertypeCache = storageManager.createCacheWithNotNullValues();
        this.notConsideredDeprecation = storageManager.createLazyValue(new Function0<Annotations>() { // from class: org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer$notConsideredDeprecation$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Annotations invoke() {
                return Annotations.INSTANCE.create(CollectionsKt.listOf(AnnotationUtilKt.createDeprecatedAnnotation$default(this.this$0.moduleDescriptor.getBuiltIns(), "This member is not fully supported by Kotlin compiler, so it may be absent or have different signature in next major version", null, null, true, 6, null)));
            }
        });
        this.deprecationForSomeOfTheListMethods = storageManager.createMemoizedFunction(new Function1<Pair<? extends String, ? extends String>, Annotations>() { // from class: org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer$deprecationForSomeOfTheListMethods$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Annotations invoke(Pair<? extends String, ? extends String> pair) {
                return invoke2((Pair<String, String>) pair);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Annotations invoke2(Pair<String, String> pair) {
                Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 0>");
                String strComponent1 = pair.component1();
                String strComponent2 = pair.component2();
                return Annotations.INSTANCE.create(CollectionsKt.listOf(AnnotationUtilKt.createDeprecatedAnnotation$default(this.this$0.moduleDescriptor.getBuiltIns(), FormatHelper.QUOTE + strComponent1 + "()' member of List is redundant in Kotlin and might be removed soon. Please use '" + strComponent2 + "()' stdlib extension instead", strComponent2 + "()", null, true, 4, null)));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JvmBuiltIns.Settings getSettings() {
        return (JvmBuiltIns.Settings) StorageKt.getValue(this.settings, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    private final SimpleType getCloneableType() {
        return (SimpleType) StorageKt.getValue(this.cloneableType, this, (KProperty<?>) $$delegatedProperties[1]);
    }

    private final Annotations getNotConsideredDeprecation() {
        return (Annotations) StorageKt.getValue(this.notConsideredDeprecation, this, (KProperty<?>) $$delegatedProperties[2]);
    }

    private final KotlinType createMockJavaIoSerializableType(StorageManager storageManager) {
        final ModuleDescriptor moduleDescriptor = this.moduleDescriptor;
        final FqName fqName = new FqName("java.io");
        ClassDescriptorImpl classDescriptorImpl = new ClassDescriptorImpl(new PackageFragmentDescriptorImpl(moduleDescriptor, fqName) { // from class: org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer$createMockJavaIoSerializableType$mockJavaIoPackageFragment$1
            @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
            /* JADX INFO: renamed from: getMemberScope */
            public MemberScope.Empty getScope() {
                return MemberScope.Empty.INSTANCE;
            }
        }, Name.identifier("Serializable"), Modality.ABSTRACT, ClassKind.INTERFACE, CollectionsKt.listOf(new LazyWrappedType(storageManager, new Function0<KotlinType>() { // from class: org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer$createMockJavaIoSerializableType$superTypes$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinType invoke() {
                SimpleType anyType = this.this$0.moduleDescriptor.getBuiltIns().getAnyType();
                Intrinsics.checkNotNullExpressionValue(anyType, "getAnyType(...)");
                return anyType;
            }
        })), SourceElement.NO_SOURCE, false, storageManager);
        classDescriptorImpl.initialize(MemberScope.Empty.INSTANCE, SetsKt.emptySet(), null);
        SimpleType defaultType = classDescriptorImpl.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        return defaultType;
    }

    @Override // org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider
    public Collection<KotlinType> getSupertypes(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        FqNameUnsafe fqNameUnsafe = DescriptorUtilsKt.getFqNameUnsafe(classDescriptor);
        if (!JvmBuiltInsSignatures.INSTANCE.isArrayOrPrimitiveArray(fqNameUnsafe)) {
            return JvmBuiltInsSignatures.INSTANCE.isSerializableInJava(fqNameUnsafe) ? CollectionsKt.listOf(this.mockSerializableType) : CollectionsKt.emptyList();
        }
        SimpleType cloneableType = getCloneableType();
        Intrinsics.checkNotNullExpressionValue(cloneableType, "<get-cloneableType>(...)");
        return CollectionsKt.listOf((Object[]) new KotlinType[]{cloneableType, this.mockSerializableType});
    }

    @Override // org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider
    public Collection<SimpleFunctionDescriptor> getFunctions(final Name name2, ClassDescriptor classDescriptor) {
        Annotations annotationsInvoke;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        if (Intrinsics.areEqual(name2, CloneableClassScope.INSTANCE.getCLONE_NAME()) && (classDescriptor instanceof DeserializedClassDescriptor) && KotlinBuiltIns.isArrayOrPrimitiveArray(classDescriptor)) {
            DeserializedClassDescriptor deserializedClassDescriptor = (DeserializedClassDescriptor) classDescriptor;
            List<ProtoBuf.Function> functionList = deserializedClassDescriptor.getClassProto().getFunctionList();
            Intrinsics.checkNotNullExpressionValue(functionList, "getFunctionList(...)");
            List<ProtoBuf.Function> list = functionList;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(NameResolverUtilKt.getName(deserializedClassDescriptor.getC().getNameResolver(), ((ProtoBuf.Function) it.next()).getName()), CloneableClassScope.INSTANCE.getCLONE_NAME())) {
                        return CollectionsKt.emptyList();
                    }
                }
            }
            return CollectionsKt.listOf(createCloneForArray(deserializedClassDescriptor, (SimpleFunctionDescriptor) CollectionsKt.single(getCloneableType().getMemberScope().getContributedFunctions(name2, NoLookupLocation.FROM_BUILTINS))));
        }
        if (!getSettings().getIsAdditionalBuiltInsFeatureSupported()) {
            return CollectionsKt.emptyList();
        }
        Collection<SimpleFunctionDescriptor> additionalFunctions = getAdditionalFunctions(classDescriptor, new Function1<MemberScope, Collection<? extends SimpleFunctionDescriptor>>() { // from class: org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer.getFunctions.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<SimpleFunctionDescriptor> invoke(MemberScope it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return it2.getContributedFunctions(name2, NoLookupLocation.FROM_BUILTINS);
            }
        });
        ArrayList arrayList = new ArrayList();
        for (SimpleFunctionDescriptor simpleFunctionDescriptor : additionalFunctions) {
            DeclarationDescriptor containingDeclaration = simpleFunctionDescriptor.getContainingModule();
            Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            FunctionDescriptor functionDescriptorSubstitute = simpleFunctionDescriptor.substitute(MappingUtilKt.createMappedTypeParametersSubstitution((ClassDescriptor) containingDeclaration, classDescriptor).buildSubstitutor());
            Intrinsics.checkNotNull(functionDescriptorSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor");
            FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> copyBuilderNewCopyBuilder = ((SimpleFunctionDescriptor) functionDescriptorSubstitute).newCopyBuilder();
            copyBuilderNewCopyBuilder.setOwner((DeclarationDescriptor) classDescriptor);
            copyBuilderNewCopyBuilder.setDispatchReceiverParameter(classDescriptor.getThisAsReceiverParameter());
            copyBuilderNewCopyBuilder.setPreserveSourceElement();
            int i = WhenMappings.$EnumSwitchMapping$0[getJdkMethodStatus(simpleFunctionDescriptor).ordinal()];
            SimpleFunctionDescriptor simpleFunctionDescriptor2 = null;
            if (i == 1) {
                if (!ModalityUtilsKt.isFinalClass(classDescriptor)) {
                    copyBuilderNewCopyBuilder.setHiddenForResolutionEverywhereBesideSupercalls();
                    FunctionDescriptor functionDescriptorBuild = copyBuilderNewCopyBuilder.build();
                    Intrinsics.checkNotNull(functionDescriptorBuild);
                    simpleFunctionDescriptor2 = (SimpleFunctionDescriptor) functionDescriptorBuild;
                }
            } else {
                if (i == 2) {
                    Name name3 = simpleFunctionDescriptor.getShortName();
                    if (Intrinsics.areEqual(name3, JvmBuiltInsCustomizerKt.GET_FIRST_LIST_NAME)) {
                        annotationsInvoke = this.deprecationForSomeOfTheListMethods.invoke(TuplesKt.to(simpleFunctionDescriptor.getShortName().asString(), "first"));
                    } else {
                        if (!Intrinsics.areEqual(name3, JvmBuiltInsCustomizerKt.GET_LAST_LIST_NAME)) {
                            throw new IllegalStateException(("Unexpected name: " + simpleFunctionDescriptor.getShortName()).toString());
                        }
                        annotationsInvoke = this.deprecationForSomeOfTheListMethods.invoke(TuplesKt.to(simpleFunctionDescriptor.getShortName().asString(), "last"));
                    }
                    copyBuilderNewCopyBuilder.setAdditionalAnnotations(annotationsInvoke);
                } else if (i == 3) {
                    copyBuilderNewCopyBuilder.setAdditionalAnnotations(getNotConsideredDeprecation());
                } else if (i != 4) {
                }
                FunctionDescriptor functionDescriptorBuild2 = copyBuilderNewCopyBuilder.build();
                Intrinsics.checkNotNull(functionDescriptorBuild2);
                simpleFunctionDescriptor2 = (SimpleFunctionDescriptor) functionDescriptorBuild2;
            }
            if (simpleFunctionDescriptor2 != null) {
                arrayList.add(simpleFunctionDescriptor2);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider
    public Set<Name> getFunctionsNames(ClassDescriptor classDescriptor) {
        LazyJavaClassMemberScope unsubstitutedMemberScope;
        Set<Name> functionNames;
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        if (!getSettings().getIsAdditionalBuiltInsFeatureSupported()) {
            return SetsKt.emptySet();
        }
        LazyJavaClassDescriptor javaAnalogue = getJavaAnalogue(classDescriptor);
        return (javaAnalogue == null || (unsubstitutedMemberScope = javaAnalogue.getUnsubstitutedMemberScope()) == null || (functionNames = unsubstitutedMemberScope.getFunctionNames()) == null) ? SetsKt.emptySet() : functionNames;
    }

    private final Collection<SimpleFunctionDescriptor> getAdditionalFunctions(ClassDescriptor classDescriptor, Function1<? super MemberScope, ? extends Collection<? extends SimpleFunctionDescriptor>> functionsByScope) {
        final LazyJavaClassDescriptor javaAnalogue = getJavaAnalogue(classDescriptor);
        if (javaAnalogue == null) {
            return CollectionsKt.emptyList();
        }
        LazyJavaClassDescriptor lazyJavaClassDescriptor = javaAnalogue;
        Collection<ClassDescriptor> collectionMapPlatformClass = this.j2kClassMapper.mapPlatformClass(DescriptorUtilsKt.getFqNameSafe(lazyJavaClassDescriptor), FallbackBuiltIns.INSTANCE.getInstance());
        final ClassDescriptor classDescriptor2 = (ClassDescriptor) CollectionsKt.lastOrNull(collectionMapPlatformClass);
        if (classDescriptor2 == null) {
            return CollectionsKt.emptyList();
        }
        SmartSet.Companion companion = SmartSet.INSTANCE;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionMapPlatformClass, 10));
        Iterator<T> it = collectionMapPlatformClass.iterator();
        while (it.hasNext()) {
            arrayList.add(DescriptorUtilsKt.getFqNameSafe((ClassDescriptor) it.next()));
        }
        SmartSet smartSetCreate = companion.create(arrayList);
        boolean zIsMutable = this.j2kClassMapper.isMutable(classDescriptor);
        MemberScope unsubstitutedMemberScope = this.javaAnalogueClassesWithCustomSupertypeCache.computeIfAbsent(DescriptorUtilsKt.getFqNameSafe(lazyJavaClassDescriptor), new Function0<ClassDescriptor>() { // from class: org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer$getAdditionalFunctions$fakeJavaClassDescriptor$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassDescriptor invoke() {
                LazyJavaClassDescriptor lazyJavaClassDescriptor2 = javaAnalogue;
                JavaResolverCache EMPTY = JavaResolverCache.EMPTY;
                Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
                return lazyJavaClassDescriptor2.copy$descriptors_jvm(EMPTY, classDescriptor2);
            }
        }).getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
        Collection<? extends SimpleFunctionDescriptor> collectionInvoke = functionsByScope.invoke(unsubstitutedMemberScope);
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : collectionInvoke) {
            SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) obj;
            if (simpleFunctionDescriptor.getKind() == CallableMemberDescriptor.Kind.DECLARATION && simpleFunctionDescriptor.getVisibility().isPublicAPI() && !KotlinBuiltIns.isDeprecated(simpleFunctionDescriptor)) {
                Collection<? extends FunctionDescriptor> overriddenDescriptors = simpleFunctionDescriptor.getOverriddenDescriptors();
                Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                Collection<? extends FunctionDescriptor> collection = overriddenDescriptors;
                if (!(collection instanceof Collection) || !collection.isEmpty()) {
                    Iterator<T> it2 = collection.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            DeclarationDescriptor containingDeclaration = ((FunctionDescriptor) it2.next()).getContainingModule();
                            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                            if (smartSetCreate.contains(DescriptorUtilsKt.getFqNameSafe(containingDeclaration))) {
                            }
                        }
                    }
                }
                if (!isMutabilityViolation(simpleFunctionDescriptor, zIsMutable)) {
                    arrayList2.add(obj);
                }
            }
        }
        return arrayList2;
    }

    private final SimpleFunctionDescriptor createCloneForArray(DeserializedClassDescriptor arrayClassDescriptor, SimpleFunctionDescriptor cloneFromCloneable) {
        FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> copyBuilderNewCopyBuilder = cloneFromCloneable.newCopyBuilder();
        copyBuilderNewCopyBuilder.setOwner((DeclarationDescriptor) arrayClassDescriptor);
        copyBuilderNewCopyBuilder.setVisibility(DescriptorVisibilities.PUBLIC);
        copyBuilderNewCopyBuilder.setReturnType((KotlinType) arrayClassDescriptor.getDefaultType());
        copyBuilderNewCopyBuilder.setDispatchReceiverParameter(arrayClassDescriptor.getThisAsReceiverParameter());
        FunctionDescriptor functionDescriptorBuild = copyBuilderNewCopyBuilder.build();
        Intrinsics.checkNotNull(functionDescriptorBuild);
        return (SimpleFunctionDescriptor) functionDescriptorBuild;
    }

    private final boolean isMutabilityViolation(SimpleFunctionDescriptor simpleFunctionDescriptor, boolean z) {
        DeclarationDescriptor containingDeclaration = simpleFunctionDescriptor.getContainingModule();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        String strComputeJvmDescriptor$default = MethodSignatureMappingKt.computeJvmDescriptor$default(simpleFunctionDescriptor, false, false, 3, null);
        if (z ^ JvmBuiltInsSignatures.INSTANCE.getMUTABLE_METHOD_SIGNATURES().contains(MethodSignatureBuildingUtilsKt.signature(SignatureBuildingComponents.INSTANCE, (ClassDescriptor) containingDeclaration, strComputeJvmDescriptor$default))) {
            return true;
        }
        Boolean boolIfAny = DFS.ifAny(CollectionsKt.listOf(simpleFunctionDescriptor), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return JvmBuiltInsCustomizer.isMutabilityViolation$lambda$7((CallableMemberDescriptor) obj);
            }
        }, new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer.isMutabilityViolation.2
            {
                super(1);
            }

            /* JADX WARN: Code duplicated, block: B:7:0x0021  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(CallableMemberDescriptor callableMemberDescriptor) {
                boolean z2;
                if (callableMemberDescriptor.getKind() == CallableMemberDescriptor.Kind.DECLARATION) {
                    JavaToKotlinClassMapper javaToKotlinClassMapper = JvmBuiltInsCustomizer.this.j2kClassMapper;
                    DeclarationDescriptor containingDeclaration2 = callableMemberDescriptor.getContainingModule();
                    Intrinsics.checkNotNull(containingDeclaration2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    if (javaToKotlinClassMapper.isMutable((ClassDescriptor) containingDeclaration2)) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                } else {
                    z2 = false;
                }
                return Boolean.valueOf(z2);
            }
        });
        Intrinsics.checkNotNullExpressionValue(boolIfAny, "ifAny(...)");
        return boolIfAny.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable isMutabilityViolation$lambda$7(CallableMemberDescriptor callableMemberDescriptor) {
        return callableMemberDescriptor.getOriginal().getOverriddenDescriptors();
    }

    private final JDKMemberStatus getJdkMethodStatus(FunctionDescriptor functionDescriptor) {
        DeclarationDescriptor containingDeclaration = functionDescriptor.getContainingModule();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        final String strComputeJvmDescriptor$default = MethodSignatureMappingKt.computeJvmDescriptor$default(functionDescriptor, false, false, 3, null);
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        Object objDfs = DFS.dfs(CollectionsKt.listOf((ClassDescriptor) containingDeclaration), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return JvmBuiltInsCustomizer.getJdkMethodStatus$lambda$9(this.f$0, (ClassDescriptor) obj);
            }
        }, new DFS.AbstractNodeHandler<ClassDescriptor, JDKMemberStatus>() { // from class: org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer.getJdkMethodStatus.2
            /* JADX WARN: Type inference failed for: r0v13, types: [T, org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer$JDKMemberStatus] */
            /* JADX WARN: Type inference failed for: r0v14, types: [T, org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer$JDKMemberStatus] */
            /* JADX WARN: Type inference failed for: r0v15, types: [T, org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer$JDKMemberStatus] */
            /* JADX WARN: Type inference failed for: r0v16, types: [T, org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsCustomizer$JDKMemberStatus] */
            @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
            public boolean beforeChildren(ClassDescriptor javaClassDescriptor) {
                Intrinsics.checkNotNullParameter(javaClassDescriptor, "javaClassDescriptor");
                String strSignature = MethodSignatureBuildingUtilsKt.signature(SignatureBuildingComponents.INSTANCE, javaClassDescriptor, strComputeJvmDescriptor$default);
                if (JvmBuiltInsSignatures.INSTANCE.getHIDDEN_METHOD_SIGNATURES().contains(strSignature)) {
                    objectRef.element = JDKMemberStatus.HIDDEN;
                } else if (JvmBuiltInsSignatures.INSTANCE.getVISIBLE_METHOD_SIGNATURES().contains(strSignature)) {
                    objectRef.element = JDKMemberStatus.VISIBLE;
                } else if (JvmBuiltInsSignatures.INSTANCE.getDEPRECATED_LIST_METHODS().contains(strSignature)) {
                    objectRef.element = JDKMemberStatus.DEPRECATED_LIST_METHODS;
                } else if (JvmBuiltInsSignatures.INSTANCE.getDROP_LIST_METHOD_SIGNATURES().contains(strSignature)) {
                    objectRef.element = JDKMemberStatus.DROP;
                }
                return objectRef.element == null;
            }

            @Override // org.jetbrains.kotlin.utils.DFS.NodeHandler
            public JDKMemberStatus result() {
                JDKMemberStatus jDKMemberStatus = objectRef.element;
                return jDKMemberStatus == null ? JDKMemberStatus.NOT_CONSIDERED : jDKMemberStatus;
            }
        });
        Intrinsics.checkNotNullExpressionValue(objDfs, "dfs(...)");
        return (JDKMemberStatus) objDfs;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable getJdkMethodStatus$lambda$9(JvmBuiltInsCustomizer this$0, ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Collection<KotlinType> collectionMo7438getSupertypes = classDescriptor.getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collectionMo7438getSupertypes.iterator();
        while (it.hasNext()) {
            ClassifierDescriptor this$1 = ((KotlinType) it.next()).getConstructor().getThis$0();
            LazyJavaClassDescriptor lazyJavaClassDescriptor = null;
            ClassifierDescriptor original = this$1 != null ? this$1.getOriginal() : null;
            ClassDescriptor classDescriptor2 = original instanceof ClassDescriptor ? (ClassDescriptor) original : null;
            if (classDescriptor2 != null) {
                LazyJavaClassDescriptor javaAnalogue = this$0.getJavaAnalogue(classDescriptor2);
                lazyJavaClassDescriptor = javaAnalogue != null ? javaAnalogue : classDescriptor2;
            }
            if (lazyJavaClassDescriptor != null) {
                arrayList.add(lazyJavaClassDescriptor);
            }
        }
        return arrayList;
    }

    private final LazyJavaClassDescriptor getJavaAnalogue(ClassDescriptor classDescriptor) {
        ClassId classIdMapKotlinToJava;
        FqName fqNameAsSingleFqName;
        if (KotlinBuiltIns.isAny(classDescriptor)) {
            return null;
        }
        ClassDescriptor classDescriptor2 = classDescriptor;
        if (!KotlinBuiltIns.isUnderKotlinPackage(classDescriptor2)) {
            return null;
        }
        FqNameUnsafe fqNameUnsafe = DescriptorUtilsKt.getFqNameUnsafe(classDescriptor2);
        if (!fqNameUnsafe.isSafe() || (classIdMapKotlinToJava = JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(fqNameUnsafe)) == null || (fqNameAsSingleFqName = classIdMapKotlinToJava.asSingleFqName()) == null) {
            return null;
        }
        ClassDescriptor classDescriptorResolveClassByFqName = DescriptorUtilKt.resolveClassByFqName(getSettings().getOwnerModuleDescriptor(), fqNameAsSingleFqName, NoLookupLocation.FROM_BUILTINS);
        if (classDescriptorResolveClassByFqName instanceof LazyJavaClassDescriptor) {
            return (LazyJavaClassDescriptor) classDescriptorResolveClassByFqName;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider
    public Collection<ClassConstructorDescriptor> getConstructors(ClassDescriptor classDescriptor) {
        ClassDescriptor classDescriptorMapJavaToKotlin$default;
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        if (classDescriptor.getClassKind() != ClassKind.CLASS || !getSettings().getIsAdditionalBuiltInsFeatureSupported()) {
            return CollectionsKt.emptyList();
        }
        LazyJavaClassDescriptor javaAnalogue = getJavaAnalogue(classDescriptor);
        if (javaAnalogue != null && (classDescriptorMapJavaToKotlin$default = JavaToKotlinClassMapper.mapJavaToKotlin$default(this.j2kClassMapper, DescriptorUtilsKt.getFqNameSafe(javaAnalogue), FallbackBuiltIns.INSTANCE.getInstance(), null, 4, null)) != null) {
            LazyJavaClassDescriptor lazyJavaClassDescriptor = javaAnalogue;
            TypeSubstitutor typeSubstitutorBuildSubstitutor = MappingUtilKt.createMappedTypeParametersSubstitution(classDescriptorMapJavaToKotlin$default, lazyJavaClassDescriptor).buildSubstitutor();
            List<ClassConstructorDescriptor> constructors = javaAnalogue.getConstructors();
            ArrayList arrayList = new ArrayList();
            for (Object obj : constructors) {
                ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) obj;
                if (classConstructorDescriptor.getVisibility().isPublicAPI()) {
                    Collection<ClassConstructorDescriptor> constructors2 = classDescriptorMapJavaToKotlin$default.getConstructors();
                    Intrinsics.checkNotNullExpressionValue(constructors2, "getConstructors(...)");
                    Collection<ClassConstructorDescriptor> collection = constructors2;
                    if (!(collection instanceof Collection) || !collection.isEmpty()) {
                        Iterator<T> it = collection.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                ClassConstructorDescriptor classConstructorDescriptor2 = (ClassConstructorDescriptor) it.next();
                                Intrinsics.checkNotNull(classConstructorDescriptor2);
                                if (getConstructors$isEffectivelyTheSameAs(classConstructorDescriptor2, typeSubstitutorBuildSubstitutor, classConstructorDescriptor)) {
                                }
                            }
                        }
                    }
                    if (!isTrivialCopyConstructorFor(classConstructorDescriptor, classDescriptor) && !KotlinBuiltIns.isDeprecated(classConstructorDescriptor) && !JvmBuiltInsSignatures.INSTANCE.getHIDDEN_CONSTRUCTOR_SIGNATURES().contains(MethodSignatureBuildingUtilsKt.signature(SignatureBuildingComponents.INSTANCE, lazyJavaClassDescriptor, MethodSignatureMappingKt.computeJvmDescriptor$default(classConstructorDescriptor, false, false, 3, null)))) {
                        arrayList.add(obj);
                    }
                }
            }
            ArrayList<ClassConstructorDescriptor> arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            for (ClassConstructorDescriptor classConstructorDescriptor3 : arrayList2) {
                FunctionDescriptor.CopyBuilder copyBuilderNewCopyBuilder = classConstructorDescriptor3.newCopyBuilder();
                copyBuilderNewCopyBuilder.setOwner((DeclarationDescriptor) classDescriptor);
                copyBuilderNewCopyBuilder.setReturnType((KotlinType) classDescriptor.getDefaultType());
                copyBuilderNewCopyBuilder.setPreserveSourceElement();
                copyBuilderNewCopyBuilder.setSubstitution(typeSubstitutorBuildSubstitutor.getSubstitution());
                if (!JvmBuiltInsSignatures.INSTANCE.getVISIBLE_CONSTRUCTOR_SIGNATURES().contains(MethodSignatureBuildingUtilsKt.signature(SignatureBuildingComponents.INSTANCE, lazyJavaClassDescriptor, MethodSignatureMappingKt.computeJvmDescriptor$default(classConstructorDescriptor3, false, false, 3, null)))) {
                    copyBuilderNewCopyBuilder.setAdditionalAnnotations(getNotConsideredDeprecation());
                }
                FunctionDescriptor functionDescriptorBuild = copyBuilderNewCopyBuilder.build();
                Intrinsics.checkNotNull(functionDescriptorBuild, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor");
                arrayList3.add((ClassConstructorDescriptor) functionDescriptorBuild);
            }
            return arrayList3;
        }
        return CollectionsKt.emptyList();
    }

    private static final boolean getConstructors$isEffectivelyTheSameAs(ConstructorDescriptor constructorDescriptor, TypeSubstitutor typeSubstitutor, ConstructorDescriptor constructorDescriptor2) {
        return OverridingUtil.getBothWaysOverridability(constructorDescriptor, constructorDescriptor2.substitute(typeSubstitutor)) == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE;
    }

    @Override // org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilter
    public boolean isFunctionAvailable(ClassDescriptor classDescriptor, SimpleFunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        LazyJavaClassDescriptor javaAnalogue = getJavaAnalogue(classDescriptor);
        if (javaAnalogue == null || !functionDescriptor.getAnnotations().hasAnnotation(PlatformDependentDeclarationFilterKt.getPLATFORM_DEPENDENT_ANNOTATION_FQ_NAME())) {
            return true;
        }
        if (!getSettings().getIsAdditionalBuiltInsFeatureSupported()) {
            return false;
        }
        String strComputeJvmDescriptor$default = MethodSignatureMappingKt.computeJvmDescriptor$default(functionDescriptor, false, false, 3, null);
        LazyJavaClassMemberScope unsubstitutedMemberScope = javaAnalogue.getUnsubstitutedMemberScope();
        Name name2 = functionDescriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        Collection<SimpleFunctionDescriptor> contributedFunctions = unsubstitutedMemberScope.getContributedFunctions(name2, NoLookupLocation.FROM_BUILTINS);
        if (!(contributedFunctions instanceof Collection) || !contributedFunctions.isEmpty()) {
            Iterator<T> it = contributedFunctions.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(MethodSignatureMappingKt.computeJvmDescriptor$default((SimpleFunctionDescriptor) it.next(), false, false, 3, null), strComputeJvmDescriptor$default)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean isTrivialCopyConstructorFor(ConstructorDescriptor constructorDescriptor, ClassDescriptor classDescriptor) {
        if (constructorDescriptor.getValueParameters().size() == 1) {
            List<ValueParameterDescriptor> valueParameters = constructorDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            ClassifierDescriptor this$0 = ((ValueParameterDescriptor) CollectionsKt.single((List) valueParameters)).getType().getConstructor().getThis$0();
            if (Intrinsics.areEqual(this$0 != null ? DescriptorUtilsKt.getFqNameUnsafe(this$0) : null, DescriptorUtilsKt.getFqNameUnsafe(classDescriptor))) {
                return true;
            }
        }
        return false;
    }
}
