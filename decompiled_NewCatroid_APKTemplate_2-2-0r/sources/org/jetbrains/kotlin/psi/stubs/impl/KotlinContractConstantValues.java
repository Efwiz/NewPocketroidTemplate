package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import org.jetbrains.kotlin.contracts.description.KtBooleanConstantReference;
import org.jetbrains.kotlin.contracts.description.KtConstantReference;

/* JADX INFO: compiled from: KotlinContractEffectStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001f\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001f\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001f\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u001f\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\bR\u001f\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinContractConstantValues;", "", "()V", "FALSE", "Lorg/jetbrains/kotlin/contracts/description/KtBooleanConstantReference;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;", "", "getFALSE", "()Lorg/jetbrains/kotlin/contracts/description/KtBooleanConstantReference;", "NOT_NULL", "Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;", "getNOT_NULL", "()Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;", "NULL", "getNULL", "TRUE", "getTRUE", "WILDCARD", "getWILDCARD", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinContractConstantValues {
    public static final KotlinContractConstantValues INSTANCE = new KotlinContractConstantValues();
    private static final KtConstantReference NULL = new KtConstantReference("NULL");
    private static final KtConstantReference WILDCARD = new KtConstantReference("WILDCARD");
    private static final KtConstantReference NOT_NULL = new KtConstantReference("NOT_NULL");
    private static final KtBooleanConstantReference TRUE = new KtBooleanConstantReference("TRUE");
    private static final KtBooleanConstantReference FALSE = new KtBooleanConstantReference("FALSE");

    private KotlinContractConstantValues() {
    }

    public final KtConstantReference getNULL() {
        return NULL;
    }

    public final KtConstantReference getWILDCARD() {
        return WILDCARD;
    }

    public final KtConstantReference getNOT_NULL() {
        return NOT_NULL;
    }

    public final KtBooleanConstantReference getTRUE() {
        return TRUE;
    }

    public final KtBooleanConstantReference getFALSE() {
        return FALSE;
    }
}
