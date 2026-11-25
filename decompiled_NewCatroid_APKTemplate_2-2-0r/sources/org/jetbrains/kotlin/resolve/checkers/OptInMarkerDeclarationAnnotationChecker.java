package org.jetbrains.kotlin.resolve.checkers;

import com.google.android.gms.cast.MediaTrack;
import com.reandroid.arsc.chunk.TypeBlock;
import io.netty.util.internal.StringUtil;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker;
import org.jetbrains.kotlin.resolve.AnnotationChecker;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: OptInMarkerDeclarationAnnotationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J*\u0010\u0005\u001a\u00020\u00062\u0010\u0010\u0007\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J>\u0010\u000e\u001a\u00020\u00062\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\r0\b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\b2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u001e\u0010\u0016\u001a\u00020\u00062\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\r0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0002J*\u0010\u0017\u001a\u00020\u00062\u0010\u0010\u0007\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J4\u0010\u0018\u001a\u00020\u00062\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0010\u0010\u0007\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/OptInMarkerDeclarationAnnotationChecker;", "Lorg/jetbrains/kotlin/resolve/AdditionalAnnotationChecker;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "checkArgumentsAreMarkers", "", "annotationClasses", "", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "entry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "checkEntries", TypeBlock.NAME_entries, "actualTargets", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "annotated", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "checkMarkerTargetsAndRetention", "checkOptInUsage", "checkSubclassOptInUsage", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OptInMarkerDeclarationAnnotationChecker implements AdditionalAnnotationChecker {
    private final ModuleDescriptor module;

    public OptInMarkerDeclarationAnnotationChecker(ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        this.module = module;
    }

    @Override // org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker
    public void checkEntries(List<? extends KtAnnotationEntry> entries, List<? extends KotlinTarget> actualTargets, BindingTrace trace, KtAnnotated annotated, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(entries, "entries");
        Intrinsics.checkNotNullParameter(actualTargets, "actualTargets");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        boolean z = false;
        for (KtAnnotationEntry ktAnnotationEntry : entries) {
            AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) trace.getContext().get(BindingContext.ANNOTATION, ktAnnotationEntry);
            if (annotationDescriptor != null) {
                FqName fqName = annotationDescriptor.getFqName();
                if (Intrinsics.areEqual(fqName, OptInNames.INSTANCE.getOPT_IN_FQ_NAME())) {
                    ConstantValue<?> constantValue = annotationDescriptor.getAllValueArguments().get(OptInNames.INSTANCE.getOPT_IN_ANNOTATION_CLASS());
                    ArrayValue arrayValue = constantValue instanceof ArrayValue ? (ArrayValue) constantValue : null;
                    List<? extends ConstantValue<?>> value = arrayValue != null ? arrayValue.getValue() : null;
                    if (value == null) {
                        value = CollectionsKt.emptyList();
                    }
                    checkOptInUsage(value, trace, ktAnnotationEntry);
                } else if (Intrinsics.areEqual(fqName, OptInNames.INSTANCE.getSUBCLASS_OPT_IN_REQUIRED_FQ_NAME())) {
                    checkSubclassOptInUsage(annotated, CollectionsKt.listOfNotNull(annotationDescriptor.getAllValueArguments().get(OptInNames.INSTANCE.getOPT_IN_ANNOTATION_CLASS())), trace, ktAnnotationEntry);
                } else if (Intrinsics.areEqual(fqName, OptInNames.INSTANCE.getREQUIRES_OPT_IN_FQ_NAME())) {
                    z = true;
                }
                ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
                if (annotationClass != null) {
                    Annotations annotations = annotationClass.getAnnotations();
                    if (!(annotations instanceof Collection) || !((Collection) annotations).isEmpty()) {
                        Iterator<AnnotationDescriptor> it = annotations.iterator();
                        while (it.hasNext()) {
                            if (Intrinsics.areEqual(it.next().getFqName(), OptInNames.INSTANCE.getREQUIRES_OPT_IN_FQ_NAME())) {
                                Set setIntersect = CollectionsKt.intersect(AnnotationChecker.INSTANCE.applicableTargetSet(annotationClass), actualTargets);
                                KtAnnotationUseSiteTarget useSiteTarget = ktAnnotationEntry.getUseSiteTarget();
                                AnnotationUseSiteTarget annotationUseSiteTarget = useSiteTarget != null ? useSiteTarget.getAnnotationUseSiteTarget() : null;
                                if (setIntersect.contains(KotlinTarget.PROPERTY_GETTER) || annotationUseSiteTarget == AnnotationUseSiteTarget.PROPERTY_GETTER) {
                                    trace.report(Errors.OPT_IN_MARKER_ON_WRONG_TARGET.on(ktAnnotationEntry, "getter"));
                                }
                                if ((setIntersect.contains(KotlinTarget.VALUE_PARAMETER) && annotationUseSiteTarget == null) || annotationUseSiteTarget == AnnotationUseSiteTarget.RECEIVER || annotationUseSiteTarget == AnnotationUseSiteTarget.SETTER_PARAMETER || annotationUseSiteTarget == AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER) {
                                    trace.report(Errors.OPT_IN_MARKER_ON_WRONG_TARGET.on(ktAnnotationEntry, "parameter"));
                                }
                                if (setIntersect.contains(KotlinTarget.LOCAL_VARIABLE)) {
                                    trace.report(Errors.OPT_IN_MARKER_ON_WRONG_TARGET.on(ktAnnotationEntry, "variable"));
                                }
                                if (annotationUseSiteTarget != AnnotationUseSiteTarget.FIELD && annotationUseSiteTarget != AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD) {
                                    break;
                                }
                                trace.report(Errors.OPT_IN_MARKER_ON_WRONG_TARGET.on(ktAnnotationEntry, "field"));
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (z) {
            checkMarkerTargetsAndRetention(entries, trace);
        }
    }

    private final void checkOptInUsage(List<? extends ConstantValue<?>> annotationClasses, BindingTrace trace, KtAnnotationEntry entry) {
        if (annotationClasses.isEmpty()) {
            trace.report(Errors.OPT_IN_WITHOUT_ARGUMENTS.on(entry));
        } else {
            checkArgumentsAreMarkers(annotationClasses, trace, entry);
        }
    }

    private final void checkSubclassOptInUsage(KtAnnotated annotated, List<? extends ConstantValue<?>> annotationClasses, BindingTrace trace, KtAnnotationEntry entry) {
        ClassDescriptor classDescriptor;
        if (annotated instanceof KtAnnotatedExpression) {
            if (((KtAnnotatedExpression) annotated).getBaseExpression() instanceof KtObjectLiteralExpression) {
                trace.report(Errors.SUBCLASS_OPT_IN_INAPPLICABLE.on(entry, "object"));
                return;
            }
            return;
        }
        if ((annotated instanceof KtClassOrObject) && (classDescriptor = (ClassDescriptor) trace.get(BindingContext.CLASS, annotated)) != null) {
            ClassKind kind = classDescriptor.getKind();
            Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
            if (kind == ClassKind.OBJECT || kind == ClassKind.ENUM_CLASS || kind == ClassKind.ANNOTATION_CLASS) {
                trace.report(Errors.SUBCLASS_OPT_IN_INAPPLICABLE.on(entry, kind.toString()));
                return;
            }
            if (kind != ClassKind.ENUM_ENTRY) {
                Modality modality = classDescriptor.getModality();
                Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
                if (modality != Modality.ABSTRACT && modality != Modality.OPEN) {
                    trace.report(Errors.SUBCLASS_OPT_IN_INAPPLICABLE.on(entry, new StringBuilder().append(modality).append(StringUtil.SPACE).append(kind).toString()));
                    return;
                } else if (classDescriptor.isFun()) {
                    trace.report(Errors.SUBCLASS_OPT_IN_INAPPLICABLE.on(entry, "fun interface"));
                    return;
                } else if (((KtClassOrObject) annotated).isLocal()) {
                    trace.report(Errors.SUBCLASS_OPT_IN_INAPPLICABLE.on(entry, "local " + kind));
                    return;
                }
            }
        }
        checkArgumentsAreMarkers(annotationClasses, trace, entry);
    }

    private final void checkArgumentsAreMarkers(List<? extends ConstantValue<?>> annotationClasses, BindingTrace trace, KtAnnotationEntry entry) {
        KotlinType argumentType;
        TypeConstructor constructor;
        for (ConstantValue<?> constantValue : annotationClasses) {
            KClassValue kClassValue = constantValue instanceof KClassValue ? (KClassValue) constantValue : null;
            ClassifierDescriptor this$0 = (kClassValue == null || (argumentType = kClassValue.getArgumentType(this.module)) == null || (constructor = argumentType.getConstructor()) == null) ? null : constructor.getThis$0();
            ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            if (classDescriptor != null && OptInUsageChecker.Companion.loadOptInForMarkerAnnotation$frontend$default(OptInUsageChecker.INSTANCE, classDescriptor, false, false, 3, null) == null) {
                trace.report(Errors.OPT_IN_ARGUMENT_IS_NOT_MARKER.on(entry, DescriptorUtilsKt.getFqNameSafe(classDescriptor)));
            }
        }
    }

    private final void checkMarkerTargetsAndRetention(List<? extends KtAnnotationEntry> entries, BindingTrace trace) {
        Object next;
        Object next2;
        AnnotationDescriptor annotationDescriptor;
        AnnotationDescriptor annotationDescriptor2;
        List<? extends KtAnnotationEntry> list = entries;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            linkedHashMap.put(obj, (AnnotationDescriptor) trace.getContext().get(BindingContext.ANNOTATION, (KtAnnotationEntry) obj));
        }
        Set setEntrySet = linkedHashMap.entrySet();
        Iterator it = setEntrySet.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                annotationDescriptor2 = (AnnotationDescriptor) ((Map.Entry) next).getValue();
            }
        } while (!Intrinsics.areEqual(annotationDescriptor2 != null ? annotationDescriptor2.getFqName() : null, StandardNames.FqNames.target));
        Map.Entry entry = (Map.Entry) next;
        if (entry != null) {
            KtAnnotationEntry ktAnnotationEntry = (KtAnnotationEntry) entry.getKey();
            AnnotationDescriptor annotationDescriptor3 = (AnnotationDescriptor) entry.getValue();
            AnnotationChecker.Companion companion = AnnotationChecker.INSTANCE;
            Intrinsics.checkNotNull(annotationDescriptor3);
            Set<KotlinTarget> setLoadAnnotationTargets = companion.loadAnnotationTargets(annotationDescriptor3);
            if (setLoadAnnotationTargets == null) {
                return;
            }
            Set setIntersect = CollectionsKt.intersect(setLoadAnnotationTargets, OptInDescription.INSTANCE.getWRONG_TARGETS_FOR_MARKER());
            if (!setIntersect.isEmpty()) {
                trace.report(Errors.OPT_IN_MARKER_WITH_WRONG_TARGET.on(ktAnnotationEntry, CollectionsKt.joinToString$default(setIntersect, null, null, null, 0, null, new PropertyReference1() { // from class: org.jetbrains.kotlin.resolve.checkers.OptInMarkerDeclarationAnnotationChecker.checkMarkerTargetsAndRetention.1
                    @Override // kotlin.jvm.internal.CallableReference
                    public String getSignature() {
                        return "getDescription()Ljava/lang/String;";
                    }

                    @Override // kotlin.reflect.KProperty1
                    public Object get(Object obj2) {
                        return ((KotlinTarget) obj2).getDescription();
                    }

                    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                    public String getName() {
                        return MediaTrack.ROLE_DESCRIPTION;
                    }

                    @Override // kotlin.jvm.internal.CallableReference
                    public KDeclarationContainer getOwner() {
                        return Reflection.getOrCreateKotlinClass(KotlinTarget.class);
                    }
                }, 31, null)));
            }
        }
        Iterator it2 = setEntrySet.iterator();
        do {
            if (!it2.hasNext()) {
                next2 = null;
                break;
            } else {
                next2 = it2.next();
                annotationDescriptor = (AnnotationDescriptor) ((Map.Entry) next2).getValue();
            }
        } while (!Intrinsics.areEqual(annotationDescriptor != null ? annotationDescriptor.getFqName() : null, StandardNames.FqNames.retention));
        Map.Entry entry2 = (Map.Entry) next2;
        if (entry2 != null) {
            KtAnnotationEntry ktAnnotationEntry2 = (KtAnnotationEntry) entry2.getKey();
            AnnotationDescriptor annotationDescriptor4 = (AnnotationDescriptor) entry2.getValue();
            if ((annotationDescriptor4 != null ? DescriptorUtilsKt.getAnnotationRetention(annotationDescriptor4) : null) == KotlinRetention.SOURCE) {
                trace.report(Errors.OPT_IN_MARKER_WITH_WRONG_RETENTION.on(ktAnnotationEntry2));
            }
        }
    }
}
