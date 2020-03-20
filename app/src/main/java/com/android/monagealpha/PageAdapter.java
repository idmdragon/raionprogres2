package com.android.monagealpha;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {
    int counttab;

    public PageAdapter(FragmentManager fm, int counttab) {
        super(fm);
        this.counttab=counttab;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 1 : KategoriPengeluaran kpl = new KategoriPengeluaran();
            return kpl;
            case 2 : KategoriPemasukan kpm= new KategoriPemasukan();
            return kpm;

        default : return null;
    }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
