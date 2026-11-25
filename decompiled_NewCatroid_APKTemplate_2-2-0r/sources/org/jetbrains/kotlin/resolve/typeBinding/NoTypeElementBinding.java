package org.jetbrains.kotlin.resolve.typeBinding;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: TypeBinding.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0002\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\"\u0010\n\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011R\u0016\u0010\u0006\u001a\u00028\u0000X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/typeBinding/NoTypeElementBinding;", "P", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/resolve/typeBinding/TypeBinding;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "psiElement", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/resolve/BindingContext;Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/types/KotlinType;)V", "arguments", "", "Lorg/jetbrains/kotlin/resolve/typeBinding/TypeArgumentBinding;", "getArguments", "()Ljava/util/List;", "isInAbbreviation", "", "()Z", "getPsiElement", "()Lcom/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class NoTypeElementBinding<P extends PsiElement> implements TypeBinding<P> {
    private final P psiElement;
    private final BindingContext trace;
    private final KotlinType type;

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeBinding
    public boolean isInAbbreviation() {
        return false;
    }

    public NoTypeElementBinding(BindingContext trace, P psiElement, KotlinType type) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        Intrinsics.checkNotNullParameter(type, "type");
        this.trace = trace;
        this.psiElement = psiElement;
        this.type = type;
    }

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeBinding
    public P getPsiElement() {
        return this.psiElement;
    }

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeBinding
    public KotlinType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.resolve.typeBinding.TypeBinding
    public List<TypeArgumentBinding<P>> getArguments() {
        return TypeBindingKt.createTypeArgumentBindingsWithSinglePsiElement(getType(), new Function1<KotlinType, TypeBinding<? extends P>>(this) { // from class: org.jetbrains.kotlin.resolve.typeBinding.NoTypeElementBinding$arguments$1
            final /* synthetic */ NoTypeElementBinding<P> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final TypeBinding<P> invoke(KotlinType argumentType) {
                Intrinsics.checkNotNullParameter(argumentType, "argumentType");
                return new NoTypeElementBinding(((NoTypeElementBinding) this.this$0).trace, this.this$0.getPsiElement(), argumentType);
            }
        });
    }
}
