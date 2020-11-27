package com.example.registro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android . content . pm . ActivityInfo
import android.util.Log
import android.widget.*
import androidx.room.Room
import com.example.registro.Database.UserRepository
import com.example.registro.Model.User
import io.reactivex.disposables.CompositeDisposable


class MainActivity : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var viewusuario:TextView
    private lateinit var viewcorreo:TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth: FirebaseAuth


    private val mutableList= mutableListOf<Registros>()

    private var indice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        requestedOrientation =    ( ActivityInfo . SCREEN_ORIENTATION_PORTRAIT )
        setContentView ( R . layout . activity_main )
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        viewusuario=findViewById(R.id.viewusuario)
        txtName=findViewById(R.id.txtName)
        txtEmail=findViewById(R.id.txtEmail)
        txtPassword=findViewById(R.id.txtPassword)
        viewcorreo=findViewById(R.id.viewcorreo)


        progressBar= findViewById(R.id.progresBarR)

        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()

       dbReference=database.reference.child("user")


    }


    fun register(view: View){
        createNewAccount()
    }

    private fun createNewAccount() {
        val name: String = txtName.text.toString()
        val email: String = txtEmail.text.toString()
        val password: String = txtPassword.text.toString()



        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                progressBar.visibility=View.VISIBLE
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                    task ->
                    if (task.isSuccessful){
                        val user: FirebaseUser?=auth.currentUser
                        verifyEmail(user)
                        mutableList.add(0,Registros(
                            textusuario = txtName.text.toString(),
                            textcorreo = txtEmail.text.toString()

                        ))
                        //var db = Room.databaseBuilder(applicationContext,UserDatabase::class.java,"UserDB").build()

                        //  val id = user?.uid!!
                       // val userBD=dbReference.child(user!!.uid)
                       // Toast.makeText(this,"entro al user", Toast.LENGTH_LONG).show()
                       // userBD.child("Name").setValue(name)
                      // userBD.child("Email").setValue(email)
                        action()
                       // Toast.makeText(this,"los regisro men", Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(this,"No registro", Toast.LENGTH_LONG).show()
                    }

                }
        }
        else{
            Toast.makeText(this,"datos en blanco", Toast.LENGTH_LONG).show()
        }

    }

    fun atras(view: View){
        if(mutableList.size==0){
            Toast.makeText(this,"datos en blanco", Toast.LENGTH_SHORT).show()
        }
        else{
            if(indice.equals(0))
                indice = mutableList.size-1
            else
                indice = indice-1
            cambioPregunta()
        }
    }

    fun cantidad(view: View){
        Toast.makeText(this,"La cantidad es "+mutableList.size,Toast.LENGTH_SHORT).show()
    }

    fun siguiente(view: View){
        if(mutableList.size==0){
            Toast.makeText(this,"datos en blanco", Toast.LENGTH_SHORT).show()
        }
         else {
            indice = ( indice + 1 ) % mutableList.size
            cambioPregunta()
        }
    }

    private  fun action(){
        startActivity(Intent(this,presesentacion::class.java))
    }

    private fun cambioPregunta() {
        val nombre = mutableList[indice].textusuario
        val correo = mutableList[indice].textcorreo
        viewusuario.setText(nombre)
        viewcorreo.setText(correo)
    }

    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                    task ->

                if(task.isSuccessful){
                    Toast.makeText(this,"Registro Completo", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this,"Error al registrar", Toast.LENGTH_LONG).show()
                }
                progressBar.visibility=View.GONE
            }
    }
}

