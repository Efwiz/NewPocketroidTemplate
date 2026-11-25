package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: KtPredicates.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B)\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJA\u0010\u0012\u001a\u0002H\u0013\"\u0004\b\u0002\u0010\u0013\"\u0004\b\u0003\u0010\u00142\u001e\u0010\u0015\u001a\u001a\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00162\u0006\u0010\u0017\u001a\u0002H\u0014H\u0016¢\u0006\u0002\u0010\u0018J\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0000R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000eR\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/KtIsInstancePredicate;", "Type", "Diagnostic", "Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;", ParameterDescription.NAME_PREFIX, "Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", ModuleXmlParser.TYPE, "isNegated", "", "(Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;Ljava/lang/Object;Z)V", "getArg", "()Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", "erroneous", "getErroneous", "()Z", "getType", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "negated", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtIsInstancePredicate<Type, Diagnostic> implements KtBooleanExpression<Type, Diagnostic> {
    private final KtValueParameterReference<Type, Diagnostic> arg;
    private final boolean isNegated;
    private final Type type;

    public KtIsInstancePredicate(KtValueParameterReference<Type, Diagnostic> arg, Type type, boolean z) {
        Intrinsics.checkNotNullParameter(arg, "arg");
        this.arg = arg;
        this.type = type;
        this.isNegated = z;
    }

    public final KtValueParameterReference<Type, Diagnostic> getArg() {
        return this.arg;
    }

    public final Type getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: isNegated, reason: from getter */
    public final boolean getIsNegated() {
        return this.isNegated;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public boolean getErroneous() {
        return this.arg.getErroneous();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.KtBooleanExpression, org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public <R, D> R accept(KtContractDescriptionVisitor<? extends R, ? super D, Type, Diagnostic> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitIsInstancePredicate(this, data);
    }

    public final KtIsInstancePredicate<Type, Diagnostic> negated() {
        return new KtIsInstancePredicate<>(this.arg, this.type, !this.isNegated);
    }
}
