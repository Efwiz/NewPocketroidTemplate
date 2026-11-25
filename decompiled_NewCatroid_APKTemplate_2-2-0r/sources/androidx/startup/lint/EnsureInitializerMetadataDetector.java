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
import com.android.tools.lint.detector.api.XmlContext;
import com.android.tools.lint.detector.api.XmlScanner;
import com.badlogic.gdx.graphics.GL20;
import com.intellij.psi.PsiClass;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.uast.UClass;
import org.jetbrains.uast.UClassLiteralExpression;
import org.jetbrains.uast.UMethod;
import org.jetbrains.uast.visitor.AbstractUastVisitor;
import org.w3c.dom.Element;

/* JADX INFO: compiled from: EnsureInitializerMetadataDetector.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/startup-runtime-1.1.1.aar:lint.jar:androidx/startup/lint/EnsureInitializerMetadataDetector.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001c2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001\u001cB\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0013H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0013H\u0016J\u0018\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0007H\u0016J\u0018\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u001d"}, d2 = {"Landroidx/startup/lint/EnsureInitializerMetadataDetector;", "Lcom/android/tools/lint/detector/api/Detector;", "Lcom/android/tools/lint/detector/api/SourceCodeScanner;", "Lcom/android/tools/lint/detector/api/XmlScanner;", "()V", "components", "", "Lorg/jetbrains/uast/UClass;", "Lcom/android/tools/lint/detector/api/Location;", "reachable", "", "", "getReachable", "()Ljava/util/Set;", "afterCheckRootProject", "", "context", "Lcom/android/tools/lint/detector/api/Context;", "applicableSuperClasses", "", "getApplicableElements", "visitClass", "Lcom/android/tools/lint/detector/api/JavaContext;", "declaration", "visitElement", "Lcom/android/tools/lint/detector/api/XmlContext;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/w3c/dom/Element;", "Companion", "startup-runtime-lint"})
public final class EnsureInitializerMetadataDetector extends Detector implements SourceCodeScanner, XmlScanner {

    @NotNull
    private final Map<UClass, Location> components = new LinkedHashMap();

    @NotNull
    private final Set<String> reachable = new LinkedHashSet();

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String DESCRIPTION = "Every Initializer needs to be accompanied by a corresponding <meta-data> entry in the AndroidManifest.xml file.";

    @NotNull
    private static final Issue ISSUE = Issue.Companion.create$default(Issue.Companion, "EnsureInitializerMetadata", DESCRIPTION, "\n                When a library defines a Initializer, it needs to be accompanied by a \\\n                corresponding <meta-data> entry in the AndroidManifest.xml file.\n            ", new Implementation(EnsureInitializerMetadataDetector.class, EnumSet.of(Scope.JAVA_FILE, Scope.MANIFEST)), (String) null, Category.CORRECTNESS, 0, Severity.FATAL, false, true, (EnumSet) null, (Collection) null, GL20.GL_SUBPIXEL_BITS, (Object) null);

    @NotNull
    public final Set<String> getReachable() {
        return this.reachable;
    }

    /* JADX INFO: compiled from: EnsureInitializerMetadataDetector.kt */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/startup-runtime-1.1.1.aar:lint.jar:androidx/startup/lint/EnsureInitializerMetadataDetector$Companion.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Landroidx/startup/lint/EnsureInitializerMetadataDetector$Companion;", "", "()V", "DESCRIPTION", "", "ISSUE", "Lcom/android/tools/lint/detector/api/Issue;", "getISSUE", "()Lcom/android/tools/lint/detector/api/Issue;", "startup-runtime-lint"})
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final Issue getISSUE() {
            return EnsureInitializerMetadataDetector.ISSUE;
        }
    }

    @NotNull
    public List<String> applicableSuperClasses() {
        return CollectionsKt.listOf("androidx.startup.Initializer");
    }

    @NotNull
    /* JADX INFO: renamed from: getApplicableElements, reason: merged with bridge method [inline-methods] */
    public List<String> m230getApplicableElements() {
        return CollectionsKt.listOf("meta-data");
    }

    public void visitClass(@NotNull JavaContext context, @NotNull UClass declaration) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        String name2 = declaration.getQualifiedName();
        if (Intrinsics.areEqual(name2, "androidx.startup.Initializer")) {
            return;
        }
        if (!declaration.isInterface()) {
            Location location = context.getLocation(declaration.getJavaPsi());
            this.components.put(declaration, location);
        }
        UMethod[] methods = declaration.getMethods();
        int i = 0;
        int length = methods.length;
        while (i < length) {
            UMethod uMethod = methods[i];
            i++;
            if (Intrinsics.areEqual(uMethod.getName(), "dependencies") && uMethod.getParameterList().isEmpty()) {
                uMethod.accept(new AbstractUastVisitor() { // from class: androidx.startup.lint.EnsureInitializerMetadataDetector$visitClass$visitor$1
                    public boolean visitClassLiteralExpression(@NotNull UClassLiteralExpression node) {
                        String qualifiedName;
                        Intrinsics.checkNotNullParameter(node, "node");
                        PsiClassReferenceType type = node.getType();
                        PsiClassReferenceType psiClassReferenceType = type instanceof PsiClassReferenceType ? type : null;
                        if (psiClassReferenceType == null) {
                            qualifiedName = null;
                        } else {
                            PsiClass psiClassResolve = psiClassReferenceType.resolve();
                            qualifiedName = psiClassResolve == null ? null : psiClassResolve.getQualifiedName();
                        }
                        String qualifiedName2 = qualifiedName;
                        if (qualifiedName2 != null) {
                            this.this$0.getReachable().add(qualifiedName2);
                            return true;
                        }
                        return true;
                    }
                });
                return;
            }
        }
        throw new NoSuchElementException("Array contains no element matching the predicate.");
    }

    public void visitElement(@NotNull XmlContext context, @NotNull Element element) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(element, "element");
        String name2 = element.getAttributeNS("http://schemas.android.com/apk/res/android", "name");
        String value = element.getAttributeNS("http://schemas.android.com/apk/res/android", "value");
        if (Intrinsics.areEqual(value, "androidx.startup") || Intrinsics.areEqual(value, "@string/androidx_startup")) {
            this.reachable.add(name2);
        }
    }

    public void afterCheckRootProject(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        for (Map.Entry<UClass, Location> entry : this.components.entrySet()) {
            UClass declaration = entry.getKey();
            Location location = entry.getValue();
            if (!CollectionsKt.contains(this.reachable, declaration.getQualifiedName())) {
                Context.report$default(context, ISSUE, location, DESCRIPTION, (LintFix) null, 8, (Object) null);
            }
        }
    }
}
