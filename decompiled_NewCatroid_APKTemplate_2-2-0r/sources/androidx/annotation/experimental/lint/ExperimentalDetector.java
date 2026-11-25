package androidx.annotation.experimental.lint;

import com.android.tools.lint.detector.api.AnnotationUsageType;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.ConstantEvaluator;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Lint;
import com.android.tools.lint.detector.api.LintFix;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.SourceCodeScanner;
import com.google.android.gms.cast.MediaError;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.uast.UAnnotated;
import org.jetbrains.uast.UAnnotation;
import org.jetbrains.uast.UClass;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UExpression;
import org.jetbrains.uast.UReferenceExpression;
import org.jetbrains.uast.UastUtils;

/* JADX INFO: compiled from: ExperimentalDetector.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/annotation-experimental-1.1.0.aar:lint.jar:androidx/annotation/experimental/lint/ExperimentalDetector.class */
@Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 $2\u00020\u00012\u00020\u0002:\u0001$B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005H\u0016J.\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0002J\u001a\u0010\u0010\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0006H\u0002J.\u0010\u0012\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00062\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0002J(\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0002J|\u0010\u0018\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u00062\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\f0\u00052\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\f0\u00052\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\f0\u00052\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\f0\u0005H\u0016¨\u0006%"}, d2 = {"Landroidx/annotation/experimental/lint/ExperimentalDetector;", "Lcom/android/tools/lint/detector/api/Detector;", "Lcom/android/tools/lint/detector/api/SourceCodeScanner;", "()V", "applicableAnnotations", "", "", "checkExperimentalUsage", "", "context", "Lcom/android/tools/lint/detector/api/JavaContext;", "annotation", "Lorg/jetbrains/uast/UAnnotation;", "usage", "Lorg/jetbrains/uast/UElement;", "useAnnotationNames", "extractAttribute", "name", "hasOrUsesAnnotation", "", "annotationName", "report", CrashHianalyticsData.MESSAGE, FirebaseAnalytics.Param.LEVEL, "visitAnnotationUsage", ModuleXmlParser.TYPE, "Lcom/android/tools/lint/detector/api/AnnotationUsageType;", "qualifiedName", "method", "Lcom/intellij/psi/PsiMethod;", "referenced", "Lcom/intellij/psi/PsiElement;", "annotations", "allMemberAnnotations", "allClassAnnotations", "allPackageAnnotations", "Companion", "annotation-experimental-lint"})
public final class ExperimentalDetector extends Detector implements SourceCodeScanner {
    private static final String KOTLIN_EXPERIMENTAL_ANNOTATION = "kotlin.Experimental";
    private static final String KOTLIN_USE_EXPERIMENTAL_ANNOTATION = "kotlin.UseExperimental";
    private static final String KOTLIN_OPT_IN_ANNOTATION = "kotlin.OptIn";
    private static final String KOTLIN_REQUIRES_OPT_IN_ANNOTATION = "kotlin.RequiresOptIn";
    private static final String JAVA_EXPERIMENTAL_ANNOTATION = "androidx.annotation.experimental.Experimental";
    private static final String JAVA_USE_EXPERIMENTAL_ANNOTATION = "androidx.annotation.experimental.UseExperimental";
    private static final String JAVA_REQUIRES_OPT_IN_ANNOTATION = "androidx.annotation.RequiresOptIn";
    private static final String JAVA_OPT_IN_ANNOTATION = "androidx.annotation.OptIn";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private static final Implementation IMPLEMENTATION = new Implementation(ExperimentalDetector.class, Scope.JAVA_FILE_SCOPE);

    @NotNull
    private static final Issue ISSUE_ERROR = INSTANCE.issueForLevel("error", Severity.ERROR);

    @NotNull
    private static final Issue ISSUE_WARNING = INSTANCE.issueForLevel(CompilerOptions.WARNING, Severity.WARNING);

    @NotNull
    private static final List<Issue> ISSUES = CollectionsKt.listOf((Object[]) new Issue[]{ISSUE_ERROR, ISSUE_WARNING});

    @Nullable
    public List<String> applicableAnnotations() {
        return CollectionsKt.listOf((Object[]) new String[]{JAVA_EXPERIMENTAL_ANNOTATION, KOTLIN_EXPERIMENTAL_ANNOTATION, JAVA_REQUIRES_OPT_IN_ANNOTATION, KOTLIN_REQUIRES_OPT_IN_ANNOTATION});
    }

    /* JADX WARN: Code duplicated, block: B:19:0x00cc  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public void visitAnnotationUsage(@NotNull JavaContext context, @NotNull UElement usage, @NotNull AnnotationUsageType type, @NotNull UAnnotation annotation, @NotNull String qualifiedName, @Nullable PsiMethod method, @Nullable PsiElement referenced, @NotNull List<? extends UAnnotation> annotations, @NotNull List<? extends UAnnotation> allMemberAnnotations, @NotNull List<? extends UAnnotation> allClassAnnotations, @NotNull List<? extends UAnnotation> allPackageAnnotations) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(usage, "usage");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(allMemberAnnotations, "allMemberAnnotations");
        Intrinsics.checkNotNullParameter(allClassAnnotations, "allClassAnnotations");
        Intrinsics.checkNotNullParameter(allPackageAnnotations, "allPackageAnnotations");
        switch (qualifiedName.hashCode()) {
            case -1859570192:
                if (!qualifiedName.equals(JAVA_REQUIRES_OPT_IN_ANNOTATION)) {
                }
                checkExperimentalUsage(context, annotation, usage, CollectionsKt.listOf((Object[]) new String[]{JAVA_USE_EXPERIMENTAL_ANNOTATION, JAVA_OPT_IN_ANNOTATION}));
                break;
            case -1652642603:
                if (!qualifiedName.equals(KOTLIN_EXPERIMENTAL_ANNOTATION)) {
                }
                if (!Lint.isKotlin(usage.getSourcePsi())) {
                    checkExperimentalUsage(context, annotation, usage, CollectionsKt.listOf((Object[]) new String[]{KOTLIN_USE_EXPERIMENTAL_ANNOTATION, KOTLIN_OPT_IN_ANNOTATION, JAVA_USE_EXPERIMENTAL_ANNOTATION, JAVA_OPT_IN_ANNOTATION}));
                }
                break;
            case 1439237597:
                if (!qualifiedName.equals(KOTLIN_REQUIRES_OPT_IN_ANNOTATION)) {
                }
                if (!Lint.isKotlin(usage.getSourcePsi())) {
                    checkExperimentalUsage(context, annotation, usage, CollectionsKt.listOf((Object[]) new String[]{KOTLIN_USE_EXPERIMENTAL_ANNOTATION, KOTLIN_OPT_IN_ANNOTATION, JAVA_USE_EXPERIMENTAL_ANNOTATION, JAVA_OPT_IN_ANNOTATION}));
                }
                break;
            case 1943420212:
                if (!qualifiedName.equals(JAVA_EXPERIMENTAL_ANNOTATION)) {
                }
                checkExperimentalUsage(context, annotation, usage, CollectionsKt.listOf((Object[]) new String[]{JAVA_USE_EXPERIMENTAL_ANNOTATION, JAVA_OPT_IN_ANNOTATION}));
                break;
        }
    }

    private final void checkExperimentalUsage(JavaContext context, UAnnotation annotation, UElement usage, List<String> useAnnotationNames) {
        String useAnnotation;
        UElement uastParent = annotation.getUastParent();
        if (!(uastParent instanceof UClass)) {
            uastParent = null;
        }
        UClass uClass = (UClass) uastParent;
        if (uClass != null && (useAnnotation = uClass.getQualifiedName()) != null && !hasOrUsesAnnotation(context, usage, useAnnotation, useAnnotationNames)) {
            String level = extractAttribute(annotation, FirebaseAnalytics.Param.LEVEL);
            if (level != null) {
                report(context, usage, "\n                    This declaration is opt-in and its usage should be marked with\n                    '@" + useAnnotation + "' or '@OptIn(markerClass = " + useAnnotation + ".class)'\n                ", level);
            } else {
                report(context, (UElement) annotation, "\n                    Failed to extract attribute \"level\" from annotation\n                ", MediaError.ERROR_TYPE_ERROR);
            }
        }
    }

    private final String extractAttribute(UAnnotation annotation, String name2) {
        UExpression uExpressionFindAttributeValue = annotation.findAttributeValue(name2);
        if (!(uExpressionFindAttributeValue instanceof UReferenceExpression)) {
            uExpressionFindAttributeValue = null;
        }
        Object objEvaluate = new ConstantEvaluator().evaluate((UReferenceExpression) uExpressionFindAttributeValue);
        if (!(objEvaluate instanceof PsiField)) {
            objEvaluate = null;
        }
        PsiField psiField = (PsiField) objEvaluate;
        if (psiField != null) {
            return psiField.getName();
        }
        return null;
    }

    private final boolean hasOrUsesAnnotation(JavaContext context, UElement usage, String annotationName, List<String> useAnnotationNames) {
        UAnnotated parentOfType$default;
        boolean z;
        boolean z2;
        boolean z3;
        if (usage instanceof UAnnotated) {
            parentOfType$default = (UAnnotated) usage;
        } else {
            parentOfType$default = UastUtils.getParentOfType$default(usage, UAnnotated.class, false, 2, (Object) null);
        }
        while (true) {
            UAnnotated element = parentOfType$default;
            if (element != null) {
                Iterable annotations = context.getEvaluator().getAllAnnotations(element, false);
                Iterable $this$any$iv = annotations;
                if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
                    Iterator it = $this$any$iv.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Object element$iv = it.next();
                            UAnnotation it2 = (UAnnotation) element$iv;
                            if (Intrinsics.areEqual(annotationName, it2.getQualifiedName())) {
                                z = true;
                                break;
                            }
                        } else {
                            z = false;
                            break;
                        }
                    }
                } else {
                    z = false;
                }
                boolean matchName = z;
                if (matchName) {
                    return true;
                }
                Iterable $this$any$iv2 = annotations;
                if (!($this$any$iv2 instanceof Collection) || !((Collection) $this$any$iv2).isEmpty()) {
                    Iterator it3 = $this$any$iv2.iterator();
                    while (true) {
                        if (it3.hasNext()) {
                            Object element$iv2 = it3.next();
                            UAnnotation annotation = (UAnnotation) element$iv2;
                            String qualifiedName = annotation.getQualifiedName();
                            if (qualifiedName != null && useAnnotationNames.contains(qualifiedName) && ExperimentalDetectorKt.hasMatchingAttributeValueClass(annotation, context, "markerClass", annotationName)) {
                                z3 = true;
                            } else {
                                z3 = false;
                            }
                            if (z3) {
                                z2 = true;
                                break;
                            }
                        } else {
                            z2 = false;
                            break;
                        }
                    }
                } else {
                    z2 = false;
                }
                boolean matchUse = z2;
                if (matchUse) {
                    return true;
                }
                parentOfType$default = UastUtils.getParentOfType$default((UElement) element, UAnnotated.class, false, 2, (Object) null);
            } else {
                return false;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final void report(JavaContext context, UElement usage, String message, String level) {
        Issue issue;
        switch (level.hashCode()) {
            case 66247144:
                if (level.equals(MediaError.ERROR_TYPE_ERROR)) {
                    issue = ISSUE_ERROR;
                    break;
                }
                throw new IllegalArgumentException("Level was \"" + level + "\" but must be one of: ERROR, WARNING");
            case 1842428796:
                if (level.equals("WARNING")) {
                    issue = ISSUE_WARNING;
                    break;
                }
                throw new IllegalArgumentException("Level was \"" + level + "\" but must be one of: ERROR, WARNING");
            default:
                throw new IllegalArgumentException("Level was \"" + level + "\" but must be one of: ERROR, WARNING");
        }
        Issue issue2 = issue;
        JavaContext.report$default(context, issue2, usage, context.getNameLocation(usage), StringsKt.trimIndent(message), (LintFix) null, 16, (Object) null);
    }

    /* JADX INFO: compiled from: ExperimentalDetector.kt */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/annotation-experimental-1.1.0.aar:lint.jar:androidx/annotation/experimental/lint/ExperimentalDetector$Companion.class */
    @Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u001bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0010X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0010X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0010X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0010X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0010X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Landroidx/annotation/experimental/lint/ExperimentalDetector$Companion;", "", "()V", "IMPLEMENTATION", "Lcom/android/tools/lint/detector/api/Implementation;", "ISSUES", "", "Lcom/android/tools/lint/detector/api/Issue;", "getISSUES", "()Ljava/util/List;", "ISSUE_ERROR", "getISSUE_ERROR", "()Lcom/android/tools/lint/detector/api/Issue;", "ISSUE_WARNING", "getISSUE_WARNING", "JAVA_EXPERIMENTAL_ANNOTATION", "", "JAVA_OPT_IN_ANNOTATION", "JAVA_REQUIRES_OPT_IN_ANNOTATION", "JAVA_USE_EXPERIMENTAL_ANNOTATION", "KOTLIN_EXPERIMENTAL_ANNOTATION", "KOTLIN_OPT_IN_ANNOTATION", "KOTLIN_REQUIRES_OPT_IN_ANNOTATION", "KOTLIN_USE_EXPERIMENTAL_ANNOTATION", "issueForLevel", FirebaseAnalytics.Param.LEVEL, "severity", "Lcom/android/tools/lint/detector/api/Severity;", "annotation-experimental-lint"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Issue issueForLevel(String level, Severity severity) {
            return Issue.Companion.create("UnsafeOptInUsage" + StringsKt.capitalize(level), "Unsafe opt-in usage intended to be " + level + "-level severity", "\n                This API has been flagged as opt-in with " + level + "-level severity.\n\n                Any declaration annotated with this marker is considered part of an unstable or\n                otherwise non-standard API surface and its call sites should accept the opt-in\n                aspect of it either by using `@OptIn` or by being annotated with that marker\n                themselves, effectively causing further propagation of the opt-in aspect.\n            ", Category.CORRECTNESS, 4, severity, ExperimentalDetector.IMPLEMENTATION);
        }

        @NotNull
        public final Issue getISSUE_ERROR() {
            return ExperimentalDetector.ISSUE_ERROR;
        }

        @NotNull
        public final Issue getISSUE_WARNING() {
            return ExperimentalDetector.ISSUE_WARNING;
        }

        @NotNull
        public final List<Issue> getISSUES() {
            return ExperimentalDetector.ISSUES;
        }
    }
}
