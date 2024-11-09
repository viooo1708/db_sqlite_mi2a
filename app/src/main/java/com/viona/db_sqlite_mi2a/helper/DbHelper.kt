package com.viona.db_sqlite_mi2a.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // Method untuk membuat tabel database
    override fun onCreate(db: SQLiteDatabase) {
        // Query SQLite untuk membuat tabel
        val query = ("CREATE TABLE " + TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY, " +
                NAMA_LENGKAP + " TEXT, " +
                NAMA_KAMPUS + " TEXT, " +
                EMAIL + " TEXT, " +
                PHONE_NUMBER + " TEXT" + ")")

        // Menjalankan query untuk membuat tabel
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // Menghapus tabel lama jika ada, dan membuat tabel baru
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // Method untuk menambahkan data ke database
    fun addName(NamaLengkap: String, NamaKampus: String, Email: String, PhoneNumber: String) {
        // Membuat ContentValues untuk memasukkan data
        val values = ContentValues()

        // Memasukkan nilai-nilai dengan pasangan key-value
        values.put(NAMA_LENGKAP, NamaLengkap)
        values.put(NAMA_KAMPUS, NamaKampus)
        values.put(EMAIL, Email)
        values.put(PHONE_NUMBER, PhoneNumber)

        // Membuka database untuk menulis data
        val db = this.writableDatabase

        // Memasukkan data ke tabel
        db.insert(TABLE_NAME, null, values)

        // Menutup database
        db.close()
    }

    // Method untuk mendapatkan semua data dari database
    fun getName(): Cursor? {
        // Membuka database untuk membaca data
        val db = this.readableDatabase

        // Mengembalikan Cursor untuk membaca data dari tabel
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }

    companion object {
        // Nama database
        private const val DATABASE_NAME = "DB_PEGAWAI_UDC"

        // Versi database
        private const val DATABASE_VERSION = 1

        // Nama tabel
        const val TABLE_NAME = "tb_pegawai"

        // Kolom tabel
        const val ID_COL = "id"
        const val NAMA_LENGKAP = "nama_lengkap"  // Perbaiki: tidak ada spasi
        const val NAMA_KAMPUS = "nama_kampus"    // Perbaiki: tidak ada spasi
        const val EMAIL = "email"
        const val PHONE_NUMBER = "phone"
    }
}
