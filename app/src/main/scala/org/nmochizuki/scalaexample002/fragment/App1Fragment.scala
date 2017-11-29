package org.nmochizuki.scalaexample002.fragment

import android.view.{LayoutInflater, View, ViewGroup}
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import org.nmochizuki.scalaexample002.R

class App1Fragment extends Fragment {

  val TAG = "App1Fragment"

  override def onAttach(context: Context): Unit = {
    super.onAttach(context)
    Log.d(TAG, "onAttach")
  }

  override def onCreateView(inflater: LayoutInflater,
                            container: ViewGroup,
                            savedInstanceState: Bundle): View = {
    super.onCreateView(inflater, container, savedInstanceState)
    Log.d(TAG, "onCreateView")

    inflater.inflate(R.layout.fragment_app1, container, false)
  }

  override def onDetach(): Unit = {
    super.onDetach()
    Log.d(TAG, "onDetach")

  }

}
