package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: AbstractReflectionApiCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\"\u0010\u0015\u001a\u00020\u00162\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0018\u0010\u001d\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0014J \u0010 \u001a\u00020\b2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u001cH\u0014J\u0010\u0010$\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0018\u0010%\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH$J\f\u0010'\u001a\u00020\b*\u00020\u000bH\u0002R\u0012\u0010\u0007\u001a\u00020\bX¤\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tR\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR!\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u000f\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/AbstractReflectionApiCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "reflectionTypes", "Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/builtins/ReflectionTypes;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "isWholeReflectionApiAvailable", "", "()Z", "kClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", Namer.GET_KCLASS, "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "kClass$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "kPropertyClasses", "", "getKPropertyClasses", "()Ljava/util/Set;", "kPropertyClasses$delegate", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "isAllowedKClassMember", "name", "Lorg/jetbrains/kotlin/name/Name;", "isAllowedReflectionApi", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "containingClass", "isReflectionSource", "report", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "isKPropertyClass", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractReflectionApiCallChecker implements CallChecker {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(AbstractReflectionApiCallChecker.class), "kPropertyClasses", "getKPropertyClasses()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(AbstractReflectionApiCallChecker.class), "kClass", "getKClass()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;"))};

    /* JADX INFO: renamed from: kClass$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue kClass;

    /* JADX INFO: renamed from: kPropertyClasses$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue kPropertyClasses;
    private final ReflectionTypes reflectionTypes;

    protected abstract boolean isWholeReflectionApiAvailable();

    protected abstract void report(PsiElement element, CallCheckerContext context);

    public AbstractReflectionApiCallChecker(ReflectionTypes reflectionTypes, StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(reflectionTypes, "reflectionTypes");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.reflectionTypes = reflectionTypes;
        this.kPropertyClasses = storageManager.createLazyValue(new Function0<Set<? extends ClassDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.calls.checkers.AbstractReflectionApiCallChecker$kPropertyClasses$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends ClassDescriptor> invoke() {
                return SetsKt.setOf((Object[]) new ClassDescriptor[]{this.this$0.reflectionTypes.getKProperty0(), this.this$0.reflectionTypes.getKProperty1(), this.this$0.reflectionTypes.getKProperty2()});
            }
        });
        this.kClass = storageManager.createLazyValue(new Function0<ClassDescriptor>() { // from class: org.jetbrains.kotlin.resolve.calls.checkers.AbstractReflectionApiCallChecker$kClass$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassDescriptor invoke() {
                return this.this$0.reflectionTypes.getKClass();
            }
        });
    }

    private final Set<ClassDescriptor> getKPropertyClasses() {
        return (Set) StorageKt.getValue(this.kPropertyClasses, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    private final ClassDescriptor getKClass() {
        return (ClassDescriptor) StorageKt.getValue(this.kClass, this, (KProperty<?>) $$delegatedProperties[1]);
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0051 A[ORIG_RETURN, RETURN] */
    protected boolean isAllowedKClassMember(Name name2, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(context, "context");
        String strAsString = name2.asString();
        int iHashCode = strAsString.hashCode();
        if (iHashCode == -1431767203) {
            if (strAsString.equals(Namer.METADATA_SIMPLE_NAME)) {
                return true;
            }
            return false;
        }
        if (iHashCode != -572444345) {
            if (iHashCode == -238142497 && strAsString.equals("isInstance")) {
                return true;
            }
        } else if (strAsString.equals("qualifiedName")) {
            return ((Boolean) context.getLanguageVersionSettings().getFlag(AnalysisFlags.getAllowFullyQualifiedNameInKClass())).booleanValue();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public final void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (isWholeReflectionApiAvailable() || isReflectionSource(reportOn)) {
            return;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        DeclarationDescriptor containingDeclaration = resultingDescriptor.getContainingDeclaration();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor != null && ReflectionTypes.INSTANCE.isReflectionClass(classDescriptor)) {
            Intrinsics.checkNotNull(resultingDescriptor);
            if (isAllowedReflectionApi(resultingDescriptor, classDescriptor, context)) {
                return;
            }
            report(reportOn, context);
        }
    }

    protected boolean isAllowedReflectionApi(CallableDescriptor descriptor, ClassDescriptor containingClass, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(context, "context");
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return AbstractReflectionApiCallCheckerKt.ALLOWED_MEMBER_NAMES.contains(name2.asString()) || (DescriptorUtils.isSubclass(containingClass, getKClass()) && isAllowedKClassMember(name2, context)) || (((Intrinsics.areEqual(name2.asString(), "get") || Intrinsics.areEqual(name2.asString(), FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX)) && isKPropertyClass(containingClass)) || AbstractReflectionApiCallCheckerKt.ALLOWED_CLASSES.contains(DescriptorUtilsKt.getFqNameSafe(containingClass)));
    }

    private final boolean isKPropertyClass(ClassDescriptor classDescriptor) {
        Set<ClassDescriptor> kPropertyClasses = getKPropertyClasses();
        if ((kPropertyClasses instanceof Collection) && kPropertyClasses.isEmpty()) {
            return false;
        }
        Iterator<T> it = kPropertyClasses.iterator();
        while (it.hasNext()) {
            if (DescriptorUtils.isSubclass(classDescriptor, (ClassDescriptor) it.next())) {
                return true;
            }
        }
        return false;
    }

    private final boolean isReflectionSource(PsiElement reportOn) {
        PsiFile containingFile = reportOn.getContainingFile();
        KtFile ktFile = containingFile instanceof KtFile ? (KtFile) containingFile : null;
        if (ktFile == null) {
            return false;
        }
        return ktFile.getPackageFqName().startsWith(StandardNames.KOTLIN_REFLECT_FQ_NAME);
    }
}
