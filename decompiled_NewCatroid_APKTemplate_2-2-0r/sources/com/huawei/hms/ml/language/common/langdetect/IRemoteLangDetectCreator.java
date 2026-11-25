package com.huawei.hms.ml.language.common.langdetect;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteLangDetectCreator extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.ml.language.common.langdetect.IRemoteLangDetectCreator";

    public static class Default implements IRemoteLangDetectCreator {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.ml.language.common.langdetect.IRemoteLangDetectCreator
        public IRemoteLangDetectDelegate newRemoteLangDetectDelegate() throws RemoteException {
            return null;
        }
    }

    IRemoteLangDetectDelegate newRemoteLangDetectDelegate() throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteLangDetectCreator {
        static final int TRANSACTION_newRemoteLangDetectDelegate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteLangDetectCreator.DESCRIPTOR);
        }

        public static IRemoteLangDetectCreator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteLangDetectCreator.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteLangDetectCreator)) {
                return (IRemoteLangDetectCreator) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteLangDetectCreator.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteLangDetectCreator.DESCRIPTOR);
                IRemoteLangDetectDelegate iRemoteLangDetectDelegateNewRemoteLangDetectDelegate = newRemoteLangDetectDelegate();
                parcel2.writeNoException();
                parcel2.writeStrongBinder(iRemoteLangDetectDelegateNewRemoteLangDetectDelegate != null ? iRemoteLangDetectDelegateNewRemoteLangDetectDelegate.asBinder() : null);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteLangDetectCreator {
            public static IRemoteLangDetectCreator sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteLangDetectCreator.DESCRIPTOR;
            }

            @Override // com.huawei.hms.ml.language.common.langdetect.IRemoteLangDetectCreator
            public IRemoteLangDetectDelegate newRemoteLangDetectDelegate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteLangDetectCreator.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().newRemoteLangDetectDelegate();
                    }
                    parcelObtain2.readException();
                    return IRemoteLangDetectDelegate.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteLangDetectCreator iRemoteLangDetectCreator) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteLangDetectCreator == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteLangDetectCreator;
            return true;
        }

        public static IRemoteLangDetectCreator getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
