package org.jetbrains.kotlin.resolve.multiplatform;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.DeserializedDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: ExpectedActualResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006*\u00020\b2\u0006\u0010\t\u001a\u00020\n\u001a4\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006*\u00020\u00072\u0006\u0010\f\u001a\u00020\n2\u0018\b\u0002\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u000ej\u0002`\u000fH\u0007\u001a4\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006*\u00020\u00072\u0006\u0010\f\u001a\u00020\n2\u0018\b\u0002\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u000ej\u0002`\u000fH\u0007\u001a*\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006*\u00020\u00072\u0018\b\u0002\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u000ej\u0002`\u000f\u001a\u0010\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006*\u00020\b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0013"}, d2 = {"couldHaveASource", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithSource;", "getCouldHaveASource", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithSource;)Z", "findActuals", "", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "inModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "findAnyActualsForExpected", "platformModule", "moduleFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ModuleFilter;", "findCompatibleActualsForExpected", "findCompatibleExpectsForActual", "findExpects", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExpectedActualResolverKt {
    public static final List<MemberDescriptor> findAnyActualsForExpected(MemberDescriptor memberDescriptor, ModuleDescriptor platformModule) {
        Intrinsics.checkNotNullParameter(memberDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(platformModule, "platformModule");
        return findAnyActualsForExpected$default(memberDescriptor, platformModule, null, 2, null);
    }

    public static final List<MemberDescriptor> findCompatibleActualsForExpected(MemberDescriptor memberDescriptor, ModuleDescriptor platformModule) {
        Intrinsics.checkNotNullParameter(memberDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(platformModule, "platformModule");
        return findCompatibleActualsForExpected$default(memberDescriptor, platformModule, null, 2, null);
    }

    public static /* synthetic */ List findCompatibleActualsForExpected$default(MemberDescriptor memberDescriptor, ModuleDescriptor moduleDescriptor, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = ModuleFilterUtilsKt.allModulesProvidingActualsFor(DescriptorUtilsKt.getModule(memberDescriptor), moduleDescriptor);
        }
        return findCompatibleActualsForExpected(memberDescriptor, moduleDescriptor, function1);
    }

    public static final List<MemberDescriptor> findCompatibleActualsForExpected(MemberDescriptor memberDescriptor, ModuleDescriptor platformModule, Function1<? super ModuleDescriptor, Boolean> moduleFilter) {
        Intrinsics.checkNotNullParameter(memberDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(platformModule, "platformModule");
        Intrinsics.checkNotNullParameter(moduleFilter, "moduleFilter");
        Map<ExpectActualCompatibility<MemberDescriptor>, List<MemberDescriptor>> mapFindActualForExpected = ExpectedActualResolver.INSTANCE.findActualForExpected(memberDescriptor, platformModule, moduleFilter);
        List<MemberDescriptor> list = mapFindActualForExpected != null ? mapFindActualForExpected.get(ExpectActualCompatibility.Compatible.INSTANCE) : null;
        return list == null ? CollectionsKt.emptyList() : list;
    }

    public static /* synthetic */ List findAnyActualsForExpected$default(MemberDescriptor memberDescriptor, ModuleDescriptor moduleDescriptor, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = ModuleFilterUtilsKt.allModulesProvidingActualsFor(DescriptorUtilsKt.getModule(memberDescriptor), moduleDescriptor);
        }
        return findAnyActualsForExpected(memberDescriptor, moduleDescriptor, function1);
    }

    public static final List<MemberDescriptor> findAnyActualsForExpected(MemberDescriptor memberDescriptor, ModuleDescriptor platformModule, Function1<? super ModuleDescriptor, Boolean> moduleFilter) {
        Collection<List<MemberDescriptor>> collectionValues;
        List<MemberDescriptor> list;
        Intrinsics.checkNotNullParameter(memberDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(platformModule, "platformModule");
        Intrinsics.checkNotNullParameter(moduleFilter, "moduleFilter");
        Map<ExpectActualCompatibility<MemberDescriptor>, List<MemberDescriptor>> mapFindActualForExpected = ExpectedActualResolver.INSTANCE.findActualForExpected(memberDescriptor, platformModule, moduleFilter);
        if (mapFindActualForExpected == null || (list = mapFindActualForExpected.get(ExpectActualCompatibility.Compatible.INSTANCE)) == null) {
            return (mapFindActualForExpected == null || (collectionValues = mapFindActualForExpected.values()) == null) ? CollectionsKt.emptyList() : CollectionsKt.flatten(collectionValues);
        }
        return list;
    }

    public static /* synthetic */ List findCompatibleExpectsForActual$default(MemberDescriptor memberDescriptor, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = ModuleFilterUtilsKt.allModulesProvidingExpectsFor(DescriptorUtilsKt.getModule(memberDescriptor));
        }
        return findCompatibleExpectsForActual(memberDescriptor, function1);
    }

    public static final List<MemberDescriptor> findCompatibleExpectsForActual(MemberDescriptor memberDescriptor, Function1<? super ModuleDescriptor, Boolean> moduleFilter) {
        Intrinsics.checkNotNullParameter(memberDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(moduleFilter, "moduleFilter");
        Map mapFindExpectedForActual$default = ExpectedActualResolver.findExpectedForActual$default(ExpectedActualResolver.INSTANCE, memberDescriptor, moduleFilter, false, 4, null);
        List<MemberDescriptor> list = mapFindExpectedForActual$default != null ? (List) mapFindExpectedForActual$default.get(ExpectActualCompatibility.Compatible.INSTANCE) : null;
        return list == null ? CollectionsKt.emptyList() : list;
    }

    public static final List<MemberDescriptor> findExpects(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return !(declarationDescriptor instanceof MemberDescriptor) ? CollectionsKt.emptyList() : findCompatibleExpectsForActual$default((MemberDescriptor) declarationDescriptor, null, 1, null);
    }

    public static final List<MemberDescriptor> findActuals(DeclarationDescriptor declarationDescriptor, ModuleDescriptor inModule) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(inModule, "inModule");
        return !(declarationDescriptor instanceof MemberDescriptor) ? CollectionsKt.emptyList() : findCompatibleActualsForExpected$default((MemberDescriptor) declarationDescriptor, inModule, null, 2, null);
    }

    public static final boolean getCouldHaveASource(DeclarationDescriptorWithSource declarationDescriptorWithSource) {
        Intrinsics.checkNotNullParameter(declarationDescriptorWithSource, "<this>");
        return !Intrinsics.areEqual(declarationDescriptorWithSource.getSourceElement().getContainingFile(), SourceFile.NO_SOURCE_FILE) || (declarationDescriptorWithSource instanceof DeserializedDescriptor);
    }
}
