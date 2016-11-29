package com.fsu.cen4020.cipher;

import android.content.Intent;

import com.parse.ui.ParseLoginBuilder;
import com.parse.ui.ParseLoginDispatchActivity;

/**
 * Created by Thai Flowers on 11/22/2016.
 */

public class LoginActivity extends ParseLoginDispatchActivity {
        @Override
        protected Class<?> getTargetClass() {
            return ChatActivity.class;
        }

    /**
     * Override by Julian 11/28/16
     */
        @Override
        protected Intent getParseLoginIntent() {
            ParseLoginBuilder builder = new ParseLoginBuilder(this);
            builder.setAppLogo(R.mipmap.ic_launcher);
            return builder.build();
        }

}
