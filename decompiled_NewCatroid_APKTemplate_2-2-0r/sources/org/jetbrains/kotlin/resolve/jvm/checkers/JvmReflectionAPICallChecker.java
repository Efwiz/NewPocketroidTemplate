package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.calls.checkers.AbstractReflectionApiCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: JvmReflectionAPICallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0014R\u001b\u0010\t\u001a\u00020\n8TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\t\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmReflectionAPICallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/AbstractReflectionApiCallChecker;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "reflectionTypes", "Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/builtins/ReflectionTypes;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "isWholeReflectionApiAvailable", "", "()Z", "isWholeReflectionApiAvailable$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "report", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmReflectionAPICallChecker extends AbstractReflectionApiCallChecker {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmReflectionAPICallChecker.class), "isWholeReflectionApiAvailable", "isWholeReflectionApiAvailable()Z"))};

    /* JADX INFO: renamed from: isWholeReflectionApiAvailable$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue isWholeReflectionApiAvailable;
    private final ModuleDescriptor module;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmReflectionAPICallChecker(ModuleDescriptor module, ReflectionTypes reflectionTypes, StorageManager storageManager) {
        super(reflectionTypes, storageManager);
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(reflectionTypes, "reflectionTypes");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.module = module;
        this.isWholeReflectionApiAvailable = storageManager.createLazyValue(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.resolve.jvm.checkers.JvmReflectionAPICallChecker.isWholeReflectionApiAvailable.2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(FindClassInModuleKt.findClassAcrossModuleDependencies(JvmReflectionAPICallChecker.this.module, JvmAbi.INSTANCE.getREFLECTION_FACTORY_IMPL()) != null);
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.AbstractReflectionApiCallChecker
    protected boolean isWholeReflectionApiAvailable() {
        return ((Boolean) StorageKt.getValue(this.isWholeReflectionApiAvailable, this, (KProperty<?>) $$delegatedProperties[0])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.AbstractReflectionApiCallChecker
    protected void report(PsiElement element, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(context, "context");
        context.getTrace().report(ErrorsJvm.NO_REFLECTION_IN_CLASS_PATH.on(element));
    }
}
