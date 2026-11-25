package org.jetbrains.kotlin.resolve.multiplatform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.mpp.CallableSymbolMarker;
import org.jetbrains.kotlin.mpp.ClassLikeSymbolMarker;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.mpp.RegularClassSymbolMarker;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.mpp.AbstractExpectActualCompatibilityChecker;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;

/* JADX INFO: compiled from: ExpectedActualResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JJ\u0010\u0003\u001a\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0007\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0018\b\u0002\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\r0\fj\u0002`\u000eJH\u0010\u000f\u001a\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0007\u0018\u00010\u00042\u0006\u0010\u0010\u001a\u00020\u00062\u0014\b\u0002\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\r0\f2\b\b\u0002\u0010\u0012\u001a\u00020\rJ6\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014*\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\n2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\r0\fH\u0002¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectedActualResolver;", "", "()V", "findActualForExpected", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "", "expected", "platformModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "moduleVisibilityFilter", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ModuleFilter;", "findExpectedForActual", "actual", "moduleFilter", "shouldCheckAbsenceOfDefaultParamsInActual", "findNamesakesFromModule", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/multiplatform/ClassicExpectActualMatchingContext;", "module", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpectedActualResolver {
    public static final ExpectedActualResolver INSTANCE = new ExpectedActualResolver();

    private ExpectedActualResolver() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Map findActualForExpected$default(ExpectedActualResolver expectedActualResolver, MemberDescriptor memberDescriptor, ModuleDescriptor moduleDescriptor, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            function1 = ModuleFilterUtilsKt.allModulesProvidingActualsFor(DescriptorUtilsKt.getModule(memberDescriptor), moduleDescriptor);
        }
        return expectedActualResolver.findActualForExpected(memberDescriptor, moduleDescriptor, function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Map<ExpectActualCompatibility<MemberDescriptor>, List<MemberDescriptor>> findActualForExpected(MemberDescriptor expected, ModuleDescriptor platformModule, Function1<? super ModuleDescriptor, Boolean> moduleVisibilityFilter) {
        Intrinsics.checkNotNullParameter(expected, "expected");
        Intrinsics.checkNotNullParameter(platformModule, "platformModule");
        Intrinsics.checkNotNullParameter(moduleVisibilityFilter, "moduleVisibilityFilter");
        LinkedHashMap linkedHashMap = null;
        ClassicExpectActualMatchingContext classicExpectActualMatchingContext = new ClassicExpectActualMatchingContext(platformModule, false, 2, 0 == true ? 1 : 0);
        if (expected instanceof CallableMemberDescriptor) {
            Collection<CallableMemberDescriptor> collectionFindNamesakesFromModule = findNamesakesFromModule((CallableMemberDescriptor) expected, classicExpectActualMatchingContext, platformModule, moduleVisibilityFilter);
            ArrayList arrayList = new ArrayList();
            for (Object obj : collectionFindNamesakesFromModule) {
                CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) obj;
                if (!Intrinsics.areEqual(expected, callableMemberDescriptor) && !callableMemberDescriptor.get$isExpect() && ExpectedActualResolverKt.getCouldHaveASource(callableMemberDescriptor)) {
                    arrayList.add(obj);
                }
            }
            linkedHashMap = new LinkedHashMap();
            for (Object obj2 : arrayList) {
                ExpectActualCompatibility callablesCompatibility = AbstractExpectActualCompatibilityChecker.INSTANCE.getCallablesCompatibility((CallableSymbolMarker) expected, (CallableMemberDescriptor) obj2, (TypeSubstitutorMarker) null, (RegularClassSymbolMarker) null, (RegularClassSymbolMarker) null, classicExpectActualMatchingContext);
                Object obj3 = linkedHashMap.get(callablesCompatibility);
                if (obj3 == null) {
                    obj3 = (List) new ArrayList();
                    linkedHashMap.put(callablesCompatibility, obj3);
                }
                ((List) obj3).add(obj2);
            }
        } else if (expected instanceof ClassDescriptor) {
            Collection<ClassifierDescriptorWithTypeParameters> collectionFindClassifiersFromModule = classicExpectActualMatchingContext.findClassifiersFromModule(DescriptorUtilsKt.getClassId((ClassifierDescriptor) expected), platformModule, moduleVisibilityFilter);
            ArrayList arrayList2 = new ArrayList();
            for (Object obj4 : collectionFindClassifiersFromModule) {
                ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters = (ClassifierDescriptorWithTypeParameters) obj4;
                if (!Intrinsics.areEqual(expected, classifierDescriptorWithTypeParameters) && !classifierDescriptorWithTypeParameters.get$isExpect() && ExpectedActualResolverKt.getCouldHaveASource(classifierDescriptorWithTypeParameters)) {
                    arrayList2.add(obj4);
                }
            }
            linkedHashMap = new LinkedHashMap();
            for (Object obj5 : arrayList2) {
                ExpectActualCompatibility classifiersCompatibility = AbstractExpectActualCompatibilityChecker.INSTANCE.getClassifiersCompatibility((RegularClassSymbolMarker) expected, (ClassifierDescriptorWithTypeParameters) obj5, classicExpectActualMatchingContext);
                Object obj6 = linkedHashMap.get(classifiersCompatibility);
                if (obj6 == null) {
                    obj6 = (List) new ArrayList();
                    linkedHashMap.put(classifiersCompatibility, obj6);
                }
                ((List) obj6).add(obj5);
            }
        }
        return linkedHashMap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Map findExpectedForActual$default(ExpectedActualResolver expectedActualResolver, MemberDescriptor memberDescriptor, Function1 function1, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = ModuleFilterUtilsKt.allModulesProvidingExpectsFor(DescriptorUtilsKt.getModule(memberDescriptor));
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return expectedActualResolver.findExpectedForActual(memberDescriptor, function1, z);
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0093  */
    public final Map<ExpectActualCompatibility<MemberDescriptor>, List<MemberDescriptor>> findExpectedForActual(MemberDescriptor actual, Function1<? super ModuleDescriptor, Boolean> moduleFilter, boolean shouldCheckAbsenceOfDefaultParamsInActual) {
        List listFindNamesakesFromModule;
        ClassDescriptor classDescriptor;
        ClassDescriptor classDescriptor2;
        TypeSubstitutorMarker typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor;
        ArrayList arrayListEmptyList;
        Collection<List<MemberDescriptor>> collectionValues;
        List list;
        Intrinsics.checkNotNullParameter(actual, "actual");
        Intrinsics.checkNotNullParameter(moduleFilter, "moduleFilter");
        MemberDescriptor memberDescriptor = actual;
        ClassicExpectActualMatchingContext classicExpectActualMatchingContext = new ClassicExpectActualMatchingContext(DescriptorUtilsKt.getModule(memberDescriptor), shouldCheckAbsenceOfDefaultParamsInActual);
        if (actual instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) actual;
            DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingModule();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (containingDeclaration instanceof ClassifierDescriptorWithTypeParameters) {
                Map<ExpectActualCompatibility<MemberDescriptor>, List<MemberDescriptor>> mapFindExpectedForActual = findExpectedForActual((MemberDescriptor) containingDeclaration, moduleFilter, shouldCheckAbsenceOfDefaultParamsInActual);
                MemberDescriptor memberDescriptor2 = (mapFindExpectedForActual == null || (collectionValues = mapFindExpectedForActual.values()) == null || (list = (List) CollectionsKt.firstOrNull(collectionValues)) == null) ? null : (MemberDescriptor) CollectionsKt.firstOrNull(list);
                ClassDescriptor classDescriptor3 = memberDescriptor2 instanceof ClassDescriptor ? (ClassDescriptor) memberDescriptor2 : null;
                if (classDescriptor3 != null) {
                    Name name2 = callableMemberDescriptor.getShortName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    List<DeclarationSymbolMarker> membersForExpectClass = classicExpectActualMatchingContext.getMembersForExpectClass(classDescriptor3, name2);
                    if (membersForExpectClass != null) {
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : membersForExpectClass) {
                            if (obj instanceof CallableMemberDescriptor) {
                                arrayList.add(obj);
                            }
                        }
                        arrayListEmptyList = arrayList;
                    } else {
                        arrayListEmptyList = null;
                    }
                } else {
                    arrayListEmptyList = null;
                }
                if (arrayListEmptyList == null) {
                    arrayListEmptyList = CollectionsKt.emptyList();
                }
                listFindNamesakesFromModule = arrayListEmptyList;
            } else {
                if (!(containingDeclaration instanceof PackageFragmentDescriptor)) {
                    return null;
                }
                listFindNamesakesFromModule = findNamesakesFromModule(callableMemberDescriptor, classicExpectActualMatchingContext, DescriptorUtilsKt.getModule(memberDescriptor), moduleFilter);
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : listFindNamesakesFromModule) {
                CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) obj2;
                if (!Intrinsics.areEqual(actual, callableMemberDescriptor2) && callableMemberDescriptor2.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE && callableMemberDescriptor2.get$isExpect()) {
                    arrayList2.add(obj2);
                }
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj3 : arrayList2) {
                CallableMemberDescriptor callableMemberDescriptor3 = (CallableMemberDescriptor) obj3;
                if (containingDeclaration instanceof ClassDescriptor) {
                    classDescriptor = (ClassDescriptor) containingDeclaration;
                    DeclarationDescriptor containingDeclaration2 = callableMemberDescriptor3.getContainingModule();
                    Intrinsics.checkNotNull(containingDeclaration2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    classDescriptor2 = (ClassDescriptor) containingDeclaration2;
                    if (classDescriptor2.getDeclaredTypeParameters().size() == classDescriptor.getDeclaredTypeParameters().size()) {
                        List<TypeParameterDescriptor> declaredTypeParameters = classDescriptor2.getDeclaredTypeParameters();
                        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
                        List<TypeParameterDescriptor> declaredTypeParameters2 = classDescriptor.getDeclaredTypeParameters();
                        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters2, "getDeclaredTypeParameters(...)");
                        typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor = classicExpectActualMatchingContext.createExpectActualTypeParameterSubstitutor(declaredTypeParameters, declaredTypeParameters2, null);
                    } else {
                        typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor = null;
                    }
                } else {
                    classDescriptor = null;
                    classDescriptor2 = null;
                    typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor = null;
                }
                ExpectActualCompatibility callablesCompatibility = AbstractExpectActualCompatibilityChecker.INSTANCE.getCallablesCompatibility(callableMemberDescriptor3, (CallableSymbolMarker) actual, typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor, classDescriptor2, classDescriptor, classicExpectActualMatchingContext);
                Object obj4 = linkedHashMap.get(callablesCompatibility);
                if (obj4 == null) {
                    obj4 = (List) new ArrayList();
                    linkedHashMap.put(callablesCompatibility, obj4);
                }
                ((List) obj4).add(obj3);
            }
            return linkedHashMap;
        }
        if (!(actual instanceof ClassifierDescriptorWithTypeParameters)) {
            return null;
        }
        Collection<ClassifierDescriptorWithTypeParameters> collectionFindClassifiersFromModule = classicExpectActualMatchingContext.findClassifiersFromModule(DescriptorUtilsKt.getClassId((ClassifierDescriptor) actual), DescriptorUtilsKt.getModule(memberDescriptor), moduleFilter);
        ArrayList arrayList3 = new ArrayList();
        for (Object obj5 : collectionFindClassifiersFromModule) {
            ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters = (ClassifierDescriptorWithTypeParameters) obj5;
            if (!Intrinsics.areEqual(actual, classifierDescriptorWithTypeParameters) && (classifierDescriptorWithTypeParameters instanceof ClassDescriptor) && ((ClassDescriptor) classifierDescriptorWithTypeParameters).get$isExpect()) {
                arrayList3.add(obj5);
            }
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Object obj6 : arrayList3) {
            ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters2 = (ClassifierDescriptorWithTypeParameters) obj6;
            AbstractExpectActualCompatibilityChecker abstractExpectActualCompatibilityChecker = AbstractExpectActualCompatibilityChecker.INSTANCE;
            Intrinsics.checkNotNull(classifierDescriptorWithTypeParameters2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            ExpectActualCompatibility classifiersCompatibility = abstractExpectActualCompatibilityChecker.getClassifiersCompatibility((ClassDescriptor) classifierDescriptorWithTypeParameters2, (ClassLikeSymbolMarker) actual, classicExpectActualMatchingContext);
            Object obj7 = linkedHashMap2.get(classifiersCompatibility);
            if (obj7 == null) {
                obj7 = (List) new ArrayList();
                linkedHashMap2.put(classifiersCompatibility, obj7);
            }
            ((List) obj7).add(obj6);
        }
        return linkedHashMap2;
    }

    private final Collection<CallableMemberDescriptor> findNamesakesFromModule(final CallableMemberDescriptor callableMemberDescriptor, ClassicExpectActualMatchingContext classicExpectActualMatchingContext, ModuleDescriptor moduleDescriptor, Function1<? super ModuleDescriptor, Boolean> function1) {
        ArrayList arrayListListOf;
        ArrayList arrayList;
        DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (containingDeclaration instanceof PackageFragmentDescriptor) {
            arrayListListOf = CollectionsKt.listOf(moduleDescriptor.getPackage(((PackageFragmentDescriptor) containingDeclaration).getFqName()).getMemberScope());
        } else if (containingDeclaration instanceof ClassDescriptor) {
            Collection<ClassifierDescriptorWithTypeParameters> collectionFindClassifiersFromModule = classicExpectActualMatchingContext.findClassifiersFromModule(DescriptorUtilsKt.getClassId((ClassifierDescriptor) containingDeclaration), moduleDescriptor, function1);
            ArrayList arrayList2 = new ArrayList();
            for (ClassDescriptor classDescriptor : collectionFindClassifiersFromModule) {
                if (classDescriptor instanceof TypeAliasDescriptor) {
                    classDescriptor = ((TypeAliasDescriptor) classDescriptor).getClassDescriptor();
                }
                if (classDescriptor != null) {
                    arrayList2.add(classDescriptor);
                }
            }
            ArrayList arrayList3 = new ArrayList();
            for (Object obj : arrayList2) {
                if (obj instanceof ClassDescriptor) {
                    arrayList3.add(obj);
                }
            }
            ArrayList arrayList4 = arrayList3;
            if (!(callableMemberDescriptor instanceof ConstructorDescriptor)) {
                ArrayList arrayList5 = arrayList4;
                ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
                Iterator it = arrayList5.iterator();
                while (it.hasNext()) {
                    arrayList6.add(((ClassDescriptor) it.next()).getUnsubstitutedMemberScope());
                }
                arrayListListOf = arrayList6;
            } else {
                ArrayList arrayList7 = new ArrayList();
                Iterator it2 = arrayList4.iterator();
                while (it2.hasNext()) {
                    Collection<ClassConstructorDescriptor> constructors = ((ClassDescriptor) it2.next()).getConstructors();
                    Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
                    CollectionsKt.addAll(arrayList7, constructors);
                }
                return arrayList7;
            }
        } else {
            return CollectionsKt.emptyList();
        }
        if (!(callableMemberDescriptor instanceof FunctionDescriptor)) {
            if (!(callableMemberDescriptor instanceof PropertyDescriptor)) {
                throw new AssertionError("Unsupported declaration: " + callableMemberDescriptor);
            }
            ArrayList arrayList8 = new ArrayList();
            Iterator it3 = arrayListListOf.iterator();
            while (it3.hasNext()) {
                Collection<DeclarationDescriptor> contributedDescriptors = ((MemberScope) it3.next()).getContributedDescriptors(DescriptorKindFilter.VARIABLES, new Function1<Name, Boolean>() { // from class: org.jetbrains.kotlin.resolve.multiplatform.ExpectedActualResolver$findNamesakesFromModule$2$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(Name it4) {
                        Intrinsics.checkNotNullParameter(it4, "it");
                        return Boolean.valueOf(Intrinsics.areEqual(it4, ((PropertyDescriptor) callableMemberDescriptor).getShortName()));
                    }
                });
                ArrayList arrayList9 = new ArrayList();
                for (Object obj2 : contributedDescriptors) {
                    if (Intrinsics.areEqual(((DeclarationDescriptor) obj2).getShortName(), ((PropertyDescriptor) callableMemberDescriptor).getShortName())) {
                        arrayList9.add(obj2);
                    }
                }
                ArrayList arrayList10 = new ArrayList();
                for (Object obj3 : arrayList9) {
                    if (obj3 instanceof CallableMemberDescriptor) {
                        arrayList10.add(obj3);
                    }
                }
                CollectionsKt.addAll(arrayList8, arrayList10);
            }
            arrayList = arrayList8;
        } else {
            ArrayList arrayList11 = new ArrayList();
            Iterator it4 = arrayListListOf.iterator();
            while (it4.hasNext()) {
                Collection<DeclarationDescriptor> contributedDescriptors2 = ((MemberScope) it4.next()).getContributedDescriptors(DescriptorKindFilter.FUNCTIONS, new Function1<Name, Boolean>() { // from class: org.jetbrains.kotlin.resolve.multiplatform.ExpectedActualResolver$findNamesakesFromModule$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(Name it5) {
                        Intrinsics.checkNotNullParameter(it5, "it");
                        return Boolean.valueOf(Intrinsics.areEqual(it5, ((FunctionDescriptor) callableMemberDescriptor).getShortName()));
                    }
                });
                ArrayList arrayList12 = new ArrayList();
                for (Object obj4 : contributedDescriptors2) {
                    if (Intrinsics.areEqual(((DeclarationDescriptor) obj4).getShortName(), ((FunctionDescriptor) callableMemberDescriptor).getShortName())) {
                        arrayList12.add(obj4);
                    }
                }
                ArrayList arrayList13 = new ArrayList();
                for (Object obj5 : arrayList12) {
                    if (obj5 instanceof CallableMemberDescriptor) {
                        arrayList13.add(obj5);
                    }
                }
                CollectionsKt.addAll(arrayList11, arrayList13);
            }
            arrayList = arrayList11;
        }
        return ModuleFilterUtilsKt.applyFilter(arrayList, function1);
    }
}
