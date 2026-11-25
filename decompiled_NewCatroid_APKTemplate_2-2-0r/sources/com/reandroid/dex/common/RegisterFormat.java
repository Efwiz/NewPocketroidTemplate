package com.reandroid.dex.common;

/* JADX INFO: loaded from: classes7.dex */
public class RegisterFormat {
    private final int hash;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f236name;
    private final RegisterType[] types;
    public static final RegisterFormat NONE = new RegisterFormat("NONE", new RegisterType[0]);
    public static final RegisterFormat READ = new RegisterFormat("READ", new RegisterType[]{RegisterType.READ});
    public static final RegisterFormat WRITE = new RegisterFormat("WRITE", new RegisterType[]{RegisterType.WRITE});
    public static final RegisterFormat READ_READ = new RegisterFormat("READ_READ", new RegisterType[]{RegisterType.READ, RegisterType.READ});
    public static final RegisterFormat READ_WRITE = new RegisterFormat("READ_WRITE", new RegisterType[]{RegisterType.READ, RegisterType.WRITE});
    public static final RegisterFormat READ_READ_READ = new RegisterFormat("READ_READ_READ", new RegisterType[]{RegisterType.READ, RegisterType.READ, RegisterType.READ});
    public static final RegisterFormat WRITE_READ = new RegisterFormat("WRITE_READ", new RegisterType[]{RegisterType.WRITE, RegisterType.READ});
    public static final RegisterFormat WRITE_READ_READ = new RegisterFormat("WRITE_READ_READ", new RegisterType[]{RegisterType.WRITE, RegisterType.READ, RegisterType.READ});
    public static final RegisterFormat OUT = new RegisterFormat("OUT", new RegisterType[0]) { // from class: com.reandroid.dex.common.RegisterFormat.1
        @Override // com.reandroid.dex.common.RegisterFormat
        public boolean isOut() {
            return true;
        }

        @Override // com.reandroid.dex.common.RegisterFormat
        public RegisterType get(int i) {
            return RegisterType.READ;
        }
    };
    public static final RegisterFormat OUT_RANGE = new RegisterFormat("OUT_RANGE", new RegisterType[]{RegisterType.READ, RegisterType.READ}) { // from class: com.reandroid.dex.common.RegisterFormat.2
        @Override // com.reandroid.dex.common.RegisterFormat
        public boolean isOut() {
            return true;
        }

        @Override // com.reandroid.dex.common.RegisterFormat
        public boolean isRange() {
            return true;
        }
    };

    public boolean equals(Object obj) {
        return obj == this;
    }

    public boolean isOut() {
        return false;
    }

    public boolean isRange() {
        return false;
    }

    private RegisterFormat(String str, RegisterType[] registerTypeArr) {
        this.f236name = str;
        this.types = registerTypeArr;
        this.hash = (registerTypeArr != null ? 1 + (registerTypeArr.length * 31) : 1) + (str.hashCode() * 31);
    }

    public RegisterType get(int i) {
        if (isOut()) {
            return RegisterType.READ;
        }
        return this.types[i];
    }

    public int size() {
        RegisterType[] registerTypeArr = this.types;
        if (registerTypeArr != null) {
            return registerTypeArr.length;
        }
        return 0;
    }

    public int hashCode() {
        return this.hash;
    }

    public String toString() {
        return this.f236name;
    }
}
