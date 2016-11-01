package com.julianengel.smsexploratoryprototype;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;
import java.util.concurrent.Exchanger;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.julianengel.smsexploratoryprototype.R.styleable.View;

/**
 * Created by Julian and Chris on 10/25/16.
 */

/* This class defines how our data will interact with the ListView; it
 * describes what data is in each item and how it's structured.
 * The adapter is responsible for creating a View for the ListView to use for
 * each of the items contained within it.
 */
public class ChatListAdapter extends ArrayAdapter<Message> {

	/* This class will track the currently logged in user.
	 */
    private String mUserId;

    /* Default constructor for the adapter. This will call the constructor for
     * ArrayAdapter(Context, int, List<T>). Takes the context of the current
     * running state, an int as a resouce ID, and the list of objects that will
     * be represented in the ListView.
     */
    public ChatListAdapter(Context context, String userId, List<Message> messages) {

	/* I beleive the 0 passed in as a resource ID will cause the
	 * ArrayAdapter to use the default TextView.
	 */
        super(context, 0, messages);

        this.mUserId = userId;
    }

	/* This function is responsible for creating a View of some data for an
	 * item in the list.
	 */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

		/* If an item in the list has already been created (for example, if we
		 * want to display a message that's already been rendered in the list)
		 * then just recycle it instead of inflating a new one.
		 */
        if(convertView == null) {

			/* If there is no older version of the data, inflate a new one.
			 * So if we get a new message that hasn't been displayed before,
			 * make a new entry in the ListView.
			 */

			/* Parameters for inflate():
			 * 	R.layout.chat_item: int resource ID of the resource to load.
			 * 	parent: ViewGroup passed in that is the parent of the newly
			 * 		inflated View. In this case this should be the lvChat
			 * 		ListView defined in activity_chat.xml.
			 * 	false: determines if the new View should be attached and
			 * 		displayed in the parent view. In this case we don't want
			 * 		to do that quite yet so we set it to false.
			 */
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_item, parent, false);

			/* This is a class that we define that will hold data for each
			 * View in the View; basically it's a method of allowing a View to
			 * have its own data associated with it, even if it's not currently
			 * being displayed.
			 */
            final ViewHolder holder = new ViewHolder();
            holder.imageOther = (ImageView)convertView.findViewById(R.id.ivProfileOther);
            holder.imageMe = (ImageView)convertView.findViewById(R.id.ivProfileMe);
            holder.body = (TextView)convertView.findViewById(R.id.tvBody);

			/* Set our new tag object on the newly created convertView.
			 */
            convertView.setTag(holder);
        }

		/* Create a new message item and set it to the message stored in this
		 * ArrayAdapter subclass's own internal data. This data is the array
		 * of messages that's used in ChatActivity.java when fetching new
		 * messages. So message will equal some Message that is contained in
		 * ChatActivity.java's mMessages variable.
		 */
        final Message message = getItem(position);

		/* Get the data that's been associated with that particular message.
		 * This contains the references for that message item's layout fields:
		 * ivProfileOther, ivProfileMe, tvBody.
		 */
        final ViewHolder holder = (ViewHolder)convertView.getTag();

		/* We test to see if the message was from our current user or some
		 * other user. This affects the user image justification.
		 */
        final boolean isMe = message.getUserId() != null && message.getUserId().equals(mUserId);

        /* Show/hide image based on logged in user.
         * Justify local user's pic to the right, left to other users.
		 */
        if(isMe) {
			/* Make current message's ivProfileMe ImageView visible.
			 */
            holder.imageMe.setVisibility(VISIBLE);

			/* Make the current message's ivProfileOther ImageView invisible.
			 */
            holder.imageOther.setVisibility(GONE);

			/* Justify the tvBody TextView to the right and center it.
			 */
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        else {
			/* If the message isn't from the current user, make the current
			 * user's picture invisible, the other user's picture visible,
			 * and justify their text to the left.
			 */
            holder.imageOther.setVisibility(VISIBLE);
            holder.imageMe.setVisibility(GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }

		/* Get the appropriate ImageView to draw a profile picture in.
		 */
        final ImageView profileView = isMe ? holder.imageMe : holder.imageOther;

		/* Picasso is a 3rd party library for downloading, transforming, and
		 * caching images. We use this because we're generating profile pictures
		 * using the website gravatar.com based on a hash of the userId. This
		 * generates a unique profile picture for each user that doesn't have
		 * to be stored externally.
		 */
		/* load(): fetches an image from the specified URL. This is calculated
		 * 	by the function below.
		 * into(): loads the fetched image into the specified ImageView.
		 */
        Picasso.with(getContext()).load(getProfileUrl(message.getUserId())).into(profileView);

		/* Set the text of the message View's TextView to the body of the stored
		 * Message object.
		 */
        holder.body.setText(message.getBody());

        return convertView;

    }

    /* Creates image based on the hash from userId. This is useful because we
	 * can programatically generate and fetch profile pictures unique to each
	 * user.
	 */
    private static String getProfileUrl(final String userId) {
        String hex = "";
        try {

			/* Returns a MessgageDigest object that implements MD5 algorithm.
			 */
            final MessageDigest digest = MessageDigest.getInstance("MD5");

			/* digest() takes in a byte[] parameter, hashes it, and returns
			 * a byte[] hash.
			 * getBytes() takes a string and converts it to a byte[] stream.
			 */
            final byte[] hash = digest.digest(userId.getBytes());

			/* Turns the byte[] hash into an integer so that we can absolute
			 * value it and transform it into a 16-character string.
			 */
            final BigInteger bigInt = new BigInteger(hash);

			/* For clarification:
			 * An MD5 hash is always 128 bits long.
			 * Characters in Java are 1 byte long, or 8 bits.
			 * So, 128/8 = 16 characters needed to store the entire MD5 hash.
			 *
			 */
            hex = bigInt.abs().toString(16);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

		/* Return a string pointing to the particular URL that Picasso can use
		 * to download an image that's generated based on our hashed value.
		 */
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }

	/* Class to use as a tag for our message Views. This allows the information
	 * about each message's unique layout elements (the ivProfileOther,
	 * ivProfileMe, tvBody) to be saved along with the item.
	 */
    final class ViewHolder {
        public ImageView imageOther;
        public ImageView imageMe;
        public TextView body;
    }
}
