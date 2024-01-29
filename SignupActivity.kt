package com.example.novelquest


import androidx.appcompat.app.AppCompatActivity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.Response.ErrorListener
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject

class SignupActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var signupUsername: EditText
    private lateinit var signupEmail: EditText
    private lateinit var signupPhone: EditText
    private lateinit var signupPassword: EditText
    private lateinit var signupButton: Button
    private lateinit var loginRedirectText: TextView
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        signupUsername = findViewById(R.id.signupUsername)
        signupEmail = findViewById(R.id.signupEmail)

        signupPhone =findViewById(R.id.signupPhone)

        signupPassword = findViewById(R.id.signupPassword)
        signupButton = findViewById(R.id.signup_button)
        loginRedirectText =findViewById(R.id.loginRedirectText)
        progressDialog = ProgressDialog(this)

        signupButton.setOnClickListener(this)
        loginRedirectText.setOnClickListener(this)
    }

    private fun registerUser() {
        val email = signupEmail.text.toString().trim()
        val fname = signupUsername.text.toString().trim()

        val lname = signupPhone.text.toString().trim()

        val password = signupPassword.text.toString().trim()

        progressDialog.setMessage("Registering User...")
        progressDialog.show()

        val stringRequest = object : StringRequest(
            Method.POST, Constants.URL_REGISTER,
            Listener { response ->
                progressDialog.dismiss()

                try {
                    val jsonObject = JSONObject(response)
                    Toast.makeText(applicationContext, jsonObject.getString("message"), Toast.LENGTH_LONG).show()
                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    startActivity(intent)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            ErrorListener { error ->
                progressDialog.hide()
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["fname"] = fname
                params["email"] = lname
                params["lname"] = email
                params["password"] = password
                return params
            }
        }

        // Assuming you have a RequestHandler class to handle your network requests
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest)
    }

    override fun onClick(view: View) {

        if (view == signupButton){
            registerUser()
        }
        if (view == loginRedirectText){
            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
            finish()
        }

    }


}

