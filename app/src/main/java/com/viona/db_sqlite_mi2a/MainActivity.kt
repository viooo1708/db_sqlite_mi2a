package com.viona.db_sqlite_mi2a

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.viona.db_sqlite_mi2a.helper.DbHelper

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etKampus: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnShowData: Button
    private lateinit var txtNama: TextView
    private lateinit var txtKampus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Menghubungkan ID dari layout XML ke dalam variabel
        etName = findViewById(R.id.etNamaLengkap)
        etKampus = findViewById(R.id.etNamaKampus)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnShowData = findViewById(R.id.btnShowData)
        txtNama = findViewById(R.id.txtNama)
        txtKampus = findViewById(R.id.txtKampus)

        // Aksi ketika tombol submit ditekan
        btnSubmit.setOnClickListener {
            val dbHelper = DbHelper(this, null)

            // Mengambil data dari input
            val name = etName.text.toString()
            val kampus = etKampus.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()

            // Mengecek apakah data input kosong
            if (name.isNotEmpty() && kampus.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
                // Menyimpan data ke database
                dbHelper.addName(name, kampus, email, phone)

                // Menampilkan pesan bahwa data berhasil disimpan
                Toast.makeText(this, "$name Data berhasil input ke db", Toast.LENGTH_LONG).show()

                // Mengosongkan input field setelah disimpan
                etName.text.clear()
                etKampus.text.clear()
                etEmail.text.clear()
                etPhone.text.clear()
            } else {
                // Menampilkan pesan jika ada data yang kosong
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        // Aksi ketika tombol Show Data ditekan
        btnShowData.setOnClickListener {
            val db = DbHelper(this, null)
            val cursor = db.getName()

            // Membersihkan tampilan teks sebelum menampilkan data baru
            txtNama.text = ""
            txtKampus.text = ""

            // Mengecek apakah ada data dalam cursor
            if (cursor != null && cursor.moveToFirst()) {
                // Menampilkan data pertama
                txtNama.append(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NAMA_LENGKAP)) + "\n")
                txtKampus.append(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NAMA_KAMPUS)) + "\n")

                // Menampilkan data berikutnya jika ada
                while (cursor.moveToNext()) {
                    txtNama.append(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NAMA_LENGKAP)) + "\n")
                    txtKampus.append(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NAMA_KAMPUS)) + "\n")
                }
            } else {
                // Menampilkan pesan jika tidak ada data yang ditemukan
                Toast.makeText(this, "Tidak ada data ditemukan!", Toast.LENGTH_SHORT).show()
            }

            cursor?.close() // Menutup cursor untuk mencegah kebocoran memori
        }

        // Menyelaraskan layout dengan insets dari system bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
