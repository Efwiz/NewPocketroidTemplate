package org.jetbrains.kotlin.com.intellij.openapi.util;

import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: loaded from: classes3.dex */
public final class Conditions {

    @Deprecated
    public static final Condition<Object> TRUE = alwaysTrue();

    /* JADX WARN: Code duplicated, block: B:50:0x0082  */
    /* JADX WARN: Code duplicated, block: B:51:0x0087  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 5 || i == 7 || i == 15 || i == 28 || i == 12 || i == 13 || i == 17 || i == 18 || i == 21 || i == 22 || i == 25 || i == 26) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 3 || i == 5 || i == 7 || i == 15 || i == 28 || i == 12 || i == 13 || i == 17 || i == 18 || i == 21 || i == 22 || i == 25 || i == 26) ? 3 : 2];
        if (i == 3 || i == 5 || i == 7) {
            objArr[0] = "clazz";
        } else if (i == 15 || i == 28) {
            objArr[0] = "c";
        } else if (i == 12 || i == 13) {
            objArr[0] = "options";
        } else if (i == 17) {
            objArr[0] = "c1";
        } else if (i == 18) {
            objArr[0] = "c2";
        } else if (i == 21) {
            objArr[0] = "c1";
        } else if (i == 22) {
            objArr[0] = "c2";
        } else if (i == 25) {
            objArr[0] = "fun";
        } else if (i != 26) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/Conditions";
        } else {
            objArr[0] = "condition";
        }
        switch (i) {
            case 1:
                objArr[1] = "alwaysFalse";
                break;
            case 2:
                objArr[1] = "notNull";
                break;
            case 3:
            case 5:
            case 7:
            case 12:
            case 13:
            case 15:
            case 17:
            case 18:
            case 21:
            case 22:
            case 25:
            case 26:
            case 28:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/Conditions";
                break;
            case 4:
            case 9:
                objArr[1] = "instanceOf";
                break;
            case 6:
                objArr[1] = "notInstanceOf";
                break;
            case 8:
                objArr[1] = "assignableTo";
                break;
            case 10:
                objArr[1] = "equalTo";
                break;
            case 11:
                objArr[1] = "notEqualTo";
                break;
            case 14:
                objArr[1] = "oneOf";
                break;
            case 16:
                objArr[1] = "not";
                break;
            case 19:
            case 20:
                objArr[1] = "and";
                break;
            case 23:
            case 24:
                objArr[1] = "or";
                break;
            case 27:
                objArr[1] = "compose";
                break;
            default:
                objArr[1] = "alwaysTrue";
                break;
        }
        if (i == 3) {
            objArr[2] = "instanceOf";
        } else if (i == 5) {
            objArr[2] = "notInstanceOf";
        } else if (i == 7) {
            objArr[2] = "assignableTo";
        } else if (i == 15) {
            objArr[2] = "not";
        } else if (i == 28) {
            objArr[2] = "cached";
        } else if (i == 12 || i == 13) {
            objArr[2] = "oneOf";
        } else if (i == 17 || i == 18) {
            objArr[2] = "and";
        } else if (i == 21 || i == 22) {
            objArr[2] = "or";
        } else if (i == 25 || i == 26) {
            objArr[2] = "compose";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5 && i != 7 && i != 15 && i != 28 && i != 12 && i != 13 && i != 17 && i != 18 && i != 21 && i != 22 && i != 25 && i != 26) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public static <T> Condition<T> alwaysTrue() {
        Condition<T> condition = (Condition<T>) Condition.TRUE;
        if (condition == null) {
            $$$reportNull$$$0(0);
        }
        return condition;
    }

    public static <T> Condition<T> alwaysFalse() {
        Condition<T> condition = (Condition<T>) Condition.FALSE;
        if (condition == null) {
            $$$reportNull$$$0(1);
        }
        return condition;
    }

    public static <T> Condition<T> notNull() {
        Condition<T> condition = (Condition<T>) Condition.NOT_NULL;
        if (condition == null) {
            $$$reportNull$$$0(2);
        }
        return condition;
    }

    public static <T> Condition<T> instanceOf(final Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(3);
        }
        return new Condition() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.Conditions$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return cls.isInstance(obj);
            }
        };
    }

    public static <T> Condition<T> not(Condition<? super T> condition) {
        if (condition == null) {
            $$$reportNull$$$0(15);
        }
        if (condition == alwaysTrue()) {
            return alwaysFalse();
        }
        if (condition == alwaysFalse()) {
            return alwaysTrue();
        }
        if (condition instanceof Not) {
            Condition<? super T> condition2 = ((Not) condition).c;
            if (condition2 == null) {
                $$$reportNull$$$0(16);
            }
            return condition2;
        }
        return new Not(condition);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> Condition<T> and(Condition<? super T> condition, Condition<? super T> condition2) {
        if (condition == 0) {
            $$$reportNull$$$0(17);
        }
        if (condition2 == 0) {
            $$$reportNull$$$0(18);
        }
        if (condition == alwaysTrue() || condition2 == alwaysFalse()) {
            if (condition2 == 0) {
                $$$reportNull$$$0(19);
            }
            return condition2;
        }
        if (condition2 != alwaysTrue() && condition != alwaysFalse()) {
            return new And(condition, condition2);
        }
        if (condition == 0) {
            $$$reportNull$$$0(20);
        }
        return condition;
    }

    public static <A, B> Condition<A> compose(final Function<? super A, B> function, final Condition<? super B> condition) {
        if (function == null) {
            $$$reportNull$$$0(25);
        }
        if (condition == null) {
            $$$reportNull$$$0(26);
        }
        return new Condition() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.Conditions$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return condition.value(function.fun(obj));
            }
        };
    }

    private static class Not<T> implements Condition<T> {
        final Condition<? super T> c;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "c", "org/jetbrains/kotlin/com/intellij/openapi/util/Conditions$Not", "<init>"));
        }

        Not(Condition<? super T> condition) {
            if (condition == null) {
                $$$reportNull$$$0(0);
            }
            this.c = condition;
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
        public boolean value(T t) {
            return !this.c.value(t);
        }
    }

    private static class And<T> implements Condition<T> {
        final Condition<? super T> c1;
        final Condition<? super T> c2;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "c1";
            } else {
                objArr[0] = "c2";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/Conditions$And";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        And(Condition<? super T> condition, Condition<? super T> condition2) {
            if (condition == null) {
                $$$reportNull$$$0(0);
            }
            if (condition2 == null) {
                $$$reportNull$$$0(1);
            }
            this.c1 = condition;
            this.c2 = condition2;
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
        public boolean value(T t) {
            return this.c1.value(t) && this.c2.value(t);
        }
    }
}
