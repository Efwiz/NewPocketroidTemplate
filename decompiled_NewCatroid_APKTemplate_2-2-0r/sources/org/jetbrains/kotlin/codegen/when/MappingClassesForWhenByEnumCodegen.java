package org.jetbrains.kotlin.codegen.when;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.bytebuddy.description.method.MethodDescription;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.WriteAnnotationUtilKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: loaded from: classes3.dex */
public class MappingClassesForWhenByEnumCodegen {
    public static final String MAPPINGS_FIELD_DESCRIPTOR = Type.getDescriptor(int[].class);
    private final GenerationState state;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 5:
            case 7:
                objArr[0] = "mappings";
                break;
            case 2:
                objArr[0] = "mappingsClass";
                break;
            case 3:
                objArr[0] = "srcFile";
                break;
            case 4:
            case 6:
            case 8:
                objArr[0] = "cb";
                break;
            case 9:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 10:
                objArr[0] = "mapping";
                break;
            default:
                objArr[0] = "state";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/when/MappingClassesForWhenByEnumCodegen";
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[2] = CompilerOptions.GENERATE;
                break;
            case 4:
            case 5:
                objArr[2] = "generateFields";
                break;
            case 6:
            case 7:
                objArr[2] = "generateInitialization";
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "generateInitializationForMapping";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public MappingClassesForWhenByEnumCodegen(GenerationState generationState) {
        if (generationState == null) {
            $$$reportNull$$$0(0);
        }
        this.state = generationState;
    }

    public void generate(List<WhenByEnumsMapping> list, Type type, KtFile ktFile) {
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        if (type == null) {
            $$$reportNull$$$0(2);
        }
        if (ktFile == null) {
            $$$reportNull$$$0(3);
        }
        ClassBuilder classBuilderNewVisitor = this.state.getFactory().newVisitor(JvmDeclarationOrigin.NO_ORIGIN, type, ktFile);
        classBuilderNewVisitor.defineClass(ktFile, this.state.getConfig().getClassFileVersion(), 4145, type.getInternalName(), null, AsmTypes.OBJECT_TYPE.getInternalName(), ArrayUtil.EMPTY_STRING_ARRAY);
        generateFields(classBuilderNewVisitor, list);
        generateInitialization(classBuilderNewVisitor, list);
        WriteAnnotationUtilKt.writeSyntheticClassMetadata(classBuilderNewVisitor, this.state, list.stream().anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.codegen.when.MappingClassesForWhenByEnumCodegen$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((WhenByEnumsMapping) obj).isPublicAbi();
            }
        }));
        classBuilderNewVisitor.done(this.state.getConfig().getGenerateSmapCopyToAnnotation());
    }

    private static void generateFields(ClassBuilder classBuilder, List<WhenByEnumsMapping> list) {
        if (classBuilder == null) {
            $$$reportNull$$$0(4);
        }
        if (list == null) {
            $$$reportNull$$$0(5);
        }
        Iterator<WhenByEnumsMapping> it = list.iterator();
        while (it.hasNext()) {
            classBuilder.newField(JvmDeclarationOrigin.NO_ORIGIN, 4121, it.next().getFieldName(), MAPPINGS_FIELD_DESCRIPTOR, null, null);
        }
    }

    private void generateInitialization(ClassBuilder classBuilder, List<WhenByEnumsMapping> list) {
        if (classBuilder == null) {
            $$$reportNull$$$0(6);
        }
        if (list == null) {
            $$$reportNull$$$0(7);
        }
        MethodVisitor methodVisitorNewMethod = classBuilder.newMethod(JvmDeclarationOrigin.NO_ORIGIN, 4104, MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME, "()V", null, ArrayUtil.EMPTY_STRING_ARRAY);
        methodVisitorNewMethod.visitCode();
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
        Iterator<WhenByEnumsMapping> it = list.iterator();
        while (it.hasNext()) {
            generateInitializationForMapping(classBuilder, instructionAdapter, it.next());
        }
        instructionAdapter.areturn(Type.VOID_TYPE);
        methodVisitorNewMethod.visitMaxs(-1, -1);
        methodVisitorNewMethod.visitEnd();
    }

    private void generateInitializationForMapping(ClassBuilder classBuilder, InstructionAdapter instructionAdapter, WhenByEnumsMapping whenByEnumsMapping) {
        if (classBuilder == null) {
            $$$reportNull$$$0(8);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(9);
        }
        if (whenByEnumsMapping == null) {
            $$$reportNull$$$0(10);
        }
        Type typeMapClass = this.state.getTypeMapper().mapClass(whenByEnumsMapping.getEnumClassDescriptor());
        instructionAdapter.invokestatic(typeMapClass.getInternalName(), CompoundEntry.NAME_values, Type.getMethodDescriptor(Type.getType("[" + typeMapClass.getDescriptor()), new Type[0]), false);
        instructionAdapter.arraylength();
        instructionAdapter.newarray(Type.INT_TYPE);
        instructionAdapter.putstatic(classBuilder.getThisName(), whenByEnumsMapping.getFieldName(), MAPPINGS_FIELD_DESCRIPTOR);
        for (Map.Entry<EnumValue, Integer> entry : whenByEnumsMapping.enumValuesToIntMapping()) {
            EnumValue key = entry.getKey();
            int iIntValue = entry.getValue().intValue();
            instructionAdapter.getstatic(classBuilder.getThisName(), whenByEnumsMapping.getFieldName(), MAPPINGS_FIELD_DESCRIPTOR);
            instructionAdapter.getstatic(typeMapClass.getInternalName(), key.getEnumEntryName().asString(), typeMapClass.getDescriptor());
            instructionAdapter.invokevirtual(typeMapClass.getInternalName(), "ordinal", Type.getMethodDescriptor(Type.INT_TYPE, new Type[0]), false);
            instructionAdapter.iconst(iIntValue);
            instructionAdapter.astore(Type.INT_TYPE);
        }
    }
}
