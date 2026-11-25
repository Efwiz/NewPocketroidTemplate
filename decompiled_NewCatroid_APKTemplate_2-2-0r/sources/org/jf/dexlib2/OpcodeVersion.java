package org.jf.dexlib2;

import java.util.Collection;

/* JADX INFO: loaded from: classes6.dex */
public class OpcodeVersion {
    public static final VersionRange ALL;
    public static final VersionRange NONE = new VersionRange(1, 0) { // from class: org.jf.dexlib2.OpcodeVersion.2
        @Override // org.jf.dexlib2.OpcodeVersion.VersionRange
        public boolean contains(int i) {
            return false;
        }

        @Override // org.jf.dexlib2.OpcodeVersion.VersionRange
        public boolean isEmpty() {
            return true;
        }

        @Override // org.jf.dexlib2.OpcodeVersion.VersionRange
        public void setMax(Integer num) {
        }

        @Override // org.jf.dexlib2.OpcodeVersion.VersionRange
        public void setMin(Integer num) {
        }
    };

    public static class VersionConstraint {
        public final VersionRange apiRange;
        public final VersionRange artVersionRange;
        public final int opcodeValue;

        public VersionConstraint(VersionRange versionRange, VersionRange versionRange2, int i) {
            this.apiRange = versionRange;
            this.artVersionRange = versionRange2;
            this.opcodeValue = i;
        }
    }

    public static VersionRange atMost(int i) {
        return new VersionRange(null, Integer.valueOf(i));
    }

    public static VersionRange openClosed(int i, int i2) {
        return new VersionRange(Integer.valueOf(i + 1), Integer.valueOf(i2));
    }

    public static VersionRange closedOpen(int i, int i2) {
        return new VersionRange(Integer.valueOf(i), Integer.valueOf(i2 - 1));
    }

    public static VersionRange closed(int i, int i2) {
        return new VersionRange(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static VersionRange open(int i, int i2) {
        return new VersionRange(Integer.valueOf(i + 1), Integer.valueOf(i2 - 1));
    }

    public static VersionRange atLeast(int i) {
        return new VersionRange(Integer.valueOf(i), null);
    }

    static {
        Integer num = null;
        ALL = new VersionRange(num, num) { // from class: org.jf.dexlib2.OpcodeVersion.1
            @Override // org.jf.dexlib2.OpcodeVersion.VersionRange
            public boolean contains(int i) {
                return true;
            }

            @Override // org.jf.dexlib2.OpcodeVersion.VersionRange
            public boolean isEmpty() {
                return false;
            }

            @Override // org.jf.dexlib2.OpcodeVersion.VersionRange
            public void setMax(Integer num2) {
            }

            @Override // org.jf.dexlib2.OpcodeVersion.VersionRange
            public void setMin(Integer num2) {
            }
        };
    }

    public static class VersionRange {
        private Integer max;
        private Integer min;

        public VersionRange(Integer num, Integer num2) {
            this.min = num;
            this.max = num2;
        }

        public boolean isEmpty() {
            Integer num = this.min;
            if (num == null && this.max == null) {
                return false;
            }
            if (num == null || this.max == null) {
                return num == null && this.max.intValue() <= 0;
            }
            return num.intValue() > this.max.intValue();
        }

        public boolean contains(int i) {
            Integer num = this.min;
            if (num != null && i < num.intValue()) {
                return false;
            }
            Integer num2 = this.max;
            return num2 == null || i <= num2.intValue();
        }

        public boolean isAll() {
            return this.min == null && this.max == null;
        }

        public Integer getMin() {
            return this.min;
        }

        public void setMin(Integer num) {
            this.min = num;
        }

        public Integer getMax() {
            return this.max;
        }

        public void setMax(Integer num) {
            this.max = num;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("(");
            Integer num = this.min;
            if (num == null) {
                sb.append("-∞");
            } else {
                sb.append(num);
            }
            sb.append(" .. ");
            Integer num2 = this.max;
            if (num2 == null) {
                sb.append("+∞");
            } else {
                sb.append(num2);
            }
            sb.append(')');
            return sb.toString();
        }
    }

    public static class VersionRangeMap {
        private final VersionValue[] versionValues;

        public VersionRangeMap(VersionValue[] versionValueArr) {
            this.versionValues = versionValueArr;
        }

        public VersionRangeMap(Collection<VersionValue> collection) {
            this.versionValues = (VersionValue[]) collection.toArray(new VersionValue[0]);
        }

        public Short get(int i) {
            VersionValue versionValue = getVersionValue(i);
            if (versionValue != null) {
                return Short.valueOf(versionValue.value);
            }
            return null;
        }

        public VersionValue getVersionValue(int i) {
            for (VersionValue versionValue : this.versionValues) {
                if (versionValue.contains(i)) {
                    return versionValue;
                }
            }
            return null;
        }
    }

    public static class VersionValue {
        public short value;
        public final VersionRange versionRange;

        public VersionValue(VersionRange versionRange, short s) {
            this.versionRange = versionRange;
            this.value = s;
        }

        public boolean contains(int i) {
            return this.versionRange.contains(i);
        }

        public String toString() {
            return this.versionRange + " = " + ((int) this.value);
        }
    }
}
