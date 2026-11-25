package org.jetbrains.kotlin.fir.java.scopes;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.scopes.jvm.SignatureUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.load.java.SpecialGenericSignatures;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JavaScopeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u0005J\u0016\u0010\n\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fR\u0015\u0010\u0003\u001a\u00020\u0004*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/scopes/BuiltinMethodsWithDifferentJvmName;", "", "()V", "isRemoveAtByIndex", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;)Z", "getJvmName", "Lorg/jetbrains/kotlin/name/Name;", "functionSymbol", "isBuiltinFunctionWithDifferentNameInJvm", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuiltinMethodsWithDifferentJvmName {
    public static final BuiltinMethodsWithDifferentJvmName INSTANCE = new BuiltinMethodsWithDifferentJvmName();

    private BuiltinMethodsWithDifferentJvmName() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Name getJvmName(FirNamedFunctionSymbol functionSymbol) {
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Map<String, Name> signature_to_jvm_representation_name = SpecialGenericSignatures.INSTANCE.getSIGNATURE_TO_JVM_REPRESENTATION_NAME();
        String strComputeJvmSignature$default = SignatureUtilsKt.computeJvmSignature$default((FirFunction) functionSymbol.getFir(), null, 1, null);
        if (strComputeJvmSignature$default == null) {
            return null;
        }
        return signature_to_jvm_representation_name.get(strComputeJvmSignature$default);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isBuiltinFunctionWithDifferentNameInJvm(FirNamedFunctionSymbol functionSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(session, "session");
        return JavaScopeUtilsKt.isFromBuiltinClass(functionSymbol, session) && SpecialGenericSignatures.INSTANCE.getSIGNATURE_TO_JVM_REPRESENTATION_NAME().containsKey(SignatureUtilsKt.computeJvmSignature$default((FirFunction) functionSymbol.getFir(), null, 1, null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isRemoveAtByIndex(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        Intrinsics.checkNotNullParameter(firNamedFunctionSymbol, "<this>");
        return Intrinsics.areEqual(firNamedFunctionSymbol.getName().asString(), "removeAt") && Intrinsics.areEqual(SignatureUtilsKt.computeJvmSignature$default((FirFunction) firNamedFunctionSymbol.getFir(), null, 1, null), SpecialGenericSignatures.INSTANCE.getREMOVE_AT_NAME_AND_SIGNATURE().getSignature());
    }
}
