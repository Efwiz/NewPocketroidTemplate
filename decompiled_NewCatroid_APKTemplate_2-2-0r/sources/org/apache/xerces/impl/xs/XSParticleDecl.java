package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSParticle;
import org.apache.xerces.xs.XSTerm;

/* JADX INFO: loaded from: classes13.dex */
public class XSParticleDecl implements XSParticle {
    public static final short PARTICLE_ELEMENT = 1;
    public static final short PARTICLE_EMPTY = 0;
    public static final short PARTICLE_MODELGROUP = 3;
    public static final short PARTICLE_ONE_OR_MORE = 6;
    public static final short PARTICLE_WILDCARD = 2;
    public static final short PARTICLE_ZERO_OR_MORE = 4;
    public static final short PARTICLE_ZERO_OR_ONE = 5;
    public short fType = 0;
    public XSTerm fValue = null;
    public int fMinOccurs = 1;
    public int fMaxOccurs = 1;
    public XSObjectList fAnnotations = null;
    private String fDescription = null;

    void appendParticle(StringBuffer stringBuffer) {
        String string;
        short s = this.fType;
        if (s != 0) {
            if (s != 1) {
                if (s == 2) {
                    stringBuffer.append('(');
                    stringBuffer.append(this.fValue.toString());
                    stringBuffer.append(')');
                    return;
                } else if (s != 3) {
                    return;
                }
            }
            string = this.fValue.toString();
        } else {
            string = "EMPTY";
        }
        stringBuffer.append(string);
    }

    public boolean emptiable() {
        return minEffectiveTotalRange() == 0;
    }

    @Override // org.apache.xerces.xs.XSParticle
    public XSObjectList getAnnotations() {
        XSObjectList xSObjectList = this.fAnnotations;
        return xSObjectList != null ? xSObjectList : XSObjectListImpl.EMPTY_LIST;
    }

    @Override // org.apache.xerces.xs.XSParticle
    public int getMaxOccurs() {
        return this.fMaxOccurs;
    }

    @Override // org.apache.xerces.xs.XSParticle
    public boolean getMaxOccursUnbounded() {
        return this.fMaxOccurs == -1;
    }

    @Override // org.apache.xerces.xs.XSParticle
    public int getMinOccurs() {
        return this.fMinOccurs;
    }

    @Override // org.apache.xerces.xs.XSObject
    public String getName() {
        return null;
    }

    @Override // org.apache.xerces.xs.XSObject
    public String getNamespace() {
        return null;
    }

    @Override // org.apache.xerces.xs.XSObject
    public XSNamespaceItem getNamespaceItem() {
        return null;
    }

    @Override // org.apache.xerces.xs.XSParticle
    public XSTerm getTerm() {
        return this.fValue;
    }

    @Override // org.apache.xerces.xs.XSObject
    public short getType() {
        return (short) 8;
    }

    public boolean isEmpty() {
        short s = this.fType;
        if (s == 0) {
            return true;
        }
        if (s == 1 || s == 2) {
            return false;
        }
        return ((XSModelGroupImpl) this.fValue).isEmpty();
    }

    public XSParticleDecl makeClone() {
        XSParticleDecl xSParticleDecl = new XSParticleDecl();
        xSParticleDecl.fType = this.fType;
        xSParticleDecl.fMinOccurs = this.fMinOccurs;
        xSParticleDecl.fMaxOccurs = this.fMaxOccurs;
        xSParticleDecl.fDescription = this.fDescription;
        xSParticleDecl.fValue = this.fValue;
        xSParticleDecl.fAnnotations = this.fAnnotations;
        return xSParticleDecl;
    }

    public int maxEffectiveTotalRange() {
        short s = this.fType;
        if (s == 0) {
            return 0;
        }
        if (s != 3) {
            return this.fMaxOccurs;
        }
        int iMaxEffectiveTotalRange = ((XSModelGroupImpl) this.fValue).maxEffectiveTotalRange();
        if (iMaxEffectiveTotalRange == -1) {
            return -1;
        }
        if (iMaxEffectiveTotalRange == 0 || this.fMaxOccurs != -1) {
            return iMaxEffectiveTotalRange * this.fMaxOccurs;
        }
        return -1;
    }

    public int minEffectiveTotalRange() {
        short s = this.fType;
        if (s == 0) {
            return 0;
        }
        return s == 3 ? ((XSModelGroupImpl) this.fValue).minEffectiveTotalRange() * this.fMinOccurs : this.fMinOccurs;
    }

    public void reset() {
        this.fType = (short) 0;
        this.fValue = null;
        this.fMinOccurs = 1;
        this.fMaxOccurs = 1;
        this.fDescription = null;
        this.fAnnotations = null;
    }

    public String toString() {
        if (this.fDescription == null) {
            StringBuffer stringBuffer = new StringBuffer();
            appendParticle(stringBuffer);
            int i = this.fMinOccurs;
            if ((i != 0 || this.fMaxOccurs != 0) && (i != 1 || this.fMaxOccurs != 1)) {
                stringBuffer.append('{').append(this.fMinOccurs);
                int i2 = this.fMaxOccurs;
                if (i2 == -1) {
                    stringBuffer.append("-UNBOUNDED");
                } else if (this.fMinOccurs != i2) {
                    stringBuffer.append('-').append(this.fMaxOccurs);
                }
                stringBuffer.append('}');
            }
            this.fDescription = stringBuffer.toString();
        }
        return this.fDescription;
    }
}
