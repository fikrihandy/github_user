package academy.bangkit.githubuser.ui

import academy.bangkit.githubuser.R
import academy.bangkit.githubuser.ui.detail.DetailActivity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val data = arguments?.getStringArrayList("dialog")
        lateinit var title: String
        val intent = when (data?.get(0)) {
            "MainActivity" -> {
                title = "Pencarian ${data[1]} Gagal"
                Intent(requireContext(), MainActivity::class.java)
            }

            "DetailActivity" -> {
                title = "Profil ${data[1]} Gagal Dimuat"
                Intent(requireContext(), DetailActivity::class.java)
                    .putExtra(DetailActivity.USERNAME, data[1])
            }

            else -> Intent(requireContext(), MainActivity::class.java)
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage("Harap periksa koneksi internet Anda")
            .setPositiveButton("Muat ulang") { dialog, _ ->
                startActivity(intent)
                requireActivity().finish()
                dialog.dismiss()
            }
            .create()
    }

}