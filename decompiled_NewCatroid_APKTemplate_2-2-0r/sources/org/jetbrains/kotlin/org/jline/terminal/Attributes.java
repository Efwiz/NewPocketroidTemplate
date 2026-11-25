package org.jetbrains.kotlin.org.jline.terminal;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: classes4.dex */
public class Attributes {
    final EnumSet<InputFlag> iflag = EnumSet.noneOf(InputFlag.class);
    final EnumSet<OutputFlag> oflag = EnumSet.noneOf(OutputFlag.class);
    final EnumSet<ControlFlag> cflag = EnumSet.noneOf(ControlFlag.class);
    final EnumSet<LocalFlag> lflag = EnumSet.noneOf(LocalFlag.class);
    final EnumMap<ControlChar, Integer> cchars = new EnumMap<>(ControlChar.class);

    public enum ControlChar {
        VEOF,
        VEOL,
        VEOL2,
        VERASE,
        VWERASE,
        VKILL,
        VREPRINT,
        VINTR,
        VQUIT,
        VSUSP,
        VDSUSP,
        VSTART,
        VSTOP,
        VLNEXT,
        VDISCARD,
        VMIN,
        VTIME,
        VSTATUS
    }

    public enum ControlFlag {
        CIGNORE,
        CS5,
        CS6,
        CS7,
        CS8,
        CSTOPB,
        CREAD,
        PARENB,
        PARODD,
        HUPCL,
        CLOCAL,
        CCTS_OFLOW,
        CRTS_IFLOW,
        CDTR_IFLOW,
        CDSR_OFLOW,
        CCAR_OFLOW
    }

    public enum InputFlag {
        IGNBRK,
        BRKINT,
        IGNPAR,
        PARMRK,
        INPCK,
        ISTRIP,
        INLCR,
        IGNCR,
        ICRNL,
        IXON,
        IXOFF,
        IXANY,
        IMAXBEL,
        IUTF8
    }

    public enum LocalFlag {
        ECHOKE,
        ECHOE,
        ECHOK,
        ECHO,
        ECHONL,
        ECHOPRT,
        ECHOCTL,
        ISIG,
        ICANON,
        ALTWERASE,
        IEXTEN,
        EXTPROC,
        TOSTOP,
        FLUSHO,
        NOKERNINFO,
        PENDIN,
        NOFLSH
    }

    public enum OutputFlag {
        OPOST,
        ONLCR,
        OXTABS,
        ONOEOT,
        OCRNL,
        ONOCR,
        ONLRET,
        OFILL,
        NLDLY,
        TABDLY,
        CRDLY,
        FFDLY,
        BSDLY,
        VTDLY,
        OFDEL
    }

    public Attributes() {
    }

    public Attributes(Attributes attributes) {
        copy(attributes);
    }

    public EnumSet<InputFlag> getInputFlags() {
        return this.iflag;
    }

    public void setInputFlags(EnumSet<InputFlag> enumSet) {
        this.iflag.clear();
        this.iflag.addAll(enumSet);
    }

    public boolean getInputFlag(InputFlag inputFlag) {
        return this.iflag.contains(inputFlag);
    }

    public void setInputFlags(EnumSet<InputFlag> enumSet, boolean z) {
        if (z) {
            this.iflag.addAll(enumSet);
        } else {
            this.iflag.removeAll(enumSet);
        }
    }

    public void setInputFlag(InputFlag inputFlag, boolean z) {
        if (z) {
            this.iflag.add(inputFlag);
        } else {
            this.iflag.remove(inputFlag);
        }
    }

    public EnumSet<OutputFlag> getOutputFlags() {
        return this.oflag;
    }

    public void setOutputFlags(EnumSet<OutputFlag> enumSet) {
        this.oflag.clear();
        this.oflag.addAll(enumSet);
    }

    public boolean getOutputFlag(OutputFlag outputFlag) {
        return this.oflag.contains(outputFlag);
    }

    public void setOutputFlag(OutputFlag outputFlag, boolean z) {
        if (z) {
            this.oflag.add(outputFlag);
        } else {
            this.oflag.remove(outputFlag);
        }
    }

    public EnumSet<ControlFlag> getControlFlags() {
        return this.cflag;
    }

    public void setControlFlags(EnumSet<ControlFlag> enumSet) {
        this.cflag.clear();
        this.cflag.addAll(enumSet);
    }

    public boolean getControlFlag(ControlFlag controlFlag) {
        return this.cflag.contains(controlFlag);
    }

    public void setControlFlag(ControlFlag controlFlag, boolean z) {
        if (z) {
            this.cflag.add(controlFlag);
        } else {
            this.cflag.remove(controlFlag);
        }
    }

    public EnumSet<LocalFlag> getLocalFlags() {
        return this.lflag;
    }

    public void setLocalFlags(EnumSet<LocalFlag> enumSet) {
        this.lflag.clear();
        this.lflag.addAll(enumSet);
    }

    public boolean getLocalFlag(LocalFlag localFlag) {
        return this.lflag.contains(localFlag);
    }

    public void setLocalFlags(EnumSet<LocalFlag> enumSet, boolean z) {
        if (z) {
            this.lflag.addAll(enumSet);
        } else {
            this.lflag.removeAll(enumSet);
        }
    }

    public void setLocalFlag(LocalFlag localFlag, boolean z) {
        if (z) {
            this.lflag.add(localFlag);
        } else {
            this.lflag.remove(localFlag);
        }
    }

    public EnumMap<ControlChar, Integer> getControlChars() {
        return this.cchars;
    }

    public void setControlChars(EnumMap<ControlChar, Integer> enumMap) {
        this.cchars.clear();
        this.cchars.putAll(enumMap);
    }

    public int getControlChar(ControlChar controlChar) {
        return ((Integer) this.cchars.getOrDefault(controlChar, -1)).intValue();
    }

    public void setControlChar(ControlChar controlChar, int i) {
        this.cchars.put(controlChar, Integer.valueOf(i));
    }

    public void copy(Attributes attributes) {
        setControlFlags(attributes.getControlFlags());
        setInputFlags(attributes.getInputFlags());
        setLocalFlags(attributes.getLocalFlags());
        setOutputFlags(attributes.getOutputFlags());
        setControlChars(attributes.getControlChars());
    }

    public String toString() {
        return "Attributes[lflags: " + append(this.lflag) + ", iflags: " + append(this.iflag) + ", oflags: " + append(this.oflag) + ", cflags: " + append(this.cflag) + ", cchars: " + append(EnumSet.allOf(ControlChar.class), new Function() { // from class: org.jetbrains.kotlin.org.jline.terminal.Attributes$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.display((Attributes.ControlChar) obj);
            }
        }) + "]";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String display(ControlChar controlChar) {
        String string;
        int controlChar2 = getControlChar(controlChar);
        if (controlChar == ControlChar.VMIN || controlChar == ControlChar.VTIME) {
            string = Integer.toString(controlChar2);
        } else if (controlChar2 < 0) {
            string = "<undef>";
        } else if (controlChar2 < 32) {
            string = "^" + ((char) (controlChar2 + 64));
        } else if (controlChar2 == 127) {
            string = "^?";
        } else if (controlChar2 >= 128) {
            string = String.format("\\u%04x", Integer.valueOf(controlChar2));
        } else {
            string = String.valueOf((char) controlChar2);
        }
        return controlChar.name().toLowerCase().substring(1) + ContainerUtils.KEY_VALUE_DELIMITER + string;
    }

    private <T extends Enum<T>> String append(EnumSet<T> enumSet) {
        return append(enumSet, new Function() { // from class: org.jetbrains.kotlin.org.jline.terminal.Attributes$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Enum) obj).name().toLowerCase();
            }
        });
    }

    private <T extends Enum<T>> String append(EnumSet<T> enumSet, Function<T, String> function) {
        return (String) enumSet.stream().map(function).collect(Collectors.joining(" "));
    }
}
