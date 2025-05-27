package com.example.praktikum3;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PostFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private ImageView postImageView;
    private Button selectFromGalleryButton, takePictureButton, postButton;
    private EditText captionEditText;
    private Uri imageUri;
    private String currentPhotoPath;

    private final ActivityResultLauncher<Intent> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.getData() != null) {
                                imageUri = data.getData();
                                postImageView.setImageURI(imageUri);
                            }
                        }
                    });

    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            postImageView.setImageURI(imageUri);
                        }
                    }
            );


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false); // Use fragment_post.xml
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postImageView = view.findViewById(R.id.postImageView);
        selectFromGalleryButton = view.findViewById(R.id.selectFromGalleryButton);
        takePictureButton = view.findViewById(R.id.takePictureButton);
        postButton = view.findViewById(R.id.postButton);
        captionEditText = view.findViewById(R.id.captionEditText);
        selectFromGalleryButton.setOnClickListener(v -> openGallery());

        takePictureButton.setOnClickListener(v -> checkCameraPermissionAndTakePicture());

        postButton.setOnClickListener(v -> {
            if (imageUri != null) {
                String caption = captionEditText.getText().toString();
                // Dapatkan userId user yang login (contoh hardcode):
                String userId = "login";  // Ganti dengan cara Anda mendapatkan userId

                // Buat objek Feed baru
                Feed newPost = new Feed(
                        R.drawable.profile1, // Atau dapatkan dari data user
                        imageUri.toString(), // Simpan URI sebagai String
                        "kvnryadi", // Atau dapatkan dari data user
                        "0",
                        "0",
                        "0",
                        userId,
                        "kvnryadi", // Atau dapatkan dari data user
                        caption,
                        imageUri.toString(),
                        true // isImageUri = true
                );

                // Simpan postingan ke UserStaticData
                List<Feed> userPosts = UserStaticData.getPosts(userId);
                if (userPosts == null) {
                    userPosts = new ArrayList<>();
                }
                userPosts.add(0, newPost);
                UserStaticData.userPosts.put(userId, userPosts);
                Log.d("PostFragment", "userId: " + userId + ", imageUri: " + imageUri + ", post saved: " + (userPosts != null));
                // Simpan postingan ke UserStaticData

                // Update post count di UserStaticData
                User user = UserStaticData.getUser(userId);
                if (user != null) {
                    user.setPostCount(user.getPostCount() + 1);
                }

                Log.d("PostFragment", "userId: " + userId + ", post count updated: " + (user != null));

                Toast.makeText(requireContext(), "Postingan berhasil diunggah", Toast.LENGTH_SHORT).show();
//                getActivity().recreate(); // Tambahkan baris ini setelah postingan berhasil diunggah
//                if (mListener != null) {
//                    mListener.onPostUploaded(newPost);
//                }
//                getActivity().getSupportFragmentManager().popBackStack(); // Kembali ke fragment sebelumnya
//                Log.d("PostFragment", "userId: " + userId + ", imageUri: " + imageUri + ", post saved: " + (userPosts != null));

                // Kembali ke FeedFragment atau ProfileFragment (opsional)
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FeedFragment())
                        .commit();// Kembali ke fragment sebelumnya
            } else {
                Toast.makeText(requireContext(), "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    private void checkCameraPermissionAndTakePicture() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),  // Use requireActivity()
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            takePicture();
        }
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) { // Use requireActivity()
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("PostFragment", "Error creating image file", ex);
                Toast.makeText(requireContext(), "Error capturing image", Toast.LENGTH_SHORT).show(); // Use requireContext()
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                imageUri = FileProvider.getUriForFile(requireContext(),  // Use requireContext()
                        requireContext().getPackageName() + ".fileprovider",  // Use requireContext()
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                cameraLauncher.launch(takePictureIntent);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Use requireContext()
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture();
            } else {
                Toast.makeText(requireContext(), "Camera permission required", Toast.LENGTH_SHORT).show(); // Use requireContext()
            }
        }
    }

//    private OnPostUploadedListener mListener;
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof OnPostUploadedListener) {
//            mListener = (OnPostUploadedListener) context;
//        } else {
//            throw new ClassCastException(context.toString()
//                    + " must implement OnPostUploadedListener");
//        }
//    }
}