package org.jetbrains.kotlin.backend.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;

/* JADX INFO: compiled from: FunctionsFromAnyGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0016\u0010\u0019\u001a\u00020\u00182\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010H\u0002J\u001e\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010H$J\u0016\u0010\u001e\u001a\u00020\u00182\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010H\u0002J\u001e\u0010\u001f\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010H$J\u0016\u0010 \u001a\u00020\u00182\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010H\u0002J\u001e\u0010!\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010H$R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00108DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0013¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/backend/common/FunctionsFromAnyGenerator;", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/resolve/BindingContext;)V", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getDeclaration", "()Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "primaryConstructorParameters", "", "Lorg/jetbrains/kotlin/psi/KtParameter;", "getPrimaryConstructorParameters", "()Ljava/util/List;", "primaryConstructorProperties", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getPrimaryConstructorProperties", CompilerOptions.GENERATE, "", "generateEqualsIfNeeded", "properties", "generateEqualsMethod", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "generateHashCodeIfNeeded", "generateHashCodeMethod", "generateToStringIfNeeded", "generateToStringMethod", "backend-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FunctionsFromAnyGenerator {
    private final BindingContext bindingContext;
    private final ClassDescriptor classDescriptor;
    private final KtClassOrObject declaration;

    protected abstract void generateEqualsMethod(FunctionDescriptor function, List<? extends PropertyDescriptor> properties);

    protected abstract void generateHashCodeMethod(FunctionDescriptor function, List<? extends PropertyDescriptor> properties);

    protected abstract void generateToStringMethod(FunctionDescriptor function, List<? extends PropertyDescriptor> properties);

    public FunctionsFromAnyGenerator(KtClassOrObject declaration, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        this.declaration = declaration;
        this.bindingContext = bindingContext;
        Object notNull = BindingContextUtils.getNotNull(bindingContext, BindingContext.CLASS, declaration);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        this.classDescriptor = (ClassDescriptor) notNull;
    }

    protected final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    protected final KtClassOrObject getDeclaration() {
        return this.declaration;
    }

    protected final ClassDescriptor getClassDescriptor() {
        return this.classDescriptor;
    }

    public void generate() {
        List<PropertyDescriptor> primaryConstructorProperties = getPrimaryConstructorProperties();
        generateToStringIfNeeded(primaryConstructorProperties);
        generateHashCodeIfNeeded(primaryConstructorProperties);
        generateEqualsIfNeeded(primaryConstructorProperties);
    }

    private final void generateToStringIfNeeded(List<? extends PropertyDescriptor> properties) {
        FunctionDescriptor memberToGenerate = CodegenUtil.INSTANCE.getMemberToGenerate(this.classDescriptor, "toString", FunctionsFromAnyGenerator$generateToStringIfNeeded$function$1.INSTANCE, FunctionsFromAnyGenerator$generateToStringIfNeeded$function$2.INSTANCE);
        if (memberToGenerate == null) {
            return;
        }
        generateToStringMethod(memberToGenerate, properties);
    }

    private final void generateHashCodeIfNeeded(List<? extends PropertyDescriptor> properties) {
        FunctionDescriptor memberToGenerate = CodegenUtil.INSTANCE.getMemberToGenerate(this.classDescriptor, "hashCode", FunctionsFromAnyGenerator$generateHashCodeIfNeeded$function$1.INSTANCE, FunctionsFromAnyGenerator$generateHashCodeIfNeeded$function$2.INSTANCE);
        if (memberToGenerate == null) {
            return;
        }
        generateHashCodeMethod(memberToGenerate, properties);
    }

    private final void generateEqualsIfNeeded(List<? extends PropertyDescriptor> properties) {
        FunctionDescriptor memberToGenerate = CodegenUtil.INSTANCE.getMemberToGenerate(this.classDescriptor, Namer.EQUALS_METHOD_NAME, FunctionsFromAnyGenerator$generateEqualsIfNeeded$function$1.INSTANCE, new Function1<List<? extends ValueParameterDescriptor>, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator$generateEqualsIfNeeded$function$2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(List<? extends ValueParameterDescriptor> parameters) {
                Intrinsics.checkNotNullParameter(parameters, "parameters");
                return Boolean.valueOf(parameters.size() == 1 && KotlinBuiltIns.isNullableAny(((ValueParameterDescriptor) CollectionsKt.first((List) parameters)).getType()));
            }
        });
        if (memberToGenerate == null) {
            return;
        }
        generateEqualsMethod(memberToGenerate, properties);
    }

    protected final List<PropertyDescriptor> getPrimaryConstructorProperties() {
        List<KtParameter> primaryConstructorParameters = getPrimaryConstructorParameters();
        ArrayList arrayList = new ArrayList();
        for (Object obj : primaryConstructorParameters) {
            if (((KtParameter) obj).hasValOrVar()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            Object obj2 = this.bindingContext.get(BindingContext.PRIMARY_CONSTRUCTOR_PARAMETER, (KtParameter) it.next());
            Intrinsics.checkNotNull(obj2);
            arrayList3.add((PropertyDescriptor) obj2);
        }
        return arrayList3;
    }

    protected final List<KtParameter> getPrimaryConstructorParameters() {
        KtClassOrObject ktClassOrObject = this.declaration;
        KtClass ktClass = ktClassOrObject instanceof KtClass ? (KtClass) ktClassOrObject : null;
        List<KtParameter> primaryConstructorParameters = ktClass != null ? ktClass.getPrimaryConstructorParameters() : null;
        return primaryConstructorParameters == null ? CollectionsKt.emptyList() : primaryConstructorParameters;
    }
}
