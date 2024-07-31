package com.brk.besinkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brk.besinkitabi.model.Besin
import com.brk.besinkitabi.roomdb.BesinDataBase
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application: Application) : AndroidViewModel(application){
    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiniAL(uuid : Int) {
        viewModelScope.launch {
            val dao = BesinDataBase(getApplication()).besinDao()
            val besin = dao.getBesin(uuid)
            besinLiveData.value = besin
        }
    }
}