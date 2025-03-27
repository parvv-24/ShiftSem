package com.example.shift;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;

public class newBookingFragment extends Fragment {

    private TextInputEditText sourceInput, destinationInput, dateInput, boxesInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sourceInput = view.findViewById(R.id.source_input);
        destinationInput = view.findViewById(R.id.destination_input);
        dateInput = view.findViewById(R.id.date_input);
        boxesInput = view.findViewById(R.id.boxes_input);
        Button submitButton = view.findViewById(R.id.submit_button);
        ImageButton backButton = view.findViewById(R.id.back_button);

        dateInput.setOnClickListener(v -> showDatePickerDialog());

        submitButton.setOnClickListener(v -> {
            String source = sourceInput.getText().toString().trim();
            String destination = destinationInput.getText().toString().trim();
            String date = dateInput.getText().toString().trim();
            String boxes = boxesInput.getText().toString().trim();

            if (source.isEmpty() || destination.isEmpty() || date.isEmpty() || boxes.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(),
                        "Courier booked from " + source + " to " + destination + " on " + date + " with " + boxes + " boxes",
                        Toast.LENGTH_LONG).show();

                Bundle args = new Bundle();
                args.putString("source", source);
                args.putString("destination", destination);
                args.putString("date", date);
                args.putString("boxes", boxes);

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.Book_menu, args);
            }
        });

        backButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.Book_menu); // Navigate back to BookFragment
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    dateInput.setText(date);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}