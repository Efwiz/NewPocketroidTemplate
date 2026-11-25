package org.jetbrains.kotlin.fir.java.deserialization;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.MemberSignature;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JvmBinaryAnnotationDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002¨\u0006\t"}, d2 = {"loadMemberAnnotations", "Lorg/jetbrains/kotlin/fir/java/deserialization/MemberAnnotations;", "Lorg/jetbrains/kotlin/fir/FirSession;", "kotlinBinaryClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "byteContent", "", "kotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmBinaryAnnotationDeserializerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final MemberAnnotations loadMemberAnnotations(FirSession firSession, KotlinJvmBinaryClass kotlinJvmBinaryClass, byte[] bArr, KotlinClassFinder kotlinClassFinder) {
        final HashMap map = new HashMap();
        final AnnotationsLoader annotationsLoader = new AnnotationsLoader(firSession, kotlinClassFinder);
        final HashMap map2 = new HashMap();
        kotlinJvmBinaryClass.visitMembers(new KotlinJvmBinaryClass.MemberVisitor() { // from class: org.jetbrains.kotlin.fir.java.deserialization.JvmBinaryAnnotationDeserializerKt.loadMemberAnnotations.1
            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MemberVisitor
            public KotlinJvmBinaryClass.MethodAnnotationVisitor visitMethod(Name name2, String desc) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                MemberSignature.Companion companion = MemberSignature.INSTANCE;
                String strAsString = name2.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return new AnnotationVisitorForMethod(this, companion.fromMethodNameAndDesc(strAsString, desc));
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MemberVisitor
            public KotlinJvmBinaryClass.AnnotationVisitor visitField(Name name2, String desc, Object initializer) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                MemberSignature.Companion companion = MemberSignature.INSTANCE;
                String strAsString = name2.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return new MemberAnnotationVisitor(this, companion.fromFieldNameAndDesc(strAsString, desc));
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.java.deserialization.JvmBinaryAnnotationDeserializerKt$loadMemberAnnotations$1$AnnotationVisitorForMethod */
            /* JADX INFO: compiled from: JvmBinaryAnnotationDeserializer.kt */
            @Metadata(d1 = {"\u00003\n\u0000\n\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0003\u0000\u0001\u0002\b\u008a\u0004\u0018\u00002\u00060\u0001R\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\n\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\"\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0010"}, d2 = {"org/jetbrains/kotlin/fir/java/deserialization/JvmBinaryAnnotationDeserializerKt$loadMemberAnnotations$1.AnnotationVisitorForMethod", "org/jetbrains/kotlin/fir/java/deserialization/JvmBinaryAnnotationDeserializerKt$loadMemberAnnotations$1.MemberAnnotationVisitor", "org/jetbrains/kotlin/fir/java/deserialization/JvmBinaryAnnotationDeserializerKt$loadMemberAnnotations$1", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$MethodAnnotationVisitor;", "signature", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "(Lorg/jetbrains/kotlin/fir/java/deserialization/JvmBinaryAnnotationDeserializerKt$loadMemberAnnotations$1;Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;)V", "visitAnnotationMemberDefaultValue", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "visitParameterAnnotation", "index", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
            public final class AnnotationVisitorForMethod extends MemberAnnotationVisitor implements KotlinJvmBinaryClass.MethodAnnotationVisitor {
                final /* synthetic */ AnonymousClass1 this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnnotationVisitorForMethod(AnonymousClass1 anonymousClass1, MemberSignature signature) {
                    super(anonymousClass1, signature);
                    Intrinsics.checkNotNullParameter(signature, "signature");
                    this.this$0 = anonymousClass1;
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MethodAnnotationVisitor
                public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitParameterAnnotation(int index, ClassId classId, SourceElement source) {
                    Intrinsics.checkNotNullParameter(classId, "classId");
                    Intrinsics.checkNotNullParameter(source, "source");
                    MemberSignature memberSignatureFromMethodSignatureAndParameterIndex = MemberSignature.INSTANCE.fromMethodSignatureAndParameterIndex(getSignature(), index);
                    ArrayList arrayList = map.get(memberSignatureFromMethodSignatureAndParameterIndex);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        map.put(memberSignatureFromMethodSignatureAndParameterIndex, arrayList);
                    }
                    return annotationsLoader.loadAnnotationIfNotSpecial$java(classId, arrayList);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MethodAnnotationVisitor
                public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotationMemberDefaultValue() {
                    AnnotationsLoader annotationsLoader = annotationsLoader;
                    MemberSignature signature = getSignature();
                    final HashMap<MemberSignature, FirExpression> map = map2;
                    return annotationsLoader.loadAnnotationMethodDefaultValue$java(signature, new Function1<FirExpression, Unit>() { // from class: org.jetbrains.kotlin.fir.java.deserialization.JvmBinaryAnnotationDeserializerKt$loadMemberAnnotations$1$AnnotationVisitorForMethod$visitAnnotationMemberDefaultValue$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirExpression firExpression) {
                            invoke2(firExpression);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirExpression it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            map.put(this.getSignature(), it);
                        }
                    });
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.java.deserialization.JvmBinaryAnnotationDeserializerKt$loadMemberAnnotations$1$MemberAnnotationVisitor */
            /* JADX INFO: compiled from: JvmBinaryAnnotationDeserializer.kt */
            @Metadata(d1 = {"\u0000;\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\u009a\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"org/jetbrains/kotlin/fir/java/deserialization/JvmBinaryAnnotationDeserializerKt$loadMemberAnnotations$1.MemberAnnotationVisitor", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationVisitor;", "signature", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "(Lorg/jetbrains/kotlin/fir/java/deserialization/JvmBinaryAnnotationDeserializerKt$loadMemberAnnotations$1;Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;)V", "result", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "Lkotlin/collections/ArrayList;", "getSignature", "()Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "visitAnnotation", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "visitEnd", "", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
            public class MemberAnnotationVisitor implements KotlinJvmBinaryClass.AnnotationVisitor {
                private final ArrayList<FirAnnotation> result;
                private final MemberSignature signature;
                final /* synthetic */ AnonymousClass1 this$0;

                public MemberAnnotationVisitor(AnonymousClass1 anonymousClass1, MemberSignature signature) {
                    Intrinsics.checkNotNullParameter(signature, "signature");
                    this.this$0 = anonymousClass1;
                    this.signature = signature;
                    this.result = new ArrayList<>();
                }

                protected final MemberSignature getSignature() {
                    return this.signature;
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor
                public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(ClassId classId, SourceElement source) {
                    Intrinsics.checkNotNullParameter(classId, "classId");
                    Intrinsics.checkNotNullParameter(source, "source");
                    return annotationsLoader.loadAnnotationIfNotSpecial$java(classId, this.result);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor
                public void visitEnd() {
                    if (this.result.isEmpty()) {
                        return;
                    }
                    map.put(this.signature, this.result);
                }
            }
        }, bArr);
        return new MemberAnnotations(map, map2);
    }
}
