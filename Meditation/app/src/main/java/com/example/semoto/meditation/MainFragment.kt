package com.example.semoto.meditation


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.semoto.meditation.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*


/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // sharedViewModel
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        binding.apply {
            viewmodel = viewModel
            setLifecycleOwner(activity)
        }

        viewModel.initParameters()

        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.playStatus.observe(this, Observer { status ->
            when (status) {
                PlayStatus.BEFORE_START -> btnPlay.setBackgroundResource(R.drawable.button_play)
            }
        })

        viewModel.themePicFileResId.observe(this, Observer { resId ->
            loadBackGroundImage(this, resId, meditation_screen)
        })
    }

    private fun loadBackGroundImage(mainFragment: MainFragment, resId: Int?, meditation_screen: ConstraintLayout?) {

        Glide.with(mainFragment)
            .load(resId)
            .into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    meditation_screen?.background = resource
                }
            })
    }
}
