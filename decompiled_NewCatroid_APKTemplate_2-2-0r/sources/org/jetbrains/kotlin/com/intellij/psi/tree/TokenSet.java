package org.jetbrains.kotlin.com.intellij.psi.tree;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;
import kotlin.jvm.internal.ShortCompanionObject;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: loaded from: classes3.dex */
public final class TokenSet {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final short myMax;
    private final IElementType.Predicate myOrCondition;
    private final short myShift;
    private volatile IElementType[] myTypes;
    private final long[] myWords;
    public static final TokenSet EMPTY = new TokenSet(ShortCompanionObject.MAX_VALUE, 0, null);
    public static final TokenSet ANY = forAllMatching(IElementType.TRUE);
    public static final TokenSet WHITE_SPACE = doCreate(TokenType.WHITE_SPACE);

    /* JADX WARN: Code duplicated, block: B:33:0x004f  */
    /* JADX WARN: Code duplicated, block: B:34:0x0054  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 5 || i == 7 || i == 8 || i == 11 || i == 12 || i == 14 || i == 15) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 5 || i == 7 || i == 8 || i == 11 || i == 12 || i == 14 || i == 15) ? 3 : 2];
        if (i == 2 || i == 5) {
            objArr[0] = SpecTypePair.NAME_types;
        } else if (i == 7) {
            objArr[0] = "condition";
        } else if (i == 8) {
            objArr[0] = "sets";
        } else if (i == 11) {
            objArr[0] = "a";
        } else if (i == 12) {
            objArr[0] = JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME;
        } else if (i == 14) {
            objArr[0] = "a";
        } else if (i != 15) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/tree/TokenSet";
        } else {
            objArr[0] = JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME;
        }
        switch (i) {
            case 2:
            case 5:
            case 7:
            case 8:
            case 11:
            case 12:
            case 14:
            case 15:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/tree/TokenSet";
                break;
            case 3:
            case 4:
                objArr[1] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            case 6:
                objArr[1] = "doCreate";
                break;
            case 9:
            case 10:
                objArr[1] = "orSet";
                break;
            case 13:
                objArr[1] = "andSet";
                break;
            case 16:
                objArr[1] = "andNot";
                break;
            default:
                objArr[1] = "getTypes";
                break;
        }
        if (i == 2) {
            objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        } else if (i == 5) {
            objArr[2] = "doCreate";
        } else if (i == 7) {
            objArr[2] = "forAllMatching";
        } else if (i == 8) {
            objArr[2] = "orSet";
        } else if (i == 11 || i == 12) {
            objArr[2] = "andSet";
        } else if (i == 14 || i == 15) {
            objArr[2] = "andNot";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5 && i != 7 && i != 8 && i != 11 && i != 12 && i != 14 && i != 15) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    private TokenSet(short s, short s2, IElementType.Predicate predicate) {
        this.myShift = s;
        this.myMax = s2;
        int i = ((s2 >> 6) + 1) - s;
        this.myWords = i > 0 ? new long[i] : ArrayUtil.EMPTY_LONG_ARRAY;
        this.myOrCondition = predicate;
    }

    private boolean get(int i) {
        int i2 = (i >> 6) - this.myShift;
        if (i2 >= 0) {
            long[] jArr = this.myWords;
            if (i2 < jArr.length && (jArr[i2] & (1 << i)) != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(IElementType iElementType) {
        IElementType.Predicate predicate;
        if (iElementType == null) {
            return false;
        }
        short index = iElementType.getIndex();
        return (index >= 0 && index <= this.myMax && get(index)) || ((predicate = this.myOrCondition) != null && predicate.matches(iElementType));
    }

    public IElementType[] getTypes() {
        IElementType iElementTypeFind;
        if (this.myOrCondition != null) {
            IElementType[] iElementTypeArrEnumerate = IElementType.enumerate(new IElementType.Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IElementType.Predicate
                public final boolean matches(IElementType iElementType) {
                    return this.f$0.contains(iElementType);
                }
            });
            if (iElementTypeArrEnumerate == null) {
                $$$reportNull$$$0(0);
            }
            return iElementTypeArrEnumerate;
        }
        IElementType[] iElementTypeArr = this.myTypes;
        if (iElementTypeArr == null) {
            if (this.myWords.length == 0) {
                iElementTypeArr = IElementType.EMPTY_ARRAY;
            } else {
                ArrayList arrayList = new ArrayList();
                int iMax = Math.max(1, this.myShift << 6);
                while (true) {
                    short s = (short) iMax;
                    if (s > this.myMax) {
                        break;
                    }
                    if (get(s) && (iElementTypeFind = IElementType.find(s)) != null) {
                        arrayList.add(iElementTypeFind);
                    }
                    iMax = s + 1;
                }
                iElementTypeArr = (IElementType[]) arrayList.toArray(IElementType.EMPTY_ARRAY);
            }
            this.myTypes = iElementTypeArr;
        }
        if (iElementTypeArr == null) {
            $$$reportNull$$$0(1);
        }
        return iElementTypeArr;
    }

    public String toString() {
        return Arrays.toString(getTypes());
    }

    public static TokenSet create(IElementType... iElementTypeArr) {
        if (iElementTypeArr == null) {
            $$$reportNull$$$0(2);
        }
        if (iElementTypeArr.length == 0) {
            TokenSet tokenSet = EMPTY;
            if (tokenSet == null) {
                $$$reportNull$$$0(3);
            }
            return tokenSet;
        }
        if (iElementTypeArr.length == 1 && iElementTypeArr[0] == TokenType.WHITE_SPACE) {
            TokenSet tokenSet2 = WHITE_SPACE;
            if (tokenSet2 == null) {
                $$$reportNull$$$0(4);
            }
            return tokenSet2;
        }
        return doCreate(iElementTypeArr);
    }

    private static TokenSet doCreate(IElementType... iElementTypeArr) {
        if (iElementTypeArr == null) {
            $$$reportNull$$$0(5);
        }
        short s = ShortCompanionObject.MAX_VALUE;
        short s2 = 0;
        for (IElementType iElementType : iElementTypeArr) {
            if (iElementType != null) {
                short index = iElementType.getIndex();
                if (s > index) {
                    s = index;
                }
                if (s2 < index) {
                    s2 = index;
                }
            }
        }
        short s3 = (short) (s >> 6);
        TokenSet tokenSet = new TokenSet(s3, s2, null);
        for (IElementType iElementType2 : iElementTypeArr) {
            if (iElementType2 != null) {
                short index2 = iElementType2.getIndex();
                int i = (index2 >> 6) - s3;
                long[] jArr = tokenSet.myWords;
                jArr[i] = jArr[i] | (1 << index2);
            }
        }
        return tokenSet;
    }

    public static TokenSet forAllMatching(IElementType.Predicate predicate) {
        if (predicate == null) {
            $$$reportNull$$$0(7);
        }
        return new TokenSet(ShortCompanionObject.MAX_VALUE, (short) 0, predicate);
    }

    public static TokenSet orSet(TokenSet... tokenSetArr) {
        IElementType.Predicate orPredicate;
        if (tokenSetArr == null) {
            $$$reportNull$$$0(8);
        }
        if (tokenSetArr.length == 0) {
            TokenSet tokenSet = EMPTY;
            if (tokenSet == null) {
                $$$reportNull$$$0(9);
            }
            return tokenSet;
        }
        ArrayList arrayList = new ArrayList();
        ContainerUtil.addIfNotNull(arrayList, tokenSetArr[0].myOrCondition);
        TokenSet tokenSet2 = tokenSetArr[0];
        short s = tokenSet2.myShift;
        short s2 = tokenSet2.myMax;
        for (int i = 1; i < tokenSetArr.length; i++) {
            TokenSet tokenSet3 = tokenSetArr[i];
            short s3 = tokenSet3.myShift;
            if (s > s3) {
                s = s3;
            }
            short s4 = tokenSet3.myMax;
            if (s2 < s4) {
                s2 = s4;
            }
            ContainerUtil.addIfNotNull(arrayList, tokenSet3.myOrCondition);
        }
        if (arrayList.isEmpty()) {
            orPredicate = null;
        } else {
            orPredicate = arrayList.size() == 1 ? (IElementType.Predicate) arrayList.get(0) : new OrPredicate(arrayList);
        }
        TokenSet tokenSet4 = new TokenSet(s, s2, orPredicate);
        for (TokenSet tokenSet5 : tokenSetArr) {
            int i2 = tokenSet5.myShift - tokenSet4.myShift;
            int i3 = 0;
            while (true) {
                long[] jArr = tokenSet5.myWords;
                if (i3 < jArr.length) {
                    long[] jArr2 = tokenSet4.myWords;
                    int i4 = i3 + i2;
                    jArr2[i4] = jArr2[i4] | jArr[i3];
                    i3++;
                }
            }
        }
        return tokenSet4;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x006c  */
    public static TokenSet andSet(final TokenSet tokenSet, final TokenSet tokenSet2) {
        IElementType.Predicate predicate;
        long j;
        if (tokenSet == null) {
            $$$reportNull$$$0(11);
        }
        if (tokenSet2 == null) {
            $$$reportNull$$$0(12);
        }
        ArrayList arrayList = new ArrayList();
        ContainerUtil.addIfNotNull(arrayList, tokenSet.myOrCondition);
        ContainerUtil.addIfNotNull(arrayList, tokenSet2.myOrCondition);
        int i = 0;
        if (arrayList.isEmpty()) {
            predicate = null;
        } else {
            predicate = arrayList.size() == 1 ? (IElementType.Predicate) arrayList.get(0) : new IElementType.Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IElementType.Predicate
                public final boolean matches(IElementType iElementType) {
                    return TokenSet.lambda$andSet$0(this.f$0, tokenSet2, iElementType);
                }
            };
        }
        TokenSet tokenSet3 = new TokenSet((short) Math.min((int) tokenSet.myShift, (int) tokenSet2.myShift), (short) Math.max((int) tokenSet.myMax, (int) tokenSet2.myMax), predicate);
        while (true) {
            long[] jArr = tokenSet3.myWords;
            if (i >= jArr.length) {
                return tokenSet3;
            }
            short s = tokenSet3.myShift;
            int i2 = (s - tokenSet.myShift) + i;
            int i3 = (s - tokenSet2.myShift) + i;
            long j2 = 0;
            if (i2 >= 0) {
                long[] jArr2 = tokenSet.myWords;
                if (i2 < jArr2.length) {
                    j = jArr2[i2];
                } else {
                    j = 0;
                }
            } else {
                j = 0;
            }
            if (i3 >= 0) {
                long[] jArr3 = tokenSet2.myWords;
                if (i3 < jArr3.length) {
                    j2 = jArr3[i3];
                }
            }
            jArr[i] = j & j2;
            i++;
        }
    }

    static /* synthetic */ boolean lambda$andSet$0(TokenSet tokenSet, TokenSet tokenSet2, IElementType iElementType) {
        return ((IElementType.Predicate) Objects.requireNonNull(tokenSet.myOrCondition)).matches(iElementType) && ((IElementType.Predicate) Objects.requireNonNull(tokenSet2.myOrCondition)).matches(iElementType);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x004d  */
    public static TokenSet andNot(final TokenSet tokenSet, final TokenSet tokenSet2) {
        long j;
        if (tokenSet == null) {
            $$$reportNull$$$0(14);
        }
        if (tokenSet2 == null) {
            $$$reportNull$$$0(15);
        }
        TokenSet tokenSet3 = new TokenSet((short) Math.min((int) tokenSet.myShift, (int) tokenSet2.myShift), (short) Math.max((int) tokenSet.myMax, (int) tokenSet2.myMax), tokenSet.myOrCondition == null ? null : new IElementType.Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IElementType.Predicate
            public final boolean matches(IElementType iElementType) {
                return TokenSet.lambda$andNot$1(this.f$0, tokenSet, iElementType);
            }
        });
        int i = 0;
        while (true) {
            long[] jArr = tokenSet3.myWords;
            if (i >= jArr.length) {
                return tokenSet3;
            }
            short s = tokenSet3.myShift;
            int i2 = (s - tokenSet.myShift) + i;
            int i3 = (s - tokenSet2.myShift) + i;
            long j2 = 0;
            if (i2 >= 0) {
                long[] jArr2 = tokenSet.myWords;
                if (i2 < jArr2.length) {
                    j = jArr2[i2];
                } else {
                    j = 0;
                }
            } else {
                j = 0;
            }
            if (i3 >= 0) {
                long[] jArr3 = tokenSet2.myWords;
                if (i3 < jArr3.length) {
                    j2 = jArr3[i3];
                }
            }
            jArr[i] = (~j2) & j;
            i++;
        }
    }

    static /* synthetic */ boolean lambda$andNot$1(TokenSet tokenSet, TokenSet tokenSet2, IElementType iElementType) {
        return !tokenSet.contains(iElementType) && tokenSet2.myOrCondition.matches(iElementType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OrPredicate implements IElementType.Predicate {
        private final IElementType.Predicate[] myComponents;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "t", "org/jetbrains/kotlin/com/intellij/psi/tree/TokenSet$OrPredicate", "matches"));
        }

        OrPredicate(List<IElementType.Predicate> list) {
            this.myComponents = (IElementType.Predicate[]) list.stream().flatMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet$OrPredicate$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return TokenSet.OrPredicate.lambda$new$0((IElementType.Predicate) obj);
                }
            }).distinct().toArray(new IntFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet$OrPredicate$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return TokenSet.OrPredicate.lambda$new$1(i);
                }
            });
        }

        static /* synthetic */ Stream lambda$new$0(IElementType.Predicate predicate) {
            return predicate instanceof OrPredicate ? Arrays.stream(((OrPredicate) predicate).myComponents) : Stream.of(predicate);
        }

        static /* synthetic */ IElementType.Predicate[] lambda$new$1(int i) {
            return new IElementType.Predicate[i];
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IElementType.Predicate
        public boolean matches(IElementType iElementType) {
            if (iElementType == null) {
                $$$reportNull$$$0(0);
            }
            for (IElementType.Predicate predicate : this.myComponents) {
                if (predicate.matches(iElementType)) {
                    return true;
                }
            }
            return false;
        }
    }
}
