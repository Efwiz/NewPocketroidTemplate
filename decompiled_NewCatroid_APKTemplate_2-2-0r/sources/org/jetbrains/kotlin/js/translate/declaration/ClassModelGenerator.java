package org.jetbrains.kotlin.js.translate.declaration;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.backend.common.bridges.Bridge;
import org.jetbrains.kotlin.backend.common.bridges.ImplKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsClassModel;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.utils.FunctionsKt;

/* JADX INFO: compiled from: ClassModelGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J \u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J(\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J \u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J0\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00112\u0006\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\"H\u0002J(\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00112\u0006\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\"H\u0002J \u0010%\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0012\u0010&\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0015\u001a\u00020\tH\u0002J\u0012\u0010'\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u0015\u001a\u00020\u001aH\u0002J*\u0010(\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0010\u0010)\u001a\f\u0012\u0004\u0012\u00020\u001a\u0012\u0002\b\u00030*H\u0002J\u0018\u0010+\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0018\u0010,\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u000e\u0010-\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0011J\u0018\u0010.\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0018\u0010/\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002Jn\u00100\u001a\u0004\u0018\u0001H1\"\b\b\u0000\u00101*\u00020\t*\u0002H12\u001d\u00102\u001a\u0019\u0012\u0004\u0012\u0002H1\u0012\n\u0012\b\u0012\u0004\u0012\u0002H10403¢\u0006\u0002\b52\u0017\u00106\u001a\u0013\u0012\u0004\u0012\u0002H1\u0012\u0004\u0012\u0002H103¢\u0006\u0002\b52\u0017\u00107\u001a\u0013\u0012\u0004\u0012\u0002H1\u0012\u0004\u0012\u00020\b03¢\u0006\u0002\b5H\u0002¢\u0006\u0002\u00108JZ\u00109\u001a\b\u0012\u0004\u0012\u0002H10:\"\b\b\u0000\u00101*\u00020\t*\b\u0012\u0004\u0012\u0002H1042\u001d\u00102\u001a\u0019\u0012\u0004\u0012\u0002H1\u0012\n\u0012\b\u0012\u0004\u0012\u0002H10403¢\u0006\u0002\b52\u0017\u00106\u001a\u0013\u0012\u0004\u0012\u0002H1\u0012\u0004\u0012\u0002H103¢\u0006\u0002\b5H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u00020\b*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\nR\u0018\u0010\u000b\u001a\u00020\b*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\nR\u0018\u0010\f\u001a\u00020\b*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\n¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/declaration/ClassModelGenerator;", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)V", "getContext", "()Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "isInheritedFromAny", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Z", "isInterfaceMember", "shouldBeCopied", "getShouldBeCopied", "copyDefaultMembers", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "model", "Lorg/jetbrains/kotlin/js/backend/ast/JsClassModel;", "copyInvisibleFakeMember", "member", "copyMember", TypedValues.TransitionType.S_FROM, "to", "copyMemberWithOptionalArgs", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "copyMethod", "sourceName", "", "targetName", "sourceDescriptor", "targetDescriptor", "block", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "copyProperty", "name", "copySimpleMember", "findMemberToCopy", "findOptionalArgsMemberToCopy", "generateBridge", "bridge", "Lorg/jetbrains/kotlin/backend/common/bridges/Bridge;", "generateBridgeMethods", "generateBridgesToTraitImpl", "generateClassModel", "generateOtherBridges", "tryCopyWhenImplementingInterfaceWithDefaultArgs", "findOverriddenDescriptor", ExifInterface.GPS_DIRECTION_TRUE, "getTypedOverriddenDescriptors", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "getOriginalDescriptor", "filter", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "removeRepeated", "", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassModelGenerator {
    private final TranslationContext context;

    public ClassModelGenerator(TranslationContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final TranslationContext getContext() {
        return this.context;
    }

    public final JsClassModel generateClassModel(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ClassDescriptor superClassNotAny = DescriptorUtilsKt.getSuperClassNotAny(descriptor);
        JsName inlineableInnerNameForDescriptor = superClassNotAny != null ? this.context.getInlineableInnerNameForDescriptor(superClassNotAny) : null;
        ClassDescriptor classDescriptor = descriptor;
        JsName inlineableInnerNameForDescriptor2 = this.context.getInlineableInnerNameForDescriptor(classDescriptor);
        Intrinsics.checkNotNullExpressionValue(inlineableInnerNameForDescriptor2, "getInlineableInnerNameForDescriptor(...)");
        JsClassModel jsClassModel = new JsClassModel(inlineableInnerNameForDescriptor2, inlineableInnerNameForDescriptor);
        List<ClassDescriptor> superInterfaces = DescriptorUtilsKt.getSuperInterfaces(descriptor);
        Set<JsName> interfaces = jsClassModel.getInterfaces();
        Iterator<T> it = superInterfaces.iterator();
        while (it.hasNext()) {
            interfaces.add(this.context.getInlineableInnerNameForDescriptor((ClassDescriptor) it.next()));
        }
        if (descriptor.getClassKind() != ClassKind.ANNOTATION_CLASS && !AnnotationsUtils.isNativeObject(classDescriptor)) {
            copyDefaultMembers(descriptor, jsClassModel);
            generateBridgeMethods(descriptor, jsClassModel);
        }
        return jsClassModel;
    }

    private final void copyDefaultMembers(ClassDescriptor descriptor, JsClassModel model) {
        MemberScope unsubstitutedMemberScope = descriptor.getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
        Collection<DeclarationDescriptor> contributedDescriptors$default = ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, DescriptorKindFilter.FUNCTIONS, null, 2, null);
        ArrayList arrayList = new ArrayList();
        for (DeclarationDescriptor declarationDescriptor : contributedDescriptors$default) {
            CallableMemberDescriptor callableMemberDescriptor = declarationDescriptor instanceof CallableMemberDescriptor ? (CallableMemberDescriptor) declarationDescriptor : null;
            if (callableMemberDescriptor != null) {
                arrayList.add(callableMemberDescriptor);
            }
        }
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        ArrayList arrayList2 = arrayList;
        ArrayList<CallableMemberDescriptor> arrayList3 = new ArrayList();
        for (Object obj : arrayList2) {
            CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) obj;
            if (callableMemberDescriptor2.getModality() != Modality.ABSTRACT && !callableMemberDescriptor2.getKind().isReal()) {
                arrayList3.add(obj);
            }
        }
        for (CallableMemberDescriptor callableMemberDescriptor3 : arrayList3) {
            if ((callableMemberDescriptor3 instanceof FunctionDescriptor) && tryCopyWhenImplementingInterfaceWithDefaultArgs((FunctionDescriptor) callableMemberDescriptor3, model)) {
                linkedHashSet.add(callableMemberDescriptor3);
            }
            copySimpleMember(descriptor, callableMemberDescriptor3, model);
        }
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(arrayList2), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassModelGenerator$copyDefaultMembers$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj2) {
                return Boolean.valueOf(obj2 instanceof FunctionDescriptor);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        Iterator it = SequencesKt.filter(sequenceFilter, new Function1<FunctionDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassModelGenerator.copyDefaultMembers.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FunctionDescriptor it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(!linkedHashSet.contains(it2));
            }
        }).iterator();
        while (it.hasNext()) {
            copyMemberWithOptionalArgs(descriptor, (FunctionDescriptor) it.next(), model);
        }
    }

    private final boolean tryCopyWhenImplementingInterfaceWithDefaultArgs(FunctionDescriptor member, JsClassModel model) {
        Object obj;
        Object next;
        FunctionDescriptor functionDescriptor;
        Collection<? extends FunctionDescriptor> overriddenDescriptors = member.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Iterator<T> it = overriddenDescriptors.iterator();
        do {
            obj = null;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            functionDescriptor = (FunctionDescriptor) next;
            Intrinsics.checkNotNull(functionDescriptor);
        } while (!UtilsKt.hasOrInheritsParametersWithDefaultValue(functionDescriptor));
        FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) next;
        if (functionDescriptor2 == null || !DescriptorUtils.isInterface(functionDescriptor2.getContainingModule())) {
            return false;
        }
        Collection<? extends FunctionDescriptor> overriddenDescriptors2 = member.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors2, "getOverriddenDescriptors(...)");
        for (Object obj2 : overriddenDescriptors2) {
            if (!DescriptorUtils.isInterface(((FunctionDescriptor) obj2).getContainingModule())) {
                obj = obj2;
                break;
            }
        }
        FunctionDescriptor functionDescriptor3 = (FunctionDescriptor) obj;
        if (functionDescriptor3 == null || UtilsKt.hasOrInheritsParametersWithDefaultValue(functionDescriptor3)) {
            return false;
        }
        DeclarationDescriptor packageFragment = member.getContainingModule();
        Intrinsics.checkNotNull(packageFragment, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        ClassDescriptor classDescriptor = (ClassDescriptor) packageFragment;
        String ident = this.context.getNameForDescriptor(functionDescriptor2).getIdent();
        Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
        String ident2 = this.context.getNameForDescriptor(functionDescriptor3).getIdent();
        Intrinsics.checkNotNullExpressionValue(ident2, "getIdent(...)");
        DeclarationDescriptor packageFragment2 = functionDescriptor3.getContainingModule();
        Intrinsics.checkNotNull(packageFragment2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        copyMethod(ident2, ident + "$default", (ClassDescriptor) packageFragment2, classDescriptor, model.getPostDeclarationBlock());
        String ident3 = this.context.getNameForDescriptor(member).getIdent();
        Intrinsics.checkNotNullExpressionValue(ident3, "getIdent(...)");
        DeclarationDescriptor packageFragment3 = functionDescriptor2.getContainingModule();
        Intrinsics.checkNotNull(packageFragment3, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        copyMethod(ident, ident3, (ClassDescriptor) packageFragment3, classDescriptor, model.getPostDeclarationBlock());
        return true;
    }

    private final void copySimpleMember(ClassDescriptor descriptor, CallableMemberDescriptor member, JsClassModel model) {
        if (Intrinsics.areEqual(member.getVisibility(), DescriptorVisibilities.INVISIBLE_FAKE)) {
            copyInvisibleFakeMember(descriptor, member, model);
            return;
        }
        CallableMemberDescriptor callableMemberDescriptorFindMemberToCopy = findMemberToCopy(member);
        if (callableMemberDescriptorFindMemberToCopy == null) {
            return;
        }
        DeclarationDescriptor containingDeclaration = callableMemberDescriptorFindMemberToCopy.getContainingModule();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
        if (classDescriptor.getClassKind() != ClassKind.INTERFACE || AnnotationsUtils.isNativeObject(classDescriptor)) {
            return;
        }
        if ((callableMemberDescriptorFindMemberToCopy instanceof FunctionDescriptor) && UtilsKt.hasOrInheritsParametersWithDefaultValue((FunctionDescriptor) callableMemberDescriptorFindMemberToCopy)) {
            String str = this.context.getNameForDescriptor(member).getIdent() + "$default";
            copyMethod(str, str, classDescriptor, descriptor, model.getPostDeclarationBlock());
        } else {
            copyMember(member, classDescriptor, descriptor, model);
        }
    }

    private final void copyInvisibleFakeMember(ClassDescriptor descriptor, CallableMemberDescriptor member, JsClassModel model) {
        for (CallableMemberDescriptor callableMemberDescriptorFindMemberToCopy : member.getOverriddenDescriptors()) {
            if (!callableMemberDescriptorFindMemberToCopy.getKind().isReal()) {
                Intrinsics.checkNotNull(callableMemberDescriptorFindMemberToCopy);
                callableMemberDescriptorFindMemberToCopy = findMemberToCopy(callableMemberDescriptorFindMemberToCopy);
                if (callableMemberDescriptorFindMemberToCopy == null) {
                }
            }
            DeclarationDescriptor containingDeclaration = callableMemberDescriptorFindMemberToCopy.getContainingModule();
            Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
            if (classDescriptor.getClassKind() == ClassKind.INTERFACE) {
                Intrinsics.checkNotNull(callableMemberDescriptorFindMemberToCopy);
                copyMember(callableMemberDescriptorFindMemberToCopy, classDescriptor, descriptor, model);
            }
        }
    }

    private final void copyMember(CallableMemberDescriptor member, ClassDescriptor from, ClassDescriptor to, JsClassModel model) {
        CallableMemberDescriptor callableMemberDescriptor = member;
        String ident = this.context.getNameForDescriptor(callableMemberDescriptor).getIdent();
        Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
        if (member instanceof FunctionDescriptor) {
            copyMethod(ident, ident, from, to, model.getPostDeclarationBlock());
            return;
        }
        if (member instanceof PropertyDescriptor) {
            if (TranslationUtils.shouldAccessViaFunctions(member) || DescriptorUtilsKt.isExtension(callableMemberDescriptor)) {
                Iterator<PropertyAccessorDescriptor> it = ((PropertyDescriptor) member).getAccessors().iterator();
                while (it.hasNext()) {
                    String ident2 = this.context.getNameForDescriptor(it.next()).getIdent();
                    Intrinsics.checkNotNullExpressionValue(ident2, "getIdent(...)");
                    copyMethod(ident2, ident2, from, to, model.getPostDeclarationBlock());
                }
                return;
            }
            copyProperty(ident, from, to, model.getPostDeclarationBlock());
        }
    }

    private final void copyMemberWithOptionalArgs(ClassDescriptor descriptor, FunctionDescriptor member, JsClassModel model) {
        FunctionDescriptor functionDescriptorFindOptionalArgsMemberToCopy = findOptionalArgsMemberToCopy(member);
        if (functionDescriptorFindOptionalArgsMemberToCopy == null) {
            return;
        }
        DeclarationDescriptor packageFragment = functionDescriptorFindOptionalArgsMemberToCopy.getContainingModule();
        Intrinsics.checkNotNull(packageFragment, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        ClassDescriptor classDescriptor = (ClassDescriptor) packageFragment;
        if (classDescriptor.getClassKind() != ClassKind.INTERFACE || AnnotationsUtils.isNativeObject(classDescriptor)) {
            return;
        }
        String ident = this.context.getNameForDescriptor(member).getIdent();
        Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
        copyMethod(ident, ident, classDescriptor, descriptor, model.getPostDeclarationBlock());
    }

    private final CallableMemberDescriptor findMemberToCopy(CallableMemberDescriptor member) {
        CallableMemberDescriptor callableMemberDescriptorFindOverriddenDescriptor = findOverriddenDescriptor(member, new Function1<CallableMemberDescriptor, Collection<? extends CallableMemberDescriptor>>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassModelGenerator$findMemberToCopy$candidate$1
            @Override // kotlin.jvm.functions.Function1
            public final Collection<CallableMemberDescriptor> invoke(CallableMemberDescriptor findOverriddenDescriptor) {
                Intrinsics.checkNotNullParameter(findOverriddenDescriptor, "$this$findOverriddenDescriptor");
                Collection overriddenDescriptors = findOverriddenDescriptor.getOverriddenDescriptors();
                Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                return overriddenDescriptors;
            }
        }, new Function1<CallableMemberDescriptor, CallableMemberDescriptor>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassModelGenerator$findMemberToCopy$candidate$2
            @Override // kotlin.jvm.functions.Function1
            public final CallableMemberDescriptor invoke(CallableMemberDescriptor findOverriddenDescriptor) {
                Intrinsics.checkNotNullParameter(findOverriddenDescriptor, "$this$findOverriddenDescriptor");
                CallableMemberDescriptor original = findOverriddenDescriptor.getOriginal();
                Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                return original;
            }
        }, new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassModelGenerator$findMemberToCopy$candidate$3
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(CallableMemberDescriptor findOverriddenDescriptor) {
                Intrinsics.checkNotNullParameter(findOverriddenDescriptor, "$this$findOverriddenDescriptor");
                return Boolean.valueOf(findOverriddenDescriptor.getModality() != Modality.ABSTRACT || ((findOverriddenDescriptor instanceof FunctionDescriptor) && UtilsKt.hasOrInheritsParametersWithDefaultValue((FunctionDescriptor) findOverriddenDescriptor)));
            }
        });
        if (callableMemberDescriptorFindOverriddenDescriptor == null || !getShouldBeCopied(callableMemberDescriptorFindOverriddenDescriptor)) {
            return null;
        }
        return callableMemberDescriptorFindOverriddenDescriptor;
    }

    private final FunctionDescriptor findOptionalArgsMemberToCopy(FunctionDescriptor member) {
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) findOverriddenDescriptor(member, new Function1<FunctionDescriptor, Collection<? extends FunctionDescriptor>>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassModelGenerator$findOptionalArgsMemberToCopy$candidate$1
            @Override // kotlin.jvm.functions.Function1
            public final Collection<FunctionDescriptor> invoke(FunctionDescriptor findOverriddenDescriptor) {
                Intrinsics.checkNotNullParameter(findOverriddenDescriptor, "$this$findOverriddenDescriptor");
                Collection overriddenDescriptors = findOverriddenDescriptor.getOverriddenDescriptors();
                Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                return overriddenDescriptors;
            }
        }, new Function1<FunctionDescriptor, FunctionDescriptor>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassModelGenerator$findOptionalArgsMemberToCopy$candidate$2
            @Override // kotlin.jvm.functions.Function1
            public final FunctionDescriptor invoke(FunctionDescriptor findOverriddenDescriptor) {
                Intrinsics.checkNotNullParameter(findOverriddenDescriptor, "$this$findOverriddenDescriptor");
                FunctionDescriptor original = findOverriddenDescriptor.getOriginal();
                Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                return original;
            }
        }, new Function1<FunctionDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassModelGenerator$findOptionalArgsMemberToCopy$candidate$3
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FunctionDescriptor findOverriddenDescriptor) {
                Intrinsics.checkNotNullParameter(findOverriddenDescriptor, "$this$findOverriddenDescriptor");
                return Boolean.valueOf(UtilsKt.hasOrInheritsParametersWithDefaultValue(findOverriddenDescriptor));
            }
        });
        if (functionDescriptor == null || !getShouldBeCopied(functionDescriptor)) {
            return null;
        }
        return functionDescriptor;
    }

    private final boolean getShouldBeCopied(CallableMemberDescriptor callableMemberDescriptor) {
        return isInterfaceMember(callableMemberDescriptor) && !isInheritedFromAny(callableMemberDescriptor);
    }

    private final boolean isInterfaceMember(CallableMemberDescriptor callableMemberDescriptor) {
        DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingModule();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        return ((ClassDescriptor) containingDeclaration).getClassKind() == ClassKind.INTERFACE;
    }

    private final boolean isInheritedFromAny(CallableMemberDescriptor callableMemberDescriptor) {
        DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingModule();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        if (!KotlinBuiltIns.isAny((ClassDescriptor) containingDeclaration)) {
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            Collection<? extends CallableMemberDescriptor> collection = overriddenDescriptors;
            if (!collection.isEmpty()) {
                for (CallableMemberDescriptor callableMemberDescriptor2 : collection) {
                    Intrinsics.checkNotNull(callableMemberDescriptor2);
                    if (isInheritedFromAny(callableMemberDescriptor2)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    private final <T extends CallableMemberDescriptor> T findOverriddenDescriptor(T t, Function1<? super T, ? extends Collection<? extends T>> function1, Function1<? super T, ? extends T> function2, Function1<? super T, Boolean> function3) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<T> it = function1.invoke(t).iterator();
        while (it.hasNext()) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) it.next();
            findOverriddenDescriptor$walk(function2, linkedHashSet, function3, function1, linkedHashMap, callableMemberDescriptor, callableMemberDescriptor);
        }
        List<T> listRemoveRepeated = removeRepeated(linkedHashMap.keySet(), function1, function2);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listRemoveRepeated, 10));
        Iterator<T> it2 = listRemoveRepeated.iterator();
        while (it2.hasNext()) {
            arrayList.add((CallableMemberDescriptor) linkedHashMap.get((CallableMemberDescriptor) it2.next()));
        }
        return (T) CollectionsKt.singleOrNull((List) arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final <T extends CallableMemberDescriptor> void findOverriddenDescriptor$walk(Function1<? super T, ? extends T> function1, Set<T> set, Function1<? super T, Boolean> function2, Function1<? super T, ? extends Collection<? extends T>> function3, Map<T, T> map, T t, T t2) {
        T tInvoke = function1.invoke(t);
        if (set.add(tInvoke) && function2.invoke(tInvoke).booleanValue()) {
            Collection<? extends T> collectionInvoke = function3.invoke(tInvoke);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionInvoke, 10));
            Iterator<T> it = collectionInvoke.iterator();
            while (it.hasNext()) {
                arrayList.add(function1.invoke((CallableMemberDescriptor) it.next()));
            }
            ArrayList arrayList2 = arrayList;
            if (tInvoke.getKind().isReal() && !DescriptorUtilsKt.isEffectivelyExternal(tInvoke)) {
                map.putIfAbsent(tInvoke, t2);
                return;
            }
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                findOverriddenDescriptor$walk(function1, set, function2, function3, map, (CallableMemberDescriptor) it2.next(), t2);
            }
        }
    }

    private final <T extends CallableMemberDescriptor> List<T> removeRepeated(Collection<? extends T> collection, final Function1<? super T, ? extends Collection<? extends T>> function1, Function1<? super T, ? extends T> function2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Collection<? extends T> collection2 = collection;
        Iterator it = SequencesKt.flatMap(CollectionsKt.asSequence(collection2), new Function1<T, Sequence<? extends T>>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassModelGenerator.removeRepeated.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            /* JADX WARN: Incorrect types in method signature: (TT;)Lkotlin/sequences/Sequence<TT;>; */
            @Override // kotlin.jvm.functions.Function1
            public final Sequence invoke(CallableMemberDescriptor it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return CollectionsKt.asSequence(function1.invoke(it2));
            }
        }).iterator();
        while (it.hasNext()) {
            removeRepeated$walk$13(function2, linkedHashSet, function1, function2.invoke((CallableMemberDescriptor) it.next()));
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection2) {
            if (!linkedHashSet.contains(function2.invoke((CallableMemberDescriptor) obj))) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final <T extends CallableMemberDescriptor> void removeRepeated$walk$13(Function1<? super T, ? extends T> function1, Set<T> set, Function1<? super T, ? extends Collection<? extends T>> function2, T t) {
        T tInvoke = function1.invoke(t);
        if (set.add(tInvoke)) {
            Collection<? extends T> collectionInvoke = function2.invoke(tInvoke);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionInvoke, 10));
            Iterator<T> it = collectionInvoke.iterator();
            while (it.hasNext()) {
                arrayList.add(function1.invoke((CallableMemberDescriptor) it.next()));
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                removeRepeated$walk$13(function1, set, function2, (CallableMemberDescriptor) it2.next());
            }
        }
    }

    private final void generateBridgeMethods(ClassDescriptor descriptor, JsClassModel model) {
        generateBridgesToTraitImpl(descriptor, model);
        generateOtherBridges(descriptor, model);
    }

    private final void generateBridgesToTraitImpl(ClassDescriptor descriptor, JsClassModel model) {
        for (Map.Entry entry : CodegenUtil.getNonPrivateTraitMethods$default(descriptor, false, 2, null).entrySet()) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) entry.getKey();
            FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) entry.getValue();
            String ident = this.context.getNameForDescriptor(functionDescriptor).getIdent();
            Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
            String ident2 = this.context.getNameForDescriptor(functionDescriptor2).getIdent();
            Intrinsics.checkNotNullExpressionValue(ident2, "getIdent(...)");
            if (!Intrinsics.areEqual(ident, ident2)) {
                JsThisRef jsThisRef = new JsThisRef();
                TranslationContext translationContext = this.context;
                SourceElement source = descriptor.getSourceElement();
                Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
                JsStatement jsStatementGenerateDelegateCall = UtilsKt.generateDelegateCall(descriptor, functionDescriptor, functionDescriptor2, jsThisRef, translationContext, false, PsiSourceElementKt.getPsi(source));
                List<JsStatement> statements = model.getPostDeclarationBlock().getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                statements.add(jsStatementGenerateDelegateCall);
            }
        }
    }

    private final void generateOtherBridges(ClassDescriptor descriptor, JsClassModel model) {
        for (DeclarationDescriptor declarationDescriptor : ResolutionScope.DefaultImpls.getContributedDescriptors$default(descriptor.getDefaultType().getMemberScope(), null, null, 3, null)) {
            if (declarationDescriptor instanceof FunctionDescriptor) {
                Iterator it = ImplKt.generateBridgesForFunctionDescriptor((FunctionDescriptor) declarationDescriptor, FunctionsKt.identity()).iterator();
                while (it.hasNext()) {
                    generateBridge(descriptor, model, (Bridge) it.next());
                }
            }
        }
    }

    private final void generateBridge(ClassDescriptor descriptor, JsClassModel model, Bridge<? extends FunctionDescriptor, ?> bridge) {
        FunctionDescriptor from = bridge.getFrom();
        FunctionDescriptor to = bridge.getTo();
        if (Intrinsics.areEqual(to.getVisibility(), DescriptorVisibilities.INVISIBLE_FAKE)) {
            return;
        }
        String ident = this.context.getNameForDescriptor(from).getIdent();
        Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
        String ident2 = this.context.getNameForDescriptor(to).getIdent();
        Intrinsics.checkNotNullExpressionValue(ident2, "getIdent(...)");
        if (Intrinsics.areEqual(ident, ident2)) {
            return;
        }
        DeclarationDescriptor packageFragment = from.getContainingModule();
        Intrinsics.checkNotNull(packageFragment, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        if (((ClassDescriptor) packageFragment).getClassKind() == ClassKind.INTERFACE || !from.getKind().isReal() || from.getModality() == Modality.ABSTRACT || to.getKind().isReal()) {
            List<JsStatement> statements = model.getPostDeclarationBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            JsThisRef jsThisRef = new JsThisRef();
            TranslationContext translationContext = this.context;
            SourceElement source = descriptor.getSourceElement();
            Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
            statements.add(UtilsKt.generateDelegateCall(descriptor, from, to, jsThisRef, translationContext, false, PsiSourceElementKt.getPsi(source)));
        }
    }

    private final void copyMethod(String sourceName, String targetName, ClassDescriptor sourceDescriptor, ClassDescriptor targetDescriptor, JsBlock block) {
        ClassDescriptor classDescriptor = targetDescriptor;
        if (this.context.isFromCurrentModule(classDescriptor)) {
            JsExpression jsExpressionPrototypeOf = JsAstUtils.prototypeOf(JsAstUtils.pureFqn(this.context.getInlineableInnerNameForDescriptor(classDescriptor), (JsExpression) null));
            Intrinsics.checkNotNullExpressionValue(jsExpressionPrototypeOf, "prototypeOf(...)");
            JsExpression jsExpressionPrototypeOf2 = JsAstUtils.prototypeOf(JsAstUtils.pureFqn(this.context.getInlineableInnerNameForDescriptor(sourceDescriptor), (JsExpression) null));
            Intrinsics.checkNotNullExpressionValue(jsExpressionPrototypeOf2, "prototypeOf(...)");
            JsNameRef jsNameRef = new JsNameRef(targetName, jsExpressionPrototypeOf);
            JsNameRef jsNameRef2 = new JsNameRef(sourceName, jsExpressionPrototypeOf2);
            List<JsStatement> statements = block.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add(JsAstUtils.assignment(jsNameRef, jsNameRef2).makeStmt());
        }
    }

    private final void copyProperty(String name2, ClassDescriptor sourceDescriptor, ClassDescriptor targetDescriptor, JsBlock block) {
        ClassDescriptor classDescriptor = targetDescriptor;
        if (this.context.isFromCurrentModule(classDescriptor)) {
            JsExpression jsExpressionPrototypeOf = JsAstUtils.prototypeOf(JsAstUtils.pureFqn(this.context.getInlineableInnerNameForDescriptor(classDescriptor), (JsExpression) null));
            Intrinsics.checkNotNullExpressionValue(jsExpressionPrototypeOf, "prototypeOf(...)");
            JsExpression jsExpressionPrototypeOf2 = JsAstUtils.prototypeOf(JsAstUtils.pureFqn(this.context.getInlineableInnerNameForDescriptor(sourceDescriptor), (JsExpression) null));
            Intrinsics.checkNotNullExpressionValue(jsExpressionPrototypeOf2, "prototypeOf(...)");
            JsInvocation jsInvocationDefineProperty = JsAstUtils.defineProperty(jsExpressionPrototypeOf, name2, new JsInvocation(new JsNameRef("getOwnPropertyDescriptor", CommonClassNames.JAVA_LANG_OBJECT_SHORT), jsExpressionPrototypeOf2, new JsStringLiteral(name2)));
            Intrinsics.checkNotNullExpressionValue(jsInvocationDefineProperty, "defineProperty(...)");
            List<JsStatement> statements = block.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add(jsInvocationDefineProperty.makeStmt());
        }
    }
}
