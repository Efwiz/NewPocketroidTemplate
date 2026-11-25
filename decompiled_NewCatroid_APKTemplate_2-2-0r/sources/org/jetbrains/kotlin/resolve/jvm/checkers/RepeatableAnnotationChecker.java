package org.jetbrains.kotlin.resolve.jvm.checkers;

import com.reandroid.arsc.chunk.TypeBlock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptorKt;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker;
import org.jetbrains.kotlin.resolve.AnnotationChecker;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.JvmPlatformAnnotationFeaturesSupport;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: RepeatableAnnotationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001+B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\"\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\"\u0010\u0012\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\"\u0010\u0013\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J>\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00110\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00172\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u0002\u001a\u00020\u0003H\u0016J \u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J \u0010!\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020 2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J(\u0010#\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u001e\u0010$\u001a\u00020\u00152\f\u0010%\u001a\b\u0012\u0004\u0012\u00020 0\u00172\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\u000eH\u0002J\u0012\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/RepeatableAnnotationChecker;", "Lorg/jetbrains/kotlin/resolve/AdditionalAnnotationChecker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "jvmTarget", "Lorg/jetbrains/kotlin/config/JvmTarget;", "platformAnnotationFeaturesSupport", "Lorg/jetbrains/kotlin/resolve/jvm/JvmPlatformAnnotationFeaturesSupport;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/config/JvmTarget;Lorg/jetbrains/kotlin/resolve/jvm/JvmPlatformAnnotationFeaturesSupport;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "checkContainerParameters", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "containerClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "annotationClass", "reportOn", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "checkContainerRetention", "checkContainerTarget", "checkEntries", "", TypeBlock.NAME_entries, "", "actualTargets", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "annotated", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "checkJavaRepeatableAnnotationDeclaration", "javaRepeatable", "Lorg/jetbrains/kotlin/resolve/jvm/checkers/RepeatableAnnotationChecker$ResolvedAnnotation;", "checkKotlinRepeatableAnnotationDeclaration", "kotlinRepeatable", "checkRepeatableAnnotationContainer", "checkRepeatedEntries", "annotations", "isRepeatableAnnotation", "", "classDescriptor", "resolveContainerAnnotation", "Lorg/jetbrains/kotlin/name/FqName;", "ResolvedAnnotation", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RepeatableAnnotationChecker implements AdditionalAnnotationChecker {
    private final JvmTarget jvmTarget;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModuleDescriptor module;
    private final JvmPlatformAnnotationFeaturesSupport platformAnnotationFeaturesSupport;

    public RepeatableAnnotationChecker(LanguageVersionSettings languageVersionSettings, JvmTarget jvmTarget, JvmPlatformAnnotationFeaturesSupport platformAnnotationFeaturesSupport, ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(jvmTarget, "jvmTarget");
        Intrinsics.checkNotNullParameter(platformAnnotationFeaturesSupport, "platformAnnotationFeaturesSupport");
        Intrinsics.checkNotNullParameter(module, "module");
        this.languageVersionSettings = languageVersionSettings;
        this.jvmTarget = jvmTarget;
        this.platformAnnotationFeaturesSupport = platformAnnotationFeaturesSupport;
        this.module = module;
    }

    @Override // org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker
    public void checkEntries(List<? extends KtAnnotationEntry> entries, List<? extends KotlinTarget> actualTargets, BindingTrace trace, KtAnnotated annotated, LanguageVersionSettings languageVersionSettings) {
        Object resolvedAnnotation;
        ClassDescriptor classDescriptor;
        Object next;
        Intrinsics.checkNotNullParameter(entries, "entries");
        Intrinsics.checkNotNullParameter(actualTargets, "actualTargets");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (entries.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = entries.iterator();
        while (true) {
            resolvedAnnotation = null;
            if (!it.hasNext()) {
                break;
            }
            KtAnnotationEntry ktAnnotationEntry = (KtAnnotationEntry) it.next();
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) trace.get(BindingContext.ANNOTATION, ktAnnotationEntry);
            KtAnnotationUseSiteTarget useSiteTarget = ktAnnotationEntry.getUseSiteTarget();
            resolvedAnnotation = annotationDescriptor != null ? new ResolvedAnnotation(ktAnnotationEntry, annotationDescriptor, useSiteTarget != null ? useSiteTarget.getAnnotationUseSiteTarget() : null) : null;
            if (resolvedAnnotation != null) {
                arrayList.add(resolvedAnnotation);
            }
        }
        ArrayList arrayList2 = arrayList;
        checkRepeatedEntries(arrayList2, trace);
        if ((annotated instanceof KtClassOrObject) && ((KtClassOrObject) annotated).hasModifier(KtTokens.ANNOTATION_KEYWORD) && (classDescriptor = (ClassDescriptor) trace.get(BindingContext.CLASS, annotated)) != null) {
            ArrayList arrayList3 = arrayList2;
            Iterator it2 = arrayList3.iterator();
            do {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                next = it2.next();
            } while (!Intrinsics.areEqual(((ResolvedAnnotation) next).getDescriptor().getFqName(), JvmAnnotationNames.REPEATABLE_ANNOTATION));
            ResolvedAnnotation resolvedAnnotation2 = (ResolvedAnnotation) next;
            for (Object obj : arrayList3) {
                if (Intrinsics.areEqual(((ResolvedAnnotation) obj).getDescriptor().getFqName(), StandardNames.FqNames.repeatable)) {
                    resolvedAnnotation = obj;
                    break;
                }
            }
            ResolvedAnnotation resolvedAnnotation3 = (ResolvedAnnotation) resolvedAnnotation;
            if (resolvedAnnotation2 != null) {
                checkJavaRepeatableAnnotationDeclaration(resolvedAnnotation2, classDescriptor, trace);
            } else if (resolvedAnnotation3 != null) {
                checkKotlinRepeatableAnnotationDeclaration(resolvedAnnotation3, classDescriptor, trace);
            }
            if (resolvedAnnotation2 == null || resolvedAnnotation3 == null) {
                return;
            }
            DiagnosticFactory2<KtAnnotationEntry, FqName, FqName> diagnosticFactory2 = ErrorsJvm.REDUNDANT_REPEATABLE_ANNOTATION;
            KtAnnotationEntry entry = resolvedAnnotation3.getEntry();
            FqName abbreviationFqName = AnnotationDescriptorKt.getAbbreviationFqName(resolvedAnnotation3.getDescriptor());
            if (abbreviationFqName == null) {
                abbreviationFqName = FqName.ROOT;
            }
            FqName abbreviationFqName2 = AnnotationDescriptorKt.getAbbreviationFqName(resolvedAnnotation2.getDescriptor());
            if (abbreviationFqName2 == null) {
                abbreviationFqName2 = FqName.ROOT;
            }
            trace.report(diagnosticFactory2.on(entry, abbreviationFqName, abbreviationFqName2));
        }
    }

    private final void checkJavaRepeatableAnnotationDeclaration(ResolvedAnnotation javaRepeatable, ClassDescriptor annotationClass, BindingTrace trace) {
        ClassDescriptor classDescriptor;
        ConstantValue<?> constantValue = javaRepeatable.getDescriptor().getAllValueArguments().get(Name.identifier("value"));
        if (!(constantValue instanceof KClassValue) || (classDescriptor = TypeUtils.getClassDescriptor(((KClassValue) constantValue).getArgumentType(this.module))) == null) {
            return;
        }
        checkRepeatableAnnotationContainer(annotationClass, classDescriptor, trace, javaRepeatable.getEntry());
    }

    private final void checkKotlinRepeatableAnnotationDeclaration(ResolvedAnnotation kotlinRepeatable, ClassDescriptor annotationClass, BindingTrace trace) {
        MemberScope unsubstitutedInnerClassesScope = annotationClass.getUnsubstitutedInnerClassesScope();
        Name nameIdentifier = Name.identifier(JvmAbi.REPEATABLE_ANNOTATION_CONTAINER_NAME);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        if (unsubstitutedInnerClassesScope.mo7465getContributedClassifier(nameIdentifier, NoLookupLocation.FOR_ALREADY_TRACKED) != null) {
            trace.report(ErrorsJvm.REPEATABLE_ANNOTATION_HAS_NESTED_CLASS_NAMED_CONTAINER.on(this.languageVersionSettings, kotlinRepeatable.getEntry()));
        }
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0094  */
    /* JADX WARN: Code duplicated, block: B:40:0x009a  */
    /* JADX WARN: Code duplicated, block: B:45:0x00ab  */
    /* JADX WARN: Code duplicated, block: B:48:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:51:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:66:0x00c9 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:68:? A[LOOP:2: B:46:0x00af->B:68:?, LOOP_END, SYNTHETIC] */
    private final void checkRepeatedEntries(List<ResolvedAnnotation> annotations, BindingTrace trace) {
        ClassDescriptor annotationClass;
        FqName fqNameResolveContainerAnnotation;
        List<ResolvedAnnotation> list;
        Iterator<T> it;
        HashMap map = new HashMap();
        for (ResolvedAnnotation resolvedAnnotation : annotations) {
            KtAnnotationEntry entry = resolvedAnnotation.getEntry();
            AnnotationDescriptor descriptor = resolvedAnnotation.getDescriptor();
            AnnotationUseSiteTarget useSiteTarget = resolvedAnnotation.getUseSiteTarget();
            FqName fqName = descriptor.getFqName();
            if (fqName != null && (annotationClass = DescriptorUtilsKt.getAnnotationClass(descriptor)) != null) {
                HashMap map2 = map;
                Object obj = map2.get(fqName);
                if (obj == null) {
                    obj = (List) new ArrayList();
                    map2.put(fqName, obj);
                }
                List list2 = (List) obj;
                if (!list2.contains(useSiteTarget)) {
                    List list3 = list2;
                    if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                        Iterator it2 = list3.iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                boolean z = true;
                                boolean z2 = ((AnnotationUseSiteTarget) it2.next()) == null;
                                if (useSiteTarget != null) {
                                    z = false;
                                }
                                if (z2 != z) {
                                    if (isRepeatableAnnotation(annotationClass) && DescriptorUtilsKt.getAnnotationRetention(annotationClass) != KotlinRetention.SOURCE) {
                                        if (this.languageVersionSettings.supportsFeature(LanguageFeature.RepeatableAnnotations)) {
                                            fqNameResolveContainerAnnotation = resolveContainerAnnotation(annotationClass);
                                            if (fqNameResolveContainerAnnotation != null) {
                                                list = annotations;
                                                if ((list instanceof Collection) || !list.isEmpty()) {
                                                    it = list.iterator();
                                                    while (it.hasNext()) {
                                                        if (Intrinsics.areEqual(((ResolvedAnnotation) it.next()).getDescriptor().getFqName(), fqNameResolveContainerAnnotation)) {
                                                            trace.report(ErrorsJvm.REPEATED_ANNOTATION_WITH_CONTAINER.on(entry, fqName, fqNameResolveContainerAnnotation));
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            trace.report(ErrorsJvm.NON_SOURCE_REPEATED_ANNOTATION.on(entry));
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (isRepeatableAnnotation(annotationClass)) {
                    if (this.languageVersionSettings.supportsFeature(LanguageFeature.RepeatableAnnotations)) {
                        fqNameResolveContainerAnnotation = resolveContainerAnnotation(annotationClass);
                        if (fqNameResolveContainerAnnotation != null) {
                            list = annotations;
                            if (list instanceof Collection) {
                                it = list.iterator();
                                while (it.hasNext()) {
                                    if (Intrinsics.areEqual(((ResolvedAnnotation) it.next()).getDescriptor().getFqName(), fqNameResolveContainerAnnotation)) {
                                        trace.report(ErrorsJvm.REPEATED_ANNOTATION_WITH_CONTAINER.on(entry, fqName, fqNameResolveContainerAnnotation));
                                        break;
                                    }
                                }
                            } else {
                                it = list.iterator();
                                while (it.hasNext()) {
                                    if (Intrinsics.areEqual(((ResolvedAnnotation) it.next()).getDescriptor().getFqName(), fqNameResolveContainerAnnotation)) {
                                        trace.report(ErrorsJvm.REPEATED_ANNOTATION_WITH_CONTAINER.on(entry, fqName, fqNameResolveContainerAnnotation));
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        trace.report(ErrorsJvm.NON_SOURCE_REPEATED_ANNOTATION.on(entry));
                    }
                }
                list2.add(useSiteTarget);
            }
        }
    }

    private final void checkRepeatableAnnotationContainer(ClassDescriptor annotationClass, ClassDescriptor containerClass, BindingTrace trace, KtAnnotationEntry reportOn) {
        Diagnostic diagnosticCheckContainerParameters = checkContainerParameters(containerClass, annotationClass, reportOn);
        if (diagnosticCheckContainerParameters != null) {
            trace.report(diagnosticCheckContainerParameters);
        }
        Diagnostic diagnosticCheckContainerRetention = checkContainerRetention(containerClass, annotationClass, reportOn);
        if (diagnosticCheckContainerRetention != null) {
            trace.report(diagnosticCheckContainerRetention);
        }
        Diagnostic diagnosticCheckContainerTarget = checkContainerTarget(containerClass, annotationClass, reportOn);
        if (diagnosticCheckContainerTarget != null) {
            trace.report(diagnosticCheckContainerTarget);
        }
    }

    private final Diagnostic checkContainerParameters(ClassDescriptor containerClass, ClassDescriptor annotationClass, KtAnnotationEntry reportOn) {
        Object next;
        Object next2;
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = containerClass.mo6347getUnsubstitutedPrimaryConstructor();
        if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
            return null;
        }
        List<ValueParameterDescriptor> valueParameters = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        Iterator<T> it = valueParameters.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((ValueParameterDescriptor) next).getShortName().asString(), "value"));
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) next;
        if (valueParameterDescriptor == null || !KotlinBuiltIns.isArray(valueParameterDescriptor.getType()) || !Intrinsics.areEqual(((TypeProjection) CollectionsKt.single((List) valueParameterDescriptor.getType().getArguments())).getNullableAnyType().getConstructor().getThis$0(), annotationClass)) {
            return ErrorsJvm.REPEATABLE_CONTAINER_MUST_HAVE_VALUE_ARRAY.on(this.languageVersionSettings, reportOn, DescriptorUtilsKt.getFqNameSafe(containerClass), DescriptorUtilsKt.getFqNameSafe(annotationClass));
        }
        List<ValueParameterDescriptor> valueParameters2 = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
        Iterator<T> it2 = valueParameters2.iterator();
        while (true) {
            if (!it2.hasNext()) {
                next2 = null;
                break;
            }
            next2 = it2.next();
            ValueParameterDescriptor valueParameterDescriptor2 = (ValueParameterDescriptor) next2;
            if (!Intrinsics.areEqual(valueParameterDescriptor2.getShortName().asString(), "value") && !valueParameterDescriptor2.declaresDefaultValue()) {
                break;
            }
        }
        ValueParameterDescriptor valueParameterDescriptor3 = (ValueParameterDescriptor) next2;
        if (valueParameterDescriptor3 != null) {
            return ErrorsJvm.REPEATABLE_CONTAINER_HAS_NON_DEFAULT_PARAMETER.on(this.languageVersionSettings, reportOn, DescriptorUtilsKt.getFqNameSafe(containerClass), valueParameterDescriptor3);
        }
        return null;
    }

    private final Diagnostic checkContainerRetention(ClassDescriptor containerClass, ClassDescriptor annotationClass, KtAnnotationEntry reportOn) {
        KotlinRetention annotationRetention = DescriptorUtilsKt.getAnnotationRetention(annotationClass);
        if (annotationRetention == null) {
            annotationRetention = KotlinRetention.RUNTIME;
        }
        KotlinRetention annotationRetention2 = DescriptorUtilsKt.getAnnotationRetention(containerClass);
        if (annotationRetention2 == null) {
            annotationRetention2 = KotlinRetention.RUNTIME;
        }
        if (annotationRetention2.compareTo(annotationRetention) > 0) {
            return ErrorsJvm.REPEATABLE_CONTAINER_HAS_SHORTER_RETENTION.on(this.languageVersionSettings, reportOn, DescriptorUtilsKt.getFqNameSafe(containerClass), annotationRetention2.name(), DescriptorUtilsKt.getFqNameSafe(annotationClass), annotationRetention.name());
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0050  */
    private final Diagnostic checkContainerTarget(ClassDescriptor containerClass, ClassDescriptor annotationClass, KtAnnotationEntry reportOn) {
        Set<KotlinTarget> setApplicableTargetSet = AnnotationChecker.INSTANCE.applicableTargetSet(annotationClass);
        for (KotlinTarget kotlinTarget : AnnotationChecker.INSTANCE.applicableTargetSet(containerClass)) {
            boolean zContains = true;
            if (!setApplicableTargetSet.contains(kotlinTarget)) {
                if (kotlinTarget == KotlinTarget.ANNOTATION_CLASS) {
                    if (!setApplicableTargetSet.contains(KotlinTarget.CLASS) && !setApplicableTargetSet.contains(KotlinTarget.TYPE)) {
                        zContains = false;
                    }
                } else if (kotlinTarget == KotlinTarget.CLASS) {
                    zContains = setApplicableTargetSet.contains(KotlinTarget.TYPE);
                } else if (kotlinTarget == KotlinTarget.TYPE_PARAMETER) {
                    zContains = setApplicableTargetSet.contains(KotlinTarget.TYPE);
                } else {
                    zContains = false;
                }
            }
            if (!zContains) {
                return ErrorsJvm.REPEATABLE_CONTAINER_TARGET_SET_NOT_A_SUBSET.on(this.languageVersionSettings, reportOn, DescriptorUtilsKt.getFqNameSafe(containerClass), DescriptorUtilsKt.getFqNameSafe(annotationClass));
            }
        }
        return null;
    }

    private final boolean isRepeatableAnnotation(ClassDescriptor classDescriptor) {
        return DescriptorUtilsKt.isAnnotatedWithKotlinRepeatable(classDescriptor) || this.platformAnnotationFeaturesSupport.isRepeatableAnnotationClass(classDescriptor);
    }

    /* JADX INFO: compiled from: RepeatableAnnotationChecker.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/RepeatableAnnotationChecker$ResolvedAnnotation;", "", "entry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "useSiteTarget", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "(Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "getEntry", "()Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "getUseSiteTarget", "()Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class ResolvedAnnotation {
        private final AnnotationDescriptor descriptor;
        private final KtAnnotationEntry entry;
        private final AnnotationUseSiteTarget useSiteTarget;

        public static /* synthetic */ ResolvedAnnotation copy$default(ResolvedAnnotation resolvedAnnotation, KtAnnotationEntry ktAnnotationEntry, AnnotationDescriptor annotationDescriptor, AnnotationUseSiteTarget annotationUseSiteTarget, int i, Object obj) {
            if ((i & 1) != 0) {
                ktAnnotationEntry = resolvedAnnotation.entry;
            }
            if ((i & 2) != 0) {
                annotationDescriptor = resolvedAnnotation.descriptor;
            }
            if ((i & 4) != 0) {
                annotationUseSiteTarget = resolvedAnnotation.useSiteTarget;
            }
            return resolvedAnnotation.copy(ktAnnotationEntry, annotationDescriptor, annotationUseSiteTarget);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final KtAnnotationEntry getEntry() {
            return this.entry;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final AnnotationDescriptor getDescriptor() {
            return this.descriptor;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final AnnotationUseSiteTarget getUseSiteTarget() {
            return this.useSiteTarget;
        }

        public final ResolvedAnnotation copy(KtAnnotationEntry entry, AnnotationDescriptor descriptor, AnnotationUseSiteTarget useSiteTarget) {
            Intrinsics.checkNotNullParameter(entry, "entry");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return new ResolvedAnnotation(entry, descriptor, useSiteTarget);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ResolvedAnnotation)) {
                return false;
            }
            ResolvedAnnotation resolvedAnnotation = (ResolvedAnnotation) other;
            return Intrinsics.areEqual(this.entry, resolvedAnnotation.entry) && Intrinsics.areEqual(this.descriptor, resolvedAnnotation.descriptor) && this.useSiteTarget == resolvedAnnotation.useSiteTarget;
        }

        public int hashCode() {
            int iHashCode = ((this.entry.hashCode() * 31) + this.descriptor.hashCode()) * 31;
            AnnotationUseSiteTarget annotationUseSiteTarget = this.useSiteTarget;
            return iHashCode + (annotationUseSiteTarget == null ? 0 : annotationUseSiteTarget.hashCode());
        }

        public String toString() {
            return "ResolvedAnnotation(entry=" + this.entry + ", descriptor=" + this.descriptor + ", useSiteTarget=" + this.useSiteTarget + ')';
        }

        public ResolvedAnnotation(KtAnnotationEntry entry, AnnotationDescriptor descriptor, AnnotationUseSiteTarget annotationUseSiteTarget) {
            Intrinsics.checkNotNullParameter(entry, "entry");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.entry = entry;
            this.descriptor = descriptor;
            this.useSiteTarget = annotationUseSiteTarget;
        }

        public final KtAnnotationEntry getEntry() {
            return this.entry;
        }

        public final AnnotationDescriptor getDescriptor() {
            return this.descriptor;
        }

        public final AnnotationUseSiteTarget getUseSiteTarget() {
            return this.useSiteTarget;
        }
    }

    private final FqName resolveContainerAnnotation(ClassDescriptor annotationClass) {
        Annotations annotations = annotationClass.getAnnotations();
        FqName REPEATABLE_ANNOTATION = JvmAnnotationNames.REPEATABLE_ANNOTATION;
        Intrinsics.checkNotNullExpressionValue(REPEATABLE_ANNOTATION, "REPEATABLE_ANNOTATION");
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = annotations.mo6752findAnnotation(REPEATABLE_ANNOTATION);
        if (annotationDescriptorMo6752findAnnotation == null) {
            return null;
        }
        ConstantValue<?> constantValue = annotationDescriptorMo6752findAnnotation.getAllValueArguments().get(Name.identifier("value"));
        KClassValue kClassValue = constantValue instanceof KClassValue ? (KClassValue) constantValue : null;
        if (kClassValue == null) {
            return null;
        }
        KClassValue.Value value = kClassValue.getValue();
        KClassValue.Value.NormalClass normalClass = value instanceof KClassValue.Value.NormalClass ? (KClassValue.Value.NormalClass) value : null;
        if (normalClass == null) {
            return null;
        }
        return normalClass.getClassId().asSingleFqName();
    }
}
