package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.AssertCodegenUtilKt;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.WriteAnnotationUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformer;
import org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformerKt;
import org.jetbrains.kotlin.codegen.serialization.JvmCodegenStringTable;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.load.kotlin.header.ReadKotlinClassHeaderAnnotationVisitor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.utils.MetadataHelpersKt;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.AnnotationNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InnerClassNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: AnonymousObjectTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B'\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u001a\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\tH\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J0\u0010 \u001a\u00020!2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010%\u001a\u00020\u001fH\u0002J \u0010&\u001a\u00020!2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020#2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0018\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020#2\u0006\u0010-\u001a\u00020\fH\u0002J0\u0010.\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010/\u001a\u0002002\u0006\u0010-\u001a\u00020\f2\u0006\u0010,\u001a\u00020#2\u0006\u00101\u001a\u00020\u0007H\u0002J0\u00102\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010/\u001a\u0002002\u0006\u00103\u001a\u00020\f2\u0006\u00104\u001a\u00020#2\u0006\u00101\u001a\u00020\u0007H\u0002J\u0010\u00105\u001a\u00020\u00072\u0006\u00106\u001a\u000207H\u0002J\u0018\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020(2\u0006\u0010;\u001a\u00020\fH\u0002J\u001e\u0010<\u001a\u0010\u0012\u0004\u0012\u00020>\u0012\u0004\u0012\u00020?\u0018\u00010=2\u0006\u00106\u001a\u000207H\u0002J\u0010\u0010@\u001a\u00020!2\u0006\u0010'\u001a\u00020(H\u0002J\u0018\u0010A\u001a\u00020!2\u0006\u00106\u001a\u0002072\u0006\u0010'\u001a\u00020(H\u0002R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\r\u001a*\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u000f0\u000ej\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u000f`\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n \u0013*\u0004\u0018\u00010\u00120\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006B"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/AnonymousObjectTransformer;", "Lorg/jetbrains/kotlin/codegen/inline/ObjectTransformer;", "Lorg/jetbrains/kotlin/codegen/inline/AnonymousObjectTransformationInfo;", "transformationInfo", "inliningContext", "Lorg/jetbrains/kotlin/codegen/inline/InliningContext;", "isSameModule", "", "continuationClassName", "", "(Lorg/jetbrains/kotlin/codegen/inline/AnonymousObjectTransformationInfo;Lorg/jetbrains/kotlin/codegen/inline/InliningContext;ZLjava/lang/String;)V", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "fieldNames", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "oldObjectType", "Lorg/jetbrains/org/objectweb/asm/Type;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "sourceMap", "Lorg/jetbrains/kotlin/codegen/inline/SMAP;", "sourceMapper", "Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapperBase;", "addUniqueField", "name", "doTransform", "Lorg/jetbrains/kotlin/codegen/inline/InlineResult;", "parentRemapper", "Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper;", "extractParametersMappingAndPatchConstructor", "", "capturedParamBuilder", "Lorg/jetbrains/kotlin/codegen/inline/ParametersBuilder;", "constructorParamBuilder", "parentFieldRemapper", "generateConstructorAndFields", "classBuilder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "constructorInlineBuilder", "getMethodParametersWithCaptured", "Lorg/jetbrains/kotlin/codegen/inline/Parameters;", "capturedBuilder", "sourceNode", "inlineMethod", "deferringVisitor", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "isConstructor", "inlineMethodAndUpdateGlobalResult", ES6Iterator.NEXT_METHOD, "allCapturedParamBuilder", "isPublicAbi", "header", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "newMethod", "Lorg/jetbrains/kotlin/codegen/inline/DeferredMethodVisitor;", "builder", "original", "transformMetadata", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "Lorg/jetbrains/kotlin/metadata/jvm/serialization/JvmStringTable;", "writeOuterInfo", "writeTransformedMetadata", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnonymousObjectTransformer extends ObjectTransformer<AnonymousObjectTransformationInfo> {
    private MethodNode constructor;
    private final String continuationClassName;
    private final HashMap<String, List<String>> fieldNames;
    private final InliningContext inliningContext;
    private final boolean isSameModule;
    private final Type oldObjectType;
    private SMAP sourceMap;
    private SourceMapper sourceMapper;
    private final KotlinTypeMapperBase typeMapper;

    /* JADX INFO: compiled from: AnonymousObjectTransformer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KotlinClassHeader.Kind.values().length];
            try {
                iArr[KotlinClassHeader.Kind.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KotlinClassHeader.Kind.SYNTHETIC_CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnonymousObjectTransformer(AnonymousObjectTransformationInfo transformationInfo, InliningContext inliningContext, boolean z, String str) {
        super(transformationInfo, inliningContext.getState());
        Intrinsics.checkNotNullParameter(transformationInfo, "transformationInfo");
        Intrinsics.checkNotNullParameter(inliningContext, "inliningContext");
        this.inliningContext = inliningContext;
        this.isSameModule = z;
        this.continuationClassName = str;
        this.oldObjectType = Type.getObjectType(transformationInfo.getOldClassName());
        this.fieldNames = new HashMap<>();
        this.typeMapper = getState().getTypeMapper();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.codegen.inline.ObjectTransformer
    public InlineResult doTransform(FieldRemapper parentRemapper) {
        Object next;
        String str;
        SourceMapper sourceMapper;
        ArrayList arrayList;
        DeferredMethodVisitor deferredMethodVisitorNewMethod;
        String internalName;
        String str2;
        Intrinsics.checkNotNullParameter(parentRemapper, "parentRemapper");
        final ArrayList arrayList2 = new ArrayList();
        final ClassBuilder classBuilderCreateRemappingClassBuilderViaFactory = createRemappingClassBuilderViaFactory(this.inliningContext);
        final ArrayList arrayList3 = new ArrayList();
        final ReadKotlinClassHeaderAnnotationVisitor readKotlinClassHeaderAnnotationVisitor = new ReadKotlinClassHeaderAnnotationVisitor();
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef4 = new Ref.ObjectRef();
        ArrayList arrayList4 = arrayList2;
        createClassReader().accept(new ClassVisitor(classBuilderCreateRemappingClassBuilderViaFactory.getVisitor()) { // from class: org.jetbrains.kotlin.codegen.inline.AnonymousObjectTransformer.doTransform.1
            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visitEnd() {
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visit(int version, int access, String name2, String signature, String superName, String[] interfaces) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(superName, "superName");
                Intrinsics.checkNotNullParameter(interfaces, "interfaces");
                classBuilderCreateRemappingClassBuilderViaFactory.defineClass(null, Math.max(version, this.getState().getConfig().getClassFileVersion()), access, name2, signature, superName, interfaces);
                if (CoroutineCodegenUtilKt.isCoroutineSuperClass(superName)) {
                    this.inliningContext.setContinuation(true);
                }
                objectRef.element = superName;
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visitInnerClass(String name2, String outerName, String innerName, int access) {
                Intrinsics.checkNotNullParameter(name2, "name");
                arrayList2.add(new InnerClassNode(name2, outerName, innerName, access));
            }

            /* JADX WARN: Type inference failed for: r0v9, types: [T, org.jetbrains.org.objectweb.asm.tree.AnnotationNode] */
            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                Intrinsics.checkNotNullParameter(desc, "desc");
                if (Intrinsics.areEqual(desc, JvmAnnotationNames.METADATA_DESC)) {
                    return FileBasedKotlinClass.convertAnnotationVisitor(readKotlinClassHeaderAnnotationVisitor, desc, new FileBasedKotlinClass.InnerClassesInfo());
                }
                if (Intrinsics.areEqual(desc, CoroutineCodegenUtilKt.getDEBUG_METADATA_ANNOTATION_ASM_TYPE().getDescriptor())) {
                    objectRef4.element = new AnnotationNode(desc);
                    return objectRef4.element;
                }
                if (Intrinsics.areEqual(desc, JvmAnnotationNames.SOURCE_DEBUG_EXTENSION_DESC)) {
                    return null;
                }
                return classBuilderCreateRemappingClassBuilderViaFactory.newAnnotation(desc, visible);
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public MethodVisitor visitMethod(int access, String name2, String desc, String signature, String[] exceptions) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                MethodNode methodNode = new MethodNode(access, name2, desc, signature, exceptions);
                if (!Intrinsics.areEqual(name2, "<init>")) {
                    arrayList3.add(methodNode);
                } else {
                    if (this.constructor != null) {
                        throw new RuntimeException("Lambda, SAM or anonymous object should have only one constructor.\nFirst:\n" + InlineCodegenUtilsKt.getNodeText(this.constructor) + "\n\nSecond:\n" + InlineCodegenUtilsKt.getNodeText(methodNode) + '\n');
                    }
                    this.constructor = methodNode;
                }
                return methodNode;
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public FieldVisitor visitField(int access, String name2, String desc, String signature, Object value) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                this.addUniqueField(name2);
                if (InlineCodegenUtilsKt.isCapturedFieldName(name2)) {
                    return null;
                }
                return classBuilderCreateRemappingClassBuilderViaFactory.newField(JvmDeclarationOrigin.NO_ORIGIN, access, name2, desc, signature, value);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visitSource(String source, String debug) {
                Intrinsics.checkNotNullParameter(source, "source");
                objectRef2.element = source;
                objectRef3.element = debug;
            }
        }, 4);
        KotlinClassHeader kotlinClassHeaderCreateHeader = readKotlinClassHeaderAnnotationVisitor.createHeader(MetadataHelpersKt.toMetadataVersion(this.inliningContext.getState().getLanguageVersionSettings().getLanguageVersion()));
        if (!this.isSameModule && kotlinClassHeaderCreateHeader != null) {
            isPublicAbi(kotlinClassHeaderCreateHeader);
        }
        String str3 = this.inliningContext.isInliningLambda() ? null : (String) objectRef3.element;
        Pair<Integer, Integer> pairLineNumberRange = InlineCodegenUtilsKt.lineNumberRange(CollectionsKt.plus((Collection) arrayList3, (Iterable) CollectionsKt.listOfNotNull(this.constructor)));
        int iIntValue = pairLineNumberRange.component1().intValue();
        int iIntValue2 = pairLineNumberRange.component2().intValue();
        String str4 = (String) objectRef2.element;
        String internalName2 = this.oldObjectType.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName2, "getInternalName(...)");
        SMAP orCreateDefault = SMAPParser.parseOrCreateDefault(str3, str4, internalName2, iIntValue, iIntValue2);
        this.sourceMap = orCreateDefault;
        if (orCreateDefault == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sourceMap");
            orCreateDefault = null;
        }
        Iterator<T> it = orCreateDefault.getFileMappings().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((FileMapping) next).getName(), objectRef2.element));
        FileMapping fileMapping = (FileMapping) next;
        this.sourceMapper = new SourceMapper(fileMapping != null ? fileMapping.toSourceInfo() : null);
        ParametersBuilder parametersBuilderNewBuilder = ParametersBuilder.INSTANCE.newBuilder();
        ParametersBuilder parametersBuilderNewBuilder2 = ParametersBuilder.INSTANCE.newBuilder();
        MethodNode methodNode = this.constructor;
        Intrinsics.checkNotNull(methodNode);
        extractParametersMappingAndPatchConstructor(methodNode, parametersBuilderNewBuilder, parametersBuilderNewBuilder2, (AnonymousObjectTransformationInfo) this.transformationInfo, parentRemapper);
        ArrayList<DeferredMethodVisitor> arrayList5 = new ArrayList();
        generateConstructorAndFields(classBuilderCreateRemappingClassBuilderViaFactory, parametersBuilderNewBuilder2, parentRemapper);
        InliningContext inliningContext = this.inliningContext;
        ArrayList arrayList6 = arrayList3;
        if (objectRef.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("superClassName");
            str = null;
        } else {
            str = (String) objectRef.element;
        }
        CoroutineTransformer coroutineTransformer = new CoroutineTransformer(inliningContext, classBuilderCreateRemappingClassBuilderViaFactory, arrayList6, str);
        Iterator it2 = arrayList3.iterator();
        boolean z = false;
        while (it2.hasNext()) {
            MethodNode methodNode2 = (MethodNode) it2.next();
            Intrinsics.checkNotNull(methodNode2);
            if (!coroutineTransformer.shouldSkip(methodNode2)) {
                if (coroutineTransformer.shouldGenerateStateMachine(methodNode2)) {
                    deferredMethodVisitorNewMethod = coroutineTransformer.newMethod(methodNode2);
                } else {
                    if (coroutineTransformer.suspendLambdaWithGeneratedStateMachine(methodNode2)) {
                        z = true;
                    }
                    deferredMethodVisitorNewMethod = newMethod(classBuilderCreateRemappingClassBuilderViaFactory, methodNode2);
                }
                boolean z2 = z;
                DeferredMethodVisitor deferredMethodVisitor = deferredMethodVisitorNewMethod;
                if (Intrinsics.areEqual(methodNode2.f1016name, MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
                    AssertCodegenUtilKt.rewriteAssertionsDisabledFieldInitialization(methodNode2, this.inliningContext.getRoot().getCallSiteInfo().getOwnerClassName());
                }
                Iterator it3 = it2;
                InlineResult inlineResultInlineMethodAndUpdateGlobalResult = inlineMethodAndUpdateGlobalResult(parentRemapper, deferredMethodVisitor, methodNode2, parametersBuilderNewBuilder, false);
                Type returnType = Type.getReturnType(methodNode2.desc);
                if (!AsmUtil.isPrimitive(returnType) && (str2 = inlineResultInlineMethodAndUpdateGlobalResult.getChangedTypes().get((internalName = returnType.getInternalName()))) != null) {
                    TypeRemapper typeRemapper = this.inliningContext.getTypeRemapper();
                    Intrinsics.checkNotNull(internalName);
                    typeRemapper.addAdditionalMappings(internalName, str2);
                }
                arrayList5.add(deferredMethodVisitor);
                z = z2;
                it2 = it3;
            }
        }
        for (DeferredMethodVisitor deferredMethodVisitor2 : arrayList5) {
            String strFindFakeContinuationConstructorClassName = CoroutineTransformer.INSTANCE.findFakeContinuationConstructorClassName(deferredMethodVisitor2.getIntermediate());
            final String strOldContinuationFrom = coroutineTransformer.oldContinuationFrom(deferredMethodVisitor2.getIntermediate());
            coroutineTransformer.replaceFakesWithReals(deferredMethodVisitor2.getIntermediate());
            InlineCodegenUtilsKt.removeFinallyMarkers(deferredMethodVisitor2.getIntermediate());
            deferredMethodVisitor2.visitEnd();
            if (strFindFakeContinuationConstructorClassName == null || !coroutineTransformer.safeToRemoveContinuationClass(deferredMethodVisitor2.getIntermediate())) {
                arrayList = arrayList4;
            } else {
                this.transformationResult.addClassToRemove(strFindFakeContinuationConstructorClassName);
                final Function1<InnerClassNode, Boolean> function1 = new Function1<InnerClassNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.AnonymousObjectTransformer$doTransform$4$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(InnerClassNode it4) {
                        Intrinsics.checkNotNullParameter(it4, "it");
                        return Boolean.valueOf(Intrinsics.areEqual(it4.f1012name, strOldContinuationFrom));
                    }
                };
                Predicate predicate = new Predicate() { // from class: org.jetbrains.kotlin.codegen.inline.AnonymousObjectTransformer$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return AnonymousObjectTransformer.doTransform$lambda$3$lambda$2(function1, obj);
                    }
                };
                arrayList = arrayList4;
                arrayList.removeIf(predicate);
            }
            arrayList4 = arrayList;
        }
        ArrayList<InnerClassNode> arrayList7 = arrayList4;
        if (!this.inliningContext.isInliningLambda()) {
            SourceMapper sourceMapper2 = this.sourceMapper;
            if (sourceMapper2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sourceMapper");
                sourceMapper = null;
            } else {
                sourceMapper = sourceMapper2;
            }
            classBuilderCreateRemappingClassBuilderViaFactory.visitSMAP(sourceMapper, !getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.CorrectSourceMappingSyntax));
        } else if (objectRef2.element != 0) {
            T t = objectRef2.element;
            Intrinsics.checkNotNull(t);
            classBuilderCreateRemappingClassBuilderViaFactory.visitSource((String) t, (String) objectRef3.element);
        }
        for (InnerClassNode innerClassNode : arrayList7) {
            classBuilderCreateRemappingClassBuilderViaFactory.visitInnerClass(innerClassNode.f1012name, innerClassNode.outerName, innerClassNode.innerName, innerClassNode.access);
        }
        if (kotlinClassHeaderCreateHeader != null) {
            writeTransformedMetadata(kotlinClassHeaderCreateHeader, classBuilderCreateRemappingClassBuilderViaFactory);
        }
        if (z && objectRef4.element != 0) {
            T t2 = objectRef4.element;
            Intrinsics.checkNotNull(t2);
            AnnotationVisitor annotationVisitorNewAnnotation = classBuilderCreateRemappingClassBuilderViaFactory.newAnnotation(((AnnotationNode) t2).desc, true);
            T t3 = objectRef4.element;
            Intrinsics.checkNotNull(t3);
            ((AnnotationNode) t3).accept(annotationVisitorNewAnnotation);
        }
        writeOuterInfo(classBuilderCreateRemappingClassBuilderViaFactory);
        if (this.inliningContext.getGenerateAssertField()) {
            HashMap<String, List<String>> map = this.fieldNames;
            if (map.isEmpty()) {
                MethodVisitor methodVisitorNewMethod = classBuilderCreateRemappingClassBuilderViaFactory.newMethod(JvmDeclarationOrigin.NO_ORIGIN, 8, MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME, "()V", null, null);
                Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
                AssertCodegenUtilKt.generateAssertionsDisabledFieldInitialization(classBuilderCreateRemappingClassBuilderViaFactory, methodVisitorNewMethod, this.inliningContext.getRoot().getCallSiteInfo().getOwnerClassName());
                methodVisitorNewMethod.visitInsn(177);
                methodVisitorNewMethod.visitEnd();
                break;
            }
            Iterator<Map.Entry<String, List<String>>> it4 = map.entrySet().iterator();
            do {
                if (!it4.hasNext()) {
                    MethodVisitor methodVisitorNewMethod2 = classBuilderCreateRemappingClassBuilderViaFactory.newMethod(JvmDeclarationOrigin.NO_ORIGIN, 8, MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME, "()V", null, null);
                    Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod2, "newMethod(...)");
                    AssertCodegenUtilKt.generateAssertionsDisabledFieldInitialization(classBuilderCreateRemappingClassBuilderViaFactory, methodVisitorNewMethod2, this.inliningContext.getRoot().getCallSiteInfo().getOwnerClassName());
                    methodVisitorNewMethod2.visitInsn(177);
                    methodVisitorNewMethod2.visitEnd();
                    break;
                }
            } while (!Intrinsics.areEqual(it4.next().getKey(), AssertCodegenUtilKt.ASSERTIONS_DISABLED_FIELD_NAME));
        }
        if (Intrinsics.areEqual(this.continuationClassName, ((AnonymousObjectTransformationInfo) this.transformationInfo).getOldClassName())) {
            coroutineTransformer.registerClassBuilder(this.continuationClassName);
        } else {
            classBuilderCreateRemappingClassBuilderViaFactory.done(getState().getConfig().getGenerateSmapCopyToAnnotation());
        }
        return this.transformationResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean doTransform$lambda$3$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    private final void writeTransformedMetadata(final KotlinClassHeader header, ClassBuilder classBuilder) {
        WriteAnnotationUtilKt.writeKotlinMetadata(classBuilder, getState(), header.getKind(), this.inliningContext.getCallSiteInfo().isInPublicInlineScope(), header.getExtraInt() & (-129), new Function1<AnnotationVisitor, Unit>() { // from class: org.jetbrains.kotlin.codegen.inline.AnonymousObjectTransformer.writeTransformedMetadata.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AnnotationVisitor annotationVisitor) {
                invoke2(annotationVisitor);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AnnotationVisitor av) {
                Intrinsics.checkNotNullParameter(av, "av");
                Pair pairTransformMetadata = AnonymousObjectTransformer.this.transformMetadata(header);
                if (pairTransformMetadata == null) {
                    KotlinClassHeader kotlinClassHeader = header;
                    String[] data = kotlinClassHeader.getData();
                    String[] strings = kotlinClassHeader.getStrings();
                    if (data == null || strings == null) {
                        return;
                    }
                    AsmUtil.writeAnnotationData(av, data, strings);
                    return;
                }
                DescriptorAsmUtil.writeAnnotationData(av, (MessageLite) pairTransformMetadata.component1(), (JvmStringTable) pairTransformMetadata.component2());
            }
        });
    }

    private final boolean isPublicAbi(KotlinClassHeader header) {
        return (header.getMetadataVersion().isAtLeast(1, 6, 0) && (header.getExtraInt() & 128) == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair<MessageLite, JvmStringTable> transformMetadata(KotlinClassHeader header) {
        String[] strings;
        String[] data = header.getData();
        if (data == null || (strings = header.getStrings()) == null) {
            return null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[header.getKind().ordinal()];
        if (i == 1) {
            Pair<JvmNameResolver, ProtoBuf.Class> classDataFrom = JvmProtoBufUtil.readClassDataFrom(data, strings);
            JvmNameResolver jvmNameResolverComponent1 = classDataFrom.component1();
            ProtoBuf.Class classComponent2 = classDataFrom.component2();
            JvmCodegenStringTable jvmCodegenStringTable = new JvmCodegenStringTable(this.typeMapper, jvmNameResolverComponent1);
            ProtoBuf.Class.Builder builder = classComponent2.toBuilder();
            GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, Integer> generatedExtension = JvmProtoBuf.anonymousObjectOriginName;
            String internalName = this.oldObjectType.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            builder.setExtension(generatedExtension, Integer.valueOf(jvmCodegenStringTable.getStringIndex(internalName)));
            return TuplesKt.to(builder.build(), jvmCodegenStringTable);
        }
        if (i != 2) {
            return null;
        }
        Pair<JvmNameResolver, ProtoBuf.Function> functionDataFrom = JvmProtoBufUtil.readFunctionDataFrom(data, strings);
        JvmNameResolver jvmNameResolverComponent2 = functionDataFrom.component1();
        ProtoBuf.Function functionComponent2 = functionDataFrom.component2();
        JvmCodegenStringTable jvmCodegenStringTable2 = new JvmCodegenStringTable(this.typeMapper, jvmNameResolverComponent2);
        ProtoBuf.Function.Builder builder2 = functionComponent2.toBuilder();
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, Integer> generatedExtension2 = JvmProtoBuf.lambdaClassOriginName;
        String internalName2 = this.oldObjectType.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName2, "getInternalName(...)");
        builder2.setExtension(generatedExtension2, Integer.valueOf(jvmCodegenStringTable2.getStringIndex(internalName2)));
        return TuplesKt.to(builder2.build(), jvmCodegenStringTable2);
    }

    private final void writeOuterInfo(ClassBuilder classBuilder) {
        InlineCallSiteInfo callSiteInfo = this.inliningContext.getCallSiteInfo();
        String ownerClassName = callSiteInfo.getOwnerClassName();
        String name2 = callSiteInfo.getMethod().getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        classBuilder.visitOuterClass(ownerClassName, StringsKt.removeSuffix(name2, (CharSequence) CoroutineTransformerKt.FOR_INLINE_SUFFIX), callSiteInfo.getMethod().getDescriptor());
    }

    private final InlineResult inlineMethodAndUpdateGlobalResult(FieldRemapper parentRemapper, MethodVisitor deferringVisitor, MethodNode next, ParametersBuilder allCapturedParamBuilder, boolean isConstructor) {
        InlineResult inlineResultInlineMethod = inlineMethod(parentRemapper, deferringVisitor, next, allCapturedParamBuilder, isConstructor);
        this.transformationResult.merge(inlineResultInlineMethod);
        this.transformationResult.getReifiedTypeParametersUsages().mergeAll(inlineResultInlineMethod.getReifiedTypeParametersUsages());
        return inlineResultInlineMethod;
    }

    private final InlineResult inlineMethod(FieldRemapper parentRemapper, MethodVisitor deferringVisitor, MethodNode sourceNode, ParametersBuilder capturedBuilder, boolean isConstructor) {
        SourceMapper sourceMapper;
        SMAP smap;
        Parameters parametersBuildParameters = isConstructor ? capturedBuilder.buildParameters() : getMethodParametersWithCaptured(capturedBuilder, sourceNode);
        String internalName = this.oldObjectType.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        RegeneratedLambdaFieldRemapper regeneratedLambdaFieldRemapper = new RegeneratedLambdaFieldRemapper(internalName, ((AnonymousObjectTransformationInfo) this.transformationInfo).getNewClassName(), parametersBuildParameters, ((AnonymousObjectTransformationInfo) this.transformationInfo).getCapturedLambdasToInline(), parentRemapper, isConstructor);
        ReifiedTypeParametersUsages reifiedTypeParametersUsagesReifyInstructions = this.inliningContext.getShouldReifyTypeParametersInObjects() ? this.inliningContext.getRoot().getInlineMethodReifier().reifyInstructions(sourceNode) : null;
        InliningContext inliningContext = this.inliningContext;
        NameGenerator nameGenerator = ((AnonymousObjectTransformationInfo) this.transformationInfo).getNameGenerator();
        Intrinsics.checkNotNullExpressionValue(nameGenerator, "<get-nameGenerator>(...)");
        InliningContext inliningContextSubInline$default = InliningContext.subInline$default(inliningContext, nameGenerator, null, null, false, 14, null);
        RegeneratedLambdaFieldRemapper regeneratedLambdaFieldRemapper2 = regeneratedLambdaFieldRemapper;
        boolean z = this.isSameModule;
        String str = "Transformer for " + ((AnonymousObjectTransformationInfo) this.transformationInfo).getOldClassName();
        SourceMapper sourceMapper2 = this.sourceMapper;
        if (sourceMapper2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sourceMapper");
            sourceMapper = null;
        } else {
            sourceMapper = sourceMapper2;
        }
        SMAP smap2 = this.sourceMap;
        if (smap2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sourceMap");
            smap = null;
        } else {
            smap = smap2;
        }
        ReifiedTypeParametersUsages reifiedTypeParametersUsages = reifiedTypeParametersUsagesReifyInstructions;
        InlineResult inlineResultDoInline = new MethodInliner(sourceNode, parametersBuildParameters, inliningContextSubInline$default, regeneratedLambdaFieldRemapper2, z, str, new SourceMapCopier(sourceMapper, smap, null, 4, null), new InlineCallSiteInfo(((AnonymousObjectTransformationInfo) this.transformationInfo).getOldClassName(), new Method(sourceNode.f1016name, isConstructor ? ((AnonymousObjectTransformationInfo) this.transformationInfo).getNewConstructorDescriptor() : sourceNode.desc), this.inliningContext.getCallSiteInfo().getInlineScopeVisibility(), this.inliningContext.getCallSiteInfo().getFile(), this.inliningContext.getCallSiteInfo().getLineNumber()), false, false, 0, 0, 3584, null).doInline(deferringVisitor, new LocalVarRemapper(parametersBuildParameters, 0), false, MapsKt.emptyMap());
        if (reifiedTypeParametersUsages != null) {
            inlineResultDoInline.getReifiedTypeParametersUsages().mergeAll(reifiedTypeParametersUsages);
        }
        deferringVisitor.visitMaxs(-1, -1);
        return inlineResultDoInline;
    }

    private final void generateConstructorAndFields(ClassBuilder classBuilder, ParametersBuilder constructorInlineBuilder, FieldRemapper parentRemapper) {
        int size;
        CapturedParamInfo capturedParamInfo;
        Parameters parametersBuildParameters = constructorInlineBuilder.buildParameters();
        ArrayList arrayList = new ArrayList();
        for (ParameterInfo parameterInfo : parametersBuildParameters) {
            if (!parameterInfo.getIsSkipped()) {
                arrayList.add(parameterInfo);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((ParameterInfo) it.next()).getType());
        }
        List listDrop = CollectionsKt.drop(arrayList3, 1);
        Type type = Type.VOID_TYPE;
        Type[] typeArr = (Type[]) listDrop.toArray(new Type[0]);
        String methodDescriptor = Type.getMethodDescriptor(type, (Type[]) Arrays.copyOf(typeArr, typeArr.length));
        AnonymousObjectTransformationInfo anonymousObjectTransformationInfo = (AnonymousObjectTransformationInfo) this.transformationInfo;
        Intrinsics.checkNotNull(methodDescriptor);
        anonymousObjectTransformationInfo.setNewConstructorDescriptor(methodDescriptor);
        JvmDeclarationOrigin jvmDeclarationOrigin = JvmDeclarationOrigin.NO_ORIGIN;
        MethodNode methodNode = this.constructor;
        Intrinsics.checkNotNull(methodNode);
        MethodVisitor methodVisitorNewMethod = classBuilder.newMethod(jvmDeclarationOrigin, methodNode.access, "<init>", methodDescriptor, null, ArrayUtil.EMPTY_STRING_ARRAY);
        Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
        final Label label = new Label();
        methodVisitorNewMethod.visitLabel(label);
        Iterator<ParameterInfo> it2 = parametersBuildParameters.iterator();
        int i = 0;
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            ParameterInfo next = it2.next();
            if (next.getIsSkipped()) {
                size = i;
                i = -1;
            } else {
                size = next.getType().getSize() + i;
            }
            CapturedParamInfo fieldEquivalent = next.getFieldEquivalent();
            if (fieldEquivalent != null) {
                constructorInlineBuilder.addCapturedParam(fieldEquivalent, fieldEquivalent.getNewFieldName()).setRemapValue(i != -1 ? StackValue.local(i, next.getType()) : null);
                capturedParamInfo = fieldEquivalent;
            } else {
                capturedParamInfo = next;
            }
            if (!next.getIsSkipped() && (capturedParamInfo instanceof CapturedParamInfo)) {
                CapturedParamInfo capturedParamInfo2 = (CapturedParamInfo) capturedParamInfo;
                if (!capturedParamInfo2.getIsSkipInConstructor()) {
                    String descriptor = capturedParamInfo.getType().getDescriptor();
                    classBuilder.newField(JvmDeclarationOrigin.NO_ORIGIN, 4112, capturedParamInfo2.getNewFieldName(), descriptor, null, null);
                    methodVisitorNewMethod.visitVarInsn(25, 0);
                    methodVisitorNewMethod.visitVarInsn(capturedParamInfo.getType().getOpcode(21), i);
                    methodVisitorNewMethod.visitFieldInsn(181, ((AnonymousObjectTransformationInfo) this.transformationInfo).getNewClassName(), capturedParamInfo2.getNewFieldName(), descriptor);
                }
            }
            i = size;
        }
        MethodNode methodNode2 = this.constructor;
        Intrinsics.checkNotNull(methodNode2);
        MethodNode methodNode3 = new MethodNode(methodNode2.access, "<init>", methodDescriptor, null, ArrayUtil.EMPTY_STRING_ARRAY);
        MethodNode methodNode4 = this.constructor;
        Intrinsics.checkNotNull(methodNode4);
        inlineMethodAndUpdateGlobalResult(parentRemapper, methodNode3, methodNode4, constructorInlineBuilder, true);
        InlineCodegenUtilsKt.removeFinallyMarkers(methodNode3);
        AbstractInsnNode first = methodNode3.instructions.getFirst();
        LabelNode labelNode = first instanceof LabelNode ? (LabelNode) first : null;
        final Label label2 = labelNode != null ? labelNode.getLabel() : null;
        methodNode3.accept(new MethodBodyVisitor(methodVisitorNewMethod) { // from class: org.jetbrains.kotlin.codegen.inline.AnonymousObjectTransformer.generateConstructorAndFields.1
            @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitLocalVariable(String name2, String desc, String signature, Label start, Label end, int index) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                Intrinsics.checkNotNullParameter(start, "start");
                Intrinsics.checkNotNullParameter(end, "end");
                if (label2 == start) {
                    start = label;
                }
                super.visitLocalVariable(name2, desc, signature, start, end, index);
            }
        });
        methodVisitorNewMethod.visitEnd();
    }

    private final Parameters getMethodParametersWithCaptured(ParametersBuilder capturedBuilder, MethodNode sourceNode) {
        ParametersBuilder parametersBuilderNewBuilder = ParametersBuilder.INSTANCE.newBuilder();
        if ((sourceNode.access & 8) == 0) {
            Type oldObjectType = this.oldObjectType;
            Intrinsics.checkNotNullExpressionValue(oldObjectType, "oldObjectType");
            parametersBuilderNewBuilder.addThis(oldObjectType, false);
        }
        Type[] argumentTypes = Type.getArgumentTypes(sourceNode.desc);
        Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
        for (Type type : argumentTypes) {
            Intrinsics.checkNotNull(type);
            ParametersBuilder.addNextParameter$default(parametersBuilderNewBuilder, type, false, null, 4, null);
        }
        Iterator<CapturedParamInfo> it = capturedBuilder.listCaptured().iterator();
        while (it.hasNext()) {
            parametersBuilderNewBuilder.addCapturedParamCopy(it.next());
        }
        return parametersBuilderNewBuilder.buildParameters();
    }

    private final DeferredMethodVisitor newMethod(final ClassBuilder builder, final MethodNode original) {
        return new DeferredMethodVisitor(new MethodNode(original.access, original.f1016name, original.desc, original.signature, ArrayUtil.toStringArray(original.exceptions)), new Function0<MethodVisitor>() { // from class: org.jetbrains.kotlin.codegen.inline.AnonymousObjectTransformer.newMethod.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MethodVisitor invoke() {
                MethodVisitor methodVisitorNewMethod = builder.newMethod(JvmDeclarationOrigin.NO_ORIGIN, original.access, original.f1016name, original.desc, original.signature, ArrayUtil.toStringArray(original.exceptions));
                Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
                return methodVisitorNewMethod;
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:37:0x00e3 A[EDGE_INSN: B:37:0x00e3->B:38:0x00e6 BREAK  A[LOOP:1: B:21:0x0085->B:36:0x00e0], PHI: r2
      0x00e3: PHI (r2v17 int) = (r2v16 int), (r2v16 int), (r2v21 int), (r2v16 int) binds: [B:12:0x0060, B:14:0x0069, B:98:0x00e3, B:18:0x007e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:73:0x023e  */
    /* JADX WARN: Code duplicated, block: B:7:0x002a  */
    private final void extractParametersMappingAndPatchConstructor(MethodNode constructor, ParametersBuilder capturedParamBuilder, ParametersBuilder constructorParamBuilder, AnonymousObjectTransformationInfo transformationInfo, FieldRemapper parentFieldRemapper) {
        boolean z;
        boolean z2;
        int i;
        String strAddUniqueField;
        HashMap map = new HashMap();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Map<Integer, FunctionalArgument> functionalArguments = transformationInfo.getFunctionalArguments();
        if (parentFieldRemapper instanceof InlinedLambdaRemapper) {
            FieldRemapper fieldRemapper = parentFieldRemapper.parent;
            Intrinsics.checkNotNull(fieldRemapper);
            if (fieldRemapper.getIsRoot()) {
                z = false;
            } else {
                z = true;
            }
        } else {
            z = false;
        }
        ArrayList arrayList = new ArrayList();
        for (FieldInsnNode fieldInsnNode : MethodInlinerUtilKt.findCapturedFieldAssignmentInstructions(constructor)) {
            String str = fieldInsnNode.f1010name;
            AbstractInsnNode previous = fieldInsnNode.getPrevious();
            Intrinsics.checkNotNull(previous, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
            VarInsnNode varInsnNode = (VarInsnNode) previous;
            int i2 = varInsnNode.var;
            FunctionalArgument functionalArgument = functionalArguments.get(Integer.valueOf(i2));
            if (z) {
                i = i2;
                strAddUniqueField = str;
                break;
            }
            Intrinsics.checkNotNull(str);
            if (!InlineCodegenUtilsKt.isThis0(str)) {
                i = i2;
                strAddUniqueField = str;
                break;
            }
            Collection<FunctionalArgument> collectionValues = functionalArguments.values();
            if ((collectionValues instanceof Collection) && collectionValues.isEmpty()) {
                i = i2;
                strAddUniqueField = str;
                break;
            }
            Iterator<T> it = collectionValues.iterator();
            while (true) {
                if (!it.hasNext()) {
                    i = i2;
                    strAddUniqueField = str;
                    break;
                }
                FunctionalArgument functionalArgument2 = (FunctionalArgument) it.next();
                i = i2;
                if (functionalArgument2 instanceof LambdaInfo) {
                    List<CapturedParamDesc> capturedVars = ((LambdaInfo) functionalArgument2).getCapturedVars();
                    if (!(capturedVars instanceof Collection) || !capturedVars.isEmpty()) {
                        Iterator<T> it2 = capturedVars.iterator();
                        while (it2.hasNext()) {
                            if (Intrinsics.areEqual(((CapturedParamDesc) it2.next()).getFieldName(), str)) {
                                strAddUniqueField = addUniqueField(str + InlineCodegenUtilsKt.INLINE_FUN_THIS_0_SUFFIX);
                                break;
                            }
                        }
                    }
                }
                i2 = i;
            }
            Type objectType = Type.getObjectType(transformationInfo.getOldClassName());
            Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
            Intrinsics.checkNotNull(str);
            Intrinsics.checkNotNull(strAddUniqueField);
            Type type = Type.getType(fieldInsnNode.desc);
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            boolean z3 = functionalArgument instanceof LambdaInfo;
            ArrayList arrayList2 = arrayList;
            CapturedParamInfo capturedParamInfoAddCapturedParam = capturedParamBuilder.addCapturedParam(objectType, str, strAddUniqueField, type, z3, null);
            capturedParamInfoAddCapturedParam.setFunctionalArgument(functionalArgument);
            if (z3) {
                linkedHashSet.add(functionalArgument);
            }
            map.put(Integer.valueOf(i), capturedParamInfoAddCapturedParam);
            arrayList2.add(varInsnNode.getPrevious());
            arrayList2.add(varInsnNode);
            arrayList2.add(fieldInsnNode);
            arrayList = arrayList2;
        }
        MethodInlinerUtilKt.remove(constructor, arrayList);
        Type oldObjectType = this.oldObjectType;
        Intrinsics.checkNotNullExpressionValue(oldObjectType, "oldObjectType");
        constructorParamBuilder.addThis(oldObjectType, false);
        String constructorDesc = transformationInfo.getConstructorDesc();
        Type[] argumentTypes = constructorDesc != null ? Type.getArgumentTypes(constructorDesc) : null;
        if (argumentTypes == null) {
            argumentTypes = new Type[0];
        }
        Type[] typeArr = argumentTypes;
        int length = typeArr.length;
        int i3 = 0;
        while (i3 < length) {
            Type type2 = typeArr[i3];
            FunctionalArgument functionalArgument3 = functionalArguments.get(Integer.valueOf(constructorParamBuilder.getNextParameterOffset()));
            CapturedParamInfo capturedParamInfo = (CapturedParamInfo) map.get(Integer.valueOf(constructorParamBuilder.getNextParameterOffset()));
            Intrinsics.checkNotNull(type2);
            boolean z4 = functionalArgument3 instanceof LambdaInfo;
            Type[] typeArr2 = typeArr;
            ParameterInfo parameterInfoAddNextParameter$default = ParametersBuilder.addNextParameter$default(constructorParamBuilder, type2, z4, null, 4, null);
            parameterInfoAddNextParameter$default.setFunctionalArgument(functionalArgument3);
            parameterInfoAddNextParameter$default.setFieldEquivalent(capturedParamInfo);
            if (z4 && parameterInfoAddNextParameter$default.getFieldEquivalent() == null) {
                linkedHashSet.add(functionalArgument3);
            }
            i3++;
            typeArr = typeArr2;
        }
        ArrayList arrayList3 = new ArrayList();
        if (!z) {
            LinkedHashSet linkedHashSet2 = new LinkedHashSet();
            Iterator it3 = linkedHashSet.iterator();
            while (it3.hasNext()) {
                for (CapturedParamDesc capturedParamDesc : ((LambdaInfo) it3.next()).getCapturedVars()) {
                    String fieldName = InlineCodegenUtilsKt.isThis0(capturedParamDesc.getFieldName()) ? capturedParamDesc.getFieldName() : addUniqueField(capturedParamDesc.getFieldName() + InlineCodegenUtilsKt.INLINE_TRANSFORMATION_SUFFIX);
                    if (InlineCodegenUtilsKt.isThis0(capturedParamDesc.getFieldName())) {
                        String className = capturedParamDesc.getType().getClassName();
                        Intrinsics.checkNotNullExpressionValue(className, "getClassName(...)");
                        if (linkedHashSet2.add(className)) {
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                    } else {
                        z2 = false;
                    }
                    CapturedParamInfo capturedParamInfoAddCapturedParam2 = constructorParamBuilder.addCapturedParam(capturedParamDesc, fieldName, z2);
                    if (capturedParamDesc.getIsSuspend()) {
                        capturedParamInfoAddCapturedParam2.setFunctionalArgument(NonInlineArgumentForInlineSuspendParameter.INLINE_LAMBDA_AS_VARIABLE);
                    }
                    capturedParamBuilder.addCapturedParam(capturedParamInfoAddCapturedParam2, capturedParamInfoAddCapturedParam2.getNewFieldName()).setRemapValue(StackValue.field(capturedParamDesc.getType(), this.oldObjectType, capturedParamInfoAddCapturedParam2.getNewFieldName(), false, StackValue.LOCAL_0));
                    arrayList3.add(capturedParamDesc);
                }
            }
        } else if (!linkedHashSet.isEmpty()) {
            FieldRemapper fieldRemapper2 = parentFieldRemapper.parent;
            RegeneratedLambdaFieldRemapper regeneratedLambdaFieldRemapper = fieldRemapper2 instanceof RegeneratedLambdaFieldRemapper ? (RegeneratedLambdaFieldRemapper) fieldRemapper2 : null;
            if (regeneratedLambdaFieldRemapper == null) {
                throw new AssertionError("Expecting RegeneratedLambdaFieldRemapper, but " + parentFieldRemapper.parent);
            }
            Type objectType2 = Type.getObjectType(regeneratedLambdaFieldRemapper.getOriginalLambdaInternalName());
            Intrinsics.checkNotNull(objectType2);
            CapturedParamDesc capturedParamDesc2 = new CapturedParamDesc(objectType2, "this", objectType2, false, 8, null);
            CapturedParamInfo capturedParamInfoAddCapturedParam3 = constructorParamBuilder.addCapturedParam(capturedParamDesc2, AsmUtil.CAPTURED_THIS_FIELD, false);
            capturedParamBuilder.addCapturedParam(capturedParamInfoAddCapturedParam3, capturedParamInfoAddCapturedParam3.getNewFieldName()).setRemapValue(StackValue.LOCAL_0);
            arrayList3.add(capturedParamDesc2);
        }
        transformationInfo.setAllRecapturedParameters(arrayList3);
        LinkedHashSet linkedHashSet3 = linkedHashSet;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(linkedHashSet3, 10)), 16));
        for (Object obj : linkedHashSet3) {
            String internalName = ((LambdaInfo) obj).getLambdaClassType().getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            linkedHashMap.put(internalName, obj);
        }
        transformationInfo.setCapturedLambdasToInline(linkedHashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String addUniqueField(String name2) {
        HashMap<String, List<String>> map = this.fieldNames;
        LinkedList linkedList = map.get(name2);
        if (linkedList == null) {
            linkedList = new LinkedList();
            map.put(name2, linkedList);
        }
        List<String> list = linkedList;
        String str = name2 + (list.isEmpty() ? "" : "$" + list.size());
        list.add(str);
        return str;
    }
}
