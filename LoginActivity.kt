package com.example.novelquest

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var signupRedirectText: TextView
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEmail = findViewById(R.id.loginEmail)
        loginPassword = findViewById(R.id.loginPassword)
        loginButton = findViewById(R.id.loginButton)
        signupRedirectText = findViewById(R.id.signupRedirectText)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait...")


        loginButton.setOnClickListener(this)
        signupRedirectText.setOnClickListener(this)
    }

    private fun userLogin() {
        val email = loginEmail.text.toString().trim()
        val password = loginPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter both email and password", Toast.LENGTH_LONG).show()
            return
        }

        progressDialog.show()

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            Constants.URL_LOGIN,
            Response.Listener { response ->
                progressDialog.dismiss()
                try {
                    val obj = JSONObject(response )

                    if (!obj.getBoolean("error" )) {
                        SharedPrefManager.getInstance(applicationContext)
                            .userLogin(
                                obj.getInt("user_id"),
                                obj.getString("fname"),
                                obj.getString("email"),
                                obj.getString("lname")
                            )
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Close the login activity

                        Toast.makeText(
                            applicationContext,
                            "User Login Successful",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {

                        Toast.makeText(
                            applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                progressDialog.dismiss()

                Toast.makeText(
                    applicationContext,
                    "Login failed. ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["email"] = email
                params["password"] = password
                return params
            }
        }
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest)
    }

    override fun onClick(view: View) {
        if (view == loginButton) {
            userLogin()
        }
        if (view == signupRedirectText) {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            finish()
        }
    }
}