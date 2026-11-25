package org.jetbrains.kotlin.fir.resolve.diagnostics;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.KtSourceElement;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeUnsupportedDefaultValueInFunctionType;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeUnsupported;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ConeUnsupportedDefaultValueInFunctionType extends ConeUnsupported {
    /* JADX WARN: Multi-variable type inference failed */
    public ConeUnsupportedDefaultValueInFunctionType() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public /* synthetic */ ConeUnsupportedDefaultValueInFunctionType(KtSourceElement ktSourceElement, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : ktSourceElement);
    }

    public ConeUnsupportedDefaultValueInFunctionType(KtSourceElement ktSourceElement) {
        super("Default value of parameter in function type", ktSourceElement);
    }
}
