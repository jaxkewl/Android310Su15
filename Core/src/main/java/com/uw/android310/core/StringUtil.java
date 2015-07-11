package com.uw.android310.core;

import android.support.annotation.NonNull;

/**
 * Created by DOu on 6/25/15.
 */
public class StringUtil {

    /**
     * Check whether or not a String is null or empty.
     *
     * @return true if the specified String is null or empty, false otherwise.
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static class Search {

        /**
         * Check if specified search query string is valid and appropriate.
         *
         * @param unSanitizedQueryString
         * @return true if query string is valid and appropriate, false otherwise
         */
        public static boolean isQueryStringValidAndAppropriate(@NonNull String unSanitizedQueryString) {
            // Implement String sanitize and filter curse words, inappropriate search results, etc.
            return true;
        }
    }
}
