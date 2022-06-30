package ultramodern.activity.milkdiary_collector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Launcher extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth auth;

    Button getstartedbutton;

    public void onClick(View paramView) {
        if (paramView == this.getstartedbutton) {
            Intent intent = new Intent(getApplicationContext(), PhoneNumber.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
    public void PhoneNumberFarmer(View paramView){
        Intent intent = new Intent(Launcher.this, PhoneNumberFarmer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_launcher);
        Button button = (Button)findViewById(R.id.getstartedbutton);
        this.getstartedbutton = button;
        button.setOnClickListener(this);
    }
}
