package com.zeiterfassung.fragment;

import com.zeiterfassung.R;

class MainFragment {
    private static final int count = 2;

    static int getCount() {
        return count;
    }

    static int getResource(int section_number) {
        int resource;

        switch (section_number) {
            case 2:
                resource = R.layout.fragment_main_show;
                break;
            default:
                resource = R.layout.fragment_main_add;
                break;
        }

        return resource;
    }

}
