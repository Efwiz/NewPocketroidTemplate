package org.jf.baksmali.Adaptors;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.bytebuddy.description.method.MethodDescription;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.baksmali.formatter.BaksmaliFormatter;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.AccessFlags;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Field;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.formats.Instruction21c;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.Reference;

/* JADX INFO: loaded from: classes6.dex */
public class ClassDefinition {
    public final ClassDef classDef;
    private final HashSet<String> fieldsSetInStaticConstructor;
    private final BaksmaliFormatter formatter;
    public final BaksmaliOptions options;
    protected boolean validationErrors;

    public ClassDefinition(BaksmaliOptions baksmaliOptions, ClassDef classDef) {
        this.options = baksmaliOptions;
        this.classDef = classDef;
        this.formatter = new BaksmaliFormatter(baksmaliOptions.implicitReferences ? classDef.getType() : null);
        this.fieldsSetInStaticConstructor = findFieldsSetInStaticConstructor(classDef);
    }

    public boolean hadValidationErrors() {
        return this.validationErrors;
    }

    private HashSet<String> findFieldsSetInStaticConstructor(ClassDef classDef) {
        MethodImplementation implementation;
        HashSet<String> hashSet = new HashSet<>();
        for (Method method : classDef.getDirectMethods()) {
            if (method.getName().equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME) && (implementation = method.getImplementation()) != null) {
                for (Instruction instruction : implementation.getInstructions()) {
                    switch (AnonymousClass1.$SwitchMap$org$jf$dexlib2$Opcode[instruction.getOpcode().ordinal()]) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                            FieldReference fieldReference = (FieldReference) ((Instruction21c) instruction).getReference();
                            try {
                                fieldReference.validateReference();
                                if (fieldReference.getDefiningClass().equals(classDef.getType())) {
                                    hashSet.add(this.formatter.getShortFieldDescriptor(fieldReference));
                                }
                            } catch (Reference.InvalidReferenceException unused) {
                            }
                            break;
                    }
                }
            }
        }
        return hashSet;
    }

    /* JADX INFO: renamed from: org.jf.baksmali.Adaptors.ClassDefinition$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jf$dexlib2$Opcode;

        static {
            int[] iArr = new int[Opcode.values().length];
            $SwitchMap$org$jf$dexlib2$Opcode = iArr;
            try {
                iArr[Opcode.SPUT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_BOOLEAN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_CHAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_OBJECT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_SHORT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPUT_WIDE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public void writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        writeClass(baksmaliWriter);
        writeSuper(baksmaliWriter);
        writeSourceFile(baksmaliWriter);
        writeInterfaces(baksmaliWriter);
        writeAnnotations(baksmaliWriter);
        writeInstanceFields(baksmaliWriter, writeStaticFields(baksmaliWriter));
        writeVirtualMethods(baksmaliWriter, writeDirectMethods(baksmaliWriter));
    }

    private void writeClass(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(".class ");
        writeAccessFlags(baksmaliWriter);
        baksmaliWriter.writeType(this.classDef.getType());
        baksmaliWriter.write(10);
    }

    private void writeAccessFlags(BaksmaliWriter baksmaliWriter) throws IOException {
        for (AccessFlags accessFlags : AccessFlags.getAccessFlagsForClass(this.classDef.getAccessFlags())) {
            baksmaliWriter.write(accessFlags.toString());
            baksmaliWriter.write(32);
        }
    }

    private void writeSuper(BaksmaliWriter baksmaliWriter) throws IOException {
        String superclass = this.classDef.getSuperclass();
        if (superclass != null) {
            baksmaliWriter.write(".super ");
            baksmaliWriter.writeType(superclass);
            baksmaliWriter.write(10);
        }
    }

    private void writeSourceFile(BaksmaliWriter baksmaliWriter) throws IOException {
        String sourceFile = this.classDef.getSourceFile();
        if (sourceFile != null) {
            baksmaliWriter.write(".source ");
            baksmaliWriter.writeQuotedString(sourceFile);
            baksmaliWriter.write("\n");
        }
    }

    private void writeInterfaces(BaksmaliWriter baksmaliWriter) throws IOException {
        List<String> interfaces = this.classDef.getInterfaces();
        if (interfaces.size() != 0) {
            baksmaliWriter.write(10);
            baksmaliWriter.write("# interfaces\n");
            for (String str : interfaces) {
                baksmaliWriter.write(".implements ");
                baksmaliWriter.writeType(str);
                baksmaliWriter.write(10);
            }
        }
    }

    private void writeAnnotations(BaksmaliWriter baksmaliWriter) throws IOException {
        Set<? extends Annotation> annotations = this.classDef.getAnnotations();
        if (annotations.size() != 0) {
            baksmaliWriter.write("\n\n");
            baksmaliWriter.write("# annotations\n");
            AnnotationFormatter.writeTo(baksmaliWriter, annotations);
        }
    }

    private Set<String> writeStaticFields(BaksmaliWriter baksmaliWriter) throws IOException {
        Iterable<? extends Field> staticFields;
        boolean zContains;
        BaksmaliWriter commentingWriter;
        HashSet hashSet = new HashSet();
        ClassDef classDef = this.classDef;
        if (classDef instanceof DexBackedClassDef) {
            staticFields = ((DexBackedClassDef) classDef).getStaticFields(false);
        } else {
            staticFields = classDef.getStaticFields();
        }
        boolean z = false;
        for (Field field : staticFields) {
            if (!z) {
                baksmaliWriter.write("\n\n");
                baksmaliWriter.write("# static fields");
                z = true;
            }
            baksmaliWriter.write(10);
            String shortFieldDescriptor = this.formatter.getShortFieldDescriptor(field);
            if (!hashSet.add(shortFieldDescriptor)) {
                baksmaliWriter.write("# duplicate field ignored\n");
                commentingWriter = getCommentingWriter(baksmaliWriter);
                System.err.println(String.format("Ignoring duplicate field: %s->%s", this.classDef.getType(), shortFieldDescriptor));
                zContains = false;
            } else {
                zContains = this.fieldsSetInStaticConstructor.contains(shortFieldDescriptor);
                commentingWriter = baksmaliWriter;
            }
            FieldDefinition.writeTo(commentingWriter, field, zContains);
        }
        return hashSet;
    }

    private void writeInstanceFields(BaksmaliWriter baksmaliWriter, Set<String> set) throws IOException {
        Iterable<? extends Field> instanceFields;
        BaksmaliWriter commentingWriter;
        HashSet hashSet = new HashSet();
        ClassDef classDef = this.classDef;
        if (classDef instanceof DexBackedClassDef) {
            instanceFields = ((DexBackedClassDef) classDef).getInstanceFields(false);
        } else {
            instanceFields = classDef.getInstanceFields();
        }
        boolean z = false;
        for (Field field : instanceFields) {
            if (!z) {
                baksmaliWriter.write("\n\n");
                baksmaliWriter.write("# instance fields");
                z = true;
            }
            baksmaliWriter.write(10);
            String shortFieldDescriptor = this.formatter.getShortFieldDescriptor(field);
            if (!hashSet.add(shortFieldDescriptor)) {
                baksmaliWriter.write("# duplicate field ignored\n");
                commentingWriter = getCommentingWriter(baksmaliWriter);
                System.err.println(String.format("Ignoring duplicate field: %s->%s", this.classDef.getType(), shortFieldDescriptor));
            } else {
                if (set.contains(shortFieldDescriptor)) {
                    System.err.println(String.format("Duplicate static+instance field found: %s->%s", this.classDef.getType(), shortFieldDescriptor));
                    System.err.println("You will need to rename one of these fields, including all references.");
                    baksmaliWriter.write("# There is both a static and instance field with this signature.\n# You will need to rename one of these fields, including all references.\n");
                }
                commentingWriter = baksmaliWriter;
            }
            FieldDefinition.writeTo(commentingWriter, field, false);
        }
    }

    private Set<String> writeDirectMethods(BaksmaliWriter baksmaliWriter) throws IOException {
        Iterable<? extends Method> directMethods;
        BaksmaliWriter commentingWriter;
        HashSet hashSet = new HashSet();
        ClassDef classDef = this.classDef;
        boolean z = false;
        if (classDef instanceof DexBackedClassDef) {
            directMethods = ((DexBackedClassDef) classDef).getDirectMethods(false);
        } else {
            directMethods = classDef.getDirectMethods();
        }
        for (Method method : directMethods) {
            if (!z) {
                baksmaliWriter.write("\n\n");
                baksmaliWriter.write("# direct methods");
                z = true;
            }
            baksmaliWriter.write(10);
            if (hashSet.add(this.formatter.getShortMethodDescriptor(method))) {
                commentingWriter = baksmaliWriter;
            } else {
                baksmaliWriter.write("# duplicate method ignored\n");
                commentingWriter = getCommentingWriter(baksmaliWriter);
            }
            MethodImplementation implementation = method.getImplementation();
            if (implementation == null) {
                MethodDefinition.writeEmptyMethodTo(commentingWriter, method, this);
            } else {
                new MethodDefinition(this, method, implementation).writeTo(commentingWriter);
            }
        }
        return hashSet;
    }

    private void writeVirtualMethods(BaksmaliWriter baksmaliWriter, Set<String> set) throws IOException {
        Iterable<? extends Method> virtualMethods;
        BaksmaliWriter commentingWriter;
        HashSet hashSet = new HashSet();
        ClassDef classDef = this.classDef;
        boolean z = false;
        if (classDef instanceof DexBackedClassDef) {
            virtualMethods = ((DexBackedClassDef) classDef).getVirtualMethods(false);
        } else {
            virtualMethods = classDef.getVirtualMethods();
        }
        for (Method method : virtualMethods) {
            if (!z) {
                baksmaliWriter.write("\n\n");
                baksmaliWriter.write("# virtual methods");
                z = true;
            }
            baksmaliWriter.write(10);
            String shortMethodDescriptor = this.formatter.getShortMethodDescriptor(method);
            if (!hashSet.add(shortMethodDescriptor)) {
                baksmaliWriter.write("# duplicate method ignored\n");
                commentingWriter = getCommentingWriter(baksmaliWriter);
            } else {
                if (set.contains(shortMethodDescriptor)) {
                    baksmaliWriter.write("# There is both a direct and virtual method with this signature.\n# You will need to rename one of these methods, including all references.\n");
                    System.err.println(String.format("Duplicate direct+virtual method found: %s->%s", this.classDef.getType(), shortMethodDescriptor));
                    System.err.println("You will need to rename one of these methods, including all references.");
                }
                commentingWriter = baksmaliWriter;
            }
            MethodImplementation implementation = method.getImplementation();
            if (implementation == null) {
                MethodDefinition.writeEmptyMethodTo(commentingWriter, method, this);
            } else {
                new MethodDefinition(this, method, implementation).writeTo(commentingWriter);
            }
        }
    }

    public BaksmaliWriter getCommentingWriter(BaksmaliWriter baksmaliWriter) {
        return this.formatter.getWriter((Writer) new CommentingIndentingWriter(baksmaliWriter.indentingWriter()));
    }

    public BaksmaliFormatter getFormatter() {
        return this.formatter;
    }
}
