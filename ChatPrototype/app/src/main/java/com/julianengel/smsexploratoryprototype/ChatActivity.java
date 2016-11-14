package com.julianengel.smsexploratoryprototype;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.support.design.widget.NavigationView;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.design.R.styleable.MenuItem;

import com.parse.ParseQueryAdapter;

public class ChatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView chatList;
    private ParseQueryAdapter<ParseObject> chatQueryAdapter;

    /* Declare a constant for the name of this class; used when sending things
     * to LogCat
     */
    static final String TAG = ChatActivity.class.getSimpleName();

    /* These are keys for the backend DB, and are the same as the ones defined
     * in Messages.java
     */
    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "body";

    /* These hold the send button and message GUI elements that are defined in
     * activity_chat.xml. etMessage is the text the user wants to send and
     * btSend is the send button.
     */
    EditText etMessage;
    Button btSend;

    Button btNewChat;

    /* Will point to the ListView inside of the activity_chat.xml file. this is
     * where the messages will appear when sent and received.
     */
    ListView lvChat;

    /* Create an instance of our own MessageListAdapter. This class sits between
     * the data we get from the Parse server - in this case an ArrayList of
     * Messages - and the ListView in our app's layout.
     */
    MessageListAdapter mAdapter;

	/* This value determines if we're loading the app for the first time.
	 * It will affect the messages that we see upon first loading the app.
	 */
    boolean mFirstLoad;

	/* How often to poll the DB for new messages. Note that this is a TERRIBLE
	 * way to handle getting new messages! We should implement something a bit
	 * more intelligent for this.
	 */
    static final int POLL_INTERVAL = 1000;

	/* These two objects allow us to multithread the process of fetching new
	 * messages.
	 * A Handler manages messages and runnable code, allowing them to be passed
	 * 		to a Looper that is constantly queueing and processing messages.
	 * A Runnable is an object that represents code that can be run inside of
	 * 		a thread.
	 */
    Handler mHandler = new Handler();
    Runnable mRefreshMessagesRunnable = new Runnable() {
		/* The run() method is a part of the Runnable interface and is the
		*  code that is to be executed in a thread.
		*/
        @Override
        public void run() {
            refreshMessages();

			/* postDelayed(Runnable, long) will cause the runnable provided to
			 * be queued to run after a set amount of time.
			 * So this part of the code implements the "pull new messages every
			 * second" aspect.
			 * Essentially this thread runs refreshMessages(), then reschedules
			 * itsef.
			 */
           // mHandler.postDelayed(this, POLL_INTERVAL);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		/* Set the current View to the one we have specified for this app,
		 * in activity_chat.xml
		 */
        setContentView(R.layout.activity_chat);

        /* Nav drawer and settings menu code implemented by Julian 11/3/16 */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        /* Reference to our ListView containing all the chats, inside the Nav Drawer */
        chatList = (ListView) findViewById(R.id.chatList);

        chatQueryAdapter = new ParseQueryAdapter<ParseObject>(this, "Chat");
        chatQueryAdapter.setTextKey("chatName");
        chatList.setAdapter(chatQueryAdapter);
        chatQueryAdapter.loadObjects();


        /* Define logic for populating the menu in the nav drawer
            Implemented by Julian 11/7/16
         */
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                /*
                ParseQuery<ParseObject> curUserQuery = ParseQuery.getQuery("chatUsers");
                curUserQuery.whereEqualTo("userId", ParseUser.getCurrentUser());

                List<ChatObject> chat_ids = curUserQuery.getFirst().get("chats");

                ParseQuery<ParseObject> chatQuery = ParseQuery.getQuery("Chat");
                chatQuery.whereContainedIn("objectId", chat_ids);
                chatQuery.find();
                */
                chatQueryAdapter.loadObjects();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        btNewChat = (Button) findViewById(R.id.btNewChat);
        btNewChat.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View V) {

                                             Intent i = new Intent(getApplicationContext(), NewChatActivity.class);
                                             startActivity(i);

                                             chatQueryAdapter.notifyDataSetChanged();
                                             chatQueryAdapter.loadObjects();

                                             DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                             drawer.closeDrawer(GravityCompat.START);
                                         }
                                     });

        // This code adds the button to the bar at the top of the screen that allows us to open the menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



		/* The ParseUser class is a local representation of a user's data: name,
		 * email, sessionToken, etc
		 */

		/* getCurrentUser() will search memory or the disk for a logged in user
		 * that has a valid session. Will return null if the user isn't found
		 * or valid (technically it returns null if any ParseException is
		 * thrown).
		 * Also note that automatic login will have to be manually enabled if
		 * we decide to use it; check ParseUser.java for more info.
		 */

        if(ParseUser.getCurrentUser() != null) {
			/* If there's currently a user logged in, go ahead and run the main
			 * part of the code.
			 */

            startWithCurrentUser();
        }
        else {
			/* Otherwise, attempt to log in as an anonymous user.
			 */
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);

        }

        mHandler.postDelayed(mRefreshMessagesRunnable, POLL_INTERVAL);



    }

    /* If the back button is pressed while in our nav menu, don't exit the app - close the drawer
    instead.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    /* Inflates the right-hand options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /* Code that handles when an option menu item is pressed
     */
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.logoutButton) {

            /* this will log the user out and send the app to the login activity
             */
            mAdapter.clear();
            ParseUser.logOut();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

            return true;
        }
        else if(id == R.id.action_settings) {
            /* TODO: implement any settings here
             */
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        /* TODO: code to run based on what navigation bar button was pressed. Note that this
            will probably change based on how we implement the Chats drawer
        */

        /* Code that will close the nav drawer after an item has been selected
         */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }



    /* If we were managing user accounts, this is where the user information
	 * would be populated before moving on.
	 */
    void startWithCurrentUser() {
        setupMessagePosting();
    }


	/* If there isn't a user logged in, log in. In this demo, we're using an
	 * anonymous user here, which is why the class ParseAnonymousUtils is being
	 * called to log a new user in.
	 * See ParseAnonymousUtils.java for more information.
	 */

	/* The anonymous user is created in the background, on a separate thread.
	 * This is why it takes a Callback as a parameter; it will execute the code
	 * in done() once the thread code for logging in the user is complete.
	 */
    void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null) {
                    Log.e(TAG,"Anonymous login failed: ", e);
                }
                else {
                    startWithCurrentUser();
                }
            }
        });
    }

    /* Sets up the button event handler that will post the message to the parse
	 * server. Handles everything needed to send a message to the server.
	 * This code, aside for the code within the onClickListener, should only
	 * ever run once at startup.
	 */
    void setupMessagePosting() {
        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (Button) findViewById(R.id.btSend);
        lvChat = (ListView) findViewById(R.id.lvChat);

        /* Scroll to the bottom when a data set change occurs. 1 will set the
		 * transcript mode of the ListView lvChat to always scroll down to show
		 * new items.
		 */
        lvChat.setTranscriptMode(1);

		/* Since this is the first time the app had loaded, tell
		 * refreshMessages() to focus the list on the very last message,
		 * effectively showing the user the most recent message made in the
		 * chat.
		 */
        mFirstLoad = true;

		/* Get the ID of the currently logged in. ObjectId + className is
		 * a unique identifier for an object in a parse application.
		 */

        mAdapter = new MessageListAdapter(ChatActivity.this);

		/* Set ListView to use our own adapter. The idea of the adapter is that
		 * it is responsible for managing the data within each item in the
		 * ListView.
		 */
        lvChat.setAdapter(mAdapter);


        /* Create message when button is pressed and save it to the remote
		 * Parse DB. Defines what code to run when the send button is pressed.
		 */
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

				/* Pull the data that was entered in the EditText so that it
				 * can be sent.
				 */
                String data = etMessage.getText().toString();

                /* Make a new instance of our extended ParseObject called
				 * Message, and put the data in it.
				 */
                Message message = new Message();

                /* Fill in data from EditText using the setter function that we
				 * created.
				 */
				/* This would be a good place to encipher the outgoing message.
				 */
                message.setBody(data);

                /* Same story for the userId.
				 */
                message.setUserId(ParseUser.getCurrentUser().getObjectId());

				/* save the message to the server. Note that this is done on
				 * a separate thread, just like the user login. That's why
				 * a callback must be passed to the function.
				 * Note that this is a function that was inherited from
				 * ParseObject.
				 */
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            Toast.makeText(ChatActivity.this, "Successfully sent message to DB", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.e(TAG, "Failed to send message", e);
                        }
                    }
                });

                etMessage.setText(null);
            }
        });


    }

    void refreshMessages() {
        if (ParseUser.getCurrentUser() != null){
            mAdapter.notifyDataSetChanged();
            mAdapter.loadObjects();

                /* If this is the first time the query has run, set the
                 * selected item in the ListView to the very last one.
                 * So, when the app is opened for the first time and
                 * all the messages are fetched, it will select the most
                 * recent one and scroll to it automatically.
                 */
               if(mFirstLoad) {
                   lvChat.setSelection(mAdapter.getCount() - 1);
                   mFirstLoad = false;
               }
           }
       }
}
