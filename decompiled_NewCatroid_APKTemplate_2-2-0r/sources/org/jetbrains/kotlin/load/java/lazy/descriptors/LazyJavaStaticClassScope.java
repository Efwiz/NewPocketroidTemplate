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
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.load.java.components.DescriptorResolverUtils;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.UtilKt;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaMember;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: LazyJavaStaticClassScope.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ,\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0015H\u0014J,\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00110\u00182\u0006\u0010\u0012\u001a\u00020\u00132\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0015H\u0014J\u001e\u0010\u0019\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u0010\u001e\u001a\u00020\u0011H\u0014J\b\u0010\u001f\u001a\u00020 H\u0014J\u001e\u0010!\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u0010\u001e\u001a\u00020\u0011H\u0014J\u001e\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u00112\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\f0\u001cH\u0014J,\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00110\u00182\u0006\u0010\u0012\u001a\u00020\u00132\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0015H\u0014JD\u0010$\u001a\b\u0012\u0004\u0012\u0002H%0\u0010\"\u0004\b\u0000\u0010%2\u0006\u0010&\u001a\u00020'2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H%0\u00182\u0018\u0010(\u001a\u0014\u0012\u0004\u0012\u00020)\u0012\n\u0012\b\u0012\u0004\u0012\u0002H%0*0\u0015H\u0002J\u001a\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u0010-\u001a\u00020.H\u0016J\u001e\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00102\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u00100\u001a\u00020'H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\u00020\f*\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaStaticClassScope;", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaStaticScope;", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "jClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "ownerDescriptor", "Lorg/jetbrains/kotlin/load/java/descriptors/JavaClassDescriptor;", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/load/java/structure/JavaClass;Lorg/jetbrains/kotlin/load/java/descriptors/JavaClassDescriptor;)V", "getOwnerDescriptor", "()Lorg/jetbrains/kotlin/load/java/descriptors/JavaClassDescriptor;", "realOriginal", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getRealOriginal", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;)Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "computeClassNames", "", "Lorg/jetbrains/kotlin/name/Name;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "computeFunctionNames", "", "computeImplicitlyDeclaredFunctions", "", "result", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "name", "computeMemberIndex", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/ClassDeclaredMemberIndex;", "computeNonDeclaredFunctions", "computeNonDeclaredProperties", "computePropertyNames", "flatMapJavaStaticSupertypesScopes", "R", "root", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "onJavaStaticScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getStaticFunctionsFromJavaSuperClasses", "descriptor", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyJavaStaticClassScope extends LazyJavaStaticScope {
    private final JavaClass jClass;
    private final JavaClassDescriptor ownerDescriptor;

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    public JavaClassDescriptor getOwnerDescriptor() {
        return this.ownerDescriptor;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyJavaStaticClassScope(LazyJavaResolverContext c, JavaClass jClass, JavaClassDescriptor ownerDescriptor) {
        super(c);
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
        this.jClass = jClass;
        this.ownerDescriptor = ownerDescriptor;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    public ClassDeclaredMemberIndex computeMemberIndex() {
        return new ClassDeclaredMemberIndex(this.jClass, new Function1<JavaMember, Boolean>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaStaticClassScope.computeMemberIndex.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(JavaMember it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.mo7267isStatic());
            }
        });
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    protected Set<Name> computeFunctionNames(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Set<Name> mutableSet = CollectionsKt.toMutableSet(getDeclaredMemberIndex().invoke().getMethodNames());
        LazyJavaStaticClassScope parentJavaStaticClassScope = UtilKt.getParentJavaStaticClassScope(getOwnerDescriptor());
        Set<Name> functionNames = parentJavaStaticClassScope != null ? parentJavaStaticClassScope.getFunctionNames() : null;
        if (functionNames == null) {
            functionNames = SetsKt.emptySet();
        }
        mutableSet.addAll(functionNames);
        if (this.jClass.mo7263isEnum()) {
            mutableSet.addAll(CollectionsKt.listOf((Object[]) new Name[]{StandardNames.ENUM_VALUE_OF, StandardNames.ENUM_VALUES}));
        }
        mutableSet.addAll(getC().getComponents().getSyntheticPartsProvider().getStaticFunctionNames(getC(), getOwnerDescriptor()));
        return mutableSet;
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    protected Set<Name> computePropertyNames(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Set<Name> mutableSet = CollectionsKt.toMutableSet(getDeclaredMemberIndex().invoke().getFieldNames());
        flatMapJavaStaticSupertypesScopes(getOwnerDescriptor(), mutableSet, new Function1<MemberScope, Collection<? extends Name>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaStaticClassScope$computePropertyNames$1$1
            @Override // kotlin.jvm.functions.Function1
            public final Collection<Name> invoke(MemberScope it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getVariableNames();
            }
        });
        if (this.jClass.mo7263isEnum()) {
            mutableSet.add(StandardNames.ENUM_ENTRIES);
        }
        return mutableSet;
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    protected Set<Name> computeClassNames(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    protected void computeNonDeclaredFunctions(Collection<SimpleFunctionDescriptor> result, Name name2) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(name2, "name");
        Collection<? extends SimpleFunctionDescriptor> collectionResolveOverridesForStaticMembers = DescriptorResolverUtils.resolveOverridesForStaticMembers(name2, getStaticFunctionsFromJavaSuperClasses(name2, getOwnerDescriptor()), result, getOwnerDescriptor(), getC().getComponents().getErrorReporter(), getC().getComponents().getKotlinTypeChecker().getOverridingUtil());
        Intrinsics.checkNotNullExpressionValue(collectionResolveOverridesForStaticMembers, "resolveOverridesForStaticMembers(...)");
        result.addAll(collectionResolveOverridesForStaticMembers);
        if (this.jClass.mo7263isEnum()) {
            if (Intrinsics.areEqual(name2, StandardNames.ENUM_VALUE_OF)) {
                SimpleFunctionDescriptor simpleFunctionDescriptorCreateEnumValueOfMethod = DescriptorFactory.createEnumValueOfMethod(getOwnerDescriptor());
                Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorCreateEnumValueOfMethod, "createEnumValueOfMethod(...)");
                result.add(simpleFunctionDescriptorCreateEnumValueOfMethod);
            } else if (Intrinsics.areEqual(name2, StandardNames.ENUM_VALUES)) {
                SimpleFunctionDescriptor simpleFunctionDescriptorCreateEnumValuesMethod = DescriptorFactory.createEnumValuesMethod(getOwnerDescriptor());
                Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorCreateEnumValuesMethod, "createEnumValuesMethod(...)");
                result.add(simpleFunctionDescriptorCreateEnumValuesMethod);
            }
        }
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    protected void computeImplicitlyDeclaredFunctions(Collection<SimpleFunctionDescriptor> result, Name name2) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(name2, "name");
        getC().getComponents().getSyntheticPartsProvider().generateStaticFunctions(getC(), getOwnerDescriptor(), name2, result);
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaStaticScope, org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    protected void computeNonDeclaredProperties(final Name name2, Collection<PropertyDescriptor> result) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(result, "result");
        Set setFlatMapJavaStaticSupertypesScopes = flatMapJavaStaticSupertypesScopes(getOwnerDescriptor(), new LinkedHashSet(), new Function1<MemberScope, Collection<? extends PropertyDescriptor>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaStaticClassScope$computeNonDeclaredProperties$propertiesFromSupertypes$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<? extends PropertyDescriptor> invoke(MemberScope it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getContributedVariables(name2, NoLookupLocation.WHEN_GET_SUPER_MEMBERS);
            }
        });
        if (!result.isEmpty()) {
            Collection<? extends PropertyDescriptor> collectionResolveOverridesForStaticMembers = DescriptorResolverUtils.resolveOverridesForStaticMembers(name2, setFlatMapJavaStaticSupertypesScopes, result, getOwnerDescriptor(), getC().getComponents().getErrorReporter(), getC().getComponents().getKotlinTypeChecker().getOverridingUtil());
            Intrinsics.checkNotNullExpressionValue(collectionResolveOverridesForStaticMembers, "resolveOverridesForStaticMembers(...)");
            result.addAll(collectionResolveOverridesForStaticMembers);
        } else {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : setFlatMapJavaStaticSupertypesScopes) {
                PropertyDescriptor realOriginal = getRealOriginal((PropertyDescriptor) obj);
                Object obj2 = linkedHashMap.get(realOriginal);
                if (obj2 == null) {
                    obj2 = (List) new ArrayList();
                    linkedHashMap.put(realOriginal, obj2);
                }
                ((List) obj2).add(obj);
            }
            ArrayList arrayList = new ArrayList();
            Iterator it = linkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                Collection collectionResolveOverridesForStaticMembers2 = DescriptorResolverUtils.resolveOverridesForStaticMembers(name2, (Collection) ((Map.Entry) it.next()).getValue(), result, getOwnerDescriptor(), getC().getComponents().getErrorReporter(), getC().getComponents().getKotlinTypeChecker().getOverridingUtil());
                Intrinsics.checkNotNullExpressionValue(collectionResolveOverridesForStaticMembers2, "resolveOverridesForStaticMembers(...)");
                CollectionsKt.addAll(arrayList, collectionResolveOverridesForStaticMembers2);
            }
            result.addAll(arrayList);
        }
        if (this.jClass.mo7263isEnum() && Intrinsics.areEqual(name2, StandardNames.ENUM_ENTRIES)) {
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(result, DescriptorFactory.createEnumEntriesProperty(getOwnerDescriptor()));
        }
    }

    private final Set<SimpleFunctionDescriptor> getStaticFunctionsFromJavaSuperClasses(Name name2, ClassDescriptor descriptor) {
        LazyJavaStaticClassScope parentJavaStaticClassScope = UtilKt.getParentJavaStaticClassScope(descriptor);
        return parentJavaStaticClassScope == null ? SetsKt.emptySet() : CollectionsKt.toSet(parentJavaStaticClassScope.getContributedFunctions(name2, NoLookupLocation.WHEN_GET_SUPER_MEMBERS));
    }

    private final <R> Set<R> flatMapJavaStaticSupertypesScopes(final ClassDescriptor root, final Set<R> result, final Function1<? super MemberScope, ? extends Collection<? extends R>> onJavaStaticScope) {
        DFS.dfs(CollectionsKt.listOf(root), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaStaticClassScope$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return LazyJavaStaticClassScope.flatMapJavaStaticSupertypesScopes$lambda$6((ClassDescriptor) obj);
            }
        }, new DFS.AbstractNodeHandler<ClassDescriptor, Unit>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaStaticClassScope.flatMapJavaStaticSupertypesScopes.2
            /* JADX INFO: renamed from: result, reason: collision with other method in class */
            public void m7343result() {
            }

            @Override // org.jetbrains.kotlin.utils.DFS.NodeHandler
            public /* bridge */ /* synthetic */ Object result() {
                m7343result();
                return Unit.INSTANCE;
            }

            /* JADX WARN: Type inference incomplete: some casts might be missing */
            @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
            public boolean beforeChildren(ClassDescriptor current) {
                Intrinsics.checkNotNullParameter(current, "current");
                if (current == root) {
                    return true;
                }
                MemberScope staticScope = current.getStaticScope();
                Intrinsics.checkNotNullExpressionValue(staticScope, "getStaticScope(...)");
                if (!(staticScope instanceof LazyJavaStaticScope)) {
                    return true;
                }
                result.addAll((Collection<? extends R>) ((Collection) onJavaStaticScope.invoke(staticScope)));
                return false;
            }
        });
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable flatMapJavaStaticSupertypesScopes$lambda$6(ClassDescriptor classDescriptor) {
        Collection<KotlinType> collectionMo7438getSupertypes = classDescriptor.getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        return SequencesKt.asIterable(SequencesKt.mapNotNull(CollectionsKt.asSequence(collectionMo7438getSupertypes), new Function1<KotlinType, ClassDescriptor>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaStaticClassScope$flatMapJavaStaticSupertypesScopes$1$1
            @Override // kotlin.jvm.functions.Function1
            public final ClassDescriptor invoke(KotlinType kotlinType) {
                ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
                if (this$0 instanceof ClassDescriptor) {
                    return (ClassDescriptor) this$0;
                }
                return null;
            }
        }));
    }

    private final PropertyDescriptor getRealOriginal(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor.getKind().isReal()) {
            return propertyDescriptor;
        }
        Collection<? extends PropertyDescriptor> overriddenDescriptors = propertyDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends PropertyDescriptor> collection = overriddenDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (PropertyDescriptor propertyDescriptor2 : collection) {
            Intrinsics.checkNotNull(propertyDescriptor2);
            arrayList.add(getRealOriginal(propertyDescriptor2));
        }
        return (PropertyDescriptor) CollectionsKt.single(CollectionsKt.distinct(arrayList));
    }
}
