package org.jetbrains.kotlin.builtins;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.StarProjectionImpl;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeAttributesKt;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: ReflectionTypes.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u0000 H2\u00020\u0001:\u0002GHB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010(\u001a\u00020\b2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0002J\u001e\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020.2\u0006\u00102\u001a\u000203J\u000e\u00104\u001a\u00020\b2\u0006\u00105\u001a\u00020,J\\\u00106\u001a\u0002072\u0006\u0010/\u001a\u0002002\b\u00108\u001a\u0004\u0018\u00010.2\f\u00109\u001a\b\u0012\u0004\u0012\u00020.0:2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020.0:2\u000e\u0010<\u001a\n\u0012\u0004\u0012\u00020=\u0018\u00010:2\u0006\u0010>\u001a\u00020.2\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020BJ,\u0010C\u001a\u0002072\u0006\u0010/\u001a\u0002002\f\u0010D\u001a\b\u0012\u0004\u0012\u00020.0:2\u0006\u0010>\u001a\u00020.2\u0006\u0010E\u001a\u00020BJ\u000e\u0010F\u001a\u00020\b2\u0006\u00105\u001a\u00020,R\u001b\u0010\u0007\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u001b\u0010\r\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\f\u001a\u0004\b\u000e\u0010\nR\u001b\u0010\u0010\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\f\u001a\u0004\b\u0011\u0010\nR\u001b\u0010\u0013\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\f\u001a\u0004\b\u0014\u0010\nR\u001b\u0010\u0016\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\f\u001a\u0004\b\u0017\u0010\nR\u001b\u0010\u0019\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\f\u001a\u0004\b\u001a\u0010\nR\u001b\u0010\u001c\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\f\u001a\u0004\b\u001d\u0010\nR\u001b\u0010\u001f\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\f\u001a\u0004\b \u0010\nR\u001b\u0010\"\u001a\u00020#8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b$\u0010%R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006I"}, d2 = {"Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "notFoundClasses", "Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;)V", "kClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", Namer.GET_KCLASS, "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "kClass$delegate", "Lorg/jetbrains/kotlin/builtins/ReflectionTypes$ClassLookup;", "kMutableProperty0", "getKMutableProperty0", "kMutableProperty0$delegate", "kMutableProperty1", "getKMutableProperty1", "kMutableProperty1$delegate", "kMutableProperty2", "getKMutableProperty2", "kMutableProperty2$delegate", "kProperty", "getKProperty", "kProperty$delegate", "kProperty0", "getKProperty0", "kProperty0$delegate", "kProperty1", "getKProperty1", "kProperty1$delegate", "kProperty2", "getKProperty2", "kProperty2$delegate", "kotlinReflectScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getKotlinReflectScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "kotlinReflectScope$delegate", "Lkotlin/Lazy;", "find", "className", "", "numberOfTypeParameters", "", "getKClassType", "Lorg/jetbrains/kotlin/types/KotlinType;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", ModuleXmlParser.TYPE, "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getKFunction", "n", "getKFunctionType", "Lorg/jetbrains/kotlin/types/SimpleType;", "receiverType", "contextReceiverTypes", "", "parameterTypes", "parameterNames", "Lorg/jetbrains/kotlin/name/Name;", "returnType", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "isSuspend", "", "getKPropertyType", "receiverTypes", "mutable", "getKSuspendFunction", "ClassLookup", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectionTypes {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kClass", "getKClass()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kProperty", "getKProperty()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kProperty0", "getKProperty0()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kProperty1", "getKProperty1()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kProperty2", "getKProperty2()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kMutableProperty0", "getKMutableProperty0()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kMutableProperty1", "getKMutableProperty1()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kMutableProperty2", "getKMutableProperty2()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;"))};

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: renamed from: kClass$delegate, reason: from kotlin metadata */
    private final ClassLookup kClass;

    /* JADX INFO: renamed from: kMutableProperty0$delegate, reason: from kotlin metadata */
    private final ClassLookup kMutableProperty0;

    /* JADX INFO: renamed from: kMutableProperty1$delegate, reason: from kotlin metadata */
    private final ClassLookup kMutableProperty1;

    /* JADX INFO: renamed from: kMutableProperty2$delegate, reason: from kotlin metadata */
    private final ClassLookup kMutableProperty2;

    /* JADX INFO: renamed from: kProperty$delegate, reason: from kotlin metadata */
    private final ClassLookup kProperty;

    /* JADX INFO: renamed from: kProperty0$delegate, reason: from kotlin metadata */
    private final ClassLookup kProperty0;

    /* JADX INFO: renamed from: kProperty1$delegate, reason: from kotlin metadata */
    private final ClassLookup kProperty1;

    /* JADX INFO: renamed from: kProperty2$delegate, reason: from kotlin metadata */
    private final ClassLookup kProperty2;

    /* JADX INFO: renamed from: kotlinReflectScope$delegate, reason: from kotlin metadata */
    private final Lazy kotlinReflectScope;
    private final NotFoundClasses notFoundClasses;

    @JvmStatic
    public static final boolean isNumberedKPropertyOrKMutablePropertyType(KotlinType kotlinType) {
        return INSTANCE.isNumberedKPropertyOrKMutablePropertyType(kotlinType);
    }

    public ReflectionTypes(final ModuleDescriptor module, NotFoundClasses notFoundClasses) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        this.notFoundClasses = notFoundClasses;
        this.kotlinReflectScope = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<MemberScope>() { // from class: org.jetbrains.kotlin.builtins.ReflectionTypes$kotlinReflectScope$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MemberScope invoke() {
                return module.getPackage(StandardNames.KOTLIN_REFLECT_FQ_NAME).getMemberScope();
            }
        });
        this.kClass = new ClassLookup(1);
        this.kProperty = new ClassLookup(1);
        this.kProperty0 = new ClassLookup(1);
        this.kProperty1 = new ClassLookup(2);
        this.kProperty2 = new ClassLookup(3);
        this.kMutableProperty0 = new ClassLookup(1);
        this.kMutableProperty1 = new ClassLookup(2);
        this.kMutableProperty2 = new ClassLookup(3);
    }

    private final MemberScope getKotlinReflectScope() {
        return (MemberScope) this.kotlinReflectScope.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor find(String className, int numberOfTypeParameters) {
        Name nameIdentifier = Name.identifier(className);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        ClassifierDescriptor contributedClassifier = getKotlinReflectScope().mo7465getContributedClassifier(nameIdentifier, NoLookupLocation.FROM_REFLECTION);
        ClassDescriptor classDescriptor = contributedClassifier instanceof ClassDescriptor ? (ClassDescriptor) contributedClassifier : null;
        return classDescriptor == null ? this.notFoundClasses.getClass(new ClassId(StandardNames.KOTLIN_REFLECT_FQ_NAME, nameIdentifier), CollectionsKt.listOf(Integer.valueOf(numberOfTypeParameters))) : classDescriptor;
    }

    /* JADX INFO: compiled from: ReflectionTypes.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001d\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\fH\u0086\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/builtins/ReflectionTypes$ClassLookup;", "", "numberOfTypeParameters", "", "(I)V", "getNumberOfTypeParameters", "()I", "getValue", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", SpecTypePair.NAME_types, "Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "property", "Lkotlin/reflect/KProperty;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ClassLookup {
        private final int numberOfTypeParameters;

        public ClassLookup(int i) {
            this.numberOfTypeParameters = i;
        }

        public final int getNumberOfTypeParameters() {
            return this.numberOfTypeParameters;
        }

        public final ClassDescriptor getValue(ReflectionTypes types, KProperty<?> property) {
            Intrinsics.checkNotNullParameter(types, "types");
            Intrinsics.checkNotNullParameter(property, "property");
            return types.find(CapitalizeDecapitalizeKt.capitalizeAsciiOnly(property.getName()), this.numberOfTypeParameters);
        }
    }

    public final ClassDescriptor getKFunction(int n) {
        return find(StandardNames.K_FUNCTION_PREFIX + n, n + 1);
    }

    public final ClassDescriptor getKSuspendFunction(int n) {
        return find(StandardNames.K_SUSPEND_FUNCTION_PREFIX + n, n + 1);
    }

    public final ClassDescriptor getKClass() {
        return this.kClass.getValue(this, $$delegatedProperties[0]);
    }

    public final ClassDescriptor getKProperty() {
        return this.kProperty.getValue(this, $$delegatedProperties[1]);
    }

    public final ClassDescriptor getKProperty0() {
        return this.kProperty0.getValue(this, $$delegatedProperties[2]);
    }

    public final ClassDescriptor getKProperty1() {
        return this.kProperty1.getValue(this, $$delegatedProperties[3]);
    }

    public final ClassDescriptor getKProperty2() {
        return this.kProperty2.getValue(this, $$delegatedProperties[4]);
    }

    public final ClassDescriptor getKMutableProperty0() {
        return this.kMutableProperty0.getValue(this, $$delegatedProperties[5]);
    }

    public final ClassDescriptor getKMutableProperty1() {
        return this.kMutableProperty1.getValue(this, $$delegatedProperties[6]);
    }

    public final ClassDescriptor getKMutableProperty2() {
        return this.kMutableProperty2.getValue(this, $$delegatedProperties[7]);
    }

    public final KotlinType getKClassType(Annotations annotations, KotlinType type, Variance variance) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(variance, "variance");
        return KotlinTypeFactory.simpleNotNullType(TypeAttributesKt.toDefaultAttributes(annotations), getKClass(), CollectionsKt.listOf(new TypeProjectionImpl(variance, type)));
    }

    public final SimpleType getKFunctionType(Annotations annotations, KotlinType receiverType, List<? extends KotlinType> contextReceiverTypes, List<? extends KotlinType> parameterTypes, List<Name> parameterNames, KotlinType returnType, KotlinBuiltIns builtIns, boolean isSuspend) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(contextReceiverTypes, "contextReceiverTypes");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        List<TypeProjection> functionTypeArgumentProjections = FunctionTypesKt.getFunctionTypeArgumentProjections(receiverType, contextReceiverTypes, parameterTypes, parameterNames, returnType, builtIns);
        int size = functionTypeArgumentProjections.size() - 1;
        return KotlinTypeFactory.simpleNotNullType(TypeAttributesKt.toDefaultAttributes(annotations), isSuspend ? getKSuspendFunction(size) : getKFunction(size), functionTypeArgumentProjections);
    }

    public final SimpleType getKPropertyType(Annotations annotations, List<? extends KotlinType> receiverTypes, KotlinType returnType, boolean mutable) {
        ClassDescriptor kProperty0;
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        int size = receiverTypes.size();
        if (size != 0) {
            if (size != 1) {
                if (size != 2) {
                    throw new AssertionError("More than 2 receivers is not allowed");
                }
                if (mutable) {
                    kProperty0 = getKMutableProperty2();
                } else {
                    kProperty0 = getKProperty2();
                }
            } else if (mutable) {
                kProperty0 = getKMutableProperty1();
            } else {
                kProperty0 = getKProperty1();
            }
        } else if (mutable) {
            kProperty0 = getKMutableProperty0();
        } else {
            kProperty0 = getKProperty0();
        }
        List listPlus = CollectionsKt.plus((Collection<? extends KotlinType>) receiverTypes, returnType);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPlus, 10));
        Iterator it = listPlus.iterator();
        while (it.hasNext()) {
            arrayList.add(new TypeProjectionImpl((KotlinType) it.next()));
        }
        return KotlinTypeFactory.simpleNotNullType(TypeAttributesKt.toDefaultAttributes(annotations), kProperty0, arrayList);
    }

    /* JADX INFO: compiled from: ReflectionTypes.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u000e\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u0019\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u0010\u0010\u001a\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004H\u0007J\u000e\u0010\u001b\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u001c\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u001d\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u001e\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u001f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/builtins/ReflectionTypes$Companion;", "", "()V", "createKPropertyStarType", "Lorg/jetbrains/kotlin/types/KotlinType;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "hasFqName", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "hasKCallableTypeFqName", ModuleXmlParser.TYPE, "hasKMutablePropertyTypeFqName", "hasKPropertyTypeFqName", "isBaseTypeForNumberedReferenceTypes", "isCallableType", "isKCallableType", "isKClassType", "isNumberedKFunction", "isNumberedKFunctionOrKSuspendFunction", "isNumberedKMutablePropertyType", "isNumberedKPropertyOrKMutablePropertyType", "isNumberedKPropertyType", "isNumberedKSuspendFunction", "isNumberedTypeWithOneOrMoreNumber", "isPossibleExpectedCallableType", "isReflectionClass", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isReflectionClass(ClassDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            PackageFragmentDescriptor packageFragmentDescriptor = (PackageFragmentDescriptor) DescriptorUtils.getParentOfType(descriptor, PackageFragmentDescriptor.class);
            return packageFragmentDescriptor != null && Intrinsics.areEqual(packageFragmentDescriptor.getFqName(), StandardNames.KOTLIN_REFLECT_FQ_NAME);
        }

        public final boolean isKClassType(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = type.unwrap().getConstructor().getThis$0();
            if (classifierDescriptorMo7434getDeclarationDescriptor == null) {
                return false;
            }
            return Intrinsics.areEqual(DescriptorUtilsKt.getClassId(classifierDescriptorMo7434getDeclarationDescriptor), StandardClassIds.INSTANCE.getKClass());
        }

        public final boolean isCallableType(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return FunctionTypesKt.isFunctionTypeOrSubtype(type) || FunctionTypesKt.isSuspendFunctionTypeOrSubtype(type) || isKCallableType(type);
        }

        public final boolean isBaseTypeForNumberedReferenceTypes(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return ReflectionTypes.INSTANCE.hasKPropertyTypeFqName(type) || ReflectionTypes.INSTANCE.hasKMutablePropertyTypeFqName(type) || ReflectionTypes.INSTANCE.hasKCallableTypeFqName(type);
        }

        @JvmStatic
        public final boolean isNumberedKPropertyOrKMutablePropertyType(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return isNumberedKPropertyType(type) || isNumberedKMutablePropertyType(type);
        }

        public final boolean isKCallableType(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            if (!hasKCallableTypeFqName(type)) {
                Collection<KotlinType> collectionMo7438getSupertypes = type.getConstructor().mo7438getSupertypes();
                Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
                Collection<KotlinType> collection = collectionMo7438getSupertypes;
                if (!collection.isEmpty()) {
                    for (KotlinType kotlinType : collection) {
                        Companion companion = ReflectionTypes.INSTANCE;
                        Intrinsics.checkNotNull(kotlinType);
                        if (companion.isKCallableType(kotlinType)) {
                        }
                    }
                }
                return false;
            }
            return true;
        }

        public final boolean hasKCallableTypeFqName(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return hasFqName(type.getConstructor(), StandardNames.FqNames.kCallable);
        }

        public final boolean hasKMutablePropertyTypeFqName(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return hasFqName(type.getConstructor(), StandardNames.FqNames.kMutablePropertyFqName);
        }

        public final boolean isNumberedKMutablePropertyType(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = type.getConstructor().getThis$0();
            ClassDescriptor classDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
            if (classDescriptor == null) {
                return false;
            }
            return hasFqName(classDescriptor, StandardNames.FqNames.kMutableProperty0) || hasFqName(classDescriptor, StandardNames.FqNames.kMutableProperty1) || hasFqName(classDescriptor, StandardNames.FqNames.kMutableProperty2);
        }

        public final boolean isNumberedTypeWithOneOrMoreNumber(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = type.getConstructor().getThis$0();
            ClassDescriptor classDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
            if (classDescriptor == null) {
                return false;
            }
            FqNameUnsafe fqName = DescriptorUtils.getFqName(classDescriptor);
            Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
            if (fqName.isRoot() || !Intrinsics.areEqual(fqName.parent().toSafe(), StandardNames.KOTLIN_REFLECT_FQ_NAME)) {
                return false;
            }
            String strAsString = classDescriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            for (String str : StandardNames.PREFIXES) {
                if (StringsKt.startsWith$default(strAsString, str, false, 2, (Object) null)) {
                    String strRemovePrefix = StringsKt.removePrefix(strAsString, (CharSequence) str);
                    return strRemovePrefix.length() > 0 && !Intrinsics.areEqual(strRemovePrefix, "0");
                }
            }
            return false;
        }

        public final boolean hasKPropertyTypeFqName(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return hasFqName(type.getConstructor(), StandardNames.FqNames.kPropertyFqName);
        }

        public final boolean isNumberedKPropertyType(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = type.getConstructor().getThis$0();
            ClassDescriptor classDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
            if (classDescriptor == null) {
                return false;
            }
            return hasFqName(classDescriptor, StandardNames.FqNames.kProperty0) || hasFqName(classDescriptor, StandardNames.FqNames.kProperty1) || hasFqName(classDescriptor, StandardNames.FqNames.kProperty2);
        }

        public final boolean isNumberedKFunctionOrKSuspendFunction(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return isNumberedKFunction(type) || isNumberedKSuspendFunction(type);
        }

        public final boolean isNumberedKFunction(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = type.getConstructor().getThis$0();
            ClassDescriptor classDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
            if (classDescriptor == null) {
                return false;
            }
            String strAsString = classDescriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString.length() > 9 && StringsKt.startsWith$default(strAsString, StandardNames.K_FUNCTION_PREFIX, false, 2, (Object) null) && Intrinsics.areEqual(DescriptorUtils.getFqName(classDescriptor).parent().toSafe(), StandardNames.KOTLIN_REFLECT_FQ_NAME);
        }

        public final boolean isNumberedKSuspendFunction(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = type.getConstructor().getThis$0();
            ClassDescriptor classDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
            if (classDescriptor == null) {
                return false;
            }
            String strAsString = classDescriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString.length() > 16 && StringsKt.startsWith$default(strAsString, StandardNames.K_SUSPEND_FUNCTION_PREFIX, false, 2, (Object) null) && Intrinsics.areEqual(DescriptorUtils.getFqName(classDescriptor).parent().toSafe(), StandardNames.KOTLIN_REFLECT_FQ_NAME);
        }

        private final boolean hasFqName(TypeConstructor typeConstructor, FqNameUnsafe fqName) {
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = typeConstructor.getThis$0();
            return (classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) && hasFqName((ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor, fqName);
        }

        private final boolean hasFqName(ClassDescriptor descriptor, FqNameUnsafe fqName) {
            return Intrinsics.areEqual(descriptor.getShortName(), fqName.shortName()) && Intrinsics.areEqual(DescriptorUtils.getFqName(descriptor), fqName);
        }

        public final KotlinType createKPropertyStarType(ModuleDescriptor module) {
            Intrinsics.checkNotNullParameter(module, "module");
            ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(module, StandardNames.FqNames.kProperty);
            if (classDescriptorFindClassAcrossModuleDependencies == null) {
                return null;
            }
            TypeAttributes empty = TypeAttributes.INSTANCE.getEmpty();
            List<TypeParameterDescriptor> parameters = classDescriptorFindClassAcrossModuleDependencies.getTypeConstructor().getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            Object objSingle = CollectionsKt.single((List<? extends Object>) parameters);
            Intrinsics.checkNotNullExpressionValue(objSingle, "single(...)");
            return KotlinTypeFactory.simpleNotNullType(empty, classDescriptorFindClassAcrossModuleDependencies, CollectionsKt.listOf(new StarProjectionImpl((TypeParameterDescriptor) objSingle)));
        }

        public final boolean isPossibleExpectedCallableType(TypeConstructor typeConstructor) {
            Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = typeConstructor.getThis$0();
            ClassDescriptor classDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
            if (classDescriptor == null) {
                return false;
            }
            if (KotlinBuiltIns.isAny(classDescriptor)) {
                return true;
            }
            String strAsString = classDescriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            FqNameUnsafe fqName = DescriptorUtils.getFqName(classDescriptor);
            Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
            if (fqName.isRoot()) {
                return false;
            }
            FqName safe = fqName.parent().toSafe();
            Intrinsics.checkNotNullExpressionValue(safe, "toSafe(...)");
            if (Intrinsics.areEqual(safe, StandardNames.KOTLIN_REFLECT_FQ_NAME)) {
                return StringsKt.startsWith$default(strAsString, StandardNames.K_FUNCTION_PREFIX, false, 2, (Object) null) || StringsKt.startsWith$default(strAsString, StandardNames.K_SUSPEND_FUNCTION_PREFIX, false, 2, (Object) null) || StringsKt.startsWith$default(strAsString, StandardNames.K_PROPERTY_PREFIX, false, 2, (Object) null) || StringsKt.startsWith$default(strAsString, StandardNames.K_MUTABLE_PROPERTY_PREFIX, false, 2, (Object) null) || Intrinsics.areEqual(strAsString, "KCallable") || Intrinsics.areEqual(strAsString, "KAnnotatedElement");
            }
            if (Intrinsics.areEqual(safe, StandardNames.BUILT_INS_PACKAGE_FQ_NAME) || Intrinsics.areEqual(safe, StandardNames.COROUTINES_PACKAGE_FQ_NAME)) {
                return StringsKt.startsWith$default(strAsString, "Function", false, 2, (Object) null) || StringsKt.startsWith$default(strAsString, "SuspendFunction", false, 2, (Object) null);
            }
            return false;
        }
    }
}
