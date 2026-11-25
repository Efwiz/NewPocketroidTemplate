package org.jetbrains.kotlin.backend.common.serialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderKt;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.ir.util.DescriptorByIdSignatureFinder;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.KotlinMangler;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;

/* JADX INFO: compiled from: DescriptorByIdSignatureFinderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001'B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ'\u0010\t\u001a\u0004\u0018\u00010\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002¢\u0006\u0002\u0010\u000fJ\u0012\u0010\u0010\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J$\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0011\u001a\u00020\u001eH\u0002J\u0012\u0010\u001f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0011\u001a\u00020 H\u0002J\u0012\u0010!\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0011\u001a\u00020\"H\u0002J\"\u0010#\u001a\b\u0012\u0004\u0012\u00020\n0\f*\u00020$2\u0006\u0010%\u001a\u00020\u001a2\u0006\u0010&\u001a\u00020\u0014H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/DescriptorByIdSignatureFinderImpl;", "Lorg/jetbrains/kotlin/ir/util/DescriptorByIdSignatureFinder;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "mangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;", "lookupMode", "Lorg/jetbrains/kotlin/backend/common/serialization/DescriptorByIdSignatureFinderImpl$LookupMode;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;Lorg/jetbrains/kotlin/backend/common/serialization/DescriptorByIdSignatureFinderImpl$LookupMode;)V", "findDescriptorByHash", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "candidates", "", "id", "", "(Ljava/util/Collection;Ljava/lang/Long;)Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "findDescriptorBySignature", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "isConstructorName", "", "n", "Lorg/jetbrains/kotlin/name/Name;", "lookupTopLevelDescriptors", "nameSegments", "", "", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "resolveAccessorSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$AccessorSignature;", "resolveCommonSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;", "resolveCompositeSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CompositeSignature;", "loadDescriptors", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "name", "isLeaf", "LookupMode", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DescriptorByIdSignatureFinderImpl implements DescriptorByIdSignatureFinder {
    private final LookupMode lookupMode;
    private final KotlinMangler.DescriptorMangler mangler;
    private final ModuleDescriptor moduleDescriptor;

    /* JADX INFO: compiled from: DescriptorByIdSignatureFinderImpl.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/DescriptorByIdSignatureFinderImpl$LookupMode;", "", "(Ljava/lang/String;I)V", "MODULE_WITH_DEPENDENCIES", "MODULE_ONLY", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum LookupMode {
        MODULE_WITH_DEPENDENCIES,
        MODULE_ONLY;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<LookupMode> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: DescriptorByIdSignatureFinderImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LookupMode.values().length];
            try {
                iArr[LookupMode.MODULE_WITH_DEPENDENCIES.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LookupMode.MODULE_ONLY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DescriptorByIdSignatureFinderImpl(ModuleDescriptor moduleDescriptor, KotlinMangler.DescriptorMangler mangler, LookupMode lookupMode) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(mangler, "mangler");
        Intrinsics.checkNotNullParameter(lookupMode, "lookupMode");
        this.moduleDescriptor = moduleDescriptor;
        this.mangler = mangler;
        this.lookupMode = lookupMode;
        if (lookupMode == LookupMode.MODULE_ONLY) {
            boolean z = moduleDescriptor instanceof ModuleDescriptorImpl;
        }
    }

    public /* synthetic */ DescriptorByIdSignatureFinderImpl(ModuleDescriptor moduleDescriptor, KotlinMangler.DescriptorMangler descriptorMangler, LookupMode lookupMode, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(moduleDescriptor, descriptorMangler, (i & 4) != 0 ? LookupMode.MODULE_WITH_DEPENDENCIES : lookupMode);
    }

    @Override // org.jetbrains.kotlin.ir.util.DescriptorByIdSignatureFinder
    public DeclarationDescriptor findDescriptorBySignature(IdSignature signature) {
        DeclarationDescriptor declarationDescriptorResolveCompositeSignature;
        Intrinsics.checkNotNullParameter(signature, "signature");
        if (signature instanceof IdSignature.AccessorSignature) {
            declarationDescriptorResolveCompositeSignature = resolveAccessorSignature((IdSignature.AccessorSignature) signature);
        } else if (signature instanceof IdSignature.CommonSignature) {
            declarationDescriptorResolveCompositeSignature = resolveCommonSignature((IdSignature.CommonSignature) signature);
        } else {
            if (!(signature instanceof IdSignature.CompositeSignature)) {
                throw new IllegalStateException(("Unexpected signature kind: " + signature).toString());
            }
            declarationDescriptorResolveCompositeSignature = resolveCompositeSignature((IdSignature.CompositeSignature) signature);
        }
        if (declarationDescriptorResolveCompositeSignature != null) {
            if (declarationDescriptorResolveCompositeSignature instanceof PropertyAccessorDescriptor) {
                PropertyAccessorDescriptor propertyAccessorDescriptor = (PropertyAccessorDescriptor) declarationDescriptorResolveCompositeSignature;
                if (!Intrinsics.areEqual(propertyAccessorDescriptor.getVisibilityImpl(), DescriptorVisibilities.INVISIBLE_FAKE) && !Intrinsics.areEqual(propertyAccessorDescriptor.getCorrespondingProperty().getVisibilityImpl(), DescriptorVisibilities.INVISIBLE_FAKE)) {
                    return declarationDescriptorResolveCompositeSignature;
                }
            } else if (!(declarationDescriptorResolveCompositeSignature instanceof DeclarationDescriptorWithVisibility) || !Intrinsics.areEqual(((DeclarationDescriptorWithVisibility) declarationDescriptorResolveCompositeSignature).getVisibilityImpl(), DescriptorVisibilities.INVISIBLE_FAKE)) {
                return declarationDescriptorResolveCompositeSignature;
            }
        }
        return null;
    }

    private final DeclarationDescriptor resolveCompositeSignature(IdSignature.CompositeSignature signature) {
        DeclarationDescriptor declarationDescriptorFindDescriptorBySignature = findDescriptorBySignature(signature.nearestPublicSig());
        if (declarationDescriptorFindDescriptorBySignature == null) {
            return null;
        }
        IdSignature inner = signature.getInner();
        IdSignature.LocalSignature localSignature = inner instanceof IdSignature.LocalSignature ? (IdSignature.LocalSignature) inner : null;
        if (localSignature != null && resolveCompositeSignature$lambda$3$isTypeParameterSig(localSignature.getLocalFqn())) {
            int iIndex = localSignature.index();
            if (declarationDescriptorFindDescriptorBySignature instanceof CallableDescriptor) {
                return ((CallableDescriptor) declarationDescriptorFindDescriptorBySignature).getTypeParameters().get(iIndex);
            }
            if (declarationDescriptorFindDescriptorBySignature instanceof ClassifierDescriptorWithTypeParameters) {
                return ((ClassifierDescriptorWithTypeParameters) declarationDescriptorFindDescriptorBySignature).getDeclaredTypeParameters().get(iIndex);
            }
        }
        return declarationDescriptorFindDescriptorBySignature;
    }

    private static final boolean resolveCompositeSignature$lambda$3$isTypeParameterSig(String str) {
        return Intrinsics.areEqual(str, MangleConstant.TYPE_PARAMETER_MARKER_NAME) || Intrinsics.areEqual(str, MangleConstant.TYPE_PARAMETER_MARKER_NAME_SETTER);
    }

    private final DeclarationDescriptor resolveAccessorSignature(IdSignature.AccessorSignature signature) {
        Name name2;
        Name name3;
        DeclarationDescriptor declarationDescriptorFindDescriptorBySignature = findDescriptorBySignature(signature.getPropertySignature());
        PropertyDescriptor propertyDescriptor = declarationDescriptorFindDescriptorBySignature instanceof PropertyDescriptor ? (PropertyDescriptor) declarationDescriptorFindDescriptorBySignature : null;
        if (propertyDescriptor == null) {
            return null;
        }
        String shortName = signature.getAccessorSignature().getShortName();
        PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
        if (Intrinsics.areEqual(shortName, (getter == null || (name3 = getter.getShortName()) == null) ? null : name3.asString())) {
            return propertyDescriptor.getGetter();
        }
        PropertySetterDescriptor setter = propertyDescriptor.getSetter();
        if (Intrinsics.areEqual(shortName, (setter == null || (name2 = setter.getShortName()) == null) ? null : name2.asString())) {
            return propertyDescriptor.getSetter();
        }
        return null;
    }

    private final boolean isConstructorName(Name n) {
        return n.isSpecial() && Intrinsics.areEqual(n.asString(), "<init>");
    }

    private final Collection<DeclarationDescriptor> loadDescriptors(MemberScope memberScope, String str, boolean z) {
        Name nameGuessByFirstCharacter = Name.guessByFirstCharacter(str);
        Intrinsics.checkNotNullExpressionValue(nameGuessByFirstCharacter, "guessByFirstCharacter(...)");
        ClassifierDescriptor contributedClassifier = memberScope.mo7465getContributedClassifier(nameGuessByFirstCharacter, NoLookupLocation.FROM_BACKEND);
        if (!z) {
            return CollectionsKt.listOfNotNull(contributedClassifier);
        }
        ArrayList arrayList = new ArrayList();
        if (contributedClassifier != null) {
            arrayList.add(contributedClassifier);
        }
        arrayList.addAll(memberScope.getContributedFunctions(nameGuessByFirstCharacter, NoLookupLocation.FROM_BACKEND));
        arrayList.addAll(memberScope.getContributedVariables(nameGuessByFirstCharacter, NoLookupLocation.FROM_BACKEND));
        return arrayList;
    }

    private final Collection<DeclarationDescriptor> lookupTopLevelDescriptors(List<String> nameSegments, FqName packageFqName) {
        String str = nameSegments.get(0);
        boolean z = nameSegments.size() == 1;
        int i = WhenMappings.$EnumSwitchMapping$0[this.lookupMode.ordinal()];
        if (i == 1) {
            return loadDescriptors(this.moduleDescriptor.getPackage(packageFqName).getMemberScope(), str, z);
        }
        if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        ModuleDescriptor moduleDescriptor = this.moduleDescriptor;
        Intrinsics.checkNotNull(moduleDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl");
        List<PackageFragmentDescriptor> listPackageFragments = PackageFragmentProviderKt.packageFragments(((ModuleDescriptorImpl) moduleDescriptor).getPackageFragmentProviderForModuleContentWithoutDependencies(), packageFqName);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listPackageFragments.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, loadDescriptors(((PackageFragmentDescriptor) it.next()).getScope(), str, z));
        }
        return arrayList;
    }

    private final DeclarationDescriptor resolveCommonSignature(IdSignature.CommonSignature signature) {
        List listEmptyList;
        List<String> nameSegments = signature.getNameSegments();
        ArrayList arrayListLookupTopLevelDescriptors = lookupTopLevelDescriptors(nameSegments, signature.getFqName());
        if (arrayListLookupTopLevelDescriptors.isEmpty()) {
            return null;
        }
        int lastIndex = CollectionsKt.getLastIndex(nameSegments);
        int size = nameSegments.size();
        int i = 1;
        while (i < size) {
            Name nameGuessByFirstCharacter = Name.guessByFirstCharacter(nameSegments.get(i));
            Intrinsics.checkNotNullExpressionValue(nameGuessByFirstCharacter, "guessByFirstCharacter(...)");
            ArrayList arrayList = new ArrayList();
            for (DeclarationDescriptor declarationDescriptor : arrayListLookupTopLevelDescriptors) {
                ClassDescriptor classDescriptor = declarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptor : null;
                if (classDescriptor == null) {
                    listEmptyList = CollectionsKt.emptyList();
                } else {
                    boolean z = i == lastIndex;
                    MemberScope unsubstitutedMemberScope = classDescriptor.getUnsubstitutedMemberScope();
                    Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
                    ClassifierDescriptor contributedClassifier = unsubstitutedMemberScope.mo7465getContributedClassifier(nameGuessByFirstCharacter, NoLookupLocation.FROM_BACKEND);
                    if (z) {
                        ArrayList arrayList2 = new ArrayList();
                        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, contributedClassifier);
                        if (signature.getId() != null) {
                            if (isConstructorName(nameGuessByFirstCharacter)) {
                                Collection<ClassConstructorDescriptor> constructors = classDescriptor.getConstructors();
                                Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
                                arrayList2.addAll(constructors);
                            }
                            arrayList2.addAll(unsubstitutedMemberScope.getContributedFunctions(nameGuessByFirstCharacter, NoLookupLocation.FROM_BACKEND));
                            arrayList2.addAll(unsubstitutedMemberScope.getContributedVariables(nameGuessByFirstCharacter, NoLookupLocation.FROM_BACKEND));
                        }
                        MemberScope staticScope = classDescriptor.getStaticScope();
                        Intrinsics.checkNotNullExpressionValue(staticScope, "getStaticScope(...)");
                        Collection contributedDescriptors$default = ResolutionScope.DefaultImpls.getContributedDescriptors$default(staticScope, null, null, 3, null);
                        ArrayList arrayList3 = new ArrayList();
                        for (Object obj : contributedDescriptors$default) {
                            if (Intrinsics.areEqual(((DeclarationDescriptor) obj).getShortName(), nameGuessByFirstCharacter)) {
                                arrayList3.add(obj);
                            }
                        }
                        arrayList2.addAll(arrayList3);
                        listEmptyList = arrayList2;
                    } else if (contributedClassifier == null || (listEmptyList = CollectionsKt.listOf(contributedClassifier)) == null) {
                        listEmptyList = CollectionsKt.emptyList();
                    }
                }
                CollectionsKt.addAll(arrayList, listEmptyList);
            }
            arrayListLookupTopLevelDescriptors = arrayList;
            i++;
        }
        return findDescriptorByHash(arrayListLookupTopLevelDescriptors, signature.getId());
    }

    private final DeclarationDescriptor findDescriptorByHash(Collection<? extends DeclarationDescriptor> candidates, Long id2) {
        Object next;
        Iterator<T> it = candidates.iterator();
        while (it.hasNext()) {
            next = it.next();
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) next;
            if (id2 == null) {
                if ((declarationDescriptor instanceof ClassDescriptor) || (declarationDescriptor instanceof TypeAliasDescriptor)) {
                    return (DeclarationDescriptor) next;
                }
            } else if (this.mangler.signatureMangle(declarationDescriptor, false) == id2.longValue()) {
                return (DeclarationDescriptor) next;
            }
        }
        next = null;
        return (DeclarationDescriptor) next;
    }
}
