package com.brk.besinkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brk.besinkitabi.model.Besin
import com.brk.besinkitabi.roomdb.BesinDataBase
import com.brk.besinkitabi.service.BesinAPIServis
import com.brk.besinkitabi.util.OzelSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BesinListesiViewModel(application: Application) : AndroidViewModel(application) {

    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()

    private val besinApiServis = BesinAPIServis()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())

    private val guncellemeZamani = 10* 60 * 1000 * 1000 * 1000L

    fun refreshData() {
        val kaydedilmeZAmani = ozelSharedPreferences.zamaniAl()
        if(kaydedilmeZAmani != null && kaydedilmeZAmani!= 0L && System.nanoTime() - kaydedilmeZAmani < guncellemeZamani) {
            //rommdan alıcaz
            verileriRoomdanAl()
        }else {
            verileriInternettenAl()
        }
    }

    private fun verileriRoomdanAl(){
        besinYukleniyor.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val besinListesi = BesinDataBase(getApplication()).besinDao().getAllBesin()
            withContext(Dispatchers.Main) {
                besinleriGoster(besinListesi)
                Toast.makeText(getApplication(),"Beinleri Roomdan Aldık",Toast.LENGTH_LONG).show()
            }
        }
    }
    fun refreshDataFromInternet(){
        verileriInternettenAl()
    }
    private fun verileriInternettenAl(){
        besinYukleniyor.value = true
        viewModelScope.launch(Dispatchers.IO) {
           val besinListesi = besinApiServis.getData()
            withContext(Dispatchers.Main) {
                besinYukleniyor.value = false
                roomaKaydet(besinListesi)
                Toast.makeText(getApplication(),"Besinleri Internetten Aldık",Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun besinleriGoster(besinListesi: List<Besin>) {
        besinler.value = besinListesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false
    }
    private fun roomaKaydet(besinListesi : List<Besin>){
        viewModelScope.launch {
            val dao = BesinDataBase(getApplication()).besinDao()
            dao.deleteAll()
            val uuidListesi = dao.insertAll(*besinListesi.toTypedArray())
            var i = 0
            while (i < besinListesi.size) {
                besinListesi[i].uuid = uuidListesi[i].toInt()
                i = i + 1
            }
            besinleriGoster(besinListesi)
        }

       ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }
}