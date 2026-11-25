package org.jetbrains.kotlin.types.expressions;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.mlsdk.common.internal.client.event.MonitorResult;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.Multimap;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtExpressionWithLabel;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtInstanceExpressionWithLabel;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtLambdaArgument;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi.psiUtil.ReservedCheckingKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorResolver;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: LabelResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001,B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J>\u0010\t\u001a \u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\n2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0006H\u0002J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0017\u001a\u00020\u0006H\u0002J\u001c\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00040\u00192\u0006\u0010\u0017\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0013J0\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020\f2\u0006\u0010#\u001a\u00020\u0013H\u0002J\u001c\u0010$\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0005\u001a\u00020%2\n\u0010&\u001a\u0006\u0012\u0002\b\u00030'J\"\u0010(\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\"\u0010)\u001a\u00020*2\u0006\u0010\u0005\u001a\u00020+2\n\u0010&\u001a\u0006\u0012\u0002\b\u00030'2\u0006\u0010\u000f\u001a\u00020\u0004¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/LabelResolver;", "", "()V", "getCallerName", "Lorg/jetbrains/kotlin/name/Name;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getContainingCallExpression", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "getElementsByLabelName", "Lkotlin/Pair;", "Ljava/util/LinkedHashSet;", "Lorg/jetbrains/kotlin/psi/KtElement;", "Lkotlin/collections/LinkedHashSet;", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "labelName", "labelExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "classNameLabelsEnabled", "", "getExpressionUnderLabel", "labeledExpression", "getLabelForFunctionalExpression", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "getLabelNamesIfAny", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "addClassNameLabels", "reportLabelResolveWillChange", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "target", "declarationElement", "closestElement", "isForExtensionReceiver", "resolveControlLabel", "Lorg/jetbrains/kotlin/psi/KtExpressionWithLabel;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "resolveNamedLabel", "resolveThisOrSuperLabel", "Lorg/jetbrains/kotlin/types/expressions/LabelResolver$LabeledReceiverResolutionResult;", "Lorg/jetbrains/kotlin/psi/KtInstanceExpressionWithLabel;", "LabeledReceiverResolutionResult", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LabelResolver {
    public static final LabelResolver INSTANCE = new LabelResolver();

    private LabelResolver() {
    }

    private final Pair<LinkedHashSet<KtElement>, KtCallableDeclaration> getElementsByLabelName(Name labelName, KtSimpleNameExpression labelExpression, boolean classNameLabelsEnabled) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        PsiElement parent = labelExpression.getLightParent();
        KtCallableDeclaration ktCallableDeclaration = null;
        while (parent != null) {
            if (getLabelNamesIfAny(parent, classNameLabelsEnabled).contains(labelName)) {
                linkedHashSet.add(getExpressionUnderLabel((KtExpression) parent));
            } else if ((parent instanceof KtCallableDeclaration) && ktCallableDeclaration == null) {
                KtCallableDeclaration ktCallableDeclaration2 = (KtCallableDeclaration) parent;
                KtTypeReference ktTypeReferenceMo7393getReceiverTypeReference = ktCallableDeclaration2.mo7393getReceiverTypeReference();
                if (Intrinsics.areEqual(ktTypeReferenceMo7393getReceiverTypeReference != null ? ktTypeReferenceMo7393getReceiverTypeReference.nameForReceiverLabel() : null, labelName.asString())) {
                    ktCallableDeclaration = ktCallableDeclaration2;
                }
            }
            parent = parent instanceof KtCodeFragment ? ((KtCodeFragment) parent).getContext() : parent.getLightParent();
        }
        return TuplesKt.to(linkedHashSet, ktCallableDeclaration);
    }

    public final List<Name> getLabelNamesIfAny(PsiElement element, boolean addClassNameLabels) {
        String strNameForReceiverLabel;
        Intrinsics.checkNotNullParameter(element, "element");
        ArrayList arrayList = new ArrayList();
        if (element instanceof KtLabeledExpression) {
            CollectionsKt.addIfNotNull(arrayList, ((KtLabeledExpression) element).getLabelNameAsName());
        } else {
            if (element instanceof KtFunctionLiteral) {
                PsiElement parent = ((KtFunctionLiteral) element).getLightParent();
                Intrinsics.checkNotNullExpressionValue(parent, "getParent(...)");
                return getLabelNamesIfAny(parent, false);
            }
            if (element instanceof KtLambdaExpression) {
                CollectionsKt.addIfNotNull(arrayList, getLabelForFunctionalExpression((KtExpression) element));
            }
        }
        if (element instanceof KtClass) {
            Iterator<T> it = ((KtClass) element).getContextReceivers().iterator();
            while (it.hasNext()) {
                String strName = ((KtContextReceiver) it.next()).name();
                Name nameIdentifier = strName != null ? Name.identifier(strName) : null;
                if (nameIdentifier != null) {
                    arrayList.add(nameIdentifier);
                }
            }
        }
        if (element instanceof KtNamedFunction) {
            ArrayList arrayList2 = arrayList;
            Name nameAsName = ((KtNamedFunction) element).getNameAsName();
            if (nameAsName == null) {
                nameAsName = getLabelForFunctionalExpression((KtExpression) element);
            }
            CollectionsKt.addIfNotNull(arrayList2, nameAsName);
        } else {
            if (element instanceof KtPropertyAccessor) {
                element = ((KtPropertyAccessor) element).getProperty();
                Intrinsics.checkNotNullExpressionValue(element, "getProperty(...)");
            }
            return arrayList;
        }
        if (addClassNameLabels) {
            KtCallableDeclaration ktCallableDeclaration = (KtCallableDeclaration) element;
            KtTypeReference ktTypeReferenceMo7393getReceiverTypeReference = ktCallableDeclaration.mo7393getReceiverTypeReference();
            if (ktTypeReferenceMo7393getReceiverTypeReference != null && (strNameForReceiverLabel = ktTypeReferenceMo7393getReceiverTypeReference.nameForReceiverLabel()) != null) {
                Name nameIdentifier2 = Name.identifier(strNameForReceiverLabel);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
                arrayList.add(nameIdentifier2);
            }
            List<KtContextReceiver> contextReceivers = ktCallableDeclaration.getContextReceivers();
            Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
            Iterator<T> it2 = contextReceivers.iterator();
            while (it2.hasNext()) {
                String strName2 = ((KtContextReceiver) it2.next()).name();
                Name nameIdentifier3 = strName2 != null ? Name.identifier(strName2) : null;
                if (nameIdentifier3 != null) {
                    arrayList.add(nameIdentifier3);
                }
            }
        }
        return arrayList;
    }

    private final Name getLabelForFunctionalExpression(KtExpression element) {
        PsiElement parent = element.getLightParent();
        if (parent instanceof KtLabeledExpression) {
            return (Name) kotlin.collections.CollectionsKt.singleOrNull((List) getLabelNamesIfAny(parent, false));
        }
        return parent instanceof KtBinaryExpression ? ((KtBinaryExpression) parent).getOperationReference().getReferencedNameAsName() : getCallerName(element);
    }

    private final KtExpression getExpressionUnderLabel(KtExpression labeledExpression) {
        KtExpression ktExpressionSafeDeparenthesize = KtPsiUtil.safeDeparenthesize(labeledExpression);
        Intrinsics.checkNotNullExpressionValue(ktExpressionSafeDeparenthesize, "safeDeparenthesize(...)");
        if (!(ktExpressionSafeDeparenthesize instanceof KtLambdaExpression)) {
            return ktExpressionSafeDeparenthesize;
        }
        KtFunctionLiteral functionLiteral = ((KtLambdaExpression) ktExpressionSafeDeparenthesize).getFunctionLiteral();
        Intrinsics.checkNotNullExpressionValue(functionLiteral, "getFunctionLiteral(...)");
        return functionLiteral;
    }

    private final Name getCallerName(KtExpression expression) {
        KtCallExpression containingCallExpression = getContainingCallExpression(expression);
        if (containingCallExpression == null) {
            return null;
        }
        KtExpression calleeExpression = containingCallExpression.getCalleeExpression();
        KtSimpleNameExpression ktSimpleNameExpression = calleeExpression instanceof KtSimpleNameExpression ? (KtSimpleNameExpression) calleeExpression : null;
        if (ktSimpleNameExpression != null) {
            return ktSimpleNameExpression.getReferencedNameAsName();
        }
        return null;
    }

    private final KtCallExpression getContainingCallExpression(KtExpression expression) {
        PsiElement parent;
        PsiElement parent2 = expression.getLightParent();
        if (parent2 instanceof KtLambdaArgument) {
            PsiElement parent3 = ((KtLambdaArgument) parent2).getLightParent();
            if (parent3 instanceof KtCallExpression) {
                return (KtCallExpression) parent3;
            }
        }
        if (!(parent2 instanceof KtValueArgument) || (parent = ((KtValueArgument) parent2).getLightParent()) == null) {
            return null;
        }
        PsiElement lightParent = parent.getLightParent();
        if (lightParent instanceof KtCallExpression) {
            return (KtCallExpression) lightParent;
        }
        return null;
    }

    public final KtElement resolveControlLabel(KtExpressionWithLabel expression, ResolutionContext<?> context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        KtSimpleNameExpression targetLabel = expression.getTargetLabel();
        if (!context.languageVersionSettings.supportsFeature(LanguageFeature.YieldIsNoMoreReserved)) {
            BindingTrace trace = context.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            ReservedCheckingKt.checkReservedYield(targetLabel, trace);
        }
        Name labelNameAsName = expression.getLabelNameAsName();
        if (targetLabel == null || labelNameAsName == null) {
            return null;
        }
        BindingTrace trace2 = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace2, "trace");
        KtElement ktElementResolveNamedLabel = resolveNamedLabel(labelNameAsName, targetLabel, trace2);
        if (ktElementResolveNamedLabel != null) {
            return ktElementResolveNamedLabel;
        }
        context.trace.report(Errors.UNRESOLVED_REFERENCE.on(targetLabel, targetLabel));
        return null;
    }

    private final KtElement resolveNamedLabel(Name labelName, KtSimpleNameExpression labelExpression, BindingTrace trace) {
        LinkedHashSet<KtElement> first = getElementsByLabelName(labelName, labelExpression, false).getFirst();
        if (first.isEmpty()) {
            return null;
        }
        if (first.size() > 1) {
            trace.report(Errors.LABEL_NAME_CLASH.on(labelExpression));
        }
        KtElement ktElement = (KtElement) kotlin.collections.CollectionsKt.first(first);
        trace.record(BindingContext.LABEL_TARGET, labelExpression, ktElement);
        return ktElement;
    }

    public final LabeledReceiverResolutionResult resolveThisOrSuperLabel(KtInstanceExpressionWithLabel expression, ResolutionContext<?> context, Name labelName) {
        PropertyDescriptor propertyDescriptor;
        ReceiverParameterDescriptor extensionReceiverParameter;
        ReceiverParameterDescriptor extensionReceiverParameter2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(labelName, "labelName");
        KtReferenceExpression instanceReference = expression.getInstanceReference();
        Intrinsics.checkNotNullExpressionValue(instanceReference, "getInstanceReference(...)");
        KtSimpleNameExpression targetLabel = expression.getTargetLabel();
        if (targetLabel == null) {
            throw new IllegalStateException(expression.toString());
        }
        LexicalScope scope = context.scope;
        Intrinsics.checkNotNullExpressionValue(scope, "scope");
        Collection<DeclarationDescriptor> declarationsByLabel = ScopeUtilsKt.getDeclarationsByLabel(scope, labelName);
        Pair<LinkedHashSet<KtElement>, KtCallableDeclaration> elementsByLabelName = getElementsByLabelName(labelName, targetLabel, (expression instanceof KtThisExpression) && context.languageVersionSettings.supportsFeature(LanguageFeature.ContextReceivers));
        LinkedHashSet<KtElement> linkedHashSetComponent1 = elementsByLabelName.component1();
        KtCallableDeclaration ktCallableDeclarationComponent2 = elementsByLabelName.component2();
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        int size = declarationsByLabel.size();
        if (size == 0) {
            if (linkedHashSetComponent1.size() > 1) {
                trace.report(Errors.LABEL_NAME_CLASH.on(targetLabel));
            }
            KtElement ktElement = (KtElement) kotlin.collections.CollectionsKt.firstOrNull(linkedHashSetComponent1);
            ReceiverParameterDescriptor receiverParameterDescriptor = null;
            if (ktElement != null) {
                trace.record(BindingContext.LABEL_TARGET, targetLabel, ktElement);
            } else {
                ktElement = null;
            }
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) trace.getContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktElement);
            boolean z = declarationDescriptor instanceof FunctionDescriptor;
            if (z || (declarationDescriptor instanceof ClassDescriptor)) {
                BindingContext context2 = trace.getContext();
                WritableSlice<DeclarationDescriptor, Multimap<String, ReceiverParameterDescriptor>> writableSlice = BindingContext.DESCRIPTOR_TO_CONTEXT_RECEIVER_MAP;
                if (declarationDescriptor instanceof PropertyAccessorDescriptor) {
                    PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) declarationDescriptor).getCorrespondingProperty();
                    Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
                    propertyDescriptor = correspondingProperty;
                } else {
                    propertyDescriptor = declarationDescriptor;
                }
                Multimap multimap = (Multimap) context2.get(writableSlice, propertyDescriptor);
                Collection collection = multimap != null ? multimap.get(labelName.getIdentifier()) : null;
                if (collection == null || collection.isEmpty()) {
                    FunctionDescriptor functionDescriptor = z ? (FunctionDescriptor) declarationDescriptor : null;
                    extensionReceiverParameter = functionDescriptor != null ? functionDescriptor.getExtensionReceiverParameter() : null;
                } else {
                    if (collection.size() != 1) {
                        BindingContextUtils.reportAmbiguousLabel(trace, targetLabel, declarationsByLabel);
                        return LabeledReceiverResolutionResult.INSTANCE.labelResolutionFailed();
                    }
                    extensionReceiverParameter = (ReceiverParameterDescriptor) kotlin.collections.CollectionsKt.single(collection);
                }
                if (extensionReceiverParameter != null) {
                    trace.record(BindingContext.LABEL_TARGET, targetLabel, ktElement);
                    trace.record(BindingContext.REFERENCE_TARGET, instanceReference, declarationDescriptor);
                    receiverParameterDescriptor = extensionReceiverParameter;
                }
                return LabeledReceiverResolutionResult.INSTANCE.labelResolutionSuccess(receiverParameterDescriptor);
            }
            trace.report(Errors.UNRESOLVED_REFERENCE.on(targetLabel, targetLabel));
        } else {
            if (size == 1) {
                DeclarationDescriptor declarationDescriptor2 = (DeclarationDescriptor) kotlin.collections.CollectionsKt.single(declarationsByLabel);
                boolean z2 = declarationDescriptor2 instanceof ClassDescriptor;
                if (z2) {
                    extensionReceiverParameter2 = ((ClassDescriptor) declarationDescriptor2).getThisAsReceiverParameter();
                } else if (declarationDescriptor2 instanceof FunctionDescriptor) {
                    extensionReceiverParameter2 = ((FunctionDescriptor) declarationDescriptor2).getExtensionReceiverParameter();
                } else {
                    if (!(declarationDescriptor2 instanceof PropertyDescriptor)) {
                        throw new UnsupportedOperationException("Unsupported descriptor: " + declarationDescriptor2);
                    }
                    extensionReceiverParameter2 = ((PropertyDescriptor) declarationDescriptor2).getExtensionReceiverParameter();
                }
                ReceiverParameterDescriptor receiverParameterDescriptor2 = extensionReceiverParameter2;
                PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(declarationDescriptor2);
                if (psiElementDescriptorToDeclaration == null) {
                    throw new IllegalStateException(("No PSI element for descriptor: " + declarationDescriptor2).toString());
                }
                trace.record(BindingContext.LABEL_TARGET, targetLabel, psiElementDescriptorToDeclaration);
                trace.record(BindingContext.REFERENCE_TARGET, instanceReference, declarationDescriptor2);
                KtElement ktElement2 = (KtElement) kotlin.collections.CollectionsKt.firstOrNull(linkedHashSetComponent1);
                if (ktElement2 != null && SequencesKt.contains(PsiUtilsKt.getParents(ktElement2), psiElementDescriptorToDeclaration)) {
                    reportLabelResolveWillChange(trace, targetLabel, psiElementDescriptorToDeclaration, ktElement2, false);
                } else if (ktCallableDeclarationComponent2 != null && SequencesKt.contains(PsiUtilsKt.getParents(ktCallableDeclarationComponent2), psiElementDescriptorToDeclaration)) {
                    reportLabelResolveWillChange(trace, targetLabel, psiElementDescriptorToDeclaration, ktCallableDeclarationComponent2, true);
                }
                if (z2 && !DescriptorResolver.checkHasOuterClassInstance(scope, trace, targetLabel, (ClassDescriptor) declarationDescriptor2)) {
                    return LabeledReceiverResolutionResult.INSTANCE.labelResolutionFailed();
                }
                return LabeledReceiverResolutionResult.INSTANCE.labelResolutionSuccess(receiverParameterDescriptor2);
            }
            BindingContextUtils.reportAmbiguousLabel(trace, targetLabel, declarationsByLabel);
        }
        return LabeledReceiverResolutionResult.INSTANCE.labelResolutionFailed();
    }

    private static final String reportLabelResolveWillChange$suffix(boolean z) {
        return z ? "extension receiver" : "context receiver";
    }

    private final void reportLabelResolveWillChange(BindingTrace trace, KtSimpleNameExpression target, PsiElement declarationElement, KtElement closestElement, boolean isForExtensionReceiver) {
        String str;
        String str2;
        if (closestElement instanceof KtFunctionLiteral) {
            str = "anonymous function";
        } else if (closestElement instanceof KtNamedFunction) {
            str = "function " + ((KtNamedFunction) closestElement).getName() + StringUtil.SPACE + reportLabelResolveWillChange$suffix(isForExtensionReceiver);
        } else {
            str = closestElement instanceof KtPropertyAccessor ? "property " + ((KtPropertyAccessor) closestElement).getProperty().getName() + StringUtil.SPACE + reportLabelResolveWillChange$suffix(isForExtensionReceiver) : "???";
        }
        if (declarationElement instanceof KtClass) {
            str2 = ExternalAnnotationProvider.CLASS_PREFIX + ((KtClass) declarationElement).getName();
        } else if (declarationElement instanceof KtNamedFunction) {
            str2 = "function " + ((KtNamedFunction) declarationElement).getName();
        } else if (declarationElement instanceof KtProperty) {
            str2 = "property " + ((KtProperty) declarationElement).getName();
        } else {
            str2 = declarationElement instanceof KtNamedDeclaration ? "declaration with name " + ((KtNamedDeclaration) declarationElement).getName() : "unknown declaration";
        }
        trace.report(Errors.LABEL_RESOLVE_WILL_CHANGE.on(target, str2, str));
    }

    /* JADX INFO: compiled from: LabelResolver.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \r2\u00020\u0001:\u0002\f\rB\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u0004\u0018\u00010\u0005J\u0006\u0010\n\u001a\u00020\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/LabelResolver$LabeledReceiverResolutionResult;", "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "Lorg/jetbrains/kotlin/types/expressions/LabelResolver$LabeledReceiverResolutionResult$Code;", "receiverParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "(Lorg/jetbrains/kotlin/types/expressions/LabelResolver$LabeledReceiverResolutionResult$Code;Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;)V", "getCode", "()Lorg/jetbrains/kotlin/types/expressions/LabelResolver$LabeledReceiverResolutionResult$Code;", "getReceiverParameterDescriptor", FirebaseAnalytics.Param.SUCCESS, "", "Code", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LabeledReceiverResolutionResult {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final Code code;
        private final ReceiverParameterDescriptor receiverParameterDescriptor;

        /* JADX INFO: compiled from: LabelResolver.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/LabelResolver$LabeledReceiverResolutionResult$Code;", "", "(Ljava/lang/String;I)V", "LABEL_RESOLUTION_ERROR", "NO_THIS", MonitorResult.SUCCESS, "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public enum Code {
            LABEL_RESOLUTION_ERROR,
            NO_THIS,
            SUCCESS;

            private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

            public static EnumEntries<Code> getEntries() {
                return $ENTRIES;
            }
        }

        public /* synthetic */ LabeledReceiverResolutionResult(Code code, ReceiverParameterDescriptor receiverParameterDescriptor, DefaultConstructorMarker defaultConstructorMarker) {
            this(code, receiverParameterDescriptor);
        }

        private LabeledReceiverResolutionResult(Code code, ReceiverParameterDescriptor receiverParameterDescriptor) {
            this.code = code;
            this.receiverParameterDescriptor = receiverParameterDescriptor;
        }

        public final Code getCode() {
            return this.code;
        }

        public final boolean success() {
            return this.code == Code.SUCCESS;
        }

        public final ReceiverParameterDescriptor getReceiverParameterDescriptor() {
            success();
            return this.receiverParameterDescriptor;
        }

        /* JADX INFO: compiled from: LabelResolver.kt */
        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0010\u0010\u0005\u001a\u00020\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/LabelResolver$LabeledReceiverResolutionResult$Companion;", "", "()V", "labelResolutionFailed", "Lorg/jetbrains/kotlin/types/expressions/LabelResolver$LabeledReceiverResolutionResult;", "labelResolutionSuccess", "receiverParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final LabeledReceiverResolutionResult labelResolutionSuccess(ReceiverParameterDescriptor receiverParameterDescriptor) {
                ReceiverParameterDescriptor receiverParameterDescriptor2 = null;
                Object[] objArr = 0;
                Object[] objArr2 = 0;
                if (receiverParameterDescriptor == null) {
                    return new LabeledReceiverResolutionResult(Code.NO_THIS, receiverParameterDescriptor2, objArr2 == true ? 1 : 0);
                }
                return new LabeledReceiverResolutionResult(Code.SUCCESS, receiverParameterDescriptor, objArr == true ? 1 : 0);
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final LabeledReceiverResolutionResult labelResolutionFailed() {
                return new LabeledReceiverResolutionResult(Code.LABEL_RESOLUTION_ERROR, null, 0 == true ? 1 : 0);
            }
        }
    }
}
