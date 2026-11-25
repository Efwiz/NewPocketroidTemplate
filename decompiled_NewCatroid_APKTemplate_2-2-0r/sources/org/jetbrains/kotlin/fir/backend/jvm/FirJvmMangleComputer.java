package org.jetbrains.kotlin.fir.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.fir.backend.FirMangleComputer;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;

/* JADX INFO: compiled from: FirJvmMangleComputer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0014J\u0010\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0006H\u0016J\u000e\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u000eH\u0014¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmMangleComputer;", "Lorg/jetbrains/kotlin/fir/backend/FirMangleComputer;", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "(Ljava/lang/StringBuilder;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;)V", "addReturnType", "", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newMode", "platformSpecificSuffix", "", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmMangleComputer extends FirMangleComputer {
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    protected boolean addReturnType() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirJvmMangleComputer(StringBuilder builder, MangleMode mode) {
        super(builder, mode);
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(mode, "mode");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public String platformSpecificSuffix(FirFunction firFunction) {
        FirFile firCallableContainerFile;
        Intrinsics.checkNotNullParameter(firFunction, "<this>");
        if (!(firFunction instanceof FirSimpleFunction)) {
            return null;
        }
        FirSimpleFunction firSimpleFunction = (FirSimpleFunction) firFunction;
        if (!Intrinsics.areEqual(firSimpleFunction.getName().asString(), "main") || (firCallableContainerFile = FirProviderKt.getFirProvider(firFunction.getModuleData().getSession()).getFirCallableContainerFile(firSimpleFunction.getSymbol())) == null) {
            return null;
        }
        return firCallableContainerFile.getName();
    }

    @Override // org.jetbrains.kotlin.fir.backend.FirMangleComputer, org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer
    public FirJvmMangleComputer copy(MangleMode newMode) {
        Intrinsics.checkNotNullParameter(newMode, "newMode");
        return new FirJvmMangleComputer(getBuilder(), newMode);
    }
}
