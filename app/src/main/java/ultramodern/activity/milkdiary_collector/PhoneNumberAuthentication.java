package ultramodern.activity.milkdiary_collector;

import static android.widget.Toast.LENGTH_LONG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.AuthResult;
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

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            Toast.makeText(getApplicationContext(),"Verifying code Automatically",LENGTH_LONG).show();

            signInWithPhoneAuthCredential(phoneAuthCredential);

        }

        public void onVerificationFailed(@NonNull FirebaseException param1FirebaseException) {}
    };

    String verificationId;

    private void signInWithPhoneAuthCredential(PhoneAuthCredential paramPhoneAuthCredential) {

        auth.signInWithCredential(paramPhoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Intent intent = new Intent(PhoneNumberAuthentication.this.getApplicationContext(), gettingready.class);
                    PhoneNumberAuthentication.this.startActivity(intent);
                }
                else{
                    Toast.makeText(PhoneNumberAuthentication.this, "Verification not successful", Toast.LENGTH_SHORT).show();
                }
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
        else if (codeInput.getText().length()>4 && codeInput.getText().length()<6){
            codeInput.setError("Please input a valid code");
        }
        else {
            verifyCode(codeInput.getText().toString());
        }

    }
    public void onClick(View paramView) { verifyCode(this.editText2.getText().toString()); }


    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_phone_number_authentication);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        this.auth = firebaseAuth;
        firebaseAuth.setLanguageCode("en");
        verification(getIntent().getStringExtra("phonenumber"));



    }
}
