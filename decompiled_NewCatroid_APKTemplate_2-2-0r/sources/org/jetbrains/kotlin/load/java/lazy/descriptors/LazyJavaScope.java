package org.jetbrains.kotlin.load.java.lazy.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.UtilsKt;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaPropertyDescriptor;
import org.jetbrains.kotlin.load.java.lazy.ContextKt;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaAnnotationsKt;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.lazy.types.JavaTypeAttributes;
import org.jetbrains.kotlin.load.java.lazy.types.JavaTypeAttributesKt;
import org.jetbrains.kotlin.load.java.structure.JavaArrayType;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.load.kotlin.MethodSignatureMappingKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.OverridingUtilsKt;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindExclude;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNullable;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.NullableLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUsage;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: LazyJavaScope.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000ä\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001:\u0002lmB\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\u0005J,\u00100\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u00101\u001a\u0002022\u0014\u00103\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020-\u0018\u000104H$J*\u00105\u001a\b\u0012\u0004\u0012\u00020\t0(2\u0006\u00101\u001a\u0002022\u0012\u00103\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020-04H\u0004J,\u00106\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u00101\u001a\u0002022\u0014\u00103\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020-\u0018\u000104H$J\u001e\u00107\u001a\u0002082\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u001a0:2\u0006\u0010;\u001a\u00020\u0010H\u0014J\b\u0010<\u001a\u00020\u001cH$J\u0018\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020@2\u0006\u0010\u0002\u001a\u00020\u0003H\u0004J\u001e\u0010A\u001a\u0002082\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u001a0:2\u0006\u0010;\u001a\u00020\u0010H$J\u001e\u0010B\u001a\u0002082\u0006\u0010;\u001a\u00020\u00102\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00170:H$J,\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u00101\u001a\u0002022\u0014\u00103\u001a\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020-\u0018\u000104H$J\u0010\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020.H\u0002J\u0010\u0010G\u001a\u00020-2\u0006\u0010;\u001a\u00020\u0010H\u0016J\u000e\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016J*\u0010I\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u00101\u001a\u0002022\u0012\u00103\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020-04H\u0016J\u001e\u0010J\u001a\b\u0012\u0004\u0012\u00020\u001a0\b2\u0006\u0010;\u001a\u00020\u00102\u0006\u0010K\u001a\u00020LH\u0016J\u001e\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00170\b2\u0006\u0010;\u001a\u00020\u00102\u0006\u0010K\u001a\u00020LH\u0016J\n\u0010N\u001a\u0004\u0018\u00010OH$J\u000e\u0010P\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016J\u0010\u0010Q\u001a\u00020>2\u0006\u0010F\u001a\u00020.H\u0002J\u000e\u0010R\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016J\u0010\u0010S\u001a\u0002082\u0006\u0010T\u001a\u00020UH\u0016J4\u0010V\u001a\u00020W2\u0006\u0010?\u001a\u00020@2\f\u0010X\u001a\b\u0012\u0004\u0012\u00020Y0(2\u0006\u0010Z\u001a\u00020>2\f\u0010[\u001a\b\u0012\u0004\u0012\u00020\\0(H$J\u0010\u0010]\u001a\u00020^2\u0006\u0010?\u001a\u00020@H\u0004J\u0010\u0010_\u001a\u00020\u00172\u0006\u0010F\u001a\u00020.H\u0002J&\u0010`\u001a\u00020a2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010b\u001a\u00020c2\f\u0010d\u001a\b\u0012\u0004\u0012\u00020e0(H\u0004J\b\u0010f\u001a\u00020gH\u0016J\u0006\u0010h\u001a\u00020-J\f\u0010i\u001a\u00020-*\u00020^H\u0014J\u0012\u0010j\u001a\u000208*\b\u0012\u0004\u0012\u00020\u001a0kH\u0002R \u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR!\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00170\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\b0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u000bR!\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010\u0014\u001a\u0004\b\u001f\u0010\u0012R \u0010!\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\b0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0000X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0012\u0010$\u001a\u00020\tX¤\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R \u0010'\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170(0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b+\u0010\u0014\u001a\u0004\b*\u0010\u0012R\u0018\u0010,\u001a\u00020-*\u00020.8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b,\u0010/¨\u0006n"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaScope;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScopeImpl;", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "mainScope", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaScope;)V", "allDescriptors", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getAllDescriptors", "()Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "getC", "()Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "classNamesLazy", "", "Lorg/jetbrains/kotlin/name/Name;", "getClassNamesLazy", "()Ljava/util/Set;", "classNamesLazy$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "declaredField", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNullable;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "declaredFunctions", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "declaredMemberIndex", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/DeclaredMemberIndex;", "getDeclaredMemberIndex", "functionNamesLazy", "getFunctionNamesLazy", "functionNamesLazy$delegate", "functions", "getMainScope", "()Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaScope;", "ownerDescriptor", "getOwnerDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "properties", "", "propertyNamesLazy", "getPropertyNamesLazy", "propertyNamesLazy$delegate", "isFinalStatic", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "(Lorg/jetbrains/kotlin/load/java/structure/JavaField;)Z", "computeClassNames", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "computeDescriptors", "computeFunctionNames", "computeImplicitlyDeclaredFunctions", "", "result", "", "name", "computeMemberIndex", "computeMethodReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "method", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "computeNonDeclaredFunctions", "computeNonDeclaredProperties", "computePropertyNames", "createPropertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/PropertyDescriptorImpl;", "field", "definitelyDoesNotContainName", "getClassifierNames", "getContributedDescriptors", "getContributedFunctions", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedVariables", "getDispatchReceiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getFunctionNames", "getPropertyType", "getVariableNames", "printScopeStructure", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "resolveMethodSignature", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaScope$MethodSignatureData;", "methodTypeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "returnType", "valueParameters", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "resolveMethodToFunctionDescriptor", "Lorg/jetbrains/kotlin/load/java/descriptors/JavaMethodDescriptor;", "resolveProperty", "resolveValueParameters", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaScope$ResolvedValueParameters;", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "jValueParameters", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", "toString", "", "wasContentRequested", "isVisibleAsFunction", "retainMostSpecificMethods", "", "MethodSignatureData", "ResolvedValueParameters", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LazyJavaScope extends MemberScopeImpl {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaScope.class), "functionNamesLazy", "getFunctionNamesLazy()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaScope.class), "propertyNamesLazy", "getPropertyNamesLazy()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaScope.class), "classNamesLazy", "getClassNamesLazy()Ljava/util/Set;"))};
    private final NotNullLazyValue<Collection<DeclarationDescriptor>> allDescriptors;
    private final LazyJavaResolverContext c;

    /* JADX INFO: renamed from: classNamesLazy$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue classNamesLazy;
    private final MemoizedFunctionToNullable<Name, PropertyDescriptor> declaredField;
    private final MemoizedFunctionToNotNull<Name, Collection<SimpleFunctionDescriptor>> declaredFunctions;
    private final NotNullLazyValue<DeclaredMemberIndex> declaredMemberIndex;

    /* JADX INFO: renamed from: functionNamesLazy$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue functionNamesLazy;
    private final MemoizedFunctionToNotNull<Name, Collection<SimpleFunctionDescriptor>> functions;
    private final LazyJavaScope mainScope;
    private final MemoizedFunctionToNotNull<Name, List<PropertyDescriptor>> properties;

    /* JADX INFO: renamed from: propertyNamesLazy$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue propertyNamesLazy;

    protected abstract Set<Name> computeClassNames(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter);

    protected abstract Set<Name> computeFunctionNames(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter);

    protected void computeImplicitlyDeclaredFunctions(Collection<SimpleFunctionDescriptor> result, Name name2) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    protected abstract DeclaredMemberIndex computeMemberIndex();

    protected abstract void computeNonDeclaredFunctions(Collection<SimpleFunctionDescriptor> result, Name name2);

    protected abstract void computeNonDeclaredProperties(Name name2, Collection<PropertyDescriptor> result);

    protected abstract Set<Name> computePropertyNames(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter);

    protected abstract ReceiverParameterDescriptor getDispatchReceiverParameter();

    protected abstract DeclarationDescriptor getOwnerDescriptor();

    protected boolean isVisibleAsFunction(JavaMethodDescriptor javaMethodDescriptor) {
        Intrinsics.checkNotNullParameter(javaMethodDescriptor, "<this>");
        return true;
    }

    protected abstract MethodSignatureData resolveMethodSignature(JavaMethod method, List<? extends TypeParameterDescriptor> methodTypeParameters, KotlinType returnType, List<? extends ValueParameterDescriptor> valueParameters);

    public /* synthetic */ LazyJavaScope(LazyJavaResolverContext lazyJavaResolverContext, LazyJavaScope lazyJavaScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(lazyJavaResolverContext, (i & 2) != 0 ? null : lazyJavaScope);
    }

    protected final LazyJavaResolverContext getC() {
        return this.c;
    }

    protected final LazyJavaScope getMainScope() {
        return this.mainScope;
    }

    public LazyJavaScope(LazyJavaResolverContext c, LazyJavaScope lazyJavaScope) {
        Intrinsics.checkNotNullParameter(c, "c");
        this.c = c;
        this.mainScope = lazyJavaScope;
        this.allDescriptors = c.getStorageManager().createRecursionTolerantLazyValue(new Function0<Collection<? extends DeclarationDescriptor>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope$allDescriptors$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends DeclarationDescriptor> invoke() {
                return this.this$0.computeDescriptors(DescriptorKindFilter.ALL, MemberScope.INSTANCE.getALL_NAME_FILTER());
            }
        }, CollectionsKt.emptyList());
        this.declaredMemberIndex = c.getStorageManager().createLazyValue(new Function0<DeclaredMemberIndex>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope$declaredMemberIndex$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DeclaredMemberIndex invoke() {
                return this.this$0.computeMemberIndex();
            }
        });
        this.declaredFunctions = c.getStorageManager().createMemoizedFunction(new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope$declaredFunctions$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<SimpleFunctionDescriptor> invoke(Name name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                if (this.this$0.getMainScope() != null) {
                    return (Collection) this.this$0.getMainScope().declaredFunctions.invoke(name2);
                }
                ArrayList arrayList = new ArrayList();
                for (JavaMethod javaMethod : this.this$0.getDeclaredMemberIndex().invoke().findMethodsByName(name2)) {
                    JavaMethodDescriptor javaMethodDescriptorResolveMethodToFunctionDescriptor = this.this$0.resolveMethodToFunctionDescriptor(javaMethod);
                    if (this.this$0.isVisibleAsFunction(javaMethodDescriptorResolveMethodToFunctionDescriptor)) {
                        this.this$0.getC().getComponents().getJavaResolverCache().recordMethod(javaMethod, javaMethodDescriptorResolveMethodToFunctionDescriptor);
                        arrayList.add(javaMethodDescriptorResolveMethodToFunctionDescriptor);
                    }
                }
                ArrayList arrayList2 = arrayList;
                this.this$0.computeImplicitlyDeclaredFunctions(arrayList2, name2);
                return arrayList2;
            }
        });
        this.declaredField = c.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<Name, PropertyDescriptor>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope$declaredField$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final PropertyDescriptor invoke(Name name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                if (this.this$0.getMainScope() != null) {
                    return (PropertyDescriptor) this.this$0.getMainScope().declaredField.invoke(name2);
                }
                JavaField javaFieldFindFieldByName = this.this$0.getDeclaredMemberIndex().invoke().findFieldByName(name2);
                if (javaFieldFindFieldByName == null || javaFieldFindFieldByName.getIsEnumEntry()) {
                    return null;
                }
                return this.this$0.resolveProperty(javaFieldFindFieldByName);
            }
        });
        this.functions = c.getStorageManager().createMemoizedFunction(new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope$functions$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<SimpleFunctionDescriptor> invoke(Name name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                LinkedHashSet linkedHashSet = new LinkedHashSet((Collection) this.this$0.declaredFunctions.invoke(name2));
                this.this$0.retainMostSpecificMethods(linkedHashSet);
                LinkedHashSet linkedHashSet2 = linkedHashSet;
                this.this$0.computeNonDeclaredFunctions(linkedHashSet2, name2);
                return CollectionsKt.toList(this.this$0.getC().getComponents().getSignatureEnhancement().enhanceSignatures(this.this$0.getC(), linkedHashSet2));
            }
        });
        this.functionNamesLazy = c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope$functionNamesLazy$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                return this.this$0.computeFunctionNames(DescriptorKindFilter.FUNCTIONS, null);
            }
        });
        this.propertyNamesLazy = c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope$propertyNamesLazy$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                return this.this$0.computePropertyNames(DescriptorKindFilter.VARIABLES, null);
            }
        });
        this.classNamesLazy = c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope$classNamesLazy$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                return this.this$0.computeClassNames(DescriptorKindFilter.CLASSIFIERS, null);
            }
        });
        this.properties = c.getStorageManager().createMemoizedFunction(new Function1<Name, List<? extends PropertyDescriptor>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope$properties$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<PropertyDescriptor> invoke(Name name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = arrayList;
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, this.this$0.declaredField.invoke(name2));
                this.this$0.computeNonDeclaredProperties(name2, arrayList2);
                if (DescriptorUtils.isAnnotationClass(this.this$0.getOwnerDescriptor())) {
                    return CollectionsKt.toList(arrayList);
                }
                return CollectionsKt.toList(this.this$0.getC().getComponents().getSignatureEnhancement().enhanceSignatures(this.this$0.getC(), arrayList2));
            }
        });
    }

    protected final NotNullLazyValue<Collection<DeclarationDescriptor>> getAllDescriptors() {
        return this.allDescriptors;
    }

    protected final NotNullLazyValue<DeclaredMemberIndex> getDeclaredMemberIndex() {
        return this.declaredMemberIndex;
    }

    public final boolean wasContentRequested() {
        return this.declaredMemberIndex.isComputed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void retainMostSpecificMethods(Set<SimpleFunctionDescriptor> set) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : set) {
            String strComputeJvmDescriptor$default = MethodSignatureMappingKt.computeJvmDescriptor$default((SimpleFunctionDescriptor) obj, false, false, 2, null);
            Object obj2 = linkedHashMap.get(strComputeJvmDescriptor$default);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(strComputeJvmDescriptor$default, obj2);
            }
            ((List) obj2).add(obj);
        }
        for (List list : linkedHashMap.values()) {
            if (list.size() != 1) {
                List list2 = list;
                Collection<? extends SimpleFunctionDescriptor> collectionSelectMostSpecificInEachOverridableGroup = OverridingUtilsKt.selectMostSpecificInEachOverridableGroup(list2, new Function1<SimpleFunctionDescriptor, CallableDescriptor>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope$retainMostSpecificMethods$mostSpecificMethods$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CallableDescriptor invoke(SimpleFunctionDescriptor selectMostSpecificInEachOverridableGroup) {
                        Intrinsics.checkNotNullParameter(selectMostSpecificInEachOverridableGroup, "$this$selectMostSpecificInEachOverridableGroup");
                        return selectMostSpecificInEachOverridableGroup;
                    }
                });
                set.removeAll(list2);
                set.addAll(collectionSelectMostSpecificInEachOverridableGroup);
            }
        }
    }

    /* JADX INFO: compiled from: LazyJavaScope.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\b\u0084\b\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006¢\u0006\u0002\u0010\u000eJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006HÆ\u0003J\t\u0010\u001c\u001a\u00020\u000bHÆ\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\r0\u0006HÆ\u0003JY\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00062\b\b\u0002\u0010\n\u001a\u00020\u000b2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006HÆ\u0001J\u0013\u0010\u001f\u001a\u00020\u000b2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010!\u001a\u00020\"HÖ\u0001J\t\u0010#\u001a\u00020\rHÖ\u0001R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaScope$MethodSignatureData;", "", "returnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "receiverType", "valueParameters", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "typeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "hasStableParameterNames", "", "errors", "", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;Ljava/util/List;Ljava/util/List;ZLjava/util/List;)V", "getErrors", "()Ljava/util/List;", "getHasStableParameterNames", "()Z", "getReceiverType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getReturnType", "getTypeParameters", "getValueParameters", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final /* data */ class MethodSignatureData {
        private final List<String> errors;
        private final boolean hasStableParameterNames;
        private final KotlinType receiverType;
        private final KotlinType returnType;
        private final List<TypeParameterDescriptor> typeParameters;
        private final List<ValueParameterDescriptor> valueParameters;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ MethodSignatureData copy$default(MethodSignatureData methodSignatureData, KotlinType kotlinType, KotlinType kotlinType2, List list, List list2, boolean z, List list3, int i, Object obj) {
            if ((i & 1) != 0) {
                kotlinType = methodSignatureData.returnType;
            }
            if ((i & 2) != 0) {
                kotlinType2 = methodSignatureData.receiverType;
            }
            KotlinType kotlinType3 = kotlinType2;
            if ((i & 4) != 0) {
                list = methodSignatureData.valueParameters;
            }
            List list4 = list;
            if ((i & 8) != 0) {
                list2 = methodSignatureData.typeParameters;
            }
            List list5 = list2;
            if ((i & 16) != 0) {
                z = methodSignatureData.hasStableParameterNames;
            }
            boolean z2 = z;
            if ((i & 32) != 0) {
                list3 = methodSignatureData.errors;
            }
            return methodSignatureData.copy(kotlinType, kotlinType3, list4, list5, z2, list3);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final KotlinType getReturnType() {
            return this.returnType;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final KotlinType getReceiverType() {
            return this.receiverType;
        }

        public final List<ValueParameterDescriptor> component3() {
            return this.valueParameters;
        }

        public final List<TypeParameterDescriptor> component4() {
            return this.typeParameters;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final boolean getHasStableParameterNames() {
            return this.hasStableParameterNames;
        }

        public final List<String> component6() {
            return this.errors;
        }

        public final MethodSignatureData copy(KotlinType returnType, KotlinType receiverType, List<? extends ValueParameterDescriptor> valueParameters, List<? extends TypeParameterDescriptor> typeParameters, boolean hasStableParameterNames, List<String> errors) {
            Intrinsics.checkNotNullParameter(returnType, "returnType");
            Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
            Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
            Intrinsics.checkNotNullParameter(errors, "errors");
            return new MethodSignatureData(returnType, receiverType, valueParameters, typeParameters, hasStableParameterNames, errors);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof MethodSignatureData)) {
                return false;
            }
            MethodSignatureData methodSignatureData = (MethodSignatureData) other;
            return Intrinsics.areEqual(this.returnType, methodSignatureData.returnType) && Intrinsics.areEqual(this.receiverType, methodSignatureData.receiverType) && Intrinsics.areEqual(this.valueParameters, methodSignatureData.valueParameters) && Intrinsics.areEqual(this.typeParameters, methodSignatureData.typeParameters) && this.hasStableParameterNames == methodSignatureData.hasStableParameterNames && Intrinsics.areEqual(this.errors, methodSignatureData.errors);
        }

        public int hashCode() {
            int iHashCode = this.returnType.hashCode() * 31;
            KotlinType kotlinType = this.receiverType;
            return ((((((((iHashCode + (kotlinType == null ? 0 : kotlinType.hashCode())) * 31) + this.valueParameters.hashCode()) * 31) + this.typeParameters.hashCode()) * 31) + Boolean.hashCode(this.hasStableParameterNames)) * 31) + this.errors.hashCode();
        }

        public String toString() {
            return "MethodSignatureData(returnType=" + this.returnType + ", receiverType=" + this.receiverType + ", valueParameters=" + this.valueParameters + ", typeParameters=" + this.typeParameters + ", hasStableParameterNames=" + this.hasStableParameterNames + ", errors=" + this.errors + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public MethodSignatureData(KotlinType returnType, KotlinType kotlinType, List<? extends ValueParameterDescriptor> valueParameters, List<? extends TypeParameterDescriptor> typeParameters, boolean z, List<String> errors) {
            Intrinsics.checkNotNullParameter(returnType, "returnType");
            Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
            Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
            Intrinsics.checkNotNullParameter(errors, "errors");
            this.returnType = returnType;
            this.receiverType = kotlinType;
            this.valueParameters = valueParameters;
            this.typeParameters = typeParameters;
            this.hasStableParameterNames = z;
            this.errors = errors;
        }

        public final KotlinType getReturnType() {
            return this.returnType;
        }

        public final KotlinType getReceiverType() {
            return this.receiverType;
        }

        public final List<ValueParameterDescriptor> getValueParameters() {
            return this.valueParameters;
        }

        public final List<TypeParameterDescriptor> getTypeParameters() {
            return this.typeParameters;
        }

        public final boolean getHasStableParameterNames() {
            return this.hasStableParameterNames;
        }

        public final List<String> getErrors() {
            return this.errors;
        }
    }

    protected final JavaMethodDescriptor resolveMethodToFunctionDescriptor(JavaMethod method) {
        Map<? extends CallableDescriptor.UserDataKey<?>, ?> mapEmptyMap;
        Intrinsics.checkNotNullParameter(method, "method");
        JavaMethodDescriptor javaMethodDescriptorCreateJavaMethod = JavaMethodDescriptor.createJavaMethod(getOwnerDescriptor(), LazyJavaAnnotationsKt.resolveAnnotations(this.c, method), method.getName(), this.c.getComponents().getSourceElementFactory().source(method), this.declaredMemberIndex.invoke().findRecordComponentByName(method.getName()) != null && method.getValueParameters().isEmpty());
        Intrinsics.checkNotNullExpressionValue(javaMethodDescriptorCreateJavaMethod, "createJavaMethod(...)");
        LazyJavaResolverContext lazyJavaResolverContextChildForMethod$default = ContextKt.childForMethod$default(this.c, javaMethodDescriptorCreateJavaMethod, method, 0, 4, null);
        List<JavaTypeParameter> typeParameters = method.mo7258getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            TypeParameterDescriptor typeParameterDescriptorResolveTypeParameter = lazyJavaResolverContextChildForMethod$default.getTypeParameterResolver().resolveTypeParameter((JavaTypeParameter) it.next());
            Intrinsics.checkNotNull(typeParameterDescriptorResolveTypeParameter);
            arrayList.add(typeParameterDescriptorResolveTypeParameter);
        }
        ResolvedValueParameters resolvedValueParametersResolveValueParameters = resolveValueParameters(lazyJavaResolverContextChildForMethod$default, javaMethodDescriptorCreateJavaMethod, method.getValueParameters());
        MethodSignatureData methodSignatureDataResolveMethodSignature = resolveMethodSignature(method, arrayList, computeMethodReturnType(method, lazyJavaResolverContextChildForMethod$default), resolvedValueParametersResolveValueParameters.getDescriptors());
        KotlinType receiverType = methodSignatureDataResolveMethodSignature.getReceiverType();
        ReceiverParameterDescriptor receiverParameterDescriptorCreateExtensionReceiverParameterForCallable = receiverType != null ? DescriptorFactory.createExtensionReceiverParameterForCallable(javaMethodDescriptorCreateJavaMethod, receiverType, Annotations.INSTANCE.getEMPTY()) : null;
        ReceiverParameterDescriptor dispatchReceiverParameter = getDispatchReceiverParameter();
        List<ReceiverParameterDescriptor> listEmptyList = CollectionsKt.emptyList();
        List<TypeParameterDescriptor> typeParameters2 = methodSignatureDataResolveMethodSignature.getTypeParameters();
        List<ValueParameterDescriptor> valueParameters = methodSignatureDataResolveMethodSignature.getValueParameters();
        KotlinType returnType = methodSignatureDataResolveMethodSignature.getReturnType();
        Modality modalityConvertFromFlags = Modality.INSTANCE.convertFromFlags(false, method.mo7260isAbstract(), !method.mo7264isFinal());
        DescriptorVisibility descriptorVisibility = UtilsKt.toDescriptorVisibility(method.getVisibility());
        if (methodSignatureDataResolveMethodSignature.getReceiverType() != null) {
            mapEmptyMap = MapsKt.mapOf(TuplesKt.to(JavaMethodDescriptor.ORIGINAL_VALUE_PARAMETER_FOR_EXTENSION_RECEIVER, CollectionsKt.first((List) resolvedValueParametersResolveValueParameters.getDescriptors())));
        } else {
            mapEmptyMap = MapsKt.emptyMap();
        }
        javaMethodDescriptorCreateJavaMethod.initialize(receiverParameterDescriptorCreateExtensionReceiverParameterForCallable, dispatchReceiverParameter, listEmptyList, typeParameters2, valueParameters, returnType, modalityConvertFromFlags, descriptorVisibility, mapEmptyMap);
        javaMethodDescriptorCreateJavaMethod.setParameterNamesStatus(methodSignatureDataResolveMethodSignature.getHasStableParameterNames(), resolvedValueParametersResolveValueParameters.getHasSynthesizedNames());
        if (!methodSignatureDataResolveMethodSignature.getErrors().isEmpty()) {
            lazyJavaResolverContextChildForMethod$default.getComponents().getSignaturePropagator().reportSignatureErrors(javaMethodDescriptorCreateJavaMethod, methodSignatureDataResolveMethodSignature.getErrors());
        }
        return javaMethodDescriptorCreateJavaMethod;
    }

    protected final KotlinType computeMethodReturnType(JavaMethod method, LazyJavaResolverContext c) {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(c, "c");
        return c.getTypeResolver().transformJavaType(method.getReturnType(), JavaTypeAttributesKt.toAttributes$default(TypeUsage.COMMON, method.getContainingClass().mo7261isAnnotationType(), false, null, 6, null));
    }

    /* JADX INFO: compiled from: LazyJavaScope.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaScope$ResolvedValueParameters;", "", "descriptors", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "hasSynthesizedNames", "", "(Ljava/util/List;Z)V", "getDescriptors", "()Ljava/util/List;", "getHasSynthesizedNames", "()Z", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final class ResolvedValueParameters {
        private final List<ValueParameterDescriptor> descriptors;
        private final boolean hasSynthesizedNames;

        /* JADX WARN: Multi-variable type inference failed */
        public ResolvedValueParameters(List<? extends ValueParameterDescriptor> descriptors, boolean z) {
            Intrinsics.checkNotNullParameter(descriptors, "descriptors");
            this.descriptors = descriptors;
            this.hasSynthesizedNames = z;
        }

        public final List<ValueParameterDescriptor> getDescriptors() {
            return this.descriptors;
        }

        public final boolean getHasSynthesizedNames() {
            return this.hasSynthesizedNames;
        }
    }

    protected final ResolvedValueParameters resolveValueParameters(LazyJavaResolverContext c, FunctionDescriptor function, List<? extends JavaValueParameter> jValueParameters) {
        Pair pair;
        Name name2;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(jValueParameters, "jValueParameters");
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(jValueParameters);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10));
        boolean z = false;
        for (IndexedValue indexedValue : iterableWithIndex) {
            int index = indexedValue.getIndex();
            JavaValueParameter javaValueParameter = (JavaValueParameter) indexedValue.component2();
            Annotations annotationsResolveAnnotations = LazyJavaAnnotationsKt.resolveAnnotations(c, javaValueParameter);
            JavaTypeAttributes attributes$default = JavaTypeAttributesKt.toAttributes$default(TypeUsage.COMMON, false, false, null, 7, null);
            if (javaValueParameter.getIsVararg()) {
                JavaType type = javaValueParameter.getType();
                JavaArrayType javaArrayType = type instanceof JavaArrayType ? (JavaArrayType) type : null;
                if (javaArrayType == null) {
                    throw new AssertionError("Vararg parameter should be an array: " + javaValueParameter);
                }
                KotlinType kotlinTypeTransformArrayType = c.getTypeResolver().transformArrayType(javaArrayType, attributes$default, true);
                pair = TuplesKt.to(kotlinTypeTransformArrayType, c.getModule().getBuiltIns().getArrayElementType(kotlinTypeTransformArrayType));
            } else {
                pair = TuplesKt.to(c.getTypeResolver().transformJavaType(javaValueParameter.getType(), attributes$default), null);
            }
            KotlinType kotlinType = (KotlinType) pair.component1();
            KotlinType kotlinType2 = (KotlinType) pair.component2();
            if (Intrinsics.areEqual(function.getShortName().asString(), Namer.EQUALS_METHOD_NAME) && jValueParameters.size() == 1 && Intrinsics.areEqual(c.getModule().getBuiltIns().getNullableAnyType(), kotlinType)) {
                name2 = Name.identifier("other");
            } else {
                name2 = javaValueParameter.getName();
                if (name2 == null) {
                    z = true;
                }
                if (name2 == null) {
                    name2 = Name.identifier("p" + index);
                    Intrinsics.checkNotNullExpressionValue(name2, "identifier(...)");
                }
            }
            Name name3 = name2;
            Intrinsics.checkNotNull(name3);
            arrayList.add(new ValueParameterDescriptorImpl(function, null, index, annotationsResolveAnnotations, name3, kotlinType, false, false, false, kotlinType2, c.getComponents().getSourceElementFactory().source(javaValueParameter)));
        }
        return new ResolvedValueParameters(CollectionsKt.toList(arrayList), z);
    }

    private final Set<Name> getFunctionNamesLazy() {
        return (Set) StorageKt.getValue(this.functionNamesLazy, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    private final Set<Name> getPropertyNamesLazy() {
        return (Set) StorageKt.getValue(this.propertyNamesLazy, this, (KProperty<?>) $$delegatedProperties[1]);
    }

    private final Set<Name> getClassNamesLazy() {
        return (Set) StorageKt.getValue(this.classNamesLazy, this, (KProperty<?>) $$delegatedProperties[2]);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getFunctionNames() {
        return getFunctionNamesLazy();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getVariableNames() {
        return getPropertyNamesLazy();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getClassifierNames() {
        return getClassNamesLazy();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public boolean definitelyDoesNotContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return (getFunctionNamesLazy().contains(name2) || getPropertyNamesLazy().contains(name2) || getClassNamesLazy().contains(name2)) ? false : true;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return !getFunctionNames().contains(name2) ? CollectionsKt.emptyList() : this.functions.invoke(name2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [T, org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl] */
    /* JADX WARN: Type inference failed for: r1v14, types: [T, org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl] */
    public final PropertyDescriptor resolveProperty(final JavaField field) {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = createPropertyDescriptor(field);
        ((PropertyDescriptorImpl) objectRef.element).initialize(null, null, null, null);
        ((PropertyDescriptorImpl) objectRef.element).setType(getPropertyType(field), CollectionsKt.emptyList(), getDispatchReceiverParameter(), null, CollectionsKt.emptyList());
        DeclarationDescriptor ownerDescriptor = getOwnerDescriptor();
        ClassDescriptor classDescriptor = ownerDescriptor instanceof ClassDescriptor ? (ClassDescriptor) ownerDescriptor : null;
        if (classDescriptor != null) {
            LazyJavaResolverContext lazyJavaResolverContext = this.c;
            objectRef.element = lazyJavaResolverContext.getComponents().getSyntheticPartsProvider().modifyField(lazyJavaResolverContext, classDescriptor, (PropertyDescriptorImpl) objectRef.element);
        }
        if (DescriptorUtils.shouldRecordInitializerForProperty((VariableDescriptor) objectRef.element, ((PropertyDescriptorImpl) objectRef.element).getType())) {
            ((PropertyDescriptorImpl) objectRef.element).setCompileTimeInitializerFactory((Function0) new Function0<NullableLazyValue<? extends ConstantValue<?>>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope.resolveProperty.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final NullableLazyValue<? extends ConstantValue<?>> invoke() {
                    StorageManager storageManager = LazyJavaScope.this.getC().getStorageManager();
                    final LazyJavaScope lazyJavaScope = LazyJavaScope.this;
                    final JavaField javaField = field;
                    final Ref.ObjectRef<PropertyDescriptorImpl> objectRef2 = objectRef;
                    return storageManager.createNullableLazyValue(new Function0<ConstantValue<?>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope.resolveProperty.2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final ConstantValue<?> invoke() {
                            return lazyJavaScope.getC().getComponents().getJavaPropertyInitializerEvaluator().getInitializerConstant(javaField, objectRef2.element);
                        }
                    });
                }
            });
        }
        this.c.getComponents().getJavaResolverCache().recordField(field, (PropertyDescriptor) objectRef.element);
        return (PropertyDescriptor) objectRef.element;
    }

    private final PropertyDescriptorImpl createPropertyDescriptor(JavaField field) {
        JavaPropertyDescriptor javaPropertyDescriptorCreate = JavaPropertyDescriptor.create(getOwnerDescriptor(), LazyJavaAnnotationsKt.resolveAnnotations(this.c, field), Modality.FINAL, UtilsKt.toDescriptorVisibility(field.getVisibility()), !field.mo7264isFinal(), field.getName(), this.c.getComponents().getSourceElementFactory().source(field), isFinalStatic(field));
        Intrinsics.checkNotNullExpressionValue(javaPropertyDescriptorCreate, "create(...)");
        return javaPropertyDescriptorCreate;
    }

    private final boolean isFinalStatic(JavaField javaField) {
        return javaField.mo7264isFinal() && javaField.mo7267isStatic();
    }

    private final KotlinType getPropertyType(JavaField field) {
        KotlinType kotlinTypeTransformJavaType = this.c.getTypeResolver().transformJavaType(field.getType(), JavaTypeAttributesKt.toAttributes$default(TypeUsage.COMMON, false, false, null, 7, null));
        if ((!KotlinBuiltIns.isPrimitiveType(kotlinTypeTransformJavaType) && !KotlinBuiltIns.isString(kotlinTypeTransformJavaType)) || !isFinalStatic(field) || !field.mo7273getHasConstantNotNullInitializer()) {
            return kotlinTypeTransformJavaType;
        }
        KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(kotlinTypeTransformJavaType);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNotNullable, "makeNotNullable(...)");
        return kotlinTypeMakeNotNullable;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return !getVariableNames().contains(name2) ? CollectionsKt.emptyList() : this.properties.invoke(name2);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return this.allDescriptors.invoke();
    }

    protected final List<DeclarationDescriptor> computeDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        NoLookupLocation noLookupLocation = NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getCLASSIFIERS_MASK())) {
            for (Name name2 : computeClassNames(kindFilter, nameFilter)) {
                if (nameFilter.invoke(name2).booleanValue()) {
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(linkedHashSet, mo7465getContributedClassifier(name2, noLookupLocation));
                }
            }
        }
        if (kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getFUNCTIONS_MASK()) && !kindFilter.getExcludes().contains(DescriptorKindExclude.NonExtensions.INSTANCE)) {
            for (Name name3 : computeFunctionNames(kindFilter, nameFilter)) {
                if (nameFilter.invoke(name3).booleanValue()) {
                    linkedHashSet.addAll(getContributedFunctions(name3, noLookupLocation));
                }
            }
        }
        if (kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getVARIABLES_MASK()) && !kindFilter.getExcludes().contains(DescriptorKindExclude.NonExtensions.INSTANCE)) {
            for (Name name4 : computePropertyNames(kindFilter, nameFilter)) {
                if (nameFilter.invoke(name4).booleanValue()) {
                    linkedHashSet.addAll(getContributedVariables(name4, noLookupLocation));
                }
            }
        }
        return CollectionsKt.toList(linkedHashSet);
    }

    public String toString() {
        return "Lazy scope for " + getOwnerDescriptor();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    /* JADX INFO: renamed from: printScopeStructure */
    public void mo6105printScopeStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.println(getClass().getSimpleName(), " {");
        p.pushIndent();
        p.println("containingDeclaration: " + getOwnerDescriptor());
        p.popIndent();
        p.println("}");
    }
}
