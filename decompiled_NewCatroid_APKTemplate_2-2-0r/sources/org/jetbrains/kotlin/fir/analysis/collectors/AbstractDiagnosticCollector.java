package org.jetbrains.kotlin.fir.analysis.collectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.SessionHolder;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolver;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: AbstractDiagnosticCollector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\u0010\nJ\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\bJ\u000e\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\bJ\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\tH$R \u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/AbstractDiagnosticCollector;", "Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "createComponents", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "Lorg/jetbrains/kotlin/fir/analysis/collectors/DiagnosticCollectorComponents;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lkotlin/jvm/functions/Function1;)V", "getCreateComponents", "()Lkotlin/jvm/functions/Function1;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "collectDiagnostics", "", "firDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "reporter", "collectDiagnosticsInSettings", "createVisitor", "Lorg/jetbrains/kotlin/fir/analysis/collectors/CheckerRunningDiagnosticCollectorVisitor;", "components", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractDiagnosticCollector implements SessionHolder {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String SUPPRESS_ALL_ERRORS = "errors";
    public static final String SUPPRESS_ALL_INFOS = "infos";
    public static final String SUPPRESS_ALL_WARNINGS = "warnings";
    private static final Name SUPPRESS_NAMES_NAME;
    private final Function1<DiagnosticReporter, DiagnosticCollectorComponents> createComponents;
    private final ScopeSession scopeSession;
    private final FirSession session;

    protected abstract CheckerRunningDiagnosticCollectorVisitor createVisitor(DiagnosticCollectorComponents components);

    /* JADX WARN: Multi-variable type inference failed */
    public AbstractDiagnosticCollector(FirSession session, ScopeSession scopeSession, Function1<? super DiagnosticReporter, DiagnosticCollectorComponents> createComponents) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(createComponents, "createComponents");
        this.session = session;
        this.scopeSession = scopeSession;
        this.createComponents = createComponents;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.SessionHolder
    public FirSession getSession() {
        return this.session;
    }

    public /* synthetic */ AbstractDiagnosticCollector(FirSession firSession, ScopeSession scopeSession, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, (i & 2) != 0 ? new ScopeSession() : scopeSession, function1);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.SessionHolder
    public ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    protected final Function1<DiagnosticReporter, DiagnosticCollectorComponents> getCreateComponents() {
        return this.createComponents;
    }

    public final void collectDiagnosticsInSettings(DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        createVisitor(this.createComponents.invoke(reporter)).checkSettings();
    }

    public final void collectDiagnostics(FirDeclaration firDeclaration, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(firDeclaration, "firDeclaration");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        CheckerRunningDiagnosticCollectorVisitor checkerRunningDiagnosticCollectorVisitorCreateVisitor = createVisitor(this.createComponents.invoke(reporter));
        FirLazyDeclarationResolver lazyDeclarationResolver = FirLazyDeclarationResolverKt.getLazyDeclarationResolver(getSession());
        boolean lazyResolveContractChecksEnabled = lazyDeclarationResolver.getLazyResolveContractChecksEnabled();
        lazyDeclarationResolver.setLazyResolveContractChecksEnabled(false);
        try {
            firDeclaration.accept(checkerRunningDiagnosticCollectorVisitorCreateVisitor, null);
            Unit unit = Unit.INSTANCE;
        } finally {
            lazyDeclarationResolver.setLazyResolveContractChecksEnabled(lazyResolveContractChecksEnabled);
        }
    }

    /* JADX INFO: compiled from: AbstractDiagnosticCollector.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004H\u0002J\u0016\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/collectors/AbstractDiagnosticCollector$Companion;", "", "()V", "SUPPRESS_ALL_ERRORS", "", "SUPPRESS_ALL_INFOS", "SUPPRESS_ALL_WARNINGS", "SUPPRESS_NAMES_NAME", "Lorg/jetbrains/kotlin/name/Name;", "correctDiagnosticCase", "diagnostic", "getDiagnosticsSuppressedForContainer", "", "annotationContainer", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private final String correctDiagnosticCase(String diagnostic) {
            int iHashCode = diagnostic.hashCode();
            if (iHashCode != -1294635157) {
                if (iHashCode != 100348293) {
                    if (iHashCode == 498091095 && diagnostic.equals(AbstractDiagnosticCollector.SUPPRESS_ALL_WARNINGS)) {
                        return diagnostic;
                    }
                } else if (diagnostic.equals(AbstractDiagnosticCollector.SUPPRESS_ALL_INFOS)) {
                    return diagnostic;
                }
            } else if (diagnostic.equals("errors")) {
                return diagnostic;
            }
            String upperCase = diagnostic.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
            return upperCase;
        }

        public final List<String> getDiagnosticsSuppressedForContainer(FirAnnotationContainer annotationContainer) {
            FirExpression firExpressionFindArgumentByName;
            List<FirExpression> listUnwrapVarargValue;
            Intrinsics.checkNotNullParameter(annotationContainer, "annotationContainer");
            ArrayList arrayList = null;
            for (FirAnnotation firAnnotation : annotationContainer.getAnnotations()) {
                ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firAnnotation.getAnnotationTypeRef());
                ConeClassLikeType coneClassLikeType = coneType instanceof ConeClassLikeType ? (ConeClassLikeType) coneType : null;
                if (coneClassLikeType != null && Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), StandardClassIds.Annotations.INSTANCE.getSuppress()) && (firExpressionFindArgumentByName = FirAnnotationUtilsKt.findArgumentByName(firAnnotation, AbstractDiagnosticCollector.SUPPRESS_NAMES_NAME)) != null && (listUnwrapVarargValue = FirAnnotationUtilsKt.unwrapVarargValue(firExpressionFindArgumentByName)) != null) {
                    for (FirExpression firExpression : listUnwrapVarargValue) {
                        FirConstExpression firConstExpression = firExpression instanceof FirConstExpression ? (FirConstExpression) firExpression : null;
                        Object value = firConstExpression != null ? firConstExpression.getValue() : null;
                        String str = value instanceof String ? (String) value : null;
                        if (str != null) {
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            arrayList.add(correctDiagnosticCase(str));
                        }
                    }
                }
            }
            return arrayList;
        }
    }

    static {
        Name nameIdentifier = Name.identifier("names");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        SUPPRESS_NAMES_NAME = nameIdentifier;
    }
}
