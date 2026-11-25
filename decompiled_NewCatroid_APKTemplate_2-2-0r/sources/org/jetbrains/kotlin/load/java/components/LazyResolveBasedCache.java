package org.jetbrains.kotlin.load.java.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaMember;
import org.jetbrains.kotlin.load.java.structure.impl.JavaClassImpl;
import org.jetbrains.kotlin.load.java.structure.impl.JavaElementImpl;
import org.jetbrains.kotlin.load.java.structure.impl.JavaFieldImpl;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.lazy.ResolveSession;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: LazyResolveBasedCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0012H\u0016J\u0018\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\t\u001a\u00020\u0016H\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/load/java/components/LazyResolveBasedCache;", "Lorg/jetbrains/kotlin/load/java/components/AbstractJavaResolverCache;", "resolveSession", "Lorg/jetbrains/kotlin/resolve/lazy/ResolveSession;", "(Lorg/jetbrains/kotlin/resolve/lazy/ResolveSession;)V", "recordClass", "", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "recordConstructor", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/load/java/structure/JavaElement;", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "recordField", "field", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "recordMethod", "member", "Lorg/jetbrains/kotlin/load/java/structure/JavaMember;", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyResolveBasedCache extends AbstractJavaResolverCache {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyResolveBasedCache(ResolveSession resolveSession) {
        super(resolveSession);
        Intrinsics.checkNotNullParameter(resolveSession, "resolveSession");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.load.java.components.JavaResolverCache
    public void recordMethod(JavaMember member, SimpleFunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(member, "member");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        BindingTrace trace = getTrace();
        JavaElementImpl javaElementImpl = member instanceof JavaElementImpl ? (JavaElementImpl) member : null;
        PsiElement psi = javaElementImpl != null ? javaElementImpl.getPsi() : null;
        if (psi == null) {
            return;
        }
        BindingContextUtils.recordFunctionDeclarationToDescriptor(trace, psi, descriptor);
    }

    @Override // org.jetbrains.kotlin.load.java.components.JavaResolverCache
    public void recordConstructor(JavaElement element, ConstructorDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        BindingTrace trace = getTrace();
        WritableSlice<PsiElement, ConstructorDescriptor> writableSlice = BindingContext.CONSTRUCTOR;
        JavaElementImpl javaElementImpl = element instanceof JavaElementImpl ? (JavaElementImpl) element : null;
        PsiElement psi = javaElementImpl != null ? javaElementImpl.getPsi() : null;
        if (psi == null) {
            return;
        }
        trace.record(writableSlice, psi, descriptor);
    }

    @Override // org.jetbrains.kotlin.load.java.components.JavaResolverCache
    public void recordField(JavaField field, PropertyDescriptor descriptor) {
        PsiField psiField;
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        BindingTrace trace = getTrace();
        WritableSlice<PsiElement, VariableDescriptor> writableSlice = BindingContext.VARIABLE;
        JavaFieldImpl javaFieldImpl = field instanceof JavaFieldImpl ? (JavaFieldImpl) field : null;
        if (javaFieldImpl == null || (psiField = (PsiField) javaFieldImpl.getPsi()) == null) {
            return;
        }
        trace.record(writableSlice, psiField, descriptor);
    }

    @Override // org.jetbrains.kotlin.load.java.components.JavaResolverCache
    public void recordClass(JavaClass javaClass, ClassDescriptor descriptor) {
        PsiClass psiClass;
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        BindingTrace trace = getTrace();
        WritableSlice<PsiElement, ClassDescriptor> writableSlice = BindingContext.CLASS;
        JavaClassImpl javaClassImpl = javaClass instanceof JavaClassImpl ? (JavaClassImpl) javaClass : null;
        if (javaClassImpl == null || (psiClass = (PsiClass) javaClassImpl.getPsi()) == null) {
            return;
        }
        trace.record(writableSlice, psiClass, descriptor);
    }
}
