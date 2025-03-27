package com.example.shift;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class BookFragment extends Fragment {

    private static final String TAG = "BookFragment";
    private TextView activeSource, activeStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Active Booking UI elements
        activeSource = view.findViewById(R.id.active_source);
        activeStatus = view.findViewById(R.id.active_status);

        // Check for arguments from NewBookingFragment
        Bundle args = getArguments();
        if (args != null) {
            String source = args.getString("source", "G. Noida");
            String destination = args.getString("destination", "");
            String date = args.getString("date", "");
            String boxes = args.getString("boxes", "");

            activeSource.setText(source);
            activeStatus.setText("To " + destination + " on " + date + " (" + boxes + " boxes)");
            BookingHistoryFragment.addBooking(source, destination, date, boxes); // Add to history
        }

        // New Booking Card
        CardView newBookingCard = view.findViewById(R.id.card_new_booking);
        if (newBookingCard == null) {
            Log.e(TAG, "New Booking Card not found!");
            Toast.makeText(getActivity(), "New Booking Card not found", Toast.LENGTH_SHORT).show();
            return;
        }
        newBookingCard.setOnClickListener(v -> {
            Log.d(TAG, "New Booking Card clicked");
            if (getActivity() != null) {
                try {
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_bookFragment_to_newBookingFragment);
                } catch (Exception e) {
                    Log.e(TAG, "Error navigating to NewBookingFragment: " + e.getMessage());
                    Toast.makeText(getActivity(), "Failed to navigate: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e(TAG, "Activity is null");
                Toast.makeText(getContext(), "Activity not available", Toast.LENGTH_SHORT).show();
            }
        });

        // Booking History Card
        CardView bookingHistoryCard = view.findViewById(R.id.card_booking_history);
        if (bookingHistoryCard == null) {
            Log.e(TAG, "Booking History Card not found!");
            Toast.makeText(getActivity(), "Booking History Card not found", Toast.LENGTH_SHORT).show();
            return;
        }
        bookingHistoryCard.setOnClickListener(v -> {
            Log.d(TAG, "Booking History Card clicked");
            if (getActivity() != null) {
                try {
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_bookFragment_to_bookingHistoryFragment);
                } catch (Exception e) {
                    Log.e(TAG, "Error navigating to BookingHistoryFragment: " + e.getMessage());
                    Toast.makeText(getActivity(), "Failed to navigate: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e(TAG, "Activity is null");
                Toast.makeText(getContext(), "Activity not available", Toast.LENGTH_SHORT).show();
            }
        });
        CardView quickContactCard = view.findViewById(R.id.card_quick_contact);
        quickContactCard.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+1234567890")); // Replace with real number
            startActivity(intent);
        });
    }
}