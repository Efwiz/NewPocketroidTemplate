package com.android.tools.r8.diagnostic.internal;

import com.android.tools.r8.diagnostic.DefinitionClassContext;
import com.android.tools.r8.diagnostic.DefinitionContext;
import com.android.tools.r8.diagnostic.DefinitionFieldContext;
import com.android.tools.r8.diagnostic.DefinitionMethodContext;
import com.android.tools.r8.diagnostic.MissingClassInfo;
import com.android.tools.r8.diagnostic.MissingDefinitionInfo;
import com.android.tools.r8.diagnostic.MissingFieldInfo;
import com.android.tools.r8.diagnostic.MissingMethodInfo;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.utils.C3882d0;
import com.android.tools.r8.utils.C3889e2;
import com.android.tools.r8.utils.N;
import com.android.tools.r8.utils.V0;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class k {
    private static final Comparator a = new Comparator() { // from class: com.android.tools.r8.diagnostic.internal.k$$ExternalSyntheticLambda6
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return k.a((MissingDefinitionInfo) obj, (MissingDefinitionInfo) obj2);
        }
    };
    static final /* synthetic */ boolean b = true;

    /* JADX INFO: Access modifiers changed from: private */
    public static int a(MissingDefinitionInfo missingDefinitionInfo, MissingDefinitionInfo missingDefinitionInfo2) {
        if (missingDefinitionInfo.isMissingClass()) {
            ClassReference classReference = missingDefinitionInfo.asMissingClass().getClassReference();
            if (missingDefinitionInfo2.isMissingClass()) {
                return C3882d0.a().compare(classReference, missingDefinitionInfo2.asMissingClass().getClassReference());
            }
            if (missingDefinitionInfo2.isMissingField()) {
                int iCompare = C3882d0.a().compare(classReference, missingDefinitionInfo2.asMissingField().getFieldReference().getHolderClass());
                if (iCompare != 0) {
                    return iCompare;
                }
                return -1;
            }
            int iCompare2 = C3882d0.a().compare(classReference, missingDefinitionInfo2.asMissingMethod().getMethodReference().getHolderClass());
            if (iCompare2 != 0) {
                return iCompare2;
            }
            return -1;
        }
        if (missingDefinitionInfo.isMissingField()) {
            FieldReference fieldReference = missingDefinitionInfo.asMissingField().getFieldReference();
            if (missingDefinitionInfo2.isMissingClass()) {
                ClassReference classReference2 = missingDefinitionInfo2.asMissingClass().getClassReference();
                int i = V0.b;
                int iCompare3 = C3882d0.a().compare(classReference2, fieldReference.getHolderClass());
                if (iCompare3 == 0) {
                    iCompare3 = -1;
                }
                return iCompare3 * (-1);
            }
            if (missingDefinitionInfo2.isMissingField()) {
                return V0.a(fieldReference, missingDefinitionInfo2.asMissingField().getFieldReference());
            }
            MethodReference methodReference = missingDefinitionInfo2.asMissingMethod().getMethodReference();
            int i2 = V0.b;
            int iCompare4 = C3882d0.a().compare(fieldReference.getHolderClass(), methodReference.getHolderClass());
            if (iCompare4 != 0) {
                return iCompare4;
            }
            return -1;
        }
        MethodReference methodReference2 = missingDefinitionInfo.asMissingMethod().getMethodReference();
        if (missingDefinitionInfo2.isMissingClass()) {
            ClassReference classReference3 = missingDefinitionInfo2.asMissingClass().getClassReference();
            int i3 = C3889e2.b;
            int iCompare5 = C3882d0.a().compare(classReference3, methodReference2.getHolderClass());
            if (iCompare5 == 0) {
                iCompare5 = -1;
            }
            return iCompare5 * (-1);
        }
        if (!missingDefinitionInfo2.isMissingField()) {
            return C3889e2.a().compare(methodReference2, missingDefinitionInfo2.asMissingMethod().getMethodReference());
        }
        FieldReference fieldReference2 = missingDefinitionInfo2.asMissingField().getFieldReference();
        int i4 = C3889e2.b;
        int i5 = V0.b;
        int iCompare6 = C3882d0.a().compare(fieldReference2.getHolderClass(), methodReference2.getHolderClass());
        if (iCompare6 == 0) {
            iCompare6 = -1;
        }
        return iCompare6 * (-1);
    }

    private static void b(StringBuilder sb, MissingDefinitionInfo missingDefinitionInfo) {
        final N n = new N();
        final N n2 = new N();
        final N n3 = new N();
        Iterator<DefinitionContext> it = missingDefinitionInfo.getReferencedFromContexts().iterator();
        while (it.hasNext()) {
            d.a(it.next(), new Consumer() { // from class: com.android.tools.r8.diagnostic.internal.k$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    k.a(n, (DefinitionClassContext) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.diagnostic.internal.k$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    k.a(n2, (DefinitionFieldContext) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.diagnostic.internal.k$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    k.a(n3, (DefinitionMethodContext) obj);
                }
            });
        }
        if (n2.c()) {
            a(sb, missingDefinitionInfo, V0.a((FieldReference) n2.a()));
        } else if (n3.c()) {
            a(sb, missingDefinitionInfo, C3889e2.b((MethodReference) n3.a()));
        } else if (n.c()) {
            a(sb, missingDefinitionInfo, ((ClassReference) n.a()).getTypeName());
        }
    }

    public static Comparator a() {
        return a;
    }

    public static void a(final StringBuilder sb, MissingDefinitionInfo missingDefinitionInfo) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.diagnostic.internal.k$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                k.a(sb, (MissingClassInfo) obj);
            }
        };
        Consumer consumer2 = new Consumer() { // from class: com.android.tools.r8.diagnostic.internal.k$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                k.a(sb, (MissingFieldInfo) obj);
            }
        };
        Consumer consumer3 = new Consumer() { // from class: com.android.tools.r8.diagnostic.internal.k$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                k.a(sb, (MissingMethodInfo) obj);
            }
        };
        if (missingDefinitionInfo.isMissingClass()) {
            consumer.accept(missingDefinitionInfo.asMissingClass());
        } else if (missingDefinitionInfo.isMissingField()) {
            consumer2.accept(missingDefinitionInfo.asMissingField());
        } else {
            if (!b && !missingDefinitionInfo.isMissingMethod()) {
                throw new AssertionError();
            }
            consumer3.accept(missingDefinitionInfo.asMissingMethod());
        }
        b(sb, missingDefinitionInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, MissingClassInfo missingClassInfo) {
        sb.append("Missing class ").append(missingClassInfo.getClassReference().getTypeName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, MissingFieldInfo missingFieldInfo) {
        sb.append("Missing field ").append(V0.a(missingFieldInfo.getFieldReference()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, MissingMethodInfo missingMethodInfo) {
        sb.append("Missing method ").append(C3889e2.b(missingMethodInfo.getMethodReference()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static void a(N n, DefinitionClassContext definitionClassContext) {
        ClassReference classReference = definitionClassContext.getClassReference();
        Comparator<ClassReference> comparatorA = C3882d0.a();
        if (!n.c() || comparatorA.compare(classReference, n.a()) < 0) {
            n.a(classReference);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(N n, DefinitionFieldContext definitionFieldContext) {
        FieldReference fieldReference = definitionFieldContext.getFieldReference();
        Comparator comparatorA = V0.a();
        if (!n.c() || comparatorA.compare(fieldReference, n.a()) < 0) {
            n.a(fieldReference);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static void a(N n, DefinitionMethodContext definitionMethodContext) {
        MethodReference methodReference = definitionMethodContext.getMethodReference();
        Comparator<MethodReference> comparatorA = C3889e2.a();
        if (!n.c() || comparatorA.compare(methodReference, n.a()) < 0) {
            n.a(methodReference);
        }
    }

    private static void a(StringBuilder sb, MissingDefinitionInfo missingDefinitionInfo, String str) {
        int size = missingDefinitionInfo.getReferencedFromContexts().size() - 1;
        if (!b && size < 0) {
            throw new AssertionError();
        }
        sb.append(" (referenced from: ").append(str);
        if (size >= 1) {
            sb.append(" and ").append(size).append(" other context");
            if (size >= 2) {
                sb.append("s");
            }
        }
        sb.append(Constant.AFTER_QUTO);
    }
}
