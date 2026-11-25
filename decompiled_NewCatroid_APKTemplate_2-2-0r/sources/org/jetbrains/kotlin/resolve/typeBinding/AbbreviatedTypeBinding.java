package org.jetbrains.kotlin.resolve.typeBinding;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: TypeBinding.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\"\u0010\t\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000eR\u0014\u0010\u0005\u001a\u00020\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/typeBinding/AbbreviatedTypeBinding;", "Lorg/jetbrains/kotlin/resolve/typeBinding/TypeBinding;", "Lorg/jetbrains/kotlin/psi/KtTypeElement;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "psiElement", "isArgumentFromQualifier", "", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/psi/KtTypeElement;Z)V", "arguments", "", "Lorg/jetbrains/kotlin/resolve/typeBinding/TypeArgumentBinding;", "getArguments", "()Ljava/util/List;", "()Z", "isInAbbreviation", "getPsiElement", "()Lorg/jetbrains/kotlin/psi/KtTypeElement;", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class AbbreviatedTypeBinding implements TypeBinding<KtTypeElement> {
    private final boolean isArgumentFromQualifier;
    private final KtTypeElement psiElement;
    private final KotlinType type;

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeBinding
    public boolean isInAbbreviation() {
        return true;
    }

    public AbbreviatedTypeBinding(KotlinType type, KtTypeElement psiElement, boolean z) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        this.type = type;
        this.psiElement = psiElement;
        this.isArgumentFromQualifier = z;
    }

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeBinding
    public KotlinType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeBinding
    public KtTypeElement getPsiElement() {
        return this.psiElement;
    }

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeBinding
    /* JADX INFO: renamed from: isArgumentFromQualifier, reason: from getter */
    public boolean getIsArgumentFromQualifier() {
        return this.isArgumentFromQualifier;
    }

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeBinding
    public List<TypeArgumentBinding<KtTypeElement>> getArguments() {
        return TypeBindingKt.createTypeArgumentBindingsWithSinglePsiElement(getType(), new Function1<KotlinType, TypeBinding<? extends KtTypeElement>>() { // from class: org.jetbrains.kotlin.resolve.typeBinding.AbbreviatedTypeBinding$arguments$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final TypeBinding<KtTypeElement> invoke(KotlinType argumentType) {
                Intrinsics.checkNotNullParameter(argumentType, "argumentType");
                return new AbbreviatedTypeBinding(argumentType, this.this$0.getPsiElement(), this.this$0.getIsArgumentFromQualifier());
            }
        });
    }
}
