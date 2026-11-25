package com.esotericsoftware.asm;

/* JADX INFO: loaded from: classes8.dex */
final class Item {
    int a;
    int b;
    int c;
    long d;
    String g;
    String h;
    String i;
    int j;
    Item k;

    Item() {
    }

    Item(int i) {
        this.a = i;
    }

    Item(int i, Item item) {
        this.a = i;
        this.b = item.b;
        this.c = item.c;
        this.d = item.d;
        this.g = item.g;
        this.h = item.h;
        this.i = item.i;
        this.j = item.j;
    }

    void a(double d) {
        this.b = 6;
        this.d = Double.doubleToRawLongBits(d);
        this.j = Integer.MAX_VALUE & (this.b + ((int) d));
    }

    void a(float f) {
        this.b = 4;
        this.c = Float.floatToRawIntBits(f);
        this.j = Integer.MAX_VALUE & (this.b + ((int) f));
    }

    void a(int i) {
        this.b = 3;
        this.c = i;
        this.j = (3 + i) & Integer.MAX_VALUE;
    }

    void a(int i, int i2) {
        this.b = 33;
        this.c = i;
        this.j = i2;
    }

    void a(int i, String str, String str2, String str3) {
        int iHashCode;
        int iHashCode2;
        int iHashCode3;
        this.b = i;
        this.g = str;
        this.h = str2;
        this.i = str3;
        if (i == 1) {
            iHashCode = str.hashCode();
        } else {
            if (i != 12) {
                if (i != 16 && i != 30) {
                    if (i == 7) {
                        this.c = 0;
                    } else if (i != 8) {
                        iHashCode2 = str.hashCode() * str2.hashCode();
                        iHashCode3 = str3.hashCode();
                    }
                }
                iHashCode = str.hashCode();
            } else {
                iHashCode2 = str.hashCode();
                iHashCode3 = str2.hashCode();
            }
            iHashCode = iHashCode2 * iHashCode3;
        }
        this.j = (i + iHashCode) & Integer.MAX_VALUE;
    }

    void a(long j) {
        this.b = 5;
        this.d = j;
        this.j = Integer.MAX_VALUE & (5 + ((int) j));
    }

    void a(String str, String str2, int i) {
        this.b = 18;
        this.d = i;
        this.g = str;
        this.h = str2;
        this.j = Integer.MAX_VALUE & ((i * str.hashCode() * this.h.hashCode()) + 18);
    }

    /* JADX WARN: Code duplicated, block: B:28:0x004c  */
    /* JADX WARN: Code duplicated, block: B:31:0x0055  */
    /* JADX WARN: Code duplicated, block: B:58:? A[RETURN, SYNTHETIC] */
    boolean a(Item item) {
        int i = this.b;
        if (i != 1) {
            if (i == 12) {
                return item.g.equals(this.g) && item.h.equals(this.h);
            }
            if (i != 16) {
                if (i == 18) {
                    return item.d == this.d && item.g.equals(this.g) && item.h.equals(this.h);
                }
                switch (i) {
                    case 3:
                    case 4:
                        return item.c == this.c;
                    case 5:
                    case 6:
                        if (item.d == this.d) {
                            return true;
                        }
                        return false;
                    case 7:
                    case 8:
                        break;
                    default:
                        switch (i) {
                            case 30:
                                break;
                            case 31:
                                return item.c == this.c && item.g.equals(this.g);
                            case 32:
                                if (item.d == this.d) {
                                    return true;
                                }
                                return false;
                            default:
                                return item.g.equals(this.g) && item.h.equals(this.h) && item.i.equals(this.i);
                        }
                        break;
                }
            }
        }
        return item.g.equals(this.g);
    }
}
