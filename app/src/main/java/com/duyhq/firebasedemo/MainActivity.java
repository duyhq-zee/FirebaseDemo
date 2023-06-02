package com.duyhq.firebasedemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;
    DatabaseReference mRef;
    DatabaseReference mBooksTable;

    EditText bookNameET;
    Button addBtn;

    ListView bookLV;
    BookAdapter bookAdapter;

    ArrayList<Book> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        bookNameET = findViewById(R.id.et_book_name);
        addBtn = findViewById(R.id.btn_add);

        bookLV = findViewById(R.id.lv_book);

        addBtn.setOnClickListener(v -> {
            Book newBook = new Book(bookNameET.getText().toString(), "Author X", 1900);
            mBooksTable.push().setValue(newBook);
            bookNameET.setText("");
        });

        bookAdapter = new BookAdapter(this, R.layout.item_book,
                bookList);

        bookLV.setAdapter(bookAdapter);

        mRef = FirebaseDatabase.getInstance("https://fir-demo-32c07-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        mBooksTable = mRef.child("books");

        mBooksTable.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book book = snapshot.getValue(Book.class);
                book.key = snapshot.getKey();
                bookList.add(book);
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Update on local
                for (int i = 0; i < bookList.size(); ++i) {
                    Book book = bookList.get(i);
                    if (book.key.equals(snapshot.getKey())) {
                        bookList.set(i, snapshot.getValue(Book.class));
                        break;
                    }
                }

                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Delete on local
                for (int i = 0; i < bookList.size(); ++i) {
                    Book book = bookList.get(i);
                    if (book.key.equals(snapshot.getKey())) {
                        bookList.remove(i);
                        break;
                    }
                }

                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Used to call MainActivity's method from other activities
    public static MainActivity getInstance() {
        return instance;
    }

    public void deleteBookByPosition(int position) {
        // Delete on Firebase
        mBooksTable.child(bookList.get(position).key).removeValue();
    }

    public void onUpdateButtonClick(String key) {
        // Navigate to update activity
        Intent intent = new Intent(this, UpdateActivity.class);
        // Pass book's key to update
        intent.putExtra("key", key);
        startActivity(intent);
    }

    public void updateBookByKey(String key, String name) {
        // Find book then update name
        Book book = null;
        for (int i = 0; i < bookList.size(); ++i) {
            if (bookList.get(i).key.equals(key)) {
                book = bookList.get(i);
                break;
            }
        }
        book.name = name;

        // Update on Firebase
        mBooksTable.child(key).setValue(book);
    }
}