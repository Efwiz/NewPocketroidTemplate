package org.jetbrains.kotlin.idea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;

/* JADX INFO: compiled from: MainFunctionDetector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u0000 (2\u00020\u0001:\u0002()B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B+\b\u0016\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b\u0012\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\n¢\u0006\u0002\u0010\rB%\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\n¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u0014\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0015\u001a\u00020\u0016J\u001a\u0010\u0014\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J.\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001a2\b\b\u0002\u0010\u001e\u001a\u00020\u001a2\b\b\u0002\u0010\u001f\u001a\u00020\u001aH\u0007J$\u0010\u0019\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u000b2\b\b\u0002\u0010\u001d\u001a\u00020\u001a2\b\b\u0002\u0010\u001f\u001a\u00020\u001aH\u0007J\u0018\u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001d\u001a\u00020\u001aH\u0002J \u0010$\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\u001aH\u0002R\u001c\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/idea/MainFunctionDetector;", "", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "languageVersionSettingsProvider", "Lkotlin/Function0;", "functionResolver", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)V", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lkotlin/jvm/functions/Function1;)V", "getFunctionDescriptor", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "languageVersionSettings$delegate", "Lkotlin/Lazy;", "getMainFunction", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "packageView", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "isMain", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "checkJvmStaticAnnotation", "checkReturnType", "allowParameterless", "function", "isMainWithParameter", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "isParameterNumberSuitsForMain", "parametersCount", "", "isTopLevel", "Companion", "Factory", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MainFunctionDetector {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Function1<KtNamedFunction, FunctionDescriptor> getFunctionDescriptor;
    private final Function0<LanguageVersionSettings> getLanguageVersionSettings;

    /* JADX INFO: renamed from: languageVersionSettings$delegate, reason: from kotlin metadata */
    private final Lazy languageVersionSettings;

    public final boolean isMain(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return isMain$default(this, descriptor, false, false, false, 14, null);
    }

    public final boolean isMain(DeclarationDescriptor descriptor, boolean z) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return isMain$default(this, descriptor, z, false, false, 12, null);
    }

    public final boolean isMain(DeclarationDescriptor descriptor, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return isMain$default(this, descriptor, z, z2, false, 8, null);
    }

    public final boolean isMain(KtNamedFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return isMain$default(this, function, false, false, 6, null);
    }

    public final boolean isMain(KtNamedFunction function, boolean z) {
        Intrinsics.checkNotNullParameter(function, "function");
        return isMain$default(this, function, z, false, 4, null);
    }

    private final LanguageVersionSettings getLanguageVersionSettings() {
        return (LanguageVersionSettings) this.languageVersionSettings.getValue();
    }

    public MainFunctionDetector(final BindingContext bindingContext, final LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.languageVersionSettings = LazyKt.lazy(new Function0<LanguageVersionSettings>() { // from class: org.jetbrains.kotlin.idea.MainFunctionDetector$languageVersionSettings$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LanguageVersionSettings invoke() {
                return (LanguageVersionSettings) this.this$0.getLanguageVersionSettings.invoke();
            }
        });
        this.getFunctionDescriptor = new Function1<KtNamedFunction, FunctionDescriptor>() { // from class: org.jetbrains.kotlin.idea.MainFunctionDetector.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final FunctionDescriptor invoke(KtNamedFunction function) {
                Intrinsics.checkNotNullParameter(function, "function");
                SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) bindingContext.get(BindingContext.FUNCTION, function);
                if (simpleFunctionDescriptor != null) {
                    return simpleFunctionDescriptor;
                }
                throw new KotlinExceptionWithAttachments("No descriptor resolved for " + function).withPsiAttachment("function.text", function);
            }
        };
        this.getLanguageVersionSettings = new Function0<LanguageVersionSettings>() { // from class: org.jetbrains.kotlin.idea.MainFunctionDetector.2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LanguageVersionSettings invoke() {
                return languageVersionSettings;
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    public MainFunctionDetector(Function0<? extends LanguageVersionSettings> languageVersionSettingsProvider, Function1<? super KtNamedFunction, ? extends FunctionDescriptor> functionResolver) {
        Intrinsics.checkNotNullParameter(languageVersionSettingsProvider, "languageVersionSettingsProvider");
        Intrinsics.checkNotNullParameter(functionResolver, "functionResolver");
        this.languageVersionSettings = LazyKt.lazy(new Function0<LanguageVersionSettings>() { // from class: org.jetbrains.kotlin.idea.MainFunctionDetector$languageVersionSettings$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LanguageVersionSettings invoke() {
                return (LanguageVersionSettings) this.this$0.getLanguageVersionSettings.invoke();
            }
        });
        this.getLanguageVersionSettings = languageVersionSettingsProvider;
        this.getFunctionDescriptor = functionResolver;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MainFunctionDetector(final LanguageVersionSettings languageVersionSettings, Function1<? super KtNamedFunction, ? extends FunctionDescriptor> functionResolver) {
        this(new Function0<LanguageVersionSettings>() { // from class: org.jetbrains.kotlin.idea.MainFunctionDetector.3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LanguageVersionSettings invoke() {
                return languageVersionSettings;
            }
        }, functionResolver);
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(functionResolver, "functionResolver");
    }

    public static /* synthetic */ boolean isMain$default(MainFunctionDetector mainFunctionDetector, KtNamedFunction ktNamedFunction, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        if ((i & 4) != 0) {
            z2 = true;
        }
        return mainFunctionDetector.isMain(ktNamedFunction, z, z2);
    }

    public final boolean isMain(KtNamedFunction function, boolean checkJvmStaticAnnotation, boolean allowParameterless) {
        FunctionDescriptor functionDescriptorInvoke;
        Intrinsics.checkNotNullParameter(function, "function");
        if (function.isLocal()) {
            return false;
        }
        int size = function.getValueParameters().size();
        if (function.mo7393getReceiverTypeReference() != null) {
            size++;
        }
        if (!isParameterNumberSuitsForMain(size, function.isTopLevel(), allowParameterless) || !function.getTypeParameters().isEmpty()) {
            return false;
        }
        if (!Intrinsics.areEqual("main", function.getMyName()) && !INSTANCE.hasAnnotationWithExactNumberOfArguments(function, 1)) {
            return false;
        }
        if ((!checkJvmStaticAnnotation || function.isTopLevel() || INSTANCE.hasAnnotationWithExactNumberOfArguments(function, 0)) && (functionDescriptorInvoke = this.getFunctionDescriptor.invoke(function)) != null) {
            return isMain$default(this, functionDescriptorInvoke, checkJvmStaticAnnotation, false, allowParameterless, 4, null);
        }
        return false;
    }

    public static /* synthetic */ boolean isMain$default(MainFunctionDetector mainFunctionDetector, DeclarationDescriptor declarationDescriptor, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        if ((i & 4) != 0) {
            z2 = true;
        }
        if ((i & 8) != 0) {
            z3 = true;
        }
        return mainFunctionDetector.isMain(declarationDescriptor, z, z2, z3);
    }

    public final boolean isMain(DeclarationDescriptor descriptor, boolean checkJvmStaticAnnotation, boolean checkReturnType, boolean allowParameterless) {
        List<KtDeclaration> declarations;
        KotlinType type;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!(descriptor instanceof FunctionDescriptor)) {
            return false;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
        if (!Intrinsics.areEqual(INSTANCE.getJVMFunctionName(functionDescriptor), "main")) {
            return false;
        }
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((ValueParameterDescriptor) it.next()).getType());
        }
        ArrayList arrayList2 = arrayList;
        ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null && (type = extensionReceiverParameter.getType()) != null) {
            arrayList2.add(type);
        }
        if (!isParameterNumberSuitsForMain(arrayList2.size(), DescriptorUtils.isTopLevelDeclaration(descriptor), allowParameterless)) {
            return false;
        }
        List<TypeParameterDescriptor> typeParameters = functionDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        if (!typeParameters.isEmpty()) {
            return false;
        }
        if (arrayList2.size() == 1) {
            Object obj = arrayList2.get(0);
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            KotlinType kotlinType = (KotlinType) obj;
            if (!KotlinBuiltIns.isArray(kotlinType)) {
                return false;
            }
            List<TypeProjection> arguments = kotlinType.getArguments();
            if (arguments.size() != 1) {
                return false;
            }
            KotlinType type2 = arguments.get(0).getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            if (!KotlinBuiltIns.isString(type2) || arguments.get(0).getProjectionKind() == Variance.IN_VARIANCE) {
                return false;
            }
        } else {
            arrayList2.size();
            DescriptorUtils.isTopLevelDeclaration(descriptor);
            KtFile containingFile = DescriptorToSourceUtils.getContainingFile(descriptor);
            if (!Intrinsics.areEqual(functionDescriptor.getShortName().asString(), "main")) {
                return false;
            }
            if (containingFile != null && (declarations = containingFile.getDeclarations()) != null) {
                List<KtDeclaration> list = declarations;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator<T> it2 = list.iterator();
                    while (it2.hasNext()) {
                        if (isMainWithParameter((KtDeclaration) it2.next(), checkJvmStaticAnnotation)) {
                            return false;
                        }
                    }
                }
            }
        }
        if (functionDescriptor.isSuspend() && !getLanguageVersionSettings().supportsFeature(LanguageFeature.ExtendedMainConvention)) {
            return false;
        }
        if (checkReturnType && !INSTANCE.isMainReturnType(functionDescriptor)) {
            return false;
        }
        if (DescriptorUtils.isTopLevelDeclaration(descriptor)) {
            return true;
        }
        DeclarationDescriptor containingDeclaration = functionDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if ((containingDeclaration instanceof ClassDescriptor) && ((ClassDescriptor) containingDeclaration).getKind().isSingleton()) {
            return AnnotationUtilKt.hasJvmStaticAnnotation(descriptor) || !checkJvmStaticAnnotation;
        }
        return false;
    }

    private final boolean isMainWithParameter(KtDeclaration declaration, boolean checkJvmStaticAnnotation) {
        return (declaration instanceof KtNamedFunction) && isMain((KtNamedFunction) declaration, checkJvmStaticAnnotation, false);
    }

    public final FunctionDescriptor getMainFunction(ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        FqName ROOT = FqName.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        return getMainFunction(module, module.getPackage(ROOT));
    }

    private final FunctionDescriptor getMainFunction(ModuleDescriptor module, PackageViewDescriptor packageView) {
        FunctionDescriptor functionDescriptor;
        List<PackageFragmentDescriptor> fragments = packageView.getFragments();
        ArrayList arrayList = new ArrayList();
        for (Object obj : fragments) {
            if (Intrinsics.areEqual(DescriptorUtilsKt.getModule((PackageFragmentDescriptor) obj), module)) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        do {
            Object obj2 = null;
            if (it.hasNext()) {
                Collection<DeclarationDescriptor> allDescriptors = DescriptorUtils.getAllDescriptors(((PackageFragmentDescriptor) it.next()).getScope());
                Intrinsics.checkNotNullExpressionValue(allDescriptors, "getAllDescriptors(...)");
                ArrayList arrayList2 = new ArrayList();
                for (Object obj3 : allDescriptors) {
                    if (obj3 instanceof FunctionDescriptor) {
                        arrayList2.add(obj3);
                    }
                }
                for (Object obj4 : arrayList2) {
                    if (isMain$default(this, (FunctionDescriptor) obj4, false, false, false, 14, null)) {
                        obj2 = obj4;
                        break;
                    }
                }
                functionDescriptor = (FunctionDescriptor) obj2;
            } else {
                Iterator<FqName> it2 = module.getSubPackagesOf(packageView.getFqName(), MemberScope.INSTANCE.getALL_NAME_FILTER()).iterator();
                while (it2.hasNext()) {
                    FunctionDescriptor mainFunction = getMainFunction(module, module.getPackage(it2.next()));
                    if (mainFunction != null) {
                        return mainFunction;
                    }
                }
                return null;
            }
        } while (functionDescriptor == null);
        return functionDescriptor;
    }

    private final boolean isParameterNumberSuitsForMain(int parametersCount, boolean isTopLevel, boolean allowParameterless) {
        if (parametersCount != 0) {
            if (parametersCount != 1) {
                return false;
            }
        } else if (!isTopLevel || !allowParameterless || !getLanguageVersionSettings().supportsFeature(LanguageFeature.ExtendedMainConvention)) {
            return false;
        }
        return true;
    }

    /* JADX INFO: compiled from: MainFunctionDetector.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0006H\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/idea/MainFunctionDetector$Companion;", "", "()V", "getJVMFunctionName", "", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "hasAnnotationWithExactNumberOfArguments", "", "function", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "number", "", "isMainReturnType", "descriptor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isMainReturnType(FunctionDescriptor descriptor) {
            KotlinType returnType = descriptor.getReturnType();
            return returnType != null && KotlinBuiltIns.isUnit(returnType);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getJVMFunctionName(FunctionDescriptor functionDescriptor) {
            String jvmName = DescriptorUtils.getJvmName(functionDescriptor);
            if (jvmName != null) {
                return jvmName;
            }
            String strAsString = functionDescriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean hasAnnotationWithExactNumberOfArguments(KtNamedFunction function, int number) {
            List<KtAnnotationEntry> annotationEntries = function.getAnnotationEntries();
            Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
            List<KtAnnotationEntry> list = annotationEntries;
            if ((list instanceof Collection) && list.isEmpty()) {
                return false;
            }
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (((KtAnnotationEntry) it.next()).getValueArguments().size() == number) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX INFO: compiled from: MainFunctionDetector.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\bJ\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/idea/MainFunctionDetector$Factory;", "", "createMainFunctionDetector", "Lorg/jetbrains/kotlin/idea/MainFunctionDetector;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "Ordinary", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Factory {
        MainFunctionDetector createMainFunctionDetector(BindingTrace trace, LanguageVersionSettings languageVersionSettings);

        /* JADX INFO: compiled from: MainFunctionDetector.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/idea/MainFunctionDetector$Factory$Ordinary;", "Lorg/jetbrains/kotlin/idea/MainFunctionDetector$Factory;", "()V", "createMainFunctionDetector", "Lorg/jetbrains/kotlin/idea/MainFunctionDetector;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Ordinary implements Factory {
            @Override // org.jetbrains.kotlin.idea.MainFunctionDetector.Factory
            public MainFunctionDetector createMainFunctionDetector(BindingTrace trace, LanguageVersionSettings languageVersionSettings) {
                Intrinsics.checkNotNullParameter(trace, "trace");
                Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
                BindingContext bindingContext = trace.getContext();
                Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
                return new MainFunctionDetector(bindingContext, languageVersionSettings);
            }
        }
    }
}
