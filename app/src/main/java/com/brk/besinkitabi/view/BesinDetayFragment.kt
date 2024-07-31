package com.brk.besinkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.brk.besinkitabi.databinding.FragmentBesinDetayBinding
import com.brk.besinkitabi.util.gorselIndir
import com.brk.besinkitabi.util.placeHolderYap
import com.brk.besinkitabi.viewmodel.BesinDetayiViewModel


class BesinDetayFragment : Fragment() {
    private lateinit var binding : FragmentBesinDetayBinding
    private lateinit var viewModel : BesinDetayiViewModel
    var besinId : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBesinDetayBinding.inflate(inflater,container,false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[BesinDetayiViewModel::class.java]

        arguments?.let {
            besinId = BesinDetayFragmentArgs.fromBundle(it).besinId
        }
        viewModel.roomVerisiniAL(besinId)
        observeLiveData()
        return view
    }
    private fun observeLiveData() {
        viewModel.besinLiveData.observe(viewLifecycleOwner) {
            binding.besinIsim.text = it.isim
            binding.besinKalori.text = it.kalori
            binding.besinProtein.text = it.protein
            binding.besinYag.text = it.yag
            binding.besinKarbonhidrat.text = it.karbonhidrat
            binding.besinImage.gorselIndir(it.gorsel, placeHolderYap(requireContext()))
        }
    }



}