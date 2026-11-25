package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\u0018\u00002\u00020\u0001B%\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tB)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\u0010\rJ\u0012\u0010\u000e\u001a\u00020\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\bH\u0002J\u001e\u0010\u0010\u001a\u0004\u0018\u00010\b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0002J\u001c\u0010\u0015\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u001a\u0010\u0016\u001a\u00020\u00012\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0017\u001a\u00020\u0012H\u0016J\u0012\u0010\u0018\u001a\u00020\u00012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\"\u0010\u0019\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaAnnotationVisitor;", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "context", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;", "signatureParser", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;", "arguments", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "(Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;Ljava/util/Collection;)V", "sink", "Lkotlin/Function1;", "", "(Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;Lkotlin/jvm/functions/Function1;)V", "addArgument", "argument", "convertConstValue", "name", "", "value", "", "visit", "visitAnnotation", "desc", "visitArray", "visitEnum", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryJavaAnnotationVisitor extends AnnotationVisitor {
    private final ClassifierResolutionContext context;
    private final BinaryClassSignatureParser signatureParser;
    private final Function1<JavaAnnotationArgument, Unit> sink;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BinaryJavaAnnotationVisitor(ClassifierResolutionContext context, BinaryClassSignatureParser signatureParser, Function1<? super JavaAnnotationArgument, Unit> sink) {
        super(589824);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(signatureParser, "signatureParser");
        Intrinsics.checkNotNullParameter(sink, "sink");
        this.context = context;
        this.signatureParser = signatureParser;
        this.sink = sink;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BinaryJavaAnnotationVisitor(ClassifierResolutionContext context, BinaryClassSignatureParser signatureParser, final Collection<JavaAnnotationArgument> arguments) {
        this(context, signatureParser, new Function1<JavaAnnotationArgument, Unit>() { // from class: org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryJavaAnnotationVisitor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(JavaAnnotationArgument javaAnnotationArgument) {
                invoke2(javaAnnotationArgument);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(JavaAnnotationArgument it) {
                Intrinsics.checkNotNullParameter(it, "it");
                arguments.add(it);
            }
        });
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(signatureParser, "signatureParser");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
    }

    private final void addArgument(JavaAnnotationArgument argument) {
        if (argument != null) {
            this.sink.invoke(argument);
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public AnnotationVisitor visitAnnotation(String name2, String desc) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        Pair pairCreateAnnotationAndVisitor$default = BinaryJavaAnnotation.Companion.createAnnotationAndVisitor$default(BinaryJavaAnnotation.INSTANCE, desc, this.context, this.signatureParser, false, 8, null);
        JavaAnnotation javaAnnotation = (JavaAnnotation) pairCreateAnnotationAndVisitor$default.component1();
        AnnotationVisitor annotationVisitor = (AnnotationVisitor) pairCreateAnnotationAndVisitor$default.component2();
        this.sink.invoke(new PlainJavaAnnotationAsAnnotationArgument(name2, javaAnnotation));
        return annotationVisitor;
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public void visitEnum(String name2, String desc, String value) {
        ClassId classIdConvertNestedClassInternalNameWithSimpleHeuristic$resolution_common_jvm;
        Intrinsics.checkNotNullParameter(desc, "desc");
        Intrinsics.checkNotNullParameter(value, "value");
        String internalName = Type.getType(desc).getInternalName();
        ClassifierResolutionContext classifierResolutionContext = this.context;
        Intrinsics.checkNotNull(internalName);
        ClassId classIdMapInternalNameToClassId$resolution_common_jvm = classifierResolutionContext.mapInternalNameToClassId$resolution_common_jvm(internalName);
        String strAsString = classIdMapInternalNameToClassId$resolution_common_jvm.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (StringsKt.contains$default((CharSequence) strAsString, (CharSequence) "$", false, 2, (Object) null) && (classIdConvertNestedClassInternalNameWithSimpleHeuristic$resolution_common_jvm = this.context.convertNestedClassInternalNameWithSimpleHeuristic$resolution_common_jvm(internalName)) != null) {
            classIdMapInternalNameToClassId$resolution_common_jvm = classIdConvertNestedClassInternalNameWithSimpleHeuristic$resolution_common_jvm;
        }
        addArgument(new PlainJavaEnumValueAnnotationArgument(name2, classIdMapInternalNameToClassId$resolution_common_jvm, value));
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public void visit(String name2, Object value) {
        addArgument(convertConstValue(name2, value));
    }

    private final JavaAnnotationArgument convertConstValue(String name2, Object value) {
        if (value instanceof Byte ? true : value instanceof Boolean ? true : value instanceof Character ? true : value instanceof Short ? true : value instanceof Integer ? true : value instanceof Long ? true : value instanceof Float ? true : value instanceof Double ? true : value instanceof String) {
            return new PlainJavaLiteralAnnotationArgument(name2, value);
        }
        if (value instanceof Type) {
            return new PlainJavaClassObjectAnnotationArgument(name2, (Type) value, this.signatureParser, this.context);
        }
        PlainJavaArrayAnnotationArgument plainJavaArrayAnnotationArgument = null;
        if (value != null) {
            if (!value.getClass().isArray()) {
                value = null;
            }
            if (value != null) {
                IntRange intRangeUntil = RangesKt.until(0, Array.getLength(value));
                ArrayList arrayList = new ArrayList();
                Iterator<Integer> it = intRangeUntil.iterator();
                while (it.hasNext()) {
                    JavaAnnotationArgument javaAnnotationArgumentConvertConstValue = convertConstValue(null, Array.get(value, ((IntIterator) it).nextInt()));
                    if (javaAnnotationArgumentConvertConstValue != null) {
                        arrayList.add(javaAnnotationArgumentConvertConstValue);
                    }
                }
                plainJavaArrayAnnotationArgument = new PlainJavaArrayAnnotationArgument(name2, arrayList);
            }
        }
        return plainJavaArrayAnnotationArgument;
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public AnnotationVisitor visitArray(String name2) {
        ArrayList arrayList = new ArrayList();
        addArgument(new PlainJavaArrayAnnotationArgument(name2, arrayList));
        return new BinaryJavaAnnotationVisitor(this.context, this.signatureParser, arrayList);
    }
}
