package org.jetbrains.kotlin.codegen;

import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodGenericSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: loaded from: classes3.dex */
public abstract class FunctionGenerationStrategy {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 6 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 6 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "name";
                break;
            case 2:
                objArr[0] = "desc";
                break;
            case 3:
                objArr[0] = "functionDescriptor";
                break;
            case 4:
                objArr[0] = "typeMapper";
                break;
            case 5:
                objArr[0] = "contextKind";
                break;
            case 6:
                objArr[0] = "org/jetbrains/kotlin/codegen/FunctionGenerationStrategy";
                break;
            default:
                objArr[0] = JvmAnnotationNames.METADATA_VERSION_FIELD_NAME;
                break;
        }
        if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/codegen/FunctionGenerationStrategy";
        } else {
            objArr[1] = "mapMethodSignature";
        }
        if (i == 3 || i == 4 || i == 5) {
            objArr[2] = "mapMethodSignature";
        } else if (i != 6) {
            objArr[2] = "wrapMethodVisitor";
        }
        String str2 = String.format(str, objArr);
        if (i == 6) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract void generateBody(MethodVisitor methodVisitor, FrameMap frameMap, JvmMethodSignature jvmMethodSignature, MethodContext methodContext, MemberCodegen<?> memberCodegen);

    public boolean skipGenericSignature() {
        return false;
    }

    public abstract boolean skipNotNullAssertionsForParameters();

    public MethodVisitor wrapMethodVisitor(MethodVisitor methodVisitor, int i, String str, String str2) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (str2 == null) {
            $$$reportNull$$$0(2);
        }
        return methodVisitor;
    }

    public JvmMethodGenericSignature mapMethodSignature(FunctionDescriptor functionDescriptor, KotlinTypeMapper kotlinTypeMapper, OwnerKind ownerKind, boolean z) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(4);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(5);
        }
        JvmMethodGenericSignature jvmMethodGenericSignatureMapSignatureWithGeneric = kotlinTypeMapper.mapSignatureWithGeneric(functionDescriptor, ownerKind, z);
        if (jvmMethodGenericSignatureMapSignatureWithGeneric == null) {
            $$$reportNull$$$0(6);
        }
        return jvmMethodGenericSignatureMapSignatureWithGeneric;
    }

    public static class FunctionDefault extends CodegenBased {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final KtDeclarationWithBody declaration;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "declaration";
            } else if (i == 2) {
                objArr[0] = "codegen";
            } else if (i != 3) {
                objArr[0] = "state";
            } else {
                objArr[0] = "signature";
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/FunctionGenerationStrategy$FunctionDefault";
            if (i == 2 || i == 3) {
                objArr[2] = "doGenerateBody";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FunctionDefault(GenerationState generationState, KtDeclarationWithBody ktDeclarationWithBody) {
            super(generationState);
            if (generationState == null) {
                $$$reportNull$$$0(0);
            }
            if (ktDeclarationWithBody == null) {
                $$$reportNull$$$0(1);
            }
            this.declaration = ktDeclarationWithBody;
        }

        @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
        public void doGenerateBody(ExpressionCodegen expressionCodegen, JvmMethodSignature jvmMethodSignature) {
            if (expressionCodegen == null) {
                $$$reportNull$$$0(2);
            }
            if (jvmMethodSignature == null) {
                $$$reportNull$$$0(3);
            }
            expressionCodegen.returnExpression(this.declaration.getBodyExpression());
        }
    }

    public static abstract class CodegenBased extends FunctionGenerationStrategy {
        protected final GenerationState state;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = JvmAnnotationNames.METADATA_VERSION_FIELD_NAME;
            } else if (i == 2) {
                objArr[0] = "frameMap";
            } else if (i == 3) {
                objArr[0] = "signature";
            } else if (i == 4) {
                objArr[0] = "context";
            } else if (i != 5) {
                objArr[0] = "state";
            } else {
                objArr[0] = "parentCodegen";
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/FunctionGenerationStrategy$CodegenBased";
            if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
                objArr[2] = "generateBody";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public abstract void doGenerateBody(ExpressionCodegen expressionCodegen, JvmMethodSignature jvmMethodSignature);

        @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
        public boolean skipNotNullAssertionsForParameters() {
            return false;
        }

        public CodegenBased(GenerationState generationState) {
            if (generationState == null) {
                $$$reportNull$$$0(0);
            }
            this.state = generationState;
        }

        @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
        public final void generateBody(MethodVisitor methodVisitor, FrameMap frameMap, JvmMethodSignature jvmMethodSignature, MethodContext methodContext, MemberCodegen<?> memberCodegen) {
            if (methodVisitor == null) {
                $$$reportNull$$$0(1);
            }
            if (frameMap == null) {
                $$$reportNull$$$0(2);
            }
            if (jvmMethodSignature == null) {
                $$$reportNull$$$0(3);
            }
            if (methodContext == null) {
                $$$reportNull$$$0(4);
            }
            if (memberCodegen == null) {
                $$$reportNull$$$0(5);
            }
            ExpressionCodegen expressionCodegen = new ExpressionCodegen(methodVisitor, frameMap, jvmMethodSignature.getReturnType(), methodContext, this.state, memberCodegen);
            this.state.getGlobalInlineContext().enterDeclaration(methodContext.getFunctionDescriptor());
            try {
                doGenerateBody(expressionCodegen, jvmMethodSignature);
            } finally {
                this.state.getGlobalInlineContext().exitDeclaration();
            }
        }
    }
}
