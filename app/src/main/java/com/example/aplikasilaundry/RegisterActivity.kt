package com.example.aplikasilaundry

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aplikasilaundry.databinding.ActivityRegisterBinding
import com.example.aplikasilaundry.model.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.math.BigInteger
import java.security.MessageDigest

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("User")
        auth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener {
            val email = binding.emailRegister.text.toString()
            val password = binding.passwordRegister.text.toString()
            val username = binding.usernameRegister.text.toString()

            val db = database.push().key!!
            val hashpassword = md5(password)
            val user = user(email,hashpassword,username)

            database.child(db).setValue(user)
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    Toast.makeText(this,"input berhasil",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                }
                .addOnCanceledListener {
                    Toast.makeText(this,"input gagal",Toast.LENGTH_SHORT).show()
                }

        }

    }
    fun md5 (string: String) : String{
        val md5 = MessageDigest.getInstance("MD5")
        return BigInteger(1, md5.digest(string.toByteArray())).toString(16).padStart(32, '0')
    }
}