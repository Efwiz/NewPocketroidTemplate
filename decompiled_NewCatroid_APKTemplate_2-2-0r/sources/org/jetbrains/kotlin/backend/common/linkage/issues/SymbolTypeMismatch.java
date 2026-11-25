package org.jetbrains.kotlin.backend.common.linkage.issues;

import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IdSignatureRendererKt;
import org.jetbrains.kotlin.utils.ResolvedDependency;
import org.jetbrains.kotlin.utils.ResolvedDependencyId;

/* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u000e\u001a\u00020\u000bH\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000bX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/SymbolTypeMismatch;", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/KotlinIrLinkerIssue;", "cause", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/IrSymbolTypeMismatchException;", "allModuleDeserializers", "", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "userVisibleIrModulesSupport", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport;", "(Lorg/jetbrains/kotlin/backend/common/linkage/issues/IrSymbolTypeMismatchException;Ljava/util/Collection;Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport;)V", MLAsrCaptureConstants.ASR_ERROR_MESSAGE, "", "getErrorMessage", "()Ljava/lang/String;", "computeErrorMessage", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolTypeMismatch extends KotlinIrLinkerIssue {
    private final Collection<IrModuleDeserializer> allModuleDeserializers;
    private final IrSymbolTypeMismatchException cause;
    private final String errorMessage;
    private final UserVisibleIrModulesSupport userVisibleIrModulesSupport;

    /* JADX WARN: Multi-variable type inference failed */
    public SymbolTypeMismatch(IrSymbolTypeMismatchException cause, Collection<? extends IrModuleDeserializer> allModuleDeserializers, UserVisibleIrModulesSupport userVisibleIrModulesSupport) throws IrSymbolTypeMismatchException {
        Intrinsics.checkNotNullParameter(cause, "cause");
        Intrinsics.checkNotNullParameter(allModuleDeserializers, "allModuleDeserializers");
        Intrinsics.checkNotNullParameter(userVisibleIrModulesSupport, "userVisibleIrModulesSupport");
        this.cause = cause;
        this.allModuleDeserializers = allModuleDeserializers;
        this.userVisibleIrModulesSupport = userVisibleIrModulesSupport;
        try {
            this.errorMessage = computeErrorMessage();
        } catch (Throwable th) {
            if (th != this.cause) {
                th.addSuppressed(th.getCause());
            }
            throw th;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.linkage.issues.KotlinIrLinkerIssue
    protected String getErrorMessage() {
        return this.errorMessage;
    }

    private final String computeErrorMessage() {
        Set<ResolvedDependencyId> setEmptySet;
        String str;
        String strRender$default;
        StringBuilder sb = new StringBuilder();
        Map<ResolvedDependencyId, ResolvedDependency> userVisibleModules = this.userVisibleIrModulesSupport.getUserVisibleModules(this.allModuleDeserializers);
        IdSignature signature = this.cause.getActual().getSignature();
        if (signature != null) {
            Collection<IrModuleDeserializer> collection = this.allModuleDeserializers;
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (IrModuleDeserializer irModuleDeserializer : collection) {
                ResolvedDependencyId problemModuleId = irModuleDeserializer.contains(signature) ? KotlinIrLinkerIssuesKt.getProblemModuleId(this.userVisibleIrModulesSupport, irModuleDeserializer, userVisibleModules) : null;
                if (problemModuleId != null) {
                    linkedHashSet.add(problemModuleId);
                }
            }
            setEmptySet = linkedHashSet;
        } else {
            setEmptySet = SetsKt.emptySet();
        }
        sb.append(this.cause.getMessage());
        sb.append("\n\nThis could happen if there are two libraries, where one library was compiled against the different version of the other library than the one currently used in the project. Please check that the project configuration is correct and has consistent versions of dependencies.");
        for (ResolvedDependencyId resolvedDependencyId : setEmptySet) {
            KotlinIrLinkerIssuesKt.appendPotentiallyConflictingDependencies(sb, "The list of libraries that depend on \"" + resolvedDependencyId + "\" and may lead to conflicts:", userVisibleModules, KotlinIrLinkerIssuesKt.findPotentiallyConflictingIncomingDependencies(resolvedDependencyId, userVisibleModules, this.userVisibleIrModulesSupport.getSourceCodeModuleId()), this.userVisibleIrModulesSupport.getModuleIdComparator());
        }
        StringBuilder sb2 = new StringBuilder("This module contains ");
        if (signature == null || (strRender$default = IdSignatureRendererKt.render$default(signature, null, 1, null)) == null || (str = "symbol " + strRender$default) == null) {
            str = "a symbol";
        }
        KotlinIrLinkerIssuesKt.appendProjectDependencies(sb, userVisibleModules, setEmptySet, sb2.append(str).append(" that is the cause of the conflict").toString(), this.userVisibleIrModulesSupport.getSourceCodeModuleId(), this.userVisibleIrModulesSupport.getModuleIdComparator());
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
