package org.jetbrains.kotlin.resolve.calls.smartcasts;

/* JADX INFO: loaded from: classes6.dex */
public enum Nullability {
    NULL(true, false),
    NOT_NULL(false, true),
    UNKNOWN(true, true),
    IMPOSSIBLE(false, false);

    private final boolean canBeNonNull;
    private final boolean canBeNull;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 15 || i == 16) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 4 || i == 15 || i == 16) ? 3 : 2];
        if (i == 4 || i == 15 || i == 16) {
            objArr[0] = "other";
        } else {
            objArr[0] = "org/jetbrains/kotlin/resolve/calls/smartcasts/Nullability";
        }
        switch (i) {
            case 4:
            case 15:
            case 16:
                objArr[1] = "org/jetbrains/kotlin/resolve/calls/smartcasts/Nullability";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[1] = "refine";
                break;
            case 11:
            case 12:
            case 13:
            case 14:
                objArr[1] = "invert";
                break;
            default:
                objArr[1] = "fromFlags";
                break;
        }
        if (i == 4) {
            objArr[2] = "refine";
        } else if (i == 15) {
            objArr[2] = "and";
        } else if (i == 16) {
            objArr[2] = "or";
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 15 && i != 16) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public static Nullability fromFlags(boolean z, boolean z2) {
        if (!z && !z2) {
            Nullability nullability = IMPOSSIBLE;
            if (nullability == null) {
                $$$reportNull$$$0(0);
            }
            return nullability;
        }
        if (!z && z2) {
            Nullability nullability2 = NOT_NULL;
            if (nullability2 == null) {
                $$$reportNull$$$0(1);
            }
            return nullability2;
        }
        if (z && !z2) {
            Nullability nullability3 = NULL;
            if (nullability3 == null) {
                $$$reportNull$$$0(2);
            }
            return nullability3;
        }
        Nullability nullability4 = UNKNOWN;
        if (nullability4 == null) {
            $$$reportNull$$$0(3);
        }
        return nullability4;
    }

    Nullability(boolean z, boolean z2) {
        this.canBeNull = z;
        this.canBeNonNull = z2;
    }

    public boolean canBeNull() {
        return this.canBeNull;
    }

    public boolean canBeNonNull() {
        return this.canBeNonNull;
    }

    public Nullability refine(Nullability nullability) {
        if (nullability == null) {
            $$$reportNull$$$0(4);
        }
        int i = AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$resolve$calls$smartcasts$Nullability[ordinal()];
        if (i == 1) {
            if (AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$resolve$calls$smartcasts$Nullability[nullability.ordinal()] == 2) {
                Nullability nullability2 = NOT_NULL;
                if (nullability2 == null) {
                    $$$reportNull$$$0(9);
                }
                return nullability2;
            }
            Nullability nullability3 = NOT_NULL;
            if (nullability3 == null) {
                $$$reportNull$$$0(10);
            }
            return nullability3;
        }
        if (i != 2) {
            if (i == 3) {
                if (nullability == null) {
                    $$$reportNull$$$0(5);
                }
                return nullability;
            }
            if (i != 4) {
                throw new IllegalStateException();
            }
            if (nullability == null) {
                $$$reportNull$$$0(6);
            }
            return nullability;
        }
        if (AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$resolve$calls$smartcasts$Nullability[nullability.ordinal()] == 1) {
            Nullability nullability4 = NOT_NULL;
            if (nullability4 == null) {
                $$$reportNull$$$0(7);
            }
            return nullability4;
        }
        Nullability nullability5 = NULL;
        if (nullability5 == null) {
            $$$reportNull$$$0(8);
        }
        return nullability5;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.calls.smartcasts.Nullability$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$resolve$calls$smartcasts$Nullability;

        static {
            int[] iArr = new int[Nullability.values().length];
            $SwitchMap$org$jetbrains$kotlin$resolve$calls$smartcasts$Nullability = iArr;
            try {
                iArr[Nullability.NOT_NULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$calls$smartcasts$Nullability[Nullability.NULL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$calls$smartcasts$Nullability[Nullability.UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$calls$smartcasts$Nullability[Nullability.IMPOSSIBLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public Nullability invert() {
        int i = AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$resolve$calls$smartcasts$Nullability[ordinal()];
        if (i == 1) {
            Nullability nullability = UNKNOWN;
            if (nullability == null) {
                $$$reportNull$$$0(12);
            }
            return nullability;
        }
        if (i == 2) {
            Nullability nullability2 = NOT_NULL;
            if (nullability2 == null) {
                $$$reportNull$$$0(11);
            }
            return nullability2;
        }
        if (i == 3) {
            Nullability nullability3 = UNKNOWN;
            if (nullability3 == null) {
                $$$reportNull$$$0(13);
            }
            return nullability3;
        }
        if (i == 4) {
            Nullability nullability4 = UNKNOWN;
            if (nullability4 == null) {
                $$$reportNull$$$0(14);
            }
            return nullability4;
        }
        throw new IllegalStateException();
    }

    public Nullability and(Nullability nullability) {
        if (nullability == null) {
            $$$reportNull$$$0(15);
        }
        return fromFlags(this.canBeNull && nullability.canBeNull, this.canBeNonNull && nullability.canBeNonNull);
    }

    public Nullability or(Nullability nullability) {
        if (nullability == null) {
            $$$reportNull$$$0(16);
        }
        return fromFlags(this.canBeNull || nullability.canBeNull, this.canBeNonNull || nullability.canBeNonNull);
    }
}
