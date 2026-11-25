package org.jetbrains.kotlin.codegen.state;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.ClassBuilderFactory;
import org.jetbrains.kotlin.codegen.ClassBuilderMode;
import org.jetbrains.kotlin.codegen.SignatureCollectingClassBuilderFactory;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilityUtils;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.load.java.descriptors.UtilKt;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaStaticClassScope;
import org.jetbrains.kotlin.load.java.sam.SamAdapterDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ConflictingJvmDeclarationsData;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.MemberKind;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.RawSignature;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: BuilderFactoryForDuplicateSignatureDiagnostics.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001:\u00014BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\r0\u000f¢\u0006\u0002\u0010\u0011J0\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\t2\u0006\u0010 \u001a\u00020!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00100#H\u0002J\u001c\u0010$\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00100%2\u0006\u0010&\u001a\u00020'H\u0002J\u0010\u0010(\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020*H\u0014J\u0010\u0010+\u001a\u00020\r2\u0006\u0010&\u001a\u00020,H\u0002J,\u0010-\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\t2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00100%H\u0014J,\u0010/\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\t2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00100%H\u0002J,\u00100\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\t2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00100%H\u0002J\u0010\u00101\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020*H\u0002J\u0006\u00102\u001a\u00020\u001aJ\f\u00103\u001a\u00020!*\u00020\u0015H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0017\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u0018j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u0019`\u001bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/BuilderFactoryForDuplicateSignatureDiagnostics;", "Lorg/jetbrains/kotlin/codegen/SignatureCollectingClassBuilderFactory;", "builderFactory", "Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "diagnostics", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "moduleName", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "useOldInlineClassesManglingScheme", "", "shouldGenerate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "(Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;Ljava/lang/String;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;ZLkotlin/jvm/functions/Function1;)V", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "mapAsmMethod", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "reportDiagnosticsTasks", "Ljava/util/ArrayList;", "Lkotlin/Function0;", "", "Lkotlin/collections/ArrayList;", "computeDiagnosticToReport", "Lorg/jetbrains/kotlin/codegen/state/BuilderFactoryForDuplicateSignatureDiagnostics$ConflictingDeclarationError;", "classOrigin", "classInternalName", "rawSignature", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/RawSignature;", "origins", "", "groupMembersDescriptorsBySignature", "Lorg/jetbrains/kotlin/com/intellij/util/containers/MultiMap;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "handleClashingSignatures", "data", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/ConflictingJvmDeclarationsData;", "isOrOverridesSamAdapter", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "onClassDone", "signatures", "reportClashingSignaturesInHierarchy", "reportClashingWithPredefinedSignatures", "reportConflictingJvmSignatures", "reportDiagnostics", "asRawSignature", "ConflictingDeclarationError", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuilderFactoryForDuplicateSignatureDiagnostics extends SignatureCollectingClassBuilderFactory {
    private final DiagnosticSink diagnostics;
    private final LanguageVersionSettings languageVersionSettings;
    private final Function1<FunctionDescriptor, Method> mapAsmMethod;
    private final ArrayList<Function0<Unit>> reportDiagnosticsTasks;

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuilderFactoryForDuplicateSignatureDiagnostics(ClassBuilderFactory builderFactory, BindingContext bindingContext, DiagnosticSink diagnostics, String moduleName, LanguageVersionSettings languageVersionSettings, boolean z, Function1<? super JvmDeclarationOrigin, Boolean> shouldGenerate) {
        super(builderFactory, shouldGenerate);
        Intrinsics.checkNotNullParameter(builderFactory, "builderFactory");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(shouldGenerate, "shouldGenerate");
        this.diagnostics = diagnostics;
        this.languageVersionSettings = languageVersionSettings;
        ClassBuilderMode classBuilderMode = ClassBuilderMode.LIGHT_CLASSES;
        Intrinsics.checkNotNull(classBuilderMode);
        this.mapAsmMethod = new BuilderFactoryForDuplicateSignatureDiagnostics$mapAsmMethod$1(new KotlinTypeMapper(bindingContext, classBuilderMode, moduleName, languageVersionSettings, z, null, false, null, null, 416, null));
        this.reportDiagnosticsTasks = new ArrayList<>();
    }

    public final void reportDiagnostics() {
        Iterator<T> it = this.reportDiagnosticsTasks.iterator();
        while (it.hasNext()) {
            ((Function0) it.next()).invoke();
        }
        this.reportDiagnosticsTasks.clear();
    }

    @Override // org.jetbrains.kotlin.codegen.SignatureCollectingClassBuilderFactory
    protected void handleClashingSignatures(final ConflictingJvmDeclarationsData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.reportDiagnosticsTasks.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.state.BuilderFactoryForDuplicateSignatureDiagnostics.handleClashingSignatures.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                BuilderFactoryForDuplicateSignatureDiagnostics.this.reportConflictingJvmSignatures(data);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void reportConflictingJvmSignatures(ConflictingJvmDeclarationsData data) {
        Collection<JvmDeclarationOrigin> signatureOrigins = data.getSignatureOrigins();
        Intrinsics.checkNotNull(signatureOrigins);
        Collection<JvmDeclarationOrigin> collection = signatureOrigins;
        boolean z = true;
        if (!(collection instanceof Collection) || !collection.isEmpty()) {
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                if (!ArraysKt.contains(BuilderFactoryForDuplicateSignatureDiagnosticsKt.EXTERNAL_SOURCES_KINDS, ((JvmDeclarationOrigin) it.next()).getOriginKind())) {
                    z = false;
                    break;
                }
            }
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (z) {
            JvmDeclarationOrigin classOrigin = data.getClassOrigin();
            Intrinsics.checkNotNull(classOrigin);
            CollectionsKt.addIfNotNull(linkedHashSet, classOrigin.getElement());
        } else {
            Collection<JvmDeclarationOrigin> signatureOrigins2 = data.getSignatureOrigins();
            Intrinsics.checkNotNull(signatureOrigins2);
            for (JvmDeclarationOrigin jvmDeclarationOrigin : signatureOrigins2) {
                PsiElement element = jvmDeclarationOrigin.getElement();
                if (element == null || ArraysKt.contains(BuilderFactoryForDuplicateSignatureDiagnosticsKt.EXTERNAL_SOURCES_KINDS, jvmDeclarationOrigin.getOriginKind())) {
                    JvmDeclarationOrigin classOrigin2 = data.getClassOrigin();
                    Intrinsics.checkNotNull(classOrigin2);
                    element = classOrigin2.getElement();
                }
                CollectionsKt.addIfNotNull(linkedHashSet, element);
            }
        }
        Iterator it2 = linkedHashSet.iterator();
        while (it2.hasNext()) {
            this.diagnostics.report(ErrorsJvm.CONFLICTING_JVM_DECLARATIONS.on((PsiElement) it2.next(), data));
        }
    }

    @Override // org.jetbrains.kotlin.codegen.SignatureCollectingClassBuilderFactory
    protected void onClassDone(final JvmDeclarationOrigin classOrigin, final String classInternalName, final MultiMap<RawSignature, JvmDeclarationOrigin> signatures) {
        Intrinsics.checkNotNullParameter(classOrigin, "classOrigin");
        Intrinsics.checkNotNullParameter(classInternalName, "classInternalName");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        this.reportDiagnosticsTasks.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.state.BuilderFactoryForDuplicateSignatureDiagnostics.onClassDone.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                BuilderFactoryForDuplicateSignatureDiagnostics.this.reportClashingWithPredefinedSignatures(classOrigin, classInternalName, signatures);
                BuilderFactoryForDuplicateSignatureDiagnostics.this.reportClashingSignaturesInHierarchy(classOrigin, classInternalName, signatures);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void reportClashingWithPredefinedSignatures(JvmDeclarationOrigin classOrigin, String classInternalName, MultiMap<RawSignature, JvmDeclarationOrigin> signatures) {
        ConflictingDeclarationError conflictingDeclarationErrorComputeDiagnosticToReport;
        for (RawSignature rawSignature : BuilderFactoryForDuplicateSignatureDiagnosticsKt.PREDEFINED_SIGNATURES) {
            Collection<JvmDeclarationOrigin> collection = signatures.get(rawSignature);
            Intrinsics.checkNotNullExpressionValue(collection, "get(...)");
            if (!collection.isEmpty() && (conflictingDeclarationErrorComputeDiagnosticToReport = computeDiagnosticToReport(classOrigin, classInternalName, rawSignature, kotlin.collections.CollectionsKt.plus((Collection<? extends JvmDeclarationOrigin>) collection, JvmDeclarationOrigin.NO_ORIGIN))) != null) {
                this.diagnostics.report(ErrorsJvm.CONFLICTING_INHERITED_JVM_DECLARATIONS.on(conflictingDeclarationErrorComputeDiagnosticToReport.getElement(), conflictingDeclarationErrorComputeDiagnosticToReport.getData()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void reportClashingSignaturesInHierarchy(JvmDeclarationOrigin classOrigin, String classInternalName, MultiMap<RawSignature, JvmDeclarationOrigin> signatures) {
        DeclarationDescriptor descriptor = classOrigin.getDescriptor();
        if (descriptor instanceof ClassDescriptor) {
            MultiMap<RawSignature, JvmDeclarationOrigin> multiMapGroupMembersDescriptorsBySignature = groupMembersDescriptorsBySignature((ClassDescriptor) descriptor);
            for (Map.Entry<RawSignature, Collection<JvmDeclarationOrigin>> entry : signatures.entrySet()) {
                Intrinsics.checkNotNull(entry);
                RawSignature key = entry.getKey();
                for (JvmDeclarationOrigin jvmDeclarationOrigin : entry.getValue()) {
                    if (jvmDeclarationOrigin.getOriginKind() == JvmDeclarationOriginKind.SYNTHETIC) {
                        multiMapGroupMembersDescriptorsBySignature.putValue(key, jvmDeclarationOrigin);
                    }
                }
            }
            for (Map.Entry<RawSignature, Collection<JvmDeclarationOrigin>> entry2 : multiMapGroupMembersDescriptorsBySignature.entrySet()) {
                Intrinsics.checkNotNull(entry2);
                RawSignature key2 = entry2.getKey();
                Collection<JvmDeclarationOrigin> value = entry2.getValue();
                if (value.size() > 1) {
                    Intrinsics.checkNotNull(key2);
                    Intrinsics.checkNotNull(value);
                    ConflictingDeclarationError conflictingDeclarationErrorComputeDiagnosticToReport = computeDiagnosticToReport(classOrigin, classInternalName, key2, value);
                    if (conflictingDeclarationErrorComputeDiagnosticToReport instanceof ConflictingDeclarationError.AccidentalOverride) {
                        ConflictingDeclarationError.AccidentalOverride accidentalOverride = (ConflictingDeclarationError.AccidentalOverride) conflictingDeclarationErrorComputeDiagnosticToReport;
                        this.diagnostics.report(ErrorsJvm.ACCIDENTAL_OVERRIDE.on(accidentalOverride.getElement(), accidentalOverride.getData()));
                    } else if (conflictingDeclarationErrorComputeDiagnosticToReport instanceof ConflictingDeclarationError.ConflictingInheritedJvmDeclarations) {
                        ConflictingDeclarationError.ConflictingInheritedJvmDeclarations conflictingInheritedJvmDeclarations = (ConflictingDeclarationError.ConflictingInheritedJvmDeclarations) conflictingDeclarationErrorComputeDiagnosticToReport;
                        this.diagnostics.report(ErrorsJvm.CONFLICTING_INHERITED_JVM_DECLARATIONS.on(conflictingInheritedJvmDeclarations.getElement(), conflictingInheritedJvmDeclarations.getData()));
                    }
                }
            }
        }
    }

    /* JADX INFO: compiled from: BuilderFactoryForDuplicateSignatureDiagnostics.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0002\u000b\fB\u0017\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0082\u0001\u0002\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/BuilderFactoryForDuplicateSignatureDiagnostics$ConflictingDeclarationError;", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "data", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/ConflictingJvmDeclarationsData;", "(Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/ConflictingJvmDeclarationsData;)V", "getData", "()Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/ConflictingJvmDeclarationsData;", "getElement", "()Lcom/intellij/psi/PsiElement;", "AccidentalOverride", "ConflictingInheritedJvmDeclarations", "Lorg/jetbrains/kotlin/codegen/state/BuilderFactoryForDuplicateSignatureDiagnostics$ConflictingDeclarationError$AccidentalOverride;", "Lorg/jetbrains/kotlin/codegen/state/BuilderFactoryForDuplicateSignatureDiagnostics$ConflictingDeclarationError$ConflictingInheritedJvmDeclarations;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static abstract class ConflictingDeclarationError {
        private final ConflictingJvmDeclarationsData data;
        private final PsiElement element;

        public /* synthetic */ ConflictingDeclarationError(PsiElement psiElement, ConflictingJvmDeclarationsData conflictingJvmDeclarationsData, DefaultConstructorMarker defaultConstructorMarker) {
            this(psiElement, conflictingJvmDeclarationsData);
        }

        private ConflictingDeclarationError(PsiElement psiElement, ConflictingJvmDeclarationsData conflictingJvmDeclarationsData) {
            this.element = psiElement;
            this.data = conflictingJvmDeclarationsData;
        }

        public final ConflictingJvmDeclarationsData getData() {
            return this.data;
        }

        public final PsiElement getElement() {
            return this.element;
        }

        /* JADX INFO: compiled from: BuilderFactoryForDuplicateSignatureDiagnostics.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/BuilderFactoryForDuplicateSignatureDiagnostics$ConflictingDeclarationError$AccidentalOverride;", "Lorg/jetbrains/kotlin/codegen/state/BuilderFactoryForDuplicateSignatureDiagnostics$ConflictingDeclarationError;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "data", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/ConflictingJvmDeclarationsData;", "(Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/ConflictingJvmDeclarationsData;)V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class AccidentalOverride extends ConflictingDeclarationError {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AccidentalOverride(PsiElement element, ConflictingJvmDeclarationsData data) {
                super(element, data, null);
                Intrinsics.checkNotNullParameter(element, "element");
                Intrinsics.checkNotNullParameter(data, "data");
            }
        }

        /* JADX INFO: compiled from: BuilderFactoryForDuplicateSignatureDiagnostics.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/BuilderFactoryForDuplicateSignatureDiagnostics$ConflictingDeclarationError$ConflictingInheritedJvmDeclarations;", "Lorg/jetbrains/kotlin/codegen/state/BuilderFactoryForDuplicateSignatureDiagnostics$ConflictingDeclarationError;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "data", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/ConflictingJvmDeclarationsData;", "(Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/ConflictingJvmDeclarationsData;)V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ConflictingInheritedJvmDeclarations extends ConflictingDeclarationError {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public ConflictingInheritedJvmDeclarations(PsiElement element, ConflictingJvmDeclarationsData data) {
                super(element, data, null);
                Intrinsics.checkNotNullParameter(element, "element");
                Intrinsics.checkNotNullParameter(data, "data");
            }
        }
    }

    private final ConflictingDeclarationError computeDiagnosticToReport(JvmDeclarationOrigin classOrigin, String classInternalName, RawSignature rawSignature, Collection<? extends JvmDeclarationOrigin> origins) {
        int i = 0;
        PsiElement element = null;
        for (JvmDeclarationOrigin jvmDeclarationOrigin : origins) {
            DeclarationDescriptor descriptor = jvmDeclarationOrigin.getDescriptor();
            CallableMemberDescriptor callableMemberDescriptor = descriptor instanceof CallableMemberDescriptor ? (CallableMemberDescriptor) descriptor : null;
            if (callableMemberDescriptor != null && Intrinsics.areEqual(callableMemberDescriptor.getContainingModule(), classOrigin.getDescriptor()) && callableMemberDescriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                i++;
                if (i > 1) {
                    return null;
                }
                if (callableMemberDescriptor.getKind() != CallableMemberDescriptor.Kind.DELEGATION) {
                    element = jvmDeclarationOrigin.getElement();
                    if (element == null) {
                        element = DescriptorToSourceUtils.descriptorToDeclaration(callableMemberDescriptor);
                    }
                    if (element == null && (callableMemberDescriptor instanceof PropertyAccessorDescriptor)) {
                        PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) callableMemberDescriptor).getCorrespondingProperty();
                        Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
                        element = DescriptorToSourceUtils.descriptorToDeclaration(correspondingProperty);
                    }
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = origins.iterator();
        while (it.hasNext()) {
            DeclarationDescriptor descriptor2 = ((JvmDeclarationOrigin) it.next()).getDescriptor();
            if (descriptor2 != null) {
                arrayList.add(descriptor2);
            }
        }
        ConflictingJvmDeclarationsData conflictingJvmDeclarationsData = new ConflictingJvmDeclarationsData(classInternalName, classOrigin, rawSignature, origins, arrayList);
        if (element != null) {
            return new ConflictingDeclarationError.AccidentalOverride(element, conflictingJvmDeclarationsData);
        }
        PsiElement element2 = classOrigin.getElement();
        if (element2 == null) {
            return null;
        }
        return new ConflictingDeclarationError.ConflictingInheritedJvmDeclarations(element2, conflictingJvmDeclarationsData);
    }

    private final MultiMap<RawSignature, JvmDeclarationOrigin> groupMembersDescriptorsBySignature(ClassDescriptor descriptor) {
        MultiMap<RawSignature, JvmDeclarationOrigin> multiMapCreate = MultiMap.create();
        Intrinsics.checkNotNullExpressionValue(multiMapCreate, "create(...)");
        Iterator it = ResolutionScope.DefaultImpls.getContributedDescriptors$default(descriptor.getDefaultType().getMemberScope(), null, null, 3, null).iterator();
        while (it.hasNext()) {
            groupMembersDescriptorsBySignature$processMember(this, multiMapCreate, (DeclarationDescriptor) it.next());
        }
        LazyJavaStaticClassScope parentJavaStaticClassScope = UtilKt.getParentJavaStaticClassScope(descriptor);
        if (parentJavaStaticClassScope != null) {
            Collection contributedDescriptors$default = ResolutionScope.DefaultImpls.getContributedDescriptors$default(parentJavaStaticClassScope, DescriptorKindFilter.FUNCTIONS, null, 2, null);
            ArrayList arrayList = new ArrayList();
            for (Object obj : contributedDescriptors$default) {
                DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) obj;
                if ((declarationDescriptor instanceof FunctionDescriptor) && DescriptorVisibilityUtils.isVisibleIgnoringReceiver((DeclarationDescriptorWithVisibility) declarationDescriptor, descriptor, this.languageVersionSettings)) {
                    arrayList.add(obj);
                }
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                groupMembersDescriptorsBySignature$processMember(this, multiMapCreate, (DeclarationDescriptor) it2.next());
            }
        }
        return multiMapCreate;
    }

    private static final void groupMembersDescriptorsBySignature$processMember(BuilderFactoryForDuplicateSignatureDiagnostics builderFactoryForDuplicateSignatureDiagnostics, MultiMap<RawSignature, JvmDeclarationOrigin> multiMap, DeclarationDescriptor declarationDescriptor) {
        HashSet of;
        if (declarationDescriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) declarationDescriptor;
            if (Intrinsics.areEqual(callableMemberDescriptor.getVisibility(), DescriptorVisibilities.INVISIBLE_FAKE) || builderFactoryForDuplicateSignatureDiagnostics.isOrOverridesSamAdapter(callableMemberDescriptor)) {
                return;
            }
            if (declarationDescriptor instanceof PropertyDescriptor) {
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor) declarationDescriptor;
                groupMembersDescriptorsBySignature$processMember(builderFactoryForDuplicateSignatureDiagnostics, multiMap, propertyDescriptor.getGetter());
                groupMembersDescriptorsBySignature$processMember(builderFactoryForDuplicateSignatureDiagnostics, multiMap, propertyDescriptor.getSetter());
            } else if (declarationDescriptor instanceof FunctionDescriptor) {
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) declarationDescriptor;
                if (functionDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                    Sequence sequenceDrop = SequencesKt.drop(DescriptorUtilsKt.overriddenTreeUniqueAsSequence((CallableDescriptor) declarationDescriptor, true), 1);
                    HashSet hashSet = new HashSet();
                    Iterator it = sequenceDrop.iterator();
                    while (it.hasNext()) {
                        hashSet.add(builderFactoryForDuplicateSignatureDiagnostics.asRawSignature((FunctionDescriptor) it.next()));
                    }
                    of = hashSet;
                } else {
                    of = SetsKt.setOf(builderFactoryForDuplicateSignatureDiagnostics.asRawSignature(functionDescriptor));
                }
                Iterator it2 = of.iterator();
                while (it2.hasNext()) {
                    multiMap.putValue((RawSignature) it2.next(), JvmDeclarationOriginKt.OtherOrigin(declarationDescriptor));
                }
            }
        }
    }

    private final RawSignature asRawSignature(FunctionDescriptor functionDescriptor) {
        Method methodInvoke = this.mapAsmMethod.invoke(functionDescriptor);
        String name2 = methodInvoke.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        String descriptor = methodInvoke.getDescriptor();
        Intrinsics.checkNotNullExpressionValue(descriptor, "getDescriptor(...)");
        return new RawSignature(name2, descriptor, MemberKind.METHOD);
    }

    private final boolean isOrOverridesSamAdapter(CallableMemberDescriptor descriptor) {
        if (descriptor instanceof SamAdapterDescriptor) {
            return true;
        }
        if (descriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = descriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            Collection<? extends CallableMemberDescriptor> collection = overriddenDescriptors;
            if (collection.isEmpty()) {
                return true;
            }
            for (CallableMemberDescriptor callableMemberDescriptor : collection) {
                Intrinsics.checkNotNull(callableMemberDescriptor);
                if (!isOrOverridesSamAdapter(callableMemberDescriptor)) {
                }
            }
            return true;
        }
        return false;
    }
}
