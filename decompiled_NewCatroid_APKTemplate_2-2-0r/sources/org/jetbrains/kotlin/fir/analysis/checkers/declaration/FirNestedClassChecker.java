package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import com.google.android.gms.cast.MediaTrack;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;

/* JADX INFO: compiled from: FirNestedClassChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirNestedClassChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", MediaTrack.ROLE_DESCRIPTION, "", "getDescription", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Ljava/lang/String;", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNestedClassChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirNestedClassChecker INSTANCE = new FirNestedClassChecker();

    /* JADX INFO: compiled from: FirNestedClassChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.INTERFACE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClassKind.ENUM_CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ClassKind.ENUM_ENTRY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ClassKind.ANNOTATION_CLASS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ClassKind.OBJECT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FirNestedClassChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirDeclaration firDeclaration;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (((declaration.getClassKind().isSingleton() || declaration.getClassKind() == ClassKind.ENUM_CLASS) && declaration.getSymbol().getClassId().isLocal()) || (firDeclaration = (FirDeclaration) CollectionsKt.lastOrNull((List) context.getContainingDeclarations())) == null) {
            return;
        }
        if (!(firDeclaration instanceof FirRegularClass)) {
            if (!(firDeclaration instanceof FirClass) || ((FirClass) firDeclaration).getClassKind() != ClassKind.ENUM_ENTRY || declaration.getStatus().isInner() || declaration.getStatus().isCompanion()) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getNESTED_CLASS_NOT_ALLOWED(), getDescription(declaration), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            return;
        }
        if (declaration.getStatus().isInner()) {
            return;
        }
        if (((FirMemberDeclaration) firDeclaration).getStatus().isInner() || ((FirClassLikeDeclaration) firDeclaration).getSymbol().getClassId().isLocal()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getNESTED_CLASS_NOT_ALLOWED(), getDescription(declaration), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }

    private final String getDescription(FirRegularClass firRegularClass) {
        switch (WhenMappings.$EnumSwitchMapping$0[firRegularClass.getClassKind().ordinal()]) {
            case 1:
                return "Class";
            case 2:
                return "Interface";
            case 3:
                return "Enum class";
            case 4:
                return "Enum entry";
            case 5:
                return "Annotation class";
            case 6:
                return firRegularClass.getStatus().isCompanion() ? "Companion object" : CommonClassNames.JAVA_LANG_OBJECT_SHORT;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}
