package com.huawei.hms.ml.language.common.translate;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteTranslateCreator extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.ml.language.common.translate.IRemoteTranslateCreator";

    public static class Default implements IRemoteTranslateCreator {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.ml.language.common.translate.IRemoteTranslateCreator
        public IRemoteTranslateDelegate newRemoteTranslateDelegate() throws RemoteException {
            return null;
        }
    }

    IRemoteTranslateDelegate newRemoteTranslateDelegate() throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteTranslateCreator {
        static final int TRANSACTION_newRemoteTranslateDelegate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteTranslateCreator.DESCRIPTOR);
        }

        public static IRemoteTranslateCreator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteTranslateCreator.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteTranslateCreator)) {
                return (IRemoteTranslateCreator) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteTranslateCreator.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteTranslateCreator.DESCRIPTOR);
                IRemoteTranslateDelegate iRemoteTranslateDelegateNewRemoteTranslateDelegate = newRemoteTranslateDelegate();
                parcel2.writeNoException();
                parcel2.writeStrongBinder(iRemoteTranslateDelegateNewRemoteTranslateDelegate != null ? iRemoteTranslateDelegateNewRemoteTranslateDelegate.asBinder() : null);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteTranslateCreator {
            public static IRemoteTranslateCreator sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteTranslateCreator.DESCRIPTOR;
            }

            @Override // com.huawei.hms.ml.language.common.translate.IRemoteTranslateCreator
            public IRemoteTranslateDelegate newRemoteTranslateDelegate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTranslateCreator.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().newRemoteTranslateDelegate();
                    }
                    parcelObtain2.readException();
                    return IRemoteTranslateDelegate.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteTranslateCreator iRemoteTranslateCreator) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteTranslateCreator == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteTranslateCreator;
            return true;
        }

        public static IRemoteTranslateCreator getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
