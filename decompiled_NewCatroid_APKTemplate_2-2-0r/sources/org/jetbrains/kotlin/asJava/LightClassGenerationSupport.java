package org.jetbrains.kotlin.asJava;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.classes.KtUltraLightClass;
import org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForAnonymousDeclaration;
import org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForFacade;
import org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForLocalDeclaration;
import org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForScript;
import org.jetbrains.kotlin.asJava.classes.KtUltraLightInlineClass;
import org.jetbrains.kotlin.asJava.classes.KtUltraLightSupport;
import org.jetbrains.kotlin.asJava.classes.UltraLightUtilsKt$safeIsLocal$$inlined$runReadAction$1;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor;

/* JADX INFO: compiled from: LightClassGenerationSupport.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 #2\u00020\u0001:\u0001#B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\tH&J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u000bH&J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u000bJ\u001c\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0005\u001a\u00020\u0006H$J\u0012\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\"H&¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/asJava/LightClassGenerationSupport;", "", "()V", "analyze", "Lorg/jetbrains/kotlin/resolve/BindingContext;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "analyzeAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "analyzeWithContent", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "createConstantEvaluator", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "createUltraLightClass", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClass;", "createUltraLightClassForFacade", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClassForFacade;", "facadeClassFqName", "Lorg/jetbrains/kotlin/name/FqName;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "createUltraLightClassForScript", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClassForScript;", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", "getUltraLightClassSupport", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "resolveToDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "Companion", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LightClassGenerationSupport {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final LightClassGenerationSupport getInstance(Project project) {
        return INSTANCE.getInstance(project);
    }

    public abstract BindingContext analyze(KtElement element);

    public abstract AnnotationDescriptor analyzeAnnotation(KtAnnotationEntry element);

    public abstract BindingContext analyzeWithContent(KtClassOrObject element);

    protected abstract KtUltraLightSupport getUltraLightClassSupport(KtElement element);

    public abstract DeclarationDescriptor resolveToDescriptor(KtDeclaration declaration);

    public final ConstantExpressionEvaluator createConstantEvaluator(KtExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        KtUltraLightSupport ultraLightClassSupport = getUltraLightClassSupport(expression);
        ModuleDescriptor moduleDescriptor = ultraLightClassSupport.getModuleDescriptor();
        LanguageVersionSettings languageVersionSettings = ultraLightClassSupport.getLanguageVersionSettings();
        Project project = expression.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return new ConstantExpressionEvaluator(moduleDescriptor, languageVersionSettings, project, null, 8, null);
    }

    public final KtUltraLightClassForFacade createUltraLightClassForFacade(FqName facadeClassFqName, Collection<? extends KtFile> files) {
        Intrinsics.checkNotNullParameter(facadeClassFqName, "facadeClassFqName");
        Intrinsics.checkNotNullParameter(files, "files");
        Collection<? extends KtFile> collection = files;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (KtFile ktFile : collection) {
            arrayList.add(TuplesKt.to(ktFile, getUltraLightClassSupport(ktFile)));
        }
        return new KtUltraLightClassForFacade(facadeClassFqName, files, arrayList);
    }

    public final KtUltraLightClass createUltraLightClass(KtClassOrObject element) {
        Intrinsics.checkNotNullParameter(element, "element");
        KtUltraLightSupport ultraLightClassSupport = getUltraLightClassSupport(element);
        if (((Boolean) ultraLightClassSupport.getLanguageVersionSettings().getFlag(AnalysisFlags.getEagerResolveOfLightClasses())).booleanValue()) {
            DeclarationDescriptor declarationDescriptorResolveToDescriptor = resolveToDescriptor(element);
            LazyClassDescriptor lazyClassDescriptor = declarationDescriptorResolveToDescriptor instanceof LazyClassDescriptor ? (LazyClassDescriptor) declarationDescriptorResolveToDescriptor : null;
            if (lazyClassDescriptor != null) {
                lazyClassDescriptor.forceResolveAllContents();
            }
        }
        if ((element instanceof KtObjectDeclaration) && ((KtObjectDeclaration) element).isObjectLiteral()) {
            return new KtUltraLightClassForAnonymousDeclaration(element, ultraLightClassSupport);
        }
        if (((Boolean) ApplicationManager.getApplication().runReadAction(new UltraLightUtilsKt$safeIsLocal$$inlined$runReadAction$1(element))).booleanValue()) {
            return new KtUltraLightClassForLocalDeclaration(element, ultraLightClassSupport);
        }
        if (element.hasModifier(KtTokens.INLINE_KEYWORD)) {
            return new KtUltraLightInlineClass(element, ultraLightClassSupport);
        }
        return new KtUltraLightClass(element, ultraLightClassSupport);
    }

    public final KtUltraLightClassForScript createUltraLightClassForScript(KtScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        return new KtUltraLightClassForScript(script, getUltraLightClassSupport(script));
    }

    /* JADX INFO: compiled from: LightClassGenerationSupport.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/asJava/LightClassGenerationSupport$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/asJava/LightClassGenerationSupport;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final LightClassGenerationSupport getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            Object service = project.getService(LightClassGenerationSupport.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (LightClassGenerationSupport) service;
        }
    }
}
