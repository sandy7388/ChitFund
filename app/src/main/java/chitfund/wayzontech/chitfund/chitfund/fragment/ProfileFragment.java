package chitfund.wayzontech.chitfund.chitfund.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.activity.EditProfileActivity;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.other.CircleTransform;
import chitfund.wayzontech.chitfund.chitfund.session.MemberSession;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private EditText editTextName,editTextEmail,
                    editTextAddress,editTextBirthday,editTextMobile;
    private ImageView imageViewEdit,imageViewProfile,imageViewChange;
    private Button buttonDelete;
    private MemberSession session;
    private DateFormat date,time;
    private Date d;
    private static final String urlProfileImg = "https://s-media-cache-ak0.pinimg.com/736x/2c/bb/04/2cbb04e7ef9266e1e57a9b0e75bc555f.jpg";
    private String strName,strMobile,strEmail,strAddress,strBirthday;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);
        getActivity().setTitle("Profile");
        initController(view);
        loadHeaderMenu();
        return view;
    }

    void initController(View view)
    {
        session = new MemberSession(getContext());
        imageViewEdit = view.findViewById(R.id.imageEdit);
        imageViewChange = view.findViewById(R.id.imageChange);
        imageViewProfile = view.findViewById(R.id.profileImage);
        editTextName = view.findViewById(R.id.profileName);
        editTextAddress = view.findViewById(R.id.profileAddress);
        editTextEmail = view.findViewById(R.id.profileEmail);
        editTextBirthday = view.findViewById(R.id.profileBirthday);
        editTextMobile = view.findViewById(R.id.profileMobile);
        //buttonDelete = view.findViewById(R.id.btnDeleteAccount);
        //buttonDelete.setOnClickListener(this);
        imageViewEdit.setOnClickListener(this);
        imageViewChange.setOnClickListener(this);
        getProfile();

    }

    private void getProfile()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_URL + "groupinfo/getprofile" ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("profile_info");
                            for (int i=0;i<jsonArray.length();i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                if (jsonObject.getString("success").equals("1")) {
                                    strName = object.getString("member_name");
                                    strMobile = object.getString("member_mobile");
                                    strEmail = object.getString("email");
                                    strBirthday = object.getString("birth_date");
                                    strAddress = object.getString("member_address");

                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        d = dateFormat.parse(strBirthday);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    date = new SimpleDateFormat("dd-MM-yyyy");

                                    editTextName.setText(strName);
                                    editTextMobile.setText(strMobile);
                                    editTextEmail.setText(strEmail);
                                    editTextBirthday.setText(date.format(d));
                                    editTextAddress.setText(strAddress);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                //map.put("userid",session.getUserID());
                map.put("user_id",session.getUserID());
                return map;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    public void setFragments(Fragment targetFragment) {
        try {
            Fragment fragment = targetFragment;
            if (fragment != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content, fragment).commit();
            } else {
                // error in creating fragment
                Log.e(TAG, "Error in creating fragment");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.imageEdit:
                //setFragments(new EditProfileFragment());
                //openEditFragment();
                break;
            case R.id.imageChange:
                //alertForImage();
                break;
        }
    }

    void openEditFragment()
    {
        //EditProfileFragment profileFragment = new EditProfileFragment();
        Intent intent = new Intent(getContext(), EditProfileActivity.class);

        intent.putExtra("KEY_NAME", strName);
        intent.putExtra("KEY_MOBILE", strMobile);
        intent.putExtra("KEY_EMAIL", strEmail);
        intent.putExtra("KEY_BIRTHDAY", strBirthday);
        intent.putExtra("KEY_ADDRESS", strAddress);

        startActivity(intent);
    }


    // Alert for logout
    void alertForImage()
    {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(getContext());

        aBuilder.setMessage("Choose One");
        //aBuilder.setTitle("Logout Alert");
        //aBuilder.setIcon(R.drawable.warning);
        aBuilder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
            }
        });
        aBuilder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);//zero can be replaced with any action code
            }
        });
        aBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = aBuilder.create();
        alertDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode)
        {
            case 0:
                if (resultCode==RESULT_OK)
                {
                    Uri uri = data.getData();
                    imageViewProfile.setImageURI(uri);
                }
            case 1:
                if (resultCode==RESULT_OK)
                {
                    Uri uri = data.getData();
                    imageViewProfile.setImageURI(uri);
                }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    void loadHeaderMenu()
    {
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(getContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageViewProfile);
    }
}
