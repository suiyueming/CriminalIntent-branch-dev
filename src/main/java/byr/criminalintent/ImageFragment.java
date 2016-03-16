package byr.criminalintent;


import android.app.Fragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import byr.criminalintent.javabean.PictureUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends DialogFragment {


    private static final String EXTRA_IMAGE_PATH = "ycc.criminalintent.image_path";
    private static final String EXTRA_IMAGE_DEGREE = "ycc.criminalintent.image_degree";
    private static final String EXTRA_SCREEN_DEGREE = "ycc.criminalintent.screen_degree";
    private static final String TAG = "ImageFragment";
    private ImageView mImageView;
    private String path;
    private int photoDegree;
    private int screenOrientation;

    public ImageFragment() {
        // Required empty public constructor
    }

    public static ImageFragment newInstance(String imagePath, int rotateDegree, int screenOrientation) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_IMAGE_PATH, imagePath);
        args.putSerializable(EXTRA_IMAGE_DEGREE, rotateDegree);
        args.putSerializable(EXTRA_SCREEN_DEGREE, screenOrientation);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mImageView = new ImageView(getActivity());
        path = (String) getArguments().getSerializable(EXTRA_IMAGE_PATH);
        photoDegree = getArguments().getInt(EXTRA_IMAGE_DEGREE);
        screenOrientation = getArguments().getInt(EXTRA_SCREEN_DEGREE);
        BitmapDrawable image = PictureUtils.getMatchScaledDrawable(getActivity(), path, photoDegree, screenOrientation);
        mImageView.setImageDrawable(image);
        mImageView.setRotation(photoDegree);
        Log.e(TAG, "photoDegree = " + photoDegree);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .remove(ImageFragment.this)
                        .commit();
            }
        });
        return mImageView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PictureUtils.cleanImageView(mImageView);
    }
}
