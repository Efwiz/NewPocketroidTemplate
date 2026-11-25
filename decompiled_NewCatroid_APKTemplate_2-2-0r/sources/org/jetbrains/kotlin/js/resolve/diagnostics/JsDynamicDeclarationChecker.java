package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: JsDynamicDeclarationChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsDynamicDeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsDynamicDeclarationChecker implements DeclarationChecker {
    public static final JsDynamicDeclarationChecker INSTANCE = new JsDynamicDeclarationChecker();

    private JsDynamicDeclarationChecker() {
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0046  */
    /* JADX WARN: Code duplicated, block: B:17:0x004c  */
    /* JADX WARN: Code duplicated, block: B:54:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:55:? A[RETURN, SYNTHETIC] */
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KotlinType type;
        KotlinType type2;
        KotlinType returnType;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        BindingTrace trace = context.getTrace();
        if ((declaration instanceof KtProperty) && (descriptor instanceof VariableDescriptorWithAccessors)) {
            KtExpression delegateExpression = ((KtProperty) declaration).getDelegateExpression();
            if (delegateExpression != null) {
                ResolvedCall resolvedCall = (ResolvedCall) trace.get(BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL, descriptor);
                if (resolvedCall == null || (returnType = ((FunctionDescriptor) resolvedCall.getResultingDescriptor()).getReturnType()) == null) {
                    type2 = trace.getType(delegateExpression);
                    if (type2 != null) {
                        return;
                    }
                    Intrinsics.checkNotNull(type2);
                    if (!DynamicTypesKt.isDynamic(type2)) {
                        return;
                    }
                } else {
                    Intrinsics.checkNotNull(returnType);
                    if (!DynamicTypesKt.isDynamic(returnType)) {
                        type2 = trace.getType(delegateExpression);
                        if (type2 != null) {
                            return;
                        }
                        Intrinsics.checkNotNull(type2);
                        if (!DynamicTypesKt.isDynamic(type2)) {
                            return;
                        }
                    }
                }
                trace.report(ErrorsJs.PROPERTY_DELEGATION_BY_DYNAMIC.on(delegateExpression));
                return;
            }
            return;
        }
        if (declaration instanceof KtClassOrObject) {
            List<KtSuperTypeListEntry> superTypeListEntries = ((KtClassOrObject) declaration).getSuperTypeListEntries();
            ArrayList arrayList = new ArrayList();
            for (Object obj : superTypeListEntries) {
                if (obj instanceof KtDelegatedSuperTypeEntry) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                KtExpression delegateExpression2 = ((KtDelegatedSuperTypeEntry) it.next()).getDelegateExpression();
                if (delegateExpression2 != null && (type = trace.getType(delegateExpression2)) != null && DynamicTypesKt.isDynamic(type)) {
                    trace.report(ErrorsJs.DELEGATION_BY_DYNAMIC.on(delegateExpression2));
                }
            }
        }
    }
}
