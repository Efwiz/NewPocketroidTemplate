package org.jetbrains.kotlin.js.translate.context;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt;
import org.jetbrains.kotlin.js.naming.NameSuggestion;
import org.jetbrains.kotlin.js.naming.SuggestedName;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;

/* JADX INFO: compiled from: UsageTracker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0012\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\fH\u0002J\b\u0010!\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u00020\"2\u0006\u0010 \u001a\u00020\fH\u0002J\u0010\u0010$\u001a\u00020\"2\u0006\u0010 \u001a\u00020\fH\u0002J\u000e\u0010%\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\fJ\f\u0010&\u001a\u00020\r*\u00020\fH\u0002R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR*\u0010\n\u001a\u001e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u00148F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u001d\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\r0\u00108F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0012R\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\r0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/context/UsageTracker;", "", "parent", "containingDescriptor", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "(Lorg/jetbrains/kotlin/js/translate/context/UsageTracker;Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;Lorg/jetbrains/kotlin/resolve/BindingContext;)V", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "captured", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lkotlin/collections/LinkedHashMap;", "capturedDescriptorToJsName", "", "getCapturedDescriptorToJsName", "()Ljava/util/Map;", "capturedDescriptors", "", "getCapturedDescriptors", "()Ljava/util/Set;", "capturedTypes", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getCapturedTypes", "capturedTypesImpl", "", "getContainingDescriptor", "()Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "captureIfNeed", "", "descriptor", "isInLocalDeclaration", "", "isReceiverAncestor", "isSingletonReceiver", "used", "getJsNameForCapturedDescriptor", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UsageTracker {
    private final BindingContext bindingContext;
    private final LinkedHashMap<DeclarationDescriptor, JsName> captured;
    private final Map<TypeParameterDescriptor, JsName> capturedTypesImpl;
    private final MemberDescriptor containingDescriptor;
    private final UsageTracker parent;

    public UsageTracker(UsageTracker usageTracker, MemberDescriptor containingDescriptor, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(containingDescriptor, "containingDescriptor");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        this.parent = usageTracker;
        this.containingDescriptor = containingDescriptor;
        this.bindingContext = bindingContext;
        this.captured = new LinkedHashMap<>();
        this.capturedTypesImpl = new LinkedHashMap();
    }

    public final MemberDescriptor getContainingDescriptor() {
        return this.containingDescriptor;
    }

    public final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    public final Map<DeclarationDescriptor, JsName> getCapturedDescriptorToJsName() {
        return this.captured;
    }

    public final Set<DeclarationDescriptor> getCapturedDescriptors() {
        Set<DeclarationDescriptor> setKeySet = this.captured.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        return setKeySet;
    }

    public final Map<TypeParameterDescriptor, JsName> getCapturedTypes() {
        return this.capturedTypesImpl;
    }

    public final void used(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (UsageTrackerKt.isCaptured(this, descriptor) || (descriptor instanceof FakeCallableDescriptorForObject)) {
            return;
        }
        if ((descriptor instanceof FunctionDescriptor) && Intrinsics.areEqual(((FunctionDescriptor) descriptor).getVisibilityImpl(), DescriptorVisibilities.LOCAL)) {
            captureIfNeed(descriptor);
            return;
        }
        if ((descriptor instanceof VariableDescriptor) && !(descriptor instanceof PropertyDescriptor)) {
            captureIfNeed(descriptor);
            return;
        }
        if (descriptor instanceof ReceiverParameterDescriptor) {
            captureIfNeed(descriptor);
        } else if ((descriptor instanceof TypeParameterDescriptor) && ((TypeParameterDescriptor) descriptor).isReified()) {
            captureIfNeed(descriptor);
        }
    }

    private final void captureIfNeed(DeclarationDescriptor descriptor) {
        if (descriptor == null || UsageTrackerKt.isCaptured(this, descriptor) || !isInLocalDeclaration() || DescriptorUtils.isAncestor(this.containingDescriptor, descriptor, true) || isReceiverAncestor(descriptor) || isSingletonReceiver(descriptor)) {
            return;
        }
        if (DescriptorUtilsKt.isCoroutineLambda(descriptor) && Intrinsics.areEqual(descriptor, this.containingDescriptor)) {
            return;
        }
        UsageTracker usageTracker = this.parent;
        if (usageTracker != null) {
            usageTracker.captureIfNeed(descriptor);
        }
        this.captured.put(descriptor, getJsNameForCapturedDescriptor(descriptor));
        if (descriptor instanceof TypeParameterDescriptor) {
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) descriptor;
            if (Intrinsics.areEqual(typeParameterDescriptor.getContainingModule().getOriginal(), this.containingDescriptor.getOriginal())) {
                return;
            }
            StringBuilder sb = new StringBuilder("typeClosure$");
            NameSuggestion.Companion companion = NameSuggestion.INSTANCE;
            String strAsString = typeParameterDescriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            String string = sb.append(companion.sanitizeName(strAsString)).toString();
            Map<TypeParameterDescriptor, JsName> map = this.capturedTypesImpl;
            JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(string);
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
            map.put(descriptor, jsNameDeclareTemporaryName);
        }
    }

    private final boolean isInLocalDeclaration() {
        ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters = this.containingDescriptor;
        if (classifierDescriptorWithTypeParameters instanceof ConstructorDescriptor) {
            ClassifierDescriptorWithTypeParameters packageFragment = ((ConstructorDescriptor) classifierDescriptorWithTypeParameters).getContainingModule();
            Intrinsics.checkNotNullExpressionValue(packageFragment, "getContainingDeclaration(...)");
            classifierDescriptorWithTypeParameters = packageFragment;
        }
        return DescriptorUtils.isDescriptorWithLocalVisibility(classifierDescriptorWithTypeParameters);
    }

    private final boolean isReceiverAncestor(DeclarationDescriptor descriptor) {
        ClassDescriptor classDescriptor;
        if (!(descriptor instanceof ReceiverParameterDescriptor)) {
            return false;
        }
        MemberDescriptor memberDescriptor = this.containingDescriptor;
        if ((!(memberDescriptor instanceof ClassDescriptor) && !(memberDescriptor instanceof ConstructorDescriptor)) || (classDescriptor = (ClassDescriptor) DescriptorUtils.getParentOfType(memberDescriptor, ClassDescriptor.class, false)) == null) {
            return false;
        }
        DeclarationDescriptor containingDeclaration = ((ReceiverParameterDescriptor) descriptor).getContainingModule();
        ClassDescriptor classDescriptor2 = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor2 == null) {
            return false;
        }
        Iterator it = SequencesKt.generateSequence(classDescriptor, new Function1<ClassDescriptor, ClassDescriptor>() { // from class: org.jetbrains.kotlin.js.translate.context.UsageTracker.isReceiverAncestor.1
            @Override // kotlin.jvm.functions.Function1
            public final ClassDescriptor invoke(ClassDescriptor it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                DeclarationDescriptor packageFragment = it2.getContainingModule();
                if (packageFragment instanceof ClassDescriptor) {
                    return (ClassDescriptor) packageFragment;
                }
                return null;
            }
        }).iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual((ClassDescriptor) it.next(), classDescriptor2)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isSingletonReceiver(DeclarationDescriptor descriptor) {
        if (!(descriptor instanceof ReceiverParameterDescriptor)) {
            return false;
        }
        DeclarationDescriptor containingDeclaration = ((ReceiverParameterDescriptor) descriptor).getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (!DescriptorUtils.isObject(containingDeclaration)) {
            return false;
        }
        MemberDescriptor memberDescriptor = this.containingDescriptor;
        return (memberDescriptor instanceof ClassDescriptor) || !Intrinsics.areEqual((ClassDescriptor) DescriptorUtils.getParentOfType(memberDescriptor, ClassDescriptor.class, false), containingDeclaration);
    }

    private final JsName getJsNameForCapturedDescriptor(DeclarationDescriptor declarationDescriptor) {
        String strIsInstanceSuggestedName;
        if (declarationDescriptor instanceof ReceiverParameterDescriptor) {
            strIsInstanceSuggestedName = UsageTrackerKt.getNameForCapturedReceiver((ReceiverParameterDescriptor) declarationDescriptor);
        } else if (declarationDescriptor instanceof TypeParameterDescriptor) {
            strIsInstanceSuggestedName = Namer.isInstanceSuggestedName((TypeParameterDescriptor) declarationDescriptor);
            Intrinsics.checkNotNullExpressionValue(strIsInstanceSuggestedName, "isInstanceSuggestedName(...)");
        } else {
            NameSuggestion.Companion companion = NameSuggestion.INSTANCE;
            SuggestedName suggestedNameSuggest = new NameSuggestion().suggest(declarationDescriptor, this.bindingContext);
            Intrinsics.checkNotNull(suggestedNameSuggest);
            strIsInstanceSuggestedName = "closure$" + companion.sanitizeName((String) CollectionsKt.last((List) suggestedNameSuggest.getNames()));
        }
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(strIsInstanceSuggestedName);
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
        MetadataProperties.setDescriptor(jsNameDeclareTemporaryName, declarationDescriptor);
        return jsNameDeclareTemporaryName;
    }
}
