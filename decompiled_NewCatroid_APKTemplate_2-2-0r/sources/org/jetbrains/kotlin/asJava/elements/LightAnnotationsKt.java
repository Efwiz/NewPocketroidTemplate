package org.jetbrains.kotlin.asJava.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassConstructorDescriptor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.CompileTimeConstantUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: lightAnnotations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0007\u001a\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0010\u001a\u00020\u0007H\u0002\u001a\u0012\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0002H\u0000\u001a\u0010\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0007H\u0002\u001a\f\u0010\u0015\u001a\u00020\u0016*\u00020\u0017H\u0002\u001a\u0016\u0010\u0018\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u001a\u0018\u00010\u0019*\u00020\u0017H\u0002\"\u001e\u0010\u0000\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0004\"\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u001b"}, d2 = {"backendNullabilityAnnotations", "", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "[Ljava/lang/String;", "nameReference", "Lorg/jetbrains/kotlin/psi/KtNameReferenceExpression;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getNameReference", "(Lorg/jetbrains/kotlin/psi/KtExpression;)Lorg/jetbrains/kotlin/psi/KtNameReferenceExpression;", "convertToLightAnnotationMemberValue", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "lightParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "argument", "getAnnotationName", "callee", "isNullabilityAnnotation", "", "qualifiedName", "unwrapCall", "analyze", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "Lorg/jetbrains/kotlin/psi/KtElement;", "getResolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "light-classes"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LightAnnotationsKt {
    private static final String[] backendNullabilityAnnotations = {Nullable.class.getName(), NotNull.class.getName()};

    public static final boolean isNullabilityAnnotation(String str) {
        return ArraysKt.contains(backendNullabilityAnnotations, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final BindingContext analyze(KtElement ktElement) {
        LightClassGenerationSupport.Companion companion = LightClassGenerationSupport.INSTANCE;
        Project project = ktElement.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return companion.getInstance(project).analyze(ktElement);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ResolvedCall<? extends CallableDescriptor> getResolvedCall(KtElement ktElement) {
        if (ktElement.isValid()) {
            return CallUtilKt.getResolvedCall(ktElement, analyze(ktElement));
        }
        return null;
    }

    public static final PsiAnnotationMemberValue convertToLightAnnotationMemberValue(PsiElement lightParent, KtExpression argument) {
        Intrinsics.checkNotNullParameter(lightParent, "lightParent");
        Intrinsics.checkNotNullParameter(argument, "argument");
        KtExpression ktExpressionUnwrapCall = unwrapCall(argument);
        if (ktExpressionUnwrapCall instanceof KtClassLiteralExpression) {
            return new KtLightPsiClassObjectAccessExpression((KtClassLiteralExpression) ktExpressionUnwrapCall, lightParent);
        }
        if (ktExpressionUnwrapCall instanceof KtStringTemplateExpression ? true : ktExpressionUnwrapCall instanceof KtConstantExpression) {
            return new KtLightPsiLiteral(ktExpressionUnwrapCall, lightParent);
        }
        if (ktExpressionUnwrapCall instanceof KtCallExpression) {
            KtCallExpression ktCallExpression = (KtCallExpression) ktExpressionUnwrapCall;
            final List<KtValueArgument> valueArguments = ktCallExpression.getValueArguments();
            Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
            KtExpression calleeExpression = ktCallExpression.getCalleeExpression();
            final String annotationName = calleeExpression != null ? getAnnotationName(calleeExpression) : null;
            if (annotationName != null) {
                return new KtLightAnnotationForSourceEntry(annotationName, new Function0<String>() { // from class: org.jetbrains.kotlin.asJava.elements.LightAnnotationsKt.convertToLightAnnotationMemberValue.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return annotationName;
                    }
                }, (KtCallElement) ktExpressionUnwrapCall, lightParent);
            }
            KtExpression ktExpression = ktExpressionUnwrapCall;
            ResolvedCall<? extends CallableDescriptor> resolvedCall = getResolvedCall(ktExpression);
            if (resolvedCall != null && CompileTimeConstantUtils.isArrayFunctionCall(resolvedCall)) {
                return new KtLightPsiArrayInitializerMemberValue(ktExpression, lightParent, new Function1<KtLightPsiArrayInitializerMemberValue, List<? extends PsiAnnotationMemberValue>>() { // from class: org.jetbrains.kotlin.asJava.elements.LightAnnotationsKt.convertToLightAnnotationMemberValue.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final List<PsiAnnotationMemberValue> invoke(KtLightPsiArrayInitializerMemberValue self) {
                        PsiAnnotationMemberValue psiAnnotationMemberValueConvertToLightAnnotationMemberValue;
                        Intrinsics.checkNotNullParameter(self, "self");
                        List<KtValueArgument> list = valueArguments;
                        ArrayList arrayList = new ArrayList();
                        Iterator<T> it = list.iterator();
                        while (it.hasNext()) {
                            KtExpression argumentExpression = ((KtValueArgument) it.next()).getArgumentExpression();
                            if (argumentExpression != null) {
                                Intrinsics.checkNotNull(argumentExpression);
                                psiAnnotationMemberValueConvertToLightAnnotationMemberValue = LightAnnotationsKt.convertToLightAnnotationMemberValue(self, argumentExpression);
                            } else {
                                psiAnnotationMemberValueConvertToLightAnnotationMemberValue = null;
                            }
                            if (psiAnnotationMemberValueConvertToLightAnnotationMemberValue != null) {
                                arrayList.add(psiAnnotationMemberValueConvertToLightAnnotationMemberValue);
                            }
                        }
                        return arrayList;
                    }
                });
            }
        } else if (ktExpressionUnwrapCall instanceof KtCollectionLiteralExpression) {
            final List<KtExpression> innerExpressions = ((KtCollectionLiteralExpression) ktExpressionUnwrapCall).getInnerExpressions();
            Intrinsics.checkNotNull(innerExpressions);
            if (!innerExpressions.isEmpty()) {
                return new KtLightPsiArrayInitializerMemberValue(ktExpressionUnwrapCall, lightParent, new Function1<KtLightPsiArrayInitializerMemberValue, List<? extends PsiAnnotationMemberValue>>() { // from class: org.jetbrains.kotlin.asJava.elements.LightAnnotationsKt.convertToLightAnnotationMemberValue.3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final List<PsiAnnotationMemberValue> invoke(KtLightPsiArrayInitializerMemberValue self) {
                        Intrinsics.checkNotNullParameter(self, "self");
                        List<KtExpression> arguments = innerExpressions;
                        Intrinsics.checkNotNullExpressionValue(arguments, "$arguments");
                        List<KtExpression> list = arguments;
                        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                        for (KtExpression ktExpression2 : list) {
                            Intrinsics.checkNotNull(ktExpression2);
                            arrayList.add(LightAnnotationsKt.convertToLightAnnotationMemberValue(self, ktExpression2));
                        }
                        return arrayList;
                    }
                });
            }
        }
        return new KtLightPsiLiteral(ktExpressionUnwrapCall, lightParent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KtNameReferenceExpression getNameReference(KtExpression ktExpression) {
        if (!(ktExpression instanceof KtConstructorCalleeExpression)) {
            if (ktExpression instanceof KtNameReferenceExpression) {
                return (KtNameReferenceExpression) ktExpression;
            }
            return null;
        }
        KtSimpleNameExpression constructorReferenceExpression = ((KtConstructorCalleeExpression) ktExpression).getConstructorReferenceExpression();
        if (constructorReferenceExpression instanceof KtNameReferenceExpression) {
            return (KtNameReferenceExpression) constructorReferenceExpression;
        }
        return null;
    }

    private static final KtExpression unwrapCall(KtExpression ktExpression) {
        if (!(ktExpression instanceof KtDotQualifiedExpression)) {
            return ktExpression;
        }
        PsiElement lastChild = ((KtDotQualifiedExpression) ktExpression).getLastChild();
        KtCallExpression ktCallExpression = lastChild instanceof KtCallExpression ? (KtCallExpression) lastChild : null;
        return ktCallExpression != null ? ktCallExpression : ktExpression;
    }

    private static final String getAnnotationName(KtExpression ktExpression) {
        ResolvedCall<? extends CallableDescriptor> resolvedCall = getResolvedCall(unwrapCall(ktExpression));
        CallableDescriptor resultingDescriptor = resolvedCall != null ? resolvedCall.getResultingDescriptor() : null;
        if (resultingDescriptor instanceof ClassConstructorDescriptor) {
            SourceElement source = ((ClassConstructorDescriptor) resultingDescriptor).getConstructedClass().getSourceElement();
            Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
            PsiElement psi = PsiSourceElementKt.getPsi(source);
            KtClass ktClass = psi instanceof KtClass ? (KtClass) psi : null;
            if (ktClass != null && ktClass.isAnnotation()) {
                FqName fqName = ktClass.mo7391getFqName();
                if (fqName != null) {
                    return fqName.toString();
                }
                return null;
            }
        }
        if (resultingDescriptor instanceof JavaClassConstructorDescriptor) {
            SourceElement source2 = ((JavaClassConstructorDescriptor) resultingDescriptor).getConstructedClass().getSourceElement();
            Intrinsics.checkNotNullExpressionValue(source2, "getSource(...)");
            PsiElement psi2 = PsiSourceElementKt.getPsi(source2);
            PsiClass psiClass = psi2 instanceof PsiClass ? (PsiClass) psi2 : null;
            if (psiClass != null && psiClass.isAnnotationType()) {
                return psiClass.getQualifiedName();
            }
        }
        return null;
    }
}
