package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: KtPredicates.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B1\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00028\u0001¢\u0006\u0002\u0010\nJA\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0002\u0010\u0012\"\u0004\b\u0003\u0010\u00132\u001e\u0010\u0014\u001a\u001a\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00152\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u0017R\u0013\u0010\t\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u000e\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/KtErroneousIsInstancePredicate;", "Type", "Diagnostic", "Lorg/jetbrains/kotlin/contracts/description/KtIsInstancePredicate;", ParameterDescription.NAME_PREFIX, "Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", ModuleXmlParser.TYPE, "isNegated", "", "diagnostic", "(Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;Ljava/lang/Object;ZLjava/lang/Object;)V", "getDiagnostic", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "erroneous", "getErroneous", "()Z", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtErroneousIsInstancePredicate<Type, Diagnostic> extends KtIsInstancePredicate<Type, Diagnostic> {
    private final Diagnostic diagnostic;

    @Override // org.jetbrains.kotlin.contracts.description.KtIsInstancePredicate, org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public boolean getErroneous() {
        return true;
    }

    public final Diagnostic getDiagnostic() {
        return this.diagnostic;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtErroneousIsInstancePredicate(KtValueParameterReference<Type, Diagnostic> arg, Type type, boolean z, Diagnostic diagnostic) {
        super(arg, type, z);
        Intrinsics.checkNotNullParameter(arg, "arg");
        this.diagnostic = diagnostic;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.KtIsInstancePredicate, org.jetbrains.kotlin.contracts.description.KtBooleanExpression, org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public <R, D> R accept(KtContractDescriptionVisitor<? extends R, ? super D, Type, Diagnostic> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitErroneousIsInstancePredicate(this, data);
    }
}
