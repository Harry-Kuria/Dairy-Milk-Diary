package ultramodern.activity.milkdiary_collector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneNumberAuthenticationFarmer extends AppCompatActivity {

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_authentication_farmer);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        this.auth = firebaseAuth;
        firebaseAuth.setLanguageCode("en");
        verification(getIntent().getStringExtra("phonenumber"));
    }
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        public void onCodeAutoRetrievalTimeOut(@NonNull String param1String) { super.onCodeAutoRetrievalTimeOut(param1String); }

        public void onCodeSent(@NonNull String param1String, @NonNull PhoneAuthProvider.ForceResendingToken param1ForceResendingToken) {
            super.onCodeSent(param1String, param1ForceResendingToken);
            PhoneNumberAuthenticationFarmer.this.verificationId = param1String;
        }

        public void onVerificationCompleted(@NonNull PhoneAuthCredential param1PhoneAuthCredential) {}

        public void onVerificationFailed(@NonNull FirebaseException param1FirebaseException) {}
    };

    String verificationId;

    private void signInWithPhoneAuthCredential(PhoneAuthCredential paramPhoneAuthCredential) { this.auth.signInWithCredential(paramPhoneAuthCredential).addOnCompleteListener(param1Task -> {
        if (param1Task.isSuccessful()) {
            Intent intent = new Intent(PhoneNumberAuthenticationFarmer.this.getApplicationContext(), gettingready.class);
            PhoneNumberAuthenticationFarmer.this.startActivity(intent);
        }
    }); }

    private void verification(String paramString) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth).setPhoneNumber(paramString).setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(this.mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
       // PhoneAuthProvider.getInstance().verifyPhoneNumber(paramString, 60L, TimeUnit.SECONDS, this, this.mCallbacks); }

    private void verifyCode(String paramString) { signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(this.verificationId, paramString)); }

    public void VerifyingInput(View paramView){
        EditText codeInput = findViewById(R.id.editText2Farmer);
        if (codeInput.getText().length()==0){
            codeInput.setError("Code is required!");
        }
        else if (codeInput.getText().length()<4){
            codeInput.setError("Please input a valid code");
        }
        else if (codeInput.getText().length()>4){
            codeInput.setError("Please input a valid code");
        }
        else {
            verifyCode(codeInput.getText().toString());
        }

    }
}