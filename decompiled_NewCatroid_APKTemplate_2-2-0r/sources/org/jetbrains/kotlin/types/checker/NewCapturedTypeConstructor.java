package org.jetbrains.kotlin.types.checker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: NewCapturedType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B)\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\bB=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\b\u0002\u0010\t\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0018\u00010\n\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0000\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rJ\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0096\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\n\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\f0\u0005H\u0016J\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016J\b\u0010!\u001a\u00020\"H\u0016J\u0014\u0010#\u001a\u00020$2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\b\u0010%\u001a\u00020\u0018H\u0016J\b\u0010&\u001a\u00020\u0018H\u0016J\u0010\u0010'\u001a\u00020\u00002\u0006\u0010(\u001a\u00020)H\u0017J\b\u0010*\u001a\u00020+H\u0016R#\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\t\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/NewCapturedTypeConstructor;", "Lorg/jetbrains/kotlin/resolve/calls/inference/CapturedTypeConstructor;", "projection", "Lorg/jetbrains/kotlin/types/TypeProjection;", "supertypes", "", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "original", "(Lorg/jetbrains/kotlin/types/TypeProjection;Ljava/util/List;Lorg/jetbrains/kotlin/types/checker/NewCapturedTypeConstructor;)V", "supertypesComputation", "Lkotlin/Function0;", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "(Lorg/jetbrains/kotlin/types/TypeProjection;Lkotlin/jvm/functions/Function0;Lorg/jetbrains/kotlin/types/checker/NewCapturedTypeConstructor;Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;)V", "_supertypes", "get_supertypes", "()Ljava/util/List;", "_supertypes$delegate", "Lkotlin/Lazy;", "getProjection", "()Lorg/jetbrains/kotlin/types/TypeProjection;", "getTypeParameter", "()Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getBuiltIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getDeclarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getParameters", "getSupertypes", "hashCode", "", "initializeSupertypes", "", "isDenotable", "isFinal", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewCapturedTypeConstructor implements CapturedTypeConstructor {

    /* JADX INFO: renamed from: _supertypes$delegate, reason: from kotlin metadata */
    private final Lazy _supertypes;
    private final NewCapturedTypeConstructor original;
    private final TypeProjection projection;
    private Function0<? extends List<? extends UnwrappedType>> supertypesComputation;
    private final TypeParameterDescriptor typeParameter;

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getDeclarationDescriptor */
    public ClassifierDescriptor getThis$0() {
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

    public NewCapturedTypeConstructor(TypeProjection projection, Function0<? extends List<? extends UnwrappedType>> function0, NewCapturedTypeConstructor newCapturedTypeConstructor, TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkNotNullParameter(projection, "projection");
        this.projection = projection;
        this.supertypesComputation = function0;
        this.original = newCapturedTypeConstructor;
        this.typeParameter = typeParameterDescriptor;
        this._supertypes = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends UnwrappedType>>() { // from class: org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor$_supertypes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends UnwrappedType> invoke() {
                Function0 function1 = this.this$0.supertypesComputation;
                if (function1 != null) {
                    return (List) function1.invoke();
                }
                return null;
            }
        });
    }

    public /* synthetic */ NewCapturedTypeConstructor(TypeProjection typeProjection, Function0 function0, NewCapturedTypeConstructor newCapturedTypeConstructor, TypeParameterDescriptor typeParameterDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeProjection, (i & 2) != 0 ? null : function0, (i & 4) != 0 ? null : newCapturedTypeConstructor, (i & 8) != 0 ? null : typeParameterDescriptor);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructor
    public TypeProjection getProjection() {
        return this.projection;
    }

    public final TypeParameterDescriptor getTypeParameter() {
        return this.typeParameter;
    }

    public /* synthetic */ NewCapturedTypeConstructor(TypeProjection typeProjection, List list, NewCapturedTypeConstructor newCapturedTypeConstructor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeProjection, list, (i & 4) != 0 ? null : newCapturedTypeConstructor);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NewCapturedTypeConstructor(TypeProjection projection, final List<? extends UnwrappedType> supertypes, NewCapturedTypeConstructor newCapturedTypeConstructor) {
        this(projection, new Function0<List<? extends UnwrappedType>>() { // from class: org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends UnwrappedType> invoke() {
                return supertypes;
            }
        }, newCapturedTypeConstructor, null, 8, null);
        Intrinsics.checkNotNullParameter(projection, "projection");
        Intrinsics.checkNotNullParameter(supertypes, "supertypes");
    }

    private final List<UnwrappedType> get_supertypes() {
        return (List) this._supertypes.getValue();
    }

    public final void initializeSupertypes(final List<? extends UnwrappedType> supertypes) {
        Intrinsics.checkNotNullParameter(supertypes, "supertypes");
        this.supertypesComputation = new Function0<List<? extends UnwrappedType>>() { // from class: org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor.initializeSupertypes.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends UnwrappedType> invoke() {
                return supertypes;
            }
        };
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getSupertypes */
    public List<UnwrappedType> mo7438getSupertypes() {
        List<UnwrappedType> list = get_supertypes();
        return list == null ? CollectionsKt.emptyList() : list;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public List<TypeParameterDescriptor> getParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public KotlinBuiltIns getBuiltIns() {
        KotlinType type = getProjection().getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return TypeUtilsKt.getBuiltIns(type);
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public NewCapturedTypeConstructor refine(final KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        TypeProjection typeProjectionRefine = getProjection().refine(kotlinTypeRefiner);
        Intrinsics.checkNotNullExpressionValue(typeProjectionRefine, "refine(...)");
        Function0<List<? extends UnwrappedType>> function0 = this.supertypesComputation != null ? new Function0<List<? extends UnwrappedType>>() { // from class: org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor$refine$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends UnwrappedType> invoke() {
                List<UnwrappedType> listMo7438getSupertypes = this.this$0.mo7438getSupertypes();
                KotlinTypeRefiner kotlinTypeRefiner2 = kotlinTypeRefiner;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listMo7438getSupertypes, 10));
                Iterator<T> it = listMo7438getSupertypes.iterator();
                while (it.hasNext()) {
                    arrayList.add(((UnwrappedType) it.next()).refine(kotlinTypeRefiner2));
                }
                return arrayList;
            }
        } : null;
        NewCapturedTypeConstructor newCapturedTypeConstructor = this.original;
        if (newCapturedTypeConstructor == null) {
            newCapturedTypeConstructor = this;
        }
        return new NewCapturedTypeConstructor(typeProjectionRefine, function0, newCapturedTypeConstructor, this.typeParameter);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor");
        NewCapturedTypeConstructor newCapturedTypeConstructor = (NewCapturedTypeConstructor) other;
        NewCapturedTypeConstructor newCapturedTypeConstructor2 = this.original;
        if (newCapturedTypeConstructor2 == null) {
            newCapturedTypeConstructor2 = this;
        }
        NewCapturedTypeConstructor newCapturedTypeConstructor3 = newCapturedTypeConstructor.original;
        if (newCapturedTypeConstructor3 != null) {
            newCapturedTypeConstructor = newCapturedTypeConstructor3;
        }
        return newCapturedTypeConstructor2 == newCapturedTypeConstructor;
    }

    public int hashCode() {
        NewCapturedTypeConstructor newCapturedTypeConstructor = this.original;
        return newCapturedTypeConstructor != null ? newCapturedTypeConstructor.hashCode() : super.hashCode();
    }

    public String toString() {
        return "CapturedType(" + getProjection() + ')';
    }
}
