package org.jetbrains.kotlin.resolve;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;

/* JADX INFO: compiled from: AnnotationTargetLists.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b2\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001EB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u00105\u001a\u00020\u00042\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u000207J\u0016\u00109\u001a\u00020\u00042\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u000207J<\u0010:\u001a\u00020\u00042\u0012\u0010;\u001a\n\u0012\u0006\b\u0001\u0012\u00020=0<\"\u00020=2\u0019\b\u0002\u0010>\u001a\u0013\u0012\u0004\u0012\u00020@\u0012\u0004\u0012\u00020A0?¢\u0006\u0002\bBH\u0002¢\u0006\u0002\u0010CJ\u001c\u0010D\u001a\u00020A*\u00020@2\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u000207H\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u0011\u0010\u0019\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0006R\u0011\u0010\u001b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0006R\u0011\u0010\u001d\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0006R\u0011\u0010\u001f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0006R\u0011\u0010#\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0006R\u0011\u0010%\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0006R\u0011\u0010'\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0006R\u0011\u0010)\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0006R\u0011\u0010+\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0006R\u0011\u0010-\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0006R\u0011\u0010/\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u0006R\u0011\u00101\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u0006R\u0011\u00103\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u0010\u0006¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/resolve/AnnotationTargetLists;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/resolve/AnnotationTargetList;", "getEMPTY", "()Lorg/jetbrains/kotlin/resolve/AnnotationTargetList;", "T_BACKING_FIELD", "getT_BACKING_FIELD", "T_CLASSIFIER", "getT_CLASSIFIER", "T_CONSTRUCTOR", "getT_CONSTRUCTOR", "T_DESTRUCTURING_DECLARATION", "getT_DESTRUCTURING_DECLARATION", "T_EXPRESSION", "getT_EXPRESSION", "T_FILE", "getT_FILE", "T_FUNCTION_EXPRESSION", "getT_FUNCTION_EXPRESSION", "T_FUNCTION_LITERAL", "getT_FUNCTION_LITERAL", "T_INITIALIZER", "getT_INITIALIZER", "T_LOCAL_FUNCTION", "getT_LOCAL_FUNCTION", "T_LOCAL_VARIABLE", "getT_LOCAL_VARIABLE", "T_MEMBER_FUNCTION", "getT_MEMBER_FUNCTION", "T_OBJECT_LITERAL", "getT_OBJECT_LITERAL", "T_PROPERTY_GETTER", "getT_PROPERTY_GETTER", "T_PROPERTY_SETTER", "getT_PROPERTY_SETTER", "T_STAR_PROJECTION", "getT_STAR_PROJECTION", "T_TOP_LEVEL_FUNCTION", "getT_TOP_LEVEL_FUNCTION", "T_TYPEALIAS", "getT_TYPEALIAS", "T_TYPE_PARAMETER", "getT_TYPE_PARAMETER", "T_TYPE_PROJECTION", "getT_TYPE_PROJECTION", "T_TYPE_REFERENCE", "getT_TYPE_REFERENCE", "T_VALUE_PARAMETER_WITHOUT_VAL", "getT_VALUE_PARAMETER_WITHOUT_VAL", "T_VALUE_PARAMETER_WITH_VAL", "getT_VALUE_PARAMETER_WITH_VAL", "T_MEMBER_PROPERTY", "backingField", "", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "T_TOP_LEVEL_PROPERTY", "targetList", "target", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "otherTargets", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/AnnotationTargetLists$TargetListBuilder;", "", "Lkotlin/ExtensionFunctionType;", "([Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/resolve/AnnotationTargetList;", "propertyTargets", "TargetListBuilder", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationTargetLists {
    private static final AnnotationTargetList EMPTY;
    public static final AnnotationTargetLists INSTANCE;
    private static final AnnotationTargetList T_BACKING_FIELD;
    private static final AnnotationTargetList T_CLASSIFIER;
    private static final AnnotationTargetList T_CONSTRUCTOR;
    private static final AnnotationTargetList T_DESTRUCTURING_DECLARATION;
    private static final AnnotationTargetList T_EXPRESSION;
    private static final AnnotationTargetList T_FILE;
    private static final AnnotationTargetList T_FUNCTION_EXPRESSION;
    private static final AnnotationTargetList T_FUNCTION_LITERAL;
    private static final AnnotationTargetList T_INITIALIZER;
    private static final AnnotationTargetList T_LOCAL_FUNCTION;
    private static final AnnotationTargetList T_LOCAL_VARIABLE;
    private static final AnnotationTargetList T_MEMBER_FUNCTION;
    private static final AnnotationTargetList T_OBJECT_LITERAL;
    private static final AnnotationTargetList T_PROPERTY_GETTER;
    private static final AnnotationTargetList T_PROPERTY_SETTER;
    private static final AnnotationTargetList T_STAR_PROJECTION;
    private static final AnnotationTargetList T_TOP_LEVEL_FUNCTION;
    private static final AnnotationTargetList T_TYPEALIAS;
    private static final AnnotationTargetList T_TYPE_PARAMETER;
    private static final AnnotationTargetList T_TYPE_PROJECTION;
    private static final AnnotationTargetList T_TYPE_REFERENCE;
    private static final AnnotationTargetList T_VALUE_PARAMETER_WITHOUT_VAL;
    private static final AnnotationTargetList T_VALUE_PARAMETER_WITH_VAL;

    private AnnotationTargetLists() {
    }

    static {
        AnnotationTargetLists annotationTargetLists = new AnnotationTargetLists();
        INSTANCE = annotationTargetLists;
        T_CLASSIFIER = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.CLASS}, null, 2, null);
        T_TYPEALIAS = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.TYPEALIAS}, null, 2, null);
        T_LOCAL_VARIABLE = annotationTargetLists.targetList(new KotlinTarget[]{KotlinTarget.LOCAL_VARIABLE}, new Function1<TargetListBuilder, Unit>() { // from class: org.jetbrains.kotlin.resolve.AnnotationTargetLists$T_LOCAL_VARIABLE$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AnnotationTargetLists.TargetListBuilder targetListBuilder) {
                invoke2(targetListBuilder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AnnotationTargetLists.TargetListBuilder targetList) {
                Intrinsics.checkNotNullParameter(targetList, "$this$targetList");
                targetList.onlyWithUseSiteTarget(KotlinTarget.PROPERTY_SETTER, KotlinTarget.VALUE_PARAMETER);
            }
        });
        T_DESTRUCTURING_DECLARATION = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.DESTRUCTURING_DECLARATION}, null, 2, null);
        T_PROPERTY_GETTER = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.PROPERTY_GETTER}, null, 2, null);
        T_PROPERTY_SETTER = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.PROPERTY_SETTER}, null, 2, null);
        T_BACKING_FIELD = annotationTargetLists.targetList(new KotlinTarget[]{KotlinTarget.BACKING_FIELD}, new Function1<TargetListBuilder, Unit>() { // from class: org.jetbrains.kotlin.resolve.AnnotationTargetLists$T_BACKING_FIELD$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AnnotationTargetLists.TargetListBuilder targetListBuilder) {
                invoke2(targetListBuilder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AnnotationTargetLists.TargetListBuilder targetList) {
                Intrinsics.checkNotNullParameter(targetList, "$this$targetList");
                targetList.extraTargets(KotlinTarget.FIELD);
            }
        });
        T_VALUE_PARAMETER_WITHOUT_VAL = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.VALUE_PARAMETER}, null, 2, null);
        T_VALUE_PARAMETER_WITH_VAL = annotationTargetLists.targetList(new KotlinTarget[]{KotlinTarget.VALUE_PARAMETER, KotlinTarget.PROPERTY, KotlinTarget.MEMBER_PROPERTY}, new Function1<TargetListBuilder, Unit>() { // from class: org.jetbrains.kotlin.resolve.AnnotationTargetLists$T_VALUE_PARAMETER_WITH_VAL$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AnnotationTargetLists.TargetListBuilder targetListBuilder) {
                invoke2(targetListBuilder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AnnotationTargetLists.TargetListBuilder targetList) {
                Intrinsics.checkNotNullParameter(targetList, "$this$targetList");
                targetList.extraTargets(KotlinTarget.FIELD);
                targetList.onlyWithUseSiteTarget(KotlinTarget.PROPERTY_GETTER, KotlinTarget.PROPERTY_SETTER);
            }
        });
        T_FILE = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.FILE}, null, 2, null);
        T_CONSTRUCTOR = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.CONSTRUCTOR}, null, 2, null);
        T_LOCAL_FUNCTION = annotationTargetLists.targetList(new KotlinTarget[]{KotlinTarget.LOCAL_FUNCTION, KotlinTarget.FUNCTION}, new Function1<TargetListBuilder, Unit>() { // from class: org.jetbrains.kotlin.resolve.AnnotationTargetLists$T_LOCAL_FUNCTION$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AnnotationTargetLists.TargetListBuilder targetListBuilder) {
                invoke2(targetListBuilder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AnnotationTargetLists.TargetListBuilder targetList) {
                Intrinsics.checkNotNullParameter(targetList, "$this$targetList");
                targetList.onlyWithUseSiteTarget(KotlinTarget.VALUE_PARAMETER);
            }
        });
        T_MEMBER_FUNCTION = annotationTargetLists.targetList(new KotlinTarget[]{KotlinTarget.MEMBER_FUNCTION, KotlinTarget.FUNCTION}, new Function1<TargetListBuilder, Unit>() { // from class: org.jetbrains.kotlin.resolve.AnnotationTargetLists$T_MEMBER_FUNCTION$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AnnotationTargetLists.TargetListBuilder targetListBuilder) {
                invoke2(targetListBuilder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AnnotationTargetLists.TargetListBuilder targetList) {
                Intrinsics.checkNotNullParameter(targetList, "$this$targetList");
                targetList.onlyWithUseSiteTarget(KotlinTarget.VALUE_PARAMETER);
            }
        });
        T_TOP_LEVEL_FUNCTION = annotationTargetLists.targetList(new KotlinTarget[]{KotlinTarget.TOP_LEVEL_FUNCTION, KotlinTarget.FUNCTION}, new Function1<TargetListBuilder, Unit>() { // from class: org.jetbrains.kotlin.resolve.AnnotationTargetLists$T_TOP_LEVEL_FUNCTION$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AnnotationTargetLists.TargetListBuilder targetListBuilder) {
                invoke2(targetListBuilder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AnnotationTargetLists.TargetListBuilder targetList) {
                Intrinsics.checkNotNullParameter(targetList, "$this$targetList");
                targetList.onlyWithUseSiteTarget(KotlinTarget.VALUE_PARAMETER);
            }
        });
        T_EXPRESSION = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.EXPRESSION}, null, 2, null);
        T_FUNCTION_LITERAL = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.LAMBDA_EXPRESSION, KotlinTarget.FUNCTION, KotlinTarget.EXPRESSION}, null, 2, null);
        T_FUNCTION_EXPRESSION = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.ANONYMOUS_FUNCTION, KotlinTarget.FUNCTION, KotlinTarget.EXPRESSION}, null, 2, null);
        T_OBJECT_LITERAL = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.OBJECT_LITERAL, KotlinTarget.CLASS, KotlinTarget.EXPRESSION}, null, 2, null);
        T_TYPE_REFERENCE = annotationTargetLists.targetList(new KotlinTarget[]{KotlinTarget.TYPE}, new Function1<TargetListBuilder, Unit>() { // from class: org.jetbrains.kotlin.resolve.AnnotationTargetLists$T_TYPE_REFERENCE$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AnnotationTargetLists.TargetListBuilder targetListBuilder) {
                invoke2(targetListBuilder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AnnotationTargetLists.TargetListBuilder targetList) {
                Intrinsics.checkNotNullParameter(targetList, "$this$targetList");
                targetList.onlyWithUseSiteTarget(KotlinTarget.VALUE_PARAMETER);
            }
        });
        T_TYPE_PARAMETER = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.TYPE_PARAMETER}, null, 2, null);
        T_STAR_PROJECTION = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.STAR_PROJECTION}, null, 2, null);
        T_TYPE_PROJECTION = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.TYPE_PROJECTION}, null, 2, null);
        T_INITIALIZER = targetList$default(annotationTargetLists, new KotlinTarget[]{KotlinTarget.INITIALIZER}, null, 2, null);
        EMPTY = targetList$default(annotationTargetLists, new KotlinTarget[0], null, 2, null);
    }

    public final AnnotationTargetList getT_CLASSIFIER() {
        return T_CLASSIFIER;
    }

    public final AnnotationTargetList getT_TYPEALIAS() {
        return T_TYPEALIAS;
    }

    public final AnnotationTargetList getT_LOCAL_VARIABLE() {
        return T_LOCAL_VARIABLE;
    }

    public final AnnotationTargetList getT_DESTRUCTURING_DECLARATION() {
        return T_DESTRUCTURING_DECLARATION;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void propertyTargets(TargetListBuilder targetListBuilder, boolean z, boolean z2) {
        if (z) {
            targetListBuilder.extraTargets(KotlinTarget.FIELD);
        }
        if (z2) {
            targetListBuilder.onlyWithUseSiteTarget(KotlinTarget.VALUE_PARAMETER, KotlinTarget.PROPERTY_GETTER, KotlinTarget.PROPERTY_SETTER, KotlinTarget.FIELD);
        } else {
            targetListBuilder.onlyWithUseSiteTarget(KotlinTarget.VALUE_PARAMETER, KotlinTarget.PROPERTY_GETTER, KotlinTarget.PROPERTY_SETTER);
        }
    }

    public final AnnotationTargetList T_MEMBER_PROPERTY(final boolean backingField, final boolean delegate) {
        KotlinTarget kotlinTarget;
        KotlinTarget[] kotlinTargetArr = new KotlinTarget[3];
        if (backingField) {
            kotlinTarget = KotlinTarget.MEMBER_PROPERTY_WITH_BACKING_FIELD;
        } else if (delegate) {
            kotlinTarget = KotlinTarget.MEMBER_PROPERTY_WITH_DELEGATE;
        } else {
            kotlinTarget = KotlinTarget.MEMBER_PROPERTY_WITHOUT_FIELD_OR_DELEGATE;
        }
        kotlinTargetArr[0] = kotlinTarget;
        kotlinTargetArr[1] = KotlinTarget.MEMBER_PROPERTY;
        kotlinTargetArr[2] = KotlinTarget.PROPERTY;
        return targetList(kotlinTargetArr, new Function1<TargetListBuilder, Unit>() { // from class: org.jetbrains.kotlin.resolve.AnnotationTargetLists.T_MEMBER_PROPERTY.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(TargetListBuilder targetListBuilder) {
                invoke2(targetListBuilder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(TargetListBuilder targetList) {
                Intrinsics.checkNotNullParameter(targetList, "$this$targetList");
                AnnotationTargetLists.INSTANCE.propertyTargets(targetList, backingField, delegate);
            }
        });
    }

    public final AnnotationTargetList T_TOP_LEVEL_PROPERTY(final boolean backingField, final boolean delegate) {
        KotlinTarget kotlinTarget;
        KotlinTarget[] kotlinTargetArr = new KotlinTarget[3];
        if (backingField) {
            kotlinTarget = KotlinTarget.TOP_LEVEL_PROPERTY_WITH_BACKING_FIELD;
        } else if (delegate) {
            kotlinTarget = KotlinTarget.TOP_LEVEL_PROPERTY_WITH_DELEGATE;
        } else {
            kotlinTarget = KotlinTarget.TOP_LEVEL_PROPERTY_WITHOUT_FIELD_OR_DELEGATE;
        }
        kotlinTargetArr[0] = kotlinTarget;
        kotlinTargetArr[1] = KotlinTarget.TOP_LEVEL_PROPERTY;
        kotlinTargetArr[2] = KotlinTarget.PROPERTY;
        return targetList(kotlinTargetArr, new Function1<TargetListBuilder, Unit>() { // from class: org.jetbrains.kotlin.resolve.AnnotationTargetLists.T_TOP_LEVEL_PROPERTY.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(TargetListBuilder targetListBuilder) {
                invoke2(targetListBuilder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(TargetListBuilder targetList) {
                Intrinsics.checkNotNullParameter(targetList, "$this$targetList");
                AnnotationTargetLists.INSTANCE.propertyTargets(targetList, backingField, delegate);
            }
        });
    }

    public final AnnotationTargetList getT_PROPERTY_GETTER() {
        return T_PROPERTY_GETTER;
    }

    public final AnnotationTargetList getT_PROPERTY_SETTER() {
        return T_PROPERTY_SETTER;
    }

    public final AnnotationTargetList getT_BACKING_FIELD() {
        return T_BACKING_FIELD;
    }

    public final AnnotationTargetList getT_VALUE_PARAMETER_WITHOUT_VAL() {
        return T_VALUE_PARAMETER_WITHOUT_VAL;
    }

    public final AnnotationTargetList getT_VALUE_PARAMETER_WITH_VAL() {
        return T_VALUE_PARAMETER_WITH_VAL;
    }

    public final AnnotationTargetList getT_FILE() {
        return T_FILE;
    }

    public final AnnotationTargetList getT_CONSTRUCTOR() {
        return T_CONSTRUCTOR;
    }

    public final AnnotationTargetList getT_LOCAL_FUNCTION() {
        return T_LOCAL_FUNCTION;
    }

    public final AnnotationTargetList getT_MEMBER_FUNCTION() {
        return T_MEMBER_FUNCTION;
    }

    public final AnnotationTargetList getT_TOP_LEVEL_FUNCTION() {
        return T_TOP_LEVEL_FUNCTION;
    }

    public final AnnotationTargetList getT_EXPRESSION() {
        return T_EXPRESSION;
    }

    public final AnnotationTargetList getT_FUNCTION_LITERAL() {
        return T_FUNCTION_LITERAL;
    }

    public final AnnotationTargetList getT_FUNCTION_EXPRESSION() {
        return T_FUNCTION_EXPRESSION;
    }

    public final AnnotationTargetList getT_OBJECT_LITERAL() {
        return T_OBJECT_LITERAL;
    }

    public final AnnotationTargetList getT_TYPE_REFERENCE() {
        return T_TYPE_REFERENCE;
    }

    public final AnnotationTargetList getT_TYPE_PARAMETER() {
        return T_TYPE_PARAMETER;
    }

    public final AnnotationTargetList getT_STAR_PROJECTION() {
        return T_STAR_PROJECTION;
    }

    public final AnnotationTargetList getT_TYPE_PROJECTION() {
        return T_TYPE_PROJECTION;
    }

    public final AnnotationTargetList getT_INITIALIZER() {
        return T_INITIALIZER;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ AnnotationTargetList targetList$default(AnnotationTargetLists annotationTargetLists, KotlinTarget[] kotlinTargetArr, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = new Function1<TargetListBuilder, Unit>() { // from class: org.jetbrains.kotlin.resolve.AnnotationTargetLists.targetList.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(TargetListBuilder targetListBuilder) {
                    Intrinsics.checkNotNullParameter(targetListBuilder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(TargetListBuilder targetListBuilder) {
                    invoke2(targetListBuilder);
                    return Unit.INSTANCE;
                }
            };
        }
        return annotationTargetLists.targetList(kotlinTargetArr, function1);
    }

    private final AnnotationTargetList targetList(KotlinTarget[] target, Function1<? super TargetListBuilder, Unit> otherTargets) {
        TargetListBuilder targetListBuilder = new TargetListBuilder((KotlinTarget[]) Arrays.copyOf(target, target.length));
        otherTargets.invoke(targetListBuilder);
        return targetListBuilder.build();
    }

    public final AnnotationTargetList getEMPTY() {
        return EMPTY;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AnnotationTargetLists.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0006\u0010\f\u001a\u00020\rJ\u001f\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001f\u0010\u000b\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/AnnotationTargetLists$TargetListBuilder;", "", "defaultTargets", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "([Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;)V", "canBeSubstituted", "", "getDefaultTargets", "()[Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "[Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "onlyWithUseSiteTarget", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/resolve/AnnotationTargetList;", "extraTargets", "", "targets", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class TargetListBuilder {
        private List<? extends KotlinTarget> canBeSubstituted;
        private final KotlinTarget[] defaultTargets;
        private List<? extends KotlinTarget> onlyWithUseSiteTarget;

        public TargetListBuilder(KotlinTarget... defaultTargets) {
            Intrinsics.checkNotNullParameter(defaultTargets, "defaultTargets");
            this.defaultTargets = defaultTargets;
            this.canBeSubstituted = CollectionsKt.emptyList();
            this.onlyWithUseSiteTarget = CollectionsKt.emptyList();
        }

        public final KotlinTarget[] getDefaultTargets() {
            return this.defaultTargets;
        }

        public final void extraTargets(KotlinTarget... targets) {
            Intrinsics.checkNotNullParameter(targets, "targets");
            this.canBeSubstituted = ArraysKt.toList(targets);
        }

        public final void onlyWithUseSiteTarget(KotlinTarget... targets) {
            Intrinsics.checkNotNullParameter(targets, "targets");
            this.onlyWithUseSiteTarget = ArraysKt.toList(targets);
        }

        public final AnnotationTargetList build() {
            return new AnnotationTargetList(ArraysKt.toList(this.defaultTargets), this.canBeSubstituted, this.onlyWithUseSiteTarget);
        }
    }
}
