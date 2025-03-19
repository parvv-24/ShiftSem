package com.example.shift;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Moving Service Card
        CardView movingServiceCard = view.findViewById(R.id.cardMovingService);
        if (movingServiceCard == null) {
            Log.e(TAG, "Moving Service Card not found!");
            Toast.makeText(getActivity(), "Moving Service Card not found", Toast.LENGTH_SHORT).show();
            return;
        }

        movingServiceCard.setOnClickListener(v -> {
            Log.d(TAG, "Moving Service Card clicked");
            if (getActivity() != null) {
                try {
                    // Find the BottomNavigationView and select the Book item
                    BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
                    if (bottomNavigationView != null) {
                        bottomNavigationView.setSelectedItemId(R.id.Book_menu);
                    } else {
                        Log.e(TAG, "BottomNavigationView not found");
                        Toast.makeText(getActivity(), "Navigation not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error navigating to BookFragment: " + e.getMessage());
                    Toast.makeText(getActivity(), "Failed to navigate: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e(TAG, "Activity is null");
                Toast.makeText(getContext(), "Activity not available", Toast.LENGTH_SHORT).show();
            }
        });

        // Calendar Card (unchanged)
        CardView calendarCard = view.findViewById(R.id.cardCalendar);
        if (calendarCard == null) {
            Log.e(TAG, "Calendar Card not found!");
            Toast.makeText(getActivity(), "Calendar Card not found", Toast.LENGTH_SHORT).show();
            return;
        }

        calendarCard.setOnClickListener(v -> {
            Log.d(TAG, "Calendar Card clicked");
            Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
            calendarIntent.setData(CalendarContract.Events.CONTENT_URI);
            calendarIntent.putExtra(CalendarContract.Events.TITLE, "Courier Delivery");
            calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Greater Noida");
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis());
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() + 60 * 60 * 1000);

            if (calendarIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                try {
                    startActivity(calendarIntent);
                } catch (Exception e) {
                    Log.e(TAG, "Error opening calendar: " + e.getMessage());
                    Toast.makeText(getActivity(), "Failed to open calendar", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.w(TAG, "No calendar app found");
                Toast.makeText(getActivity(), "No calendar app found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}