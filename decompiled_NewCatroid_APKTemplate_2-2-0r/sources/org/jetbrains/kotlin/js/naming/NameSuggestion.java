package org.jetbrains.kotlin.js.naming;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: NameSuggestion.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0016\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000b\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\u0005H\u0002J\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nR\u001c\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/js/naming/NameSuggestion;", "", "()V", ResponseCacheMiddleware.CACHE, "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/js/naming/SuggestedName;", CompilerOptions.GENERATE, "descriptor", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "generateDefault", "getSuggestedName", "", "suggest", "Companion", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class NameSuggestion {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Map<DeclarationDescriptor, SuggestedName> cache;

    @JvmStatic
    public static final String getPrivateMangledName(String str, CallableDescriptor callableDescriptor) {
        return INSTANCE.getPrivateMangledName(str, callableDescriptor);
    }

    @JvmStatic
    public static final String getStableMangledName(String str, String str2) {
        return INSTANCE.getStableMangledName(str, str2);
    }

    @JvmStatic
    public static final String sanitizeName(String str) {
        return INSTANCE.sanitizeName(str);
    }

    public NameSuggestion() {
        Map<DeclarationDescriptor, SuggestedName> mapSynchronizedMap = Collections.synchronizedMap(new WeakHashMap());
        Intrinsics.checkNotNullExpressionValue(mapSynchronizedMap, "synchronizedMap(...)");
        this.cache = mapSynchronizedMap;
    }

    public final SuggestedName suggest(DeclarationDescriptor descriptor, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Map<DeclarationDescriptor, SuggestedName> map = this.cache;
        SuggestedName suggestedNameGenerate = map.get(descriptor);
        if (suggestedNameGenerate == null) {
            DeclarationDescriptor original = descriptor.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
            suggestedNameGenerate = generate(original, bindingContext);
            map.put(descriptor, suggestedNameGenerate);
        }
        return suggestedNameGenerate;
    }

    private static final SuggestedName generate$suggest(NameSuggestion nameSuggestion, BindingContext bindingContext, DeclarationDescriptor declarationDescriptor) {
        return nameSuggestion.suggest(declarationDescriptor, bindingContext);
    }

    private final SuggestedName generate(DeclarationDescriptor descriptor, BindingContext bindingContext) {
        FunctionDescriptor initialSignatureDescriptor;
        if (AnnotationsUtils.isNativeObject(descriptor) && DescriptorUtils.isCompanionObject(descriptor)) {
            DeclarationDescriptor moduleDescriptor = descriptor.getModuleDescriptor();
            Intrinsics.checkNotNull(moduleDescriptor);
            return suggest(moduleDescriptor, bindingContext);
        }
        boolean z = descriptor instanceof FunctionDescriptor;
        if (z) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
            if (functionDescriptor.isSuspend() && (initialSignatureDescriptor = functionDescriptor.getInitialSignatureDescriptor()) != null) {
                return generate$suggest(this, bindingContext, initialSignatureDescriptor);
            }
        }
        if (DynamicCallsKt.isDynamic(descriptor)) {
            List listListOf = CollectionsKt.listOf(descriptor.getName().asString());
            DeclarationDescriptor moduleDescriptor2 = descriptor.getModuleDescriptor();
            Intrinsics.checkNotNull(moduleDescriptor2);
            return new SuggestedName(listListOf, true, descriptor, moduleDescriptor2);
        }
        if (descriptor instanceof ModuleDescriptor) {
            return null;
        }
        if (descriptor instanceof PackageFragmentDescriptor) {
            PackageFragmentDescriptor packageFragmentDescriptor = (PackageFragmentDescriptor) descriptor;
            if (packageFragmentDescriptor.getFqName().isRoot()) {
                return null;
            }
            List<Name> listPathSegments = packageFragmentDescriptor.getFqName().pathSegments();
            Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
            List<Name> list = listPathSegments;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((Name) it.next()).asString());
            }
            return new SuggestedName(arrayList, true, descriptor, packageFragmentDescriptor.getModuleDescriptor());
        }
        if (descriptor instanceof FakeCallableDescriptorForObject) {
            return generate$suggest(this, bindingContext, ((FakeCallableDescriptorForObject) descriptor).getReferencedObject());
        }
        if (descriptor instanceof TypeAliasConstructorDescriptor) {
            return generate$suggest(this, bindingContext, ((TypeAliasConstructorDescriptor) descriptor).getUnderlyingConstructorDescriptor());
        }
        if (descriptor instanceof ConstructorDescriptor) {
            ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor) descriptor;
            if (constructorDescriptor.isPrimary() || AnnotationsUtils.isNativeObject(descriptor)) {
                ClassifierDescriptorWithTypeParameters moduleDescriptor3 = constructorDescriptor.getModuleDescriptor();
                Intrinsics.checkNotNullExpressionValue(moduleDescriptor3, "getContainingDeclaration(...)");
                return generate$suggest(this, bindingContext, moduleDescriptor3);
            }
        } else if ((descriptor instanceof CallableDescriptor) && DescriptorUtils.isDescriptorWithLocalVisibility(descriptor)) {
            String nameForAnnotatedObject = AnnotationsUtils.getNameForAnnotatedObject(descriptor, bindingContext);
            if (nameForAnnotatedObject == null) {
                nameForAnnotatedObject = getSuggestedName(descriptor);
            }
            Intrinsics.checkNotNull(nameForAnnotatedObject);
            DeclarationDescriptor containingDeclaration = ((CallableDescriptor) descriptor).getModuleDescriptor();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (z) {
                DeclarationDescriptor moduleDescriptor4 = ((FunctionDescriptor) descriptor).getModuleDescriptor();
                Intrinsics.checkNotNullExpressionValue(moduleDescriptor4, "getContainingDeclaration(...)");
                String str = nameForAnnotatedObject;
                boolean z2 = false;
                while (moduleDescriptor4 instanceof FunctionDescriptor) {
                    FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) moduleDescriptor4;
                    if (functionDescriptor2.isInline() && INSTANCE.getOwnEffectiveVisibility((DeclarationDescriptorWithVisibility) moduleDescriptor4).isPublicAPI()) {
                        z2 = true;
                    }
                    str = getSuggestedName(moduleDescriptor4) + '$' + str;
                    moduleDescriptor4 = functionDescriptor2.getModuleDescriptor();
                    Intrinsics.checkNotNullExpressionValue(moduleDescriptor4, "getContainingDeclaration(...)");
                }
                if (z2) {
                    containingDeclaration = moduleDescriptor4;
                    nameForAnnotatedObject = str;
                }
            }
            return new SuggestedName(CollectionsKt.listOf(nameForAnnotatedObject), false, descriptor, containingDeclaration);
        }
        return generateDefault(descriptor, bindingContext);
    }

    private final SuggestedName generateDefault(DeclarationDescriptor descriptor, BindingContext bindingContext) {
        String suggestedName;
        DeclarationDescriptor moduleDescriptor;
        DeclarationDescriptor moduleDescriptor2;
        ArrayList arrayList = new ArrayList();
        if ((descriptor instanceof CallableMemberDescriptor) && ((CallableMemberDescriptor) descriptor).getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            CallableDescriptor overridden = INSTANCE.getOverridden((CallableDescriptor) descriptor);
            Intrinsics.checkNotNull(overridden, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableMemberDescriptor");
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) overridden;
            if (!ModalityUtilsKt.isOverridableOrOverrides(callableMemberDescriptor)) {
                descriptor = callableMemberDescriptor;
            }
        }
        ArrayList arrayList2 = arrayList;
        if (descriptor instanceof ConstructorDescriptor) {
            moduleDescriptor = descriptor.getModuleDescriptor();
            Intrinsics.checkNotNull(moduleDescriptor);
            suggestedName = getSuggestedName(moduleDescriptor) + "_init";
        } else {
            suggestedName = getSuggestedName(descriptor);
            moduleDescriptor = descriptor;
        }
        arrayList2.add(suggestedName);
        if ((moduleDescriptor.getModuleDescriptor() instanceof FunctionDescriptor) && !(moduleDescriptor instanceof TypeParameterDescriptor)) {
            DeclarationDescriptor moduleDescriptor3 = moduleDescriptor.getModuleDescriptor();
            Intrinsics.checkNotNull(moduleDescriptor3, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.FunctionDescriptor");
            SuggestedName suggestedNameSuggest = suggest((FunctionDescriptor) moduleDescriptor3, bindingContext);
            Intrinsics.checkNotNull(suggestedNameSuggest);
            arrayList2.add(CollectionsKt.single((List) suggestedNameSuggest.getNames()));
            moduleDescriptor2 = suggestedNameSuggest.getScope();
        } else {
            moduleDescriptor2 = moduleDescriptor.getModuleDescriptor();
            Intrinsics.checkNotNull(moduleDescriptor2);
        }
        if (moduleDescriptor2 instanceof PropertyDescriptor) {
            moduleDescriptor2 = ((PropertyDescriptor) moduleDescriptor2).getModuleDescriptor();
            Intrinsics.checkNotNullExpressionValue(moduleDescriptor2, "getContainingDeclaration(...)");
        }
        CollectionsKt.reverse(arrayList);
        Companion.NameAndStability nameAndStabilityMangleNameIfNecessary = INSTANCE.mangleNameIfNecessary(CollectionsKt.joinToString$default(arrayList, "$", null, null, 0, null, null, 62, null), descriptor, bindingContext);
        return new SuggestedName(CollectionsKt.listOf(nameAndStabilityMangleNameIfNecessary.getName()), nameAndStabilityMangleNameIfNecessary.getStable(), descriptor, moduleDescriptor2);
    }

    private final String getSuggestedName(DeclarationDescriptor descriptor) {
        Name name2 = descriptor.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (name2.isSpecial()) {
            if (descriptor instanceof PropertyGetterDescriptor) {
                StringBuilder sb = new StringBuilder("get_");
                PropertyDescriptor correspondingProperty = ((PropertyGetterDescriptor) descriptor).getCorrespondingProperty();
                Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
                return sb.append(getSuggestedName(correspondingProperty)).toString();
            }
            if (!(descriptor instanceof PropertySetterDescriptor)) {
                return "f";
            }
            StringBuilder sb2 = new StringBuilder("set_");
            PropertyDescriptor correspondingProperty2 = ((PropertySetterDescriptor) descriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty2, "getCorrespondingProperty(...)");
            return sb2.append(getSuggestedName(correspondingProperty2)).toString();
        }
        String strAsString = name2.asString();
        Intrinsics.checkNotNull(strAsString);
        return strAsString;
    }

    /* JADX INFO: compiled from: NameSuggestion.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u001bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0002J\u0018\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\rH\u0007J\u0018\u0010\u0011\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0007J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0018\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u0014H\u0002J\u0010\u0010\u0018\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0002J\u0010\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\tH\u0007R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/js/naming/NameSuggestion$Companion;", "", "()V", "ownEffectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "getOwnEffectiveVisibility", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;)Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "getInternalMangledName", "", "suggestedName", "forCalculateId", "getOverridden", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "descriptor", "getPrivateMangledName", "baseName", "getStableMangledName", "mangleNameIfNecessary", "Lorg/jetbrains/kotlin/js/naming/NameSuggestion$Companion$NameAndStability;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "mangleRegularNameIfNecessary", "mangledId", "sanitizeName", "name", "NameAndStability", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final NameAndStability mangleNameIfNecessary(String baseName, DeclarationDescriptor descriptor, BindingContext bindingContext) {
            if (descriptor instanceof CallableDescriptor) {
                descriptor = (DeclarationDescriptor) SequencesKt.last(SequencesKt.generateSequence(descriptor, new Function1<CallableDescriptor, CallableDescriptor>() { // from class: org.jetbrains.kotlin.js.naming.NameSuggestion$Companion$mangleNameIfNecessary$overriddenDescriptor$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CallableDescriptor invoke(CallableDescriptor it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        Collection<? extends CallableDescriptor> overriddenDescriptors = it.getOverriddenDescriptors();
                        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                        CallableDescriptor callableDescriptor = (CallableDescriptor) CollectionsKt.firstOrNull(overriddenDescriptors);
                        if (callableDescriptor != null) {
                            return callableDescriptor.getOriginal();
                        }
                        return null;
                    }
                }));
            }
            String nameForAnnotatedObject = AnnotationsUtils.getNameForAnnotatedObject(descriptor, bindingContext);
            if (nameForAnnotatedObject != null) {
                return new NameAndStability(nameForAnnotatedObject, true);
            }
            if (descriptor instanceof FunctionDescriptor) {
                String strAsString = DescriptorUtilsKt.getFqNameUnsafe(descriptor).asString();
                int iHashCode = strAsString.hashCode();
                if (iHashCode != -1544237172) {
                    if (iHashCode == 1930023214 && strAsString.equals("kotlin.Any.equals")) {
                        return new NameAndStability(Namer.EQUALS_METHOD_NAME, true);
                    }
                } else if (strAsString.equals("kotlin.CharSequence.get")) {
                    return new NameAndStability("charCodeAt", true);
                }
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
                DeclarationDescriptor moduleDescriptor = functionDescriptor.getModuleDescriptor();
                Intrinsics.checkNotNullExpressionValue(moduleDescriptor, "getContainingDeclaration(...)");
                if (moduleDescriptor instanceof ClassDescriptor) {
                    ReflectionTypes.Companion companion = ReflectionTypes.INSTANCE;
                    SimpleType defaultType = ((ClassDescriptor) moduleDescriptor).getDefaultType();
                    Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                    if (companion.isNumberedKPropertyOrKMutablePropertyType(defaultType)) {
                        String strAsString2 = functionDescriptor.getName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                        if (Intrinsics.areEqual(strAsString2, "get") ? true : Intrinsics.areEqual(strAsString2, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX)) {
                            return new NameAndStability(strAsString2, true);
                        }
                    }
                }
            } else if (descriptor instanceof PropertyDescriptor) {
                String strAsString3 = DescriptorUtilsKt.getFqNameUnsafe(descriptor).asString();
                if (strAsString3.hashCode() == -1572854188 && strAsString3.equals("kotlin.reflect.KCallable.name")) {
                    return new NameAndStability("callableName", true);
                }
            }
            return mangleRegularNameIfNecessary(baseName, descriptor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final CallableDescriptor getOverridden(CallableDescriptor descriptor) {
            return (CallableDescriptor) SequencesKt.last(SequencesKt.generateSequence(descriptor, new Function1<CallableDescriptor, CallableDescriptor>() { // from class: org.jetbrains.kotlin.js.naming.NameSuggestion$Companion$getOverridden$1
                @Override // kotlin.jvm.functions.Function1
                public final CallableDescriptor invoke(CallableDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    Collection<? extends CallableDescriptor> overriddenDescriptors = it.getOverriddenDescriptors();
                    Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                    CallableDescriptor callableDescriptor = (CallableDescriptor) CollectionsKt.firstOrNull(overriddenDescriptors);
                    if (callableDescriptor != null) {
                        return callableDescriptor.getOriginal();
                    }
                    return null;
                }
            }));
        }

        private final NameAndStability mangleRegularNameIfNecessary(String baseName, DeclarationDescriptor descriptor) {
            if (descriptor instanceof ClassOrPackageFragmentDescriptor) {
                return new NameAndStability(baseName, !DescriptorUtils.isDescriptorWithLocalVisibility(descriptor));
            }
            if (!(descriptor instanceof CallableMemberDescriptor)) {
                return mangleRegularNameIfNecessary$regularAndUnstable(baseName);
            }
            DescriptorVisibility ownEffectiveVisibility = getOwnEffectiveVisibility((DeclarationDescriptorWithVisibility) descriptor);
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) descriptor;
            DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getModuleDescriptor();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (containingDeclaration instanceof PackageFragmentDescriptor) {
                if (ownEffectiveVisibility.isPublicAPI()) {
                    return mangleRegularNameIfNecessary$mangledAndStable(baseName, descriptor);
                }
                return Intrinsics.areEqual(ownEffectiveVisibility, DescriptorVisibilities.INTERNAL) ? mangleRegularNameIfNecessary$mangledInternal(baseName, descriptor) : mangleRegularNameIfNecessary$regularAndUnstable(baseName);
            }
            if (containingDeclaration instanceof ClassDescriptor) {
                if ((!(descriptor instanceof FunctionDescriptor) || !DescriptorUtilsKt.isEnumValueOfMethod((FunctionDescriptor) descriptor)) && !Intrinsics.areEqual(ownEffectiveVisibility, DescriptorVisibilities.PUBLIC) && !ModalityUtilsKt.isOverridableOrOverrides(callableMemberDescriptor)) {
                    if (Intrinsics.areEqual(ownEffectiveVisibility, DescriptorVisibilities.PROTECTED)) {
                        ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
                        if (!ModalityUtilsKt.isFinalClass(classDescriptor) && classDescriptor.getVisibility().isPublicAPI()) {
                            return mangleRegularNameIfNecessary$mangledAndStable(baseName, descriptor);
                        }
                    }
                    if (Intrinsics.areEqual(ownEffectiveVisibility, DescriptorVisibilities.INTERNAL)) {
                        return mangleRegularNameIfNecessary$mangledInternal(baseName, descriptor);
                    }
                    ClassDescriptor classDescriptor2 = (ClassDescriptor) containingDeclaration;
                    return (!classDescriptor2.getVisibility().isPublicAPI() || ModalityUtilsKt.isFinalClass(classDescriptor2)) ? mangleRegularNameIfNecessary$regularAndUnstable(baseName) : mangleRegularNameIfNecessary$mangledPrivate(baseName, descriptor);
                }
                return mangleRegularNameIfNecessary$mangledAndStable(baseName, descriptor);
            }
            boolean z = containingDeclaration instanceof CallableMemberDescriptor;
            return mangleRegularNameIfNecessary$regularAndUnstable(baseName);
        }

        private static final NameAndStability mangleRegularNameIfNecessary$regularAndUnstable(String str) {
            return new NameAndStability(str, false);
        }

        private static final NameAndStability mangleRegularNameIfNecessary$mangledAndStable(String str, DeclarationDescriptor declarationDescriptor) {
            return new NameAndStability(NameSuggestion.INSTANCE.getStableMangledName(str, EncodeSignatureKt.encodeSignature((CallableDescriptor) declarationDescriptor)), true);
        }

        private static final NameAndStability mangleRegularNameIfNecessary$mangledInternal(String str, DeclarationDescriptor declarationDescriptor) {
            return new NameAndStability(NameSuggestion.INSTANCE.getInternalMangledName(str, EncodeSignatureKt.encodeSignature((CallableDescriptor) declarationDescriptor)), true);
        }

        private static final NameAndStability mangleRegularNameIfNecessary$mangledPrivate(String str, DeclarationDescriptor declarationDescriptor) {
            return new NameAndStability(NameSuggestion.INSTANCE.getPrivateMangledName(str, (CallableDescriptor) declarationDescriptor), false);
        }

        /* JADX INFO: compiled from: NameSuggestion.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/js/naming/NameSuggestion$Companion$NameAndStability;", "", "name", "", "stable", "", "(Ljava/lang/String;Z)V", "getName", "()Ljava/lang/String;", "getStable", "()Z", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class NameAndStability {
            private final String name;
            private final boolean stable;

            public static /* synthetic */ NameAndStability copy$default(NameAndStability nameAndStability, String str, boolean z, int i, Object obj) {
                if ((i & 1) != 0) {
                    str = nameAndStability.name;
                }
                if ((i & 2) != 0) {
                    z = nameAndStability.stable;
                }
                return nameAndStability.copy(str, z);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final String getName() {
                return this.name;
            }

            /* JADX INFO: renamed from: component2, reason: from getter */
            public final boolean getStable() {
                return this.stable;
            }

            public final NameAndStability copy(String name2, boolean stable) {
                Intrinsics.checkNotNullParameter(name2, "name");
                return new NameAndStability(name2, stable);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof NameAndStability)) {
                    return false;
                }
                NameAndStability nameAndStability = (NameAndStability) other;
                return Intrinsics.areEqual(this.name, nameAndStability.name) && this.stable == nameAndStability.stable;
            }

            public int hashCode() {
                return (this.name.hashCode() * 31) + Boolean.hashCode(this.stable);
            }

            public String toString() {
                return "NameAndStability(name=" + this.name + ", stable=" + this.stable + ')';
            }

            public NameAndStability(String name2, boolean z) {
                Intrinsics.checkNotNullParameter(name2, "name");
                this.name = name2;
                this.stable = z;
            }

            public final String getName() {
                return this.name;
            }

            public final boolean getStable() {
                return this.stable;
            }
        }

        @JvmStatic
        public final String getPrivateMangledName(String baseName, CallableDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(baseName, "baseName");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            DeclarationDescriptor containingDeclaration = descriptor.getModuleDescriptor();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            String strAsString = DescriptorUtilsKt.getFqNameUnsafe(containingDeclaration).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return getStableMangledName(sanitizeName(baseName), strAsString + '.' + baseName + ':' + EncodeSignatureKt.encodeSignature(descriptor));
        }

        public final String getInternalMangledName(String suggestedName, String forCalculateId) {
            Intrinsics.checkNotNullParameter(suggestedName, "suggestedName");
            Intrinsics.checkNotNullParameter(forCalculateId, "forCalculateId");
            return suggestedName + ("_" + mangledId("internal:" + forCalculateId) + '$');
        }

        @JvmStatic
        public final String getStableMangledName(String suggestedName, String forCalculateId) {
            Intrinsics.checkNotNullParameter(suggestedName, "suggestedName");
            Intrinsics.checkNotNullParameter(forCalculateId, "forCalculateId");
            return suggestedName + (forCalculateId.length() == 0 ? "" : "_" + mangledId(forCalculateId) + '$');
        }

        private final String mangledId(String forCalculateId) {
            int iAbs = Math.abs(forCalculateId.hashCode());
            if (iAbs == 0) {
                return "";
            }
            String string = Integer.toString(iAbs, CharsKt.checkRadix(36));
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final DescriptorVisibility getOwnEffectiveVisibility(DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility) {
            DescriptorVisibility visibility = declarationDescriptorWithVisibility.getVisibility();
            Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
            return EffectiveVisibilityUtilsKt.toDescriptorVisibility(EffectiveVisibilityUtilsKt.effectiveVisibility(visibility, (DeclarationDescriptor) declarationDescriptorWithVisibility, true));
        }

        @JvmStatic
        public final String sanitizeName(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            String str = name2;
            if (str.length() == 0) {
                return "_";
            }
            char cFirst = StringsKt.first(str);
            if (!IdentifierPolicyKt.isES5IdentifierStart(cFirst)) {
                cFirst = '_';
            }
            StringBuilder sbAppend = new StringBuilder().append(cFirst);
            String strDrop = StringsKt.drop(name2, 1);
            ArrayList arrayList = new ArrayList(strDrop.length());
            for (int i = 0; i < strDrop.length(); i++) {
                char cCharAt = strDrop.charAt(i);
                if (!IdentifierPolicyKt.isES5IdentifierPart(cCharAt)) {
                    cCharAt = '_';
                }
                arrayList.add(Character.valueOf(cCharAt));
            }
            return sbAppend.append(CollectionsKt.joinToString$default(arrayList, "", null, null, 0, null, null, 62, null)).toString();
        }
    }
}
