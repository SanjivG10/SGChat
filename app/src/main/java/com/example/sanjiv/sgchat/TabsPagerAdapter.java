package com.example.sanjiv.sgchat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sanjiv on 12/15/17.
 */

class TabsPagerAdapter extends FragmentPagerAdapter {
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //managing position of tabs
        switch (position)
        {
            case 0:
                RequestFragment requestFragment = new RequestFragment();
                return requestFragment;
            case 1:
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;
            case 2:
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        //no of tabs item
        return 3;
    }

    public CharSequence getPageTitle(int position)
    {
            switch (position)
            {
                case 0:
                    return "REQUESTS";
                case 1:
                    return "CHATS";
                case 2:
                    return "FRIENDS";
                default:
                    return null;

            }
    }

}
