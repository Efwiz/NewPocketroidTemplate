package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: AbstractFirReflectionApiCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 \u00182\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003:\u0001\u0018B\u0005¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\tH\u0014J \u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH$J\"\u0010\u0015\u001a\u00020\u00062\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH$¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/AbstractFirReflectionApiCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBasicExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isAllowedKClassMember", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "isAllowedReflectionApi", "containingClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "isReflectionSource", "isWholeReflectionApiAvailable", "report", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractFirReflectionApiCallChecker extends FirExpressionChecker<FirStatement> {
    private static final Set<ClassId> ALLOWED_CLASSES;
    private static final Set<Name> ALLOWED_MEMBER_NAMES;
    private static final ClassId K_CLASS;
    private static final Name K_CLASS_IS_INSTANCE;
    private static final Name K_CLASS_QUALIFIED_NAME;
    private static final Name K_CLASS_SIMPLE_NAME;
    private static final Set<ClassId> K_PROPERTY_CLASSES;

    protected abstract boolean isWholeReflectionApiAvailable(CheckerContext context);

    protected abstract void report(KtSourceElement source, CheckerContext context, DiagnosticReporter reporter);

    protected boolean isAllowedKClassMember(Name name2, CheckerContext context) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(context, "context");
        if (Intrinsics.areEqual(name2, K_CLASS_SIMPLE_NAME) ? true : Intrinsics.areEqual(name2, K_CLASS_IS_INSTANCE)) {
            return true;
        }
        if (Intrinsics.areEqual(name2, K_CLASS_QUALIFIED_NAME)) {
            return ((Boolean) context.get$languageVersionSettings().getFlag(AnalysisFlags.getAllowFullyQualifiedNameInKClass())).booleanValue();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public final void check(FirStatement expression, CheckerContext context, DiagnosticReporter reporter) {
        FirReference calleeReference;
        FirResolvedNamedReference resolved;
        FirExpression dispatchReceiver;
        FirTypeRef typeRef;
        FirClassLikeSymbol<?> classLikeSymbol;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (isWholeReflectionApiAvailable(context) || isReflectionSource(context) || (calleeReference = FirExpressionUtilKt.getCalleeReference(expression)) == null || (resolved = FirReferenceUtilsKt.getResolved(calleeReference)) == null) {
            return;
        }
        FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
        ClassId classId = null;
        FirCallableSymbol firCallableSymbol = resolvedSymbol instanceof FirCallableSymbol ? (FirCallableSymbol) resolvedSymbol : null;
        if (firCallableSymbol == null) {
            return;
        }
        FirQualifiedAccessExpression firQualifiedAccessExpression = expression instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) expression : null;
        if (firQualifiedAccessExpression != null && (dispatchReceiver = firQualifiedAccessExpression.getDispatchReceiver()) != null && (typeRef = dispatchReceiver.getTypeRef()) != null && (classLikeSymbol = FirHelpersKt.toClassLikeSymbol(typeRef, context.getSession())) != null) {
            classId = classLikeSymbol.getClassId();
        }
        if (classId == null || !Intrinsics.areEqual(classId.getPackageFqName(), StandardNames.KOTLIN_REFLECT_FQ_NAME) || isAllowedReflectionApi(firCallableSymbol.getName(), classId, context)) {
            return;
        }
        KtSourceElement source = resolved.getSource();
        if (source == null) {
            source = expression.getSource();
        }
        report(source, context, reporter);
    }

    private final boolean isAllowedReflectionApi(Name name2, ClassId containingClassId, CheckerContext context) {
        return ALLOWED_MEMBER_NAMES.contains(name2) || (Intrinsics.areEqual(containingClassId, K_CLASS) && isAllowedKClassMember(name2, context)) || (((Intrinsics.areEqual(name2.asString(), "get") || Intrinsics.areEqual(name2.asString(), FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX)) && K_PROPERTY_CLASSES.contains(containingClassId)) || ALLOWED_CLASSES.contains(containingClassId));
    }

    private final boolean isReflectionSource(CheckerContext context) {
        FirFile containingFile = context.getContainingFile();
        return containingFile != null && UtilsKt.getPackageFqName(containingFile).startsWith(StandardNames.KOTLIN_REFLECT_FQ_NAME);
    }

    static {
        ClassId classId = ClassId.topLevel(StandardNames.FqNames.kClass.toSafe());
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        K_CLASS = classId;
        Name nameIdentifier = Name.identifier(Namer.METADATA_SIMPLE_NAME);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        K_CLASS_SIMPLE_NAME = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("isInstance");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        K_CLASS_IS_INSTANCE = nameIdentifier2;
        Name nameIdentifier3 = Name.identifier("qualifiedName");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        K_CLASS_QUALIFIED_NAME = nameIdentifier3;
        List listListOf = CollectionsKt.listOf((Object[]) new FqNameUnsafe[]{StandardNames.FqNames.kProperty0, StandardNames.FqNames.kProperty1, StandardNames.FqNames.kProperty2, StandardNames.FqNames.kMutableProperty0, StandardNames.FqNames.kMutableProperty1, StandardNames.FqNames.kMutableProperty2});
        HashSet hashSet = new HashSet();
        Iterator it = listListOf.iterator();
        while (it.hasNext()) {
            hashSet.add(ClassId.topLevel(((FqNameUnsafe) it.next()).toSafe()));
        }
        K_PROPERTY_CLASSES = hashSet;
        List listListOf2 = CollectionsKt.listOf((Object[]) new String[]{Namer.EQUALS_METHOD_NAME, "hashCode", "toString", "invoke", "name"});
        HashSet hashSet2 = new HashSet();
        Iterator it2 = listListOf2.iterator();
        while (it2.hasNext()) {
            hashSet2.add(Name.identifier((String) it2.next()));
        }
        ALLOWED_MEMBER_NAMES = hashSet2;
        List listListOf3 = CollectionsKt.listOf((Object[]) new String[]{"KType", "KTypeParameter", "KTypeProjection", "KTypeProjection.Companion", "KVariance"});
        HashSet hashSet3 = new HashSet();
        Iterator it3 = listListOf3.iterator();
        while (it3.hasNext()) {
            hashSet3.add(new ClassId(StandardNames.KOTLIN_REFLECT_FQ_NAME, new FqName((String) it3.next()), false));
        }
        ALLOWED_CLASSES = hashSet3;
    }
}
