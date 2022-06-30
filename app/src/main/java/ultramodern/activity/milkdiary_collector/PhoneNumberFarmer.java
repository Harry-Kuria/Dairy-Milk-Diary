package ultramodern.activity.milkdiary_collector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PhoneNumberFarmer extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_farmer);
    }
    public void ToAuthentication(View paramView){
        EditText phoneNumberInput = findViewById(R.id.editText);
        if (phoneNumberInput.length()==0){
            phoneNumberInput.setError("Input is needed!");
        }
        else if (phoneNumberInput.length()<12){
            phoneNumberInput.setError("That contact number is incomplete!");
        }
        else if (!phoneNumberInput.getText().toString().startsWith("+")){
            phoneNumberInput.setError("Use this format, +country code contact!");
        }
        else {
            Intent intent = new Intent(PhoneNumberFarmer.this,PhoneNumberAuthenticationFarmer.class);
            intent.putExtra("phonenumber",phoneNumberInput.getText().toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}