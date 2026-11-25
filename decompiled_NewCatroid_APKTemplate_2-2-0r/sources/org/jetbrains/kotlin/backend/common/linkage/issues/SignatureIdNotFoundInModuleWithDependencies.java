package org.jetbrains.kotlin.backend.common.linkage.issues;

import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IdSignatureRendererKt;
import org.jetbrains.kotlin.utils.ResolvedDependency;
import org.jetbrains.kotlin.utils.ResolvedDependencyId;

/* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u000f\u001a\u00020\fH\u0002R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\fX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/SignatureIdNotFoundInModuleWithDependencies;", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/KotlinIrLinkerIssue;", "idSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "problemModuleDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "allModuleDeserializers", "", "userVisibleIrModulesSupport", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport;", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;Ljava/util/Collection;Lorg/jetbrains/kotlin/backend/common/linkage/issues/UserVisibleIrModulesSupport;)V", MLAsrCaptureConstants.ASR_ERROR_MESSAGE, "", "getErrorMessage", "()Ljava/lang/String;", "computeErrorMessage", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SignatureIdNotFoundInModuleWithDependencies extends KotlinIrLinkerIssue {
    private final Collection<IrModuleDeserializer> allModuleDeserializers;
    private final String errorMessage;
    private final IdSignature idSignature;
    private final IrModuleDeserializer problemModuleDeserializer;
    private final UserVisibleIrModulesSupport userVisibleIrModulesSupport;

    /* JADX WARN: Multi-variable type inference failed */
    public SignatureIdNotFoundInModuleWithDependencies(IdSignature idSignature, IrModuleDeserializer problemModuleDeserializer, Collection<? extends IrModuleDeserializer> allModuleDeserializers, UserVisibleIrModulesSupport userVisibleIrModulesSupport) {
        Intrinsics.checkNotNullParameter(idSignature, "idSignature");
        Intrinsics.checkNotNullParameter(problemModuleDeserializer, "problemModuleDeserializer");
        Intrinsics.checkNotNullParameter(allModuleDeserializers, "allModuleDeserializers");
        Intrinsics.checkNotNullParameter(userVisibleIrModulesSupport, "userVisibleIrModulesSupport");
        this.idSignature = idSignature;
        this.problemModuleDeserializer = problemModuleDeserializer;
        this.allModuleDeserializers = allModuleDeserializers;
        this.userVisibleIrModulesSupport = userVisibleIrModulesSupport;
        try {
            this.errorMessage = computeErrorMessage();
        } catch (Throwable unused) {
            StringBuilder sb = new StringBuilder();
            StringBuilder sbAppend = sb.append("Failed to compute the detailed error message. See the root cause exception.");
            Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
            Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
            Intrinsics.checkNotNullExpressionValue(sb.append('\n'), "append(...)");
            sb.append("Shortly: The required symbol " + IdSignatureRendererKt.render$default(this.idSignature, null, 1, null) + " is missing in the module or module dependencies.");
            sb.append(" This could happen if the required dependency is missing in the project. Or if there is a dependency that has a different version (without the required symbol) in the project than the version (with the required symbol) that the module was initially compiled with.");
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            throw new RuntimeException(string);
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.linkage.issues.KotlinIrLinkerIssue
    protected String getErrorMessage() {
        return this.errorMessage;
    }

    private final String computeErrorMessage() {
        StringBuilder sb = new StringBuilder();
        Map<ResolvedDependencyId, ResolvedDependency> userVisibleModules = this.userVisibleIrModulesSupport.getUserVisibleModules(this.allModuleDeserializers);
        ResolvedDependencyId problemModuleId = KotlinIrLinkerIssuesKt.getProblemModuleId(this.userVisibleIrModulesSupport, this.problemModuleDeserializer, userVisibleModules);
        String moduleIdWithVersion = ((ResolvedDependency) MapsKt.getValue(userVisibleModules, problemModuleId)).getModuleIdWithVersion();
        sb.append("Module \"" + problemModuleId + "\" has a reference to symbol " + IdSignatureRendererKt.render$default(this.idSignature, null, 1, null) + '.');
        sb.append(" Neither the module itself nor its dependencies contain such declaration.\n\nThis could happen if the required dependency is missing in the project.");
        sb.append(" Or if there is a dependency of \"" + problemModuleId + "\" that has a different version in the project");
        sb.append(" than the version that \"" + moduleIdWithVersion + "\" was initially compiled with.");
        sb.append(" Please check that the project configuration is correct and has consistent versions of all required dependencies.");
        KotlinIrLinkerIssuesKt.appendPotentiallyConflictingDependencies(sb, "The list of \"" + moduleIdWithVersion + "\" dependencies that may lead to conflicts:", userVisibleModules, KotlinIrLinkerIssuesKt.findPotentiallyConflictingOutgoingDependencies(problemModuleId, userVisibleModules), this.userVisibleIrModulesSupport.getModuleIdComparator());
        KotlinIrLinkerIssuesKt.appendProjectDependencies(sb, userVisibleModules, SetsKt.setOf(problemModuleId), "This module requires symbol " + IdSignatureRendererKt.render$default(this.idSignature, null, 1, null), this.userVisibleIrModulesSupport.getSourceCodeModuleId(), this.userVisibleIrModulesSupport.getModuleIdComparator());
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
