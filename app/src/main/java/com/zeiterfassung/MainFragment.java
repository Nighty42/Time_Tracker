package com.zeiterfassung;

public class MainFragment {
    private static final int count = 2;

    public static int getCount()
    {
        return count;
    }

    public static int getResource(int section_number)
    {
        int resource = 1;

        switch(section_number)
        {
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
