package androidx.activity.lint;

import com.android.builder.model.AndroidLibrary;
import com.android.builder.model.MavenCoordinates;
import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.GradleContext;
import com.android.tools.lint.detector.api.GradleScanner;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.LintFix;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.Project;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.beust.jcommander.Parameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KFunction;
import kotlin.reflect.KProperty1;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.KCallablesJvm;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UIdentifier;

/* JADX INFO: compiled from: ActivityResultFragmentVersionDetector.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/activity-1.7.2.aar:lint.jar:androidx/activity/lint/ActivityResultFragmentVersionDetector.class */
@Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 ;2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001;B\u0005¢\u0006\u0002\u0010\u0004J$\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u001bH\u0002J\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020 H\u0002JB\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020 2\u0006\u0010%\u001a\u00020 2\u0006\u0010&\u001a\u00020 2\b\u0010'\u001a\u0004\u0018\u00010 2\u0006\u0010(\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\u001dH\u0016J\u0018\u0010*\u001a\u00020\u00162\u0006\u0010+\u001a\u00020,2\u0006\u0010\"\u001a\u00020#H\u0002J\u0018\u0010-\u001a\u00020\u00162\u0006\u0010+\u001a\u00020,2\u0006\u0010\"\u001a\u00020#H\u0002J\u0012\u0010.\u001a\u0004\u0018\u00010/2\u0006\u0010\"\u001a\u000200H\u0016J\u0018\u00101\u001a\u0012\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020403\u0018\u000102H\u0016J\u0018\u00105\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u00106\u001a\u00020 H\u0002J\u0010\u00107\u001a\u00020 2\u0006\u0010%\u001a\u00020 H\u0002J\"\u00108\u001a\u00020\u00162\u0006\u0010%\u001a\u00020 2\u0006\u0010\"\u001a\u00020#2\b\b\u0002\u00109\u001a\u00020\u0006H\u0002J\u0010\u0010:\u001a\u00020\u00062\u0006\u0010+\u001a\u00020,H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR*\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006<"}, d2 = {"Landroidx/activity/lint/ActivityResultFragmentVersionDetector;", "Lcom/android/tools/lint/detector/api/Detector;", "Lcom/android/tools/lint/detector/api/Detector$UastScanner;", "Lcom/android/tools/lint/detector/api/GradleScanner;", "()V", "checkedImplementationDependencies", "", "expression", "Lorg/jetbrains/uast/UCallExpression;", "getExpression", "()Lorg/jetbrains/uast/UCallExpression;", "setExpression", "(Lorg/jetbrains/uast/UCallExpression;)V", "locations", "Ljava/util/ArrayList;", "Lcom/android/tools/lint/detector/api/Location;", "Lkotlin/collections/ArrayList;", "getLocations", "()Ljava/util/ArrayList;", "setLocations", "(Ljava/util/ArrayList;)V", "addIndirectAndroidLibraries", "", "libraries", "", "Lcom/android/builder/model/AndroidLibrary;", "result", "", "callFunctionWithReflection", "", "caller", "functionName", "", "checkDslPropertyAssignment", "context", "Lcom/android/tools/lint/detector/api/GradleContext;", "property", "value", "parent", "parentParent", "valueCookie", "statementCookie", "checkWithNewLintVersion", "project", "Lcom/android/tools/lint/detector/api/Project;", "checkWithOldLintVersion", "createUastHandler", "Lcom/android/tools/lint/client/api/UElementHandler;", "Lcom/android/tools/lint/detector/api/JavaContext;", "getApplicableUastTypes", "", "Ljava/lang/Class;", "Lorg/jetbrains/uast/UElement;", "getMemberWithReflection", "memberName", "getStringLiteralValue", "reportIssue", "removeQuotes", "useNewLintVersion", "Companion", "activity-lint"})
@SourceDebugExtension({"SMAP\nActivityResultFragmentVersionDetector.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActivityResultFragmentVersionDetector.kt\nandroidx/activity/lint/ActivityResultFragmentVersionDetector\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,228:1\n1855#2,2:229\n1855#2:231\n1855#2,2:232\n1856#2:234\n1855#2,2:235\n1855#2,2:237\n1855#2,2:239\n1855#2,2:241\n*S KotlinDebug\n*F\n+ 1 ActivityResultFragmentVersionDetector.kt\nandroidx/activity/lint/ActivityResultFragmentVersionDetector\n*L\n110#1:229,2\n123#1:231\n124#1:232,2\n123#1:234\n146#1:235,2\n156#1:237,2\n166#1:239,2\n202#1:241,2\n*E\n"})
public final class ActivityResultFragmentVersionDetector extends Detector implements Detector.UastScanner, GradleScanner {

    @NotNull
    private ArrayList<Location> locations = new ArrayList<>();
    public UCallExpression expression;
    private boolean checkedImplementationDependencies;

    @NotNull
    public static final String FRAGMENT_VERSION = "1.3.0";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Issue ISSUE = Issue.Companion.create$default(Issue.Companion, "InvalidFragmentVersionForActivityResult", "Update to Fragment 1.3.0 to use ActivityResult APIs", "In order to use the ActivityResult APIs you must upgrade your \\\n                Fragment version to 1.3.0. Previous versions of FragmentActivity \\\n                failed to call super.onRequestPermissionsResult() and used invalid request codes", new Implementation(ActivityResultFragmentVersionDetector.class, EnumSet.of(Scope.JAVA_FILE, Scope.GRADLE_FILE), new EnumSet[]{Scope.JAVA_FILE_SCOPE, Scope.GRADLE_SCOPE}), (String) null, Category.CORRECTNESS, 0, Severity.FATAL, false, (Boolean) null, (EnumSet) null, (Collection) null, 3920, (Object) null).addMoreInfo("https://developer.android.com/training/permissions/requesting#make-the-request");

    /* JADX INFO: compiled from: ActivityResultFragmentVersionDetector.kt */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/activity-1.7.2.aar:lint.jar:androidx/activity/lint/ActivityResultFragmentVersionDetector$Companion.class */
    @Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Landroidx/activity/lint/ActivityResultFragmentVersionDetector$Companion;", "", "()V", "FRAGMENT_VERSION", "", "ISSUE", "Lcom/android/tools/lint/detector/api/Issue;", "getISSUE", "()Lcom/android/tools/lint/detector/api/Issue;", "activity-lint"})
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final Issue getISSUE() {
            return ActivityResultFragmentVersionDetector.ISSUE;
        }
    }

    @NotNull
    public final ArrayList<Location> getLocations() {
        return this.locations;
    }

    public final void setLocations(@NotNull ArrayList<Location> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.locations = arrayList;
    }

    @NotNull
    public final UCallExpression getExpression() {
        UCallExpression uCallExpression = this.expression;
        if (uCallExpression != null) {
            return uCallExpression;
        }
        Intrinsics.throwUninitializedPropertyAccessException("expression");
        return null;
    }

    public final void setExpression(@NotNull UCallExpression uCallExpression) {
        Intrinsics.checkNotNullParameter(uCallExpression, "<set-?>");
        this.expression = uCallExpression;
    }

    @Nullable
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        return CollectionsKt.listOf(UCallExpression.class);
    }

    @Nullable
    public UElementHandler createUastHandler(@NotNull final JavaContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return new UElementHandler() { // from class: androidx.activity.lint.ActivityResultFragmentVersionDetector.createUastHandler.1
            public void visitCallExpression(@NotNull UCallExpression node) {
                Intrinsics.checkNotNullParameter(node, "node");
                UIdentifier methodIdentifier = node.getMethodIdentifier();
                if (!Intrinsics.areEqual(methodIdentifier != null ? methodIdentifier.getName() : null, "registerForActivityResult")) {
                    return;
                }
                ActivityResultFragmentVersionDetector.this.setExpression(node);
                ActivityResultFragmentVersionDetector.this.getLocations().add(context.getLocation((UElement) node));
            }
        };
    }

    public void checkDslPropertyAssignment(@NotNull GradleContext context, @NotNull String property, @NotNull String value, @NotNull String parent, @Nullable String parentParent, @NotNull Object valueCookie, @NotNull Object statementCookie) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(valueCookie, "valueCookie");
        Intrinsics.checkNotNullParameter(statementCookie, "statementCookie");
        if (this.locations.isEmpty()) {
            return;
        }
        if (Intrinsics.areEqual(property, "api")) {
            reportIssue$default(this, value, context, false, 4, null);
            return;
        }
        if (!this.checkedImplementationDependencies) {
            Project project = context.getProject();
            if (useNewLintVersion(project)) {
                checkWithNewLintVersion(project, context);
            } else {
                checkWithOldLintVersion(project, context);
            }
        }
    }

    private final boolean useNewLintVersion(Project project) {
        Iterable $this$forEach$iv = KClasses.getMemberFunctions(Reflection.getOrCreateKotlinClass(project.getClass()));
        for (Object element$iv : $this$forEach$iv) {
            KFunction function = (KFunction) element$iv;
            if (Intrinsics.areEqual(function.getName(), "getBuildVariant")) {
                return true;
            }
        }
        return false;
    }

    private final void checkWithNewLintVersion(Project project, GradleContext context) {
        Object buildVariant = callFunctionWithReflection(project, "getBuildVariant");
        Object mainArtifact = getMemberWithReflection(buildVariant, "mainArtifact");
        Object dependencies = getMemberWithReflection(mainArtifact, "dependencies");
        Object all = callFunctionWithReflection(dependencies, "getAll");
        Intrinsics.checkNotNull(all, "null cannot be cast to non-null type java.util.ArrayList<*>{ kotlin.collections.TypeAliasesKt.ArrayList<*> }");
        Iterable $this$forEach$iv = (ArrayList) all;
        for (Object element$iv : $this$forEach$iv) {
            Iterable<KProperty1> $this$forEach$iv2 = KClasses.getMemberProperties(Reflection.getOrCreateKotlinClass(element$iv.getClass()));
            for (KProperty1 libraryMembers : $this$forEach$iv2) {
                if (Intrinsics.areEqual(libraryMembers.getName(), "resolvedCoordinates")) {
                    KCallablesJvm.setAccessible(libraryMembers, true);
                    reportIssue(String.valueOf(libraryMembers.call(element$iv)), context, false);
                }
            }
        }
    }

    private final void checkWithOldLintVersion(Project project, GradleContext context) {
        Object currentVariant = callFunctionWithReflection(project, "getCurrentVariant");
        Object mainArtifact = callFunctionWithReflection(currentVariant, "getMainArtifact");
        Object dependencies = callFunctionWithReflection(mainArtifact, "getDependencies");
        Object objCallFunctionWithReflection = callFunctionWithReflection(dependencies, "getLibraries");
        Intrinsics.checkNotNull(objCallFunctionWithReflection, "null cannot be cast to non-null type kotlin.collections.Collection<com.android.builder.model.AndroidLibrary>");
        Collection<? extends AndroidLibrary> collection = (Collection) objCallFunctionWithReflection;
        Iterable allLibraries = new HashSet();
        addIndirectAndroidLibraries(collection, (Set) allLibraries);
        Iterable $this$forEach$iv = allLibraries;
        for (Object element$iv : $this$forEach$iv) {
            AndroidLibrary it = (AndroidLibrary) element$iv;
            MavenCoordinates resolvedCoords = it.getResolvedCoordinates();
            String groupId = resolvedCoords.getGroupId();
            String artifactId = resolvedCoords.getArtifactId();
            String version = resolvedCoords.getVersion();
            reportIssue(groupId + ":" + artifactId + ":" + version, context, false);
        }
    }

    private final Object callFunctionWithReflection(Object caller, String functionName) {
        Iterable $this$forEach$iv = KClasses.getMemberFunctions(Reflection.getOrCreateKotlinClass(caller.getClass()));
        for (Object element$iv : $this$forEach$iv) {
            KFunction function = (KFunction) element$iv;
            if (Intrinsics.areEqual(function.getName(), functionName)) {
                KCallablesJvm.setAccessible(function, true);
                R rCall = function.call(caller);
                Intrinsics.checkNotNull(rCall);
                return rCall;
            }
        }
        return Unit.INSTANCE;
    }

    private final Object getMemberWithReflection(Object caller, String memberName) {
        Iterable $this$forEach$iv = KClasses.getMemberProperties(Reflection.getOrCreateKotlinClass(caller.getClass()));
        for (Object element$iv : $this$forEach$iv) {
            KProperty1 member = (KProperty1) element$iv;
            if (Intrinsics.areEqual(member.getName(), memberName)) {
                KCallablesJvm.setAccessible(member.getGetter(), true);
                V vCall = member.getGetter().call(caller);
                Intrinsics.checkNotNull(vCall);
                return vCall;
            }
        }
        return Unit.INSTANCE;
    }

    private final void addIndirectAndroidLibraries(Collection<? extends AndroidLibrary> libraries, Set<AndroidLibrary> result) {
        for (AndroidLibrary library : libraries) {
            if (!result.contains(library)) {
                result.add(library);
                List libraryDependencies = library.getLibraryDependencies();
                Intrinsics.checkNotNullExpressionValue(libraryDependencies, "library.libraryDependencies");
                addIndirectAndroidLibraries(libraryDependencies, result);
            }
        }
    }

    static /* synthetic */ void reportIssue$default(ActivityResultFragmentVersionDetector activityResultFragmentVersionDetector, String str, GradleContext gradleContext, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        activityResultFragmentVersionDetector.reportIssue(str, gradleContext, z);
    }

    private final void reportIssue(String value, GradleContext context, boolean removeQuotes) {
        String stringLiteralValue;
        if (removeQuotes) {
            stringLiteralValue = getStringLiteralValue(value);
        } else {
            stringLiteralValue = value;
        }
        String library = stringLiteralValue;
        if (library.length() > 0) {
            String currentVersion = StringsKt.substringBeforeLast$default(StringsKt.substringAfter$default(library, "androidx.fragment:fragment:", (String) null, 2, (Object) null), Parameters.DEFAULT_OPTION_PREFIXES, (String) null, 2, (Object) null);
            if (!Intrinsics.areEqual(library, currentVersion) && currentVersion.compareTo(FRAGMENT_VERSION) < 0) {
                Iterable $this$forEach$iv = this.locations;
                for (Object element$iv : $this$forEach$iv) {
                    Location location = (Location) element$iv;
                    GradleContext.report$default(context, ISSUE, getExpression(), location, "Upgrade Fragment version to at least 1.3.0.", (LintFix) null, 16, (Object) null);
                }
            }
        }
    }

    private final String getStringLiteralValue(String value) {
        if (value.length() > 2) {
            if ((StringsKt.startsWith$default(value, FormatHelper.QUOTE, false, 2, (Object) null) && StringsKt.endsWith$default(value, FormatHelper.QUOTE, false, 2, (Object) null)) || (StringsKt.startsWith$default(value, "\"", false, 2, (Object) null) && StringsKt.endsWith$default(value, "\"", false, 2, (Object) null))) {
                String strSubstring = value.substring(1, value.length() - 1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                return strSubstring;
            }
            return "";
        }
        return "";
    }
}
