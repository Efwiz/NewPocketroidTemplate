package org.jetbrains.kotlin.js.backend.ast.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: metadataProperties.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001f\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B_\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\rHÆ\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0010HÆ\u0003J\t\u0010$\u001a\u00020\u0003HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003J\t\u0010'\u001a\u00020\u0003HÆ\u0003J\t\u0010(\u001a\u00020\u0003HÆ\u0003J\t\u0010)\u001a\u00020\nHÆ\u0003J\t\u0010*\u001a\u00020\nHÆ\u0003J\t\u0010+\u001a\u00020\rHÆ\u0003Jy\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÆ\u0001J\u0013\u0010-\u001a\u00020\r2\b\u0010.\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010/\u001a\u000200HÖ\u0001J\t\u00101\u001a\u000202HÖ\u0001R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u000e\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015R\u0011\u0010\u000b\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0013¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/metadata/CoroutineMetadata;", "", "doResumeName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "stateName", "exceptionStateName", "finallyPathName", "resultName", "exceptionName", "baseClassRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "suspendObjectRef", "hasController", "", "hasReceiver", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;ZZLcom/intellij/psi/PsiElement;)V", "getBaseClassRef", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getDoResumeName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getExceptionName", "getExceptionStateName", "getFinallyPathName", "getHasController", "()Z", "getHasReceiver", "getPsiElement", "()Lcom/intellij/psi/PsiElement;", "getResultName", "getStateName", "getSuspendObjectRef", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CoroutineMetadata {
    private final JsExpression baseClassRef;
    private final JsName doResumeName;
    private final JsName exceptionName;
    private final JsName exceptionStateName;
    private final JsName finallyPathName;
    private final boolean hasController;
    private final boolean hasReceiver;
    private final PsiElement psiElement;
    private final JsName resultName;
    private final JsName stateName;
    private final JsExpression suspendObjectRef;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final JsName getDoResumeName() {
        return this.doResumeName;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final boolean getHasReceiver() {
        return this.hasReceiver;
    }

    /* JADX INFO: renamed from: component11, reason: from getter */
    public final PsiElement getPsiElement() {
        return this.psiElement;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final JsName getStateName() {
        return this.stateName;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final JsName getExceptionStateName() {
        return this.exceptionStateName;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final JsName getFinallyPathName() {
        return this.finallyPathName;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final JsName getResultName() {
        return this.resultName;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final JsName getExceptionName() {
        return this.exceptionName;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final JsExpression getBaseClassRef() {
        return this.baseClassRef;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final JsExpression getSuspendObjectRef() {
        return this.suspendObjectRef;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final boolean getHasController() {
        return this.hasController;
    }

    public final CoroutineMetadata copy(JsName doResumeName, JsName stateName, JsName exceptionStateName, JsName finallyPathName, JsName resultName, JsName exceptionName, JsExpression baseClassRef, JsExpression suspendObjectRef, boolean hasController, boolean hasReceiver, PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(doResumeName, "doResumeName");
        Intrinsics.checkNotNullParameter(stateName, "stateName");
        Intrinsics.checkNotNullParameter(exceptionStateName, "exceptionStateName");
        Intrinsics.checkNotNullParameter(finallyPathName, "finallyPathName");
        Intrinsics.checkNotNullParameter(resultName, "resultName");
        Intrinsics.checkNotNullParameter(exceptionName, "exceptionName");
        Intrinsics.checkNotNullParameter(baseClassRef, "baseClassRef");
        Intrinsics.checkNotNullParameter(suspendObjectRef, "suspendObjectRef");
        return new CoroutineMetadata(doResumeName, stateName, exceptionStateName, finallyPathName, resultName, exceptionName, baseClassRef, suspendObjectRef, hasController, hasReceiver, psiElement);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CoroutineMetadata)) {
            return false;
        }
        CoroutineMetadata coroutineMetadata = (CoroutineMetadata) other;
        return Intrinsics.areEqual(this.doResumeName, coroutineMetadata.doResumeName) && Intrinsics.areEqual(this.stateName, coroutineMetadata.stateName) && Intrinsics.areEqual(this.exceptionStateName, coroutineMetadata.exceptionStateName) && Intrinsics.areEqual(this.finallyPathName, coroutineMetadata.finallyPathName) && Intrinsics.areEqual(this.resultName, coroutineMetadata.resultName) && Intrinsics.areEqual(this.exceptionName, coroutineMetadata.exceptionName) && Intrinsics.areEqual(this.baseClassRef, coroutineMetadata.baseClassRef) && Intrinsics.areEqual(this.suspendObjectRef, coroutineMetadata.suspendObjectRef) && this.hasController == coroutineMetadata.hasController && this.hasReceiver == coroutineMetadata.hasReceiver && Intrinsics.areEqual(this.psiElement, coroutineMetadata.psiElement);
    }

    public int hashCode() {
        int iHashCode = ((((((((((((((((((this.doResumeName.hashCode() * 31) + this.stateName.hashCode()) * 31) + this.exceptionStateName.hashCode()) * 31) + this.finallyPathName.hashCode()) * 31) + this.resultName.hashCode()) * 31) + this.exceptionName.hashCode()) * 31) + this.baseClassRef.hashCode()) * 31) + this.suspendObjectRef.hashCode()) * 31) + Boolean.hashCode(this.hasController)) * 31) + Boolean.hashCode(this.hasReceiver)) * 31;
        PsiElement psiElement = this.psiElement;
        return iHashCode + (psiElement == null ? 0 : psiElement.hashCode());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CoroutineMetadata(doResumeName=");
        sb.append(this.doResumeName).append(", stateName=").append(this.stateName).append(", exceptionStateName=").append(this.exceptionStateName).append(", finallyPathName=").append(this.finallyPathName).append(", resultName=").append(this.resultName).append(", exceptionName=").append(this.exceptionName).append(", baseClassRef=").append(this.baseClassRef).append(", suspendObjectRef=").append(this.suspendObjectRef).append(", hasController=").append(this.hasController).append(", hasReceiver=").append(this.hasReceiver).append(", psiElement=").append(this.psiElement).append(')');
        return sb.toString();
    }

    public CoroutineMetadata(JsName doResumeName, JsName stateName, JsName exceptionStateName, JsName finallyPathName, JsName resultName, JsName exceptionName, JsExpression baseClassRef, JsExpression suspendObjectRef, boolean z, boolean z2, PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(doResumeName, "doResumeName");
        Intrinsics.checkNotNullParameter(stateName, "stateName");
        Intrinsics.checkNotNullParameter(exceptionStateName, "exceptionStateName");
        Intrinsics.checkNotNullParameter(finallyPathName, "finallyPathName");
        Intrinsics.checkNotNullParameter(resultName, "resultName");
        Intrinsics.checkNotNullParameter(exceptionName, "exceptionName");
        Intrinsics.checkNotNullParameter(baseClassRef, "baseClassRef");
        Intrinsics.checkNotNullParameter(suspendObjectRef, "suspendObjectRef");
        this.doResumeName = doResumeName;
        this.stateName = stateName;
        this.exceptionStateName = exceptionStateName;
        this.finallyPathName = finallyPathName;
        this.resultName = resultName;
        this.exceptionName = exceptionName;
        this.baseClassRef = baseClassRef;
        this.suspendObjectRef = suspendObjectRef;
        this.hasController = z;
        this.hasReceiver = z2;
        this.psiElement = psiElement;
    }

    public final JsName getDoResumeName() {
        return this.doResumeName;
    }

    public final JsName getStateName() {
        return this.stateName;
    }

    public final JsName getExceptionStateName() {
        return this.exceptionStateName;
    }

    public final JsName getFinallyPathName() {
        return this.finallyPathName;
    }

    public final JsName getResultName() {
        return this.resultName;
    }

    public final JsName getExceptionName() {
        return this.exceptionName;
    }

    public final JsExpression getBaseClassRef() {
        return this.baseClassRef;
    }

    public final JsExpression getSuspendObjectRef() {
        return this.suspendObjectRef;
    }

    public final boolean getHasController() {
        return this.hasController;
    }

    public final boolean getHasReceiver() {
        return this.hasReceiver;
    }

    public final PsiElement getPsiElement() {
        return this.psiElement;
    }
}
