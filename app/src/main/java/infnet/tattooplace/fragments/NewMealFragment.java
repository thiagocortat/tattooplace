package infnet.tattooplace.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import infnet.tattooplace.R;
import infnet.tattooplace.activities.AddTattoActivity;
import infnet.tattooplace.models.Tatto;

public class NewMealFragment extends Fragment {

    public static final int GALLERY_REQUEST_CODE = 444;

	private ImageButton photoButton;
	private Button saveButton;
	private Button cancelButton;
	private TextView mealName;
	private Spinner mealRating;
	private ImageView mealPreview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle SavedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_new_meal, parent, false);

		mealName = ((EditText) v.findViewById(R.id.meal_name));

		mealRating = ((Spinner) v.findViewById(R.id.rating_spinner));
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(getActivity(), R.array.ratings_array,
						android.R.layout.simple_spinner_dropdown_item);
		mealRating.setAdapter(spinnerAdapter);

		photoButton = ((ImageButton) v.findViewById(R.id.photo_button));
        photoButton.setOnClickListener(dialogClick);

		saveButton = ((Button) v.findViewById(R.id.save_button));
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Tatto meal = ((AddTattoActivity) getActivity()).getCurrentMeal();
				meal.setTitle(mealName.getText().toString());
				meal.setAuthor(ParseUser.getCurrentUser());
				meal.setRating(mealRating.getSelectedItem().toString());
//                meal.setLocation(geoPointFromLocation(ParseApplication.getLocation()));

				meal.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							getActivity().setResult(Activity.RESULT_OK);
							getActivity().finish();
						} else {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Error saving: " + e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					}
				});

			}
		});

		cancelButton = ((Button) v.findViewById(R.id.cancel_button));
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().setResult(Activity.RESULT_CANCELED);
				getActivity().finish();
			}
		});

		// Until the user has taken a photo, hide the preview
		mealPreview = (ImageView) v.findViewById(R.id.meal_preview_image);
		mealPreview.setVisibility(View.INVISIBLE);

		return v;
	}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case GALLERY_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    mealPreview.setImageURI(selectedImage);
                    mealPreview.setVisibility(View.VISIBLE);

                    byte[] data;
                    try {
                        ContentResolver cr = getActivity().getContentResolver();
                        InputStream inputStream = cr.openInputStream(selectedImage);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos);
                        data = baos.toByteArray();

                        // Create the ParseFile
                        ParseFile file = new ParseFile("photo.jpg", data);
                        ((AddTattoActivity) getActivity()).getCurrentMeal().setPhotoFile(file);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
    }

	public void startCamera() {
		Fragment cameraFragment = new CameraFragment();
		FragmentTransaction transaction = getActivity().getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.fragmentContainer, cameraFragment);
		transaction.addToBackStack("NewMealFragment");
		transaction.commit();
	}

	@Override
	public void onResume() {
		super.onResume();
		ParseFile photoFile = ((AddTattoActivity) getActivity())
				.getCurrentMeal().getPhotoFile();
		if (photoFile != null) {

            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeByteArray(photoFile.getData(), 0, photoFile.getData().length);
                mealPreview.setImageBitmap(bmp);
                mealPreview.setVisibility(View.VISIBLE);
            } catch (ParseException e) {
                e.printStackTrace();
            }
		}
	}

    /*
   * Helper method to get the Parse GEO point representation of a location
   */
    private ParseGeoPoint geoPointFromLocation(Location loc) {
        return new ParseGeoPoint(loc.getLatitude(), loc.getLongitude());
    }

    private View.OnClickListener dialogClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String options[] = getActivity().getResources().getStringArray(R.array.send_photo_profile);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, options);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.select_photo));
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int position) {

                    Intent intent;
                    if (position == 0) {
                        InputMethodManager imm = (InputMethodManager) getActivity()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(mealName.getWindowToken(), 0);
                        startCamera();
                    } else if (position == 1) {
                        intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(intent, GALLERY_REQUEST_CODE);
                    }
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    };

}
