package org.jetbrains.kotlin.resolve.typeBinding;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: compiled from: TypeBinding.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a@\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u00020\u0001\"\b\b\u0000\u0010\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\t0\bH\u0000\u001a.\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a\u001a\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\t*\u00020\u00122\u0006\u0010\f\u001a\u00020\r\u001a\u001a\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\t*\u00020\u00142\u0006\u0010\f\u001a\u00020\r¨\u0006\u0015"}, d2 = {"createTypeArgumentBindingsWithSinglePsiElement", "", "Lorg/jetbrains/kotlin/resolve/typeBinding/TypeArgumentBinding;", "P", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "createBinding", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/typeBinding/TypeBinding;", "createTypeBindingFromPsi", "Lorg/jetbrains/kotlin/psi/KtTypeElement;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "psiElement", "isArgumentFromQualifier", "", "createTypeBinding", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "createTypeBindingForReturnType", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeBindingKt {
    public static final TypeBinding<KtTypeElement> createTypeBinding(KtTypeReference ktTypeReference, BindingContext trace) {
        Intrinsics.checkNotNullParameter(ktTypeReference, "<this>");
        Intrinsics.checkNotNullParameter(trace, "trace");
        KotlinType kotlinType = (KotlinType) trace.get(BindingContext.TYPE, ktTypeReference);
        KtTypeElement typeElement = ktTypeReference.getTypeElement();
        if (kotlinType == null || typeElement == null) {
            return null;
        }
        return createTypeBindingFromPsi(trace, typeElement, kotlinType, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TypeBinding<KtTypeElement> createTypeBindingFromPsi(BindingContext bindingContext, KtTypeElement ktTypeElement, KotlinType kotlinType, boolean z) {
        if (SpecialTypesKt.getAbbreviatedType(kotlinType) != null) {
            return new AbbreviatedTypeBinding(kotlinType, ktTypeElement, z);
        }
        return new ExplicitTypeBinding(bindingContext, ktTypeElement, kotlinType, z);
    }

    public static final TypeBinding<PsiElement> createTypeBindingForReturnType(KtCallableDeclaration ktCallableDeclaration, BindingContext trace) {
        Intrinsics.checkNotNullParameter(ktCallableDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(trace, "trace");
        KtTypeReference ktTypeReferenceMo7396getTypeReference = ktCallableDeclaration.mo7396getTypeReference();
        if (ktTypeReferenceMo7396getTypeReference != null) {
            return createTypeBinding(ktTypeReferenceMo7396getTypeReference, trace);
        }
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) trace.get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktCallableDeclaration);
        if (!(declarationDescriptor instanceof CallableDescriptor)) {
            return null;
        }
        KotlinType returnType = ((CallableDescriptor) declarationDescriptor).getReturnType();
        return returnType != null ? new NoTypeElementBinding(trace, ktCallableDeclaration, returnType) : null;
    }

    public static final <P extends PsiElement> List<TypeArgumentBinding<P>> createTypeArgumentBindingsWithSinglePsiElement(KotlinType type, Function1<? super KotlinType, ? extends TypeBinding<? extends P>> createBinding) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(createBinding, "createBinding");
        int i = 0;
        boolean z = KotlinTypeKt.isError(type) || type.getConstructor().getParameters().size() != type.getArguments().size();
        List<TypeProjection> arguments = type.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        for (Object obj : arguments) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeProjection typeProjection = (TypeProjection) obj;
            TypeParameterDescriptor typeParameterDescriptor = z ? null : type.getConstructor().getParameters().get(i);
            KotlinType type2 = typeProjection.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            arrayList.add(new TypeArgumentBindingImpl(typeProjection, typeParameterDescriptor, createBinding.invoke(type2)));
            i = i2;
        }
        return arrayList;
    }
}
