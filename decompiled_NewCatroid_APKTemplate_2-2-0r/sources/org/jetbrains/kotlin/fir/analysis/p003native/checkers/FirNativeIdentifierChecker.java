package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.p002native.FirNativeErrors;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirNativeIdentifierChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J*\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeIdentifierChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "invalidChars", "", "", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkNameAndReport", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeIdentifierChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirNativeIdentifierChecker INSTANCE = new FirNativeIdentifierChecker();
    private static final Set<Character> invalidChars = SetsKt.setOf((Object[]) new Character[]{'.', ';', ',', '(', ')', '[', ']', '{', '}', '/', '<', '>', ':', '\\', '$', Character.valueOf(Typography.amp), '~', '*', Character.valueOf(MangleConstant.Q_MARK), '#', '|', (char) 167, Character.valueOf(MangleConstant.PLATFORM_FUNCTION_MARKER), '@'});

    private FirNativeIdentifierChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source = declaration.getSource();
        if (declaration instanceof FirRegularClass) {
            checkNameAndReport(((FirRegularClass) declaration).getName(), source, context, reporter);
            return;
        }
        if (declaration instanceof FirSimpleFunction) {
            checkNameAndReport(((FirSimpleFunction) declaration).getName(), source, context, reporter);
            return;
        }
        if (declaration instanceof FirTypeParameter) {
            checkNameAndReport(((FirTypeParameter) declaration).getName(), source, context, reporter);
            return;
        }
        if (declaration instanceof FirProperty) {
            checkNameAndReport(((FirProperty) declaration).getName(), source, context, reporter);
            return;
        }
        if (declaration instanceof FirTypeAlias) {
            checkNameAndReport(((FirTypeAlias) declaration).getName(), source, context, reporter);
        } else if (declaration instanceof FirValueParameter) {
            checkNameAndReport(((FirValueParameter) declaration).getName(), source, context, reporter);
        } else if (declaration instanceof FirEnumEntry) {
            checkNameAndReport(((FirEnumEntry) declaration).getName(), source, context, reporter);
        }
    }

    private final void checkNameAndReport(Name name2, KtSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        String str;
        if (source == null || (source.getKind() instanceof KtFakeSourceElementKind) || name2.isSpecial()) {
            return;
        }
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String str2 = strAsString;
        if (str2.length() != 0) {
            int i = 0;
            while (true) {
                if (i >= str2.length()) {
                    str = null;
                    break;
                }
                char cCharAt = str2.charAt(i);
                Set<Character> set = invalidChars;
                if (set.contains(Character.valueOf(cCharAt))) {
                    str = "contains illegal characters: " + CollectionsKt.joinToString$default(CollectionsKt.intersect(set, StringsKt.toSet(str2)), "", "\"", "\"", 0, null, null, 56, null);
                    break;
                }
                i++;
            }
        } else {
            str = "should not be empty";
        }
        String str3 = str;
        if (str3 != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirNativeErrors.INSTANCE.getINVALID_CHARACTERS_NATIVE(), str3, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }
}
