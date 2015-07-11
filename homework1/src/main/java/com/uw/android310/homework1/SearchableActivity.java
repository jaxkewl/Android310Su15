package com.uw.android310.homework1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class SearchableActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        mListView = (ListView) findViewById(R.id.list);

        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_searchable, menu);

        // SearchView is not supported before the Honeycomb OS release.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // TODO: Get the SearchView and set the searchable configuration
            // Your code here

            // TODO: Register the search configuration
            // Your code here

            // Do not iconify the widget; expand it by default!
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // This method will be called if this activity is in the foreground.
        handleIntent(intent);
    }

    /**
     * Helper method to handle a search query intent and a search suggestion click event.
     * @param intent
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // TODO: Handle a click on a search suggestion; should launch activity to show word and definition
            // Your code here

        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // TODO: Handle a search query and show results
            // Your code here
        }
    }

    /**
     * Searches the dictionary and displays results for the given query.
     * @param query The search query
     */
    private void showResults(String query) {

        Cursor cursor = managedQuery(DictionaryProvider.CONTENT_URI, null, null,
                new String[]{query}, null);

        if (cursor == null) {
            // TODO: Update the activity to show that there are no results

            // Refer to this to implement the empty list view pattern:
            // http://developer.android.com/reference/android/widget/AdapterView.html#setEmptyView(android.view.View)

            // Your code here

        } else {
            // Update the activity to show the search results

            // TODO: Specify the columns to display in the result
            // Your code here

            // TODO: Specify the corresponding layout elements for the above columns
            // Your code here

            // TODO: Create a simple cursor adapter for the definitions and apply them to the ListView
            // Your code here

            // TODO: Define the on-click listener for the list items
            // Click on a search result should take you to a new activity that displays the word and definition.

            // For the on-click behavior, refer to:
            // http://developer.android.com/reference/android/widget/AdapterView.OnItemClickListener.html
        }
    }
}
