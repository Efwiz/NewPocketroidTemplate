package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryPackageSourceElement;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinarySourceElement;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor;

/* JADX INFO: compiled from: InlinePlatformCompatibilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J$\u0010\f\u001a\u00020\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/InlinePlatformCompatibilityChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "jvmTarget", "Lorg/jetbrains/kotlin/config/JvmTarget;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/config/JvmTarget;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "doCheck", "", "getJvmTarget", "()Lorg/jetbrains/kotlin/config/JvmTarget;", "properError", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlinePlatformCompatibilityChecker implements CallChecker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean doCheck;
    private final JvmTarget jvmTarget;
    private final boolean properError;

    public InlinePlatformCompatibilityChecker(JvmTarget jvmTarget, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(jvmTarget, "jvmTarget");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.jvmTarget = jvmTarget;
        this.properError = languageVersionSettings.supportsFeature(LanguageFeature.ProperInlineFromHigherPlatformDiagnostic);
        this.doCheck = INSTANCE.doCheck();
    }

    public final JvmTarget getJvmTarget() {
        return this.jvmTarget;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        int iIntValue;
        Integer bytecodeVersionIfDeserializedDescriptor;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.doCheck) {
            CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
            CallableMemberDescriptor callableMemberDescriptor = resultingDescriptor instanceof CallableMemberDescriptor ? (CallableMemberDescriptor) resultingDescriptor : null;
            if (callableMemberDescriptor == null) {
                return;
            }
            if (InlineUtil.isInline(callableMemberDescriptor) || ((callableMemberDescriptor instanceof PropertyDescriptor) && InlineUtil.isInline(((PropertyDescriptor) callableMemberDescriptor).getGetter()))) {
                CallableMemberDescriptor directMember = DescriptorUtils.getDirectMember(callableMemberDescriptor);
                Intrinsics.checkNotNullExpressionValue(directMember, "getDirectMember(...)");
                int majorVersion = this.jvmTarget.getMajorVersion();
                if (!this.properError && (bytecodeVersionIfDeserializedDescriptor = INSTANCE.getBytecodeVersionIfDeserializedDescriptor(directMember, false)) != null && majorVersion < bytecodeVersionIfDeserializedDescriptor.intValue()) {
                    context.getTrace().report(ErrorsJvm.INLINE_FROM_HIGHER_PLATFORM.on(reportOn, JvmTarget.INSTANCE.getDescription(bytecodeVersionIfDeserializedDescriptor.intValue()), JvmTarget.INSTANCE.getDescription(majorVersion)));
                    return;
                }
                Integer bytecodeVersionIfDeserializedDescriptor2 = INSTANCE.getBytecodeVersionIfDeserializedDescriptor(directMember, true);
                if (bytecodeVersionIfDeserializedDescriptor2 == null || majorVersion >= (iIntValue = bytecodeVersionIfDeserializedDescriptor2.intValue())) {
                    return;
                }
                if (this.properError) {
                    context.getTrace().report(ErrorsJvm.INLINE_FROM_HIGHER_PLATFORM.on(reportOn, JvmTarget.INSTANCE.getDescription(iIntValue), JvmTarget.INSTANCE.getDescription(majorVersion)));
                } else {
                    context.getTrace().report(ErrorsJvm.INLINE_FROM_HIGHER_PLATFORM_WARNING.on(reportOn, JvmTarget.INSTANCE.getDescription(iIntValue), JvmTarget.INSTANCE.getDescription(majorVersion)));
                }
            }
        }
    }

    /* JADX INFO: compiled from: InlinePlatformCompatibilityChecker.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u001d\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004¢\u0006\u0002\u0010\nJ\f\u0010\u000b\u001a\u00020\f*\u00020\fH\u0002¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/InlinePlatformCompatibilityChecker$Companion;", "", "()V", "doCheck", "", "getBytecodeVersionIfDeserializedDescriptor", "", "funOrProperty", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "useConcreteSuperImplementation", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Z)Ljava/lang/Integer;", "getConcreteDeclarationForInline", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean doCheck() {
            return !Intrinsics.areEqual("true", System.getProperty("kotlin.skip.bytecode.version.check"));
        }

        public final Integer getBytecodeVersionIfDeserializedDescriptor(DeclarationDescriptor funOrProperty, boolean useConcreteSuperImplementation) {
            KotlinJvmBinaryClass containingBinaryClass;
            Intrinsics.checkNotNullParameter(funOrProperty, "funOrProperty");
            if (!(funOrProperty instanceof DeserializedCallableMemberDescriptor)) {
                return null;
            }
            DeclarationDescriptor containingDeclaration = (useConcreteSuperImplementation ? getConcreteDeclarationForInline((CallableMemberDescriptor) funOrProperty) : (CallableMemberDescriptor) funOrProperty).getContainingDeclaration();
            Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor");
            SourceElement source = ((ClassOrPackageFragmentDescriptor) containingDeclaration).getSourceElement();
            Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
            if (source instanceof KotlinJvmBinarySourceElement) {
                containingBinaryClass = ((KotlinJvmBinarySourceElement) source).getBinaryClass();
            } else {
                containingBinaryClass = source instanceof KotlinJvmBinaryPackageSourceElement ? ((KotlinJvmBinaryPackageSourceElement) source).getContainingBinaryClass((DeserializedMemberDescriptor) funOrProperty) : null;
            }
            FileBasedKotlinClass fileBasedKotlinClass = containingBinaryClass instanceof FileBasedKotlinClass ? (FileBasedKotlinClass) containingBinaryClass : null;
            if (fileBasedKotlinClass == null) {
                return null;
            }
            return Integer.valueOf(fileBasedKotlinClass.getClassVersion());
        }

        private final CallableMemberDescriptor getConcreteDeclarationForInline(CallableMemberDescriptor callableMemberDescriptor) {
            Object next;
            if (callableMemberDescriptor.getKind().isReal()) {
                return callableMemberDescriptor;
            }
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            Iterator<T> it = overriddenDescriptors.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                DeclarationDescriptor containingDeclaration = ((CallableMemberDescriptor) next).getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                if (!DescriptorUtils.isInterface(containingDeclaration) && !DescriptorUtils.isAnnotationClass(containingDeclaration)) {
                    break;
                }
            }
            CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) next;
            return callableMemberDescriptor2 != null ? InlinePlatformCompatibilityChecker.INSTANCE.getConcreteDeclarationForInline(callableMemberDescriptor2) : callableMemberDescriptor;
        }
    }
}
