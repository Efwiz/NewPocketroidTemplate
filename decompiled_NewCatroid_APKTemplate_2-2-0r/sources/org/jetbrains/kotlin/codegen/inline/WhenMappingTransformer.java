package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: ObjectTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\u0010H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/WhenMappingTransformer;", "Lorg/jetbrains/kotlin/codegen/inline/ObjectTransformer;", "Lorg/jetbrains/kotlin/codegen/inline/WhenMappingTransformationInfo;", "whenObjectRegenerationInfo", "inliningContext", "Lorg/jetbrains/kotlin/codegen/inline/InliningContext;", "(Lorg/jetbrains/kotlin/codegen/inline/WhenMappingTransformationInfo;Lorg/jetbrains/kotlin/codegen/inline/InliningContext;)V", "cutOtherMappings", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "node", "doTransform", "Lorg/jetbrains/kotlin/codegen/inline/InlineResult;", "parentRemapper", "Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper;", "isValues", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WhenMappingTransformer extends ObjectTransformer<WhenMappingTransformationInfo> {
    private final InliningContext inliningContext;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WhenMappingTransformer(WhenMappingTransformationInfo whenObjectRegenerationInfo, InliningContext inliningContext) {
        super(whenObjectRegenerationInfo, inliningContext.getState());
        Intrinsics.checkNotNullParameter(whenObjectRegenerationInfo, "whenObjectRegenerationInfo");
        Intrinsics.checkNotNullParameter(inliningContext, "inliningContext");
        this.inliningContext = inliningContext;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ObjectTransformer
    public InlineResult doTransform(FieldRemapper parentRemapper) {
        Intrinsics.checkNotNullParameter(parentRemapper, "parentRemapper");
        ClassReader classReaderCreateClassReader = createClassReader();
        final ClassBuilder classBuilderCreateRemappingClassBuilderViaFactory = createRemappingClassBuilderViaFactory(this.inliningContext);
        final ArrayList arrayList = new ArrayList();
        final FieldInsnNode fieldNode = ((WhenMappingTransformationInfo) this.transformationInfo).getFieldNode();
        classReaderCreateClassReader.accept(new ClassVisitor(classBuilderCreateRemappingClassBuilderViaFactory.getVisitor()) { // from class: org.jetbrains.kotlin.codegen.inline.WhenMappingTransformer.doTransform.1
            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visitInnerClass(String name2, String outerName, String innerName, int access) {
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visit(int version, int access, String name2, String signature, String superName, String[] interfaces) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(superName, "superName");
                Intrinsics.checkNotNullParameter(interfaces, "interfaces");
                classBuilderCreateRemappingClassBuilderViaFactory.defineClass(null, Math.max(version, this.getState().getConfig().getClassFileVersion()), access, name2, signature, superName, interfaces);
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public FieldVisitor visitField(int access, String name2, String desc, String signature, Object value) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                if (Intrinsics.areEqual(name2, fieldNode.f1010name)) {
                    return classBuilderCreateRemappingClassBuilderViaFactory.newField(JvmDeclarationOrigin.NO_ORIGIN, access, name2, desc, signature, value);
                }
                return null;
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public MethodVisitor visitMethod(int access, String name2, String desc, String signature, String[] exceptions) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                MethodNode methodNode = new MethodNode(access, name2, desc, signature, exceptions);
                arrayList.add(methodNode);
                return methodNode;
            }
        }, 4);
        arrayList.size();
        MethodNode methodNode = (MethodNode) CollectionsKt.first((List) arrayList);
        Intrinsics.areEqual(methodNode.f1016name, MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME);
        MethodNode methodNodeCutOtherMappings = cutOtherMappings(methodNode);
        JvmDeclarationOrigin jvmDeclarationOrigin = JvmDeclarationOrigin.NO_ORIGIN;
        int i = methodNodeCutOtherMappings.access;
        String str = methodNodeCutOtherMappings.f1016name;
        String str2 = methodNodeCutOtherMappings.desc;
        String str3 = methodNodeCutOtherMappings.signature;
        List<String> exceptions = methodNodeCutOtherMappings.exceptions;
        Intrinsics.checkNotNullExpressionValue(exceptions, "exceptions");
        MethodVisitor methodVisitorNewMethod = classBuilderCreateRemappingClassBuilderViaFactory.newMethod(jvmDeclarationOrigin, i, str, str2, str3, (String[]) exceptions.toArray(new String[0]));
        Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
        methodNodeCutOtherMappings.accept(methodVisitorNewMethod);
        classBuilderCreateRemappingClassBuilderViaFactory.done(getState().getConfig().getGenerateSmapCopyToAnnotation());
        return this.transformationResult;
    }

    private final MethodNode cutOtherMappings(MethodNode node) {
        InsnList instructions = node.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        for (AbstractInsnNode abstractInsnNode : new InsnSequence(instructions)) {
            if ((abstractInsnNode instanceof FieldInsnNode) && Intrinsics.areEqual(((FieldInsnNode) abstractInsnNode).f1010name, ((WhenMappingTransformationInfo) this.transformationInfo).getFieldNode().f1010name)) {
                for (AbstractInsnNode abstractInsnNode2 : SequencesKt.generateSequence(abstractInsnNode, new Function1<AbstractInsnNode, AbstractInsnNode>() { // from class: org.jetbrains.kotlin.codegen.inline.WhenMappingTransformer$cutOtherMappings$myValuesAccess$1
                    @Override // kotlin.jvm.functions.Function1
                    public final AbstractInsnNode invoke(AbstractInsnNode it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it.getPrevious();
                    }
                })) {
                    if (isValues(abstractInsnNode2)) {
                        for (AbstractInsnNode abstractInsnNode3 : SequencesKt.generateSequence(abstractInsnNode, new Function1<AbstractInsnNode, AbstractInsnNode>() { // from class: org.jetbrains.kotlin.codegen.inline.WhenMappingTransformer$cutOtherMappings$nextValuesAccessOrEnd$1
                            @Override // kotlin.jvm.functions.Function1
                            public final AbstractInsnNode invoke(AbstractInsnNode it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                return it.getNext();
                            }
                        })) {
                            if (isValues(abstractInsnNode3) || abstractInsnNode3.getOpcode() == 177) {
                                int i = node.access;
                                String str = node.f1016name;
                                String str2 = node.desc;
                                String str3 = node.signature;
                                List<String> exceptions = node.exceptions;
                                Intrinsics.checkNotNullExpressionValue(exceptions, "exceptions");
                                MethodNode methodNode = new MethodNode(i, str, str2, str3, (String[]) exceptions.toArray(new String[0]));
                                Iterator<AbstractInsnNode> it = new InsnSequence(abstractInsnNode2, abstractInsnNode3).iterator();
                                while (it.hasNext()) {
                                    it.next().accept(methodNode);
                                }
                                methodNode.visitInsn(177);
                                return methodNode;
                            }
                        }
                        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                    }
                }
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private final boolean isValues(AbstractInsnNode node) {
        if (node instanceof MethodInsnNode) {
            MethodInsnNode methodInsnNode = (MethodInsnNode) node;
            if (methodInsnNode.getOpcode() == 184 && Intrinsics.areEqual(methodInsnNode.f1015name, CompoundEntry.NAME_values) && Intrinsics.areEqual(methodInsnNode.desc, "()[" + Type.getObjectType(methodInsnNode.owner).getDescriptor())) {
                return true;
            }
        }
        return false;
    }
}
