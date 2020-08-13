package com.thoughtworks.androidactivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ContactsActivity extends AppCompatActivity {
    static final int REQUEST_SELECT_CONTACTS = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button chooseContact = findViewById(R.id.select_contacts);
        chooseContact.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectContacts();
                    }
                }
        );
    }

    public void selectContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK)
                .setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(intent, REQUEST_SELECT_CONTACTS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_CONTACTS& resultCode == RESULT_OK) {
            assert data != null;
            Uri contractUri = data.getData();
            assert contractUri != null;
            Cursor phoneCursor = getContentResolver().query(contractUri, null, null, null, null);
            assert phoneCursor != null;
            if (phoneCursor.moveToFirst()){
                String contactsName = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phoneCursor.close();
                TextView contactInfo = findViewById(R.id.contacts_number);
                contactInfo.setText(contactsName.concat(phoneNumber) );
            }
        }
}
}