package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rsschool.android2021.SecondFragment.Companion.newInstance
import com.rsschool.android2021.interfaces.FirstFragmentListener

class FirstFragment : Fragment() {
    private lateinit var firstFragmentListener: FirstFragmentListener
    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var min: Int = 0
    private var max: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            firstFragmentListener = context as FirstFragmentListener
        } catch (e: Exception) {
            throw RuntimeException("$context must implement FirstFragmentListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        view.findViewById<EditText>(R.id.min_value).doAfterTextChanged {
            view.findViewById<EditText>(R.id.min_value).text.toString().toIntOrNull()?.let {
                min = it
            }
        }

        view.findViewById<EditText>(R.id.max_value).doAfterTextChanged {
            view.findViewById<EditText>(R.id.max_value).text.toString().toIntOrNull()?.let {
                max = it
            }
        }

        generateButton?.setOnClickListener {
            firstFragmentListener.openSecondFragment(min, max)
        }
    }


    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}