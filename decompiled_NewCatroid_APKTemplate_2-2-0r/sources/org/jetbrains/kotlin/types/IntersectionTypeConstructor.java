package org.jetbrains.kotlin.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.TypeIntersectionScope;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.model.IntersectionTypeConstructorMarker;

/* JADX INFO: compiled from: IntersectionTypeConstructor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\b\u0012\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\bJ\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0096\u0002J\b\u0010\u0016\u001a\u0004\u0018\u00010\u0005J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\n\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0016J\u000e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u001f\u001a\u00020\u0013H\u0016J\b\u0010 \u001a\u00020\u0013H\u0016J\u001c\u0010!\u001a\u00020\"2\u0014\b\u0002\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00150$J\u0010\u0010%\u001a\u00020\u00002\u0006\u0010&\u001a\u00020'H\u0017J\u0010\u0010(\u001a\u00020\u00002\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005J\b\u0010)\u001a\u00020\"H\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0010\u0012\f\u0012\n \r*\u0004\u0018\u00010\u00050\u00050\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/types/IntersectionTypeConstructor;", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "Lorg/jetbrains/kotlin/types/model/IntersectionTypeConstructorMarker;", "typesToIntersect", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "alternative", "(Ljava/util/Collection;Lorg/jetbrains/kotlin/types/KotlinType;)V", "(Ljava/util/Collection;)V", "hashCode", "", "intersectedTypes", "Ljava/util/LinkedHashSet;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "createScopeForKotlinType", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "createType", "Lorg/jetbrains/kotlin/types/SimpleType;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getAlternativeType", "getBuiltIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getDeclarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getSupertypes", "isDenotable", "isFinal", "makeDebugNameForIntersectionType", "", "getProperTypeRelatedToStringify", "Lkotlin/Function1;", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "setAlternative", "toString", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntersectionTypeConstructor implements TypeConstructor, IntersectionTypeConstructorMarker {
    private KotlinType alternative;
    private final int hashCode;
    private final LinkedHashSet<KotlinType> intersectedTypes;

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getDeclarationDescriptor */
    public ClassifierDescriptor mo7434getDeclarationDescriptor() {
        return null;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public boolean isDenotable() {
        return false;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public boolean isFinal() {
        return false;
    }

    public IntersectionTypeConstructor(Collection<? extends KotlinType> typesToIntersect) {
        Intrinsics.checkNotNullParameter(typesToIntersect, "typesToIntersect");
        typesToIntersect.isEmpty();
        LinkedHashSet<KotlinType> linkedHashSet = new LinkedHashSet<>(typesToIntersect);
        this.intersectedTypes = linkedHashSet;
        this.hashCode = linkedHashSet.hashCode();
    }

    private IntersectionTypeConstructor(Collection<? extends KotlinType> collection, KotlinType kotlinType) {
        this(collection);
        this.alternative = kotlinType;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public List<TypeParameterDescriptor> getParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getSupertypes */
    public Collection<KotlinType> mo7438getSupertypes() {
        return this.intersectedTypes;
    }

    public final MemberScope createScopeForKotlinType() {
        return TypeIntersectionScope.INSTANCE.create("member scope for intersection type", this.intersectedTypes);
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getBuiltIns */
    public KotlinBuiltIns getKotlinBuiltIns() {
        KotlinBuiltIns kotlinBuiltIns = this.intersectedTypes.iterator().next().getConstructor().getKotlinBuiltIns();
        Intrinsics.checkNotNullExpressionValue(kotlinBuiltIns, "getBuiltIns(...)");
        return kotlinBuiltIns;
    }

    public String toString() {
        return makeDebugNameForIntersectionType$default(this, null, 1, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ String makeDebugNameForIntersectionType$default(IntersectionTypeConstructor intersectionTypeConstructor, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = new Function1<KotlinType, String>() { // from class: org.jetbrains.kotlin.types.IntersectionTypeConstructor.makeDebugNameForIntersectionType.1
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(KotlinType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.toString();
                }
            };
        }
        return intersectionTypeConstructor.makeDebugNameForIntersectionType(function1);
    }

    public final String makeDebugNameForIntersectionType(final Function1<? super KotlinType, ? extends Object> getProperTypeRelatedToStringify) {
        Intrinsics.checkNotNullParameter(getProperTypeRelatedToStringify, "getProperTypeRelatedToStringify");
        return CollectionsKt.joinToString$default(CollectionsKt.sortedWith(this.intersectedTypes, new Comparator() { // from class: org.jetbrains.kotlin.types.IntersectionTypeConstructor$makeDebugNameForIntersectionType$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                KotlinType kotlinType = (KotlinType) t;
                Function1 function1 = getProperTypeRelatedToStringify;
                Intrinsics.checkNotNull(kotlinType);
                String string = function1.invoke(kotlinType).toString();
                KotlinType kotlinType2 = (KotlinType) t2;
                Function1 function2 = getProperTypeRelatedToStringify;
                Intrinsics.checkNotNull(kotlinType2);
                return ComparisonsKt.compareValues(string, function2.invoke(kotlinType2).toString());
            }
        }), " & ", "{", "}", 0, null, new Function1<KotlinType, CharSequence>() { // from class: org.jetbrains.kotlin.types.IntersectionTypeConstructor.makeDebugNameForIntersectionType.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(KotlinType kotlinType) {
                Function1<KotlinType, Object> function1 = getProperTypeRelatedToStringify;
                Intrinsics.checkNotNull(kotlinType);
                return function1.invoke(kotlinType).toString();
            }
        }, 24, null);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof IntersectionTypeConstructor) {
            return Intrinsics.areEqual(this.intersectedTypes, ((IntersectionTypeConstructor) other).intersectedTypes);
        }
        return false;
    }

    public final SimpleType createType() {
        return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(TypeAttributes.INSTANCE.getEmpty(), this, CollectionsKt.emptyList(), false, createScopeForKotlinType(), new Function1<KotlinTypeRefiner, SimpleType>() { // from class: org.jetbrains.kotlin.types.IntersectionTypeConstructor.createType.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SimpleType invoke(KotlinTypeRefiner kotlinTypeRefiner) {
                Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
                return IntersectionTypeConstructor.this.refine(kotlinTypeRefiner).createType();
            }
        });
    }

    /* JADX INFO: renamed from: hashCode, reason: from getter */
    public int getHashCode() {
        return this.hashCode;
    }

    public final IntersectionTypeConstructor setAlternative(KotlinType alternative) {
        return new IntersectionTypeConstructor(this.intersectedTypes, alternative);
    }

    /* JADX INFO: renamed from: getAlternativeType, reason: from getter */
    public final KotlinType getAlternative() {
        return this.alternative;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public IntersectionTypeConstructor refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        Collection<KotlinType> collectionMo7438getSupertypes = mo7438getSupertypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionMo7438getSupertypes, 10));
        Iterator<T> it = collectionMo7438getSupertypes.iterator();
        boolean z = false;
        while (it.hasNext()) {
            arrayList.add(((KotlinType) it.next()).refine(kotlinTypeRefiner));
            z = true;
        }
        ArrayList arrayList2 = arrayList;
        IntersectionTypeConstructor alternative = null;
        if (z) {
            KotlinType alternative2 = getAlternative();
            alternative = new IntersectionTypeConstructor(arrayList2).setAlternative(alternative2 != null ? alternative2.refine(kotlinTypeRefiner) : null);
        }
        return alternative == null ? this : alternative;
    }
}
