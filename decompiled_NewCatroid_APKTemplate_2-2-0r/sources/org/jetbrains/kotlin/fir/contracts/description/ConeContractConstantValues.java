package org.jetbrains.kotlin.fir.contracts.description;

import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.contracts.description.KtBooleanConstantReference;
import org.jetbrains.kotlin.contracts.description.KtConstantReference;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: ConeValues.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u001d\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\bR\u001d\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/contracts/description/ConeContractConstantValues;", "", "()V", "FALSE", "Lorg/jetbrains/kotlin/contracts/description/KtBooleanConstantReference;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getFALSE", "()Lorg/jetbrains/kotlin/contracts/description/KtBooleanConstantReference;", "NOT_NULL", "Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;", "getNOT_NULL", "()Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;", "NULL", "getNULL", "TRUE", "getTRUE", "WILDCARD", "getWILDCARD", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeContractConstantValues {
    public static final ConeContractConstantValues INSTANCE = new ConeContractConstantValues();
    private static final KtConstantReference<ConeKotlinType, ConeDiagnostic> NULL = new KtConstantReference<>("NULL");
    private static final KtConstantReference<ConeKotlinType, ConeDiagnostic> WILDCARD = new KtConstantReference<>("WILDCARD");
    private static final KtConstantReference<ConeKotlinType, ConeDiagnostic> NOT_NULL = new KtConstantReference<>("NOT_NULL");
    private static final KtBooleanConstantReference<ConeKotlinType, ConeDiagnostic> TRUE = new KtBooleanConstantReference<>("TRUE");
    private static final KtBooleanConstantReference<ConeKotlinType, ConeDiagnostic> FALSE = new KtBooleanConstantReference<>("FALSE");

    private ConeContractConstantValues() {
    }

    public final KtConstantReference<ConeKotlinType, ConeDiagnostic> getNULL() {
        return NULL;
    }

    public final KtConstantReference<ConeKotlinType, ConeDiagnostic> getWILDCARD() {
        return WILDCARD;
    }

    public final KtConstantReference<ConeKotlinType, ConeDiagnostic> getNOT_NULL() {
        return NOT_NULL;
    }

    public final KtBooleanConstantReference<ConeKotlinType, ConeDiagnostic> getTRUE() {
        return TRUE;
    }

    public final KtBooleanConstantReference<ConeKotlinType, ConeDiagnostic> getFALSE() {
        return FALSE;
    }
}
