package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.util.containers.SLRUMap;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;

/* JADX INFO: compiled from: LambdaInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 &2\u00020\u0001:\u0001&B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$H\u0002R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\rR\u0014\u0010\u0015\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010!\u001a\u0004\u0018\u00010\u001c¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001e¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/DefaultLambda;", "Lorg/jetbrains/kotlin/codegen/inline/LambdaInfo;", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/codegen/inline/ExtractedDefaultLambda;", "sourceCompiler", "Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;", "functionName", "", "(Lorg/jetbrains/kotlin/codegen/inline/ExtractedDefaultLambda;Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;Ljava/lang/String;)V", "capturedVars", "", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamDesc;", "getCapturedVars", "()Ljava/util/List;", "invokeMethod", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "getInvokeMethod", "()Lorg/jetbrains/org/objectweb/asm/commons/Method;", "invokeMethodParameters", "Lorg/jetbrains/kotlin/types/KotlinType;", "getInvokeMethodParameters", "invokeMethodReturnType", "getInvokeMethodReturnType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "isBoundCallableReference", "", "()Z", "lambdaClassType", "Lorg/jetbrains/org/objectweb/asm/Type;", "getLambdaClassType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "nullableAnyType", "Lorg/jetbrains/kotlin/types/SimpleType;", "originalBoundReceiverType", "getOriginalBoundReceiverType", "createNodeWithFakeVariables", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "originNode", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultLambda extends LambdaInfo {
    private static final Companion Companion = new Companion(null);
    private static final HashSet<String> PROPERTY_REFERENCE_SUPER_CLASSES;
    private final List<CapturedParamDesc> capturedVars;
    private final String functionName;
    private final boolean isBoundCallableReference;
    private final Type lambdaClassType;
    private final SimpleType nullableAnyType;
    private final Type originalBoundReceiverType;

    /* JADX WARN: Code duplicated, block: B:37:0x0106  */
    public DefaultLambda(ExtractedDefaultLambda info, SourceCompilerForInline sourceCompiler, String functionName) {
        byte[] bArrLoadClassBytesByInternalName;
        List<CapturedParamDesc> listEmptyList;
        Sequence<FieldInsnNode> sequenceFindCapturedFieldAssignmentInstructions;
        Sequence map;
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(sourceCompiler, "sourceCompiler");
        Intrinsics.checkNotNullParameter(functionName, "functionName");
        this.functionName = functionName;
        this.lambdaClassType = info.getType();
        SimpleType nullableAnyType = sourceCompiler.getState().getModule().getBuiltIns().getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getNullableAnyType(...)");
        this.nullableAnyType = nullableAnyType;
        SLRUMap<String, byte[]> classBytes = sourceCompiler.getState().getInlineCache().getClassBytes();
        String internalName = getLambdaClassType().getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        synchronized (classBytes) {
            bArrLoadClassBytesByInternalName = classBytes.get(internalName);
            if (bArrLoadClassBytesByInternalName == null) {
                GenerationState state = sourceCompiler.getState();
                String internalName2 = getLambdaClassType().getInternalName();
                Intrinsics.checkNotNullExpressionValue(internalName2, "getInternalName(...)");
                bArrLoadClassBytesByInternalName = InlineCodegenUtilsKt.loadClassBytesByInternalName(state, internalName2);
                classBytes.put(internalName, bArrLoadClassBytesByInternalName);
            }
        }
        byte[] bArr = bArrLoadClassBytesByInternalName;
        String superName = new ClassReader(bArr).getSuperName();
        Intrinsics.checkNotNull(superName);
        if (CoroutineCodegenUtilKt.isCoroutineSuperClass(superName)) {
            throw new IllegalArgumentException(("suspend default lambda " + getLambdaClassType().getInternalName() + " cannot be inlined; use a function reference instead").toString());
        }
        SMAPAndMethodNode methodNode = InlineCodegenUtilsKt.getMethodNode(bArr, getLambdaClassType(), new Method("<init>", Type.VOID_TYPE, info.getCapturedArgs()));
        Type type = null;
        MethodNode node = methodNode != null ? methodNode.getNode() : null;
        if (node == null) {
            int length = info.getCapturedArgs().length;
        }
        boolean zContains = PROPERTY_REFERENCE_SUPER_CLASSES.contains(superName);
        boolean z = zContains || Intrinsics.areEqual(superName, AsmTypes.FUNCTION_REFERENCE.getInternalName()) || Intrinsics.areEqual(superName, AsmTypes.FUNCTION_REFERENCE_IMPL.getInternalName());
        Type type2 = (Type) ArraysKt.singleOrNull(info.getCapturedArgs());
        if (type2 != null && z && AsmUtil.isPrimitive(type2)) {
            type = type2;
        }
        this.originalBoundReceiverType = type;
        if (z) {
            if (((Type) ArraysKt.singleOrNull(info.getCapturedArgs())) != null) {
                Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
                Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
                listEmptyList = CollectionsKt.listOf(LambdaInfo.INSTANCE.capturedParamDesc(this, AsmUtil.RECEIVER_PARAMETER_NAME, OBJECT_TYPE, false));
                if (listEmptyList == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
            } else {
                listEmptyList = CollectionsKt.emptyList();
            }
        } else if (node == null || (sequenceFindCapturedFieldAssignmentInstructions = MethodInlinerUtilKt.findCapturedFieldAssignmentInstructions(node)) == null || (map = SequencesKt.map(sequenceFindCapturedFieldAssignmentInstructions, new Function1<FieldInsnNode, CapturedParamDesc>() { // from class: org.jetbrains.kotlin.codegen.inline.DefaultLambda.5
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CapturedParamDesc invoke(FieldInsnNode fieldNode) {
                Intrinsics.checkNotNullParameter(fieldNode, "fieldNode");
                LambdaInfo.Companion companion = LambdaInfo.INSTANCE;
                DefaultLambda defaultLambda = DefaultLambda.this;
                String name2 = fieldNode.f1010name;
                Intrinsics.checkNotNullExpressionValue(name2, "name");
                Type type3 = Type.getType(fieldNode.desc);
                Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                return companion.capturedParamDesc(defaultLambda, name2, type3, false);
            }
        })) == null || (listEmptyList = SequencesKt.toList(map)) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        this.capturedVars = listEmptyList;
        this.isBoundCallableReference = z && !getCapturedVars().isEmpty();
        SMAPAndMethodNode sMAPAndMethodNodeLoadDefaultLambdaBody = DefaultMethodUtilKt.loadDefaultLambdaBody(bArr, getLambdaClassType(), zContains);
        setNode(new SMAPAndMethodNode(createNodeWithFakeVariables(sMAPAndMethodNodeLoadDefaultLambdaBody.getNode()), sMAPAndMethodNodeLoadDefaultLambdaBody.getClassSMAP()));
    }

    /* JADX INFO: renamed from: isBoundCallableReference, reason: from getter */
    public final boolean getIsBoundCallableReference() {
        return this.isBoundCallableReference;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public Type getLambdaClassType() {
        return this.lambdaClassType;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public List<CapturedParamDesc> getCapturedVars() {
        return this.capturedVars;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public Method getInvokeMethod() {
        return new Method(getNode().getNode().f1016name, getNode().getNode().desc);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public List<KotlinType> getInvokeMethodParameters() {
        int length = getInvokeMethod().getArgumentTypes().length;
        ArrayList arrayList = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            arrayList.add(this.nullableAnyType);
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public KotlinType getInvokeMethodReturnType() {
        return this.nullableAnyType;
    }

    public final Type getOriginalBoundReceiverType() {
        return this.originalBoundReceiverType;
    }

    private final MethodNode createNodeWithFakeVariables(MethodNode originNode) {
        int i = originNode.access;
        String str = originNode.f1016name;
        String str2 = originNode.desc;
        String str3 = originNode.signature;
        List<String> list = originNode.exceptions;
        MethodNode methodNode = new MethodNode(i, str, str2, str3, list != null ? (String[]) list.toArray(new String[0]) : null);
        int i2 = originNode.maxLocals;
        methodNode.instructions.add(new LdcInsnNode(0));
        methodNode.instructions.add(new VarInsnNode(54, i2));
        LabelNode labelNode = new LabelNode();
        methodNode.instructions.add(labelNode);
        originNode.accept(methodNode);
        AbstractInsnNode last = methodNode.instructions.getLast();
        LabelNode labelNode2 = last instanceof LabelNode ? (LabelNode) last : null;
        if (labelNode2 == null) {
            labelNode2 = new LabelNode();
            methodNode.instructions.add(labelNode2);
        }
        LabelNode labelNode3 = labelNode2;
        List<LocalVariableNode> list2 = methodNode.localVariables;
        StringBuilder sbAppend = new StringBuilder("$i$a$-").append(this.functionName).append('-');
        String internalName = getLambdaClassType().getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        list2.add(new LocalVariableNode(sbAppend.append(StringsKt.substringAfterLast$default(internalName, '/', (String) null, 2, (Object) null)).toString(), Type.INT_TYPE.getDescriptor(), null, labelNode, labelNode3, i2));
        methodNode.maxLocals++;
        return methodNode;
    }

    /* JADX INFO: compiled from: LambdaInfo.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R1\u0010\u0003\u001a\"\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004j\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/DefaultLambda$Companion;", "", "()V", "PROPERTY_REFERENCE_SUPER_CLASSES", "Ljava/util/HashSet;", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lkotlin/collections/HashSet;", "getPROPERTY_REFERENCE_SUPER_CLASSES", "()Ljava/util/HashSet;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final HashSet<String> getPROPERTY_REFERENCE_SUPER_CLASSES() {
            return DefaultLambda.PROPERTY_REFERENCE_SUPER_CLASSES;
        }
    }

    static {
        List listListOf = CollectionsKt.listOf((Object[]) new Type[]{AsmTypes.PROPERTY_REFERENCE0, AsmTypes.PROPERTY_REFERENCE1, AsmTypes.PROPERTY_REFERENCE2, AsmTypes.MUTABLE_PROPERTY_REFERENCE0, AsmTypes.MUTABLE_PROPERTY_REFERENCE1, AsmTypes.MUTABLE_PROPERTY_REFERENCE2});
        List<Type> OPTIMIZED_PROPERTY_REFERENCE_SUPERTYPES = AsmTypes.OPTIMIZED_PROPERTY_REFERENCE_SUPERTYPES;
        Intrinsics.checkNotNullExpressionValue(OPTIMIZED_PROPERTY_REFERENCE_SUPERTYPES, "OPTIMIZED_PROPERTY_REFERENCE_SUPERTYPES");
        List listPlus = CollectionsKt.plus((Collection) listListOf, (Iterable) OPTIMIZED_PROPERTY_REFERENCE_SUPERTYPES);
        HashSet<String> hashSet = new HashSet<>();
        Iterator it = listPlus.iterator();
        while (it.hasNext()) {
            hashSet.add(((Type) it.next()).getInternalName());
        }
        PROPERTY_REFERENCE_SUPER_CLASSES = hashSet;
    }
}
