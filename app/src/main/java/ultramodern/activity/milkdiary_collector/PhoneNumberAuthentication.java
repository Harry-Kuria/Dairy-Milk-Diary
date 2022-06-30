package ultramodern.activity.milkdiary_collector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class PhoneNumberAuthentication extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;

    Button button2;

    EditText editText2;

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        public void onCodeAutoRetrievalTimeOut(@NonNull String param1String) { super.onCodeAutoRetrievalTimeOut(param1String); }

        public void onCodeSent(@NonNull String param1String, @NonNull PhoneAuthProvider.ForceResendingToken param1ForceResendingToken) {
            super.onCodeSent(param1String, param1ForceResendingToken);
            PhoneNumberAuthentication.this.verificationId = param1String;
        }

        public void onVerificationCompleted(@NonNull PhoneAuthCredential param1PhoneAuthCredential) {}

        public void onVerificationFailed(@NonNull FirebaseException param1FirebaseException) {}
    };

    String verificationId;

    private void signInWithPhoneAuthCredential(PhoneAuthCredential paramPhoneAuthCredential) { this.auth.signInWithCredential(paramPhoneAuthCredential).addOnCompleteListener(param1Task -> {
        if (param1Task.isSuccessful()) {
            Intent intent = new Intent(PhoneNumberAuthentication.this.getApplicationContext(), gettingready.class);
            PhoneNumberAuthentication.this.startActivity(intent);
        }
    }); }

    private void verification(String paramString) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth).setPhoneNumber(paramString).setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(this.mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void verifyCode(String paramString) { signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(this.verificationId, paramString)); }

    public void onClick(View paramView) { verifyCode(this.editText2.getText().toString()); }

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_phone_number_authentication);
        EditText editText = findViewById(R.id.editText2);
        this.editText2 = editText;
        if (editText.length() == 0)
            this.editText2.setText("Code required");
        Button button = findViewById(R.id.button2);
        this.button2 = button;
        button.setOnClickListener(this);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        this.auth = firebaseAuth;
        firebaseAuth.setLanguageCode("fr");
        verification(getIntent().getStringExtra("phonenumber"));
    }
}
