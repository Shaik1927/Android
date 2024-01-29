package com.example.novelquest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private lateinit var profileName: EditText
    private lateinit var profileEmail: EditText
    private lateinit var profilePhone: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!SharedPrefManager.getInstance(requireContext()).isLoggedIn()) {
            requireActivity().finish()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            return
        }

        profileName = view.findViewById(R.id.profileName)
        profileEmail = view.findViewById(R.id.profileEmail)
        profilePhone = view.findViewById(R.id.profilePhone)

        profileName.setText(SharedPrefManager.getInstance(requireContext()).getUsername())
        profileEmail.setText(SharedPrefManager.getInstance(requireContext()).getUserEmail())
        profilePhone.setText(SharedPrefManager.getInstance(requireContext()).getUserLname())
    }
}
