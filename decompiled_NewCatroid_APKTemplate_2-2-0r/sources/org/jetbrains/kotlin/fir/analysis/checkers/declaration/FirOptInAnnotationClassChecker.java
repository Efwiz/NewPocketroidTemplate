package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import com.google.android.gms.cast.MediaTrack;
import java.util.Set;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.FirRetentionAnnotationHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirAnnotationHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.resolve.checkers.OptInDescription;
import org.jetbrains.kotlin.resolve.checkers.OptInNames;

/* JADX INFO: compiled from: FirOptInAnnotationClassChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirOptInAnnotationClassChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOptInAnnotationClassChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirOptInAnnotationClassChecker INSTANCE = new FirOptInAnnotationClassChecker();

    private FirOptInAnnotationClassChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getClassKind() != ClassKind.ANNOTATION_CLASS) {
            return;
        }
        FirSession session = context.getSession();
        if (FirAnnotationUtilsKt.getAnnotationByClassId(declaration, OptInNames.INSTANCE.getREQUIRES_OPT_IN_CLASS_ID(), session) == null) {
            return;
        }
        if (FirRetentionAnnotationHelpersKt.getRetention(declaration, session) == AnnotationRetention.SOURCE) {
            FirAnnotation retentionAnnotation = FirRetentionAnnotationHelpersKt.getRetentionAnnotation(declaration, session);
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, retentionAnnotation != null ? retentionAnnotation.getSource() : null, FirErrors.INSTANCE.getOPT_IN_MARKER_WITH_WRONG_RETENTION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        Set setIntersect = CollectionsKt.intersect(FirAnnotationHelpersKt.getAllowedAnnotationTargets(declaration, session), OptInDescription.INSTANCE.getWRONG_TARGETS_FOR_MARKER());
        if (setIntersect.isEmpty()) {
            return;
        }
        FirAnnotation targetAnnotation = FirAnnotationHelpersKt.getTargetAnnotation(declaration, session);
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, targetAnnotation != null ? targetAnnotation.getSource() : null, FirErrors.INSTANCE.getOPT_IN_MARKER_WITH_WRONG_TARGET(), CollectionsKt.joinToString$default(setIntersect, null, null, null, 0, null, new PropertyReference1() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirOptInAnnotationClassChecker.check.1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getDescription()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((KotlinTarget) obj).getDescription();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return MediaTrack.ROLE_DESCRIPTION;
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(KotlinTarget.class);
            }
        }, 31, null), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }
}
