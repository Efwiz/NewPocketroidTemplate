package com.android.tools.r8.diagnostic.internal;

import com.android.tools.r8.diagnostic.DefinitionClassContext;
import com.android.tools.r8.diagnostic.DefinitionContext;
import com.android.tools.r8.diagnostic.DefinitionFieldContext;
import com.android.tools.r8.diagnostic.DefinitionMethodContext;
import com.android.tools.r8.graph.InterfaceC0593a0;
import com.android.tools.r8.graph.l5;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.utils.C3889e2;
import com.android.tools.r8.utils.V0;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class d {
    static final /* synthetic */ boolean a = true;

    public static void a(DefinitionContext definitionContext, Consumer<DefinitionClassContext> consumer, Consumer<DefinitionFieldContext> consumer2, Consumer<DefinitionMethodContext> consumer3) {
        if (definitionContext.isClassContext()) {
            consumer.accept(definitionContext.asClassContext());
            return;
        }
        if (definitionContext.isFieldContext()) {
            consumer2.accept(definitionContext.asFieldContext());
        } else {
            if (!a && !definitionContext.isMethodContext()) {
                throw new AssertionError();
            }
            consumer3.accept(definitionContext.asMethodContext());
        }
    }

    public static DefinitionContext a(l5 l5Var) {
        b bVarA;
        InterfaceC0593a0 context = l5Var.getContext();
        if (context.isClass()) {
            bVarA = a.a().a(context.asClass().Z0());
        } else if (context.j0()) {
            bVarA = e.a().a(context.d().y());
        } else if (context.h0()) {
            bVarA = f.a().a(context.c().y());
        } else {
            throw new C3405y10();
        }
        return bVarA.a(context.getOrigin()).a();
    }

    public static String a(DefinitionContext definitionContext) {
        Object objApply;
        Function function = new Function() { // from class: com.android.tools.r8.diagnostic.internal.d$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return d.a((DefinitionClassContext) obj);
            }
        };
        Function function2 = new Function() { // from class: com.android.tools.r8.diagnostic.internal.d$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return d.a((DefinitionFieldContext) obj);
            }
        };
        Function function3 = new Function() { // from class: com.android.tools.r8.diagnostic.internal.d$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return d.a((DefinitionMethodContext) obj);
            }
        };
        if (definitionContext.isClassContext()) {
            objApply = function.apply(definitionContext.asClassContext());
        } else if (definitionContext.isFieldContext()) {
            objApply = function2.apply(definitionContext.asFieldContext());
        } else {
            if (!a && !definitionContext.isMethodContext()) {
                throw new AssertionError();
            }
            objApply = function3.apply(definitionContext.asMethodContext());
        }
        return (String) objApply;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(DefinitionClassContext definitionClassContext) {
        return definitionClassContext.getClassReference().getTypeName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(DefinitionFieldContext definitionFieldContext) {
        return V0.a(definitionFieldContext.getFieldReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(DefinitionMethodContext definitionMethodContext) {
        return C3889e2.b(definitionMethodContext.getMethodReference());
    }
}
