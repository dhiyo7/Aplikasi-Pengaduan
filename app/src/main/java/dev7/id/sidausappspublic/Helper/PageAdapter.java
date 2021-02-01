package dev7.id.sidausappspublic.Helper;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import dev7.id.sidausappspublic.Fragment.UnVerifFragment;
import dev7.id.sidausappspublic.Fragment.VerifFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    int counttab;

    public PageAdapter(FragmentManager fm, int counttab) {
        super(fm);
        this.counttab = counttab;

    }

    @NonNull
    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0 :
                VerifFragment verifFragment = new VerifFragment();
                return verifFragment;
            case 1 :
                UnVerifFragment unVerifFragment = new UnVerifFragment();
                return unVerifFragment;
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}
