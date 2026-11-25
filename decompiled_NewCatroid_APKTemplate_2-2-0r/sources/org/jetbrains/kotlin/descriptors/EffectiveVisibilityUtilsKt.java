package org.jetbrains.kotlin.descriptors;

import com.reandroid.apk.ApkUtil;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;

/* JADX INFO: compiled from: EffectiveVisibilityUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0002\u001a\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005*\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\u0012\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005*\u00020\nH\u0002\u001a(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005*\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u00052\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\u0014\u0010\f\u001a\u00020\u0001*\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u001a\"\u0010\f\u001a\u00020\u0001*\u00020\r2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u00052\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u001a\u001e\u0010\f\u001a\u00020\u0001*\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u001a\u001c\u0010\f\u001a\u00020\u0001*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u001a\u001e\u0010\u0016\u001a\u00020\u0001*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0002\u001a\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0006*\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0018\u001a\u00020\u0001H\u0002\u001a\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u0006*\u00020\n2\u0006\u0010\u0018\u001a\u00020\u0001\u001a\n\u0010\u001a\u001a\u00020\u0013*\u00020\u0001¨\u0006\u001b"}, d2 = {"lowerBound", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "first", "second", "dependentDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DescriptorWithRelation;", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "ownRelation", "Lorg/jetbrains/kotlin/descriptors/RelationToType;", "Lorg/jetbrains/kotlin/types/KotlinType;", SpecTypePair.NAME_types, "effectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "checkPublishedApi", "", "classes", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "forVisibility", "leastPermissive", ApkUtil.DEF_MODULE_NAME, "leastPermissiveDescriptor", "toDescriptorVisibility", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EffectiveVisibilityUtilsKt {

    /* JADX INFO: compiled from: EffectiveVisibilityUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EffectiveVisibility.Permissiveness.values().length];
            try {
                iArr[EffectiveVisibility.Permissiveness.LESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[EffectiveVisibility.Permissiveness.UNKNOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final DescriptorVisibility toDescriptorVisibility(EffectiveVisibility effectiveVisibility) {
        Intrinsics.checkNotNullParameter(effectiveVisibility, "<this>");
        DescriptorVisibility descriptorVisibility = DescriptorVisibilities.toDescriptorVisibility(effectiveVisibility.toVisibility());
        Intrinsics.checkNotNullExpressionValue(descriptorVisibility, "toDescriptorVisibility(...)");
        return descriptorVisibility;
    }

    public static /* synthetic */ EffectiveVisibility effectiveVisibility$default(DescriptorVisibility descriptorVisibility, DeclarationDescriptor declarationDescriptor, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return effectiveVisibility(descriptorVisibility, declarationDescriptor, z);
    }

    public static final EffectiveVisibility effectiveVisibility(DescriptorVisibility descriptorVisibility, DeclarationDescriptor descriptor, boolean z) {
        Intrinsics.checkNotNullParameter(descriptorVisibility, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        EffectiveVisibility effectiveVisibilityCustomEffectiveVisibility = descriptorVisibility.customEffectiveVisibility();
        return effectiveVisibilityCustomEffectiveVisibility == null ? forVisibility(descriptorVisibility.normalize(), descriptor, z) : effectiveVisibilityCustomEffectiveVisibility;
    }

    private static final EffectiveVisibility forVisibility(DescriptorVisibility descriptorVisibility, DeclarationDescriptor declarationDescriptor, boolean z) {
        SimpleType defaultType;
        if (Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.PRIVATE_TO_THIS) ? true : Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.INVISIBLE_FAKE)) {
            return EffectiveVisibility.PrivateInClass.INSTANCE;
        }
        if (Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.PRIVATE)) {
            return ((declarationDescriptor instanceof ClassDescriptor) && (((ClassDescriptor) declarationDescriptor).getContainingModule() instanceof PackageFragmentDescriptor)) ? EffectiveVisibility.PrivateInFile.INSTANCE : EffectiveVisibility.PrivateInClass.INSTANCE;
        }
        if (Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.PROTECTED)) {
            DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingModule();
            TypeConstructor constructor = null;
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if (classDescriptor != null && (defaultType = classDescriptor.getDefaultType()) != null) {
                constructor = defaultType.getConstructor();
            }
            return new EffectiveVisibility.Protected(constructor);
        }
        if (Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.INTERNAL)) {
            return (z && DescriptorUtilsKt.isPublishedApi(declarationDescriptor)) ? EffectiveVisibility.Public.INSTANCE : EffectiveVisibility.Internal.INSTANCE;
        }
        if (Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.PUBLIC)) {
            return EffectiveVisibility.Public.INSTANCE;
        }
        if (Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.LOCAL)) {
            return EffectiveVisibility.Local.INSTANCE;
        }
        throw new AssertionError("Visibility " + descriptorVisibility.getName() + " is not allowed in forVisibility");
    }

    private static final Set<DescriptorWithRelation> dependentDescriptors(ClassifierDescriptor classifierDescriptor, RelationToType relationToType) {
        Set<DescriptorWithRelation> setEmptySet;
        Set of = SetsKt.setOf(new DescriptorWithRelation(classifierDescriptor, relationToType));
        DeclarationDescriptor containingDeclaration = classifierDescriptor.getContainingModule();
        ClassifierDescriptor classifierDescriptor2 = containingDeclaration instanceof ClassifierDescriptor ? (ClassifierDescriptor) containingDeclaration : null;
        if (classifierDescriptor2 == null || (setEmptySet = dependentDescriptors(classifierDescriptor2, relationToType.containerRelation())) == null) {
            setEmptySet = SetsKt.emptySet();
        }
        return SetsKt.plus(of, (Iterable) setEmptySet);
    }

    public static final EffectiveVisibility effectiveVisibility(ClassDescriptor classDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        return effectiveVisibility(classDescriptor, (Set<? extends ClassDescriptor>) SetsKt.emptySet(), z);
    }

    public static /* synthetic */ EffectiveVisibility effectiveVisibility$default(ClassDescriptor classDescriptor, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return effectiveVisibility(classDescriptor, z);
    }

    private static final EffectiveVisibility effectiveVisibility(ClassDescriptor classDescriptor, Set<? extends ClassDescriptor> set, boolean z) {
        EffectiveVisibility.Public publicEffectiveVisibility;
        if (set.contains(classDescriptor)) {
            return EffectiveVisibility.Public.INSTANCE;
        }
        DeclarationDescriptor containingDeclaration = classDescriptor.getContainingModule();
        ClassDescriptor classDescriptor2 = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        DescriptorVisibility visibility = classDescriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        EffectiveVisibility effectiveVisibility = effectiveVisibility(visibility, classDescriptor, z);
        if (classDescriptor2 == null || (publicEffectiveVisibility = effectiveVisibility(classDescriptor2, (Set<? extends ClassDescriptor>) SetsKt.plus(set, classDescriptor), z)) == null) {
            publicEffectiveVisibility = EffectiveVisibility.Public.INSTANCE;
        }
        return lowerBound(effectiveVisibility, publicEffectiveVisibility);
    }

    private static final Set<DescriptorWithRelation> dependentDescriptors(KotlinType kotlinType) {
        return dependentDescriptors(kotlinType, SetsKt.emptySet(), RelationToType.CONSTRUCTOR);
    }

    private static final Set<DescriptorWithRelation> dependentDescriptors(KotlinType kotlinType, Set<? extends KotlinType> set, RelationToType relationToType) {
        Set<DescriptorWithRelation> setEmptySet;
        if (set.contains(kotlinType)) {
            return SetsKt.emptySet();
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().getThis$0();
        if (classifierDescriptorMo7434getDeclarationDescriptor == null || (setEmptySet = dependentDescriptors(classifierDescriptorMo7434getDeclarationDescriptor, relationToType)) == null) {
            setEmptySet = SetsKt.emptySet();
        }
        List<TypeProjection> arguments = kotlinType.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            KotlinType type = ((TypeProjection) it.next()).getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            arrayList.add(dependentDescriptors(type, SetsKt.plus(set, kotlinType), RelationToType.ARGUMENT));
        }
        return SetsKt.plus((Set) setEmptySet, (Iterable) CollectionsKt.flatten(arrayList));
    }

    private static final DescriptorWithRelation leastPermissive(Set<DescriptorWithRelation> set, EffectiveVisibility effectiveVisibility) {
        for (DescriptorWithRelation descriptorWithRelation : set) {
            int i = WhenMappings.$EnumSwitchMapping$0[descriptorWithRelation.effectiveVisibility().relation(effectiveVisibility, SimpleClassicTypeSystemContext.INSTANCE).ordinal()];
            if (i == 1 || i == 2) {
                return descriptorWithRelation;
            }
        }
        return null;
    }

    public static final DescriptorWithRelation leastPermissiveDescriptor(KotlinType kotlinType, EffectiveVisibility base) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        return leastPermissive(dependentDescriptors(kotlinType), base);
    }

    public static /* synthetic */ EffectiveVisibility effectiveVisibility$default(DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility, DescriptorVisibility descriptorVisibility, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            descriptorVisibility = declarationDescriptorWithVisibility.getVisibility();
            Intrinsics.checkNotNullExpressionValue(descriptorVisibility, "getVisibility(...)");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return effectiveVisibility(declarationDescriptorWithVisibility, descriptorVisibility, z);
    }

    public static final EffectiveVisibility effectiveVisibility(DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility, DescriptorVisibility visibility, boolean z) {
        EffectiveVisibility.Public publicEffectiveVisibility;
        Intrinsics.checkNotNullParameter(declarationDescriptorWithVisibility, "<this>");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        EffectiveVisibility effectiveVisibility = effectiveVisibility(visibility, declarationDescriptorWithVisibility, z);
        DeclarationDescriptor containingDeclaration = declarationDescriptorWithVisibility.getContainingModule();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor == null || (publicEffectiveVisibility = effectiveVisibility(classDescriptor, z)) == null) {
            publicEffectiveVisibility = EffectiveVisibility.Public.INSTANCE;
        }
        return lowerBound(effectiveVisibility, publicEffectiveVisibility);
    }

    private static final EffectiveVisibility lowerBound(EffectiveVisibility effectiveVisibility, EffectiveVisibility effectiveVisibility2) {
        return effectiveVisibility.lowerBound(effectiveVisibility2, SimpleClassicTypeSystemContext.INSTANCE);
    }
}
