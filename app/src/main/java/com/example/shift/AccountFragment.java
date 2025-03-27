package com.example.shift;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class AccountFragment extends Fragment {

    private FirebaseAuth mAuth;
    private TextView accountName, accountEmail;
    private TextInputEditText editName;
    private Button saveNameButton;
    Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        accountName = view.findViewById(R.id.account_name);
        accountEmail = view.findViewById(R.id.account_email);
        editName = view.findViewById(R.id.edit_name);
        saveNameButton = view.findViewById(R.id.save_name_button);
        button = view.findViewById(R.id.logoutbutton);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String displayName = currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "Not set";
            String email = currentUser.getEmail() != null ? currentUser.getEmail() : "Not set";
            accountName.setText("Name: " + displayName);
            accountEmail.setText("Email: " + email);
            editName.setText(displayName.equals("Not set") ? "" : displayName);
        } else {
            accountName.setText("Name: Not logged in");
            accountEmail.setText("Email: Not logged in");
            editName.setEnabled(false);
            saveNameButton.setEnabled(false);
        }

        saveNameButton.setOnClickListener(v -> {
            String newName = editName.getText().toString().trim();
            if (newName.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter a name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentUser != null) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(newName)
                        .build();

                currentUser.updateProfile(profileUpdates)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                accountName.setText("Name: " + newName);
                                Toast.makeText(getActivity(), "Name updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Failed to update name: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });
    }
}