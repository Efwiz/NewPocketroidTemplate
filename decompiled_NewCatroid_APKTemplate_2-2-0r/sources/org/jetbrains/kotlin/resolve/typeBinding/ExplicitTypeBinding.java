package org.jetbrains.kotlin.resolve.typeBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;

/* JADX INFO: compiled from: TypeBinding.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\"\u0010\u000b\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\r0\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0010R\u0014\u0010\u0005\u001a\u00020\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/typeBinding/ExplicitTypeBinding;", "Lorg/jetbrains/kotlin/resolve/typeBinding/TypeBinding;", "Lorg/jetbrains/kotlin/psi/KtTypeElement;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "psiElement", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "isArgumentFromQualifier", "", "(Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/psi/KtTypeElement;Lorg/jetbrains/kotlin/types/KotlinType;Z)V", "arguments", "", "Lorg/jetbrains/kotlin/resolve/typeBinding/TypeArgumentBinding;", "getArguments", "()Ljava/util/List;", "()Z", "isInAbbreviation", "getPsiElement", "()Lorg/jetbrains/kotlin/psi/KtTypeElement;", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ExplicitTypeBinding implements TypeBinding<KtTypeElement> {
    private final boolean isArgumentFromQualifier;
    private final KtTypeElement psiElement;
    private final BindingContext trace;
    private final KotlinType type;

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeBinding
    public boolean isInAbbreviation() {
        return false;
    }

    public ExplicitTypeBinding(BindingContext trace, KtTypeElement psiElement, KotlinType type, boolean z) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        Intrinsics.checkNotNullParameter(type, "type");
        this.trace = trace;
        this.psiElement = psiElement;
        this.type = type;
        this.isArgumentFromQualifier = z;
    }

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeBinding
    public KtTypeElement getPsiElement() {
        return this.psiElement;
    }

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeBinding
    public KotlinType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeBinding
    /* JADX INFO: renamed from: isArgumentFromQualifier, reason: from getter */
    public boolean getIsArgumentFromQualifier() {
        return this.isArgumentFromQualifier;
    }

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeBinding
    public List<TypeArgumentBinding<KtTypeElement>> getArguments() {
        KtTypeElement typeElement;
        KtUserType qualifier;
        List<KtTypeReference> typeArgumentsAsTypes = getPsiElement().getTypeArgumentsAsTypes();
        Intrinsics.checkNotNullExpressionValue(typeArgumentsAsTypes, "getTypeArgumentsAsTypes(...)");
        List mutableList = CollectionsKt.toMutableList((Collection) typeArgumentsAsTypes);
        int size = mutableList.size();
        KtUserType psiElement = getPsiElement();
        while ((psiElement instanceof KtUserType) && (qualifier = ((KtUserType) psiElement).getQualifier()) != null) {
            psiElement = qualifier;
            List<KtTypeReference> typeArgumentsAsTypes2 = psiElement.getTypeArgumentsAsTypes();
            Intrinsics.checkNotNullExpressionValue(typeArgumentsAsTypes2, "getTypeArgumentsAsTypes(...)");
            CollectionsKt.addAll(mutableList, typeArgumentsAsTypes2);
        }
        SpecialTypesKt.getAbbreviatedType(getType());
        boolean z = KotlinTypeKt.isError(getType()) || !(mutableList.size() == getType().getArguments().size() && mutableList.size() == getType().getConstructor().getParameters().size());
        IntRange indices = CollectionsKt.getIndices(mutableList);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(indices, 10));
        Iterator<Integer> it = indices.iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            KtTypeReference ktTypeReference = (KtTypeReference) mutableList.get(iNextInt);
            TypeArgumentBindingImpl typeArgumentBindingImpl = null;
            if (ktTypeReference != null && (typeElement = ktTypeReference.getTypeElement()) != null) {
                boolean z2 = iNextInt >= size;
                if (z) {
                    KotlinType kotlinType = (KotlinType) this.trace.get(BindingContext.TYPE, ktTypeReference);
                    if (kotlinType != null) {
                        Intrinsics.checkNotNull(kotlinType);
                        typeArgumentBindingImpl = new TypeArgumentBindingImpl(new TypeProjectionImpl(kotlinType), null, TypeBindingKt.createTypeBindingFromPsi(this.trace, typeElement, kotlinType, z2));
                    }
                } else {
                    TypeProjection typeProjection = getType().getArguments().get(iNextInt);
                    TypeParameterDescriptor typeParameterDescriptor = getType().getConstructor().getParameters().get(iNextInt);
                    BindingContext bindingContext = this.trace;
                    KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                    Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                    typeArgumentBindingImpl = new TypeArgumentBindingImpl(typeProjection, typeParameterDescriptor, TypeBindingKt.createTypeBindingFromPsi(bindingContext, typeElement, nullableAnyType, z2));
                }
            }
            arrayList.add(typeArgumentBindingImpl);
        }
        return arrayList;
    }
}
