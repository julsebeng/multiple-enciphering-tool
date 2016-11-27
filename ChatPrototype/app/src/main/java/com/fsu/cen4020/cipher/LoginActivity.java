package com.fsu.cen4020.cipher;

import com.parse.ui.ParseLoginDispatchActivity;

/**
 * Created by Thai Flowers on 11/22/2016.
 */

public class LoginActivity extends ParseLoginDispatchActivity {
        @Override
        protected Class<?> getTargetClass() {
            return ChatActivity.class;
        }
}
