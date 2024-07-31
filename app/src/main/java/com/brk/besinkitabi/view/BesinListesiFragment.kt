package com.brk.besinkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brk.besinkitabi.adapter.BesinRecyclerAdapter
import com.brk.besinkitabi.databinding.FragmentBesinListesiBinding
import com.brk.besinkitabi.service.BesinAPI
import com.brk.besinkitabi.util.benimEklentim
import com.brk.besinkitabi.viewmodel.BesinListesiViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class BesinListesiFragment : Fragment() {


private lateinit var binding : FragmentBesinListesiBinding
private lateinit var  viewModel: BesinListesiViewModel
private val besinRecyclerAdapter = BesinRecyclerAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBesinListesiBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this) [BesinListesiViewModel::class.java]
        viewModel.refreshData()
        binding.besinRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.besinRecyclerView.adapter = besinRecyclerAdapter
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.besinRecyclerView.visibility = View.GONE
            binding.besinHataMesaji.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            viewModel.refreshDataFromInternet()
            binding.besinRecyclerView.visibility = View.VISIBLE
            binding.swipeRefreshLayout.isRefreshing = false
        }
        observeLiveData()
        init()
        return binding.root


    }
    private fun observeLiveData(){
        viewModel.besinler.observe(viewLifecycleOwner) {
            //adpter
            besinRecyclerAdapter.besinListesiniGuncelle(it)
            binding.besinRecyclerView.visibility = View.VISIBLE

        }
        viewModel.besinHataMesaji.observe(viewLifecycleOwner) {
            if(it) {
                binding.besinHataMesaji.visibility = View.VISIBLE
                binding.besinRecyclerView.visibility = View.GONE
            }else {
                binding.besinHataMesaji.visibility = View.GONE
            }
        }
        viewModel.besinYukleniyor.observe(viewLifecycleOwner) {
            if(it) {
                binding.besinHataMesaji.visibility = View.GONE
                binding.besinRecyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    private fun init(){
        val x = ""
        x.benimEklentim("sadasd")
    }



}