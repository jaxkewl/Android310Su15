package com.uw.android310.homework1;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class SearchableActivity extends AppCompatActivity {
    private static final String TAG = SearchableActivity.class.getSimpleName();

    private TextView mTextView;
    private ListView mListView;
    private SimpleCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        mListView = (ListView) findViewById(R.id.list);
        //mTextView = (TextView) findViewById(R.id.);

        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu called");

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_searchable, menu);

        // GOTCHA:SearchView is not supported before the Honeycomb OS release.
        // search widget is available only in Android 3.0 (API Level 11) and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Log.d(TAG, "onCreateOptionsMenu inner called");

            // TODO: Get the SearchView and set the searchable configuration
            // Your code here
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

            // TODO: Register the search configuration
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            // Do not iconify the widget; expand it by default!
            searchView.setIconifiedByDefault(false);
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

        // TODO: Update the options menu to initiate the search request if the SearchView menu item is collapsed.
        // Your code here
        if (item.getItemId() == R.id.action_search) {
            onSearchRequested();
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
     * <p/>
     * Note: this method does not get called when search suggestions is coming up with suggestions.
     * this method is only called if the user clicks on a search suggestion (ACTION_VIEW)
     * or presses enter (ACTION_SEARCH)
     *
     * @param intent
     */
    private void handleIntent(Intent intent) {
        Log.d(TAG, "handleIntent called with " + intent.toString() + "         " + intent.getAction());

        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // TODO: Handle a click on a search suggestion; should launch activity to show word and definition

            //Note: when this is called, we know the Row ID of which word was chosen. That's all we need.

            //get the uri that is stored in the intent, and pass that to the handling activity which
            //will display the definition.
            //Note: Uri is only stored in the intent if searchSuggestIntentData
            // was defined in the searchable.xml
            Intent defnIntent = new Intent(SearchableActivity.this, DefinitionActivity.class);
            defnIntent.setData(intent.getData());
            startActivity(defnIntent);

        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // TODO: Handle a search query and show results
            //this is what gets called when user presses enter, the suggestions are ignored.

            String query = intent.getStringExtra(SearchManager.QUERY);
            showResults(query);
        }
    }

    /**
     * Searches the dictionary and displays results for the given query.
     *
     * @param query The search query
     */
    private void showResults(final String query) {
        Log.d(TAG, "showResults called with " + query + " and URI is " + DictionaryProvider.CONTENT_URI);
        //Toast.makeText(this, query, Toast.LENGTH_LONG).show();

        Cursor cursor = managedQuery(DictionaryProvider.CONTENT_URI, null, null,
                new String[]{query}, null);

        if (cursor == null) {
            Log.d(TAG, "empty list");
            // TODO: Update the activity to show that there are no results

            // Refer to this to implement the empty list view pattern:
            // http://developer.android.com/reference/android/widget/AdapterView.html#setEmptyView(android.view.View)

            // Your code here
            //add this to the layout
            /*<TextView
            android:id="@android:id/empty"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="No Results"/>*/

        } else {
            Log.d(TAG, "results list");
            // Update the activity to show the search results

            // TODO: Specify the columns to display in the result
            String[] fromFields = new String[]{DictionaryDatabase.KEY_WORD, DictionaryDatabase.KEY_DEFINITION};   //name of the table column

            // TODO: Specify the corresponding layout elements for the above columns
            int[] toFields = new int[]{R.id.word, R.id.definition};

            // TODO: Create a simple cursor adapter for the definitions and apply them to the ListView
            mAdapter = new SimpleCursorAdapter(
                    this,
                    R.layout.activity_definition,
                    cursor,
                    fromFields,
                    toFields);

            mListView.setAdapter(mAdapter);

            // TODO: Define the on-click listener for the list items
            // Click on a search result should take you to a new activity that displays the word and definition.
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d(TAG, "@@@@@@@@@@@@@@@@ onClick called...");
                    Intent defnIntent = new Intent(SearchableActivity.this, DefinitionActivity.class);
                    Uri data = Uri.parse(DictionaryProvider.CONTENT_URI + "/" + l);
                    defnIntent.setData(data);
                    startActivity(defnIntent);
                }
            });

            // For the on-click behavior, refer to:
            // http://developer.android.com/reference/android/widget/AdapterView.OnItemClickListener.html
        }
    }
}


// ======================================
// manifest changes
// ======================================
/*
Set the launch mode
 singleTop-if an instance of the activity is already at the top of a backstack, then the system routes
 the intent to that instance instead of creating a new one and adding it on top of that.

Add an intent filter to receive search requests
 an intent filter tells the system, what types of intents we want this activity to respond to.
 i.e. so if an intent is sent, with intent.setAction("whatever.i.chose.here"); that corrosponding activity should start
      "whatever.i.chose.here" should be part of the intent filter name defined in the manifest.
      Also include the default category

Add a searchable configuration xml
 Add a meta-data tag to the manifest so activities and fragments have access to some "global hash table" you've created
 in the manifest.
 a searchable config is an XML file that configures some settings for the search dialog or widget.
 It includes settings for features such as voice search, search suggestion, and hint text for the search box.

 ApplicationInfo ai = getPackageManager().getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
    Bundle bundle = ai.metaData;
    String myApiKey = bundle.getString("android.app.searchable");


*/


// ======================================
//java code changes
// ======================================
/*
The SearchableActivity is an activity that receives the search query, searches your data, and displays the search results.
use http://developer.android.com/guide/topics/search/search-dialog.html as a reference

Configuring the search widget.



Custom suggestions
 A normal search query sends an intent with the ACTION_SEARCH action, you can instead define
 custom suggestions to use the ACTION_VIEW
 To use custom suggestions you need the following
 -Search Interface (Search widget for us)
 -searchable configuration with information about the CP
 -SQLite DB
 -Content provider to the DB
 -declare type of intent to be sent when user selects a suggestion

 Simply make sure you have android:searchSuggestAuthority="com.uw.android310.homework1.DictionaryProvider"
 defined in the searchable xml which points to your content provider.

 as each letter is typed in the search widged interface, a call is made to the query method of the
 content provider. make sure you have selection args populated using the the uri that is provided.
 also make sure you are mapping to the correct uri type.


*/


// ======================================
//xml changes
// ======================================

/*
 add in the content provider so search suggestions knows where to query for it
    android:searchSuggestSelection="com.uw.android310.homework1.DictionaryProvider"


*/