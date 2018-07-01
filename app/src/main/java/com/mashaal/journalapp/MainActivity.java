package com.mashaal.journalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mashaal.journalapp.db.DataManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private TextView mTextView;
    private SignInButton mSignInButton;
    private Button mSignOutButton;
    private static final int RC_SIGN_IN = 1001;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ConstraintLayout mLoginScreen;
    private ConstraintLayout mMainScreen;
    private ConstraintLayout mAdditionScreen;
    private ArrayList<DiaryItem> mDataSet = new ArrayList<>();
    private EditText mYearEditText;
    private EditText mMonthEditText;
    private EditText mDayEditText;
    private EditText mContentEditText;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mLoginScreen = (ConstraintLayout) findViewById(R.id.login_screen);
        mMainScreen = (ConstraintLayout)findViewById(R.id.main_screen);
        mAdditionScreen = (ConstraintLayout)findViewById(R.id.addition_screen);
        mTextView = (TextView)findViewById(R.id.middle_text);
        mSignInButton = (SignInButton)findViewById(R.id.sign_in_button);
        mSignOutButton = (Button) findViewById(R.id.sign_out_button);
        mRecyclerView = (RecyclerView)findViewById(R.id.diary_list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mYearEditText = (EditText)findViewById(R.id.year_edit_text);
        mMonthEditText = (EditText)findViewById(R.id.month_edit_text);
        mDayEditText = (EditText)findViewById(R.id.day_edit_text);
        mContentEditText = (EditText)findViewById(R.id.content_edit_text);
        dataManager = DataManager.getDataManager(this);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isSignedIn()){ // user already signed in!
            updateUI(GoogleSignIn.getLastSignedInAccount(this));
        }
    }

    private void updateUI(GoogleSignInAccount lastSignedInAccount) {
        if(lastSignedInAccount != null) {
            showMainScreen();
            updateDataSet();
        }
        else{
            showLoginScreen();
        }
    }

    private void updateDataSet() {
        dataManager.setCurrentUserID(GoogleSignIn.getLastSignedInAccount(this).getId());
        mDataSet = dataManager.getCurrentUserDiaries();
        mAdapter.updateDataSet(mDataSet);
    }

    private void showLoginScreen() {
        mLoginScreen.setVisibility(View.VISIBLE);
        mMainScreen.setVisibility(View.INVISIBLE);
        mAdditionScreen.setVisibility(View.INVISIBLE);
    }

    private void showMainScreen() {
        mMainScreen.setVisibility(View.VISIBLE);
        mLoginScreen.setVisibility(View.INVISIBLE);
        mAdditionScreen.setVisibility(View.INVISIBLE);
    }

    private boolean isSignedIn(){
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }

    public void signOut(View view) {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(GoogleSignIn.getLastSignedInAccount(MainActivity.this));
                    }
                });
    }

    public void newDiary(View view) {
        showAdditionScreen();

    }

    private void showAdditionScreen() {
        mMainScreen.setVisibility(View.INVISIBLE);
        mAdditionScreen.setVisibility(View.VISIBLE);
        mLoginScreen.setVisibility(View.INVISIBLE);
    }

    public void addNewDiary(View view) {
        int year = Integer.parseInt(mYearEditText.getText().toString());
        int month = Integer.parseInt(mMonthEditText.getText().toString());
        int day = Integer.parseInt(mDayEditText.getText().toString());
        String content = mContentEditText.getText().toString();
        DiaryItem item = new DiaryItem(year,month,day);
        item.setDairyContent(content);
        dataManager.addNewDiaryForCurrentUser(item);
        mDataSet.add(item);
        mAdapter.updateDataSet(mDataSet);
        mMainScreen.setVisibility(View.VISIBLE);
        mAdditionScreen.setVisibility(View.INVISIBLE);
    }
}
