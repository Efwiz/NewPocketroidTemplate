package org.jetbrains.kotlin.resolve;

import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeImpl;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.LocalRedeclarationChecker;
import org.jetbrains.kotlin.resolve.scopes.TraceBasedLocalRedeclarationChecker;
import org.jetbrains.kotlin.types.IndexedParametersSubstitution;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: loaded from: classes6.dex */
public class FunctionDescriptorUtil {
    private static final TypeSubstitutor MAKE_TYPE_PARAMETERS_FRESH = TypeSubstitutor.create(new TypeSubstitution() { // from class: org.jetbrains.kotlin.resolve.FunctionDescriptorUtil.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", ASTExpr.DEFAULT_MAP_KEY_NAME, "org/jetbrains/kotlin/resolve/FunctionDescriptorUtil$1", "get"));
        }

        @Override // org.jetbrains.kotlin.types.TypeSubstitution
        /* JADX INFO: renamed from: get */
        public TypeProjection mo7475get(KotlinType kotlinType) {
            if (kotlinType != null) {
                return null;
            }
            $$$reportNull$$$0(0);
            return null;
        }

        public String toString() {
            return "FunctionDescriptorUtil.MAKE_TYPE_PARAMETERS_FRESH";
        }
    });

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "typeArguments";
                break;
            case 2:
            case 6:
                objArr[0] = "outerScope";
                break;
            case 3:
            case 7:
                objArr[0] = "descriptor";
                break;
            case 4:
                objArr[0] = "trace";
                break;
            case 5:
                objArr[0] = "overloadChecker";
                break;
            case 8:
                objArr[0] = "redeclarationChecker";
                break;
            default:
                objArr[0] = "functionDescriptor";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/FunctionDescriptorUtil";
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[2] = "getFunctionInnerScope";
                break;
            default:
                objArr[2] = "createSubstitution";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private FunctionDescriptorUtil() {
    }

    public static TypeSubstitution createSubstitution(FunctionDescriptor functionDescriptor, List<KotlinType> list) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        return functionDescriptor.getTypeParameters().isEmpty() ? TypeSubstitution.EMPTY : new IndexedParametersSubstitution(functionDescriptor.getTypeParameters(), TypeUtilsKt.defaultProjections(list));
    }

    public static LexicalScope getFunctionInnerScope(LexicalScope lexicalScope, FunctionDescriptor functionDescriptor, BindingTrace bindingTrace, OverloadChecker overloadChecker) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(2);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(4);
        }
        if (overloadChecker == null) {
            $$$reportNull$$$0(5);
        }
        return getFunctionInnerScope(lexicalScope, functionDescriptor, new TraceBasedLocalRedeclarationChecker(bindingTrace, overloadChecker));
    }

    public static LexicalScope getFunctionInnerScope(LexicalScope lexicalScope, final FunctionDescriptor functionDescriptor, LocalRedeclarationChecker localRedeclarationChecker) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(6);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        if (localRedeclarationChecker == null) {
            $$$reportNull$$$0(8);
        }
        return new LexicalScopeImpl(lexicalScope, functionDescriptor, true, functionDescriptor.getExtensionReceiverParameter(), functionDescriptor.getContextReceiverParameters(), LexicalScopeKind.FUNCTION_INNER_SCOPE, localRedeclarationChecker, new Function1() { // from class: org.jetbrains.kotlin.resolve.FunctionDescriptorUtil$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return FunctionDescriptorUtil.lambda$getFunctionInnerScope$0(functionDescriptor, (LexicalScopeImpl.InitializeHandler) obj);
            }
        });
    }

    static /* synthetic */ Unit lambda$getFunctionInnerScope$0(FunctionDescriptor functionDescriptor, LexicalScopeImpl.InitializeHandler initializeHandler) {
        Iterator<TypeParameterDescriptor> it = functionDescriptor.getTypeParameters().iterator();
        while (it.hasNext()) {
            initializeHandler.addClassifierDescriptor(it.next());
        }
        for (ValueParameterDescriptor valueParameterDescriptor : functionDescriptor.getValueParameters()) {
            if (valueParameterDescriptor instanceof ValueParameterDescriptorImpl.WithDestructuringDeclaration) {
                Iterator<VariableDescriptor> it2 = ((ValueParameterDescriptorImpl.WithDestructuringDeclaration) valueParameterDescriptor).getDestructuringVariables().iterator();
                while (it2.hasNext()) {
                    initializeHandler.addVariableDescriptor(it2.next());
                }
            } else {
                initializeHandler.addVariableDescriptor(valueParameterDescriptor);
            }
        }
        return Unit.INSTANCE;
    }

    public static <D extends CallableDescriptor> D alphaConvertTypeParameters(D d) {
        return (D) d.substitute(MAKE_TYPE_PARAMETERS_FRESH);
    }
}
