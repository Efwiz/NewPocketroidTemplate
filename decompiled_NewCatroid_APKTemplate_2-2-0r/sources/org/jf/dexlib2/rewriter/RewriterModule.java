package org.jf.dexlib2.rewriter;

import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.AnnotationElement;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.DexFile;
import org.jf.dexlib2.iface.ExceptionHandler;
import org.jf.dexlib2.iface.Field;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.MethodParameter;
import org.jf.dexlib2.iface.TryBlock;
import org.jf.dexlib2.iface.debug.DebugItem;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.iface.value.EncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public class RewriterModule {
    public Rewriter<DexFile> getDexFileRewriter(Rewriters rewriters) {
        return new DexFileRewriter(rewriters);
    }

    public Rewriter<ClassDef> getClassDefRewriter(Rewriters rewriters) {
        return new ClassDefRewriter(rewriters);
    }

    public Rewriter<Field> getFieldRewriter(Rewriters rewriters) {
        return new FieldRewriter(rewriters);
    }

    public Rewriter<Method> getMethodRewriter(Rewriters rewriters) {
        return new MethodRewriter(rewriters);
    }

    public Rewriter<MethodParameter> getMethodParameterRewriter(Rewriters rewriters) {
        return new MethodParameterRewriter(rewriters);
    }

    public Rewriter<MethodImplementation> getMethodImplementationRewriter(Rewriters rewriters) {
        return new MethodImplementationRewriter(rewriters);
    }

    public Rewriter<Instruction> getInstructionRewriter(Rewriters rewriters) {
        return new InstructionRewriter(rewriters);
    }

    public Rewriter<TryBlock<? extends ExceptionHandler>> getTryBlockRewriter(Rewriters rewriters) {
        return new TryBlockRewriter(rewriters);
    }

    public Rewriter<ExceptionHandler> getExceptionHandlerRewriter(Rewriters rewriters) {
        return new ExceptionHandlerRewriter(rewriters);
    }

    public Rewriter<DebugItem> getDebugItemRewriter(Rewriters rewriters) {
        return new DebugItemRewriter(rewriters);
    }

    public Rewriter<String> getTypeRewriter(Rewriters rewriters) {
        return new TypeRewriter();
    }

    public Rewriter<FieldReference> getFieldReferenceRewriter(Rewriters rewriters) {
        return new FieldReferenceRewriter(rewriters);
    }

    public Rewriter<MethodReference> getMethodReferenceRewriter(Rewriters rewriters) {
        return new MethodReferenceRewriter(rewriters);
    }

    public Rewriter<Annotation> getAnnotationRewriter(Rewriters rewriters) {
        return new AnnotationRewriter(rewriters);
    }

    public Rewriter<AnnotationElement> getAnnotationElementRewriter(Rewriters rewriters) {
        return new AnnotationElementRewriter(rewriters);
    }

    public Rewriter<EncodedValue> getEncodedValueRewriter(Rewriters rewriters) {
        return new EncodedValueRewriter(rewriters);
    }
}
