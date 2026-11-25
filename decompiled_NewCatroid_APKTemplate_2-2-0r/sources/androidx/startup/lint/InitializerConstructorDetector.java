package androidx.startup.lint;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.LintFix;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.SourceCodeScanner;
import com.badlogic.gdx.graphics.GL20;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.psi.PsiMethod;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UClass;

/* JADX INFO: compiled from: InitializerConstructorDetector.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/startup-runtime-1.1.1.aar:lint.jar:androidx/startup/lint/InitializerConstructorDetector.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u00012\u00020\u0002:\u0001\rB\u0005¢\u0006\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\u000e"}, d2 = {"Landroidx/startup/lint/InitializerConstructorDetector;", "Lcom/android/tools/lint/detector/api/Detector;", "Lcom/android/tools/lint/detector/api/SourceCodeScanner;", "()V", "applicableSuperClasses", "", "", "visitClass", "", "context", "Lcom/android/tools/lint/detector/api/JavaContext;", "declaration", "Lorg/jetbrains/uast/UClass;", "Companion", "startup-runtime-lint"})
public final class InitializerConstructorDetector extends Detector implements SourceCodeScanner {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String DESCRIPTION = "Missing Initializer no-arg constructor";

    @NotNull
    private static final Issue ISSUE = Issue.Companion.create$default(Issue.Companion, "EnsureInitializerNoArgConstr", DESCRIPTION, "\n                Every `Initializer` must have a no argument constructor.\n            ", new Implementation(InitializerConstructorDetector.class, EnumSet.of(Scope.JAVA_FILE)), (String) null, Category.CORRECTNESS, 0, Severity.FATAL, false, true, (EnumSet) null, (Collection) null, GL20.GL_SUBPIXEL_BITS, (Object) null);

    /* JADX INFO: compiled from: InitializerConstructorDetector.kt */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/startup-runtime-1.1.1.aar:lint.jar:androidx/startup/lint/InitializerConstructorDetector$Companion.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Landroidx/startup/lint/InitializerConstructorDetector$Companion;", "", "()V", "DESCRIPTION", "", "ISSUE", "Lcom/android/tools/lint/detector/api/Issue;", "getISSUE", "()Lcom/android/tools/lint/detector/api/Issue;", "startup-runtime-lint"})
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final Issue getISSUE() {
            return InitializerConstructorDetector.ISSUE;
        }
    }

    @NotNull
    public List<String> applicableSuperClasses() {
        return CollectionsKt.listOf("androidx.startup.Initializer");
    }

    public void visitClass(@NotNull JavaContext context, @NotNull UClass declaration) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        String name2 = declaration.getQualifiedName();
        if (Intrinsics.areEqual(name2, "androidx.startup.Initializer") || declaration.isInterface()) {
            return;
        }
        boolean isJava = Intrinsics.areEqual(declaration.getJavaPsi().getLanguage(), JavaLanguage.INSTANCE);
        if (isJava) {
            PsiMethod[] constructors = declaration.getConstructors();
            Intrinsics.checkNotNullExpressionValue(constructors, "declaration.constructors");
            if (constructors.length == 0) {
                return;
            }
        }
        PsiMethod[] constructors2 = declaration.getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors2, "declaration.constructors");
        int i = 0;
        int length = constructors2.length;
        while (i < length) {
            PsiMethod constructor = constructors2[i];
            i++;
            if (!constructor.hasParameters()) {
                return;
            }
        }
        Location location = context.getLocation(declaration.getJavaPsi());
        Context.report$default((Context) context, ISSUE, location, DESCRIPTION, (LintFix) null, 8, (Object) null);
    }
}
