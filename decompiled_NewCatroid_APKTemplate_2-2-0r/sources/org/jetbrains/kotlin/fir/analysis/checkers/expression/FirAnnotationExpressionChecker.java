package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.ConstantArgumentKind;
import org.jetbrains.kotlin.fir.analysis.checkers.FirConstChecksKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.resolve.CallableIdUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.RequireKotlinConstants;

/* JADX INFO: compiled from: FirAnnotationExpressionChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J*\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u000e\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J \u0010\u0018\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J*\u0010\u0019\u001a\u00020\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J@\u0010\u001d\u001a\u00020\r2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\u0010\u001a\u001a\u0004\u0018\u00010\u00072\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00150!2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J \u0010\"\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\"\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010\u000e\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAnnotationExpressionChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAnnotationCallChecker;", "()V", "annotationFqNamesWithVersion", "", "Lorg/jetbrains/kotlin/name/FqName;", "deprecatedSinceKotlinFqName", "sinceKotlinFqName", "versionArgumentName", "Lorg/jetbrains/kotlin/name/Name;", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkAnnotationArgumentWithSubElements", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "checkAnnotationUsedAsAnnotationArgument", "checkAnnotationsWithVersion", "fqName", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "checkDeprecatedSinceKotlin", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "argumentMapping", "", "checkNotAClass", "parseVersionExpressionOrReport", "Lorg/jetbrains/kotlin/config/ApiVersion;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnnotationExpressionChecker extends FirExpressionChecker<FirAnnotationCall> {
    public static final FirAnnotationExpressionChecker INSTANCE = new FirAnnotationExpressionChecker();
    private static final Set<FqName> annotationFqNamesWithVersion;
    private static final FqName deprecatedSinceKotlinFqName;
    private static final FqName sinceKotlinFqName;
    private static final Name versionArgumentName;

    /* JADX INFO: compiled from: FirAnnotationExpressionChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ConstantArgumentKind.values().length];
            try {
                iArr[ConstantArgumentKind.NOT_CONST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ConstantArgumentKind.ENUM_NOT_CONST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ConstantArgumentKind.NOT_KCLASS_LITERAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ConstantArgumentKind.KCLASS_LITERAL_OF_TYPE_PARAMETER_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ConstantArgumentKind.NOT_CONST_VAL_IN_CONST_EXPRESSION.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FirAnnotationExpressionChecker() {
    }

    static {
        Name nameIdentifier = Name.identifier("version");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        versionArgumentName = nameIdentifier;
        deprecatedSinceKotlinFqName = new FqName("kotlin.DeprecatedSinceKotlin");
        FqName fqName = new FqName("kotlin.SinceKotlin");
        sinceKotlinFqName = fqName;
        annotationFqNamesWithVersion = SetsKt.setOf((Object[]) new FqName[]{RequireKotlinConstants.INSTANCE.getFQ_NAME(), fqName});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirAnnotationCall expression, CheckerContext context, DiagnosticReporter reporter) {
        FirExpression expression2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Map<Name, FirExpression> mapping = expression.getArgumentMapping().getMapping();
        FirAnnotationCall firAnnotationCall = expression;
        FqName fqName = CallableIdUtilsKt.fqName(firAnnotationCall, context.getSession());
        for (FirExpression firExpression : mapping.values()) {
            FirNamedArgumentExpression firNamedArgumentExpression = firExpression instanceof FirNamedArgumentExpression ? (FirNamedArgumentExpression) firExpression : null;
            if (firNamedArgumentExpression == null || (expression2 = firNamedArgumentExpression.getExpression()) == null) {
                FirErrorExpression firErrorExpression = firExpression instanceof FirErrorExpression ? (FirErrorExpression) firExpression : null;
                FirExpression expression3 = firErrorExpression != null ? firErrorExpression.getExpression() : null;
                if (expression3 != null) {
                    firExpression = expression3;
                }
            } else {
                firExpression = expression2;
            }
            KtDiagnosticFactory0 ktDiagnosticFactory0CheckAnnotationArgumentWithSubElements = checkAnnotationArgumentWithSubElements(firExpression, context.getSession(), reporter, context);
            if (ktDiagnosticFactory0CheckAnnotationArgumentWithSubElements != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firExpression.getSource(), ktDiagnosticFactory0CheckAnnotationArgumentWithSubElements, context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
        checkAnnotationsWithVersion(fqName, firAnnotationCall, context, reporter);
        checkDeprecatedSinceKotlin(expression.getSource(), fqName, mapping, context, reporter);
        checkAnnotationUsedAsAnnotationArgument(expression, context, reporter);
        checkNotAClass(expression, context, reporter);
    }

    private static final KtDiagnosticFactory0 checkAnnotationArgumentWithSubElements$checkArgumentList(FirSession firSession, DiagnosticReporter diagnosticReporter, CheckerContext checkerContext, FirArgumentList firArgumentList) {
        boolean z = false;
        for (FirExpression firExpression : firArgumentList.getArguments()) {
            KtSourceElement source = firExpression.getSource();
            KtDiagnosticFactory0 ktDiagnosticFactory0CheckAnnotationArgumentWithSubElements = INSTANCE.checkAnnotationArgumentWithSubElements(firExpression, firSession, diagnosticReporter, checkerContext);
            if (ktDiagnosticFactory0CheckAnnotationArgumentWithSubElements != null) {
                if (!Intrinsics.areEqual(ktDiagnosticFactory0CheckAnnotationArgumentWithSubElements, FirErrors.INSTANCE.getANNOTATION_ARGUMENT_MUST_BE_KCLASS_LITERAL())) {
                    z = true;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, source, ktDiagnosticFactory0CheckAnnotationArgumentWithSubElements, checkerContext, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
        if (z) {
            return FirErrors.INSTANCE.getNON_CONST_VAL_USED_IN_CONSTANT_EXPRESSION();
        }
        return null;
    }

    private final KtDiagnosticFactory0 checkAnnotationArgumentWithSubElements(FirExpression expression, FirSession session, DiagnosticReporter reporter, CheckerContext context) {
        if (expression instanceof FirArrayLiteral) {
            return checkAnnotationArgumentWithSubElements$checkArgumentList(session, reporter, context, ((FirArrayLiteral) expression).getArgumentList());
        }
        if (expression instanceof FirVarargArgumentsExpression) {
            Iterator<FirExpression> it = ((FirVarargArgumentsExpression) expression).getArguments().iterator();
            while (it.hasNext()) {
                FirExpression firExpressionUnwrapArgument = FirExpressionUtilKt.unwrapArgument(it.next());
                KtDiagnosticFactory0 ktDiagnosticFactory0CheckAnnotationArgumentWithSubElements = checkAnnotationArgumentWithSubElements(firExpressionUnwrapArgument, session, reporter, context);
                if (ktDiagnosticFactory0CheckAnnotationArgumentWithSubElements != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firExpressionUnwrapArgument.getSource(), ktDiagnosticFactory0CheckAnnotationArgumentWithSubElements, context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
            return null;
        }
        ConstantArgumentKind constantArgumentKindCheckConstantArguments = FirConstChecksKt.checkConstantArguments(expression, session);
        int i = constantArgumentKindCheckConstantArguments == null ? -1 : WhenMappings.$EnumSwitchMapping$0[constantArgumentKindCheckConstantArguments.ordinal()];
        if (i == -1) {
            if (expression instanceof FirFunctionCall) {
                return checkAnnotationArgumentWithSubElements$checkArgumentList(session, reporter, context, ((FirFunctionCall) expression).getArgumentList());
            }
            return null;
        }
        if (i == 1) {
            return FirErrors.INSTANCE.getANNOTATION_ARGUMENT_MUST_BE_CONST();
        }
        if (i == 2) {
            return FirErrors.INSTANCE.getANNOTATION_ARGUMENT_MUST_BE_ENUM_CONST();
        }
        if (i == 3) {
            return FirErrors.INSTANCE.getANNOTATION_ARGUMENT_MUST_BE_KCLASS_LITERAL();
        }
        if (i == 4) {
            return FirErrors.INSTANCE.getANNOTATION_ARGUMENT_KCLASS_LITERAL_OF_TYPE_PARAMETER_ERROR();
        }
        if (i == 5) {
            return FirErrors.INSTANCE.getNON_CONST_VAL_USED_IN_CONSTANT_EXPRESSION();
        }
        throw new NoWhenBranchMatchedException();
    }

    private final ApiVersion parseVersionExpressionOrReport(FirExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        FirConstExpression firConstExpression = expression instanceof FirConstExpression ? (FirConstExpression) expression : null;
        if (firConstExpression == null) {
            FirNamedArgumentExpression firNamedArgumentExpression = expression instanceof FirNamedArgumentExpression ? (FirNamedArgumentExpression) expression : null;
            FirExpression expression2 = firNamedArgumentExpression != null ? firNamedArgumentExpression.getExpression() : null;
            firConstExpression = expression2 instanceof FirConstExpression ? (FirConstExpression) expression2 : null;
            if (firConstExpression == null) {
                return null;
            }
        }
        Object value = firConstExpression.getValue();
        String str = value instanceof String ? (String) value : null;
        if (str == null) {
            return null;
        }
        if (!RequireKotlinConstants.INSTANCE.getVERSION_REGEX().matches(str)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getILLEGAL_KOTLIN_VERSION_STRING_VALUE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return null;
        }
        ApiVersion apiVersion = ApiVersion.INSTANCE.parse(str);
        if (apiVersion == null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getILLEGAL_KOTLIN_VERSION_STRING_VALUE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        return apiVersion;
    }

    private final void checkAnnotationsWithVersion(FqName fqName, FirAnnotation annotation, CheckerContext context, DiagnosticReporter reporter) {
        FirExpression firExpressionFindArgumentByName;
        ApiVersion versionExpressionOrReport;
        if (CollectionsKt.contains(annotationFqNamesWithVersion, fqName) && (firExpressionFindArgumentByName = FirAnnotationUtilsKt.findArgumentByName(annotation, versionArgumentName)) != null && (versionExpressionOrReport = parseVersionExpressionOrReport(firExpressionFindArgumentByName, context, reporter)) != null && Intrinsics.areEqual(fqName, sinceKotlinFqName)) {
            ApiVersion apiVersion = FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession()).getApiVersion();
            if (versionExpressionOrReport.compareTo(apiVersion) > 0) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firExpressionFindArgumentByName.getSource(), FirErrors.INSTANCE.getNEWER_VERSION_IN_SINCE_KOTLIN(), apiVersion.getVersionString(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:56:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:76:0x0092 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:77:0x00af A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:78:0x00a6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:79:0x009d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:81:0x00ad A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:82:0x009a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:83:0x00b7 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:84:0x0098 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:86:0x0034 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:87:0x0034 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:89:0x0034 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:91:0x0034 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    private final void checkDeprecatedSinceKotlin(KtSourceElement source, FqName fqName, Map<Name, ? extends FirExpression> argumentMapping, CheckerContext context, DiagnosticReporter reporter) {
        boolean z;
        ApiVersion versionExpressionOrReport;
        int iHashCode;
        if (Intrinsics.areEqual(fqName, deprecatedSinceKotlinFqName)) {
            if (argumentMapping.size() == 0) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getDEPRECATED_SINCE_KOTLIN_WITHOUT_ARGUMENTS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            ApiVersion apiVersion = null;
            ApiVersion apiVersion2 = null;
            ApiVersion apiVersion3 = null;
            for (Map.Entry<Name, ? extends FirExpression> entry : argumentMapping.entrySet()) {
                Name key = entry.getKey();
                FirExpression value = entry.getValue();
                String identifier = key.getIdentifier();
                Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
                int iHashCode2 = identifier.hashCode();
                if (iHashCode2 != -2027506434) {
                    if (iHashCode2 != -855220304) {
                        if (iHashCode2 == 1624782354 && identifier.equals("errorSince")) {
                            versionExpressionOrReport = parseVersionExpressionOrReport(value, context, reporter);
                            if (versionExpressionOrReport != null) {
                                iHashCode = identifier.hashCode();
                                if (iHashCode != -2027506434) {
                                    if (iHashCode != -855220304) {
                                        if (iHashCode != 1624782354 && identifier.equals("errorSince")) {
                                            apiVersion2 = versionExpressionOrReport;
                                        }
                                    } else if (identifier.equals("hiddenSince")) {
                                        apiVersion3 = versionExpressionOrReport;
                                    }
                                } else if (identifier.equals("warningSince")) {
                                    apiVersion = versionExpressionOrReport;
                                }
                            }
                        }
                    } else if (identifier.equals("hiddenSince")) {
                        versionExpressionOrReport = parseVersionExpressionOrReport(value, context, reporter);
                        if (versionExpressionOrReport != null) {
                            iHashCode = identifier.hashCode();
                            if (iHashCode != -2027506434) {
                                if (iHashCode != -855220304) {
                                    if (iHashCode != 1624782354) {
                                        apiVersion2 = versionExpressionOrReport;
                                    }
                                } else if (identifier.equals("hiddenSince")) {
                                    apiVersion3 = versionExpressionOrReport;
                                }
                            } else if (identifier.equals("warningSince")) {
                                apiVersion = versionExpressionOrReport;
                            }
                        }
                    }
                } else if (identifier.equals("warningSince")) {
                    versionExpressionOrReport = parseVersionExpressionOrReport(value, context, reporter);
                    if (versionExpressionOrReport != null) {
                        iHashCode = identifier.hashCode();
                        if (iHashCode != -2027506434) {
                            if (iHashCode != -855220304) {
                                if (iHashCode != 1624782354) {
                                    apiVersion2 = versionExpressionOrReport;
                                }
                            } else if (identifier.equals("hiddenSince")) {
                                apiVersion3 = versionExpressionOrReport;
                            }
                        } else if (identifier.equals("warningSince")) {
                            apiVersion = versionExpressionOrReport;
                        }
                    }
                }
            }
            if (apiVersion == null) {
                z = false;
            } else {
                z = apiVersion2 != null && apiVersion.compareTo(apiVersion2) > 0;
                if (apiVersion3 != null && !z) {
                    if (apiVersion.compareTo(apiVersion3) > 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                }
            }
            if (apiVersion2 != null && apiVersion3 != null && !z) {
                z = apiVersion2.compareTo(apiVersion3) > 0;
            }
            if (z) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getDEPRECATED_SINCE_KOTLIN_WITH_UNORDERED_VERSIONS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final void checkAnnotationUsedAsAnnotationArgument(FirAnnotationCall expression, CheckerContext context, DiagnosticReporter reporter) {
        Iterator<FirExpression> it = expression.getArgumentList().getArguments().iterator();
        while (it.hasNext()) {
            Iterator<FirAnnotation> it2 = FirExpressionUtilKt.unwrapArgument(it.next()).getAnnotations().iterator();
            while (it2.hasNext()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, it2.next().getSource(), FirErrors.INSTANCE.getANNOTATION_USED_AS_ANNOTATION_ARGUMENT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final void checkNotAClass(FirAnnotationCall expression, CheckerContext context, DiagnosticReporter reporter) {
        FirTypeRef annotationTypeRef = expression.getAnnotationTypeRef();
        if (!(expression.getCalleeReference() instanceof FirErrorNamedReference) || (annotationTypeRef instanceof FirErrorTypeRef) || (FirTypeUtilsKt.getConeType(annotationTypeRef) instanceof ConeClassLikeType)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationTypeRef.getSource(), FirErrors.INSTANCE.getNOT_A_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }
}
