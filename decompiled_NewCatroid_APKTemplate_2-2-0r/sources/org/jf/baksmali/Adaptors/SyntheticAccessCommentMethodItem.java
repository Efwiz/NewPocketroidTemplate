package org.jf.baksmali.Adaptors;

import java.io.IOException;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.util.SyntheticAccessorResolver;
import org.jf.util.ExceptionWithContext;

/* JADX INFO: loaded from: classes6.dex */
public class SyntheticAccessCommentMethodItem extends MethodItem {
    private final SyntheticAccessorResolver.AccessedMember accessedMember;
    private final ClassDefinition classDef;

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public double getSortOrder() {
        return 99.8d;
    }

    public SyntheticAccessCommentMethodItem(ClassDefinition classDefinition, SyntheticAccessorResolver.AccessedMember accessedMember, int i) {
        super(i);
        this.classDef = classDefinition;
        this.accessedMember = accessedMember;
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write("# ");
        switch (this.accessedMember.accessedMemberType) {
            case 0:
                baksmaliWriter.write("invokes: ");
                break;
            case 1:
                baksmaliWriter.write("getter for: ");
                break;
            case 2:
                baksmaliWriter.write("setter for: ");
                break;
            case 3:
                baksmaliWriter.write("operator++ for: ");
                break;
            case 4:
                baksmaliWriter.write("++operator for: ");
                break;
            case 5:
                baksmaliWriter.write("operator-- for: ");
                break;
            case 6:
                baksmaliWriter.write("--operator for: ");
                break;
            case 7:
                baksmaliWriter.write("+= operator for: ");
                break;
            case 8:
                baksmaliWriter.write("-= operator for: ");
                break;
            case 9:
                baksmaliWriter.write("*= operator for: ");
                break;
            case 10:
                baksmaliWriter.write("/= operator for: ");
                break;
            case 11:
                baksmaliWriter.write("%= operator for: ");
                break;
            case 12:
                baksmaliWriter.write("&= operator for: ");
                break;
            case 13:
                baksmaliWriter.write("|= operator for: ");
                break;
            case 14:
                baksmaliWriter.write("^= operator for: ");
                break;
            case 15:
                baksmaliWriter.write("<<= operator for: ");
                break;
            case 16:
                baksmaliWriter.write(">>= operator for: ");
                break;
            case 17:
                baksmaliWriter.write(">>>= operator for: ");
                break;
            default:
                throw new ExceptionWithContext("Unknown access type: %d", Integer.valueOf(this.accessedMember.accessedMemberType));
        }
        baksmaliWriter.writeReference(this.accessedMember.accessedMember);
        return true;
    }
}
