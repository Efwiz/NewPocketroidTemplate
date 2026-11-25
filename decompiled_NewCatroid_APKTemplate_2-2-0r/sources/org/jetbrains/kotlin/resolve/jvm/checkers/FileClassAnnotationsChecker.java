package org.jetbrains.kotlin.resolve.jvm.checkers;

import com.reandroid.arsc.chunk.TypeBlock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker;
import org.jetbrains.kotlin.resolve.AnnotationChecker;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: FileClassAnnotationsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J>\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\n2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/FileClassAnnotationsChecker;", "Lorg/jetbrains/kotlin/resolve/AdditionalAnnotationChecker;", "()V", "alwaysApplicable", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/name/FqName;", "Lkotlin/collections/HashSet;", "checkEntries", "", TypeBlock.NAME_entries, "", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "actualTargets", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "annotated", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileClassAnnotationsChecker implements AdditionalAnnotationChecker {
    public static final FileClassAnnotationsChecker INSTANCE = new FileClassAnnotationsChecker();
    private static final HashSet<FqName> alwaysApplicable = SetsKt.hashSetOf(JvmNames.JVM_NAME, JvmNames.INSTANCE.getJVM_MULTIFILE_CLASS());

    private FileClassAnnotationsChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.AdditionalAnnotationChecker
    public void checkEntries(List<? extends KtAnnotationEntry> entries, List<? extends KotlinTarget> actualTargets, BindingTrace trace, KtAnnotated annotated, LanguageVersionSettings languageVersionSettings) {
        KtExpression argumentExpression;
        KtStringTemplateEntry[] entries2;
        AnnotationDescriptor annotationDescriptor;
        ClassDescriptor annotationClass;
        Set<KotlinTarget> setApplicableTargetSetFromTargetAnnotationOrNull;
        Intrinsics.checkNotNullParameter(entries, "entries");
        Intrinsics.checkNotNullParameter(actualTargets, "actualTargets");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        ArrayList<Pair> arrayList = new ArrayList();
        Iterator<? extends KtAnnotationEntry> it = entries.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            KtAnnotationEntry next = it.next();
            KtAnnotationUseSiteTarget useSiteTarget = next.getUseSiteTarget();
            if ((useSiteTarget != null ? useSiteTarget.getAnnotationUseSiteTarget() : null) == AnnotationUseSiteTarget.FILE && (annotationDescriptor = (AnnotationDescriptor) trace.get(BindingContext.ANNOTATION, next)) != null && (annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor)) != null && (setApplicableTargetSetFromTargetAnnotationOrNull = AnnotationChecker.INSTANCE.applicableTargetSetFromTargetAnnotationOrNull(annotationClass)) != null && setApplicableTargetSetFromTargetAnnotationOrNull.contains(KotlinTarget.FILE)) {
                arrayList.add(new Pair(next, annotationClass));
            }
        }
        ArrayList arrayList2 = arrayList;
        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                if (Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe((DeclarationDescriptor) ((Pair) it2.next()).getSecond()), JvmNames.INSTANCE.getJVM_MULTIFILE_CLASS())) {
                    for (Pair pair : arrayList) {
                        KtAnnotationEntry ktAnnotationEntry = (KtAnnotationEntry) pair.component1();
                        ClassDescriptor classDescriptor = (ClassDescriptor) pair.component2();
                        FqName fqNameSafe = DescriptorUtilsKt.getFqNameSafe(classDescriptor);
                        if (!alwaysApplicable.contains(fqNameSafe) && DescriptorUtilsKt.getAnnotationRetention(classDescriptor) != KotlinRetention.SOURCE) {
                            trace.report(ErrorsJvm.ANNOTATION_IS_NOT_APPLICABLE_TO_MULTIFILE_CLASSES.on(ktAnnotationEntry, fqNameSafe));
                        }
                    }
                    return;
                }
            }
        }
        for (Pair pair2 : arrayList) {
            KtAnnotationEntry ktAnnotationEntry2 = (KtAnnotationEntry) pair2.component1();
            if (Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe((ClassDescriptor) pair2.component2()), JvmNames.INSTANCE.getJVM_PACKAGE_NAME())) {
                List<? extends ValueArgument> valueArguments = ktAnnotationEntry2.getValueArguments();
                Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
                ValueArgument valueArgument = (ValueArgument) CollectionsKt.firstOrNull((List) valueArguments);
                if (valueArgument != null && (argumentExpression = valueArgument.getArgumentExpression()) != null) {
                    KtStringTemplateExpression ktStringTemplateExpression = argumentExpression instanceof KtStringTemplateExpression ? (KtStringTemplateExpression) argumentExpression : null;
                    if (ktStringTemplateExpression != null && (entries2 = ktStringTemplateExpression.getEntries()) != null && entries2.length <= 1) {
                        Object objSingleOrNull = ArraysKt.singleOrNull(entries2);
                        KtLiteralStringTemplateEntry ktLiteralStringTemplateEntry = objSingleOrNull instanceof KtLiteralStringTemplateEntry ? (KtLiteralStringTemplateEntry) objSingleOrNull : null;
                        String text = ktLiteralStringTemplateEntry != null ? ktLiteralStringTemplateEntry.getText() : null;
                        if (text == null) {
                            trace.report(ErrorsJvm.JVM_PACKAGE_NAME_CANNOT_BE_EMPTY.on(ktAnnotationEntry2));
                        } else if (!FqNamesUtilKt.isValidJavaFqName(text)) {
                            trace.report(ErrorsJvm.JVM_PACKAGE_NAME_MUST_BE_VALID_NAME.on(ktAnnotationEntry2));
                        } else {
                            List<KtDeclaration> declarations = ktAnnotationEntry2.getContainingKtFile().getDeclarations();
                            if (!(declarations instanceof Collection) || !declarations.isEmpty()) {
                                for (KtDeclaration ktDeclaration : declarations) {
                                    if (!(ktDeclaration instanceof KtFunction) && !(ktDeclaration instanceof KtProperty) && !(ktDeclaration instanceof KtTypeAlias)) {
                                        trace.report(ErrorsJvm.JVM_PACKAGE_NAME_NOT_SUPPORTED_IN_FILES_WITH_CLASSES.on(ktAnnotationEntry2));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
