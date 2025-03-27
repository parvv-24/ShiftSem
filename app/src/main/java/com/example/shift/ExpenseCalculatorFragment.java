package com.example.shift;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpenseCalculatorFragment extends Fragment {

    private Spinner sourceSpinner, destinationSpinner;
    private LinearLayout boxesContainer;
    private Switch packingSwitch;
    private TextView resultText;
    private Map<String, LatLng> locationCoordinates;
    private ArrayList<EditText> boxInputs = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expense_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sourceSpinner = view.findViewById(R.id.source_spinner);
        destinationSpinner = view.findViewById(R.id.destination_spinner);
        boxesContainer = view.findViewById(R.id.boxes_container);
        packingSwitch = view.findViewById(R.id.packing_switch);
        resultText = view.findViewById(R.id.result_text);
        Button addBoxButton = view.findViewById(R.id.add_box_button);
        Button submitButton = view.findViewById(R.id.submit_button);
        ImageButton backButton = view.findViewById(R.id.back_button);

        // Sample city list with coordinates (replace with API call if needed)
        String[] cities = {"Delhi", "Mumbai", "Bangalore", "Kolkata", "Chennai", "Greater Noida"};
        locationCoordinates = new HashMap<>();
        locationCoordinates.put("Delhi", new LatLng(28.6139, 77.2090));
        locationCoordinates.put("Mumbai", new LatLng(19.0760, 72.8777));
        locationCoordinates.put("Bangalore", new LatLng(12.9716, 77.5946));
        locationCoordinates.put("Kolkata", new LatLng(22.5726, 88.3639));
        locationCoordinates.put("Chennai", new LatLng(13.0827, 80.2707));
        locationCoordinates.put("Greater Noida", new LatLng(28.4744, 77.5040));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(adapter);
        destinationSpinner.setAdapter(adapter);

        // Add initial box input
        addBoxInput();

        addBoxButton.setOnClickListener(v -> addBoxInput());

        submitButton.setOnClickListener(v -> calculateExpense());

        backButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.Home_menu);
        });
    }

    private void addBoxInput() {
        EditText boxInput = new EditText(requireContext());
        boxInput.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        boxInput.setHint("Number of Boxes (e.g., 1)");
        boxInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        boxesContainer.addView(boxInput);
        boxInputs.add(boxInput);
    }

    private void calculateExpense() {
        String source = sourceSpinner.getSelectedItem() != null ? sourceSpinner.getSelectedItem().toString() : null;
        String destination = destinationSpinner.getSelectedItem() != null ? destinationSpinner.getSelectedItem().toString() : null;

        if (source == null || destination == null || source.equals(destination)) {
            resultText.setText("Distance: N/A\nEstimated Charges: Select different cities");
            return;
        }

        LatLng sourceLatLng = locationCoordinates.get(source);
        LatLng destLatLng = locationCoordinates.get(destination);
        if (sourceLatLng == null || destLatLng == null) {
            resultText.setText("Distance: N/A\nEstimated Charges: Unable to calculate");
            return;
        }

        double distance = calculateDistance(sourceLatLng, destLatLng);
        int totalBoxes = 0;
        for (EditText boxInput : boxInputs) {
            String boxCount = boxInput.getText().toString().trim();
            if (!boxCount.isEmpty()) {
                totalBoxes += Integer.parseInt(boxCount);
            }
        }

        if (totalBoxes == 0) {
            resultText.setText("Distance: N/A\nEstimated Charges: Add at least one box");
            return;
        }

        double baseCost = totalBoxes * distance * 3; // 300 per box per km
        double packingCost = packingSwitch.isChecked() ? totalBoxes * 500 : 0; // Assume 500 per box for packing
        double totalCost = baseCost + packingCost;

        resultText.setText(String.format(
                "Distance: %.2f km\nEstimated Charges: ₹%.2f%s",
                distance, totalCost, packingSwitch.isChecked() ? " (includes packing)" : ""
        ));
    }

    private double calculateDistance(LatLng start, LatLng end) {
        final int R = 6371; // Earth's radius in kilometers
        double latDistance = Math.toRadians(end.latitude - start.latitude);
        double lonDistance = Math.toRadians(end.longitude - start.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(start.latitude)) * Math.cos(Math.toRadians(end.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in kilometers
    }

    private static class LatLng {
        double latitude, longitude;

        LatLng(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}