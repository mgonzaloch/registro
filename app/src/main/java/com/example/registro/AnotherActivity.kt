package com.example.registro

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.activity_another.*

class AnotherActivity : AppCompatActivity() {

    val REQUEST_SELECT_CONTACT = 1
    lateinit var contactUri: Uri
    val CAMARA_REQUEST_CODE = 0
    var mutableList: MutableList<String> = mutableListOf("priero","segundo")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another)

        val actionBar: ActionBar? = supportActionBar

        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayShowHomeEnabled(true)

        val intent = intent
        val  aTitle = intent.getStringExtra("iTitle")
        val aDescription = intent.getStringExtra("iDescription")
        val aImageView = intent.getIntExtra("iTmageView", 0)

        actionBar.setTitle("Tipe -> $aTitle")

        a_title.text = aTitle
        a_description.text =aDescription
        Description.text = "Numero de  $aDescription"

        imageIv.setImageResource(aImageView)

    }

    fun contact(view: View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type=ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        if (intent.resolveActivity(packageManager)!= null){
            startActivityForResult(intent,REQUEST_SELECT_CONTACT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == Activity.RESULT_OK){
            contactUri = data!!.data!!

            getPhoneNumber()
        }

        if (requestCode == CAMARA_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){
            Imagen_views.setImageBitmap(data.extras?.get("data") as Bitmap)
        }
        else  {
            Toast.makeText(this,"uncognized code", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getPhoneNumber() {
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val projections = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

        val cursor = contentResolver.query(contactUri,projection,null,null,null)
        val cursors =contentResolver.query(contactUri,projections,null,null,null)
        if(cursor!!.moveToFirst()&& cursors!!.moveToFirst()){
            val PhoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val NameContac = cursors.getString(cursors.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            Number_Contact.text = PhoneNumber
            Name_Contact.text = NameContac
            Description.text = "Numero de $NameContac"
        }
    }

    fun Send(view: View) {
        val texto = " el pedido de ${a_title.text}  \n ${a_description.text} de ${Name_Contact.text} numero ${Number_Contact.text} "
        val sendIntent:Intent=Intent().apply { 
            action=Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,"$texto")

            type ="text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent,null)
        startActivity(shareIntent)
        
    }

    fun camera(view: View) {
        val callCamaraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(callCamaraIntent.resolveActivity(packageManager)!= null){
            startActivityForResult(callCamaraIntent,CAMARA_REQUEST_CODE)
        }
    }


}
