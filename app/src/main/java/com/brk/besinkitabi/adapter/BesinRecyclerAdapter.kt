package com.brk.besinkitabi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.brk.besinkitabi.databinding.BesinRecyclerRowBinding
import com.brk.besinkitabi.model.Besin
import com.brk.besinkitabi.util.gorselIndir
import com.brk.besinkitabi.util.placeHolderYap
import com.brk.besinkitabi.view.BesinListesiFragmentDirections

class BesinRecyclerAdapter(val besinListesi : ArrayList<Besin>) : RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>() {

    class BesinViewHolder(val binding : BesinRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
       val binding = BesinRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BesinViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return besinListesi.size

    }
    fun besinListesiniGuncelle(yeniBesinListesi : List<Besin>) {
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.binding.isim.text = besinListesi[position].isim
        holder.binding.kalori.text = besinListesi[position].kalori

        holder.itemView.setOnClickListener {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayFragment(besinListesi[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.besinImageView.gorselIndir(besinListesi[position].gorsel, placeHolderYap(holder.itemView.context))
    }
}