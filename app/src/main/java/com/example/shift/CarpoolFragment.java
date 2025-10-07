package com.example.shift;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.shift.databinding.FragmentCarpoolBinding;
import com.example.shift.model.CarpoolFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarpoolFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarpoolFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FragmentCarpoolBinding binding;

    public CarpoolFragment() {
        // Required empty public constructor
    }

    public static CarpoolFragment newInstance(String param1, String param2) {
        CarpoolFragment fragment = new CarpoolFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCarpoolBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupFeatureCards();
    }

    private void setupFeatureCards() {
        // Define feature data using CarpoolFeature
        List<CarpoolFeature> features = new ArrayList<>();
        features.add(new CarpoolFeature(
                getString(R.string.create_ride_title),
                getString(R.string.create_ride_description),
                R.drawable.black_local_taxi,
                getString(R.string.create_ride_icon_description),
                v -> Toast.makeText(getActivity(), "Create Ride coming soon", Toast.LENGTH_SHORT).show()
        ));
        features.add(new CarpoolFeature(
                getString(R.string.find_rides_title),
                getString(R.string.find_rides_description),
                R.drawable.ic_search_rides,
                getString(R.string.find_rides_icon_description),
                v -> Toast.makeText(getActivity(), "Find Rides coming soon", Toast.LENGTH_SHORT).show()
        ));
        features.add(new CarpoolFeature(
                getString(R.string.my_bookings_title),
                getString(R.string.my_bookings_description),
                R.drawable.ic_bookings,
                getString(R.string.my_bookings_icon_description),
                v -> Toast.makeText(getActivity(), "My Bookings coming soon", Toast.LENGTH_SHORT).show()
        ));
        features.add(new CarpoolFeature(
                getString(R.string.ride_history_title),
                getString(R.string.ride_history_description),
                R.drawable.ic_history,
                getString(R.string.ride_history_icon_description),
                v -> Toast.makeText(getActivity(), "Ride History coming soon", Toast.LENGTH_SHORT).show()
        ));

        // Map features to CardViews
        CardView[] cardViews = new CardView[]{
                binding.cardCreateRide,
                binding.cardFindRides,
                binding.cardMyBookings,
                binding.cardRideHistory
        };

        // Set click listeners and verify cards
        for (int i = 0; i < cardViews.length; i++) {
            CardView card = cardViews[i];
            CarpoolFeature feature = features.get(i);
            if (card != null) {
                card.setOnClickListener(feature.getOnClickListener());
            } else {
                Toast.makeText(getActivity(), feature.getTitle() + " card not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}